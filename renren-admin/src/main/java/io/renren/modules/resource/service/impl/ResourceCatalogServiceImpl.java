package io.renren.modules.resource.service.impl;

import io.renren.common.utils.Constant;
import io.renren.modules.resource.entity.CatalogDeptEntity;
import io.renren.modules.resource.entity.CatalogUserEntity;
import io.renren.modules.resource.service.CatalogDeptService;
import io.renren.modules.resource.service.CatalogUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.resource.dao.ResourceCatalogDao;
import io.renren.modules.resource.entity.ResourceCatalogEntity;
import io.renren.modules.resource.service.ResourceCatalogService;


@Service("resourceCatalogService")
public class ResourceCatalogServiceImpl extends ServiceImpl<ResourceCatalogDao, ResourceCatalogEntity> implements ResourceCatalogService {

    @Autowired
    private CatalogUserService catalogUserService;
    @Autowired
    private CatalogDeptService catalogDeptService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String name = (String) params.get("name");
        Page<ResourceCatalogEntity> page = this.selectPage(
                new Query<ResourceCatalogEntity>(params).getPage(),
                new EntityWrapper<ResourceCatalogEntity>()
                        .like(StringUtils.isNotEmpty(name),"name",name)
                        .eq("is_used",1)
                        .eq("is_deleted",0)
        );

        return new PageUtils(page);
    }

    @Override
    public List<ResourceCatalogEntity> selectUserList(EntityWrapper<ResourceCatalogEntity> wrapper, Long userId, Long deptId) {
        List<ResourceCatalogEntity> allList = new ArrayList<ResourceCatalogEntity>();
        List<ResourceCatalogEntity> list = new ArrayList<ResourceCatalogEntity>();
        allList = this.selectList(wrapper);
        //系统管理员，拥有最高权限
        if(userId == Constant.SUPER_ADMIN){
            return allList;
        }
        Set<Long> idSet = new HashSet<Long>();
        //普通权限，先查询他的部门权限和用户权限，将所有的catalogId添加到Set
//        List<CatalogDeptEntity> catalogDeptList = catalogDeptService.selectList(new EntityWrapper<CatalogDeptEntity>().eq("dept_id",deptId));
        List<CatalogUserEntity> catalogUserList = catalogUserService.selectList(new EntityWrapper<CatalogUserEntity>().eq("user_id",userId));
//        if(catalogDeptList != null && catalogDeptList.size() > 0){
//            for(CatalogDeptEntity catalogDeptEntity : catalogDeptList){
//                idSet.add(catalogDeptEntity.getCatalogId());
//            }
//        }
        if(catalogUserList != null && catalogUserList.size() > 0){
            for(CatalogUserEntity catalogUserEntity : catalogUserList){
                idSet.add(catalogUserEntity.getCatalogId());
            }
        }
        for(ResourceCatalogEntity catalog : allList){
            if(idSet.contains(catalog.getCatalogId())){
                list.add(catalog);
            }
        }
        return list;
    }

    @Override
    public List<ResourceCatalogEntity> selectOgList(EntityWrapper<ResourceCatalogEntity> wrapper, Long organisationId) {
        List<ResourceCatalogEntity> allList = new ArrayList<ResourceCatalogEntity>();
        List<ResourceCatalogEntity> list = new ArrayList<ResourceCatalogEntity>();
        allList = this.selectList(wrapper);
        Set<Long> idSet = new HashSet<Long>();
        //普通权限，先查询他的部门权限，将所有的catalogId添加到Set
        List<CatalogDeptEntity> catalogDeptList = catalogDeptService.selectList(new EntityWrapper<CatalogDeptEntity>().eq("organisation_id",organisationId));

        if(catalogDeptList != null && catalogDeptList.size() > 0){
            for(CatalogDeptEntity catalogDeptEntity : catalogDeptList){
                idSet.add(catalogDeptEntity.getCatalogId());
            }
        }
        for(ResourceCatalogEntity catalog : allList){
            if(idSet.contains(catalog.getCatalogId())){
                list.add(catalog);
            }
        }
        return list;
    }

    @Override
    public void insertCatalog(ResourceCatalogEntity catalogEntity) {
        Long parentId;
        if(StringUtils.isNotEmpty(catalogEntity.getThreeName())){
            parentId = this.selectOne(new EntityWrapper<ResourceCatalogEntity>().eq("name",catalogEntity.getTwoName())).getCatalogId();
            catalogEntity.setName(catalogEntity.getThreeName());
            catalogEntity.setParentId(parentId);
        }else if(StringUtils.isNotEmpty(catalogEntity.getTwoName())){
            parentId = this.selectOne(new EntityWrapper<ResourceCatalogEntity>().eq("name",catalogEntity.getOneName())).getCatalogId();
            catalogEntity.setName(catalogEntity.getTwoName());
            catalogEntity.setParentId(parentId);
        }else if(StringUtils.isNotEmpty(catalogEntity.getOneName())){
            catalogEntity.setName(catalogEntity.getOneName());
            if(catalogEntity.getType() == 0){
                catalogEntity.setParentId(1L);
            }else{
                catalogEntity.setParentId(2L);
            }
        }
        this.insert(catalogEntity);
    }

    @Override
    public ResourceCatalogEntity selectCatalog(ResourceCatalogEntity catalogEntity){
        String oneName = null;
        String twoName = null;
        String threeName = null;
        Long a = catalogEntity.getParentId();
        if(a != 1L && a != 2L){
            Long b = this.selectById(a).getParentId();
            if(b != 1L && b != 1L){
                oneName  = this.selectById(b).getName();
                twoName  = this.selectById(a).getName();
                threeName  = catalogEntity.getName();
            }else{
                oneName = this.selectById(a).getName();
                twoName = catalogEntity.getName();
            }
        }else{
            oneName = catalogEntity.getName();
        }
        catalogEntity.setOneName(oneName);
        catalogEntity.setTwoName(twoName);
        catalogEntity.setThreeName(threeName);
        return catalogEntity;
    }

    @Override
    public String selectAllCatalogName(Long catalogId) {
        StringBuffer stf = new StringBuffer("");
        List<ResourceCatalogEntity> list = new ArrayList<ResourceCatalogEntity>();
        ResourceCatalogEntity currentCatalog = this.selectById(catalogId);
        if(currentCatalog != null){
            list.add(currentCatalog);
            list = selectParentCatalogList(list,currentCatalog);
            for(int i = list.size()-1; i > -1; i--){
                stf.append(list.get(i).getName());
                stf.append("/");
            }
        }
        String result = stf.toString();
        if(result.length() > 0){
            result = result.substring(0,result.length()-1);
        }
        return result;
    }

    @Override
    public List<ResourceCatalogEntity> selectParentCatalogList(List<ResourceCatalogEntity> list, ResourceCatalogEntity currentCatalog) {
        if(currentCatalog == null){
            return list;
        }
        ResourceCatalogEntity parentCatalog = this.selectById(currentCatalog.getParentId());
        if(parentCatalog != null){
            list.add(parentCatalog);
            selectParentCatalogList(list,parentCatalog);
        }
        return list;
    }

    @Override
    public List<ResourceCatalogEntity> selectChildCatalogList(List<ResourceCatalogEntity> list, ResourceCatalogEntity currentCatalog) {
        if(currentCatalog == null){
            return list;
        }
        List<ResourceCatalogEntity> childCatalogList = this.selectList(new EntityWrapper<ResourceCatalogEntity>().eq("parent_id",currentCatalog.getCatalogId()));
        if(childCatalogList != null && childCatalogList.size() > 0){
            list.addAll(childCatalogList);
            for(ResourceCatalogEntity child : childCatalogList){
                selectChildCatalogList(list,child);
            }
        }
        return list;
    }
}
