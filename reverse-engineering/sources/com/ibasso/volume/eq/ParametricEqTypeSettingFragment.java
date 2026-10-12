package com.ibasso.volume.eq;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import com.ibasso.music.widgets.GainSeekBar;
import com.ibasso.music.widgets.tablayout.TabLayout;
import com.ibasso.music.widgets.tablayout.ViewPager;
import com.ibasso.volume.R;
import com.ibasso.volume.eq.EqualizerManager;
import com.ibasso.volume.eq.ParamEqualizerView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import okhttp3.HttpUrl;
public class ParametricEqTypeSettingFragment extends Fragment implements ParamEqualizerView.OnEQPointChangeListener, EqualizerManager.OnEqStatusChangeListener {
    List<ParametricEqualizer> customDefaultParametricEqualizers;
    private AlertDialog defaultParamEqSettingsDialog;
    private AlertDialog factorDialog;
    private List<String> filterTypeNameList;
    private AlertDialog frequencyDialog;
    private AlertDialog gainDialog;
    private GainSeekBar gainSeekBar;
    private ParamEqualizerView paramEqualizerView;
    private ParametricEqualizer parametricEqualizer;
    private ParametricSectionPagerAdapter parametricSectionPagerAdapter;
    private TabLayout sectionTab;
    private ViewPager sectionViewPager;
    private int settingIndex;
    private AlertDialog singleChoiceDialog;
    private String title;
    private List<View> sectionViews = new ArrayList();
    private List<Integer> sectionTitleColors = new ArrayList();
    public final int SECTION_COUNT = 10;
    private Switch[] filterSwitchViews = new Switch[10];
    private TextView[] filterTypeValueViews = new TextView[10];
    private TextView[] filterFrequencyViews = new TextView[10];
    private TextView[] filterGainViews = new TextView[10];
    private TextView[] filterFactorViews = new TextView[10];
    private final TextView[] filterSectionSwitchOffText = new TextView[10];
    private final TextView[] filterSectionSwitchOnText = new TextView[10];
    TabLayout.OnTabSelectedListener mOnTabSelectedListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabReselected(TabLayout.Tab tab) {
        }

        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            ParametricEqTypeSettingFragment.this.sectionViewPager.setCurrentItem(tab.getPosition(), false);
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {
        }
    };

    public class FilterAdapter extends BaseAdapter {
        private List<String> filterDataList;
        private LayoutInflater mInflater;

        public FilterAdapter(Context context, List<String> list) {
            this.filterDataList = list;
            this.mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            List<String> list = this.filterDataList;
            if (list != null) {
                return list.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int i) {
            return this.filterDataList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View inflate = this.mInflater.inflate(R.layout.filter_type_item, (ViewGroup) null);
            ((TextView) inflate.findViewById(R.id.filter_type_id)).setText(HttpUrl.FRAGMENT_ENCODE_SET + (i + 1));
            ((TextView) inflate.findViewById(R.id.filter_type_name)).setText((String) getItem(i));
            return inflate;
        }
    }

    public ParametricEqTypeSettingFragment(String str, int i) {
        this.title = HttpUrl.FRAGMENT_ENCODE_SET;
        this.title = str;
        this.settingIndex = i;
    }

    public static void a(View view) {
    }

    public static Unit b(ParametricEqTypeSettingFragment parametricEqTypeSettingFragment, Integer num) {
        parametricEqTypeSettingFragment.parametricEqualizer.setTotalGain(num.intValue());
        parametricEqTypeSettingFragment.paramEqualizerView.setTotalGain(num.intValue());
        EqualizerManager.getInstance().updateParamEq(parametricEqTypeSettingFragment.parametricEqualizer);
        return null;
    }

    private void initSectionViews() {
        this.sectionViews.clear();
        for (int i = 0; i < 10; i++) {
            FilterSetting filterSetting = this.parametricEqualizer.getFilterSettings().get(i);
            View inflate = View.inflate(getContext(), R.layout.fragment_parametric_equalizer_section, null);
            this.filterSectionSwitchOffText[i] = (TextView) inflate.findViewById(R.id.filter_section_switch_off_text);
            this.filterSectionSwitchOnText[i] = (TextView) inflate.findViewById(R.id.filter_section_switch_on_text);
            this.filterSwitchViews[i] = (Switch) inflate.findViewById(R.id.filter_section_switch);
            if (this.filterSwitchViews[i].isChecked()) {
                this.filterSectionSwitchOffText[i].setTextColor(getContext().getColor(R.color.settings_switch_off_color));
                this.filterSectionSwitchOnText[i].setTextColor(getContext().getColor(R.color.settings_switch_on_color));
            } else {
                this.filterSectionSwitchOffText[i].setTextColor(getContext().getColor(R.color.settings_switch_on_color));
                this.filterSectionSwitchOnText[i].setTextColor(getContext().getColor(R.color.settings_switch_off_color));
            }
            this.filterTypeValueViews[i] = (TextView) inflate.findViewById(R.id.filter_type_value);
            this.filterTypeValueViews[i].setText(this.filterTypeNameList.get(Math.max(filterSetting.getFilterType() - 1, 0)));
            this.filterTypeValueViews[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ParametricEqTypeSettingFragment.this.showSingleChoiceDialog();
                }
            });
            this.filterFrequencyViews[i] = (TextView) inflate.findViewById(R.id.filter_frequency_value);
            this.filterFrequencyViews[i].setText(filterSetting.getFrequency() + "Hz");
            this.filterFrequencyViews[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ParametricEqTypeSettingFragment.this.showFrequencyDialog();
                }
            });
            this.filterGainViews[i] = (TextView) inflate.findViewById(R.id.filter_gain_value);
            this.filterGainViews[i].setText(filterSetting.getGain() + "dB");
            this.filterGainViews[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ParametricEqTypeSettingFragment.this.showGainDialog();
                }
            });
            this.filterFactorViews[i] = (TextView) inflate.findViewById(R.id.filter_factor_value);
            this.filterFactorViews[i].setText(filterSetting.getFactor() + HttpUrl.FRAGMENT_ENCODE_SET);
            this.filterFactorViews[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ParametricEqTypeSettingFragment.this.showFactorDialog();
                }
            });
            this.sectionViews.add(inflate);
        }
        this.sectionTitleColors.clear();
        this.sectionTitleColors.add(Integer.valueOf(getContext().getColor(R.color.filter_title_color1)));
        this.sectionTitleColors.add(Integer.valueOf(getContext().getColor(R.color.filter_title_color2)));
        this.sectionTitleColors.add(Integer.valueOf(getContext().getColor(R.color.filter_title_color3)));
        this.sectionTitleColors.add(Integer.valueOf(getContext().getColor(R.color.filter_title_color4)));
        this.sectionTitleColors.add(Integer.valueOf(getContext().getColor(R.color.filter_title_color5)));
        this.sectionTitleColors.add(Integer.valueOf(getContext().getColor(R.color.filter_title_color6)));
        this.sectionTitleColors.add(Integer.valueOf(getContext().getColor(R.color.filter_title_color7)));
        this.sectionTitleColors.add(Integer.valueOf(getContext().getColor(R.color.filter_title_color8)));
        this.sectionTitleColors.add(Integer.valueOf(getContext().getColor(R.color.filter_title_color9)));
        this.sectionTitleColors.add(Integer.valueOf(getContext().getColor(R.color.filter_title_color10)));
    }

    public void showFactorDialog() {
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.filter_factor_dialog_layout, (ViewGroup) null);
        final EditText editText = (EditText) inflate.findViewById(R.id.filter_factor_value_et);
        ((Button) inflate.findViewById(R.id.confirm_action_bt)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String obj = editText.getText().toString();
                if (obj != null) {
                    try {
                        double parseDouble = Double.parseDouble(obj);
                        if (parseDouble <= 10.0d && parseDouble >= 0.1d) {
                            ParametricEqTypeSettingFragment.this.parametricEqualizer.getFilterSettings().get(ParametricEqTypeSettingFragment.this.sectionViewPager.getCurrentItem()).setFactor(parseDouble);
                            TextView textView = ParametricEqTypeSettingFragment.this.filterFactorViews[ParametricEqTypeSettingFragment.this.sectionViewPager.getCurrentItem()];
                            textView.setText(HttpUrl.FRAGMENT_ENCODE_SET + parseDouble);
                            ParametricEqTypeSettingFragment.this.factorDialog.dismiss();
                            EqualizerManager.getInstance().updateParamEq(ParametricEqTypeSettingFragment.this.parametricEqualizer);
                            ParametricEqTypeSettingFragment.this.paramEqualizerView.setFilters(ParametricEqTypeSettingFragment.this.parametricEqualizer.getFilterSettings());
                            return;
                        }
                    } catch (NumberFormatException e2) {
                        e2.printStackTrace();
                    }
                }
                Toast.makeText(ParametricEqTypeSettingFragment.this.getContext(), ParametricEqTypeSettingFragment.this.getContext().getString(R.string.input_error), 0).show();
            }
        });
        ((Button) inflate.findViewById(R.id.cancel_action_bt)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParametricEqTypeSettingFragment.this.factorDialog.dismiss();
            }
        });
        AlertDialog create = new AlertDialog.Builder(getContext()).setView(inflate).create();
        this.factorDialog = create;
        create.show();
    }

    public void showFrequencyDialog() {
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.filter_frequency_dialog_layout, (ViewGroup) null);
        final EditText editText = (EditText) inflate.findViewById(R.id.filter_frequency_value_et);
        ((Button) inflate.findViewById(R.id.confirm_action_bt)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String obj = editText.getText().toString();
                if (obj != null) {
                    try {
                        int parseInt = Integer.parseInt(obj);
                        if (parseInt <= 20000 && parseInt >= 20) {
                            ParametricEqTypeSettingFragment.this.parametricEqualizer.getFilterSettings().get(ParametricEqTypeSettingFragment.this.sectionViewPager.getCurrentItem()).setFrequency(parseInt);
                            TextView textView = ParametricEqTypeSettingFragment.this.filterFrequencyViews[ParametricEqTypeSettingFragment.this.sectionViewPager.getCurrentItem()];
                            textView.setText(HttpUrl.FRAGMENT_ENCODE_SET + parseInt + "Hz");
                            ParametricEqTypeSettingFragment.this.frequencyDialog.dismiss();
                            EqualizerManager.getInstance().updateParamEq(ParametricEqTypeSettingFragment.this.parametricEqualizer);
                            ParametricEqTypeSettingFragment.this.paramEqualizerView.setFilters(ParametricEqTypeSettingFragment.this.parametricEqualizer.getFilterSettings());
                            return;
                        }
                    } catch (NumberFormatException e2) {
                        e2.printStackTrace();
                    }
                }
                Toast.makeText(ParametricEqTypeSettingFragment.this.getContext(), "Input is wrong!", 0).show();
            }
        });
        ((Button) inflate.findViewById(R.id.cancel_action_bt)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParametricEqTypeSettingFragment.this.frequencyDialog.dismiss();
            }
        });
        AlertDialog create = new AlertDialog.Builder(getContext()).setView(inflate).create();
        this.frequencyDialog = create;
        create.show();
    }

    public void showGainDialog() {
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.filter_gain_dialog_layout, (ViewGroup) null);
        final EditText editText = (EditText) inflate.findViewById(R.id.filter_gain_value_et);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
                String obj = editable.toString();
                if (obj.isEmpty() || obj.equals("-")) {
                    return;
                }
                try {
                    double parseDouble = Double.parseDouble(obj);
                    if (parseDouble < -24.0d) {
                        editText.setText("-24.0");
                        editText.setSelection(3);
                    } else if (parseDouble > 12.0d) {
                        editText.setText("12.0");
                        editText.setSelection(2);
                    } else {
                        String[] split = obj.split("\\.");
                        if (split.length <= 1 || split[1].length() <= 1) {
                            return;
                        }
                        String str = split[0] + "." + split[1].charAt(0);
                        editText.setText(str);
                        editText.setSelection(str.length());
                    }
                } catch (NumberFormatException unused) {
                    editText.setText("0");
                    editText.setSelection(1);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }
        });
        ((Button) inflate.findViewById(R.id.confirm_action_bt)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    double parseDouble = Double.parseDouble(editText.getText().toString());
                    if (parseDouble <= 12.0d && parseDouble >= -24.0d) {
                        ParametricEqTypeSettingFragment.this.parametricEqualizer.getFilterSettings().get(ParametricEqTypeSettingFragment.this.sectionViewPager.getCurrentItem()).setGain(parseDouble);
                        TextView textView = ParametricEqTypeSettingFragment.this.filterGainViews[ParametricEqTypeSettingFragment.this.sectionViewPager.getCurrentItem()];
                        textView.setText(HttpUrl.FRAGMENT_ENCODE_SET + parseDouble + "dB");
                        ParametricEqTypeSettingFragment.this.gainDialog.dismiss();
                        ParametricEqTypeSettingFragment.this.paramEqualizerView.setFilters(ParametricEqTypeSettingFragment.this.parametricEqualizer.getFilterSettings());
                        EqualizerManager.getInstance().updateParamEq(ParametricEqTypeSettingFragment.this.parametricEqualizer);
                        return;
                    }
                } catch (NumberFormatException e2) {
                    e2.printStackTrace();
                }
                Toast.makeText(ParametricEqTypeSettingFragment.this.getContext(), ParametricEqTypeSettingFragment.this.getContext().getString(R.string.input_error), 0).show();
            }
        });
        ((Button) inflate.findViewById(R.id.cancel_action_bt)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParametricEqTypeSettingFragment.this.gainDialog.dismiss();
            }
        });
        AlertDialog create = new AlertDialog.Builder(getContext()).setView(inflate).create();
        this.gainDialog = create;
        create.show();
    }

    public void showSingleChoiceDialog() {
        FilterAdapter filterAdapter = new FilterAdapter(getContext(), this.filterTypeNameList);
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.fiter_type_layout, (ViewGroup) null);
        ListView listView = (ListView) inflate.findViewById(R.id.filter_type_list);
        listView.setAdapter((ListAdapter) filterAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                ParametricEqTypeSettingFragment.this.parametricEqualizer.getFilterSettings().get(ParametricEqTypeSettingFragment.this.sectionViewPager.getCurrentItem()).setFilterType(i + 1);
                ParametricEqTypeSettingFragment.this.filterTypeValueViews[ParametricEqTypeSettingFragment.this.sectionViewPager.getCurrentItem()].setText((CharSequence) ParametricEqTypeSettingFragment.this.filterTypeNameList.get(i));
                ParametricEqTypeSettingFragment.this.singleChoiceDialog.dismiss();
                ParametricEqTypeSettingFragment.this.paramEqualizerView.setFilters(ParametricEqTypeSettingFragment.this.parametricEqualizer.getFilterSettings());
                EqualizerManager.getInstance().updateParamEq(ParametricEqTypeSettingFragment.this.parametricEqualizer);
            }
        });
        AlertDialog create = new AlertDialog.Builder(getContext()).setView(inflate).create();
        this.singleChoiceDialog = create;
        create.show();
    }

    public int getLayoutId() {
        return R.layout.fragment_parametric_eq_type;
    }

    public CharSequence getTitle() {
        return this.title;
    }

    @Override
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        String[] stringArray = getResources().getStringArray(R.array.param_filter_type_entries);
        ArrayList arrayList = new ArrayList();
        this.filterTypeNameList = arrayList;
        arrayList.addAll(Arrays.asList(stringArray));
        this.parametricEqualizer = EqualizerManager.getInstance().getParametricEqualizerByIndex(this.settingIndex);
        Log.i("TAG", "update PEQ: " + this.parametricEqualizer);
        this.customDefaultParametricEqualizers = EqualizerManager.getInstance().getCustomDefaultParametricEqualizers();
    }

    @Override
    @Nullable
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View inflate = layoutInflater.inflate(getLayoutId(), viewGroup, false);
        inflate.findViewById(R.id.layout_root).setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                ParametricEqTypeSettingFragment.a(view);
            }
        });
        this.sectionTab = (TabLayout) inflate.findViewById(R.id.parametric_eq_section_tab);
        this.sectionViewPager = (ViewPager) inflate.findViewById(R.id.parametric_eq_section_view_pager);
        this.gainSeekBar = (GainSeekBar) inflate.findViewById(R.id.total_gain_bar);
        initSectionViews();
        ParametricSectionPagerAdapter parametricSectionPagerAdapter = new ParametricSectionPagerAdapter(getContext(), this.sectionViews, this.sectionTitleColors);
        this.parametricSectionPagerAdapter = parametricSectionPagerAdapter;
        this.sectionViewPager.setAdapter(parametricSectionPagerAdapter);
        this.sectionTab.setupWithViewPager(this.sectionViewPager);
        this.sectionTab.addOnTabSelectedListener(this.mOnTabSelectedListener);
        this.paramEqualizerView = (ParamEqualizerView) inflate.findViewById(R.id.parametric_eq_surface_view);
        this.parametricEqualizer = EqualizerManager.getInstance().getParametricEqualizerByIndex(this.settingIndex);
        this.paramEqualizerView.setColors(new int[]{getResources().getColor(R.color.filter_title_color1), getResources().getColor(R.color.filter_title_color2), getResources().getColor(R.color.filter_title_color3), getResources().getColor(R.color.filter_title_color4), getResources().getColor(R.color.filter_title_color5), getResources().getColor(R.color.filter_title_color6), getResources().getColor(R.color.filter_title_color7), getResources().getColor(R.color.filter_title_color8), getResources().getColor(R.color.filter_title_color9), getResources().getColor(R.color.filter_title_color10)});
        this.paramEqualizerView.setFilters(this.parametricEqualizer.getFilterSettings());
        this.paramEqualizerView.setOnEQPointChangeListener(this);
        this.gainSeekBar.setGain(this.parametricEqualizer.getTotalGain());
        this.paramEqualizerView.setTotalGain(this.parametricEqualizer.getTotalGain());
        this.gainSeekBar.setOnGainChangeListener(new Function1() {
            @Override
            public final Object invoke(Object obj) {
                return ParametricEqTypeSettingFragment.b(ParametricEqTypeSettingFragment.this, (Integer) obj);
            }
        });
        EqualizerManager.getInstance().addEqStatusChangeListener(this);
        return inflate;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EqualizerManager.getInstance().removeEqStatusChangeListener(this);
    }

    @Override
    public void onEQPointChanged(ParamEqualizerView.EQPoint eQPoint) {
        this.sectionViewPager.setCurrentItem(eQPoint.getId(), false);
        TextView textView = this.filterFrequencyViews[eQPoint.getId()];
        textView.setText(eQPoint.getFrequency() + "Hz");
        TextView textView2 = this.filterGainViews[eQPoint.getId()];
        textView2.setText(eQPoint.getGain() + "dB");
        this.parametricEqualizer.getFilterSettings().get(eQPoint.getId()).setFrequency(eQPoint.getFrequency());
        this.parametricEqualizer.getFilterSettings().get(eQPoint.getId()).setGain(eQPoint.getGain());
        EqualizerManager.getInstance().updateParamEq(this.parametricEqualizer);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStatusChanged(int i) {
        resetParamEqStatus();
    }

    @Override
    public void onStop() {
        super.onStop();
        AlertDialog alertDialog = this.singleChoiceDialog;
        if (alertDialog != null && alertDialog.isShowing()) {
            this.singleChoiceDialog.dismiss();
        }
        AlertDialog alertDialog2 = this.frequencyDialog;
        if (alertDialog2 != null && alertDialog2.isShowing()) {
            this.frequencyDialog.dismiss();
        }
        AlertDialog alertDialog3 = this.gainDialog;
        if (alertDialog3 != null && alertDialog3.isShowing()) {
            this.gainDialog.dismiss();
        }
        AlertDialog alertDialog4 = this.factorDialog;
        if (alertDialog4 != null && alertDialog4.isShowing()) {
            this.factorDialog.dismiss();
        }
        AlertDialog alertDialog5 = this.defaultParamEqSettingsDialog;
        if (alertDialog5 == null || !alertDialog5.isShowing()) {
            return;
        }
        this.defaultParamEqSettingsDialog.dismiss();
    }

    public void resetParamEqStatus() {
        if (this.paramEqualizerView != null) {
            ParametricEqualizer parametricEqualizerByIndex = EqualizerManager.getInstance().getParametricEqualizerByIndex(this.settingIndex);
            this.parametricEqualizer = parametricEqualizerByIndex;
            this.paramEqualizerView.setFilters(parametricEqualizerByIndex.getFilterSettings());
            updateView();
            EqualizerManager.getInstance().updateParamEq(this.parametricEqualizer);
        }
    }

    public void showEqMenu() {
        ArrayList arrayList = new ArrayList();
        arrayList.add("Default");
        for (ParametricEqualizer parametricEqualizer : this.customDefaultParametricEqualizers) {
            arrayList.add(parametricEqualizer.getName());
        }
        FilterAdapter filterAdapter = new FilterAdapter(getContext(), arrayList);
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.fiter_type_layout, (ViewGroup) null);
        ListView listView = (ListView) inflate.findViewById(R.id.filter_type_list);
        ((TextView) inflate.findViewById(R.id.filter_type)).setText("EQ:");
        listView.setAdapter((ListAdapter) filterAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                ParametricEqTypeSettingFragment.this.parametricEqualizer.copyNoId(i == 0 ? EqualizerManager.getInstance().getDefaultParametricEqualizerByIndex(ParametricEqTypeSettingFragment.this.settingIndex) : ParametricEqTypeSettingFragment.this.customDefaultParametricEqualizers.get(i - 1));
                ParametricEqTypeSettingFragment.this.paramEqualizerView.setFilters(ParametricEqTypeSettingFragment.this.parametricEqualizer.getFilterSettings());
                ParametricEqTypeSettingFragment.this.updateView();
                EqualizerManager.getInstance().updateParamEq(ParametricEqTypeSettingFragment.this.parametricEqualizer);
                ParametricEqTypeSettingFragment.this.defaultParamEqSettingsDialog.dismiss();
            }
        });
        AlertDialog create = new AlertDialog.Builder(getContext()).setView(inflate).create();
        this.defaultParamEqSettingsDialog = create;
        create.show();
    }

    public void updateView() {
        for (int i = 0; i < 10; i++) {
            FilterSetting filterSetting = this.parametricEqualizer.getFilterSettings().get(i);
            if (this.filterSwitchViews[i].isChecked()) {
                this.filterSectionSwitchOffText[i].setTextColor(getContext().getColor(R.color.settings_switch_off_color));
                this.filterSectionSwitchOnText[i].setTextColor(getContext().getColor(R.color.settings_switch_on_color));
            } else {
                this.filterSectionSwitchOffText[i].setTextColor(getContext().getColor(R.color.settings_switch_on_color));
                this.filterSectionSwitchOnText[i].setTextColor(getContext().getColor(R.color.settings_switch_off_color));
            }
            this.filterTypeValueViews[i].setText(this.filterTypeNameList.get(filterSetting.getFilterType() - 1));
            TextView textView = this.filterFrequencyViews[i];
            textView.setText(filterSetting.getFrequency() + "Hz");
            TextView textView2 = this.filterGainViews[i];
            textView2.setText(filterSetting.getGain() + "dB");
            TextView textView3 = this.filterFactorViews[i];
            textView3.setText(filterSetting.getFactor() + HttpUrl.FRAGMENT_ENCODE_SET);
        }
    }
}
