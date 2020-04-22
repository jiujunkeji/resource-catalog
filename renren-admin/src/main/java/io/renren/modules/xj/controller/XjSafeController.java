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

import io.renren.modules.xj.entity.XjSafeEntity;
import io.renren.modules.xj.service.XjSafeService;
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
@RequestMapping("xj/xjsafe")
public class XjSafeController {
    @Autowired
    private XjSafeService xjSafeService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("xj:xjsafe:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = xjSafeService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{safeId}")
    //@RequiresPermissions("xj:xjsafe:info")
    public R info(@PathVariable("safeId") Long safeId){
        XjSafeEntity xjSafe = xjSafeService.selectById(safeId);

        return R.ok().put("xjSafe", xjSafe);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("xj:xjsafe:save")
    public R save(@RequestBody XjSafeEntity xjSafe){
        xjSafeService.insert(xjSafe);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("xj:xjsafe:update")
    public R update(@RequestBody XjSafeEntity xjSafe){
        ValidatorUtils.validateEntity(xjSafe);
        xjSafeService.updateAllColumnById(xjSafe);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("xj:xjsafe:delete")
    public R delete(@RequestBody Long[] safeIds){
        xjSafeService.deleteBatchIds(Arrays.asList(safeIds));

        return R.ok();
    }

}
