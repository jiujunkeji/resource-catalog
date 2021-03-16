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

package io.renren.common.aspect;

import com.alibaba.fastjson.JSONObject;



import io.renren.common.annotation.SysLog;
import io.renren.common.utils.Constant;
import io.renren.common.utils.InfoJson;
import io.renren.modules.sys.entity.SysLogEntity;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.service.SysLogService;
import io.renren.common.utils.HttpContextUtils;
import io.renren.common.utils.IPUtils;

import io.renren.modules.sys.service.SysUserService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 系统日志，切面处理类
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.3.0 2017-03-08
 */
@Aspect
@Component
public class SysLogAspect {
	@Autowired
	private SysLogService sysLogService;
	@Autowired
	private SysUserService sysUserService;

	/**
	 * spel解析器
	 */
	private static final SpelExpressionParser parser= new SpelExpressionParser();

	/**
	 * spel缓存
	 */
	private static final ConcurrentHashMap<String, Expression> expressionMap = new ConcurrentHashMap<>(256);


	@Pointcut("@annotation(io.renren.common.annotation.SysLog)")
	public void logPointCut() { 
		
	}

	@Around("logPointCut()")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		long beginTime = System.currentTimeMillis();
		//执行方法
		Object result = point.proceed();
		//执行时长(毫秒)
		long time = System.currentTimeMillis() - beginTime;

		//保存日志
		saveSysLog(point, time);

		return result;
	}

	private void saveSysLog(ProceedingJoinPoint joinPoint, long time) {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		SysLog syslog = method.getAnnotation(SysLog.class);
		Object [] args = joinPoint.getArgs();
		String p = parseExpression(syslog.content(),method,args);
		if(syslog != null) {
			//用户名
			SysUserEntity user = (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
			String username = user.getUsername();
			String name = user.getName();
			String refreshToken = user.getRefreshToken();
			if(StringUtils.isNotEmpty(syslog.type())){
				SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Map params = new HashMap();
				params.put("sysNum", Constant.SYS_NUM);
				params.put("systemSecret", Constant.CLIENT_SECRET);
				params.put("userName", name);
				params.put("loginName", username);
				params.put("operationType", syslog.type());
				params.put("content", syslog.content());
				params.put("dateTime", s.format(new Date()));
				JSONObject result = InfoJson.postJson(Constant.OPERATION_URI, params);
				String code = result.getString("RetCode");
				if ("S".equals(code)) {
					System.out.println("审计日志保存成功");
				} else {
					System.out.println("审计日志保存失败");
					System.out.println("失败原因：" + result.getString("RetMsg"));
				}
			}
			if(StringUtils.isNotEmpty(syslog.menuId())){
				//刷新token
				Map params1 = new HashMap();
				params1.put("clinet_id",Constant.CLIENT_ID);
				params1.put("client_secret",Constant.CLIENT_SECRET);
				params1.put("refresh_token",refreshToken);
				params1.put("grant_type","refresh_token");
				params1.put("scope","all");
				JSONObject tokenRes = InfoJson.postJson(Constant.REFRESH_TOKEN_URI,params1);
				String token = tokenRes.getString("access_token");
				if(StringUtils.isNotEmpty(token)){
					//更新user的token值
					String reToken = tokenRes.getString("refresh_token");
					user.setAccessToken(token);
					user.setRefreshToken(reToken);
					sysUserService.updateById(user);
					//调用鉴权方法
					Map params2 = new HashMap();
					params2.put("clientId",Constant.CLIENT_ID);
					params2.put("accessToken",token);
					params2.put("loginName",username);
					params2.put("menuId",syslog.menuId());
					JSONObject authMenuRes = InfoJson.postJson(Constant.AUTHMENU_URI,params2);
					if(authMenuRes.getInteger("code") == 0){
						System.out.println("鉴权成功");
					}else{
						System.out.println("鉴权失败");
					}
				}else{
					System.out.println("刷新token失败");
				}
			}
		}
	}

	/**
	 * 解析
	 * @param expressionString
	 * @param method
	 * @param args
	 * @return
	 */
	private String parseExpression(String expressionString, Method method, Object[] args) {
		//获取被拦截方法参数名列表
		LocalVariableTableParameterNameDiscoverer discoverer = new LocalVariableTableParameterNameDiscoverer();
		String[] paramNameArr = discoverer.getParameterNames(method);
		//SPEL解析
		ExpressionParser parser = new SpelExpressionParser();
		StandardEvaluationContext context = new StandardEvaluationContext();
		if(expressionString.indexOf("root") > -1){
			context = new StandardEvaluationContext(args[0]);
		}else{
			for (int i = 0; i < paramNameArr.length; i++) {
				System.out.println("i:" +i);
				System.out.println(args[i]);
				System.out.println();
				if("params".equals(paramNameArr[i])){
					context.setVariables(JSONObject.parseObject(JSONObject.toJSONString(args[i]), Map.class));
				}else{
					context.setVariable(paramNameArr[i], args[i]);
				}

			}
		}

		String result = getExpression(expressionString).getValue(context, String.class);
		return result;
	}

	/**
	 * 从缓存中获取spel编译表达式
	 *
	 * @return              SpelExpression
	 */
	private static Expression getExpression(String el) {
		Expression expression = expressionMap.get(el);

		if (expression != null) {
			return expression;
		}

		return expressionMap.computeIfAbsent(el, k -> parser.parseRaw(el));
	}
}
