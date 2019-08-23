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

import io.renren.modules.resource.entity.CatalogUserEntity;
import io.renren.modules.resource.service.CatalogUserService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 
 *
 * @author wangdehai
 * @email 594340717@qq.com
 * @date 2019-08-20 16:11:33
 */
@RestController
@RequestMapping("resource/cataloguser")
public class CatalogUserController {
    @Autowired
    private CatalogUserService catalogUserService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("resource:cataloguser:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = catalogUserService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("resource:cataloguser:info")
    public R info(@PathVariable("id") Long id){
        CatalogUserEntity catalogUser = catalogUserService.selectById(id);

        return R.ok().put("catalogUser", catalogUser);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("resource:cataloguser:save")
    public R save(@RequestBody CatalogUserEntity catalogUser){
        catalogUserService.insert(catalogUser);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("resource:cataloguser:update")
    public R update(@RequestBody CatalogUserEntity catalogUser){
        ValidatorUtils.validateEntity(catalogUser);
        catalogUserService.updateAllColumnById(catalogUser);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("resource:cataloguser:delete")
    public R delete(@RequestBody Long[] ids){
        catalogUserService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
