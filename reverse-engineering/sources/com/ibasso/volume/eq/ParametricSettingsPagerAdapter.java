package com.ibasso.volume.eq;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.ibasso.music.widgets.tablayout.FragmentPagerAdapter;
import java.util.List;
public class ParametricSettingsPagerAdapter extends FragmentPagerAdapter {
    private List<ParametricEqTypeSettingFragment> mFragmentlist;

    public ParametricSettingsPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public int getCount() {
        return this.mFragmentlist.size();
    }

    @Override
    public Fragment getItem(int i) {
        return this.mFragmentlist.get(i);
    }

    @Override
    @Nullable
    public CharSequence getPageTitle(int i) {
        return this.mFragmentlist.get(i).getTitle();
    }

    public void setFragmentList(List<ParametricEqTypeSettingFragment> list) {
        this.mFragmentlist = list;
    }
}
