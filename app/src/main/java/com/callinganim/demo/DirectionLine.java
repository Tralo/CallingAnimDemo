package com.callinganim.demo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

public class DirectionLine extends View{
    private ValueAnimator anim;

    private Canvas myCanvas;
    private Paint myPaint=new Paint();
    private int lineDistances = 5;
    private Point pointStart = new Point();
    private Point pointEnd;
    private final int DEFAULT_LINESTROKE = 1;
    private int currentLineWidth = DEFAULT_LINESTROKE;


    private Point pointCurrent = new Point();

    private boolean startAnim = false;

    private int currentLineColor = Color.GRAY;
    public DirectionLine(Context context) {
        this(context,null);
    }
    public DirectionLine(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DirectionLine(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }



    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        this.myCanvas=canvas;
        if(startAnim && !(pointStart.x < 0.1f || pointStart.y < 0.1f || pointEnd.x < 0.1f || pointEnd.y < 0.1f)){
            drawAL(pointStart.x, pointStart.y, pointEnd.x, pointEnd.y);
        }

    }



    public void drawLine(Point pointStart,Point pointEnd){
        this.pointStart = pointStart;
        this.pointCurrent = pointEnd;
        this.pointEnd = pointEnd;
    }

    private void initPaint() {
        myPaint.setAntiAlias(true);
        myPaint.setColor(currentLineColor);
        myPaint.setStyle(Paint.Style.STROKE);
        myPaint.setStrokeWidth(currentLineWidth);
    }

    public void startAnimation(final EndCallBackListener endCallBackListener) {
        startAnim = true;
        ValueAnimator anim = ValueAnimator.ofObject(new PointEvaluator(), pointStart, pointCurrent);
        anim.setInterpolator(new LinearInterpolator());
//        anim.setCurrentFraction(0.5f);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                pointEnd = (Point) animation.getAnimatedValue();
                if(pointEnd.y > 30){
                    invalidate();
                }

            }

        });

        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if(endCallBackListener != null){
                    endCallBackListener.endCallBackListener();
                }
            }
        });
        anim.setRepeatCount(2);
        anim.setDuration(500);
        anim.start();
    }

    public void stopAnimation(){
        startAnim = false;
        if(anim != null){
            anim.end();
            anim = null;
        }

    }


    /**
     * 画箭头
     * @param sx
     * @param sy
     * @param ex
     * @param ey
     */
    public void drawAL(int sx, int sy, int ex, int ey)
    {
        double H = 8; // 箭头高度
        double L = 3.5; // 底边的一半
        int x3 = 0;
        int y3 = 0;
        int x4 = 0;
        int y4 = 0;
        double awrad = Math.atan(L / H); // 箭头角度
        double arraow_len = Math.sqrt(L * L + H * H); // 箭头的长度
        double[] arrXY_1 = rotateVec(ex - sx, ey - sy, awrad, true, arraow_len);
        double[] arrXY_2 = rotateVec(ex - sx, ey - sy, -awrad, true, arraow_len);
        double x_3 = ex - arrXY_1[0]; // (x3,y3)是第一端点
        double y_3 = ey - arrXY_1[1];
        double x_4 = ex - arrXY_2[0]; // (x4,y4)是第二端点
        double y_4 = ey - arrXY_2[1];
        Double X3 = new Double(x_3);
        x3 = X3.intValue();
        Double Y3 = new Double(y_3);
        y3 = Y3.intValue();
        Double X4 = new Double(x_4);
        x4 = X4.intValue();
        Double Y4 = new Double(y_4);
        y4 = Y4.intValue();
        // 画线
        myCanvas.drawLine(sx, sy, ex, ey,myPaint);
        Path triangle = new Path();
        triangle.moveTo(ex, ey);
        triangle.lineTo(x3, y3);
        triangle.lineTo(x4, y4);
        triangle.close();
        myCanvas.drawPath(triangle,myPaint);

    }
    // 计算
    public double[] rotateVec(int px, int py, double ang, boolean isChLen, double newLen)
    {
        double mathstr[] = new double[2];
        // 矢量旋转函数，参数含义分别是x分量、y分量、旋转角、是否改变长度、新长度
        double vx = px * Math.cos(ang) - py * Math.sin(ang);
        double vy = px * Math.sin(ang) + py * Math.cos(ang);
        if (isChLen) {
            double d = Math.sqrt(vx * vx + vy * vy);
            vx = vx / d * newLen;
            vy = vy / d * newLen;
            mathstr[0] = vx;
            mathstr[1] = vy;
        }
        return mathstr;
    }
    public class PointEvaluator implements TypeEvaluator {

        @Override
        public Object evaluate(float fraction, Object startValue, Object endValue) {
            Point startPoint = (Point) startValue;
            Point endPoint = (Point) endValue;
            int x = (int) (startPoint.x + fraction * (endPoint.x - startPoint.x));
            int y = (int) (startPoint.y + fraction * (endPoint.y - startPoint.y));
            Point point = new Point();
            point.x = x;
            point.y = y;
            return point;
        }
    }

    public interface EndCallBackListener{
        void endCallBackListener();
    }
}
