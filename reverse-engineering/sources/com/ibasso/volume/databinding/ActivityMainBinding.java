package com.ibasso.volume.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.ibasso.music.widgets.MarqueeTextView;
import com.ibasso.music.widgets.VolumeGradientView;
import com.ibasso.volume.R;
public final class ActivityMainBinding implements ViewBinding {
    @NonNull
    public final MarqueeTextView deviceNameTv;
    @NonNull
    public final ImageView mainSettingsIv;
    @NonNull
    public final ImageView navBarBackIv;
    @NonNull
    private final RelativeLayout rootView;
    @NonNull
    public final ImageView volumeAdd;
    @NonNull
    public final VolumeGradientView volumeGradientView;
    @NonNull
    public final ImageView volumeSubtract;

    private ActivityMainBinding(@NonNull RelativeLayout relativeLayout, @NonNull MarqueeTextView marqueeTextView, @NonNull ImageView imageView, @NonNull ImageView imageView2, @NonNull ImageView imageView3, @NonNull VolumeGradientView volumeGradientView, @NonNull ImageView imageView4) {
        this.rootView = relativeLayout;
        this.deviceNameTv = marqueeTextView;
        this.mainSettingsIv = imageView;
        this.navBarBackIv = imageView2;
        this.volumeAdd = imageView3;
        this.volumeGradientView = volumeGradientView;
        this.volumeSubtract = imageView4;
    }

    @NonNull
    public static ActivityMainBinding bind(@NonNull View view) {
        int i = R.id.device_name_tv;
        MarqueeTextView marqueeTextView = (MarqueeTextView) ViewBindings.findChildViewById(view, R.id.device_name_tv);
        if (marqueeTextView != null) {
            i = R.id.main_settings_iv;
            ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, R.id.main_settings_iv);
            if (imageView != null) {
                i = R.id.nav_bar_back_iv;
                ImageView imageView2 = (ImageView) ViewBindings.findChildViewById(view, R.id.nav_bar_back_iv);
                if (imageView2 != null) {
                    i = R.id.volume_add;
                    ImageView imageView3 = (ImageView) ViewBindings.findChildViewById(view, R.id.volume_add);
                    if (imageView3 != null) {
                        i = R.id.volume_gradient_view;
                        VolumeGradientView volumeGradientView = (VolumeGradientView) ViewBindings.findChildViewById(view, R.id.volume_gradient_view);
                        if (volumeGradientView != null) {
                            i = R.id.volume_subtract;
                            ImageView imageView4 = (ImageView) ViewBindings.findChildViewById(view, R.id.volume_subtract);
                            if (imageView4 != null) {
                                return new ActivityMainBinding((RelativeLayout) view, marqueeTextView, imageView, imageView2, imageView3, volumeGradientView, imageView4);
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i)));
    }

    @NonNull
    public static ActivityMainBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static ActivityMainBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z) {
        View inflate = layoutInflater.inflate(R.layout.activity_main, viewGroup, false);
        if (z) {
            viewGroup.addView(inflate);
        }
        return bind(inflate);
    }

    @Override
    @NonNull
    public RelativeLayout getRoot() {
        return this.rootView;
    }
}
