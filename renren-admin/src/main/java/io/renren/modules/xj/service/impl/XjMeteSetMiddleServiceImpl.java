package io.renren.modules.xj.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.xj.dao.XjMeteSetMiddleDao;
import io.renren.modules.xj.entity.XjMeteSetMiddleEntity;
import io.renren.modules.xj.service.XjMeteSetMiddleService;


@Service("xjMeteSetMiddleService")
public class XjMeteSetMiddleServiceImpl extends ServiceImpl<XjMeteSetMiddleDao, XjMeteSetMiddleEntity> implements XjMeteSetMiddleService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<XjMeteSetMiddleEntity> page = this.selectPage(
                new Query<XjMeteSetMiddleEntity>(params).getPage(),
                new EntityWrapper<XjMeteSetMiddleEntity>()
        );

        return new PageUtils(page);
    }

}
