package com.example.gxcg.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.example.gxcg.R;
import com.example.gxcg.util.InforUtil;
import com.example.gxcg.view.WheelView;

import java.util.ArrayList;

public class RegisterActivity extends Activity {

    String id;
    String pwd;
    String name;
    String nickname;
    String telephone;
    String sex;
    String age;

    private Toolbar toolbar;

    private TextView select_sex;
    private TextView select_age;

    private Button button_register;
    private EditText editText_ID;
    private EditText editText_pwd;
    private EditText editText_name;
    private EditText editText_nickname;
    private EditText editText_telephone;

    String[] sexArray = new String[]{"男","女"};
    private ArrayList<String> ageList = new ArrayList<>();

    private String selectText = "";

    boolean userIsExist = false;        //添加用户ID之前未有
    //初始化年龄列表数据
    public void initData() {
        ageList.clear();
        for (int i = 10; i <= 50; i++) {
            ageList.add(String.format("%d岁", i));
        }
    }
    //调用年龄选择框
    private void showMyDialog(TextView textView, ArrayList<String> list, int selected){
        showChoiceDialog(list, textView, selected,
                new WheelView.OnWheelViewListener() {
                    @Override
                    public void onSelected(int selectedIndex, String item) {
                        selectText = item;
                    }
                });
    }
    //显示选择框
    private void showChoiceDialog(ArrayList<String> dataList,final TextView textView,int selected,
                                  WheelView.OnWheelViewListener listener){
        selectText = "";
        View outerView = LayoutInflater.from(this).inflate(R.layout.register_dialog_wheelview,null);
        final WheelView wheelView = outerView.findViewById(R.id.wheel_view);
        wheelView.setOffset(2);// 对话框中当前项上面和下面的项数
        wheelView.setItems(dataList);// 设置数据源
        wheelView.setSeletion(selected);// 默认选中第三项
        wheelView.setOnWheelViewListener(listener);

        // 显示对话框，点击确认后将所选项的值显示到Button上
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setView(outerView)
                .setPositiveButton("确认",
                        (dialogInterface, i) -> {
                            textView.setText(selectText);
                            textView.setTextColor(this.getResources().getColor(R.color.black));
                        })
                .setNegativeButton("取消",null).create();
        alertDialog.show();
        int green = this.getResources().getColor(R.color.yellow);
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(green);
        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(green);
    }

    private void initInfoView()
    {
        button_register = findViewById(R.id.button_register);
        editText_ID = findViewById(R.id.editText_ID);
        editText_pwd = findViewById(R.id.editText_pwd);
        editText_name = findViewById(R.id.editText_name);
        editText_nickname = findViewById(R.id.editText_nickname);
        editText_telephone = findViewById(R.id.editText_telephone);
        select_sex = findViewById(R.id.textView_select_sex);
        select_age = findViewById(R.id.textView_select_age);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //=======================================================取消页面标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        setContentView(R.layout.activity_register);     //这句话必须写在所有的findViewByID前面！！！！！！
        initData();
        initInfoView();
        //=================================注册按钮
        button_register.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        id = editText_ID.getText().toString().trim();
                        pwd = editText_pwd.getText().toString().trim();
                        name = editText_name.getText().toString().trim();
                        nickname = editText_nickname.getText().toString().trim();
                        telephone = editText_telephone.getText().toString().trim();
                        sex = select_sex.getText().toString().trim();
                        age = select_age.getText().toString().trim();
                        if(id.equals(""))
                        {
                            Toast.makeText(RegisterActivity.this,"请输入用户id",Toast.LENGTH_SHORT).show();
                        }else if(pwd.equals(""))
                        {
                            Toast.makeText(RegisterActivity.this,"请输入用户密码",Toast.LENGTH_SHORT).show();
                        }else if(name.equals(""))
                        {
                            Toast.makeText(RegisterActivity.this,"请输入用户姓名",Toast.LENGTH_SHORT).show();
                        }else if(nickname.equals(""))
                        {
                            Toast.makeText(RegisterActivity.this,"请输入用户昵称",Toast.LENGTH_SHORT).show();
                        }else if(telephone.equals(""))
                        {
                            Toast.makeText(RegisterActivity.this,"请输入用户电话号码",Toast.LENGTH_SHORT).show();
                        }else if(sex.equals("选择性别"))
                        {
                            Toast.makeText(RegisterActivity.this,"请选择用户性别",Toast.LENGTH_SHORT).show();
                        }else if(age.equals("选择年龄"))
                        {
                            Toast.makeText(RegisterActivity.this,"请选择用户年龄",Toast.LENGTH_SHORT).show();
                        }else if(pwd.length()<6)
                        {
                            Toast.makeText(RegisterActivity.this,"密码格式有误",Toast.LENGTH_SHORT).show();
                        }else if(telephone.length()<11)
                        {
                            Toast.makeText(RegisterActivity.this, "电话格式有误", Toast.LENGTH_SHORT).show();
                        } else
                        {//下面是通过检测逻辑的部分
                            IsExistThread isExistThread=new IsExistThread();//判断是否存在用户的分支
                            isExistThread.start();
                            try
                            {
                                isExistThread.join();
                            }catch(Exception e)
                            {
                                e.printStackTrace();
                            }
                            if(!userIsExist)
                            {
                                Toast.makeText(RegisterActivity.this,"此用户id已经存在",Toast.LENGTH_SHORT).show();
                            }else{
                                InsertUserThread insertUserThread=new InsertUserThread();//判断是否存在用户的分支
                                insertUserThread.start();
                                try
                                {
                                    isExistThread.join();
                                }catch(Exception e)
                                {
                                    e.printStackTrace();
                                }
                                Toast.makeText(RegisterActivity.this,"注册成功!请登录",Toast.LENGTH_SHORT).show();
                                RegisterActivity.this.finish();
                            }
                        }
                    }
                }
        );
        //=======================================================年龄选择逻辑
        select_age.setOnClickListener(
                view -> showMyDialog(select_age,ageList,8)
        );
        //=======================================================性别选择逻辑
        select_sex.setOnClickListener(v -> {//性别点击后弹出性别选择框
            AlertDialog.Builder builder = new AlertDialog.Builder(this);// 自定义对话框
            // checkedItem默认的选中 setSingleChoiceItems设置单选按钮组
            builder.setSingleChoiceItems(sexArray, 1, (dialog, which) -> {// which是被选中的位置
                // showToast(which+"");
                select_sex.setText(sexArray[which]);
                dialog.dismiss();// 随便点击一个item消失对话框，不用点击确认取消
            });
            builder.show();// 让弹出框显示
        });
        //========================================================toolbar返回按钮
        toolbar = findViewById(R.id.toolbar);
        toolbar.getBackground().setAlpha(255/3);
        toolbar.setNavigationOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();       //返回
                    }
                });
    }//onCreated方法结束

    //检索用户ID是否存在的方法
    private class IsExistThread extends Thread
    {
        @Override
        public void run()
        {
            userIsExist = InforUtil.isExistUser(id);
        }
    }
    //数据库添加用户
    private class InsertUserThread extends Thread
    {
        @Override
        public void run()
        {
            String msg=id+"<#>"+pwd+"<#>"+name+"<#>"+age+"<#>"+telephone+"<#>"+sex+"<#>"+nickname+"<#>"
                    +RegisterActivity.this.getString(R.string.user_default_pic);
            InforUtil.insertUserAndroid(msg);
        }
    }
}
