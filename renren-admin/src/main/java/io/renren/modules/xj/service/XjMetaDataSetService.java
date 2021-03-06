package io.renren.modules.xj.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.xj.entity.XjMetaDataSetEntity;

import java.util.List;
import java.util.Map;

/**
 * 元数据集表
 *
 * @author wangdehai
 * @email 594340717@qq.com
 * @date 2020-04-22 15:46:35
 */
public interface XjMetaDataSetService extends IService<XjMetaDataSetEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 元数据集搜索(可根据编号或者中文名称)
     */
    PageUtils searchFindByMeteDataSetNumberOrName(Map<String, Object> params);




}

