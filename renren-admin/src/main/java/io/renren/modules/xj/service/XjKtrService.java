package io.renren.modules.xj.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.xj.entity.XjDataSourceEntity;
import io.renren.modules.xj.entity.XjKtrEntity;
import org.pentaho.di.core.exception.KettleException;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author wangdehai
 * @email 594340717@qq.com
 * @date 2020-04-23 14:31:38
 */
public interface XjKtrService extends IService<XjKtrEntity> {

    PageUtils queryPage(Map<String, Object> params);
    List list2(Map<String, Object> params);

    void kettleJob(XjKtrEntity xjKtr, XjDataSourceEntity ds) throws Exception;
}

