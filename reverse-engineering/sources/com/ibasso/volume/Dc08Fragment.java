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
import com.ibasso.music.widgets.ThirdSwitchBar;
import com.ibasso.volume.bean.FpgaVersionEvent;
import com.ibasso.volume.databinding.FragmentDc08Binding;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.HttpUrl;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
public final class Dc08Fragment extends Fragment implements SeekBar.OnSeekBarChangeListener {
    @Nullable
    private FragmentDc08Binding _binding;
    private boolean isTouched;

    public static final void onViewCreated$lambda$0(RadioGroup radioGroup, int i) {
        Intrinsics.checkNotNullParameter(radioGroup, "<anonymous parameter 0>");
        switch (i) {
            case R.id.digitalFilter1Rg:
                UacManager.getUacManager().setDigitalFilter(66);
                return;
            case R.id.digitalFilter2Rg:
                UacManager.getUacManager().setDigitalFilter(130);
                return;
            case R.id.digitalFilter3Rg:
                UacManager.getUacManager().setDigitalFilter(2);
                return;
            case R.id.digitalFilter4Rg:
                UacManager.getUacManager().setDigitalFilter(194);
                return;
            case R.id.digitalFilter5Rg:
                UacManager.getUacManager().setDigitalFilter(34);
                return;
            default:
                UacManager.getUacManager().setDigitalFilter(2);
                return;
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

    public static final void onViewCreated$lambda$4(Dc08Fragment this$0, CompoundButton buttonView, boolean z) {
        TextView textView;
        TextView textView2;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(buttonView, "buttonView");
        if (z) {
            UacManager.getUacManager().setOutput(1);
            FragmentDc08Binding fragmentDc08Binding = this$0._binding;
            if (fragmentDc08Binding == null || (textView2 = fragmentDc08Binding.outputSwitchTv) == null) {
                return;
            }
            textView2.setText(R.string.output_turbo);
            return;
        }
        UacManager.getUacManager().setOutput(0);
        FragmentDc08Binding fragmentDc08Binding2 = this$0._binding;
        if (fragmentDc08Binding2 == null || (textView = fragmentDc08Binding2.outputSwitchTv) == null) {
            return;
        }
        textView.setText(R.string.output_normal);
    }

    public final void updateThreeGainTipsViewStatus(int i) {
        TextView textView;
        TextView textView2;
        TextView textView3;
        if (i == 1) {
            FragmentDc08Binding fragmentDc08Binding = this._binding;
            if (fragmentDc08Binding != null && (textView = fragmentDc08Binding.gainTipsTv) != null) {
                textView.setText(R.string.settings_gain_middle);
            }
        } else if (i != 2) {
            FragmentDc08Binding fragmentDc08Binding2 = this._binding;
            if (fragmentDc08Binding2 != null && (textView3 = fragmentDc08Binding2.gainTipsTv) != null) {
                textView3.setText(R.string.settings_gain_low);
            }
        } else {
            FragmentDc08Binding fragmentDc08Binding3 = this._binding;
            if (fragmentDc08Binding3 != null && (textView2 = fragmentDc08Binding3.gainTipsTv) != null) {
                textView2.setText(R.string.settings_gain_high);
            }
        }
        UacManager.getUacManager().setGain(i);
    }

    @Override
    @Nullable
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        FragmentDc08Binding inflate = FragmentDc08Binding.inflate(inflater, viewGroup, false);
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
        FragmentDc08Binding fragmentDc08Binding = this._binding;
        SeekBar seekBar = fragmentDc08Binding != null ? fragmentDc08Binding.volumeSeekBar : null;
        if (seekBar != null) {
            Integer valueOf = dc06VolumeEvent != null ? Integer.valueOf(dc06VolumeEvent.volume) : null;
            Intrinsics.checkNotNull(valueOf);
            seekBar.setProgress(valueOf.intValue());
        }
        FragmentDc08Binding fragmentDc08Binding2 = this._binding;
        TextView textView = fragmentDc08Binding2 != null ? fragmentDc08Binding2.volumeValueTv : null;
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
        TextView textView3;
        RadioGroup radioGroup3;
        TextView textView4;
        RadioGroup radioGroup4;
        TextView textView5;
        RadioGroup radioGroup5;
        Integer valueOf = digitalFilterEvent != null ? Integer.valueOf(digitalFilterEvent.value & 255) : null;
        if (valueOf != null && valueOf.intValue() == 130) {
            FragmentDc08Binding fragmentDc08Binding = this._binding;
            if (fragmentDc08Binding != null && (radioGroup5 = fragmentDc08Binding.digitalFilterRg) != null) {
                radioGroup5.check(R.id.digitalFilter2Rg);
            }
            FragmentDc08Binding fragmentDc08Binding2 = this._binding;
            if (fragmentDc08Binding2 == null || (textView5 = fragmentDc08Binding2.digitalFilterValueTv) == null) {
                return;
            }
            textView5.setText(R.string.digital_filter_dx160_two);
        } else if (valueOf != null && valueOf.intValue() == 2) {
            FragmentDc08Binding fragmentDc08Binding3 = this._binding;
            if (fragmentDc08Binding3 != null && (radioGroup4 = fragmentDc08Binding3.digitalFilterRg) != null) {
                radioGroup4.check(R.id.digitalFilter3Rg);
            }
            FragmentDc08Binding fragmentDc08Binding4 = this._binding;
            if (fragmentDc08Binding4 == null || (textView4 = fragmentDc08Binding4.digitalFilterValueTv) == null) {
                return;
            }
            textView4.setText(R.string.digital_filter_dx160_three);
        } else if (valueOf != null && valueOf.intValue() == 194) {
            FragmentDc08Binding fragmentDc08Binding5 = this._binding;
            if (fragmentDc08Binding5 != null && (radioGroup3 = fragmentDc08Binding5.digitalFilterRg) != null) {
                radioGroup3.check(R.id.digitalFilter4Rg);
            }
            FragmentDc08Binding fragmentDc08Binding6 = this._binding;
            if (fragmentDc08Binding6 == null || (textView3 = fragmentDc08Binding6.digitalFilterValueTv) == null) {
                return;
            }
            textView3.setText(R.string.digital_filter_dx160_four);
        } else if (valueOf != null && valueOf.intValue() == 34) {
            FragmentDc08Binding fragmentDc08Binding7 = this._binding;
            if (fragmentDc08Binding7 != null && (radioGroup2 = fragmentDc08Binding7.digitalFilterRg) != null) {
                radioGroup2.check(R.id.digitalFilter5Rg);
            }
            FragmentDc08Binding fragmentDc08Binding8 = this._binding;
            if (fragmentDc08Binding8 == null || (textView2 = fragmentDc08Binding8.digitalFilterValueTv) == null) {
                return;
            }
            textView2.setText(R.string.digital_filter_dx160_five);
        } else {
            FragmentDc08Binding fragmentDc08Binding9 = this._binding;
            if (fragmentDc08Binding9 != null && (radioGroup = fragmentDc08Binding9.digitalFilterRg) != null) {
                radioGroup.check(R.id.digitalFilter1Rg);
            }
            FragmentDc08Binding fragmentDc08Binding10 = this._binding;
            if (fragmentDc08Binding10 == null || (textView = fragmentDc08Binding10.digitalFilterValueTv) == null) {
                return;
            }
            textView.setText(R.string.digital_filter_dx160_one);
        }
    }

    @Subscribe(sticky = EmbeddingCompat.DEBUG, threadMode = ThreadMode.MAIN)
    public final void onFpgaVersionEvent(@Nullable FpgaVersionEvent fpgaVersionEvent) {
        if (fpgaVersionEvent != null) {
            FragmentDc08Binding fragmentDc08Binding = this._binding;
            TextView textView = fragmentDc08Binding != null ? fragmentDc08Binding.fpgaVersionTv : null;
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
        FragmentDc08Binding fragmentDc08Binding = this._binding;
        TextView textView = fragmentDc08Binding != null ? fragmentDc08Binding.volumeValueTv : null;
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
            FragmentDc08Binding fragmentDc08Binding = this._binding;
            if (fragmentDc08Binding != null && (imageView4 = fragmentDc08Binding.volumeDownBt) != null) {
                imageView4.setImageResource(R.drawable.ic_volume_up_dc08);
            }
            FragmentDc08Binding fragmentDc08Binding2 = this._binding;
            if (fragmentDc08Binding2 == null || (imageView3 = fragmentDc08Binding2.volumeUpBt) == null) {
                return;
            }
            imageView3.setImageResource(R.drawable.ic_volume_down_dc08);
            return;
        }
        FragmentDc08Binding fragmentDc08Binding3 = this._binding;
        if (fragmentDc08Binding3 != null && (imageView2 = fragmentDc08Binding3.volumeDownBt) != null) {
            imageView2.setImageResource(R.drawable.ic_volume_down_dc08);
        }
        FragmentDc08Binding fragmentDc08Binding4 = this._binding;
        if (fragmentDc08Binding4 == null || (imageView = fragmentDc08Binding4.volumeUpBt) == null) {
            return;
        }
        imageView.setImageResource(R.drawable.ic_volume_up_dc08);
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
        TextView textView;
        RadioGroup radioGroup;
        ImageView imageView;
        ImageView imageView2;
        TextView textView2;
        Switch r4;
        TextView textView3;
        ThirdSwitchBar thirdSwitchBar;
        ThirdSwitchBar thirdSwitchBar2;
        BalanceView balanceView;
        ImageView imageView3;
        ImageView imageView4;
        ImageView imageView5;
        ImageView imageView6;
        BalanceView balanceView2;
        RadioGroup radioGroup2;
        TextView textView4;
        RadioGroup radioGroup3;
        TextView textView5;
        RadioGroup radioGroup4;
        TextView textView6;
        RadioGroup radioGroup5;
        TextView textView7;
        RadioGroup radioGroup6;
        SeekBar seekBar;
        Intrinsics.checkNotNullParameter(view, "view");
        super.onViewCreated(view, bundle);
        FragmentDc08Binding fragmentDc08Binding = this._binding;
        SeekBar seekBar2 = fragmentDc08Binding != null ? fragmentDc08Binding.volumeSeekBar : null;
        if (seekBar2 != null) {
            seekBar2.setMax(100);
        }
        FragmentDc08Binding fragmentDc08Binding2 = this._binding;
        SeekBar seekBar3 = fragmentDc08Binding2 != null ? fragmentDc08Binding2.volumeSeekBar : null;
        if (seekBar3 != null) {
            seekBar3.setProgress(UacManager.getUacManager().getVolume());
        }
        FragmentDc08Binding fragmentDc08Binding3 = this._binding;
        TextView textView8 = fragmentDc08Binding3 != null ? fragmentDc08Binding3.volumeValueTv : null;
        if (textView8 != null) {
            textView8.setText(HttpUrl.FRAGMENT_ENCODE_SET + UacManager.getUacManager().getVolume());
        }
        FragmentDc08Binding fragmentDc08Binding4 = this._binding;
        if (fragmentDc08Binding4 != null && (seekBar = fragmentDc08Binding4.volumeSeekBar) != null) {
            seekBar.setOnSeekBarChangeListener(this);
        }
        int digitalFilter = UacManager.getUacManager().getDigitalFilter() & 255;
        if (digitalFilter == 34) {
            FragmentDc08Binding fragmentDc08Binding5 = this._binding;
            if (fragmentDc08Binding5 != null && (radioGroup = fragmentDc08Binding5.digitalFilterRg) != null) {
                radioGroup.check(R.id.digitalFilter5Rg);
            }
            FragmentDc08Binding fragmentDc08Binding6 = this._binding;
            if (fragmentDc08Binding6 != null && (textView = fragmentDc08Binding6.digitalFilterValueTv) != null) {
                textView.setText(R.string.digital_filter_dx160_five);
            }
        } else if (digitalFilter == 66) {
            FragmentDc08Binding fragmentDc08Binding7 = this._binding;
            if (fragmentDc08Binding7 != null && (radioGroup3 = fragmentDc08Binding7.digitalFilterRg) != null) {
                radioGroup3.check(R.id.digitalFilter2Rg);
            }
            FragmentDc08Binding fragmentDc08Binding8 = this._binding;
            if (fragmentDc08Binding8 != null && (textView4 = fragmentDc08Binding8.digitalFilterValueTv) != null) {
                textView4.setText(R.string.digital_filter_dx160_two);
            }
        } else if (digitalFilter == 130) {
            FragmentDc08Binding fragmentDc08Binding9 = this._binding;
            if (fragmentDc08Binding9 != null && (radioGroup4 = fragmentDc08Binding9.digitalFilterRg) != null) {
                radioGroup4.check(R.id.digitalFilter4Rg);
            }
            FragmentDc08Binding fragmentDc08Binding10 = this._binding;
            if (fragmentDc08Binding10 != null && (textView5 = fragmentDc08Binding10.digitalFilterValueTv) != null) {
                textView5.setText(R.string.digital_filter_dx160_four);
            }
        } else if (digitalFilter != 194) {
            FragmentDc08Binding fragmentDc08Binding11 = this._binding;
            if (fragmentDc08Binding11 != null && (radioGroup6 = fragmentDc08Binding11.digitalFilterRg) != null) {
                radioGroup6.check(R.id.digitalFilter1Rg);
            }
            FragmentDc08Binding fragmentDc08Binding12 = this._binding;
            if (fragmentDc08Binding12 != null && (textView7 = fragmentDc08Binding12.digitalFilterValueTv) != null) {
                textView7.setText(R.string.digital_filter_dx160_one);
            }
        } else {
            FragmentDc08Binding fragmentDc08Binding13 = this._binding;
            if (fragmentDc08Binding13 != null && (radioGroup5 = fragmentDc08Binding13.digitalFilterRg) != null) {
                radioGroup5.check(R.id.digitalFilter3Rg);
            }
            FragmentDc08Binding fragmentDc08Binding14 = this._binding;
            if (fragmentDc08Binding14 != null && (textView6 = fragmentDc08Binding14.digitalFilterValueTv) != null) {
                textView6.setText(R.string.digital_filter_dx160_three);
            }
        }
        FragmentDc08Binding fragmentDc08Binding15 = this._binding;
        if (fragmentDc08Binding15 != null && (radioGroup2 = fragmentDc08Binding15.digitalFilterRg) != null) {
            radioGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public final void onCheckedChanged(RadioGroup radioGroup7, int i) {
                    Dc08Fragment.onViewCreated$lambda$0(radioGroup7, i);
                }
            });
        }
        FragmentDc08Binding fragmentDc08Binding16 = this._binding;
        if (fragmentDc08Binding16 != null && (balanceView2 = fragmentDc08Binding16.balanceSeekBar) != null) {
            balanceView2.setSeekBarProgressClickListener(new BalanceView.ISeekBarProgressClickListener() {
                @Override
                public void onSeekBarChanged(@Nullable BalanceView balanceView3, int i) {
                    FragmentDc08Binding fragmentDc08Binding17;
                    TextView textView9;
                    FragmentDc08Binding fragmentDc08Binding18;
                    FragmentDc08Binding fragmentDc08Binding19;
                    UacManager.getUacManager().setBalanceVolume(i);
                    if (i > 0) {
                        fragmentDc08Binding19 = Dc08Fragment.this._binding;
                        textView9 = fragmentDc08Binding19 != null ? fragmentDc08Binding19.balanceValueTv : null;
                        if (textView9 == null) {
                            return;
                        }
                        StringBuilder sb = new StringBuilder();
                        sb.append('R');
                        sb.append(i);
                        textView9.setText(sb.toString());
                    } else if (i >= 0) {
                        fragmentDc08Binding17 = Dc08Fragment.this._binding;
                        textView9 = fragmentDc08Binding17 != null ? fragmentDc08Binding17.balanceValueTv : null;
                        if (textView9 == null) {
                            return;
                        }
                        textView9.setText("L=R");
                    } else {
                        fragmentDc08Binding18 = Dc08Fragment.this._binding;
                        textView9 = fragmentDc08Binding18 != null ? fragmentDc08Binding18.balanceValueTv : null;
                        if (textView9 == null) {
                            return;
                        }
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append('L');
                        sb2.append(-i);
                        textView9.setText(sb2.toString());
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
            FragmentDc08Binding fragmentDc08Binding17 = this._binding;
            if (fragmentDc08Binding17 != null && (imageView6 = fragmentDc08Binding17.volumeDownBt) != null) {
                imageView6.setImageResource(R.drawable.ic_volume_up_dc08);
            }
            FragmentDc08Binding fragmentDc08Binding18 = this._binding;
            if (fragmentDc08Binding18 != null && (imageView5 = fragmentDc08Binding18.volumeUpBt) != null) {
                imageView5.setImageResource(R.drawable.ic_volume_down_dc08);
            }
        } else {
            FragmentDc08Binding fragmentDc08Binding19 = this._binding;
            if (fragmentDc08Binding19 != null && (imageView2 = fragmentDc08Binding19.volumeDownBt) != null) {
                imageView2.setImageResource(R.drawable.ic_volume_down_dc08);
            }
            FragmentDc08Binding fragmentDc08Binding20 = this._binding;
            if (fragmentDc08Binding20 != null && (imageView = fragmentDc08Binding20.volumeUpBt) != null) {
                imageView.setImageResource(R.drawable.ic_volume_up_dc08);
            }
        }
        FragmentDc08Binding fragmentDc08Binding21 = this._binding;
        if (fragmentDc08Binding21 != null && (imageView4 = fragmentDc08Binding21.volumeDownBt) != null) {
            imageView4.setOnClickListener(new View.OnClickListener() {
                @Override
                public final void onClick(View view2) {
                    Dc08Fragment.onViewCreated$lambda$1(view2);
                }
            });
        }
        FragmentDc08Binding fragmentDc08Binding22 = this._binding;
        if (fragmentDc08Binding22 != null && (imageView3 = fragmentDc08Binding22.volumeUpBt) != null) {
            imageView3.setOnClickListener(new View.OnClickListener() {
                @Override
                public final void onClick(View view2) {
                    Dc08Fragment.onViewCreated$lambda$2(view2);
                }
            });
        }
        int balanceVolume = UacManager.getUacManager().getBalanceVolume();
        if (balanceVolume > 0) {
            FragmentDc08Binding fragmentDc08Binding23 = this._binding;
            TextView textView9 = fragmentDc08Binding23 != null ? fragmentDc08Binding23.balanceValueTv : null;
            if (textView9 != null) {
                textView9.setText("R-" + balanceVolume);
            }
        } else if (balanceVolume < 0) {
            FragmentDc08Binding fragmentDc08Binding24 = this._binding;
            TextView textView10 = fragmentDc08Binding24 != null ? fragmentDc08Binding24.balanceValueTv : null;
            if (textView10 != null) {
                StringBuilder sb = new StringBuilder();
                sb.append('L');
                sb.append(balanceVolume);
                textView10.setText(sb.toString());
            }
        } else {
            FragmentDc08Binding fragmentDc08Binding25 = this._binding;
            TextView textView11 = fragmentDc08Binding25 != null ? fragmentDc08Binding25.balanceValueTv : null;
            if (textView11 != null) {
                textView11.setText("L=R");
            }
        }
        FragmentDc08Binding fragmentDc08Binding26 = this._binding;
        if (fragmentDc08Binding26 != null && (balanceView = fragmentDc08Binding26.balanceSeekBar) != null) {
            balanceView.setProgress(balanceVolume);
        }
        FragmentDc08Binding fragmentDc08Binding27 = this._binding;
        if (fragmentDc08Binding27 != null && (thirdSwitchBar2 = fragmentDc08Binding27.gainThirdSwitchBar) != null) {
            thirdSwitchBar2.setStatus(SpStorage.getInstance().getDc08Gain());
        }
        updateThreeGainTipsViewStatus(SpStorage.getInstance().getDc08Gain());
        FragmentDc08Binding fragmentDc08Binding28 = this._binding;
        if (fragmentDc08Binding28 != null && (thirdSwitchBar = fragmentDc08Binding28.gainThirdSwitchBar) != null) {
            thirdSwitchBar.setSeekTouchListener(new ThirdSwitchBar.SeekTouchListener() {
                @Override
                public void touchEnd(@Nullable SeekBar seekBar4) {
                    Dc08Fragment.this.updateThreeGainTipsViewStatus(2);
                }

                @Override
                public void touchMiddle(@Nullable SeekBar seekBar4) {
                    Dc08Fragment.this.updateThreeGainTipsViewStatus(1);
                }

                @Override
                public void touchTop(@Nullable SeekBar seekBar4) {
                    Dc08Fragment.this.updateThreeGainTipsViewStatus(0);
                }
            });
        }
        FragmentDc08Binding fragmentDc08Binding29 = this._binding;
        Switch r5 = fragmentDc08Binding29 != null ? fragmentDc08Binding29.outputSwitch : null;
        if (r5 != null) {
            r5.setChecked(SpStorage.getInstance().getDc08Output() == 1);
        }
        if (SpStorage.getInstance().getDc08Output() == 1) {
            FragmentDc08Binding fragmentDc08Binding30 = this._binding;
            if (fragmentDc08Binding30 != null && (textView3 = fragmentDc08Binding30.outputSwitchTv) != null) {
                textView3.setText(R.string.output_turbo);
            }
        } else {
            FragmentDc08Binding fragmentDc08Binding31 = this._binding;
            if (fragmentDc08Binding31 != null && (textView2 = fragmentDc08Binding31.outputSwitchTv) != null) {
                textView2.setText(R.string.output_normal);
            }
        }
        FragmentDc08Binding fragmentDc08Binding32 = this._binding;
        if (fragmentDc08Binding32 != null && (r4 = fragmentDc08Binding32.outputSwitch) != null) {
            r4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    Dc08Fragment.onViewCreated$lambda$4(Dc08Fragment.this, compoundButton, z);
                }
            });
        }
        EventBus.getDefault().register(this);
    }
}
