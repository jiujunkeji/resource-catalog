package io.renren.modules.xj.service.impl;

import io.renren.common.utils.Constant;
import io.renren.modules.resource.entity.CatalogUserEntity;
import io.renren.modules.resource.entity.ResourceCatalogEntity;
import io.renren.modules.sys.entity.SysDeptEntity;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.xj.entity.XjSafeEntity;
import io.renren.modules.xj.service.XjSafeService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.xj.dao.XjCatalogDao;
import io.renren.modules.xj.entity.XjCatalogEntity;
import io.renren.modules.xj.service.XjCatalogService;


@Service("xjCatalogService")
public class XjCatalogServiceImpl extends ServiceImpl<XjCatalogDao, XjCatalogEntity> implements XjCatalogService {

    @Autowired
    private XjSafeService safeService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String name = (String)params.get("name");
        String reviewState = (String)params.get("reviewState");
        String pushState = (String)params.get("pushState");
        EntityWrapper<XjCatalogEntity> wrapper = new EntityWrapper<XjCatalogEntity>();
        if(StringUtils.isNotBlank(reviewState)){
            wrapper.eq("review_state",reviewState);
        }else if(StringUtils.isNotBlank(pushState)){
            wrapper.eq("push_state",pushState);
        }

        wrapper.like(StringUtils.isNotBlank(name),"catalog_name",name);
        wrapper.eq("is_deleted",0);
        Page<XjCatalogEntity> page = this.selectPage(
                new Query<XjCatalogEntity>(params).getPage(),
                wrapper
        );
        for(XjCatalogEntity xjCatalogEntity : page.getRecords()){
            XjCatalogEntity parentXjCatalogEntity = this.selectById(xjCatalogEntity.getParentId());
            if(parentXjCatalogEntity != null){
                xjCatalogEntity.setParentName(parentXjCatalogEntity.getCatalogName());
            }
        }
        return new PageUtils(page);
    }

    @Override
    public List<XjCatalogEntity> selectUserList(EntityWrapper<XjCatalogEntity> wrapper, SysUserEntity user){
        List<XjCatalogEntity> allList = new ArrayList<XjCatalogEntity>();
        List<XjCatalogEntity> list = new ArrayList<XjCatalogEntity>();
        allList = this.selectList(wrapper);
        //系统管理员，拥有最高权限
        if(user.getUserId() == Constant.SUPER_ADMIN){
            return allList;
        }
        Set<Long> idSet = new HashSet<Long>();
        //根据安全等级查询他有权限查看的所有目录，将所有的catalogId添加到Set
        int safeCode = user.getSafeCode();
        List<XjSafeEntity> catalogSafeList = safeService.selectList(
                new EntityWrapper<XjSafeEntity>().ge("safe_code",safeCode)
        );
        if(catalogSafeList != null && catalogSafeList.size() > 0){
            for(XjSafeEntity safeEntity : catalogSafeList){
                idSet.add(safeEntity.getCatalogId());
            }
        }
        for(XjCatalogEntity catalog : allList){
            if(idSet.contains(catalog.getCatalogId())){
                list.add(catalog);
            }
        }
        return list;
    }
}
