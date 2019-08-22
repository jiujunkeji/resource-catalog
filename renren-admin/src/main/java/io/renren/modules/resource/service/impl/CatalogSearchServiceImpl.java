package io.renren.modules.resource.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.resource.dao.CatalogSearchDao;
import io.renren.modules.resource.entity.CatalogSearchEntity;
import io.renren.modules.resource.service.CatalogSearchService;


@Service("catalogSearchService")
public class CatalogSearchServiceImpl extends ServiceImpl<CatalogSearchDao, CatalogSearchEntity> implements CatalogSearchService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<CatalogSearchEntity> page = this.selectPage(
                new Query<CatalogSearchEntity>(params).getPage(),
                new EntityWrapper<CatalogSearchEntity>()
        );

        return new PageUtils(page);
    }

}
