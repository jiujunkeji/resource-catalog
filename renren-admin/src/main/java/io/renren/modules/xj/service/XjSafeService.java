package io.renren.modules.xj.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.xj.entity.XjSafeEntity;

import java.util.Map;

/**
 * 
 *
 * @author wangdehai
 * @email 594340717@qq.com
 * @date 2020-04-22 17:21:53
 */
public interface XjSafeService extends IService<XjSafeEntity> {

    PageUtils queryPage(Map<String, Object> params);

    XjSafeEntity setDefaultSafe(Long catalogId);
}

