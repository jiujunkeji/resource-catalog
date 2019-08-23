package io.renren.common.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

public class InfoJson {

    public static final String DEF_CHATSET = "UTF-8";
    public static final int DEF_CONN_TIMEOUT = 30000;
    public static final int DEF_READ_TIMEOUT = 30000;
    public static String userAgent =  "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";

    /**
     * post请求（用于请求json格式的参数）
     * @param path
     * @param param
     * @return
     */
    public static JSONObject postJson(String path,Map param){

        try {
            URL url = new URL(path);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");// 提交模式
            httpURLConnection.setConnectTimeout(1000);//连接超时 单位毫秒
            // conn.setReadTimeout(2000);//读取超时 单位毫秒
            // 发送POST请求必须设置如下两行
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            PrintWriter printWriter = new PrintWriter(httpURLConnection.getOutputStream());
            // 发送请求参数
            printWriter.write(urlencode(param));//post的参数 xx=xx&yy=yy
            // flush输出流的缓冲
            printWriter.flush();
            //开始获取数据
            BufferedInputStream bis = new BufferedInputStream(httpURLConnection.getInputStream());
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            int len;
            byte[] arr = new byte[1024];
            while((len=bis.read(arr))!= -1){
                bos.write(arr,0,len);
                bos.flush();
            }
            bos.close();
            return JSONObject.parseObject(bos.toString("utf-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * post请求（用于请求json格式的参数）
     * @param url
     * @param params
     * @return
     */
    public static JSONObject doPost(String url, String params){

        CloseableHttpClient httpclient = HttpClients.createDefault();
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(2000).setConnectTimeout(2000).build();
        JSONObject jsonObject = null;

        HttpPost httpPost = new HttpPost(url);// 创建httpPost
//        httpPost.setHeader("Accept", "application/json");
//        httpPost.setHeader("Content-Type", "application/json");
//        String charSet = "UTF-8";
        httpPost.setConfig(requestConfig);
        StringEntity entity = new StringEntity(params, Charset.forName("UTF-8"));
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        httpPost.setEntity(entity);
        CloseableHttpResponse response = null;
        String jsonString = null;
        try {

            response = httpclient.execute(httpPost);
            StatusLine status = response.getStatusLine();

            System.out.println("状态码：" + status.getStatusCode());
            HttpEntity responseEntity = response.getEntity();
            jsonString = EntityUtils.toString(responseEntity);
            System.out.println("结果==="+jsonString);
        }catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }catch (ClientProtocolException e){
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        jsonObject = JSONObject.parseObject(jsonString);
        return jsonObject;
    }

    public static JSONObject doPost222(String url, String params){

        CloseableHttpClient httpclient = HttpClients.createDefault();
        JSONObject jsonObject = null;
        HttpPost httpPost = new HttpPost(url);// 创建httpPost
//        httpPost.setHeader("Accept", "application/json");
//        httpPost.setHeader("Content-Type", "application/json");
//        String charSet = "UTF-8";
        StringEntity entity = new StringEntity(params, Charset.forName("UTF-8"));
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        httpPost.setEntity(entity);
        CloseableHttpResponse response = null;
        String jsonString = null;
        try {

            response = httpclient.execute(httpPost);
            StatusLine status = response.getStatusLine();

            System.out.println("状态码：" + status.getStatusCode());
            HttpEntity responseEntity = response.getEntity();
            jsonString = EntityUtils.toString(responseEntity);
            System.out.println("结果==="+jsonString);
        }catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }catch (ClientProtocolException e){
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        jsonObject = JSONObject.parseObject(jsonString);
        return jsonObject;
    }

    /**
     *
     * @param strUrl 请求地址
     * @param params 请求参数
     * @param method 请求方法
     * @return  网络请求字符串
     * @throws Exception
     */
    public static String net(String strUrl, Map params, String method) throws Exception {
        HttpURLConnection conn = null;
        BufferedReader reader = null;
        String rs = null;
        try {
            StringBuffer sb = new StringBuffer();
            if(method==null || method.equals("GET")){
                strUrl = strUrl+"?"+urlencode(params);
            }
            URL url = new URL(strUrl);
            conn = (HttpURLConnection) url.openConnection();
            if(method==null || method.equals("GET")){
                conn.setRequestMethod("GET");
            }else{
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
            }
            conn.setRequestProperty("User-agent", userAgent);
            conn.setUseCaches(false);
            conn.setConnectTimeout(DEF_CONN_TIMEOUT);
            conn.setReadTimeout(DEF_READ_TIMEOUT);
            conn.setInstanceFollowRedirects(false);
            conn.connect();
            if (params!= null && method.equals("POST")) {
                try {
                    DataOutputStream out = new DataOutputStream(conn.getOutputStream());
                    out.writeBytes(urlencode(params));
                } catch (Exception e) {
                    System.out.println("发生错误：" + e);
                }
            }
            InputStream is = conn.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, DEF_CHATSET));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sb.append(strRead);
            }
            rs = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
        return rs;
    }
    //将map型转为请求参数型
    public static String urlencode(Map<String,Object>data) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry i : data.entrySet()) {
            try {
                sb.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue()+"","UTF-8")).append("&");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
