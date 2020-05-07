package io.renren.modules.xj.service.impl;

import io.renren.modules.xj.entity.XjChildMonitorEntity;
import io.renren.modules.xj.entity.XjKlogEntity;
import io.renren.modules.xj.service.XjChildMonitorService;
import io.renren.modules.xj.service.XjKlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.xj.dao.XjMonitorDao;
import io.renren.modules.xj.entity.XjMonitorEntity;
import io.renren.modules.xj.service.XjMonitorService;


@Service("xjMonitorService")
public class XjMonitorServiceImpl extends ServiceImpl<XjMonitorDao, XjMonitorEntity> implements XjMonitorService {

    @Autowired
    private XjChildMonitorService xjChildMonitorService;
    @Autowired
    private XjKlogService xjKlogService;
    @Autowired
    private XjMonitorService xjMonitorService;


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<XjMonitorEntity> page = this.selectPage(
                new Query<XjMonitorEntity>(params).getPage(),
                new EntityWrapper<XjMonitorEntity>()
        );
        List<XjMonitorEntity> list = page.getRecords();
        List<XjChildMonitorEntity> childMonitorList = new ArrayList<>();
        List<XjKlogEntity> logList = new ArrayList<>();
        if(list != null && list.size() > 0){
            for(XjMonitorEntity monitor : list){
                childMonitorList = xjChildMonitorService.selectList(new EntityWrapper<XjChildMonitorEntity>().eq("monitor_id",monitor.getMonitorId()));
                monitor.setChildMonitorList(childMonitorList);
                logList = xjKlogService.selectList(new EntityWrapper<XjKlogEntity>().eq("monitor_id",monitor.getMonitorId()));
                monitor.setLogList(logList);
                xjMonitorService.updateById(monitor);
            }
        }
        return new PageUtils(page);
    }

}
