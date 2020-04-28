package io.renren.modules.xj.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.xj.entity.XjScheduleJobEntity;

import java.util.Map;

/**
 * 
 *
 * @author wangdehai
 * @email 594340717@qq.com
 * @date 2020-04-28 15:21:11
 */
public interface XjScheduleJobService extends IService<XjScheduleJobEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

