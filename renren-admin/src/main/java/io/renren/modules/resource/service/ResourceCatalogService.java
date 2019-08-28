package io.renren.modules.resource.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.resource.entity.ResourceCatalogEntity;

import java.util.List;
import java.util.Map;

/**
 *
 *
 * @author wangdehai
 * @email 594340717@qq.com
 * @date 2019-07-25 16:57:52
 */
public interface ResourceCatalogService extends IService<ResourceCatalogEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<ResourceCatalogEntity> selectUserList(EntityWrapper<ResourceCatalogEntity> wrapper, Long userId, Long deptId);

    List<ResourceCatalogEntity> selectOgList(EntityWrapper<ResourceCatalogEntity> wrapper, Long organisationId);

    void insertCatalog(ResourceCatalogEntity catalogEntity);

    ResourceCatalogEntity selectCatalog(ResourceCatalogEntity catalogEntity);

    String selectAllCatalogName(Long catalogId);

    List<ResourceCatalogEntity> selectParentCatalogList(List<ResourceCatalogEntity> list, ResourceCatalogEntity currentCatalog);

    List<ResourceCatalogEntity> selectChildCatalogList(List<ResourceCatalogEntity> list, ResourceCatalogEntity currentCatalog);
}

