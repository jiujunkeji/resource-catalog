/**
 * Copyright 2018 人人开源 http://www.renren.io
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package io.renren.modules.sys.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import io.renren.common.utils.Constant;
import io.renren.common.utils.InfoJson;
import io.renren.common.utils.R;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.service.SysUserRoleService;
import io.renren.modules.sys.service.SysUserService;
import io.renren.modules.sys.shiro.ShiroUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

/**
 * 登录相关
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年11月10日 下午1:15:31
 */
@Controller
public class SysLoginController {
	@Autowired
	private Producer producer;
	@Autowired
	private SysUserService userService;
	@Autowired
	private SysUserRoleService sysUserRoleService;

	@RequestMapping("captcha.jpg")
	public void captcha(HttpServletResponse response)throws IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");

        //生成文字验证码
        String text = producer.createText();
        //生成图片验证码
        BufferedImage image = producer.createImage(text);
        //保存到shiro session
        ShiroUtils.setSessionAttribute(Constants.KAPTCHA_SESSION_KEY, text);
        
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
	}
	
	/**
	 * 登录
	 */
	@ResponseBody
	@RequestMapping(value = "/sys/login", method = RequestMethod.POST)
	public R login(String username, String password, String captcha) {
		/*String kaptcha = ShiroUtils.getKaptcha(Constants.KAPTCHA_SESSION_KEY);
		if(!captcha.equalsIgnoreCase(kaptcha)){
			return R.error("验证码不正确");
		}*/
		
		try{
			Subject subject = ShiroUtils.getSubject();
			UsernamePasswordToken token = new UsernamePasswordToken(username, password);
			subject.login(token);
		}catch (UnknownAccountException e) {
			return R.error(e.getMessage());
		}catch (IncorrectCredentialsException e) {
			return R.error("账号或密码不正确");
		}catch (LockedAccountException e) {
			return R.error("账号已被锁定,请联系管理员");
		}catch (AuthenticationException e) {
			return R.error("账户验证失败");
		}
	    
		return R.ok();
	}

	/**
	 * 获取access_token
	 */
	@RequestMapping(value = "/getToken", method = RequestMethod.GET)
	public String getToken(String code, String state) {
		System.out.println("code= " + code);
		System.out.println("state= " + state);
		Map params1 = new HashMap();
		params1.put("clinet_id",Constant.CLIENT_ID);
		params1.put("client_secret",Constant.CLIENT_SECRET);
		params1.put("code",code);
		params1.put("grant_type","authorization_code");
		params1.put("redirect_uri",Constant.REDIRECT_URI);
		JSONObject tokenRes = InfoJson.postJson(Constant.TOKEN_URI,params1);
		System.out.println("tokenRes : " + tokenRes);
		Map params2 = new HashMap();
		params2.put("clientId",Constant.CLIENT_ID);
		params2.put("accessToken",tokenRes.getString("access_token"));
		params2.put("loginName",tokenRes.getString("loginName"));
		JSONObject userRes = InfoJson.postJson(Constant.GET_USER_URI,params2);
		System.out.println("userRes : " + userRes);

		JSONObject data = userRes.getJSONObject("data");
		JSONObject userInfo = data.getJSONObject("userInfo");
		JSONArray roleList = data.getJSONArray("roleList");
		oauthLogin(userInfo,roleList,tokenRes.getString("access_token"),tokenRes.getString("refresh_token"));
		return "index";
	}

	/**
	 * 验证用户并登录
	 */
	public void oauthLogin(JSONObject userInfo, JSONArray roleList, String accessToken, String refreshToken) {
		String idnumber = userInfo.getString("idnumber");
		String password = "1";
		SysUserEntity user = userService.selectOne(new EntityWrapper<SysUserEntity>().eq("idnumber",idnumber));
		if(user == null){
			user = new SysUserEntity();
			user.setIdnumber(idnumber);
			user.setAccessToken(accessToken);
			user.setRefreshToken(refreshToken);
			user.setDeptId(1L);
			user.setStatus(1);
			user.setUsername(userInfo.getString("loginName"));
			user.setName(userInfo.getString("name"));
			user.setMobile(userInfo.getString("mobile"));
			user.setEmail(userInfo.getString("email"));
			user.setCreateTime(new Date());
			//sha256加密
			String salt = RandomStringUtils.randomAlphanumeric(20);
			user.setSalt(salt);
			user.setPassword(ShiroUtils.sha256(password, user.getSalt()));
			userService.insert(user);
		}else{
			user.setAccessToken(accessToken);
			user.setRefreshToken(refreshToken);
			user.setDeptId(1L);
			user.setStatus(1);
			user.setUsername(userInfo.getString("loginName"));
			user.setName(userInfo.getString("name"));
			user.setMobile(userInfo.getString("mobile"));
			user.setEmail(userInfo.getString("email"));
			user.setCreateTime(new Date());
			//sha256加密
			String salt = RandomStringUtils.randomAlphanumeric(20);
			user.setSalt(salt);
			user.setPassword(ShiroUtils.sha256(password, user.getSalt()));
			userService.updateById(user);
		}
		List<Long> roleIdList = new ArrayList<Long>();
		for(int i = 0; i < roleList.size(); i++){
			switch (roleList.getJSONObject(i).getString("roleCode")){
				case Constant.ROLE_ID_1:
					roleIdList.add(1L);
					break;
				case Constant.ROLE_ID_2:
					roleIdList.add(2L);
					break;
				case Constant.ROLE_ID_3:
					roleIdList.add(3L);
					break;
				default:
					break;
			}
		}
		//保存用户与角色关系
		sysUserRoleService.saveOrUpdate(user.getUserId(), roleIdList);
		Subject subject = ShiroUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), password);
		subject.login(token);
	}

	/**
	 * 退出
	 */
	@RequestMapping(value = "logout", method = RequestMethod.GET)
	public String logout() {
		ShiroUtils.logout();
		return "redirect:login.html";
	}
}
