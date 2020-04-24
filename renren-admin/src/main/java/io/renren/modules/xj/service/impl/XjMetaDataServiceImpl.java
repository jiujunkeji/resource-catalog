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
    public PageUtils searchFindByMeteDataNumberOrName(Map<String,Object> params) {
        String meteCategoryId = (String) params.get("meteCategoryId");
        String meteNumber = (String) params.get("meteNumber");
        String cnName = (String) params.get("cnName");
        Page<XjMetaDataEntity> page = null;
        if (StringUtils.isNotBlank(meteCategoryId)) {
            if (StringUtils.isNotBlank(meteNumber) && StringUtils.isBlank(cnName)) {
                page = this.selectPage(new Query<XjMetaDataEntity>(params).getPage(), new EntityWrapper<XjMetaDataEntity>().eq("mete_number", meteNumber).and().eq("mete_category_id", Long.valueOf(meteCategoryId)));
                return new PageUtils(page);
            } else if (StringUtils.isNotBlank(cnName) && StringUtils.isBlank(meteNumber)) {
                page = this.selectPage(new Query<XjMetaDataEntity>(params).getPage(), new EntityWrapper<XjMetaDataEntity>().eq("cn_name", cnName).and().eq("mete_category_id", Long.valueOf(meteCategoryId)));
                return new PageUtils(page);
            } else if (StringUtils.isBlank(meteNumber) && StringUtils.isBlank(cnName)) {
                page = this.selectPage(new Query<XjMetaDataEntity>(params).getPage(), new EntityWrapper<XjMetaDataEntity>().eq("mete_category_id", Long.valueOf(meteCategoryId)));
                return new PageUtils(page);
            } else {
                page = this.selectPage(new Query<XjMetaDataEntity>(params).getPage(), new EntityWrapper<XjMetaDataEntity>().eq("mete_number", meteNumber).and().eq("mete_category_id", Long.valueOf(meteCategoryId)));
                return new PageUtils(page);
            }
        } else {
            if (StringUtils.isNotBlank(meteNumber) && StringUtils.isBlank(cnName)) {
                page = this.selectPage(new Query<XjMetaDataEntity>(params).getPage(), new EntityWrapper<XjMetaDataEntity>().eq("mete_number", meteNumber));
                return new PageUtils(page);
            } else if (StringUtils.isNotBlank(cnName) && StringUtils.isBlank(meteNumber)) {
                page = this.selectPage(new Query<XjMetaDataEntity>(params).getPage(), new EntityWrapper<XjMetaDataEntity>().eq("cn_name", cnName));
                return new PageUtils(page);
            } else if (StringUtils.isBlank(meteNumber) && StringUtils.isBlank(cnName)) {
                return queryPage(params);
            } else {
                page = this.selectPage(new Query<XjMetaDataEntity>(params).getPage(), new EntityWrapper<XjMetaDataEntity>().eq("mete_number", meteNumber));
                return new PageUtils(page);
            }
        }

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
