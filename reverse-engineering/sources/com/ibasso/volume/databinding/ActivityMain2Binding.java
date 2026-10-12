package com.ibasso.volume.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.ibasso.volume.R;
public final class ActivityMain2Binding implements ViewBinding {
    @NonNull
    public final FrameLayout mainActivityRootContainer;
    @NonNull
    public final ImageView mainSettingsIv;
    @NonNull
    public final ImageView navBarBackIv;
    @NonNull
    private final FrameLayout rootView;

    private ActivityMain2Binding(@NonNull FrameLayout frameLayout, @NonNull FrameLayout frameLayout2, @NonNull ImageView imageView, @NonNull ImageView imageView2) {
        this.rootView = frameLayout;
        this.mainActivityRootContainer = frameLayout2;
        this.mainSettingsIv = imageView;
        this.navBarBackIv = imageView2;
    }

    @NonNull
    public static ActivityMain2Binding bind(@NonNull View view) {
        FrameLayout frameLayout = (FrameLayout) view;
        int i = R.id.main_settings_iv;
        ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, R.id.main_settings_iv);
        if (imageView != null) {
            i = R.id.nav_bar_back_iv;
            ImageView imageView2 = (ImageView) ViewBindings.findChildViewById(view, R.id.nav_bar_back_iv);
            if (imageView2 != null) {
                return new ActivityMain2Binding(frameLayout, frameLayout, imageView, imageView2);
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i)));
    }

    @NonNull
    public static ActivityMain2Binding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static ActivityMain2Binding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z) {
        View inflate = layoutInflater.inflate(R.layout.activity_main2, viewGroup, false);
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
