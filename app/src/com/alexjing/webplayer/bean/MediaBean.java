package com.alexjing.webplayer.bean;

/**
 * Created by alex on 15/1/26.
 */
public class MediaBean
{
    private String[] videoPaths;

    private String videoName;

    private long startPos;

    private boolean isHWCoder;

    private boolean isNetwork;

    public String[] getVideoPaths()
    {
        return videoPaths;
    }

    public void setVideoPaths(String[] videoPaths)
    {
        this.videoPaths = videoPaths;
    }

    public String getVideoName()
    {
        return videoName;
    }

    public void setVideoName(String videoName)
    {
        this.videoName = videoName;
    }

    public long getStartPos()
    {
        return startPos;
    }

    public void setStartPos(long startPos)
    {
        this.startPos = startPos;
    }

    public boolean isHWCoder()
    {
        return isHWCoder;
    }

    public void setHWCoder(boolean isHWCoder)
    {
        this.isHWCoder = isHWCoder;
    }

    public boolean isNetwork()
    {
        return isNetwork;
    }

    public void setNetwork(boolean isNetwork)
    {
        this.isNetwork = isNetwork;
    }
}
