package com.example.gxcg.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.gxcg.R;
import com.example.gxcg.util.Constant;
import com.example.gxcg.util.InforUtil;
import com.example.gxcg.view.WheelView;

import java.util.ArrayList;

//2023/9/30
public class AccountActivity extends Activity {

    TextView back;

    RelativeLayout relativeLayout;

    TextView name;
    TextView password;
    TextView phone;
    TextView sex;
    TextView signature;
    TextView age;

    LinearLayout nameLayout;
    LinearLayout passwordLayout;
    LinearLayout phoneLayout;
    LinearLayout sexLayout;
    LinearLayout signatureLayout;
    LinearLayout ageLayout;
    Runnable runnable;

    DialogView mDialogView ;

    private String selectText = "";
    private ArrayList<String> ageList = new ArrayList<>();
    String[] sexArray = new String[]{"男","女"};

    Activity activity;

    public void initData() {
        ageList.clear();
        for (int i = 10; i <= 50; i++) {
            ageList.add(String.format("%d", i));
        }
    }

    public static final int SELECT_NAME = 2;
    public static final int SELECT_PASSWORD = 1;
    public static final int SELECT_PHONE = 6;
    public static final int SELECT_SEX = 4;
    public static final int SELECT_SIGNATURE = 5;
    public static final int SELECT_AGE = 3 ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        initView();
    }

    private void initView(){

        initData();

        back = this.findViewById(R.id.account_back);

        activity = this;

        relativeLayout = this.findViewById(R.id.account);
        //界面初始化
        name = this.findViewById(R.id.account_name);
        password = this.findViewById(R.id.account_password);
        phone = this.findViewById(R.id.account_phone);
        sex = this.findViewById(R.id.account_sex);
        signature = this.findViewById(R.id.account_signature);
        age = this.findViewById(R.id.account_age);
        //自定义dialog初始化
        mDialogView = new DialogView(this);

        //用子线程更新View
        runnable = ()->{
            name.setText(Constant.userInfo[2]);
            phone.setText(Constant.userInfo[6]);
            sex.setText(Constant.userInfo[4]);
            signature.setText(Constant.userInfo[5]);
            age.setText(Constant.userInfo[3]);
        };

        try {
            Thread updateView = new Thread(runnable);
            updateView.start();
            updateView.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        this.setFinishOnTouchOutside(false);
        //初始化可点击的layout
        nameLayout = this.findViewById(R.id.account_name_layout);
        passwordLayout = this.findViewById(R.id.account_password_layout);
        phoneLayout = this.findViewById(R.id.account_phone_layout);
        sexLayout = this.findViewById(R.id.account_sex_layout);
        signatureLayout = this.findViewById(R.id.account_signature_layout);
        ageLayout = this.findViewById(R.id.account_age_layout);
        //初始化事件
        initEvent();
    }

    private void initEvent(){
        nameLayout.setOnClickListener(
            e->{
                    mDialogView.setMessage(Constant.userInfo[SELECT_NAME],SELECT_NAME);
                    mDialogView.show();
            }
        );
        passwordLayout.setOnClickListener(
            e->{
                mDialogView.setMessage(Constant.userInfo[SELECT_PASSWORD],SELECT_NAME);
                mDialogView.show();
            }
        );
        phoneLayout.setOnClickListener(
            e->{
                mDialogView.setMessage(Constant.userInfo[SELECT_PHONE],SELECT_PHONE);
                mDialogView.show();
            }
        );
        signatureLayout.setOnClickListener(
            e->{
                mDialogView.setMessage(Constant.userInfo[SELECT_SIGNATURE],SELECT_SIGNATURE);
                mDialogView.show();
            }
        );
        ageLayout.setOnClickListener(
            e -> {
                showMyDialog(age,ageList,8);
                Constant.userInfo[SELECT_AGE] = age.getText().toString();
            }
        );
        sexLayout.setOnClickListener(
            e->{
                AlertDialog.Builder builder = new AlertDialog.Builder(this);// 自定义对话框
                // checkedItem默认的选中 setSingleChoiceItems设置单选按钮组
                builder.setSingleChoiceItems(sexArray, 1, (dialog, which) -> {// which是被选中的位置
                    sex.setText(sexArray[which]);
                    Constant.userInfo[SELECT_SEX] = sex.getText().toString();
                    dialog.dismiss();// 随便点击一个item消失对话框，不用点击确认取消
                });
                builder.show();// 让弹出框显示
            }
        );

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


    private class DialogView extends Dialog {

        private EditText rawEdit;
        private Button submitButton;
        //查询选择值
        int select;

        public DialogView(Context context ) {
            super(context, R.style.Dialog_style);
            setContentView(R.layout.account_dialog_view);
            rawEdit = findViewById(R.id.raw_edit);
            submitButton = findViewById(R.id.update_submit_button);
            this.setCanceledOnTouchOutside(false);
            setCancelable(false);

        }

        @Override
        public Window getWindow() {
            return super.getWindow();
        }

        /** 设置弹窗信息 */
        public void setMessage( String rawEdit,int select) {

            this.rawEdit.setText(rawEdit);
            this.select = select;

            submitButton.setOnClickListener(
                e->{
                    boolean isPhone = true;
                    String update = this.rawEdit.getText().toString();
                    if(select == SELECT_PHONE){
                        isPhone =  isPhone(update);
                    }
                    if(!isPhone){
                        Toast.makeText(activity,"电话格式有误请重新输入",Toast.LENGTH_SHORT).show();
                    }else{
                        Constant.userInfo[select] = update;
                        name.setText(Constant.userInfo[2]);

                        sex.setText(Constant.userInfo[4]);
                        signature.setText(Constant.userInfo[5]);
                        age.setText(Constant.userInfo[3]);
                        //写方法更新 //更新数据
                        try {
                            Runnable r = ()-> InforUtil.updateUserInfo(Constant.userInfo);
                            Thread thread = new Thread(r);
                            thread.start();
                            thread.join();
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                        cancel();
                    }
                }
            );
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

        public  boolean isPhone(String str) {
            String regex = "^(1)\\d{10}$";//正则表达式
            return str.matches(regex);
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
    //
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
}
