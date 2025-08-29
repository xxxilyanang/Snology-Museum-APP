package com.example.gxcg.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.example.gxcg.R;
import com.example.gxcg.util.InforUtil;

import java.util.ArrayList;
import java.util.List;

public class ForgetPwdActivity extends Activity
{
    private Toolbar toolbar_in_forget_pwd;

    private EditText editText_userID;
    private EditText editText_user_NewPwd;
    private EditText editText_user_ReNewPwd;
    private EditText editText_CAPTCHA;

    private Boolean userIsExist=false;
    private List<String[]> ls;
    String userID = null;
    String newPwd = null;
    String reNewPwd = null;
    String CAPTCHA = null;

    private Button button_InForget_ChangePwd;

    private void initInfoView()
    {
        editText_userID = findViewById(R.id.editText_InForget_userID);
        editText_user_NewPwd = findViewById(R.id.editText_InForget_userNewPwd);
        editText_user_ReNewPwd = findViewById(R.id.editText_InForget_userAffirmPwd);
        editText_CAPTCHA = findViewById(R.id.editText_InForget_CAPTCHA);
        button_InForget_ChangePwd = findViewById(R.id.button_InForget_ChangePwd);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        setContentView(R.layout.activity_forget_pwd);
        initInfoView();

        button_InForget_ChangePwd.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        userID=editText_userID.getText().toString().trim();
                        newPwd=editText_user_NewPwd.getText().toString().trim();
                        reNewPwd=editText_user_ReNewPwd.getText().toString().trim();
                        CAPTCHA=editText_CAPTCHA.getText().toString().trim();
                        if (userID.equals("")||userID==null)
                        {
                            Toast.makeText(ForgetPwdActivity.this,"请输入要修改的账户ID",Toast.LENGTH_SHORT).show();
                        }else if(newPwd==null||newPwd.equals(""))
                        {
                            Toast.makeText(ForgetPwdActivity.this,"请输入新密码",Toast.LENGTH_SHORT).show();
                        }else if(reNewPwd==null||reNewPwd.equals(""))
                        {
                            Toast.makeText(ForgetPwdActivity.this,"请确认新密码",Toast.LENGTH_SHORT).show();
                        }else if(!reNewPwd.equals(newPwd))
                        {
                            Toast.makeText(ForgetPwdActivity.this,"两次密码输入不一样!",Toast.LENGTH_SHORT).show();
                        }else if(newPwd.length()<6)
                        {
                            Toast.makeText(ForgetPwdActivity.this,"密码格式输入有问题",Toast.LENGTH_SHORT).show();
                        }else if(reNewPwd.length()<6)
                        {
                            Toast.makeText(ForgetPwdActivity.this,"密码格式输入有问题",Toast.LENGTH_SHORT).show();
                        }else{//输入框都通过了检测
                            if(CAPTCHA.equals("7364"))
                            {
                                GetPasswordThread getPasswordThread=new GetPasswordThread();
                                getPasswordThread.start();
                                try
                                {
                                    getPasswordThread.join();
                                }catch (Exception e)
                                {
                                    e.printStackTrace();
                                }
                                if(userIsExist)
                                {
                                    UpdatePassThread updatePassThread=new UpdatePassThread();
                                    updatePassThread.start();
                                    try
                                    {
                                        updatePassThread.join();
                                    }
                                    catch(Exception e)
                                    {
                                        e.printStackTrace();
                                    }
//                                    String msg=ls.get(0)[0]+"<#>"+newPwd+"<#>";//ls.get(0)[0]是当前用户ID
//                                    InforUtil.updatePass(msg);
                                    Toast.makeText(ForgetPwdActivity.this,"密码修改成功",Toast.LENGTH_SHORT).show();
                                    ForgetPwdActivity.this.finish();
                                }
                                else {
                                    Toast.makeText(ForgetPwdActivity.this,"该用户不存在!!",Toast.LENGTH_SHORT).show();
                                }
                            }
                            else
                            {
                                Toast.makeText(ForgetPwdActivity.this,"验证码输入错误！",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
        );

        //========================================================toolbar返回按钮
        toolbar_in_forget_pwd = findViewById(R.id.toolbar_in_forget_pwd);
        toolbar_in_forget_pwd.getBackground().setAlpha(255/3);
        toolbar_in_forget_pwd.setNavigationOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();       //返回
                    }
                });
    }
    private class GetPasswordThread extends Thread
    {
        @Override
        public void run()
        {
            ls=new ArrayList<String[]>();
            ls= InforUtil.getUserPass(userID);
            if(!ls.isEmpty()&&ls.size()!=0)
            {
                userIsExist=true;
            }else {
                userIsExist=false;
            }
        }
    }
    private class UpdatePassThread extends Thread
    {
        @Override
        public void run()
        {
            String msg=ls.get(0)[0]+"<#>"+newPwd+"<#>";//ls.get(0)[0]是当前用户ID
            InforUtil.updatePass(msg);
        }
    }
}
