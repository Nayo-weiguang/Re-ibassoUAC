package com.ibasso.music.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.SeekBar;
import androidx.appcompat.widget.AppCompatSeekBar;
public class ThirdSwitchBar extends AppCompatSeekBar implements SeekBar.OnSeekBarChangeListener {
    public int FLAG_HIGH;
    public int FLAG_LOW;
    public int FLAG_MIDDLE;
    private int newProgress;
    private SeekTouchListener touchListener;

    public interface SeekTouchListener {
        void touchEnd(SeekBar seekBar);

        void touchMiddle(SeekBar seekBar);

        void touchTop(SeekBar seekBar);
    }

    public ThirdSwitchBar(Context context) {
        super(context);
        this.FLAG_HIGH = 2;
        this.FLAG_MIDDLE = 1;
        this.FLAG_LOW = 0;
        this.newProgress = 100;
        setOnSeekBarChangeListener(this);
        setProgress(100);
    }

    public ThirdSwitchBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.FLAG_HIGH = 2;
        this.FLAG_MIDDLE = 1;
        this.FLAG_LOW = 0;
        this.newProgress = 100;
        setOnSeekBarChangeListener(this);
        setProgress(100);
    }

    public ThirdSwitchBar(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.FLAG_HIGH = 2;
        this.FLAG_MIDDLE = 1;
        this.FLAG_LOW = 0;
        this.newProgress = 100;
        setOnSeekBarChangeListener(this);
        setProgress(0);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
        this.newProgress = i;
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        int i = this.newProgress;
        if (i < 30) {
            this.newProgress = 0;
            setProgress(0);
            SeekTouchListener seekTouchListener = this.touchListener;
            if (seekTouchListener != null) {
                seekTouchListener.touchTop(seekBar);
            }
        } else if (i >= 70) {
            this.newProgress = 100;
            setProgress(100);
            SeekTouchListener seekTouchListener2 = this.touchListener;
            if (seekTouchListener2 != null) {
                seekTouchListener2.touchEnd(seekBar);
            }
        } else {
            this.newProgress = 50;
            setProgress(50);
            SeekTouchListener seekTouchListener3 = this.touchListener;
            if (seekTouchListener3 != null) {
                seekTouchListener3.touchMiddle(seekBar);
            }
        }
    }

    public void setSeekTouchListener(SeekTouchListener seekTouchListener) {
        this.touchListener = seekTouchListener;
    }

    public void setStatus(int i) {
        if (i == this.FLAG_HIGH) {
            setProgress(100);
        } else if (i == this.FLAG_MIDDLE) {
            setProgress(50);
        } else if (i == this.FLAG_LOW) {
            setProgress(0);
        } else {
            setProgress(0);
        }
    }
}
