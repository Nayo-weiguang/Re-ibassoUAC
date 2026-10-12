package com.ibasso.volume.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.ibasso.volume.R;
public final class ActivitySplashBinding implements ViewBinding {
    @NonNull
    public final Button adDownTime;
    @NonNull
    public final ImageView adIv;
    @NonNull
    private final ConstraintLayout rootView;

    private ActivitySplashBinding(@NonNull ConstraintLayout constraintLayout, @NonNull Button button, @NonNull ImageView imageView) {
        this.rootView = constraintLayout;
        this.adDownTime = button;
        this.adIv = imageView;
    }

    @NonNull
    public static ActivitySplashBinding bind(@NonNull View view) {
        int i = R.id.adDownTime;
        Button button = (Button) ViewBindings.findChildViewById(view, R.id.adDownTime);
        if (button != null) {
            i = R.id.adIv;
            ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, R.id.adIv);
            if (imageView != null) {
                return new ActivitySplashBinding((ConstraintLayout) view, button, imageView);
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i)));
    }

    @NonNull
    public static ActivitySplashBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static ActivitySplashBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z) {
        View inflate = layoutInflater.inflate(R.layout.activity_splash, viewGroup, false);
        if (z) {
            viewGroup.addView(inflate);
        }
        return bind(inflate);
    }

    @Override
    @NonNull
    public ConstraintLayout getRoot() {
        return this.rootView;
    }
}
