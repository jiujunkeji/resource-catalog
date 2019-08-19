package io.renren.modules.resource.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.resource.entity.ResourceCatalogEntity;
import io.renren.modules.resource.service.ResourceCatalogService;
import io.renren.modules.sys.controller.AbstractController;
import io.renren.modules.sys.controller.SysRoleController;
import io.renren.modules.sys.entity.SysRoleEntity;
import io.renren.modules.sys.service.SysRoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.resource.entity.CatalogGrantEntity;
import io.renren.modules.resource.service.CatalogGrantService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 
 *
 * @author wangdehai
 * @email 594340717@qq.com
 * @date 2019-08-19 15:13:13
 */
@RestController
@RequestMapping("resource/cataloggrant")
public class CatalogGrantController extends AbstractController{
    @Autowired
    private CatalogGrantService catalogGrantService;
    @Autowired
    private SysRoleService roleService;
    @Autowired
    private ResourceCatalogService resourceCatalogService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = catalogGrantService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{grantId}")
    public R info(@PathVariable("grantId") Long grantId){
        CatalogGrantEntity catalogGrant = catalogGrantService.selectById(grantId);

        return R.ok().put("catalogGrant", catalogGrant);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody CatalogGrantEntity catalogGrant){
        SysRoleEntity sysRoleEntity = roleService.selectOne(new EntityWrapper<SysRoleEntity>().eq("user_id",catalogGrant.getUserId()));
        catalogGrant.setRoleId(sysRoleEntity.getRoleId());
        catalogGrant.setRoleName(sysRoleEntity.getRoleName());
        catalogGrant.setCreateUserId(getUserId());
        catalogGrant.setCreateTime(new Date());
        catalogGrant.setCatalogName(resourceCatalogService.selectAllCatalogName(catalogGrant.getCatalogId()));
        catalogGrantService.insert(catalogGrant);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody CatalogGrantEntity catalogGrant){
        ValidatorUtils.validateEntity(catalogGrant);
        catalogGrantService.updateAllColumnById(catalogGrant);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] grantIds){
        catalogGrantService.deleteBatchIds(Arrays.asList(grantIds));

        return R.ok();
    }

    /**
     * 停用
     */
    @RequestMapping("/stop")
    public R stop(@RequestParam Long grantId){
        CatalogGrantEntity catalogGrant = catalogGrantService.selectById(grantId);
        if(catalogGrant != null){
            catalogGrant.setIsUsed(0);
            catalogGrantService.updateById(catalogGrant);
            return R.ok();
        }else{
            return R.error("没有找到目标授权");
        }
    }
    /**
     * 启用
     */
    @RequestMapping("/start")
    public R start(@RequestParam Long grantId){
        CatalogGrantEntity catalogGrant = catalogGrantService.selectById(grantId);
        if(catalogGrant != null){
            catalogGrant.setIsUsed(1);
            catalogGrantService.updateById(catalogGrant);
            return R.ok();
        }else{
            return R.error("没有找到目标授权");
        }
    }
}
