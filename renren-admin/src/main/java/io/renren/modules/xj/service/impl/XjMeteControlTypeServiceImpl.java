package io.renren.modules.xj.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.xj.dao.XjMeteControlTypeDao;
import io.renren.modules.xj.entity.XjMeteControlTypeEntity;
import io.renren.modules.xj.service.XjMeteControlTypeService;


@Service("xjMeteControlTypeService")
public class XjMeteControlTypeServiceImpl extends ServiceImpl<XjMeteControlTypeDao, XjMeteControlTypeEntity> implements XjMeteControlTypeService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<XjMeteControlTypeEntity> page = this.selectPage(
                new Query<XjMeteControlTypeEntity>(params).getPage(),
                new EntityWrapper<XjMeteControlTypeEntity>()
        );

        return new PageUtils(page);
    }

}
