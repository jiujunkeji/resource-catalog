package io.renren.modules.xj.dao;

import io.renren.modules.xj.entity.XjKlogEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * 
 * @author wangdehai
 * @email 594340717@qq.com
 * @date 2020-05-07 15:53:30
 */
public interface XjKlogDao extends BaseMapper<XjKlogEntity> {
    int sum(@Param("createDate")String createDate);
}
