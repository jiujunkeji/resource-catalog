package io.renren.modules.xj.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.xj.dao.XjCatalogLinkDataDao;
import io.renren.modules.xj.entity.XjCatalogLinkDataEntity;
import io.renren.modules.xj.service.XjCatalogLinkDataService;


@Service("xjCatalogLinkDataService")
public class XjCatalogLinkDataServiceImpl extends ServiceImpl<XjCatalogLinkDataDao, XjCatalogLinkDataEntity> implements XjCatalogLinkDataService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<XjCatalogLinkDataEntity> page = this.selectPage(
                new Query<XjCatalogLinkDataEntity>(params).getPage(),
                new EntityWrapper<XjCatalogLinkDataEntity>()
        );

        return new PageUtils(page);
    }

}
