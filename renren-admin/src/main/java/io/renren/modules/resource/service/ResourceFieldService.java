package io.renren.modules.resource.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.resource.entity.ResourceFieldEntity;

import java.util.Map;

/**
 * 
 *
 * @author wangdehai
 * @email 594340717@qq.com
 * @date 2019-08-12 14:54:08
 */
public interface ResourceFieldService extends IService<ResourceFieldEntity> {

    PageUtils queryPage(Map<String, Object> params);

}

