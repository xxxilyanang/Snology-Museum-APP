package com.example.gxcg.activity;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.gxcg.R;
import com.example.gxcg.util.InforUtil;
import com.example.gxcg.util.StrListChange;

import java.util.ArrayList;
import java.util.List;

public class FlyzDetailsActivity extends Activity
{
    private ListView list;//列表管理
    private BaseAdapter ba;//列表适配器
    private String result;
    private List<String[]> rel=new ArrayList<>();
    private EditText plnr;//将要发表的评论内容
    private Button fb;//发表按键
    private String fbresult;//发表结果
    String flyzzx;

    private Context mContext;
    Bitmap LargeBitmap = null;
    public static final int NOTIFICATION_ID=1;
    String CHANNEL_ID="add pj";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_flyzdetails);
        init();
    }

    public void init()
    {

        mContext = FlyzDetailsActivity.this;
        //创建大图标的Bitmap
        LargeBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.jdal);


        result=this.getIntent().getStringExtra("infor");
        flyzzx=this.getIntent().getStringExtra("flyzzx");
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
            else {
                for (int i=0;i<str.length;i+=7)
                {
                    String[] temp=new String[]{str[i],str[i+1],str[i+2],str[i+3],str[i+4],str[i+5],str[i+6]};
                    rel.add(temp);
                }
            }

        }
        plnr=this.findViewById(R.id.fbtext);
        fb=this.findViewById(R.id.fb);
        fb.setOnClickListener
                (
                        e->
                        {
                            String nr=plnr.getText().toString();
                            if (nr.equals(""))
                            {
                                Toast.makeText(this,"评论不能为空",Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                System.out.println("12345");
                                AddPLThread addPLThread=new AddPLThread(MainActivity.userMess[0],flyzzx,nr);
                                addPLThread.start();
                                try {
                                    addPLThread.join();
                                } catch (InterruptedException interruptedException) {
                                    interruptedException.printStackTrace();
                                }
                                if (fbresult.equals("1"))
                                {
                                    Toast.makeText(this,"发表成功",Toast.LENGTH_SHORT).show();
                                    plnr.setText("");
                                    //定义一个PendingIntent点击Notification后启动一个Activity
                                    Intent it = new Intent(mContext, FlyzDetailsActivity.class);
                                    PendingIntent pit = PendingIntent.getActivity(mContext, 0, it, 0);



                                    NotificationManager notificationManager  = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);


                                    if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O) {
                                        NotificationChannel channel = new NotificationChannel(CHANNEL_ID
                                                , "name", NotificationManager.IMPORTANCE_DEFAULT);

                                        notificationManager.createNotificationChannel(channel);
                                    }
                                    NotificationCompat.Builder builder = new NotificationCompat.Builder(FlyzDetailsActivity.this, CHANNEL_ID)
                                            .setSmallIcon(R.mipmap.ic_launcher)
                                            .setContentTitle("发表成功")
                                            .setContentText("您的评论发表成功")
                                            .setTicker("收到典亮生活发送过来的信息~")             //收到信息后状态栏显示的文字信息
                                            .setSubText(nr)                    //内容下面的一小段文字
                                            .setWhen(System.currentTimeMillis())           //设置通知时间
                                            .setLargeIcon(LargeBitmap)                     //设置大图标
                                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                                            .setSmallIcon(R.drawable.lvshi)            //设置小图标
                                            .setContentIntent(pit)
                                            .setFullScreenIntent(pit, true)
                                            //在用户点按通知后自动移除通知
                                            .setAutoCancel(true);                        //设置PendingIntent;

                                    NotificationManagerCompat.from(this).notify(NOTIFICATION_ID, builder.build());


                                }
                            }
                        }
                );

        //加载律师答疑列表界面
        LayoutInflater li = getLayoutInflater();
        list=this.findViewById(R.id.flyz_list);

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
                RelativeLayout relativeLayout=(RelativeLayout)view;
                if (relativeLayout==null)
                {
                    relativeLayout=(RelativeLayout) (li.inflate(R.layout.flyzitemlayout, null).findViewById(R.id.listitem));
                }
                ImageView img=(ImageView)relativeLayout.getChildAt(0);
                TextView nc=(TextView) relativeLayout.getChildAt(1);
                TextView rq=(TextView) relativeLayout.getChildAt(2);
                TextView text=(TextView) relativeLayout.getChildAt(3);

                nc.setText(rel.get(i)[2]);
                text.setText(rel.get(i)[4]);
                rq.setText(rel.get(i)[5]);
                return relativeLayout;
            }
        };
        list.setAdapter(ba);
    }
    private class AddPLThread extends Thread
    {
        String yh_id;
        String flyzzx_id;
        String pj_nr;
        public AddPLThread(String yh_id,String flyzzx_id,String pj_nr)
        {
            this.yh_id=yh_id;
            this.flyzzx_id=flyzzx_id;
            this.pj_nr=pj_nr;
        }
        @Override
        public void run() {
            fbresult= InforUtil.addAllFlyzPLInfor(yh_id,flyzzx_id,pj_nr);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
