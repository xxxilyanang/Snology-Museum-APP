package com.example.gxcg.fragment;

import static com.example.gxcg.util.Constant.bitmap;
import static com.example.gxcg.util.Constant.userInfo;

import android.Manifest;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.gxcg.R;
import com.example.gxcg.activity.AboutUsActivity;
import com.example.gxcg.activity.AccountActivity;
import com.example.gxcg.activity.CollectionActivity;
import com.example.gxcg.activity.CommentActivity;
import com.example.gxcg.activity.MyAchievementActivity;
import com.example.gxcg.activity.MyArticleActivity;
import com.example.gxcg.activity.MyLaboratoryActivity;
import com.example.gxcg.activity.OptionActivity;
import com.example.gxcg.activity.QuestionActivity;
import com.example.gxcg.activity.StudyActivity;
import com.example.gxcg.activity.UserFeedbackActivity;
import com.example.gxcg.util.Constant;
import com.example.gxcg.util.InforUtil;
import com.example.gxcg.util.PermissionUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;

//2023/9/27俞炫改
//用户主界面
public class UserFragment extends Fragment {
    View view;//加载的界面布局

    LinearLayout collection;
    LinearLayout history;
    LinearLayout article;
    LinearLayout comments;
    LinearLayout account;
    LinearLayout option;
    LinearLayout about_us;
    LinearLayout feedback;
    LinearLayout question;
    LinearLayout laboratory;
    LinearLayout achievement;

    ImageView head;
    TextView userName;
    TextView userSignature;
    Bitmap headPic;
    public static final String IMAGE_FILE_NAME = "head.png";
    private File file = new File(Environment.getExternalStorageDirectory(), IMAGE_FILE_NAME);
    public static final String IMAGE_FILE_NAME_TEMP = "dt.png";
    private File cropFile = new File(Environment.getExternalStorageDirectory(), IMAGE_FILE_NAME_TEMP);
    private Uri imageCropUri = Uri.fromFile(cropFile);
    //拍照的照片

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //预加载界面布局view
        view = inflater.inflate(R.layout.fragment_user,null);

        return view;//返回页面布局

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public void onResume() {
        userName.setText(userInfo[2]);
        userSignature.setText("签名: "+userInfo[5]);
        head.setImageBitmap(createCircleImage(bitmap));
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        initView();
        Thread getUserInfoTherad = new Thread(){
            @Override
            public void run() {
                super.run();
                userInfo =  InforUtil.getLoginUserInfo(Constant.UserId);
                byte[] data = InforUtil.getPicbyEntirelyPath(Constant.userInfo[7]);
                Constant.bitmap = BitmapFactory.decodeByteArray(data,0,data.length);
            }
        };
        getUserInfoTherad.start();
        try {
            getUserInfoTherad.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
            userName.setText(userInfo[2]);
            userSignature.setText("签名: "+userInfo[5]);
            head.setImageBitmap(createCircleImage(bitmap));
    }

    @Override
    public void onStop() {
        super.onStop();
    }
    //配置改变时，不用重新启动Activity
    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onPause() {
        super.onPause();
    }


    public void initView(){

        collection = this.getActivity().findViewById(R.id.user_collection);
        history = this.getActivity().findViewById(R.id.user_history);
        article = this.getActivity().findViewById(R.id.user_article);
        comments = this.getActivity().findViewById(R.id.user_comments);
        account = this.getActivity().findViewById(R.id.user_account);
        option = this.getActivity().findViewById(R.id.user_setting);
        about_us = this.getActivity().findViewById(R.id.about_us);
        feedback = this.getActivity().findViewById(R.id.user_feedback);
        question = this.getActivity().findViewById(R.id.user_question);
        head = this.getActivity().findViewById(R.id.user_head);
        userName = this.getActivity().findViewById(R.id.myuser_name);
        userSignature = this.getActivity().findViewById(R.id.user_signature);
        laboratory = this.getActivity().findViewById(R.id.user_laboratory);
        achievement = this.getActivity().findViewById(R.id.user_achievement);

        userName.setText(userInfo[2]);
        userSignature.setText("签名: "+userInfo[5]);
        head.setImageBitmap(createCircleImage(bitmap));

        initViewEvent();
    }
    public void initViewEvent(){
        //收藏部分点击事件
        collection.setOnClickListener(
                e->{
                    Intent intent = new Intent(getActivity() , CollectionActivity.class);
                    startActivity(intent);
                }
        );
        //浏览历史部分点击事件
        history.setOnClickListener(
                e->{
                    Intent intent = new Intent(getActivity() , StudyActivity.class);
                    startActivity(intent);
                }
        );
        //创作部分点击事件
        article.setOnClickListener(
                e->{
                    Intent intent = new Intent(getActivity() , MyArticleActivity.class);
                    startActivity(intent);
                }
        );
        //评论部分点击事件
        comments.setOnClickListener(
                e->{
                    Intent intent = new Intent(getActivity() , CommentActivity.class);
                    startActivity(intent);
                }
        );
        //账号部分点击事件
        account.setOnClickListener(
                e->{
                    Intent intent = new Intent(getActivity() , AccountActivity.class);
                    startActivity(intent);
                }
        );
        //设置部分点击事件
        option.setOnClickListener(
                e->{
                    Intent intent = new Intent(getActivity() , OptionActivity.class);
                    startActivity(intent);
                }
        );
        //用户反馈点击事件
        feedback.setOnClickListener(
                e->{
                    Intent intent = new Intent(getActivity() , UserFeedbackActivity.class);
                    startActivity(intent);
                }
        );
        //关于我们部分点击事件
        about_us.setOnClickListener(
                e->{
                    Intent intent = new Intent(getActivity() , AboutUsActivity.class);
                    startActivity(intent);
                }
        );

        //常见问题部分点击事件
        question.setOnClickListener(
                e->{
                    Intent intent = new Intent(getActivity(), QuestionActivity.class);
                    startActivity(intent);
                }
        );
        head.setOnClickListener(
                e->{
                    showChoosePicDialog();
                }
        );

        laboratory.setOnClickListener(
                e->{
                    Intent intent = new Intent(getActivity(), MyLaboratoryActivity.class);
                    startActivity(intent);
                }
        );

        achievement.setOnClickListener(
                e->{
                    Intent intent = new Intent(getActivity(), MyAchievementActivity.class);
                    startActivity(intent);
                }
        );

    }
    //弹出对话框方法
    public void showChoosePicDialog()
    {
        //创建对话框对象
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this.getActivity());
        builder.setTitle("设置头像");//设置对话框标题
        String[] items = {"选择本地照片","拍照"};//设置对话框内容
        builder.setNegativeButton("取消",null);//设置对话框按钮
        if(Build.VERSION.SDK_INT>=23)//判断安卓版本
        {
            //申请权限
            PermissionUtil.setNeedPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA});//具体权限
            PermissionUtil.requestAllNeedPermissions(this.getActivity());//申请动态权限
        }
        //添加监听//设置对话框内容为简单列表项
        builder.setItems(items, new DialogInterface.OnClickListener() {//给对话框添加监听
            @Override
            public void onClick(DialogInterface dialog, int which) {//具体点击监听方法
                switch (which)//判断点的是哪个
                {
                    case Constant.CHOOSE_PICTURE://选择本地照片
                        Intent openAlbumIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);//设置intent
                        //弹出文件选择器
                        startActivityForResult(openAlbumIntent, Constant.CHOOSE_PICTURE);
                        break;
                    case Constant.TAKE_PICTURE://选择拍照
                        //调用相机
                        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        Constant.tempUri=getImageUri();//获取相片具体位置
                        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Constant.tempUri);//申请码和值
                        startActivityForResult(openCameraIntent, Constant.TAKE_PICTURE);//跳转界面
                        break;
                }
            }
        });
        builder.create().show();//使对话框显示出来

    }
    //获取图片路径
    public Uri getImageUri()
    {

        file=new File(Environment.getExternalStorageDirectory(),"/temp/"+System.currentTimeMillis() + ".jpg");
        if(!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
        String path = file.getPath();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            Constant.tempUri = Uri.fromFile(file);
        } else {
            //兼容android7.0 使用共享文件的形式
            ContentValues contentValues = new ContentValues(1);
            contentValues.put(MediaStore.Images.Media.DATA, path);
            System.out.println("contentValues="+contentValues+"******");
            Constant.tempUri = this.getActivity().getApplication().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        }
        return Constant.tempUri;
    }
    //重写回调方法
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==this.getActivity().RESULT_OK){//判断处理结果
            switch (requestCode)//判断请求码
            {
                //若为拍照
                case Constant.TAKE_PICTURE:
                    startPhotoZoom(Constant.tempUri);//进行剪裁处理
                    break;
                case Constant.CHOOSE_PICTURE://若为选图
                    startPhotoZoom(data.getData());//进行剪裁处理
                    break;
                case Constant.CROP_SMALL_PICTURE://若为显示图片
                    if(data!=null)
                    {
                        setImageToView(data);//显示图片
                    }
                    break;
                case PermissionUtil.REQUEST_CODE_BN :
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && !Environment.isExternalStorageManager())
                    {
                            Toast.makeText(this.getActivity(),"用户不允许所需权限，程序退出！！！",Toast.LENGTH_LONG).show();
                            this.getActivity().finish();
                    }
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + requestCode);
            }
        }
    }
    //保存图片
    protected void setImageToView(Intent data) {
        Bundle extras = data.getExtras();
        Bitmap bitmap = null;

        try {
            bitmap = BitmapFactory.decodeStream(this.getActivity().getContentResolver().openInputStream(imageCropUri));
            head.setImageBitmap(createCircleImage(bitmap));
            uploadPic(bitmap); //上传图片
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //对图片进行裁剪
    protected void startPhotoZoom(Uri uri) {
        if (uri == null) {
            Log.i("tag", "========The uri is not exist.");
        }
        Intent intent = new Intent("com.android.camera.action.CROP");//创建intent
        intent.setDataAndType(uri, "image/*");//设置类型和数据
        // 设置裁剪
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);//设置宽比例
        intent.putExtra("aspectY", 1);//设置高比例
        intent.putExtra("scale", true);//设置缩放
        intent.putExtra("outputX", 150);//设置宽度
        intent.putExtra("outputY", 150);//设置高度
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);//设置保存
        imageCropUri = uri;
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());//保存图片名字
        intent.putExtra("noFaceDetection", true);//取消人脸识别
        intent.putExtra("return-data", false);//不保留图片数据
        startActivityForResult(intent, Constant.CROP_SMALL_PICTURE);//跳转界面
    }

    public void uploadPic(Bitmap bitmap){
        uploadPicThread upload=new uploadPicThread(bitmap);
        upload.start();
        try {
            upload.join();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class uploadPicThread extends Thread{
        
        private Bitmap bitmap;
        
        public uploadPicThread(Bitmap bitmap){
            this.bitmap=bitmap;
        }
        
        @Override
        public void run()
        {
            ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteOutput);
            //用于保存Bitmap的字节数组
            byte[] picData = byteOutput.toByteArray();

            //分割字符串将路径信息与文件分割
            String []picInfo = userInfo[7].split("/");
            String subPath = picInfo[0];
            String fileName = picInfo[1];

            //当用户头像是默认头像时，只有当用户头像为默认值时才更新用户
            if(fileName.equals("user_moren.png")){
                fileName = userInfo[0]+"_user.png";
                InforUtil.updateUserPic(subPath+"/"+fileName,userInfo[0]);
            }

            Constant.bitmap=bitmap;
            InforUtil.uploadUserHead(fileName,picData);

            try{
                Thread.sleep(100);
            }catch(Exception ee){
                ee.printStackTrace();
            }
        }
    }
    public static Bitmap createCircleImage(Bitmap source) {
        int length = source.getWidth() < source.getHeight() ? source.getWidth() : source.getHeight();
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        Bitmap target = Bitmap.createBitmap(length, length, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(target);
        canvas.drawCircle(length / 2, length / 2, length / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(source, 0, 0, paint);
        return target;
    }
}
