package io.renren.modules.xj.dao;

import io.renren.common.utils.PageUtils;
import io.renren.modules.xj.entity.XjMetaDataEntity;
import io.renren.modules.xj.entity.XjMetaDataSetEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * 元数据集表
 * 
 * @author wangdehai
 * @email 594340717@qq.com
 * @date 2020-04-22 15:46:35
 */
public interface XjMetaDataSetDao extends BaseMapper<XjMetaDataSetEntity> {
    /**
     * 元数据集搜索(可根据编号或者中文名称)
     */
    PageUtils searchFindByMeteDataSetNumberOrName(Map<String, Object> params);


}
