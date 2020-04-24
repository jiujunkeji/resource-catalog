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

import io.renren.modules.xj.entity.XjMeteSetMiddleEntity;
import io.renren.modules.xj.service.XjMeteSetMiddleService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 元数据与元数据集的中间表
 *
 * @author wangdehai
 * @email 594340717@qq.com
 * @date 2020-04-24 10:54:31
 */
@RestController
@RequestMapping("xj/xjmetesetmiddle")
public class XjMeteSetMiddleController {
    @Autowired
    private XjMeteSetMiddleService xjMeteSetMiddleService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("xj:xjmetesetmiddle:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = xjMeteSetMiddleService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{versionId}")
    //@RequiresPermissions("xj:xjmetesetmiddle:info")
    public R info(@PathVariable("versionId") Long versionId){
        XjMeteSetMiddleEntity xjMeteSetMiddle = xjMeteSetMiddleService.selectById(versionId);

        return R.ok().put("xjMeteSetMiddle", xjMeteSetMiddle);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("xj:xjmetesetmiddle:save")
    public R save(@RequestBody XjMeteSetMiddleEntity xjMeteSetMiddle){
        xjMeteSetMiddleService.insert(xjMeteSetMiddle);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("xj:xjmetesetmiddle:update")
    public R update(@RequestBody XjMeteSetMiddleEntity xjMeteSetMiddle){
        ValidatorUtils.validateEntity(xjMeteSetMiddle);
        xjMeteSetMiddleService.updateAllColumnById(xjMeteSetMiddle);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("xj:xjmetesetmiddle:delete")
    public R delete(@RequestBody Long[] versionIds){
        xjMeteSetMiddleService.deleteBatchIds(Arrays.asList(versionIds));

        return R.ok();
    }

}
