package com.example.gxcg.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.gxcg.R;
import com.example.gxcg.util.InforUtil;

import java.util.ArrayList;
import java.util.List;

public class QuestionActivity extends Activity {
    ListView listView;
    //listview所需数据及项
    List<String[]> listData = new ArrayList<>();
    BaseAdapter baseAdapter;
    TextView back;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        listView = this.findViewById(R.id.question_list);
        back = this.findViewById(R.id.question_back);
        back.setOnClickListener(
                e->{
                    Intent intent = new Intent(this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//关掉所要到的界面中间的 activity
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("id",4);
                    startActivity(intent);
                    finish();
                }
        );
        initListView();
    }

    private void initListView(){
        getDataThread getDataThread = new getDataThread();
        getDataThread.start();
        try {
            getDataThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LayoutInflater inflater=LayoutInflater.from(this);
        baseAdapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return listData.size();
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                LinearLayout linearLayout=(LinearLayout) convertView;
                if (linearLayout == null)
                {
                    linearLayout = (LinearLayout) (inflater.inflate(R.layout.question_listitem, null).findViewById(R.id.relative_question));
                }
                RelativeLayout relativeLayout=(RelativeLayout)linearLayout.getChildAt(0);
                TextView question=(TextView)relativeLayout.getChildAt(0);

                if(!(listData==null)) {
                   question.setText(listData.get(position)[1]);
                }else {
                    Toast.makeText(QuestionActivity.this,"没有查询到信息",Toast.LENGTH_SHORT).show();
                }
                return linearLayout;
            }
        };
        listView.setAdapter(baseAdapter);
        //列表item点击事件
        listView.setOnItemClickListener(
                (parent, view, position, id)->{
                    Intent intent = new Intent(this, QusetionDetailActivity.class);
                    intent.putExtra("data", listData.get(position));
                    startActivity(intent);
                }
        );
    }


    private class getDataThread extends Thread{
        @Override
        public void run() {
            super.run();
            listData = InforUtil.getAndroidQuestion();
        }
    }
}
