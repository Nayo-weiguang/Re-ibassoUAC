package com.ibasso.volume.eq;

import java.util.ArrayList;
import java.util.List;
public class ParametricEqualizer {
    private String description;
    private List<FilterSetting> filterSettings;
    private String name;
    private int settingId;
    private int totalGain;

    public ParametricEqualizer() {
        this.totalGain = 0;
        this.filterSettings = new ArrayList();
        for (int i = 0; i < 10; i++) {
            this.filterSettings.add(new FilterSetting());
        }
    }

    public ParametricEqualizer(ParametricEqualizer parametricEqualizer) {
        this.totalGain = 0;
        this.filterSettings = new ArrayList();
        this.settingId = parametricEqualizer.settingId;
        this.name = parametricEqualizer.name;
        this.description = parametricEqualizer.description;
        this.totalGain = parametricEqualizer.totalGain;
        this.filterSettings = new ArrayList();
        for (FilterSetting filterSetting : parametricEqualizer.filterSettings) {
            this.filterSettings.add(new FilterSetting(filterSetting));
        }
    }

    public void copyNoId(ParametricEqualizer parametricEqualizer) {
        this.name = parametricEqualizer.name;
        this.description = parametricEqualizer.description;
        this.totalGain = parametricEqualizer.totalGain;
        int size = this.filterSettings.size();
        for (int i = 0; i < size; i++) {
            FilterSetting filterSetting = parametricEqualizer.filterSettings.get(i);
            FilterSetting filterSetting2 = this.filterSettings.get(i);
            filterSetting2.setFilterIndex(filterSetting.getFilterIndex());
            filterSetting2.setFilterType(filterSetting.getFilterType());
            filterSetting2.setFactor(filterSetting.getFactor());
            filterSetting2.setFrequency(filterSetting.getFrequency());
            filterSetting2.setGain(filterSetting.getGain());
        }
    }

    public String getDescription() {
        return this.description;
    }

    public List<FilterSetting> getFilterSettings() {
        return this.filterSettings;
    }

    public String getName() {
        return this.name;
    }

    public int getSettingId() {
        return this.settingId;
    }

    public int getTotalGain() {
        return this.totalGain;
    }

    public void setDescription(String str) {
        this.description = str;
    }

    public void setFilterSettings(List<FilterSetting> list) {
        this.filterSettings = list;
    }

    public void setName(String str) {
        this.name = str;
    }

    public void setSettingId(int i) {
        this.settingId = i;
    }

    public void setTotalGain(int i) {
        this.totalGain = i;
    }

    public String toString() {
        return "ParametricEqualizer{settingId='" + this.settingId + "'name='" + this.name + "'description='" + this.description + "'totalGain='" + this.totalGain + "', FilterSetting=" + this.filterSettings + '}';
    }
}
