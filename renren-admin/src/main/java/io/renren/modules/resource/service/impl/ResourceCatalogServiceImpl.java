package io.renren.modules.resource.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
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

}
