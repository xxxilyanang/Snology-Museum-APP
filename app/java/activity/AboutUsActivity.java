package com.example.gxcg.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.gxcg.R;

//2023/9/30
public class AboutUsActivity extends Activity {
    TextView version;
    RelativeLayout update;
    TextView back;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        version = this.findViewById(R.id.version);
        version.setText("version: "+getVersionCode(this));
        update = this.findViewById(R.id.updata);
        back = this.findViewById(R.id.abous_us_detail_back);
    }

    @Override
    protected void onStart() {
        super.onStart();

        update.setOnClickListener(
            e-> Toast.makeText(AboutUsActivity.this,"现在是最新版本",Toast.LENGTH_SHORT).show()
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



    /**
     * 获取自己应用内部的版本号
     */
    public static String getVersionCode(Context context) {
        PackageManager manager = context.getPackageManager();
        String code ="";
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            code = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return code;
    }
}
