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
    private Long userId;
    private Long organisationId;
    private List<Long> catalogIdList;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getOrganisationId() {
        return organisationId;
    }

    public void setOrganisationId(Long organisationId) {
        this.organisationId = organisationId;
    }

    public List<Long> getCatalogIdList() {
        return catalogIdList;
    }

    public void setCatalogIdList(List<Long> catalogIdList) {
        this.catalogIdList = catalogIdList;
    }
}
