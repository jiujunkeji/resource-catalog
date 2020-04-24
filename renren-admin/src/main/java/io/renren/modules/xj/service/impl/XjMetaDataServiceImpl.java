package io.renren.modules.xj.service.impl;

import io.renren.modules.xj.entity.XjMeteCategoryEntity;
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

import io.renren.modules.xj.dao.XjMetaDataDao;
import io.renren.modules.xj.entity.XjMetaDataEntity;
import io.renren.modules.xj.service.XjMetaDataService;


@Service("xjMetaDataService")
public class XjMetaDataServiceImpl extends ServiceImpl<XjMetaDataDao, XjMetaDataEntity> implements XjMetaDataService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<XjMetaDataEntity> page = this.selectPage(
                new Query<XjMetaDataEntity>(params).getPage(),
                new EntityWrapper<XjMetaDataEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils searchFindByMeteDataNumberOrName(Map<String, Object> params) {
        String metaNumber = (String) params.get("meteNumber");
        String name = (String) params.get("cnName");
        String meteCategoryId=((String) params.get("meteCategoryId"));
        Page<XjMetaDataEntity> page =null;
        if(StringUtils.isNotBlank(meteCategoryId)){
            if (StringUtils.isNotBlank(metaNumber) && StringUtils.isBlank(name)) {
                page = this.selectPage(new Query<XjMetaDataEntity>(params).getPage(), new EntityWrapper<XjMetaDataEntity>().eq("mete_number", metaNumber).and().eq("mete_category_id",Long.valueOf(meteCategoryId)));
                return new PageUtils(page);
            } else if (StringUtils.isNotBlank(name) && StringUtils.isBlank(metaNumber)) {
                page = this.selectPage(new Query<XjMetaDataEntity>(params).getPage(), new EntityWrapper<XjMetaDataEntity>().eq("cn_name", name).and().eq("mete_category_id",Long.valueOf(meteCategoryId)));
                return new PageUtils(page);
            }else if(StringUtils.isBlank(metaNumber)&&StringUtils.isBlank(name)){
                page = this.selectPage(new Query<XjMetaDataEntity>(params).getPage(), new EntityWrapper<XjMetaDataEntity>().eq("mete_category_id",Long.valueOf(meteCategoryId)));
                return new PageUtils(page);
            }
        }else {
            if (StringUtils.isNotBlank(metaNumber) && StringUtils.isBlank(name)) {
                page = this.selectPage(new Query<XjMetaDataEntity>(params).getPage(), new EntityWrapper<XjMetaDataEntity>().eq("mete_number", metaNumber));
                return new PageUtils(page);
            } else if (StringUtils.isNotBlank(name) && StringUtils.isBlank(metaNumber)) {
                page = this.selectPage(new Query<XjMetaDataEntity>(params).getPage(), new EntityWrapper<XjMetaDataEntity>().eq("cn_name", name));
                return new PageUtils(page);
            }else if(StringUtils.isBlank(metaNumber)&&StringUtils.isBlank(name)){
                return queryPage(params);
            }
        }

        return new PageUtils(page);
    }

    @Override
    public List<XjMetaDataEntity> updateEnabledState(Long[] mete_ids) {
        List<XjMetaDataEntity> xjMetaDataEntities=new ArrayList<>();
        List<Long> list= Arrays.asList(mete_ids);
        for(Long meteId: list){
            XjMetaDataEntity xjMetaDataEntity=this.selectOne(new EntityWrapper<XjMetaDataEntity>().eq("mete_id",meteId));
            xjMetaDataEntities.add(xjMetaDataEntity);
        }
        return xjMetaDataEntities;
    }

    @Override
    public List<XjMetaDataEntity> updateDisabledState(Long[] mete_ids) {
        List<XjMetaDataEntity> xjMetaDataEntities=new ArrayList<>();
        List<Long> list= Arrays.asList(mete_ids);
        for(Long meteId: list){
            XjMetaDataEntity xjMetaDataEntity=this.selectOne(new EntityWrapper<XjMetaDataEntity>().eq("mete_id",meteId));
            xjMetaDataEntities.add(xjMetaDataEntity);
        }
        return xjMetaDataEntities;
    }


}
