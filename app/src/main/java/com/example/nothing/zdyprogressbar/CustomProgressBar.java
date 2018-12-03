package com.example.nothing.zdyprogressbar;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.BounceInterpolator;

public class CustomProgressBar extends View {

    private final int DEFAULT_WIDTH = dp2px(getContext() , 200);
    private final int DEFAULT_HEIGHT = dp2px(getContext() , 200);
    private Paint arcPaint , arcPaint2 , arcPaint3 , arcPaint4;
    private Paint textPaint;
    private int arcWidth = dp2px(getContext() , 15);//弧线宽度
    private int textSize = dp2px(getContext() , 30);//字体大小
    private float currentAngle = 0;//动画当前角度
    private float startAngle = 180;///弧形初始角度
    private float maxAngle = 360;//弧形最大角度
    private float contentCurrentAngle = 0;//当前角度
    private String textConetnt = "这是个圆弧";
    private float v;
    private int contentMaxAngle;

    public CustomProgressBar(Context context) {
        this(context , null);
    }

    public CustomProgressBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs , 0);
    }

    public CustomProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        arcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        arcPaint.setStyle(Paint.Style.STROKE);
        arcPaint.setStrokeWidth(arcWidth);
        arcPaint.setColor(getResources().getColor(R.color.colorAccent));
        arcPaint.setStrokeCap(Paint.Cap.ROUND);
        arcPaint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        arcPaint2.setStyle(Paint.Style.STROKE);
        arcPaint2.setStrokeWidth(arcWidth);
        arcPaint2.setColor(getResources().getColor(R.color.colorPrimary));
        arcPaint2.setStrokeCap(Paint.Cap.ROUND);
        arcPaint3 = new Paint(Paint.ANTI_ALIAS_FLAG);
        arcPaint3.setStyle(Paint.Style.STROKE);
        arcPaint3.setStrokeWidth(arcWidth);
        arcPaint3.setColor(getResources().getColor(R.color.colorPrimaryDark));
        arcPaint3.setStrokeCap(Paint.Cap.ROUND);
        arcPaint4 = new Paint(Paint.ANTI_ALIAS_FLAG);
        arcPaint4.setStyle(Paint.Style.STROKE);
        arcPaint4.setStrokeWidth(arcWidth);
        arcPaint4.setColor(getResources().getColor(R.color.colorAccent));
        arcPaint4.setStrokeCap(Paint.Cap.ROUND);
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(getResources().getColor(R.color.colorPrimary));
        textPaint.setTextSize(textSize);
        startAnim();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = getMeasureSize(widthMeasureSpec , DEFAULT_WIDTH);
        int heightSize = getMeasureSize(heightMeasureSpec , DEFAULT_HEIGHT);
        setMeasuredDimension(widthSize, heightSize);
    }

    private int getMeasureSize(int measureSpec , int defaultValue) {
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        switch (mode){
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                size = defaultValue;
                break;
            case MeasureSpec.EXACTLY:
                size = size;
                break;
        }
        return size;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //圆弧
        RectF rect = new RectF(arcWidth , arcWidth , getWidth() - arcWidth, getHeight() - arcWidth);
        canvas.drawArc(rect , startAngle , currentAngle , false , arcPaint);
        //圆弧
        RectF rect2 = new RectF(arcWidth * 3 , arcWidth * 3 , getWidth() - arcWidth * 3, getHeight() - arcWidth * 3);
        canvas.drawArc(rect2 , startAngle , currentAngle , false , arcPaint2);
        //圆弧
        RectF rect3 = new RectF(arcWidth * 5 , arcWidth * 5 , getWidth() - arcWidth * 5, getHeight() - arcWidth * 5);
        canvas.drawArc(rect3 , startAngle , currentAngle , false , arcPaint3);
        //圆弧
        RectF rect4 = new RectF(arcWidth * 7 , arcWidth * 7 , getWidth() - arcWidth * 7, getHeight() - arcWidth * 7);
        canvas.drawArc(rect4 , startAngle , currentAngle , false , arcPaint4);
        //字体
        Rect textRect = new Rect();
        textPaint.getTextBounds(textConetnt , 0 , textConetnt.length() , textRect);
        canvas.drawText(textConetnt , getWidth()/2 - textRect.width()/2 , getHeight()/2 + textRect.height()/2 , textPaint);
        invalidate();
    }

    private int dp2px(Context context , int dpValue){
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * density + 0.5f);
    }

    public void startAnim(){
        ValueAnimator progressAnimator = ValueAnimator.ofFloat (0, contentCurrentAngle);
        progressAnimator.setDuration(1000);
        progressAnimator.setTarget(currentAngle);
        progressAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentAngle = (float) animation.getAnimatedValue();
                int cur = Math.round(currentAngle / v);
                if (cur < 0){
                    cur= 0;
                }else if (cur > contentMaxAngle){
                    cur = contentMaxAngle;
                }
                textConetnt = cur + "";

            }
        });
        //progressAnimator.setInterpolator(new BounceInterpolator());
        progressAnimator.start();
        progressAnimator.setRepeatCount(-1);
    }
    
    public void setProgressValue(int contentMaxAngle , int contentCurrentAngle){
        if (contentCurrentAngle > contentMaxAngle){
            contentCurrentAngle = contentMaxAngle;
        }
        this.contentMaxAngle = contentMaxAngle;
        //占比
        v = maxAngle / contentMaxAngle;
        this.contentCurrentAngle = contentCurrentAngle * v;
        startAnim();
    }

    public void setMaxAngle(int maxAngle){
        this.maxAngle = maxAngle;
    }
}
