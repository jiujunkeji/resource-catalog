package io.renren.modules.resource.controller;

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

import io.renren.modules.resource.entity.CatalogDeptEntity;
import io.renren.modules.resource.service.CatalogDeptService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 
 *
 * @author wangdehai
 * @email 594340717@qq.com
 * @date 2019-08-20 16:13:07
 */
@RestController
@RequestMapping("resource/catalogdept")
public class CatalogDeptController {
    @Autowired
    private CatalogDeptService catalogDeptService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("resource:catalogdept:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = catalogDeptService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("resource:catalogdept:info")
    public R info(@PathVariable("id") Long id){
        CatalogDeptEntity catalogDept = catalogDeptService.selectById(id);

        return R.ok().put("catalogDept", catalogDept);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("resource:catalogdept:save")
    public R save(@RequestBody CatalogDeptEntity catalogDept){
        catalogDeptService.insert(catalogDept);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("resource:catalogdept:update")
    public R update(@RequestBody CatalogDeptEntity catalogDept){
        ValidatorUtils.validateEntity(catalogDept);
        catalogDeptService.updateAllColumnById(catalogDept);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("resource:catalogdept:delete")
    public R delete(@RequestBody Long[] ids){
        catalogDeptService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
