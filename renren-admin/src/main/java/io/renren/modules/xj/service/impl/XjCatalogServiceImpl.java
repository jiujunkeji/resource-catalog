package io.renren.modules.xj.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
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

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<XjCatalogEntity> page = this.selectPage(
                new Query<XjCatalogEntity>(params).getPage(),
                new EntityWrapper<XjCatalogEntity>()
        );

        return new PageUtils(page);
    }

}
