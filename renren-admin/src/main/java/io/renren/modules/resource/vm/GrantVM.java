package io.renren.modules.resource.vm;

import io.renren.modules.sys.entity.SysDeptEntity;
import io.renren.modules.sys.entity.SysUserEntity;

import java.util.List;

/**
 * @Auther: wdh
 * @Date: 2019-08-20 14:49
 * @Description:
 */
public class GrantVM {
    private List<SysUserEntity> userList;
    private List<SysDeptEntity> deptList;
    private Long catalogId;

    public List<SysUserEntity> getUserList() {
        return userList;
    }

    public void setUserList(List<SysUserEntity> userList) {
        this.userList = userList;
    }

    public List<SysDeptEntity> getDeptList() {
        return deptList;
    }

    public void setDeptList(List<SysDeptEntity> deptList) {
        this.deptList = deptList;
    }

    public Long getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(Long catalogId) {
        this.catalogId = catalogId;
    }
}
