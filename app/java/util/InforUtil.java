package com.example.gxcg.util;

import android.util.Log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InforUtil {
    public static String result = "";//结果
    public static String url = "GetDataAndroid.jsp";
    public static byte[] datapic;//图片数据
    public static byte[] datavideo;//视频数据
    public static byte[] dataaudio;//音频数据
    //=========================================================================
    //根据用户所填写的账号密码 获取用户的真实密码【若账号密码出错，则返回空值】
    public static String selectUser(String ms)
    {
        try{

            Map<String,String> parameters=new HashMap<String,String>();
            parameters.put("action", Constant.selectUser);
            parameters.put("ms", ms);
            result = HttpUtil.getStringMsg(parameters,url,"POST");
        } catch(Exception e){
            e.printStackTrace();
        }
        result=MyConverter.unescape(result.trim());
        return result;
    }
    //判断新注册用户是否存在
    public static boolean isExistUser(String id)
    {
        try{
            Map<String,String> parameters=new HashMap<String,String>();
            parameters.put("action",Constant.isExistUser);
            parameters.put("ms", id);
            result=HttpUtil.getStringMsg(parameters,url,"POST");
        } catch(Exception e){
            e.printStackTrace();
        }
        if (result.equals("0")) {
            return false;//不存在
        } else {
            return true;//已存在
        }
    }
    //插入新注册的用户
    public static void insertUserAndroid(String msg)
    {
        try{
            Map<String,String> parameters=new HashMap<String,String>();
            parameters.put("action",Constant.insertUserAndroid);
            parameters.put("ms", msg);
            result=HttpUtil.getStringMsg(parameters,url,"POST");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    //根据用户ID获取指定用户密码
    public static List<String[]> getUserPass(String input_id)
    {
        try{
            Map<String,String> parameters=new HashMap<String,String>();
            parameters.put("action",Constant.getUserPass);
            parameters.put("ms", input_id);
            result=HttpUtil.getStringMsg(parameters,url,"POST");
        } catch(Exception e){
            e.printStackTrace();
        }
        result=MyConverter.unescape(result.trim());
        return StrListChange.StrToList(result);
    }
    //更新用户密码
    public static void updatePass(String msg)
    {
        try{
            Map<String,String> parameters=new HashMap<String,String>();
            parameters.put("action",Constant.updatePass);
            parameters.put("ms", msg);
            result=HttpUtil.getStringMsg(parameters,url,"POST");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    //=========================================================================



    //获取用户账号密码等各类信息
    public static String[] getUserInfor(String uid,String pwd)
    {
        //System.out.println( "================getUserInfor====================" );
        try{
            Map<String,String> parameters=new HashMap<String,String>();
            parameters.put("action", Constant.getUserInfor);
            parameters.put("ms", uid+"<#>"+pwd);
            result= HttpUtil.getStringMsg(parameters,url,"POST");
        } catch(Exception e){
            e.printStackTrace();
        }
        result=MyConverter.unescape(result.trim());
        //System.out.println(result);
        //System.out.println( "================getUserInfor===================="+result);
        return StrListChange.StrToArray(result);
    }
    public static String[] getSinglelsglerXx(String uid)
    {
        System.out.println( "================getUserInfor====================" );
        try{
            Map<String,String> parameters=new HashMap<String,String>();
            parameters.put("action", Constant.getSinglelsglerXx);
            parameters.put("ms", uid);
            result= HttpUtil.getStringMsg(parameters,url,"POST");
        } catch(Exception e){
            e.printStackTrace();
        }
        result=MyConverter.unescape(result.trim());
        System.out.println(result);
        System.out.println( "================getUserInfor===================="+result);
        return StrListChange.StrToArray(result);
    }
    //获取用户账号密码等各类信息
    public static String[] getyzInfor(String uid,String pwd)
    {
        System.out.println( "================getyzInfor====================" );
        try{
            Map<String,String> parameters=new HashMap<String,String>();
            parameters.put("action", Constant.getyzInfor);
            parameters.put("ms", uid+"<#>"+pwd);
            result= HttpUtil.getStringMsg(parameters,url,"POST");
        } catch(Exception e){
            e.printStackTrace();
        }
        result=MyConverter.unescape(result.trim());
        System.out.println(result);
        System.out.println( "================getyzInfor===================="+result);
        return StrListChange.StrToArray(result);
    }
    //判断新注册用户是否存在
//    public static boolean isExistUser(String id)
//    {System.out.println( "================isExistUser====================" );
//        try{
//            Map<String,String> parameters=new HashMap<String,String>();
//            parameters.put("action",Constant.isExistUser);
//            parameters.put("ms", id);
//            result=HttpUtil.getStringMsg(parameters,url,"POST");
//        } catch(Exception e){
//            e.printStackTrace();
//        }
//        if (result.equals("0")) {
//            return false;//不存在
//        } else {
//            return true;//已存在
//        }
//    }
    //插入新注册的用户
    public static void insertUser(String msg)
    {System.out.println( "================insertUser====================" );
        try{
            Map<String,String> parameters=new HashMap<String,String>();
            parameters.put("action",Constant.insertUser);
            parameters.put("ms", msg);
            result=HttpUtil.getStringMsg(parameters,url,"POST");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    //插入新注册的用户
    public static void insertUserPic(String msg)
    {System.out.println( "================insertUser====================" );
        try{
            Map<String,String> parameters=new HashMap<String,String>();
            parameters.put("action",Constant.insertUserPic);
            parameters.put("ms", msg);
            result=HttpUtil.getStringMsg(parameters,url,"POST");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    //获取图片
    public static byte[] getPic(String picPath, String picName)
    {
        try{
            Map<String, String> params = new HashMap<String, String>();
            datapic= HttpUtil.getBytesMsg(params,picPath+"/"+picName,"POST");
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return datapic;
    }
    //获取视频
    public static byte[] getVedio(String videoName)
    {
        System.out.println( "================getVideo====================" );
        try{
            Map<String, String> params = new HashMap<String, String>();
            datavideo= HttpUtil.getBytesMsg(params,"video/"+videoName,"POST");
        }
        catch(Exception e){
            e.printStackTrace();
        }
        System.out.println( "================getVideo====================" );
        return datavideo;
    }
    //获取用户账号密码等各类信息
    public static void  updateqianming(String msg)
    {
        System.out.println( "================updateqianming===================="+msg );
        try{
            Map<String,String> parameters=new HashMap<String,String>();
            parameters.put("action",Constant.updateqianming);
            parameters.put("ms", msg);
            result=HttpUtil.getStringMsg(parameters,url,"POST");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    //获取用户账号密码等各类信息
    public static void  updatesf(String msg)
    {
        System.out.println( "================updatesf===================="+msg );
        try{
            Map<String,String> parameters=new HashMap<String,String>();
            parameters.put("action",Constant.updatesf);
            parameters.put("ms", msg);
            result=HttpUtil.getStringMsg(parameters,url,"POST");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    public static void  updatesex(String msg)
    {
        System.out.println( "================updatesex===================="+msg );
        try{
            Map<String,String> parameters=new HashMap<String,String>();
            parameters.put("action",Constant.updatesex);
            parameters.put("ms", msg);
            result=HttpUtil.getStringMsg(parameters,url,"POST");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    public static void  updateold(String msg)
    {
        System.out.println( "================updatesex===================="+msg );
        try{
            Map<String,String> parameters=new HashMap<String,String>();
            parameters.put("action",Constant.updateold);
            parameters.put("ms", msg);
            result=HttpUtil.getStringMsg(parameters,url,"POST");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    public static void  updatename(String msg)
    {
        System.out.println( "================updatename===================="+msg );
        try{
            Map<String,String> parameters=new HashMap<String,String>();
            parameters.put("action",Constant.updatename);
            parameters.put("ms", msg);
            result=HttpUtil.getStringMsg(parameters,url,"POST");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    public static String gettxmaxid()
    {System.out.println( "================getcy_name====================" );
        try{
            Map<String,String> parameters=new HashMap<String,String>();
            parameters.put("action",Constant.gettxmaxid);
            result=HttpUtil.getStringMsg(parameters,url,"POST");
        } catch(Exception e) {
            e.printStackTrace();
        }
        result=MyConverter.unescape(result.trim());
        return result;
    }
    public static String getlsglmaxid()
    {System.out.println( "================getcy_name====================" );
        try{
            Map<String,String> parameters=new HashMap<String,String>();
            parameters.put("action",Constant.getlsglmaxid);
            result=HttpUtil.getStringMsg(parameters,url,"POST");
        } catch(Exception e) {
            e.printStackTrace();
        }
        result=MyConverter.unescape(result.trim());
        return result;
    }
    //获取所有的咨询反馈信息
    public static List<String[]> getSortjdal(String id)
    {System.out.println( "================getSortuser====================" );
        try{
            Map<String,String> parameters=new HashMap<String,String>();
            parameters.put("action",Constant.getSortjdal);
            parameters.put( "ms",id );
            result=HttpUtil.getStringMsg(parameters,url,"POST");
        } catch(Exception e) {
            e.printStackTrace();
        }
        result=MyConverter.unescape(result.trim());
        return StrListChange.StrToList(result);
    }

    //获取用户部分信息
    public static List<String[]> getalljdal(String id)
    {System.out.println( "================getuser====================" );
        try{
            Map<String,String> parameters=new HashMap<String,String>();
            parameters.put("action",Constant.getalljdal);
            parameters.put( "ms",id );
            result=HttpUtil.getStringMsg(parameters,url,"POST");
        } catch(Exception e) {
            e.printStackTrace();
        }
        result=MyConverter.unescape(result.trim());
        return StrListChange.StrToList(result);
    }
    //获取所有的咨询反馈信息
    public static List<String[]> getjdalall()
    {System.out.println( "================getchayeall===================="+StrListChange.StrToList(result));
        try{
            Map<String,String> parameters=new HashMap<String,String>();
            parameters.put("action",Constant.getjdalall);
            result=HttpUtil.getStringMsg(parameters,url,"POST");
        } catch(Exception e) {
            e.printStackTrace();
        }
        result=MyConverter.unescape(result.trim());
        return StrListChange.StrToList(result);
    }
    public static List<String[]> getSinglejdalAllInfo(String msg)
    {System.out.println( "================getSingleChayeAllInfo====================" );
        try{
            Map<String,String> parameters=new HashMap<String,String>();
            parameters.put("action",Constant.getSinglejdalAllInfo);
            parameters.put( "ms",msg );
            result=HttpUtil.getStringMsg(parameters,url,"POST");
        } catch(Exception e) {
            e.printStackTrace();
        }
        result=MyConverter.unescape(result.trim());
        return StrListChange.StrToList(result);
    }
    public static String getalfl_name(String msg)
    {System.out.println( "================getcy_name====================" );
        try{
            Map<String,String> parameters=new HashMap<String,String>();
            parameters.put("action",Constant.getalfl_name);
            parameters.put( "ms",msg );
            result=HttpUtil.getStringMsg(parameters,url,"POST");
        } catch(Exception e) {
            e.printStackTrace();
        }
        result=MyConverter.unescape(result.trim());
        return result;
    }
    //获取所有的公告信息
    public static List<String[]> getallgg()
    {System.out.println( "================getallgg====================" );
        try{
            Map<String,String> parameters=new HashMap<String,String>();
            parameters.put("action",Constant.getallgg);
            result=HttpUtil.getStringMsg(parameters,url,"POST");
        } catch(Exception e) {
            e.printStackTrace();
        }
        result=MyConverter.unescape(result.trim());
        return StrListChange.StrToList(result);
    }
    public static List<String[]> getptinfo(String msg)
    {System.out.println( "================getzoneinfo====================" );
        try{
            Map<String,String> parameters=new HashMap<String,String>();
            parameters.put("action",Constant.getptinfo);
            parameters.put( "ms",msg );
            result=HttpUtil.getStringMsg(parameters,url,"POST");
        } catch(Exception e) {
            e.printStackTrace();
        }
        result=MyConverter.unescape(result.trim());
        return StrListChange.StrToList(result);
    }
    //获取所有的公告信息
    public static List<String[]> getallsfjs()
    {System.out.println( "================getallsfjs====================" );
        try{
            Map<String,String> parameters=new HashMap<String,String>();
            parameters.put("action",Constant.getallsfjs);
            result=HttpUtil.getStringMsg(parameters,url,"POST");
        } catch(Exception e) {
            e.printStackTrace();
        }
        result=MyConverter.unescape(result.trim());
        return StrListChange.StrToList(result);
    }
    public static List<String[]> getsfjsinfo(String msg)
    {System.out.println( "================getsfjsinfo====================" );
        try{
            Map<String,String> parameters=new HashMap<String,String>();
            parameters.put("action",Constant.getsfjsinfo);
            parameters.put( "ms",msg );
            result=HttpUtil.getStringMsg(parameters,url,"POST");
        } catch(Exception e) {
            e.printStackTrace();
        }
        result=MyConverter.unescape(result.trim());
        return StrListChange.StrToList(result);
    }
    //获取用户的照片名字
    public static String getuserpicname(String userid)
    {
        System.out.println( "================getuserpicname====================" );
        try{
            Map<String,String> parameters=new HashMap<String,String>();
            parameters.put("action",Constant.getuserpicname);
            parameters.put( "ms",userid );
            result=HttpUtil.getStringMsg(parameters,url,"POST");
        } catch(Exception e) {
            e.printStackTrace();
        }
        result=MyConverter.unescape(result.trim());
        return result;
    }
    //获取所有的公告信息
    public static List<String[]> getallyjfk(String msg)
    {System.out.println( "================getsfjsinfo====================" );
        try{
            Map<String,String> parameters=new HashMap<String,String>();
            parameters.put("action",Constant.getallyjfk);
            parameters.put( "ms",msg );
            result=HttpUtil.getStringMsg(parameters,url,"POST");
        } catch(Exception e) {
            e.printStackTrace();
        }
        result=MyConverter.unescape(result.trim());
        return StrListChange.StrToList(result);
    }
    public static String getallyjfkmax()
    {System.out.println( "================getallyjfkmax====================" );
        try{
            Map<String,String> parameters=new HashMap<String,String>();
            parameters.put("action",Constant.getallyjfkmax);
            result=HttpUtil.getStringMsg(parameters,url,"POST");
        } catch(Exception e) {
            e.printStackTrace();
        }
        result=MyConverter.unescape(result.trim());
        return result;
    }
    public static void insertyjfk(String message)
    {System.out.println( "================insertchayou====================" );
        try{
            Map<String,String> parameters=new HashMap<String,String>();
            parameters.put("action",Constant.insertyjfk);
            parameters.put( "ms",message );
            result=HttpUtil.getStringMsg(parameters,url,"POST");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    public static void insertlsgl(String message)
    {System.out.println( "================insertchayou====================" );
        try{
            Map<String,String> parameters=new HashMap<String,String>();
            parameters.put("action",Constant.insertlsgl);
            parameters.put( "ms",message );
            result=HttpUtil.getStringMsg(parameters,url,"POST");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    public static List<String[]> getlvcsall(String msg)
    {System.out.println( "================getlvcsall====================" );
        try{
            Map<String,String> parameters=new HashMap<String,String>();
            parameters.put("action",Constant.getlvcsall);
            parameters.put( "ms",msg );
            result=HttpUtil.getStringMsg(parameters,url,"POST");
        } catch(Exception e) {
            e.printStackTrace();
        }
        result=MyConverter.unescape(result.trim());
        return StrListChange.StrToList(result);
    }
    public static String getlvpic(String lsid)
    {
        System.out.println( "================getlvpic===================="+result);
        try{
            Map<String,String> parameters=new HashMap<String,String>();
            parameters.put("action",Constant.getlvpic);
            parameters.put( "ms",lsid);
            result=HttpUtil.getStringMsg(parameters,url,"POST");
        } catch(Exception e) {
            e.printStackTrace();
        }
        result=MyConverter.unescape(result.trim());
        return result;
    }
    public static List<String[]> getyzcsall(String msg)
    {System.out.println( "================getlvcsall====================" );
        try{
            Map<String,String> parameters=new HashMap<String,String>();
            parameters.put("action",Constant.getyzcsall);
            parameters.put( "ms",msg );
            result=HttpUtil.getStringMsg(parameters,url,"POST");
        } catch(Exception e) {
            e.printStackTrace();
        }
        result=MyConverter.unescape(result.trim());
        return StrListChange.StrToList(result);
    }
    public static String getyzpic(String lsid)
    {
        System.out.println( "================getlvpic===================="+result);
        try{
            Map<String,String> parameters=new HashMap<String,String>();
            parameters.put("action",Constant.getyzpic);
            parameters.put( "ms",lsid);
            result=HttpUtil.getStringMsg(parameters,url,"POST");
        } catch(Exception e) {
            e.printStackTrace();
        }
        result=MyConverter.unescape(result.trim());
        return result;
    }
    public static List<String[]> getftnrall(String msg)
    {System.out.println( "================getftnrall====================" );
        try{
            Map<String,String> parameters=new HashMap<String,String>();
            parameters.put("action",Constant.getftnrall);
            parameters.put( "ms",msg );
            result=HttpUtil.getStringMsg(parameters,url,"POST");
        } catch(Exception e) {
            e.printStackTrace();
        }
        result=MyConverter.unescape(result.trim());
        return StrListChange.StrToList(result);
    }
    public static List<String[]> getftnrxiaall(String msg)
    {System.out.println( "================getftnrxiaall====================");
        try{
            Map<String,String> parameters=new HashMap<String,String>();
            parameters.put("action",Constant.getftnrxiaall);
            parameters.put( "ms",msg );
            result=HttpUtil.getStringMsg(parameters,url,"POST");
        } catch(Exception e) {
            e.printStackTrace();
        }
        result=MyConverter.unescape(result.trim());
        return StrListChange.StrToList(result);
    }
    //上载图片
    public static void insertUserpic(byte[] data,String picname)
    {
        System.out.println( "================insertUserpic====================" );
        try {
            Map<String,String> params=new HashMap<String,String>();
            params.put("msg","????????"+"UP_"+picname);
            String msg= HttpUtil.upload(params,"UploadServletAndroid",picname,data);
        } catch(Exception e){
            e.printStackTrace();
        }
        System.out.println( "================insertUserpic====================" );
    }
    //上载图片
    public static void insertlsglpic(byte[] data,String picname)
    {
        System.out.println( "================insertUserpic====================" );
        try {
            Map<String,String> params=new HashMap<String,String>();
            params.put("msg","????????"+"UP_"+picname);
            String msg= HttpUtil.upload(params,"UploadServletAndroid",picname,data);
        } catch(Exception e){
            e.printStackTrace();
        }
        System.out.println( "================insertUserpic====================" );
    }
    //修改用户头像
    public static String UpdateUserPic
    (
            String users_pic,String users_id
    )
    {
        System.out.println( "================UpdateUserPic====================" );
        try{
            Map<String,String> parameters=new HashMap<String,String>();
            parameters.put("action", Constant.UpdateUserPic);
            parameters.put("ms",users_pic+"<#>"+users_id);
            result= HttpUtil.getStringMsg(parameters,url,"POST");
        } catch(Exception e){
            e.printStackTrace();
        }
        result=MyConverter.unescape(result.trim());
        System.out.println( "================updateUserTelInfor===================="+result );
        return result;
    }
    //修改用户头像
    public static String UpdatelsglPic1
    (
            String users_pic,String users_id
    )
    {
        System.out.println( "================UpdatelsglPic1====================" );
        try{
            Map<String,String> parameters=new HashMap<String,String>();
            parameters.put("action", Constant.UpdatelsglPic1);
            parameters.put("ms",users_pic+"<#>"+users_id);
            result= HttpUtil.getStringMsg(parameters,url,"POST");
        } catch(Exception e){
            e.printStackTrace();
        }
        result=MyConverter.unescape(result.trim());
        System.out.println( "================updateUserTelInfor===================="+result );
        return result;
    }
    //修改用户头像
    public static String UpdatelsglPic2
    (
            String users_pic,String users_id
    )
    {
        System.out.println( "================UpdatelsglPic2====================" );
        try{
            Map<String,String> parameters=new HashMap<String,String>();
            parameters.put("action", Constant.UpdatelsglPic2);
            parameters.put("ms",users_pic+"<#>"+users_id);
            result= HttpUtil.getStringMsg(parameters,url,"POST");
        } catch(Exception e){
            e.printStackTrace();
        }
        result=MyConverter.unescape(result.trim());
        System.out.println( "================updateUserTelInfor===================="+result );
        return result;
    }
    //获取全部文章
    public static List<String[]> getwz() {
        System.out.println( "================getwz====================" );
        try{
            Map<String,String> parameters=new HashMap<String,String>();
            parameters.put("action",Constant.getwz);
            result=HttpUtil.getStringMsg(parameters,url,"POST");
        } catch(Exception e) {
            e.printStackTrace();
        }
        result=MyConverter.unescape(result.trim());
        return StrListChange.StrToList(result);
    }
    //获取单篇文章对应图片
    public static List<String[]> getwzpic(String id) {
        System.out.println( "================getwzpic====================" );
        try{
            Map<String,String> parameters=new HashMap<String,String>();
            parameters.put("action",Constant.getwzpic);
            parameters.put( "ms",id );
            result=HttpUtil.getStringMsg(parameters,url,"POST");
        } catch(Exception e) {
            e.printStackTrace();
        }
        result=MyConverter.unescape(result.trim());
        return StrListChange.StrToList(result);
    }
    //获取单篇文章
    public static List<String[]> getSinglewz(String msg) {
        System.out.println( "================getSinglewz====================" );
        try{
            Map<String,String> parameters=new HashMap<String,String>();
            parameters.put("action",Constant.getSinglewz);
            parameters.put( "ms",msg );
            result=HttpUtil.getStringMsg(parameters,url,"POST");
        } catch(Exception e) {
            e.printStackTrace();
        }
        result=MyConverter.unescape(result.trim());
        return StrListChange.StrToList(result);
    }
    public static String[] getSearchcyNames() {
        System.out.println( "================getSearchcyNames====================" );
        try{
            Map<String,String> parameters=new HashMap<String,String>();//创建Map引用
            parameters.put("action",Constant.getSearchcyNames);//添加传送的参数信息
            result=HttpUtil.getStringMsg(parameters,url,"POST");//拿到返回的信息
        } catch(Exception e) {//捕获异常
            e.printStackTrace();
        }
        result=MyConverter.unescape(result.trim());
        return StrListChange.StrToArray(result);//将名称转成数组返回
    }
    public static List<String[]> getzjmfdForSraech(String msg) {
        System.out.println( "================getzjmfdForjtf====================");
        try{
            Map<String,String> parameters=new HashMap<String,String>();
            parameters.put("action",Constant.getzjmfdForSraech);
            parameters.put( "ms",msg );
            result=HttpUtil.getStringMsg(parameters,url,"POST");
        } catch(Exception e) {
            e.printStackTrace();
        }
        result=MyConverter.unescape(result.trim());
        return StrListChange.StrToList(result);
    }
    //获取用户密码
//    public static List<String[]> getUserPass(String inputid) {System.out.println( "================getUserPass====================" );
//        try{
//            Map<String,String> parameters=new HashMap<String,String>();
//            parameters.put("action",Constant.getUserPass);
//            parameters.put("ms", inputid);
//            result=HttpUtil.getStringMsg(parameters,url,"POST");
//        } catch(Exception e){
//            e.printStackTrace();
//        }
//        result=MyConverter.unescape(result.trim());
//        return StrListChange.StrToList(result);
//    }
//    public static void updatePass(String msg)
//    {
//        System.out.println( "================updatePass====================" );
//        try{
//            Map<String,String> parameters=new HashMap<String,String>();
//            parameters.put("action",Constant.updatePass);
//            parameters.put("ms", msg);
//        } catch(Exception e) {
//            e.printStackTrace();
//        }
//    }
    //获取电台的信息
    public static String getAllDtInfor() {
        System.out.println( "================getDtInfor====================" );
        try{
            Map<String,String> parameters=new HashMap<String,String>();
            parameters.put("action",Constant.GetDTAllInfor);
            result=HttpUtil.getStringMsg(parameters,url,"POST");
        } catch(Exception e) {
            e.printStackTrace();
        }
        result=MyConverter.unescape(result.trim());
        return result;
    }

    //获取律师介绍的所有信息
    public static String getAllLsInfor() {
        System.out.println( "================getLsInfor====================" );
        try{
            Map<String,String> parameters=new HashMap<String,String>();
            parameters.put("action",Constant.GetLSAllInfor);
            result=HttpUtil.getStringMsg(parameters,url,"POST");
        } catch(Exception e) {
            e.printStackTrace();
        }
        result=MyConverter.unescape(result.trim());
        return result;
    }
    //获取法律援助的所有信息
    public static String getAllFlyzInfor() {
        System.out.println( "================getAllFlyzInfor====================" );
        try{
            Map<String,String> parameters=new HashMap<String,String>();
            parameters.put("action",Constant.GetFlyzAllInfor);
            result=HttpUtil.getStringMsg(parameters,url,"POST");
        } catch(Exception e) {
            e.printStackTrace();
        }
        result=MyConverter.unescape(result.trim());
        return result;
    }
    //获取视频的所有信息
    public static String getAllSPInfor() {
        System.out.println( "================getAllSPInfor====================" );
        try{
            Map<String,String> parameters=new HashMap<String,String>();
            parameters.put("action",Constant.GetSPAllInfor);
            result=HttpUtil.getStringMsg(parameters,url,"POST");
        } catch(Exception e) {
            e.printStackTrace();
        }
        result=MyConverter.unescape(result.trim());
        return result;
    }
    //获取法律援助的所有评论
    public static String getAllFlyzPLInfor(String ms) {
        System.out.println( "================getAllSPInfor====================" );
        try{
            Map<String,String> parameters=new HashMap<String,String>();
            parameters.put("action",Constant.GetFLYZPLAllInfor);
            parameters.put("ms",ms);
            result=HttpUtil.getStringMsg(parameters,url,"POST");
        } catch(Exception e) {
            e.printStackTrace();
        }
        result=MyConverter.unescape(result.trim());
        return result;
    }
    //添加法律援助的所有评论
    public static String addAllFlyzPLInfor(String yh_id,String flyzzx_id,String pj_nr) {
        System.out.println( "================addAllSPInfor====================" );
        try{
            Map<String,String> parameters=new HashMap<String,String>();
            parameters.put("action",Constant.AddFlyzPJInfor);
            parameters.put("ms",yh_id+"<#>"+flyzzx_id+"<#>"+pj_nr);
            result=HttpUtil.getStringMsg(parameters,url,"POST");
        } catch(Exception e) {
            e.printStackTrace();
        }
        result=MyConverter.unescape(result.trim());
        return result;
    }
    //添加电台收藏
    public static String addDTSCInfor(String yh_id,String dt_id) {
        System.out.println( "================addDTSCInfor====================" );
        try{
            Map<String,String> parameters=new HashMap<String,String>();
            parameters.put("action",Constant.AddDtSCInfor);
            parameters.put("ms",yh_id+"<#>"+dt_id);
            result=HttpUtil.getStringMsg(parameters,url,"POST");
        } catch(Exception e) {
            e.printStackTrace();
        }
        result=MyConverter.unescape(result.trim());
        return result;
    }
    //添加律师收藏
    public static String addLSSCInfor(String yh_id,String ls_id) {
        System.out.println( "================addLSSCInfor====================" );
        try{
            Map<String,String> parameters=new HashMap<String,String>();
            parameters.put("action",Constant.AddLSSCInfor);
            parameters.put("ms",yh_id+"<#>"+ls_id);
            result=HttpUtil.getStringMsg(parameters,url,"POST");
        } catch(Exception e) {
            e.printStackTrace();
        }
        result=MyConverter.unescape(result.trim());
        return result;
    }
    public static List<String []> getSearchallName() {System.out.println( "================getSearchallName====================" );
        try{
            Map<String,String> parameters=new HashMap<String,String>();
            parameters.put("action",Constant.getSearchallName);
            result=HttpUtil.getStringMsg(parameters,url,"POST");
        } catch(Exception e) {
            e.printStackTrace();
        }
        result=MyConverter.unescape(result.trim());
        return StrListChange.StrToList(result);
    }

    public static List<String []> getAndroidQuestion(){
        try{

            Map<String,String> parameters=new HashMap<String,String>();
            parameters.put("action", Constant.getAndroidQuestion);
            result = HttpUtil.getStringMsg(parameters,url,"POST");
        } catch(Exception e){
            e.printStackTrace();
        }
        result=MyConverter.unescape(result.trim());
        return StrListChange.StrToList(result);
    }
    //-----23_9_27_Yang
    //获取视频课程组的姓名信息
    public static List<String[]> getCourseGroupData()
    {
        try{
            Map<String,String> parameters=new HashMap<String,String>();
            parameters.put("action",Constant.getCourseGroupData);
            result=HttpUtil.getStringMsg(parameters, url, "POST");
        } catch(Exception e) {
            e.printStackTrace();
        }
        result=MyConverter.unescape(result.trim());
        return StrListChange.StrToList(result);
    }
    //根据某个课程组ID获取所有其下的所有课程ID
    public static List<String[]> getVideoCourseIdByGroupId(String id)
    {
        try{
            Map<String,String> parameters=new HashMap<String,String>();
            parameters.put("action",Constant.getVideoCourseIdByGroupId);
            parameters.put( "ms",id );
            result=HttpUtil.getStringMsg(parameters,url,"POST");
        } catch(Exception e) {
            e.printStackTrace();
        }
        result=MyConverter.unescape(result.trim());
        return StrListChange.StrToList(result);
    }
    //根据课程组下的课程ID获取对应的全部信息
    public static List<String[]> getVideoCourseAllInfoById(String id)
    {
        try{
            Map<String,String> parameters=new HashMap<String,String>();
            parameters.put("action",Constant.getVideoCourseAllInfoById);
            parameters.put( "ms",id );
            result=HttpUtil.getStringMsg(parameters,url,"POST");
        } catch(Exception e) {
            e.printStackTrace();
        }
        result=MyConverter.unescape(result.trim());
        return StrListChange.StrToList(result);
    }
    //根据经史子集类称获取里面的每个书籍
    public static List<String[]> getClassicsJingData(String groupName)
    {
        try {
            Map<String,String> parameters=new HashMap<String,String>();
            parameters.put("action",Constant.getClassicsJingData);
            parameters.put( "ms", groupName);
            result=HttpUtil.getStringMsg(parameters,url,"POST");
        } catch(Exception e) {
            e.printStackTrace();
        }
        result=MyConverter.unescape(result.trim());
            return StrListChange.StrToList(result);
    }
    //获取Book相关的txt文件信息
    public static String getBookTxtRelative(String picPath, String picName)
    {
        String content = null;
        try{
            Map<String, String> params = new HashMap<String, String>();
            content = HttpUtil.getStringMsg(params,picPath+"/"+picName,"POST");
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return content;
    }
    //2023-09-29-朝阳
    //获取答疑（话题）的所有信息
    public static String getAllDyInfor() {
        try{
            Map<String,String> parameters=new HashMap<String,String>();
            parameters.put("action",Constant.GetDYAllInfor);
            result=HttpUtil.getStringMsg(parameters,url,"POST");
        } catch(Exception e) {
            e.printStackTrace();
        }
        result=MyConverter.unescape(result.trim());
        return result;
    }
    //获取话题下的所有评论
    public static String getAllDyComments(String id) {
        System.out.println( "================getAllDyComments====================" );
        try{
            Map<String,String> parameters=new HashMap<String,String>();
            parameters.put("action",Constant.GetDYAllComments);
            parameters.put("ms", id);
            result=HttpUtil.getStringMsg(parameters,url,"POST");
        } catch(Exception e) {
            e.printStackTrace();
        }
        result=MyConverter.unescape(result.trim());
        return result;
    }
    //2023-09-30 朝阳
    //添加话题
    public static String addAllFldyInfor(String yh_id,String tm,String nr) {
        try{
            Map<String,String> parameters=new HashMap<String,String>();
            parameters.put("action",Constant.AddDYInfor);
            parameters.put("ms",yh_id+"<#>"+tm+"<#>"+nr);
            result=HttpUtil.getStringMsg(parameters,url,"POST");
        } catch(Exception e) {
            e.printStackTrace();
        }
        result=MyConverter.unescape(result.trim());
        return result;
    }
    //添加话题评论
    public static String insertDYComments(String topic_id,String nr,String yh_id) {
        try{
            Map<String,String> parameters=new HashMap<String,String>();
            parameters.put("action",Constant.InsertDYComments);
            parameters.put("ms",topic_id+"<#>"+nr+"<#>"+yh_id);
            result=HttpUtil.getStringMsg(parameters,url,"POST");
        } catch(Exception e) {
            e.printStackTrace();
        }
        result=MyConverter.unescape(result.trim());
        return result;
    }
    //获取图片（专为获取用户头像设计）
    public static byte[] getPicbyEntirelyPath(String picName)
    {
        try{
            Map<String, String> params = new HashMap<String, String>();
            datapic= HttpUtil.getBytesMsg(params, picName,"POST");
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return datapic;
    }
    //2023_10_1_Yang
    //如：根据论语ID找论语这本书下的所有篇章信息
    public static List<String[]> getClassicsItemsData(String classics_items_id)
    {
        try {
            Map<String,String> parameters=new HashMap<String,String>();
            parameters.put("action",Constant.getClassicsItemsData);
            parameters.put( "ms", classics_items_id);
            result=HttpUtil.getStringMsg(parameters,url,"POST");
        } catch(Exception e) {
            e.printStackTrace();
        }
        result=MyConverter.unescape(result.trim());
        return StrListChange.StrToList(result);
    }
    //2023_10_2_Yang
    //根据单个视频下的短视频:
    public static List<String[]> getCourseVideoBriefData(String course_video_id)
    {
        try {
            Map<String,String> parameters=new HashMap<String,String>();
            parameters.put("action",Constant.getCourseVideoBriefData);
            parameters.put( "ms", course_video_id);
            result=HttpUtil.getStringMsg(parameters,url,"POST");
        } catch(Exception e) {
            e.printStackTrace();
        }
        result=MyConverter.unescape(result.trim());
        return StrListChange.StrToList(result);
    }
    //2023_10_2_chao
    //获取视频下所有评论
    public static String getAllVideoComments(String id) {
        try{
            Map<String,String> parameters=new HashMap<String,String>();
            parameters.put("action",Constant.GetAllVideoComments);
            parameters.put("ms", id);
            result=HttpUtil.getStringMsg(parameters,url,"POST");
        } catch(Exception e) {
            e.printStackTrace();
        }
        result=MyConverter.unescape(result.trim());
        return result;
    }
    //2023_10_2_yu
    //获取登录用户信息
    public static String[] getLoginUserInfo(String uid){
        try{
            Map<String,String> parameters=new HashMap<String,String>();
            parameters.put("action", Constant.getLoginUserInfo);
            parameters.put("ms", uid);
            result= HttpUtil.getStringMsg(parameters,url,"POST");
        } catch(Exception e){
            e.printStackTrace();
        }
        result=MyConverter.unescape(result.trim());
        return StrListChange.StrToArray(result);
    }

    public static void insertUserFeedback(String feedback){
        String msg = Constant.UserId+"<#>"+feedback+"<#>"+"你的反馈我们已经收到";
        try{
            Map<String,String> parameters=new HashMap<String,String>();
            parameters.put("action",Constant.insertUserFeedback);
            parameters.put("ms", msg);
            result=HttpUtil.getStringMsg(parameters,url,"POST");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static List<String[]> getAllUserFeedbackInfo(){
        try{
            Map<String,String> parameters=new HashMap<String,String>();
            parameters.put("action", Constant.getAllUserFeedbackInfo);
            result = HttpUtil.getStringMsg(parameters,url,"POST");
        } catch(Exception e){
            e.printStackTrace();
        }
        result=MyConverter.unescape(result.trim());
        return StrListChange.StrToList(result);
    }

    public static List<String[]> getUserFeedbackResult(String userId){
        try{
            Map<String,String> parameters=new HashMap<String,String>();
            parameters.put("action", Constant.getUserFeedbackResult);
            parameters.put("ms",userId);
            result = HttpUtil.getStringMsg(parameters,url,"POST");
        } catch(Exception e){
            e.printStackTrace();
        }
        result=MyConverter.unescape(result.trim());
        return StrListChange.StrToList(result);
    }

    public static void updateUserPic(String fileName , String userId){
        System.out.println("fake run run");
        try{
            Map<String,String> parameters=new HashMap<String,String>();
            parameters.put("action", Constant.updateUserPic);
            parameters.put("ms",userId+"<#>"+fileName);
            result= HttpUtil.getStringMsg(parameters,url,"POST");
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void uploadUserHead(String picName , byte[] data) {
        System.out.println(picName+"fake");
        try {
            Map<String,String> params= new HashMap<String,String>();
            params.put("msg",picName);
            String msg= HttpUtil.upload(params,"UploadServletAndroidUserHead",picName,data);
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void updateUserInfo(String[] info){
        String msg = "";
        for (String s:info) {
            msg+=s+"<#>";
        }
        msg.substring(0,msg.length()-3);

        try{
            Map<String,String> parameters=new HashMap<String,String>();
            parameters.put("action",Constant.updateUserInfo);
            parameters.put("ms", msg);
            result=HttpUtil.getStringMsg(parameters,url,"POST");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static List<String[]> getCollection(String userId , String sort){
        String msg = userId+"<#>"+sort;
        try{
            Map<String,String> parameters=new HashMap<String,String>();
            parameters.put("action", Constant.getCollection);
            parameters.put("ms",msg);
            result = HttpUtil.getStringMsg(parameters,url,"POST");
        } catch(Exception e){
            e.printStackTrace();
        }
        result=MyConverter.unescape(result.trim());
        System.out.println("fake:"+result);
        return StrListChange.StrToList(result);
    }

    public static List<String []> getUserComment(String userId){
        try{
            Map<String,String> parameters=new HashMap<String,String>();
            parameters.put("action", Constant.getUserComment);
            parameters.put("ms",userId);
            result = HttpUtil.getStringMsg(parameters,url,"POST");
        } catch(Exception e){
            e.printStackTrace();
        }

        result=MyConverter.unescape(result.trim());
        return StrListChange.StrToList(result);
    }
    //23_10_3_yang
    //获取国学课程视频目录信息
    public static List<String[]> getVideoDirectoryData(String course_video_id)
    {
        try {
            Map<String,String> parameters=new HashMap<String,String>();
            parameters.put("action",Constant.getVideoDirectoryData);
            parameters.put( "ms", course_video_id);
            result=HttpUtil.getStringMsg(parameters,url,"POST");
            Log.d("TAG", result);
        } catch(Exception e) {
            e.printStackTrace();
        }
        result=MyConverter.unescape(result.trim());
        Log.d("TAG", "2:" + result);
        return StrListChange.StrToList(result);
    }
}