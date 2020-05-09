package io.renren.modules.xj.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.xj.dao.XjKlogDao;
import io.renren.modules.xj.entity.XjKlogEntity;
import io.renren.modules.xj.service.XjKlogService;


@Service("xjKlogService")
public class XjKlogServiceImpl extends ServiceImpl<XjKlogDao, XjKlogEntity> implements XjKlogService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<XjKlogEntity> page = this.selectPage(
                new Query<XjKlogEntity>(params).getPage(),
                new EntityWrapper<XjKlogEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public int sum(String createDate) {
        return  baseMapper.sum(createDate);
    }

}
