package io.renren.modules.resource.controller;

import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import io.renren.common.validator.ValidatorUtils;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.resource.entity.ResourceCatalogEntity;
import io.renren.modules.resource.service.ResourceCatalogService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;

import javax.servlet.http.HttpServletResponse;


/**
 * 
 *
 * @author wangdehai
 * @email 594340717@qq.com
 * @date 2019-07-25 16:57:52
 */
@RestController
@RequestMapping("resource/resourcecatalog")
public class ResourceCatalogController {
    @Autowired
    private ResourceCatalogService resourceCatalogService;

    /**
     * 列表
     */
    @RequestMapping("/list")
//    @RequiresPermissions("resource:resourcecatalog:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = resourceCatalogService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{catalogId}")
//    @RequiresPermissions("resource:resourcecatalog:info")
    public R info(@PathVariable("catalogId") Long catalogId){
        ResourceCatalogEntity resourceCatalog = resourceCatalogService.selectById(catalogId);

        return R.ok().put("resourceCatalog", resourceCatalog);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
//    @RequiresPermissions("resource:resourcecatalog:save")
    public R save(@RequestBody ResourceCatalogEntity resourceCatalog){
        resourceCatalogService.insert(resourceCatalog);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
//    @RequiresPermissions("resource:resourcecatalog:update")
    public R update(@RequestBody ResourceCatalogEntity resourceCatalog){
        ValidatorUtils.validateEntity(resourceCatalog);
        resourceCatalogService.updateAllColumnById(resourceCatalog);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
//    @RequiresPermissions("resource:resourcecatalog:delete")
    public R delete(@RequestBody Long[] catalogIds){
//        resourceCatalogService.deleteBatchIds(Arrays.asList(catalogIds));
        List<ResourceCatalogEntity> list = new ArrayList<ResourceCatalogEntity>();
        list = resourceCatalogService.selectBatchIds(Arrays.asList(catalogIds));
        if(list != null && list.size() > 0){
            for(ResourceCatalogEntity resourceCatalogEntity : list){
                resourceCatalogEntity.setIsDeleted(1);
            }
            resourceCatalogService.updateBatchById(list);
        }
        return R.ok();
    }

    /**
     * 下载目录模板
     */
    @RequestMapping("/downTemplate")
    public void downTemplate(HttpServletResponse response, HttpServletRequest request){
        try{
            String pathName = "catalogTemplate.xlsx";
            String fileName = "资源目录导入模板.xlsx";
            String fn = URLEncoder.encode(fileName,"UTF-8");
            response.setHeader("Content-disposition","attachment;fileName=" + new String(fn.getBytes("UTF-8"),"iso-8859-1").replace(" ","_"));
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            String filePath = getClass().getClassLoader().getResource("TAB1/" + pathName).getPath();
            FileInputStream input = new FileInputStream(filePath);
            OutputStream out = response.getOutputStream();
            byte[] b = new byte[2048];
            int len;
            while((len = input.read(b)) != -1){
                out.write(b,0,len);
            }
            response.setHeader("Conten-Length",String.valueOf(input.getChannel().size()));
        }catch (Exception ex){
            System.out.println(ex);
        }
    }
    /**
     * 停用
     */
    @RequestMapping("/stop")
    public R stop(@RequestParam Long catalogId){
        ResourceCatalogEntity resourceCatalogEntity = resourceCatalogService.selectById(catalogId);
        if(resourceCatalogEntity != null){
            resourceCatalogEntity.setIsUsed(0);
            resourceCatalogService.updateById(resourceCatalogEntity);
            return R.ok();
        }else{
            return R.error("没有找到目标目录");
        }
    }

    /**
     * 启用
     */
    @RequestMapping("/start")
    public R start(@RequestParam Long catalogId){
        ResourceCatalogEntity resourceCatalogEntity = resourceCatalogService.selectById(catalogId);
        if(resourceCatalogEntity != null){
            resourceCatalogEntity.setIsUsed(1);
            resourceCatalogService.updateById(resourceCatalogEntity);
            return R.ok();
        }else{
            return R.error("没有找到目标目录");
        }
    }
}
