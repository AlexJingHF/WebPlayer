package com.alexjing.webplayer.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.view.SurfaceHolder;
import com.alexjing.webplayer.utils.Config;
import com.alexjing.webplayer.utils.LogUtil;
import com.alexjing.webplayer.utils.NetworkUtil;
import io.vov.vitamio.MediaPlayer;

/**
 * 创建播放服务，为其提供监听回调方法.
 * Created by alex on 15/1/23.
 */
public class VideoService extends Service implements MediaPlayer.OnCompletionListener, MediaPlayer.OnBufferingUpdateListener
        , MediaPlayer.OnCachingUpdateListener, MediaPlayer.OnErrorListener, MediaPlayer.OnHWRenderFailedListener, MediaPlayer.OnInfoListener
        , MediaPlayer.OnPreparedListener, MediaPlayer.OnSeekCompleteListener, MediaPlayer.OnTimedTextListener, MediaPlayer.OnVideoSizeChangedListener
{
    private static final String TAG = VideoService.class.getSimpleName();
    /**
     * 是否初始化
     */
    private boolean isInit;

    /**
     * vitamio播放器
     */
    private MediaPlayer mediaPlayer;

    /**
     * 视频屏幕
     */
    private SurfaceHolder surfaceHolder;

    /**
     * 播放地址数组
     */
    private String[] mediaUrls;

    /**
     * 进度切换至
     */
    private long seekTo;
    /**
     * 播放监听
     */
    private PlayerListener playerListener;
    /**
     * 自定义播放回调接口
     */
    private static interface PlayerListener
    {
        /**
         * 硬件渲染失败调用
         */
        public void onHWRenderFailed();

        /**
         * 视频尺寸变化时调用
         * @param width
         * @param height
         */
        public void onVideoSizeChanged(int width,int height);

        /**
         * 播放器初始化完成后，将开始读取文件时调用
         */
        public void onOpenStart();

        /**
         * 准备播放完成后，将开始播放时播放
         */
        public void onOpenSuccess();

        /**
         * 打开播放错误时调用
         */
        public void onOpenFailed();

        /**
         * 缓冲开始调用
         */
        public void onBufferStart();

        /**
         * 缓冲完成调用
         */
        public void onBufferComplete();

        /**
         * 缓冲时下载速率调用
         * @param kbPerSec
         */
        public void onDownloadRateChanged(int kbPerSec);

        /**
         * 播放完成时调用
         */
        public void onPlayComplete();

        /**
         * 网络错误时调用
         */
        public void onNetworkError();

        /**
         * seek 完成调用
         */
        public void onSeekComplete();
    }

    private final IBinder mBinder = new LocalBinder();

    public class LocalBinder extends Binder
    {
        public VideoService getService()
        {
            return VideoService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return mBinder;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        isInit = false;
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        //release
        release();
    }

    private void release()
    {
        if (mediaPlayer != null)
        {
            mediaPlayer.reset();
            mediaPlayer.release();
            playerListener = null;
            surfaceHolder = null;
            isInit = false;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public boolean onUnbind(Intent intent)
    {
        return super.onUnbind(intent);
    }

    /**
     * 开始播放
     */
    public void start()
    {
        if (!isPlaying())
        {
            mediaPlayer.start();
        }else
        {
            LogUtil.d(TAG,"start\n can't start");
        }
    }

    /**
     * 停止播放
     */
    public void stop()
    {
        if (isPlaying())
        {
            mediaPlayer.start();
        }else
        {
            LogUtil.d(TAG,"stop\n can't stop");
        }
    }

    /**
     * 设置跳转时间
     * @param seekTo
     */
    public void seekTo(long seekTo)
    {
        if (isInit)
        {
            mediaPlayer.seekTo(seekTo);
        }
    }

    /**
     * 获取时间总长
     * @return
     */
    public long getDuration()
    {
        if (isInit)
        {
            return mediaPlayer.getDuration();
        }else
            return 0;
    }

    /**
     *  设置视频播放质量
     *  default is VIDEOQUALITY_LOW.
     *
     * @param quality <ul>
     *                <li>{@link io.vov.vitamio.MediaPlayer.VIDEOQUALITY_HIGH}
     *                <li>{@link io.vov.vitamio.MediaPlayer.VIDEOQUALITY_MEDIUM}
     *                <li>{@link io.vov.vitamio.MediaPlayer.VIDEOQUALITY_LOW}
     *                </ul>
     */
    public void setVideoQuality(int quality)
    {
        if (isInit)
        {
            mediaPlayer.setVideoQuality(quality);
        }
    }

    /**
     * 设置隔行扫描
     * @param deinterlace
     */
    public void setDeinterlace(boolean deinterlace)
    {
        if (isInit)
        {
            mediaPlayer.setDeinterlace(deinterlace);
        }
    }

    /**
     * 设置音量
     * @param left 左声道
     * @param right 右声道
     */
    public void setVolume(int left,int right)
    {
        if (isInit)
        {
            mediaPlayer.setVolume(left, right);
        }
    }

    /**
     * 获取缓冲状态
     * @return boolean
     */
    public boolean isBuffering()
    {
        return (isInit && mediaPlayer.isBuffering());
    }

    /**
     * 设置缓存区大小
     * @param bufferSize
     */
    public void setBufferSize(int bufferSize)
    {
        if (isInit)
        {
            mediaPlayer.setBufferSize(bufferSize);
        }
    }

    /**
     * 获取缓冲进度
     * @return
     */
    public float getBufferProgress()
    {
        if (isInit)
        {
            return mediaPlayer.getBufferProgress();
        }else
            return 0f;
    }

    /**
     * 获取当前播放时间
     * @return
     */
    public long getCurrentPositon()
    {
        if (isInit)
        {
            return mediaPlayer.getCurrentPosition();
        }else
            return 0;
    }

    /**
     * 获取视频宽
     * @return
     */
    public int getVideoWidth()
    {
        if (isInit)
        {
            return mediaPlayer.getVideoWidth();
        }else
        {
            return 0;
        }
    }

    /**
     * 获取视频高
     * @return
     */
    public int getVideoHeight()
    {
        if (isInit)
        {
            return mediaPlayer.getVideoHeight();
        }else
        {
            return 0;
        }
    }

    /**
     * 获取视频横宽比例
     * @return
     */
    public float getVideoAspectRatio()
    {
        if (isInit)
        {
            return mediaPlayer.getVideoAspectRatio();
        }
        return 0f;
    }
    /**
     * 获得播放状态
     * @return
     */
    public boolean isPlaying()
    {
        if (isInit && mediaPlayer != null)
        {
            return mediaPlayer.isPlaying();
        }else
            return false;
    }

    private void initialize(PlayerListener playerListener,String[] datas,long startPos,boolean isHWCodec)
    {
        if (mediaPlayer == null)
        {
            mediaPlayerInit(isHWCodec);
        }

        this.playerListener = playerListener;
        this.mediaUrls = datas;
        this.seekTo = startPos;

        playerListener.onOpenStart();//准备打开加载播放

        openVideo();
    }

    /**
     * 初始化媒体播放器
     * @param isHWCodec 是否启动硬件渲染
     */
    private void mediaPlayerInit(boolean isHWCodec)
    {
        mediaPlayer = new MediaPlayer(VideoService.this.getApplicationContext(),isHWCodec);

        mediaPlayer.setOnVideoSizeChangedListener(this);
        mediaPlayer.setOnBufferingUpdateListener(this);
        mediaPlayer.setOnHWRenderFailedListener(this);//监听硬件渲染
        mediaPlayer.setOnCachingUpdateListener(this);
        mediaPlayer.setOnSeekCompleteListener(this);
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnTimedTextListener(this);
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnErrorListener(this);
        mediaPlayer.setOnInfoListener(this);
    }

    /**
     * 打开Video
     */
    private void openVideo()
    {
        if (mediaPlayer == null || mediaUrls == null || mediaUrls.length == 0)
        {
            LogUtil.e(TAG, "openVideo error\n mediaPlayer:" + mediaPlayer + "\n mediaUrls:" + mediaUrls);
            return;
        }

        isInit = false;
        mediaPlayer.reset();
        //此处可以先创建文件夹
        mediaPlayer.setDataSegments(mediaUrls, Environment.getExternalStorageDirectory()+ Config.VIDEO_CACHE_DIR);

        if (surfaceHolder != null && surfaceHolder.getSurface() != null && surfaceHolder.getSurface().isValid())
        {
            setDisplay(surfaceHolder);
        }

        mediaPlayer.prepareAsync();

    }

    /**
     * 视频播放完成后调用
     *
     * @param mp the MediaPlayer that reached the end of the file
     */
    @Override
    public void onCompletion(MediaPlayer mp)
    {
        if (playerListener != null)
        {
            playerListener.onPlayComplete();
        }
    }

    /**
     * 在网络视频流缓冲变化时调用
     *
     * @param mp      the MediaPlayer the update pertains to
     * @param percent the percentage (0-100) of the buffer that has been filled thus
     */
    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent)
    {

    }

    //以下的Cache回调
    @Override
    public void onCachingUpdate(MediaPlayer mp, long[] segments)
    {

    }

    @Override
    public void onCachingSpeed(MediaPlayer mp, int speed)
    {

    }

    @Override
    public void onCachingStart(MediaPlayer mp)
    {

    }

    @Override
    public void onCachingComplete(MediaPlayer mp)
    {

    }

    @Override
    public void onCachingNotAvailable(MediaPlayer mp, int info)
    {

    }

    /**
     * 在异步操作调用过程中发生错误时调用。例如视频打开失败。
     *
     * @param mp    the MediaPlayer the error pertains to
     * @param what  the type of error that has occurred:
     *              <ul>
     *              <li>{@link io.vov.vitamio.MediaPlayer.MEDIA_ERROR_UNKNOWN}
     *              <li>
     *              {@link io.vov.vitamio.MediaPlayer.MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK}
     *              </ul>
     * @param extra an extra code, specific to the error. Typically implementation
     *              dependant.
     * @return
     */
    @Override
    public boolean onError(MediaPlayer mp, int what, int extra)
    {
        //具体错误提示，以后添加
        if (playerListener != null)
        {
            playerListener.onOpenFailed();
        }
        return false;
    }

    /**
     * 硬解失败回调
     */
    @Override
    public void onFailed()
    {
        if (playerListener != null)
        {
            playerListener.onHWRenderFailed();
        }
    }

    /**
     * 在有警告或错误信息时调用。例如：开始缓冲、缓冲结束、下载速度变化。
     *
     * @param mp    the MediaPlayer the info pertains to.
     * @param what  the type of info or warning.
     *              <ul>
     *              <li>{@link io.vov.vitamio.MediaPlayer.MEDIA_INFO_VIDEO_TRACK_LAGGING}
     *              <li>{@link io.vov.vitamio.MediaPlayer.MEDIA_INFO_BUFFERING_START}
     *              <li>{@link io.vov.vitamio.MediaPlayer.MEDIA_INFO_BUFFERING_END}
     *              <li>{@link io.vov.vitamio.MediaPlayer.MEDIA_INFO_NOT_SEEKABLE}
     *              <li>{@link io.vov.vitamio.MediaPlayer.MEDIA_INFO_DOWNLOAD_RATE_CHANGED}
     *              </ul>
     * @param extra an extra code, specific to the info. Typically implementation
     *              dependant.
     * @return
     */
    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra)
    {
        if (playerListener != null)
        {
            if (NetworkUtil.isAvailable(getApplicationContext()))
            {
                switch (what)
                {
                    case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                        playerListener.onBufferStart();
                        break;
                    case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                        playerListener.onBufferComplete();
                        break;
                    case MediaPlayer.MEDIA_INFO_DOWNLOAD_RATE_CHANGED:
                        playerListener.onDownloadRateChanged(extra);
                        break;
                    case MediaPlayer.MEDIA_INFO_NOT_SEEKABLE:
                        //影片不可seek
                        break;
                    case MediaPlayer.MEDIA_INFO_VIDEO_TRACK_LAGGING:
                        //影片解码困难
                        break;
                }
            }else
            {
                playerListener.onNetworkError();
            }
        }else
        {
            LogUtil.e(TAG,"onInfo\n playerListener == null");
        }
        return true;
    }

    /**
     * 在视频预处理完成后调用
     *
     * @param mp the MediaPlayer that is ready for playback
     */
    @Override
    public void onPrepared(MediaPlayer mp)
    {
        preparedSuccess();
    }

    private void preparedSuccess()
    {
        isInit = true;
        mediaPlayer.seekTo(seekTo);
        playerListener.onOpenSuccess();
    }

    /**
     * 在seek操作完成后调用。
     *
     * @param mp the MediaPlayer that issued the seek operation
     */
    @Override
    public void onSeekComplete(MediaPlayer mp)
    {

    }

    /**
     * 字幕相关
     *
     * @param text the timedText to display
     */
    @Override
    public void onTimedText(String text)
    {

    }

    /**
     * 字幕相关
     *
     * @param pixels the pixels of the timed text image
     * @param width  the width of the timed text image
     * @param height the height of the timed text image
     */
    @Override
    public void onTimedTextUpdate(byte[] pixels, int width, int height)
    {

    }

    /**
     * 播放器尺寸变化的回调
     *
     * @param mp     the MediaPlayer associated with this callback
     * @param width  the width of the video
     * @param height the height of the video
     */
    @Override
    public void onVideoSizeChanged(MediaPlayer mp, int width, int height)
    {
        if (playerListener != null)
        {
            playerListener.onVideoSizeChanged(width,height);
        }
    }

    /**
     * 设置播放监听
     * @param playerListener
     */
    public void setPlayerListener(PlayerListener playerListener)
    {
        this.playerListener = playerListener;
    }

    /**
     * 设置播放屏幕
     * @param surfaceHolder
     */
    public void setDisplay(SurfaceHolder surfaceHolder)
    {
        this.surfaceHolder = surfaceHolder;
        if (mediaPlayer != null)
        {
            mediaPlayer.setDisplay(surfaceHolder);
        }else
            LogUtil.e(TAG,"setDisplay->media player is null");
    }

    /**
     * 释放播放屏幕
     */
    public void releaseDisplay()
    {
        if (mediaPlayer != null)
        {
            mediaPlayer.releaseDisplay();
            surfaceHolder = null;
        }
    }
    public boolean isInitialized()
    {
        return isInit;
    }
}
