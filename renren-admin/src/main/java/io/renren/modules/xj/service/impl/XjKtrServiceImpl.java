package io.renren.modules.xj.service.impl;

import io.renren.modules.xj.entity.XjDataSourceEntity;

import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.ObjectLocationSpecificationMethod;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.exception.KettleException;
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
import org.springframework.scheduling.annotation.Async;
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
    public void kettleJob(XjKtrEntity xjKtr, XjDataSourceEntity ds) throws Exception {

        KettleEnvironment.init();
        KettleDatabaseRepository kettleDatabaseRepository = repositoryCon(ds);
        //createAndSaveTrans(kettleDatabaseRepository);
        JobMeta jobMeta = generateJob(kettleDatabaseRepository,xjKtr,ds);
        saveJob(kettleDatabaseRepository, jobMeta);
        Job job = setSql(ds,xjKtr);
        job.start();
        job.waitUntilFinished();
        if (job.getErrors() > 0) {
            xjKtr.setKtrStatus("3");
        }else {
            xjKtr.setKtrStatus("2");
        }
        this.updateById(xjKtr);
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
        job.setVariable("test",ke.getKtrSql());
        return job;
    }

}
