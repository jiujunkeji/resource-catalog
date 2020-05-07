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

import io.renren.modules.xj.entity.XjChildMonitorEntity;
import io.renren.modules.xj.service.XjChildMonitorService;
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
@RequestMapping("xj/xjchildmonitor")
public class XjChildMonitorController {
    @Autowired
    private XjChildMonitorService xjChildMonitorService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("xj:xjchildmonitor:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = xjChildMonitorService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{childJobId}")
    //@RequiresPermissions("xj:xjchildmonitor:info")
    public R info(@PathVariable("childJobId") Integer childJobId){
        XjChildMonitorEntity xjChildMonitor = xjChildMonitorService.selectById(childJobId);

        return R.ok().put("xjChildMonitor", xjChildMonitor);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("xj:xjchildmonitor:save")
    public R save(@RequestBody XjChildMonitorEntity xjChildMonitor){
        xjChildMonitorService.insert(xjChildMonitor);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("xj:xjchildmonitor:update")
    public R update(@RequestBody XjChildMonitorEntity xjChildMonitor){
        ValidatorUtils.validateEntity(xjChildMonitor);
        xjChildMonitorService.updateAllColumnById(xjChildMonitor);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("xj:xjchildmonitor:delete")
    public R delete(@RequestBody Integer[] childJobIds){
        xjChildMonitorService.deleteBatchIds(Arrays.asList(childJobIds));

        return R.ok();
    }

}
