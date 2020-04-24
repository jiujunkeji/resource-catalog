package io.renren.modules.xj.service.impl;

import io.renren.modules.sys.entity.SysDictEntity;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.service.SysDictService;
import io.renren.modules.xj.entity.XjCatalogEntity;
import io.renren.modules.xj.service.XjCatalogService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.xj.dao.XjSafeDao;
import io.renren.modules.xj.entity.XjSafeEntity;
import io.renren.modules.xj.service.XjSafeService;


@Service("xjSafeService")
public class XjSafeServiceImpl extends ServiceImpl<XjSafeDao, XjSafeEntity> implements XjSafeService {
    @Autowired
    private XjCatalogService catalogService;
    @Autowired
    private SysDictService dictService;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String name = (String) params.get("");
        String safeLevelCode = (String) params.get("");
        String safeCode = (String) params.get("");
        String catalogId = (String) params.get("catalogId");
        Page<XjSafeEntity> page = this.selectPage(
                new Query<XjSafeEntity>(params).getPage(),
                new EntityWrapper<XjSafeEntity>()
                        .eq(StringUtils.isNotBlank(catalogId),"catalog_id", catalogId)
        );

        return new PageUtils(page);
    }

    @Override
    public XjSafeEntity setDefaultSafe(Long catalogId, SysUserEntity user) {
        XjCatalogEntity xjCatalog = catalogService.selectById(catalogId);

        XjSafeEntity safeEntity = new XjSafeEntity();
        safeEntity.setCatalogId(xjCatalog.getCatalogId());
        safeEntity.setCatalogName(xjCatalog.getCatalogName());
        //设置默认安全类型
        SysDictEntity defaultSafeType = dictService.selectOne(new EntityWrapper<SysDictEntity>().eq("type","safe_type_default"));
        if(defaultSafeType != null){
            safeEntity.setSafeTypeCode(defaultSafeType.getCode());
            safeEntity.setSafeType(defaultSafeType.getValue());
        }else{
            safeEntity.setSafeTypeCode(1);
            safeEntity.setSafeType("有条件共享");
        }
        //设置默认等级
        SysDictEntity defaultSafeLevel = dictService.selectOne(new EntityWrapper<SysDictEntity>().eq("type","safe_level_default"));
        if(defaultSafeLevel != null){
            safeEntity.setSafeCode(defaultSafeLevel.getCode());
            safeEntity.setSafe(defaultSafeLevel.getValue());
        }else{
            safeEntity.setSafeCode(5);
            safeEntity.setSafe("五级");
        }
        //获取默认加密方式
        SysDictEntity defaultEncrypt = dictService.selectOne(new EntityWrapper<SysDictEntity>().eq("type","encrypt_method_default"));
        if(defaultEncrypt != null){
            safeEntity.setEncryptCode(defaultEncrypt.getCode());
            safeEntity.setEncrypt(defaultEncrypt.getValue());
        }else{
            safeEntity.setEncryptCode(1);
            safeEntity.setEncrypt("AES");
        }
        //设置创建人
        safeEntity.setCreateUserId(user.getUserId());
        safeEntity.setCreateUser(user.getName());
        safeEntity.setCreateTime(new Date());
        this.insert(safeEntity);
        return null;
    }

}
