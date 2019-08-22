package io.renren.modules.resource.vm;

import io.renren.modules.sys.entity.SysDeptEntity;
import io.renren.modules.sys.entity.SysUserEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: wdh
 * @Date: 2019-08-20 14:49
 * @Description:
 */
public class GrantVM {
    private List<Long> userList = new ArrayList<Long>();;
    private List<Long> deptList = new ArrayList<Long>();
    private Long catalogId;

    public List<Long> getUserList() {
        return userList;
    }

    public void setUserList(List<Long> userList) {
        this.userList = userList;
    }

    public List<Long> getDeptList() {
        return deptList;
    }

    public void setDeptList(List<Long> deptList) {
        this.deptList = deptList;
    }

    public Long getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(Long catalogId) {
        this.catalogId = catalogId;
    }
}
