package io.renren.modules.xj.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.xj.entity.XjCatalogLinkDataEntity;

import java.util.Map;

/**
 * 
 *
 * @author wangdehai
 * @email 594340717@qq.com
 * @date 2020-04-26 15:14:46
 */
public interface XjCatalogLinkDataService extends IService<XjCatalogLinkDataEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

