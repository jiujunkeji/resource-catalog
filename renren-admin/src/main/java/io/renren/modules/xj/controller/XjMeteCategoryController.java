package io.renren.modules.xj.controller;

import java.util.Arrays;
import java.util.Map;

import io.renren.common.validator.ValidatorUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.xj.entity.XjMeteCategoryEntity;
import io.renren.modules.xj.service.XjMeteCategoryService;
import io.renren.common.utils.PageUtils;
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
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = xjMeteCategoryService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{meteCategoryId}")
    //@RequiresPermissions("xj:xjmetecategory:info")
    public R info(@PathVariable("meteCategoryId") Long meteCategoryId){
        XjMeteCategoryEntity xjMeteCategory = xjMeteCategoryService.selectById(meteCategoryId);

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
