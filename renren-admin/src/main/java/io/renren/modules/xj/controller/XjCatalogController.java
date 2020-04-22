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

import io.renren.modules.xj.entity.XjCatalogEntity;
import io.renren.modules.xj.service.XjCatalogService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 
 *
 * @author wangdehai
 * @email 594340717@qq.com
 * @date 2020-04-22 17:21:53
 */
@RestController
@RequestMapping("xj/xjcatalog")
public class XjCatalogController {
    @Autowired
    private XjCatalogService xjCatalogService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("xj:xjcatalog:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = xjCatalogService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{catalogId}")
    //@RequiresPermissions("xj:xjcatalog:info")
    public R info(@PathVariable("catalogId") Long catalogId){
        XjCatalogEntity xjCatalog = xjCatalogService.selectById(catalogId);

        return R.ok().put("xjCatalog", xjCatalog);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("xj:xjcatalog:save")
    public R save(@RequestBody XjCatalogEntity xjCatalog){
        xjCatalogService.insert(xjCatalog);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("xj:xjcatalog:update")
    public R update(@RequestBody XjCatalogEntity xjCatalog){
        ValidatorUtils.validateEntity(xjCatalog);
        xjCatalogService.updateAllColumnById(xjCatalog);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("xj:xjcatalog:delete")
    public R delete(@RequestBody Long[] catalogIds){
        xjCatalogService.deleteBatchIds(Arrays.asList(catalogIds));

        return R.ok();
    }

}
