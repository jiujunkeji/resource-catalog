package io.renren.modules.resource.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.resource.entity.MeteCategoryEntity;
import io.renren.modules.resource.service.MeteCategoryService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * 
 *
 * @author wangdehai
 * @email 594340717@qq.com
 * @date 2019-07-25 16:57:53
 */
@RestController
@RequestMapping("resource/metecategory")
public class MeteCategoryController {
    @Autowired
    private MeteCategoryService meteCategoryService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("resource:metecategory:list")
    public R list(@RequestParam Map<String, Object> params){
        /*List<MeteCategoryEntity> menuList = meteCategoryService.selectList(null);
        for(MeteCategoryEntity meteCategoryEntity : menuList){
            MeteCategoryEntity parentMenuEntity = meteCategoryService.selectById(meteCategoryEntity.getParentId());
            if(parentMenuEntity != null){
                meteCategoryEntity.setParentName(parentMenuEntity.getName());
            }
        }*/
        List<MeteCategoryEntity> list = meteCategoryService.selectList(null);
        return R.ok().put("list",list);
    }


    /**
     * 初始化列表(一级)
     */
    @RequestMapping("/initList")
    public R initList(){
        List<MeteCategoryEntity> meteCategoryList = meteCategoryService.selectList(new EntityWrapper<MeteCategoryEntity>().eq("parent_id",0));
        return R.ok().put("meteCategoryList",meteCategoryList);
    }
    /**
     * 查询下级列表(下级)
     */
    @RequestMapping("/selectChildList")
    public R selectChildList(@RequestParam Long parentId){
        List<MeteCategoryEntity> meteCategoryList = meteCategoryService.selectList(new EntityWrapper<MeteCategoryEntity>().eq("parent_id",parentId));
        return R.ok().put("childList",meteCategoryList);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{meteCategoryId}")
    @RequiresPermissions("resource:metecategory:info")
    public R info(@PathVariable("meteCategoryId") Long meteCategoryId){
        MeteCategoryEntity meteCategory = meteCategoryService.selectById(meteCategoryId);

        return R.ok().put("meteCategory", meteCategory);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("resource:metecategory:save")
    public R save(@RequestBody MeteCategoryEntity meteCategory){
        meteCategoryService.insert(meteCategory);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("resource:metecategory:update")
    public R update(@RequestBody MeteCategoryEntity meteCategory){
        ValidatorUtils.validateEntity(meteCategory);
        meteCategoryService.updateAllColumnById(meteCategory);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("resource:metecategory:delete")
    public R delete(@RequestBody Long[] meteCategoryIds){
        meteCategoryService.deleteBatchIds(Arrays.asList(meteCategoryIds));

        return R.ok();
    }

}
