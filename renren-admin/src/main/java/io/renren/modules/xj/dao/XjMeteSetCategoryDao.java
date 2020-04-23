package io.renren.modules.xj.dao;

import io.renren.common.utils.PageUtils;
import io.renren.modules.xj.entity.XjMeteCategoryEntity;
import io.renren.modules.xj.entity.XjMeteSetCategoryEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * 元数据集分类表
 * 
 * @author wangdehai
 * @email 594340717@qq.com
 * @date 2020-04-22 15:46:35
 */
public interface XjMeteSetCategoryDao extends BaseMapper<XjMeteSetCategoryEntity> {
    /**
     * 元数据分类搜索(可根据分类编号或者分类名称)
     */
    PageUtils searchFindByMeteSetCategoryNumberOrName(Map<String, Object> params);

    /**
     * 元数据集分类启用
     */
    public List<XjMeteSetCategoryEntity> updateEnabledState(Long[] mete_category_set_ids);

    /**元数据集分类禁用
     */
    public List<XjMeteSetCategoryEntity> updateDisabledState(Long[] mete_category_set_ids);
}
