package io.renren.modules.xj.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.xj.dao.XjChildMonitorDao;
import io.renren.modules.xj.entity.XjChildMonitorEntity;
import io.renren.modules.xj.service.XjChildMonitorService;


@Service("xjChildMonitorService")
public class XjChildMonitorServiceImpl extends ServiceImpl<XjChildMonitorDao, XjChildMonitorEntity> implements XjChildMonitorService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<XjChildMonitorEntity> page = this.selectPage(
                new Query<XjChildMonitorEntity>(params).getPage(),
                new EntityWrapper<XjChildMonitorEntity>()
        );

        return new PageUtils(page);
    }

}
