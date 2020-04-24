package io.renren.modules.xj.service.impl;

import io.renren.modules.xj.entity.XjDataSourceEntity;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.component.plugins.PluginRegistry;
import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.exception.KettleXMLException;
import org.pentaho.di.core.plugins.StepPluginType;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransHopMeta;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.steps.insertupdate.InsertUpdateMeta;
import org.pentaho.di.trans.steps.tablecompare.Kjube;
import org.pentaho.di.trans.steps.tableinput.TableInputMeta;
import org.pentaho.di.trans.steps.tableoutput.TableOutputMeta;
import org.pentaho.di.trans.steps.textfileoutput.TextFileOutputMeta;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.xj.dao.XjKtrDao;
import io.renren.modules.xj.entity.XjKtrEntity;
import io.renren.modules.xj.service.XjKtrService;


@Service("xjKtrService")
public class XjKtrServiceImpl extends ServiceImpl<XjKtrDao, XjKtrEntity> implements XjKtrService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<XjKtrEntity> page = this.selectPage(
                new Query<XjKtrEntity>(params).getPage(),
                new EntityWrapper<XjKtrEntity>()
        );

        return new PageUtils(page);
    }
    private static TransMeta createTrans(){
         return null;
    }
    @Override
    public String kettleMysql(XjKtrEntity xjKtr, XjDataSourceEntity ds) throws KettleException {


        //创建转换
        TransMeta transMeta = new TransMeta();
        //设置转换名称
        transMeta.setName(xjKtr.getKtrName());
        //设置转换数据库连接
        transMeta.addDatabase(new DatabaseMeta(xjKtr.getKtrName(),ds.getDsType(),ds.getDsLinetype(),ds.getDsIp(),xjKtr.getKtrDsname(),ds.getPort().toString(),ds.getDsUsername(),ds.getDsPassword()));

        //新建表输入步骤
        TableInputMeta tableInputMeta = new TableInputMeta();
        //设置步骤的数据库连接
        tableInputMeta.setDatabaseMeta(transMeta.findDatabase(ds.getDsDatabasename()));
        //设置步骤sql
        tableInputMeta.setSQL(xjKtr.getKtrSql());
        //设置步骤名称
        StepMeta step1 =new StepMeta("表输入",tableInputMeta);

        //新建文本输出步骤
        TextFileOutputMeta textFileOutputMeta = new TextFileOutputMeta();
        //设置文本输出名称及地址
        textFileOutputMeta.setFileName("ktext" + xjKtr.getKtrName());
        //设置步骤名
        StepMeta step2 = new StepMeta("文本输出",textFileOutputMeta);

        //设置起始步骤和目标步骤并串联
        TransHopMeta transHopMeta = new TransHopMeta(step1,step2);

        //为转换添加步骤并串联
        transMeta.addStep(step1);
        transMeta.addStep(step2);
        transMeta.addTransHop(transHopMeta);

        //初始化
        KettleEnvironment.init();
        Trans trans =new Trans(transMeta);
        trans.execute(null);
        trans.waitUntilFinished();

        return null;
    }
}
