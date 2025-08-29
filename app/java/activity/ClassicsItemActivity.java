package com.example.gxcg.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.gxcg.R;
import com.example.gxcg.util.InforUtil;
import com.example.gxcg.util.StrListChange;

import java.util.ArrayList;
import java.util.List;

public class ClassicsItemActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView tv;
    //数据相关
    private String classics_items_id;
    private String classics_chapter_name;
    private List<String[]> classics_items_list;
    private String[][] classics_items_array;

    //更新UI界面的handler线程
    private Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case 1:
                    initClassicsItemsListView();
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classics_subitem_layout);
        toolbar = findViewById(R.id.tb_classicsItem_back);
        tv = findViewById(R.id.tv_classicsItems_title);
        classics_items_id = getIntent().getStringExtra("classics_id");
        classics_chapter_name = getIntent().getStringExtra("classics_chapter_name");
        tv.setText("《"+classics_chapter_name+"》" + "目录");//更改标题为书籍名称
        initToolbar();
        GetClassicsItemsThread getClassicsItemsThread = new GetClassicsItemsThread();
        getClassicsItemsThread.start();
        try {
            getClassicsItemsThread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //初始标题栏
    private void initToolbar() {

        toolbar = findViewById(R.id.tb_classicsItem_back);
        toolbar.setNavigationOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();       //返回
                    }
                });
    }
    //更新UI的方法
    public void initClassicsItemsListView()
    {
        ListView listView = findViewById(R.id.lv_classicsItem);
        listView.setVisibility(View.VISIBLE);
        ClassicsItemsListViewAdapter classicsItemsListViewAdapter =
                new ClassicsItemsListViewAdapter(ClassicsItemActivity.this);
        listView.setAdapter(classicsItemsListViewAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(
                        ClassicsItemActivity.this,
                        ClassicsContentActivity.class);
                intent.putExtra("classics_name", classics_items_array[position][2]);
                intent.putExtra("classics_content_url", classics_items_array[position][3]); //传入ID
                intent.putExtra("classics_translate_url", classics_items_array[position][4]);
                startActivity(intent);
            }
        });
    }
    //获取信息的线程内部类
    public class GetClassicsItemsThread extends Thread
    {
        @Override
        public void run() {
            classics_items_list = new ArrayList<>();
            Log.d("TAG-classics_items_list", "run: "+classics_items_id);
            classics_items_list = InforUtil.getClassicsItemsData(classics_items_id);
            //classics_items_list: 结果:{[课程号,名称,作者,简介路径名...],[a,b,c]}
            if (!classics_items_list.isEmpty() && classics_items_list.size() != 0) {
                classics_items_array = StrListChange.ListToTwoStringArray(classics_items_list);
            }

            Message message = Message.obtain();
            message.what = 1;
            handler.sendMessage(message);
        }
    }

    //内部类适配器
    class ClassicsItemsListViewAdapter extends BaseAdapter
    {
        LayoutInflater layoutInflater;
        public ClassicsItemsListViewAdapter(Context context)
        {
            this.layoutInflater = LayoutInflater.from(context);
        }
        @Override
        public int getCount()
        {
            return classics_items_array.length;
        }

        @Override
        public Object getItem(int position)
        {
            return position;
        }

        @Override
        public long getItemId(int position)
        {
            return position;
        }

        @Override
        public View getView(int i, View view, ViewGroup parent)
        {
            ClassicsItemActivity.ClassicsItemsListViewAdapter.ViewClass viewClass;
            if(view==null)
            {
                view = layoutInflater.inflate(R.layout.classicsitems_item,null);
                viewClass = new ClassicsItemActivity.ClassicsItemsListViewAdapter.ViewClass();
                //默认外观设置
                viewClass.name = view.findViewById(R.id.tv_classicsItem_name);
                view.setTag(viewClass);//很重要  要写
            }else {
                viewClass = (ClassicsItemActivity.ClassicsItemsListViewAdapter.ViewClass)view.getTag();
            }
            viewClass.name.setText(classics_items_array[i][2]);
            return view;        }
        private class ViewClass
        {
            TextView name;
        }
    }
}