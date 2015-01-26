package com.alexjing.webplayer.ui;

import android.app.Activity;
import android.content.ComponentName;
import android.content.ServiceConnection;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.IBinder;
import android.view.SurfaceHolder;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.alexjing.webplayer.R;
import com.alexjing.webplayer.bean.MediaBean;
import com.alexjing.webplayer.service.VideoService;
import com.alexjing.webplayer.utils.Config;
import com.alexjing.webplayer.utils.LogUtil;
import com.alexjing.webplayer.utils.StorageUtils;

/**
 * Created by alex on 15/1/23.
 */
public class VideoActivity extends Activity implements VideoView.SurfaceCallbackListener
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
            }else
            {
                LogUtil.e(TAG,"onServiceConnected \n service is not VideoService.LocalBinder");
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

    private void loadView(int id)
    {
        setContentView(id);
        getWindow().setBackgroundDrawable(null);
        rlLoading = (RelativeLayout) findViewById(R.id.rlyt_loading);
        tvLoading = (TextView) findViewById(R.id.txt_loading);
        videoView = (VideoView) findViewById(R.id.sv_video);
        videoView.initialize(this,mediaBean.isHWCoder());
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
}
