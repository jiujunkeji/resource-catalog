package io.renren.modules.resource.service.impl;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.resource.dao.CatalogDeptDao;
import io.renren.modules.resource.entity.CatalogDeptEntity;
import io.renren.modules.resource.service.CatalogDeptService;


@Service("catalogDeptService")
public class CatalogDeptServiceImpl extends ServiceImpl<CatalogDeptDao, CatalogDeptEntity> implements CatalogDeptService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<CatalogDeptEntity> page = this.selectPage(
                new Query<CatalogDeptEntity>(params).getPage(),
                new EntityWrapper<CatalogDeptEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public Boolean selectDeptIsNull(Long deptId, Long catalogId) {
        CatalogDeptEntity a = this.selectOne(new EntityWrapper<CatalogDeptEntity>().eq("dept_id",deptId).eq("catalog_id",catalogId));
        if(a != null){
            return false;
        }else{
            return true;
        }
    }

    @Override
    public void addBatch(List<Long> idList, Long deptId) {
        List<CatalogDeptEntity> list = new ArrayList<CatalogDeptEntity>();
        for(Long id : idList){
            list.add(new CatalogDeptEntity(id,deptId));
        }
        this.insertBatch(list);
    }

    @Override
    public void deleteBatch(List<Long> idList, Long deptId) {
        for(Long id : idList){
            this.delete(new EntityWrapper<CatalogDeptEntity>().eq("dept_id",deptId).eq("catalog_id",id));
        }
    }
}
