package io.renren.modules.sys.dto;

import io.renren.modules.sys.entity.SysDeptEntity;

import java.util.List;

/**
 * @Auther: wdh
 * @Date: 2019-08-21 16:05
 * @Description:
 */
public class SysDeptDto {
    private Long deptId;
    private String deptName;
    private List<SysDeptDto> childrenList;

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public List<SysDeptDto> getChildrenList() {
        return childrenList;
    }

    public void setChildrenList(List<SysDeptDto> childrenList) {
        this.childrenList = childrenList;
    }
}
