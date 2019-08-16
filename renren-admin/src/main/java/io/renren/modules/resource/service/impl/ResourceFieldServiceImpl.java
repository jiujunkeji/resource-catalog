package io.renren.modules.resource.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.resource.dao.ResourceFieldDao;
import io.renren.modules.resource.entity.ResourceFieldEntity;
import io.renren.modules.resource.service.ResourceFieldService;


@Service("resourceFieldService")
public class ResourceFieldServiceImpl extends ServiceImpl<ResourceFieldDao, ResourceFieldEntity> implements ResourceFieldService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String meteId = (String) params.get("id");
        Page<ResourceFieldEntity> page = this.selectPage(
                new Query<ResourceFieldEntity>(params).getPage(),
                new EntityWrapper<ResourceFieldEntity>()
                       .eq("mete_id",meteId).or().isNull("mete_id")
        );

        return new PageUtils(page);
    }



}
