package io.renren.modules.xj.controller;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import io.renren.common.annotation.SysLog;
import io.renren.common.utils.QueryPage;
import io.renren.modules.resource.entity.MeteCategoryEntity;
import io.renren.modules.resource.service.MeteCategoryService;
import io.renren.modules.sys.controller.AbstractController;
import io.renren.modules.xj.dto.CountDTO;
import io.renren.modules.xj.dto.MeteCategoryDto;
import io.renren.modules.xj.entity.*;
import io.renren.modules.xj.service.*;
import io.renren.modules.xj.utils.Config;
import io.renren.modules.xj.utils.JDBCUtils;
import net.sf.json.JSONArray;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    private XjMeteSetMiddleService meteSetMiddleService;
    @Autowired
    private XjSafeService safeService;
    @Autowired
    private XjCatalogAuditService auditService;
    @Autowired
    private MeteCategoryService meteCategoryService;
    @Autowired
    private XjCatalogLinkDataService linkDataService;
    @Autowired
    private XjDataSourceService dataSourceService;
    @Autowired
    private XjMetaDataSetService meteSetService;
    @Autowired
    private XjMetaDataService metaDataService;
    @Autowired
    private XjMeteCategoryService xjMeteCategoryService;
    @Autowired
    private XjMonitorService xjMonitorService;
    @Autowired
    private XjKlogService xjKlogService;
    /**
     * 目录列表
     */
    @RequestMapping("/list")
    public List<XjCatalogEntity> list(@RequestParam Map<String, Object> params){
        String name = (String) params.get("name");
        EntityWrapper<XjCatalogEntity> wrapper = new EntityWrapper<XjCatalogEntity>();
        wrapper
                .like(StringUtils.isNotEmpty(name), "name", name)
                .eq("is_deleted",0);
        List<XjCatalogEntity> list = xjCatalogService.selectList(wrapper);
        for(XjCatalogEntity xjCatalogEntity : list){
            XjCatalogEntity parentXjCatalogEntity = xjCatalogService.selectById(xjCatalogEntity.getParentId());
            if(parentXjCatalogEntity != null){
                xjCatalogEntity.setParentName(parentXjCatalogEntity.getCatalogName());
            }
        }
        return list;
    }

    /**
     * 目录分页列表（维护）
     */
//    @SysLog(type = "3", content = "目录")
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params){
        PageUtils page = xjCatalogService.queryPage(params);
        return R.ok().put("page",page);
    }
    /**
     * 目录检索，获取我的目录列表（安全级别控制）
     */
    @RequestMapping("/myList")
    public List<XjCatalogEntity> myList(@RequestParam Map<String, Object> params){
        String name = (String) params.get("name");
        EntityWrapper<XjCatalogEntity> wrapper = new EntityWrapper<XjCatalogEntity>();
        wrapper
                .like(StringUtils.isNotEmpty(name), "name", name)
                .eq("is_deleted",0)
                .eq("review_state",2)
                .eq("push_state",1);
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
        List<XjMeteSetMiddleEntity> meteDataList = new ArrayList<>();
        if(xjCatalog.getMeteSetId() != null && xjCatalog.getMeteSetId() != 0L){
            XjMetaDataSetEntity metaDataSet = meteSetService.selectById(xjCatalog.getMeteSetId());
            xjCatalog.setMeteSetName(metaDataSet.getCnName());
            meteDataList = meteSetMiddleService.selectList(new EntityWrapper<XjMeteSetMiddleEntity>().eq("mete_set_id",xjCatalog.getMeteSetId()));
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
        MeteCategoryEntity category = meteCategoryService.selectById(xjCatalog.getCategoryId());
        xjCatalog.setCategoryCode(category.getCode());
        xjCatalog.setCategoryName(category.getName());
        xjCatalog.setCreateUserId(getUserId());
        xjCatalog.setDatasourceType(null);
        xjCatalog.setCreateUserName(getUser().getName());
        xjCatalog.setCreateTime(new Date());
        xjCatalogService.insert(xjCatalog);
        //设置元数据标识
        String metedataIdentifier = "metadata_ " + xjCatalog.getCategoryId() + "-" + xjCatalog.getCatalogId();
        xjCatalog.setMetedataIdentifier(metedataIdentifier);
        xjCatalogService.updateById(xjCatalog);
        //设置目录的默认安全级别
        safeService.setDefaultSafe(xjCatalog.getCatalogId(),getUser());
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("xj:xjcatalog:update")
    public R update(@RequestBody XjCatalogEntity xjCatalog){
        MeteCategoryEntity category = meteCategoryService.selectById(xjCatalog.getCategoryId());
        xjCatalog.setCategoryCode(category.getCode());
        xjCatalog.setCategoryName(category.getName());
        xjCatalog.setUpdateTime(new Date());
        xjCatalogService.updateById(xjCatalog);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("xj:xjcatalog:delete")
    public R delete(@RequestBody Long[] catalogIds){
        List<Long> catalogIdList = Arrays.asList(catalogIds);
//        xjCatalogService.deleteBatchIds(catalogIdList);
        for(Long catalogId : catalogIdList){
            int count = xjCatalogService.selectCount(new EntityWrapper<XjCatalogEntity>().eq("parent_id",catalogId).eq("is_deleted",0));
            if(count > 0){
                return R.error("请先删除下级目录");
            }
            xjCatalogService.deleteById(catalogId);
            safeService.delete(new EntityWrapper<XjSafeEntity>().eq("catalog_id",catalogId));
            //删除关联的数据连接信息
            linkDataService.delete(new EntityWrapper<XjCatalogLinkDataEntity>().eq("catalog_id",catalogId));
        }

        return R.ok();
    }

    /**
     * 提交
     */
    @RequestMapping("/submit")
    public R submit(@RequestBody Long[] catalogIds){
        List<XjCatalogEntity> list = xjCatalogService.selectBatchIds(Arrays.asList(catalogIds));
        if(list != null && list.size() > 0){
            List<XjCatalogAuditEntity> auditList = new ArrayList<>();
            for(XjCatalogEntity catalog : list){
                catalog.setReviewState(1);
                XjCatalogAuditEntity audit = new XjCatalogAuditEntity();
                audit.setCatalogId(catalog.getCatalogId());
                audit.setOperatType("提交");
                audit.setOperatUserId(getUserId());
                audit.setOperatUserName(getUser().getName());
                audit.setOperatTime(new Date());
                auditList.add(audit);
            }
            xjCatalogService.updateBatchById(list);
            auditService.insertBatch(auditList);
        }
        return R.ok();
    }
    /**
     * 撤回
     */
    @RequestMapping("/revoke")
    public R revoke(@RequestParam Long catalogId){
        XjCatalogEntity catalog = xjCatalogService.selectById(catalogId);
        //获取最后一次提交的时间
        XjCatalogAuditEntity audit = auditService.selectOne(
                new EntityWrapper<XjCatalogAuditEntity>()
                        .eq("catalog_id",catalogId)
                        .eq("operat_type","提交")
                        .orderBy("operat_time",false)
        );
        Date now = new Date(System.currentTimeMillis() - 600000);
        Date submitTime = audit.getOperatTime();
        if (now.before(submitTime)) {
            catalog.setReviewState(0);
            xjCatalogService.updateById(catalog);
            XjCatalogAuditEntity auditEntity = new XjCatalogAuditEntity();
            auditEntity.setCatalogId(catalog.getCatalogId());
            auditEntity.setOperatType("撤回");
            auditEntity.setOperatUserId(getUserId());
            auditEntity.setOperatUserName(getUser().getName());
            auditEntity.setOperatTime(new Date());
            auditService.insert(auditEntity);
            return R.ok("操作成功");
        } else {
            return R.error("提交时间超过限制，不能撤回");
        }
    }
    /**
     * 审核通过
     */
    @RequestMapping("/agree")
    public R agree(@RequestParam Long catalogId){
        XjCatalogEntity catalog = xjCatalogService.selectById(catalogId);
        catalog.setReviewState(2);
        XjCatalogAuditEntity audit = new XjCatalogAuditEntity();
        audit.setCatalogId(catalog.getCatalogId());
        audit.setOperatType("通过");
        audit.setOperatUserId(getUserId());
        audit.setOperatUserName(getUser().getName());
        audit.setOperatTime(new Date());
        xjCatalogService.updateById(catalog);
        auditService.insert(audit);
        return R.ok();
    }

    /**
     * 退回
     */
    @RequestMapping("/refuse")
    public R refuse(@RequestBody XjCatalogAuditEntity audit){
        XjCatalogEntity catalog = xjCatalogService.selectById(audit.getCatalogId());
        catalog.setReviewState(3);
        audit.setOperatType("退回");
        audit.setOperatUserId(getUserId());
        audit.setOperatUserName(getUser().getName());
        audit.setOperatTime(new Date());
        xjCatalogService.updateById(catalog);
        auditService.insert(audit);
        return R.ok();
    }

    /**
     * 发布
     */
    @RequestMapping("/push")
    public R push(@RequestParam Long catalogId){
        XjCatalogEntity catalog = xjCatalogService.selectById(catalogId);
        catalog.setPushState(1);
        xjCatalogService.updateById(catalog);
        return R.ok();
    }

    /**
     * 停止发布
     */
    @RequestMapping("/stopPush")
    public R stopPush(@RequestParam Long catalogId){
        XjCatalogEntity catalog = xjCatalogService.selectById(catalogId);
        catalog.setPushState(0);
        xjCatalogService.updateById(catalog);
        return R.ok();
    }


    /**
     * 查询数据
     */
    @RequestMapping("/selectDataList")
    public R selectDataList(@RequestParam Map<String, Object> params){
        String catalogId = (String)params.get("catalogId");
        XjCatalogEntity catalog = xjCatalogService.selectById(catalogId);
        XjCatalogLinkDataEntity link = linkDataService.selectOne(new EntityWrapper<XjCatalogLinkDataEntity>().eq("catalog_id",catalogId));
        if(link == null){
            return R.error("该目录进行数据关联");
        }
        XjDataSourceEntity dataSource = dataSourceService.selectById(link.getDataSourceId());
        if(catalog.getMeteSetId() != null){
            List<XjMeteSetMiddleEntity> meteDataList = new ArrayList<>();
            meteDataList = meteSetMiddleService.selectList(new EntityWrapper<XjMeteSetMiddleEntity>().eq("mete_set_id",catalog.getMeteSetId()));
            if(meteDataList != null && meteDataList.size() > 0){
                //当前用户安全等级
                int userSafeCode = getUser().getSafeCode();
                StringBuffer sqlBuf =   new StringBuffer();
                sqlBuf.append("SELECT ");
                for(XjMeteSetMiddleEntity mete : meteDataList){
                    //字段安全等级
                    Integer fieldSafeCode = mete.getSafeCode();
                    if(fieldSafeCode != null && fieldSafeCode < userSafeCode){
                        sqlBuf.append("'******' AS ").append(mete.getMeteEname()).append(", ");
                    }else{
                        if(StringUtils.isBlank(mete.getField())){
                            sqlBuf.append(mete.getMeteEname()).append(", ");
                        }else{
                            sqlBuf.append(mete.getField()).append(" AS ").append(mete.getMeteEname()).append(", ");
                        }
                    }

                }
                String sql = sqlBuf.substring(0,sqlBuf.length() - 2);
                sql = sql + " FROM " + link.getTableName();
                String countSql = "SELECT COUNT(*) FROM " + link.getTableName();
                Config config = new Config();
                config.setUsername(dataSource.getDsUsername());
                config.setPassword(dataSource.getDsPassword());
                if(dataSource.getDsType().toLowerCase().contains("mysql")){
                    config.setUrl("jdbc:mysql://" + dataSource.getDsIp() + ":" + dataSource.getDsPort() + "/" + dataSource.getDsDatabasename());
                    config.setDriver("com.mysql.jdbc.Driver");
                }else if(dataSource.getDsType().toLowerCase().contains("gbase")){
                    config.setUrl("jdbc:gbase://" + dataSource.getDsIp() + ":" + dataSource.getDsPort() + "/" + dataSource.getDsDatabasename());
                    config.setDriver("com.gbase.jdbc.Driver");
                }else{
                    config.setUrl("jdbc:gbase://" + dataSource.getDsIp() + ":" + dataSource.getDsPort() + "/" + dataSource.getDsDatabasename());
                    config.setDriver("com.gbase.jdbc.Driver");
                }
                JSONArray jsonArray = null;
                try {
                    jsonArray = JDBCUtils.queryJsonArray(Integer.parseInt(params.get("page").toString()),0,config,sql,null);
                    int count = JDBCUtils.queryCount(countSql,config,null);
                    QueryPage page = new QueryPage();
                    page.setHeaderList(meteDataList);
                    page.setDataList(jsonArray);
                    page.setCurrPage(Integer.parseInt(params.get("page").toString()));
                    page.setTotalCount(count);
                    return R.ok().put("page",page);
                } catch (SQLException e) {
                    e.printStackTrace();
                    return R.error("查询失败");
                }
            }else{
                return R.error("该目录未关联元数据");
            }
        }else{
            return R.error("该目录未关联元数据集");
        }
    }

    /**
     * 查询数据
     */
    @RequestMapping("/count1")
    public R count1(){
        CountDTO countDTO = new CountDTO();
        int meteDataCount = metaDataService.selectCount(null);
        countDTO.setMeteDataCount(meteDataCount);
        int meteCategoryCount = xjMeteCategoryService.selectCount(null);
        countDTO.setMeteCategoryCount(meteCategoryCount);
        int catalogCount = xjCatalogService.selectCount(new EntityWrapper<XjCatalogEntity>().eq("is_deleted",0));
        countDTO.setCatalogCount(catalogCount);
        //计算日增长量
        long current=System.currentTimeMillis();    //当前时间毫秒数
        long zeroT=current/(1000*3600*24)*(1000*3600*24)- TimeZone.getDefault().getRawOffset();  //今天零点零分零秒的毫秒数
        //当天零点
        String zero = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(zeroT);
        int catalogDayChange = xjCatalogService.selectCount(
                new EntityWrapper<XjCatalogEntity>()
                        .eq("is_deleted",0)
                        .ge("create_time", zero)
        );
        countDTO.setCatalogDayChange(catalogDayChange);

        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONDAY), calendar.get(Calendar.DAY_OF_MONTH), 0, 0,0);
        calendar.set(Calendar.DAY_OF_MONTH,calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        long tt = calendar.getTime().getTime();
        //本月第一天零点
        String monDay = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(tt);
        int catalogMonChange = xjCatalogService.selectCount(
                new EntityWrapper<XjCatalogEntity>()
                        .eq("is_deleted",0)
                        .ge("create_time", monDay)
        );
        countDTO.setCatalogMonChange(catalogMonChange);
        int monitorCount = xjMonitorService.selectCount(null);
        countDTO.setMonitorCount(monitorCount);
        int monitorDayCount = xjMonitorService.selectCount(new EntityWrapper<XjMonitorEntity>().ge("create_time",zero));
        countDTO.setMonitorDayCount(monitorDayCount);
        int monitorDataCount = xjKlogService.sum(null);
        countDTO.setMonitorDataCount(new BigDecimal(monitorDataCount));
        int monitorDataDayCount = xjKlogService.sum(zero);
        countDTO.setMonitorDataDayCount(new BigDecimal(monitorDataDayCount));
        return R.ok().put("countDTO",countDTO);
    }

    /**
     * 查询数据
     */
    @RequestMapping("/count2")
    public R count2(){
        List<MeteCategoryDto> list = new ArrayList<>();
        list = metaDataService.selectMeteByCategory();
        return R.ok().put("list",list);
    }
}
