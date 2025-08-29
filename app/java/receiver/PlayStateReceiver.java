package com.example.gxcg.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;

import com.example.gxcg.activity.PfdtActivity;
import com.example.gxcg.util.Constant;

public class PlayStateReceiver extends BroadcastReceiver
{
    public MediaPlayer mp;//媒体播放器引用
    public int status;//状态值

    @Override
    public void onReceive(Context context, Intent intent) {
//获取收到的Intent的Action字符串
        String action=intent.getAction();
        //若收到的是通知栏按钮发出的播放/暂停动作
        if(action.equals(Constant.NOTIFICAYON_PLAYPAUSE))
        {
            //若当前是暂停状态
            if(status==Constant.STATUS_PAUSE)
            {
                //将当前状态切换为播放
                status=Constant.STATUS_PLAY;
                //播放歌曲
                mp.start();
            }
            else if(status==Constant.STATUS_PLAY)
            {//若当前是播放状态
                //将当前状态切换为暂停
                status=Constant.STATUS_PAUSE;
                //暂停播放歌曲
                mp.pause();
            }
            else if(status==Constant.STATUS_STOP)
            {//若当前是停止状态
                //如果已有播放器则释放
                if(mp!=null){mp.release();}
                //创建新的播放器
                mp=new MediaPlayer();
                //歌曲播放结束事件的监听器
                mp.setOnCompletionListener(
                        new MediaPlayer.OnCompletionListener()
                        {
                            @Override
                            public void onCompletion(MediaPlayer arg0)
                            {//歌曲播放结束停止播放并更新界面状态
                                arg0.stop();
                                status=Constant.STATUS_STOP;
                                updateUI(context);
                            }
                        }
                );
                try
                {
                    //设置播放歌曲的路径
                    mp.setDataSource(PfdtActivity.currentPlay);
                    //进行播放前的准备工作，new方式创建的MediaPlayer一定需要prepare
                    //否则报错
                    mp.prepare();
                    //开始播放
                    mp.start();
                } catch (Exception e1){e1.printStackTrace();}
                //启动一个线程不断更新播放进度
                new Thread()
                {
                    public void run()
                    {
                        looper:while(true)
                        {
                            //若当前状态为停止状态，则终止线程
                            if(status==Constant.STATUS_STOP)
                            {
                                break looper;
                            }

                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            int duration=0;
                            int current=0;
                            if(status==Constant.STATUS_PLAY||status==Constant.STATUS_PAUSE)
                            {//若当前为播放或暂停态则获取总时间与当前时间
                                duration=mp.getDuration();
                                current=mp.getCurrentPosition();
                            }
                            //创建Action为Constant.UPDATE_STATUS（更新界面）的Intent
                            Intent intent =new Intent(Constant.UPDATE_STATUS);
                            //将更新后的新状态值放到intent中
                            intent.putExtra("status", Constant.PROGRESS_GO);
                            intent.putExtra("duration", duration);
                            intent.putExtra("current", current);
                            //广播Intent
                            context.sendBroadcast(intent);
                        }
                    }
                }.start();
                status=Constant.STATUS_PLAY;
            }
        }
        else if(action.equals(Constant.NOTIFICATION_STOP))
        {//若收到的是通知栏按钮发出的停止动作
            //将当前状态切换为停止
            status=Constant.STATUS_STOP;
            //停止播放
            mp.stop();
        }
        switch(intent.getIntExtra("cmd", -1))
        {//根据收到命令的不同做不同的工作
            case Constant.COMMAND_PLAY://收到播放命令
                //获取要播放歌曲的路径
                String path=intent.getStringExtra("path");
                //如果路径不为空则进行播放工作
                if(path!=null)
                {//若收到了Path说明是新的一轮播放
                    //如果以前有播放器则释放
                    if(mp!=null){mp.release();}
                    //创建新的播放器
                    mp=new MediaPlayer();
                    mp.setOnCompletionListener(//歌曲播放结束事件的监听器
                            new MediaPlayer.OnCompletionListener()
                            {
                                @Override
                                public void onCompletion(MediaPlayer arg0)
                                {//歌曲播放结束停止播放并更新界面状态
                                    arg0.stop();
                                    status=Constant.STATUS_STOP;
                                    updateUI(context);
                                }
                            }
                    );
                    try
                    {
                        //设置播放歌曲的路径
                        mp.setDataSource(path);
                        //进行播放前的准备工作，new方式创建的MediaPlayer一定需要prepare
                        //否则报错
                        mp.prepare();
                        //开始播放
                        mp.start();
                    } catch (Exception e1)
                    {
                        e1.printStackTrace();
                    }
                    //启动一个线程不断更新播放进度
                    new Thread()
                    {
                        public void run()
                        {
                            looper:while(true)
                            {
                                //若当前状态为停止状态，则终止线程
                                if(status==Constant.STATUS_STOP)
                                {
                                    break looper;
                                }

                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                                int duration=0;
                                int current=0;
                                if(status==Constant.STATUS_PLAY||status==Constant.STATUS_PAUSE)
                                {//若当前为播放或暂停态则获取总时间与当前时间
                                    duration=mp.getDuration();
                                    current=mp.getCurrentPosition();
                                }
                                //创建Action为Constant.UPDATE_STATUS（更新界面）的Intent
                                Intent intent =new Intent(Constant.UPDATE_STATUS);
                                //将更新后的新状态值放到intent中
                                intent.putExtra("status", Constant.PROGRESS_GO);
                                intent.putExtra("duration", duration);
                                intent.putExtra("current", current);
                                //广播Intent
                                context.sendBroadcast(intent);
                            }
                        }
                    }.start();
                }
                else
                {//若没有收到path说明是暂停后的继续播放
                    mp.start();
                }
                //更新状态值到播放中状态
                status=Constant.STATUS_PLAY;
                break;
            case Constant.COMMAND_PAUSE://收到暂停命令
                status=Constant.STATUS_PAUSE;//更新状态值到暂停状态
                mp.pause();//暂停播放
                break;
            case Constant.COMMAND_STOP://收到停止命令
                status=Constant.STATUS_STOP;//更新状态值到停止状态
                mp.stop();//停止播放
                break;
        }

        //更新界面
        updateUI(context);
    }

    public void updateUI(Context context)
    {
        //创建Action为Constant.UPDATE_STATUS（更新界面）的Intent
        Intent intent =new Intent(Constant.UPDATE_STATUS);
        //将更新后的新状态值放到intent中
        intent.putExtra("status", status);
        context.sendBroadcast(intent);

    }
}
