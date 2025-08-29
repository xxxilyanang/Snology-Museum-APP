package com.example.gxcg.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.example.gxcg.R;
import com.example.gxcg.util.InforUtil;
import com.example.gxcg.util.MyGetBitmap;
import com.example.gxcg.util.StrListChange;

import java.util.ArrayList;
import java.util.List;

public class WenzhangDetailActivity extends AppCompatActivity {
    LinearLayout back;//返回按钮
    TextView wzname;//文章标题
    TextView wzinfo;//文章内容
    TextView wzau;//文章作者
    ImageView iv;
    String mes;//接受过来的消息  文章id号码
    Bitmap[] wzBitmap;//文章图片
    public static List<String[]> ls=new ArrayList<String[]>();
    byte[] pic;//图片字节
    String[][] wzlb;//文章列表
    List<String[]> wzlbpic;//文章图片
    private List<Integer> imgs = new ArrayList<Integer>();
    ConvenientBanner convenientBanner;
    String s;
    public List ChayeXx;
    Bitmap[] cybitmap;
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wzdetail);
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        mes=bundle.getString("mes");

        ActionBar actionbar=getSupportActionBar();
        if(actionbar!=null) {
            actionbar.hide();
        }
        MyThread my=new MyThread();
        my.start();
        try {
            my.join();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        convenientBanner=this.findViewById(R.id.convenientBanner);
        initconvenientBanner();//初始化广告栏
        wzname=this.findViewById(R.id.wz_backtext);//获取标题栏控件，用于传入文章名称
        wzname.setText(wzlb[0][1]);
        wzinfo=this.findViewById(R.id.wzdetail_text2);//获取文本框控件，用于传入文章内容
        wzinfo.setText(wzlb[0][2]);
        wzau=this.findViewById(R.id.wzdetail_text);
        wzau.setText(wzlb[0][3]);
    }
    public class MyThread extends Thread
    {
        @Override
        public void run()
        {
            //ls=new ArrayList<String[]>();
            ls = InforUtil.getSinglewz(mes);//获取单篇文章
            if(!ls.isEmpty()&&ls!=null)
            {
                wzlb= StrListChange.ListToTwoStringArray(ls);//获取返回结果wzlb
            }
            wzBitmap=new Bitmap[5];
            wzlbpic= InforUtil.getwzpic(wzlb[0][0]);//获取文章图片
            for(int i=0;i<wzlbpic.size();i++)
            {
                if(wzlb!=null)
                {
                    if (MyGetBitmap.isEmpty(wzlbpic.get(i)[0].trim())) {
                        pic = InforUtil.getPic("articlepic",wzlbpic.get(i)[0].trim());//从数据库获取图片
                        wzBitmap[i] = MyGetBitmap.getBitmapFromByteArray(pic);
                        wzBitmap[i] = MyGetBitmap.zoomImg(wzBitmap[i], 270, 270);
                        MyGetBitmap.setInSDBitmap(pic, wzlbpic.get(i)[0].trim());
                    } else {
                        wzBitmap[i] = MyGetBitmap.getSDBitmap(wzlbpic.get(i)[0].trim());
                        //缩小大小
                        wzBitmap[i] = MyGetBitmap.zoomImg(wzBitmap[i], 270, 270);
                        //不等于null就设置成null
                        if (MyGetBitmap.bitmap != null && !MyGetBitmap.bitmap.isRecycled()) {
                            MyGetBitmap.bitmap = null;
                        }
                    }
                }
            }
            if(ChayeXx!=null)//开辟内存
            {
                cybitmap = new Bitmap[5];
                for(int i=0;i<5;i++)
                {
                    if(MyGetBitmap.isEmpty(wzlbpic.get(i)[0].trim()))
                    {
                        pic = null;
                        pic = InforUtil.getPic("articlepic",wzlbpic.get(i)[0].trim());
                        cybitmap[i]= MyGetBitmap.getBitmapFromByteArray(pic);
                        cybitmap[i] = MyGetBitmap.zoomImg(cybitmap[i], 270, 270);
                        cybitmap[i] = MyGetBitmap.getRoundedCornerBitmap(cybitmap[i], 10.5f);
                        MyGetBitmap.setInSDBitmap(pic,wzlbpic.get(i)[0].trim());
                    }else {
                        cybitmap[i]=MyGetBitmap.getSDBitmap(wzlbpic.get(i)[0].trim());
                        cybitmap[i] = MyGetBitmap.zoomImg(cybitmap[i], 270, 270);
                        cybitmap[i] = MyGetBitmap.getRoundedCornerBitmap(cybitmap[i], 10.5f);
                        //不等于NULL就设置成NULL
                        if(MyGetBitmap.bitmap!=null&&!MyGetBitmap.bitmap.isRecycled())
                        {
                            MyGetBitmap.bitmap = null;
                        }
                    }
                }
            }
        }
    }
    public void initconvenientBanner()//初始化广告栏方法
    {
        for(int i = 0;i<wzlbpic.size();i++)
        {
            imgs.add(i);
        }
        convenientBanner.setPages(new CBViewHolderCreator() {
            @Override
            public Object createHolder() {
                return  new LocalImageHolderView();//获取轮播图片合集
            }
        },imgs)//设置需要切换的View
                .setPointViewVisible(true)//设置指示器是否可见
                .setPageIndicator(new int[]{R.drawable.d1,R.drawable.d2})//设置两个点图片作为翻页指示器
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)//设置指示器位置居中
                .startTurning(1800)//设置自动切换（同时设置了切换时间间隔）
                .setManualPageable(true);//设置手动影响（设置了该项可以手动切换）
    }
    public class LocalImageHolderView implements Holder<Integer> {
        private ImageView imageView;//轮播图片控件引用
        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);//创建轮播图片控件
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);//设置图片缩放属性为完全填充广告栏
            return imageView;//返回退片控件
        }
        @Override
        public void UpdateUI(Context context, int position, Integer data) {
            imageView.setImageBitmap(wzBitmap[data]);//显示对应图片
        }
    }

}
