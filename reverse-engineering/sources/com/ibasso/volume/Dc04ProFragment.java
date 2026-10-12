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
import com.ibasso.volume.databinding.FragmentDc04proBinding;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.HttpUrl;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
public final class Dc04ProFragment extends Fragment implements SeekBar.OnSeekBarChangeListener {
    @Nullable
    private FragmentDc04proBinding _binding;
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

    public static final void onViewCreated$lambda$4(Dc04ProFragment this$0, CompoundButton buttonView, boolean z) {
        TextView textView;
        TextView textView2;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(buttonView, "buttonView");
        if (z) {
            UacManager.getUacManager().setOutput(1);
            FragmentDc04proBinding fragmentDc04proBinding = this$0._binding;
            if (fragmentDc04proBinding == null || (textView2 = fragmentDc04proBinding.outputSwitchTv) == null) {
                return;
            }
            textView2.setText(R.string.output_turbo);
            return;
        }
        UacManager.getUacManager().setOutput(0);
        FragmentDc04proBinding fragmentDc04proBinding2 = this$0._binding;
        if (fragmentDc04proBinding2 == null || (textView = fragmentDc04proBinding2.outputSwitchTv) == null) {
            return;
        }
        textView.setText(R.string.output_normal);
    }

    public static final void onViewCreated$lambda$6(Dc04ProFragment this$0, CompoundButton buttonView, boolean z) {
        TextView textView;
        TextView textView2;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(buttonView, "buttonView");
        if (z) {
            UacManager.getUacManager().setCodecExternal(1);
            FragmentDc04proBinding fragmentDc04proBinding = this$0._binding;
            if (fragmentDc04proBinding == null || (textView2 = fragmentDc04proBinding.codecExternalSwitchTv) == null) {
                return;
            }
            textView2.setText(R.string.on);
            return;
        }
        UacManager.getUacManager().setCodecExternal(0);
        FragmentDc04proBinding fragmentDc04proBinding2 = this$0._binding;
        if (fragmentDc04proBinding2 == null || (textView = fragmentDc04proBinding2.codecExternalSwitchTv) == null) {
            return;
        }
        textView.setText(R.string.off);
    }

    public final void updateThreeGainTipsViewStatus(int i) {
        TextView textView;
        TextView textView2;
        TextView textView3;
        if (i == 1) {
            FragmentDc04proBinding fragmentDc04proBinding = this._binding;
            if (fragmentDc04proBinding != null && (textView = fragmentDc04proBinding.gainTipsTv) != null) {
                textView.setText(R.string.settings_gain_middle);
            }
        } else if (i != 2) {
            FragmentDc04proBinding fragmentDc04proBinding2 = this._binding;
            if (fragmentDc04proBinding2 != null && (textView3 = fragmentDc04proBinding2.gainTipsTv) != null) {
                textView3.setText(R.string.settings_gain_low);
            }
        } else {
            FragmentDc04proBinding fragmentDc04proBinding3 = this._binding;
            if (fragmentDc04proBinding3 != null && (textView2 = fragmentDc04proBinding3.gainTipsTv) != null) {
                textView2.setText(R.string.settings_gain_high);
            }
        }
        UacManager.getUacManager().setGain(i);
    }

    @Override
    @Nullable
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        FragmentDc04proBinding inflate = FragmentDc04proBinding.inflate(inflater, viewGroup, false);
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
        FragmentDc04proBinding fragmentDc04proBinding = this._binding;
        SeekBar seekBar = fragmentDc04proBinding != null ? fragmentDc04proBinding.volumeSeekBar : null;
        if (seekBar != null) {
            Integer valueOf = dc06VolumeEvent != null ? Integer.valueOf(dc06VolumeEvent.volume) : null;
            Intrinsics.checkNotNull(valueOf);
            seekBar.setProgress(valueOf.intValue());
        }
        FragmentDc04proBinding fragmentDc04proBinding2 = this._binding;
        TextView textView = fragmentDc04proBinding2 != null ? fragmentDc04proBinding2.volumeValueTv : null;
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
            FragmentDc04proBinding fragmentDc04proBinding = this._binding;
            if (fragmentDc04proBinding != null && (radioGroup5 = fragmentDc04proBinding.digitalFilterRg) != null) {
                radioGroup5.check(R.id.digitalFilter2Rg);
            }
            FragmentDc04proBinding fragmentDc04proBinding2 = this._binding;
            if (fragmentDc04proBinding2 == null || (textView5 = fragmentDc04proBinding2.digitalFilterValueTv) == null) {
                return;
            }
            textView5.setText(R.string.digital_filter_dx160_two);
        } else if (valueOf != null && valueOf.intValue() == 2) {
            FragmentDc04proBinding fragmentDc04proBinding3 = this._binding;
            if (fragmentDc04proBinding3 != null && (radioGroup4 = fragmentDc04proBinding3.digitalFilterRg) != null) {
                radioGroup4.check(R.id.digitalFilter3Rg);
            }
            FragmentDc04proBinding fragmentDc04proBinding4 = this._binding;
            if (fragmentDc04proBinding4 == null || (textView4 = fragmentDc04proBinding4.digitalFilterValueTv) == null) {
                return;
            }
            textView4.setText(R.string.digital_filter_dx160_three);
        } else if (valueOf != null && valueOf.intValue() == 194) {
            FragmentDc04proBinding fragmentDc04proBinding5 = this._binding;
            if (fragmentDc04proBinding5 != null && (radioGroup3 = fragmentDc04proBinding5.digitalFilterRg) != null) {
                radioGroup3.check(R.id.digitalFilter4Rg);
            }
            FragmentDc04proBinding fragmentDc04proBinding6 = this._binding;
            if (fragmentDc04proBinding6 == null || (textView3 = fragmentDc04proBinding6.digitalFilterValueTv) == null) {
                return;
            }
            textView3.setText(R.string.digital_filter_dx160_four);
        } else if (valueOf != null && valueOf.intValue() == 34) {
            FragmentDc04proBinding fragmentDc04proBinding7 = this._binding;
            if (fragmentDc04proBinding7 != null && (radioGroup2 = fragmentDc04proBinding7.digitalFilterRg) != null) {
                radioGroup2.check(R.id.digitalFilter5Rg);
            }
            FragmentDc04proBinding fragmentDc04proBinding8 = this._binding;
            if (fragmentDc04proBinding8 == null || (textView2 = fragmentDc04proBinding8.digitalFilterValueTv) == null) {
                return;
            }
            textView2.setText(R.string.digital_filter_dx160_five);
        } else {
            FragmentDc04proBinding fragmentDc04proBinding9 = this._binding;
            if (fragmentDc04proBinding9 != null && (radioGroup = fragmentDc04proBinding9.digitalFilterRg) != null) {
                radioGroup.check(R.id.digitalFilter1Rg);
            }
            FragmentDc04proBinding fragmentDc04proBinding10 = this._binding;
            if (fragmentDc04proBinding10 == null || (textView = fragmentDc04proBinding10.digitalFilterValueTv) == null) {
                return;
            }
            textView.setText(R.string.digital_filter_dx160_one);
        }
    }

    @Subscribe(sticky = EmbeddingCompat.DEBUG, threadMode = ThreadMode.MAIN)
    public final void onFpgaVersionEvent(@Nullable FpgaVersionEvent fpgaVersionEvent) {
        if (fpgaVersionEvent != null) {
            FragmentDc04proBinding fragmentDc04proBinding = this._binding;
            TextView textView = fragmentDc04proBinding != null ? fragmentDc04proBinding.fpgaVersionTv : null;
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
        FragmentDc04proBinding fragmentDc04proBinding = this._binding;
        TextView textView = fragmentDc04proBinding != null ? fragmentDc04proBinding.volumeValueTv : null;
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
            FragmentDc04proBinding fragmentDc04proBinding = this._binding;
            if (fragmentDc04proBinding != null && (imageView4 = fragmentDc04proBinding.volumeDownBt) != null) {
                imageView4.setImageResource(R.drawable.ic_volume_up);
            }
            FragmentDc04proBinding fragmentDc04proBinding2 = this._binding;
            if (fragmentDc04proBinding2 == null || (imageView3 = fragmentDc04proBinding2.volumeUpBt) == null) {
                return;
            }
            imageView3.setImageResource(R.drawable.ic_volume_down);
            return;
        }
        FragmentDc04proBinding fragmentDc04proBinding3 = this._binding;
        if (fragmentDc04proBinding3 != null && (imageView2 = fragmentDc04proBinding3.volumeDownBt) != null) {
            imageView2.setImageResource(R.drawable.ic_volume_down);
        }
        FragmentDc04proBinding fragmentDc04proBinding4 = this._binding;
        if (fragmentDc04proBinding4 == null || (imageView = fragmentDc04proBinding4.volumeUpBt) == null) {
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
        TextView textView;
        RadioGroup radioGroup;
        ImageView imageView;
        ImageView imageView2;
        TextView textView2;
        TextView textView3;
        Switch r4;
        TextView textView4;
        Switch r42;
        TextView textView5;
        ThirdSwitchBar thirdSwitchBar;
        ThirdSwitchBar thirdSwitchBar2;
        BalanceView balanceView;
        ImageView imageView3;
        ImageView imageView4;
        ImageView imageView5;
        ImageView imageView6;
        BalanceView balanceView2;
        RadioGroup radioGroup2;
        TextView textView6;
        RadioGroup radioGroup3;
        TextView textView7;
        RadioGroup radioGroup4;
        TextView textView8;
        RadioGroup radioGroup5;
        TextView textView9;
        RadioGroup radioGroup6;
        SeekBar seekBar;
        Intrinsics.checkNotNullParameter(view, "view");
        super.onViewCreated(view, bundle);
        FragmentDc04proBinding fragmentDc04proBinding = this._binding;
        SeekBar seekBar2 = fragmentDc04proBinding != null ? fragmentDc04proBinding.volumeSeekBar : null;
        if (seekBar2 != null) {
            seekBar2.setMax(100);
        }
        FragmentDc04proBinding fragmentDc04proBinding2 = this._binding;
        SeekBar seekBar3 = fragmentDc04proBinding2 != null ? fragmentDc04proBinding2.volumeSeekBar : null;
        if (seekBar3 != null) {
            seekBar3.setProgress(UacManager.getUacManager().getVolume());
        }
        FragmentDc04proBinding fragmentDc04proBinding3 = this._binding;
        TextView textView10 = fragmentDc04proBinding3 != null ? fragmentDc04proBinding3.volumeValueTv : null;
        if (textView10 != null) {
            textView10.setText(HttpUrl.FRAGMENT_ENCODE_SET + UacManager.getUacManager().getVolume());
        }
        FragmentDc04proBinding fragmentDc04proBinding4 = this._binding;
        if (fragmentDc04proBinding4 != null && (seekBar = fragmentDc04proBinding4.volumeSeekBar) != null) {
            seekBar.setOnSeekBarChangeListener(this);
        }
        int digitalFilter = UacManager.getUacManager().getDigitalFilter() & 255;
        if (digitalFilter == 34) {
            FragmentDc04proBinding fragmentDc04proBinding5 = this._binding;
            if (fragmentDc04proBinding5 != null && (radioGroup = fragmentDc04proBinding5.digitalFilterRg) != null) {
                radioGroup.check(R.id.digitalFilter5Rg);
            }
            FragmentDc04proBinding fragmentDc04proBinding6 = this._binding;
            if (fragmentDc04proBinding6 != null && (textView = fragmentDc04proBinding6.digitalFilterValueTv) != null) {
                textView.setText(R.string.digital_filter_dx160_five);
            }
        } else if (digitalFilter == 66) {
            FragmentDc04proBinding fragmentDc04proBinding7 = this._binding;
            if (fragmentDc04proBinding7 != null && (radioGroup3 = fragmentDc04proBinding7.digitalFilterRg) != null) {
                radioGroup3.check(R.id.digitalFilter2Rg);
            }
            FragmentDc04proBinding fragmentDc04proBinding8 = this._binding;
            if (fragmentDc04proBinding8 != null && (textView6 = fragmentDc04proBinding8.digitalFilterValueTv) != null) {
                textView6.setText(R.string.digital_filter_dx160_two);
            }
        } else if (digitalFilter == 130) {
            FragmentDc04proBinding fragmentDc04proBinding9 = this._binding;
            if (fragmentDc04proBinding9 != null && (radioGroup4 = fragmentDc04proBinding9.digitalFilterRg) != null) {
                radioGroup4.check(R.id.digitalFilter4Rg);
            }
            FragmentDc04proBinding fragmentDc04proBinding10 = this._binding;
            if (fragmentDc04proBinding10 != null && (textView7 = fragmentDc04proBinding10.digitalFilterValueTv) != null) {
                textView7.setText(R.string.digital_filter_dx160_four);
            }
        } else if (digitalFilter != 194) {
            FragmentDc04proBinding fragmentDc04proBinding11 = this._binding;
            if (fragmentDc04proBinding11 != null && (radioGroup6 = fragmentDc04proBinding11.digitalFilterRg) != null) {
                radioGroup6.check(R.id.digitalFilter1Rg);
            }
            FragmentDc04proBinding fragmentDc04proBinding12 = this._binding;
            if (fragmentDc04proBinding12 != null && (textView9 = fragmentDc04proBinding12.digitalFilterValueTv) != null) {
                textView9.setText(R.string.digital_filter_dx160_one);
            }
        } else {
            FragmentDc04proBinding fragmentDc04proBinding13 = this._binding;
            if (fragmentDc04proBinding13 != null && (radioGroup5 = fragmentDc04proBinding13.digitalFilterRg) != null) {
                radioGroup5.check(R.id.digitalFilter3Rg);
            }
            FragmentDc04proBinding fragmentDc04proBinding14 = this._binding;
            if (fragmentDc04proBinding14 != null && (textView8 = fragmentDc04proBinding14.digitalFilterValueTv) != null) {
                textView8.setText(R.string.digital_filter_dx160_three);
            }
        }
        FragmentDc04proBinding fragmentDc04proBinding15 = this._binding;
        if (fragmentDc04proBinding15 != null && (radioGroup2 = fragmentDc04proBinding15.digitalFilterRg) != null) {
            radioGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public final void onCheckedChanged(RadioGroup radioGroup7, int i) {
                    Dc04ProFragment.onViewCreated$lambda$0(radioGroup7, i);
                }
            });
        }
        FragmentDc04proBinding fragmentDc04proBinding16 = this._binding;
        if (fragmentDc04proBinding16 != null && (balanceView2 = fragmentDc04proBinding16.balanceSeekBar) != null) {
            balanceView2.setSeekBarProgressClickListener(new BalanceView.ISeekBarProgressClickListener() {
                @Override
                public void onSeekBarChanged(@Nullable BalanceView balanceView3, int i) {
                    FragmentDc04proBinding fragmentDc04proBinding17;
                    TextView textView11;
                    FragmentDc04proBinding fragmentDc04proBinding18;
                    FragmentDc04proBinding fragmentDc04proBinding19;
                    UacManager.getUacManager().setBalanceVolume(i);
                    if (i > 0) {
                        fragmentDc04proBinding19 = Dc04ProFragment.this._binding;
                        textView11 = fragmentDc04proBinding19 != null ? fragmentDc04proBinding19.balanceValueTv : null;
                        if (textView11 == null) {
                            return;
                        }
                        StringBuilder sb = new StringBuilder();
                        sb.append('R');
                        sb.append(i);
                        textView11.setText(sb.toString());
                    } else if (i >= 0) {
                        fragmentDc04proBinding17 = Dc04ProFragment.this._binding;
                        textView11 = fragmentDc04proBinding17 != null ? fragmentDc04proBinding17.balanceValueTv : null;
                        if (textView11 == null) {
                            return;
                        }
                        textView11.setText("L=R");
                    } else {
                        fragmentDc04proBinding18 = Dc04ProFragment.this._binding;
                        textView11 = fragmentDc04proBinding18 != null ? fragmentDc04proBinding18.balanceValueTv : null;
                        if (textView11 == null) {
                            return;
                        }
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append('L');
                        sb2.append(-i);
                        textView11.setText(sb2.toString());
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
            FragmentDc04proBinding fragmentDc04proBinding17 = this._binding;
            if (fragmentDc04proBinding17 != null && (imageView6 = fragmentDc04proBinding17.volumeDownBt) != null) {
                imageView6.setImageResource(R.drawable.ic_volume_up);
            }
            FragmentDc04proBinding fragmentDc04proBinding18 = this._binding;
            if (fragmentDc04proBinding18 != null && (imageView5 = fragmentDc04proBinding18.volumeUpBt) != null) {
                imageView5.setImageResource(R.drawable.ic_volume_down);
            }
        } else {
            FragmentDc04proBinding fragmentDc04proBinding19 = this._binding;
            if (fragmentDc04proBinding19 != null && (imageView2 = fragmentDc04proBinding19.volumeDownBt) != null) {
                imageView2.setImageResource(R.drawable.ic_volume_down);
            }
            FragmentDc04proBinding fragmentDc04proBinding20 = this._binding;
            if (fragmentDc04proBinding20 != null && (imageView = fragmentDc04proBinding20.volumeUpBt) != null) {
                imageView.setImageResource(R.drawable.ic_volume_up);
            }
        }
        FragmentDc04proBinding fragmentDc04proBinding21 = this._binding;
        if (fragmentDc04proBinding21 != null && (imageView4 = fragmentDc04proBinding21.volumeDownBt) != null) {
            imageView4.setOnClickListener(new View.OnClickListener() {
                @Override
                public final void onClick(View view2) {
                    Dc04ProFragment.onViewCreated$lambda$1(view2);
                }
            });
        }
        FragmentDc04proBinding fragmentDc04proBinding22 = this._binding;
        if (fragmentDc04proBinding22 != null && (imageView3 = fragmentDc04proBinding22.volumeUpBt) != null) {
            imageView3.setOnClickListener(new View.OnClickListener() {
                @Override
                public final void onClick(View view2) {
                    Dc04ProFragment.onViewCreated$lambda$2(view2);
                }
            });
        }
        int balanceVolume = UacManager.getUacManager().getBalanceVolume();
        if (balanceVolume > 0) {
            FragmentDc04proBinding fragmentDc04proBinding23 = this._binding;
            TextView textView11 = fragmentDc04proBinding23 != null ? fragmentDc04proBinding23.balanceValueTv : null;
            if (textView11 != null) {
                textView11.setText("R-" + balanceVolume);
            }
        } else if (balanceVolume < 0) {
            FragmentDc04proBinding fragmentDc04proBinding24 = this._binding;
            TextView textView12 = fragmentDc04proBinding24 != null ? fragmentDc04proBinding24.balanceValueTv : null;
            if (textView12 != null) {
                StringBuilder sb = new StringBuilder();
                sb.append('L');
                sb.append(balanceVolume);
                textView12.setText(sb.toString());
            }
        } else {
            FragmentDc04proBinding fragmentDc04proBinding25 = this._binding;
            TextView textView13 = fragmentDc04proBinding25 != null ? fragmentDc04proBinding25.balanceValueTv : null;
            if (textView13 != null) {
                textView13.setText("L=R");
            }
        }
        FragmentDc04proBinding fragmentDc04proBinding26 = this._binding;
        if (fragmentDc04proBinding26 != null && (balanceView = fragmentDc04proBinding26.balanceSeekBar) != null) {
            balanceView.setProgress(balanceVolume);
        }
        FragmentDc04proBinding fragmentDc04proBinding27 = this._binding;
        if (fragmentDc04proBinding27 != null && (thirdSwitchBar2 = fragmentDc04proBinding27.gainThirdSwitchBar) != null) {
            thirdSwitchBar2.setStatus(SpStorage.getInstance().getDc04ProGain());
        }
        updateThreeGainTipsViewStatus(SpStorage.getInstance().getDc04ProGain());
        FragmentDc04proBinding fragmentDc04proBinding28 = this._binding;
        if (fragmentDc04proBinding28 != null && (thirdSwitchBar = fragmentDc04proBinding28.gainThirdSwitchBar) != null) {
            thirdSwitchBar.setSeekTouchListener(new ThirdSwitchBar.SeekTouchListener() {
                @Override
                public void touchEnd(@Nullable SeekBar seekBar4) {
                    Dc04ProFragment.this.updateThreeGainTipsViewStatus(2);
                }

                @Override
                public void touchMiddle(@Nullable SeekBar seekBar4) {
                    Dc04ProFragment.this.updateThreeGainTipsViewStatus(1);
                }

                @Override
                public void touchTop(@Nullable SeekBar seekBar4) {
                    Dc04ProFragment.this.updateThreeGainTipsViewStatus(0);
                }
            });
        }
        FragmentDc04proBinding fragmentDc04proBinding29 = this._binding;
        Switch r43 = fragmentDc04proBinding29 != null ? fragmentDc04proBinding29.outputSwitch : null;
        if (r43 != null) {
            r43.setChecked(SpStorage.getInstance().getDc04ProOutput() == 1);
        }
        if (SpStorage.getInstance().getDc04ProOutput() == 1) {
            FragmentDc04proBinding fragmentDc04proBinding30 = this._binding;
            if (fragmentDc04proBinding30 != null && (textView5 = fragmentDc04proBinding30.outputSwitchTv) != null) {
                textView5.setText(R.string.output_turbo);
            }
        } else {
            FragmentDc04proBinding fragmentDc04proBinding31 = this._binding;
            if (fragmentDc04proBinding31 != null && (textView2 = fragmentDc04proBinding31.outputSwitchTv) != null) {
                textView2.setText(R.string.output_normal);
            }
        }
        FragmentDc04proBinding fragmentDc04proBinding32 = this._binding;
        if (fragmentDc04proBinding32 != null && (r42 = fragmentDc04proBinding32.outputSwitch) != null) {
            r42.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    Dc04ProFragment.onViewCreated$lambda$4(Dc04ProFragment.this, compoundButton, z);
                }
            });
        }
        int codecExternal = UacManager.getUacManager().getCodecExternal();
        FragmentDc04proBinding fragmentDc04proBinding33 = this._binding;
        Switch r5 = fragmentDc04proBinding33 != null ? fragmentDc04proBinding33.codecExternalSwitch : null;
        if (r5 != null) {
            r5.setChecked(codecExternal == 1);
        }
        if (codecExternal == 1) {
            FragmentDc04proBinding fragmentDc04proBinding34 = this._binding;
            if (fragmentDc04proBinding34 != null && (textView4 = fragmentDc04proBinding34.codecExternalSwitchTv) != null) {
                textView4.setText(R.string.on);
            }
        } else {
            FragmentDc04proBinding fragmentDc04proBinding35 = this._binding;
            if (fragmentDc04proBinding35 != null && (textView3 = fragmentDc04proBinding35.codecExternalSwitchTv) != null) {
                textView3.setText(R.string.off);
            }
        }
        FragmentDc04proBinding fragmentDc04proBinding36 = this._binding;
        if (fragmentDc04proBinding36 != null && (r4 = fragmentDc04proBinding36.codecExternalSwitch) != null) {
            r4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    Dc04ProFragment.onViewCreated$lambda$6(Dc04ProFragment.this, compoundButton, z);
                }
            });
        }
        EventBus.getDefault().register(this);
    }
}
