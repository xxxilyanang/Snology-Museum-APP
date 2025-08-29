package com.example.gxcg.util;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

public class HttpUtil
{
    public static String getStringMsg(Map<String, String> params, String subPath, String method)
    {
        int responseCode = 0;
        //返回结果
        String result=null;
        //生成参数字符串的缓冲
        StringBuffer buffer = new StringBuffer();
        try {
            //如果有请求参数
            if(params != null && !params.isEmpty()){
                //遍历每一个请求参数键值对
                for(Map.Entry<String, String> entry : params.entrySet())
                {
                    buffer.append(entry.getKey()).append("=").
                            append(URLEncoder.encode(entry.getValue(),"utf-8")).
                            append("&");
                }
            }
            //删除最后一个字符&，最后会多一个
            if(buffer.length()!=0)
            {
                buffer.deleteCharAt(buffer.length()-1);
            }
            //将请求参数即查询字符串转为字节序列
            byte[] mydata = buffer.toString().getBytes();
            //创建URL
            URL url=new URL(Constant.ADD_PRE+subPath);
            Log.d("zzzzzzzzzzzzz", "getStringMsg: "+url);
            //打开连接
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            //设置超时参数
            connection.setConnectTimeout(3000);
            //表示从服务器获取数据
            connection.setDoInput(true);
            //表示向服务器写数据
            connection.setDoOutput(true);
            //设置请求方法 GET POST  二选一
            connection.setRequestMethod(method);
            //是否使用缓存
            connection.setUseCaches(false);
            //设置请求体的类型是文本类型
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            //设置请求体的长度
            connection.setRequestProperty("Content-Length", String.valueOf(mydata.length));
            connection.connect();

            //获得输出流，向服务器输出数据
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(mydata,0,mydata.length);

            //获得服务器响应的结果和状态码
            responseCode = connection.getResponseCode();
            Log.d("zzzzzzzzzzzzz", "getStringMsg: "+responseCode);

            if(responseCode == HttpURLConnection.HTTP_OK)
            {
                InputStream in=connection.getInputStream();
                int ch=0;
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                while((ch=in.read())!=-1)
                {
                    baos.write(ch);
                }
                byte[] bb=baos.toByteArray();
                baos.close();
                in.close();
                result=new String(bb,"UTF-8");
                result=result.replaceAll("\\r\\n","\n");
            }
        } catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        //Log.d("TAG-HttpU", "onItemClick:  " +  responseCode);
        return result;
    }

    //获取二进制类数据返回的方法（如图像、视频、音频数据）
    //请求参数键值对   请求子路径   请求方法-GET POST  二选一
    public static byte[] getBytesMsg(Map<String, String> params,String subPath,String method)
    {
        //返回结果
        byte[] result=null;
        //生成参数字符串的缓冲
        StringBuffer buffer = new StringBuffer();
        try {
            //如果有请求参数
            if(params != null&&!params.isEmpty())
            {
                //遍历每一个请求参数键值对
                for(Map.Entry<String, String> entry : params.entrySet())
                {
                    buffer.append(entry.getKey()).append("=").
                            append(URLEncoder.encode(entry.getValue(),"utf-8")).
                            append("&");
                }
            }

            //删除最后一个字符&，最后会多一个
            if(buffer.length() != 0)
            {
                buffer.deleteCharAt(buffer.length()-1);
            }
            //将请求参数即查询字符串转为字节序列
            byte[] mydata = buffer.toString().getBytes();
            //创建URL
            URL url=new URL(Constant.ADD_PRE+subPath);
            //打开连接
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            //设置超时参数
            connection.setConnectTimeout(3000);
            //表示从服务器获取数据
            connection.setDoInput(true);
            //表示向服务器写数据
            connection.setDoOutput(true);
            //设置请求方法 GET POST  二选一
            connection.setRequestMethod(method);
            //是否使用缓存
            connection.setUseCaches(false);
            //设置请求体的类型是文本类型
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            //设置请求体的长度
            connection.setRequestProperty("Content-Length", String.valueOf(mydata.length));
            connection.connect();

            //获得输出流，向服务器输出数据
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(mydata,0,mydata.length);
            //获得服务器响应的结果和状态码
            int responseCode = connection.getResponseCode();
            if(responseCode == HttpURLConnection.HTTP_OK)
            {
                InputStream in=connection.getInputStream();
                int ch=0;
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                while((ch=in.read())!=-1)
                {
                    baos.write(ch);
                }
                result=baos.toByteArray();
                baos.close();
                in.close();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }




    //上传文件获取文本类返回数据的方法
    //请求参数键值对  请求子路径  希望服务器保存的文件名  字节数据
    public static String upload(Map<String, String> params,String subPath,String fname,byte[] data)
    {
        //换行符
        final String newLine = "\r\n";
        //边界前缀
        final String boundaryPrefix = "--";
        //数据分隔线
        final String boundary = String.format("=========%s",System.currentTimeMillis());
        //返回结果
        String result=null;
        try {
            //创建URL
            URL url=new URL(Constant.ADD_PRE+subPath);
            //打开连接
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            //设置超时参数
            connection.setConnectTimeout(3000);
            //表示从服务器获取数据
            connection.setDoInput(true);
            //表示向服务器写数据
            connection.setDoOutput(true);
            //设置请求方法POST(上传文件只能使用POST方法)
            connection.setRequestMethod("POST");
            //是否使用缓存
            connection.setUseCaches(false);
            //设置为保持连接
            connection.setRequestProperty("connection", "Keep-Alive");
            //设置字符集编码
            connection.setRequestProperty("Charsert", "UTF-8");
            //设置请求类型并指定数据分隔字符串
            connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
            connection.connect();

            //获得输出流
            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            //参数字符串格式串
            String keyValue = "Content-Disposition: form-data; name=\"%s\"\r\n\r\n%s\r\n";
            //参数分隔字符串字节序列
            byte[] parameterLine= (boundaryPrefix+boundary+newLine).getBytes();
            //若要提交的参数键值对不为空
            if(params!=null&&params.size()>0)
            {
                //遍历每一对参数键值对
                for (Map.Entry<String, String> e : params.entrySet())
                {
                    //基于参数名称参数值和格式串生成此参数字符串并转为字节数组
                    byte[] keyValueBytes = String.format(keyValue,e.getKey(), MyConverter.escape(e.getValue())).getBytes();
                    //写出参数分隔
                    out.write(parameterLine);
                    //写出本参数
                    out.write(keyValueBytes);
                }
            }
            //组织文件数据参数头
            StringBuilder sb = new StringBuilder();
            sb.append(boundaryPrefix);
            sb.append(boundary);
            sb.append(newLine);
            //指定文件名和相关参数（这里的文件名是提交给服务器的，并不要求和本地实际文件名相同）
            sb.append("Content-Disposition: form-data;name=\"file\";filename=\"" + fname + "\"" + newLine);
            sb.append("Content-Type:application/octet-stream");
            //参数头设置完以后需要两个换行，然后才是参数内容
            sb.append(newLine);
            sb.append(newLine);
            //将参数头的数据写入到输出流中
            out.write(sb.toString().getBytes());
            //写出实际的文件数据
            out.write(data);
            //最后添加换行
            out.write(newLine.getBytes());
            //数据结束分隔线，即--加上boundary再加上--。
            byte[] end_data = (newLine + boundaryPrefix + boundary + boundaryPrefix + newLine).getBytes();
            //写出数据结束分隔
            out.write(end_data);
            //刷新留
            out.flush();
            //关闭流
            out.close();

            //获得服务器响应的结果和状态码
            int responseCode = connection.getResponseCode();
            if(responseCode == HttpURLConnection.HTTP_OK)
            {
                InputStream in=connection.getInputStream();
                int ch=0;
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                while((ch=in.read())!=-1)
                {
                    baos.write(ch);
                }
                byte[] bb=baos.toByteArray();
                baos.close();
                in.close();
                result=MyConverter.unescape(new String(bb,"UTF-8"));
                result=result.replaceAll("\\r\\n","\n");
            }
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return result;
    }
}
