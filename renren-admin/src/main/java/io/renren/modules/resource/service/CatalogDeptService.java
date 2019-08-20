package io.renren.modules.resource.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.resource.entity.CatalogDeptEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author wangdehai
 * @email 594340717@qq.com
 * @date 2019-08-20 16:13:07
 */
public interface CatalogDeptService extends IService<CatalogDeptEntity> {

    PageUtils queryPage(Map<String, Object> params);

    Boolean selectDeptIsNull(Long deptId, Long catalogId);

    void addBatch(List<Long> idList, Long deptId);

    void deleteBatch(List<Long> idList, Long deptId);
}

