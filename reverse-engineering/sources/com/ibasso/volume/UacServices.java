package com.ibasso.volume;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.UiModeManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Build;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyCallback;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.RemoteViews;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.window.embedding.EmbeddingCompat;
import java.util.concurrent.Executor;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
public class UacServices extends Service {
    private static final String ACTION_VOLUME_DOWN = "action_volume_down";
    private static final String ACTION_VOLUME_UP = "action_volume_up";
    private static final String CHANNEL_ID = "com.ibasso.volume";
    private static final int NOTIFICATION_ID = 808;
    private static final String description = "";
    private static final CharSequence name = "notification";
    private CallStateListener callStateListener;
    PendingIntent contentIntent;
    private boolean isShowingNotification;
    private AudioManager mAudioManager;
    private int mMusicMaxVolume;
    private int mNotificationMaxVolume;
    private int mRingMaxVolume;
    private int mSystemMaxVolume;
    private UacManager mUacManager;
    private int mVoiceCallMaxVolume;
    private MyVolumeReceiver mVolumeReceiver;
    private Notification notification;
    NotificationChannel notificationChannel;
    NotificationManager notificationManager;
    private PhoneStateListener phoneStateListener;
    private final String TAG = "UacManager";
    private boolean callStateListenerRegistered = false;

    @RequiresApi(api = 31)
    public static abstract class CallStateListener extends TelephonyCallback implements TelephonyCallback.CallStateListener {
        private CallStateListener() {
        }

        public abstract void onCallStateChanged(int i);
    }

    public class MyVolumeReceiver extends BroadcastReceiver {
        private MyVolumeReceiver() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            if ("android.media.VOLUME_CHANGED_ACTION".equals(intent.getAction())) {
                int streamVolume = UacServices.this.mAudioManager.getStreamVolume(1);
                int streamVolume2 = UacServices.this.mAudioManager.getStreamVolume(3);
                int streamVolume3 = UacServices.this.mAudioManager.getStreamVolume(5);
                int streamVolume4 = UacServices.this.mAudioManager.getStreamVolume(0);
                int streamVolume5 = UacServices.this.mAudioManager.getStreamVolume(2);
                Log.i("UacManager", "ringVolume:" + streamVolume5 + "/" + UacServices.this.mRingMaxVolume + " systemVolume:" + streamVolume + "/" + UacServices.this.mSystemMaxVolume + " notificationVolume:" + streamVolume3 + "/" + UacServices.this.mNotificationMaxVolume + " voice_call:" + streamVolume4 + "/" + UacServices.this.mVoiceCallMaxVolume + " musicMax:" + streamVolume2 + "/" + UacServices.this.mMusicMaxVolume);
            }
        }
    }

    public UacServices() {
        int i = Build.VERSION.SDK_INT;
        this.callStateListener = i >= 31 ? new CallStateListener() {
            @Override
            public void onCallStateChanged(int i2) {
                if (i2 == 0) {
                    Log.d("UacManager", "call idle");
                } else if (i2 == 1) {
                    Log.d("UacManager", "call in");
                    UacServices.this.resetVolumeOnCall();
                } else if (i2 != 2) {
                } else {
                    Log.d("UacManager", "call out");
                    UacServices.this.resetVolumeOnCall();
                }
            }
        } : null;
        this.phoneStateListener = i < 31 ? new PhoneStateListener() {
            @Override
            public void onCallStateChanged(int i2, String str) {
                super.onCallStateChanged(i2, str);
                if (i2 == 0) {
                    Log.d("UacManager", "call idle");
                } else if (i2 == 1) {
                    Log.d("UacManager", "call in");
                    UacServices.this.resetVolumeOnCall();
                } else if (i2 != 2) {
                } else {
                    Log.d("UacManager", "call out");
                    UacServices.this.resetVolumeOnCall();
                }
            }
        } : null;
        this.isShowingNotification = false;
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT < 26 || this.notificationChannel != null) {
            return;
        }
        this.notificationManager = (NotificationManager) getSystemService("notification");
        androidx.core.app.C.a();
        NotificationChannel a2 = androidx.core.app.B.a(CHANNEL_ID, name, 2);
        this.notificationChannel = a2;
        this.notificationManager.createNotificationChannel(a2);
    }

    private RemoteViews getBigContentView(int i) {
        RemoteViews remoteViews = new RemoteViews(getPackageName(), (int) R.layout.notification_larger);
        setUpRemoteView(remoteViews);
        if (((UiModeManager) getSystemService("uimode")).getNightMode() == 2) {
            remoteViews.setTextColor(R.id.current_volume_tv, getResources().getColor(R.color.color_light));
        }
        remoteViews.setTextViewText(R.id.current_volume_tv, "" + i);
        return remoteViews;
    }

    private PendingIntent getPendingIntent(String str) {
        Intent intent = new Intent(str);
        intent.setClassName(this, UacServices.class.getName());
        return PendingIntent.getService(this, 0, intent, Build.VERSION.SDK_INT >= 31 ? 67108864 : 0);
    }

    private void myRegisterReceiver() {
        if (this.mVolumeReceiver == null) {
            this.mVolumeReceiver = new MyVolumeReceiver();
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.media.VOLUME_CHANGED_ACTION");
            ContextCompat.registerReceiver(this, this.mVolumeReceiver, intentFilter, 2);
        }
    }

    private void registerCallStateListener() {
        Executor mainExecutor;
        if (this.callStateListenerRegistered) {
            return;
        }
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService("phone");
        if (Build.VERSION.SDK_INT < 31) {
            telephonyManager.listen(this.phoneStateListener, 32);
            this.callStateListenerRegistered = true;
        } else if (ContextCompat.checkSelfPermission(this, "android.permission.READ_PHONE_STATE") == 0) {
            mainExecutor = getMainExecutor();
            telephonyManager.registerTelephonyCallback(mainExecutor, this.callStateListener);
            this.callStateListenerRegistered = true;
        }
    }

    public void resetVolumeOnCall() {
        if (!this.mUacManager.isConnected() || this.mUacManager.getVolume() <= SpStorage.getInstance().getMaxVoiceVolume()) {
            return;
        }
        this.mUacManager.setVolume(SpStorage.getInstance().getMaxVoiceVolume());
    }

    private void setUpRemoteView(RemoteViews remoteViews) {
        remoteViews.setOnClickPendingIntent(R.id.volume_down_layout, getPendingIntent(ACTION_VOLUME_DOWN));
        remoteViews.setOnClickPendingIntent(R.id.volume_up_layout, getPendingIntent(ACTION_VOLUME_UP));
        remoteViews.setOnClickPendingIntent(R.id.volume_up, getPendingIntent(ACTION_VOLUME_UP));
        remoteViews.setOnClickPendingIntent(R.id.volume_up, getPendingIntent(ACTION_VOLUME_UP));
    }

    private void showNotification(int i) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);
        if (i == -100) {
            if (this.isShowingNotification) {
                stopForeground(1);
                this.isShowingNotification = false;
                return;
            }
            builder.setSmallIcon(R.drawable.ic_notification).setShowWhen(false).setContentIntent(this.contentIntent).setPriority(-1).setOngoing(false);
            Notification build = builder.build();
            this.notification = build;
            if (this.isShowingNotification) {
                this.notificationManager.notify(NOTIFICATION_ID, build);
            } else if (Build.VERSION.SDK_INT >= 33) {
                startForeground(NOTIFICATION_ID, build, 16);
            } else {
                startForeground(NOTIFICATION_ID, build);
            }
            this.isShowingNotification = true;
        } else if (i == -1) {
            if (this.isShowingNotification) {
                stopForeground(1);
                this.isShowingNotification = false;
            }
        } else {
            builder.setSmallIcon(R.drawable.ic_notification).setShowWhen(false).setStyle(new NotificationCompat.DecoratedCustomViewStyle()).setContentIntent(this.contentIntent).setCustomContentView(getBigContentView(i)).setPriority(-1).setOngoing(false);
            Notification build2 = builder.build();
            this.notification = build2;
            if (this.isShowingNotification) {
                this.notificationManager.notify(NOTIFICATION_ID, build2);
            } else if (Build.VERSION.SDK_INT >= 33) {
                startForeground(NOTIFICATION_ID, build2, 16);
            } else {
                startForeground(NOTIFICATION_ID, build2);
            }
            this.isShowingNotification = true;
        }
    }

    private void updateNotification(int i) {
        createNotificationChannel();
        showNotification(i);
    }

    @Override
    @Nullable
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.mUacManager = UacManager.getUacManager();
        EventBus.getDefault().register(this);
        Log.i("UacManager", "UacServices onCreate");
        AudioManager audioManager = (AudioManager) getSystemService("audio");
        this.mAudioManager = audioManager;
        this.mSystemMaxVolume = audioManager.getStreamMaxVolume(1);
        this.mMusicMaxVolume = this.mAudioManager.getStreamMaxVolume(3);
        this.mNotificationMaxVolume = this.mAudioManager.getStreamMaxVolume(5);
        this.mVoiceCallMaxVolume = this.mAudioManager.getStreamMaxVolume(0);
        this.mRingMaxVolume = this.mAudioManager.getStreamMaxVolume(2);
        this.contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity2.class), Build.VERSION.SDK_INT >= 31 ? 67108864 : 0);
        registerCallStateListener();
        myRegisterReceiver();
    }

    @Subscribe(sticky = EmbeddingCompat.DEBUG, threadMode = ThreadMode.MAIN)
    public void onDc06VolumeEvent(Dc06VolumeEvent dc06VolumeEvent) {
        Log.i("UacManager", "onNormalVolumeEvent:" + dc06VolumeEvent.volume);
        updateNotification(dc06VolumeEvent.volume);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("UacManager", "service onDestroy");
        MyVolumeReceiver myVolumeReceiver = this.mVolumeReceiver;
        if (myVolumeReceiver != null) {
            unregisterReceiver(myVolumeReceiver);
            this.mVolumeReceiver = null;
        }
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(sticky = EmbeddingCompat.DEBUG, threadMode = ThreadMode.MAIN)
    public void onMaxVoiceVolumeEvent(MaxVoiceVolume maxVoiceVolume) {
    }

    @Subscribe(sticky = EmbeddingCompat.DEBUG, threadMode = ThreadMode.MAIN)
    public void onNormalVolumeEvent(NormalVolumeEvent normalVolumeEvent) {
        Log.i("UacManager", "onNormalVolumeEvent#:" + normalVolumeEvent.volume);
        updateNotification(normalVolumeEvent.volume);
    }

    @Subscribe(sticky = EmbeddingCompat.DEBUG, threadMode = ThreadMode.MAIN)
    public void onResponseEvent(ResponseEvent responseEvent) {
        UacManager.getUacManager().parseCallBackValue(responseEvent);
    }

    @Override
    public int onStartCommand(Intent intent, int i, int i2) {
        Log.i("UacManager", "intent:" + intent);
        if (intent != null) {
            String action = intent.getAction();
            if (ACTION_VOLUME_DOWN.equals(action)) {
                this.mUacManager.setVolume(false);
            } else if (ACTION_VOLUME_UP.equals(action)) {
                this.mUacManager.setVolume(true);
            } else {
                updateNotification(-100);
            }
        }
        return 1;
    }
}
