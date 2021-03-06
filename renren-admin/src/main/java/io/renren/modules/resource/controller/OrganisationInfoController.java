package io.renren.modules.resource.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.resource.entity.OrganisationInfoEntity;
import io.renren.modules.resource.service.OrganisationInfoService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;


/**
 * 
 *
 * @author wangdehai
 * @email 594340717@qq.com
 * @date 2019-07-25 16:57:52
 */
@RestController
@RequestMapping("resource/organisationinfo")
public class OrganisationInfoController {
    @Autowired
    private OrganisationInfoService organisationInfoService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("resource:organisationinfo:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = organisationInfoService.queryPage(params);

        return R.ok().put("page", page);
    }
    /**
     * 列表
     */
    @RequestMapping("/selectList")
    //@RequiresPermissions("resource:organisationinfo:list")
    public List<OrganisationInfoEntity> selectList(){
        List<OrganisationInfoEntity> list = organisationInfoService.selectList(null);

        return list;
    }
    /**
     * select列表
     */
    @RequestMapping("/select")
    public R select(@RequestParam Map<String, Object> params){
        List<OrganisationInfoEntity> list = organisationInfoService.selectList(null);

        return R.ok().put("list", list);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{organisationId}")
    //@RequiresPermissions("resource:organisationinfo:info")
    public R info(@PathVariable("organisationId") Long organisationId){
        OrganisationInfoEntity organisationInfo = organisationInfoService.selectById(organisationId);

        return R.ok().put("organisationInfo", organisationInfo);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("resource:organisationinfo:save")
    public R save(@RequestBody OrganisationInfoEntity organisationInfo){
        String key = getRandomString(20);
        String secret = getRandomString(20);
        organisationInfo.setAccessKey(key);
        organisationInfo.setAccessSecret(secret);
        organisationInfoService.insert(organisationInfo);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("resource:organisationinfo:update")
    public R update(@RequestBody OrganisationInfoEntity organisationInfo){
        ValidatorUtils.validateEntity(organisationInfo);
        organisationInfoService.updateAllColumnById(organisationInfo);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("resource:organisationinfo:delete")
    public R delete(@RequestBody Long[] organisationIds){
        organisationInfoService.deleteBatchIds(Arrays.asList(organisationIds));

        return R.ok();
    }

    public static String getRandomString(int length){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<length;i++){
            int number=random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }
}
