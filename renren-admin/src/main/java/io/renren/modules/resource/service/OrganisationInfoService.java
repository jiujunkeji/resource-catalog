package io.renren.modules.resource.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.resource.entity.OrganisationInfoEntity;

import java.util.Map;

/**
 * 
 *
 * @author wangdehai
 * @email 594340717@qq.com
 * @date 2019-07-25 16:57:52
 */
public interface OrganisationInfoService extends IService<OrganisationInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

