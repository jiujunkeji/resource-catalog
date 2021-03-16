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

package io.renren.modules.sys.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.Constant;
import io.renren.common.utils.InfoJson;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.sys.dao.SysLogDao;
import io.renren.modules.sys.entity.SysLogEntity;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.service.SysLogService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Service("sysLogService")
public class SysLogServiceImpl extends ServiceImpl<SysLogDao, SysLogEntity> implements SysLogService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String key = (String)params.get("key");

        Page<SysLogEntity> page = this.selectPage(
            new Query<SysLogEntity>(params).getPage(),
            new EntityWrapper<SysLogEntity>().like(StringUtils.isNotBlank(key),"username", key)
        );

        return new PageUtils(page);
    }

    @Override
    public void saveSysLog(String type, String content) {
        //用户名
        SysUserEntity user = (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
        String username = user.getUsername();
        String name = user.getName();
        String refreshToken = user.getRefreshToken();
        if(StringUtils.isNotEmpty(type)){
            SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Map params = new HashMap();
            params.put("sysNum", Constant.SYS_NUM);
            params.put("systemSecret", Constant.CLIENT_SECRET);
            params.put("userName", name);
            params.put("loginName", username);
            params.put("operationType", type);
            params.put("content", content);
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
    }
}
