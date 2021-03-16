package io.renren.modules.xj.utils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.security.UserGroupInformation;

import java.sql.*;

public class HiveUtils {
    /** 分页查询默认每页记录条数 */
    public static final int PAGE_SIZE_DEFAULT = 10;

    public static final String krb5_conf = "*";
    public static final String krb5_debug = "*";
    public static final String user_principal = "*";
    public static final String user_keytab = "*";
    public static final String JDBC_DRIVER = "*";
    public static final String CONNECTION_URL = "*";

    /**
     * 获取数据库连接
     * @return 数据库连接对象
     */
    public static Connection getConnection(Connection conn) {
        if(conn == null){
            try {
                System.out.println("初始化");
                System.setProperty("java.security.krb5.conf",krb5_conf);
                System.setProperty("java.security.krb5.debug",krb5_debug);

                Class.forName(JDBC_DRIVER);

                Configuration configuration = new Configuration();
                configuration.set("hadoop.security.authentication","kerberos");

                UserGroupInformation.setConfiguration(configuration);
                UserGroupInformation ugi = UserGroupInformation.loginUserFromKeytabAndReturnUGI(
                        user_principal,user_keytab);
                UserGroupInformation.setLoginUser(ugi);
                conn = DriverManager.getConnection(CONNECTION_URL);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return conn;
    }

    /**
     * 查询结果以ArrayList返回
     * @param sql 查询语句 例如："select u.id,u.name,u.sex,u.depart_id AS departId,d.* from user u,depart d where u.depart_id=d.id"
     * @param values
     * @throws SQLException
     */
    public static JSONArray queryJsonArray(int pageIndex, int pageSize, String sql, Object... values) throws SQLException {
        JSONArray jsonArray = null;
        Connection conn = null;
        PreparedStatement pStmt = null;
        //校验参数
        if(pageIndex <= 0){
            pageIndex = 1;
        }
        if(pageSize <= 0){
            pageSize = PAGE_SIZE_DEFAULT;
        }
        conn = getConnection(conn);
        pStmt = conn.prepareStatement(sql);
        //设置参数
        if(pStmt != null && values != null && values.length > 0){
            for (int i = 0; i < values.length; i++) {
                pStmt.setObject(i+1, values[i]);
            }
        }
        //设置最大查询到第几条记录
        pStmt.setMaxRows(pageIndex*pageSize);
        ResultSet rs = pStmt.executeQuery();
        //游标移动到要输出的第一条记录
        rs.relative((pageIndex-1)*pageSize);
        if(rs != null){
            try {
                jsonArray = new JSONArray();
                ResultSetMetaData md = rs.getMetaData(); //得到结果集(rs)的结构信息，比如字段数、字段名等
                //遍历结果集
                while(rs.next()){
                    JSONObject jsonObject = new JSONObject();
                    for (int i = 1; i <= md.getColumnCount(); i++) {
                        jsonObject.put((md.getColumnLabel(i)), rs.getObject(i)+"");
                    }
                    jsonArray.add(jsonObject);
                }
            }finally{
                if(rs != null){
                    rs.close();
                }
                if(pStmt != null){
                    pStmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            }
        }
        return jsonArray;
    }

    /**
     * 查询记录条数
     * @param sql 例如："select count(*) from user where xxx"
     * @param values
     * @throws SQLException
     */
    public static int queryCount(String sql, Object... values) throws SQLException{
        int count = -1;
        Connection conn = null;
        PreparedStatement pStmt = null;
        conn = getConnection(conn);
        pStmt = conn.prepareStatement(sql);
        //设置参数
        if(pStmt != null && values != null && values.length > 0){
            for (int i = 0; i < values.length; i++) {
                pStmt.setObject(i+1, values[i]);
            }
        }
        ResultSet rs = pStmt.executeQuery();
        if(rs != null){
            try {
                while(rs.next()){
                    count = rs.getInt(1);
                }
            }finally{
                if(rs != null){
                    rs.close();
                }
                if(pStmt != null){
                    pStmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            }
        }
        return count;
    }
}
