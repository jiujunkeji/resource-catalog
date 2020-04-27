package io.renren.modules.xj.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.xj.entity.XjFtpEntity;

import java.io.FileNotFoundException;
import java.util.Map;

/**
 * 
 *
 * @author wangdehai
 * @email 594340717@qq.com
 * @date 2020-04-27 11:20:21
 */
public interface XjFtpService extends IService<XjFtpEntity> {

    PageUtils queryPage(Map<String, Object> params);

   void uploadFile(XjFtpEntity fe) throws FileNotFoundException;

}

