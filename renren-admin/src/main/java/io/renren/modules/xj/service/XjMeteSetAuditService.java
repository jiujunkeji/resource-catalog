package io.renren.modules.xj.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.xj.entity.XjMeteSetAuditEntity;

import java.util.Map;

/**
 * 元数据集的审核表
 *
 * @author wangdehai
 * @email 594340717@qq.com
 * @date 2020-04-25 15:42:51
 */
public interface XjMeteSetAuditService extends IService<XjMeteSetAuditEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

