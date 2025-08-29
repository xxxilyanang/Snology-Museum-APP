package com.example.gxcg.activity;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.gxcg.R;

import java.util.List;

//10/2
public class OptionActivity extends Activity {
    LinearLayout switchLayout;
    LinearLayout exitLayout;
    TextView back;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);
        back = this.findViewById(R.id.option_back);
        switchLayout = this.findViewById(R.id.switch_layout);
        switchLayout.setOnClickListener(
                e->{
                    Intent intent = new Intent(OptionActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//关掉所要到的界面中间的 activity
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("id",1);
                    startActivity(intent);
                    finish();
                }
        );
        exitLayout = this.findViewById(R.id.exit_layout);
        exitLayout.setOnClickListener(
                e->{
                    DialogView dialogView = new DialogView(this);
                    dialogView.show();
                }
        );
        back.setOnClickListener(
                e->{
                    Intent intent = new Intent(OptionActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//关掉所要到的界面中间的 activity
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("id",4);
                    startActivity(intent);
                    finish();
                }
        );
    }
    private class DialogView extends Dialog {

        private Button exitApp;
        private Button exitLogin;
        private Button cancel;

        //查询选择值

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public DialogView(Context context ) {
            super(context, R.style.Dialog_style);
            setContentView(R.layout.option_exit_dialog_view);
            exitApp = this.findViewById(R.id.exit_app);
            exitLogin = this.findViewById(R.id.exit_login);
            cancel = this.findViewById(R.id.cancel);
            this.setCanceledOnTouchOutside(false);
            setCancelable(false);
            initEvent();
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        private void initEvent() {
            exitApp.setOnClickListener(
                    e->{
                        ActivityManager activityManager = (ActivityManager) OptionActivity.this.getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
                        List<ActivityManager.AppTask> appTaskList = activityManager.getAppTasks();
                        for (ActivityManager.AppTask appTask : appTaskList) {
                            appTask.finishAndRemoveTask();
                        }
                        System.exit(0);
                    }
            );
            exitLogin.setOnClickListener(
                    e->{
                        Intent intent = new Intent(OptionActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//关掉所要到的界面中间的 activity
                        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        intent.putExtra("id",1);
                        startActivity(intent);
                    }
            );
            cancel.setOnClickListener(
                    e->{
                        cancel();
                    }
            );
        }

        @Override
        public Window getWindow() {
            return super.getWindow();
        }

        @Override
        public void show() {
            this.setCanceledOnTouchOutside(true);
            Window window = this.getWindow();
            WindowManager.LayoutParams lp = window.getAttributes();
            window.setGravity(Gravity.BOTTOM);
            window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
            super.show();
        }

        @Override
        public void cancel() {
            super.cancel();
        }

    }


}
