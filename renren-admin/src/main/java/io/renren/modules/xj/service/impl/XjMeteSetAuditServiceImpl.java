package io.renren.modules.xj.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.xj.dao.XjMeteSetAuditDao;
import io.renren.modules.xj.entity.XjMeteSetAuditEntity;
import io.renren.modules.xj.service.XjMeteSetAuditService;


@Service("xjMeteSetAuditService")
public class XjMeteSetAuditServiceImpl extends ServiceImpl<XjMeteSetAuditDao, XjMeteSetAuditEntity> implements XjMeteSetAuditService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Long meteSetId= (Long) params.get("meteSetId");
        if(meteSetId!=null){
            Page<XjMeteSetAuditEntity> page = this.selectPage(
                    new Query<XjMeteSetAuditEntity>(params).getPage(),
                    new EntityWrapper<XjMeteSetAuditEntity>().eq("mete_set_id",meteSetId)
            );
            return new PageUtils(page);
        }else{
            Page<XjMeteSetAuditEntity> page = this.selectPage(
                    new Query<XjMeteSetAuditEntity>(params).getPage(),
                    new EntityWrapper<XjMeteSetAuditEntity>()

            );
            return new PageUtils(page);
        }

    }

}
