package com.ibasso.volume.eq;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.ibasso.music.widgets.tablayout.TabLayout;
import com.ibasso.music.widgets.tablayout.ViewPager;
import com.ibasso.volume.R;
import java.util.ArrayList;
public class ParametricEqualizerFragment extends Fragment implements View.OnClickListener {
    private ArrayList<ParametricEqTypeSettingFragment> mFragmentlist = new ArrayList<>();
    TabLayout.OnTabSelectedListener mOnTabSelectedListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabReselected(TabLayout.Tab tab) {
        }

        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            ParametricEqualizerFragment.this.parametricEqViewPager.setCurrentItem(tab.getPosition(), false);
            if (EqualizerManager.getInstance().getParametricEqSelectIndex() != tab.getPosition()) {
                EqualizerManager.getInstance().setParametricEqSelectIndex(tab.getPosition());
            }
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {
        }
    };
    private ViewPager parametricEqViewPager;
    private View parametricEqualizerResetView;
    private TabLayout tabLayout;

    private void initFragmentList() {
        this.mFragmentlist.add(new ParametricEqTypeSettingFragment(getContext().getString(R.string.parametric_equalizer_setting1), 0));
        this.mFragmentlist.add(new ParametricEqTypeSettingFragment(getContext().getString(R.string.parametric_equalizer_setting2), 1));
        this.mFragmentlist.add(new ParametricEqTypeSettingFragment(getContext().getString(R.string.parametric_equalizer_setting3), 2));
        this.mFragmentlist.add(new ParametricEqTypeSettingFragment(getContext().getString(R.string.parametric_equalizer_setting4), 3));
        this.mFragmentlist.add(new ParametricEqTypeSettingFragment(getContext().getString(R.string.parametric_equalizer_setting5), 4));
        this.mFragmentlist.add(new ParametricEqTypeSettingFragment(getContext().getString(R.string.parametric_equalizer_setting6), 5));
    }

    public int getLayoutId() {
        return R.layout.fragment_parametric_equalizer;
    }

    @Override
    public void onClick(View view) {
        view.getId();
    }

    @Override
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        initFragmentList();
    }

    @Override
    @Nullable
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View inflate = layoutInflater.inflate(getLayoutId(), viewGroup, false);
        this.tabLayout = (TabLayout) inflate.findViewById(R.id.parametric_eq_tab);
        this.parametricEqViewPager = (ViewPager) inflate.findViewById(R.id.parametric_eq_view_pager);
        ParametricSettingsPagerAdapter parametricSettingsPagerAdapter = new ParametricSettingsPagerAdapter(getChildFragmentManager());
        parametricSettingsPagerAdapter.setFragmentList(this.mFragmentlist);
        this.parametricEqViewPager.setAdapter(parametricSettingsPagerAdapter);
        this.parametricEqViewPager.enableScroll(false);
        this.parametricEqViewPager.setCurrentItem(EqualizerManager.getInstance().getParametricEqSelectIndex(), false);
        this.tabLayout.setupWithViewPager(this.parametricEqViewPager);
        this.tabLayout.setOnTabSelectedListener(this.mOnTabSelectedListener);
        return inflate;
    }
}
