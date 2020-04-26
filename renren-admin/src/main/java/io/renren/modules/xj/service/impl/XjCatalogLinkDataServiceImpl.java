package io.renren.modules.xj.service.impl;

import org.apache.commons.lang.StringUtils;
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
        String catalogId = (String)params.get("catalogId");
        String catalogName = (String)params.get("catalogName");
        Page<XjCatalogLinkDataEntity> page = this.selectPage(
                new Query<XjCatalogLinkDataEntity>(params).getPage(),
                new EntityWrapper<XjCatalogLinkDataEntity>()
                    .eq(StringUtils.isNotBlank(catalogId),"catalog_id",catalogId)
                    .like(StringUtils.isNotBlank(catalogName),"catalog_name",catalogName)
        );

        return new PageUtils(page);
    }

}
