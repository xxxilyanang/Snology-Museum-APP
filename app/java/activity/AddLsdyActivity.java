package com.example.gxcg.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.gxcg.R;
import com.example.gxcg.util.InforUtil;

public class AddLsdyActivity extends Activity implements View.OnClickListener
{
    private EditText dytitle;//答疑题目
    private EditText dynr;//答疑内容
    private Button add;//发表
    String result;//发表结果
    Bitmap LargeBitmap = null;
    public static final int NOTIFICATION_ID=1;
    String CHANNEL_ID="add pj";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_lsdy_add);
        init();

    }
    private void init()
    {
        //创建大图标的Bitmap
        LargeBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.jdal);
        dytitle=this.findViewById(R.id.dytitle);
        dynr=this.findViewById(R.id.dynr);
        add=this.findViewById(R.id.adddy);
        add.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getTag().equals("发表"))
        {
            String titl=dytitle.getText().toString();
            String nr=dynr.getText().toString();
            if (titl.equals(""))
            {
                Toast.makeText(this,"标题不能为空",Toast.LENGTH_SHORT).show();
                return;
            }
            if (nr.equals(""))
            {
                Toast.makeText(this,"内容不能为空",Toast.LENGTH_SHORT).show();
                return;
            }
            Thread thread=new Thread()
            {
                @Override
                public void run() {
                    //增加答疑（话题）内容的方法
                    result= InforUtil.addAllFldyInfor(MainActivity.userMess[0],titl,nr);
                }
            };
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (result.equals("1"))
            {
                Toast.makeText(this,"发表成功",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
    @Override
    protected void onRestart() {
        super.onRestart();
    }
}

