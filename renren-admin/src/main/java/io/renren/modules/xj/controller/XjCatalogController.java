package io.renren.modules.xj.controller;

import java.util.*;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import io.renren.common.annotation.SysLog;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.sys.controller.AbstractController;
import io.renren.modules.sys.entity.SysDictEntity;
import io.renren.modules.sys.service.SysDictService;
import io.renren.modules.xj.entity.XjMetaDataEntity;
import io.renren.modules.xj.entity.XjSafeEntity;
import io.renren.modules.xj.service.XjMetaDataService;
import io.renren.modules.xj.service.XjSafeService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.xj.entity.XjCatalogEntity;
import io.renren.modules.xj.service.XjCatalogService;
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
    private XjMetaDataService metaDataService;
    @Autowired
    private XjSafeService safeService;
    /**
     * 列表
     */
//    @SysLog(type = "3", content = "目录")
    @RequestMapping("/list")
    public List<XjCatalogEntity> list(@RequestParam Map<String, Object> params){
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
        List<XjMetaDataEntity> meteDataList = new ArrayList<>();
        if(xjCatalog.getMeteSetId() != null && xjCatalog.getMeteSetId() != 0L){
            meteDataList = metaDataService.selectList(new EntityWrapper<XjMetaDataEntity>().eq("mete_set_id",xjCatalog.getMeteSetId()));
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
        xjCatalog.setCreateUserId(getUserId());
        xjCatalog.setCreateUserName(getUser().getName());
        xjCatalog.setCreateTime(new Date());
        xjCatalogService.insert(xjCatalog);
        //设置目录的默认安全级别
        safeService.setDefaultSafe(xjCatalog.getCatalogId());
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("xj:xjcatalog:update")
    public R update(@RequestBody XjCatalogEntity xjCatalog){
        ValidatorUtils.validateEntity(xjCatalog);
        xjCatalogService.updateAllColumnById(xjCatalog);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("xj:xjcatalog:delete")
    public R delete(@RequestBody Long[] catalogIds){
        xjCatalogService.deleteBatchIds(Arrays.asList(catalogIds));

        return R.ok();
    }

}
