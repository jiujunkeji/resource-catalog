package io.renren.modules.resource.service.impl;

import io.renren.modules.resource.entity.CatalogDeptEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.resource.dao.CatalogUserDao;
import io.renren.modules.resource.entity.CatalogUserEntity;
import io.renren.modules.resource.service.CatalogUserService;


@Service("catalogUserService")
public class CatalogUserServiceImpl extends ServiceImpl<CatalogUserDao, CatalogUserEntity> implements CatalogUserService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<CatalogUserEntity> page = this.selectPage(
                new Query<CatalogUserEntity>(params).getPage(),
                new EntityWrapper<CatalogUserEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public Boolean selectUserIsNull(Long userId, Long catalogId) {
        CatalogUserEntity a = this.selectOne(new EntityWrapper<CatalogUserEntity>().eq("user_id",userId).eq("catalog_id",catalogId));
        if(a != null){
            return false;
        }else{
            return true;
        }
    }

    @Override
    public void addBatch(List<Long> idList, Long userId) {
        List<CatalogUserEntity> list = new ArrayList<CatalogUserEntity>();
        for(Long id : idList){
            list.add(new CatalogUserEntity(id,userId));
        }
        this.insertBatch(list);
    }

    @Override
    public void deleteBatch(List<Long> idList, Long userId) {
        for(Long id : idList){
            this.delete(new EntityWrapper<CatalogUserEntity>().eq("user_id",userId).eq("catalog_id",id));
        }
    }
}
