package io.renren.modules.xj.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.xj.entity.XjMeteCategoryEntity;

import java.util.Map;

/**
 * 元数据分类表
 *
 * @author wangdehai
 * @email 594340717@qq.com
 * @date 2020-04-22 15:46:35
 */
public interface XjMeteCategoryService extends IService<XjMeteCategoryEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

