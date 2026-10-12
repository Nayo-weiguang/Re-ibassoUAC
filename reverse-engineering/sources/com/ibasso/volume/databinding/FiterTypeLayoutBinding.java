package com.ibasso.volume.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.ibasso.volume.R;
public final class FiterTypeLayoutBinding implements ViewBinding {
    @NonNull
    public final TextView filterType;
    @NonNull
    public final ListView filterTypeList;
    @NonNull
    private final LinearLayout rootView;

    private FiterTypeLayoutBinding(@NonNull LinearLayout linearLayout, @NonNull TextView textView, @NonNull ListView listView) {
        this.rootView = linearLayout;
        this.filterType = textView;
        this.filterTypeList = listView;
    }

    @NonNull
    public static FiterTypeLayoutBinding bind(@NonNull View view) {
        int i = R.id.filter_type;
        TextView textView = (TextView) ViewBindings.findChildViewById(view, R.id.filter_type);
        if (textView != null) {
            i = R.id.filter_type_list;
            ListView listView = (ListView) ViewBindings.findChildViewById(view, R.id.filter_type_list);
            if (listView != null) {
                return new FiterTypeLayoutBinding((LinearLayout) view, textView, listView);
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i)));
    }

    @NonNull
    public static FiterTypeLayoutBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static FiterTypeLayoutBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z) {
        View inflate = layoutInflater.inflate(R.layout.fiter_type_layout, viewGroup, false);
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
