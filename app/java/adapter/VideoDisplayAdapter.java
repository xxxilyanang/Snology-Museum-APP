package com.example.gxcg.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaMetadataRetriever;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.gxcg.R;
import com.example.gxcg.item.VideoListItem;
import com.volokh.danylo.video_player_manager.ui.MediaPlayerWrapper;
import com.volokh.danylo.video_player_manager.ui.VideoPlayerView;

import java.io.File;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/7/28.
 */
public class VideoDisplayAdapter extends RecyclerView.Adapter<VideoDisplayAdapter.VideoViewHolder>{

    private final List<VideoListItem> mList; // 视频项列表
    // 构造器
    public VideoDisplayAdapter(List<VideoListItem> list) {
        mList = list;

    }

    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.video_display_layout,
                parent,
                false);
        // 必须要设置Tag, 否则无法显示
        VideoViewHolder holder = new VideoViewHolder(view);
        view.setTag(holder);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final VideoViewHolder holder, int position) {
        VideoListItem videoItem = mList.get(position);
        holder.bindTo(videoItem);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class VideoViewHolder extends RecyclerView.ViewHolder {
        VideoPlayerView mVpvPlayer; // 播放控件
        ImageView mIvCover; // 覆盖层
        ImageView playCover; // 覆盖层2
        TextView mTvTitle; // 标题
        ImageView fd;//全屏放大
        TextView fx;//分享
        String url;//播放路径
        File file=null;//视频文件
        private Context mContext;
        private MediaPlayerWrapper.MainThreadMediaPlayerListener mPlayerListener;

        public VideoViewHolder(View itemView) {
            super(itemView);
            mVpvPlayer = (VideoPlayerView) itemView.findViewById(R.id.item_video_player); // 播放控件
            mTvTitle = (TextView) itemView.findViewById(R.id.video_title);//视频标题
            mIvCover = (ImageView) itemView.findViewById(R.id.video_fm);
            playCover= (ImageView) itemView.findViewById(R.id.video_splay);
            //全屏按钮
            fd=(ImageView)itemView.findViewById(R.id.video_magnify);
//            fd.setOnClickListener
//                    (
//                            e->
//                            {
//                                if (e.getTag().equals("全屏放大"))
//                                {
//                                    Intent intent=new Intent(HsmfdActivity.activity, PfspwebActivity.class);
//                                    intent.putExtra("Url", url);
//                                    HsmfdActivity.activity.startActivity(intent);
//                                }
//                            }
//                    );
            //分享按钮
            fx=(TextView)itemView.findViewById(R.id.video_share);
//            fx.setOnClickListener
//                    (
//                            e->
//                            {
//
//                                Thread thread=new Thread()
//                                {
//                                    @Override
//                                    public void run() {
//                                        byte[] temp= InforUtil.getVedio(url.substring(url.length()-14,url.length()));
//                                        file= FileUtil.getFileByBytes(temp, HsmfdActivity.activity.getCacheDir().getPath(),url.substring(url.length()-14,url.length()));
//                                    }
//                                };
//                                thread.start();
//                                try {
//                                    thread.join();
//                                } catch (InterruptedException interruptedException) {
//                                    interruptedException.printStackTrace();
//                                }
//                                if (file==null)
//                                {
//                                    System.out.println("null");
//                                }
//                                else
//                                {
//                                    IntentUtil.shareVideo(HsmfdActivity.activity,file,"典亮你的生活");
//                                }
//                            }
//                    );
            mContext = itemView.getContext().getApplicationContext();
            mPlayerListener = new MediaPlayerWrapper.MainThreadMediaPlayerListener() {
                @Override
                public void onVideoSizeChangedMainThread(int width, int height) {
                }

                @Override
                public void onVideoPreparedMainThread() {
                    // 视频播放隐藏前图
                    mIvCover.setVisibility(View.INVISIBLE);
                    playCover.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onVideoCompletionMainThread() {
                }

                @Override
                public void onErrorMainThread(int what, int extra) {
                }

                @Override
                public void onBufferingUpdateMainThread(int percent) {
                }

                @Override
                public void onVideoStoppedMainThread() {
                    // 视频暂停显示前图
                    mIvCover.setVisibility(View.VISIBLE);
                    playCover.setVisibility(View.VISIBLE);
                }
            };

            mVpvPlayer.addMediaPlayerListener(mPlayerListener);
        }
        Bitmap bit=null;
        public void bindTo(VideoListItem vli){
            mTvTitle.setText(vli.getTitle());
            url=vli.getVedioUrl();
            //   mIvCover.setImageURI(Uri.parse(vli.getCoverImageUrl()));
            Thread thread=new Thread()
            {
                @Override
                public void run() {
                    try
                    {
                        HashMap<String,String> hashMap=new HashMap<>();
//                        byte[] bytes= InforUtil.getPic("mhtp_10006.png");
//
//                        System.out.println(bytes.length);
                        //   bit =BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }

                }
            };
            thread.start();
            try
            {
                thread.join();
            }
            catch(InterruptedException ie)
            {
                ie.printStackTrace();
            }
            System.out.println(url);
            bit=createThumbnailAtTime(url,(int)(Math.random()*10));

            BitmapDrawable drawable=new BitmapDrawable(bit);
            mIvCover.setBackground(drawable);


        }

        // 返回播放器
        public VideoPlayerView getVpvPlayer() {
            return mVpvPlayer;
        }

    }
    private static Bitmap createThumbnailAtTime(String filePath, int timeInSeconds){
        MediaMetadataRetriever mMMR = new MediaMetadataRetriever();
        mMMR.setDataSource(filePath,new HashMap<String, String>());
        //api time unit is microseconds
        return mMMR.getFrameAtTime(timeInSeconds*1000000, MediaMetadataRetriever.OPTION_CLOSEST);
    }
}
