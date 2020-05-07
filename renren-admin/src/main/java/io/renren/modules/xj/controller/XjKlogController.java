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

import io.renren.modules.xj.entity.XjKlogEntity;
import io.renren.modules.xj.service.XjKlogService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 
 *
 * @author wangdehai
 * @email 594340717@qq.com
 * @date 2020-05-07 15:53:30
 */
@RestController
@RequestMapping("xj/xjklog")
public class XjKlogController {
    @Autowired
    private XjKlogService xjKlogService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("xj:xjklog:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = xjKlogService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{klogId}")
    //@RequiresPermissions("xj:xjklog:info")
    public R info(@PathVariable("klogId") Integer klogId){
        XjKlogEntity xjKlog = xjKlogService.selectById(klogId);

        return R.ok().put("xjKlog", xjKlog);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("xj:xjklog:save")
    public R save(@RequestBody XjKlogEntity xjKlog){
        xjKlogService.insert(xjKlog);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("xj:xjklog:update")
    public R update(@RequestBody XjKlogEntity xjKlog){
        ValidatorUtils.validateEntity(xjKlog);
        xjKlogService.updateAllColumnById(xjKlog);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("xj:xjklog:delete")
    public R delete(@RequestBody Integer[] klogIds){
        xjKlogService.deleteBatchIds(Arrays.asList(klogIds));

        return R.ok();
    }

}
