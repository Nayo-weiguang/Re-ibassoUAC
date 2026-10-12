package com.ibasso.volume;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.window.embedding.EmbeddingCompat;
import com.ibasso.music.widgets.BalanceView;
import com.ibasso.volume.bean.FpgaVersionEvent;
import com.ibasso.volume.databinding.FragmentDc06proBinding;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.HttpUrl;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
public final class Dc06ProFragment extends Fragment implements SeekBar.OnSeekBarChangeListener {
    @Nullable
    private FragmentDc06proBinding _binding;
    private boolean isTouched;

    public static final void onViewCreated$lambda$0(RadioGroup radioGroup, int i) {
        Intrinsics.checkNotNullParameter(radioGroup, "<anonymous parameter 0>");
        if (i == R.id.digitalFilter1Rg) {
            UacManager.getUacManager().setDigitalFilter(0);
        } else {
            UacManager.getUacManager().setDigitalFilter(192);
        }
    }

    public static final void onViewCreated$lambda$1(View view) {
        if (SpStorage.getInstance().getIsVolumeBtReverse()) {
            UacManager.getUacManager().setVolume(true);
        } else {
            UacManager.getUacManager().setVolume(false);
        }
    }

    public static final void onViewCreated$lambda$2(View view) {
        if (SpStorage.getInstance().getIsVolumeBtReverse()) {
            UacManager.getUacManager().setVolume(false);
        } else {
            UacManager.getUacManager().setVolume(true);
        }
    }

    public static final void onViewCreated$lambda$4(Dc06ProFragment this$0, CompoundButton buttonView, boolean z) {
        TextView textView;
        TextView textView2;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(buttonView, "buttonView");
        if (z) {
            UacManager.getUacManager().setCodecExternal(1);
            FragmentDc06proBinding fragmentDc06proBinding = this$0._binding;
            if (fragmentDc06proBinding == null || (textView2 = fragmentDc06proBinding.codecExternalSwitchTv) == null) {
                return;
            }
            textView2.setText(R.string.on);
            return;
        }
        UacManager.getUacManager().setCodecExternal(0);
        FragmentDc06proBinding fragmentDc06proBinding2 = this$0._binding;
        if (fragmentDc06proBinding2 == null || (textView = fragmentDc06proBinding2.codecExternalSwitchTv) == null) {
            return;
        }
        textView.setText(R.string.off);
    }

    @Override
    @Nullable
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        FragmentDc06proBinding inflate = FragmentDc06proBinding.inflate(inflater, viewGroup, false);
        this._binding = inflate;
        if (inflate != null) {
            return inflate.getRoot();
        }
        return null;
    }

    @Subscribe(sticky = EmbeddingCompat.DEBUG, threadMode = ThreadMode.MAIN)
    public final void onDc06VolumeEvent(@Nullable Dc06VolumeEvent dc06VolumeEvent) {
        if (this.isTouched) {
            return;
        }
        FragmentDc06proBinding fragmentDc06proBinding = this._binding;
        SeekBar seekBar = fragmentDc06proBinding != null ? fragmentDc06proBinding.volumeSeekBar : null;
        if (seekBar != null) {
            Integer valueOf = dc06VolumeEvent != null ? Integer.valueOf(dc06VolumeEvent.volume) : null;
            Intrinsics.checkNotNull(valueOf);
            seekBar.setProgress(valueOf.intValue());
        }
        FragmentDc06proBinding fragmentDc06proBinding2 = this._binding;
        TextView textView = fragmentDc06proBinding2 != null ? fragmentDc06proBinding2.volumeValueTv : null;
        if (textView == null) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(HttpUrl.FRAGMENT_ENCODE_SET);
        sb.append(dc06VolumeEvent != null ? Integer.valueOf(dc06VolumeEvent.volume) : null);
        textView.setText(sb.toString());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
        this._binding = null;
    }

    @Subscribe(sticky = EmbeddingCompat.DEBUG, threadMode = ThreadMode.MAIN)
    public final void onDigitalFilterEvent(@Nullable DigitalFilterEvent digitalFilterEvent) {
        TextView textView;
        RadioGroup radioGroup;
        TextView textView2;
        RadioGroup radioGroup2;
        if (digitalFilterEvent == null || digitalFilterEvent.value != 0) {
            FragmentDc06proBinding fragmentDc06proBinding = this._binding;
            if (fragmentDc06proBinding != null && (radioGroup = fragmentDc06proBinding.digitalFilterRg) != null) {
                radioGroup.check(R.id.digitalFilter2Rg);
            }
            FragmentDc06proBinding fragmentDc06proBinding2 = this._binding;
            if (fragmentDc06proBinding2 == null || (textView = fragmentDc06proBinding2.digitalFilterValueTv) == null) {
                return;
            }
            textView.setText(R.string.dc06_digital_filter_2);
            return;
        }
        FragmentDc06proBinding fragmentDc06proBinding3 = this._binding;
        if (fragmentDc06proBinding3 != null && (radioGroup2 = fragmentDc06proBinding3.digitalFilterRg) != null) {
            radioGroup2.check(R.id.digitalFilter1Rg);
        }
        FragmentDc06proBinding fragmentDc06proBinding4 = this._binding;
        if (fragmentDc06proBinding4 == null || (textView2 = fragmentDc06proBinding4.digitalFilterValueTv) == null) {
            return;
        }
        textView2.setText(R.string.dc06_digital_filter_1);
    }

    @Subscribe(sticky = EmbeddingCompat.DEBUG, threadMode = ThreadMode.MAIN)
    public final void onFpgaVersionEvent(@Nullable FpgaVersionEvent fpgaVersionEvent) {
        if (fpgaVersionEvent != null) {
            FragmentDc06proBinding fragmentDc06proBinding = this._binding;
            TextView textView = fragmentDc06proBinding != null ? fragmentDc06proBinding.fpgaVersionTv : null;
            if (textView == null) {
                return;
            }
            textView.setText(getString(R.string.fpga_version) + fpgaVersionEvent.fpgaVersion);
        }
    }

    @Subscribe(sticky = EmbeddingCompat.DEBUG, threadMode = ThreadMode.MAIN)
    public final void onNormalVolumeEvent(@NotNull NormalVolumeEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
    }

    @Override
    public void onProgressChanged(@Nullable SeekBar seekBar, int i, boolean z) {
        FragmentDc06proBinding fragmentDc06proBinding = this._binding;
        TextView textView = fragmentDc06proBinding != null ? fragmentDc06proBinding.volumeValueTv : null;
        if (textView != null) {
            textView.setText(HttpUrl.FRAGMENT_ENCODE_SET + i);
        }
        if (!this.isTouched || seekBar == null) {
            return;
        }
        Log.i("UacManager", "volume changed progress: " + seekBar + ".progress  max " + seekBar.getMax());
        UacManager.getUacManager().setVolume(seekBar.getProgress());
    }

    @Override
    public void onResume() {
        ImageView imageView;
        ImageView imageView2;
        ImageView imageView3;
        ImageView imageView4;
        super.onResume();
        if (SpStorage.getInstance().getIsVolumeBtReverse()) {
            FragmentDc06proBinding fragmentDc06proBinding = this._binding;
            if (fragmentDc06proBinding != null && (imageView4 = fragmentDc06proBinding.volumeDownBt) != null) {
                imageView4.setImageResource(R.drawable.ic_volume_up);
            }
            FragmentDc06proBinding fragmentDc06proBinding2 = this._binding;
            if (fragmentDc06proBinding2 == null || (imageView3 = fragmentDc06proBinding2.volumeUpBt) == null) {
                return;
            }
            imageView3.setImageResource(R.drawable.ic_volume_down);
            return;
        }
        FragmentDc06proBinding fragmentDc06proBinding3 = this._binding;
        if (fragmentDc06proBinding3 != null && (imageView2 = fragmentDc06proBinding3.volumeDownBt) != null) {
            imageView2.setImageResource(R.drawable.ic_volume_down);
        }
        FragmentDc06proBinding fragmentDc06proBinding4 = this._binding;
        if (fragmentDc06proBinding4 == null || (imageView = fragmentDc06proBinding4.volumeUpBt) == null) {
            return;
        }
        imageView.setImageResource(R.drawable.ic_volume_up);
    }

    @Override
    public void onStartTrackingTouch(@Nullable SeekBar seekBar) {
        this.isTouched = true;
    }

    @Override
    public void onStopTrackingTouch(@Nullable SeekBar seekBar) {
        this.isTouched = false;
    }

    @Override
    @SuppressLint({"SetTextI18n"})
    public void onViewCreated(@NotNull View view, @Nullable Bundle bundle) {
        ImageView imageView;
        ImageView imageView2;
        TextView textView;
        RadioGroup radioGroup;
        TextView textView2;
        Switch r4;
        TextView textView3;
        BalanceView balanceView;
        TextView textView4;
        RadioGroup radioGroup2;
        ImageView imageView3;
        ImageView imageView4;
        ImageView imageView5;
        ImageView imageView6;
        BalanceView balanceView2;
        RadioGroup radioGroup3;
        SeekBar seekBar;
        Intrinsics.checkNotNullParameter(view, "view");
        super.onViewCreated(view, bundle);
        FragmentDc06proBinding fragmentDc06proBinding = this._binding;
        if (fragmentDc06proBinding != null && (seekBar = fragmentDc06proBinding.volumeSeekBar) != null) {
            seekBar.setOnSeekBarChangeListener(this);
        }
        FragmentDc06proBinding fragmentDc06proBinding2 = this._binding;
        if (fragmentDc06proBinding2 != null && (radioGroup3 = fragmentDc06proBinding2.digitalFilterRg) != null) {
            radioGroup3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public final void onCheckedChanged(RadioGroup radioGroup4, int i) {
                    Dc06ProFragment.onViewCreated$lambda$0(radioGroup4, i);
                }
            });
        }
        FragmentDc06proBinding fragmentDc06proBinding3 = this._binding;
        if (fragmentDc06proBinding3 != null && (balanceView2 = fragmentDc06proBinding3.balanceSeekBar) != null) {
            balanceView2.setSeekBarProgressClickListener(new BalanceView.ISeekBarProgressClickListener() {
                @Override
                public void onSeekBarChanged(@Nullable BalanceView balanceView3, int i) {
                    FragmentDc06proBinding fragmentDc06proBinding4;
                    TextView textView5;
                    FragmentDc06proBinding fragmentDc06proBinding5;
                    FragmentDc06proBinding fragmentDc06proBinding6;
                    UacManager.getUacManager().setBalanceVolume(i);
                    if (i > 0) {
                        fragmentDc06proBinding6 = Dc06ProFragment.this._binding;
                        textView5 = fragmentDc06proBinding6 != null ? fragmentDc06proBinding6.balanceValueTv : null;
                        if (textView5 == null) {
                            return;
                        }
                        textView5.setText("R-" + i);
                    } else if (i >= 0) {
                        fragmentDc06proBinding4 = Dc06ProFragment.this._binding;
                        textView5 = fragmentDc06proBinding4 != null ? fragmentDc06proBinding4.balanceValueTv : null;
                        if (textView5 == null) {
                            return;
                        }
                        textView5.setText("L=R");
                    } else {
                        fragmentDc06proBinding5 = Dc06ProFragment.this._binding;
                        textView5 = fragmentDc06proBinding5 != null ? fragmentDc06proBinding5.balanceValueTv : null;
                        if (textView5 == null) {
                            return;
                        }
                        StringBuilder sb = new StringBuilder();
                        sb.append('L');
                        sb.append(i);
                        textView5.setText(sb.toString());
                    }
                }

                @Override
                public void onSeekBarStart(@Nullable BalanceView balanceView3, int i) {
                }

                @Override
                public void onSeekBarStop(@Nullable BalanceView balanceView3, int i) {
                }
            });
        }
        if (SpStorage.getInstance().getIsVolumeBtReverse()) {
            FragmentDc06proBinding fragmentDc06proBinding4 = this._binding;
            if (fragmentDc06proBinding4 != null && (imageView6 = fragmentDc06proBinding4.volumeDownBt) != null) {
                imageView6.setImageResource(R.drawable.ic_volume_up);
            }
            FragmentDc06proBinding fragmentDc06proBinding5 = this._binding;
            if (fragmentDc06proBinding5 != null && (imageView5 = fragmentDc06proBinding5.volumeUpBt) != null) {
                imageView5.setImageResource(R.drawable.ic_volume_down);
            }
        } else {
            FragmentDc06proBinding fragmentDc06proBinding6 = this._binding;
            if (fragmentDc06proBinding6 != null && (imageView2 = fragmentDc06proBinding6.volumeDownBt) != null) {
                imageView2.setImageResource(R.drawable.ic_volume_down);
            }
            FragmentDc06proBinding fragmentDc06proBinding7 = this._binding;
            if (fragmentDc06proBinding7 != null && (imageView = fragmentDc06proBinding7.volumeUpBt) != null) {
                imageView.setImageResource(R.drawable.ic_volume_up);
            }
        }
        FragmentDc06proBinding fragmentDc06proBinding8 = this._binding;
        if (fragmentDc06proBinding8 != null && (imageView4 = fragmentDc06proBinding8.volumeDownBt) != null) {
            imageView4.setOnClickListener(new View.OnClickListener() {
                @Override
                public final void onClick(View view2) {
                    Dc06ProFragment.onViewCreated$lambda$1(view2);
                }
            });
        }
        FragmentDc06proBinding fragmentDc06proBinding9 = this._binding;
        if (fragmentDc06proBinding9 != null && (imageView3 = fragmentDc06proBinding9.volumeUpBt) != null) {
            imageView3.setOnClickListener(new View.OnClickListener() {
                @Override
                public final void onClick(View view2) {
                    Dc06ProFragment.onViewCreated$lambda$2(view2);
                }
            });
        }
        FragmentDc06proBinding fragmentDc06proBinding10 = this._binding;
        SeekBar seekBar2 = fragmentDc06proBinding10 != null ? fragmentDc06proBinding10.volumeSeekBar : null;
        if (seekBar2 != null) {
            seekBar2.setMax(100);
        }
        FragmentDc06proBinding fragmentDc06proBinding11 = this._binding;
        SeekBar seekBar3 = fragmentDc06proBinding11 != null ? fragmentDc06proBinding11.volumeSeekBar : null;
        if (seekBar3 != null) {
            seekBar3.setProgress(UacManager.getUacManager().getVolume());
        }
        FragmentDc06proBinding fragmentDc06proBinding12 = this._binding;
        TextView textView5 = fragmentDc06proBinding12 != null ? fragmentDc06proBinding12.volumeValueTv : null;
        if (textView5 != null) {
            textView5.setText(HttpUrl.FRAGMENT_ENCODE_SET + UacManager.getUacManager().getVolume());
        }
        if (UacManager.getUacManager().getDigitalFilter() == 0) {
            FragmentDc06proBinding fragmentDc06proBinding13 = this._binding;
            if (fragmentDc06proBinding13 != null && (radioGroup2 = fragmentDc06proBinding13.digitalFilterRg) != null) {
                radioGroup2.check(R.id.digitalFilter1Rg);
            }
            FragmentDc06proBinding fragmentDc06proBinding14 = this._binding;
            if (fragmentDc06proBinding14 != null && (textView4 = fragmentDc06proBinding14.digitalFilterValueTv) != null) {
                textView4.setText(R.string.dc06_digital_filter_1);
            }
        } else {
            FragmentDc06proBinding fragmentDc06proBinding15 = this._binding;
            if (fragmentDc06proBinding15 != null && (radioGroup = fragmentDc06proBinding15.digitalFilterRg) != null) {
                radioGroup.check(R.id.digitalFilter2Rg);
            }
            FragmentDc06proBinding fragmentDc06proBinding16 = this._binding;
            if (fragmentDc06proBinding16 != null && (textView = fragmentDc06proBinding16.digitalFilterValueTv) != null) {
                textView.setText(R.string.dc06_digital_filter_2);
            }
        }
        int balanceVolume = UacManager.getUacManager().getBalanceVolume();
        if (balanceVolume > 0) {
            FragmentDc06proBinding fragmentDc06proBinding17 = this._binding;
            TextView textView6 = fragmentDc06proBinding17 != null ? fragmentDc06proBinding17.balanceValueTv : null;
            if (textView6 != null) {
                StringBuilder sb = new StringBuilder();
                sb.append('R');
                sb.append(balanceVolume);
                textView6.setText(sb.toString());
            }
        } else if (balanceVolume < 0) {
            FragmentDc06proBinding fragmentDc06proBinding18 = this._binding;
            TextView textView7 = fragmentDc06proBinding18 != null ? fragmentDc06proBinding18.balanceValueTv : null;
            if (textView7 != null) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append('L');
                sb2.append(-balanceVolume);
                textView7.setText(sb2.toString());
            }
        } else {
            FragmentDc06proBinding fragmentDc06proBinding19 = this._binding;
            TextView textView8 = fragmentDc06proBinding19 != null ? fragmentDc06proBinding19.balanceValueTv : null;
            if (textView8 != null) {
                textView8.setText("L=R");
            }
        }
        FragmentDc06proBinding fragmentDc06proBinding20 = this._binding;
        if (fragmentDc06proBinding20 != null && (balanceView = fragmentDc06proBinding20.balanceSeekBar) != null) {
            balanceView.setProgress(balanceVolume);
        }
        int codecExternal = UacManager.getUacManager().getCodecExternal();
        FragmentDc06proBinding fragmentDc06proBinding21 = this._binding;
        Switch r5 = fragmentDc06proBinding21 != null ? fragmentDc06proBinding21.codecExternalSwitch : null;
        if (r5 != null) {
            r5.setChecked(codecExternal == 1);
        }
        if (codecExternal == 1) {
            FragmentDc06proBinding fragmentDc06proBinding22 = this._binding;
            if (fragmentDc06proBinding22 != null && (textView3 = fragmentDc06proBinding22.codecExternalSwitchTv) != null) {
                textView3.setText(R.string.on);
            }
        } else {
            FragmentDc06proBinding fragmentDc06proBinding23 = this._binding;
            if (fragmentDc06proBinding23 != null && (textView2 = fragmentDc06proBinding23.codecExternalSwitchTv) != null) {
                textView2.setText(R.string.off);
            }
        }
        FragmentDc06proBinding fragmentDc06proBinding24 = this._binding;
        if (fragmentDc06proBinding24 != null && (r4 = fragmentDc06proBinding24.codecExternalSwitch) != null) {
            r4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    Dc06ProFragment.onViewCreated$lambda$4(Dc06ProFragment.this, compoundButton, z);
                }
            });
        }
        EventBus.getDefault().register(this);
    }
}
