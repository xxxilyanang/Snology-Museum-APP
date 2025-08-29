package com.example.gxcg.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.gxcg.R;
import com.example.gxcg.util.Constant;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

public class JZVideoFragment extends Fragment {
    View view;//加载的界面布局
    JzvdStd jzvdStd1;
    JzvdStd jzvdStd2;
    JzvdStd jzvdStd3;
    JzvdStd jzvdStd4;
    JzvdStd jzvdStd5;
    JzvdStd jzvdStd6;
    JzvdStd jzvdStd7;
    JzvdStd jzvdStd8;
    JzvdStd jzvdStd9;
    JzvdStd jzvdStd10;
    JzvdStd jzvdStd11;
    JzvdStd jzvdStd12;
    JzvdStd jzvdStd13;
    JzvdStd jzvdStd14;

    public JZVideoFragment() {
        // Required empty public constructor

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //预加载界面布局view
        view = inflater.inflate(R.layout.fragment_jzvideo,null);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initview();//初始化界面
    }

    void initview(){
        jzvdStd1 = view.findViewById(R.id.jz_video1);
        jzvdStd2 = view.findViewById(R.id.jz_video2);
        jzvdStd3 = view.findViewById(R.id.jz_video3);
        jzvdStd4 = view.findViewById(R.id.jz_video4);
        jzvdStd5 = view.findViewById(R.id.jz_video5);
        jzvdStd6 = view.findViewById(R.id.jz_video6);
        jzvdStd7 = view.findViewById(R.id.jz_video7);
        jzvdStd8 = view.findViewById(R.id.jz_video8);
        jzvdStd9 = view.findViewById(R.id.jz_video9);
        jzvdStd10 = view.findViewById(R.id.jz_video10);
        jzvdStd11 = view.findViewById(R.id.jz_video11);
        jzvdStd12 = view.findViewById(R.id.jz_video12);
        jzvdStd13 = view.findViewById(R.id.jz_video13);
        jzvdStd14 = view.findViewById(R.id.jz_video14);

        //视频配置
        jzvdStd1.setUp(  Constant.ADD_PRE + "video/shortvideo1.mp4", ""
                , JzvdStd.SCREEN_NORMAL);
        //缩略图
        Glide.with(this).load(Constant.ADD_PRE + "pic/shortvideo_pic/pic1.jpg").into(jzvdStd1.posterImageView);
        //jzvdStd.posterImageView.setImage("http://p.qpic.cn/videoyun/0/2449_43b6f696980311e59ed467f22794e792_1/640");
        //自动播放视频
        //jzvdStd.startVideo();

        //其他视频
        jzvdStd2.setUp(  Constant.ADD_PRE + "video/shortvideo2.mp4", ""
                , JzvdStd.SCREEN_NORMAL);
        Glide.with(this).load(Constant.ADD_PRE + "pic/shortvideo_pic/pic2.jpg").into(jzvdStd2.posterImageView);

        jzvdStd3.setUp(  Constant.ADD_PRE + "video/shortvideo3.mp4", ""
                , JzvdStd.SCREEN_NORMAL);
        Glide.with(this).load(Constant.ADD_PRE + "pic/shortvideo_pic/pic3.jpg").into(jzvdStd3.posterImageView);

        jzvdStd4.setUp(  Constant.ADD_PRE + "video/shortvideo4.mp4", ""
                , JzvdStd.SCREEN_NORMAL);
        Glide.with(this).load(Constant.ADD_PRE + "pic/shortvideo_pic/pic4.jpg").into(jzvdStd4.posterImageView);

        jzvdStd5.setUp(  Constant.ADD_PRE + "video/shortvideo5.mp4", ""
                , JzvdStd.SCREEN_NORMAL);
        Glide.with(this).load(Constant.ADD_PRE + "pic/shortvideo_pic/pic5.jpg").into(jzvdStd5.posterImageView);

        jzvdStd6.setUp(  Constant.ADD_PRE + "video/shortvideo6.mp4", ""
                , JzvdStd.SCREEN_NORMAL);
        Glide.with(this).load(Constant.ADD_PRE + "pic/shortvideo_pic/pic6.jpg").into(jzvdStd6.posterImageView);

        jzvdStd7.setUp(  Constant.ADD_PRE + "video/shortvideo7.mp4", ""
                , JzvdStd.SCREEN_NORMAL);
        Glide.with(this).load(Constant.ADD_PRE + "pic/shortvideo_pic/pic7.jpg").into(jzvdStd7.posterImageView);

        jzvdStd8.setUp(  Constant.ADD_PRE + "video/shortvideo8.mp4", ""
                , JzvdStd.SCREEN_NORMAL);
        Glide.with(this).load(Constant.ADD_PRE + "pic/shortvideo_pic/pic8.jpg").into(jzvdStd8.posterImageView);

        jzvdStd9.setUp(  Constant.ADD_PRE + "video/shortvideo9.mp4", ""
                , JzvdStd.SCREEN_NORMAL);
        Glide.with(this).load(Constant.ADD_PRE + "pic/shortvideo_pic/pic9.jpg").into(jzvdStd9.posterImageView);

        jzvdStd10.setUp(  "https://stream7.iqilu.com/10339/upload_transcode/202002/17/20200217101826WjyFCbUXQ2.mp4",
                "饺子会旋转"
                , JzvdStd.SCREEN_NORMAL);
        Glide.with(this).load("http://www.baidu.com/img/bdlogo.png").into(jzvdStd10.posterImageView);

        jzvdStd11.setUp(  "https://stream7.iqilu.com/10339/upload_transcode/202002/17/20200217101826WjyFCbUXQ2.mp4",
                "饺子会旋转"
                , JzvdStd.SCREEN_NORMAL);
        Glide.with(this).load("http://www.baidu.com/img/bdlogo.png").into(jzvdStd11.posterImageView);

        jzvdStd12.setUp(  "https://stream7.iqilu.com/10339/upload_transcode/202002/17/20200217101826WjyFCbUXQ2.mp4",
                "饺子会旋转"
                , JzvdStd.SCREEN_NORMAL);
        Glide.with(this).load("http://www.baidu.com/img/bdlogo.png").into(jzvdStd12.posterImageView);

        jzvdStd13.setUp(  "https://stream7.iqilu.com/10339/upload_transcode/202002/17/20200217101826WjyFCbUXQ2.mp4",
                "饺子会旋转"
                , JzvdStd.SCREEN_NORMAL);
        Glide.with(this).load("http://www.baidu.com/img/bdlogo.png").into(jzvdStd13.posterImageView);

        jzvdStd14.setUp(  "https://stream7.iqilu.com/10339/upload_transcode/202002/17/20200217101826WjyFCbUXQ2.mp4",
                "饺子会旋转"
                , JzvdStd.SCREEN_NORMAL);
        Glide.with(this).load("http://www.baidu.com/img/bdlogo.png").into(jzvdStd14.posterImageView);
    }

    @Override
    public void onPause() {
        super.onPause();
        Jzvd.releaseAllVideos();
    }
}