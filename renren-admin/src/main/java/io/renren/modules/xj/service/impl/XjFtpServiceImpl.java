package io.renren.modules.xj.service.impl;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.StringTokenizer;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.xj.dao.XjFtpDao;
import io.renren.modules.xj.entity.XjFtpEntity;
import io.renren.modules.xj.service.XjFtpService;


@Service("xjFtpService")
public class XjFtpServiceImpl extends ServiceImpl<XjFtpDao, XjFtpEntity> implements XjFtpService {


    //把文件上传到ftp服务器上
    public static boolean uploadFile2(XjFtpEntity fe) throws FileNotFoundException {
        String FTP_ADDRESS = fe.getFtpIp();
        int FTP_PORT = fe.getFtpPort();
        String FTP_USERNAME = fe.getFtpUsername();
        String FTP_PASSWORD = fe.getFtpPassword();

        InputStream is = new FileInputStream(fe.getFtpFilepath());

        boolean success = false;
        //创建FTPClient客户端
        FTPClient ftp = new FTPClient();
        ftp.setControlEncoding("GBK");
        try {
            int reply;
            // 连接FTP服务器,ip地址，端口号
            ftp.connect(FTP_ADDRESS, FTP_PORT);
            // 登录账号和密码
            ftp.login(FTP_USERNAME, FTP_PASSWORD);
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return success;
            }
            //主动模式
            // ftp.enterLocalActiveMode();
            //被动模式
            ftp.enterLocalPassiveMode();
            // 设置ftp上传模式，已二进制方式上传。
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            boolean a = ftp.changeWorkingDirectory("/");
            //如果输入的路径为空或者为根路径，则不转换操作目录
            if (StringUtils.isBlank(fe.getFtpFtppath()) || fe.getFtpFtppath().equals("/")) {
                //否则创建想要上传文件的目录，并且将操作目录转为新创建的目录
            } else {
                StringTokenizer s = new StringTokenizer(fe.getFtpFtppath(), "/");
                s.countTokens();
                String pathName = "";
                while (s.hasMoreElements()) {
                    //创建多级目录
                    pathName = pathName + "/" + (String) s.nextElement();
                    try {
                        ftp.mkd(pathName);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                ftp.changeWorkingDirectory(fe.getFtpFtppath());
            }
            ftp.storeFile(fe.getFtpName(), is);
            is.close();
            ftp.logout();
            success = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        return success;
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        EntityWrapper<XjFtpEntity> wrapper = new EntityWrapper<XjFtpEntity>();
        wrapper.eq("ftp_delete",0);
        Page<XjFtpEntity> page = this.selectPage(
                new Query<XjFtpEntity>(params).getPage(),
                new EntityWrapper<XjFtpEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public boolean uploadFile(XjFtpEntity fe) throws FileNotFoundException {
        return uploadFile2(fe);

    }

}
