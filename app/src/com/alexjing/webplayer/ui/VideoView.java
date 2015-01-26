package com.alexjing.webplayer.ui;

import android.content.Context;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;
import com.alexjing.webplayer.utils.DeviceInfo;

/**
 * Created by alex on 15/1/26.
 */
public class VideoView extends SurfaceView
{
    private SurfaceCallbackListener surfaceCallbackListener;

    public VideoView(Context context)
    {
        super(context);
        init();
    }

    public VideoView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public VideoView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    private void init()
    {
        getHolder().addCallback(mCallback);
        getHolder().setFormat(PixelFormat.RGBA_8888);
    }

    public void initialize(SurfaceCallbackListener surfaceCallbackListener,boolean push)
    {
        this.surfaceCallbackListener = surfaceCallbackListener;
        if (push)
        {
            getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }else
        {
            getHolder().setType(SurfaceHolder.SURFACE_TYPE_NORMAL);
        }
    }

    public void setSurfaceLayout(int videoWidth,int videoHeight,float videoAspectRatio)
    {
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        int windowWidth = DeviceInfo.getScreenWidth(getContext());
        int windowHeight = DeviceInfo.getScreenHeight(getContext());
        float windowRatio = windowWidth / (float) windowHeight;
        layoutParams.width = windowRatio < videoAspectRatio ? windowWidth : (int) (windowHeight * videoAspectRatio);
        layoutParams.height = windowRatio > videoAspectRatio ? windowHeight : (int) (windowWidth / videoAspectRatio);
        setLayoutParams(layoutParams);
        getHolder().setFixedSize(videoWidth,videoHeight);
    }

    /**
     * SurfaceView 改变的情况
     */
    private SurfaceHolder.Callback mCallback = new SurfaceHolder.Callback()
    {
        @Override
        public void surfaceCreated(SurfaceHolder holder)
        {
            if (surfaceCallbackListener != null)
            {
                surfaceCallbackListener.onSurfaceCreated(holder);
            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
        {
            holder.setKeepScreenOn(true);//使屏幕强制强亮
            if (surfaceCallbackListener != null)
            {
                surfaceCallbackListener.onSurfaceChanged(holder,format,width,height);
            }
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder)
        {
            if (surfaceCallbackListener != null)
            {
                surfaceCallbackListener.onSurfaceDestroyed(holder);
            }
        }
    };

    public void setSurfaceCallbackListener(SurfaceCallbackListener surfaceCallbackListener)
    {
        this.surfaceCallbackListener = surfaceCallbackListener;
    }

    public interface SurfaceCallbackListener
    {
        public void onSurfaceCreated(SurfaceHolder holder);

        public void onSurfaceChanged(SurfaceHolder holder,int format,int width,int height);

        public void onSurfaceDestroyed(SurfaceHolder holder);
    }
}
