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
    public void insertCatalog(ResourceCatalogEntity catalogEntity) {
        Long parentId;
        if(StringUtils.isNotEmpty(catalogEntity.getThreeName())){
            parentId = this.selectOne(new EntityWrapper<ResourceCatalogEntity>().eq("name",catalogEntity.getTwoName())).getCatalogId();
            catalogEntity.setName(catalogEntity.getThreeName());
            catalogEntity.setParentId(parentId);
        }else if(StringUtils.isNotEmpty(catalogEntity.getTwoName())){
            parentId = this.selectOne(new EntityWrapper<ResourceCatalogEntity>().eq("name",catalogEntity.getOneName())).getCatalogId();
            catalogEntity.setName(catalogEntity.getTwoName());
            catalogEntity.setParentId(parentId);
        }else if(StringUtils.isNotEmpty(catalogEntity.getOneName())){
            catalogEntity.setName(catalogEntity.getOneName());
            if(catalogEntity.getType() == 0){
                catalogEntity.setParentId(1L);
            }else{
                catalogEntity.setParentId(2L);
            }
        }
        this.insert(catalogEntity);
    }

    @Override
    public ResourceCatalogEntity selectCatalog(ResourceCatalogEntity catalogEntity){
        String oneName = null;
        String twoName = null;
        String threeName = null;
        Long a = catalogEntity.getParentId();
        if(a != 1L && a != 2L){
            Long b = this.selectById(a).getParentId();
            if(b != 1L && b != 1L){
                oneName  = this.selectById(b).getName();
                twoName  = this.selectById(a).getName();
                threeName  = catalogEntity.getName();
            }else{
                oneName = this.selectById(a).getName();
                twoName = catalogEntity.getName();
            }
        }else{
            oneName = catalogEntity.getName();
        }
        catalogEntity.setOneName(oneName);
        catalogEntity.setTwoName(twoName);
        catalogEntity.setThreeName(threeName);
        return catalogEntity;
    }
}
