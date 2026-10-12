package com.ibasso.volume.bean;

import java.util.List;
public class Advertisement {
    private List<Device> device;
    private String endTime;
    private String httpUrl;
    private String version;

    public List<Device> getDevice() {
        return this.device;
    }

    public String getEndTime() {
        return this.endTime;
    }

    public String getHttpUrl() {
        return this.httpUrl;
    }

    public String getVersion() {
        return this.version;
    }

    public void setDevice(List<Device> list) {
        this.device = list;
    }

    public void setEndTime(String str) {
        this.endTime = str;
    }

    public void setHttpUrl(String str) {
        this.httpUrl = str;
    }

    public void setVersion(String str) {
        this.version = str;
    }
}
