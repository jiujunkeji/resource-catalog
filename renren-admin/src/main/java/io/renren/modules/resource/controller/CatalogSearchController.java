package io.renren.modules.resource.controller;

import java.text.DateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import io.renren.common.validator.ValidatorUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.resource.entity.CatalogSearchEntity;
import io.renren.modules.resource.service.CatalogSearchService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 
 *
 * @author wangdehai
 * @email 594340717@qq.com
 * @date 2019-08-22 11:40:18
 */
@RestController
@RequestMapping("resource/catalogsearch")
public class CatalogSearchController {
    @Autowired
    private CatalogSearchService catalogSearchService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("resource:catalogsearch:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = catalogSearchService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("resource:catalogsearch:info")
    public R info(@PathVariable("id") Integer id){
        CatalogSearchEntity catalogSearch = catalogSearchService.selectById(id);

        return R.ok().put("catalogSearch", catalogSearch);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("resource:catalogsearch:save")
    public R save(@RequestBody CatalogSearchEntity catalogSearch){
        catalogSearchService.insert(catalogSearch);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("resource:catalogsearch:update")
    public R update(@RequestBody CatalogSearchEntity catalogSearch){
        ValidatorUtils.validateEntity(catalogSearch);
        catalogSearchService.updateAllColumnById(catalogSearch);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("resource:catalogsearch:delete")
    public R delete(@RequestBody Integer[] ids){
        catalogSearchService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

    /**
     * 访问统计
     */
    @RequestMapping("/stat")
    public R stat(){
        Date date = new Date();
        DateFormat df = DateFormat.getDateInstance();
        String date2 = df.format(date);
        int callAll = catalogSearchService.selectCount(new EntityWrapper<CatalogSearchEntity>());
        int callToday = catalogSearchService.selectCount(new EntityWrapper<CatalogSearchEntity>().like("search_date", date2));

        return R.ok().put("callAll",callAll).put("callToday",callToday);
    }

}
