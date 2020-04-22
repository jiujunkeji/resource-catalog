package io.renren.modules.xj.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.xj.dao.XjMetaDataSetDao;
import io.renren.modules.xj.entity.XjMetaDataSetEntity;
import io.renren.modules.xj.service.XjMetaDataSetService;


@Service("xjMetaDataSetService")
public class XjMetaDataSetServiceImpl extends ServiceImpl<XjMetaDataSetDao, XjMetaDataSetEntity> implements XjMetaDataSetService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<XjMetaDataSetEntity> page = this.selectPage(
                new Query<XjMetaDataSetEntity>(params).getPage(),
                new EntityWrapper<XjMetaDataSetEntity>()
        );

        return new PageUtils(page);
    }

}
