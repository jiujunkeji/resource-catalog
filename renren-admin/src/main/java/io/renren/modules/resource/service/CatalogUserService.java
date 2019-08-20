package io.renren.modules.resource.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.resource.entity.CatalogUserEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author wangdehai
 * @email 594340717@qq.com
 * @date 2019-08-20 16:11:33
 */
public interface CatalogUserService extends IService<CatalogUserEntity> {

    PageUtils queryPage(Map<String, Object> params);

    Boolean selectUserIsNull(Long userId, Long catalogId);

    void addBatch(List<Long> idList, Long userId);

    void deleteBatch(List<Long> idList, Long userId);
}

