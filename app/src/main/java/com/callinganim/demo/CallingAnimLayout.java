package com.callinganim.demo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class CallingAnimLayout extends LinearLayout {
    
    private DirectionLine directionLine1;
    private DirectionLine directionLine2;
    private CircleImageView leftClientImg;
    private CircleImageView rightClientImg;
    private CircularZoom dashLine;
    private TextView tv_toptip;
    private TextView tv_bottomtip;
    private int width;
    private int height;
    private int lineHorizontalDistances = 20;
    private int lineVerticalDistances = 1;
    private final long ANIMATOR_DURATION = 1000;

    private Point pointFirstStart;
    private Point pointFirstEnd;
    private Point pointSecondStart;
    private Point pointSecondEnd;

    private boolean isStop = true;
    PropertyValuesHolder rotationHolder = PropertyValuesHolder.ofFloat("Rotation",0f,
            60f, -60f, 40f, -40f,
            45f,-45f,22f,-22f,-88f,88f,18f,-18f,53f,-53f,0f);

    public CallingAnimLayout(Context context) {
        this(context,null);
    }

    public CallingAnimLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CallingAnimLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLayout();
    }

    private void initLayout() {
        inflate(getContext(),R.layout.layout_callinganim,this);
        directionLine1 = findViewById(R.id.directionLine1);
        directionLine2 = findViewById(R.id.directionLine2);
        leftClientImg = findViewById(R.id.leftClientImg);
        rightClientImg = findViewById(R.id.rightClientImg);
        dashLine = findViewById(R.id.dashLine);
        tv_toptip = findViewById(R.id.tv_toptip);
        tv_bottomtip = findViewById(R.id.tv_bottomtip);
        directionLine1.setVisibility(View.GONE);
        directionLine2.setVisibility(View.GONE);
        tv_toptip.setVisibility(View.GONE);
        tv_bottomtip.setVisibility(View.GONE);

        dashLine.setVisibility(View.GONE);
        dashLine.setViewColor(Color.GRAY);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasuredWidth();
        height = getMeasuredHeight();
        calcChildLines();
    }
    private int half;
    private void calcChildLines() {
        half = width / 2;
        pointFirstStart = new Point();
        int firstStartX = half - dip2px(lineHorizontalDistances);
        int firstStartY = dip2px(lineVerticalDistances);
        pointFirstStart.x = firstStartX;
        pointFirstStart.y = firstStartY;
        pointFirstEnd = new Point();
        int firstEndX = height * 3 / 20;
        int firstEndY = height * 4 / 5 - dip2px(lineVerticalDistances);
        pointFirstEnd.x = firstEndX;
        pointFirstEnd.y = firstEndY;

        pointSecondStart = new Point();
        int secondStartX = dip2px(lineHorizontalDistances);
        int secondStartY = dip2px(lineVerticalDistances);
        pointSecondStart.x = secondStartX;
        pointSecondStart.y = secondStartY;

        pointSecondEnd = new Point();
        int secondEndX = half - height * 3 / 20;
        int secondEndY = height * 4 / 5 - dip2px(lineVerticalDistances);
        pointSecondEnd.x = secondEndX;
        pointSecondEnd.y = secondEndY;

        directionLine1.drawLine(pointFirstStart,pointFirstEnd);
        directionLine2.drawLine(pointSecondStart,pointSecondEnd);

    }

    public ImageView getLeftClientImg(){
        return leftClientImg;
    }
    public ImageView getRightClientImg(){
        return rightClientImg;
    }

    public void startAnim(){
        isStop = false;
        post(new Runnable() {
            @Override
            public void run() {
                startDirectLineAnim();
            }
        });
    }

    private ValueAnimator animator;
    /**
     * 1.带箭头的直线沿着其方向移动
     */
    private void startDirectLineAnim() {

        /*final int tWidth = pointFirstStart.x - pointFirstEnd.x;
        final int tHeight = pointFirstEnd.y - pointFirstStart.y;

        final int maxLen = (int)Math.sqrt(Math.pow(tWidth,2) + Math.pow(tHeight,2));
        animator = ValueAnimator.ofInt(0, maxLen);
        animator.setDuration(ANIMATOR_DURATION);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                Integer value = (Integer) animation.getAnimatedValue();

                ObjectAnimator translationFirstX = ofFloat(directionLine1, "TranslationX", value * tWidth / maxLen,0);
                ObjectAnimator translationFirstY = ofFloat(directionLine1, "TranslationY", -(value * tHeight / maxLen),0);

                ObjectAnimator translationSecondX = ofFloat(directionLine2, "TranslationX", -value * tWidth / maxLen,0);
                ObjectAnimator translationSecondY = ofFloat(directionLine2, "TranslationY", -(value * tHeight / maxLen),0);
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.setInterpolator(new LinearInterpolator());
                animatorSet.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
                        directionLine1.setVisibility(View.VISIBLE);
                        directionLine2.setVisibility(View.VISIBLE);
                    }
                    @Override
                    public void onAnimationEnd(Animator animation) {
                    }
                });
                animatorSet.playTogether(translationFirstX,translationFirstY,translationSecondX,translationSecondY);
                animatorSet.start();
            }
        });
        animator.setRepeatCount(2);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if(!isStop){
                    startImgAnim();
                }
            }
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }
        });
        animator.start();*/
        /*ObjectAnimator translationFirstX = ofFloat(directionLine1, "TranslationX", pointFirstStart.x - pointFirstEnd.x,0);
        ObjectAnimator translationFirstY = ofFloat(directionLine1, "TranslationY", -(pointFirstEnd.y - pointFirstStart.y),0);

        ObjectAnimator translationSecondX = ofFloat(directionLine2, "TranslationX", -pointFirstStart.x - pointFirstEnd.x,0);
        ObjectAnimator translationSecondY = ofFloat(directionLine2, "TranslationY", -(pointFirstEnd.y - pointFirstStart.y),0);*/
//        ObjectAnimator translationFirstX = ofFloat(directionLine1, "TranslationX", pointFirstStart.x - pointFirstEnd.x,0);
//        ObjectAnimator translationFirstY = ofFloat(directionLine1, "TranslationY", -(pointFirstEnd.y - pointFirstStart.y),0);
//
//        ObjectAnimator translationSecondX = ofFloat(directionLine2, "TranslationX", -pointFirstStart.x - pointFirstEnd.x,0);
//        ObjectAnimator translationSecondY = ofFloat(directionLine2, "TranslationY", -(pointFirstEnd.y - pointFirstStart.y),0);
        /*AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(ANIMATOR_DURATION);
        animatorSet.setInterpolator(new LinearInterpolator());
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }
            @Override
            public void onAnimationEnd(Animator animation) {
                directionLine1.stopAnimation();
                directionLine2.stopAnimation();
                startImgAnim();
            }
        });
//        animatorSet.playTogether(translationFirstX,translationFirstY,translationSecondX,translationSecondY);
        animatorSet.start();*/
        directionLine1.setVisibility(View.VISIBLE);
        directionLine2.setVisibility(View.VISIBLE);
        directionLine1.startAnimation(new DirectionLine.EndCallBackListener() {
            @Override
            public void endCallBackListener() {
                directionLine1.setVisibility(View.GONE);
                directionLine2.setVisibility(View.GONE);
                directionLine1.stopAnimation();
                directionLine2.stopAnimation();
                startImgAnim();
            }
        });
        directionLine2.startAnimation(new DirectionLine.EndCallBackListener() {
            @Override
            public void endCallBackListener() {
            }
        });
    }
    /**
     * 2.头像抖动
     */
    private void startImgAnim() {
        ObjectAnimator leftAnim = ObjectAnimator.ofPropertyValuesHolder(leftClientImg, rotationHolder);
        ObjectAnimator rightAnim = ObjectAnimator.ofPropertyValuesHolder(rightClientImg, rotationHolder);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(1000);

        animatorSet.setInterpolator(new AccelerateInterpolator());
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if(!isStop){
                    startTipAnim();
                }

            }
        });
        animatorSet.playTogether(leftAnim,rightAnim);
        animatorSet.start();
    }

    /**
     * 3.提示信息动画
     */
    private void startTipAnim() {
        ObjectAnimator tv_toptipAnim = ObjectAnimator.ofFloat(tv_toptip, "alpha", 0.0f, 1.0f);
        ObjectAnimator tv_bottomtipAnim = ObjectAnimator.ofFloat(tv_bottomtip, "alpha", 0.0f, 1.0f);
        tv_bottomtipAnim.setRepeatCount(2);
        tv_toptipAnim.setRepeatCount(2);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(870);
        animatorSet.setInterpolator(new AccelerateInterpolator());
        animatorSet.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                dashLine.startAnim(Integer.parseInt(ANIMATOR_DURATION + ""));
                tv_toptip.setVisibility(View.VISIBLE);
                tv_bottomtip.setVisibility(View.VISIBLE);
                dashLine.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
//                dashLine.stopAnim();
                tv_toptip.setVisibility(View.GONE);
                tv_bottomtip.setVisibility(View.GONE);
                dashLine.setVisibility(View.GONE);
                if(!isStop){
                    startDirectLineAnim();
                }

            }
        });
        animatorSet.playTogether(tv_toptipAnim,tv_bottomtipAnim);
        animatorSet.start();
    }
    public void stopAnim(){
        dashLine.stopAnim();
        directionLine1.clearAnimation();
        directionLine2.clearAnimation();
        tv_bottomtip.clearAnimation();
        tv_toptip.clearAnimation();
        leftClientImg.clearAnimation();
        rightClientImg.clearAnimation();
        directionLine1.setVisibility(GONE);
        directionLine2.setVisibility(GONE);
        dashLine.setVisibility(GONE);
        tv_bottomtip.setVisibility(GONE);
        tv_bottomtip.setVisibility(GONE);
        isStop = true;
    }
    private int dip2px(float dipValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int)(dipValue * scale + 0.5f);
    }

}
