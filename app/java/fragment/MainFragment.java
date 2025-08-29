package com.example.gxcg.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.gxcg.R;
import com.example.gxcg.activity.ClassicsActivity;
import com.example.gxcg.activity.MainActivity;
import com.example.gxcg.activity.PfdtActivity;
import com.example.gxcg.activity.WenzhangDetailActivity;
import com.example.gxcg.util.Constant;
//import com.example.gxcg.util.MyGetBitmap;

public class MainFragment extends Fragment implements View.OnClickListener
{
    public static TextView mainname;
    View view;//加载的界面布局
    private WebView wView;
    private WebView showView1;//显示内容
    private WebView showView2;//显示内容
    private WebSettings webSettings;
    //private SearchView searchView;
    SearchView searchView;//搜索框
    private Button jing;//儒家经典的按键
    private Button shi;//史书全录的按键
    private Button zi;//诸子百家
    private Button ji;//文集百部
    private ImageView v;//
    ImageView iv;//用户头像
    TextView edit;
    AlertDialog dialog;
    AlertDialog.Builder builder;
    TextView tex_1;
    LayoutInflater myinflater;
    AlertDialog dialog2;
    AlertDialog.Builder builder2;
    LayoutInflater myinflater2;
    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //预加载界面布局view
        view = inflater.inflate(R.layout.fragment_main,null);
        searchView = view.findViewById(R.id.mainfragment_search);
        //searchView.setHintTextColor(fontColor);
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();//初始化界面
    }
    //页面初始化方法
    @SuppressLint("JavascriptInterface")
    public void init()
    {
        //加载第一个webview
        wView =view.findViewById(R.id.binnerWeb);
        wView.setVerticalScrollBarEnabled(false); //去除上下滑动
        wView.setHorizontalScrollBarEnabled(false);//去除左右滑动
        wView.setInitialScale(100); //这个一般用来设置缩放倍率
        webSettings=wView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        wView.clearCache(true);
        wView.loadUrl( Constant.ADD_PRE+"binner_main_layout.jsp");
        wView.setWebChromeClient(new WebChromeClient() {
            //这里设置获取到的网站title
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }
        });
        wView.setWebViewClient(new WebViewClient() {
            //在webview里打开新链接
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        wView.setBackgroundColor(0);
        wView.getBackground().setAlpha(0);


        //加载第二个webview
        showView1=view.findViewById(R.id.webView1);
        showView1.setVerticalScrollBarEnabled(false); //去除上下滑动
        showView1.setHorizontalScrollBarEnabled(false);//去除左右滑动
        showView1.setInitialScale(100); //这个一般用来设置缩放倍率
        webSettings=showView1.getSettings();
        webSettings.setJavaScriptEnabled(true);
        showView1.clearCache(true);
        showView1.loadUrl( Constant.ADD_PRE+"show_main_layout1.jsp");
        showView1.setWebChromeClient(new WebChromeClient() {
            //这里设置获取到的网站title
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);

            }
        });
        showView1.addJavascriptInterface(new ShowBinner(),"showByBinner");

        showView1.setWebViewClient(new WebViewClient() {
            //在webview里打开新链接
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        //加载第三个webview
        showView2=view.findViewById(R.id.webView2);
        showView2.setVerticalScrollBarEnabled(false); //去除上下滑动
        showView2.setHorizontalScrollBarEnabled(false);//去除左右滑动
        showView2.setInitialScale(100); //这个一般用来设置缩放倍率
        webSettings=showView2.getSettings();
        webSettings.setJavaScriptEnabled(true);
        showView2.clearCache(true);
        showView2.loadUrl( Constant.ADD_PRE+"show_main_layout2.jsp");
        showView2.setWebChromeClient(new WebChromeClient() {
            //这里设置获取到的网站title
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);

            }
        });
        showView2.addJavascriptInterface(new ShowBinner(),"showByBinner");

        showView2.setWebViewClient(new WebViewClient() {
            //在webview里打开新链接
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        //searchView=view.findViewById(R.id.flyz_search);//获取搜索框--（不知道是否对）---

        jing=view.findViewById(R.id.btn_ruoJia);//获取儒家经典的模块按键
        jing.setOnClickListener(this);
        shi=view.findViewById(R.id.btn_shiShu);//获取史书全录的模块按键
        shi.setOnClickListener(this);
        zi=view.findViewById(R.id.btn_zhuZi);//获取诸子百家的模块按键
        zi.setOnClickListener(this);
        ji=view.findViewById(R.id.btn_wenJi);//获取文集百部的模块按键
        ji.setOnClickListener(this);

    }

    //监听方法
    @Override
    public void onClick(View view)
    {
        /*主页面中部处按钮，按tag匹配
        if (view.getTag().equals("画说民法典"))
        {
            this.startActivity(new Intent(MainActivity.activity, HsmfdActivity.class));
        }
        if (view.getTag().equals("普法电台"))
        {
            this.startActivity(new Intent(MainActivity.activity, PfdtActivity.class));
        }
        if (view.getTag().equals("律师介绍"))
        {
            this.startActivity(new Intent(MainActivity.activity, LsjsActivity.class));
        }
        if (view.getTag().equals("律师答疑"))
        {
            this.startActivity(new Intent(MainActivity.activity, LsdyActivity.class));
        }
        if (view.getTag().equals("法律援助"))
        {
            this.startActivity(new Intent(MainActivity.activity, FlyzActivity.class));
        }
        */
        if (view.getTag().equals("儒家经典"))
        {
            Intent intent = new Intent();
            intent.putExtra("fragment_flag", "0");
            intent.setClass(MainActivity.activity, ClassicsActivity.class);
            this.startActivity(intent);
        }else if(view.getTag().equals("史书全录"))
        {
            Intent intent = new Intent();
            intent.putExtra("fragment_flag", "1");
            intent.setClass(MainActivity.activity, ClassicsActivity.class);
            this.startActivity(intent);
        }else if(view.getTag().equals("诸子百家"))
        {
            Intent intent = new Intent();
            intent.putExtra("fragment_flag", "2");
            intent.setClass(MainActivity.activity, ClassicsActivity.class);
            this.startActivity(intent);
        }else if(view.getTag().equals("文集百部"))
        {
            Intent intent = new Intent();
            intent.putExtra("fragment_flag", "3");
            intent.setClass(MainActivity.activity, ClassicsActivity.class);
            this.startActivity(intent);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
    //配置改变时，不用重新启动Activity
    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public static Bitmap makeRoundCorner(Bitmap bitmap)
    {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int left = 0, top = 0, right = width, bottom = height;
        float roundPx = height/2;
        if (width > height) {
            left = (width - height)/2;
            top = 0;
            right = left + height;
            bottom = height;
        } else if (height > width) {
            left = 0;
            top = (height - width)/2;
            right = width;
            bottom = top + width;
            roundPx = width/2;
        }
        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        int color = 0xff424242;
        Paint paint = new Paint();
        Rect rect = new Rect(left, top, right, bottom);
        RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }
}

//点击webview与JavaScript的交互行为
class ShowBinner
{
    public ShowBinner()
    {
    }
    //转跳到文章1
    @JavascriptInterface
    @SuppressLint("JavascriptInterface")
    public void showWz1()
    {
        Intent intent=new Intent(MainActivity.activity, WenzhangDetailActivity.class);
        intent.putExtra("mes","10004");
        MainActivity.activity.startActivity(intent);
    }
    //转跳到文章2
    @JavascriptInterface
    @SuppressLint("JavascriptInterface")
    public void showWz2()
    {
        Intent intent=new Intent(MainActivity.activity, WenzhangDetailActivity.class);
        intent.putExtra("mes","10005");
        MainActivity.activity.startActivity(intent);
    }
    //转跳到视频
    @JavascriptInterface
    @SuppressLint("JavascriptInterface")
    public void showLs()
    {
        //new Intent(MainActivity.activity,LsjsActivity.class);
    }
    //转跳到电台，方法调用写在响应的jsp文件中
    @JavascriptInterface
    @SuppressLint("JavascriptInterface")
    public void showDt()
    {
        MainActivity.activity.startActivity(new Intent(MainActivity.activity, PfdtActivity.class));
    }
}
