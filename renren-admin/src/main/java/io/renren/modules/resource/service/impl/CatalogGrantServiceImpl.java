package io.renren.modules.resource.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.resource.dao.CatalogGrantDao;
import io.renren.modules.resource.entity.CatalogGrantEntity;
import io.renren.modules.resource.service.CatalogGrantService;


@Service("catalogGrantService")
public class CatalogGrantServiceImpl extends ServiceImpl<CatalogGrantDao, CatalogGrantEntity> implements CatalogGrantService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<CatalogGrantEntity> page = this.selectPage(
                new Query<CatalogGrantEntity>(params).getPage(),
                new EntityWrapper<CatalogGrantEntity>()
        );

        return new PageUtils(page);
    }

}
