package com.ibasso.volume.eq;
public class FilterSetting {
    private double factor;
    private int filterIndex;
    private int filterType;
    private int frequency;
    private double gain;

    public FilterSetting() {
    }

    public FilterSetting(FilterSetting filterSetting) {
        this.filterIndex = filterSetting.getFilterIndex();
        this.filterType = filterSetting.getFilterType();
        this.frequency = filterSetting.getFrequency();
        this.gain = filterSetting.getGain();
        this.factor = filterSetting.getFactor();
    }

    public FilterSetting m50clone() {
        try {
            return (FilterSetting) super.clone();
        } catch (CloneNotSupportedException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public double getFactor() {
        return this.factor;
    }

    public int getFilterIndex() {
        return this.filterIndex;
    }

    public int getFilterType() {
        return this.filterType;
    }

    public int getFrequency() {
        return this.frequency;
    }

    public double getGain() {
        return this.gain;
    }

    public void setFactor(double d2) {
        this.factor = d2;
    }

    public void setFilterIndex(int i) {
        this.filterIndex = i;
    }

    public void setFilterSetting(int i, boolean z, int i2, int i3, double d2, double d3) {
        this.filterIndex = i;
        this.filterType = i2;
        this.frequency = i3;
        this.gain = d2;
        this.factor = d3;
    }

    public void setFilterType(int i) {
        this.filterType = i;
    }

    public void setFrequency(int i) {
        this.frequency = i;
    }

    public void setGain(double d2) {
        this.gain = d2;
    }

    public String toString() {
        return "FilterSetting{filterIndex=" + this.filterIndex + ", filterType=" + this.filterType + ", frequency=" + this.frequency + ", gain=" + this.gain + ", factor=" + this.factor + '}';
    }
}
