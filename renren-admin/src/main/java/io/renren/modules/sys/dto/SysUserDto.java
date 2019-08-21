package io.renren.modules.sys.dto;

import io.renren.modules.sys.entity.SysUserEntity;

import java.util.List;

/**
 * @Auther: wdh
 * @Date: 2019-08-21 16:58
 * @Description:
 */
public class SysUserDto {
    private String deptName;
    private List<SysUserEntity> userList;

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public List<SysUserEntity> getUserList() {
        return userList;
    }

    public void setUserList(List<SysUserEntity> userList) {
        this.userList = userList;
    }
}
