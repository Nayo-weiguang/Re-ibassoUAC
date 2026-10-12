package com.ibasso.volume.eq;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.ibasso.volume.UacManager;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
public class EqualizerManager {
    private static EqualizerManager mEqualizerManager;
    private final Context mContext;
    private static final List<ParametricEqualizer> parametricEqualizers = new ArrayList();
    private static final List<ParametricEqualizer> sDefaultParametricEqualizers = new ArrayList();
    private static final List<ParametricEqualizer> customDefaultParametricEqualizers = new ArrayList();
    private int selectIndex = 0;
    private final Handler mHandler = new Handler();
    private final List<OnEqStatusChangeListener> listeners = new ArrayList();
    private final String CUSTOM_EQ_PRESET_PATH_NAME = "parametric_eq_preset_v4.json";
    private final String CUSTOM_EQ_SAVE_PATH_NAME = "parametric_eq_v4.json";
    private final ParametricEqualizerRunnable parametricEqualizerRunnable = new ParametricEqualizerRunnable(null);
    private final Runnable dumpFlash = new Runnable() {
        @Override
        public final void run() {
            UacManager.getUacManager().dumpFlash();
        }
    };
    private final Runnable eraseFlash = new Runnable() {
        @Override
        public final void run() {
            UacManager.getUacManager().eraseFlash();
        }
    };

    public interface OnEqStatusChangeListener {
        void onStatusChanged(int i);
    }

    public class ParametricEqualizerRunnable implements Runnable {
        private ParametricEqualizer parametricEqualizer;

        public ParametricEqualizerRunnable(ParametricEqualizer parametricEqualizer) {
            this.parametricEqualizer = parametricEqualizer;
        }

        @Override
        public void run() {
            if (this.parametricEqualizer != null) {
                UacManager.getUacManager().setParametricEqualizer(this.parametricEqualizer);
                EqualizerManager.this.saveCustomParametricEq();
            }
        }

        public void setParametricEqualizer(ParametricEqualizer parametricEqualizer) {
            this.parametricEqualizer = parametricEqualizer;
        }
    }

    private EqualizerManager(Context context) {
        this.mContext = context;
        String parametricEqJson = getParametricEqJson(context, "parametric_eq.json");
        JsonParser jsonParser = new JsonParser();
        Iterator<JsonElement> it = jsonParser.parse(parametricEqJson).getAsJsonArray().iterator();
        while (it.hasNext()) {
            Gson gson = new Gson();
            sDefaultParametricEqualizers.add((ParametricEqualizer) gson.fromJson(it.next(), (Class<Object>) ParametricEqualizer.class));
        }
        File file = new File(this.mContext.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), "parametric_eq_preset_v4.json");
        if (!file.exists()) {
            copyFileFromAssetsToDownloads(this.mContext, "parametric_eq_custom.json", "parametric_eq_preset_v4.json");
        }
        if (file.exists()) {
            try {
                Iterator<JsonElement> it2 = getJsonElements(file, jsonParser).iterator();
                while (it2.hasNext()) {
                    Gson gson2 = new Gson();
                    customDefaultParametricEqualizers.add((ParametricEqualizer) gson2.fromJson(it2.next(), (Class<Object>) ParametricEqualizer.class));
                }
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
        File file2 = new File(this.mContext.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), "parametric_eq_v4.json");
        if (!file2.exists()) {
            for (ParametricEqualizer parametricEqualizer : sDefaultParametricEqualizers) {
                parametricEqualizers.add(new ParametricEqualizer(parametricEqualizer));
            }
            return;
        }
        try {
            Iterator<JsonElement> it3 = getJsonElements(file2, jsonParser).iterator();
            while (it3.hasNext()) {
                Gson gson3 = new Gson();
                parametricEqualizers.add((ParametricEqualizer) gson3.fromJson(it3.next(), (Class<Object>) ParametricEqualizer.class));
            }
        } catch (IOException e3) {
            e3.printStackTrace();
            for (ParametricEqualizer parametricEqualizer2 : sDefaultParametricEqualizers) {
                parametricEqualizers.add(new ParametricEqualizer(parametricEqualizer2));
            }
        }
    }

    public static EqualizerManager getInstance() {
        return mEqualizerManager;
    }

    private static JsonArray getJsonElements(File file, JsonParser jsonParser) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        StringBuilder sb = new StringBuilder();
        while (true) {
            String readLine = bufferedReader.readLine();
            if (readLine == null) {
                bufferedReader.close();
                return jsonParser.parse(sb.toString()).getAsJsonArray();
            }
            sb.append(readLine);
        }
    }

    public static synchronized EqualizerManager init(Context context) {
        EqualizerManager equalizerManager;
        synchronized (EqualizerManager.class) {
            try {
                if (mEqualizerManager == null) {
                    mEqualizerManager = new EqualizerManager(context);
                }
                equalizerManager = mEqualizerManager;
            } catch (Throwable th) {
                throw th;
            }
        }
        return equalizerManager;
    }

    public void saveCustomParametricEq() {
        try {
            File file = new File(this.mContext.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), "parametric_eq_v4.json");
            String json = new Gson().toJson(parametricEqualizers);
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(json);
            fileWriter.close();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    public void addEqStatusChangeListener(OnEqStatusChangeListener onEqStatusChangeListener) {
        synchronized (this.listeners) {
            this.listeners.add(onEqStatusChangeListener);
        }
    }

    public void copyFileFromAssetsToDownloads(Context context, String str, String str2) {
        AssetManager assets = context.getAssets();
        File externalFilesDir = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
        if (externalFilesDir != null && !externalFilesDir.exists()) {
            externalFilesDir.mkdirs();
        }
        File file = new File(externalFilesDir, str2);
        try {
            InputStream open = assets.open(str);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            byte[] bArr = new byte[1024];
            while (true) {
                int read = open.read(bArr);
                if (read == -1) {
                    fileOutputStream.close();
                    open.close();
                    return;
                }
                fileOutputStream.write(bArr, 0, read);
            }
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    public void dumpFlash() {
        this.mHandler.removeCallbacks(this.dumpFlash);
        this.mHandler.postDelayed(this.dumpFlash, 2000L);
    }

    public void eraseFlash() {
        parametricEqualizers.clear();
        for (ParametricEqualizer parametricEqualizer : sDefaultParametricEqualizers) {
            parametricEqualizers.add(new ParametricEqualizer(parametricEqualizer));
        }
        this.mHandler.removeCallbacks(this.eraseFlash);
        this.mHandler.postDelayed(this.eraseFlash, 500L);
    }

    public List<ParametricEqualizer> getCustomDefaultParametricEqualizers() {
        return customDefaultParametricEqualizers;
    }

    public ParametricEqualizer getDefaultParametricEqualizerByIndex(int i) {
        this.selectIndex = i;
        return sDefaultParametricEqualizers.get(i);
    }

    public String getParametricEqJson(Context context, String str) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(context.getAssets().open(str)));
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    break;
                }
                sb.append(readLine);
            }
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        return sb.toString();
    }

    public int getParametricEqSelectIndex() {
        return this.selectIndex;
    }

    public ParametricEqualizer getParametricEqualizerByIndex(int i) {
        this.selectIndex = i;
        return parametricEqualizers.get(i);
    }

    public void notifyEqStatusChangeListener(int i) {
        synchronized (this.listeners) {
            try {
                for (OnEqStatusChangeListener onEqStatusChangeListener : this.listeners) {
                    onEqStatusChangeListener.onStatusChanged(i);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void removeEqStatusChangeListener(OnEqStatusChangeListener onEqStatusChangeListener) {
        synchronized (this.listeners) {
            this.listeners.remove(onEqStatusChangeListener);
        }
    }

    public void resetCurrentParamEq() {
        List<FilterSetting> filterSettings = sDefaultParametricEqualizers.get(this.selectIndex).getFilterSettings();
        List<FilterSetting> filterSettings2 = parametricEqualizers.get(this.selectIndex).getFilterSettings();
        int size = filterSettings.size();
        for (int i = 0; i < size; i++) {
            FilterSetting filterSetting = filterSettings.get(i);
            FilterSetting filterSetting2 = filterSettings2.get(i);
            filterSetting2.setFilterIndex(filterSetting.getFilterIndex());
            filterSetting2.setFilterType(filterSetting.getFilterType());
            filterSetting2.setFactor(filterSetting.getFactor());
            filterSetting2.setFrequency(filterSetting.getFrequency());
            filterSetting2.setGain(filterSetting.getGain());
        }
        notifyEqStatusChangeListener(0);
        saveCustomParametricEq();
    }

    public void setParametricEqSelectIndex(int i) {
        this.selectIndex = i;
    }

    public void updateDeviceEqToCache(int i, ParametricEqualizer parametricEqualizer) {
        ParametricEqualizer parametricEqualizer2 = parametricEqualizers.get(i);
        parametricEqualizer2.setTotalGain(parametricEqualizer.getTotalGain());
        List<FilterSetting> filterSettings = parametricEqualizer.getFilterSettings();
        List<FilterSetting> filterSettings2 = parametricEqualizer2.getFilterSettings();
        int size = filterSettings.size();
        for (int i2 = 0; i2 < size; i2++) {
            FilterSetting filterSetting = filterSettings.get(i2);
            FilterSetting filterSetting2 = filterSettings2.get(i2);
            filterSetting2.setFilterIndex(filterSetting.getFilterIndex());
            filterSetting2.setFilterType(filterSetting.getFilterType());
            filterSetting2.setFactor(filterSetting.getFactor());
            filterSetting2.setFrequency(filterSetting.getFrequency());
            filterSetting2.setGain(filterSetting.getGain());
        }
    }

    public void updateParamEq(ParametricEqualizer parametricEqualizer) {
        Log.d(getClass().getName(), parametricEqualizer.toString());
        this.mHandler.removeCallbacks(this.parametricEqualizerRunnable);
        this.parametricEqualizerRunnable.setParametricEqualizer(parametricEqualizer);
        this.mHandler.postDelayed(this.parametricEqualizerRunnable, 500L);
    }
}
