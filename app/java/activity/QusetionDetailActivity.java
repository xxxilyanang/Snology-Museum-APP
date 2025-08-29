package com.example.gxcg.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.gxcg.R;

public class QusetionDetailActivity extends Activity {
    TextView t1 ;
    TextView t2 ;
    TextView t0;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_detail);
        t0 = this.findViewById(R.id.question_detail_back);
        t1 = this.findViewById(R.id.question_detail);
        t2 = this.findViewById(R.id.question_detail_answer);
        Intent intent = getIntent();
        String[] strings = intent.getStringArrayExtra("data");
        t1.setText(strings[1]);
        t2.setText(strings[2]);
        t0.setOnClickListener(
                e->{
                    Intent intent2 = new Intent(this, QuestionActivity.class);
                    intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//关掉所要到的界面中间的 activity
                    intent2.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent2);
                    finish();
                }
        );
    }
    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String[] strings = intent.getStringArrayExtra("data");
        t1.setText(strings[1]);
        t2.setText(strings[2]);
    }
}
