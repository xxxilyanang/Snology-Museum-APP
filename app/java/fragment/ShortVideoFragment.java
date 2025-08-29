package com.example.gxcg.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.gxcg.R;
import com.example.gxcg.activity.MainActivity;
import com.example.gxcg.util.Constant;
import com.example.gxcg.util.OnlineVideoListItem;
import com.example.gxcg.item.VideoListItem;
import com.example.gxcg.util.VideoWatchAdapter;
import com.volokh.danylo.video_player_manager.manager.PlayerItemChangeListener;
import com.volokh.danylo.video_player_manager.manager.SingleVideoPlayerManager;
import com.volokh.danylo.video_player_manager.manager.VideoPlayerManager;
import com.volokh.danylo.video_player_manager.meta.MetaData;
import com.volokh.danylo.visibility_utils.calculator.DefaultSingleItemCalculatorCallback;
import com.volokh.danylo.visibility_utils.calculator.ListItemsVisibilityCalculator;
import com.volokh.danylo.visibility_utils.calculator.SingleListViewItemActiveCalculator;
import com.volokh.danylo.visibility_utils.scroll_utils.RecyclerViewItemPositionGetter;

import java.util.ArrayList;
import java.util.List;
public class ShortVideoFragment extends Fragment
{
//    View view;//加载的界面布局
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
//    {
//        view = inflater.inflate(R.layout.fragment_shortvideo,null); //获得当前主布局
//
//        return view;
//    }
//    @Override
//    public void onHiddenChanged(boolean hidden) {
//        super.onHiddenChanged(hidden);
//        Intent intent=new Intent(getActivity(), ShortVideoActivity.class);
//        startActivity(intent);
//    }
    View view;//加载的界面布局
    ViewPager viewPager;//滑动框
    TextView page1;//按键1
    MyPagerAdapter adapter;//轮图适配器
    private ArrayList<View> fragments;//轮图管理
    private RecyclerView mRecyclerView;//视频播放列表

    private List<String[]> rel=new ArrayList<>();

    boolean isPlaying;//判断是否在播放界面
    String result;
    //视频数据，相当于普通adapter里的datas
    private List<VideoListItem> mLists = new ArrayList<VideoListItem>();
    //它充当ListItemsVisibilityCalculator和列表（ListView, RecyclerView）之间的适配器（Adapter）。
    private RecyclerViewItemPositionGetter mItemsPositionGetter;

    private final ListItemsVisibilityCalculator mVideoVisibilityCalculator =
            new SingleListViewItemActiveCalculator(new DefaultSingleItemCalculatorCallback(), mLists);

    private int mScrollState;
    private LinearLayoutManager mLayoutManager = new LinearLayoutManager(MainActivity.activity); //这个上下文到底该用哪个呢
    private static final String URL =
            Constant.ADD_PRE+"video/";
    //当一个view开始播放时，之前那个就会停止,SingleVideoPlayerManager就是只能同时播放一个视频。
    private final VideoPlayerManager<MetaData> mVideoPlayerManager = new SingleVideoPlayerManager(new PlayerItemChangeListener()
    {
        @Override
        public void onPlayerItemChanged(MetaData metaData) {
        }
    });
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_shortvideo,null); //获得当前主布局
        init();
        Log.d("Error", "SVF666: ");
        mVideoPlayerManager.stopAnyPlayback(); // 页面不显示时, 暂停播放器
        return view;
    }
    public void init()
    {
        rel.add(new String[]{"guoxue1.mp4","五行相生相克论"});
        rel.add(new String[]{"guoxue3.mp4","国学书籍篇鉴赏"});

        LayoutInflater li = getLayoutInflater();

        page1=view.findViewById(R.id.page1);

        viewPager=view.findViewById(R.id.hsmfd_viewpager);
        fragments = new ArrayList<View>();

        //加载视频界面
        View hsmfd_pager2=li.inflate(R.layout.activity_hsmfd_pager2,null,false);
        mRecyclerView = (RecyclerView)hsmfd_pager2.findViewById(R.id.pfsp_listview);
        initVideoPlayer();

        //加载视频
        fragments.add(hsmfd_pager2);

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
                        if (position==0)
                        {
                            isPlaying=true;
                            page1.setBackgroundResource(R.drawable.activity_hsmfd_page1);
                            page1.setTextColor(getResources().getColor(R.color.white));
                            //page2.setBackgroundResource(R.drawable.activity_hsmfd_page2);
                            //page2.setTextColor(getResources().getColor(R.color.red));
                        }
                        else
                        {
                            isPlaying=false;
                            mVideoPlayerManager.stopAnyPlayback(); // 页面不显示时, 暂停播放器
                            page1.setBackgroundResource(R.drawable.activity_hsmfd_page4);
                            page1.setTextColor(getResources().getColor(R.color.red));
                            //page2.setBackgroundResource(R.drawable.activity_hsmfd_page3);
                            //page2.setTextColor(getResources().getColor(R.color.white));
                        }
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                }
        );
    }
    //初始化视频播放器
    public void initVideoPlayer()
    {
        mLists.clear();
        //添加视频数据
        for (int i = 0; i < rel.size(); i++) {
            mLists.add(new OnlineVideoListItem(mVideoPlayerManager, rel.get(i)[1],   //介绍
                    Constant.ADD_PRE+"articlepic/article_100031.png",
                    URL+rel.get(i)[0]));                                    //视频名称
        }
        mRecyclerView.setLayoutManager(mLayoutManager);
        VideoWatchAdapter adapter = new VideoWatchAdapter(mLists);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int scrollState) {
                mScrollState = scrollState;
                if(scrollState == RecyclerView.SCROLL_STATE_IDLE && !mLists.isEmpty()){

                    mVideoVisibilityCalculator.onScrollStateIdle(
                            mItemsPositionGetter,
                            mLayoutManager.findFirstVisibleItemPosition(),
                            mLayoutManager.findLastVisibleItemPosition());
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if(!mLists.isEmpty()){
                    mVideoVisibilityCalculator.onScroll(
                            mItemsPositionGetter,
                            mLayoutManager.findFirstVisibleItemPosition(),
                            mLayoutManager.findLastVisibleItemPosition() - mLayoutManager.findFirstVisibleItemPosition() + 1,
                            mScrollState);
                }
            }
        });
        mItemsPositionGetter = new RecyclerViewItemPositionGetter(mLayoutManager, mRecyclerView);
    }

    @Override
    public void onPause() {
        super.onPause();
        mVideoPlayerManager.stopAnyPlayback(); // 页面不显示时, 暂停播放器
    }

    @Override
    public void onResume() {
        super.onResume();
        if(!mLists.isEmpty()){
            // need to call this method from list view handler in order to have filled list

            mRecyclerView.post(new Runnable() {
                @Override
                public void run() {

                    mVideoVisibilityCalculator.onScrollStateIdle(
                            mItemsPositionGetter,
                            mLayoutManager.findFirstVisibleItemPosition(),
                            mLayoutManager.findLastVisibleItemPosition());
                }
            });

        }
    }

    @Override
    public void onStop() {
        super.onStop();
        mVideoPlayerManager.resetMediaPlayer(); // 页面不显示时, 释放播放器
    }

//    @Override
//    protected void onRestart() {
//        super.onRestart();
//    }

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
}
