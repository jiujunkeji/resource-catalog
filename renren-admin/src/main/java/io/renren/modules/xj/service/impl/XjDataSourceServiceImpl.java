package io.renren.modules.xj.service.impl;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.xj.dao.XjDataSourceDao;
import io.renren.modules.xj.entity.XjDataSourceEntity;
import io.renren.modules.xj.service.XjDataSourceService;


@Service("xjDataSourceService")
public class XjDataSourceServiceImpl extends ServiceImpl<XjDataSourceDao, XjDataSourceEntity> implements XjDataSourceService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<XjDataSourceEntity> page = this.selectPage(
                new Query<XjDataSourceEntity>(params).getPage(),
                new EntityWrapper<XjDataSourceEntity>()
        );


        return new PageUtils(page);
    }

    @Override
    public List list2(Map<String, Object> params) {
        return returnList(params);
    }

    public static List  returnList(Map<String, Object> params) {
        List list = new ArrayList();
        Iterator iter = params.entrySet().iterator();  //获得map的Iterator
        while(iter.hasNext()) {
            Map.Entry entry = (Map.Entry)iter.next();
            list.add(entry.getValue());
        }
        return list;
    }

}
