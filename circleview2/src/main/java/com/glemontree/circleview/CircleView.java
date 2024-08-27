package com.glemontree.circleview;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

public class CircleView extends View {

    private Paint mPaint;
    private int mStrokeWidth;
    private int mCurrentDegree;
    private ArgbEvaluator argbEvaluator = new ArgbEvaluator();

    public CircleView(Context context) {
        this(context, null);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CircleView, defStyleAttr, 0);
        mStrokeWidth = a.getDimensionPixelSize(R.styleable.CircleView_strokeWidth,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics()));
        a.recycle();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(mStrokeWidth);
        mPaint.setStyle(Paint.Style.STROKE);

        new Thread() {
            public void run() {
                while (true) {
                    if (mCurrentDegree < 359) {
                        mCurrentDegree++;
                    } else {
                        mCurrentDegree = 0;
                    }
                    postInvalidate();
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int centre = getWidth() / 2;
        int radius = centre - mStrokeWidth / 2;
        RectF oval = new RectF(centre - radius, centre - radius, centre + radius, centre + radius);
        for (int i = 0; i < mCurrentDegree; i++) {
            int color = (int) argbEvaluator.evaluate(i / 360f, Color.BLUE, Color.GREEN);
            mPaint.setColor(color);
            if (i % 2 == 0) {
                canvas.drawArc(oval, -90 + i, 1.35f, false, mPaint);
            }
        }
    }

    public void setCurrentDegree(int CurrentDegree) {
        this.mCurrentDegree = CurrentDegree;
    }

}
