package io.renren.modules.xj.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.xj.entity.XjMetaDataEntity;

import java.util.Map;

/**
 * 元数据表
 *
 * @author wangdehai
 * @email 594340717@qq.com
 * @date 2020-04-22 15:46:35
 */
public interface XjMetaDataService extends IService<XjMetaDataEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

