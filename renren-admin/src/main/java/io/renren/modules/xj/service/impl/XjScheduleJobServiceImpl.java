package io.renren.modules.xj.service.impl;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.xj.dao.XjScheduleJobDao;
import io.renren.modules.xj.entity.XjScheduleJobEntity;
import io.renren.modules.xj.service.XjScheduleJobService;


@Service("xjScheduleJobService")
public class XjScheduleJobServiceImpl extends ServiceImpl<XjScheduleJobDao, XjScheduleJobEntity> implements XjScheduleJobService {


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<XjScheduleJobEntity> page = this.selectPage(
                new Query<XjScheduleJobEntity>(params).getPage(),
                new EntityWrapper<XjScheduleJobEntity>()
                .eq("is_delete",0)
        );

        return new PageUtils(page);
    }

}
