package com.example.gxcg.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.RequiresApi;

import com.example.gxcg.R;
import com.example.gxcg.activity.PfdtActivity;
import com.example.gxcg.util.Constant;

public class UpdateReceiver extends BroadcastReceiver
{
    public static int status= Constant.STATUS_STOP;//初始状态为停止状态
    PfdtActivity tpa;
    public UpdateReceiver(){}
    public UpdateReceiver(PfdtActivity tpa)
    {
        this.tpa=tpa;
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onReceive(Context context, Intent intent) {
        //从接收的Intent中获取状态值
        int tempStatus=intent.getIntExtra("status", -1);
        Log.i(status+"","status");
        //获取播放/暂停按钮的引用
        ImageView ib= (ImageView) tpa.findViewById(R.id.radio_details_img_music_play);
        //根据状态的不同对界面进行更新
        switch(tempStatus)
        {
            //若更新到播放状态则将图片换为按下后暂停的提示图片
            case Constant.STATUS_PLAY:
                ib.setImageResource(R.drawable.dt_pause);
                status=tempStatus;
                //调用initNotificationBar方法，从而修改通知中的按钮图标
                tpa.initNotificationBar();//<---------
                break;
            //若更新到停止或暂停状态则将图片换为按下后播放的提示图片
            case Constant.STATUS_STOP:
            case Constant.STATUS_PAUSE:
                ib.setImageResource(R.drawable.dt_play);
                status=tempStatus;
                //调用initNotificationBar方法，从而修改通知中的按钮图标
                tpa.initNotificationBar();//<----------
                break;
            //更新进度条及已播放时间与总时间
            case Constant.PROGRESS_GO:
                //更新进度
                ProgressBar pb=(ProgressBar)tpa.findViewById(R.id.radio_details_seek_bar);
                int duration=intent.getIntExtra("duration", 0);
                int current=intent.getIntExtra("current", 0);
                pb.setMax(duration);
                pb.setProgress(current);
//                //更新已播放时间与总时间
//                TextView tv=(TextView)tpa.findViewById(R.id.TextView01);
//                tv.setText(fromMsToMinuteStr(current)+"/"+fromMsToMinuteStr(duration));
                break;
        }
    }
    //将毫秒转换成  mi:ss格式的时间字符串
    public String fromMsToMinuteStr(int ms)
    {
        ms=ms/1000;
        int minute=ms/60;
        int second=ms%60;
        return minute+":"+((second>9)?second:"0"+second);
    }
}
