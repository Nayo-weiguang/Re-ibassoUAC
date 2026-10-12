package com.ibasso.volume.eq;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.ibasso.music.widgets.tablayout.PagerAdapter;
import com.ibasso.volume.R;
import java.util.List;
public class ParametricSectionPagerAdapter extends PagerAdapter {
    private List<Integer> colorList;
    private String[] filterTitles;
    private List<View> views;

    public ParametricSectionPagerAdapter(Context context, List<View> list, List<Integer> list2) {
        this.views = list;
        this.colorList = list2;
        this.filterTitles = context.getResources().getStringArray(R.array.parametric_equalizer_filter_entries);
    }

    @Override
    public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
        viewGroup.removeView((View) obj);
    }

    @Override
    public int getCount() {
        List<View> list = this.views;
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    @Override
    @Nullable
    public CharSequence getPageTitle(int i) {
        return this.filterTitles[i];
    }

    @Override
    public int getPageTitleColor(int i) {
        return this.colorList.get(i).intValue();
    }

    @Override
    public Object instantiateItem(ViewGroup viewGroup, int i) {
        View view = this.views.get(i);
        viewGroup.addView(view);
        return view;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object obj) {
        return view == obj;
    }

    public void setParametricSectionData(List<View> list, List<Integer> list2) {
        this.views = list;
        this.colorList = list2;
    }
}
