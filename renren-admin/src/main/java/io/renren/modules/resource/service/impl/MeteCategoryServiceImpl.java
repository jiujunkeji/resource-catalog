package io.renren.modules.resource.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.resource.dao.MeteCategoryDao;
import io.renren.modules.resource.entity.MeteCategoryEntity;
import io.renren.modules.resource.service.MeteCategoryService;


@Service("meteCategoryService")
public class MeteCategoryServiceImpl extends ServiceImpl<MeteCategoryDao, MeteCategoryEntity> implements MeteCategoryService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<MeteCategoryEntity> page = this.selectPage(
                new Query<MeteCategoryEntity>(params).getPage(),
                new EntityWrapper<MeteCategoryEntity>()
        );

        return new PageUtils(page);
    }

}
