package com.callinganim.demo;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

public class CircularZoom extends LVBase {
    private Paint mPaint;

    private float mWidth = 0f;
    private float mHigh = 0f;
    private float mMaxRadius = 2;
    private int circularCount = 50;
    private float mAnimatedValue = 1.0f;
    private int mJumpValue = 0;
    public CircularZoom(Context context) {
        super(context);
    }

    public CircularZoom(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircularZoom(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setEndCallbackListener(EndCallbackListener callbackListener) {
        super.mCallbackListener = callbackListener;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHigh = getMeasuredHeight();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float circularX = mWidth / circularCount;

        for (int i = 0,j = circularCount-1; i < circularCount; i++,j--) {

            if (i == mJumpValue % (circularCount / 2)) {

                canvas.drawCircle(i * circularX + circularX / 2f,
                        mHigh / 2,
                        mMaxRadius* mAnimatedValue, mPaint);
                if(j < circularCount / 2){
                    j = j + (circularCount  / 2 );
                }
                canvas.drawCircle(j * circularX + circularX / 2f,
                        mHigh / 2,
                        mMaxRadius* mAnimatedValue, mPaint);
                int k = 0;
                int p = circularCount-1;
                for(; k < i ; k++ ,p--){
                    canvas.drawCircle(k * circularX + circularX / 2f,
                            mHigh / 2,
                            mMaxRadius* mAnimatedValue, mPaint);
                    canvas.drawCircle(p * circularX + circularX / 2f,
                            mHigh / 2,
                            mMaxRadius* mAnimatedValue, mPaint);
                }
            }
        }


    }


    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.WHITE);


    }


    public void setViewColor(int color)
    {
        mPaint.setColor(color);
        postInvalidate();
    }


    @Override
    protected void OnAnimationRepeat(Animator animation) {
        mJumpValue++;
    }

    @Override
    protected void OnAnimationUpdate(ValueAnimator valueAnimator) {
        mAnimatedValue = (float) valueAnimator.getAnimatedValue();

        if (mAnimatedValue < 0.3) {
            mAnimatedValue = 0.3f;
        }


        invalidate();
    }

    @Override
    protected int OnStopAnim() {
        mAnimatedValue = 0f;
        mJumpValue = 0;
        return 0;
    }
    @Override
    protected int SetAnimRepeatMode() {
        return ValueAnimator.RESTART;
    }
    @Override
    protected void InitPaint() {
        initPaint();
    }
    @Override
    protected void AinmIsRunning() {

    }
    @Override
    protected int SetAnimRepeatCount() {
        return ValueAnimator.INFINITE;
    }




}
