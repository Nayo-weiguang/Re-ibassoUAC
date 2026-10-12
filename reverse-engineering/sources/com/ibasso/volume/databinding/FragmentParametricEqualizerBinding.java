package com.ibasso.volume.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.ibasso.music.widgets.tablayout.TabLayout;
import com.ibasso.music.widgets.tablayout.ViewPager;
import com.ibasso.volume.R;
public final class FragmentParametricEqualizerBinding implements ViewBinding {
    @NonNull
    public final RelativeLayout layoutRoot;
    @NonNull
    public final TabLayout parametricEqTab;
    @NonNull
    public final ViewPager parametricEqViewPager;
    @NonNull
    private final RelativeLayout rootView;

    private FragmentParametricEqualizerBinding(@NonNull RelativeLayout relativeLayout, @NonNull RelativeLayout relativeLayout2, @NonNull TabLayout tabLayout, @NonNull ViewPager viewPager) {
        this.rootView = relativeLayout;
        this.layoutRoot = relativeLayout2;
        this.parametricEqTab = tabLayout;
        this.parametricEqViewPager = viewPager;
    }

    @NonNull
    public static FragmentParametricEqualizerBinding bind(@NonNull View view) {
        RelativeLayout relativeLayout = (RelativeLayout) view;
        int i = R.id.parametric_eq_tab;
        TabLayout tabLayout = (TabLayout) ViewBindings.findChildViewById(view, R.id.parametric_eq_tab);
        if (tabLayout != null) {
            i = R.id.parametric_eq_view_pager;
            ViewPager viewPager = (ViewPager) ViewBindings.findChildViewById(view, R.id.parametric_eq_view_pager);
            if (viewPager != null) {
                return new FragmentParametricEqualizerBinding(relativeLayout, relativeLayout, tabLayout, viewPager);
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i)));
    }

    @NonNull
    public static FragmentParametricEqualizerBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static FragmentParametricEqualizerBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z) {
        View inflate = layoutInflater.inflate(R.layout.fragment_parametric_equalizer, viewGroup, false);
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
