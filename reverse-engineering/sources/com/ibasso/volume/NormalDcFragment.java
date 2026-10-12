package com.ibasso.volume;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.fragment.app.Fragment;
import androidx.window.embedding.EmbeddingCompat;
import com.ibasso.music.widgets.MarqueeTextView;
import com.ibasso.music.widgets.VolumeGradientView;
import com.ibasso.volume.databinding.FragmentNormalDcBinding;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
@SourceDebugExtension({"SMAP\nNormalDcFragment.kt\nKotlin\n*S Kotlin\n*F\n+ 1 NormalDcFragment.kt\ncom/ibasso/volume/NormalDcFragment\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,128:1\n1#2:129\n*E\n"})
public final class NormalDcFragment extends Fragment {
    @NotNull
    private final String TAG = "UacManager";
    @Nullable
    private FragmentNormalDcBinding _binding;
    @Nullable
    private UacManager mUacManager;

    private final FragmentNormalDcBinding getBinding() {
        FragmentNormalDcBinding fragmentNormalDcBinding = this._binding;
        Intrinsics.checkNotNull(fragmentNormalDcBinding);
        return fragmentNormalDcBinding;
    }

    public static final void onResume$lambda$6(NormalDcFragment this$0, View view) {
        VolumeGradientView volumeGradientView;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        FragmentNormalDcBinding fragmentNormalDcBinding = this$0._binding;
        if (fragmentNormalDcBinding == null || (volumeGradientView = fragmentNormalDcBinding.volumeGradientView) == null) {
            return;
        }
        volumeGradientView.subtractProgressValue();
    }

    public static final void onResume$lambda$7(NormalDcFragment this$0, View view) {
        VolumeGradientView volumeGradientView;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        FragmentNormalDcBinding fragmentNormalDcBinding = this$0._binding;
        if (fragmentNormalDcBinding == null || (volumeGradientView = fragmentNormalDcBinding.volumeGradientView) == null) {
            return;
        }
        volumeGradientView.addProgressValue();
    }

    public static final void onResume$lambda$8(NormalDcFragment this$0, View view) {
        VolumeGradientView volumeGradientView;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        FragmentNormalDcBinding fragmentNormalDcBinding = this$0._binding;
        if (fragmentNormalDcBinding == null || (volumeGradientView = fragmentNormalDcBinding.volumeGradientView) == null) {
            return;
        }
        volumeGradientView.addProgressValue();
    }

    public static final void onResume$lambda$9(NormalDcFragment this$0, View view) {
        VolumeGradientView volumeGradientView;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        FragmentNormalDcBinding fragmentNormalDcBinding = this$0._binding;
        if (fragmentNormalDcBinding == null || (volumeGradientView = fragmentNormalDcBinding.volumeGradientView) == null) {
            return;
        }
        volumeGradientView.subtractProgressValue();
    }

    public static final void onViewCreated$lambda$1(NormalDcFragment this$0, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (i > 0) {
            this$0.setVolume(true);
        } else if (i < 0) {
            this$0.setVolume(false);
        }
    }

    public static final void onViewCreated$lambda$2(NormalDcFragment this$0, View view) {
        VolumeGradientView volumeGradientView;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        FragmentNormalDcBinding fragmentNormalDcBinding = this$0._binding;
        if (fragmentNormalDcBinding == null || (volumeGradientView = fragmentNormalDcBinding.volumeGradientView) == null) {
            return;
        }
        volumeGradientView.subtractProgressValue();
    }

    public static final void onViewCreated$lambda$3(NormalDcFragment this$0, View view) {
        VolumeGradientView volumeGradientView;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        FragmentNormalDcBinding fragmentNormalDcBinding = this$0._binding;
        if (fragmentNormalDcBinding == null || (volumeGradientView = fragmentNormalDcBinding.volumeGradientView) == null) {
            return;
        }
        volumeGradientView.addProgressValue();
    }

    public static final void onViewCreated$lambda$4(NormalDcFragment this$0, View view) {
        VolumeGradientView volumeGradientView;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        FragmentNormalDcBinding fragmentNormalDcBinding = this$0._binding;
        if (fragmentNormalDcBinding == null || (volumeGradientView = fragmentNormalDcBinding.volumeGradientView) == null) {
            return;
        }
        volumeGradientView.addProgressValue();
    }

    public static final void onViewCreated$lambda$5(NormalDcFragment this$0, View view) {
        VolumeGradientView volumeGradientView;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        FragmentNormalDcBinding fragmentNormalDcBinding = this$0._binding;
        if (fragmentNormalDcBinding == null || (volumeGradientView = fragmentNormalDcBinding.volumeGradientView) == null) {
            return;
        }
        volumeGradientView.subtractProgressValue();
    }

    private final void setVolume(boolean z) {
        UacManager uacManager = this.mUacManager;
        if (uacManager != null) {
            uacManager.setVolume(z);
        }
    }

    private final void updateView(int i) {
        VolumeGradientView volumeGradientView;
        MarqueeTextView marqueeTextView;
        VolumeGradientView volumeGradientView2;
        MarqueeTextView marqueeTextView2;
        VolumeGradientView volumeGradientView3;
        if (i != -1) {
            FragmentNormalDcBinding fragmentNormalDcBinding = this._binding;
            if (fragmentNormalDcBinding != null && (volumeGradientView3 = fragmentNormalDcBinding.volumeGradientView) != null) {
                volumeGradientView3.setProgressValue(i);
            }
        } else {
            FragmentNormalDcBinding fragmentNormalDcBinding2 = this._binding;
            if (fragmentNormalDcBinding2 != null && (volumeGradientView = fragmentNormalDcBinding2.volumeGradientView) != null) {
                volumeGradientView.setProgressValue(0);
            }
        }
        UacManager uacManager = this.mUacManager;
        if (uacManager != null) {
            Intrinsics.checkNotNull(uacManager);
            if (uacManager.isConnected()) {
                FragmentNormalDcBinding fragmentNormalDcBinding3 = this._binding;
                if (fragmentNormalDcBinding3 == null || (marqueeTextView2 = fragmentNormalDcBinding3.deviceNameTv) == null) {
                    return;
                }
                UacManager uacManager2 = this.mUacManager;
                Intrinsics.checkNotNull(uacManager2);
                marqueeTextView2.setText(uacManager2.getProductName());
                return;
            }
        }
        FragmentNormalDcBinding fragmentNormalDcBinding4 = this._binding;
        if (fragmentNormalDcBinding4 != null && (volumeGradientView2 = fragmentNormalDcBinding4.volumeGradientView) != null) {
            volumeGradientView2.setProgressValue(-1);
        }
        FragmentNormalDcBinding fragmentNormalDcBinding5 = this._binding;
        if (fragmentNormalDcBinding5 == null || (marqueeTextView = fragmentNormalDcBinding5.deviceNameTv) == null) {
            return;
        }
        marqueeTextView.setText(R.string.no_device);
    }

    @Override
    @Nullable
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        this._binding = FragmentNormalDcBinding.inflate(inflater, viewGroup, false);
        return getBinding().getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
        this._binding = null;
    }

    @Subscribe(sticky = EmbeddingCompat.DEBUG, threadMode = ThreadMode.MAIN)
    public final void onNormalVolumeEvent(@NotNull NormalVolumeEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        updateView(event.volume);
    }

    @Override
    public void onResume() {
        ImageView imageView;
        ImageView imageView2;
        ImageView imageView3;
        ImageView imageView4;
        ImageView imageView5;
        ImageView imageView6;
        ImageView imageView7;
        ImageView imageView8;
        super.onResume();
        Log.i(this.TAG, "onResume");
        if (SpStorage.getInstance().getIsVolumeBtReverse()) {
            FragmentNormalDcBinding fragmentNormalDcBinding = this._binding;
            if (fragmentNormalDcBinding != null && (imageView8 = fragmentNormalDcBinding.volumeAdd) != null) {
                imageView8.setImageDrawable(getResources().getDrawable(R.drawable.volume_subtract));
            }
            FragmentNormalDcBinding fragmentNormalDcBinding2 = this._binding;
            if (fragmentNormalDcBinding2 != null && (imageView7 = fragmentNormalDcBinding2.volumeSubtract) != null) {
                imageView7.setImageDrawable(getResources().getDrawable(R.drawable.volume_add));
            }
            FragmentNormalDcBinding fragmentNormalDcBinding3 = this._binding;
            if (fragmentNormalDcBinding3 != null && (imageView6 = fragmentNormalDcBinding3.volumeAdd) != null) {
                imageView6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public final void onClick(View view) {
                        NormalDcFragment.onResume$lambda$6(NormalDcFragment.this, view);
                    }
                });
            }
            FragmentNormalDcBinding fragmentNormalDcBinding4 = this._binding;
            if (fragmentNormalDcBinding4 != null && (imageView5 = fragmentNormalDcBinding4.volumeSubtract) != null) {
                imageView5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public final void onClick(View view) {
                        NormalDcFragment.onResume$lambda$7(NormalDcFragment.this, view);
                    }
                });
            }
        } else {
            FragmentNormalDcBinding fragmentNormalDcBinding5 = this._binding;
            if (fragmentNormalDcBinding5 != null && (imageView4 = fragmentNormalDcBinding5.volumeAdd) != null) {
                imageView4.setImageDrawable(getResources().getDrawable(R.drawable.volume_add));
            }
            FragmentNormalDcBinding fragmentNormalDcBinding6 = this._binding;
            if (fragmentNormalDcBinding6 != null && (imageView3 = fragmentNormalDcBinding6.volumeSubtract) != null) {
                imageView3.setImageDrawable(getResources().getDrawable(R.drawable.volume_subtract));
            }
            FragmentNormalDcBinding fragmentNormalDcBinding7 = this._binding;
            if (fragmentNormalDcBinding7 != null && (imageView2 = fragmentNormalDcBinding7.volumeAdd) != null) {
                imageView2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public final void onClick(View view) {
                        NormalDcFragment.onResume$lambda$8(NormalDcFragment.this, view);
                    }
                });
            }
            FragmentNormalDcBinding fragmentNormalDcBinding8 = this._binding;
            if (fragmentNormalDcBinding8 != null && (imageView = fragmentNormalDcBinding8.volumeSubtract) != null) {
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public final void onClick(View view) {
                        NormalDcFragment.onResume$lambda$9(NormalDcFragment.this, view);
                    }
                });
            }
        }
        UacManager uacManager = this.mUacManager;
        if (uacManager != null) {
            uacManager.enumerationUsb();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(this.TAG, "onStart");
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onViewCreated(@NotNull View view, @Nullable Bundle bundle) {
        ImageView imageView;
        ImageView imageView2;
        ImageView imageView3;
        ImageView imageView4;
        ImageView imageView5;
        ImageView imageView6;
        ImageView imageView7;
        ImageView imageView8;
        VolumeGradientView volumeGradientView;
        VolumeGradientView volumeGradientView2;
        Intrinsics.checkNotNullParameter(view, "view");
        super.onViewCreated(view, bundle);
        UacManager uacManager = UacManager.getUacManager();
        this.mUacManager = uacManager;
        if (uacManager != null) {
            int maxVolume = uacManager.getMaxVolume();
            FragmentNormalDcBinding fragmentNormalDcBinding = this._binding;
            if (fragmentNormalDcBinding != null && (volumeGradientView2 = fragmentNormalDcBinding.volumeGradientView) != null) {
                volumeGradientView2.setMaxProgress(maxVolume);
            }
        }
        FragmentNormalDcBinding fragmentNormalDcBinding2 = this._binding;
        if (fragmentNormalDcBinding2 != null && (volumeGradientView = fragmentNormalDcBinding2.volumeGradientView) != null) {
            volumeGradientView.setOnProgressChangedListener(new VolumeGradientView.OnProgressChangedListener() {
                @Override
                public final void progressChanged(int i) {
                    NormalDcFragment.onViewCreated$lambda$1(NormalDcFragment.this, i);
                }
            });
        }
        if (SpStorage.getInstance().getIsVolumeBtReverse()) {
            FragmentNormalDcBinding fragmentNormalDcBinding3 = this._binding;
            if (fragmentNormalDcBinding3 != null && (imageView8 = fragmentNormalDcBinding3.volumeAdd) != null) {
                imageView8.setImageDrawable(getResources().getDrawable(R.drawable.volume_subtract));
            }
            FragmentNormalDcBinding fragmentNormalDcBinding4 = this._binding;
            if (fragmentNormalDcBinding4 != null && (imageView7 = fragmentNormalDcBinding4.volumeSubtract) != null) {
                imageView7.setImageDrawable(getResources().getDrawable(R.drawable.volume_add));
            }
            FragmentNormalDcBinding fragmentNormalDcBinding5 = this._binding;
            if (fragmentNormalDcBinding5 != null && (imageView6 = fragmentNormalDcBinding5.volumeAdd) != null) {
                imageView6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public final void onClick(View view2) {
                        NormalDcFragment.onViewCreated$lambda$2(NormalDcFragment.this, view2);
                    }
                });
            }
            FragmentNormalDcBinding fragmentNormalDcBinding6 = this._binding;
            if (fragmentNormalDcBinding6 != null && (imageView5 = fragmentNormalDcBinding6.volumeSubtract) != null) {
                imageView5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public final void onClick(View view2) {
                        NormalDcFragment.onViewCreated$lambda$3(NormalDcFragment.this, view2);
                    }
                });
            }
        } else {
            FragmentNormalDcBinding fragmentNormalDcBinding7 = this._binding;
            if (fragmentNormalDcBinding7 != null && (imageView4 = fragmentNormalDcBinding7.volumeAdd) != null) {
                imageView4.setImageDrawable(getResources().getDrawable(R.drawable.volume_add));
            }
            FragmentNormalDcBinding fragmentNormalDcBinding8 = this._binding;
            if (fragmentNormalDcBinding8 != null && (imageView3 = fragmentNormalDcBinding8.volumeSubtract) != null) {
                imageView3.setImageDrawable(getResources().getDrawable(R.drawable.volume_subtract));
            }
            FragmentNormalDcBinding fragmentNormalDcBinding9 = this._binding;
            if (fragmentNormalDcBinding9 != null && (imageView2 = fragmentNormalDcBinding9.volumeAdd) != null) {
                imageView2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public final void onClick(View view2) {
                        NormalDcFragment.onViewCreated$lambda$4(NormalDcFragment.this, view2);
                    }
                });
            }
            FragmentNormalDcBinding fragmentNormalDcBinding10 = this._binding;
            if (fragmentNormalDcBinding10 != null && (imageView = fragmentNormalDcBinding10.volumeSubtract) != null) {
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public final void onClick(View view2) {
                        NormalDcFragment.onViewCreated$lambda$5(NormalDcFragment.this, view2);
                    }
                });
            }
        }
        EventBus.getDefault().register(this);
    }
}
