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

import io.renren.modules.xj.entity.XjMeteSetVersionEntity;
import io.renren.modules.xj.service.XjMeteSetVersionService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 元数据集变更版本表
 *
 * @author wangdehai
 * @email 594340717@qq.com
 * @date 2020-04-23 17:29:47
 */
@RestController
@RequestMapping("xj/xjmetesetversion")
public class XjMeteSetVersionController {
    @Autowired
    private XjMeteSetVersionService xjMeteSetVersionService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("xj:xjmetesetversion:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = xjMeteSetVersionService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{versionId}")
    //@RequiresPermissions("xj:xjmetesetversion:info")
    public R info(@PathVariable("versionId") Long versionId){
        XjMeteSetVersionEntity xjMeteSetVersion = xjMeteSetVersionService.selectById(versionId);

        return R.ok().put("xjMeteSetVersion", xjMeteSetVersion);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("xj:xjmetesetversion:save")
    public R save(@RequestBody XjMeteSetVersionEntity xjMeteSetVersion){
        xjMeteSetVersionService.insert(xjMeteSetVersion);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("xj:xjmetesetversion:update")
    public R update(@RequestBody XjMeteSetVersionEntity xjMeteSetVersion){
        ValidatorUtils.validateEntity(xjMeteSetVersion);
        xjMeteSetVersionService.updateAllColumnById(xjMeteSetVersion);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("xj:xjmetesetversion:delete")
    public R delete(@RequestBody Long[] versionIds){
        xjMeteSetVersionService.deleteBatchIds(Arrays.asList(versionIds));

        return R.ok();
    }

}
