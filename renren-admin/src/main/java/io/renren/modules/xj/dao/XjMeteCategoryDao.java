package io.renren.modules.xj.dao;

import io.renren.common.utils.PageUtils;
import io.renren.modules.resource.entity.MeteCategoryEntity;
import io.renren.modules.xj.entity.XjMeteCategoryEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * 元数据分类表
 * 
 * @author wangdehai
 * @email 594340717@qq.com
 * @date 2020-04-22 15:46:35
 */
public interface XjMeteCategoryDao extends BaseMapper<XjMeteCategoryEntity> {
    /**
     * 元数据分类搜索(可根据分类编号或者分类名称)
     */
    PageUtils searchFindByMeteCategoryNumberOrName(Map<String, Object> params);

    /**
     * 元数据分类启用
     */
    public List<XjMeteCategoryEntity> updateEnabledState(Long[] mete_category_ids);

    /**元数据分类禁用
     */
    public List<XjMeteCategoryEntity> updateDisabledState(Long[] mete_category_ids);
}
