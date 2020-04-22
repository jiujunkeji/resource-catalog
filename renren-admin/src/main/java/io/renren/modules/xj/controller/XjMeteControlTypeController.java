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

import io.renren.modules.xj.entity.XjMeteControlTypeEntity;
import io.renren.modules.xj.service.XjMeteControlTypeService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 元数据控件类型表
 *
 * @author wangdehai
 * @email 594340717@qq.com
 * @date 2020-04-22 17:25:12
 */
@RestController
@RequestMapping("xj/xjmetecontroltype")
public class XjMeteControlTypeController {
    @Autowired
    private XjMeteControlTypeService xjMeteControlTypeService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("xj:xjmetecontroltype:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = xjMeteControlTypeService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{controlTypeId}")
    //@RequiresPermissions("xj:xjmetecontroltype:info")
    public R info(@PathVariable("controlTypeId") Long controlTypeId){
        XjMeteControlTypeEntity xjMeteControlType = xjMeteControlTypeService.selectById(controlTypeId);

        return R.ok().put("xjMeteControlType", xjMeteControlType);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("xj:xjmetecontroltype:save")
    public R save(@RequestBody XjMeteControlTypeEntity xjMeteControlType){
        xjMeteControlTypeService.insert(xjMeteControlType);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("xj:xjmetecontroltype:update")
    public R update(@RequestBody XjMeteControlTypeEntity xjMeteControlType){
        ValidatorUtils.validateEntity(xjMeteControlType);
        xjMeteControlTypeService.updateAllColumnById(xjMeteControlType);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("xj:xjmetecontroltype:delete")
    public R delete(@RequestBody Long[] controlTypeIds){
        xjMeteControlTypeService.deleteBatchIds(Arrays.asList(controlTypeIds));

        return R.ok();
    }

}
