package io.renren.modules.xj.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.xj.dao.XjMeteSetVersionDao;
import io.renren.modules.xj.entity.XjMeteSetVersionEntity;
import io.renren.modules.xj.service.XjMeteSetVersionService;


@Service("xjMeteSetVersionService")
public class XjMeteSetVersionServiceImpl extends ServiceImpl<XjMeteSetVersionDao, XjMeteSetVersionEntity> implements XjMeteSetVersionService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<XjMeteSetVersionEntity> page = this.selectPage(
                new Query<XjMeteSetVersionEntity>(params).getPage(),
                new EntityWrapper<XjMeteSetVersionEntity>()
        );

        return new PageUtils(page);
    }

}
