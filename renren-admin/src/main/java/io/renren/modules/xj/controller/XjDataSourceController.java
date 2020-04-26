package io.renren.modules.xj.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import io.renren.common.validator.ValidatorUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.xj.entity.XjDataSourceEntity;
import io.renren.modules.xj.service.XjDataSourceService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 
 *
 * @author wangdehai
 * @email 594340717@qq.com
 * @date 2020-04-22 13:31:15
 */
@RestController
@RequestMapping("xj/xjdatasource")
public class XjDataSourceController {
    @Autowired
    private XjDataSourceService xjDataSourceService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("xj:xjdatasource:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = xjDataSourceService.queryPage(params);

        return R.ok().put("page", page);
    }

    @RequestMapping("/list2")
    public R list2(@RequestParam Map<String, Object> params){
        List list = xjDataSourceService.list2(params);

        return R.ok().put("list",list);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{dsId}")
    //@RequiresPermissions("xj:xjdatasource:info")
    public R info(@PathVariable("dsId") Integer dsId){
        XjDataSourceEntity xjDataSource = xjDataSourceService.selectById(dsId);

        return R.ok().put("xjDataSource", xjDataSource);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("xj:xjdatasource:save")
    public R save(@RequestBody XjDataSourceEntity xjDataSource){
        xjDataSource.setDsCreatetime(new Date());
        xjDataSource.setDsUpdatetime(new Date());
        xjDataSourceService.insert(xjDataSource);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("xj:xjdatasource:update")
    public R update(@RequestBody XjDataSourceEntity xjDataSource){
        ValidatorUtils.validateEntity(xjDataSource);
        xjDataSource.setDsUpdatetime(new Date());
        xjDataSourceService.updateAllColumnById(xjDataSource);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("xj:xjdatasource:delete")
    public R delete(@RequestBody Integer[] dsIds){
        xjDataSourceService.deleteBatchIds(Arrays.asList(dsIds));

        return R.ok();
    }

}
