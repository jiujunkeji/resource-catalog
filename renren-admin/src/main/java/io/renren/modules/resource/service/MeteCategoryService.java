package io.renren.modules.resource.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.resource.entity.MeteCategoryEntity;

import java.util.Map;

/**
 * 
 *
 * @author wangdehai
 * @email 594340717@qq.com
 * @date 2019-07-25 16:57:53
 */
public interface MeteCategoryService extends IService<MeteCategoryEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

