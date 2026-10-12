package com.ibasso.music.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import com.ibasso.volume.R;
public class BalanceView extends View {
    private final String TAG;
    private boolean isCenter;
    private boolean isInit;
    private boolean isInitSetProgress;
    private boolean isTouch;
    private int mBgColor;
    private Paint mBgPaint;
    private int mBitmapHeight;
    private int mBitmapWidth;
    private float mDownX;
    private float mDownY;
    private ISeekBarProgressClickListener mListener;
    private float mMoveX;
    private Paint mNormalBarPaint;
    private int mPaddingSize;
    private int mProgress;
    private int mProgressColor;
    private Paint mProgressPaint;
    private int mSeekBarSize;
    private Paint mSelectBarPaint;
    private int mSize;
    private int mThumbColor;
    private Paint mThumbPaint;
    private int mThumbSelectColor;
    private int mThumbSize;
    private int mThumbTouchSize;
    private int mTouchSlop;
    private int mViewWidth;
    private int maxProgress;

    public interface ISeekBarProgressClickListener {
        void onSeekBarChanged(BalanceView balanceView, int i);

        void onSeekBarStart(BalanceView balanceView, int i);

        void onSeekBarStop(BalanceView balanceView, int i);
    }

    public BalanceView(Context context) {
        this(context, null);
    }

    public BalanceView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public BalanceView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.TAG = "MyProgressView";
        this.maxProgress = 100;
        this.mProgress = 0;
        this.mSeekBarSize = 3;
        this.mPaddingSize = 10;
        this.mViewWidth = 0;
        this.isInitSetProgress = true;
        this.isInit = true;
        this.mThumbColor = -1;
        this.mThumbSelectColor = -1;
        this.mThumbSize = 20;
        this.mThumbTouchSize = 30;
        this.isTouch = false;
        this.mSize = 20;
        this.isCenter = true;
        setWillNotDraw(false);
        this.mSize = dip2px(context, 5.0f);
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.BalanceView, i, 0);
        this.mProgress = (int) obtainStyledAttributes.getDimension(7, 0.0f);
        this.mSeekBarSize = obtainStyledAttributes.getDimensionPixelSize(4, 3);
        this.mBgColor = obtainStyledAttributes.getColor(1, context.getResources().getColor(R.color.while_fifteen));
        this.mProgressColor = obtainStyledAttributes.getColor(2, -1);
        this.mThumbSize = obtainStyledAttributes.getDimensionPixelSize(9, this.mSize);
        this.mThumbTouchSize = obtainStyledAttributes.getDimensionPixelSize(10, this.mSize) + 3;
        this.mThumbColor = obtainStyledAttributes.getColor(5, -1);
        this.mThumbSelectColor = obtainStyledAttributes.getColor(8, -16776961);
        this.maxProgress = obtainStyledAttributes.getInteger(0, 10);
        obtainStyledAttributes.recycle();
        init(context);
    }

    private void calculationProgress() {
        int width;
        int width2 = getWidth() / 2;
        float width3 = (getWidth() - this.mPaddingSize) - getPaddingLeft();
        if (this.mMoveX > width3) {
            this.mMoveX = width3;
        }
        float f = this.mMoveX;
        int i = this.mPaddingSize;
        if (f < i) {
            this.mMoveX = i;
        }
        float f2 = this.mMoveX;
        float f3 = width2;
        if (f2 >= f3) {
            this.mProgress = (int) (this.maxProgress * ((f2 - f3) / (width - width2)));
        } else {
            float f4 = width2 - i;
            this.mProgress = (int) (this.maxProgress * (((f2 - i) - f4) / f4));
        }
        int i2 = this.mProgress;
        int i3 = this.maxProgress;
        if (i2 > i3) {
            this.mProgress = i3;
        }
        if (this.mProgress < (-i3)) {
            this.mProgress = -i3;
        }
    }

    private void calculationProgressLeft() {
        int width;
        float width2 = (getWidth() - this.mPaddingSize) - getPaddingLeft();
        if (this.mMoveX >= width2) {
            this.mMoveX = width2;
        }
        float f = this.mMoveX;
        int i = this.mPaddingSize;
        if (f <= i) {
            this.mMoveX = i;
        }
        this.mProgress = (int) (((this.mMoveX - i) / (width - i)) * this.maxProgress);
    }

    private void init(Context context) {
        this.mPaddingSize = context.getResources().getDimensionPixelSize(R.dimen.simpleEditor_text_progress_padding_size);
        this.mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        Paint paint = new Paint();
        this.mProgressPaint = paint;
        paint.setColor(this.mProgressColor);
        Paint paint2 = this.mProgressPaint;
        Paint.Style style = Paint.Style.STROKE;
        paint2.setStyle(style);
        Paint paint3 = this.mProgressPaint;
        Paint.Cap cap = Paint.Cap.ROUND;
        paint3.setStrokeCap(cap);
        Paint paint4 = this.mProgressPaint;
        Paint.Join join = Paint.Join.ROUND;
        paint4.setStrokeJoin(join);
        this.mProgressPaint.setStrokeWidth(this.mSeekBarSize);
        this.mProgressPaint.setAntiAlias(true);
        Paint paint5 = new Paint();
        this.mBgPaint = paint5;
        paint5.setColor(this.mBgColor);
        this.mBgPaint.setStyle(style);
        this.mBgPaint.setStrokeCap(cap);
        this.mBgPaint.setStrokeJoin(join);
        this.mBgPaint.setStrokeWidth(this.mSeekBarSize);
        this.mBgPaint.setAntiAlias(true);
        Paint paint6 = new Paint();
        this.mThumbPaint = paint6;
        paint6.setColor(this.mThumbColor);
        Paint paint7 = this.mThumbPaint;
        Paint.Style style2 = Paint.Style.FILL;
        paint7.setStyle(style2);
        this.mThumbPaint.setStrokeCap(cap);
        this.mThumbPaint.setStrokeJoin(join);
        this.mThumbPaint.setStrokeWidth(this.mSeekBarSize);
        this.mThumbPaint.setAntiAlias(true);
        Paint paint8 = new Paint();
        this.mNormalBarPaint = paint8;
        paint8.setColor(-1);
        this.mNormalBarPaint.setStyle(style2);
        Paint paint9 = new Paint();
        this.mSelectBarPaint = paint9;
        paint9.setColor(this.mThumbSelectColor);
        this.mSelectBarPaint.setStyle(style2);
    }

    public int dip2px(Context context, float f) {
        return (int) ((f * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        try {
            getParent().requestDisallowInterceptTouchEvent(true);
        } catch (Exception unused) {
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    public int get() {
        return this.mThumbColor;
    }

    public int getBgColor() {
        return this.mBgColor;
    }

    public int getMax() {
        return this.maxProgress;
    }

    public float getProgress() {
        return this.mProgress;
    }

    public int getProgressColor() {
        return this.mProgressColor;
    }

    public int getSeekBarSize() {
        return this.mSeekBarSize;
    }

    public boolean isCenter() {
        return this.isCenter;
    }

    @Override
    public void onDraw(Canvas canvas) {
        int height;
        float f;
        int height2;
        super.onDraw(canvas);
        float height3 = (getHeight() / 2) + 6;
        int paddingLeft = getPaddingLeft() + this.mPaddingSize;
        getPaddingTop();
        int paddingRight = getPaddingRight() + this.mPaddingSize;
        getPaddingBottom();
        int width = (getWidth() - paddingLeft) - paddingRight;
        getHeight();
        float f2 = paddingLeft;
        float f3 = (float) (width / 20.0d);
        int height4 = (getHeight() / 2) - 40;
        this.mViewWidth = getWidth();
        canvas.save();
        canvas.drawLine(f2, height3, paddingLeft + width, height3, this.mBgPaint);
        if (this.isCenter) {
            f = f2;
            canvas.drawLine(this.mViewWidth / 2, height3, this.mMoveX, height3, this.mProgressPaint);
            if (this.isTouch) {
                canvas.drawCircle(this.mMoveX, height3 - (this.mBitmapHeight / 2), this.mThumbTouchSize, this.mThumbPaint);
            } else {
                canvas.drawCircle(this.mMoveX, height3 - (this.mBitmapHeight / 2), this.mThumbSize, this.mThumbPaint);
            }
        } else {
            float f4 = this.mMoveX;
            canvas.drawLine(f2, height3, f4, height3, this.mProgressPaint);
            f = f2;
            if (this.isTouch) {
                canvas.drawCircle(f4, height3, this.mThumbTouchSize, this.mThumbPaint);
            } else {
                canvas.drawCircle(f4, height3, this.mThumbSize, this.mThumbPaint);
            }
        }
        canvas.restore();
        for (int i = 0; i < 21; i++) {
            this.mNormalBarPaint.setStrokeWidth(6.0f);
            float f5 = (i * f3) + f;
            float f6 = 2;
            canvas.drawRect(f5 - f6, height4, f5 + f6, height - 20, this.mNormalBarPaint);
        }
        int height5 = (getHeight() / 2) - 50;
        int i2 = this.mProgress;
        if (i2 >= 0) {
            for (int i3 = 10; i3 <= this.mProgress + 10; i3++) {
                float f7 = f + (i3 * f3);
                float f8 = 2;
                canvas.drawRect(f7 - f8, height5, f7 + f8, height2 - 20, this.mSelectBarPaint);
            }
            return;
        }
        for (int i4 = i2 + 10; i4 <= 10; i4++) {
            float f9 = f + (i4 * f3);
            float f10 = 2;
            canvas.drawRect(f9 - f10, height5, f9 + f10, height2 - 20, this.mSelectBarPaint);
        }
    }

    @Override
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i2, i, i4, i3);
        this.mViewWidth = i;
        if (this.isInit) {
            this.isInit = false;
            if (this.isCenter) {
                this.mMoveX = 0.0f;
            } else {
                this.mMoveX = i / 2;
            }
        }
        if (this.isInitSetProgress) {
            setProgress(this.mProgress);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action == 0) {
            getParent().requestDisallowInterceptTouchEvent(true);
            this.isTouch = true;
            this.mDownX = motionEvent.getX();
            this.mDownY = motionEvent.getY();
            this.mMoveX = motionEvent.getX();
            if (this.isCenter) {
                calculationProgress();
            } else {
                calculationProgressLeft();
            }
            ISeekBarProgressClickListener iSeekBarProgressClickListener = this.mListener;
            if (iSeekBarProgressClickListener != null) {
                iSeekBarProgressClickListener.onSeekBarStart(this, this.mProgress);
            }
        } else if (action == 1) {
            this.isTouch = false;
            this.mMoveX = motionEvent.getX();
            if (this.isCenter) {
                calculationProgress();
            } else {
                calculationProgressLeft();
            }
            ISeekBarProgressClickListener iSeekBarProgressClickListener2 = this.mListener;
            if (iSeekBarProgressClickListener2 != null) {
                iSeekBarProgressClickListener2.onSeekBarChanged(this, this.mProgress);
                this.mListener.onSeekBarStop(this, this.mProgress);
            }
            setProgress(this.mProgress);
        } else if (action == 2) {
            this.isTouch = true;
            float x = motionEvent.getX() - this.mDownX;
            float y = motionEvent.getY() - this.mDownY;
            if (Math.abs(x) > this.mTouchSlop || Math.abs(y) > this.mTouchSlop) {
                this.mMoveX = motionEvent.getX();
                if (this.isCenter) {
                    calculationProgress();
                } else {
                    calculationProgressLeft();
                }
                ISeekBarProgressClickListener iSeekBarProgressClickListener3 = this.mListener;
                if (iSeekBarProgressClickListener3 != null) {
                    iSeekBarProgressClickListener3.onSeekBarChanged(this, this.mProgress);
                }
            }
        }
        invalidate();
        return true;
    }

    public void setBgColor(int i) {
        this.mBgColor = i;
        this.mBgPaint.setColor(i);
        invalidate();
    }

    public void setCenter(boolean z) {
        this.isCenter = z;
    }

    public void setMax(int i) {
        this.maxProgress = i;
        invalidate();
    }

    public void setProgress(int i) {
        this.mProgress = i;
        int i2 = this.mViewWidth;
        if (i2 != 0) {
            if (this.isCenter) {
                int i3 = i2 / 2;
                int paddingLeft = (i2 - this.mPaddingSize) - getPaddingLeft();
                if (this.mProgress >= 0) {
                    this.mMoveX = ((i * (paddingLeft - i3)) / this.maxProgress) + i3;
                } else {
                    int i4 = this.mPaddingSize;
                    float f = i3 - i4;
                    this.mMoveX = (((i * 1.0f) / this.maxProgress) * f) + f + i4;
                }
            } else {
                int paddingLeft2 = (i2 - this.mPaddingSize) - getPaddingLeft();
                int i5 = this.mPaddingSize;
                this.mMoveX = ((i * (paddingLeft2 - i5)) / this.maxProgress) + i5;
            }
            this.isInitSetProgress = false;
        } else {
            this.isInitSetProgress = true;
        }
        invalidate();
    }

    public void setProgressColor(int i) {
        this.mProgressColor = i;
        this.mProgressPaint.setColor(i);
        invalidate();
    }

    public void setSeekBarProgressClickListener(ISeekBarProgressClickListener iSeekBarProgressClickListener) {
        this.mListener = iSeekBarProgressClickListener;
    }

    public void setSeekBarSize(int i) {
        this.mSeekBarSize = i;
    }

    public void setThumbColor(int i) {
        this.mThumbColor = i;
        this.mThumbPaint.setColor(i);
        invalidate();
    }
}
