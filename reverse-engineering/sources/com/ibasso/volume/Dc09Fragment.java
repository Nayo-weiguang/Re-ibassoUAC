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
import com.ibasso.volume.databinding.FragmentDc09Binding;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import okhttp3.HttpUrl;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
@SourceDebugExtension({"SMAP\nDc09Fragment.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Dc09Fragment.kt\ncom/ibasso/volume/Dc09Fragment\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,455:1\n1#2:456\n*E\n"})
public final class Dc09Fragment extends Fragment implements SeekBar.OnSeekBarChangeListener {
    @Nullable
    private FragmentDc09Binding _binding;
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
        FragmentDc09Binding fragmentDc09Binding;
        BalanceView balanceView3;
        if (!z && (fragmentDc09Binding = this._binding) != null && (balanceView3 = fragmentDc09Binding.balanceSeekBar) != null) {
            balanceView3.setSeekBarProgressClickListener(null);
        }
        int i2 = i - 10;
        if (i2 > 0) {
            FragmentDc09Binding fragmentDc09Binding2 = this._binding;
            textView = fragmentDc09Binding2 != null ? fragmentDc09Binding2.balanceValueTv : null;
            if (textView != null) {
                StringBuilder sb = new StringBuilder();
                sb.append('R');
                sb.append(i2);
                textView.setText(sb.toString());
            }
        } else if (i2 < 0) {
            FragmentDc09Binding fragmentDc09Binding3 = this._binding;
            textView = fragmentDc09Binding3 != null ? fragmentDc09Binding3.balanceValueTv : null;
            if (textView != null) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append('L');
                sb2.append(-i2);
                textView.setText(sb2.toString());
            }
        } else {
            FragmentDc09Binding fragmentDc09Binding4 = this._binding;
            textView = fragmentDc09Binding4 != null ? fragmentDc09Binding4.balanceValueTv : null;
            if (textView != null) {
                textView.setText("L=R");
            }
        }
        FragmentDc09Binding fragmentDc09Binding5 = this._binding;
        if (fragmentDc09Binding5 != null && (balanceView2 = fragmentDc09Binding5.balanceSeekBar) != null) {
            balanceView2.setProgress(i2);
        }
        FragmentDc09Binding fragmentDc09Binding6 = this._binding;
        if (fragmentDc09Binding6 == null || (balanceView = fragmentDc09Binding6.balanceSeekBar) == null) {
            return;
        }
        balanceView.setSeekBarProgressClickListener(new BalanceView.ISeekBarProgressClickListener() {
            @Override
            public void onSeekBarChanged(@Nullable BalanceView balanceView4, int i3) {
                FragmentDc09Binding fragmentDc09Binding7;
                TextView textView2;
                FragmentDc09Binding fragmentDc09Binding8;
                FragmentDc09Binding fragmentDc09Binding9;
                UacManager.getUacManager().setBalanceVolume(i3 + 10);
                if (i3 > 0) {
                    fragmentDc09Binding9 = Dc09Fragment.this._binding;
                    textView2 = fragmentDc09Binding9 != null ? fragmentDc09Binding9.balanceValueTv : null;
                    if (textView2 == null) {
                        return;
                    }
                    textView2.setText("R-" + i3);
                } else if (i3 >= 0) {
                    fragmentDc09Binding7 = Dc09Fragment.this._binding;
                    textView2 = fragmentDc09Binding7 != null ? fragmentDc09Binding7.balanceValueTv : null;
                    if (textView2 == null) {
                        return;
                    }
                    textView2.setText("L=R");
                } else {
                    fragmentDc09Binding8 = Dc09Fragment.this._binding;
                    textView2 = fragmentDc09Binding8 != null ? fragmentDc09Binding8.balanceValueTv : null;
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

    public static void updateBalance$default(Dc09Fragment dc09Fragment, int i, boolean z, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z = false;
        }
        dc09Fragment.updateBalance(i, z);
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
        FragmentDc09Binding fragmentDc09Binding;
        RadioGroup radioGroup7;
        if (!z && (fragmentDc09Binding = this._binding) != null && (radioGroup7 = fragmentDc09Binding.digitalFilterRg) != null) {
            radioGroup7.setOnCheckedChangeListener(null);
        }
        if (i == 1) {
            FragmentDc09Binding fragmentDc09Binding2 = this._binding;
            if (fragmentDc09Binding2 != null && (radioGroup = fragmentDc09Binding2.digitalFilterRg) != null) {
                radioGroup.check(R.id.digitalFilter2Rg);
            }
            FragmentDc09Binding fragmentDc09Binding3 = this._binding;
            if (fragmentDc09Binding3 != null && (textView = fragmentDc09Binding3.digitalFilterValueTv) != null) {
                textView.setText(R.string.digital_filter_dc07pro_two);
            }
        } else if (i == 2) {
            FragmentDc09Binding fragmentDc09Binding4 = this._binding;
            if (fragmentDc09Binding4 != null && (radioGroup3 = fragmentDc09Binding4.digitalFilterRg) != null) {
                radioGroup3.check(R.id.digitalFilter3Rg);
            }
            FragmentDc09Binding fragmentDc09Binding5 = this._binding;
            if (fragmentDc09Binding5 != null && (textView2 = fragmentDc09Binding5.digitalFilterValueTv) != null) {
                textView2.setText(R.string.digital_filter_dc07pro_three);
            }
        } else if (i == 3) {
            FragmentDc09Binding fragmentDc09Binding6 = this._binding;
            if (fragmentDc09Binding6 != null && (radioGroup4 = fragmentDc09Binding6.digitalFilterRg) != null) {
                radioGroup4.check(R.id.digitalFilter4Rg);
            }
            FragmentDc09Binding fragmentDc09Binding7 = this._binding;
            if (fragmentDc09Binding7 != null && (textView3 = fragmentDc09Binding7.digitalFilterValueTv) != null) {
                textView3.setText(R.string.digital_filter_dc07pro_four);
            }
        } else if (i != 4) {
            FragmentDc09Binding fragmentDc09Binding8 = this._binding;
            if (fragmentDc09Binding8 != null && (radioGroup6 = fragmentDc09Binding8.digitalFilterRg) != null) {
                radioGroup6.check(R.id.digitalFilter1Rg);
            }
            FragmentDc09Binding fragmentDc09Binding9 = this._binding;
            if (fragmentDc09Binding9 != null && (textView5 = fragmentDc09Binding9.digitalFilterValueTv) != null) {
                textView5.setText(R.string.digital_filter_dc07pro_one);
            }
        } else {
            FragmentDc09Binding fragmentDc09Binding10 = this._binding;
            if (fragmentDc09Binding10 != null && (radioGroup5 = fragmentDc09Binding10.digitalFilterRg) != null) {
                radioGroup5.check(R.id.digitalFilter5Rg);
            }
            FragmentDc09Binding fragmentDc09Binding11 = this._binding;
            if (fragmentDc09Binding11 != null && (textView4 = fragmentDc09Binding11.digitalFilterValueTv) != null) {
                textView4.setText(R.string.digital_filter_dc07pro_five);
            }
        }
        FragmentDc09Binding fragmentDc09Binding12 = this._binding;
        if (fragmentDc09Binding12 == null || (radioGroup2 = fragmentDc09Binding12.digitalFilterRg) == null) {
            return;
        }
        radioGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public final void onCheckedChanged(RadioGroup radioGroup8, int i2) {
                Dc09Fragment.updateDigitalFilter$lambda$3(Dc09Fragment.this, radioGroup8, i2);
            }
        });
    }

    public static void updateDigitalFilter$default(Dc09Fragment dc09Fragment, int i, boolean z, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z = true;
        }
        dc09Fragment.updateDigitalFilter(i, z);
    }

    public static final void updateDigitalFilter$lambda$3(Dc09Fragment this$0, RadioGroup radioGroup, int i) {
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
                FragmentDc09Binding fragmentDc09Binding = this$0._binding;
                if (fragmentDc09Binding == null || (textView = fragmentDc09Binding.digitalFilterValueTv) == null) {
                    return;
                }
                textView.setText(R.string.digital_filter_dc07pro_two);
                return;
            case R.id.digitalFilter3Rg:
                UacManager.getUacManager().setDigitalFilter(2);
                FragmentDc09Binding fragmentDc09Binding2 = this$0._binding;
                if (fragmentDc09Binding2 == null || (textView2 = fragmentDc09Binding2.digitalFilterValueTv) == null) {
                    return;
                }
                textView2.setText(R.string.digital_filter_dc07pro_three);
                return;
            case R.id.digitalFilter4Rg:
                UacManager.getUacManager().setDigitalFilter(3);
                FragmentDc09Binding fragmentDc09Binding3 = this$0._binding;
                if (fragmentDc09Binding3 == null || (textView3 = fragmentDc09Binding3.digitalFilterValueTv) == null) {
                    return;
                }
                textView3.setText(R.string.digital_filter_dc07pro_four);
                return;
            case R.id.digitalFilter5Rg:
                UacManager.getUacManager().setDigitalFilter(4);
                FragmentDc09Binding fragmentDc09Binding4 = this$0._binding;
                if (fragmentDc09Binding4 == null || (textView4 = fragmentDc09Binding4.digitalFilterValueTv) == null) {
                    return;
                }
                textView4.setText(R.string.digital_filter_dc07pro_five);
                return;
            default:
                UacManager.getUacManager().setDigitalFilter(0);
                FragmentDc09Binding fragmentDc09Binding5 = this$0._binding;
                if (fragmentDc09Binding5 == null || (textView5 = fragmentDc09Binding5.digitalFilterValueTv) == null) {
                    return;
                }
                textView5.setText(R.string.digital_filter_dc07pro_one);
                return;
        }
    }

    private final void updateGain(int i, boolean z) {
        Switch r4;
        FragmentDc09Binding fragmentDc09Binding;
        Switch r5;
        if (!z && (fragmentDc09Binding = this._binding) != null && (r5 = fragmentDc09Binding.gainSwitch) != null) {
            r5.setOnCheckedChangeListener(null);
        }
        FragmentDc09Binding fragmentDc09Binding2 = this._binding;
        Switch r52 = fragmentDc09Binding2 != null ? fragmentDc09Binding2.gainSwitch : null;
        if (r52 != null) {
            r52.setChecked(i == 1);
        }
        updateGainTipsViewStatus$default(this, i, false, 2, null);
        FragmentDc09Binding fragmentDc09Binding3 = this._binding;
        if (fragmentDc09Binding3 == null || (r4 = fragmentDc09Binding3.gainSwitch) == null) {
            return;
        }
        r4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z2) {
                Dc09Fragment.updateGain$lambda$5(Dc09Fragment.this, compoundButton, z2);
            }
        });
    }

    public static void updateGain$default(Dc09Fragment dc09Fragment, int i, boolean z, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z = true;
        }
        dc09Fragment.updateGain(i, z);
    }

    public static final void updateGain$lambda$5(Dc09Fragment this$0, CompoundButton buttonView, boolean z) {
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
            FragmentDc09Binding fragmentDc09Binding = this._binding;
            if (fragmentDc09Binding != null && (textView2 = fragmentDc09Binding.gainTipsTv) != null) {
                textView2.setText(R.string.settings_gain_low);
            }
        } else {
            FragmentDc09Binding fragmentDc09Binding2 = this._binding;
            if (fragmentDc09Binding2 != null && (textView = fragmentDc09Binding2.gainTipsTv) != null) {
                textView.setText(R.string.settings_gain_high);
            }
        }
        if (z) {
            UacManager.getUacManager().setGain(i);
        }
    }

    public static void updateGainTipsViewStatus$default(Dc09Fragment dc09Fragment, int i, boolean z, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z = false;
        }
        dc09Fragment.updateGainTipsViewStatus(i, z);
    }

    private final void updateHpFilter(int i, boolean z) {
        Switch r3;
        Switch r32;
        Switch r33;
        FragmentDc09Binding fragmentDc09Binding;
        Switch r4;
        if (!z && (fragmentDc09Binding = this._binding) != null && (r4 = fragmentDc09Binding.hpFilterSwitch) != null) {
            r4.setOnCheckedChangeListener(null);
        }
        FragmentDc09Binding fragmentDc09Binding2 = this._binding;
        Switch r0 = fragmentDc09Binding2 != null ? fragmentDc09Binding2.hpFilterSwitch : null;
        if (r0 != null) {
            r0.setChecked(i == 1);
        }
        if (i == 1) {
            FragmentDc09Binding fragmentDc09Binding3 = this._binding;
            if (fragmentDc09Binding3 != null && (r33 = fragmentDc09Binding3.hpFilterSwitch) != null) {
                r33.setText(R.string.on);
            }
        } else {
            FragmentDc09Binding fragmentDc09Binding4 = this._binding;
            if (fragmentDc09Binding4 != null && (r3 = fragmentDc09Binding4.hpFilterSwitch) != null) {
                r3.setText(R.string.off);
            }
        }
        FragmentDc09Binding fragmentDc09Binding5 = this._binding;
        if (fragmentDc09Binding5 == null || (r32 = fragmentDc09Binding5.hpFilterSwitch) == null) {
            return;
        }
        r32.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z2) {
                Dc09Fragment.updateHpFilter$lambda$13(Dc09Fragment.this, compoundButton, z2);
            }
        });
    }

    public static void updateHpFilter$default(Dc09Fragment dc09Fragment, int i, boolean z, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z = true;
        }
        dc09Fragment.updateHpFilter(i, z);
    }

    public static final void updateHpFilter$lambda$13(Dc09Fragment this$0, CompoundButton buttonView, boolean z) {
        TextView textView;
        TextView textView2;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(buttonView, "buttonView");
        if (z) {
            UacManager.getUacManager().setHpFilter(1);
            FragmentDc09Binding fragmentDc09Binding = this$0._binding;
            if (fragmentDc09Binding == null || (textView2 = fragmentDc09Binding.hpFilterSwitchTv) == null) {
                return;
            }
            textView2.setText(R.string.on);
            return;
        }
        UacManager.getUacManager().setHpFilter(0);
        FragmentDc09Binding fragmentDc09Binding2 = this$0._binding;
        if (fragmentDc09Binding2 == null || (textView = fragmentDc09Binding2.hpFilterSwitchTv) == null) {
            return;
        }
        textView.setText(R.string.off);
    }

    private final void updateKeyControllerBar(int i, boolean z) {
        TextView textView;
        Switch r2;
        TextView textView2;
        FragmentDc09Binding fragmentDc09Binding;
        Switch r3;
        if (!z && (fragmentDc09Binding = this._binding) != null && (r3 = fragmentDc09Binding.pcmVolumeTrimKeySwitch) != null) {
            r3.setOnCheckedChangeListener(null);
        }
        FragmentDc09Binding fragmentDc09Binding2 = this._binding;
        Switch r0 = fragmentDc09Binding2 != null ? fragmentDc09Binding2.pcmVolumeTrimKeySwitch : null;
        if (r0 != null) {
            r0.setChecked(i != 1);
        }
        if (i == 0) {
            FragmentDc09Binding fragmentDc09Binding3 = this._binding;
            if (fragmentDc09Binding3 != null && (textView2 = fragmentDc09Binding3.pcmVolumeTrimKeySwitchTv) != null) {
                textView2.setText(R.string.enable);
            }
        } else {
            FragmentDc09Binding fragmentDc09Binding4 = this._binding;
            if (fragmentDc09Binding4 != null && (textView = fragmentDc09Binding4.pcmVolumeTrimKeySwitchTv) != null) {
                textView.setText(R.string.disable);
            }
        }
        FragmentDc09Binding fragmentDc09Binding5 = this._binding;
        if (fragmentDc09Binding5 == null || (r2 = fragmentDc09Binding5.pcmVolumeTrimKeySwitch) == null) {
            return;
        }
        r2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z2) {
                Dc09Fragment.updateKeyControllerBar$lambda$15(Dc09Fragment.this, compoundButton, z2);
            }
        });
    }

    public static void updateKeyControllerBar$default(Dc09Fragment dc09Fragment, int i, boolean z, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z = true;
        }
        dc09Fragment.updateKeyControllerBar(i, z);
    }

    public static final void updateKeyControllerBar$lambda$15(Dc09Fragment this$0, CompoundButton buttonView, boolean z) {
        TextView textView;
        TextView textView2;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(buttonView, "buttonView");
        if (z) {
            UacManager.getUacManager().setKeyControlStatus(0);
            FragmentDc09Binding fragmentDc09Binding = this$0._binding;
            if (fragmentDc09Binding == null || (textView2 = fragmentDc09Binding.pcmVolumeTrimKeySwitchTv) == null) {
                return;
            }
            textView2.setText(R.string.enable);
            return;
        }
        UacManager.getUacManager().setKeyControlStatus(1);
        FragmentDc09Binding fragmentDc09Binding2 = this$0._binding;
        if (fragmentDc09Binding2 == null || (textView = fragmentDc09Binding2.pcmVolumeTrimKeySwitchTv) == null) {
            return;
        }
        textView.setText(R.string.disable);
    }

    private final void updateOutput(int i, boolean z) {
        TextView textView;
        Switch r3;
        TextView textView2;
        FragmentDc09Binding fragmentDc09Binding;
        Switch r4;
        if (!z && (fragmentDc09Binding = this._binding) != null && (r4 = fragmentDc09Binding.workModeSwitch) != null) {
            r4.setOnCheckedChangeListener(null);
        }
        FragmentDc09Binding fragmentDc09Binding2 = this._binding;
        Switch r0 = fragmentDc09Binding2 != null ? fragmentDc09Binding2.workModeSwitch : null;
        if (r0 != null) {
            r0.setChecked(i == 1);
        }
        if (i == 1) {
            FragmentDc09Binding fragmentDc09Binding3 = this._binding;
            if (fragmentDc09Binding3 != null && (textView2 = fragmentDc09Binding3.workModeSwitchTv) != null) {
                textView2.setText(R.string.work_mode_ab);
            }
        } else {
            FragmentDc09Binding fragmentDc09Binding4 = this._binding;
            if (fragmentDc09Binding4 != null && (textView = fragmentDc09Binding4.workModeSwitchTv) != null) {
                textView.setText(R.string.work_mode_tube);
            }
        }
        FragmentDc09Binding fragmentDc09Binding5 = this._binding;
        if (fragmentDc09Binding5 == null || (r3 = fragmentDc09Binding5.workModeSwitch) == null) {
            return;
        }
        r3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z2) {
                Dc09Fragment.updateOutput$lambda$9(Dc09Fragment.this, compoundButton, z2);
            }
        });
    }

    public static void updateOutput$default(Dc09Fragment dc09Fragment, int i, boolean z, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z = true;
        }
        dc09Fragment.updateOutput(i, z);
    }

    public static final void updateOutput$lambda$9(Dc09Fragment this$0, CompoundButton buttonView, boolean z) {
        TextView textView;
        TextView textView2;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(buttonView, "buttonView");
        if (z) {
            UacManager.getUacManager().setOutput(1);
            FragmentDc09Binding fragmentDc09Binding = this$0._binding;
            if (fragmentDc09Binding == null || (textView2 = fragmentDc09Binding.workModeSwitchTv) == null) {
                return;
            }
            textView2.setText(R.string.work_mode_ab);
            return;
        }
        UacManager.getUacManager().setOutput(0);
        FragmentDc09Binding fragmentDc09Binding2 = this$0._binding;
        if (fragmentDc09Binding2 == null || (textView = fragmentDc09Binding2.workModeSwitchTv) == null) {
            return;
        }
        textView.setText(R.string.work_mode_tube);
    }

    private final void updateScreenRation(int i, boolean z) {
        TextView textView;
        Switch r3;
        TextView textView2;
        FragmentDc09Binding fragmentDc09Binding;
        Switch r4;
        if (!z && (fragmentDc09Binding = this._binding) != null && (r4 = fragmentDc09Binding.screenRotateSwitch) != null) {
            r4.setOnCheckedChangeListener(null);
        }
        FragmentDc09Binding fragmentDc09Binding2 = this._binding;
        Switch r0 = fragmentDc09Binding2 != null ? fragmentDc09Binding2.screenRotateSwitch : null;
        if (r0 != null) {
            r0.setChecked(i == 1);
        }
        if (i == 1) {
            FragmentDc09Binding fragmentDc09Binding3 = this._binding;
            if (fragmentDc09Binding3 != null && (textView2 = fragmentDc09Binding3.screenRotateSwitchTv) != null) {
                textView2.setText(R.string.on);
            }
        } else {
            FragmentDc09Binding fragmentDc09Binding4 = this._binding;
            if (fragmentDc09Binding4 != null && (textView = fragmentDc09Binding4.screenRotateSwitchTv) != null) {
                textView.setText(R.string.off);
            }
        }
        FragmentDc09Binding fragmentDc09Binding5 = this._binding;
        if (fragmentDc09Binding5 == null || (r3 = fragmentDc09Binding5.screenRotateSwitch) == null) {
            return;
        }
        r3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z2) {
                Dc09Fragment.updateScreenRation$lambda$7(Dc09Fragment.this, compoundButton, z2);
            }
        });
    }

    public static void updateScreenRation$default(Dc09Fragment dc09Fragment, int i, boolean z, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z = true;
        }
        dc09Fragment.updateScreenRation(i, z);
    }

    public static final void updateScreenRation$lambda$7(Dc09Fragment this$0, CompoundButton buttonView, boolean z) {
        TextView textView;
        TextView textView2;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(buttonView, "buttonView");
        if (z) {
            UacManager.getUacManager().setScreenRotation(1);
            FragmentDc09Binding fragmentDc09Binding = this$0._binding;
            if (fragmentDc09Binding == null || (textView2 = fragmentDc09Binding.screenRotateSwitchTv) == null) {
                return;
            }
            textView2.setText(R.string.on);
            return;
        }
        UacManager.getUacManager().setScreenRotation(0);
        FragmentDc09Binding fragmentDc09Binding2 = this$0._binding;
        if (fragmentDc09Binding2 == null || (textView = fragmentDc09Binding2.screenRotateSwitchTv) == null) {
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
        FragmentDc09Binding fragmentDc09Binding;
        RadioGroup radioGroup6;
        if (!z && (fragmentDc09Binding = this._binding) != null && (radioGroup6 = fragmentDc09Binding.screenTimeRg) != null) {
            radioGroup6.setOnCheckedChangeListener(null);
        }
        if (i == 1) {
            FragmentDc09Binding fragmentDc09Binding2 = this._binding;
            if (fragmentDc09Binding2 != null && (radioGroup = fragmentDc09Binding2.screenTimeRg) != null) {
                radioGroup.check(R.id.screenTime1Rg);
            }
        } else if (i == 2) {
            FragmentDc09Binding fragmentDc09Binding3 = this._binding;
            if (fragmentDc09Binding3 != null && (radioGroup3 = fragmentDc09Binding3.screenTimeRg) != null) {
                radioGroup3.check(R.id.screenTime2Rg);
            }
        } else if (i != 3) {
            FragmentDc09Binding fragmentDc09Binding4 = this._binding;
            if (fragmentDc09Binding4 != null && (radioGroup5 = fragmentDc09Binding4.screenTimeRg) != null) {
                radioGroup5.check(R.id.screenTime0Rg);
            }
        } else {
            FragmentDc09Binding fragmentDc09Binding5 = this._binding;
            if (fragmentDc09Binding5 != null && (radioGroup4 = fragmentDc09Binding5.screenTimeRg) != null) {
                radioGroup4.check(R.id.screenTime3Rg);
            }
        }
        FragmentDc09Binding fragmentDc09Binding6 = this._binding;
        if (fragmentDc09Binding6 == null || (radioGroup2 = fragmentDc09Binding6.screenTimeRg) == null) {
            return;
        }
        radioGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public final void onCheckedChanged(RadioGroup radioGroup7, int i2) {
                Dc09Fragment.updateScreenTime$lambda$16(radioGroup7, i2);
            }
        });
    }

    public static void updateScreenTime$default(Dc09Fragment dc09Fragment, int i, boolean z, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z = false;
        }
        dc09Fragment.updateScreenTime(i, z);
    }

    public static final void updateScreenTime$lambda$16(RadioGroup radioGroup, int i) {
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
        FragmentDc09Binding fragmentDc09Binding;
        Switch r4;
        if (!z && (fragmentDc09Binding = this._binding) != null && (r4 = fragmentDc09Binding.codecExternalSwitch) != null) {
            r4.setOnCheckedChangeListener(null);
        }
        FragmentDc09Binding fragmentDc09Binding2 = this._binding;
        Switch r0 = fragmentDc09Binding2 != null ? fragmentDc09Binding2.codecExternalSwitch : null;
        if (r0 != null) {
            r0.setChecked(i == 1);
        }
        if (i == 1) {
            FragmentDc09Binding fragmentDc09Binding3 = this._binding;
            if (fragmentDc09Binding3 != null && (textView2 = fragmentDc09Binding3.codecExternalSwitchTv) != null) {
                textView2.setText(R.string.on);
            }
        } else {
            FragmentDc09Binding fragmentDc09Binding4 = this._binding;
            if (fragmentDc09Binding4 != null && (textView = fragmentDc09Binding4.codecExternalSwitchTv) != null) {
                textView.setText(R.string.off);
            }
        }
        FragmentDc09Binding fragmentDc09Binding5 = this._binding;
        if (fragmentDc09Binding5 == null || (r3 = fragmentDc09Binding5.codecExternalSwitch) == null) {
            return;
        }
        r3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z2) {
                Dc09Fragment.updateSpdif$lambda$11(Dc09Fragment.this, compoundButton, z2);
            }
        });
    }

    public static void updateSpdif$default(Dc09Fragment dc09Fragment, int i, boolean z, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z = true;
        }
        dc09Fragment.updateSpdif(i, z);
    }

    public static final void updateSpdif$lambda$11(Dc09Fragment this$0, CompoundButton buttonView, boolean z) {
        TextView textView;
        TextView textView2;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(buttonView, "buttonView");
        if (z) {
            UacManager.getUacManager().setCodecExternal(1);
            FragmentDc09Binding fragmentDc09Binding = this$0._binding;
            if (fragmentDc09Binding == null || (textView2 = fragmentDc09Binding.codecExternalSwitchTv) == null) {
                return;
            }
            textView2.setText(R.string.on);
            return;
        }
        UacManager.getUacManager().setCodecExternal(0);
        FragmentDc09Binding fragmentDc09Binding2 = this$0._binding;
        if (fragmentDc09Binding2 == null || (textView = fragmentDc09Binding2.codecExternalSwitchTv) == null) {
            return;
        }
        textView.setText(R.string.off);
    }

    @Override
    @Nullable
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        FragmentDc09Binding inflate = FragmentDc09Binding.inflate(inflater, viewGroup, false);
        this._binding = inflate;
        if (inflate != null) {
            return inflate.getRoot();
        }
        return null;
    }

    @Subscribe(sticky = EmbeddingCompat.DEBUG, threadMode = ThreadMode.MAIN)
    public final void onDc06VolumeEvent(@Nullable Dc06VolumeEvent dc06VolumeEvent) {
        FragmentDc09Binding fragmentDc09Binding = this._binding;
        SeekBar seekBar = fragmentDc09Binding != null ? fragmentDc09Binding.volumeSeekBar : null;
        if (seekBar == null) {
            return;
        }
        Intrinsics.checkNotNull(dc06VolumeEvent);
        seekBar.setProgress(dc06VolumeEvent.volume);
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
            FragmentDc09Binding fragmentDc09Binding = this._binding;
            TextView textView = fragmentDc09Binding != null ? fragmentDc09Binding.fpgaVersionTv : null;
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
                updateHpFilter(nativeReportEvent.value1, false);
            } else if (i == 96) {
                updateSpdif(nativeReportEvent.value, false);
                updateBalance(nativeReportEvent.value1, false);
                updateGain(nativeReportEvent.value2, false);
            } else if (i == 97) {
                updateScreenRation(nativeReportEvent.value, false);
                updateKeyControllerBar(nativeReportEvent.value1, false);
                updateScreenTime(nativeReportEvent.value2, false);
                updateOutput(nativeReportEvent.value3, false);
            }
        }
    }

    @Subscribe(sticky = EmbeddingCompat.DEBUG, threadMode = ThreadMode.MAIN)
    public final void onNormalVolumeEvent(@NotNull NormalVolumeEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
    }

    @Override
    public void onProgressChanged(@Nullable SeekBar seekBar, int i, boolean z) {
        FragmentDc09Binding fragmentDc09Binding = this._binding;
        TextView textView = fragmentDc09Binding != null ? fragmentDc09Binding.volumeValueTv : null;
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
        FragmentDc09Binding fragmentDc09Binding = this._binding;
        if (fragmentDc09Binding != null && (seekBar = fragmentDc09Binding.volumeSeekBar) != null) {
            seekBar.setOnSeekBarChangeListener(this);
        }
        if (SpStorage.getInstance().getIsVolumeBtReverse()) {
            FragmentDc09Binding fragmentDc09Binding2 = this._binding;
            if (fragmentDc09Binding2 != null && (imageView6 = fragmentDc09Binding2.volumeDownBt) != null) {
                imageView6.setImageResource(R.drawable.ic_volume_up);
            }
            FragmentDc09Binding fragmentDc09Binding3 = this._binding;
            if (fragmentDc09Binding3 != null && (imageView5 = fragmentDc09Binding3.volumeUpBt) != null) {
                imageView5.setImageResource(R.drawable.ic_volume_down);
            }
        } else {
            FragmentDc09Binding fragmentDc09Binding4 = this._binding;
            if (fragmentDc09Binding4 != null && (imageView2 = fragmentDc09Binding4.volumeDownBt) != null) {
                imageView2.setImageResource(R.drawable.ic_volume_down);
            }
            FragmentDc09Binding fragmentDc09Binding5 = this._binding;
            if (fragmentDc09Binding5 != null && (imageView = fragmentDc09Binding5.volumeUpBt) != null) {
                imageView.setImageResource(R.drawable.ic_volume_up);
            }
        }
        FragmentDc09Binding fragmentDc09Binding6 = this._binding;
        if (fragmentDc09Binding6 != null && (imageView4 = fragmentDc09Binding6.volumeDownBt) != null) {
            imageView4.setOnClickListener(new View.OnClickListener() {
                @Override
                public final void onClick(View view2) {
                    Dc09Fragment.onViewCreated$lambda$0(view2);
                }
            });
        }
        FragmentDc09Binding fragmentDc09Binding7 = this._binding;
        if (fragmentDc09Binding7 != null && (imageView3 = fragmentDc09Binding7.volumeUpBt) != null) {
            imageView3.setOnClickListener(new View.OnClickListener() {
                @Override
                public final void onClick(View view2) {
                    Dc09Fragment.onViewCreated$lambda$1(view2);
                }
            });
        }
        updateDigitalFilter(UacManager.getUacManager().getDigitalFilter() & 255, false);
        updateHpFilter(UacManager.getUacManager().getHpFilter() & 255, false);
        updateGain(SpStorage.getInstance().getDc09Gain(), false);
        updateScreenRation(SpStorage.getInstance().getDc09ScreenRotation(), false);
        updateOutput(SpStorage.getInstance().getDc09Output(), false);
        updateSpdif(UacManager.getUacManager().getCodecExternal(), false);
        updateKeyControllerBar(UacManager.getUacManager().getKeyControlStatus(), false);
        EventBus.getDefault().register(this);
    }
}
