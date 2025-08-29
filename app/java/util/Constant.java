package com.example.gxcg.util;

import android.graphics.Bitmap;
import android.net.Uri;

import java.util.Date;

public class Constant
{
    public static String port="8080";
    public static String host="192.168.60.91";//IP地址
    public  static Uri tempUri;//图片的uri
    //上传头像的
    public  static final int CHOOSE_PICTURE = 0;//选择图片的
    public  static final int TAKE_PICTURE = 1;//拍照
    public static final String ADD_PRE="http://192.168.239.180:8080/GXCG/";   //192.168.119.1//192.168.43.171// 192.168.43.154
    //public static String UserId=null;//插入一个新的用户
    public static Date loginTime=null;//登陆时间
    public static byte[] picByte;//用户头像数组
    public static Bitmap bitmap;//用户头像
    public static final String updateqianming="<#updateqianming#>";//更新签名
    public static final String updatesex="<#updatesex#>";//更新性别
    public static final String updateold="<#updateold#>";
    public static final String updatename="<#updatename#>";
    public static final String getyzInfor="<#getyzInfor#>";
    public static final String updatesf="<#updatesf#>";
    public static final String getUserInfor="<#getUserInfor#>";//获取用户的密码
    public static String getSearchallName="<#getSearchallName#>";
    public static final String getalljdal="<#getalljdal#>";//获取用户的密码
    public static final String getjdalall="<#getjdalall#>";//获取用户的密码
    public static final String getSortjdal="<#getSortjdal#>";//获取用户的密码
    public static final String getSinglejdalAllInfo="<#getSinglejdalAllInfo#>";//获取用户的密码
    public static final String getalfl_name="<#getalfl_name#>";//获取用户的密码
    public static final String getallgg="<#getallgg#>";//获取用户的密码
    public static final String getptinfo="<#getptinfo#>";
    public static final String getallsfjs="<#getallsfjs#>";
    public static final String getsfjsinfo="<#getsfjsinfo#>";
    public static final String getuserpicname="<#getuserpicname#>";//获取用户的照片名字
    public static final String getallyjfk="<#getallyjfk#>";//获取用户的照片名字
    public static final String getallyjfkmax="<#getallyjfkmax#>";
    public static final String insertyjfk="<#insertyjfk#>";
    public static final String getlvcsall="<#getlvcsall#>";
    public static final String getlvpic="<#getlvpic#>";
    public static final String getyzcsall="<#getyzcsall#>";
    public static final String getyzpic="<#getyzpic#>";
    public static final String getftnrall="<#getftnrall#>";
    public static final String getftnrxiaall="<#getftnrxiaall#>";
    public static final String UpdateUserPic="<#UpdateUserPic#>";//修改用户头像信息
    public static final String USER_PIC="__userPic__";//用户头像常量
    //public static final String USER_PIC_1="__userPic__1";//用户头像常量
    public static final int CROP_SMALL_PICTURE = 2;//显示在界面上
    public static final String isExistUser="<#isExistUser#>";//判断新用户注册时有无重复
    public static final String insertUser="<#insertUser#>";//插入新注册的用户
    public static final String insertUserPic="<#insertUserPic#>";//插入新注册的用户
    public static final String gettxmaxid="<#gettxmaxid#>";//插入新注册的用户

    public static String getSearchcyNames="<#getSearchcyNames#>";//获得茶叶名称,返回的是数组
    public static String getzjmfdForSraech="<#getzjmfdForSraech#>";//获得茶叶名称,返回的是数组
    public static String getUserPass="<#getUserPass#>";//获得茶叶名称,返回的是数组
    public static String updatePass="<#updatePass#>";//更新用户密码

    public static final String GetDTAllInfor="<#GetDTAllInfor#>";//获取电台所有信息
    public static final String getwz="<#GetAllArticle#>";//获取全部文章
    public static final String getwzpic="<#GetOneArticleAllPic#>";//获取单篇文章
    public static final String getSinglewz="<#GetOneArticle#>";//获取单篇文章对应图片


    public static String GetLSAllInfor="<#GetLSAllInfor#>";//获取律师所有信息
    public static String UpdatelsglPic1="<#UpdatelsglPic1#>";//添加答疑信息insertlsgl
    public static String UpdatelsglPic2="<#UpdatelsglPic2#>";//添加答疑信息insertlsgl
    public static String insertlsgl="<#insertlsgl#>";//添加答疑信息
    public static String getlsglmaxid="<#getlsglmaxid#>";//添加答疑信息
    public static String getSinglelsglerXx="<#getSinglelsglerXx#>";
    //法律援助中心
    public static String GetFlyzAllInfor="<#GetFlyzAllInfor#>";//获取所有
    //视频信息
    public static String GetSPAllInfor="<#GetSPAllInfor#>";//获取视频所有信息
    public static String GetFLYZPLAllInfor="<#GetFLYZPLAllInfor#>";//获取法律援助评论
    public static String AddFlyzPJInfor="<#AddFlyzPJAllInfor#>";//获取所有
    public static String AddDtSCInfor="<#AddDtSCInfor#>";//添加电台收藏
    public static String AddLSSCInfor="<#AddLSSCInfor#>";//添加电台收藏
    //表示不同命令的常量
    public static final int COMMAND_PLAY=0;	//播放命令
    public static final int COMMAND_PAUSE=1; //暂停命令
    public static final int COMMAND_STOP=2;  //停止命令

    //表示不同状态的常量
    public static final int STATUS_PLAY=3;  //播放状态
    public static final int STATUS_PAUSE=4; //暂停状态
    public static final int STATUS_STOP=5;  //停止状态

    //表示更新进度状态的常量
    public static final int PROGRESS_GO=6;

    //表示不同功能Intent的字符串=========================================
    //表示控制命令的Action，如：播放、暂停、停止等
    public static final String MUSIC_CONTROL="TinyPlayer.ACTION_CONTROL";
    //表示更新界面的Action，如：播放、暂停、停止等
    public static final String UPDATE_STATUS="TinyPlayer.ACTION_UPDATE";
    //表示通知栏按钮控制动作的Action
    public static final String NOTIFICAYON_PLAYPAUSE="TinyPlayer.Notification.PLAYPAUSE";
    public static final String NOTIFICATION_STOP="TinyPlayer.Notification.STOP";

    public static String selectUser = "<#selectUser#>";//查询是否存在该用户
    //public static final String isExistUser="<#isExistUser#>";//判断新用户注册时有无重复
    public static final String insertUserAndroid="<#insertUserAndroid#>";//插入新注册的用户
    //public static String getUserPass="<#getUserPass#>";//获得指定用户ID的密码
    //public static String updatePass="<#updatePass#>";//更新用户密码
    public static String getAndroidQuestion = "<#getAndroidQuestion#>";//获取问题

    //23_9_27_Yang
    public static String getCourseGroupData = "<#getCourseGroupData>";
    public static String getVideoCourseIdByGroupId = "<#getVideoCourseIdByGroupId>";
    public static String getVideoCourseAllInfoById = "<#getVideoCourseAllInfoById>";
    //根据经史子集类称获取里面的每个书籍
    public static String getClassicsJingData = "<#getClassicsJingData>";
    //23_9_30_Chao
    public static String GetDYAllInfor="<#GetDYAllInfor#>";//获取答疑信息
    public static String GetDYAllComments="<#GetDYAllComments#>";//获取答疑信息
    public static String AddDYInfor="<#AddDYInfor#>";//添加答疑信息
    public static String InsertDYComments="<#InsertDYComments#>";//添加评论信息
    //2023_10_1_Yang
    //如：根据论语ID找论语这本书下的所有篇章信息
    public static String getClassicsItemsData="<#getClassicsItemsData#>";
    //2023_10_2_Yang
    //根据单个视频下的短视频:
    public static String getCourseVideoBriefData="<#getCourseVideoBriefData#>";
    //2023_10_2_chao
    //获取视频下所有评论
    public static String GetAllVideoComments="<#GetAllVideoComments#>";//获取视频下评论
    //2023_10_2_yu
    public static String getLoginUserInfo = "<#getLoginUserInfo#>";//在登陆时获取用户信息
    public static String insertUserFeedback = "<#insertUserFeedback#>";//插入用户反馈
    public static String getAllUserFeedbackInfo = "<#getAllUserFeedbackInfo#>";//获取所有用户信反馈
    public static String getUserFeedbackResult = "<#getUserFeedbackResult#>";//获取单个用户的信息
    public static String updateUserPic  = "<#updateUserPic#>"; //更新用户默认头像
    public static String updateUserInfo = "<#updateUserInfo#>";//更新用户信息
    public static String getCollection = "<#getCollection#>";//获取收藏
    public static String getUserComment = "<#getUserComment#>";//获取评论
    public static String [] userInfo;
    public static String UserId=null;//插入一个新的用户
    //23_10_3_yang
    //获取国学课程视频目录信息
    public static String getVideoDirectoryData = "<#getVideoDirectoryData#>";
}
