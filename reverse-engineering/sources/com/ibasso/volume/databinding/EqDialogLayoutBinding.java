package com.ibasso.volume.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.ibasso.volume.R;
public final class EqDialogLayoutBinding implements ViewBinding {
    @NonNull
    public final RadioButton eq0Rb;
    @NonNull
    public final RadioButton eq10Rb;
    @NonNull
    public final RadioButton eq1Rb;
    @NonNull
    public final RadioButton eq2Rb;
    @NonNull
    public final RadioButton eq3Rb;
    @NonNull
    public final RadioButton eq4Rb;
    @NonNull
    public final RadioButton eq5Rb;
    @NonNull
    public final RadioButton eq6Rb;
    @NonNull
    public final RadioButton eq7Rb;
    @NonNull
    public final RadioButton eq8Rb;
    @NonNull
    public final RadioButton eq9Rb;
    @NonNull
    public final TextView eqResetTv;
    @NonNull
    public final RadioGroup eqRg;
    @NonNull
    public final TextView opVoltageTips;
    @NonNull
    private final LinearLayout rootView;

    private EqDialogLayoutBinding(@NonNull LinearLayout linearLayout, @NonNull RadioButton radioButton, @NonNull RadioButton radioButton2, @NonNull RadioButton radioButton3, @NonNull RadioButton radioButton4, @NonNull RadioButton radioButton5, @NonNull RadioButton radioButton6, @NonNull RadioButton radioButton7, @NonNull RadioButton radioButton8, @NonNull RadioButton radioButton9, @NonNull RadioButton radioButton10, @NonNull RadioButton radioButton11, @NonNull TextView textView, @NonNull RadioGroup radioGroup, @NonNull TextView textView2) {
        this.rootView = linearLayout;
        this.eq0Rb = radioButton;
        this.eq10Rb = radioButton2;
        this.eq1Rb = radioButton3;
        this.eq2Rb = radioButton4;
        this.eq3Rb = radioButton5;
        this.eq4Rb = radioButton6;
        this.eq5Rb = radioButton7;
        this.eq6Rb = radioButton8;
        this.eq7Rb = radioButton9;
        this.eq8Rb = radioButton10;
        this.eq9Rb = radioButton11;
        this.eqResetTv = textView;
        this.eqRg = radioGroup;
        this.opVoltageTips = textView2;
    }

    @NonNull
    public static EqDialogLayoutBinding bind(@NonNull View view) {
        int i = R.id.eq_0_rb;
        RadioButton radioButton = (RadioButton) ViewBindings.findChildViewById(view, R.id.eq_0_rb);
        if (radioButton != null) {
            i = R.id.eq_10_rb;
            RadioButton radioButton2 = (RadioButton) ViewBindings.findChildViewById(view, R.id.eq_10_rb);
            if (radioButton2 != null) {
                i = R.id.eq_1_rb;
                RadioButton radioButton3 = (RadioButton) ViewBindings.findChildViewById(view, R.id.eq_1_rb);
                if (radioButton3 != null) {
                    i = R.id.eq_2_rb;
                    RadioButton radioButton4 = (RadioButton) ViewBindings.findChildViewById(view, R.id.eq_2_rb);
                    if (radioButton4 != null) {
                        i = R.id.eq_3_rb;
                        RadioButton radioButton5 = (RadioButton) ViewBindings.findChildViewById(view, R.id.eq_3_rb);
                        if (radioButton5 != null) {
                            i = R.id.eq_4_rb;
                            RadioButton radioButton6 = (RadioButton) ViewBindings.findChildViewById(view, R.id.eq_4_rb);
                            if (radioButton6 != null) {
                                i = R.id.eq_5_rb;
                                RadioButton radioButton7 = (RadioButton) ViewBindings.findChildViewById(view, R.id.eq_5_rb);
                                if (radioButton7 != null) {
                                    i = R.id.eq_6_rb;
                                    RadioButton radioButton8 = (RadioButton) ViewBindings.findChildViewById(view, R.id.eq_6_rb);
                                    if (radioButton8 != null) {
                                        i = R.id.eq_7_rb;
                                        RadioButton radioButton9 = (RadioButton) ViewBindings.findChildViewById(view, R.id.eq_7_rb);
                                        if (radioButton9 != null) {
                                            i = R.id.eq_8_rb;
                                            RadioButton radioButton10 = (RadioButton) ViewBindings.findChildViewById(view, R.id.eq_8_rb);
                                            if (radioButton10 != null) {
                                                i = R.id.eq_9_rb;
                                                RadioButton radioButton11 = (RadioButton) ViewBindings.findChildViewById(view, R.id.eq_9_rb);
                                                if (radioButton11 != null) {
                                                    i = R.id.eq_reset_tv;
                                                    TextView textView = (TextView) ViewBindings.findChildViewById(view, R.id.eq_reset_tv);
                                                    if (textView != null) {
                                                        i = R.id.eq_rg;
                                                        RadioGroup radioGroup = (RadioGroup) ViewBindings.findChildViewById(view, R.id.eq_rg);
                                                        if (radioGroup != null) {
                                                            i = R.id.op_voltage_tips;
                                                            TextView textView2 = (TextView) ViewBindings.findChildViewById(view, R.id.op_voltage_tips);
                                                            if (textView2 != null) {
                                                                return new EqDialogLayoutBinding((LinearLayout) view, radioButton, radioButton2, radioButton3, radioButton4, radioButton5, radioButton6, radioButton7, radioButton8, radioButton9, radioButton10, radioButton11, textView, radioGroup, textView2);
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
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i)));
    }

    @NonNull
    public static EqDialogLayoutBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static EqDialogLayoutBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z) {
        View inflate = layoutInflater.inflate(R.layout.eq_dialog_layout, viewGroup, false);
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
