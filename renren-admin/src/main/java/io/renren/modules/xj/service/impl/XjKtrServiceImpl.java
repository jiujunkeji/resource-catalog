package io.renren.modules.xj.service.impl;

import io.renren.modules.xj.entity.*;

import io.renren.modules.xj.service.XjChildMonitorService;
import io.renren.modules.xj.service.XjKlogService;
import io.renren.modules.xj.service.XjMonitorService;
import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.ObjectLocationSpecificationMethod;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.logging.KettleLogStore;
import org.pentaho.di.job.Job;
import org.pentaho.di.job.JobHopMeta;
import org.pentaho.di.job.JobMeta;
import org.pentaho.di.job.entries.special.JobEntrySpecial;
import org.pentaho.di.job.entries.success.JobEntrySuccess;
import org.pentaho.di.job.entries.trans.JobEntryTrans;
import org.pentaho.di.job.entry.JobEntryCopy;
import org.pentaho.di.repository.RepositoryDirectoryInterface;
import org.pentaho.di.repository.kdr.KettleDatabaseRepository;
import org.pentaho.di.repository.kdr.KettleDatabaseRepositoryMeta;
import org.pentaho.di.trans.TransMeta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.xj.dao.XjKtrDao;
import io.renren.modules.xj.service.XjKtrService;


@Service("xjKtrService")
public class XjKtrServiceImpl extends ServiceImpl<XjKtrDao, XjKtrEntity> implements XjKtrService {

    @Autowired
    private XjMonitorService xjMonitorService;
    @Autowired
    private XjChildMonitorService xjChildMonitorService;
    @Autowired
    private XjKlogService xjKlogService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<XjKtrEntity> page = this.selectPage(
                new Query<XjKtrEntity>(params).getPage(),
                new EntityWrapper<XjKtrEntity>()
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

    @Override
    @Async("taskExecutor")
    public void kettleJob(XjKtrEntity xjKtr, XjDataSourceEntity ds)  {

        //生成监控主任务
        XjMonitorEntity xjMonitorEntity = new XjMonitorEntity();
        xjMonitorEntity.setJobName(xjKtr.getKtrName());
        xjMonitorEntity.setCreateTime(new Date());
        xjMonitorEntity.setStartTime(new Date());
        xjMonitorEntity.setStatus(0);
        xjMonitorService.insert(xjMonitorEntity);

        //生成子任务
        XjChildMonitorEntity xjChildMonitorEntity = new XjChildMonitorEntity();
        xjChildMonitorEntity.setMonitorId(xjMonitorEntity.getMonitorId());
        xjChildMonitorEntity.setCreateTime(new Date());
        xjChildMonitorEntity.setChildJobName(xjKtr.getKtrName()+"_开始加载作业");
        xjChildMonitorEntity.setStatus(0);
        xjChildMonitorService.insert(xjChildMonitorEntity);
        //开始加载作业
        try {
            KettleEnvironment.init();
            KettleDatabaseRepository kettleDatabaseRepository = repositoryCon(ds);
            //createAndSaveTrans(kettleDatabaseRepository);
            JobMeta jobMeta = generateJob(kettleDatabaseRepository,xjKtr,ds);
            saveJob(kettleDatabaseRepository, jobMeta);
            Job job = setSql(ds,xjKtr);

            xjChildMonitorEntity.setCreateTime(new Date());
            xjChildMonitorEntity.setChildJobName(xjKtr.getKtrName()+"_加载作业完成");
            xjChildMonitorEntity.setStatus(1);
            xjChildMonitorService.insert(xjChildMonitorEntity);
            //作业加载完成
            job.start();
            xjChildMonitorEntity.setCreateTime(new Date());
            xjChildMonitorEntity.setChildJobName(xjKtr.getKtrName()+"_开始执行作业");
            xjChildMonitorEntity.setStatus(2);
            xjChildMonitorService.insert(xjChildMonitorEntity);
            //开始执行作业
            job.waitUntilFinished();
            if (job.getErrors() > 0) {
                //失败
                xjKtr.setKtrStatus("3");

                xjChildMonitorEntity.setCreateTime(new Date());
                xjChildMonitorEntity.setChildJobName(xjKtr.getKtrName()+"_结束执行作业");
                xjChildMonitorEntity.setStatus(4);
                xjChildMonitorService.insert(xjChildMonitorEntity);

                xjMonitorEntity.setStatus(2);
                xjMonitorEntity.setEndTime(new Date());
                xjMonitorService.updateById(xjMonitorEntity);

                String file = KettleLogStore.getAppender().getBuffer().toString();
                XjKlogEntity xjKlogEntity = tableInput(file,xjMonitorEntity.getMonitorId());
                xjKlogService.insert(xjKlogEntity);
                XjKlogEntity xjKlogEntity1 = textOutput(file,xjMonitorEntity.getMonitorId());
                xjKlogService.insert(xjKlogEntity1);


            }else {
                //成功
                xjKtr.setKtrStatus("2");

                xjChildMonitorEntity.setCreateTime(new Date());
                xjChildMonitorEntity.setChildJobName(xjKtr.getKtrName()+"_结束执行作业");
                xjChildMonitorEntity.setStatus(3);
                xjChildMonitorService.insert(xjChildMonitorEntity);

                xjMonitorEntity.setStatus(1);
                xjMonitorEntity.setEndTime(new Date());
                xjMonitorService.updateById(xjMonitorEntity);

                String file = KettleLogStore.getAppender().getBuffer().toString();
                XjKlogEntity xjKlogEntity = tableInput(file,xjMonitorEntity.getMonitorId());
                xjKlogService.insert(xjKlogEntity);
                XjKlogEntity xjKlogEntity1 = textOutput(file,xjMonitorEntity.getMonitorId());
                xjKlogService.insert(xjKlogEntity1);
            }
            this.updateById(xjKtr);
        } catch (KettleException e) {
            //失败
            xjKtr.setKtrStatus("3");

            xjChildMonitorEntity.setCreateTime(new Date());
            xjChildMonitorEntity.setChildJobName(xjKtr.getKtrName()+"_结束执行作业");
            xjChildMonitorEntity.setStatus(4);
            xjChildMonitorService.insert(xjChildMonitorEntity);

            xjMonitorEntity.setStatus(2);
            xjMonitorEntity.setEndTime(new Date());
            xjMonitorService.updateById(xjMonitorEntity);

            String file = KettleLogStore.getAppender().getBuffer().toString();
            XjKlogEntity xjKlogEntity = tableInput(file,xjMonitorEntity.getMonitorId());
            xjKlogService.insert(xjKlogEntity);
            XjKlogEntity xjKlogEntity1 = textOutput(file,xjMonitorEntity.getMonitorId());
            xjKlogService.insert(xjKlogEntity1);
            e.printStackTrace();
        } catch (Exception e) {
            //失败
            xjKtr.setKtrStatus("3");

            xjChildMonitorEntity.setCreateTime(new Date());
            xjChildMonitorEntity.setChildJobName(xjKtr.getKtrName()+"_结束执行作业");
            xjChildMonitorEntity.setStatus(4);
            xjChildMonitorService.insert(xjChildMonitorEntity);

            xjMonitorEntity.setStatus(2);
            xjMonitorEntity.setEndTime(new Date());
            xjMonitorService.updateById(xjMonitorEntity);

            String file = KettleLogStore.getAppender().getBuffer().toString();
            XjKlogEntity xjKlogEntity = tableInput(file,xjMonitorEntity.getMonitorId());
            xjKlogService.insert(xjKlogEntity);
            XjKlogEntity xjKlogEntity1 = textOutput(file,xjMonitorEntity.getMonitorId());
            xjKlogService.insert(xjKlogEntity1);
            e.printStackTrace();
        }

    }

    /**
     *  *  * 连接到资源库
     */
    private static KettleDatabaseRepository repositoryCon(XjDataSourceEntity ds) throws KettleException {
        // 初始化环境
        if (!KettleEnvironment.isInitialized()) {
            try {
                KettleEnvironment.init();
            } catch (KettleException e) {
                e.printStackTrace();
            }
        }
        // 数据库连接元对象
        // （kettle数据库连接名称(KETTLE工具右上角显示)，资源库类型，连接方式，IP，数据库名，端口，用户名，密码） //cgmRepositoryConn
        DatabaseMeta databaseMeta = new DatabaseMeta(ds.getDsName(), ds.getDsType(), ds.getDsLinetype(), ds.getDsIp(),
                ds.getDsDatabasename(), ds.getDsPort().toString(), ds.getDsUsername(), ds.getDsPassword());
        // 数据库形式的资源库元对象
        KettleDatabaseRepositoryMeta kettleDatabaseRepositoryMeta = new KettleDatabaseRepositoryMeta();
        kettleDatabaseRepositoryMeta.setConnection(databaseMeta);
        // 数据库形式的资源库对象
        KettleDatabaseRepository kettleDatabaseRepository = new KettleDatabaseRepository();
        // 用资源库元对象初始化资源库对象
        kettleDatabaseRepository.init(kettleDatabaseRepositoryMeta);
        // 连接到资源库
        kettleDatabaseRepository.connect("admin", "admin");// 默认的连接资源库的用户名和密码
        if (kettleDatabaseRepository.isConnected()) {
            System.out.println("连接成功");
            return kettleDatabaseRepository;
        } else {
            System.out.println("连接失败");
            return null;
        }
    }

    /**
     * 生成作业
     *
     * @param kettleDatabaseRepository
     * @return
     * @throws Exception
     */
    private static JobMeta generateJob(KettleDatabaseRepository kettleDatabaseRepository,XjKtrEntity ke,XjDataSourceEntity ds) throws Exception {
        // 创建作业
        JobMeta jobMeta = createJob(ke.getKtrName());
        // 创建作业中的各个节点
        JobEntryCopy start = createJobStart();
        JobEntryCopy trans1 = createJobTrans1(kettleDatabaseRepository,ds);
        JobEntryCopy success = createJobSuccess();
        // 将节点加入作业中
        jobMeta.addJobEntry(start);
        jobMeta.addJobEntry(trans1);
        jobMeta.addJobEntry(success);
        // 创建并增加节点连接
        jobMeta.addJobHop(createJobHopMeta(start, trans1));
        jobMeta.addJobHop(createJobHopMeta(trans1, success));
        return jobMeta;
    }

    /**
     *  * 创建作业  *   * @return
     */
    private static JobMeta createJob(String jobname) {
        JobMeta jobMeta = new JobMeta();
        jobMeta.setName(jobname);
        jobMeta.setJobstatus(0);
        return jobMeta;
    }

    /**
     *  * 创建作业开始节点  *   * @return
     */
    private static JobEntryCopy createJobStart() {
        JobEntrySpecial jobEntrySpecial = new JobEntrySpecial();
        jobEntrySpecial.setName("START");
        jobEntrySpecial.setStart(true);
        JobEntryCopy start = new JobEntryCopy(jobEntrySpecial);
        start.setDrawn();
        start.setLocation(10, 10);
        return start;
    }

    /**
     *  * 创建作业转换节点  *   * @return  * @throws Exception
     */
    private static JobEntryCopy createJobTrans1(KettleDatabaseRepository kettleDatabaseRepository,XjDataSourceEntity ds) throws Exception {
        JobEntryTrans jobEntryTrans = new JobEntryTrans();
        // 指定从资源库中以转换名称的方式找到目标转换cgmTransName对象
        jobEntryTrans
                .setSpecificationMethod(ObjectLocationSpecificationMethod.getSpecificationMethodByCode("rep_name"));
        // 指定从哪个资源库查找转换对象
        jobEntryTrans.setRepository(kettleDatabaseRepository);
        // 指定从资源库的哪个目录下查找转换对象
        jobEntryTrans.setDirectory("/");
        // 指定被查找的转换目标名称(转换名为数据库名)
        jobEntryTrans.setTransname(ds.getDsDatabasename());
        JobEntryCopy jobEntryCopy = new JobEntryCopy(jobEntryTrans);
        // 设置job中的转换名称
        jobEntryCopy.setName("JTrans");
        jobEntryCopy.setDrawn(true);
        // 这个一定要加，不然会报错空指针异常
        jobEntryCopy.setLocation(10, 20);
        return jobEntryCopy;
    }

    /**
     *  * 创建作业成功节点  *   * @return
     */
    private static JobEntryCopy createJobSuccess() {
        JobEntrySuccess jobEntrySuccess = new JobEntrySuccess();
        jobEntrySuccess.setName("Success");
        JobEntryCopy success = new JobEntryCopy(jobEntrySuccess);
        success.setDrawn();
        success.setLocation(10, 30);
        return success;
    }

    /**
     *  * 创建节点连接  *   * @param start  * @param end  * @return
     */
    private static JobHopMeta createJobHopMeta(JobEntryCopy start, JobEntryCopy end) {
        return new JobHopMeta(start, end);
    }

    /**
     * 保存作业到资源库
     *
     * @param kettleDatabaseRepository
     * @param jobMeta
     * @throws Exception
     */
    private static void saveJob(KettleDatabaseRepository kettleDatabaseRepository, JobMeta jobMeta) throws Exception {
        RepositoryDirectoryInterface dir = kettleDatabaseRepository.loadRepositoryDirectoryTree().findDirectory("/");
        jobMeta.setRepositoryDirectory(dir);
        kettleDatabaseRepository.save(jobMeta, null);
    }


    //sql替换
    private static Job setSql(XjDataSourceEntity ds,XjKtrEntity ke) throws KettleException {
        KettleDatabaseRepository repository=new KettleDatabaseRepository();
        DatabaseMeta databaseMeta = new DatabaseMeta(ds.getDsName(), ds.getDsType(), ds.getDsLinetype(), ds.getDsIp(),
                ds.getDsDatabasename(), ds.getDsPort().toString(), ds.getDsUsername(), ds.getDsPassword());
        //选择资源库
        KettleDatabaseRepositoryMeta kettleDatabaseRepositoryMeta=new KettleDatabaseRepositoryMeta("sjw","sjw","Transformation description",databaseMeta);
        repository.init(kettleDatabaseRepositoryMeta);
        repository.connect("admin","admin");
        RepositoryDirectoryInterface directoryInterface=repository.loadRepositoryDirectoryTree();

        JobMeta jobMeta = repository.loadJob(ke.getKtrName(),directoryInterface,null,null);
        Job job = new Job(repository,jobMeta);
        if (ke.getKtrSql() == null){
            String sql = "select * from "+ke.getKtrTablename()+"limit" +ke.getKtrNumber();
            job.setVariable("test",sql);
        }else {
            job.setVariable("test",ke.getKtrSql());
        }
        return job;
    }

    //表输入字段匹配
    private static XjKlogEntity tableInput (String file ,int monid){
        XjKlogEntity xjKlogEntity = new XjKlogEntity();
        xjKlogEntity.setmonitorId(monid);
        xjKlogEntity.setAssemblyName("表输入");
        Pattern p = Pattern.compile("表输入.*完成处理.*");
        Matcher m = p.matcher(file);
        if (m.find() != false){
            System.out.println(m.group(0));
            Pattern p1 = Pattern.compile(".=\\d*");
            Matcher m1 = p1.matcher(m.group(0));
            while (m1.find()){
                String[] s =  m1.group(0).split("=");
                if (s[0].equals("I") ){
                    xjKlogEntity.setxjIn(s[1]);
                }else if (s[0] .equals("O") ){
                    xjKlogEntity.setxjOut(s[1]);
                }else if (s[0] .equals("R") ){
                    xjKlogEntity.setxjRead(s[1]);
                }else if (s[0] .equals("W") ){
                    xjKlogEntity.setxjWrite(s[1]);
                }else if (s[0] .equals("U") ){
                    xjKlogEntity.setxjUpdate(s[1]);
                }else if (s[0] .equals("E") ){
                    xjKlogEntity.setxjError(s[1]);
                }
            }
            xjKlogEntity.setCreateTime(new Date());
            return xjKlogEntity;
        }else {
            return null;
        }
    }

    //文本输出字段匹配
    private static XjKlogEntity textOutput (String file ,int monid){
        XjKlogEntity xjKlogEntity = new XjKlogEntity();
        xjKlogEntity.setmonitorId(monid);
        xjKlogEntity.setAssemblyName("文本文件输出");
        Pattern p = Pattern.compile("文本文件输出.*完成处理.*");
        Matcher m = p.matcher(file);
        if (m.find() != false){
            System.out.println(m.group(0));
            Pattern p1 = Pattern.compile(".=\\d*");
            Matcher m1 = p1.matcher(m.group(0));
            while (m1.find()){
                String[] s =  m1.group(0).split("=");
                if (s[0].equals("I") ){
                    xjKlogEntity.setxjIn(s[1]);
                }else if (s[0] .equals("O") ){
                    xjKlogEntity.setxjOut(s[1]);
                }else if (s[0] .equals("R") ){
                    xjKlogEntity.setxjRead(s[1]);
                }else if (s[0] .equals("W") ){
                    xjKlogEntity.setxjWrite(s[1]);
                }else if (s[0] .equals("U") ){
                    xjKlogEntity.setxjUpdate(s[1]);
                }else if (s[0] .equals("E") ){
                    xjKlogEntity.setxjError(s[1]);
                }
            }
            xjKlogEntity.setCreateTime(new Date());
            return xjKlogEntity;
        }else {
            return null;
        }
    }
}
