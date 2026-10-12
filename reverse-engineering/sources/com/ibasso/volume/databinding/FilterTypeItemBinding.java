package com.ibasso.volume.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.ibasso.volume.R;
public final class FilterTypeItemBinding implements ViewBinding {
    @NonNull
    public final TextView filterTypeId;
    @NonNull
    public final TextView filterTypeName;
    @NonNull
    private final LinearLayout rootView;

    private FilterTypeItemBinding(@NonNull LinearLayout linearLayout, @NonNull TextView textView, @NonNull TextView textView2) {
        this.rootView = linearLayout;
        this.filterTypeId = textView;
        this.filterTypeName = textView2;
    }

    @NonNull
    public static FilterTypeItemBinding bind(@NonNull View view) {
        int i = R.id.filter_type_id;
        TextView textView = (TextView) ViewBindings.findChildViewById(view, R.id.filter_type_id);
        if (textView != null) {
            i = R.id.filter_type_name;
            TextView textView2 = (TextView) ViewBindings.findChildViewById(view, R.id.filter_type_name);
            if (textView2 != null) {
                return new FilterTypeItemBinding((LinearLayout) view, textView, textView2);
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i)));
    }

    @NonNull
    public static FilterTypeItemBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static FilterTypeItemBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z) {
        View inflate = layoutInflater.inflate(R.layout.filter_type_item, viewGroup, false);
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
