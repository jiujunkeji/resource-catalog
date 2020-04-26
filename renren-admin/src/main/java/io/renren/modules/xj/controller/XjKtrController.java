package io.renren.modules.xj.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.xj.entity.XjDataSourceEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.xj.entity.XjKtrEntity;
import io.renren.modules.xj.service.XjKtrService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 
 *
 * @author wangdehai
 * @email 594340717@qq.com
 * @date 2020-04-23 14:31:38
 */
@RestController
@RequestMapping("xj/xjktr")
public class XjKtrController {
    @Autowired
    private XjKtrService xjKtrService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("xj:xjktr:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = xjKtrService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{ktrId}")
    //@RequiresPermissions("xj:xjktr:info")
    public R info(@PathVariable("ktrId") Long ktrId){
        XjKtrEntity xjKtr = xjKtrService.selectById(ktrId);

        return R.ok().put("xjKtr", xjKtr);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("xj:xjktr:save")
    public R save(@RequestBody XjKtrEntity xjKtr){
        xjKtr.setKtrCreatetime(new Date());
        xjKtr.setKtrUpdatetime(new Date());
        xjKtrService.insert(xjKtr);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("xj:xjktr:update")
    public R update(@RequestBody XjKtrEntity xjKtr){
        ValidatorUtils.validateEntity(xjKtr);
        xjKtr.setKtrUpdatetime(new Date());
        xjKtrService.updateAllColumnById(xjKtr);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("xj:xjktr:delete")
    public R delete(@RequestBody Long[] ktrIds){
        xjKtrService.deleteBatchIds(Arrays.asList(ktrIds));

        return R.ok();
    }

    /**
     * 任务执行
     */
    @RequestMapping("/run")
    public R extract(@RequestBody XjKtrEntity xjKtr, XjDataSourceEntity ds) throws Exception {
        xjKtrService.kettleJob(xjKtr,ds);
        return R.ok();
    }
}
