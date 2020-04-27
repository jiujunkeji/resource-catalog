package io.renren.modules.xj.controller;

import java.io.FileNotFoundException;
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

import io.renren.modules.xj.entity.XjFtpEntity;
import io.renren.modules.xj.service.XjFtpService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 
 *
 * @author wangdehai
 * @email 594340717@qq.com
 * @date 2020-04-27 11:20:21
 */
@RestController
@RequestMapping("resource/xjftp")
public class XjFtpController {
    @Autowired
    private XjFtpService xjFtpService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("resource:xjftp:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = xjFtpService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{ftpId}")
    //@RequiresPermissions("resource:xjftp:info")
    public R info(@PathVariable("ftpId") Integer ftpId){
        XjFtpEntity xjFtp = xjFtpService.selectById(ftpId);

        return R.ok().put("xjFtp", xjFtp);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("resource:xjftp:save")
    public R save(@RequestBody XjFtpEntity xjFtp){
        xjFtp.setFtpCreatetime(new Date());
        xjFtp.setFtpDelete(0);
        xjFtp.setFtpStatus(1);
        xjFtpService.insert(xjFtp);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("resource:xjftp:update")
    public R update(@RequestBody XjFtpEntity xjFtp){
        ValidatorUtils.validateEntity(xjFtp);
        xjFtpService.updateAllColumnById(xjFtp);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("resource:xjftp:delete")
    public R delete(@RequestBody Integer[] ftpIds){
        List<Integer> ftpid =  Arrays.asList(ftpIds);
        for (Integer id: ftpid){
            XjFtpEntity fe = xjFtpService.selectById(id);
            fe.setFtpDelete(1);
        }
        return R.ok();
    }

    /**
     * 上传文件
     */
    @RequestMapping("/upload")
    public R upload(@RequestParam int ftpId) throws FileNotFoundException {
        XjFtpEntity fe = xjFtpService.selectById(ftpId);
        fe.setFtpStatus(2);
        xjFtpService.uploadFile(fe);
        return R.ok();
    }

}
