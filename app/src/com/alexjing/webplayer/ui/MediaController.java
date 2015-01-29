package com.alexjing.webplayer.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import com.alexjing.webplayer.R;

/**
 * Created by alex on 15-1-22.
 */
public class MediaController extends FrameLayout {

    private Context context;

    private Animation animSlideInTop;
    private Animation animSlideInBottom;
    private Animation animSlideOutTop;
    private Animation animSlideOutBottom;

    private View root;

    private View back;

    private View play;

    private View backWard;

    private View forWard;

    private TextView currentTimeTxt;

    private TextView totalTimeTxt;

    private TextView downloadRateTxt;

    private SeekBar progressBar;


    public MediaController(Context context) {
        super(context);
        init(context);
    }

    public MediaController(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MediaController(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context)
    {
        this.context = context;
        initAnimation();
        initView();
    }

    /**
     * 释放所有资源，移除命令队列
     */
    public void release()
    {

    }

    /**
     * <p>初始化动画</p>
     */
    private void initAnimation()
    {
        animSlideOutBottom = AnimationUtils.loadAnimation(context,
                R.anim.slide_out_bottom);
        animSlideOutTop = AnimationUtils.loadAnimation(context,
                R.anim.slide_out_top);
        animSlideInBottom = AnimationUtils.loadAnimation(context,
                R.anim.slide_in_bottom);
        animSlideInTop = AnimationUtils.loadAnimation(context,
                R.anim.slide_in_top);
        animSlideOutBottom.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
//                rootLayout.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

    private void initView()
    {
        root = View.inflate(getContext(),R.layout.rlyt_mediacontroller,this);

        back = root.findViewById(R.id.btn_back);
        backWard = root.findViewById(R.id.btn_backward);
        forWard = root.findViewById(R.id.btn_forward);
        play = root.findViewById(R.id.btn_play);

        currentTimeTxt = (TextView) root.findViewById(R.id.txt_current_time);
        totalTimeTxt = (TextView) root.findViewById(R.id.txt_total_time);
        downloadRateTxt = (TextView) root.findViewById(R.id.download_speed);
        progressBar = (SeekBar) root.findViewById(R.id.seekbar);
    }

    public interface MediaControllerListener(

}
