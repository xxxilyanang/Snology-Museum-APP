package com.example.gxcg.activity;

import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Icon;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.gxcg.R;
import com.example.gxcg.receiver.UpdateReceiver;
import com.example.gxcg.service.PlayerService;
import com.example.gxcg.util.Constant;
import com.example.gxcg.util.InforUtil;
import com.example.gxcg.util.StrListChange;
import com.example.gxcg.util.SwipeMenu;

import java.util.ArrayList;
import java.util.List;

public class PfdtActivity extends Activity
{
    //下滑刷新
    private SwipeRefreshLayout mSwipeRefreshLayout;
    //ListView电台列表
    private ListView listView;
    //适配器
    private BaseAdapter ba;
    //获取电台信息
    private List<String[]> rel=new ArrayList<>();
    String result;//电台收藏
    String relSC;//电
    public static String currentPlay= Constant.ADD_PRE+"audio/radio_chuci_001.mp3";//播放路径
    UpdateReceiver uiur;//界面更新Intent的接收者
    //通知对象引用
    private Notification notification;
    String CHANNEL_ID="dtsc";
    Bitmap LargeBitmap = null;
    MediaPlayer mediaPlayer = new MediaPlayer();
    private int isPlay=0;//是否播放，0为未播放，1，为播放，2为暂停，3为播放下一首停止当前
    //下滑刷新消息接收
    private Handler mHandler = new Handler()
    {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case 1:
                    mSwipeRefreshLayout.setRefreshing(false);
                    break;
                default:
                    break;
            }
        }
    };
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_pfdt);
        init();
    }
    //初始化
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void init()
    {
        //获取播放/暂停按钮的引用
        ImageView ibPlayPause=(ImageView)findViewById(R.id.radio_details_img_music_play);
        //创建大图标的Bitmap
        LargeBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.jdal);
        GetAllDtInforThread getAllATInfor=new GetAllDtInforThread();
        getAllATInfor.start();
        try {
            getAllATInfor.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(result);
        //获取下拉刷新组件
        mSwipeRefreshLayout=this.findViewById(R.id.swipeRefresh);
        //设置下拉刷新样式
        mSwipeRefreshLayout.setColorSchemeResources(
                R.color.design_default_color_error,
                R.color.design_default_color_on_primary,
                R.color.design_default_color_primary
        );
        //设置大小
        mSwipeRefreshLayout.setSize(SwipeRefreshLayout.DEFAULT);
        //下拉刷新监听
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh() {
                new Thread()
                {
                    @Override
                    public void run() {
                        mHandler.sendEmptyMessage(1);
                    }
                }.start();
            }
        });
        //获取电台列表
        listView=this.findViewById(R.id.pfdt_listview);
        LayoutInflater inflater=LayoutInflater.from(this);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                View firstView = view.getChildAt(firstVisibleItem);
                if(firstVisibleItem ==0 && (firstView == null || firstView.getTop() == 0)) {
                    /*上滑到listView的顶部时，下拉刷新组件可见*/
                    mSwipeRefreshLayout.setEnabled(true);
                } else {
                    /*不是listView的顶部时，下拉刷新组件不可见*/
                    mSwipeRefreshLayout.setEnabled(false);
                }
            }
        });
        //初始化适配器
        ba=new BaseAdapter() {
            @Override
            public int getCount() {
                return rel.size();
            }

            @Override
            public Object getItem(int i) {
                return null;
            }

            @Override
            public long getItemId(int i) {
                return 0;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                RelativeLayout relativeLayout=(RelativeLayout) view;
                if (relativeLayout == null)
                {
                    relativeLayout = (RelativeLayout) (inflater.inflate(R.layout.pfdt_list_item, null).findViewById(R.id.pfdt_relativelayout));
                }
                SwipeMenu swipeMenu=(SwipeMenu)relativeLayout.getChildAt(0);
                RelativeLayout relayout=(RelativeLayout) swipeMenu.getChildAt(0);
                //获取组件，以显示电台信息
                TextView dtmc=(TextView)relayout.getChildAt(1);//getChildAt获取的顺序是按在代码中书写顺序
                TextView dtinfo=(TextView)relayout.getChildAt(2);
                ImageView play=(ImageView) relayout.getChildAt(3);
                play.setOnClickListener
                        (
                                e->
                                {
                                    currentPlay=Constant.ADD_PRE+"audio/"+rel.get(i)[2];//播放路径

                                    if (mediaPlayer==null)
                                    {
                                        mediaPlayer=new MediaPlayer();
                                        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                                        try {
                                            mediaPlayer.setDataSource(currentPlay);
                                            mediaPlayer.prepare(); // 准备
                                        }
                                        catch (Exception ee)
                                        {
                                            Toast.makeText(PfdtActivity.this,"暂无音源",Toast.LENGTH_SHORT).show();
                                            ee.printStackTrace();
                                        }
                                        ibPlayPause.setImageResource(R.drawable.dt_pause);
                                        mediaPlayer.start();
                                        isPlay=1;//设置为播放
                                    }
                                    else
                                    {
                                        mediaPlayer.stop();
                                        mediaPlayer.release();
                                        mediaPlayer=new MediaPlayer();
                                        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                                        System.out.println();
                                        try {
                                            mediaPlayer.setDataSource(currentPlay);
                                            mediaPlayer.prepare(); // 准备
                                        }
                                        catch (Exception ee)
                                        {
                                            Toast.makeText(PfdtActivity.this,"暂无音源",Toast.LENGTH_SHORT).show();
                                            ee.printStackTrace();
                                        }
                                        ibPlayPause.setImageResource(R.drawable.dt_pause);
                                        mediaPlayer.start();
                                        isPlay=1;//设置为播放
                                    }


                                }
                        );
                dtmc.setText(rel.get(i)[1].trim());
                dtinfo.setText(rel.get(i)[3].trim());
                Button scdt=(Button)swipeMenu.getChildAt(1);
                scdt.setOnClickListener
                        (
                                e->{
                                    //启动收藏电台的线程
                                    AddDTSCThread addDTSCThread=new AddDTSCThread(MainActivity.userMess[0],rel.get(i)[0]);
                                    addDTSCThread.start();
                                    try {
                                        addDTSCThread.join();
                                    } catch (InterruptedException interruptedException) {
                                        interruptedException.printStackTrace();
                                    }
                                    if (relSC.equals("1"))
                                    {
                                        //定义一个PendingIntent点击Notification后启动一个Activity
                                        Intent it = new Intent(PfdtActivity.this, FlyzDetailsActivity.class);
                                        PendingIntent pit = PendingIntent.getActivity(PfdtActivity.this, 0, it, 0);



                                        NotificationManager notificationManager  = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


                                        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O) {
                                            NotificationChannel channel = new NotificationChannel(CHANNEL_ID
                                                    , "name", NotificationManager.IMPORTANCE_DEFAULT);

                                            notificationManager.createNotificationChannel(channel);
                                        }
                                        NotificationCompat.Builder builder = new NotificationCompat.Builder(PfdtActivity.this, CHANNEL_ID)
                                                .setSmallIcon(R.mipmap.ic_launcher)
                                                .setContentTitle("收藏成功")
                                                .setContentText(rel.get(i)[1])
                                                .setTicker("收到国学藏馆发送过来的信息~")             //收到信息后状态栏显示的文字信息
                                                .setSubText("国学电台")                    //内容下面的一小段文字
                                                .setWhen(System.currentTimeMillis())           //设置通知时间
                                                .setLargeIcon(LargeBitmap)                     //设置大图标
                                                .setPriority(NotificationCompat.PRIORITY_HIGH)
                                                .setSmallIcon(R.drawable.lvshi)            //设置小图标
                                                .setContentIntent(pit)
                                                .setFullScreenIntent(pit, true)
                                                //在用户点按通知后自动移除通知
                                                .setAutoCancel(true);                        //设置PendingIntent;

                                        NotificationManagerCompat.from(PfdtActivity.this).notify(FlyzDetailsActivity.NOTIFICATION_ID, builder.build());
                                        Toast.makeText(PfdtActivity.this,"收藏成功",Toast.LENGTH_SHORT).show();
                                    }
                                }
                        );
                return relativeLayout;
            }
        };
        listView.setAdapter(ba);


//        initNotificationBar();
        //根据状态标志位的值设置播放/暂停按钮的图片
//        if(UpdateReceiver.status==Constant.STATUS_PLAY)
//        {
//            ibPlayPause.setImageResource(R.drawable.dt_pause);
//        }

//        //创建界面更新Intent的接收者
//        uiur=new UpdateReceiver(this);
//        //发出启动后台Service的Intent
//        this.startService(new Intent(this,PlayerService.class));
        //为播放/暂停按钮添加监听器
        ibPlayPause.setOnClickListener(
                new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v) {
//                        if(UpdateReceiver.status==Constant.STATUS_PLAY)
//                        {//若当前为播放状态则发出暂停命令
//                            Intent intent=new Intent(Constant.MUSIC_CONTROL);
//                            intent.putExtra("cmd", Constant.COMMAND_PAUSE);
//                            System.out.println(UpdateReceiver.status);
//                            PfdtActivity.this.sendBroadcast(intent);
//                        }
//                        else if(UpdateReceiver.status==Constant.STATUS_STOP)
//                        {//若当前为停止状态则发出播放命令，同时发送出播放路径
//                            Intent intent=new Intent(Constant.MUSIC_CONTROL);
//                            intent.putExtra("cmd", Constant.COMMAND_PLAY);
//                            intent.putExtra("path", currentPlay);
//                            System.out.println(UpdateReceiver.status);
//                            PfdtActivity.this.sendBroadcast(intent);
//                        }
//                        else
//                        {//若当前为暂停状态则发出继续播放命令
//                            Intent intent=new Intent(Constant.MUSIC_CONTROL);
//                            intent.putExtra("cmd", Constant.COMMAND_PLAY);
//                            System.out.println(UpdateReceiver.status);
//                            PfdtActivity.this.sendBroadcast(intent);
//                        }
                        if (isPlay==0)
                        {
                            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                            try {
                                mediaPlayer.setDataSource(currentPlay);
                                mediaPlayer.prepare(); // 准备
                            }
                            catch (Exception e)
                            {
                                Toast.makeText(PfdtActivity.this,"暂无音源",Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }
                            ibPlayPause.setImageResource(R.drawable.dt_pause);
                            mediaPlayer.start();
                            isPlay=1;//设置为播放
                        }
                        else if (isPlay==1)
                        {
                            ibPlayPause.setImageResource(R.drawable.dt_play);
                            mediaPlayer.pause();
                            isPlay=2;
                        }
                        else if (isPlay==2)
                        {
                            ibPlayPause.setImageResource(R.drawable.dt_pause);
                            mediaPlayer.start();
                            isPlay=1;
                        }
                    }
                }
        );

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void initNotificationBar()
    {
        //通知中播放暂停按钮对应的Intent
        Intent intentplay=new Intent(Constant.NOTIFICAYON_PLAYPAUSE);
        PendingIntent pIntentplay=PendingIntent.getBroadcast(this,0,intentplay,FLAG_UPDATE_CURRENT);

        //通知中停止按钮对应的Intent
        Intent intentstop=new Intent(Constant.NOTIFICATION_STOP);//设置停止的广播
        PendingIntent pIntentstop=PendingIntent.getBroadcast(this,0,intentstop,FLAG_UPDATE_CURRENT);//--------------------<创建暂停action
        Notification.Action stopAction = new Notification.Action.Builder(
                Icon.createWithResource(this,R.drawable.stop),
                "stop",
                pIntentstop)
                .build();

        try
        {
            //创建Intent
            Intent intent = new Intent(this, PfdtActivity.class);
            //将Intent封装为PendingIntent
            PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);
            //获取通知管理器
            NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            //创建通知频道
            String myChannelID = "BN_CHANNEL";
            NotificationChannel nc = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                nc = new NotificationChannel(myChannelID, "MyChannel", NotificationManager.IMPORTANCE_HIGH);
                nm.createNotificationChannel(nc);
                //检查此应用是否允许通知
                if (!nm.areNotificationsEnabled()) {
                    Toast.makeText(
                            this, //上下文
                            "通知未被允许！！！", //提示内容
                            Toast.LENGTH_LONG                        //信息显示时间
                    ).show();
                }
                Notification.Action playOrPauseAction = null;
                if (UpdateReceiver.status == Constant.STATUS_PLAY)//------------------------------------------------------------------------<begin
                {
                    playOrPauseAction = new Notification.Action.Builder(
                            Icon.createWithResource(this, R.drawable.dt_pause),
                            "palypause",
                            pIntentplay)
                            .build();//创建播放暂停的action
                } else if (UpdateReceiver.status == Constant.STATUS_PAUSE) {
                    playOrPauseAction = new Notification.Action.Builder(
                            Icon.createWithResource(this, R.drawable.dt_play),
                            "palypause",
                            pIntentplay)
                            .build();//创建播放暂停的action
                } else if (UpdateReceiver.status == Constant.STATUS_STOP) {
                    playOrPauseAction = new Notification.Action.Builder(
                            Icon.createWithResource(this, R.drawable.dt_play),
                            "playpause",
                            pIntentplay)
                            .build();//创建播放暂停的action
                }
                //----------------------------------------------------------------<end
                //通知对象构建者
                Notification.MediaStyle mediaStyle = new Notification.MediaStyle();//创建系统通知栏媒体样式Notification.MediaStyle
                notification = new Notification.Builder(this, myChannelID)
                        .setContentTitle("音乐播放器运行中")
                        .setContentText("点击查看")
                        .setSmallIcon(R.drawable.lvshi)
                        .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.drawable.lvshi))
                        .setTicker("音乐播放")
                        .setContentIntent(pi)
                        .setVisibility(Notification.VISIBILITY_PUBLIC)
                        .setStyle(mediaStyle)//添加Notification.MediaStyle到notification
                        .addAction(playOrPauseAction)//添加播放停止action  <--------------
                        .addAction(stopAction)//添加停止action  <--------------
                        .build();

                notification.flags = Notification.FLAG_ONGOING_EVENT;
                notification.flags = Notification.FLAG_ONLY_ALERT_ONCE;
                //发出notification
                nm.notify(1, notification);

                this.startService(new Intent(this, PlayerService.class));
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }


    }
    private class GetAllDtInforThread extends Thread
    {
        @Override
        public void run()
        {
            result= InforUtil.getAllDtInfor();//获取电台信息，包括电台id，电台名称，所属专栏名称，日期，作者，是否被删
            if (result==null)
            {
            }
            else
            {
                rel.clear();
                String[] str= StrListChange.StrToArray(result);
                if (str.length<2)
                {
                }
                else
                {
                    for (int i=0;i<str.length;i+=4)
                    {
                        String[] temp=new String[]{str[i],str[i+1],str[i+2],str[i+3]};
                        rel.add(temp);
                    }
                }
            }
        }
    }
    //添加电台收藏
    private class AddDTSCThread extends Thread{
        String yh_id;
        String dt_id;
        public AddDTSCThread(String yh_id,String dt_id)
        {
            this.yh_id=yh_id;
            this.dt_id=dt_id;
        }
        @Override
        public void run() {
            //调用添加电台方法
            relSC= InforUtil.addDTSCInfor(yh_id,dt_id);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }
}
