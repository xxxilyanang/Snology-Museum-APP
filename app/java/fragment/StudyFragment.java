package com.example.gxcg.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.gxcg.R;
import com.example.gxcg.activity.AddLsdyActivity;
import com.example.gxcg.activity.LsdyDetailsActivity;
import com.example.gxcg.util.InforUtil;
import com.example.gxcg.util.MyGetBitmap;
import com.example.gxcg.util.StrListChange;

import java.util.ArrayList;
import java.util.List;

public class StudyFragment extends Fragment implements View.OnClickListener
{
    //下滑刷新
    private SwipeRefreshLayout mSwipeRefreshLayout;
    //ViewPager
    View view;//加载的界面布局
    ViewPager viewPager;//viewpager
    MyPagerAdapter adapter;//轮图适配器
    private ArrayList<View> fragments;//轮图管理
    private ListView list;//列表管理
    private BaseAdapter ba;//列表适配器
    private ImageView add;//发表问题
    //获取电台信息
    private List<String[]> rel=new ArrayList<>();
    String result;
    byte[] pic;
    View lsdylist;
    //下滑刷新消息接收
    private Handler mHandler = new Handler()
    {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case 1:
                    initListView();
                    mSwipeRefreshLayout.setRefreshing(false);
                    break;
                default:
                    break;
            }
        }
    };
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //预加载界面布局view
        view = inflater.inflate(R.layout.fragment_study,null);
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();//初始化界面
    }
    //初始化列表
    private void initListView()
    {
        //加载律师答疑列表界面
        LayoutInflater li = getLayoutInflater();
        LayoutInflater inflater=LayoutInflater.from(requireContext());
        lsdylist=inflater.inflate(R.layout.fragment_study_list,null,false);//律师答疑列表(view)
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
                    //每一项答疑的视图（外面看）
                    relativeLayout=(RelativeLayout) (li.inflate(R.layout.fragment_study_listitem, null).findViewById(R.id.lsdyItem));
                }
                RelativeLayout relayout=(RelativeLayout)relativeLayout.getChildAt(0);
                relayout.setOnClickListener
                        (e->
                                {//每一项答疑点击跳转至里面具体的活动
                                    Intent intent=new Intent(getActivity(),LsdyDetailsActivity.class);
                                    intent.putExtra("infor",rel.get(i));
                                    startActivity(intent);
                                }
                        );
                ImageView yhimg=(ImageView)relayout.getChildAt(0);//回答的用户的图片
                ImageView more=(ImageView)relayout.getChildAt(1);//更多（三点）的图片
                //每一项回答的各种文字
                //TextView hdrs=(TextView)relayout.getChildAt(2);
                TextView yh=(TextView)relayout.getChildAt(2);
                TextView sj=(TextView)relayout.getChildAt(3);
                TextView dy_tm=(TextView)relayout.getChildAt(4);
                TextView dy_nr=(TextView)relayout.getChildAt(5);

                yh.setText(rel.get(i)[1]);
                sj.setText(rel.get(i)[4]);
                dy_nr.setText(rel.get(i)[3]);
                dy_tm.setText(rel.get(i)[2]);
                //获取用户图片的线程
                Thread thread=new Thread()
                {
                    @Override
                    public void run() {
                        pic = InforUtil.getPicbyEntirelyPath(rel.get(i)[6]);
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
                //更多 的点击监听事件
                more.setOnClickListener
                        (e->
                                {PopupMenu popup = new PopupMenu(getContext(),more);
                                    //收藏分享按钮弹出菜单
                                    popup.getMenuInflater().inflate(R.menu.menu_pop, popup.getMenu());
                                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                        @Override
                                        public boolean onMenuItemClick(MenuItem item) {
                                            switch (item.getItemId()){
                                                case R.id.sc:
                                                    //收藏逻辑
                                                    break;
                                                case R.id.fx:
                                                    //分享逻辑
                                                    break;
                                            }
                                            return true;
                                        }
                                    });
                                    popup.show();
                                }
                        );
                return relativeLayout;
            }
        };
        list.setAdapter(ba);
    }
    //初始化
    public void init()
    {
        GetDyInforThread getDyInforThread=new GetDyInforThread();
        getDyInforThread.start();
        try {
            getDyInforThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //点击发表按钮
        add=view.findViewById(R.id.mhxc);
        add.setOnClickListener
                (e-> {
                            //发表话题内容
                            Intent intent=new Intent(getActivity(), AddLsdyActivity.class);
                            startActivity(intent);
                        }
                );
        //点击8个热门话题跳转
        TextView textview01 = view.findViewById(R.id.textview01);
        TextView textview02 = view.findViewById(R.id.textview02);
        TextView textview03 = view.findViewById(R.id.textview03);
        TextView textview04 = view.findViewById(R.id.textview04);
        TextView textview05 = view.findViewById(R.id.textview05);
        TextView textview06 = view.findViewById(R.id.textview06);
        TextView textview07 = view.findViewById(R.id.textview07);
        TextView textview08 = view.findViewById(R.id.textview08);
        // 监听热门话题点击
        textview01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),LsdyDetailsActivity.class);
                intent.putExtra("infor",rel.get(0));
                //此处 0 相当于获取目前listview中第一行对应的数据,之后可以将8个热门话题数据提前保存到这样的数组（or 数据库中，单独获取）
                //新解决方法，直接将人们话题也当普通话题一样放在listview里，知道它是第几个话题就获取它就行
                startActivity(intent);
            }
        });
        textview02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),LsdyDetailsActivity.class);
                intent.putExtra("infor",rel.get(0));
                startActivity(intent);
            }
        });
        textview03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),LsdyDetailsActivity.class);
                intent.putExtra("infor",rel.get(0));
                startActivity(intent);
            }
        });
        textview04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),LsdyDetailsActivity.class);
                intent.putExtra("infor",rel.get(0));
                startActivity(intent);
            }
        });textview05.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent=new Intent(getActivity(),LsdyDetailsActivity.class);
            intent.putExtra("infor",rel.get(0));
            startActivity(intent);
        }
    });
        textview06.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),LsdyDetailsActivity.class);
                intent.putExtra("infor",rel.get(0));
                startActivity(intent);
            }
        });
        textview07.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),LsdyDetailsActivity.class);
                intent.putExtra("infor",rel.get(0));
                startActivity(intent);
            }
        });
        textview08.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),LsdyDetailsActivity.class);
                intent.putExtra("infor",rel.get(0));
                startActivity(intent);
            }
        });


        //初始化列表
        initListView();
        mSwipeRefreshLayout=(SwipeRefreshLayout) view.findViewById(R.id.swipeRefresh_Lsdy);
        mSwipeRefreshLayout.setColorSchemeResources(
                R.color.design_default_color_error,
                R.color.design_default_color_on_primary,
                R.color.design_default_color_primary
        );
        mSwipeRefreshLayout.setSize(SwipeRefreshLayout.DEFAULT);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh() {
                new Thread()
                {
                    @Override
                    public void run() {
                        mHandler.sendEmptyMessage(1);
                    }
                }.start();
            }
        });
        list.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                View firstView = view.getChildAt(firstVisibleItem);
                if(firstVisibleItem ==0 && (firstView == null || firstView.getTop() == 0)) {
                    /*上滑到listView的顶部时，下拉刷新组件可见*/
                    mSwipeRefreshLayout.setEnabled(true);
                } else {
                    /*不是listView的顶部时，下拉刷新组件不可见*/
                    mSwipeRefreshLayout.setEnabled(false);
                }
            }
        });
        viewPager=view.findViewById(R.id.lsdy_Pager);//获取律师答疑的ViewPager

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
    private class GetDyInforThread extends Thread
    {
        @Override
        public void run() {
            result= InforUtil.getAllDyInfor();

            if (result==null) {}
            else//result不为空
            {
                rel.clear();
                String[] str= StrListChange.StrToArray(result);
                for (int i=0;i<str.length;i+=7)
                {
                    String[] temp=new String[]{str[i],str[i+1],str[i+2],str[i+3],str[i+4],str[i+5],str[i+6]};
                    rel.add(temp);
                }
            }
        }
    }
    @Override
    public void onClick(View view) {
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}
