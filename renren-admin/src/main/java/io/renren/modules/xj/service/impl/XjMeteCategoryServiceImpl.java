package io.renren.modules.xj.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.xj.dao.XjMeteCategoryDao;
import io.renren.modules.xj.entity.XjMeteCategoryEntity;
import io.renren.modules.xj.service.XjMeteCategoryService;


@Service("xjMeteCategoryService")
public class XjMeteCategoryServiceImpl extends ServiceImpl<XjMeteCategoryDao, XjMeteCategoryEntity> implements XjMeteCategoryService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<XjMeteCategoryEntity> page = this.selectPage(
                new Query<XjMeteCategoryEntity>(params).getPage(),
                new EntityWrapper<XjMeteCategoryEntity>()
        );

        return new PageUtils(page);
    }

}
