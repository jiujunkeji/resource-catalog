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

import io.renren.modules.xj.entity.XjMetaDataSetEntity;
import io.renren.modules.xj.service.XjMetaDataSetService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 元数据集表
 *
 * @author wangdehai
 * @email 594340717@qq.com
 * @date 2020-04-22 15:46:35
 */
@RestController
@RequestMapping("xj/xjmetadataset")
public class XjMetaDataSetController {
    @Autowired
    private XjMetaDataSetService xjMetaDataSetService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("xj:xjmetadataset:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = xjMetaDataSetService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{meteSetId}")
    //@RequiresPermissions("xj:xjmetadataset:info")
    public R info(@PathVariable("meteSetId") Long meteSetId){
        XjMetaDataSetEntity xjMetaDataSet = xjMetaDataSetService.selectById(meteSetId);

        return R.ok().put("xjMetaDataSet", xjMetaDataSet);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("xj:xjmetadataset:save")
    public R save(@RequestBody XjMetaDataSetEntity xjMetaDataSet){
        xjMetaDataSetService.insert(xjMetaDataSet);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("xj:xjmetadataset:update")
    public R update(@RequestBody XjMetaDataSetEntity xjMetaDataSet){
        ValidatorUtils.validateEntity(xjMetaDataSet);
        xjMetaDataSetService.updateAllColumnById(xjMetaDataSet);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("xj:xjmetadataset:delete")
    public R delete(@RequestBody Long[] meteSetIds){
        xjMetaDataSetService.deleteBatchIds(Arrays.asList(meteSetIds));

        return R.ok();
    }

}
