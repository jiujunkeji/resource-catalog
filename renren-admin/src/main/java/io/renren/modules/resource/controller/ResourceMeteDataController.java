package io.renren.modules.resource.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.resource.entity.ResourceMeteDataEntity;
import io.renren.modules.resource.service.ResourceMeteDataService;
import io.renren.modules.sys.controller.AbstractController;
import io.renren.modules.sys.service.SysDeptService;
import io.renren.modules.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;


/**
 * 
 *
 * @author wangdehai
 * @email 594340717@qq.com
 * @date 2019-07-25 16:57:52
 */
@RestController
@RequestMapping("resource/resourcemetedata")
public class ResourceMeteDataController extends AbstractController{
    @Autowired
    private ResourceMeteDataService resourceMeteDataService;
    @Autowired
    private SysDeptService deptService;
    @Autowired
    private SysUserService userService;

    /**
     * 列表
     */
    @RequestMapping("/list")
//    @RequiresPermissions("resource:resourcemetedata:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = resourceMeteDataService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{meteId}")
//    @RequiresPermissions("resource:resourcemetedata:info")
    public R info(@PathVariable("meteId") Long meteId){
        ResourceMeteDataEntity resourceMeteData = resourceMeteDataService.selectById(meteId);

        return R.ok().put("resourceMeteData", resourceMeteData);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
//    @RequiresPermissions("resource:resourcemetedata:save")
    public R save(@RequestBody ResourceMeteDataEntity resourceMeteData){
        resourceMeteData.setUpdateTime(new Date());
        resourceMeteData.setReviewState(0);
        resourceMeteData.setPushState(0);
        resourceMeteDataService.insert(resourceMeteData);
        //设置元数据标识
        String metedataIdentifier = "metadata_ " + resourceMeteData.getCategoryId() + "-" + resourceMeteData.getCatalogId() + "-" + resourceMeteData.getMeteId();
        resourceMeteData.setMetedataIdentifier(metedataIdentifier);
        resourceMeteDataService.updateById(resourceMeteData);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
//    @RequiresPermissions("resource:resourcemetedata:update")
    public R update(@RequestBody ResourceMeteDataEntity resourceMeteData){
        ValidatorUtils.validateEntity(resourceMeteData);
        resourceMeteDataService.updateAllColumnById(resourceMeteData);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
//    @RequiresPermissions("resource:resourcemetedata:delete")
    public R delete(@RequestBody Long[] meteIds){
//        resourceMeteDataService.deleteBatchIds(Arrays.asList(meteIds));
        List<ResourceMeteDataEntity> list = new ArrayList<ResourceMeteDataEntity>();
        list = resourceMeteDataService.selectBatchIds(Arrays.asList(meteIds));
        if(list != null && list.size() > 0){
            for(ResourceMeteDataEntity resourceMeteDataEntity : list){
                resourceMeteDataEntity.setIsDeleted(1);
            }
            resourceMeteDataService.updateBatchById(list);
        }
        return R.ok();
    }

    /**
     * 提交
     */
    @RequestMapping("/submit")
    public R submit(@RequestBody Long[] meteIds){
        List<ResourceMeteDataEntity> list = new ArrayList<ResourceMeteDataEntity>();
        list = resourceMeteDataService.selectBatchIds(Arrays.asList(meteIds));
        if(list != null && list.size() > 0){
            for(ResourceMeteDataEntity mete : list){
                mete.setReviewState(1);
                mete.setSubmitDeptId(getDeptId());
                mete.setSubmitDeptName(deptService.selectById(getDeptId()).getName());
                mete.setSubmitTime(new Date());
                mete.setSubmitUserId(getUserId());
                mete.setSubmitUserName(getUser().getUsername());
            }
            resourceMeteDataService.updateBatchById(list);
        }
        return R.ok("提交成功");
    }

    /**
     * 撤回
     */
    @RequestMapping("/revoke")
    public R revoke(@RequestParam Long meteId){
        ResourceMeteDataEntity mete = resourceMeteDataService.selectById(meteId);
        Date now = new Date(System.currentTimeMillis() - 600000);
        Date submitTime = mete.getSubmitTime();
        if (now.before(submitTime)) {
            mete.setReviewState(0);
        } else {
            return R.error("提交时间超过限制，不能撤回");
        }
        resourceMeteDataService.updateById(mete);
        return R.ok("操作成功");
    }

    /**
     * 审核通过
     */
    @RequestMapping("/agree")
    public R agree(@RequestParam Long meteId){
        ResourceMeteDataEntity mete = resourceMeteDataService.selectById(meteId);
        mete.setReviewState(2);
        mete.setReviewDeptId(getDeptId());
        mete.setReviewDeptName(deptService.selectById(getDeptId()).getName());
        mete.setReviewTime(new Date());
        mete.setReviewUserId(getUserId());
        mete.setReviewUserName(getUser().getUsername());
        return R.ok();
    }

    /**
     * 审核拒绝
     */
    @RequestMapping("/refuse")
    public R refuse(@RequestParam Long meteId){
        ResourceMeteDataEntity mete = resourceMeteDataService.selectById(meteId);
        mete.setReviewState(3);
        mete.setReviewDeptId(getDeptId());
        mete.setReviewDeptName(deptService.selectById(getDeptId()).getName());
        mete.setReviewTime(new Date());
        mete.setReviewUserId(getUserId());
        mete.setReviewUserName(getUser().getUsername());
        return R.ok();
    }

    /**
     * 发布
     */
    @RequestMapping("/push")
    public R push(@RequestParam Long meteId){
        ResourceMeteDataEntity mete = resourceMeteDataService.selectById(meteId);
        mete.setPushState(1);
        mete.setPushDeptId(getDeptId());
        mete.setPushDeptName(deptService.selectById(getDeptId()).getName());
        mete.setPushTime(new Date());
        mete.setPushUserId(getUserId());
        mete.setPushUserName(getUser().getUsername());
        return R.ok();
    }
    /**
     * 停止发布
     */
    @RequestMapping("/stopPush")
    public R stopPush(@RequestParam Long meteId){
        ResourceMeteDataEntity mete = resourceMeteDataService.selectById(meteId);
        mete.setPushState(0);
        mete.setPushDeptId(getDeptId());
        mete.setPushDeptName(deptService.selectById(getDeptId()).getName());
        mete.setPushTime(new Date());
        mete.setPushUserId(getUserId());
        mete.setPushUserName(getUser().getUsername());
        return R.ok();
    }
}
