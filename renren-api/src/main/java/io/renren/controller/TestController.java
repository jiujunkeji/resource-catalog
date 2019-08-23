package io.renren.controller;

/**
 * @Auther: wdh
 * @Date: 2019-08-22 15:34
 * @Description:
 */

import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.entity.DataDto;
import io.renren.entity.Menu;
import io.renren.entity.UserInfo;
import io.renren.form.LoginForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 登录接口
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-03-23 15:31
 */
@RestController
@RequestMapping("/oauth")
@Api(tags="测试单点登录接口")
public class TestController {

    @GetMapping("authorize")
    public void authorize(String client_id, String redirect_uri, String response_type, String state, HttpServletResponse response) throws IOException {
        System.out.println("clinet_id= " + client_id);
        System.out.println("redirect_uri= " + redirect_uri);
        System.out.println("response_type= " + response_type);
        System.out.println("state= " + state);
        redirect_uri = URLDecoder.decode(redirect_uri,"UTF-8");
        String path = redirect_uri + "?code=" + response_type + "&state=" + state;
        response.sendRedirect(path);
    }

    @PostMapping("token")
    public R authorize(String client_id, String client_secret, String code, String grant_type, String redirect_uri) throws IOException {
        System.out.println("clinet_id= " + client_id);
        System.out.println("client_secret= " + client_secret);
        System.out.println("code= " + code);
        System.out.println("grant_type= " + grant_type);
        System.out.println("redirect_uri= " + redirect_uri);
        return R.ok()
                .put("access_token","257214a8-3e8c-4106-939a-35ac67338ec5")
                .put("token_type","bearer")
                .put("refresh_token","98d75dd7-db36-4299-ba7a-e1be989bb042")
                .put("expires_in",58)
                .put("scope","all")
                .put("loginName","wangdehai")
                .put("grantType","authorization_code");
    }

    @PostMapping("getUserInfoById")
    public R getUserInfoById(String clientId, String accessToken, String loginName) throws IOException {
        System.out.println("clientId= " + clientId);
        System.out.println("accessToken= " + accessToken);
        System.out.println("loginName= " + loginName);

        DataDto data = new DataDto();
        UserInfo userInfo = new UserInfo();
        userInfo.setIdnumber("320108199910015014");
        userInfo.setLoginName("wangdehai");
        userInfo.setMobile("18334784662");
        userInfo.setName("王德海");
        userInfo.setEmail("18334784662@163.com");
        data.setUserInfo(userInfo);
        Menu menu1 = new Menu();
        menu1.setMenuCode("M000001");
        menu1.setMenuName("平台系统管理");
        Menu menu2 = new Menu();
        menu2.setMenuCode("M000002");
        menu2.setMenuName("停用启用");
        List<Menu> menuList = new ArrayList<Menu>();
        menuList.add(menu1);
        menuList.add(menu2);
        data.setMenuList(menuList);
        List<String> roleList = new ArrayList<String>();
        roleList.add("R00001");
        roleList.add("R00002");
        roleList.add("R00003");
        roleList.add("R00004");
        roleList.add("R00005");
        data.setRoleList(roleList);
        return R.ok().put("data",data);
    }
}
