package io.renren.modules.xj.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.xj.entity.XjChildMonitorEntity;

import java.util.Map;

/**
 * 
 *
 * @author wangdehai
 * @email 594340717@qq.com
 * @date 2020-05-07 15:53:30
 */
public interface XjChildMonitorService extends IService<XjChildMonitorEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

