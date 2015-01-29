package com.alexjing.webplayer.ui;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.alexjing.webplayer.R;
import com.alexjing.webplayer.bean.MediaBean;
import com.alexjing.webplayer.service.VideoService;
import com.alexjing.webplayer.utils.Config;
import com.alexjing.webplayer.utils.LogUtil;
import com.alexjing.webplayer.utils.StorageUtils;
import io.vov.vitamio.MediaPlayer;

/**
 * Created by alex on 15/1/23.
 */
public class VideoActivity extends Activity implements VideoView.SurfaceCallbackListener ,VideoService.PlayerListener
{
    private String TAG = VideoActivity.class.getSimpleName();
    /**
     * 创建状态
     */
    private boolean isCreated = false;

    /**
     * surfaceview 创建状态
     */
    private boolean isSurfaceCreated = false;

    /**
     * 服务连接状态
     */
    private boolean isServiceConnected = false;

    private VideoService videoService;

    private VideoView videoView;

    private RelativeLayout rlLoading;

    private TextView tvLoading;

    private MediaController mediaController;

    private MediaBean mediaBean;

    private ServiceConnection serviceConnection = new ServiceConnection()
    {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service)
        {
            if (service instanceof VideoService.LocalBinder)
            {
                isServiceConnected = true;
                videoService = ((VideoService.LocalBinder) service).getService();
            } else
            {
                LogUtil.e(TAG, "onServiceConnected \n service is not VideoService.LocalBinder");
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name)
        {
            isServiceConnected = false;
            videoService = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (!io.vov.vitamio.LibsChecker.checkVitamioLibs(this))
            return;
        //缓存目录
        StorageUtils.getOwnCacheDirectory(getApplicationContext(), Config.VIDEO_CACHE_DIR);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        loadView(R.layout.activity_video);
        isCreated = true;
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        if (!isServiceConnected)
        {
            Intent serviceIntent = new Intent(this,VideoService.class);
            serviceIntent.putExtra("isHWCoder",mediaBean.isHWCoder());
            bindService(serviceIntent,serviceConnection, Context.BIND_AUTO_CREATE);
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        if (mediaController != null)
        {
            //mediaController.show
            //显示操作栏
        }
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        if (!isCreated)
        {
            return;
        }
        if (isInitalized())
        {
            if (videoService!=null && videoService.isPlaying())
            {
                videoService.stop();
                mediaBean.setStartPos(videoService.getCurrentPositon());
            }
        }
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        if (isCreated)
        {
            if (isInitalized())
            {
                if (videoService!=null && videoService.isPlaying())
                {
                    videoService.stop();
                    mediaBean.setStartPos(videoService.getCurrentPositon());
                }
            }
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        if (isServiceConnected)
        {
            unbindService(serviceConnection);
        }
        if (isInitalized() && !videoService.isPlaying())
        {
            videoService.release();
        }
        if (mediaController != null)
        {
            mediaController.
        }
    }

    private void loadView(int id)
    {
        setContentView(id);
        getWindow().setBackgroundDrawable(null);
        rlLoading = (RelativeLayout) findViewById(R.id.rlyt_loading);
        tvLoading = (TextView) findViewById(R.id.txt_loading);
        videoView = (VideoView) findViewById(R.id.sv_video);
        videoView.initialize(this, mediaBean.isHWCoder());
    }

    /**
     * 设置Loading文字
     * @param message
     */
    private void setLoadingTxt(String message)
    {
        tvLoading.setText(message);
    }

    /**
     * 设置loading的可见性
     * @param visibility
     */
    private void setLoadingLayoutVisibility(int visibility)
    {
        rlLoading.setVisibility(visibility);
    }

    private void loadPlayConfig()
    {
        if (!isInitalized())
        {
            videoService.setBufferSize(Config.DEFAULT_BUFFER_SIZE);
            videoService.setVideoQuality(MediaPlayer.VIDEOQUALITY_MEDIUM);
            videoService.setDeinterlace(Config.DEFAULT_DEINTERLACE);
            videoService.setVolume(Config.DEFAULT_STEREO_VOLUME,Config.DEFAULT_STEREO_VOLUME);
            if (videoView != null)
            {
                setVideoLayout();
            }
        }else
        {
            LogUtil.e(TAG, "loadPlayConfig error  not initalized complete!");
        }
    }

    private void setVideoLayout()
    {
        videoView.setSurfaceLayout(videoService.getVideoWidth(),videoService.getVideoHeight(),videoService.getVideoAspectRatio());
    }


    private boolean isInitalized()
    {
        return isCreated && videoService != null && videoService.isInitialized();
    }

    @Override
    public void onSurfaceCreated(SurfaceHolder holder)
    {

    }

    @Override
    public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height)
    {

    }

    @Override
    public void onSurfaceDestroyed(SurfaceHolder holder)
    {

    }

    /**
     * 硬件渲染失败调用
     */
    @Override
    public void onHWRenderFailed()
    {

    }

    /**
     * 视频尺寸变化时调用
     *
     * @param width
     * @param height
     */
    @Override
    public void onVideoSizeChanged(int width, int height)
    {

    }

    /**
     * 播放器初始化完成后，将开始读取文件时调用
     */
    @Override
    public void onOpenStart()
    {

    }

    /**
     * 准备播放完成后，将开始播放时播放
     */
    @Override
    public void onOpenSuccess()
    {

    }

    /**
     * 打开播放错误时调用
     */
    @Override
    public void onOpenFailed()
    {

    }

    /**
     * 缓冲开始调用
     */
    @Override
    public void onBufferStart()
    {

    }

    /**
     * 缓冲完成调用
     */
    @Override
    public void onBufferComplete()
    {

    }

    /**
     * 缓冲时下载速率调用
     *
     * @param kbPerSec
     */
    @Override
    public void onDownloadRateChanged(int kbPerSec)
    {

    }

    /**
     * 播放完成时调用
     */
    @Override
    public void onPlayComplete()
    {

    }

    /**
     * 网络错误时调用
     */
    @Override
    public void onNetworkError()
    {

    }

    /**
     * seek 完成调用
     */
    @Override
    public void onSeekComplete()
    {

    }

    /**
     * 命令队列
     */

    public static final int OPEN_FILE = 0;
    public static final int OPEN_START= 1;
    public static final int OPEN_SUCCESS = 2;
    public static final int OPEN_FAILED = 3;
    public static final int HW_FAILED = 4;
    public static final int BUFFER_START = 5;
    public static final int BUFFER_PROGRESS = 6;
    public static final int BUFFER_COMPLETE = 7;
    public static final int SEEK_START = 8;
    public static final int SEEK_COMPLETE = 9;

    private Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            LogUtil.d(TAG,"handler what == "+msg.what);
            switch (msg.what)
            {
                case OPEN_FILE:
                {
                    if (!videoService.isInitialized())
                    {
                        videoService.initialize(VideoActivity.this, mediaBean.getVideoPaths(), mediaBean.getStartPos(), mediaBean.isHWCoder());
                    }
                    videoService.setPlayerListener(VideoActivity.this);
                    if (videoView != null)
                    {
                        videoService.setDisplay(videoView.getHolder());
                    }
                }
                break;
                case OPEN_START:
                {
                    setLoadingLayoutVisibility(View.VISIBLE);
                    setLoadingTxt(getResources().getString(R.string.loading));
                }
                break;
                case OPEN_SUCCESS:
                {

                }
                break;
            }
        }
    };
}
