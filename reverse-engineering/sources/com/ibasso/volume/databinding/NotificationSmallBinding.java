package com.ibasso.volume.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.ibasso.volume.R;
public final class NotificationSmallBinding implements ViewBinding {
    @NonNull
    public final TextView currentVolumeTv;
    @NonNull
    public final LinearLayout layoutRoot;
    @NonNull
    private final LinearLayout rootView;
    @NonNull
    public final ImageView volumeDown;
    @NonNull
    public final ImageView volumeUp;

    private NotificationSmallBinding(@NonNull LinearLayout linearLayout, @NonNull TextView textView, @NonNull LinearLayout linearLayout2, @NonNull ImageView imageView, @NonNull ImageView imageView2) {
        this.rootView = linearLayout;
        this.currentVolumeTv = textView;
        this.layoutRoot = linearLayout2;
        this.volumeDown = imageView;
        this.volumeUp = imageView2;
    }

    @NonNull
    public static NotificationSmallBinding bind(@NonNull View view) {
        int i = R.id.current_volume_tv;
        TextView textView = (TextView) ViewBindings.findChildViewById(view, R.id.current_volume_tv);
        if (textView != null) {
            LinearLayout linearLayout = (LinearLayout) view;
            i = R.id.volume_down;
            ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, R.id.volume_down);
            if (imageView != null) {
                i = R.id.volume_up;
                ImageView imageView2 = (ImageView) ViewBindings.findChildViewById(view, R.id.volume_up);
                if (imageView2 != null) {
                    return new NotificationSmallBinding(linearLayout, textView, linearLayout, imageView, imageView2);
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i)));
    }

    @NonNull
    public static NotificationSmallBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static NotificationSmallBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z) {
        View inflate = layoutInflater.inflate(R.layout.notification_small, viewGroup, false);
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
