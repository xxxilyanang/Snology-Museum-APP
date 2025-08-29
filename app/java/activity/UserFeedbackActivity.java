package com.example.gxcg.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.gxcg.R;
import com.example.gxcg.util.InforUtil;

//09/27
public class UserFeedbackActivity extends Activity {
    Button feedbackSubmit;
    EditText feedback;
    TextView back;
    TextView result;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userfeed);
        feedbackSubmit = this.findViewById(R.id.feedback_submit);
        feedback = this.findViewById(R.id.feedback_content);
        back = this.findViewById(R.id.feedback_back);
        result = this.findViewById(R.id.feedback_result);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //返回
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
        //反馈结果
        result.setOnClickListener(
                e->{
                        Intent intent = new Intent(this , FeedbackResultActivity.class);
                        startActivity(intent);
                }
        );
        //提交反馈
        feedbackSubmit.setOnClickListener(
            event ->{
                String feedbackText = feedback.getText().toString();
                if(feedbackText.equals(""))
                {
                    Toast.makeText(UserFeedbackActivity.this,
                            "请输入您的反馈",
                            Toast.LENGTH_SHORT).show();
                }else{
                    try {
                        Runnable r = ()-> InforUtil.insertUserFeedback(feedbackText);
                        Thread thread = new Thread(r);
                        thread.start();
                        thread.join();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    feedback.setText(" ");
                    Toast.makeText(UserFeedbackActivity.this,
                            "反馈成功,感谢您的反馈",
                            Toast.LENGTH_SHORT).show();
                }
            }
        );
    }
}
