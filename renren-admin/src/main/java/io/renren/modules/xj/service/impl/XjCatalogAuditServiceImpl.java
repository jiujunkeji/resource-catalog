package io.renren.modules.xj.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.xj.dao.XjCatalogAuditDao;
import io.renren.modules.xj.entity.XjCatalogAuditEntity;
import io.renren.modules.xj.service.XjCatalogAuditService;


@Service("xjCatalogAuditService")
public class XjCatalogAuditServiceImpl extends ServiceImpl<XjCatalogAuditDao, XjCatalogAuditEntity> implements XjCatalogAuditService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<XjCatalogAuditEntity> page = this.selectPage(
                new Query<XjCatalogAuditEntity>(params).getPage(),
                new EntityWrapper<XjCatalogAuditEntity>()
        );

        return new PageUtils(page);
    }

}
