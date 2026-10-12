package com.ibasso.volume.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.ibasso.music.widgets.GainSeekBar;
import com.ibasso.music.widgets.tablayout.TabLayout;
import com.ibasso.music.widgets.tablayout.ViewPager;
import com.ibasso.volume.R;
import com.ibasso.volume.eq.ParamEqualizerView;
public final class FragmentParametricEqTypeBinding implements ViewBinding {
    @NonNull
    public final RelativeLayout layoutRoot;
    @NonNull
    public final TabLayout parametricEqSectionTab;
    @NonNull
    public final View parametricEqSectionTabLine0;
    @NonNull
    public final View parametricEqSectionTabLine1;
    @NonNull
    public final View parametricEqSectionTabLine2;
    @NonNull
    public final ViewPager parametricEqSectionViewPager;
    @NonNull
    public final ParamEqualizerView parametricEqSurfaceView;
    @NonNull
    public final FrameLayout parametricEqualizerChart;
    @NonNull
    private final RelativeLayout rootView;
    @NonNull
    public final GainSeekBar totalGainBar;

    private FragmentParametricEqTypeBinding(@NonNull RelativeLayout relativeLayout, @NonNull RelativeLayout relativeLayout2, @NonNull TabLayout tabLayout, @NonNull View view, @NonNull View view2, @NonNull View view3, @NonNull ViewPager viewPager, @NonNull ParamEqualizerView paramEqualizerView, @NonNull FrameLayout frameLayout, @NonNull GainSeekBar gainSeekBar) {
        this.rootView = relativeLayout;
        this.layoutRoot = relativeLayout2;
        this.parametricEqSectionTab = tabLayout;
        this.parametricEqSectionTabLine0 = view;
        this.parametricEqSectionTabLine1 = view2;
        this.parametricEqSectionTabLine2 = view3;
        this.parametricEqSectionViewPager = viewPager;
        this.parametricEqSurfaceView = paramEqualizerView;
        this.parametricEqualizerChart = frameLayout;
        this.totalGainBar = gainSeekBar;
    }

    @NonNull
    public static FragmentParametricEqTypeBinding bind(@NonNull View view) {
        RelativeLayout relativeLayout = (RelativeLayout) view;
        int i = R.id.parametric_eq_section_tab;
        TabLayout tabLayout = (TabLayout) ViewBindings.findChildViewById(view, R.id.parametric_eq_section_tab);
        if (tabLayout != null) {
            i = R.id.parametric_eq_section_tab_line0;
            View findChildViewById = ViewBindings.findChildViewById(view, R.id.parametric_eq_section_tab_line0);
            if (findChildViewById != null) {
                i = R.id.parametric_eq_section_tab_line1;
                View findChildViewById2 = ViewBindings.findChildViewById(view, R.id.parametric_eq_section_tab_line1);
                if (findChildViewById2 != null) {
                    i = R.id.parametric_eq_section_tab_line2;
                    View findChildViewById3 = ViewBindings.findChildViewById(view, R.id.parametric_eq_section_tab_line2);
                    if (findChildViewById3 != null) {
                        i = R.id.parametric_eq_section_view_pager;
                        ViewPager viewPager = (ViewPager) ViewBindings.findChildViewById(view, R.id.parametric_eq_section_view_pager);
                        if (viewPager != null) {
                            i = R.id.parametric_eq_surface_view;
                            ParamEqualizerView paramEqualizerView = (ParamEqualizerView) ViewBindings.findChildViewById(view, R.id.parametric_eq_surface_view);
                            if (paramEqualizerView != null) {
                                i = R.id.parametric_equalizer_chart;
                                FrameLayout frameLayout = (FrameLayout) ViewBindings.findChildViewById(view, R.id.parametric_equalizer_chart);
                                if (frameLayout != null) {
                                    i = R.id.total_gain_bar;
                                    GainSeekBar gainSeekBar = (GainSeekBar) ViewBindings.findChildViewById(view, R.id.total_gain_bar);
                                    if (gainSeekBar != null) {
                                        return new FragmentParametricEqTypeBinding(relativeLayout, relativeLayout, tabLayout, findChildViewById, findChildViewById2, findChildViewById3, viewPager, paramEqualizerView, frameLayout, gainSeekBar);
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
    public static FragmentParametricEqTypeBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static FragmentParametricEqTypeBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z) {
        View inflate = layoutInflater.inflate(R.layout.fragment_parametric_eq_type, viewGroup, false);
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
