package com.example.gxcg.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.gxcg.R;
import com.example.gxcg.util.InforUtil;
import com.example.gxcg.util.MyGetBitmap;
import com.example.gxcg.util.StrListChange;

import java.util.ArrayList;
import java.util.List;

public class LsdyDetailsActivity extends Activity
{
    String infor[];
    private TextView title;
    private TextView twr;
    private TextView rq;
    private ImageView tx;
    private TextView nr;
    byte pic[];
    //ViewPager
    View view;//加载的界面布局
    ViewPager viewPager;//viewpager
    MyPagerAdapter adapter;//轮图适配器
    private ArrayList<View> fragments;//轮图管理
    private ListView list;//列表管理
    private BaseAdapter ba;//列表适配器
    private Button add;//发表评论
    private EditText fbtext;
    private List<String[]> rel=new ArrayList<>();
    String result;
    View lsdylist;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_lsdy_details);
        init();
    }

    private void initListView()
    {
        //加载热门评论列表界面
        LayoutInflater li = getLayoutInflater();
        LayoutInflater inflater=LayoutInflater.from(this);
        lsdylist=inflater.inflate(R.layout.fragment_study_list,null,false);//评论列表(view)
        list=lsdylist.findViewById(R.id.study_list);//（listview）

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
                if (relativeLayout==null) {
                    //每一项评论的视图（外面看）
                    relativeLayout=(RelativeLayout) (li.inflate(R.layout.activity_lsdycomments_listitem, null).findViewById(R.id.lsdyItem));
                }

                RelativeLayout relayout=(RelativeLayout)relativeLayout.getChildAt(0);

                ImageView yhimg=(ImageView)relayout.getChildAt(0);//回答的用户的图片
                //ImageView more=(ImageView)relayout.getChildAt(1);//更多（三点）的图片
                //每一项回答的各种文字
                //TextView hdrs=(TextView)relayout.getChildAt(2);
                TextView yh=(TextView)relayout.getChildAt(1);//用户名
                TextView sj=(TextView)relayout.getChildAt(2);//时间
                //TextView dy_tm=(TextView)relayout.getChildAt(4);//题目
                TextView dy_nr=(TextView)relayout.getChildAt(3);//内容

                yh.setText(rel.get(i)[1]);
                sj.setText(rel.get(i)[3]);
                dy_nr.setText(rel.get(i)[2]);
                //获取用户图片的线程
                Thread thread=new Thread()
                {
                    @Override
                    public void run() {
                        pic = InforUtil.getPicbyEntirelyPath(rel.get(i)[5]);
                    }
                };
                thread.start();
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (pic!=null)
                {
                    Bitmap bit= BitmapFactory.decodeByteArray(pic,0, pic.length);
                    yhimg.setImageBitmap(MyGetBitmap.makeRoundCorner(bit));
                }
                //删除更多按钮
                return relativeLayout;
            }
        };
        list.setAdapter(ba);
    }

    public void init()
    {
        view = findViewById(android.R.id.content); // 获取根视图
        infor=this.getIntent().getStringArrayExtra("infor");
        title=this.findViewById(R.id.tmtitle);
        twr=this.findViewById(R.id.twr);
        rq=this.findViewById(R.id.rq);
        tx=this.findViewById(R.id.tx);
        nr=this.findViewById(R.id.nr);
        Thread thread=new Thread()
        {
            @Override
            public void run() {
                pic = InforUtil.getPicbyEntirelyPath(infor[6]);
            }
        };
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (pic!=null)
        {
            Bitmap bit= BitmapFactory.decodeByteArray(pic,0, pic.length);
            tx.setImageBitmap(MyGetBitmap.makeRoundCorner(bit));
        }
        title.setText(infor[2]);
        twr.setText(infor[1]);
        rq.setText(infor[4]);

        nr.setText(infor[3]);
        GetDyCommentsThread getDyCommentsThread=new GetDyCommentsThread();
        getDyCommentsThread.start();
        try {
            getDyCommentsThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        initListView();
        viewPager=view.findViewById(R.id.comments_Pager);//获取热门评论的ViewPager
        fragments=new ArrayList<>();
        //加载漫画
        fragments.add(lsdylist);
        //设置view pager适配器
        adapter = new MyPagerAdapter(fragments);
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(
                new ViewPager.OnPageChangeListener()
                {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    }
                    @Override
                    public void onPageSelected(int position) {
                    }
                    @Override
                    public void onPageScrollStateChanged(int state) {
                    }
                }
        );
        //点击发表按钮
        fbtext=view.findViewById(R.id.fbpltext);
        add=view.findViewById(R.id.fbpl);
        add.setOnClickListener
                (e-> {
                            String fbtextcontent=fbtext.getText().toString();
                            if (fbtextcontent.equals(""))
                            {
                                Toast.makeText(this,"内容不能为空",Toast.LENGTH_SHORT).show();
                                return;
                            }
                            Thread threadfb=new Thread()
                            {
                                @Override
                                public void run() {
                                    //增加评论内容的方法
                                    result= InforUtil.insertDYComments(infor[0],fbtextcontent,MainActivity.userMess[0]);
                                }
                            };
                            threadfb.start();
                            try {
                                threadfb.join();
                            } catch (InterruptedException ie) {
                                ie.printStackTrace();
                            }
                            if (result.equals("1"))
                            {
                                Toast.makeText(this,"评论发表成功",Toast.LENGTH_SHORT).show();
                            }
                            //收起键盘
                            fbtext.setText("");
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(fbtext.getWindowToken(), 0);
                        }
                );
    }
    public class MyPagerAdapter extends PagerAdapter {
        private ArrayList<View> viewLists;

        public MyPagerAdapter() {
        }

        public MyPagerAdapter(ArrayList<View> viewLists) {
            super();
            this.viewLists = viewLists;
        }

        @Override
        public int getCount() {
            return viewLists.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(viewLists.get(position));
            return viewLists.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(viewLists.get(position));
        }
    }
    private class GetDyCommentsThread extends Thread
    {
        @Override
        public void run() {
            result= InforUtil.getAllDyComments(infor[0]);

            if (result==null || result.trim().isEmpty()) {
            }
            else//result不为空
            {
                rel.clear();
                String[] str= StrListChange.StrToArray(result);
                for (int i=0;i<str.length;i+=6)
                {
                    String[] temp=new String[]{str[i],str[i+1],str[i+2],str[i+3],str[i+4],str[i+5]};
                    Log.d("zzzzzz","0为"+temp[0]);//说明此处没问题
                    rel.add(temp);
                }
            }
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

