package io.renren.modules.xj.controller;

import java.util.*;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import io.renren.common.annotation.SysLog;
import io.renren.modules.resource.entity.MeteCategoryEntity;
import io.renren.modules.resource.service.MeteCategoryService;
import io.renren.modules.sys.controller.AbstractController;
import io.renren.modules.xj.entity.*;
import io.renren.modules.xj.service.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
public class XjCatalogController extends AbstractController{
    @Autowired
    private XjCatalogService xjCatalogService;
    @Autowired
    private XjMeteSetMiddleService meteSetMiddleService;
    @Autowired
    private XjSafeService safeService;
    @Autowired
    private XjCatalogAuditService auditService;
    @Autowired
    private MeteCategoryService meteCategoryService;
    /**
     * 目录列表
     */
    @RequestMapping("/list")
    public List<XjCatalogEntity> list(@RequestParam Map<String, Object> params){
        String name = (String) params.get("name");
        EntityWrapper<XjCatalogEntity> wrapper = new EntityWrapper<XjCatalogEntity>();
        wrapper
                .like(StringUtils.isNotEmpty(name), "name", name)
                .eq("is_deleted",0);
        List<XjCatalogEntity> list = xjCatalogService.selectList(wrapper);
        for(XjCatalogEntity xjCatalogEntity : list){
            XjCatalogEntity parentXjCatalogEntity = xjCatalogService.selectById(xjCatalogEntity.getParentId());
            if(parentXjCatalogEntity != null){
                xjCatalogEntity.setParentName(parentXjCatalogEntity.getCatalogName());
            }
        }
        return list;
    }

    /**
     * 目录分页列表（维护）
     */
//    @SysLog(type = "3", content = "目录")
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params){
        PageUtils page = xjCatalogService.queryPage(params);
        return R.ok().put("page",page);
    }
    /**
     * 获取我的目录列表（安全级别控制）
     */
    @RequestMapping("/myList")
    public List<XjCatalogEntity> myList(@RequestParam Map<String, Object> params){
        String name = (String) params.get("name");
        EntityWrapper<XjCatalogEntity> wrapper = new EntityWrapper<XjCatalogEntity>();
        wrapper
                .like(StringUtils.isNotEmpty(name), "name", name)
                .eq("is_deleted",0);
        List<XjCatalogEntity> list = xjCatalogService.selectUserList(wrapper,getUser());
        for(XjCatalogEntity xjCatalogEntity : list){
            XjCatalogEntity parentXjCatalogEntity = xjCatalogService.selectById(xjCatalogEntity.getParentId());
            if(parentXjCatalogEntity != null){
                xjCatalogEntity.setParentName(parentXjCatalogEntity.getCatalogName());
            }
        }
        return list;
    }
    /**
     * 信息
     */
    @RequestMapping("/info/{catalogId}")
    //@RequiresPermissions("xj:xjcatalog:info")
    public R info(@PathVariable("catalogId") Long catalogId){
        XjCatalogEntity xjCatalog = xjCatalogService.selectById(catalogId);
        XjCatalogEntity parentXjCatalog = xjCatalogService.selectById(xjCatalog.getParentId());
        if(parentXjCatalog != null){
            xjCatalog.setParentName(parentXjCatalog.getCatalogName());
        }
        List<XjMeteSetMiddleEntity> meteDataList = new ArrayList<>();
        if(xjCatalog.getMeteSetId() != null && xjCatalog.getMeteSetId() != 0L){
            meteDataList = meteSetMiddleService.selectList(new EntityWrapper<XjMeteSetMiddleEntity>().eq("mete_set_id",xjCatalog.getMeteSetId()));
            if(meteDataList != null && meteDataList.size() > 0){
                xjCatalog.setMeteDataList(meteDataList);
            }
        }
        return R.ok().put("xjCatalog", xjCatalog);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("xj:xjcatalog:save")
    public R save(@RequestBody XjCatalogEntity xjCatalog){
        MeteCategoryEntity category = meteCategoryService.selectById(xjCatalog.getCategoryId());
        xjCatalog.setCategoryCode(category.getCode());
        xjCatalog.setCategoryName(category.getName());
        xjCatalog.setCreateUserId(getUserId());
        xjCatalog.setCreateUserName(getUser().getName());
        xjCatalog.setCreateTime(new Date());
        xjCatalogService.insert(xjCatalog);
        //设置元数据标识
        String metedataIdentifier = "metadata_ " + xjCatalog.getCategoryId() + "-" + xjCatalog.getCatalogId();
        xjCatalog.setMetedataIdentifier(metedataIdentifier);
        xjCatalogService.updateById(xjCatalog);
        //设置目录的默认安全级别
        safeService.setDefaultSafe(xjCatalog.getCatalogId(),getUser());
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("xj:xjcatalog:update")
    public R update(@RequestBody XjCatalogEntity xjCatalog){
        MeteCategoryEntity category = meteCategoryService.selectById(xjCatalog.getCategoryId());
        xjCatalog.setCategoryCode(category.getCode());
        xjCatalog.setCategoryName(category.getName());
        xjCatalog.setUpdateTime(new Date());
        xjCatalogService.updateById(xjCatalog);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("xj:xjcatalog:delete")
    public R delete(@RequestBody Long[] catalogIds){
        List<Long> catalogIdList = Arrays.asList(catalogIds);
//        xjCatalogService.deleteBatchIds(catalogIdList);
        for(Long catalogId : catalogIdList){
            int count = xjCatalogService.selectCount(new EntityWrapper<XjCatalogEntity>().eq("parent_id",catalogId).eq("is_deleted",0));
            if(count > 0){
                return R.error("请先删除下级目录");
            }
            xjCatalogService.deleteById(catalogId);
            safeService.delete(new EntityWrapper<XjSafeEntity>().eq("catalog_id",catalogId));
            // TODO: 2020-04-24 删除关联的数据连接信息
        }

        return R.ok();
    }

    /**
     * 提交
     */
    @RequestMapping("/submit")
    public R submit(@RequestBody Long[] catalogIds){
        List<XjCatalogEntity> list = xjCatalogService.selectBatchIds(Arrays.asList(catalogIds));
        if(list != null && list.size() > 0){
            List<XjCatalogAuditEntity> auditList = new ArrayList<>();
            for(XjCatalogEntity catalog : list){
                catalog.setReviewState(1);
                XjCatalogAuditEntity audit = new XjCatalogAuditEntity();
                audit.setCatalogId(catalog.getCatalogId());
                audit.setOperatType("提交");
                audit.setOperatUserId(getUserId());
                audit.setOperatUserName(getUser().getName());
                audit.setOperatTime(new Date());
                auditList.add(audit);
            }
            xjCatalogService.updateBatchById(list);
            auditService.insertBatch(auditList);
        }
        return R.ok();
    }
    /**
     * 撤回
     */
    @RequestMapping("/revoke")
    public R revoke(@RequestParam Long catalogId){
        XjCatalogEntity catalog = xjCatalogService.selectById(catalogId);
        //获取最后一次提交的时间
        XjCatalogAuditEntity audit = auditService.selectOne(
                new EntityWrapper<XjCatalogAuditEntity>()
                        .eq("catalog_id",catalogId)
                        .eq("operat_type","提交")
                        .orderBy("operat_time",false)
        );
        Date now = new Date(System.currentTimeMillis() - 600000);
        Date submitTime = audit.getOperatTime();
        if (now.before(submitTime)) {
            catalog.setReviewState(0);
            xjCatalogService.updateById(catalog);
            XjCatalogAuditEntity auditEntity = new XjCatalogAuditEntity();
            auditEntity.setCatalogId(catalog.getCatalogId());
            auditEntity.setOperatType("撤回");
            auditEntity.setOperatUserId(getUserId());
            auditEntity.setOperatUserName(getUser().getName());
            auditEntity.setOperatTime(new Date());
            auditService.insert(auditEntity);
            return R.ok("操作成功");
        } else {
            return R.error("提交时间超过限制，不能撤回");
        }
    }
    /**
     * 审核通过
     */
    @RequestMapping("/agree")
    public R agree(@RequestParam Long catalogId){
        XjCatalogEntity catalog = xjCatalogService.selectById(catalogId);
        catalog.setReviewState(2);
        XjCatalogAuditEntity audit = new XjCatalogAuditEntity();
        audit.setCatalogId(catalog.getCatalogId());
        audit.setOperatType("通过");
        audit.setOperatUserId(getUserId());
        audit.setOperatUserName(getUser().getName());
        audit.setOperatTime(new Date());
        xjCatalogService.updateById(catalog);
        auditService.insert(audit);
        return R.ok();
    }

    /**
     * 退回
     */
    @RequestMapping("/refuse")
    public R refuse(@RequestBody XjCatalogAuditEntity audit){
        XjCatalogEntity catalog = xjCatalogService.selectById(audit.getCatalogId());
        catalog.setReviewState(3);
        audit.setOperatType("退回");
        audit.setOperatUserId(getUserId());
        audit.setOperatUserName(getUser().getName());
        audit.setOperatTime(new Date());
        xjCatalogService.updateById(catalog);
        auditService.insert(audit);
        return R.ok();
    }

    /**
     * 发布
     */
    @RequestMapping("/push")
    public R push(@RequestParam Long catalogId){
        XjCatalogEntity catalog = xjCatalogService.selectById(catalogId);
        catalog.setPushState(1);
        xjCatalogService.updateById(catalog);
        return R.ok();
    }

    /**
     * 停止发布
     */
    @RequestMapping("/stopPush")
    public R stopPush(@RequestParam Long catalogId){
        XjCatalogEntity catalog = xjCatalogService.selectById(catalogId);
        catalog.setPushState(0);
        xjCatalogService.updateById(catalog);
        return R.ok();
    }


    /**
     * 查询数据
     */
    @RequestMapping("/selectDataList")
    public R selectDataList(@RequestParam Map<String, Object> params){
        String catalogId = (String)params.get("catalogId");
        XjCatalogEntity catalog = xjCatalogService.selectById(catalogId);
        catalog.getMeteSetId();
        PageUtils page = xjCatalogService.queryPage(params);
        return R.ok().put("page",page);
    }
}
