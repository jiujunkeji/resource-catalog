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

import io.renren.modules.xj.entity.XjMeteSetMiddleVersionEntity;
import io.renren.modules.xj.service.XjMeteSetMiddleVersionService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 元数据与元数据集的中间表的版本表
 *
 * @author wangdehai
 * @email 594340717@qq.com
 * @date 2020-04-24 10:54:31
 */
@RestController
@RequestMapping("xj/xjmetesetmiddleversion")
public class XjMeteSetMiddleVersionController {
    @Autowired
    private XjMeteSetMiddleVersionService xjMeteSetMiddleVersionService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("xj:xjmetesetmiddleversion:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = xjMeteSetMiddleVersionService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{versionId}")
    //@RequiresPermissions("xj:xjmetesetmiddleversion:info")
    public R info(@PathVariable("versionId") Long versionId){
        XjMeteSetMiddleVersionEntity xjMeteSetMiddleVersion = xjMeteSetMiddleVersionService.selectById(versionId);

        return R.ok().put("xjMeteSetMiddleVersion", xjMeteSetMiddleVersion);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("xj:xjmetesetmiddleversion:save")
    public R save(@RequestBody XjMeteSetMiddleVersionEntity xjMeteSetMiddleVersion){
        xjMeteSetMiddleVersionService.insert(xjMeteSetMiddleVersion);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("xj:xjmetesetmiddleversion:update")
    public R update(@RequestBody XjMeteSetMiddleVersionEntity xjMeteSetMiddleVersion){
        ValidatorUtils.validateEntity(xjMeteSetMiddleVersion);
        xjMeteSetMiddleVersionService.updateAllColumnById(xjMeteSetMiddleVersion);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("xj:xjmetesetmiddleversion:delete")
    public R delete(@RequestBody Long[] versionIds){
        xjMeteSetMiddleVersionService.deleteBatchIds(Arrays.asList(versionIds));

        return R.ok();
    }

}
