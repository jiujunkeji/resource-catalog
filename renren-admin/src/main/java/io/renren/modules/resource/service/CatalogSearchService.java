package io.renren.modules.resource.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.resource.entity.CatalogSearchEntity;

import java.util.Map;

/**
 * 
 *
 * @author wangdehai
 * @email 594340717@qq.com
 * @date 2019-08-22 11:40:18
 */
public interface CatalogSearchService extends IService<CatalogSearchEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

