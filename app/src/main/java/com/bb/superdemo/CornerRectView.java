package com.bb.superdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author ethan
 * @version 创建时间:  2017/7/24.
 */

public class CornerRectView extends View {
    public CornerRectView(Context context) {
        super(context);
    }

    public CornerRectView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CornerRectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CornerRectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    int strokeWidth = 0;
    private Bitmap mSrcB;
    private Bitmap mDstB;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 绘制背景
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);
        paint.setDither(true);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();


        mSrcB = makeSrc(paint, width, height);
        strokeWidth = 5;

        mDstB = makeDst(paint);

        int sc = canvas.saveLayer(0, 0, width, height, null,
                Canvas.MATRIX_SAVE_FLAG | Canvas.CLIP_SAVE_FLAG
                        | Canvas.HAS_ALPHA_LAYER_SAVE_FLAG
                        | Canvas.FULL_COLOR_LAYER_SAVE_FLAG
                        | Canvas.CLIP_TO_LAYER_SAVE_FLAG);
        canvas.drawBitmap(mSrcB, 0, 0, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.XOR));
        canvas.drawBitmap(mDstB, 0, 0, paint);
        paint.setXfermode(null);
        canvas.restoreToCount(sc);
    }

    /**
     * 创建内圆bitmap
     */
    private Bitmap makeDst(Paint paint) {
        int width = getMeasuredWidth() - strokeWidth * 2;
        int height = getMeasuredHeight() - strokeWidth * 2;
        Bitmap bm = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bm);
        RectF left_rect = new RectF(height / 2 + strokeWidth, 0.f + strokeWidth * 2, width - height / 2, height);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawCircle(height / 2 + strokeWidth, height / 2 + strokeWidth, height / 2 - strokeWidth, paint);
        canvas.drawRect(left_rect, paint);
        canvas.drawCircle(width - height / 2 + strokeWidth, height / 2 + strokeWidth, height / 2 - strokeWidth, paint);
        return bm;
    }

    /**
     * 创建外圆bitmap
     */
    static Bitmap makeSrc(Paint paint, int width, int height) {
        Bitmap bm = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bm);
        RectF left_rect = new RectF(height / 2, 0.f, width - height / 2, height);
        canvas.drawCircle(height / 2, height / 2, height / 2, paint);
        canvas.drawRect(left_rect, paint);
        canvas.drawCircle(width - height / 2, height / 2, height / 2, paint);
        return bm;
    }
}
