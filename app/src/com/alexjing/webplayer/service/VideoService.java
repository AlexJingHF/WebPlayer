package com.alexjing.webplayer.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import io.vov.vitamio.MediaPlayer;

/**
 * 创建播放服务，为其提供监控方法.
 * Created by alex on 15/1/23.
 */
public class VideoService extends Service implements MediaPlayer.OnCompletionListener, MediaPlayer.OnBufferingUpdateListener
        , MediaPlayer.OnCachingUpdateListener, MediaPlayer.OnErrorListener, MediaPlayer.OnHWRenderFailedListener, MediaPlayer.OnInfoListener
        , MediaPlayer.OnPreparedListener, MediaPlayer.OnSeekCompleteListener, MediaPlayer.OnTimedTextListener, MediaPlayer.OnVideoSizeChangedListener
{
    /**
     * 是否初始化
     */
    private boolean isInit;
    /**
     * vitamio播放器
     */
    private MediaPlayer mediaPlayer;

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


    private void initlize()
    {

    }

    /**
     * 视频播放完成后调用
     *
     * @param mp the MediaPlayer that reached the end of the file
     */
    @Override
    public void onCompletion(MediaPlayer mp)
    {

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
        return false;
    }

    /**
     * 硬解失败回调
     */
    @Override
    public void onFailed()
    {

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
        return false;
    }

    /**
     * 在视频预处理完成后调用
     *
     * @param mp the MediaPlayer that is ready for playback
     */
    @Override
    public void onPrepared(MediaPlayer mp)
    {

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

    }
}
