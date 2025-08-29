package com.example.gxcg.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.gxcg.R;
import com.example.gxcg.fragment.CourseFragment;
import com.example.gxcg.fragment.JZVideoFragment;
import com.example.gxcg.fragment.MainFragment;
import com.example.gxcg.fragment.StudyFragment;
import com.example.gxcg.fragment.UserFragment;
import com.example.gxcg.util.BanSlidingViewPager;

import java.util.ArrayList;

import devlight.io.library.ntb.NavigationTabBar;

public class MainActivity extends FragmentActivity implements View.OnClickListener {
    //获取Login界面传来的Intent
    Intent strMess;
    //记录第一次退出
    public static long firstTime=0;
    //当前主界面的静态对象，操作页面时，直接操作他
    public static MainActivity activity;
    //退出事件记录
    private long exitTime = 0;
    //登录时的用户信息全部存在于此，操作时直接修改和提取userMess即可
    public static String[] userMess;
    private MainFragment mMain;//首页
    private CourseFragment courseFragment;//课程列表Fragment
    //private ShortVideoFragment shortVideoFragment;
    private JZVideoFragment jzVideoFragment;
    private UserFragment userFragment;
    private StudyFragment studyFragment;
    BanSlidingViewPager viewPager;//viewpager
    private FragmentManager mManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //申请权限
        activity=this;
        //获取转跳
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN ,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.setContentView(R.layout.activity_main);
        //全屏
        if(mMain == null)
        {
            mMain=new MainFragment();
        }
        if (courseFragment==null)
        {
            courseFragment = new CourseFragment();
        }
        if (jzVideoFragment == null)
        {
            jzVideoFragment = new JZVideoFragment();
        }
        if (studyFragment==null)
        {
            studyFragment = new StudyFragment();
        }
        if (userFragment==null)
        {
            userFragment = new UserFragment();
        }
        init();
    }

    public void init()
    {
        mManager = getSupportFragmentManager();
        viewPager=(BanSlidingViewPager) findViewById(R.id.main_fragment);
        viewPager.setAdapter(new ViewPagerAdapter(mManager));

        final String[] colors = getResources().getStringArray(R.array.default_preview);
        final NavigationTabBar navigationTabBar = (NavigationTabBar) findViewById(R.id.ntb_horizontal);
        navigationTabBar.setBgColor(this.getResources().getColor(R.color.lightbrown));
        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.discover),
                        Color.parseColor(colors[0]))
                        //    .selectedIcon(getResources().getDrawable(R.drawable.ic_sixth))
                        .title("发现")
                        .badgeTitle("新")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.course),
                        Color.parseColor(colors[1]))
//                        .selectedIcon(getResources().getDrawable(R.drawable.ic_eighth))//   数字角标？
                        .title("课程")
                        .badgeTitle("名师讲堂")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.video),
                        Color.parseColor(colors[2]))
                        //      .selectedIcon(getResources().getDrawable(R.drawable.ic_seventh))
                        .title("小视频")
                        .badgeTitle("猜你想看")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.study),
                        Color.parseColor(colors[3]))
//                        .selectedIcon(getResources().getDrawable(R.drawable.ic_eighth))
                        .title("社区")
                        .badgeTitle("热门话题")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.user),
                        Color.parseColor(colors[4]))
//                        .selectedIcon(getResources().getDrawable(R.drawable.ic_eighth))
                        .title("我的")
                        .badgeTitle("成就一览")
                        .build()
        );
        navigationTabBar.setModels(models);
        navigationTabBar.setViewPager(viewPager, 0);//进入APP后首先展示的导航页面
        navigationTabBar.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(final int position, final float positionOffset, final int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(final int position) {
                navigationTabBar.getModels().get(position).hideBadge();
                Log.d("TAG", "当前ViewPager页面索引:"+position);
            }

            @Override
            public void onPageScrollStateChanged(final int state) {

            }
        });

        navigationTabBar.postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < navigationTabBar.getModels().size(); i++) {
                    final NavigationTabBar.Model model = navigationTabBar.getModels().get(i);
                    navigationTabBar.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            model.showBadge();
                        }
                    }, i * 100);
                }
            }
        }, 500);
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter
    {
        private ArrayList<Fragment> mFragments = new ArrayList<Fragment>();
        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
            mFragments.add(mMain);
            mFragments.add(courseFragment);
            mFragments.add(jzVideoFragment);
            mFragments.add(studyFragment);
            mFragments.add(userFragment);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }
    }

    //我们需要重写回退按钮的时间,当用户点击回退按钮：
    //1.webView.canGoBack()判断网页是否能后退,可以则goback()
    //2.如果不可以连续点击两次退出App,否则弹出提示Toast
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event)
    {
        //在主界面，连击连两下退出程序，类似微博
        if(keyCode==KeyEvent.KEYCODE_BACK){
            long secondTime=System.currentTimeMillis();
            if(secondTime-firstTime>800){
                Toast.makeText(MainActivity.this,"再按一次返回键退出国学藏馆app",Toast.LENGTH_SHORT).show();
                firstTime=secondTime;
                return true;
            }else{
                MainActivity.activity.finish();
                System.exit(0);
            }
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public void onClick(View view) {
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
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


    private ArrayList<MyOnTouchListener> onTouchListeners = new ArrayList<MyOnTouchListener>(
            10);
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        for (MyOnTouchListener listener : onTouchListeners) {
            listener.onTouch(ev);
        }
        return super.dispatchTouchEvent(ev);
    }

    public void registerMyOnTouchListener(MyOnTouchListener myOnTouchListener) {
        onTouchListeners.add(myOnTouchListener);
    }

    public void unregisterMyOnTouchListener(MyOnTouchListener myOnTouchListener) {
        onTouchListeners.remove(myOnTouchListener);
    }
    public interface MyOnTouchListener {
        public boolean onTouch(MotionEvent ev);
    }

    @Override
    public void onNewIntent(Intent intent) {//有什么用？
        super.onNewIntent(intent);
        int id = intent.getIntExtra("id", 0);
        switch (id) {
            case 0:
                viewPager.setCurrentItem(0);
                break;
            case 1:
                viewPager.setCurrentItem(1);
                break;
            case 2:
                viewPager.setCurrentItem(2);
                break;
            case 3:
                viewPager.setCurrentItem(3);
                break;
            case 4:
                viewPager.setCurrentItem(4);
                break;
        }
    }
}
