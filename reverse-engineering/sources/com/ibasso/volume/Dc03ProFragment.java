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
import com.ibasso.volume.databinding.FragmentDc03proBinding;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.HttpUrl;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
public final class Dc03ProFragment extends Fragment implements SeekBar.OnSeekBarChangeListener {
    @Nullable
    private FragmentDc03proBinding _binding;
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

    public static final void onViewCreated$lambda$4(Dc03ProFragment this$0, CompoundButton buttonView, boolean z) {
        TextView textView;
        TextView textView2;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(buttonView, "buttonView");
        if (z) {
            UacManager.getUacManager().setOutput(1);
            FragmentDc03proBinding fragmentDc03proBinding = this$0._binding;
            if (fragmentDc03proBinding == null || (textView2 = fragmentDc03proBinding.outputSwitchTv) == null) {
                return;
            }
            textView2.setText(R.string.output_turbo);
            return;
        }
        UacManager.getUacManager().setOutput(0);
        FragmentDc03proBinding fragmentDc03proBinding2 = this$0._binding;
        if (fragmentDc03proBinding2 == null || (textView = fragmentDc03proBinding2.outputSwitchTv) == null) {
            return;
        }
        textView.setText(R.string.output_normal);
    }

    public final void updateThreeGainTipsViewStatus(int i) {
        TextView textView;
        TextView textView2;
        TextView textView3;
        if (i == 1) {
            FragmentDc03proBinding fragmentDc03proBinding = this._binding;
            if (fragmentDc03proBinding != null && (textView = fragmentDc03proBinding.gainTipsTv) != null) {
                textView.setText(R.string.settings_gain_middle);
            }
        } else if (i != 2) {
            FragmentDc03proBinding fragmentDc03proBinding2 = this._binding;
            if (fragmentDc03proBinding2 != null && (textView3 = fragmentDc03proBinding2.gainTipsTv) != null) {
                textView3.setText(R.string.settings_gain_low);
            }
        } else {
            FragmentDc03proBinding fragmentDc03proBinding3 = this._binding;
            if (fragmentDc03proBinding3 != null && (textView2 = fragmentDc03proBinding3.gainTipsTv) != null) {
                textView2.setText(R.string.settings_gain_high);
            }
        }
        UacManager.getUacManager().setGain(i);
    }

    @Override
    @Nullable
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        FragmentDc03proBinding inflate = FragmentDc03proBinding.inflate(inflater, viewGroup, false);
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
        FragmentDc03proBinding fragmentDc03proBinding = this._binding;
        SeekBar seekBar = fragmentDc03proBinding != null ? fragmentDc03proBinding.volumeSeekBar : null;
        if (seekBar != null) {
            Integer valueOf = dc06VolumeEvent != null ? Integer.valueOf(dc06VolumeEvent.volume) : null;
            Intrinsics.checkNotNull(valueOf);
            seekBar.setProgress(valueOf.intValue());
        }
        FragmentDc03proBinding fragmentDc03proBinding2 = this._binding;
        TextView textView = fragmentDc03proBinding2 != null ? fragmentDc03proBinding2.volumeValueTv : null;
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
            FragmentDc03proBinding fragmentDc03proBinding = this._binding;
            if (fragmentDc03proBinding != null && (radioGroup5 = fragmentDc03proBinding.digitalFilterRg) != null) {
                radioGroup5.check(R.id.digitalFilter2Rg);
            }
            FragmentDc03proBinding fragmentDc03proBinding2 = this._binding;
            if (fragmentDc03proBinding2 == null || (textView5 = fragmentDc03proBinding2.digitalFilterValueTv) == null) {
                return;
            }
            textView5.setText(R.string.digital_filter_dx160_two);
        } else if (valueOf != null && valueOf.intValue() == 2) {
            FragmentDc03proBinding fragmentDc03proBinding3 = this._binding;
            if (fragmentDc03proBinding3 != null && (radioGroup4 = fragmentDc03proBinding3.digitalFilterRg) != null) {
                radioGroup4.check(R.id.digitalFilter3Rg);
            }
            FragmentDc03proBinding fragmentDc03proBinding4 = this._binding;
            if (fragmentDc03proBinding4 == null || (textView4 = fragmentDc03proBinding4.digitalFilterValueTv) == null) {
                return;
            }
            textView4.setText(R.string.digital_filter_dx160_three);
        } else if (valueOf != null && valueOf.intValue() == 194) {
            FragmentDc03proBinding fragmentDc03proBinding5 = this._binding;
            if (fragmentDc03proBinding5 != null && (radioGroup3 = fragmentDc03proBinding5.digitalFilterRg) != null) {
                radioGroup3.check(R.id.digitalFilter4Rg);
            }
            FragmentDc03proBinding fragmentDc03proBinding6 = this._binding;
            if (fragmentDc03proBinding6 == null || (textView3 = fragmentDc03proBinding6.digitalFilterValueTv) == null) {
                return;
            }
            textView3.setText(R.string.digital_filter_dx160_four);
        } else if (valueOf != null && valueOf.intValue() == 34) {
            FragmentDc03proBinding fragmentDc03proBinding7 = this._binding;
            if (fragmentDc03proBinding7 != null && (radioGroup2 = fragmentDc03proBinding7.digitalFilterRg) != null) {
                radioGroup2.check(R.id.digitalFilter5Rg);
            }
            FragmentDc03proBinding fragmentDc03proBinding8 = this._binding;
            if (fragmentDc03proBinding8 == null || (textView2 = fragmentDc03proBinding8.digitalFilterValueTv) == null) {
                return;
            }
            textView2.setText(R.string.digital_filter_dx160_five);
        } else {
            FragmentDc03proBinding fragmentDc03proBinding9 = this._binding;
            if (fragmentDc03proBinding9 != null && (radioGroup = fragmentDc03proBinding9.digitalFilterRg) != null) {
                radioGroup.check(R.id.digitalFilter1Rg);
            }
            FragmentDc03proBinding fragmentDc03proBinding10 = this._binding;
            if (fragmentDc03proBinding10 == null || (textView = fragmentDc03proBinding10.digitalFilterValueTv) == null) {
                return;
            }
            textView.setText(R.string.digital_filter_dx160_one);
        }
    }

    @Subscribe(sticky = EmbeddingCompat.DEBUG, threadMode = ThreadMode.MAIN)
    public final void onNormalVolumeEvent(@NotNull NormalVolumeEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
    }

    @Override
    public void onProgressChanged(@Nullable SeekBar seekBar, int i, boolean z) {
        FragmentDc03proBinding fragmentDc03proBinding = this._binding;
        TextView textView = fragmentDc03proBinding != null ? fragmentDc03proBinding.volumeValueTv : null;
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
            FragmentDc03proBinding fragmentDc03proBinding = this._binding;
            if (fragmentDc03proBinding != null && (imageView4 = fragmentDc03proBinding.volumeDownBt) != null) {
                imageView4.setImageResource(R.drawable.ic_volume_up);
            }
            FragmentDc03proBinding fragmentDc03proBinding2 = this._binding;
            if (fragmentDc03proBinding2 == null || (imageView3 = fragmentDc03proBinding2.volumeUpBt) == null) {
                return;
            }
            imageView3.setImageResource(R.drawable.ic_volume_down);
            return;
        }
        FragmentDc03proBinding fragmentDc03proBinding3 = this._binding;
        if (fragmentDc03proBinding3 != null && (imageView2 = fragmentDc03proBinding3.volumeDownBt) != null) {
            imageView2.setImageResource(R.drawable.ic_volume_down);
        }
        FragmentDc03proBinding fragmentDc03proBinding4 = this._binding;
        if (fragmentDc03proBinding4 == null || (imageView = fragmentDc03proBinding4.volumeUpBt) == null) {
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
        FragmentDc03proBinding fragmentDc03proBinding = this._binding;
        SeekBar seekBar2 = fragmentDc03proBinding != null ? fragmentDc03proBinding.volumeSeekBar : null;
        if (seekBar2 != null) {
            seekBar2.setMax(100);
        }
        FragmentDc03proBinding fragmentDc03proBinding2 = this._binding;
        SeekBar seekBar3 = fragmentDc03proBinding2 != null ? fragmentDc03proBinding2.volumeSeekBar : null;
        if (seekBar3 != null) {
            seekBar3.setProgress(UacManager.getUacManager().getVolume());
        }
        FragmentDc03proBinding fragmentDc03proBinding3 = this._binding;
        TextView textView8 = fragmentDc03proBinding3 != null ? fragmentDc03proBinding3.volumeValueTv : null;
        if (textView8 != null) {
            textView8.setText(HttpUrl.FRAGMENT_ENCODE_SET + UacManager.getUacManager().getVolume());
        }
        FragmentDc03proBinding fragmentDc03proBinding4 = this._binding;
        if (fragmentDc03proBinding4 != null && (seekBar = fragmentDc03proBinding4.volumeSeekBar) != null) {
            seekBar.setOnSeekBarChangeListener(this);
        }
        int digitalFilter = UacManager.getUacManager().getDigitalFilter() & 255;
        if (digitalFilter == 34) {
            FragmentDc03proBinding fragmentDc03proBinding5 = this._binding;
            if (fragmentDc03proBinding5 != null && (radioGroup = fragmentDc03proBinding5.digitalFilterRg) != null) {
                radioGroup.check(R.id.digitalFilter5Rg);
            }
            FragmentDc03proBinding fragmentDc03proBinding6 = this._binding;
            if (fragmentDc03proBinding6 != null && (textView = fragmentDc03proBinding6.digitalFilterValueTv) != null) {
                textView.setText(R.string.digital_filter_dx160_five);
            }
        } else if (digitalFilter == 66) {
            FragmentDc03proBinding fragmentDc03proBinding7 = this._binding;
            if (fragmentDc03proBinding7 != null && (radioGroup3 = fragmentDc03proBinding7.digitalFilterRg) != null) {
                radioGroup3.check(R.id.digitalFilter2Rg);
            }
            FragmentDc03proBinding fragmentDc03proBinding8 = this._binding;
            if (fragmentDc03proBinding8 != null && (textView4 = fragmentDc03proBinding8.digitalFilterValueTv) != null) {
                textView4.setText(R.string.digital_filter_dx160_two);
            }
        } else if (digitalFilter == 130) {
            FragmentDc03proBinding fragmentDc03proBinding9 = this._binding;
            if (fragmentDc03proBinding9 != null && (radioGroup4 = fragmentDc03proBinding9.digitalFilterRg) != null) {
                radioGroup4.check(R.id.digitalFilter4Rg);
            }
            FragmentDc03proBinding fragmentDc03proBinding10 = this._binding;
            if (fragmentDc03proBinding10 != null && (textView5 = fragmentDc03proBinding10.digitalFilterValueTv) != null) {
                textView5.setText(R.string.digital_filter_dx160_four);
            }
        } else if (digitalFilter != 194) {
            FragmentDc03proBinding fragmentDc03proBinding11 = this._binding;
            if (fragmentDc03proBinding11 != null && (radioGroup6 = fragmentDc03proBinding11.digitalFilterRg) != null) {
                radioGroup6.check(R.id.digitalFilter1Rg);
            }
            FragmentDc03proBinding fragmentDc03proBinding12 = this._binding;
            if (fragmentDc03proBinding12 != null && (textView7 = fragmentDc03proBinding12.digitalFilterValueTv) != null) {
                textView7.setText(R.string.digital_filter_dx160_one);
            }
        } else {
            FragmentDc03proBinding fragmentDc03proBinding13 = this._binding;
            if (fragmentDc03proBinding13 != null && (radioGroup5 = fragmentDc03proBinding13.digitalFilterRg) != null) {
                radioGroup5.check(R.id.digitalFilter3Rg);
            }
            FragmentDc03proBinding fragmentDc03proBinding14 = this._binding;
            if (fragmentDc03proBinding14 != null && (textView6 = fragmentDc03proBinding14.digitalFilterValueTv) != null) {
                textView6.setText(R.string.digital_filter_dx160_three);
            }
        }
        FragmentDc03proBinding fragmentDc03proBinding15 = this._binding;
        if (fragmentDc03proBinding15 != null && (radioGroup2 = fragmentDc03proBinding15.digitalFilterRg) != null) {
            radioGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public final void onCheckedChanged(RadioGroup radioGroup7, int i) {
                    Dc03ProFragment.onViewCreated$lambda$0(radioGroup7, i);
                }
            });
        }
        FragmentDc03proBinding fragmentDc03proBinding16 = this._binding;
        if (fragmentDc03proBinding16 != null && (balanceView2 = fragmentDc03proBinding16.balanceSeekBar) != null) {
            balanceView2.setSeekBarProgressClickListener(new BalanceView.ISeekBarProgressClickListener() {
                @Override
                public void onSeekBarChanged(@Nullable BalanceView balanceView3, int i) {
                    FragmentDc03proBinding fragmentDc03proBinding17;
                    TextView textView9;
                    FragmentDc03proBinding fragmentDc03proBinding18;
                    FragmentDc03proBinding fragmentDc03proBinding19;
                    UacManager.getUacManager().setBalanceVolume(i);
                    if (i > 0) {
                        fragmentDc03proBinding19 = Dc03ProFragment.this._binding;
                        textView9 = fragmentDc03proBinding19 != null ? fragmentDc03proBinding19.balanceValueTv : null;
                        if (textView9 == null) {
                            return;
                        }
                        textView9.setText("R-" + i);
                    } else if (i >= 0) {
                        fragmentDc03proBinding17 = Dc03ProFragment.this._binding;
                        textView9 = fragmentDc03proBinding17 != null ? fragmentDc03proBinding17.balanceValueTv : null;
                        if (textView9 == null) {
                            return;
                        }
                        textView9.setText("L=R");
                    } else {
                        fragmentDc03proBinding18 = Dc03ProFragment.this._binding;
                        textView9 = fragmentDc03proBinding18 != null ? fragmentDc03proBinding18.balanceValueTv : null;
                        if (textView9 == null) {
                            return;
                        }
                        StringBuilder sb = new StringBuilder();
                        sb.append('L');
                        sb.append(i);
                        textView9.setText(sb.toString());
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
            FragmentDc03proBinding fragmentDc03proBinding17 = this._binding;
            if (fragmentDc03proBinding17 != null && (imageView6 = fragmentDc03proBinding17.volumeDownBt) != null) {
                imageView6.setImageResource(R.drawable.ic_volume_up);
            }
            FragmentDc03proBinding fragmentDc03proBinding18 = this._binding;
            if (fragmentDc03proBinding18 != null && (imageView5 = fragmentDc03proBinding18.volumeUpBt) != null) {
                imageView5.setImageResource(R.drawable.ic_volume_down);
            }
        } else {
            FragmentDc03proBinding fragmentDc03proBinding19 = this._binding;
            if (fragmentDc03proBinding19 != null && (imageView2 = fragmentDc03proBinding19.volumeDownBt) != null) {
                imageView2.setImageResource(R.drawable.ic_volume_down);
            }
            FragmentDc03proBinding fragmentDc03proBinding20 = this._binding;
            if (fragmentDc03proBinding20 != null && (imageView = fragmentDc03proBinding20.volumeUpBt) != null) {
                imageView.setImageResource(R.drawable.ic_volume_up);
            }
        }
        FragmentDc03proBinding fragmentDc03proBinding21 = this._binding;
        if (fragmentDc03proBinding21 != null && (imageView4 = fragmentDc03proBinding21.volumeDownBt) != null) {
            imageView4.setOnClickListener(new View.OnClickListener() {
                @Override
                public final void onClick(View view2) {
                    Dc03ProFragment.onViewCreated$lambda$1(view2);
                }
            });
        }
        FragmentDc03proBinding fragmentDc03proBinding22 = this._binding;
        if (fragmentDc03proBinding22 != null && (imageView3 = fragmentDc03proBinding22.volumeUpBt) != null) {
            imageView3.setOnClickListener(new View.OnClickListener() {
                @Override
                public final void onClick(View view2) {
                    Dc03ProFragment.onViewCreated$lambda$2(view2);
                }
            });
        }
        int balanceVolume = UacManager.getUacManager().getBalanceVolume();
        if (balanceVolume > 0) {
            FragmentDc03proBinding fragmentDc03proBinding23 = this._binding;
            TextView textView9 = fragmentDc03proBinding23 != null ? fragmentDc03proBinding23.balanceValueTv : null;
            if (textView9 != null) {
                StringBuilder sb = new StringBuilder();
                sb.append('R');
                sb.append(balanceVolume);
                textView9.setText(sb.toString());
            }
        } else if (balanceVolume < 0) {
            FragmentDc03proBinding fragmentDc03proBinding24 = this._binding;
            TextView textView10 = fragmentDc03proBinding24 != null ? fragmentDc03proBinding24.balanceValueTv : null;
            if (textView10 != null) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append('L');
                sb2.append(-balanceVolume);
                textView10.setText(sb2.toString());
            }
        } else {
            FragmentDc03proBinding fragmentDc03proBinding25 = this._binding;
            TextView textView11 = fragmentDc03proBinding25 != null ? fragmentDc03proBinding25.balanceValueTv : null;
            if (textView11 != null) {
                textView11.setText("L=R");
            }
        }
        FragmentDc03proBinding fragmentDc03proBinding26 = this._binding;
        if (fragmentDc03proBinding26 != null && (balanceView = fragmentDc03proBinding26.balanceSeekBar) != null) {
            balanceView.setProgress(balanceVolume);
        }
        FragmentDc03proBinding fragmentDc03proBinding27 = this._binding;
        if (fragmentDc03proBinding27 != null && (thirdSwitchBar2 = fragmentDc03proBinding27.gainThirdSwitchBar) != null) {
            thirdSwitchBar2.setStatus(SpStorage.getInstance().getDc03ProGain());
        }
        updateThreeGainTipsViewStatus(SpStorage.getInstance().getDc03ProGain());
        FragmentDc03proBinding fragmentDc03proBinding28 = this._binding;
        if (fragmentDc03proBinding28 != null && (thirdSwitchBar = fragmentDc03proBinding28.gainThirdSwitchBar) != null) {
            thirdSwitchBar.setSeekTouchListener(new ThirdSwitchBar.SeekTouchListener() {
                @Override
                public void touchEnd(@Nullable SeekBar seekBar4) {
                    Dc03ProFragment.this.updateThreeGainTipsViewStatus(2);
                }

                @Override
                public void touchMiddle(@Nullable SeekBar seekBar4) {
                    Dc03ProFragment.this.updateThreeGainTipsViewStatus(1);
                }

                @Override
                public void touchTop(@Nullable SeekBar seekBar4) {
                    Dc03ProFragment.this.updateThreeGainTipsViewStatus(0);
                }
            });
        }
        FragmentDc03proBinding fragmentDc03proBinding29 = this._binding;
        Switch r5 = fragmentDc03proBinding29 != null ? fragmentDc03proBinding29.outputSwitch : null;
        if (r5 != null) {
            r5.setChecked(SpStorage.getInstance().getDc03ProOutput() == 1);
        }
        if (SpStorage.getInstance().getDc03ProOutput() == 1) {
            FragmentDc03proBinding fragmentDc03proBinding30 = this._binding;
            if (fragmentDc03proBinding30 != null && (textView3 = fragmentDc03proBinding30.outputSwitchTv) != null) {
                textView3.setText(R.string.output_turbo);
            }
        } else {
            FragmentDc03proBinding fragmentDc03proBinding31 = this._binding;
            if (fragmentDc03proBinding31 != null && (textView2 = fragmentDc03proBinding31.outputSwitchTv) != null) {
                textView2.setText(R.string.output_normal);
            }
        }
        FragmentDc03proBinding fragmentDc03proBinding32 = this._binding;
        if (fragmentDc03proBinding32 != null && (r4 = fragmentDc03proBinding32.outputSwitch) != null) {
            r4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    Dc03ProFragment.onViewCreated$lambda$4(Dc03ProFragment.this, compoundButton, z);
                }
            });
        }
        EventBus.getDefault().register(this);
    }
}
