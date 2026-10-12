package com.ibasso.music.widgets;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import androidx.constraintlayout.core.widgets.analyzer.BasicMeasure;
import com.ibasso.volume.R;
import java.util.Arrays;
import kotlin.Unit;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.math.MathKt;
import kotlin.ranges.RangesKt;
import okhttp3.HttpUrl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
@SourceDebugExtension({"SMAP\nGainSeekBar.kt\nKotlin\n*S Kotlin\n*F\n+ 1 GainSeekBar.kt\ncom/ibasso/music/widgets/GainSeekBar\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,307:1\n1#2:308\n*E\n"})
public final class GainSeekBar extends View {
    private float animatedThumbScale;
    private float bottomPadding;
    private final float density;
    private int gain;
    private boolean isDragging;
    private int majorLabelColor;
    private float majorLabelGap;
    @NotNull
    private final Paint majorLabelPaint;
    private float majorLabelTextSize;
    private int majorTickInterval;
    private float majorTickLen;
    private int maxValue;
    private int minValue;
    private float minorTickLen;
    @Nullable
    private Function1<? super Integer, Unit> onGainChangeListener;
    private float sidePadding;
    private int step;
    @Nullable
    private ValueAnimator thumbAnimator;
    private int thumbBorderColor;
    @NotNull
    private final Paint thumbBorderPaint;
    private int thumbColor;
    @NotNull
    private final Paint thumbPaint;
    private float thumbRadius;
    private int tickColorMajor;
    private int tickColorMinor;
    @NotNull
    private final Paint tickPaint;
    private float titleBaselineY;
    @NotNull
    private String titleFormat;
    @NotNull
    private final Paint titlePaint;
    private int titleTextColor;
    private float titleTextSize;
    private int trackColorActive;
    private int trackColorInactive;
    private float trackHeight;
    @NotNull
    private final Paint trackPaint;
    @NotNull
    private final RectF trackRect;
    private float trackY;

    public static final class SavedState extends View.BaseSavedState {
        @NotNull
        public static final CREATOR CREATOR = new CREATOR(null);
        private int gain;

        public static final class CREATOR implements Parcelable.Creator<SavedState> {
            private CREATOR() {
            }

            public CREATOR(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            @Override
            @NotNull
            public SavedState createFromParcel(@NotNull Parcel parcel) {
                Intrinsics.checkNotNullParameter(parcel, "parcel");
                return new SavedState(parcel);
            }

            @Override
            @NotNull
            public SavedState[] newArray(int i) {
                return new SavedState[i];
            }
        }

        public SavedState(@NotNull Parcel parcel) {
            super(parcel);
            Intrinsics.checkNotNullParameter(parcel, "parcel");
            this.gain = parcel.readInt();
        }

        public SavedState(@Nullable Parcelable parcelable) {
            super(parcelable);
        }

        public final int getGain() {
            return this.gain;
        }

        public final void setGain(int i) {
            this.gain = i;
        }

        @Override
        public void writeToParcel(@NotNull Parcel out, int i) {
            Intrinsics.checkNotNullParameter(out, "out");
            super.writeToParcel(out, i);
            out.writeInt(this.gain);
        }
    }

    @JvmOverloads
    public GainSeekBar(@NotNull Context context) {
        this(context, null, 0, 6, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    @JvmOverloads
    public GainSeekBar(@NotNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0, 4, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    @JvmOverloads
    public GainSeekBar(@NotNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        Intrinsics.checkNotNullParameter(context, "context");
        this.minValue = -24;
        this.maxValue = 12;
        this.step = 1;
        this.majorTickInterval = 6;
        this.titleFormat = "Total Gain: %1$s dB";
        this.density = getResources().getDisplayMetrics().density;
        this.trackHeight = dp(4.0f);
        this.trackColorInactive = Color.parseColor("#33131313");
        this.trackColorActive = Color.parseColor("#1D9E75");
        this.thumbRadius = dp(9.0f);
        this.thumbColor = Color.parseColor("#1D9E75");
        this.thumbBorderColor = -1;
        this.tickColorMinor = Color.parseColor("#4D131313");
        this.tickColorMajor = Color.parseColor("#99131313");
        this.majorLabelColor = Color.parseColor("#99131313");
        this.titleTextColor = Color.parseColor("#000000");
        this.titleTextSize = dp(14.0f);
        this.majorLabelTextSize = dp(11.0f);
        this.sidePadding = dp(20.0f);
        this.titleBaselineY = dp(24.0f);
        this.trackY = dp(42.0f);
        this.minorTickLen = dp(6.0f);
        this.majorTickLen = dp(14.0f);
        this.majorLabelGap = dp(14.0f);
        this.bottomPadding = dp(10.0f);
        Paint paint = new Paint(1);
        Paint.Style style = Paint.Style.FILL;
        paint.setStyle(style);
        this.trackPaint = paint;
        Paint paint2 = new Paint(1);
        paint2.setStyle(style);
        this.thumbPaint = paint2;
        Paint paint3 = new Paint(1);
        paint3.setStyle(Paint.Style.STROKE);
        paint3.setStrokeWidth(dp(2.0f));
        this.thumbBorderPaint = paint3;
        Paint paint4 = new Paint(1);
        paint4.setStrokeWidth(dp(1.5f));
        this.tickPaint = paint4;
        Paint paint5 = new Paint(1);
        paint5.setTextAlign(Paint.Align.CENTER);
        this.majorLabelPaint = paint5;
        Paint paint6 = new Paint(1);
        paint6.setTextAlign(Paint.Align.LEFT);
        this.titlePaint = paint6;
        this.trackRect = new RectF();
        this.animatedThumbScale = 1.0f;
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.GainSeekBar, i, 0);
            Intrinsics.checkNotNullExpressionValue(obtainStyledAttributes, "context.obtainStyledAttr…SeekBar, defStyleAttr, 0)");
            try {
                setMinValue(obtainStyledAttributes.getInt(2, this.minValue));
                setMaxValue(obtainStyledAttributes.getInt(1, this.maxValue));
                setStep(obtainStyledAttributes.getInt(3, this.step));
                setMajorTickInterval(obtainStyledAttributes.getInt(0, this.majorTickInterval));
                setGain(obtainStyledAttributes.getInt(5, this.gain));
                String fmt = obtainStyledAttributes.getString(4);
                if (fmt != null) {
                    Intrinsics.checkNotNullExpressionValue(fmt, "fmt");
                    setTitleFormat(fmt);
                    Unit unit = Unit.INSTANCE;
                }
            } finally {
                obtainStyledAttributes.recycle();
            }
        }
        paint6.setTextSize(this.titleTextSize);
        paint6.setColor(this.titleTextColor);
        paint5.setTextSize(this.majorLabelTextSize);
        paint5.setColor(this.majorLabelColor);
    }

    public GainSeekBar(Context context, AttributeSet attributeSet, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i2 & 2) != 0 ? null : attributeSet, (i2 & 4) != 0 ? 0 : i);
    }

    private final void animateThumb(float f) {
        ValueAnimator valueAnimator = this.thumbAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        ValueAnimator ofFloat = ValueAnimator.ofFloat(this.animatedThumbScale, f);
        ofFloat.setDuration(120L);
        ofFloat.setInterpolator(new DecelerateInterpolator());
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                GainSeekBar.animateThumb$lambda$9$lambda$8(GainSeekBar.this, valueAnimator2);
            }
        });
        ofFloat.start();
        this.thumbAnimator = ofFloat;
    }

    public static final void animateThumb$lambda$9$lambda$8(GainSeekBar this$0, ValueAnimator it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(it, "it");
        Object animatedValue = it.getAnimatedValue();
        Intrinsics.checkNotNull(animatedValue, "null cannot be cast to non-null type kotlin.Float");
        this$0.animatedThumbScale = ((Float) animatedValue).floatValue();
        this$0.invalidate();
    }

    private final float dp(float f) {
        return f * this.density;
    }

    private final float valueToX(int i) {
        int i2 = this.minValue;
        float f = (i - i2) / (this.maxValue - i2);
        RectF rectF = this.trackRect;
        return rectF.left + (f * rectF.width());
    }

    private final int xToValue(float f) {
        RectF rectF = this.trackRect;
        float coerceIn = RangesKt.coerceIn((f - rectF.left) / rectF.width(), 0.0f, 1.0f);
        int i = this.minValue;
        return RangesKt.coerceIn(MathKt.roundToInt((i + (coerceIn * (this.maxValue - i))) / this.step) * this.step, this.minValue, this.maxValue);
    }

    public final int getGain() {
        return this.gain;
    }

    public final int getMajorTickInterval() {
        return this.majorTickInterval;
    }

    public final int getMaxValue() {
        return this.maxValue;
    }

    public final int getMinValue() {
        return this.minValue;
    }

    public final int getStep() {
        return this.step;
    }

    @NotNull
    public final String getTitleFormat() {
        return this.titleFormat;
    }

    @Override
    public void onDraw(@NotNull Canvas canvas) {
        String valueOf;
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        super.onDraw(canvas);
        String str = this.gain > 0 ? "+" : HttpUrl.FRAGMENT_ENCODE_SET;
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        String str2 = this.titleFormat;
        String format = String.format(str2, Arrays.copyOf(new Object[]{str + this.gain}, 1));
        Intrinsics.checkNotNullExpressionValue(format, "format(format, *args)");
        canvas.drawText(format, this.sidePadding - ((float) 10), this.titleBaselineY, this.titlePaint);
        this.trackPaint.setColor(this.trackColorInactive);
        RectF rectF = this.trackRect;
        float f = this.trackHeight;
        canvas.drawRoundRect(rectF, f / 2.0f, f / 2.0f, this.trackPaint);
        float valueToX = valueToX(RangesKt.coerceIn(0, this.minValue, this.maxValue));
        float valueToX2 = valueToX(this.gain);
        RectF rectF2 = new RectF(Math.min(valueToX, valueToX2), this.trackRect.top, Math.max(valueToX, valueToX2), this.trackRect.bottom);
        if (rectF2.width() > 0.0f) {
            this.trackPaint.setColor(this.trackColorActive);
            float f2 = this.trackHeight;
            canvas.drawRoundRect(rectF2, f2 / 2.0f, f2 / 2.0f, this.trackPaint);
        }
        float dp = this.trackRect.bottom + dp(2.0f);
        int i = this.minValue;
        while (i <= this.maxValue) {
            float valueToX3 = valueToX(i);
            boolean z = i % this.majorTickInterval == 0;
            this.tickPaint.setColor(z ? this.tickColorMajor : this.tickColorMinor);
            Canvas canvas2 = canvas;
            canvas2.drawLine(valueToX3, dp, valueToX3, dp + (z ? this.majorTickLen : this.minorTickLen), this.tickPaint);
            if (z) {
                if (i > 0) {
                    StringBuilder sb = new StringBuilder();
                    sb.append('+');
                    sb.append(i);
                    valueOf = sb.toString();
                } else {
                    valueOf = String.valueOf(i);
                }
                canvas2.drawText(valueOf, valueToX3, this.majorTickLen + dp + this.majorLabelGap, this.majorLabelPaint);
            }
            i += this.step;
            canvas = canvas2;
        }
        Canvas canvas3 = canvas;
        float valueToX4 = valueToX(this.gain);
        float f3 = this.thumbRadius * this.animatedThumbScale;
        this.thumbPaint.setColor(this.thumbColor);
        canvas3.drawCircle(valueToX4, this.trackY, f3, this.thumbPaint);
        this.thumbBorderPaint.setColor(this.thumbBorderColor);
        canvas3.drawCircle(valueToX4, this.trackY, f3, this.thumbBorderPaint);
    }

    @Override
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, View.MeasureSpec.makeMeasureSpec(View.resolveSize((int) (this.trackY + this.majorTickLen + this.majorLabelGap + this.majorLabelTextSize + this.bottomPadding), i2), BasicMeasure.EXACTLY));
    }

    @Override
    public void onRestoreInstanceState(@Nullable Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        setGain(savedState.getGain());
    }

    @Override
    @Nullable
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.setGain(this.gain);
        return savedState;
    }

    @Override
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        this.trackRect.set(this.sidePadding, this.trackY - (this.trackHeight / 2.0f), getWidth() - this.sidePadding, this.trackY + (this.trackHeight / 2.0f));
    }

    @Override
    public boolean onTouchEvent(@org.jetbrains.annotations.NotNull android.view.MotionEvent r4) {
        throw new UnsupportedOperationException("Method not decompiled: com.ibasso.music.widgets.GainSeekBar.onTouchEvent(android.view.MotionEvent):boolean");
    }

    public final void setGain(int i) {
        int coerceIn = RangesKt.coerceIn(i, this.minValue, this.maxValue);
        int i2 = this.minValue;
        int roundToInt = i2 + (MathKt.roundToInt((coerceIn - i2) / this.step) * this.step);
        if (this.gain != roundToInt) {
            this.gain = roundToInt;
            Function1<? super Integer, Unit> function1 = this.onGainChangeListener;
            if (function1 != null) {
                function1.invoke(Integer.valueOf(roundToInt));
            }
        }
        invalidate();
    }

    public final void setMajorTickInterval(int i) {
        this.majorTickInterval = RangesKt.coerceAtLeast(i, 1);
        invalidate();
    }

    public final void setMaxValue(int i) {
        this.maxValue = i;
        invalidate();
    }

    public final void setMinValue(int i) {
        this.minValue = i;
        invalidate();
    }

    public final void setOnGainChangeListener(@NotNull Function1<? super Integer, Unit> listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.onGainChangeListener = listener;
    }

    public final void setStep(int i) {
        this.step = RangesKt.coerceAtLeast(i, 1);
        invalidate();
    }

    public final void setTitleFormat(@NotNull String value) {
        Intrinsics.checkNotNullParameter(value, "value");
        this.titleFormat = value;
        invalidate();
    }
}
