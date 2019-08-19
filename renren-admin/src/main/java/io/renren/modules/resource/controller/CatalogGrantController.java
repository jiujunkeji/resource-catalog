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

import io.renren.modules.resource.entity.CatalogGrantEntity;
import io.renren.modules.resource.service.CatalogGrantService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 
 *
 * @author wangdehai
 * @email 594340717@qq.com
 * @date 2019-08-19 15:13:13
 */
@RestController
@RequestMapping("resource/cataloggrant")
public class CatalogGrantController {
    @Autowired
    private CatalogGrantService catalogGrantService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = catalogGrantService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{grantId}")
    public R info(@PathVariable("grantId") Long grantId){
        CatalogGrantEntity catalogGrant = catalogGrantService.selectById(grantId);

        return R.ok().put("catalogGrant", catalogGrant);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody CatalogGrantEntity catalogGrant){
        catalogGrantService.insert(catalogGrant);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody CatalogGrantEntity catalogGrant){
        ValidatorUtils.validateEntity(catalogGrant);
        catalogGrantService.updateAllColumnById(catalogGrant);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] grantIds){
        catalogGrantService.deleteBatchIds(Arrays.asList(grantIds));

        return R.ok();
    }

}
