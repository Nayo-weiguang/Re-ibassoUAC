package com.ibasso.volume.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.ibasso.volume.R;
public final class WebviewBinding implements ViewBinding {
    @NonNull
    public final ProgressBar progressbar;
    @NonNull
    private final FrameLayout rootView;
    @NonNull
    public final WebView webview;

    private WebviewBinding(@NonNull FrameLayout frameLayout, @NonNull ProgressBar progressBar, @NonNull WebView webView) {
        this.rootView = frameLayout;
        this.progressbar = progressBar;
        this.webview = webView;
    }

    @NonNull
    public static WebviewBinding bind(@NonNull View view) {
        int i = R.id.progressbar;
        ProgressBar progressBar = (ProgressBar) ViewBindings.findChildViewById(view, R.id.progressbar);
        if (progressBar != null) {
            i = R.id.webview;
            WebView webView = (WebView) ViewBindings.findChildViewById(view, R.id.webview);
            if (webView != null) {
                return new WebviewBinding((FrameLayout) view, progressBar, webView);
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i)));
    }

    @NonNull
    public static WebviewBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static WebviewBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z) {
        View inflate = layoutInflater.inflate(R.layout.webview, viewGroup, false);
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
