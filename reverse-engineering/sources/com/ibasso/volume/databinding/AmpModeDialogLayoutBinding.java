package com.ibasso.volume.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.ibasso.volume.R;
public final class AmpModeDialogLayoutBinding implements ViewBinding {
    @NonNull
    public final RadioButton rb0;
    @NonNull
    public final RadioButton rb1;
    @NonNull
    public final RadioGroup rg;
    @NonNull
    private final LinearLayout rootView;

    private AmpModeDialogLayoutBinding(@NonNull LinearLayout linearLayout, @NonNull RadioButton radioButton, @NonNull RadioButton radioButton2, @NonNull RadioGroup radioGroup) {
        this.rootView = linearLayout;
        this.rb0 = radioButton;
        this.rb1 = radioButton2;
        this.rg = radioGroup;
    }

    @NonNull
    public static AmpModeDialogLayoutBinding bind(@NonNull View view) {
        int i = R.id.rb_0;
        RadioButton radioButton = (RadioButton) ViewBindings.findChildViewById(view, R.id.rb_0);
        if (radioButton != null) {
            i = R.id.rb_1;
            RadioButton radioButton2 = (RadioButton) ViewBindings.findChildViewById(view, R.id.rb_1);
            if (radioButton2 != null) {
                i = R.id.rg;
                RadioGroup radioGroup = (RadioGroup) ViewBindings.findChildViewById(view, R.id.rg);
                if (radioGroup != null) {
                    return new AmpModeDialogLayoutBinding((LinearLayout) view, radioButton, radioButton2, radioGroup);
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i)));
    }

    @NonNull
    public static AmpModeDialogLayoutBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static AmpModeDialogLayoutBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z) {
        View inflate = layoutInflater.inflate(R.layout.amp_mode_dialog_layout, viewGroup, false);
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
