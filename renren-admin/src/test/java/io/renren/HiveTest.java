package io.renren;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.security.UserGroupInformation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;


@RunWith(SpringRunner.class)
@SpringBootTest
public class HiveTest {
    public static final String krb5_conf = "*";
    public static final String krb5_debug = "*";
    public static final String user_principal = "*";
    public static final String user_keytab = "*";
    public static final String JDBC_DRIVER = "*";
    public static final String CONNECTION_URL = "*";



    @Test
    public void hive() {
        System.setProperty("java.security.krb5.conf",krb5_conf);
        System.setProperty("java.security.krb5.debug",krb5_debug);
        try{
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        Configuration configuration = new Configuration();
        configuration.set("hadoop.security.authentication","kerberos");

        UserGroupInformation.setConfiguration(configuration);
        UserGroupInformation ugi = null;
        try{
            ugi = UserGroupInformation.loginUserFromKeytabAndReturnUGI(user_principal,user_keytab);
        } catch (IOException e){
            e.printStackTrace();
        }
        UserGroupInformation.setLoginUser(ugi);
    }
}
