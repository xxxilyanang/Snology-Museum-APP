package com.example.gxcg.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.gxcg.R;
import com.example.gxcg.util.Constant;
import com.example.gxcg.util.InforUtil;

import java.util.ArrayList;
import java.util.List;

//09/28
public class FeedbackResultActivity extends Activity {

    List<String[]> listData = new ArrayList<>();
    ListView listView;
    BaseAdapter baseAdapter;

    TextView myFeedback;
    TextView allFeedback;

    boolean myOrAll = true;

    TextView back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_result);
        initView();
    }
    //初始话View方法
    private void initView() {
        //初始话View
        listView = this.findViewById(R.id.feedback_list);
        myFeedback = this.findViewById(R.id.my_feedback);
        allFeedback = this.findViewById(R.id.all_feedback);
        back = this.findViewById(R.id.feedback_result_back);
        //填充ListView
        initListView();
        //初始化view事件
        initViewEvent();
    }

    private void initViewEvent() {

        myFeedback.setOnClickListener(
                e->{
                    if(!myOrAll) {
                        myOrAll = !myOrAll;
                        initListView();
                    }
                }
        );
        //改变搜索方式
        allFeedback.setOnClickListener(
                e->{
                    if(myOrAll){
                        myOrAll = !myOrAll;
                        initListView();
                    }
                }
        );
        //界面回退
        back.setOnClickListener(
                e->{
                    Intent intent = new Intent(this, UserFeedbackActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//关掉所要到的界面中间的 activity
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("id",4);
                    startActivity(intent);
                }
        );
    }


    private void initListView(){

        //获取数据
        Runnable r = ()->{
            if(myOrAll){
                listData = InforUtil.getUserFeedbackResult(Constant.UserId);
            }else {
                listData = InforUtil.getAllUserFeedbackInfo();
            }
        };
        Thread thread = new Thread(r);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //给listview填值
        LayoutInflater inflater=LayoutInflater.from(this);
        baseAdapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return listData.size();
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                LinearLayout linearLayout=(LinearLayout) convertView;
                if (linearLayout == null)
                {
                    linearLayout = (LinearLayout) (inflater.inflate(R.layout.feedback_listitem, null).findViewById(R.id.relative_feedback));
                }
                //这个0代表这个RelariveLayout是这个linearLayout的第1个子控件
                RelativeLayout relativeLayout=(RelativeLayout)linearLayout.getChildAt(0);
                //这个0代表这个TextView是这个RelariveLayout的第1个子控件
                TextView question=(TextView)relativeLayout.getChildAt(0);

                if(!(listData==null)) {
                    //由于数据库传过来的值顺序不同所以通过bool值来判断用哪个索引
                    if(myOrAll){
                        question.setText(listData.get(position)[2]);
                    }else {
                        question.setText(listData.get(position)[3]);
                    }

                }else {
                    Toast.makeText(FeedbackResultActivity.this,"没有查询到信息",Toast.LENGTH_SHORT).show();
                }
                return linearLayout;
            }
        };
        listView.setAdapter(baseAdapter);
        //列表item点击事件
        listView.setOnItemClickListener(
            (parent, view, position, id)->{
                Intent intent = new Intent(this, FeedBackDetailActivity.class);
                if(myOrAll){
                    intent.putExtra("feedback", listData.get(position)[2]);
                    intent.putExtra("result",listData.get(position)[3]);
                }else {
                    intent.putExtra("feedback", listData.get(position)[3]);
                    intent.putExtra("result",listData.get(position)[4]);
                }
                startActivity(intent);
            }
        );
    }
}
