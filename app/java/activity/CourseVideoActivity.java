package com.example.gxcg.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.gxcg.R;
import com.example.gxcg.adapter.InVideoFragmentPagerAdapter;
import com.example.gxcg.fragment.VideoDirectoryFragment;
import com.example.gxcg.fragment.VideoProfileFragment;
import com.example.gxcg.fragment.VideoRemarkFragment;
import com.example.gxcg.item.VideoListItem;
import com.example.gxcg.util.Constant;
import com.example.gxcg.util.InforUtil;
import com.example.gxcg.util.StrListChange;
import com.google.android.material.tabs.TabLayout;
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

import cn.jzvd.JzvdStd;

public class CourseVideoActivity extends AppCompatActivity {

    //视频处理相关初始化
    //视频数据，相当于普通adapter里的data
    private List<VideoListItem> videoListItems = new ArrayList<>();
    //SingleVideoPlayerManager就是只能同时播放一个视频。当一个view开始播放时，之前那个就会停止
    private final VideoPlayerManager<MetaData> mVideoPlayerManager =
            new SingleVideoPlayerManager(new PlayerItemChangeListener() {
                @Override
                public void onPlayerItemChanged(MetaData metaData) {
                }
            });
    private final ListItemsVisibilityCalculator mVideoVisibilityCalculator =
            new SingleListViewItemActiveCalculator(new DefaultSingleItemCalculatorCallback(), videoListItems);
    private static final String URL = Constant.ADD_PRE+"video/";
    //视频所需组件
    private LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
    //它充当ListItemsVisibilityCalculator和列表（ListView, RecyclerView）之间的适配器（Adapter）。
    private RecyclerViewItemPositionGetter mItemsPositionGetter;
    private int mScrollState;

    //界面相关初始化
    private RecyclerView mRecyclerView;//视频播放列表
    private List<String> mTitles;
    private String [] title={"简介","目录","评论"};
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private InVideoFragmentPagerAdapter fragmentAdapter;
    private List<Fragment> fragmentList;
    private VideoDirectoryFragment videoDirectoryFragment;
    private VideoProfileFragment videoProfileFragment;
    private VideoRemarkFragment videoRemarkFragment;

    //初始化视频播放器
    private JzvdStd jzvdStd;

    //初始化上一个界面传过来的数据
    private String course_video_id;//用于查找数据
    private String course_video_name;
    private String course_introduce;        //该视频下的详细介绍
    private String course_video_picName;    //视频不播放时显示的图片
    //数据来
    private List<String[]> couVideo_ls;
    private String[][] couVideo_ay;
    //单个数据
    private String videoUrl;
    private String teacher_name;
    private String teacher_pic_url;
    private String teacher_introduce;
    private String learn_number;
    private String video_course_brief;  //该视频下的简短介绍
    private String video_brief_picUrl; //该视频下的长图介绍路径

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_video);
        tabLayout = findViewById(R.id.video_tab_layout);
        viewPager = findViewById(R.id.viewpager_video);
        jzvdStd = findViewById(R.id.jz_video_course);
        //这个数据初始化必须放在最前面!!
        initDataFromIntent();
        //启动联网线程
        getCourseVideoDataThread getCourseVideoDataThread = new getCourseVideoDataThread();
        getCourseVideoDataThread.start();
        try {
            getCourseVideoDataThread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //mRecyclerView = findViewById(R.id.video_recyclerView);
        initPager();//同时将所需数据传入Fragment，需要在初始化数据之后执行
        //最后才能播放视频
        initVideoPlayer(videoUrl, course_video_name, course_video_picName);
    }

    //初始化从上一个界面传过来的数据
    private void initDataFromIntent()
    {
        Intent intent = getIntent();
        course_video_id = intent.getStringExtra("course_video_id");
        course_video_name = intent.getStringExtra("course_video_name");
        course_introduce = intent.getStringExtra("course_introduce");
        course_video_picName = intent.getStringExtra("course_video_picUrl");
    }

    //初始化切换界面Pager
    private void initPager() {
        //创建并添加Fragment
        videoDirectoryFragment = new VideoDirectoryFragment(
                course_video_id
        );
        //传入显示用的数据
//        if (teacher_name != null && teacher_pic_url != null && teacher_introduce != null &&
//                learn_number != null && video_course_brief != null && video_brief_picUrl != null){
//            videoProfileFragment = new VideoProfileFragment(
//                    "某老师", "pic/video_course_pic_brief/default.jpg", "某简介",
//                    "20", "某课程简介", "pic/video_course_pic/阅史明理/cou5.jpg",
//                    course_introduce
//            );
//        }else{
            videoProfileFragment = new VideoProfileFragment(
                    teacher_name, teacher_pic_url, teacher_introduce,
                    learn_number, video_course_brief, video_brief_picUrl,
                    course_introduce);
//        }
        videoRemarkFragment = new VideoRemarkFragment(
                course_video_id
        );
        fragmentList=new ArrayList<>();
        fragmentList.add(videoProfileFragment);
        fragmentList.add(videoDirectoryFragment);
        fragmentList.add(videoRemarkFragment);
        mTitles = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            mTitles.add(title[i]);
        }
        fragmentAdapter = new InVideoFragmentPagerAdapter
                //getActivity().getSupportFragmentManager()
                (this.getSupportFragmentManager(),
                        fragmentList,
                        mTitles);
        viewPager.setAdapter(fragmentAdapter);
        tabLayout.setupWithViewPager(viewPager);//与ViewPage建立关系
    }

    //线程获取数据
    public class getCourseVideoDataThread extends Thread
    {
        @Override
        public void run() {
            couVideo_ls = new ArrayList<>();
            //根据单个视频下的短视频:
            //Log.d("TAG-couF", "onItemClick: "+ course_video_id);
            couVideo_ls = InforUtil.getCourseVideoBriefData(course_video_id);
            //courseGroup_ls: 结果:{[a,b,c],[a,b,c]}
            if (!couVideo_ls.isEmpty() && couVideo_ls.size() != 0) {
                couVideo_ay = StrListChange.ListToTwoStringArray(couVideo_ls);
            }
            if(couVideo_ay[0][7] != null)
                videoUrl = couVideo_ay[0][7];
            //这里只会获取一条数据:
            if(couVideo_ay[0][2] != null)
                teacher_name = couVideo_ay[0][2];
            if(couVideo_ay[0][3] != null)
                teacher_pic_url = couVideo_ay[0][3];
            if(couVideo_ay[0][4] != null)
                teacher_introduce = couVideo_ay[0][4];
            if(couVideo_ay[0][5] != null)
                learn_number = couVideo_ay[0][5];
            if(couVideo_ay[0][6] != null)
                video_course_brief  = couVideo_ay[0][6];
            if(couVideo_ay[0][8] != null)
                video_brief_picUrl = couVideo_ay[0][8];
        }
    }

    //初始化视频播放器
    private void initVideoPlayer(String videoUrl, String videoName, String course_video_picName) {
        //        if (videoListItems != null)
    //            videoListItems.clear();
            //添加视频数据
    //        for (int i = 0; i < rel.size(); i++) {
    //            videoListItems.add(
    //                    new OnlineVideoListItem(
    //                            mVideoPlayerManager,
    //                            rel.get(i)[5],
    //                            Constant.ADD_PRE+"img/mhtp_10006.png",
    //                            URL+rel.get(i)[1]));
    //
    //        }
    //        videoListItems.add(
    //                new OnlineVideoListItem(
    //                        mVideoPlayerManager,
    //                        "名称1",
    //                        Constant.ADD_PRE+"articlepic/moren.png",
    //                        URL+"guoxue1.mp4"));
    //        mRecyclerView.setLayoutManager(mLayoutManager);
    //        VideoDisplayAdapter adapter = new VideoDisplayAdapter(videoListItems);
    //        mRecyclerView.setAdapter(adapter);
    //        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
    //
    //            @Override
    //            public void onScrollStateChanged(RecyclerView recyclerView, int scrollState) {
    //                mScrollState = scrollState;
    //                if(scrollState == RecyclerView.SCROLL_STATE_IDLE && !videoListItems.isEmpty()){
    //
    //                    mVideoVisibilityCalculator.onScrollStateIdle(
    //                            mItemsPositionGetter,
    //                            mLayoutManager.findFirstVisibleItemPosition(),
    //                            mLayoutManager.findLastVisibleItemPosition());
    //                }
    //            }
    //            @Override
    //            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
    //                if(!videoListItems.isEmpty()){
    //                    mVideoVisibilityCalculator.onScroll(
    //                            mItemsPositionGetter,
    //                            mLayoutManager.findFirstVisibleItemPosition(),
    //                            mLayoutManager.findLastVisibleItemPosition() - mLayoutManager.findFirstVisibleItemPosition() + 1,
    //                            mScrollState);
    //                }
    //            }
    //        });
    //        mItemsPositionGetter = new RecyclerViewItemPositionGetter(mLayoutManager, mRecyclerView);
        if (videoUrl == null) {
            jzvdStd.setUp(
                    Constant.ADD_PRE + "video/guoxue1.mp4",
                    videoName
                    , JzvdStd.SCREEN_NORMAL);
        }else{
            jzvdStd.setUp(
                    Constant.ADD_PRE + videoUrl,
                    videoName
                    , JzvdStd.SCREEN_NORMAL);
        }
        Glide.with(this).
                load(Constant.ADD_PRE + "pic/" + "video_course_pic/" + course_video_picName).
                into(jzvdStd.posterImageView);
    }

    //生命周期回调方法
    @Override
    protected void onPause() {
        super.onPause();
        mVideoPlayerManager.stopAnyPlayback(); // 页面不显示时, 暂停播放器
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!videoListItems.isEmpty()){
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
    protected void onStop() {
        super.onStop();
        mVideoPlayerManager.resetMediaPlayer(); // 页面不显示时, 释放播放器
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }
}