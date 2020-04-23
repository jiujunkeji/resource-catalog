package io.renren.modules.xj.service.impl;

import io.renren.modules.resource.entity.MeteCategoryEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.xj.dao.XjMeteCategoryDao;
import io.renren.modules.xj.entity.XjMeteCategoryEntity;
import io.renren.modules.xj.service.XjMeteCategoryService;


@Service("xjMeteCategoryService")
public class XjMeteCategoryServiceImpl extends ServiceImpl<XjMeteCategoryDao, XjMeteCategoryEntity> implements XjMeteCategoryService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<XjMeteCategoryEntity> page = this.selectPage(
                new Query<XjMeteCategoryEntity>(params).getPage(),
                new EntityWrapper<XjMeteCategoryEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<XjMeteCategoryEntity> searchFindByMeteCategoryNumberOrName(String str) {
        //先判断是否是根据编号查询
        List<XjMeteCategoryEntity> xjMeteCategoryEntityList = this.selectList(new EntityWrapper<XjMeteCategoryEntity>().eq("meta_category_number", str));
        if (xjMeteCategoryEntityList.size() == 0 || xjMeteCategoryEntityList == null) {
            //再去根据名称查询
            xjMeteCategoryEntityList = this.selectList(new EntityWrapper<XjMeteCategoryEntity>().like("name", str));

        }
        return xjMeteCategoryEntityList;
    }

    @Override
    public List<XjMeteCategoryEntity> updateEnabledState(Long mete_category_id) {
        EntityWrapper<XjMeteCategoryEntity> wrapper=new EntityWrapper<>();
        List<XjMeteCategoryEntity> xjMeteCategoryEntityList=this.selectList(wrapper.eq("mete_category_id",mete_category_id));
        return xjMeteCategoryEntityList;
    }

    @Override
    public List<XjMeteCategoryEntity> updateDisabledState(Long mete_category_id) {
        EntityWrapper<XjMeteCategoryEntity> wrapper=new EntityWrapper<>();
        List<XjMeteCategoryEntity> xjMeteCategoryEntityList=this.selectList(wrapper.eq("mete_category_id",mete_category_id));
        return xjMeteCategoryEntityList;

    }

}
