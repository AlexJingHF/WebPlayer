package com.alexjing.webplayer.utils;

/**
 * Created by alex on 15/1/24.
 */
public class Config
{
    public static final boolean IS_DEBUG = true;

    /**
     * 设置缓存路径
     */
    public static final String VIDEO_CACHE_DIR = "Android/webplayer/cache/video";

    /**
     * 默认缓存大小
     */
    public static final int DEFAULT_BUFFER_SIZE = 512 * 1024;

    /**
     * 是否采用隔行扫描
     */
    public static final boolean DEFAULT_DEINTERLACE = false;

    /**
     * 默认音量
     */
    public static final float DEFAULT_STEREO_VOLUME = 1.0f;

    /**
     * 默认菜单弹出世间
     */
    public static final int DEFAULT_SHOW_TIME = 4000;
}
