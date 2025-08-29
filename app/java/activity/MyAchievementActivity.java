package com.example.gxcg.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.gxcg.R;

public class MyAchievementActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study);
        TextView t = this.findViewById(R.id.study_head);
        t.setText("我的成就");
        TextView back = this.findViewById(R.id.study_back);
        back.setOnClickListener(
                e->{

                    Intent intent = new Intent(this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//关掉所要到的界面中间的 activity
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("id",4);
                    startActivity(intent);
                    finish();
                }
        );
    }
}
