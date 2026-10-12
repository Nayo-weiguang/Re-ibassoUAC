package com.ibasso.volume.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.ibasso.volume.R;
public final class FilterFactorDialogLayoutBinding implements ViewBinding {
    @NonNull
    public final Button cancelActionBt;
    @NonNull
    public final Button confirmActionBt;
    @NonNull
    public final TextView filterFactorTips;
    @NonNull
    public final EditText filterFactorValueEt;
    @NonNull
    private final LinearLayout rootView;

    private FilterFactorDialogLayoutBinding(@NonNull LinearLayout linearLayout, @NonNull Button button, @NonNull Button button2, @NonNull TextView textView, @NonNull EditText editText) {
        this.rootView = linearLayout;
        this.cancelActionBt = button;
        this.confirmActionBt = button2;
        this.filterFactorTips = textView;
        this.filterFactorValueEt = editText;
    }

    @NonNull
    public static FilterFactorDialogLayoutBinding bind(@NonNull View view) {
        int i = R.id.cancel_action_bt;
        Button button = (Button) ViewBindings.findChildViewById(view, R.id.cancel_action_bt);
        if (button != null) {
            i = R.id.confirm_action_bt;
            Button button2 = (Button) ViewBindings.findChildViewById(view, R.id.confirm_action_bt);
            if (button2 != null) {
                i = R.id.filter_factor_tips;
                TextView textView = (TextView) ViewBindings.findChildViewById(view, R.id.filter_factor_tips);
                if (textView != null) {
                    i = R.id.filter_factor_value_et;
                    EditText editText = (EditText) ViewBindings.findChildViewById(view, R.id.filter_factor_value_et);
                    if (editText != null) {
                        return new FilterFactorDialogLayoutBinding((LinearLayout) view, button, button2, textView, editText);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i)));
    }

    @NonNull
    public static FilterFactorDialogLayoutBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static FilterFactorDialogLayoutBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z) {
        View inflate = layoutInflater.inflate(R.layout.filter_factor_dialog_layout, viewGroup, false);
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
