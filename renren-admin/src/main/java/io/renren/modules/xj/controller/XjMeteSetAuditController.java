package io.renren.modules.xj.controller;

import java.util.*;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.sys.controller.AbstractController;
import io.renren.modules.xj.entity.XjCatalogAuditEntity;
import io.renren.modules.xj.entity.XjCatalogEntity;
import io.renren.modules.xj.entity.XjMetaDataSetEntity;
import io.renren.modules.xj.service.XjMetaDataSetService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.xj.entity.XjMeteSetAuditEntity;
import io.renren.modules.xj.service.XjMeteSetAuditService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;

import static io.renren.modules.sys.shiro.ShiroUtils.getUserId;


/**
 * 元数据集的审核表
 *
 * @author wangdehai
 * @email 594340717@qq.com
 * @date 2020-04-25 15:42:51
 */
@RestController
@RequestMapping("xj/xjmetesetaudit")
public class XjMeteSetAuditController extends AbstractController {
    @Autowired
    private XjMeteSetAuditService xjMeteSetAuditService;
    @Autowired
    private XjMetaDataSetService xjMetaDataSetService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("xj:xjmetesetaudit:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = xjMeteSetAuditService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{meteSetAuditId}")
    //@RequiresPermissions("xj:xjmetesetaudit:info")
    public R info(@PathVariable("meteSetAuditId") Long meteSetAuditId){
        XjMeteSetAuditEntity xjMeteSetAudit = xjMeteSetAuditService.selectById(meteSetAuditId);

        return R.ok().put("xjMeteSetAudit", xjMeteSetAudit);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("xj:xjmetesetaudit:save")
    public R save(@RequestBody XjMeteSetAuditEntity xjMeteSetAudit){
        xjMeteSetAuditService.insert(xjMeteSetAudit);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("xj:xjmetesetaudit:update")
    public R update(@RequestBody XjMeteSetAuditEntity xjMeteSetAudit){
        ValidatorUtils.validateEntity(xjMeteSetAudit);
        xjMeteSetAuditService.updateAllColumnById(xjMeteSetAudit);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("xj:xjmetesetaudit:delete")
    public R delete(@RequestBody Long[] meteSetAuditIds){
        xjMeteSetAuditService.deleteBatchIds(Arrays.asList(meteSetAuditIds));

        return R.ok();
    }


    /**
     * 提交
     */
    @RequestMapping("/submit")
    public R submit(@RequestBody Long[] meteSetIds){
        List<XjMetaDataSetEntity> list = xjMetaDataSetService.selectBatchIds(Arrays.asList(meteSetIds));
        if(list != null && list.size() > 0){
            List<XjMeteSetAuditEntity> auditList = new ArrayList<>();
            for(XjMetaDataSetEntity setEntity : list){
                setEntity.setReviewState(1);
                XjMeteSetAuditEntity audit = new XjMeteSetAuditEntity();
                audit.setMeteSetId(setEntity.getMeteSetId());
                audit.setOperatType("提交");
                audit.setOperatUserId(getUserId());
                audit.setOperatUserName(getUser().getName());
                audit.setOperatTime(new Date());
                auditList.add(audit);
            }
            xjMetaDataSetService.updateBatchById(list);
            xjMeteSetAuditService.insertBatch(auditList);
        }
        return R.ok();
    }
    /**
     * 撤回
     */
    @RequestMapping("/revoke")
    public R revoke(@RequestParam Long meteSetId){
        XjMetaDataSetEntity metset = xjMetaDataSetService.selectById(meteSetId);
        //获取最后一次提交的时间
        XjMeteSetAuditEntity audit = xjMeteSetAuditService.selectOne(
                new EntityWrapper<XjMeteSetAuditEntity>()
                        .eq("mete_set_id",meteSetId)
                        .eq("operat_type","提交")
                        .orderBy("operat_time",false)
        );
        Date now = new Date(System.currentTimeMillis() - 600000);
        Date submitTime = audit.getOperatTime();
        if (now.before(submitTime)) {
            metset.setReviewState(0);
            xjMetaDataSetService.updateById(metset);
            XjMeteSetAuditEntity auditEntity = new XjMeteSetAuditEntity();
            auditEntity.setMeteSetId(metset.getMeteSetId());
            auditEntity.setOperatType("撤回");
            auditEntity.setOperatUserId(getUserId());
            auditEntity.setOperatUserName(getUser().getName());
            auditEntity.setOperatTime(new Date());
            xjMeteSetAuditService.insert(auditEntity);
            return R.ok("操作成功");
        } else {
            return R.error("提交时间超过限制，不能撤回");
        }
    }
    /**
     * 审核通过
     */
    @RequestMapping("/agree")
    public R agree(@RequestParam Long meteSetId){
        XjMetaDataSetEntity metset = xjMetaDataSetService.selectById(meteSetId);
        metset.setReviewState(2);
        XjMeteSetAuditEntity audit = new XjMeteSetAuditEntity();
        audit.setMeteSetId(metset.getMeteSetId());
        audit.setOperatType("通过");
        audit.setOperatUserId(getUserId());
        audit.setOperatUserName(getUser().getName());
        audit.setOperatTime(new Date());
        xjMetaDataSetService.updateById(metset);
        xjMeteSetAuditService.insert(audit);
        return R.ok();
    }

    /**
     * 退回
     */
    @RequestMapping("/refuse")
    public R refuse(@RequestBody XjMeteSetAuditEntity audit){
        XjMetaDataSetEntity metset = xjMetaDataSetService.selectById(audit.getMeteSetId());
        metset.setReviewState(3);
        audit.setOperatType("退回");
        audit.setOperatUserId(getUserId());
        audit.setOperatUserName(getUser().getName());
        audit.setOperatTime(new Date());
        xjMetaDataSetService.updateById(metset);
        xjMeteSetAuditService.insert(audit);
        return R.ok();
    }

    /**
     * 发布
     */
    @RequestMapping("/push")
    public R push(@RequestParam Long meteSetId){
        XjMetaDataSetEntity metset = xjMetaDataSetService.selectById(meteSetId);
        metset.setPushState(1);
        xjMetaDataSetService.updateById(metset);
        return R.ok();
    }

    /**
     * 停止发布
     */
    @RequestMapping("/stopPush")
    public R stopPush(@RequestParam Long catalogId) {
        XjMetaDataSetEntity metset = xjMetaDataSetService.selectById(catalogId);
        metset.setPushState(0);
        xjMetaDataSetService.updateById(metset);
        return R.ok();
    }

}
