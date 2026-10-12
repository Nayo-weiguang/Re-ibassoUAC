package com.ibasso.volume.eq;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class ParamEqualizerView extends View {
    public static final double DEFAULT_Q = 0.75d;
    private static final double MAX_FREQ = 20000.0d;
    public static final double MAX_FREQ_VALID = 20000.0d;
    private static final double MAX_GAIN = 12.0d;
    public static final double MAX_GAIN_VALID = 12.0d;
    private static final double MIN_FREQ = 20.0d;
    public static final double MIN_FREQ_VALID = 20.0d;
    private static final double MIN_GAIN = -24.0d;
    public static final double MIN_GAIN_VALID = -24.0d;
    private static final int SAMPLE_POINTS = 500;
    private static final String TAG = "EqualizerView";
    int[] colors;
    private Paint curvePaint;
    private Paint dashPaint;
    private List<EQPoint> eqPoints;
    private Paint fillPaint;
    private List<Path> fillPaths;
    private List<Integer> freqLabels;
    private List<Integer> gainLabels;
    private RectF graphRect;
    private Paint gridPaint;
    private int height;
    private List<Path> individualPaths;
    private OnEQPointChangeListener onEQPointDataChanged;
    private Paint pointPaint;
    private Paint pointTitlePaint;
    private EQPoint selectedPoint;
    private TextPaint textPaint;
    private double totalGain;
    private Path totalResponsePath;
    private int width;

    public static class EQPoint {
        public int color;
        public FilterSetting data;
        public PointF drawPoint = new PointF();

        public EQPoint(FilterSetting filterSetting, int i) {
            this.data = filterSetting;
            this.color = i;
        }

        public int getFilterType() {
            return this.data.getFilterType();
        }

        public int getFrequency() {
            return this.data.getFrequency();
        }

        public double getGain() {
            return Math.round(this.data.getGain() * 100.0d) / 100.0d;
        }

        public int getId() {
            return this.data.getFilterIndex();
        }

        public double getQ() {
            return Math.round(this.data.getFactor() * 100.0d) / 100.0d;
        }

        public void setFilterType(int i) {
            this.data.setFilterType(i);
        }

        public void setFrequency(int i) {
            this.data.setFrequency(i);
        }

        public void setGain(double d2) {
            this.data.setGain(d2);
        }

        public void setQ(double d2) {
            this.data.setFactor(d2);
        }
    }

    public interface OnEQPointChangeListener {
        void onEQPointChanged(EQPoint eQPoint);
    }

    public ParamEqualizerView(Context context) {
        super(context);
        this.totalGain = 0.0d;
        this.eqPoints = new ArrayList();
        this.colors = new int[]{Color.argb(100, 255, 0, 0), Color.argb(100, 0, 0, 255), Color.argb(100, 0, 255, 0), Color.argb(100, 255, 0, 255), Color.argb(100, 0, 255, 255), Color.argb(100, 0, 150, 136), Color.argb(100, 255, 193, 7), Color.argb(100, 108, 58, 183), Color.argb(100, 128, 0, 128)};
        this.freqLabels = Arrays.asList(20, 50, 100, Integer.valueOf((int) ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION), Integer.valueOf((int) SAMPLE_POINTS), 1000, 2000, 5000, 10000, Integer.valueOf((int) AccessibilityNodeInfoCompat.EXTRA_DATA_TEXT_CHARACTER_LOCATION_ARG_MAX_LENGTH));
        this.gainLabels = Arrays.asList(-24, -22, -18, -14, -10, -6, -4, 0, 4, 6, 10, 12);
        init(null);
    }

    public ParamEqualizerView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.totalGain = 0.0d;
        this.eqPoints = new ArrayList();
        this.colors = new int[]{Color.argb(100, 255, 0, 0), Color.argb(100, 0, 0, 255), Color.argb(100, 0, 255, 0), Color.argb(100, 255, 0, 255), Color.argb(100, 0, 255, 255), Color.argb(100, 0, 150, 136), Color.argb(100, 255, 193, 7), Color.argb(100, 108, 58, 183), Color.argb(100, 128, 0, 128)};
        this.freqLabels = Arrays.asList(20, 50, 100, Integer.valueOf((int) ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION), Integer.valueOf((int) SAMPLE_POINTS), 1000, 2000, 5000, 10000, Integer.valueOf((int) AccessibilityNodeInfoCompat.EXTRA_DATA_TEXT_CHARACTER_LOCATION_ARG_MAX_LENGTH));
        this.gainLabels = Arrays.asList(-24, -22, -18, -14, -10, -6, -4, 0, 4, 6, 10, 12);
        init(attributeSet);
    }

    public ParamEqualizerView(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.totalGain = 0.0d;
        this.eqPoints = new ArrayList();
        this.colors = new int[]{Color.argb(100, 255, 0, 0), Color.argb(100, 0, 0, 255), Color.argb(100, 0, 255, 0), Color.argb(100, 255, 0, 255), Color.argb(100, 0, 255, 255), Color.argb(100, 0, 150, 136), Color.argb(100, 255, 193, 7), Color.argb(100, 108, 58, 183), Color.argb(100, 128, 0, 128)};
        this.freqLabels = Arrays.asList(20, 50, 100, Integer.valueOf((int) ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION), Integer.valueOf((int) SAMPLE_POINTS), 1000, 2000, 5000, 10000, Integer.valueOf((int) AccessibilityNodeInfoCompat.EXTRA_DATA_TEXT_CHARACTER_LOCATION_ARG_MAX_LENGTH));
        this.gainLabels = Arrays.asList(-24, -22, -18, -14, -10, -6, -4, 0, 4, 6, 10, 12);
        init(attributeSet);
    }

    private void calculateAndDrawCurves(Canvas canvas) {
        this.totalResponsePath.reset();
        for (Path path : this.individualPaths) {
            path.reset();
        }
        for (Path path2 : this.fillPaths) {
            path2.reset();
        }
        double[] dArr = new double[SAMPLE_POINTS];
        Arrays.fill(dArr, 0.0d);
        double[] dArr2 = new double[SAMPLE_POINTS];
        int i = 0;
        for (int i2 = 0; i2 < SAMPLE_POINTS; i2++) {
            dArr2[i2] = Math.pow(10.0d, Math.log10(20.0d) + ((i2 / 499.0d) * (Math.log10(20000.0d) - Math.log10(20.0d))));
        }
        int save = canvas.save();
        float f = 0.0f;
        canvas.clipRect(new RectF(0.0f, gainToY(12.0f), canvas.getWidth(), gainToY(-24.0f)));
        int i3 = 0;
        while (i3 < this.eqPoints.size()) {
            EQPoint eQPoint = this.eqPoints.get(i3);
            Path path3 = this.individualPaths.get(i3);
            Path path4 = this.fillPaths.get(i3);
            float freqToX = freqToX((float) dArr2[i]);
            float gainToY = gainToY(f);
            path4.moveTo(freqToX, gainToY);
            int i4 = i;
            while (i4 < SAMPLE_POINTS) {
                double d2 = dArr2[i4];
                int i5 = i4;
                double calculateFilterResponse = calculateFilterResponse(eQPoint.data, d2);
                dArr[i5] = dArr[i5] + calculateFilterResponse;
                float freqToX2 = freqToX((float) d2);
                float gainToY2 = gainToY((float) calculateFilterResponse);
                if (i5 == 0) {
                    path3.moveTo(freqToX2, gainToY2);
                } else {
                    path3.lineTo(freqToX2, gainToY2);
                }
                path4.lineTo(freqToX2, gainToY2);
                i4 = i5 + 1;
            }
            path4.lineTo(freqToX((float) dArr2[499]), gainToY);
            path4.close();
            this.curvePaint.setColor(eQPoint.color);
            canvas.drawPath(path3, this.curvePaint);
            this.fillPaint.setColor(eQPoint.color);
            canvas.drawPath(path4, this.fillPaint);
            i3++;
            i = 0;
            f = 0.0f;
        }
        for (int i6 = 0; i6 < SAMPLE_POINTS; i6++) {
            float freqToX3 = freqToX((float) dArr2[i6]);
            float gainToY3 = gainToY((float) (dArr[i6] + this.totalGain));
            if (i6 == 0) {
                this.totalResponsePath.moveTo(freqToX3, gainToY3);
            } else {
                this.totalResponsePath.lineTo(freqToX3, gainToY3);
            }
        }
        this.curvePaint.setColor(ViewCompat.MEASURED_STATE_MASK);
        this.curvePaint.setStrokeWidth(3.5f);
        canvas.drawPath(this.totalResponsePath, this.curvePaint);
        this.curvePaint.setStrokeWidth(2.5f);
        canvas.restoreToCount(save);
    }

    private double calculateFilterResponse(FilterSetting filterSetting, double d2) {
        FilterCoefficients calculateCoefficients = EQProcessor.calculateCoefficients(filterSetting);
        return Math.log10(ComplexUtils.evaluateFrequencyResponse(calculateCoefficients.f281b, calculateCoefficients.f280a, (d2 * 6.283185307179586d) / EQProcessor.SAMPLE_RATE).abs()) * 20.0d;
    }

    private void drawEQPoints(Canvas canvas) {
        for (EQPoint eQPoint : this.eqPoints) {
            this.pointPaint.setColor(eQPoint.color | ViewCompat.MEASURED_STATE_MASK);
            PointF pointF = eQPoint.drawPoint;
            canvas.drawCircle(pointF.x, pointF.y, 15.0f, this.pointPaint);
            Paint.FontMetrics fontMetrics = this.pointTitlePaint.getFontMetrics();
            String valueOf = String.valueOf(eQPoint.getId() + 1);
            PointF pointF2 = eQPoint.drawPoint;
            canvas.drawText(valueOf, pointF2.x, pointF2.y - ((fontMetrics.ascent + fontMetrics.descent) / 2.0f), this.pointTitlePaint);
            if (eQPoint == this.selectedPoint) {
                Paint paint = new Paint(this.pointPaint);
                paint.setStyle(Paint.Style.STROKE);
                paint.setColor(ViewCompat.MEASURED_STATE_MASK);
                paint.setStrokeWidth(5.0f);
                PointF pointF3 = eQPoint.drawPoint;
                canvas.drawCircle(pointF3.x, pointF3.y, 20.0f, paint);
                DecimalFormat decimalFormat = new DecimalFormat("#0.02#");
                String str = decimalFormat.format(eQPoint.getFrequency()) + "Hz, " + decimalFormat.format(eQPoint.getGain()) + "dB, Q=" + decimalFormat.format(eQPoint.getQ());
                canvas.drawText(str, Math.min(eQPoint.drawPoint.x, (this.width - this.textPaint.measureText(str)) - 10.0f), eQPoint.drawPoint.y - 20.0f, this.textPaint);
            }
        }
    }

    private void drawGrid(Canvas canvas) {
        canvas.drawRect(this.graphRect, this.gridPaint);
        gainToY(0.0f);
        for (Integer num : this.gainLabels) {
            float gainToY = gainToY(num.intValue());
            Paint paint = num.intValue() == 0 ? this.gridPaint : this.dashPaint;
            RectF rectF = this.graphRect;
            canvas.drawLine(rectF.left, gainToY, rectF.right, gainToY, paint);
            String str = num + " dB";
            canvas.drawText(str, (this.graphRect.left - this.textPaint.measureText(str)) - 10.0f, gainToY + 10.0f, this.textPaint);
        }
        for (Integer num2 : this.freqLabels) {
            float freqToX = freqToX(num2.intValue());
            RectF rectF2 = this.graphRect;
            canvas.drawLine(freqToX, rectF2.top, freqToX, rectF2.bottom, this.dashPaint);
            String formatFrequency = formatFrequency(num2.intValue());
            canvas.drawText(formatFrequency, freqToX - (this.textPaint.measureText(formatFrequency) / 2.0f), this.graphRect.bottom + 40.0f, this.textPaint);
        }
    }

    private EQPoint findNearestPoint(float f, float f2) {
        EQPoint eQPoint = null;
        float f3 = Float.MAX_VALUE;
        for (EQPoint eQPoint2 : this.eqPoints) {
            PointF pointF = eQPoint2.drawPoint;
            float f4 = pointF.x - f;
            float f5 = pointF.y - f2;
            float sqrt = (float) Math.sqrt((f4 * f4) + (f5 * f5));
            if (sqrt < 50.0f && sqrt < f3) {
                eQPoint = eQPoint2;
                f3 = sqrt;
            }
        }
        return eQPoint;
    }

    private String formatFrequency(int i) {
        if (i >= 1000) {
            return (i / 1000) + "k";
        }
        return String.valueOf(i);
    }

    private float freqToX(float f) {
        double log10 = Math.log10(f);
        double log102 = Math.log10(20.0d);
        RectF rectF = this.graphRect;
        return (float) (rectF.left + (((log10 - log102) / (Math.log10(20000.0d) - log102)) * rectF.width()));
    }

    private float gainToY(float f) {
        double d2 = 1.0d - ((f - (-24.0d)) / 36.0d);
        RectF rectF = this.graphRect;
        return (float) (rectF.top + (d2 * rectF.height()));
    }

    private void init(@Nullable AttributeSet attributeSet) {
        Paint paint = new Paint();
        this.gridPaint = paint;
        paint.setColor(-3355444);
        this.gridPaint.setStrokeWidth(1.0f);
        Paint paint2 = this.gridPaint;
        Paint.Style style = Paint.Style.STROKE;
        paint2.setStyle(style);
        this.gridPaint.setAntiAlias(true);
        Paint paint3 = new Paint();
        this.curvePaint = paint3;
        paint3.setStrokeWidth(3.0f);
        this.curvePaint.setStyle(style);
        this.curvePaint.setAntiAlias(true);
        Paint paint4 = new Paint();
        this.pointPaint = paint4;
        paint4.setStrokeWidth(5.0f);
        this.pointPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        this.pointPaint.setAntiAlias(true);
        Paint paint5 = new Paint();
        this.pointTitlePaint = paint5;
        paint5.setColor(-1);
        this.pointTitlePaint.setTextSize(24.0f);
        this.pointTitlePaint.setTextAlign(Paint.Align.CENTER);
        this.pointTitlePaint.setAntiAlias(true);
        Paint paint6 = new Paint();
        this.fillPaint = paint6;
        paint6.setStyle(Paint.Style.FILL);
        this.fillPaint.setAlpha(100);
        this.fillPaint.setAntiAlias(true);
        Paint paint7 = new Paint(this.gridPaint);
        this.dashPaint = paint7;
        paint7.setPathEffect(new DashPathEffect(new float[]{5.0f, 5.0f}, 0.0f));
        TextPaint textPaint = new TextPaint();
        this.textPaint = textPaint;
        textPaint.setColor(-12303292);
        this.textPaint.setTextSize(30.0f);
        this.textPaint.setAntiAlias(true);
        this.totalResponsePath = new Path();
        this.individualPaths = new ArrayList();
        this.fillPaths = new ArrayList();
    }

    private void updateAllPointsCoordinates() {
        if (this.graphRect == null) {
            return;
        }
        for (EQPoint eQPoint : this.eqPoints) {
            eQPoint.drawPoint.x = freqToX(eQPoint.getFrequency());
            eQPoint.drawPoint.y = gainToY((float) eQPoint.getGain());
        }
    }

    private int xToFreq(float f) {
        RectF rectF = this.graphRect;
        return (int) Math.pow(10.0d, Math.log10(20.0d) + (((f - rectF.left) / rectF.width()) * (Math.log10(20000.0d) - Math.log10(20.0d))));
    }

    private double yToGain(float f) {
        RectF rectF = this.graphRect;
        return ((1.0f - ((f - rectF.top) / rectF.height())) * 36.0d) - 24.0d;
    }

    public List<EQPoint> getEQPoints() {
        return new ArrayList(this.eqPoints);
    }

    public double getTotalGain() {
        return this.totalGain;
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.parseColor("#EBEBEB"));
        drawGrid(canvas);
        calculateAndDrawCurves(canvas);
        drawEQPoints(canvas);
    }

    @Override
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        this.width = i;
        this.height = i2;
        float f = 80;
        float f2 = 0.5f * f;
        this.graphRect = new RectF(1.5f * f, f2, this.width - (f / 2.0f), this.height - f2);
        updateAllPointsCoordinates();
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        int action = motionEvent.getAction();
        if (action == 0) {
            this.selectedPoint = findNearestPoint(x, y);
        } else if (action == 1) {
            this.selectedPoint = null;
        } else if (action == 2 && this.selectedPoint != null) {
            float max = Math.max(freqToX(20.0f), Math.min(x, freqToX(20000.0f)));
            float max2 = Math.max(gainToY(12.0f), Math.min(y, gainToY(-24.0f)));
            this.selectedPoint.setFrequency(xToFreq(max));
            this.selectedPoint.setGain(yToGain(max2));
            PointF pointF = this.selectedPoint.drawPoint;
            pointF.x = max;
            pointF.y = max2;
            invalidate();
            OnEQPointChangeListener onEQPointChangeListener = this.onEQPointDataChanged;
            if (onEQPointChangeListener != null) {
                onEQPointChangeListener.onEQPointChanged(this.selectedPoint);
            }
        }
        return true;
    }

    public void setColors(int[] iArr) {
        if (iArr != null) {
            this.colors = iArr;
        }
    }

    public void setFilterType(int i, int i2) {
        for (EQPoint eQPoint : this.eqPoints) {
            if (eQPoint.getId() == i) {
                eQPoint.setFilterType(i2);
                invalidate();
                return;
            }
        }
    }

    public void setFilters(List<FilterSetting> list) {
        this.eqPoints = new ArrayList();
        this.individualPaths.clear();
        this.fillPaths.clear();
        for (int i = 0; i < list.size(); i++) {
            this.eqPoints.add(new EQPoint(list.get(i), (this.colors[i] & ViewCompat.MEASURED_SIZE_MASK) | 1509949440));
            this.individualPaths.add(new Path());
            this.fillPaths.add(new Path());
        }
        updateAllPointsCoordinates();
        invalidate();
        Log.i("ParamEqualizerView", "setFilters: " + list.toString() + " eqPoints: " + this.eqPoints.toString() + " individualPaths: " + this.individualPaths.size() + " fillPaths: " + this.fillPaths.size() + " eqPoints.size(): " + this.eqPoints.size());
    }

    public void setOnEQPointChangeListener(OnEQPointChangeListener onEQPointChangeListener) {
        this.onEQPointDataChanged = onEQPointChangeListener;
    }

    public void setPointEnabled(int i, boolean z) {
        for (EQPoint eQPoint : this.eqPoints) {
            if (eQPoint.getId() == i) {
                invalidate();
                return;
            }
        }
    }

    public void setQ(int i, double d2) {
        for (EQPoint eQPoint : this.eqPoints) {
            if (eQPoint.getId() == i) {
                eQPoint.setQ(d2);
                invalidate();
                return;
            }
        }
    }

    public void setTotalGain(double d2) {
        double max = Math.max(-24.0d, Math.min(12.0d, d2));
        if (this.totalGain != max) {
            this.totalGain = max;
            invalidate();
        }
    }
}
