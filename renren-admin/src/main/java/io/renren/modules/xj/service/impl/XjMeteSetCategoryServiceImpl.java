package io.renren.modules.xj.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.xj.dao.XjMeteSetCategoryDao;
import io.renren.modules.xj.entity.XjMeteSetCategoryEntity;
import io.renren.modules.xj.service.XjMeteSetCategoryService;


@Service("xjMeteSetCategoryService")
public class XjMeteSetCategoryServiceImpl extends ServiceImpl<XjMeteSetCategoryDao, XjMeteSetCategoryEntity> implements XjMeteSetCategoryService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<XjMeteSetCategoryEntity> page = this.selectPage(
                new Query<XjMeteSetCategoryEntity>(params).getPage(),
                new EntityWrapper<XjMeteSetCategoryEntity>()
        );

        return new PageUtils(page);
    }

}
