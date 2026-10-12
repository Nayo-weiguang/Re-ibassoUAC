package com.ibasso.volume.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.ibasso.music.widgets.MarqueeTextView;
import com.ibasso.volume.R;
public final class NavBarBinding implements ViewBinding {
    @NonNull
    public final MarqueeTextView entryTitleTv;
    @NonNull
    public final ImageView headerDivideView;
    @NonNull
    public final RelativeLayout navBar;
    @NonNull
    public final ImageView navBarBackIv;
    @NonNull
    public final ImageView navBarMenuIv;
    @NonNull
    private final View rootView;

    private NavBarBinding(@NonNull View view, @NonNull MarqueeTextView marqueeTextView, @NonNull ImageView imageView, @NonNull RelativeLayout relativeLayout, @NonNull ImageView imageView2, @NonNull ImageView imageView3) {
        this.rootView = view;
        this.entryTitleTv = marqueeTextView;
        this.headerDivideView = imageView;
        this.navBar = relativeLayout;
        this.navBarBackIv = imageView2;
        this.navBarMenuIv = imageView3;
    }

    @NonNull
    public static NavBarBinding bind(@NonNull View view) {
        int i = R.id.entry_title_tv;
        MarqueeTextView marqueeTextView = (MarqueeTextView) ViewBindings.findChildViewById(view, R.id.entry_title_tv);
        if (marqueeTextView != null) {
            i = R.id.header_divide_view;
            ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, R.id.header_divide_view);
            if (imageView != null) {
                i = R.id.nav_bar;
                RelativeLayout relativeLayout = (RelativeLayout) ViewBindings.findChildViewById(view, R.id.nav_bar);
                if (relativeLayout != null) {
                    i = R.id.nav_bar_back_iv;
                    ImageView imageView2 = (ImageView) ViewBindings.findChildViewById(view, R.id.nav_bar_back_iv);
                    if (imageView2 != null) {
                        i = R.id.nav_bar_menu_iv;
                        ImageView imageView3 = (ImageView) ViewBindings.findChildViewById(view, R.id.nav_bar_menu_iv);
                        if (imageView3 != null) {
                            return new NavBarBinding(view, marqueeTextView, imageView, relativeLayout, imageView2, imageView3);
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i)));
    }

    @NonNull
    public static NavBarBinding inflate(@NonNull LayoutInflater layoutInflater, @NonNull ViewGroup viewGroup) {
        if (viewGroup != null) {
            layoutInflater.inflate(R.layout.nav_bar, viewGroup);
            return bind(viewGroup);
        }
        throw new NullPointerException("parent");
    }

    @Override
    @NonNull
    public View getRoot() {
        return this.rootView;
    }
}
