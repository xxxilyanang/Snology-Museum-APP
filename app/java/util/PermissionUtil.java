package com.example.gxcg.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

//动态权限工具类
public class PermissionUtil
{
    //权限获取动作编号（自定义）
    public static final int REQUEST_CODE_BN=9999;
    //所需权限名称数组
    private static String[] needPermissions;
    //设置所需权限名称数组
    public static void setNeedPermissions(String[] np)
    {
        needPermissions=np;
    }
    //检查是否所有所需权限已经获取
    public static boolean checkAllNeedPermissions(Context context)
    {
        boolean flag=true;
        for(String s:needPermissions)
        {
            int checkPermission = ContextCompat.checkSelfPermission(context, s);
            if(checkPermission!=PackageManager.PERMISSION_GRANTED)
            {
                flag=false;
                break;
            }
        }
        return flag;
    }

    //获取所有所需权限
    public static void requestAllNeedPermissions(Activity activity)
    {
        ActivityCompat.requestPermissions(activity,needPermissions,REQUEST_CODE_BN);
    }
}
