package io.renren.modules.xj.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.xj.dao.XjDataSourceDao;
import io.renren.modules.xj.entity.XjDataSourceEntity;
import io.renren.modules.xj.service.XjDataSourceService;


@Service("xjDataSourceService")
public class XjDataSourceServiceImpl extends ServiceImpl<XjDataSourceDao, XjDataSourceEntity> implements XjDataSourceService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<XjDataSourceEntity> page = this.selectPage(
                new Query<XjDataSourceEntity>(params).getPage(),
                new EntityWrapper<XjDataSourceEntity>()
        );

        return new PageUtils(page);
    }

}
