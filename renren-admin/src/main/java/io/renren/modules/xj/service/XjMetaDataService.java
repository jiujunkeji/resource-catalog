package io.renren.modules.xj.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.xj.dto.MeteCategoryDto;
import io.renren.modules.xj.entity.XjMetaDataEntity;

import java.util.List;
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

    /**
     * 元数据搜索(可根据编号或者中文名称)
     */
    PageUtils searchFindByMeteDataNumberOrName(Map<String,Object> params);

    /**
     * 元数据启用
     */
    List<XjMetaDataEntity> updateEnabledState(Long[] mete_ids);

    /**元数据禁用
     */
    List<XjMetaDataEntity> updateDisabledState(Long[] mete_ids);

    List<MeteCategoryDto> selectMeteByCategory();
}

