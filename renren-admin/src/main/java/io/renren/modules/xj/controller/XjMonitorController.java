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

import io.renren.modules.xj.entity.XjMonitorEntity;
import io.renren.modules.xj.service.XjMonitorService;
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
@RequestMapping("xj/xjmonitor")
public class XjMonitorController {
    @Autowired
    private XjMonitorService xjMonitorService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("xj:xjmonitor:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = xjMonitorService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{monitorId}")
    //@RequiresPermissions("xj:xjmonitor:info")
    public R info(@PathVariable("monitorId") Integer monitorId){
        XjMonitorEntity xjMonitor = xjMonitorService.selectById(monitorId);

        return R.ok().put("xjMonitor", xjMonitor);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("xj:xjmonitor:save")
    public R save(@RequestBody XjMonitorEntity xjMonitor){
        xjMonitorService.insert(xjMonitor);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("xj:xjmonitor:update")
    public R update(@RequestBody XjMonitorEntity xjMonitor){
        ValidatorUtils.validateEntity(xjMonitor);
        xjMonitorService.updateAllColumnById(xjMonitor);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("xj:xjmonitor:delete")
    public R delete(@RequestBody Integer[] monitorIds){
        xjMonitorService.deleteBatchIds(Arrays.asList(monitorIds));

        return R.ok();
    }

}
