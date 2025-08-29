package com.example.gxcg.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.gxcg.R;
import com.example.gxcg.util.Constant;
import com.example.gxcg.util.InforUtil;

import java.util.ArrayList;
import java.util.List;

public class CollectionActivity extends Activity {

    ListView listView;
    //listview所需数据及项
    List<String[]> listData = new ArrayList<>();
    BaseAdapter baseAdapter;
    TextView back;
    TextView video;
    TextView article;
    String select = "article";

    TextView myVideo;
    TextView myArticle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        listView = this.findViewById(R.id.collection_list);
        back = this.findViewById(R.id.collection_back);
        video = this.findViewById(R.id.collection_video);
        article = this.findViewById(R.id.collection_article);
        article.setTextColor(Color.rgb(255,0,0));
        video.setTextColor(Color.rgb(0,0,0));
        myArticle = this.findViewById(R.id.my_video);
        myVideo = this.findViewById(R.id.my_article);
        myVideo.setBackgroundColor(Color.rgb(255,0,0));
        myArticle.setBackgroundColor(Color.rgb(245,245,245));
        video.setOnClickListener(
                e->{
                    select = "video";
                    initListView();
                    video.setTextColor(Color.rgb(255,0,0));
                    article.setTextColor(Color.rgb(0,0,0));
                    myVideo.setBackgroundColor(Color.rgb(245,245,245));
                    myArticle.setBackgroundColor(Color.rgb(255,0,0));
                }
        );
        article.setOnClickListener(
                e->{
                    select = "article";
                    initListView();
                    article.setTextColor(Color.rgb(255,0,0));
                    video.setTextColor(Color.rgb(0,0,0));
                    myVideo.setBackgroundColor(Color.rgb(255,0,0));
                    myArticle.setBackgroundColor(Color.rgb(245,245,245));
                }
        );
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
        Runnable r = ()-> listData = InforUtil.getCollection(Constant.UserId,select);
        Thread thread = new Thread(r);
        thread.start();
        try {
            thread.join();
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
                    linearLayout = (LinearLayout) (inflater.inflate(R.layout.collection_listitem, null).findViewById(R.id.relative_collection));
                }

                TextView name=(TextView)linearLayout.getChildAt(0);
                LinearLayout l = (LinearLayout) linearLayout.getChildAt(1);
                ImageView pic = (ImageView) l.getChildAt(0);
                TextView time = (TextView) l.getChildAt(1);

                if(!(listData==null)) {
                    name.setText(listData.get(position)[1]);
                    if(select.equals(article)){
                        pic.setImageResource(R.drawable.my_article);
                    }else{
                        pic.setBackgroundResource(R.drawable.video);
                    }

                    time.setText("收藏时间："+listData.get(position)[2]);
                }else {
                    Toast.makeText(CollectionActivity.this,"没有查询到信息",Toast.LENGTH_SHORT).show();
                }
                return linearLayout;
            }
        };
        listView.setAdapter(baseAdapter);
    }

}
