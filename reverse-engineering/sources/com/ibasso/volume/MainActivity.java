package com.ibasso.volume;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.window.embedding.EmbeddingCompat;
import com.ibasso.music.widgets.VolumeGradientView;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
public class MainActivity extends AppCompatActivity implements VolumeGradientView.OnProgressChangedListener {
    private static final String TAG = "UacManager";
    TextView mDeviceNameTv;
    ImageView mMainSettingsIv;
    ImageView mNavBarBackIv;
    private UacManager mUacManager;
    ImageView mVolumeAddIv;
    private VolumeGradientView mVolumeGradientView;
    ImageView mVolumeSubtractIv;

    private void setVolume(boolean z) {
        UacManager uacManager = this.mUacManager;
        if (uacManager != null) {
            uacManager.setVolume(z);
        }
    }

    private void updateView(int i) {
        if (i != -1) {
            this.mVolumeGradientView.setProgressValue(i);
        } else {
            this.mVolumeGradientView.setProgressValue(0);
        }
        UacManager uacManager = this.mUacManager;
        if (uacManager != null && uacManager.isConnected()) {
            this.mDeviceNameTv.setText(this.mUacManager.getProductName());
            return;
        }
        this.mVolumeGradientView.setProgressValue(-1);
        this.mDeviceNameTv.setText(R.string.no_device);
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_main);
        ImageView imageView = (ImageView) findViewById(R.id.main_settings_iv);
        this.mMainSettingsIv = imageView;
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                MainActivity.this.onMainSettingsClick();
            }
        });
        ImageView imageView2 = (ImageView) findViewById(R.id.nav_bar_back_iv);
        this.mNavBarBackIv = imageView2;
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                MainActivity.this.onNavBarBackClick();
            }
        });
        this.mDeviceNameTv = (TextView) findViewById(R.id.device_name_tv);
        this.mVolumeAddIv = (ImageView) findViewById(R.id.volume_add);
        this.mVolumeSubtractIv = (ImageView) findViewById(R.id.volume_subtract);
        this.mUacManager = UacManager.getUacManager();
        VolumeGradientView volumeGradientView = (VolumeGradientView) findViewById(R.id.volume_gradient_view);
        this.mVolumeGradientView = volumeGradientView;
        volumeGradientView.setMaxProgress(this.mUacManager.getMaxVolume());
        this.mVolumeGradientView.setOnProgressChangedListener(this);
        if (SpStorage.getInstance().getIsVolumeBtReverse()) {
            this.mVolumeAddIv.setImageDrawable(getResources().getDrawable(R.drawable.volume_subtract));
            this.mVolumeSubtractIv.setImageDrawable(getResources().getDrawable(R.drawable.volume_add));
            this.mVolumeAddIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public final void onClick(View view) {
                    MainActivity.this.mVolumeGradientView.subtractProgressValue();
                }
            });
            this.mVolumeSubtractIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public final void onClick(View view) {
                    MainActivity.this.mVolumeGradientView.addProgressValue();
                }
            });
        } else {
            this.mVolumeAddIv.setImageDrawable(getResources().getDrawable(R.drawable.volume_add));
            this.mVolumeSubtractIv.setImageDrawable(getResources().getDrawable(R.drawable.volume_subtract));
            this.mVolumeAddIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public final void onClick(View view) {
                    MainActivity.this.mVolumeGradientView.addProgressValue();
                }
            });
            this.mVolumeSubtractIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public final void onClick(View view) {
                    MainActivity.this.mVolumeGradientView.subtractProgressValue();
                }
            });
        }
        EventBus.getDefault().register(this);
        Log.i("UacManager", "onCreate");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i == 24 || i == 25) {
            if (i == 24) {
                setVolume(true);
                return true;
            } else if (i == 25) {
                setVolume(false);
                return true;
            }
        } else if (i == 4) {
            moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(i, keyEvent);
    }

    public void onMainSettingsClick() {
        startActivity(new Intent(this, SettingsActivity.class));
    }

    @Subscribe(sticky = EmbeddingCompat.DEBUG, threadMode = ThreadMode.MAIN)
    public void onMessageEvent(Integer num) {
        updateView(num.intValue());
    }

    public void onNavBarBackClick() {
        moveTaskToBack(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("UacManager", "onResume");
        this.mUacManager.enumerationUsb();
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i("UacManager", "onStart");
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void progressChanged(int i) {
        if (i > 0) {
            setVolume(true);
        } else if (i < 0) {
            setVolume(false);
        }
    }
}
