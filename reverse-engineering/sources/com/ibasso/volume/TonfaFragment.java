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
import com.ibasso.volume.bean.NativeReportEvent;
import com.ibasso.volume.databinding.FragmentTonfaBinding;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import okhttp3.HttpUrl;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
@SourceDebugExtension({"SMAP\nTonfaFragment.kt\nKotlin\n*S Kotlin\n*F\n+ 1 TonfaFragment.kt\ncom/ibasso/volume/TonfaFragment\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,384:1\n1#2:385\n*E\n"})
public final class TonfaFragment extends Fragment implements SeekBar.OnSeekBarChangeListener {
    @Nullable
    private FragmentTonfaBinding _binding;
    private boolean isTouched;

    public static final void onViewCreated$lambda$0(View view) {
        if (SpStorage.getInstance().getIsVolumeBtReverse()) {
            UacManager.getUacManager().setVolume(true);
        } else {
            UacManager.getUacManager().setVolume(false);
        }
    }

    public static final void onViewCreated$lambda$1(View view) {
        if (SpStorage.getInstance().getIsVolumeBtReverse()) {
            UacManager.getUacManager().setVolume(false);
        } else {
            UacManager.getUacManager().setVolume(true);
        }
    }

    private final void updateBalance(int i, boolean z) {
        TextView textView;
        BalanceView balanceView;
        BalanceView balanceView2;
        FragmentTonfaBinding fragmentTonfaBinding;
        BalanceView balanceView3;
        if (!z && (fragmentTonfaBinding = this._binding) != null && (balanceView3 = fragmentTonfaBinding.balanceSeekBar) != null) {
            balanceView3.setSeekBarProgressClickListener(null);
        }
        int i2 = i - 10;
        if (i2 > 0) {
            FragmentTonfaBinding fragmentTonfaBinding2 = this._binding;
            textView = fragmentTonfaBinding2 != null ? fragmentTonfaBinding2.balanceValueTv : null;
            if (textView != null) {
                StringBuilder sb = new StringBuilder();
                sb.append('R');
                sb.append(i2);
                textView.setText(sb.toString());
            }
        } else if (i2 < 0) {
            FragmentTonfaBinding fragmentTonfaBinding3 = this._binding;
            textView = fragmentTonfaBinding3 != null ? fragmentTonfaBinding3.balanceValueTv : null;
            if (textView != null) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append('L');
                sb2.append(-i2);
                textView.setText(sb2.toString());
            }
        } else {
            FragmentTonfaBinding fragmentTonfaBinding4 = this._binding;
            textView = fragmentTonfaBinding4 != null ? fragmentTonfaBinding4.balanceValueTv : null;
            if (textView != null) {
                textView.setText("L=R");
            }
        }
        FragmentTonfaBinding fragmentTonfaBinding5 = this._binding;
        if (fragmentTonfaBinding5 != null && (balanceView2 = fragmentTonfaBinding5.balanceSeekBar) != null) {
            balanceView2.setProgress(i2);
        }
        FragmentTonfaBinding fragmentTonfaBinding6 = this._binding;
        if (fragmentTonfaBinding6 == null || (balanceView = fragmentTonfaBinding6.balanceSeekBar) == null) {
            return;
        }
        balanceView.setSeekBarProgressClickListener(new BalanceView.ISeekBarProgressClickListener() {
            @Override
            public void onSeekBarChanged(@Nullable BalanceView balanceView4, int i3) {
                FragmentTonfaBinding fragmentTonfaBinding7;
                TextView textView2;
                FragmentTonfaBinding fragmentTonfaBinding8;
                FragmentTonfaBinding fragmentTonfaBinding9;
                UacManager.getUacManager().setBalanceVolume(i3 + 10);
                if (i3 > 0) {
                    fragmentTonfaBinding9 = TonfaFragment.this._binding;
                    textView2 = fragmentTonfaBinding9 != null ? fragmentTonfaBinding9.balanceValueTv : null;
                    if (textView2 == null) {
                        return;
                    }
                    textView2.setText("R-" + i3);
                } else if (i3 >= 0) {
                    fragmentTonfaBinding7 = TonfaFragment.this._binding;
                    textView2 = fragmentTonfaBinding7 != null ? fragmentTonfaBinding7.balanceValueTv : null;
                    if (textView2 == null) {
                        return;
                    }
                    textView2.setText("L=R");
                } else {
                    fragmentTonfaBinding8 = TonfaFragment.this._binding;
                    textView2 = fragmentTonfaBinding8 != null ? fragmentTonfaBinding8.balanceValueTv : null;
                    if (textView2 == null) {
                        return;
                    }
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append('L');
                    sb3.append(i3);
                    textView2.setText(sb3.toString());
                }
            }

            @Override
            public void onSeekBarStart(@Nullable BalanceView balanceView4, int i3) {
            }

            @Override
            public void onSeekBarStop(@Nullable BalanceView balanceView4, int i3) {
            }
        });
    }

    public static void updateBalance$default(TonfaFragment tonfaFragment, int i, boolean z, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z = false;
        }
        tonfaFragment.updateBalance(i, z);
    }

    private final void updateDigitalFilter(int i, boolean z) {
        TextView textView;
        RadioGroup radioGroup;
        RadioGroup radioGroup2;
        TextView textView2;
        RadioGroup radioGroup3;
        TextView textView3;
        RadioGroup radioGroup4;
        TextView textView4;
        RadioGroup radioGroup5;
        TextView textView5;
        RadioGroup radioGroup6;
        FragmentTonfaBinding fragmentTonfaBinding;
        RadioGroup radioGroup7;
        if (!z && (fragmentTonfaBinding = this._binding) != null && (radioGroup7 = fragmentTonfaBinding.digitalFilterRg) != null) {
            radioGroup7.setOnCheckedChangeListener(null);
        }
        if (i == 1) {
            FragmentTonfaBinding fragmentTonfaBinding2 = this._binding;
            if (fragmentTonfaBinding2 != null && (radioGroup = fragmentTonfaBinding2.digitalFilterRg) != null) {
                radioGroup.check(R.id.digitalFilter2Rg);
            }
            FragmentTonfaBinding fragmentTonfaBinding3 = this._binding;
            if (fragmentTonfaBinding3 != null && (textView = fragmentTonfaBinding3.digitalFilterValueTv) != null) {
                textView.setText(R.string.digital_filter_dc07pro_two);
            }
        } else if (i == 2) {
            FragmentTonfaBinding fragmentTonfaBinding4 = this._binding;
            if (fragmentTonfaBinding4 != null && (radioGroup3 = fragmentTonfaBinding4.digitalFilterRg) != null) {
                radioGroup3.check(R.id.digitalFilter3Rg);
            }
            FragmentTonfaBinding fragmentTonfaBinding5 = this._binding;
            if (fragmentTonfaBinding5 != null && (textView2 = fragmentTonfaBinding5.digitalFilterValueTv) != null) {
                textView2.setText(R.string.digital_filter_dc07pro_three);
            }
        } else if (i == 3) {
            FragmentTonfaBinding fragmentTonfaBinding6 = this._binding;
            if (fragmentTonfaBinding6 != null && (radioGroup4 = fragmentTonfaBinding6.digitalFilterRg) != null) {
                radioGroup4.check(R.id.digitalFilter4Rg);
            }
            FragmentTonfaBinding fragmentTonfaBinding7 = this._binding;
            if (fragmentTonfaBinding7 != null && (textView3 = fragmentTonfaBinding7.digitalFilterValueTv) != null) {
                textView3.setText(R.string.digital_filter_dc07pro_four);
            }
        } else if (i != 4) {
            FragmentTonfaBinding fragmentTonfaBinding8 = this._binding;
            if (fragmentTonfaBinding8 != null && (radioGroup6 = fragmentTonfaBinding8.digitalFilterRg) != null) {
                radioGroup6.check(R.id.digitalFilter1Rg);
            }
            FragmentTonfaBinding fragmentTonfaBinding9 = this._binding;
            if (fragmentTonfaBinding9 != null && (textView5 = fragmentTonfaBinding9.digitalFilterValueTv) != null) {
                textView5.setText(R.string.digital_filter_dc07pro_one);
            }
        } else {
            FragmentTonfaBinding fragmentTonfaBinding10 = this._binding;
            if (fragmentTonfaBinding10 != null && (radioGroup5 = fragmentTonfaBinding10.digitalFilterRg) != null) {
                radioGroup5.check(R.id.digitalFilter5Rg);
            }
            FragmentTonfaBinding fragmentTonfaBinding11 = this._binding;
            if (fragmentTonfaBinding11 != null && (textView4 = fragmentTonfaBinding11.digitalFilterValueTv) != null) {
                textView4.setText(R.string.digital_filter_dc07pro_five);
            }
        }
        FragmentTonfaBinding fragmentTonfaBinding12 = this._binding;
        if (fragmentTonfaBinding12 == null || (radioGroup2 = fragmentTonfaBinding12.digitalFilterRg) == null) {
            return;
        }
        radioGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public final void onCheckedChanged(RadioGroup radioGroup8, int i2) {
                TonfaFragment.updateDigitalFilter$lambda$3(TonfaFragment.this, radioGroup8, i2);
            }
        });
    }

    public static void updateDigitalFilter$default(TonfaFragment tonfaFragment, int i, boolean z, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z = true;
        }
        tonfaFragment.updateDigitalFilter(i, z);
    }

    public static final void updateDigitalFilter$lambda$3(TonfaFragment this$0, RadioGroup radioGroup, int i) {
        TextView textView;
        TextView textView2;
        TextView textView3;
        TextView textView4;
        TextView textView5;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(radioGroup, "<anonymous parameter 0>");
        switch (i) {
            case R.id.digitalFilter2Rg:
                UacManager.getUacManager().setDigitalFilter(1);
                FragmentTonfaBinding fragmentTonfaBinding = this$0._binding;
                if (fragmentTonfaBinding == null || (textView = fragmentTonfaBinding.digitalFilterValueTv) == null) {
                    return;
                }
                textView.setText(R.string.digital_filter_dc07pro_two);
                return;
            case R.id.digitalFilter3Rg:
                UacManager.getUacManager().setDigitalFilter(2);
                FragmentTonfaBinding fragmentTonfaBinding2 = this$0._binding;
                if (fragmentTonfaBinding2 == null || (textView2 = fragmentTonfaBinding2.digitalFilterValueTv) == null) {
                    return;
                }
                textView2.setText(R.string.digital_filter_dc07pro_three);
                return;
            case R.id.digitalFilter4Rg:
                UacManager.getUacManager().setDigitalFilter(3);
                FragmentTonfaBinding fragmentTonfaBinding3 = this$0._binding;
                if (fragmentTonfaBinding3 == null || (textView3 = fragmentTonfaBinding3.digitalFilterValueTv) == null) {
                    return;
                }
                textView3.setText(R.string.digital_filter_dc07pro_four);
                return;
            case R.id.digitalFilter5Rg:
                UacManager.getUacManager().setDigitalFilter(4);
                FragmentTonfaBinding fragmentTonfaBinding4 = this$0._binding;
                if (fragmentTonfaBinding4 == null || (textView4 = fragmentTonfaBinding4.digitalFilterValueTv) == null) {
                    return;
                }
                textView4.setText(R.string.digital_filter_dc07pro_five);
                return;
            default:
                UacManager.getUacManager().setDigitalFilter(0);
                FragmentTonfaBinding fragmentTonfaBinding5 = this$0._binding;
                if (fragmentTonfaBinding5 == null || (textView5 = fragmentTonfaBinding5.digitalFilterValueTv) == null) {
                    return;
                }
                textView5.setText(R.string.digital_filter_dc07pro_one);
                return;
        }
    }

    private final void updateGain(int i, boolean z) {
        Switch r4;
        FragmentTonfaBinding fragmentTonfaBinding;
        Switch r5;
        if (!z && (fragmentTonfaBinding = this._binding) != null && (r5 = fragmentTonfaBinding.gainSwitch) != null) {
            r5.setOnCheckedChangeListener(null);
        }
        FragmentTonfaBinding fragmentTonfaBinding2 = this._binding;
        Switch r52 = fragmentTonfaBinding2 != null ? fragmentTonfaBinding2.gainSwitch : null;
        if (r52 != null) {
            r52.setChecked(i == 1);
        }
        updateGainTipsViewStatus$default(this, i, false, 2, null);
        FragmentTonfaBinding fragmentTonfaBinding3 = this._binding;
        if (fragmentTonfaBinding3 == null || (r4 = fragmentTonfaBinding3.gainSwitch) == null) {
            return;
        }
        r4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z2) {
                TonfaFragment.updateGain$lambda$5(TonfaFragment.this, compoundButton, z2);
            }
        });
    }

    public static void updateGain$default(TonfaFragment tonfaFragment, int i, boolean z, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z = true;
        }
        tonfaFragment.updateGain(i, z);
    }

    public static final void updateGain$lambda$5(TonfaFragment this$0, CompoundButton buttonView, boolean z) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(buttonView, "buttonView");
        if (z) {
            this$0.updateGainTipsViewStatus(1, true);
        } else {
            this$0.updateGainTipsViewStatus(0, true);
        }
    }

    private final void updateGainTipsViewStatus(int i, boolean z) {
        TextView textView;
        TextView textView2;
        if (i == 0) {
            FragmentTonfaBinding fragmentTonfaBinding = this._binding;
            if (fragmentTonfaBinding != null && (textView2 = fragmentTonfaBinding.gainTipsTv) != null) {
                textView2.setText(R.string.settings_gain_low);
            }
        } else {
            FragmentTonfaBinding fragmentTonfaBinding2 = this._binding;
            if (fragmentTonfaBinding2 != null && (textView = fragmentTonfaBinding2.gainTipsTv) != null) {
                textView.setText(R.string.settings_gain_high);
            }
        }
        if (z) {
            UacManager.getUacManager().setGain(i);
        }
    }

    public static void updateGainTipsViewStatus$default(TonfaFragment tonfaFragment, int i, boolean z, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z = false;
        }
        tonfaFragment.updateGainTipsViewStatus(i, z);
    }

    private final void updateKeyControllerBar(int i, boolean z) {
        TextView textView;
        Switch r2;
        TextView textView2;
        FragmentTonfaBinding fragmentTonfaBinding;
        Switch r3;
        if (!z && (fragmentTonfaBinding = this._binding) != null && (r3 = fragmentTonfaBinding.pcmVolumeTrimKeySwitch) != null) {
            r3.setOnCheckedChangeListener(null);
        }
        FragmentTonfaBinding fragmentTonfaBinding2 = this._binding;
        Switch r0 = fragmentTonfaBinding2 != null ? fragmentTonfaBinding2.pcmVolumeTrimKeySwitch : null;
        if (r0 != null) {
            r0.setChecked(i != 1);
        }
        if (i == 0) {
            FragmentTonfaBinding fragmentTonfaBinding3 = this._binding;
            if (fragmentTonfaBinding3 != null && (textView2 = fragmentTonfaBinding3.pcmVolumeTrimKeySwitchTv) != null) {
                textView2.setText(R.string.enable);
            }
        } else {
            FragmentTonfaBinding fragmentTonfaBinding4 = this._binding;
            if (fragmentTonfaBinding4 != null && (textView = fragmentTonfaBinding4.pcmVolumeTrimKeySwitchTv) != null) {
                textView.setText(R.string.disable);
            }
        }
        FragmentTonfaBinding fragmentTonfaBinding5 = this._binding;
        if (fragmentTonfaBinding5 == null || (r2 = fragmentTonfaBinding5.pcmVolumeTrimKeySwitch) == null) {
            return;
        }
        r2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z2) {
                TonfaFragment.updateKeyControllerBar$lambda$11(TonfaFragment.this, compoundButton, z2);
            }
        });
    }

    public static void updateKeyControllerBar$default(TonfaFragment tonfaFragment, int i, boolean z, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z = true;
        }
        tonfaFragment.updateKeyControllerBar(i, z);
    }

    public static final void updateKeyControllerBar$lambda$11(TonfaFragment this$0, CompoundButton buttonView, boolean z) {
        TextView textView;
        TextView textView2;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(buttonView, "buttonView");
        if (z) {
            UacManager.getUacManager().setKeyControlStatus(0);
            FragmentTonfaBinding fragmentTonfaBinding = this$0._binding;
            if (fragmentTonfaBinding == null || (textView2 = fragmentTonfaBinding.pcmVolumeTrimKeySwitchTv) == null) {
                return;
            }
            textView2.setText(R.string.enable);
            return;
        }
        UacManager.getUacManager().setKeyControlStatus(1);
        FragmentTonfaBinding fragmentTonfaBinding2 = this$0._binding;
        if (fragmentTonfaBinding2 == null || (textView = fragmentTonfaBinding2.pcmVolumeTrimKeySwitchTv) == null) {
            return;
        }
        textView.setText(R.string.disable);
    }

    private final void updateScreenRation(int i, boolean z) {
        TextView textView;
        Switch r3;
        TextView textView2;
        FragmentTonfaBinding fragmentTonfaBinding;
        Switch r4;
        if (!z && (fragmentTonfaBinding = this._binding) != null && (r4 = fragmentTonfaBinding.screenRotateSwitch) != null) {
            r4.setOnCheckedChangeListener(null);
        }
        FragmentTonfaBinding fragmentTonfaBinding2 = this._binding;
        Switch r0 = fragmentTonfaBinding2 != null ? fragmentTonfaBinding2.screenRotateSwitch : null;
        if (r0 != null) {
            r0.setChecked(i == 1);
        }
        if (i == 1) {
            FragmentTonfaBinding fragmentTonfaBinding3 = this._binding;
            if (fragmentTonfaBinding3 != null && (textView2 = fragmentTonfaBinding3.screenRotateSwitchTv) != null) {
                textView2.setText(R.string.on);
            }
        } else {
            FragmentTonfaBinding fragmentTonfaBinding4 = this._binding;
            if (fragmentTonfaBinding4 != null && (textView = fragmentTonfaBinding4.screenRotateSwitchTv) != null) {
                textView.setText(R.string.off);
            }
        }
        FragmentTonfaBinding fragmentTonfaBinding5 = this._binding;
        if (fragmentTonfaBinding5 == null || (r3 = fragmentTonfaBinding5.screenRotateSwitch) == null) {
            return;
        }
        r3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z2) {
                TonfaFragment.updateScreenRation$lambda$7(TonfaFragment.this, compoundButton, z2);
            }
        });
    }

    public static void updateScreenRation$default(TonfaFragment tonfaFragment, int i, boolean z, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z = true;
        }
        tonfaFragment.updateScreenRation(i, z);
    }

    public static final void updateScreenRation$lambda$7(TonfaFragment this$0, CompoundButton buttonView, boolean z) {
        TextView textView;
        TextView textView2;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(buttonView, "buttonView");
        if (z) {
            UacManager.getUacManager().setScreenRotation(1);
            FragmentTonfaBinding fragmentTonfaBinding = this$0._binding;
            if (fragmentTonfaBinding == null || (textView2 = fragmentTonfaBinding.screenRotateSwitchTv) == null) {
                return;
            }
            textView2.setText(R.string.on);
            return;
        }
        UacManager.getUacManager().setScreenRotation(0);
        FragmentTonfaBinding fragmentTonfaBinding2 = this$0._binding;
        if (fragmentTonfaBinding2 == null || (textView = fragmentTonfaBinding2.screenRotateSwitchTv) == null) {
            return;
        }
        textView.setText(R.string.off);
    }

    private final void updateScreenTime(int i, boolean z) {
        RadioGroup radioGroup;
        RadioGroup radioGroup2;
        RadioGroup radioGroup3;
        RadioGroup radioGroup4;
        RadioGroup radioGroup5;
        FragmentTonfaBinding fragmentTonfaBinding;
        RadioGroup radioGroup6;
        if (!z && (fragmentTonfaBinding = this._binding) != null && (radioGroup6 = fragmentTonfaBinding.screenTimeRg) != null) {
            radioGroup6.setOnCheckedChangeListener(null);
        }
        if (i == 1) {
            FragmentTonfaBinding fragmentTonfaBinding2 = this._binding;
            if (fragmentTonfaBinding2 != null && (radioGroup = fragmentTonfaBinding2.screenTimeRg) != null) {
                radioGroup.check(R.id.screenTime1Rg);
            }
        } else if (i == 2) {
            FragmentTonfaBinding fragmentTonfaBinding3 = this._binding;
            if (fragmentTonfaBinding3 != null && (radioGroup3 = fragmentTonfaBinding3.screenTimeRg) != null) {
                radioGroup3.check(R.id.screenTime2Rg);
            }
        } else if (i != 3) {
            FragmentTonfaBinding fragmentTonfaBinding4 = this._binding;
            if (fragmentTonfaBinding4 != null && (radioGroup5 = fragmentTonfaBinding4.screenTimeRg) != null) {
                radioGroup5.check(R.id.screenTime0Rg);
            }
        } else {
            FragmentTonfaBinding fragmentTonfaBinding5 = this._binding;
            if (fragmentTonfaBinding5 != null && (radioGroup4 = fragmentTonfaBinding5.screenTimeRg) != null) {
                radioGroup4.check(R.id.screenTime3Rg);
            }
        }
        FragmentTonfaBinding fragmentTonfaBinding6 = this._binding;
        if (fragmentTonfaBinding6 == null || (radioGroup2 = fragmentTonfaBinding6.screenTimeRg) == null) {
            return;
        }
        radioGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public final void onCheckedChanged(RadioGroup radioGroup7, int i2) {
                TonfaFragment.updateScreenTime$lambda$12(radioGroup7, i2);
            }
        });
    }

    public static void updateScreenTime$default(TonfaFragment tonfaFragment, int i, boolean z, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z = false;
        }
        tonfaFragment.updateScreenTime(i, z);
    }

    public static final void updateScreenTime$lambda$12(RadioGroup radioGroup, int i) {
        Intrinsics.checkNotNullParameter(radioGroup, "<anonymous parameter 0>");
        switch (i) {
            case R.id.screenTime1Rg:
                UacManager.getUacManager().setScreenTime(1);
                return;
            case R.id.screenTime2Rg:
                UacManager.getUacManager().setScreenTime(2);
                return;
            case R.id.screenTime3Rg:
                UacManager.getUacManager().setScreenTime(3);
                return;
            default:
                UacManager.getUacManager().setScreenTime(0);
                return;
        }
    }

    private final void updateSpdif(int i, boolean z) {
        TextView textView;
        Switch r3;
        TextView textView2;
        FragmentTonfaBinding fragmentTonfaBinding;
        Switch r4;
        if (!z && (fragmentTonfaBinding = this._binding) != null && (r4 = fragmentTonfaBinding.codecExternalSwitch) != null) {
            r4.setOnCheckedChangeListener(null);
        }
        FragmentTonfaBinding fragmentTonfaBinding2 = this._binding;
        Switch r0 = fragmentTonfaBinding2 != null ? fragmentTonfaBinding2.codecExternalSwitch : null;
        if (r0 != null) {
            r0.setChecked(i == 1);
        }
        if (i == 1) {
            FragmentTonfaBinding fragmentTonfaBinding3 = this._binding;
            if (fragmentTonfaBinding3 != null && (textView2 = fragmentTonfaBinding3.codecExternalSwitchTv) != null) {
                textView2.setText(R.string.on);
            }
        } else {
            FragmentTonfaBinding fragmentTonfaBinding4 = this._binding;
            if (fragmentTonfaBinding4 != null && (textView = fragmentTonfaBinding4.codecExternalSwitchTv) != null) {
                textView.setText(R.string.off);
            }
        }
        FragmentTonfaBinding fragmentTonfaBinding5 = this._binding;
        if (fragmentTonfaBinding5 == null || (r3 = fragmentTonfaBinding5.codecExternalSwitch) == null) {
            return;
        }
        r3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z2) {
                TonfaFragment.updateSpdif$lambda$9(TonfaFragment.this, compoundButton, z2);
            }
        });
    }

    public static void updateSpdif$default(TonfaFragment tonfaFragment, int i, boolean z, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z = true;
        }
        tonfaFragment.updateSpdif(i, z);
    }

    public static final void updateSpdif$lambda$9(TonfaFragment this$0, CompoundButton buttonView, boolean z) {
        TextView textView;
        TextView textView2;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(buttonView, "buttonView");
        if (z) {
            UacManager.getUacManager().setCodecExternal(1);
            FragmentTonfaBinding fragmentTonfaBinding = this$0._binding;
            if (fragmentTonfaBinding == null || (textView2 = fragmentTonfaBinding.codecExternalSwitchTv) == null) {
                return;
            }
            textView2.setText(R.string.on);
            return;
        }
        UacManager.getUacManager().setCodecExternal(0);
        FragmentTonfaBinding fragmentTonfaBinding2 = this$0._binding;
        if (fragmentTonfaBinding2 == null || (textView = fragmentTonfaBinding2.codecExternalSwitchTv) == null) {
            return;
        }
        textView.setText(R.string.off);
    }

    @Override
    @Nullable
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        FragmentTonfaBinding inflate = FragmentTonfaBinding.inflate(inflater, viewGroup, false);
        this._binding = inflate;
        if (inflate != null) {
            return inflate.getRoot();
        }
        return null;
    }

    @Subscribe(sticky = EmbeddingCompat.DEBUG, threadMode = ThreadMode.MAIN)
    public final void onDc06VolumeEvent(@NotNull Dc06VolumeEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        FragmentTonfaBinding fragmentTonfaBinding = this._binding;
        SeekBar seekBar = fragmentTonfaBinding != null ? fragmentTonfaBinding.volumeSeekBar : null;
        if (seekBar == null) {
            return;
        }
        seekBar.setProgress(event.volume);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
        this._binding = null;
    }

    @Subscribe(sticky = EmbeddingCompat.DEBUG, threadMode = ThreadMode.MAIN)
    public final void onFpgaVersionEvent(@Nullable FpgaVersionEvent fpgaVersionEvent) {
        if (fpgaVersionEvent != null) {
            FragmentTonfaBinding fragmentTonfaBinding = this._binding;
            TextView textView = fragmentTonfaBinding != null ? fragmentTonfaBinding.fpgaVersionTv : null;
            if (textView != null) {
                textView.setText(getString(R.string.version) + fpgaVersionEvent.fpgaVersion);
            }
            String str = fpgaVersionEvent.fpgaVersion;
            Intrinsics.checkNotNullExpressionValue(str, "event.fpgaVersion");
            StringsKt.split$default((CharSequence) str, new String[]{"."}, false, 0, 6, (Object) null);
        }
    }

    @Subscribe(sticky = EmbeddingCompat.DEBUG, threadMode = ThreadMode.MAIN)
    public final void onNativeReportEvent(@Nullable NativeReportEvent nativeReportEvent) {
        StringBuilder sb = new StringBuilder();
        sb.append("onNativeReportEvent ");
        sb.append(nativeReportEvent != null ? IConstants.idToString((byte) nativeReportEvent.type) : null);
        Log.d("UacManager", sb.toString());
        if (nativeReportEvent != null) {
            int i = nativeReportEvent.type;
            if (89 == i) {
                updateDigitalFilter(nativeReportEvent.value, false);
            } else if (i == 96) {
                updateSpdif(nativeReportEvent.value, false);
                updateBalance(nativeReportEvent.value1, false);
                updateGain(nativeReportEvent.value2, false);
            } else if (i == 97) {
                updateScreenRation(nativeReportEvent.value, false);
                updateKeyControllerBar(nativeReportEvent.value1, false);
                updateScreenTime(nativeReportEvent.value2, false);
            }
        }
    }

    @Subscribe(sticky = EmbeddingCompat.DEBUG, threadMode = ThreadMode.MAIN)
    public final void onNormalVolumeEvent(@NotNull NormalVolumeEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
    }

    @Override
    public void onProgressChanged(@Nullable SeekBar seekBar, int i, boolean z) {
        FragmentTonfaBinding fragmentTonfaBinding = this._binding;
        TextView textView = fragmentTonfaBinding != null ? fragmentTonfaBinding.volumeValueTv : null;
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
        super.onResume();
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
        ImageView imageView3;
        ImageView imageView4;
        ImageView imageView5;
        ImageView imageView6;
        SeekBar seekBar;
        Intrinsics.checkNotNullParameter(view, "view");
        super.onViewCreated(view, bundle);
        FragmentTonfaBinding fragmentTonfaBinding = this._binding;
        if (fragmentTonfaBinding != null && (seekBar = fragmentTonfaBinding.volumeSeekBar) != null) {
            seekBar.setOnSeekBarChangeListener(this);
        }
        if (SpStorage.getInstance().getIsVolumeBtReverse()) {
            FragmentTonfaBinding fragmentTonfaBinding2 = this._binding;
            if (fragmentTonfaBinding2 != null && (imageView6 = fragmentTonfaBinding2.volumeDownBt) != null) {
                imageView6.setImageResource(R.drawable.ic_volume_up);
            }
            FragmentTonfaBinding fragmentTonfaBinding3 = this._binding;
            if (fragmentTonfaBinding3 != null && (imageView5 = fragmentTonfaBinding3.volumeUpBt) != null) {
                imageView5.setImageResource(R.drawable.ic_volume_down);
            }
        } else {
            FragmentTonfaBinding fragmentTonfaBinding4 = this._binding;
            if (fragmentTonfaBinding4 != null && (imageView2 = fragmentTonfaBinding4.volumeDownBt) != null) {
                imageView2.setImageResource(R.drawable.ic_volume_down);
            }
            FragmentTonfaBinding fragmentTonfaBinding5 = this._binding;
            if (fragmentTonfaBinding5 != null && (imageView = fragmentTonfaBinding5.volumeUpBt) != null) {
                imageView.setImageResource(R.drawable.ic_volume_up);
            }
        }
        FragmentTonfaBinding fragmentTonfaBinding6 = this._binding;
        if (fragmentTonfaBinding6 != null && (imageView4 = fragmentTonfaBinding6.volumeDownBt) != null) {
            imageView4.setOnClickListener(new View.OnClickListener() {
                @Override
                public final void onClick(View view2) {
                    TonfaFragment.onViewCreated$lambda$0(view2);
                }
            });
        }
        FragmentTonfaBinding fragmentTonfaBinding7 = this._binding;
        if (fragmentTonfaBinding7 != null && (imageView3 = fragmentTonfaBinding7.volumeUpBt) != null) {
            imageView3.setOnClickListener(new View.OnClickListener() {
                @Override
                public final void onClick(View view2) {
                    TonfaFragment.onViewCreated$lambda$1(view2);
                }
            });
        }
        updateDigitalFilter(UacManager.getUacManager().getDigitalFilter() & 255, false);
        updateGain(SpStorage.getInstance().getTonfaGain(), false);
        updateScreenRation(SpStorage.getInstance().getTonfaScreenRotation(), false);
        updateSpdif(UacManager.getUacManager().getCodecExternal(), false);
        updateKeyControllerBar(UacManager.getUacManager().getKeyControlStatus(), false);
        EventBus.getDefault().register(this);
    }
}
