package com.example.gxcg.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.gxcg.R;

public class FeedBackDetailActivity extends Activity {
    TextView t1 ;
    TextView t2 ;
    TextView t0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_detail);
        t0 = this.findViewById(R.id.feedback_detail_back);
        t1 = this.findViewById(R.id.feedback_question);
        t2 = this.findViewById(R.id.feedback_result_text);
        Intent intent = getIntent();
        String feedback = intent.getStringExtra("feedback");
        String result = intent.getStringExtra("result");
        t1.setText("反馈：\n\n"+feedback);
        t2.setText("反馈结果：\n\n"+result);
        t0.setOnClickListener(
                e->{
                    Intent goIntent = new Intent(this, FeedbackResultActivity.class);
                    goIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//关掉所要到的界面中间的 activity
                    goIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(goIntent);
                }
        );
    }
    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String feedback = intent.getStringExtra("feedback");
        String result = intent.getStringExtra("result");
        t1.setText(feedback);
        t2.setText(result);
    }

}
