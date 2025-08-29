package com.example.gxcg.service;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.example.gxcg.receiver.PlayStateReceiver;
import com.example.gxcg.util.Constant;

public class PlayerService extends Service
{
    //命令Intent接收者对象引用
    PlayStateReceiver cr;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();

        //创建命令Intent接收者对象
        cr=new PlayStateReceiver();
        //创建媒体播放器对象
        cr.mp=new MediaPlayer();
        //初始状态为停止状态
        cr.status= Constant.STATUS_STOP;

        //动态注册接收播放、暂停、停止命令Intent的CommandReceiver
        IntentFilter filter=new IntentFilter();
        filter.addAction(Constant.MUSIC_CONTROL);
        //服务于通知栏按钮播放/暂停动作的Action
        filter.addAction(Constant.NOTIFICAYON_PLAYPAUSE);//<--------
        //服务于通知栏按钮停止动作的Action
        filter.addAction(Constant.NOTIFICATION_STOP);//<---------
        this.registerReceiver(cr, filter);
    }
    @Override
    public void onDestroy()
    {
        super.onDestroy();
        //取消注册接收播放、暂停、停止命令Intent的CommandReceiver
        this.unregisterReceiver(cr);
        //释放播放器对象
        cr.mp.release();
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        //更新界面状态
        cr.updateUI(this.getApplicationContext());
        //如果系统在onStartCommand()方法返回之后杀死这个服务，
        //那么直到接受到新的Intent对象，这个服务才会被重新创建。
        // 这是最安全的选项，用来避免在不需要的时候运行你的服务。
        return START_NOT_STICKY;
    }
}
