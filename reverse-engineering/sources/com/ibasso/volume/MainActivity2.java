package com.ibasso.volume;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.ActivityKt;
import androidx.navigation.NavDestination;
import androidx.window.embedding.EmbeddingCompat;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.ibasso.volume.BackHandlerHelper;
import com.ibasso.volume.databinding.ActivityMain2Binding;
import com.ibasso.volume.eq.ParametricEqTypeSettingFragment;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.PermissionRequest;
public final class MainActivity2 extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {
    @Nullable
    private BackHandlerHelper.BackCallbackWrapper backCallback;
    private ActivityMain2Binding binding;
    private UacManager mUacManager;
    @Nullable
    private String productName;
    @NotNull
    private final String TAG = "UacManager";
    private final int RC_PHONE_STATE = 500;
    @NotNull
    private final String[] perms = {"android.permission.READ_PHONE_STATE", "android.permission.RECORD_AUDIO"};

    public static final void onCreate$lambda$0(MainActivity2 this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.onMainSettingsClick();
    }

    private final void onMainSettingsClick() {
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            if (Intrinsics.areEqual(fragment.getClass().getSimpleName(), "NavHostFragment")) {
                List<Fragment> fragments = fragment.getChildFragmentManager().getFragments();
                Intrinsics.checkNotNullExpressionValue(fragments, "fragment.childFragmentManager.fragments");
                if (fragments.isEmpty()) {
                    continue;
                } else {
                    List<Fragment> fragments2 = fragment.getChildFragmentManager().getFragments();
                    Intrinsics.checkNotNullExpressionValue(fragments2, "fragment.childFragmentManager.fragments");
                    Fragment fragment2 = (Fragment) CollectionsKt.last((List<? extends Object>) fragments2);
                    List<Fragment> fragments3 = fragment2.getChildFragmentManager().getFragments();
                    Intrinsics.checkNotNullExpressionValue(fragments3, "displayFragment.childFragmentManager.fragments");
                    if (fragments3.isEmpty()) {
                        continue;
                    } else {
                        List<Fragment> fragments4 = fragment2.getChildFragmentManager().getFragments();
                        Intrinsics.checkNotNullExpressionValue(fragments4, "displayFragment.childFragmentManager.fragments");
                        Fragment fragment3 = (Fragment) CollectionsKt.last((List<? extends Object>) fragments4);
                        fragment3.isVisible();
                        if (fragment3 instanceof ParametricEqTypeSettingFragment) {
                            ((ParametricEqTypeSettingFragment) fragment3).showEqMenu();
                            return;
                        }
                    }
                }
            }
        }
        startActivity(new Intent(this, SettingsActivity.class));
    }

    private final void setVolume(boolean z) {
        UacManager uacManager = this.mUacManager;
        if (uacManager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mUacManager");
            uacManager = null;
        }
        uacManager.setVolume(z);
    }

    public final boolean navigateToProduct() {
        setDefaultBackground();
        if (Intrinsics.areEqual(UacManager.DC06, this.productName)) {
            NavDestination currentDestination = ActivityKt.findNavController(this, R.id.nav_host_fragment_content_main).getCurrentDestination();
            if (currentDestination == null || currentDestination.getId() != R.id.Dc06Fragment) {
                ActivityKt.findNavController(this, R.id.nav_host_fragment_content_main).navigate(R.id.action_NormalDcFragment_to_Dc06Fragment);
            }
        } else if (Intrinsics.areEqual(UacManager.DC03PRO, this.productName)) {
            NavDestination currentDestination2 = ActivityKt.findNavController(this, R.id.nav_host_fragment_content_main).getCurrentDestination();
            if (currentDestination2 == null || currentDestination2.getId() != R.id.Dc03ProFragment) {
                ActivityKt.findNavController(this, R.id.nav_host_fragment_content_main).navigate(R.id.action_NormalDcFragment_to_Dc03ProFragment);
            }
        } else if (Intrinsics.areEqual(UacManager.DC04PRO, this.productName)) {
            NavDestination currentDestination3 = ActivityKt.findNavController(this, R.id.nav_host_fragment_content_main).getCurrentDestination();
            if (currentDestination3 == null || currentDestination3.getId() != R.id.Dc04ProFragment) {
                ActivityKt.findNavController(this, R.id.nav_host_fragment_content_main).navigate(R.id.action_NormalDcFragment_to_Dc04ProFragment);
            }
        } else if (Intrinsics.areEqual(UacManager.DC08, this.productName)) {
            NavDestination currentDestination4 = ActivityKt.findNavController(this, R.id.nav_host_fragment_content_main).getCurrentDestination();
            if (currentDestination4 == null || currentDestination4.getId() != R.id.Dc08Fragment) {
                ActivityKt.findNavController(this, R.id.nav_host_fragment_content_main).navigate(R.id.action_NormalDcFragment_to_Dc08Fragment);
            }
        } else if (Intrinsics.areEqual(UacManager.DC06PRO, this.productName)) {
            NavDestination currentDestination5 = ActivityKt.findNavController(this, R.id.nav_host_fragment_content_main).getCurrentDestination();
            if (currentDestination5 == null || currentDestination5.getId() != R.id.Dc06ProFragment) {
                ActivityKt.findNavController(this, R.id.nav_host_fragment_content_main).navigate(R.id.action_NormalDcFragment_to_Dc06ProFragment);
            }
        } else if (Intrinsics.areEqual(UacManager.DC07, this.productName)) {
            NavDestination currentDestination6 = ActivityKt.findNavController(this, R.id.nav_host_fragment_content_main).getCurrentDestination();
            if (currentDestination6 == null || currentDestination6.getId() != R.id.Dc07Fragment) {
                ActivityKt.findNavController(this, R.id.nav_host_fragment_content_main).navigate(R.id.action_NormalDcFragment_to_Dc07Fragment);
            }
        } else if (Intrinsics.areEqual(UacManager.DC07PRO, this.productName)) {
            NavDestination currentDestination7 = ActivityKt.findNavController(this, R.id.nav_host_fragment_content_main).getCurrentDestination();
            if (currentDestination7 == null || currentDestination7.getId() != R.id.Dc07ProFragment) {
                ActivityKt.findNavController(this, R.id.nav_host_fragment_content_main).navigate(R.id.action_NormalDcFragment_to_Dc07ProFragment);
            }
        } else if (Intrinsics.areEqual(UacManager.DC09, this.productName)) {
            NavDestination currentDestination8 = ActivityKt.findNavController(this, R.id.nav_host_fragment_content_main).getCurrentDestination();
            if (currentDestination8 == null || currentDestination8.getId() != R.id.Dc09Fragment) {
                ActivityKt.findNavController(this, R.id.nav_host_fragment_content_main).navigate(R.id.action_NormalDcFragment_to_Dc09Fragment);
            }
        } else if (Intrinsics.areEqual(UacManager.DC04U, this.productName)) {
            NavDestination currentDestination9 = ActivityKt.findNavController(this, R.id.nav_host_fragment_content_main).getCurrentDestination();
            if (currentDestination9 == null || currentDestination9.getId() != R.id.Dc04uFragment) {
                ActivityKt.findNavController(this, R.id.nav_host_fragment_content_main).navigate(R.id.action_NormalDcFragment_to_Dc04uFragment);
            }
        } else if (Intrinsics.areEqual(UacManager.Macchiato, this.productName)) {
            NavDestination currentDestination10 = ActivityKt.findNavController(this, R.id.nav_host_fragment_content_main).getCurrentDestination();
            if (currentDestination10 == null || currentDestination10.getId() != R.id.MacchiatoFragment) {
                ActivityKt.findNavController(this, R.id.nav_host_fragment_content_main).navigate(R.id.action_NormalDcFragment_to_MacchiatoFragment);
            }
        } else if (Intrinsics.areEqual(UacManager.D18, this.productName)) {
            NavDestination currentDestination11 = ActivityKt.findNavController(this, R.id.nav_host_fragment_content_main).getCurrentDestination();
            if (currentDestination11 == null || currentDestination11.getId() != R.id.D18Fragment) {
                ActivityKt.findNavController(this, R.id.nav_host_fragment_content_main).navigate(R.id.action_NormalDcFragment_to_D18Fragment);
            }
        } else if (Intrinsics.areEqual(UacManager.TONFA, this.productName)) {
            NavDestination currentDestination12 = ActivityKt.findNavController(this, R.id.nav_host_fragment_content_main).getCurrentDestination();
            if (currentDestination12 == null || currentDestination12.getId() != R.id.TonfaFragment) {
                ActivityKt.findNavController(this, R.id.nav_host_fragment_content_main).navigate(R.id.action_NormalDcFragment_to_TonfaFragment);
            }
        } else {
            NavDestination currentDestination13 = ActivityKt.findNavController(this, R.id.nav_host_fragment_content_main).getCurrentDestination();
            if (currentDestination13 == null || currentDestination13.getId() != R.id.Dc06Fragment) {
                NavDestination currentDestination14 = ActivityKt.findNavController(this, R.id.nav_host_fragment_content_main).getCurrentDestination();
                if (currentDestination14 == null || currentDestination14.getId() != R.id.Dc03ProFragment) {
                    NavDestination currentDestination15 = ActivityKt.findNavController(this, R.id.nav_host_fragment_content_main).getCurrentDestination();
                    if (currentDestination15 == null || currentDestination15.getId() != R.id.Dc04ProFragment) {
                        NavDestination currentDestination16 = ActivityKt.findNavController(this, R.id.nav_host_fragment_content_main).getCurrentDestination();
                        if (currentDestination16 == null || currentDestination16.getId() != R.id.Dc08Fragment) {
                            NavDestination currentDestination17 = ActivityKt.findNavController(this, R.id.nav_host_fragment_content_main).getCurrentDestination();
                            if (currentDestination17 == null || currentDestination17.getId() != R.id.Dc06ProFragment) {
                                NavDestination currentDestination18 = ActivityKt.findNavController(this, R.id.nav_host_fragment_content_main).getCurrentDestination();
                                if (currentDestination18 == null || currentDestination18.getId() != R.id.Dc07Fragment) {
                                    NavDestination currentDestination19 = ActivityKt.findNavController(this, R.id.nav_host_fragment_content_main).getCurrentDestination();
                                    if (currentDestination19 == null || currentDestination19.getId() != R.id.Dc07ProFragment) {
                                        NavDestination currentDestination20 = ActivityKt.findNavController(this, R.id.nav_host_fragment_content_main).getCurrentDestination();
                                        if (currentDestination20 == null || currentDestination20.getId() != R.id.Dc09Fragment) {
                                            NavDestination currentDestination21 = ActivityKt.findNavController(this, R.id.nav_host_fragment_content_main).getCurrentDestination();
                                            if (currentDestination21 == null || currentDestination21.getId() != R.id.Dc04uFragment) {
                                                NavDestination currentDestination22 = ActivityKt.findNavController(this, R.id.nav_host_fragment_content_main).getCurrentDestination();
                                                if (currentDestination22 == null || currentDestination22.getId() != R.id.MacchiatoFragment) {
                                                    NavDestination currentDestination23 = ActivityKt.findNavController(this, R.id.nav_host_fragment_content_main).getCurrentDestination();
                                                    if (currentDestination23 == null || currentDestination23.getId() != R.id.D18Fragment) {
                                                        NavDestination currentDestination24 = ActivityKt.findNavController(this, R.id.nav_host_fragment_content_main).getCurrentDestination();
                                                        if (currentDestination24 != null && currentDestination24.getId() == R.id.TonfaFragment) {
                                                            ActivityKt.findNavController(this, R.id.nav_host_fragment_content_main).navigate(R.id.action_TonfaFragment_to_NormalDcFragment);
                                                        }
                                                    } else {
                                                        ActivityKt.findNavController(this, R.id.nav_host_fragment_content_main).navigate(R.id.action_D18Fragment_to_NormalDcFragment);
                                                    }
                                                } else {
                                                    ActivityKt.findNavController(this, R.id.nav_host_fragment_content_main).navigate(R.id.action_MacchiatoFragment_to_NormalDcFragment);
                                                }
                                            } else {
                                                ActivityKt.findNavController(this, R.id.nav_host_fragment_content_main).navigate(R.id.action_Dc04uFragment_to_NormalDcFragment);
                                            }
                                        } else {
                                            ActivityKt.findNavController(this, R.id.nav_host_fragment_content_main).navigate(R.id.action_Dc09Fragment_to_NormalDcFragment);
                                        }
                                    } else {
                                        ActivityKt.findNavController(this, R.id.nav_host_fragment_content_main).navigate(R.id.action_Dc07ProFragment_to_NormalDcFragment);
                                    }
                                } else {
                                    ActivityKt.findNavController(this, R.id.nav_host_fragment_content_main).navigate(R.id.action_Dc07Fragment_to_NormalDcFragment);
                                }
                            } else {
                                ActivityKt.findNavController(this, R.id.nav_host_fragment_content_main).navigate(R.id.action_Dc06ProFragment_to_NormalDcFragment);
                            }
                        } else {
                            ActivityKt.findNavController(this, R.id.nav_host_fragment_content_main).navigate(R.id.action_Dc08Fragment_to_NormalDcFragment);
                        }
                    } else {
                        ActivityKt.findNavController(this, R.id.nav_host_fragment_content_main).navigate(R.id.action_Dc04ProFragment_to_NormalDcFragment);
                    }
                } else {
                    ActivityKt.findNavController(this, R.id.nav_host_fragment_content_main).navigate(R.id.action_Dc03ProFragment_to_NormalDcFragment);
                }
            } else {
                ActivityKt.findNavController(this, R.id.nav_host_fragment_content_main).navigate(R.id.action_Dc06Fragment_to_NormalDcFragment);
            }
        }
        resetSettingIv();
        return true;
    }

    @Override
    public void onCreate(@Nullable Bundle bundle) {
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        super.onCreate(bundle);
        this.backCallback = BackHandlerHelper.Companion.registerBackHandler(this, new Function0<Unit>() {
            @Override
            public Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            public final void invoke2() {
                Log.d("BackPressed", "拦截返回键 - Activity");
            }
        });
        if (!EasyPermissions.hasPermissions(this, "android.permission.READ_PHONE_STATE", "android.permission.RECORD_AUDIO", "android.permission.POST_NOTIFICATIONS")) {
            EasyPermissions.requestPermissions(new PermissionRequest.Builder(this, this.RC_PHONE_STATE, "android.permission.READ_PHONE_STATE", "android.permission.RECORD_AUDIO", "android.permission.POST_NOTIFICATIONS").setRationale(getString(R.string.phone_state_rationale)).setTheme(R.style.permissionStyle).build());
        }
        ActivityMain2Binding inflate = ActivityMain2Binding.inflate(getLayoutInflater());
        Intrinsics.checkNotNullExpressionValue(inflate, "inflate(layoutInflater)");
        this.binding = inflate;
        ActivityMain2Binding activityMain2Binding = null;
        if (inflate == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            inflate = null;
        }
        setContentView(inflate.getRoot());
        UacManager uacManager = UacManager.getUacManager();
        Intrinsics.checkNotNullExpressionValue(uacManager, "getUacManager()");
        this.mUacManager = uacManager;
        ActivityMain2Binding activityMain2Binding2 = this.binding;
        if (activityMain2Binding2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            activityMain2Binding = activityMain2Binding2;
        }
        activityMain2Binding.mainSettingsIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                MainActivity2.onCreate$lambda$0(MainActivity2.this, view);
            }
        });
        if (bundle != null) {
            this.productName = bundle.getString("productName");
        }
        navigateToProduct();
        EventBus.getDefault().register(this);
        Log.i("MainActivity2", "onCreate");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        BackHandlerHelper.BackCallbackWrapper backCallbackWrapper = this.backCallback;
        if (backCallbackWrapper != null) {
            backCallbackWrapper.unregister(this);
        }
        EventBus.getDefault().unregister(this);
    }

    @Override
    public boolean onKeyDown(int i, @Nullable KeyEvent keyEvent) {
        if (i == 4) {
            onNavBarBackClick(null);
            return true;
        }
        if (i == 24 || i == 25) {
            if (i == 24) {
                setVolume(true);
                return true;
            } else if (i == 25) {
                setVolume(false);
                return true;
            }
        }
        return super.onKeyDown(i, keyEvent);
    }

    public final void onNavBarBackClick(@Nullable View view) {
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            if (Intrinsics.areEqual(fragment.getClass().getSimpleName(), "NavHostFragment")) {
                List<Fragment> fragments = fragment.getChildFragmentManager().getFragments();
                Intrinsics.checkNotNullExpressionValue(fragments, "fragment.childFragmentManager.fragments");
                if (fragments.isEmpty()) {
                    continue;
                } else {
                    List<Fragment> fragments2 = fragment.getChildFragmentManager().getFragments();
                    Intrinsics.checkNotNullExpressionValue(fragments2, "fragment.childFragmentManager.fragments");
                    Fragment fragment2 = (Fragment) CollectionsKt.last((List<? extends Object>) fragments2);
                    List<Fragment> fragments3 = fragment2.getChildFragmentManager().getFragments();
                    Intrinsics.checkNotNullExpressionValue(fragments3, "displayFragment.childFragmentManager.fragments");
                    if (fragments3.isEmpty()) {
                        continue;
                    } else {
                        List<Fragment> fragments4 = fragment2.getChildFragmentManager().getFragments();
                        Intrinsics.checkNotNullExpressionValue(fragments4, "displayFragment.childFragmentManager.fragments");
                        if (((Fragment) CollectionsKt.last((List<? extends Object>) fragments4)) instanceof ParametricEqTypeSettingFragment) {
                            ActivityMain2Binding activityMain2Binding = this.binding;
                            if (activityMain2Binding == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("binding");
                                activityMain2Binding = null;
                            }
                            activityMain2Binding.mainSettingsIv.setImageResource(R.drawable.main_settings_iv);
                            fragment2.getChildFragmentManager().popBackStack();
                            return;
                        }
                    }
                }
            }
        }
        moveTaskToBack(true);
    }

    @Subscribe(sticky = EmbeddingCompat.DEBUG, threadMode = ThreadMode.MAIN)
    public final void onNormalVolumeEvent(@NotNull NormalVolumeEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
    }

    @Override
    public void onPermissionsDenied(int i, @NotNull List<String> perms) {
        Intrinsics.checkNotNullParameter(perms, "perms");
        Log.e(this.TAG, "permissionsDenied");
    }

    @Override
    public void onPermissionsGranted(int i, @NotNull List<String> perms) {
        Intrinsics.checkNotNullParameter(perms, "perms");
    }

    @Subscribe(sticky = EmbeddingCompat.DEBUG, threadMode = ThreadMode.MAIN)
    public final void onProductChangedEvent(@NotNull ProductChangedEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        this.productName = event.productName;
        ActivityMain2Binding activityMain2Binding = this.binding;
        if (activityMain2Binding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityMain2Binding = null;
        }
        activityMain2Binding.mainSettingsIv.setVisibility(0);
        navigateToProduct();
    }

    @Override
    public void onRequestPermissionsResult(int i, @NotNull String[] permissions, @NotNull int[] grantResults) {
        Intrinsics.checkNotNullParameter(permissions, "permissions");
        Intrinsics.checkNotNullParameter(grantResults, "grantResults");
        super.onRequestPermissionsResult(i, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(i, permissions, grantResults, this);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("MainActivity2", "onResume");
        UacManager uacManager = this.mUacManager;
        if (uacManager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mUacManager");
            uacManager = null;
        }
        uacManager.enumerationUsb();
        navigateToProduct();
    }

    @Override
    public void onSaveInstanceState(@NotNull Bundle outState) {
        Intrinsics.checkNotNullParameter(outState, "outState");
        super.onSaveInstanceState(outState);
        outState.putString("productName", this.productName);
    }

    @Override
    public boolean onSupportNavigateUp() {
        ActivityKt.findNavController(this, R.id.nav_host_fragment_content_main);
        return super.onSupportNavigateUp();
    }

    @Subscribe(sticky = EmbeddingCompat.DEBUG, threadMode = ThreadMode.MAIN)
    public final void onTestObjectEvent(@NotNull TestObject event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Log.i("UacManager", "Digital:  " + event.value);
        Toast.makeText(this, "value:" + event.value, 0).show();
    }

    public final void resetSettingIv() {
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            if (Intrinsics.areEqual(fragment.getClass().getSimpleName(), "NavHostFragment")) {
                List<Fragment> fragments = fragment.getChildFragmentManager().getFragments();
                Intrinsics.checkNotNullExpressionValue(fragments, "fragment.childFragmentManager.fragments");
                if (fragments.isEmpty()) {
                    continue;
                } else {
                    List<Fragment> fragments2 = fragment.getChildFragmentManager().getFragments();
                    Intrinsics.checkNotNullExpressionValue(fragments2, "fragment.childFragmentManager.fragments");
                    Fragment fragment2 = (Fragment) CollectionsKt.last((List<? extends Object>) fragments2);
                    List<Fragment> fragments3 = fragment2.getChildFragmentManager().getFragments();
                    Intrinsics.checkNotNullExpressionValue(fragments3, "displayFragment.childFragmentManager.fragments");
                    if (fragments3.isEmpty()) {
                        continue;
                    } else {
                        List<Fragment> fragments4 = fragment2.getChildFragmentManager().getFragments();
                        Intrinsics.checkNotNullExpressionValue(fragments4, "displayFragment.childFragmentManager.fragments");
                        if (((Fragment) CollectionsKt.last((List<? extends Object>) fragments4)) instanceof ParametricEqTypeSettingFragment) {
                            return;
                        }
                    }
                }
            }
        }
        ActivityMain2Binding activityMain2Binding = this.binding;
        if (activityMain2Binding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityMain2Binding = null;
        }
        activityMain2Binding.mainSettingsIv.setImageResource(R.drawable.main_settings_iv);
    }

    public final void setDefaultBackground() {
        String bgPath = SpStorage.getInstance().getDefaultBg();
        Intrinsics.checkNotNullExpressionValue(bgPath, "bgPath");
        if (bgPath.length() != 0) {
            Glide.with((FragmentActivity) this).asBitmap().load(bgPath).into((RequestBuilder<Bitmap>) new SimpleTarget<Bitmap>() {
                public void onResourceReady(@NotNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                    ActivityMain2Binding activityMain2Binding;
                    Intrinsics.checkNotNullParameter(resource, "resource");
                    Resources resources = MainActivity2.this.getResources();
                    Intrinsics.checkNotNullExpressionValue(resources, "resources");
                    BitmapDrawable bitmapDrawable = new BitmapDrawable(resources, resource);
                    activityMain2Binding = MainActivity2.this.binding;
                    if (activityMain2Binding == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("binding");
                        activityMain2Binding = null;
                    }
                    activityMain2Binding.mainActivityRootContainer.setBackground(bitmapDrawable);
                }

                @Override
                public void onResourceReady(Object obj, Transition transition) {
                    onResourceReady((Bitmap) obj, (Transition<? super Bitmap>) transition);
                }
            });
            return;
        }
        ActivityMain2Binding activityMain2Binding = null;
        if (Intrinsics.areEqual(this.productName, UacManager.DC08)) {
            ActivityMain2Binding activityMain2Binding2 = this.binding;
            if (activityMain2Binding2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
            } else {
                activityMain2Binding = activityMain2Binding2;
            }
            activityMain2Binding.mainActivityRootContainer.setBackgroundResource(R.drawable.ic_dc08_bg);
        } else if (Intrinsics.areEqual(this.productName, UacManager.Macchiato)) {
            ActivityMain2Binding activityMain2Binding3 = this.binding;
            if (activityMain2Binding3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
            } else {
                activityMain2Binding = activityMain2Binding3;
            }
            activityMain2Binding.mainActivityRootContainer.setBackgroundResource(R.drawable.ic_macchiato_bg);
        } else {
            ActivityMain2Binding activityMain2Binding4 = this.binding;
            if (activityMain2Binding4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
            } else {
                activityMain2Binding = activityMain2Binding4;
            }
            activityMain2Binding.mainActivityRootContainer.setBackgroundResource(R.drawable.ic_main_bg);
        }
    }

    public final void setSettingIv() {
        ActivityMain2Binding activityMain2Binding = this.binding;
        if (activityMain2Binding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityMain2Binding = null;
        }
        activityMain2Binding.mainSettingsIv.setImageResource(R.drawable.ic_menu);
    }
}
