package io.renren.modules.xj.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.xj.dao.XjSafeDao;
import io.renren.modules.xj.entity.XjSafeEntity;
import io.renren.modules.xj.service.XjSafeService;


@Service("xjSafeService")
public class XjSafeServiceImpl extends ServiceImpl<XjSafeDao, XjSafeEntity> implements XjSafeService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<XjSafeEntity> page = this.selectPage(
                new Query<XjSafeEntity>(params).getPage(),
                new EntityWrapper<XjSafeEntity>()
        );

        return new PageUtils(page);
    }

}
