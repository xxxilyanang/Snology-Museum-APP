package com.example.gxcg.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.gxcg.R;
import com.example.gxcg.adapter.ClassicsFragmentPagerAdapter;
import com.example.gxcg.fragment.ClassicsJiFragment;
import com.example.gxcg.fragment.ClassicsJingFragment;
import com.example.gxcg.fragment.ClassicsShiFragment;
import com.example.gxcg.fragment.ClassicsZiFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class ClassicsActivity extends AppCompatActivity {
    //页面所需组件声明
    private List<String> mTitles;//用于送入适配器的标题列表
    private String [] title={"经部","史部","子部", "集部"};//初始化标题
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar toolbar;
    private List<Fragment> fragmentList;//用于送入适配器的列表
    private ClassicsJingFragment classicsJingFragment;
    private ClassicsShiFragment classicsShiFragment;
    private ClassicsZiFragment classicsZiFragment;
    private ClassicsJiFragment classicsJiFragment;
    private ClassicsFragmentPagerAdapter classicsFragmentPagerAdapter;

    //用于跳转指定fragment的标志位
    private String fragment_flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classics);
        tabLayout = findViewById(R.id.classics_tab_layout);
        viewPager = findViewById(R.id.viewpager_classics);
        initToolbar();
        initPager();
        fragment_flag = getIntent().getStringExtra("fragment_flag");
        viewPager.setCurrentItem(Integer.parseInt(fragment_flag));//跳转指定的fragment


    }
    //初始标题栏
    private void initToolbar() {

        toolbar = findViewById(R.id.tb_classics_back);
        toolbar.setNavigationOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();       //返回
                    }
                });
    }
    //初始化切换界面Pager
    private void initPager() {
        //创建并添加Fragment
        classicsJingFragment = new ClassicsJingFragment();
        classicsShiFragment = new ClassicsShiFragment();
        classicsZiFragment = new ClassicsZiFragment();
        classicsJiFragment = new ClassicsJiFragment();

        fragmentList=new ArrayList<>();
        fragmentList.add(classicsJingFragment);
        fragmentList.add(classicsShiFragment);
        fragmentList.add(classicsZiFragment);
        fragmentList.add(classicsJiFragment);

        mTitles = new ArrayList<>();
        for (int i = 0; i < title.length; i++) {
            mTitles.add(title[i]);
        }
        classicsFragmentPagerAdapter = new ClassicsFragmentPagerAdapter
                //getActivity().getSupportFragmentManager()
                (this.getSupportFragmentManager(),
                        fragmentList,
                        mTitles);
        viewPager.setAdapter(classicsFragmentPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);//与ViewPage建立关系
    }
}