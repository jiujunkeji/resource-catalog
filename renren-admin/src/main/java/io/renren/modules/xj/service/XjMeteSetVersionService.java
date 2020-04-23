package io.renren.modules.xj.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.xj.entity.XjMeteSetVersionEntity;

import java.util.Map;

/**
 * 元数据集变更版本表
 *
 * @author wangdehai
 * @email 594340717@qq.com
 * @date 2020-04-23 17:29:47
 */
public interface XjMeteSetVersionService extends IService<XjMeteSetVersionEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

