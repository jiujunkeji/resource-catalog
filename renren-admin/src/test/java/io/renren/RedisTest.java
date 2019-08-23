package io.renren;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import io.renren.common.utils.RedisUtils;
import io.renren.modules.resource.service.ResourceCatalogService;
import io.renren.modules.sys.dto.SysDeptDto;
import io.renren.modules.sys.dto.SysUserDto;
import io.renren.modules.sys.entity.SysDeptEntity;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.service.SysDeptService;
import io.renren.modules.sys.service.SysUserService;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private ResourceCatalogService resourceCatalogService;
    @Autowired
    private SysDeptService sysDeptService;
    @Autowired
    private SysUserService sysUserService;

    @Test
    public void contextLoads() {
//        SysUserEntity user = new SysUserEntity();
//        user.setEmail("123456@qq.com");
//        redisUtils.set("user", user);
//
//        System.out.println(ToStringBuilder.reflectionToString(redisUtils.get("user", SysUserEntity.class)));

//        System.out.println(resourceCatalogService.selectAllCatalogName(7L));


//        List<SysDeptDto> deptDtoList = new ArrayList<SysDeptDto>();
//        List<SysDeptEntity> oneList = sysDeptService.selectList(new EntityWrapper<SysDeptEntity>().eq("parent_id",0).eq("del_flag",0));
//        for(SysDeptEntity deptEntity : oneList){
//            SysDeptDto dept = sysDeptService.init(deptEntity);
//            deptDtoList.add(dept);
//        }
//        System.out.println(deptDtoList);

        List<SysUserDto> list = new ArrayList<SysUserDto>();
        List<SysDeptEntity> oneList = sysDeptService.selectList(new EntityWrapper<SysDeptEntity>().eq("del_flag",0));
        for(SysDeptEntity dept : oneList){
            if(sysUserService.selectCount(new EntityWrapper<SysUserEntity>().eq("dept_id",dept.getDeptId())) != 0){
                SysUserDto dto = new SysUserDto();
                dto.setDeptName(dept.getName());
                List<SysUserEntity> userList = sysUserService.selectList(new EntityWrapper<SysUserEntity>().eq("dept_id",dept.getDeptId()));
                dto.setUserList(userList);
                list.add(dto);
            }
        }

        System.out.println(list);
    }

}
