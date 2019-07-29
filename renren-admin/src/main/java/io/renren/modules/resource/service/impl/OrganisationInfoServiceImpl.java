package io.renren.modules.resource.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.resource.dao.OrganisationInfoDao;
import io.renren.modules.resource.entity.OrganisationInfoEntity;
import io.renren.modules.resource.service.OrganisationInfoService;


@Service("organisationInfoService")
public class OrganisationInfoServiceImpl extends ServiceImpl<OrganisationInfoDao, OrganisationInfoEntity> implements OrganisationInfoService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String name = (String)params.get("organisationName");
        Page<OrganisationInfoEntity> page = this.selectPage(
                new Query<OrganisationInfoEntity>(params).getPage(),
                new EntityWrapper<OrganisationInfoEntity>()
                        .like(StringUtils.isNotEmpty(name),"organisation_name",name)
        );

        return new PageUtils(page);
    }

}
