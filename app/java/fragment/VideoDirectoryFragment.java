package com.example.gxcg.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.gxcg.R;
import com.example.gxcg.util.InforUtil;
import com.example.gxcg.util.StrListChange;

import java.util.ArrayList;
import java.util.List;

public class VideoDirectoryFragment extends Fragment {

    View view;//加载的界面布局

    private String course_video_id;
    //数据存放:
    private List<String[]> directoryInfo_list;
    private String[][] directoryInfo_array;

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

    //更新内容:
    private void initClassicsListView() {
        ListView listView = view.findViewById(R.id.lv_video_directory);
        listView.setVisibility(View.VISIBLE);
        VideoDirectoryAdapter videoDirectoryAdapter =
                new VideoDirectoryAdapter(VideoDirectoryFragment.this.getContext());
        listView.setAdapter(videoDirectoryAdapter);
    }

    //构造器
    public VideoDirectoryFragment(String course_video_id)
    {
        this.course_video_id = course_video_id;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        view=inflater.inflate(R.layout.fragment_video_directory,null);
        //获取所有的目录信息
        GetVideoDirectoryThread getVideoDirectoryThread = new GetVideoDirectoryThread();
        getVideoDirectoryThread.start();
        try {
            getVideoDirectoryThread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    //获取信息的线程内部类
    public class GetVideoDirectoryThread extends Thread
    {
        @Override
        public void run() {
            directoryInfo_list = new ArrayList<>();
            //获取国学课程视频目录信息
            directoryInfo_list = InforUtil.getVideoDirectoryData(course_video_id);
            //courseGroup_ls: 结果:{[课程号,名称,作者,简介路径名...],[a,b,c]}
            if (!directoryInfo_list.isEmpty() && directoryInfo_list.size() != 0) {
                directoryInfo_array = StrListChange.ListToTwoStringArray(directoryInfo_list);
            }
            Message message = Message.obtain();
            message.what = 1;
            handler.sendMessage(message);
        }
    }

    //内部类适配器
    class VideoDirectoryAdapter extends BaseAdapter
    {
        LayoutInflater layoutInflater;
        public VideoDirectoryAdapter(Context context)
        {
            this.layoutInflater = LayoutInflater.from(context);
        }
        @Override
        public int getCount()
        {
            return directoryInfo_array.length;
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
            VideoDirectoryAdapter.ViewClass viewClass;
            if(view==null)
            {
                view = layoutInflater.inflate(R.layout.fragment_videodirectory_listitem,null);
                viewClass = new VideoDirectoryAdapter.ViewClass();
                //默认外观设置
                viewClass.name =  (TextView) view.findViewById(R.id.tv_video_course_name);
                viewClass.index = (TextView) view.findViewById(R.id.tv_video_course_index);
                viewClass.schedule = (TextView) view.findViewById(R.id.tv_video_course_schedule);
                view.setTag(viewClass);//很重要  要写
            }else {
                viewClass = (VideoDirectoryAdapter.ViewClass)view.getTag();
            }
            viewClass.name.setText(directoryInfo_array[i][2]);
            viewClass.index.setText(i+"");
            viewClass.schedule.setText(directoryInfo_array[i][3] + "  学习进度:" + directoryInfo_array[i][4]);
            return view;        }
        private class ViewClass
        {
            TextView name;
            TextView index;
            TextView schedule;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
