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
public final class NotificationLargerBinding implements ViewBinding {
    @NonNull
    public final TextView currentVolumeTv;
    @NonNull
    public final LinearLayout layoutRoot;
    @NonNull
    private final LinearLayout rootView;
    @NonNull
    public final ImageView volumeDown;
    @NonNull
    public final LinearLayout volumeDownLayout;
    @NonNull
    public final ImageView volumeUp;
    @NonNull
    public final LinearLayout volumeUpLayout;

    private NotificationLargerBinding(@NonNull LinearLayout linearLayout, @NonNull TextView textView, @NonNull LinearLayout linearLayout2, @NonNull ImageView imageView, @NonNull LinearLayout linearLayout3, @NonNull ImageView imageView2, @NonNull LinearLayout linearLayout4) {
        this.rootView = linearLayout;
        this.currentVolumeTv = textView;
        this.layoutRoot = linearLayout2;
        this.volumeDown = imageView;
        this.volumeDownLayout = linearLayout3;
        this.volumeUp = imageView2;
        this.volumeUpLayout = linearLayout4;
    }

    @NonNull
    public static NotificationLargerBinding bind(@NonNull View view) {
        int i = R.id.current_volume_tv;
        TextView textView = (TextView) ViewBindings.findChildViewById(view, R.id.current_volume_tv);
        if (textView != null) {
            LinearLayout linearLayout = (LinearLayout) view;
            i = R.id.volume_down;
            ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, R.id.volume_down);
            if (imageView != null) {
                i = R.id.volume_down_layout;
                LinearLayout linearLayout2 = (LinearLayout) ViewBindings.findChildViewById(view, R.id.volume_down_layout);
                if (linearLayout2 != null) {
                    i = R.id.volume_up;
                    ImageView imageView2 = (ImageView) ViewBindings.findChildViewById(view, R.id.volume_up);
                    if (imageView2 != null) {
                        i = R.id.volume_up_layout;
                        LinearLayout linearLayout3 = (LinearLayout) ViewBindings.findChildViewById(view, R.id.volume_up_layout);
                        if (linearLayout3 != null) {
                            return new NotificationLargerBinding(linearLayout, textView, linearLayout, imageView, linearLayout2, imageView2, linearLayout3);
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i)));
    }

    @NonNull
    public static NotificationLargerBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static NotificationLargerBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z) {
        View inflate = layoutInflater.inflate(R.layout.notification_larger, viewGroup, false);
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
