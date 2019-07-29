package io.renren.modules.resource.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import io.renren.common.validator.ValidatorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.resource.entity.ResourceMeteDataEntity;
import io.renren.modules.resource.service.ResourceMeteDataService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 
 *
 * @author wangdehai
 * @email 594340717@qq.com
 * @date 2019-07-25 16:57:52
 */
@RestController
@RequestMapping("resource/resourcemetedata")
public class ResourceMeteDataController {
    @Autowired
    private ResourceMeteDataService resourceMeteDataService;

    /**
     * 列表
     */
    @RequestMapping("/list")
//    @RequiresPermissions("resource:resourcemetedata:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = resourceMeteDataService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{meteId}")
//    @RequiresPermissions("resource:resourcemetedata:info")
    public R info(@PathVariable("meteId") Long meteId){
        ResourceMeteDataEntity resourceMeteData = resourceMeteDataService.selectById(meteId);

        return R.ok().put("resourceMeteData", resourceMeteData);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
//    @RequiresPermissions("resource:resourcemetedata:save")
    public R save(@RequestBody ResourceMeteDataEntity resourceMeteData){
        resourceMeteDataService.insert(resourceMeteData);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
//    @RequiresPermissions("resource:resourcemetedata:update")
    public R update(@RequestBody ResourceMeteDataEntity resourceMeteData){
        ValidatorUtils.validateEntity(resourceMeteData);
        resourceMeteDataService.updateAllColumnById(resourceMeteData);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
//    @RequiresPermissions("resource:resourcemetedata:delete")
    public R delete(@RequestBody Long[] meteIds){
//        resourceMeteDataService.deleteBatchIds(Arrays.asList(meteIds));
        List<ResourceMeteDataEntity> list = new ArrayList<ResourceMeteDataEntity>();
        list = resourceMeteDataService.selectBatchIds(Arrays.asList(meteIds));
        if(list != null && list.size() > 0){
            for(ResourceMeteDataEntity resourceMeteDataEntity : list){
                resourceMeteDataEntity.setIsDeleted(1);
            }
            resourceMeteDataService.updateBatchById(list);
        }
        return R.ok();
    }

}
