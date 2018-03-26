package com.xuanli.oepcms.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

public class HttpRequest {
    /**
     * 向指定URL发送GET方法的请求
     * 
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     * 
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPostUTF8(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(),"utf-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }    
    /**
     * 向指定 URL 发送POST方法的请求
     * 
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPostGBK(String url, String param) {
    	PrintWriter out = null;
    	BufferedReader in = null;
    	String result = "";
    	try {
    		URL realUrl = new URL(url);
    		// 打开和URL之间的连接
    		URLConnection conn = realUrl.openConnection();
    		// 设置通用的请求属性
    		conn.setRequestProperty("accept", "*/*");
    		conn.setRequestProperty("connection", "Keep-Alive");
    		conn.setRequestProperty("user-agent",
    				"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
    		conn.setRequestProperty("Accept-Charset", "GBK");
    		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=GBK");
    		// 发送POST请求必须设置如下两行
    		conn.setDoOutput(true);
    		conn.setDoInput(true);
    		// 获取URLConnection对象对应的输出流
    		out = new PrintWriter(conn.getOutputStream());
    		// 发送请求参数
    		out.print(param);
    		// flush输出流的缓冲
    		out.flush();
    		// 定义BufferedReader输入流来读取URL的响应
    		in = new BufferedReader(
    				new InputStreamReader(conn.getInputStream(),"GBK"));
    		String line;
    		while ((line = in.readLine()) != null) {
    			result += line;
    		}
    	} catch (Exception e) {
    		System.out.println("发送 POST 请求出现异常！"+e);
    		e.printStackTrace();
    	}
    	//使用finally块来关闭输出流、输入流
    	finally{
    		try{
    			if(out!=null){
    				out.close();
    			}
    			if(in!=null){
    				in.close();
    			}
    		}
    		catch(IOException ex){
    			ex.printStackTrace();
    		}
    	}
    	return result;
    }    
    
    /** 
     * post请求(用于key-value格式的参数) 
     * @param url 
     * @param params 
     * @return 
     */  
    public static String doPost(String url, Map params){  
          
        BufferedReader in = null;    
        try {    
            // 定义HttpClient    
            HttpClient client = new DefaultHttpClient();    
            // 实例化HTTP方法    
            HttpPost request = new HttpPost();    
            request.setURI(new URI(url));  
              
            //设置参数  
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();   
            for (Iterator iter = params.keySet().iterator(); iter.hasNext();) {  
                String name = (String) iter.next();  
                String value = String.valueOf(params.get(name));  
                nvps.add(new BasicNameValuePair(name, value));  
                  
            }  
            request.setEntity(new UrlEncodedFormEntity(nvps,HTTP.UTF_8));  
              
            HttpResponse response = client.execute(request);    
            int code = response.getStatusLine().getStatusCode();  
            if(code == 200){    //请求成功  
                in = new BufferedReader(new InputStreamReader(response.getEntity()    
                        .getContent(),"utf-8"));  
                StringBuffer sb = new StringBuffer("");    
                String line = "";    
                String NL = System.getProperty("line.separator");    
                while ((line = in.readLine()) != null) {    
                    sb.append(line + NL);    
                }  
                  
                in.close();    
                  
                return sb.toString();  
            }  
            else{   //  
                System.out.println("状态码：" + code);  
                return null;  
            }  
        }  
        catch(Exception e){  
            e.printStackTrace();  
              
            return null;  
        }  
    }  
    
    
    
    //http://127.0.0.1:8080/service/sendgbk.do?parameter=测试
//    public static void main(String[] args) {
//		String url="http://127.0.0.1:8080/service/sendgbk.do";
//		String param="parameter=测试";
//    	String sendPostGBK = sendPostGBK(url, param);
//    	System.out.println(sendPostGBK);
//	}
    
}
