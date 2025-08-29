package com.example.gxcg.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.gxcg.R;
import com.example.gxcg.util.Constant;
import com.example.gxcg.util.InforUtil;
import com.example.gxcg.util.MyGetBitmap;

public class VideoProfileFragment extends Fragment {
    private View view;

    //数据
    private String teacher_name;
    private String teacher_pic_url;
    private String teacher_introduce;
    private String learn_number;
    private String video_course_brief;
    private String video_brief_picUrl;
    private String course_introduce;

    //控件
    private TextView tv_video_course_brief;
    private TextView tv_learn_number;
    private TextView tv_teacher_name;
    private ImageView iv_teacher_pic_url;
    private TextView tv_teacher_introduce;
    private TextView tv_course_introduce;
    private ImageView iv_video_brief_picUrl;

    //加载过后的图片
    private Bitmap teacher_pic;
    private Bitmap introduce_pic;
    private byte[] teacher_pic_bytes;
    private byte[] introduce_pic_bytes;


    private Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case 1:
                    initView();
                    break;
            }
        }
    };
    public VideoProfileFragment(
            String teacher_name, String teacher_pic_url, String teacher_introduce,
            String learn_number, String video_course_brief, String video_brief_picUrl,
            String course_introduce
    )
    {
        this.teacher_name = teacher_name;
        this.teacher_pic_url = teacher_pic_url;
        this.teacher_introduce = teacher_introduce;
        this.learn_number = learn_number;
        this.video_course_brief = video_course_brief;
        this.video_brief_picUrl = video_brief_picUrl;
        this.course_introduce = course_introduce;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        view=inflater.inflate(R.layout.fragment_video_profile, container, false);
        tv_video_course_brief = view.findViewById(R.id.tv_video_course_brief);
        tv_learn_number = view.findViewById(R.id.tv_learn_number);
        tv_teacher_name = view.findViewById(R.id.tv_teacher_name);
        iv_teacher_pic_url = view.findViewById(R.id.iv_teacher_pic_url);
        tv_teacher_introduce = view.findViewById(R.id.tv_teacher_introduce);
        tv_course_introduce = view.findViewById(R.id.tv_course_introduce);
        iv_video_brief_picUrl = view.findViewById(R.id.iv_video_brief_picUrl);

        MyThread myThread = new MyThread();
        myThread.start();
        try {
            myThread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    public void initView()
    {
        tv_video_course_brief.setText(video_course_brief);
        tv_learn_number.setText("共学习了"+learn_number+"人");
        tv_teacher_name.setText(teacher_name);
        tv_teacher_introduce.setText(teacher_introduce);
        iv_teacher_pic_url.setImageBitmap(teacher_pic);
        iv_video_brief_picUrl.setImageBitmap(introduce_pic);
        tv_course_introduce.setText(course_introduce);
        //setImageViewWideHigh(iv_video_brief_picUrl, introduce_pic);
        //setImageViewWideHigh(iv_teacher_pic_url, teacher_pic);
    }

    //已完成
    public class MyThread extends Thread
    {
        @Override
        public void run() {
            teacher_pic_bytes = InforUtil.getPicbyEntirelyPath(teacher_pic_url);
            introduce_pic_bytes = InforUtil.getPicbyEntirelyPath(video_brief_picUrl);
            Log.d("TAG-CVA", "onItemClick:  " +
                    Constant.ADD_PRE  + teacher_pic_url);

            if (teacher_pic_bytes != null && introduce_pic_bytes != null)
            {
                teacher_pic = MyGetBitmap.getBitmapFromByteArray(teacher_pic_bytes);
                introduce_pic = MyGetBitmap.getBitmapFromByteArray(introduce_pic_bytes);
            }
            Message message = Message.obtain();
            message.what = 1;
            handler.sendMessage(message);
        }
    }

    //设置图片适配比例
    public void setImageViewWideHigh(ImageView imageView, Bitmap bitmap) {
        ViewGroup.LayoutParams params = imageView.getLayoutParams();
        // 获得bitmap宽高
        float bitWidth = bitmap.getWidth();
        float bithight = bitmap.getHeight();
        float bitScalew = bithight / bitWidth;
        // 获得屏幕宽高（有多种方式、用自己喜欢的）
        WindowManager manager = this.getActivity().getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        int imgWidth = outMetrics.widthPixels;
        int imgHight = outMetrics.heightPixels;
        // 根据需求展示长图、宽填满，高按比例设置
        params.width = (int) imgWidth;
        params.height = (int) (imgWidth * bitScalew);
        // ImageView 控件设置
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setAdjustViewBounds(true);
        imageView.setLayoutParams(params);
        imageView.setImageBitmap(bitmap);
    }
}
