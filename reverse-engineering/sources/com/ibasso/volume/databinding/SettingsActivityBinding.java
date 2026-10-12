package com.ibasso.volume.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.ibasso.volume.R;
public final class SettingsActivityBinding implements ViewBinding {
    @NonNull
    public final TextView bgPickTips;
    @NonNull
    public final TextView copyright;
    @NonNull
    public final CardView defaultCoverCustom;
    @NonNull
    public final ImageView defaultCoverCustomAddIv;
    @NonNull
    public final ImageView defaultCoverCustomIv;
    @NonNull
    public final RadioButton defaultCoverCustomRb;
    @NonNull
    public final CardView defaultCoverFirst;
    @NonNull
    public final ImageView defaultCoverFirstIv;
    @NonNull
    public final RadioButton defaultCoverFirstRb;
    @NonNull
    private final ConstraintLayout rootView;
    @NonNull
    public final FrameLayout settings;
    @NonNull
    public final TextView versionTv;
    @NonNull
    public final View view;

    private SettingsActivityBinding(@NonNull ConstraintLayout constraintLayout, @NonNull TextView textView, @NonNull TextView textView2, @NonNull CardView cardView, @NonNull ImageView imageView, @NonNull ImageView imageView2, @NonNull RadioButton radioButton, @NonNull CardView cardView2, @NonNull ImageView imageView3, @NonNull RadioButton radioButton2, @NonNull FrameLayout frameLayout, @NonNull TextView textView3, @NonNull View view) {
        this.rootView = constraintLayout;
        this.bgPickTips = textView;
        this.copyright = textView2;
        this.defaultCoverCustom = cardView;
        this.defaultCoverCustomAddIv = imageView;
        this.defaultCoverCustomIv = imageView2;
        this.defaultCoverCustomRb = radioButton;
        this.defaultCoverFirst = cardView2;
        this.defaultCoverFirstIv = imageView3;
        this.defaultCoverFirstRb = radioButton2;
        this.settings = frameLayout;
        this.versionTv = textView3;
        this.view = view;
    }

    @NonNull
    public static SettingsActivityBinding bind(@NonNull View view) {
        int i = R.id.bg_pick_tips;
        TextView textView = (TextView) ViewBindings.findChildViewById(view, R.id.bg_pick_tips);
        if (textView != null) {
            i = R.id.copyright;
            TextView textView2 = (TextView) ViewBindings.findChildViewById(view, R.id.copyright);
            if (textView2 != null) {
                i = R.id.default_cover_custom;
                CardView cardView = (CardView) ViewBindings.findChildViewById(view, R.id.default_cover_custom);
                if (cardView != null) {
                    i = R.id.default_cover_custom_add_iv;
                    ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, R.id.default_cover_custom_add_iv);
                    if (imageView != null) {
                        i = R.id.default_cover_custom_iv;
                        ImageView imageView2 = (ImageView) ViewBindings.findChildViewById(view, R.id.default_cover_custom_iv);
                        if (imageView2 != null) {
                            i = R.id.default_cover_custom_rb;
                            RadioButton radioButton = (RadioButton) ViewBindings.findChildViewById(view, R.id.default_cover_custom_rb);
                            if (radioButton != null) {
                                i = R.id.default_cover_first;
                                CardView cardView2 = (CardView) ViewBindings.findChildViewById(view, R.id.default_cover_first);
                                if (cardView2 != null) {
                                    i = R.id.default_cover_first_iv;
                                    ImageView imageView3 = (ImageView) ViewBindings.findChildViewById(view, R.id.default_cover_first_iv);
                                    if (imageView3 != null) {
                                        i = R.id.default_cover_first_rb;
                                        RadioButton radioButton2 = (RadioButton) ViewBindings.findChildViewById(view, R.id.default_cover_first_rb);
                                        if (radioButton2 != null) {
                                            i = R.id.settings;
                                            FrameLayout frameLayout = (FrameLayout) ViewBindings.findChildViewById(view, R.id.settings);
                                            if (frameLayout != null) {
                                                i = R.id.version_tv;
                                                TextView textView3 = (TextView) ViewBindings.findChildViewById(view, R.id.version_tv);
                                                if (textView3 != null) {
                                                    i = R.id.view;
                                                    View findChildViewById = ViewBindings.findChildViewById(view, R.id.view);
                                                    if (findChildViewById != null) {
                                                        return new SettingsActivityBinding((ConstraintLayout) view, textView, textView2, cardView, imageView, imageView2, radioButton, cardView2, imageView3, radioButton2, frameLayout, textView3, findChildViewById);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i)));
    }

    @NonNull
    public static SettingsActivityBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static SettingsActivityBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z) {
        View inflate = layoutInflater.inflate(R.layout.settings_activity, viewGroup, false);
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
