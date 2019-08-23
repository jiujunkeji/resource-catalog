package io.renren.entity;

import java.util.List;

/**
 * @Auther: wdh
 * @Date: 2019-08-23 10:01
 * @Description:
 */
public class DataDto {

    private UserInfo userInfo;

    private List<Menu> menuList;

    private List<String> roleList;

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public List<Menu> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<Menu> menuList) {
        this.menuList = menuList;
    }

    public List<String> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<String> roleList) {
        this.roleList = roleList;
    }


}
