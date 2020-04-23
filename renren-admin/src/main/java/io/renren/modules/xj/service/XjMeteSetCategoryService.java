package io.renren.modules.xj.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.xj.entity.XjMeteSetCategoryEntity;

import java.util.List;
import java.util.Map;

/**
 * 元数据集分类表
 *
 * @author wangdehai
 * @email 594340717@qq.com
 * @date 2020-04-22 15:46:35
 */
public interface XjMeteSetCategoryService extends IService<XjMeteSetCategoryEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 元数据分类搜索(可根据分类编号或者分类名称)
     */
    PageUtils searchFindByMeteSetCategoryNumberOrName(Map<String, Object> params);

    /**
     * 元数据分类启用
     */
    public List<XjMeteSetCategoryEntity> updateEnabledState(Long[] mete_category_set_ids);

    /**元数据分类禁用
     */
    public List<XjMeteSetCategoryEntity> updateDisabledState(Long[] mete_category_set_ids);
}

