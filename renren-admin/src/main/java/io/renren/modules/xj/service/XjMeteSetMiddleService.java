package io.renren.modules.xj.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.xj.entity.XjMeteSetMiddleEntity;

import java.util.Map;

/**
 * 元数据与元数据集的中间表
 *
 * @author wangdehai
 * @email 594340717@qq.com
 * @date 2020-04-24 10:54:31
 */
public interface XjMeteSetMiddleService extends IService<XjMeteSetMiddleEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

