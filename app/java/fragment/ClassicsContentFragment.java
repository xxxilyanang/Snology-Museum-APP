package com.example.gxcg.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.gxcg.R;
import com.example.gxcg.util.InforUtil;

public class ClassicsContentFragment extends Fragment {

    //界面组件
    private View view;//加载的界面布局
    private TextView tv_name;
    private TextView tv_content;
    private String content;
    private String name;
    private String url;
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
                    initClassicsContent();
                    break;
            }
        }
    };
    //构造方法
    public ClassicsContentFragment(String name, String content_url)
    {
        this.name = name;
        this.url = content_url;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //预加载界面布局view
        view = inflater.inflate(R.layout.fragment_classics_content_inpager,null);
        tv_name = view.findViewById(R.id.tv_classicsContent_name);
        tv_content = view.findViewById(R.id.tv_classicsContent);
        //线程获取数据库内信息：放在容器里
        //从服务器磁盘中加载文本信息
        GetClassicsContentThread getClassicsContentThread = new GetClassicsContentThread();
        getClassicsContentThread.start();
        try {
            getClassicsContentThread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    //获取信息的线程内部类
    public class GetClassicsContentThread extends Thread
    {
        @Override
        public void run() {
            content = InforUtil.getBookTxtRelative("book", url);
            Message message = Message.obtain();
            message.what = 1;
            handler.sendMessage(message);
        }
    }

    //更新UI的方法
    public void initClassicsContent()
    {
        tv_name.setText(name);
        tv_content.setText(content);
    }
}
