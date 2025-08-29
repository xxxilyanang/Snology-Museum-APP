package com.example.gxcg.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.gxcg.R;
import com.example.gxcg.activity.CourseVideoActivity;
import com.example.gxcg.util.InforUtil;
import com.example.gxcg.util.MyGetBitmap;
import com.example.gxcg.util.StrListChange;

import java.util.ArrayList;
import java.util.List;


public class CourseFragment extends Fragment
{
    private int be_selected_item = 0;       //选择选项来改变当前选中前景图用
    View view;//加载的界面布局
    List<String[]> list;
    String[][] strLeftArray;
    String[][] strRightArray;
    int[] pic_id;

    List<String[]> courseGroup_ls;//课程组列表数据
    String[][] courseGroup_array;//课程组数组数据
    List<String[]> videoCourseID_ls;//对应课程组下的所有课程ID列表
    String[][] videoCourseID_array;//对应课程组下的所有课程ID数组
    String[][] videoCourse_array;//课程信息数组
    List<String[]> temp_right_ls;//右边临时数据

    String videoCourse_prePicPath = "pic/video_course_pic";//路径前缀
    String[] videoCourse_picPath;//图片路径
    List<byte[]> bytes_pic = new ArrayList<>();//图片二进制数据
    List<Bitmap> course_bitmap = new ArrayList<>();


    //主页面布局
    ViewPager viewPager;
    private View view_video_course;
    private View view_right;

    private ArrayList<View> mViews;
    private ArrayList<String> mTitle;
    private MyPagerAdapter mAdapter;

    private int current_index = 0;
    private Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case 1:
                    initView_courseGroup_leftList();
                    break;
                case 2:
                    initView_videoCourse_RightList();
                    break;
                case 3:
                    //reInitView_videoCourse_RightList();
                    break;
            }
        }
    };
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {

        view = inflater.inflate(R.layout.fragment_course,null); //       获得当前主布局
        viewPager = view.findViewById(R.id.viewpager_course);        //       获取布局中的viewPager
        view_video_course = inflater.inflate(R.layout.add_fragment_course1,null);
        //view_right= this.getLayoutInflater().inflate((R.layout.add_fragment_course1), null);
        //view_2 = inflater.inflate(R.layout.add_fragment_course2,null);
        //获取所有的ID和名字(标题)，放到courseGroup_array里面
        getCourseGroupDataThread getCourseGroupDataThread = new getCourseGroupDataThread();
        getCourseGroupDataThread.start();
        try {
            getCourseGroupDataThread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //传入第一个左方选项块的第一个数据即ID
        getVideoCourseDataThread getVideoCourseDataThread = new getVideoCourseDataThread(courseGroup_array[0][0]);
        getVideoCourseDataThread.start();
        try {
            getVideoCourseDataThread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //主界面的ViewPager设置
        mViews = new ArrayList<>();//将要显示的布局存放到list数组
        mViews.add(view_video_course);
        //mViews.add(view_2);
        mTitle = new ArrayList<>();//存放标题的数组
        mTitle.add("视频课程");
        mTitle.add("热门话题");
        mAdapter=new MyPagerAdapter(mViews, mTitle);//实例化适配器
        viewPager.setAdapter(mAdapter);//设置适配器
        return view;//  返回页面布局
    }

    private class MyListAdapter extends BaseAdapter
    {
        LayoutInflater layoutInflater;
        public MyListAdapter(Context context)
        {
            this.layoutInflater=LayoutInflater.from(context);
        }
        @Override
        public int getCount()
        {
            return courseGroup_array.length;
        }

        @Override
        public Object getItem(int position)
        {
            return position;
        }

        @Override
        public long getItemId(int position)
        {
            return position;
        }

        @Override
        public View getView(int i, View view, ViewGroup parent)
        {
            ViewClass viewClass;
            if(view == null)
            {
                //获取选项块布局文件
                view = layoutInflater.inflate(R.layout.listview_item_course,null);
                viewClass = new CourseFragment.MyListAdapter.ViewClass();
                viewClass.textView = (TextView) view.findViewById(R.id.text_listview_course);
                //viewClass.imageView = (ImageView) view.findViewById(R.id.pic_listview_course);
                view.setTag(viewClass);//很重要  要写
            }else {
                viewClass = (CourseFragment.MyListAdapter.ViewClass)view.getTag();
            }
            viewClass.textView.setText(courseGroup_array[i][1]);
            if(current_index==i)
            {
                //viewClass.imageView.setVisibility(View.VISIBLE);
                //viewClass.imageView.setImageBitmap(cyBitmap[i]);
                viewClass.textView.setTextColor(android.graphics.Color.parseColor("#000000"));
                view.setBackgroundColor(Color.parseColor("#ecd8bd"));
            }else{
                //viewClass.imageView.setVisibility(View.GONE);
                viewClass.textView.setTextColor(android.graphics.Color.parseColor("#000000"));
            }
            return view;
        }
        private class ViewClass
        {
            ImageView imageView;
            TextView textView;
        }
    }
    private class MyGridViewAdapter extends BaseAdapter
    {
        private LayoutInflater layoutInflater = null;
        public MyGridViewAdapter(Context context)
        {
            this.layoutInflater = LayoutInflater.from(context);
        }
        @Override
        public int getCount()
        {
            return videoCourse_array.length;
        }
        @Override
        public Object getItem(int position)
        {
            return position;
        }

        @Override
        public long getItemId(int position)
        {
            return position;
        }

        public View getView(int i, View view, ViewGroup parent)
        {
            ViewClass viewClass;
            if(view==null)
            {
                view = layoutInflater.inflate(R.layout.user_item,null);
                viewClass = new MyGridViewAdapter.ViewClass();
                //默认外观设置
                viewClass.imageView = (ImageView)view.findViewById(R.id.imageView17);
                viewClass.textView_name = (TextView)view.findViewById(R.id.user_name);
                viewClass.nc = (TextView)view.findViewById(R.id.nc);
                view.setTag(viewClass);//很重要  要写
            }else {
                viewClass = (MyGridViewAdapter.ViewClass)view.getTag();
            }
            //viewClass.imageView.setImageResource(pic_id[i]);
            viewClass.imageView.setImageBitmap(course_bitmap.get(i));
            viewClass.textView_name.setText(videoCourse_array[i][2]);
            viewClass.nc.setText(videoCourse_array[i][1]);
            return view;
        }
        private class ViewClass
        {
            ImageView imageView;
            TextView textView_name;
            TextView nc;
        }
    }

    public void initView_courseGroup_leftList()
    {
        ListView lv = (ListView) view_video_course.findViewById(R.id.listview_course_left);
        //列表适配器
        final MyListAdapter myListAdapter =new MyListAdapter(CourseFragment.this.getContext());
        lv.setAdapter(myListAdapter);
        //添加监听事件
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(be_selected_item != position){
                    //上次被选中的item，恢复原背景色
                    parent.getChildAt(be_selected_item).setBackgroundColor(Color.parseColor("#d0b78f"));
                    //当前被选中的item，背景色变成白色
                    view.setBackgroundColor(Color.parseColor("#ecd8bd"));
                    //刷新被选中item的编号
                    be_selected_item = position;
                }
                current_index = position;
                getVideoCourseDataThread gvcdt = new getVideoCourseDataThread(courseGroup_array[position][0]);
                gvcdt.start();
                try {
                    gvcdt.join();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //刷新与适配器绑定的数据
                myListAdapter.notifyDataSetInvalidated();
            }
        });
    }
    public void initView_videoCourse_RightList()
    {

        //获取右方的的ListView
        GridView gv = (GridView) view_video_course.findViewById(R.id.gridview_course_right);
        gv.setVisibility(View.VISIBLE);
        //右方的数据适配器
        MyGridViewAdapter myGridViewAdapter = new MyGridViewAdapter(CourseFragment.this.getContext());
        gv.setAdapter(myGridViewAdapter);
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //暂无点击事件
                Intent intent = new Intent(CourseFragment.this.getContext(), CourseVideoActivity.class);
                //Log.d("TAG-couF", "onItemClick: "+videoCourse_array[position].length);
                intent.putExtra("course_video_id", videoCourse_array[position][0]);
                intent.putExtra("course_name", videoCourse_array[position][1]);
                intent.putExtra("course_introduce", videoCourse_array[position][2]);
                intent.putExtra("course_video_picUrl", videoCourse_array[position][3]);
                startActivity(intent);
            }
        });
    }

    //加上这个会闪退，待研究
//    public void reInitView_videoCourse_RightList()
//    {
//        GridView gv = (GridView) view.findViewById(R.id.gridview_course_right);
//        gv.setVisibility(View.INVISIBLE);//设置不可见
//    }

    //已完成
    public class getCourseGroupDataThread extends Thread
    {
        @Override
        public void run() {
            courseGroup_ls = new ArrayList<>();
            courseGroup_ls = InforUtil.getCourseGroupData();
            //courseGroup_ls: 结果:{[a,b,c],[a,b,c]}
            if (!courseGroup_ls.isEmpty() && courseGroup_ls.size() != 0) {
                courseGroup_array = StrListChange.ListToTwoStringArray(courseGroup_ls);
            }
            Message message = Message.obtain();
            message.what = 1;
            handler.sendMessage(message);
        }
    }
    //已完成
    public class getVideoCourseDataThread extends Thread
    {
        String id;
        public getVideoCourseDataThread(String id) {
            this.id = id;
        }
        @Override
        public void run() {
            videoCourseID_ls = new ArrayList<>();
            //获取指定类别id下的所有课程ID(videoCourseID_ls)，，{[10001],[10002],[]}
            videoCourseID_ls = InforUtil.getVideoCourseIdByGroupId(id);
            //videoCourse_picPath_ls = InforUtil.getVideoCoursePicPathByCourseId()
            if (course_bitmap != null && bytes_pic != null)
            {
                bytes_pic.clear();
                course_bitmap.clear();//先清空图片信息
            }
            if (!videoCourseID_ls.isEmpty() && videoCourseID_ls.size() != 0) {
                videoCourseID_array = StrListChange.ListToTwoStringArray(videoCourseID_ls);//[[10001],[10002],[]]
                //videoCourseID_array.length就是多少个item
                videoCourse_array = new String[videoCourseID_array.length][4];
                for (int i = 0; i < videoCourse_array.length; i++) {
                    //获取指定id文章的所有具体信息（每篇4个）
                    temp_right_ls = InforUtil.getVideoCourseAllInfoById(videoCourseID_array[i][0]);

                    if (!temp_right_ls.isEmpty() && temp_right_ls.size() != 0) {
                        String[][] temp_list = StrListChange.ListToTwoStringArray(temp_right_ls);
                        for (int j = 0; j < 4; j++) {
                            videoCourse_array[i][j] = temp_list[0][j];
                        }
                    }
                    bytes_pic.add(InforUtil.getPic(videoCourse_prePicPath, videoCourse_array[i][3]));
                    if (bytes_pic.get(i) != null)
                    {
                        course_bitmap.add(MyGetBitmap.getBitmapFromByteArray(bytes_pic.get(i)));
                    }
                }
                Message message = Message.obtain();
                message.what = 2;
                handler.sendMessage(message);
            } else {
                Message message = Message.obtain();
                message.what = 3;
                handler.sendMessage(message);
            }
        }
    }
}

class MyPagerAdapter extends PagerAdapter
{
    private ArrayList<View> mViewList;
    private ArrayList<String> mTitleList;

    public MyPagerAdapter() {

    }

    public MyPagerAdapter(ArrayList<View> viewlist, ArrayList<String> mtitlelist) {

        mViewList = viewlist;
        this.mTitleList = mtitlelist;
    }

    @Override
    public int getCount() {

        return mViewList.size();//返回view数组大小
    }


    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {

        return view==object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        container.addView(mViewList.get(position));
        return mViewList.get(position);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView(mViewList.get(position));
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        return mTitleList.get(position);
    }
}
