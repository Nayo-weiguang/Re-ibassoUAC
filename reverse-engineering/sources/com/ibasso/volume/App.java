package com.ibasso.volume;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import com.ibasso.volume.eq.EqualizerManager;
public class App extends Application {
    @SuppressLint({"StaticFieldLeak"})
    private static Context mContext;
    public static int starTimes;

    public static Context getContext() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        SpStorage.init(this);
        UacManager.init(this);
        EqualizerManager.init(this);
        mContext = this;
    }
}
