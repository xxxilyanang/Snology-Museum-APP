package com.example.gxcg.activity;

import static com.example.gxcg.util.Constant.userInfo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gxcg.R;
import com.example.gxcg.util.Constant;
import com.example.gxcg.util.InforUtil;

public class LoginActivity extends Activity implements View.OnClickListener {
    //永久的储存数据,轻量储存
    static public SharedPreferences sp = null;
    //记录第一次点击退出时间
    public static long firstTime = 0;
    //动画资源
    Animation animation_to_up;              //四个大字同时向上的动画
    Animation animation_editor_up;
    Animation animation_word_faded;
    Animation animation_moveup_xue;
    Animation animation_logo05_fadeout;
    ImageView imageView_guo;
    ImageView imageView_xue;
    ImageView imageView_cang;
    ImageView imageView_guan;
    ImageView imageView_logo05;

    ImageView login_imageView;
    RelativeLayout relativeLayout;
    Handler hd;
    //用户交互组件
    EditText edit_uid;
    EditText edit_pwd;
    Button login;
    Button button_forget_pw;
    TextView textView_sign;

    boolean isLogin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //取消页面标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        setContentView(R.layout.activity_login);
        imageView_logo05 = this.findViewById(R.id.imageView_logo05);
        imageView_logo05.setVisibility(View.INVISIBLE);
//        设置初始上卡片透明度为0，方便下滑时渐变
//        login_imageView = this.findViewById(R.id.imageView_up);
//        login_imageView.getBackground().setAlpha(0);

        //设置输入框初始不可见，方便渐变
        relativeLayout = this.findViewById(R.id.login_layout);
        relativeLayout.setVisibility(View.INVISIBLE);

        //初始化动画资源
        initAnimation();
        //主线程处理设置输入框可见
        hd = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 0:
                        relativeLayout.setVisibility(View.VISIBLE);
                        break;
                }
            }
        };
        //执行动画效果
        startMainAnimation();
        //初始化逻辑
        init();
    }

    public void startMainAnimation() {
        //下一步动画等待
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    Thread.sleep(2000);
                    animation_logo05_fadeout = AnimationUtils.loadAnimation(LoginActivity.this,
                            R.anim.animation_fadeout);
                    animation_word_faded = AnimationUtils.loadAnimation(LoginActivity.this,
                            R.anim.faded_anim_word);
//                    animation_upback = AnimationUtils.loadAnimation(LoginActivity.this,
//                            R.anim.show_anim_upback);//上卡片下滑的动画
                    animation_editor_up = AnimationUtils.loadAnimation(LoginActivity.this,
                            R.anim.show_anim_edittext);
                    animation_moveup_xue = AnimationUtils.loadAnimation(LoginActivity.this,
                            R.anim.moveup_anim_xue);
//                    imageView_guo.startAnimation(animation_guo);
                    imageView_xue.startAnimation(animation_moveup_xue);
                    imageView_cang.startAnimation(animation_word_faded);
                    imageView_guan.startAnimation(animation_word_faded);
                    imageView_logo05.startAnimation(animation_logo05_fadeout);
                    //设置下滑卡片不透明
//                    login_imageView.getBackground().setAlpha(255);
//                    login_imageView.startAnimation(animation_upback);

                    //初始登录框设置为不透明，方便接下来的渐变效果
                    hd.sendEmptyMessage(0);
                    relativeLayout.startAnimation(animation_editor_up);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public void initAnimation() {
        //初始四个字渐变上滑
        animation_to_up = AnimationUtils.loadAnimation(this,
                R.anim.show_anim_up);
        imageView_guo = this.findViewById(R.id.imageView_guo);
        imageView_xue = this.findViewById(R.id.imageView_xue);
        imageView_cang = this.findViewById(R.id.imageView_cang);
        imageView_guan = this.findViewById(R.id.imageView_guan);
        imageView_guo.startAnimation(animation_to_up);
        imageView_xue.startAnimation(animation_to_up);
        imageView_cang.startAnimation(animation_to_up);
        imageView_guan.startAnimation(animation_to_up);
    }

    public void init() {
        //获得两个输入框的引用
        edit_uid = this.findViewById(R.id.editText_Number);
        edit_pwd = this.findViewById(R.id.editText_TextPassword);
        //获取一个文件名为userInfo、权限为private的xml文件的SharedPreferences对象
        sp = LoginActivity.this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        //登录按钮
        login = this.findViewById(R.id.login);

        textView_sign = findViewById(R.id.sign);
        textView_sign.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);   //添加下划线
        textView_sign.getPaint().setAntiAlias(true);                    //抗锯齿

        button_forget_pw = findViewById(R.id.button_forget_pw);
        button_forget_pw.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);   //添加下划线
        button_forget_pw.getPaint().setAntiAlias(true);                    //抗锯齿

        //--------------------跳转到注册界面
        textView_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.this.startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                overridePendingTransition(R.anim.register_enter, R.anim.login_exit);
            }
        });
        //--------------------跳转到忘记密码界面
        button_forget_pw.setOnClickListener(this);

        //-------------------登录逻辑
        login.setOnClickListener(this);

        edit_uid = this.findViewById(R.id.editText_Number);//用户名输入框
        edit_uid.setText(sp.getString("UserId", ""));//返回上一次输入用户名的消息，若没有，就空
        edit_pwd = this.findViewById(R.id.editText_TextPassword);//密码输入框
    }

    @Override
    public void onClick(View view) {
        if (view.getTag().equals("登录")) {
            String uid = edit_uid.getText().toString().trim();
            String pwd = edit_pwd.getText().toString().trim();

            if (uid == null || uid.equals("")) {
                Toast.makeText(this, "账号不能为空", Toast.LENGTH_SHORT).show();
                return;
            } else if (pwd == null || pwd.equals("")) {
                Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
                return;
            }
            //如果成功获取信息
            String message = uid + "<#>" + pwd;

            Thread loginThread = new Thread() {
                @Override
                public void run() {
                    try {
                        //如果返回的密码和用户输入的一样就返回true

                        isLogin = InforUtil.selectUser(message).equals(pwd);
                        Log.d("zzzzzzzzzzzz", "run: "+isLogin);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            loginThread.start();
            try {
                loginThread.join();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            //得到了isLogin的结果后，开始判断
            if (isLogin) {
                Constant.UserId = uid;
                Thread getUserInfoTherad = new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        userInfo =  InforUtil.getLoginUserInfo(Constant.UserId);
                        byte[] data = InforUtil.getPicbyEntirelyPath(Constant.userInfo[7]);
                        Constant.bitmap = BitmapFactory.decodeByteArray(data,0,data.length);
                    }
                };
                getUserInfoTherad.start();
                try {
                    getUserInfoTherad.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();

                //跳转到主界面
                Intent intent = new Intent(this, MainActivity.class);
                sp.edit().putString("UserId", uid).commit();
                //传送用户数据
                //intent.putExtra("userMess",str);
                startActivity(intent);
                //Toast.makeText(this,"欢迎"+str[2],Toast.LENGTH_SHORT).show();
                //this.finish();
            } else {
                Toast.makeText(LoginActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
                return;
            }
        }//结束登录if语句
        if (view.getTag().equals("忘记密码")) {
            Intent intent = new Intent(this, ForgetPwdActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.forget_pwd_enter, R.anim.login_exit);
        }
    }
    /*
        重写回退按钮的时间,当用户点击回退按钮：
        1.webView.canGoBack()判断网页是否能后退,可以则goback()
        2.如果不可以连续点击两次退出App,否则弹出提示Toast
    */
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        //在主界面，连击连两下退出程序，类似微博
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            long secondTime = System.currentTimeMillis();
            if (secondTime - firstTime > 800) {
                Toast.makeText(this, "再按一次退出国学藏馆APP", Toast.LENGTH_SHORT).show();
                firstTime = secondTime;
                return true;
            } else {
                //MainActivity.activity.finish();
                System.exit(0);
            }
        }
        return super.onKeyUp(keyCode, event);
    }
    @Override
    protected void onPause() {
        super.onPause();
    }
    @Override
    protected void onRestart() {
        super.onRestart();
    }
    @Override
    protected void onStop() {
        super.onStop();
    }
}