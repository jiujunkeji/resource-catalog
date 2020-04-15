package io.renren.modules.resource.api;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import io.renren.common.utils.R;
import io.renren.modules.resource.dto.ResourceCatalogDto;
import io.renren.modules.resource.dto.ResourceMeteDataDto;
import io.renren.modules.resource.entity.*;
import io.renren.modules.resource.service.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Auther: wdh
 * @Date: 2019-08-28 15:52
 * @Description:
 */
@RestController
@RequestMapping("api/resource")
public class MeteDataApi {

    @Autowired
    private ResourceMeteDataService resourceMeteDataService;
    @Autowired
    private CatalogSearchService catalogSearchService;
    @Autowired
    private OrganisationInfoService organisationInfoService;
    @Autowired
    private ResourceCatalogService resourceCatalogService;
    @Autowired
    private CatalogDeptService catalogDeptService;
    /**
     *检索目录列表
     */
    @ResponseBody
    @RequestMapping("/selectCatalogList")
    public R selectCatalogList(@RequestParam Map<String,Object> params){
        System.out.println("access_key:" + params.get("access_key"));
        System.out.println("access_secret:" + params.get("access_secret"));
        System.out.println("name:" + params.get("name"));
        String access_key = (String) params.get("access_key");
        String access_secret = (String) params.get("access_secret");
        String name = (String) params.get("name");
        if(StringUtils.isBlank(access_key) || StringUtils.isBlank(access_secret)){
            return R.error(501,"access_key/access_secret can't be null");
        }
        OrganisationInfoEntity og = organisationInfoService.selectOne(new EntityWrapper<OrganisationInfoEntity>().eq("access_key",access_key).eq("access_secret",access_secret));
        if(og == null){
            return R.error(502,"Access Info error");
        }
        EntityWrapper<ResourceCatalogEntity> wrapper = new EntityWrapper<ResourceCatalogEntity>();
        wrapper
                .like(StringUtils.isNotEmpty(name), "name", name)
                .eq("is_deleted",0);
        List<ResourceCatalogEntity> list = resourceCatalogService.selectOgList(wrapper,og.getOrganisationId());
        List<ResourceCatalogDto> data = new ArrayList<ResourceCatalogDto>();
        if(list != null && list.size() > 0){
            for(ResourceCatalogEntity catalog : list){
                ResourceCatalogDto dto = new ResourceCatalogDto();
                dto.setCatalogId(catalog.getCatalogId());
                dto.setName(catalog.getName());
                dto.setParentId(catalog.getParentId());
                ResourceCatalogEntity parentResourceCatalogEntity = resourceCatalogService.selectById(catalog.getParentId());
                if(parentResourceCatalogEntity != null){
                    dto.setParentName(parentResourceCatalogEntity.getName());
                }else{
                    dto.setParentName("");
                }
                dto.setRemark(catalog.getRemark());
                if(catalog.getType() != null && catalog.getType() == 0){
                    dto.setType("资源类型");
                }else{
                    dto.setType("服务类型");
                }
                data.add(dto);
            }
        }
        CatalogSearchEntity catalogSearchEntity = new CatalogSearchEntity();
        catalogSearchEntity.setSearchDate(new Date());
        catalogSearchService.insert(catalogSearchEntity);
        return R.ok().put("data",data);
    }

    /**
     *检索列表
     */
    @ResponseBody
    @RequestMapping("/selectMeteData")
    public R list2(@RequestParam Map<String,Object> params){
        String access_key = (String) params.get("access_key");
        String access_secret = (String) params.get("access_secret");
        String catalog_id = (String) params.get("catalog_id");
        if(StringUtils.isBlank(access_key) || StringUtils.isBlank(access_secret)){
            return R.error(501,"access_key/access_secret can't be null");
        }
        if(StringUtils.isBlank(catalog_id)){
            return R.error(501,"catalog_id can't be null");
        }
        OrganisationInfoEntity og = organisationInfoService.selectOne(new EntityWrapper<OrganisationInfoEntity>().eq("access_key",access_key).eq("access_secret",access_secret));
        if(og == null){
            return R.error(502,"Access Info error");
        }
        CatalogDeptEntity catalogDeptEntity = catalogDeptService.selectOne(new EntityWrapper<CatalogDeptEntity>().eq("catalog_id",catalog_id).eq("organisation_id",og.getOrganisationId()));
        if(catalogDeptEntity == null){
            return R.error(503,"No permission for this catalog");
        }
        EntityWrapper<ResourceMeteDataEntity> wrapper = new EntityWrapper<ResourceMeteDataEntity>();
        wrapper.eq(StringUtils.isNotEmpty(catalog_id),"catalog_id",params.get("catalog_id"))
                .eq("push_state",1);
        List<ResourceMeteDataEntity> list = resourceMeteDataService.selectList(wrapper);
        List<ResourceMeteDataDto> data = new ArrayList<ResourceMeteDataDto>();
        if(list != null && list.size() > 0){
            for(ResourceMeteDataEntity mete : list){
                ResourceMeteDataDto dto = new ResourceMeteDataDto();
                dto.setCatagoryCode(mete.getCategoryCode());
                dto.setCatalogName(mete.getCatalogName());
                dto.setCategoryName(mete.getCategoryName());
                dto.setKeyword(mete.getKeyword());
                dto.setMetedataIdentifier(mete.getMetedataIdentifier());
                dto.setMeteId(mete.getMeteId());
                if(mete.getMeteType() == 0){
                    dto.setMeteType("资源类型");
                }else{
                    dto.setMeteType("服务类型");
                }
                dto.setOrganisationName(mete.getOrganisationName());
                dto.setResourceAbstract(mete.getResourceAbstract());
                dto.setResourceSign(mete.getResourceSign());
                dto.setResourceTitle(mete.getResourceTitle());
                dto.setResourceCategory("主题分类");
                data.add(dto);
            }
        }
        CatalogSearchEntity catalogSearchEntity = new CatalogSearchEntity();
        catalogSearchEntity.setSearchDate(new Date());
        catalogSearchService.insert(catalogSearchEntity);
        return R.ok().put("data",data);
    }
}
