package io.renren.modules.xj.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.xj.dao.XjMeteCategoryDao;
import io.renren.modules.xj.entity.XjMeteCategoryEntity;

import java.util.List;
import java.util.Map;

/**
 * 元数据分类表
 *
 * @author wangdehai
 * @email 594340717@qq.com
 * @date 2020-04-22 15:46:35
 */
public interface XjMeteCategoryService extends IService<XjMeteCategoryEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 元数据分类搜索(可根据分类编号或者分类名称)
     */
    PageUtils searchFindByMeteCategoryNumberOrName(Map<String, Object> params);

    /**
     * 元数据分类一键启用
     */
    public List<XjMeteCategoryEntity> updateEnabledState(Long[] mete_category_ids);

    /**
     */
    public List<XjMeteCategoryEntity> updateDisabledState(Long[] mete_category_ids);
}

