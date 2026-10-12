package com.ibasso.volume;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.CoroutineLiveDataKt;
import com.bumptech.glide.Glide;
import com.ibasso.utils.DateUtil;
import com.ibasso.volume.bean.Advertisement;
import com.ibasso.volume.controller.AdvertisementService;
import com.ibasso.volume.controller.UpdateAdvertisement;
import com.ibasso.volume.databinding.ActivitySplashBinding;
import java.io.File;
import java.util.List;
import java.util.Locale;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.OkHttpClient;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public final class SplashActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivitySplashBinding binding;
    @Nullable
    private String httpServer;
    @Nullable
    private String httpUrl;
    @NotNull
    private final SplashActivity$mHandler$1 mHandler = new Handler() {
        @Override
        public void handleMessage(@NotNull Message msg) {
            Intrinsics.checkNotNullParameter(msg, "msg");
            super.handleMessage(msg);
        }
    };
    @Nullable
    private CountDownTimer textTimer;
    @Nullable
    private Integer tickTime;

    private final void getAdvertisementList() {
        final long currentTimeMillis = System.currentTimeMillis();
        Retrofit.Builder client = new Retrofit.Builder().client(new OkHttpClient());
        String str = this.httpServer;
        Intrinsics.checkNotNull(str);
        Object create = client.baseUrl(str).addConverterFactory(GsonConverterFactory.create()).build().create(AdvertisementService.class);
        Intrinsics.checkNotNullExpressionValue(create, "retrofit.create(\n       …ice::class.java\n        )");
        Call<List<Advertisement>> advertisementList = ((AdvertisementService) create).getAdvertisementList();
        Intrinsics.checkNotNullExpressionValue(advertisementList, "ad.advertisementList");
        advertisementList.enqueue(new Callback<List<? extends Advertisement>>() {
            @Override
            public void onFailure(@NotNull Call<List<? extends Advertisement>> call, @NotNull Throwable t) {
                Intrinsics.checkNotNullParameter(call, "call");
                Intrinsics.checkNotNullParameter(t, "t");
                StringBuilder sb = new StringBuilder();
                sb.append(' ');
                sb.append(t);
                Log.i("UacManager", sb.toString());
            }

            @Override
            public void onResponse(@NotNull Call<List<? extends Advertisement>> call, @NotNull Response<List<? extends Advertisement>> response) {
                Intrinsics.checkNotNullParameter(call, "call");
                Intrinsics.checkNotNullParameter(response, "response");
                Log.i("UacManager", String.valueOf(response.body()));
                if (response.body() != null) {
                    new UpdateAdvertisement().getLatestVersion(response);
                    SpStorage.getInstance().setLastOpenTime(System.currentTimeMillis());
                    Log.i("UacManager", "use time: " + (System.currentTimeMillis() - currentTimeMillis));
                }
            }
        });
    }

    private final void verifyNetwork() {
        Object systemService = getApplicationContext().getSystemService("connectivity");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.net.ConnectivityManager");
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) systemService).getActiveNetworkInfo();
        if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
            Log.i("UacManager", "当前网络不可用");
            return;
        }
        Log.i("UacManager", "当前网络可用" + activeNetworkInfo);
        getAdvertisementList();
    }

    public final void adCountDown() {
        this.tickTime = 5;
        this.textTimer = new CountDownTimer() {
            {
                super(CoroutineLiveDataKt.DEFAULT_TIMEOUT, 1000L);
            }

            @Override
            public void onFinish() {
                SplashActivity.this.goMainActivity();
            }

            @Override
            @SuppressLint({"SetTextI18n"})
            public void onTick(long j) {
                ActivitySplashBinding activitySplashBinding;
                Integer num;
                Integer num2;
                activitySplashBinding = SplashActivity.this.binding;
                if (activitySplashBinding == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("binding");
                    activitySplashBinding = null;
                }
                Button button = activitySplashBinding.adDownTime;
                StringBuilder sb = new StringBuilder();
                sb.append("跳过 ");
                num = SplashActivity.this.tickTime;
                sb.append(num);
                sb.append("  s");
                button.setText(sb.toString());
                SplashActivity splashActivity = SplashActivity.this;
                num2 = splashActivity.tickTime;
                Intrinsics.checkNotNull(num2);
                splashActivity.tickTime = Integer.valueOf(num2.intValue() - 1);
            }
        }.start();
    }

    public final void goMainActivity() {
        SpStorage.getInstance().setLastOpenTime(System.currentTimeMillis());
        startActivity(new Intent(this, MainActivity2.class));
        overridePendingTransition(0, 0);
        finish();
    }

    @Override
    public void onClick(@Nullable View view) {
        Intrinsics.checkNotNull(view);
        switch (view.getId()) {
            case R.id.adDownTime:
                CountDownTimer countDownTimer = this.textTimer;
                Intrinsics.checkNotNull(countDownTimer);
                countDownTimer.cancel();
                goMainActivity();
                return;
            case R.id.adIv:
                if (Intrinsics.areEqual(this.httpUrl, "none")) {
                    return;
                }
                CountDownTimer countDownTimer2 = this.textTimer;
                Intrinsics.checkNotNull(countDownTimer2);
                countDownTimer2.cancel();
                Intent intent = new Intent(this, WebViewActivity.class);
                intent.putExtra("httpUrl", this.httpUrl);
                startActivity(intent);
                return;
            default:
                return;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        ActivitySplashBinding inflate = ActivitySplashBinding.inflate(getLayoutInflater());
        Intrinsics.checkNotNullExpressionValue(inflate, "inflate(layoutInflater)");
        this.binding = inflate;
        if (inflate == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            inflate = null;
        }
        setContentView(inflate.getRoot());
        Point point = new Point();
        getWindowManager().getDefaultDisplay().getRealSize(point);
        int i = point.x;
        int i2 = point.y;
        Locale locale = getResources().getConfiguration().locale;
        Intrinsics.checkNotNullExpressionValue(locale, "resources.configuration.locale");
        SpStorage spStorage = SpStorage.getInstance();
        StringBuilder sb = new StringBuilder();
        sb.append(i);
        sb.append('x');
        sb.append(i2);
        spStorage.setScreenSize(sb.toString());
        SpStorage.getInstance().setLanguage(locale.getLanguage());
        this.httpServer = "zh".equals(SpStorage.getInstance().getLanguage()) ? "http://ibasso.cn/ota/ad/" : "http://ibasso.com/ota/ad/";
    }

    @Override
    public void onResume() {
        super.onResume();
        App.starTimes++;
        long lastOpenTime = SpStorage.getInstance().getLastOpenTime();
        long currentTimeMillis = System.currentTimeMillis();
        long j = currentTimeMillis - lastOpenTime;
        if (App.starTimes != 1 || SpStorage.getInstance().get("version", (Object) "0").equals("0")) {
            goMainActivity();
        } else {
            long parseDateToMillis = DateUtil.parseDateToMillis((String) SpStorage.getInstance().get("endTime", (Object) "2022-01-01 20:00"));
            if (j < 43200000 && currentTimeMillis < parseDateToMillis) {
                this.httpUrl = (String) SpStorage.getInstance().get("httpUrl", (Object) "none");
                ActivitySplashBinding activitySplashBinding = null;
                if (new File(getApplicationContext().getCacheDir().getPath(), "adImage.jpg").exists()) {
                    adCountDown();
                    ActivitySplashBinding activitySplashBinding2 = this.binding;
                    if (activitySplashBinding2 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("binding");
                    } else {
                        activitySplashBinding = activitySplashBinding2;
                    }
                    ImageView imageView = activitySplashBinding.adIv;
                    if (imageView != null) {
                        Glide.with(imageView).load(getApplicationContext().getCacheDir().getPath() + "/adImage.jpg").centerCrop().into(imageView);
                    }
                } else {
                    ActivitySplashBinding activitySplashBinding3 = this.binding;
                    if (activitySplashBinding3 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("binding");
                    } else {
                        activitySplashBinding = activitySplashBinding3;
                    }
                    ImageView imageView2 = activitySplashBinding.adIv;
                    if (imageView2 != null) {
                        imageView2.setVisibility(8);
                    }
                    SpStorage.getInstance().put("version", "0");
                }
            }
        }
        if (j < 86400000) {
            verifyNetwork();
        }
    }
}
