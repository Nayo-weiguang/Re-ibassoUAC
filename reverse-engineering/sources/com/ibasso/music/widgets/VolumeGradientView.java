package com.ibasso.music.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.Nullable;
import com.ibasso.volume.R;
import okhttp3.HttpUrl;
public class VolumeGradientView extends View {
    private Paint mCenterPaint;
    private Paint mGradientPaint;
    private int mHeigth;
    private int mIncreaseVolume;
    private int mMaxProgress;
    private OnProgressChangedListener mOnProgressChangedListener;
    private float mPositionY;
    private int mProgressValue;
    private Shader mRadialGradient;
    private Paint mTextPaint;
    private int mTextSize;
    private int mUnitCellValue;
    private int mWidth;

    public interface OnProgressChangedListener {
        void progressChanged(int i);
    }

    public VolumeGradientView(Context context) {
        this(context, null);
    }

    public VolumeGradientView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mGradientPaint = null;
        this.mRadialGradient = null;
        this.mCenterPaint = null;
        this.mTextPaint = null;
        this.mProgressValue = -1;
        this.mTextSize = 160;
        this.mMaxProgress = 150;
        initPaint(context);
    }

    private void initPaint(Context context) {
        this.mTextSize = getResources().getDimensionPixelSize(R.dimen.sp_38);
        Paint paint = new Paint();
        this.mGradientPaint = paint;
        paint.setAntiAlias(true);
        this.mGradientPaint.setDither(true);
        Paint paint2 = new Paint();
        this.mCenterPaint = paint2;
        paint2.setColor(Color.parseColor("#DFB678"));
        this.mCenterPaint.setAntiAlias(true);
        this.mCenterPaint.setDither(true);
        Paint paint3 = new Paint();
        this.mTextPaint = paint3;
        paint3.setColor(Color.parseColor("#ffffff"));
        this.mTextPaint.setAntiAlias(true);
        this.mTextPaint.setDither(true);
    }

    private void updateUnitCellValue() {
        this.mUnitCellValue = getWidth() / this.mMaxProgress;
        Log.i("Touch", "mUnitCellValue:" + this.mUnitCellValue);
    }

    public void addProgressValue() {
        OnProgressChangedListener onProgressChangedListener = this.mOnProgressChangedListener;
        if (onProgressChangedListener != null) {
            onProgressChangedListener.progressChanged(1);
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        RadialGradient radialGradient = new RadialGradient(getWidth() / 2, getWidth() / 2, getWidth() / 2, new int[]{-1725753323, -435708756}, new float[]{0.0f, 1.0f}, Shader.TileMode.REPEAT);
        this.mRadialGradient = radialGradient;
        this.mGradientPaint.setShader(radialGradient);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, (((getWidth() / 2) - 150) * (this.mProgressValue / this.mMaxProgress)) + 150.0f, this.mGradientPaint);
        Rect rect = new Rect();
        this.mTextPaint.setTextSize(this.mTextSize);
        if (this.mProgressValue == -1) {
            this.mTextPaint.setColor(Color.parseColor("#ee0000"));
            this.mProgressValue = 0;
        } else {
            this.mTextPaint.setColor(Color.parseColor("#ffffff"));
        }
        String str = HttpUrl.FRAGMENT_ENCODE_SET + this.mProgressValue;
        this.mTextPaint.getTextBounds(str, 0, str.length(), rect);
        canvas.drawText(str, (getWidth() - this.mTextPaint.measureText(str)) / 2.0f, (getHeight() + rect.height()) / 2, this.mTextPaint);
    }

    @Override
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
    }

    @Override
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        updateUnitCellValue();
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        OnProgressChangedListener onProgressChangedListener;
        int action = motionEvent.getAction();
        if (action == 0) {
            this.mPositionY = motionEvent.getY();
            this.mIncreaseVolume = -1;
            Log.i("Touch", "positionY:" + this.mPositionY);
            return true;
        }
        if (action == 2) {
            float y = motionEvent.getY();
            int i = (int) ((y - this.mPositionY) / this.mUnitCellValue);
            int i2 = this.mIncreaseVolume;
            if (i2 == -1) {
                this.mIncreaseVolume = i;
            } else {
                int i3 = i2 - i;
                Log.i("Touch", "lastPositionY:" + y + " positionY:" + this.mPositionY + " move:" + (y - this.mPositionY) + " diff:" + i3 + " increase:" + i + " mIncreaseVolume:" + this.mIncreaseVolume);
                this.mIncreaseVolume = i;
                this.mProgressValue = this.mProgressValue + i3;
                if (i3 != 0 && (onProgressChangedListener = this.mOnProgressChangedListener) != null) {
                    onProgressChangedListener.progressChanged(i3);
                }
            }
        }
        return super.onTouchEvent(motionEvent);
    }

    public void setMaxProgress(int i) {
        this.mMaxProgress = i;
        updateUnitCellValue();
    }

    public void setOnProgressChangedListener(OnProgressChangedListener onProgressChangedListener) {
        this.mOnProgressChangedListener = onProgressChangedListener;
    }

    public void setProgressValue(int i) {
        this.mProgressValue = i;
        invalidate();
    }

    public void setTextSize(int i) {
        this.mTextSize = i;
        invalidate();
    }

    public void subtractProgressValue() {
        OnProgressChangedListener onProgressChangedListener = this.mOnProgressChangedListener;
        if (onProgressChangedListener != null) {
            onProgressChangedListener.progressChanged(-1);
        }
    }
}
