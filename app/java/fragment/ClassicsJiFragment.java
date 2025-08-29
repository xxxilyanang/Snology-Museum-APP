package com.example.gxcg.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.gxcg.R;
import com.example.gxcg.activity.ClassicsItemActivity;
import com.example.gxcg.util.InforUtil;
import com.example.gxcg.util.StrListChange;

import java.util.ArrayList;
import java.util.List;

public class ClassicsJiFragment extends Fragment {

    //界面组件
    private View view;//加载的界面布局
    private ListView listView;//显示用的listView

    //数据存放:
    private List<String[]> classics_list;
    private String[] classics_introduce;
    private String[][] classics_array;

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
                    initClassicsListView();
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //预加载界面布局view
        view = inflater.inflate(R.layout.fragment_classicsji,null);
        listView = view.findViewById(R.id.listview_classics_ji);
        //线程获取数据库内信息：放在容器里
        //根据容器
        //获取所有的ID和名字(标题)，放到courseGroup_array里面
        GetClassicsJiThread getClassicsJiThread = new GetClassicsJiThread();
        getClassicsJiThread.start();
        try {
            getClassicsJiThread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    public void initClassicsListView()
    {
        ListView listView = (ListView) view.findViewById(R.id.listview_classics_ji);
        listView.setVisibility(View.VISIBLE);
        ClassicsListViewAdapter classicsListViewAdapter =
                new ClassicsListViewAdapter(ClassicsJiFragment.this.getContext());
        listView.setAdapter(classicsListViewAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ClassicsJiFragment.this.getContext(), ClassicsItemActivity.class);
                intent.putExtra("classics_id", classics_array[position][0]); //传入ID
                intent.putExtra("classics_chapter_name", classics_array[position][1]);//传入书籍名称
                intent.putExtra("classics_introduce", classics_introduce[position]);//传入简介
                startActivity(intent);
            }
        });
    }

    //获取信息的线程内部类
    public class GetClassicsJiThread extends Thread
    {
        @Override
        public void run() {
            classics_list = new ArrayList<>();
            classics_list = InforUtil.getClassicsJingData("集部");
            //courseGroup_ls: 结果:{[课程号,名称,作者,简介路径名...],[a,b,c]}
            if (!classics_list.isEmpty() && classics_list.size() != 0) {
                classics_array = StrListChange.ListToTwoStringArray(classics_list);
            }
            classics_introduce = new String[classics_array.length];
            //获取完该大类的全部内容后，
            for (int i = 0; i < classics_array.length; i++) {
                //classics_array[i][4]代表路径：如: 经部\论语\简介.txt
                classics_introduce[i] = InforUtil.getBookTxtRelative("book/经史子集", classics_array[i][4]);
            }
            Message message = Message.obtain();
            message.what = 1;
            handler.sendMessage(message);
        }
    }
    //内部类适配器
    class ClassicsListViewAdapter extends BaseAdapter
    {
        LayoutInflater layoutInflater;
        public ClassicsListViewAdapter(Context context)
        {
            this.layoutInflater = LayoutInflater.from(context);
        }
        @Override
        public int getCount()
        {
            return classics_array.length;
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
            ClassicsJiFragment.ClassicsListViewAdapter.ViewClass viewClass;
            if(view==null)
            {
                view = layoutInflater.inflate(R.layout.classics_item,null);
                viewClass = new ClassicsJiFragment.ClassicsListViewAdapter.ViewClass();
                //默认外观设置
                viewClass.imageView = (ImageView) view.findViewById(R.id.classics_item_img);
                viewClass.textView_name = (TextView) view.findViewById(R.id.classics_item_bookName);
                viewClass.nc = (TextView) view.findViewById(R.id.classics_item_author);
                viewClass.introduce = (TextView) view.findViewById(R.id.classics_item_introduce);

                view.setTag(viewClass);//很重要  要写
            }else {
                viewClass = (ClassicsJiFragment.ClassicsListViewAdapter.ViewClass)view.getTag();
            }
            viewClass.textView_name.setText(classics_array[i][1]);
            viewClass.nc.setText("作者:" + classics_array[i][2]);
            viewClass.introduce.setText(classics_introduce[i]);
            return view;        }
        private class ViewClass
        {
            ImageView imageView;
            TextView textView_name;
            TextView nc;
            TextView introduce;
        }
    }
}
