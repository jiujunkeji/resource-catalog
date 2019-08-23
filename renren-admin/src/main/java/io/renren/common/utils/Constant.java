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

package io.renren.common.utils;

/**
 * 常量
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2016-11-15
 */
public class Constant {
	/** 超级管理员ID */
	public static final int SUPER_ADMIN = 1;
    /** 数据权限过滤 */
	public static final String SQL_FILTER = "sql_filter";
    /** 应用唯一标识 */
    public static final String CLIENT_ID = "client_id";
    /** 应用密钥 */
    public static final String CLIENT_SECRET = "client_secret";
    /** 请求code接口*/
    public static final String AUTHORIZE_URI = "http://localhost:8081/renren-api/oauth/authorize";
    /** 回调url*/
    public static final String REDIRECT_URI = "http://localhost:8080/resource-catalog/getToken";
    /** 获取access_token接口*/
    public static final String TOKEN_URI = "http://localhost:8081/renren-api/oauth/token";
    /** 获取用户信息接口*/
    public static final String GET_USER_URI = "http://localhost:8081/renren-api/oauth/getUserInfoById";

    /** 中心角色1（目录创建人员）*/
    public static final String ROLE_ID_1 = "R00001";
    /** 中心角色2（目录管理人员）*/
    public static final String ROLE_ID_2 = "R00002";
    /** 中心角色3（目录使用人员）*/
    public static final String ROLE_ID_3 = "R00003";
    /**
	 * 菜单类型
	 */
    public enum MenuType {
        /**
         * 目录
         */
    	CATALOG(0),
        /**
         * 菜单
         */
        MENU(1),
        /**
         * 按钮
         */
        BUTTON(2);

        private int value;

        MenuType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
    
    /**
     * 定时任务状态
     */
    public enum ScheduleStatus {
        /**
         * 正常
         */
    	NORMAL(0),
        /**
         * 暂停
         */
    	PAUSE(1);

        private int value;

        ScheduleStatus(int value) {
            this.value = value;
        }
        
        public int getValue() {
            return value;
        }
    }

    /**
     * 云服务商
     */
    public enum CloudService {
        /**
         * 七牛云
         */
        QINIU(1),
        /**
         * 阿里云
         */
        ALIYUN(2),
        /**
         * 腾讯云
         */
        QCLOUD(3);

        private int value;

        CloudService(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

}
