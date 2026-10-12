package com.ibasso.music.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import androidx.core.internal.view.SupportMenu;
import com.ibasso.volume.R;
public class BalanceView2 extends View {
    private int mExampleColor;
    private float mExampleDimension;
    private Drawable mExampleDrawable;
    private String mExampleString;
    private Paint mNormalBarPaint;
    private Paint mSelectBarPaint;
    private float mTextHeight;
    private TextPaint mTextPaint;
    private float mTextWidth;
    private int maxProgress;
    private int progress;

    public BalanceView2(Context context) {
        super(context);
        this.mExampleColor = SupportMenu.CATEGORY_MASK;
        this.mExampleDimension = 0.0f;
        this.progress = 10;
        init(null, 0);
    }

    public BalanceView2(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mExampleColor = SupportMenu.CATEGORY_MASK;
        this.mExampleDimension = 0.0f;
        this.progress = 10;
        init(attributeSet, 0);
    }

    public BalanceView2(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mExampleColor = SupportMenu.CATEGORY_MASK;
        this.mExampleDimension = 0.0f;
        this.progress = 10;
        init(attributeSet, i);
    }

    private void init(AttributeSet attributeSet, int i) {
        TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R.styleable.BalanceView2, i, 0);
        this.mExampleString = obtainStyledAttributes.getString(3);
        this.mExampleColor = obtainStyledAttributes.getColor(0, this.mExampleColor);
        this.maxProgress = 21;
        this.mExampleDimension = obtainStyledAttributes.getDimension(1, this.mExampleDimension);
        if (obtainStyledAttributes.hasValue(2)) {
            Drawable drawable = obtainStyledAttributes.getDrawable(2);
            this.mExampleDrawable = drawable;
            drawable.setCallback(this);
        }
        obtainStyledAttributes.recycle();
        TextPaint textPaint = new TextPaint();
        this.mTextPaint = textPaint;
        textPaint.setFlags(1);
        this.mTextPaint.setTextAlign(Paint.Align.LEFT);
        Paint paint = new Paint();
        this.mNormalBarPaint = paint;
        paint.setColor(-1);
        Paint paint2 = this.mNormalBarPaint;
        Paint.Style style = Paint.Style.FILL;
        paint2.setStyle(style);
        Paint paint3 = new Paint();
        this.mSelectBarPaint = paint3;
        paint3.setColor(-16776961);
        this.mSelectBarPaint.setStyle(style);
        invalidateTextPaintAndMeasurements();
    }

    private void invalidateTextPaintAndMeasurements() {
        this.mTextPaint.setTextSize(this.mExampleDimension);
        this.mTextPaint.setColor(this.mExampleColor);
        this.mTextWidth = this.mTextPaint.measureText(this.mExampleString);
        this.mTextHeight = this.mTextPaint.getFontMetrics().bottom;
    }

    public int getExampleColor() {
        return this.mExampleColor;
    }

    public float getExampleDimension() {
        return this.mExampleDimension;
    }

    public Drawable getExampleDrawable() {
        return this.mExampleDrawable;
    }

    public String getExampleString() {
        return this.mExampleString;
    }

    @Override
    public void onDraw(Canvas canvas) {
        int height;
        int i;
        int i2;
        int height2;
        int i3;
        super.onDraw(canvas);
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();
        int width = (getWidth() - paddingLeft) - paddingRight;
        int height3 = (getHeight() - paddingTop) - paddingBottom;
        float f = paddingLeft;
        canvas.drawRect(f, ((getHeight() - 6.0f) / 2.0f) + 20.0f, width + f, ((getHeight() + 6.0f) / 2.0f) + 20.0f, this.mNormalBarPaint);
        float f2 = (float) (width / (this.maxProgress - 1.0d));
        int height4 = (getHeight() / 2) - 40;
        int i4 = 0;
        while (true) {
            i = this.maxProgress;
            i2 = 5;
            if (i4 >= i) {
                break;
            }
            float f3 = (i4 * f2) + f;
            float f4 = 5;
            canvas.drawRect(f3 - f4, height4, f3 + f4, height - 20, this.mNormalBarPaint);
            i4++;
        }
        int i5 = i / 2;
        int height5 = (getHeight() / 2) - 50;
        int i6 = this.progress;
        if (i6 <= i5) {
            while (i6 <= i5) {
                float f5 = (i6 * f2) + f;
                float f6 = i2;
                canvas.drawRect(f5 - f6, height5, f5 + f6, height2 - 20, this.mSelectBarPaint);
                i6++;
                i2 = 5;
                height3 = height3;
            }
            i3 = height3;
        } else {
            i3 = height3;
            while (i5 < this.progress) {
                float f7 = (i5 * f2) + f;
                float f8 = 5;
                canvas.drawRect(f7 - f8, height5, f7 + f8, height2 - 20, this.mSelectBarPaint);
                i5++;
            }
        }
        Drawable drawable = this.mExampleDrawable;
        if (drawable != null) {
            drawable.setBounds(paddingLeft, paddingTop, width + paddingLeft, paddingTop + i3);
        }
    }

    public void setExampleColor(int i) {
        this.mExampleColor = i;
        invalidateTextPaintAndMeasurements();
    }

    public void setExampleDimension(float f) {
        this.mExampleDimension = f;
        invalidateTextPaintAndMeasurements();
    }

    public void setExampleDrawable(Drawable drawable) {
        this.mExampleDrawable = drawable;
    }

    public void setExampleString(String str) {
        this.mExampleString = str;
        invalidateTextPaintAndMeasurements();
    }
}
