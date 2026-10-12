package com.ibasso.volume.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.ibasso.volume.R;
public final class FragmentParametricEqualizerSectionBinding implements ViewBinding {
    @NonNull
    public final TextView filterFactorTitle;
    @NonNull
    public final TextView filterFactorValue;
    @NonNull
    public final TextView filterFrequencyTitle;
    @NonNull
    public final TextView filterFrequencyValue;
    @NonNull
    public final TextView filterGainTitle;
    @NonNull
    public final TextView filterGainValue;
    @NonNull
    public final Switch filterSectionSwitch;
    @NonNull
    public final TextView filterSectionSwitchOffText;
    @NonNull
    public final TextView filterSectionSwitchOnText;
    @NonNull
    public final TextView filterSwitchTitle;
    @NonNull
    public final TextView filterTypeTitle;
    @NonNull
    public final TextView filterTypeValue;
    @NonNull
    public final LinearLayout layoutRoot;
    @NonNull
    private final LinearLayout rootView;

    private FragmentParametricEqualizerSectionBinding(@NonNull LinearLayout linearLayout, @NonNull TextView textView, @NonNull TextView textView2, @NonNull TextView textView3, @NonNull TextView textView4, @NonNull TextView textView5, @NonNull TextView textView6, @NonNull Switch r8, @NonNull TextView textView7, @NonNull TextView textView8, @NonNull TextView textView9, @NonNull TextView textView10, @NonNull TextView textView11, @NonNull LinearLayout linearLayout2) {
        this.rootView = linearLayout;
        this.filterFactorTitle = textView;
        this.filterFactorValue = textView2;
        this.filterFrequencyTitle = textView3;
        this.filterFrequencyValue = textView4;
        this.filterGainTitle = textView5;
        this.filterGainValue = textView6;
        this.filterSectionSwitch = r8;
        this.filterSectionSwitchOffText = textView7;
        this.filterSectionSwitchOnText = textView8;
        this.filterSwitchTitle = textView9;
        this.filterTypeTitle = textView10;
        this.filterTypeValue = textView11;
        this.layoutRoot = linearLayout2;
    }

    @NonNull
    public static FragmentParametricEqualizerSectionBinding bind(@NonNull View view) {
        int i = R.id.filter_factor_title;
        TextView textView = (TextView) ViewBindings.findChildViewById(view, R.id.filter_factor_title);
        if (textView != null) {
            i = R.id.filter_factor_value;
            TextView textView2 = (TextView) ViewBindings.findChildViewById(view, R.id.filter_factor_value);
            if (textView2 != null) {
                i = R.id.filter_frequency_title;
                TextView textView3 = (TextView) ViewBindings.findChildViewById(view, R.id.filter_frequency_title);
                if (textView3 != null) {
                    i = R.id.filter_frequency_value;
                    TextView textView4 = (TextView) ViewBindings.findChildViewById(view, R.id.filter_frequency_value);
                    if (textView4 != null) {
                        i = R.id.filter_gain_title;
                        TextView textView5 = (TextView) ViewBindings.findChildViewById(view, R.id.filter_gain_title);
                        if (textView5 != null) {
                            i = R.id.filter_gain_value;
                            TextView textView6 = (TextView) ViewBindings.findChildViewById(view, R.id.filter_gain_value);
                            if (textView6 != null) {
                                i = R.id.filter_section_switch;
                                Switch r11 = (Switch) ViewBindings.findChildViewById(view, R.id.filter_section_switch);
                                if (r11 != null) {
                                    i = R.id.filter_section_switch_off_text;
                                    TextView textView7 = (TextView) ViewBindings.findChildViewById(view, R.id.filter_section_switch_off_text);
                                    if (textView7 != null) {
                                        i = R.id.filter_section_switch_on_text;
                                        TextView textView8 = (TextView) ViewBindings.findChildViewById(view, R.id.filter_section_switch_on_text);
                                        if (textView8 != null) {
                                            i = R.id.filter_switch_title;
                                            TextView textView9 = (TextView) ViewBindings.findChildViewById(view, R.id.filter_switch_title);
                                            if (textView9 != null) {
                                                i = R.id.filter_type_title;
                                                TextView textView10 = (TextView) ViewBindings.findChildViewById(view, R.id.filter_type_title);
                                                if (textView10 != null) {
                                                    i = R.id.filter_type_value;
                                                    TextView textView11 = (TextView) ViewBindings.findChildViewById(view, R.id.filter_type_value);
                                                    if (textView11 != null) {
                                                        LinearLayout linearLayout = (LinearLayout) view;
                                                        return new FragmentParametricEqualizerSectionBinding(linearLayout, textView, textView2, textView3, textView4, textView5, textView6, r11, textView7, textView8, textView9, textView10, textView11, linearLayout);
                                                    }
                                                }
                                            }
                                        }
                                    }
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
    public static FragmentParametricEqualizerSectionBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static FragmentParametricEqualizerSectionBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z) {
        View inflate = layoutInflater.inflate(R.layout.fragment_parametric_equalizer_section, viewGroup, false);
        if (z) {
            viewGroup.addView(inflate);
        }
        return bind(inflate);
    }

    @Override
    @NonNull
    public LinearLayout getRoot() {
        return this.rootView;
    }
}
