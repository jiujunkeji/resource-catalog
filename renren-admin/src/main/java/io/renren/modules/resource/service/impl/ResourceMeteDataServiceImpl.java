package io.renren.modules.resource.service.impl;

import io.renren.modules.resource.entity.ResourceCatalogEntity;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.resource.dao.ResourceMeteDataDao;
import io.renren.modules.resource.entity.ResourceMeteDataEntity;
import io.renren.modules.resource.service.ResourceMeteDataService;


@Service("resourceMeteDataService")
public class ResourceMeteDataServiceImpl extends ServiceImpl<ResourceMeteDataDao, ResourceMeteDataEntity> implements ResourceMeteDataService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String resourceTitle = (String) params.get("resourceTitle");
        Page<ResourceMeteDataEntity> page = this.selectPage(
                new Query<ResourceMeteDataEntity>(params).getPage(),
                new EntityWrapper<ResourceMeteDataEntity>()
                        .like(StringUtils.isNotEmpty(resourceTitle),"resource_title",resourceTitle)
                        .eq("is_deleted",0)
        );
        return new PageUtils(page);
    }

}
