package io.renren.modules.resource.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.resource.dao.ResourceCatalogDao;
import io.renren.modules.resource.entity.ResourceCatalogEntity;
import io.renren.modules.resource.service.ResourceCatalogService;


@Service("resourceCatalogService")
public class ResourceCatalogServiceImpl extends ServiceImpl<ResourceCatalogDao, ResourceCatalogEntity> implements ResourceCatalogService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String name = (String) params.get("name");
        Page<ResourceCatalogEntity> page = this.selectPage(
                new Query<ResourceCatalogEntity>(params).getPage(),
                new EntityWrapper<ResourceCatalogEntity>()
                        .like(StringUtils.isNotEmpty(name),"name",name)
                        .eq("is_used",1)
                        .eq("is_deleted",0)
        );

        return new PageUtils(page);
    }

    @Override
    public void insertCatalog(String oneName, String twoName, String threeName, ResourceCatalogEntity catalogEntity) {
        Long parentId;
        if(StringUtils.isNotEmpty(threeName)){
            parentId = this.selectOne(new EntityWrapper<ResourceCatalogEntity>().eq("name",twoName)).getCatalogId();
            catalogEntity.setName(threeName);
            catalogEntity.setParentId(parentId);
        }else if(StringUtils.isNotEmpty(twoName)){
            parentId = this.selectOne(new EntityWrapper<ResourceCatalogEntity>().eq("name",oneName)).getCatalogId();
            catalogEntity.setName(twoName);
            catalogEntity.setParentId(parentId);
        }else if(StringUtils.isNotEmpty(oneName)){
            catalogEntity.setName(oneName);
            catalogEntity.setParentId(0L);
        }
        this.insert(catalogEntity);
    }

}
