package com.ibasso.volume;

import android.os.Build;
import android.window.OnBackInvokedCallback;
import android.window.OnBackInvokedDispatcher;
import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import com.ibasso.volume.BackHandlerHelper;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
public final class BackHandlerHelper {
    @NotNull
    public static final Companion Companion = new Companion(null);

    public static final class BackCallbackWrapper {
        @Nullable
        private OnBackInvokedCallback android13Callback;
        @NotNull
        private final Function0<Unit> block;
        @Nullable
        private OnBackPressedCallback onBackPressedCallback;

        public BackCallbackWrapper(@NotNull Function0<Unit> block) {
            Intrinsics.checkNotNullParameter(block, "block");
            this.block = block;
        }

        @Nullable
        public final OnBackInvokedCallback getAndroid13Callback() {
            return this.android13Callback;
        }

        @NotNull
        public final Function0<Unit> getBlock() {
            return this.block;
        }

        @Nullable
        public final OnBackPressedCallback getOnBackPressedCallback() {
            return this.onBackPressedCallback;
        }

        public final void setAndroid13Callback(@Nullable OnBackInvokedCallback onBackInvokedCallback) {
            this.android13Callback = onBackInvokedCallback;
        }

        public final void setOnBackPressedCallback(@Nullable OnBackPressedCallback onBackPressedCallback) {
            this.onBackPressedCallback = onBackPressedCallback;
        }

        public final void unregister(@NotNull FragmentActivity activity) {
            OnBackInvokedDispatcher onBackInvokedDispatcher;
            Intrinsics.checkNotNullParameter(activity, "activity");
            if (Build.VERSION.SDK_INT < 33) {
                OnBackPressedCallback onBackPressedCallback = this.onBackPressedCallback;
                if (onBackPressedCallback != null) {
                    onBackPressedCallback.remove();
                    return;
                }
                return;
            }
            OnBackInvokedCallback onBackInvokedCallback = this.android13Callback;
            if (onBackInvokedCallback != null) {
                onBackInvokedDispatcher = activity.getOnBackInvokedDispatcher();
                onBackInvokedDispatcher.unregisterOnBackInvokedCallback(onBackInvokedCallback);
            }
        }
    }

    public static final class Companion {
        private Companion() {
        }

        public Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static final void registerBackHandler$lambda$0(Function0 block) {
            Intrinsics.checkNotNullParameter(block, "$block");
            block.invoke();
        }

        public static final void registerBackHandler$lambda$1(Function0 block) {
            Intrinsics.checkNotNullParameter(block, "$block");
            block.invoke();
        }

        @NotNull
        public final BackCallbackWrapper registerBackHandler(@NotNull Fragment fragment, @NotNull final Function0<Unit> block) {
            OnBackInvokedDispatcher onBackInvokedDispatcher;
            Intrinsics.checkNotNullParameter(fragment, "fragment");
            Intrinsics.checkNotNullParameter(block, "block");
            BackCallbackWrapper backCallbackWrapper = new BackCallbackWrapper(block);
            if (Build.VERSION.SDK_INT >= 33) {
                backCallbackWrapper.setAndroid13Callback(new OnBackInvokedCallback() {
                    public final void onBackInvoked() {
                        BackHandlerHelper.Companion.registerBackHandler$lambda$1(Function0.this);
                    }
                });
                onBackInvokedDispatcher = fragment.requireActivity().getOnBackInvokedDispatcher();
                OnBackInvokedCallback android13Callback = backCallbackWrapper.getAndroid13Callback();
                Intrinsics.checkNotNull(android13Callback);
                onBackInvokedDispatcher.registerOnBackInvokedCallback(0, android13Callback);
                return backCallbackWrapper;
            }
            backCallbackWrapper.setOnBackPressedCallback(new OnBackPressedCallback() {
                {
                    super(true);
                }

                @Override
                public void handleOnBackPressed() {
                    block.invoke();
                }
            });
            OnBackPressedDispatcher onBackPressedDispatcher = fragment.requireActivity().getOnBackPressedDispatcher();
            LifecycleOwner viewLifecycleOwner = fragment.getViewLifecycleOwner();
            Intrinsics.checkNotNullExpressionValue(viewLifecycleOwner, "fragment.viewLifecycleOwner");
            OnBackPressedCallback onBackPressedCallback = backCallbackWrapper.getOnBackPressedCallback();
            Intrinsics.checkNotNull(onBackPressedCallback);
            onBackPressedDispatcher.addCallback(viewLifecycleOwner, onBackPressedCallback);
            return backCallbackWrapper;
        }

        @NotNull
        public final BackCallbackWrapper registerBackHandler(@NotNull FragmentActivity activity, @NotNull final Function0<Unit> block) {
            OnBackInvokedDispatcher onBackInvokedDispatcher;
            Intrinsics.checkNotNullParameter(activity, "activity");
            Intrinsics.checkNotNullParameter(block, "block");
            BackCallbackWrapper backCallbackWrapper = new BackCallbackWrapper(block);
            if (Build.VERSION.SDK_INT >= 33) {
                backCallbackWrapper.setAndroid13Callback(new OnBackInvokedCallback() {
                    public final void onBackInvoked() {
                        BackHandlerHelper.Companion.registerBackHandler$lambda$0(Function0.this);
                    }
                });
                onBackInvokedDispatcher = activity.getOnBackInvokedDispatcher();
                OnBackInvokedCallback android13Callback = backCallbackWrapper.getAndroid13Callback();
                Intrinsics.checkNotNull(android13Callback);
                onBackInvokedDispatcher.registerOnBackInvokedCallback(0, android13Callback);
                return backCallbackWrapper;
            }
            backCallbackWrapper.setOnBackPressedCallback(new OnBackPressedCallback() {
                {
                    super(true);
                }

                @Override
                public void handleOnBackPressed() {
                    block.invoke();
                }
            });
            OnBackPressedDispatcher onBackPressedDispatcher = activity.getOnBackPressedDispatcher();
            OnBackPressedCallback onBackPressedCallback = backCallbackWrapper.getOnBackPressedCallback();
            Intrinsics.checkNotNull(onBackPressedCallback);
            onBackPressedDispatcher.addCallback(onBackPressedCallback);
            return backCallbackWrapper;
        }
    }
}
