package io.renren.modules.xj.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.xj.entity.XjMetaDataEntity;
import io.renren.modules.xj.entity.XjMetaDataSetEntity;
import io.renren.modules.xj.entity.XjMeteCategoryEntity;
import io.renren.modules.xj.service.XjMetaDataSetService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.xj.entity.XjMeteSetCategoryEntity;
import io.renren.modules.xj.service.XjMeteSetCategoryService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 元数据集分类表
 *
 * @author wangdehai
 * @email 594340717@qq.com
 * @date 2020-04-22 15:46:35
 */
@RestController
@RequestMapping("xj/xjmetesetcategory")
public class XjMeteSetCategoryController {
    @Autowired
    private XjMeteSetCategoryService xjMeteSetCategoryService;
    @Autowired
    private XjMetaDataSetService xjMetaDataSetService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("xj:xjmetesetcategory:list")
    public List<XjMeteSetCategoryEntity> list(@RequestParam Map<String, Object> params){
        List<XjMeteSetCategoryEntity> list = xjMeteSetCategoryService.selectList(null);
        for(XjMeteSetCategoryEntity meteSetCategoryEntity : list){
            XjMeteSetCategoryEntity parentEntity = xjMeteSetCategoryService.selectById(meteSetCategoryEntity.getParentId());
            if(parentEntity != null){
                meteSetCategoryEntity.setParentName(parentEntity.getName());
            }
        }
        return list;
    }

    /**
     * 条件查询（根据元数据分类编号或者分类名称）
     */
    @RequestMapping("/queryList")
    //@RequiresPermissions("resource:metecategory:list")
    public R queryList(@RequestParam Map<String, Object> params){
        PageUtils page=xjMeteSetCategoryService.searchFindByMeteSetCategoryNumberOrName(params);
        return R.ok().put("page",page);
    }

    /**
     * 元数据集分类一键启用
     */
    @RequestMapping("/updateEnabledState")
    public R updateEnabledState(@RequestBody Long[] meteCategorySetIds){
        List<XjMeteSetCategoryEntity> xjMeteSetCategoryEntityList=xjMeteSetCategoryService.updateEnabledState(meteCategorySetIds);
        if(xjMeteSetCategoryEntityList!=null && xjMeteSetCategoryEntityList.size()>0){
            for(XjMeteSetCategoryEntity xjMeteSetCategoryEntity:xjMeteSetCategoryEntityList){
                xjMeteSetCategoryEntity.setIsDisabled(0);
                xjMeteSetCategoryService.updateById(xjMeteSetCategoryEntity);
            }
        }
        return R.ok();
    }

    /**
     * 元数据集分类一键禁用
     */
    @RequestMapping("/updateDisabledState")
    public R updateDisabledState(@RequestBody Long[] meteCategorySetIds){
        List<XjMeteSetCategoryEntity> xjMeteSetCategoryEntityList=xjMeteSetCategoryService.updateEnabledState(meteCategorySetIds);
        if(xjMeteSetCategoryEntityList!=null && xjMeteSetCategoryEntityList.size()>0){
            for(XjMeteSetCategoryEntity xjMeteSetCategoryEntity:xjMeteSetCategoryEntityList){
                xjMeteSetCategoryEntity.setIsDisabled(1);
                xjMeteSetCategoryService.updateById(xjMeteSetCategoryEntity);
            }

        }
        return R.ok();
    }
    /**
     * 信息
     */
    @RequestMapping("/info/{meteCategorySetId}")
    //@RequiresPermissions("xj:xjmetesetcategory:info")
    public R info(@PathVariable("meteCategorySetId") Long meteCategorySetId){
        XjMeteSetCategoryEntity xjMeteSetCategory = xjMeteSetCategoryService.selectById(meteCategorySetId);

        return R.ok().put("xjMeteSetCategory", xjMeteSetCategory);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("xj:xjmetesetcategory:save")
    public R save(@RequestBody XjMeteSetCategoryEntity xjMeteSetCategory){
        xjMeteSetCategoryService.insert(xjMeteSetCategory);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("xj:xjmetesetcategory:update")
    public R update(@RequestBody XjMeteSetCategoryEntity xjMeteSetCategory){
        ValidatorUtils.validateEntity(xjMeteSetCategory);
        xjMeteSetCategoryService.updateAllColumnById(xjMeteSetCategory);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("xj:xjmetesetcategory:delete")
    public R delete(@RequestBody Long[] meteCategorySetIds){
        xjMeteSetCategoryService.deleteBatchIds(Arrays.asList(meteCategorySetIds));
        List<XjMetaDataSetEntity> datasetList=xjMetaDataSetService.selectBatchIds(Arrays.asList(meteCategorySetIds));
        if(datasetList.size()>0 && datasetList!=null){
            return R.error("该分类下有关联的元数据集，请勿删除！，否则后果自负！");
        }
        return R.ok();
    }

}
