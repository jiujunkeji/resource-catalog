package io.renren.modules.xj.controller;

import java.util.*;

import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.job.controller.ScheduleJobController;
import io.renren.modules.job.entity.ScheduleJobEntity;
import io.renren.modules.job.service.ScheduleJobService;
import io.renren.modules.xj.entity.XjFtpEntity;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.xj.entity.XjScheduleJobEntity;
import io.renren.modules.xj.service.XjScheduleJobService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 
 *
 * @author wangdehai
 * @email 594340717@qq.com
 * @date 2020-04-28 15:21:11
 */
@RestController
@RequestMapping("xj/xjschedulejob")
public class XjScheduleJobController {
    @Autowired
    private XjScheduleJobService xjScheduleJobService;

    @Autowired
    private ScheduleJobService scheduleJobService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("resource:xjschedulejob:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = xjScheduleJobService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{triggerId}")
    //@RequiresPermissions("resource:xjschedulejob:info")
    public R info(@PathVariable("triggerId") Integer triggerId){
        XjScheduleJobEntity xjScheduleJob = xjScheduleJobService.selectById(triggerId);

        return R.ok().put("xjScheduleJob", xjScheduleJob);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("resource:xjschedulejob:save")
    public R save(@RequestBody XjScheduleJobEntity xjScheduleJob){
        ScheduleJobEntity scheduleJobEntity = new ScheduleJobEntity();
        scheduleJobEntity.setBeanName("ktrRun");
        scheduleJobEntity.setMethodName("run");
        scheduleJobEntity.setParams(xjScheduleJob.getKtrId().toString());
        scheduleJobEntity.setCronExpression(xjScheduleJob.getTriggerCron());
        scheduleJobEntity.setCreateTime(new Date());
        long sjId = scheduleJobService.save(scheduleJobEntity);
        xjScheduleJob.setStatus(0);
        xjScheduleJob.setIsDelete(0);
        xjScheduleJob.setCreateTime(new Date());
        xjScheduleJob.setScheduleId(sjId);
        xjScheduleJobService.insert(xjScheduleJob);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("resource:xjschedulejob:update")
    public R update(@RequestBody XjScheduleJobEntity xjScheduleJob){
        ValidatorUtils.validateEntity(xjScheduleJob);
        xjScheduleJobService.updateAllColumnById(xjScheduleJob);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("resource:xjschedulejob:delete")
    public R delete(@RequestBody Integer[] triggerIds){
        List<Integer> triggerId =  Arrays.asList(triggerIds);
        for (Integer id: triggerId){
            XjScheduleJobEntity xe = xjScheduleJobService.selectById(id);
            xe.setIsDelete(1);
            xjScheduleJobService.updateById(xe);
        }

        return R.ok();
    }

    /**
     *立即执行
     */
    @RequestMapping("/run")
    //@RequiresPermissions("sys:schedule:run")
    public R run(@RequestBody int triggerId){
        XjScheduleJobEntity xe = xjScheduleJobService.selectById(triggerId);
        ScheduleJobEntity se = new ScheduleJobEntity();
        se.setParams(xe.getKtrId().toString());
        se.setCronExpression(xe.getTriggerCron());
        scheduleJobService.updateById(se);
        xe.setStatus(scheduleJobService.run(xe.getScheduleId()));
        xjScheduleJobService.updateById(xe);
        return R.ok();
    }

    /**
     * 暂停执行
     */
    @RequestMapping("/pause")
    //@RequiresPermissions("sys:schedule:pause")
    public R pause(@RequestBody int triggerId){
        XjScheduleJobEntity xe = xjScheduleJobService.selectById(triggerId);
        xe.setStatus(scheduleJobService.pause(xe.getScheduleId()));
        xjScheduleJobService.updateById(xe);

        return R.ok();
    }

    /**
     * 恢复执行
     */
    @RequestMapping("/resume")
    //@RequiresPermissions("sys:schedule:resume")
    public R resume(@RequestBody int triggerId){
        XjScheduleJobEntity xe = xjScheduleJobService.selectById(triggerId);
        xe.setStatus(scheduleJobService.resume(xe.getScheduleId()));
        xjScheduleJobService.updateById(xe);

        return R.ok();
    }


}
