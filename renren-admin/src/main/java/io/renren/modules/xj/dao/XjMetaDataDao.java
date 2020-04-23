package io.renren.modules.xj.dao;

import com.baomidou.mybatisplus.plugins.Page;
import io.renren.common.utils.PageUtils;
import io.renren.modules.xj.entity.XjMetaDataEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import io.renren.modules.xj.entity.XjMeteCategoryEntity;

import java.util.List;
import java.util.Map;

/**
 * 元数据表
 * 
 * @author wangdehai
 * @email 594340717@qq.com
 * @date 2020-04-22 15:46:35
 */
public interface XjMetaDataDao extends BaseMapper<XjMetaDataEntity> {
    /**
     * 元数据搜索(可根据编号或者中文名称)
     */
    PageUtils searchFindByMeteDataNumberOrName(Map<String, Object> params);

    /**
     * 元数据启用
     */
    public List<XjMetaDataEntity> updateEnabledState(Long[] mete_ids);

    /**元数据禁用
     */
    public List<XjMetaDataEntity> updateDisabledState(Long[] mete_ids);
}
