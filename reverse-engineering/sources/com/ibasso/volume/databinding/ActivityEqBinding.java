package com.ibasso.volume.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.ibasso.volume.R;
import com.ibasso.volume.eq.ParamEqualizerView;
public final class ActivityEqBinding implements ViewBinding {
    @NonNull
    public final LinearLayout controlsLayout;
    @NonNull
    public final Spinner eqPointSelector;
    @NonNull
    public final ParamEqualizerView equalizerView;
    @NonNull
    public final Spinner filterTypeSelector;
    @NonNull
    public final SeekBar qFactorSeekBar;
    @NonNull
    public final TextView qFactorValueText;
    @NonNull
    private final FrameLayout rootView;
    @NonNull
    public final TextView selectPointTv;

    private ActivityEqBinding(@NonNull FrameLayout frameLayout, @NonNull LinearLayout linearLayout, @NonNull Spinner spinner, @NonNull ParamEqualizerView paramEqualizerView, @NonNull Spinner spinner2, @NonNull SeekBar seekBar, @NonNull TextView textView, @NonNull TextView textView2) {
        this.rootView = frameLayout;
        this.controlsLayout = linearLayout;
        this.eqPointSelector = spinner;
        this.equalizerView = paramEqualizerView;
        this.filterTypeSelector = spinner2;
        this.qFactorSeekBar = seekBar;
        this.qFactorValueText = textView;
        this.selectPointTv = textView2;
    }

    @NonNull
    public static ActivityEqBinding bind(@NonNull View view) {
        int i = R.id.controlsLayout;
        LinearLayout linearLayout = (LinearLayout) ViewBindings.findChildViewById(view, R.id.controlsLayout);
        if (linearLayout != null) {
            i = R.id.eqPointSelector;
            Spinner spinner = (Spinner) ViewBindings.findChildViewById(view, R.id.eqPointSelector);
            if (spinner != null) {
                i = R.id.equalizerView;
                ParamEqualizerView paramEqualizerView = (ParamEqualizerView) ViewBindings.findChildViewById(view, R.id.equalizerView);
                if (paramEqualizerView != null) {
                    i = R.id.filterTypeSelector;
                    Spinner spinner2 = (Spinner) ViewBindings.findChildViewById(view, R.id.filterTypeSelector);
                    if (spinner2 != null) {
                        i = R.id.qFactorSeekBar;
                        SeekBar seekBar = (SeekBar) ViewBindings.findChildViewById(view, R.id.qFactorSeekBar);
                        if (seekBar != null) {
                            i = R.id.qFactorValueText;
                            TextView textView = (TextView) ViewBindings.findChildViewById(view, R.id.qFactorValueText);
                            if (textView != null) {
                                i = R.id.selectPointTv;
                                TextView textView2 = (TextView) ViewBindings.findChildViewById(view, R.id.selectPointTv);
                                if (textView2 != null) {
                                    return new ActivityEqBinding((FrameLayout) view, linearLayout, spinner, paramEqualizerView, spinner2, seekBar, textView, textView2);
                                }
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i)));
    }

    @NonNull
    public static ActivityEqBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static ActivityEqBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z) {
        View inflate = layoutInflater.inflate(R.layout.activity_eq, viewGroup, false);
        if (z) {
            viewGroup.addView(inflate);
        }
        return bind(inflate);
    }

    @Override
    @NonNull
    public FrameLayout getRoot() {
        return this.rootView;
    }
}
