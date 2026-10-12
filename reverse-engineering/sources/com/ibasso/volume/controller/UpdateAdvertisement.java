package com.ibasso.volume.controller;

import android.util.Log;
import com.ibasso.volume.SpStorage;
import com.ibasso.volume.bean.Advertisement;
import com.ibasso.volume.bean.Device;
import java.util.Iterator;
import java.util.List;
import retrofit2.Response;
public class UpdateAdvertisement {
    private static final int LOAD_ERROR = -1;
    private static final int LOAD_SUCCESS = 1;
    private final String TAG = "UacManager";
    private String currentVersion;
    private String imageUrl;
    private String latestVersion;

    public void getPicture() {
        throw new UnsupportedOperationException("Method not decompiled: com.ibasso.volume.controller.UpdateAdvertisement.getPicture():void");
    }

    public void getLatestVersion(Response<List<Advertisement>> response) {
        this.currentVersion = SpStorage.getInstance().get("version", (Object) "0").toString();
        Log.i("UacManager", "currentVersion:" + this.currentVersion);
        if (response.body().size() != 0) {
            Advertisement advertisement = response.body().get(response.body().size() - 1);
            this.latestVersion = advertisement.getVersion();
            Log.i("UacManager", "latestVersion" + this.latestVersion);
            if (this.latestVersion.equals(this.currentVersion)) {
                return;
            }
            String screenSize = SpStorage.getInstance().getScreenSize();
            Iterator<Device> it = advertisement.getDevice().iterator();
            Device device = null;
            while (it.hasNext()) {
                device = it.next();
                if (screenSize.equals(device.getScreenSize())) {
                    break;
                }
            }
            if (device == null) {
                return;
            }
            SpStorage.getInstance().put("version", advertisement.getVersion());
            SpStorage.getInstance().put("endTime", advertisement.getEndTime());
            SpStorage.getInstance().put("httpUrl", advertisement.getHttpUrl());
            SpStorage.getInstance().put("screenSize", device.getScreenSize());
            SpStorage.getInstance().put("imgUrlEN", device.getImgUrl());
            this.imageUrl = device.getImgUrl();
            new Thread(new Runnable() {
                @Override
                public final void run() {
                    UpdateAdvertisement.this.getPicture();
                }
            }).start();
        }
    }
}
