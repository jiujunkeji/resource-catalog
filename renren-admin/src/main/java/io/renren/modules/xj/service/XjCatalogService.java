package io.renren.modules.xj.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.xj.entity.XjCatalogEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author wangdehai
 * @email 594340717@qq.com
 * @date 2020-04-22 17:21:53
 */
public interface XjCatalogService extends IService<XjCatalogEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<XjCatalogEntity> selectUserList(EntityWrapper<XjCatalogEntity> wrapper, SysUserEntity user);
}

