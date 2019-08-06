package io.renren.modules.resource.service.impl;

import io.renren.modules.resource.entity.ResourceCatalogEntity;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.resource.dao.ResourceMeteDataDao;
import io.renren.modules.resource.entity.ResourceMeteDataEntity;
import io.renren.modules.resource.service.ResourceMeteDataService;


@Service("resourceMeteDataService")
public class ResourceMeteDataServiceImpl extends ServiceImpl<ResourceMeteDataDao, ResourceMeteDataEntity> implements ResourceMeteDataService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String catalogId = (String) params.get("catalogId");
        String type = (String)params.get("type");
        EntityWrapper<ResourceMeteDataEntity> wrapper = new EntityWrapper<ResourceMeteDataEntity>();
        wrapper
                .eq("is_deleted",0)
                .eq(StringUtils.isNotEmpty(catalogId),"catalog_id",catalogId);
        if(type != null){
            if("0".equals(type)){
                wrapper.andNew().eq("review_state","0").or().eq("review_state","3");
            }else{
                wrapper.eq("review_state",type);
            }
        }
        Page<ResourceMeteDataEntity> page = this.selectPage(
                new Query<ResourceMeteDataEntity>(params).getPage(),
                wrapper
        );
        return new PageUtils(page);
    }

}
