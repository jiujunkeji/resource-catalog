package io.renren.modules.xj.service.impl;

import io.renren.modules.resource.entity.MeteCategoryEntity;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.xj.dao.XjMeteCategoryDao;
import io.renren.modules.xj.entity.XjMeteCategoryEntity;
import io.renren.modules.xj.service.XjMeteCategoryService;


@Service("xjMeteCategoryService")
public class XjMeteCategoryServiceImpl extends ServiceImpl<XjMeteCategoryDao, XjMeteCategoryEntity> implements XjMeteCategoryService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<XjMeteCategoryEntity> page = this.selectPage(
                new Query<XjMeteCategoryEntity>(params).getPage(),
                new EntityWrapper<XjMeteCategoryEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils searchFindByMeteCategoryNumberOrName(Map<String, Object> params) {
        String metaCategoryNumber = (String) params.get("metaCategoryNumber");
        String name = (String) params.get("name");
        Page<XjMeteCategoryEntity> page =null;
        if (StringUtils.isNotBlank(metaCategoryNumber) && StringUtils.isBlank(name)) {
            page = this.selectPage(new Query<XjMeteCategoryEntity>(params).getPage(), new EntityWrapper<XjMeteCategoryEntity>().eq("meta_category_number", metaCategoryNumber));
            return new PageUtils(page);
        } else if (StringUtils.isNotBlank(name) && StringUtils.isBlank(metaCategoryNumber)) {
            page = this.selectPage(new Query<XjMeteCategoryEntity>(params).getPage(), new EntityWrapper<XjMeteCategoryEntity>().eq("name", name));
            return new PageUtils(page);
        }else if(StringUtils.isBlank(name) && StringUtils.isBlank(metaCategoryNumber)){
            return  queryPage(params);
        }
        return new PageUtils(page);
    }



    @Override
    public List<XjMeteCategoryEntity> updateEnabledState(Long[] mete_category_ids) {
        List<XjMeteCategoryEntity> xjMeteCategoryEntityList=new ArrayList<>();
        List<Long> list=Arrays.asList(mete_category_ids);
        for(Long meteCategoryId: list){
            XjMeteCategoryEntity xjMeteCategoryEntity=this.selectOne(new EntityWrapper<XjMeteCategoryEntity>().eq("mete_category_id",meteCategoryId));
            xjMeteCategoryEntityList.add(xjMeteCategoryEntity);
        }
        return xjMeteCategoryEntityList;
    }

    @Override
    public List<XjMeteCategoryEntity> updateDisabledState(Long[] mete_category_ids) {
        List<XjMeteCategoryEntity> xjMeteCategoryEntityList=new ArrayList<>();
        List<Long> list=Arrays.asList(mete_category_ids);
        for(Long meteCategoryId:list){
            XjMeteCategoryEntity xjMeteCategoryEntity=this.selectOne(new EntityWrapper<XjMeteCategoryEntity>().eq("mete_category_id",meteCategoryId));
            xjMeteCategoryEntityList.add(xjMeteCategoryEntity);
        }
        return xjMeteCategoryEntityList;

    }

}
