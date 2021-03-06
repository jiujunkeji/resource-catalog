package io.renren.modules.xj.service.impl;

import io.renren.modules.xj.entity.XjMeteCategoryEntity;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.xj.dao.XjMeteSetCategoryDao;
import io.renren.modules.xj.entity.XjMeteSetCategoryEntity;
import io.renren.modules.xj.service.XjMeteSetCategoryService;


@Service("xjMeteSetCategoryService")
public class XjMeteSetCategoryServiceImpl extends ServiceImpl<XjMeteSetCategoryDao, XjMeteSetCategoryEntity> implements XjMeteSetCategoryService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<XjMeteSetCategoryEntity> page = this.selectPage(
                new Query<XjMeteSetCategoryEntity>(params).getPage(),
                new EntityWrapper<XjMeteSetCategoryEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils searchFindByMeteSetCategoryNumberOrName(Map<String, Object> params) {
        String metaCategorySetNumber = (String) params.get("metaCategorySetNumber");
        String name = (String) params.get("name");
        Page<XjMeteSetCategoryEntity> page =null;
        if (StringUtils.isNotBlank(metaCategorySetNumber) && StringUtils.isBlank(name)) {
            page = this.selectPage(new Query<XjMeteSetCategoryEntity>(params).getPage(), new EntityWrapper<XjMeteSetCategoryEntity>().eq("meta_category_set_number", metaCategorySetNumber));
            return new PageUtils(page);
        } else if (StringUtils.isNotBlank(name) && StringUtils.isBlank(metaCategorySetNumber)) {
            page = this.selectPage(new Query<XjMeteSetCategoryEntity>(params).getPage(), new EntityWrapper<XjMeteSetCategoryEntity>().like("name", name));
            return new PageUtils(page);
        }else if(StringUtils.isBlank(metaCategorySetNumber) && StringUtils.isBlank(name)){
            return queryPage(params);
        }else{
            page = this.selectPage(new Query<XjMeteSetCategoryEntity>(params).getPage(), new EntityWrapper<XjMeteSetCategoryEntity>().eq("meta_category_set_number", metaCategorySetNumber));
            return new PageUtils(page);
        }
    }

    @Override
    public List<XjMeteSetCategoryEntity> updateEnabledState(Long[] mete_category_set_ids) {
        List<XjMeteSetCategoryEntity> xjMeteSetCategoryEntityList=new ArrayList<>();
        List<Long> list= Arrays.asList(mete_category_set_ids);
        for(Long meteCategorySetId:list){
            XjMeteSetCategoryEntity xjMeteSetCategoryEntity=this.selectOne(new EntityWrapper<XjMeteSetCategoryEntity>().eq("mete_category_set_id",meteCategorySetId));
            xjMeteSetCategoryEntityList.add(xjMeteSetCategoryEntity);
        }
        return xjMeteSetCategoryEntityList;
    }

    @Override
    public List<XjMeteSetCategoryEntity> updateDisabledState(Long[] mete_category_set_ids) {
        List<XjMeteSetCategoryEntity> xjMeteSetCategoryEntityList=new ArrayList<>();
        List<Long> list= Arrays.asList(mete_category_set_ids);
        for(Long meteCategorySetId:list){
            XjMeteSetCategoryEntity xjMeteSetCategoryEntity=this.selectOne(new EntityWrapper<XjMeteSetCategoryEntity>().eq("mete_category_set_id",meteCategorySetId));
            xjMeteSetCategoryEntityList.add(xjMeteSetCategoryEntity);
        }
        return xjMeteSetCategoryEntityList;
    }


}
