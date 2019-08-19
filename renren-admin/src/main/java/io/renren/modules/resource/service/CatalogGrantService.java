package io.renren.modules.resource.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.resource.entity.CatalogGrantEntity;

import java.util.Map;

/**
 * 
 *
 * @author wangdehai
 * @email 594340717@qq.com
 * @date 2019-08-19 15:13:13
 */
public interface CatalogGrantService extends IService<CatalogGrantEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

