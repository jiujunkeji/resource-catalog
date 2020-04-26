package io.renren.modules.xj.controller;

import java.util.*;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.sys.controller.AbstractController;
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

import io.renren.modules.xj.entity.XjSafeEntity;
import io.renren.modules.xj.service.XjSafeService;
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
@RequestMapping("xj/xjsafe")
public class XjSafeController extends AbstractController{
    @Autowired
    private XjSafeService xjSafeService;
    @Autowired
    private XjCatalogService catalogService;
    @Autowired
    private XjMeteSetMiddleService meteSetMiddleService;
    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("xj:xjsafe:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = xjSafeService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 查询单条目录的安全信息
     */
    @RequestMapping("/getInfo/{catalogId}")
    //@RequiresPermissions("xj:xjsafe:info")
    public R getInfo(@PathVariable("catalogId") Long catalogId){
        XjSafeEntity xjSafe = xjSafeService.selectOne(
                new EntityWrapper<XjSafeEntity>().eq("catalog_id",catalogId)
        );
        if(xjSafe != null){
            return R.ok().put("xjSafe", xjSafe);
        }else{
            return R.ok().put("xjSafe",xjSafeService.setDefaultSafe(catalogId,getUser()));
        }
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{safeId}")
    //@RequiresPermissions("xj:xjsafe:info")
    public R info(@PathVariable("safeId") Long safeId){
        XjSafeEntity xjSafe = xjSafeService.selectById(safeId);
        XjCatalogEntity catalog = catalogService.selectById(xjSafe.getCatalogId());
        List<XjMeteSetMiddleEntity> list = new ArrayList<>();
        list = meteSetMiddleService.selectList(new EntityWrapper<XjMeteSetMiddleEntity>().eq("mete_set_id",catalog.getMeteSetId()));
        xjSafe.setMeteDataList(list);
        return R.ok().put("xjSafe", xjSafe);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("xj:xjsafe:save")
    public R save(@RequestBody XjSafeEntity xjSafe){
        int count = xjSafeService.selectCount(
                new EntityWrapper<XjSafeEntity>().eq("catalog_id",xjSafe.getCatalogId())
        );
        if(count > 0){
            return R.error("该条目录已有安全级别");
        }
        //判断安全级别是否小于上级
        XjCatalogEntity catalog = catalogService.selectById(xjSafe.getCatalogId());
        if(catalog.getParentId() != null && catalog.getParentId() != 0L){
            XjSafeEntity parentSafe = xjSafeService.selectOne(
                    new EntityWrapper<XjSafeEntity>().eq("catalog_id",catalog.getParentId())
            );
            if(parentSafe != null && parentSafe.getSafe() != null){
                if(xjSafe.getSafeCode() > parentSafe.getSafeCode()){
                    return R.error("安全级别不能低于上级目录");
                }
            }
        }
        //设置创建人
        xjSafe.setCreateUserId(getUserId());
        xjSafe.setCreateUser(getUser().getName());
        xjSafe.setCreateTime(new Date());
        xjSafeService.insert(xjSafe);
        List<XjMeteSetMiddleEntity> meteDataList = new ArrayList<>();
        meteDataList = xjSafe.getMeteDataList();
        if(meteDataList != null && meteDataList.size() > 0){
            meteSetMiddleService.updateBatchById(meteDataList);
        }
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("xj:xjsafe:update")
    public R update(@RequestBody XjSafeEntity xjSafe){
        //判断安全级别是否小于上级
        XjCatalogEntity catalog = catalogService.selectById(xjSafe.getCatalogId());
        if(catalog.getParentId() != null && catalog.getParentId() != 0L){
            XjSafeEntity parentSafe = xjSafeService.selectOne(
                    new EntityWrapper<XjSafeEntity>().eq("catalog_id",catalog.getParentId())
            );
            if(parentSafe != null && parentSafe.getSafe() != null){
                if(xjSafe.getSafeCode() > parentSafe.getSafeCode()){
                    return R.error("安全级别不能低于上级目录");
                }
            }else{
                return R.error("请先设置上级目录安全等级");
            }
        }

        //设置创建人
        xjSafe.setUpdateUserId(getUserId());
        xjSafe.setUpdateUser(getUser().getName());
        xjSafe.setUpdateTime(new Date());
        xjSafeService.updateById(xjSafe);
        List<XjMeteSetMiddleEntity> meteDataList = new ArrayList<>();
        meteDataList = xjSafe.getMeteDataList();
        if(meteDataList != null && meteDataList.size() > 0){
            meteSetMiddleService.updateBatchById(meteDataList);
        }
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("xj:xjsafe:delete")
    public R delete(@RequestBody Long[] safeIds){
        xjSafeService.deleteBatchIds(Arrays.asList(safeIds));
        return R.ok();
    }

}
