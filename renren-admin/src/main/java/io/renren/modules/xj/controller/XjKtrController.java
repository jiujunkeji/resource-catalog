package io.renren.modules.xj.controller;

import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.xj.entity.XjDataSourceEntity;
import io.renren.modules.xj.service.XjDataSourceService;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


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
    @Autowired
    private XjDataSourceService xjDataSourceService;

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
        XjDataSourceEntity ds = xjDataSourceService.selectById(xjKtr.getKtrDsid());
        xjKtr.setKtrStatus("0");
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
    public R run(@RequestParam int ktrId) throws Exception {
        XjKtrEntity xk = xjKtrService.selectById(ktrId);
        xk.setKtrStatus("1");
        xjKtrService.updateById(xk);
        XjDataSourceEntity ds = xjDataSourceService.selectById(xk.getKtrDsid());
        xjKtrService.kettleJob(xk,ds);
        return R.ok();
    }

    /**
     * 下载采集插件
     */
    @RequestMapping("/downTemplate")
    public void downTemplate(HttpServletResponse response, String ktrName){
        try{
            String pathName = ktrName+".txt";
            String fileName = ktrName+".txt";
            String fn = URLEncoder.encode(fileName,"UTF-8");
            response.setHeader("Content-disposition","attachment;fileName=" + new String(fn.getBytes("UTF-8"),"iso-8859-1").replace(" ","_"));
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            String filePath = "D:\\xj\\"+pathName;
            System.out.println(filePath);
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
}
