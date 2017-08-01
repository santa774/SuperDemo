package com.bb.superdemo;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;


/**
 * @author ethan
 * @version 创建时间:  2017/7/24.
 */

public class ShapeIndicatorView extends View implements TabLayout.OnTabSelectedListener, ViewPager.OnPageChangeListener {

    private Paint mShapePaint;
    private Path mShapePath;
    private RectF mRectF;
    private int mShapeHorizontalSpace;
    private int mShapeColor = Color.GREEN;

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    int strokeWidth = 0;
    private Bitmap mSrcB;
    private Bitmap mDstB;


    public ShapeIndicatorView(Context context) {
        this(context, null);
    }

    public ShapeIndicatorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShapeIndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews(context, attrs, defStyleAttr, 0);
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ShapeIndicatorView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initViews(context, attrs, defStyleAttr, defStyleRes);
    }

    private void initViews(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ShapeIndicatorView, defStyleRes, 0);
        mShapeHorizontalSpace = array.getInteger(R.styleable.ShapeIndicatorView_horizontalSpace, 0);
        mShapeColor = array.getColor(R.styleable.ShapeIndicatorView_fullColor, Color.GREEN);
        strokeWidth = array.getInteger(R.styleable.ShapeIndicatorView_strokeWidth, 0);
        array.recycle();

        mShapePaint = new Paint();
        mShapePaint.setAntiAlias(true);
        mShapePaint.setDither(true);
        mShapePaint.setColor(mShapeColor);
    }


    public void setupWithTabLayout(final TabLayout tableLayout) {
        mTabLayout = tableLayout;


        tableLayout.setSelectedTabIndicatorColor(Color.TRANSPARENT);
        tableLayout.setOnTabSelectedListener(this);


        tableLayout.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                if (mTabLayout.getScrollX() != getScrollX())
                    scrollTo(mTabLayout.getScrollX(), mTabLayout.getScrollY());
            }
        });

        ViewCompat.setElevation(this, ViewCompat.getElevation(mTabLayout));
        tableLayout.post(new Runnable() {
            @Override
            public void run() {
                if (mTabLayout.getTabCount() > 0)
                    onTabSelected(mTabLayout.getTabAt(0));

            }
        });

        //清除Tab background
        for (int tab = 0; tab < tableLayout.getTabCount(); tab++) {
            View tabView = getTabViewByPosition(tab);
            tabView.setBackgroundResource(0);
        }
    }

    public void setupWithViewPager(ViewPager viewPager) {
        mViewPager = viewPager;
        viewPager.addOnPageChangeListener(this);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 绘制滑块
        drawPath(canvas);
        // 绘制背景
        drawBackground(canvas);

    }

    /**
     *  画一个圆角矩形的背景
     * @param canvas
     */
    private void drawBackground(Canvas canvas) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        paint.setDither(true);
        paint.setStrokeWidth(strokeWidth);

        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        mSrcB = makeSrc(paint, width, height);
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
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
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

    /**
     * 创建滑块bitmap
     */
    Bitmap makeSubSrc( Paint paint) {
        Bitmap bm = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bm);
        RectF left_rect = new RectF(mRectF.left + mRectF.bottom / 2, mRectF.top, mRectF.right - mRectF.bottom / 2, mRectF.bottom);
        canvas.drawCircle(mRectF.left + mRectF.bottom / 2, mRectF.bottom / 2, mRectF.bottom / 2, paint);
        canvas.drawRect(left_rect, paint);
        canvas.drawCircle(mRectF.right - mRectF.bottom / 2, mRectF.bottom / 2, mRectF.bottom / 2, paint);
        return bm;
    }


    private void drawPath(Canvas canvas) {
        if (mRectF == null || mRectF.isEmpty())
            return;

        int savePos = canvas.save();
        Bitmap subBitmap = makeSubSrc(mShapePaint);
        canvas.drawBitmap(subBitmap, 0, 0, mShapePaint);
        canvas.restoreToCount(savePos);
    }

    private RectF generatePath(int position, float positionOffset) {

        RectF range = new RectF();
        View tabView = getTabViewByPosition(position);

        if (tabView == null)
            return null;

        int left, top, right, bottom;
        left = top = right = bottom = 0;

        if (positionOffset > 0.f && position < mTabLayout.getTabCount() - 1) {
            View nextTabView = getTabViewByPosition(position + 1);
            left += (int) (nextTabView.getLeft() * positionOffset + tabView.getLeft() * (1.f - positionOffset));
            right += (int) (nextTabView.getRight() * positionOffset + tabView.getRight() * (1.f - positionOffset));


            left += mShapeHorizontalSpace;
            right -= mShapeHorizontalSpace;
            top = tabView.getTop() + getPaddingTop();
            bottom = tabView.getBottom() - getPaddingBottom();
            range.set(left, top, right, bottom);
        } else {

            left = tabView.getLeft() + mShapeHorizontalSpace;
            right = tabView.getRight() - mShapeHorizontalSpace;
            top = tabView.getTop() + getPaddingTop();
            bottom = tabView.getBottom() - getPaddingBottom();
            range.set(left, top, right, bottom);


            if (range.isEmpty())
                return range;

        }

        if (mShapePath == null)
            mShapePath = new Path();
        return range;
    }


    private View getTabViewByPosition(int position) {
        if (mTabLayout != null && mTabLayout.getTabCount() > 0) {
            ViewGroup tabStrip = (ViewGroup) mTabLayout.getChildAt(0);
            return tabStrip != null ? tabStrip.getChildAt(position) : null;
        }

        return null;
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        mRectF = generatePath(position, positionOffset);
        invalidate();
    }

    @Override
    public void onPageSelected(int position) {
        if (mTabLayout.getSelectedTabPosition() != position)
            mTabLayout.getTabAt(position).select();

    }


    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * 当已经有一个ViewPager后，当TabLayout的tab改变的时候在onTabSelected方法直接调用ViewPager的
     * setCurrentItem方法调用这个方法后会触发ViewPager的scroll事件也就是在onPageScrolled方法中调用
     * generatePath方法来更新Path，如果没有ViewPager的话直接在onTabSelected的方法中调用generatePath
     * 方法。
     **/
    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        if (mViewPager != null) {
            if (tab.getPosition() != mViewPager.getCurrentItem())
                mViewPager.setCurrentItem(tab.getPosition());
        } else {
            mRectF = generatePath(tab.getPosition(), 0);
            invalidate();
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}