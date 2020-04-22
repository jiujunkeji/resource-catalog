package io.renren.modules.xj.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.xj.entity.XjMetaDataEntity;

import java.util.Map;

/**
 * 
 *
 * @author fanwei
 * @email 3275869736@qq.com
 * @date 2020-04-22 13:31:15
 */
public interface XjMetaDataService extends IService<XjMetaDataEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

