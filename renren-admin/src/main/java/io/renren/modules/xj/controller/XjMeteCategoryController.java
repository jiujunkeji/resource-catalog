package io.renren.modules.xj.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import io.renren.common.validator.ValidatorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.xj.entity.XjMeteCategoryEntity;
import io.renren.modules.xj.service.XjMeteCategoryService;
import io.renren.common.utils.R;



/**
 * 元数据分类表
 *
 * @author wangdehai
 * @email 594340717@qq.com
 * @date 2020-04-22 15:46:35
 */
@RestController
@RequestMapping("xj/xjmetecategory")
public class XjMeteCategoryController {
    @Autowired
    private XjMeteCategoryService xjMeteCategoryService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("xj:xjmetecategory:list")
    public List<XjMeteCategoryEntity> list(@RequestParam Map<String, Object> params){
        List<XjMeteCategoryEntity> list = xjMeteCategoryService.selectList(null);
        for(XjMeteCategoryEntity meteCategoryEntity : list){
            XjMeteCategoryEntity parentEntity = xjMeteCategoryService.selectById(meteCategoryEntity.getParentId());
            if(parentEntity != null){
                meteCategoryEntity.setParentName(parentEntity.getName());
            }
        }
        return list;
    }

    /**
     * 条件查询（根据元数据分类编号或者分类名称）
     */
    @RequestMapping("/queryList")
    //@RequiresPermissions("resource:metecategory:list")
    public R queryList(@RequestParam String str){
        List<XjMeteCategoryEntity> meteCategoryEntities=xjMeteCategoryService.searchFindByMeteCategoryNumberOrName(str);
        if(meteCategoryEntities!=null && meteCategoryEntities.size()>0){
            return R.ok().put("meteCategoryList",meteCategoryEntities);
        }
        return R.error();
    }

    /**
     * 元数据分类一键启用
     */
    @RequestMapping("/updateEnabledState")
    public R updateEnabledState(@RequestParam Long meteCategoryId){
        List<XjMeteCategoryEntity> xjMeteCategoryEntityList=xjMeteCategoryService.updateEnabledState(meteCategoryId);
        if(xjMeteCategoryEntityList!=null && xjMeteCategoryEntityList.size()>0){
            for(XjMeteCategoryEntity xjMeteCategoryEntity:xjMeteCategoryEntityList){
                xjMeteCategoryEntity.setIsDisabled(0);
                xjMeteCategoryService.update(xjMeteCategoryEntity,null);
            }
        }
        return R.ok();
    }

    /**
     * 元数据分类一键禁用
     */
    @RequestMapping("/updateDisabledState")
    public R updateDisabledState(@RequestParam Long meteCategoryId){
        List<XjMeteCategoryEntity> xjMeteCategoryEntityList=xjMeteCategoryService.updateEnabledState(meteCategoryId);
        if(xjMeteCategoryEntityList!=null && xjMeteCategoryEntityList.size()>0){
            for(XjMeteCategoryEntity xjMeteCategoryEntity:xjMeteCategoryEntityList){
                xjMeteCategoryEntity.setIsDisabled(1);
                xjMeteCategoryService.update(xjMeteCategoryEntity,null);
            }
        }
        return R.ok();
    }



    /**
     * 初始化列表(一级)
     */
    @RequestMapping("/initList")
    public R initList(){
        List<XjMeteCategoryEntity> meteCategoryList = xjMeteCategoryService.selectList(new EntityWrapper<XjMeteCategoryEntity>().eq("parent_id",0));
        return R.ok().put("meteCategoryList",meteCategoryList);
    }
    /**
     * 查询下级列表(下级)
     */
    @RequestMapping("/selectChildList")
    public R selectChildList(@RequestParam Long parentId){
        List<XjMeteCategoryEntity> meteCategoryList = xjMeteCategoryService.selectList(new EntityWrapper<XjMeteCategoryEntity>().eq("parent_id",parentId));
        return R.ok().put("childList",meteCategoryList);
    }
    /**
     * 信息
     */
    @RequestMapping("/info/{meteCategoryId}")
    //@RequiresPermissions("xj:xjmetecategory:info")
    public R info(@PathVariable("meteCategoryId") Long meteCategoryId){
        XjMeteCategoryEntity xjMeteCategory = xjMeteCategoryService.selectById(meteCategoryId);
        XjMeteCategoryEntity parentEntity = xjMeteCategoryService.selectById(xjMeteCategory.getParentId());
        if(parentEntity != null){
            xjMeteCategory.setParentName(parentEntity.getName());
        }
        return R.ok().put("xjMeteCategory", xjMeteCategory);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("xj:xjmetecategory:save")
    public R save(@RequestBody XjMeteCategoryEntity xjMeteCategory){
        xjMeteCategoryService.insert(xjMeteCategory);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("xj:xjmetecategory:update")
    public R update(@RequestBody XjMeteCategoryEntity xjMeteCategory){
        ValidatorUtils.validateEntity(xjMeteCategory);
        xjMeteCategoryService.updateAllColumnById(xjMeteCategory);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("xj:xjmetecategory:delete")
    public R delete(@RequestBody Long[] meteCategoryIds){
        xjMeteCategoryService.deleteBatchIds(Arrays.asList(meteCategoryIds));

        return R.ok();
    }

}
