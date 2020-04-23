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

import io.renren.modules.xj.entity.XjCatalogAuditEntity;
import io.renren.modules.xj.service.XjCatalogAuditService;
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
@RequestMapping("xj/xjcatalogaudit")
public class XjCatalogAuditController {
    @Autowired
    private XjCatalogAuditService xjCatalogAuditService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("xj:xjcatalogaudit:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = xjCatalogAuditService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{catalogAuditId}")
    //@RequiresPermissions("xj:xjcatalogaudit:info")
    public R info(@PathVariable("catalogAuditId") Long catalogAuditId){
        XjCatalogAuditEntity xjCatalogAudit = xjCatalogAuditService.selectById(catalogAuditId);

        return R.ok().put("xjCatalogAudit", xjCatalogAudit);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("xj:xjcatalogaudit:save")
    public R save(@RequestBody XjCatalogAuditEntity xjCatalogAudit){
        xjCatalogAuditService.insert(xjCatalogAudit);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("xj:xjcatalogaudit:update")
    public R update(@RequestBody XjCatalogAuditEntity xjCatalogAudit){
        ValidatorUtils.validateEntity(xjCatalogAudit);
        xjCatalogAuditService.updateAllColumnById(xjCatalogAudit);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("xj:xjcatalogaudit:delete")
    public R delete(@RequestBody Long[] catalogAuditIds){
        xjCatalogAuditService.deleteBatchIds(Arrays.asList(catalogAuditIds));

        return R.ok();
    }

}
