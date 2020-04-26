package io.renren.modules.xj.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.xj.entity.XjCatalogEntity;
import io.renren.modules.xj.entity.XjMeteSetMiddleEntity;
import io.renren.modules.xj.service.XjCatalogService;
import io.renren.modules.xj.service.XjMeteSetMiddleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.xj.entity.XjCatalogLinkDataEntity;
import io.renren.modules.xj.service.XjCatalogLinkDataService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 
 *
 * @author wangdehai
 * @email 594340717@qq.com
 * @date 2020-04-26 15:14:46
 */
@RestController
@RequestMapping("xj/xjcataloglinkdata")
public class XjCatalogLinkDataController {
    @Autowired
    private XjCatalogLinkDataService xjCatalogLinkDataService;
    @Autowired
    private XjCatalogService catalogService;
    @Autowired
    private XjMeteSetMiddleService meteSetMiddleService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = xjCatalogLinkDataService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{linkId}")
    public R info(@PathVariable("linkId") Long linkId){
        XjCatalogLinkDataEntity xjCatalogLinkData = xjCatalogLinkDataService.selectById(linkId);
        XjCatalogEntity catalog = catalogService.selectById(xjCatalogLinkData.getCatalogId());
        List<XjMeteSetMiddleEntity> list = new ArrayList<>();
        list = meteSetMiddleService.selectList(new EntityWrapper<XjMeteSetMiddleEntity>().eq("mete_set_id",catalog.getMeteSetId()));
        xjCatalogLinkData.setMeteDataList(list);
        return R.ok().put("xjCatalogLinkData", xjCatalogLinkData);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody XjCatalogLinkDataEntity xjCatalogLinkData){
        xjCatalogLinkDataService.insert(xjCatalogLinkData);
        List<XjMeteSetMiddleEntity> meteDataList = new ArrayList<>();
        meteDataList = xjCatalogLinkData.getMeteDataList();
        if(meteDataList != null && meteDataList.size() > 0){
            meteSetMiddleService.updateBatchById(meteDataList);
        }
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody XjCatalogLinkDataEntity xjCatalogLinkData){
        xjCatalogLinkDataService.updateById(xjCatalogLinkData);
        List<XjMeteSetMiddleEntity> meteDataList = new ArrayList<>();
        meteDataList = xjCatalogLinkData.getMeteDataList();
        if(meteDataList != null && meteDataList.size() > 0){
            meteSetMiddleService.updateBatchById(meteDataList);
        }
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] linkIds){
        xjCatalogLinkDataService.deleteBatchIds(Arrays.asList(linkIds));

        return R.ok();
    }

}
