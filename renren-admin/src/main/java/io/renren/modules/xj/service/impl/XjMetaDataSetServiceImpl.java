package io.renren.modules.xj.service.impl;


import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.xj.dao.XjMetaDataSetDao;
import io.renren.modules.xj.entity.XjMetaDataSetEntity;
import io.renren.modules.xj.service.XjMetaDataSetService;


@Service("xjMetaDataSetService")
public class XjMetaDataSetServiceImpl extends ServiceImpl<XjMetaDataSetDao, XjMetaDataSetEntity> implements XjMetaDataSetService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<XjMetaDataSetEntity> page = this.selectPage(
                new Query<XjMetaDataSetEntity>(params).getPage(),
                new EntityWrapper<XjMetaDataSetEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils searchFindByMeteDataSetNumberOrName(Map<String, Object> params) {
        String meteCategorySetId = (String) params.get("meteCategorySetId");
        String meteSetNumber = (String) params.get("meteSetNumber");
        String cnName = (String) params.get("cnName");
        Page<XjMetaDataSetEntity> page =null;
        if(StringUtils.isNotBlank(meteCategorySetId)){
            if (StringUtils.isNotBlank(meteSetNumber) && StringUtils.isBlank(cnName)) {
                page = this.selectPage(new Query<XjMetaDataSetEntity>(params).getPage(), new EntityWrapper<XjMetaDataSetEntity>().eq("mete_set_number", meteSetNumber).and().eq("mete_category_set_id",Long.valueOf(meteCategorySetId)));
                return new PageUtils(page);
            } else if (StringUtils.isNotBlank(cnName) && StringUtils.isBlank(cnName)) {
                page = this.selectPage(new Query<XjMetaDataSetEntity>(params).getPage(), new EntityWrapper<XjMetaDataSetEntity>().eq("cn_name", cnName).and().eq("mete_category_set_id",Long.valueOf(meteCategorySetId)));
                return new PageUtils(page);
            }else if(StringUtils.isBlank(cnName) && StringUtils.isBlank(cnName)){
                page = this.selectPage(new Query<XjMetaDataSetEntity>(params).getPage(), new EntityWrapper<XjMetaDataSetEntity>().eq("mete_category_set_id",Long.valueOf(meteCategorySetId)));
                return new PageUtils(page);
            }else{
                page = this.selectPage(new Query<XjMetaDataSetEntity>(params).getPage(), new EntityWrapper<XjMetaDataSetEntity>().eq("mete_set_number", meteSetNumber).and().eq("mete_category_set_id",Long.valueOf(meteCategorySetId)));
                return new PageUtils(page);
            }
        }else{
            if (StringUtils.isNotBlank(meteSetNumber) && StringUtils.isBlank(cnName)) {
                page = this.selectPage(new Query<XjMetaDataSetEntity>(params).getPage(), new EntityWrapper<XjMetaDataSetEntity>().eq("mete_set_number", meteSetNumber));
                return new PageUtils(page);
            } else if (StringUtils.isNotBlank(cnName) && StringUtils.isBlank(cnName)) {
                page = this.selectPage(new Query<XjMetaDataSetEntity>(params).getPage(), new EntityWrapper<XjMetaDataSetEntity>().eq("cn_name", cnName));
                return new PageUtils(page);
            }else if(StringUtils.isBlank(cnName) && StringUtils.isBlank(cnName)){
               return queryPage(params);
            }else{
                page = this.selectPage(new Query<XjMetaDataSetEntity>(params).getPage(), new EntityWrapper<XjMetaDataSetEntity>().eq("mete_set_number", meteSetNumber));
                return new PageUtils(page);
            }
        }

    }




}
