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

import io.renren.modules.xj.entity.XjMetaDataEntity;
import io.renren.modules.xj.service.XjMetaDataService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 元数据表
 *
 * @author wangdehai
 * @email 594340717@qq.com
 * @date 2020-04-22 15:46:35
 */
@RestController
@RequestMapping("xj/xjmetadata")
public class XjMetaDataController {
    @Autowired
    private XjMetaDataService xjMetaDataService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("xj:xjmetadata:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = xjMetaDataService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{meteId}")
    //@RequiresPermissions("xj:xjmetadata:info")
    public R info(@PathVariable("meteId") Long meteId){
        XjMetaDataEntity xjMetaData = xjMetaDataService.selectById(meteId);

        return R.ok().put("xjMetaData", xjMetaData);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("xj:xjmetadata:save")
    public R save(@RequestBody XjMetaDataEntity xjMetaData){
        xjMetaDataService.insert(xjMetaData);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("xj:xjmetadata:update")
    public R update(@RequestBody XjMetaDataEntity xjMetaData){
        ValidatorUtils.validateEntity(xjMetaData);
        xjMetaDataService.updateAllColumnById(xjMetaData);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("xj:xjmetadata:delete")
    public R delete(@RequestBody Long[] meteIds){
        xjMetaDataService.deleteBatchIds(Arrays.asList(meteIds));

        return R.ok();
    }

}
