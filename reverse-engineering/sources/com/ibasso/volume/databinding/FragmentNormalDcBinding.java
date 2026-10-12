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
public final class FragmentNormalDcBinding implements ViewBinding {
    @NonNull
    public final MarqueeTextView deviceNameTv;
    @NonNull
    private final RelativeLayout rootView;
    @NonNull
    public final ImageView volumeAdd;
    @NonNull
    public final VolumeGradientView volumeGradientView;
    @NonNull
    public final ImageView volumeSubtract;

    private FragmentNormalDcBinding(@NonNull RelativeLayout relativeLayout, @NonNull MarqueeTextView marqueeTextView, @NonNull ImageView imageView, @NonNull VolumeGradientView volumeGradientView, @NonNull ImageView imageView2) {
        this.rootView = relativeLayout;
        this.deviceNameTv = marqueeTextView;
        this.volumeAdd = imageView;
        this.volumeGradientView = volumeGradientView;
        this.volumeSubtract = imageView2;
    }

    @NonNull
    public static FragmentNormalDcBinding bind(@NonNull View view) {
        int i = R.id.deviceNameTv;
        MarqueeTextView marqueeTextView = (MarqueeTextView) ViewBindings.findChildViewById(view, R.id.deviceNameTv);
        if (marqueeTextView != null) {
            i = R.id.volumeAdd;
            ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, R.id.volumeAdd);
            if (imageView != null) {
                i = R.id.volumeGradientView;
                VolumeGradientView volumeGradientView = (VolumeGradientView) ViewBindings.findChildViewById(view, R.id.volumeGradientView);
                if (volumeGradientView != null) {
                    i = R.id.volumeSubtract;
                    ImageView imageView2 = (ImageView) ViewBindings.findChildViewById(view, R.id.volumeSubtract);
                    if (imageView2 != null) {
                        return new FragmentNormalDcBinding((RelativeLayout) view, marqueeTextView, imageView, volumeGradientView, imageView2);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i)));
    }

    @NonNull
    public static FragmentNormalDcBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static FragmentNormalDcBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z) {
        View inflate = layoutInflater.inflate(R.layout.fragment_normal_dc, viewGroup, false);
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
