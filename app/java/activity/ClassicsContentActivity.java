package com.example.gxcg.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.gxcg.R;
import com.example.gxcg.adapter.ClassicsContentFragmentPagerAdapter;
import com.example.gxcg.fragment.ClassicsContentFragment;
import com.example.gxcg.fragment.ClassicsTranslateFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class ClassicsContentActivity extends AppCompatActivity {

    private Toolbar toolbar;
    //上一个界面传过来的数据
    private String classics_name;
    private String classics_content_url;
    private String classics_translate_url;
    //组件
    private List<String> mTitles;//用于送入适配器的标题列表
    private String [] title={"原文","译文"};//初始化标题
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private List<Fragment> fragmentList;//用于送入适配器的列表
    //下面的两个Fragment
    private ClassicsContentFragment classicsContentFragment;
    private ClassicsTranslateFragment classicsTranslateFragment;
    private ClassicsContentFragmentPagerAdapter classicsContentFragmentPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classics_content);
        tabLayout = findViewById(R.id.classicsContent_tab_layout);
        viewPager = findViewById(R.id.viewpager_classicsContent);
        initToolbar();

        classics_name = getIntent().getStringExtra("classics_name");
        classics_content_url = getIntent().getStringExtra("classics_content_url");
        classics_translate_url = getIntent().getStringExtra("classics_translate_url");
        initPager();
    }
    //初始化切换界面Pager
    private void initPager() {
        //创建并添加Fragment
        classicsContentFragment = new ClassicsContentFragment(classics_name, classics_content_url);
        classicsTranslateFragment = new ClassicsTranslateFragment(classics_name, classics_translate_url);

        fragmentList=new ArrayList<>();
        fragmentList.add(classicsContentFragment);
        fragmentList.add(classicsTranslateFragment);

        mTitles = new ArrayList<>();
        for (int i = 0; i < title.length; i++) {
            mTitles.add(title[i]);
        }
        classicsContentFragmentPagerAdapter = new ClassicsContentFragmentPagerAdapter
                //getActivity().getSupportFragmentManager()
                (this.getSupportFragmentManager(),
                        fragmentList,
                        mTitles);
        viewPager.setAdapter(classicsContentFragmentPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);//与ViewPage建立关系
    }
    //初始标题栏
    private void initToolbar() {
        toolbar = findViewById(R.id.tb_classicsContent_back);
        toolbar.setNavigationOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();       //返回
                    }
                });
    }
}