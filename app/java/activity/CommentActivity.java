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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.gxcg.R;
import com.example.gxcg.util.Constant;
import com.example.gxcg.util.InforUtil;

import java.util.ArrayList;
import java.util.List;

//2023/10/1
public class CommentActivity extends Activity {
    ListView listView;
    //listview所需数据及项
    List<String[]> listData = new ArrayList<>();
    BaseAdapter baseAdapter;
    TextView back;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        initView();
        initListView();
    }

    private void initView(){
        listView = this.findViewById(R.id.comment_list);
        back = this.findViewById(R.id.comment_back);
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
    }

    private void initListView(){
        try {
            Runnable r = ()->  listData = InforUtil.getUserComment(Constant.UserId);
            Thread thread = new Thread(r);
            thread.start();
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
                    linearLayout = (LinearLayout) (inflater.inflate(R.layout.comment_listitem, null).findViewById(R.id.relative_comment));
                }

                TextView name=(TextView)linearLayout.getChildAt(0);
                TextView context = (TextView)linearLayout.getChildAt(1);
                TextView time = (TextView) linearLayout.getChildAt(2);

                if(!(listData==null)) {
                    name.setText(listData.get(position)[5]);
                    context.setText(listData.get(position)[3]);
                    time.setText("评论时间："+listData.get(position)[4]);
                }else {
                    Toast.makeText(CommentActivity.this,"没有查询到信息",Toast.LENGTH_SHORT).show();
                }
                return linearLayout;
            }
        };
        listView.setAdapter(baseAdapter);
    }
}
