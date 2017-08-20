package com.wheat7.cashew.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * Created by wheat7 on 28/04/2017.
 */

public class BulingBulingTextView extends AppCompatTextView {

    private Paint mPaint;
    private Paint mPaint1;
    private Paint mPaint2;
    private float mViewWidth;
    private LinearGradient mLinearGradient;
    private Matrix mGradientMatrix;
    private float mTranslate;

    public BulingBulingTextView(Context context) {
        super(context);
    }

    public BulingBulingTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BulingBulingTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
/*        mPaint1 = new Paint();
        mPaint1.setColor(getResources().getColor(android.R.color.holo_blue_light));
        mPaint1.setStyle(Paint.Style.FILL);

        mPaint2 = new Paint();
        mPaint2.setColor(Color.YELLOW);
        mPaint2.setStyle(Paint.Style.FILL);

        //绘制外层矩形
        canvas.drawRect(
                0,
                0,
                getMeasuredWidth(),
                getMaxHeight(),
                mPaint1);

        //绘制内层矩形
        canvas.drawRect(
                10,
                10,
                getMeasuredWidth() - 10,
                getMeasuredHeight() - 10,
                mPaint2);
        canvas.save();
        canvas.translate(10, 0);

        super.onDraw(canvas);
        canvas.restore();*/

        super.onDraw(canvas);
        if (mGradientMatrix != null){
            mTranslate += mViewWidth / 5;
            if (mTranslate > 2 * mViewWidth){
                mTranslate = -mViewWidth;
            }
            mGradientMatrix.setTranslate(mTranslate, 0);
            mLinearGradient.setLocalMatrix(mGradientMatrix);
            postInvalidateDelayed(100);
        }
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mViewWidth == 0) {
            mViewWidth = getMeasuredWidth();
            if (mViewWidth > 0) {
                mPaint = getPaint();
                mLinearGradient = new LinearGradient(
                        0,
                        0,
                        mViewWidth,
                        0,
                        new int[]{
                                Color.DKGRAY, 0xffffffff,
                                Color.DKGRAY
                        },
                        null,
                        Shader.TileMode.CLAMP);
                mPaint.setShader(mLinearGradient);
                mGradientMatrix = new Matrix();

            }
        }
    }
}
