package com.alexjing.webplayer.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
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

    }
}
