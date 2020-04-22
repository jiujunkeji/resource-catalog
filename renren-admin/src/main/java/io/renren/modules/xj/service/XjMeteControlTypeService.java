package io.renren.modules.xj.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.xj.entity.XjMeteControlTypeEntity;

import java.util.Map;

/**
 * 元数据控件类型表
 *
 * @author wangdehai
 * @email 594340717@qq.com
 * @date 2020-04-22 17:25:12
 */
public interface XjMeteControlTypeService extends IService<XjMeteControlTypeEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

