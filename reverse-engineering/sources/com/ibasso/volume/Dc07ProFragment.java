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
import com.ibasso.volume.bean.NativeReportEvent;
import com.ibasso.volume.databinding.FragmentDc07proBinding;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import okhttp3.HttpUrl;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
@SourceDebugExtension({"SMAP\nDc07ProFragment.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Dc07ProFragment.kt\ncom/ibasso/volume/Dc07ProFragment\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,438:1\n1#2:439\n*E\n"})
public final class Dc07ProFragment extends Fragment implements SeekBar.OnSeekBarChangeListener {
    @Nullable
    private FragmentDc07proBinding _binding;
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
        FragmentDc07proBinding fragmentDc07proBinding;
        BalanceView balanceView3;
        if (!z && (fragmentDc07proBinding = this._binding) != null && (balanceView3 = fragmentDc07proBinding.balanceSeekBar) != null) {
            balanceView3.setSeekBarProgressClickListener(null);
        }
        int i2 = i - 10;
        if (i2 > 0) {
            FragmentDc07proBinding fragmentDc07proBinding2 = this._binding;
            textView = fragmentDc07proBinding2 != null ? fragmentDc07proBinding2.balanceValueTv : null;
            if (textView != null) {
                StringBuilder sb = new StringBuilder();
                sb.append('R');
                sb.append(i2);
                textView.setText(sb.toString());
            }
        } else if (i2 < 0) {
            FragmentDc07proBinding fragmentDc07proBinding3 = this._binding;
            textView = fragmentDc07proBinding3 != null ? fragmentDc07proBinding3.balanceValueTv : null;
            if (textView != null) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append('L');
                sb2.append(-i2);
                textView.setText(sb2.toString());
            }
        } else {
            FragmentDc07proBinding fragmentDc07proBinding4 = this._binding;
            textView = fragmentDc07proBinding4 != null ? fragmentDc07proBinding4.balanceValueTv : null;
            if (textView != null) {
                textView.setText("L=R");
            }
        }
        FragmentDc07proBinding fragmentDc07proBinding5 = this._binding;
        if (fragmentDc07proBinding5 != null && (balanceView2 = fragmentDc07proBinding5.balanceSeekBar) != null) {
            balanceView2.setProgress(i2);
        }
        FragmentDc07proBinding fragmentDc07proBinding6 = this._binding;
        if (fragmentDc07proBinding6 == null || (balanceView = fragmentDc07proBinding6.balanceSeekBar) == null) {
            return;
        }
        balanceView.setSeekBarProgressClickListener(new BalanceView.ISeekBarProgressClickListener() {
            @Override
            public void onSeekBarChanged(@Nullable BalanceView balanceView4, int i3) {
                FragmentDc07proBinding fragmentDc07proBinding7;
                TextView textView2;
                FragmentDc07proBinding fragmentDc07proBinding8;
                FragmentDc07proBinding fragmentDc07proBinding9;
                UacManager.getUacManager().setBalanceVolume(i3 + 10);
                if (i3 > 0) {
                    fragmentDc07proBinding9 = Dc07ProFragment.this._binding;
                    textView2 = fragmentDc07proBinding9 != null ? fragmentDc07proBinding9.balanceValueTv : null;
                    if (textView2 == null) {
                        return;
                    }
                    textView2.setText("R-" + i3);
                } else if (i3 >= 0) {
                    fragmentDc07proBinding7 = Dc07ProFragment.this._binding;
                    textView2 = fragmentDc07proBinding7 != null ? fragmentDc07proBinding7.balanceValueTv : null;
                    if (textView2 == null) {
                        return;
                    }
                    textView2.setText("L=R");
                } else {
                    fragmentDc07proBinding8 = Dc07ProFragment.this._binding;
                    textView2 = fragmentDc07proBinding8 != null ? fragmentDc07proBinding8.balanceValueTv : null;
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

    public static void updateBalance$default(Dc07ProFragment dc07ProFragment, int i, boolean z, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z = false;
        }
        dc07ProFragment.updateBalance(i, z);
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
        FragmentDc07proBinding fragmentDc07proBinding;
        RadioGroup radioGroup7;
        if (!z && (fragmentDc07proBinding = this._binding) != null && (radioGroup7 = fragmentDc07proBinding.digitalFilterRg) != null) {
            radioGroup7.setOnCheckedChangeListener(null);
        }
        if (i == 1) {
            FragmentDc07proBinding fragmentDc07proBinding2 = this._binding;
            if (fragmentDc07proBinding2 != null && (radioGroup = fragmentDc07proBinding2.digitalFilterRg) != null) {
                radioGroup.check(R.id.digitalFilter2Rg);
            }
            FragmentDc07proBinding fragmentDc07proBinding3 = this._binding;
            if (fragmentDc07proBinding3 != null && (textView = fragmentDc07proBinding3.digitalFilterValueTv) != null) {
                textView.setText(R.string.digital_filter_dc07pro_two);
            }
        } else if (i == 2) {
            FragmentDc07proBinding fragmentDc07proBinding4 = this._binding;
            if (fragmentDc07proBinding4 != null && (radioGroup3 = fragmentDc07proBinding4.digitalFilterRg) != null) {
                radioGroup3.check(R.id.digitalFilter3Rg);
            }
            FragmentDc07proBinding fragmentDc07proBinding5 = this._binding;
            if (fragmentDc07proBinding5 != null && (textView2 = fragmentDc07proBinding5.digitalFilterValueTv) != null) {
                textView2.setText(R.string.digital_filter_dc07pro_three);
            }
        } else if (i == 3) {
            FragmentDc07proBinding fragmentDc07proBinding6 = this._binding;
            if (fragmentDc07proBinding6 != null && (radioGroup4 = fragmentDc07proBinding6.digitalFilterRg) != null) {
                radioGroup4.check(R.id.digitalFilter4Rg);
            }
            FragmentDc07proBinding fragmentDc07proBinding7 = this._binding;
            if (fragmentDc07proBinding7 != null && (textView3 = fragmentDc07proBinding7.digitalFilterValueTv) != null) {
                textView3.setText(R.string.digital_filter_dc07pro_four);
            }
        } else if (i != 4) {
            FragmentDc07proBinding fragmentDc07proBinding8 = this._binding;
            if (fragmentDc07proBinding8 != null && (radioGroup6 = fragmentDc07proBinding8.digitalFilterRg) != null) {
                radioGroup6.check(R.id.digitalFilter1Rg);
            }
            FragmentDc07proBinding fragmentDc07proBinding9 = this._binding;
            if (fragmentDc07proBinding9 != null && (textView5 = fragmentDc07proBinding9.digitalFilterValueTv) != null) {
                textView5.setText(R.string.digital_filter_dc07pro_one);
            }
        } else {
            FragmentDc07proBinding fragmentDc07proBinding10 = this._binding;
            if (fragmentDc07proBinding10 != null && (radioGroup5 = fragmentDc07proBinding10.digitalFilterRg) != null) {
                radioGroup5.check(R.id.digitalFilter5Rg);
            }
            FragmentDc07proBinding fragmentDc07proBinding11 = this._binding;
            if (fragmentDc07proBinding11 != null && (textView4 = fragmentDc07proBinding11.digitalFilterValueTv) != null) {
                textView4.setText(R.string.digital_filter_dc07pro_five);
            }
        }
        FragmentDc07proBinding fragmentDc07proBinding12 = this._binding;
        if (fragmentDc07proBinding12 == null || (radioGroup2 = fragmentDc07proBinding12.digitalFilterRg) == null) {
            return;
        }
        radioGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public final void onCheckedChanged(RadioGroup radioGroup8, int i2) {
                Dc07ProFragment.updateDigitalFilter$lambda$3(Dc07ProFragment.this, radioGroup8, i2);
            }
        });
    }

    public static void updateDigitalFilter$default(Dc07ProFragment dc07ProFragment, int i, boolean z, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z = true;
        }
        dc07ProFragment.updateDigitalFilter(i, z);
    }

    public static final void updateDigitalFilter$lambda$3(Dc07ProFragment this$0, RadioGroup radioGroup, int i) {
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
                FragmentDc07proBinding fragmentDc07proBinding = this$0._binding;
                if (fragmentDc07proBinding == null || (textView = fragmentDc07proBinding.digitalFilterValueTv) == null) {
                    return;
                }
                textView.setText(R.string.digital_filter_dc07pro_two);
                return;
            case R.id.digitalFilter3Rg:
                UacManager.getUacManager().setDigitalFilter(2);
                FragmentDc07proBinding fragmentDc07proBinding2 = this$0._binding;
                if (fragmentDc07proBinding2 == null || (textView2 = fragmentDc07proBinding2.digitalFilterValueTv) == null) {
                    return;
                }
                textView2.setText(R.string.digital_filter_dc07pro_three);
                return;
            case R.id.digitalFilter4Rg:
                UacManager.getUacManager().setDigitalFilter(3);
                FragmentDc07proBinding fragmentDc07proBinding3 = this$0._binding;
                if (fragmentDc07proBinding3 == null || (textView3 = fragmentDc07proBinding3.digitalFilterValueTv) == null) {
                    return;
                }
                textView3.setText(R.string.digital_filter_dc07pro_four);
                return;
            case R.id.digitalFilter5Rg:
                UacManager.getUacManager().setDigitalFilter(4);
                FragmentDc07proBinding fragmentDc07proBinding4 = this$0._binding;
                if (fragmentDc07proBinding4 == null || (textView4 = fragmentDc07proBinding4.digitalFilterValueTv) == null) {
                    return;
                }
                textView4.setText(R.string.digital_filter_dc07pro_five);
                return;
            default:
                UacManager.getUacManager().setDigitalFilter(0);
                FragmentDc07proBinding fragmentDc07proBinding5 = this$0._binding;
                if (fragmentDc07proBinding5 == null || (textView5 = fragmentDc07proBinding5.digitalFilterValueTv) == null) {
                    return;
                }
                textView5.setText(R.string.digital_filter_dc07pro_one);
                return;
        }
    }

    private final void updateGain(int i, boolean z) {
        ThirdSwitchBar thirdSwitchBar;
        ThirdSwitchBar thirdSwitchBar2;
        FragmentDc07proBinding fragmentDc07proBinding;
        ThirdSwitchBar thirdSwitchBar3;
        if (!z && (fragmentDc07proBinding = this._binding) != null && (thirdSwitchBar3 = fragmentDc07proBinding.gainhirdSwitchBar) != null) {
            thirdSwitchBar3.setSeekTouchListener(null);
        }
        FragmentDc07proBinding fragmentDc07proBinding2 = this._binding;
        if (fragmentDc07proBinding2 != null && (thirdSwitchBar2 = fragmentDc07proBinding2.gainhirdSwitchBar) != null) {
            thirdSwitchBar2.setStatus(i);
        }
        updateThreeGainTipsViewStatus$default(this, i, false, 2, null);
        FragmentDc07proBinding fragmentDc07proBinding3 = this._binding;
        if (fragmentDc07proBinding3 == null || (thirdSwitchBar = fragmentDc07proBinding3.gainhirdSwitchBar) == null) {
            return;
        }
        thirdSwitchBar.setSeekTouchListener(new ThirdSwitchBar.SeekTouchListener() {
            @Override
            public void touchEnd(@Nullable SeekBar seekBar) {
                Dc07ProFragment.this.updateThreeGainTipsViewStatus(2, true);
            }

            @Override
            public void touchMiddle(@Nullable SeekBar seekBar) {
                Dc07ProFragment.this.updateThreeGainTipsViewStatus(1, true);
            }

            @Override
            public void touchTop(@Nullable SeekBar seekBar) {
                Dc07ProFragment.this.updateThreeGainTipsViewStatus(0, true);
            }
        });
    }

    public static void updateGain$default(Dc07ProFragment dc07ProFragment, int i, boolean z, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z = true;
        }
        dc07ProFragment.updateGain(i, z);
    }

    private final void updateHpFilter(int i, boolean z) {
        Switch r3;
        Switch r32;
        Switch r33;
        FragmentDc07proBinding fragmentDc07proBinding;
        Switch r4;
        if (!z && (fragmentDc07proBinding = this._binding) != null && (r4 = fragmentDc07proBinding.hpFilterSwitch) != null) {
            r4.setOnCheckedChangeListener(null);
        }
        FragmentDc07proBinding fragmentDc07proBinding2 = this._binding;
        Switch r0 = fragmentDc07proBinding2 != null ? fragmentDc07proBinding2.hpFilterSwitch : null;
        if (r0 != null) {
            r0.setChecked(i == 1);
        }
        if (i == 1) {
            FragmentDc07proBinding fragmentDc07proBinding3 = this._binding;
            if (fragmentDc07proBinding3 != null && (r33 = fragmentDc07proBinding3.hpFilterSwitch) != null) {
                r33.setText(R.string.on);
            }
        } else {
            FragmentDc07proBinding fragmentDc07proBinding4 = this._binding;
            if (fragmentDc07proBinding4 != null && (r3 = fragmentDc07proBinding4.hpFilterSwitch) != null) {
                r3.setText(R.string.off);
            }
        }
        FragmentDc07proBinding fragmentDc07proBinding5 = this._binding;
        if (fragmentDc07proBinding5 == null || (r32 = fragmentDc07proBinding5.hpFilterSwitch) == null) {
            return;
        }
        r32.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z2) {
                Dc07ProFragment.updateHpFilter$lambda$9(Dc07ProFragment.this, compoundButton, z2);
            }
        });
    }

    public static void updateHpFilter$default(Dc07ProFragment dc07ProFragment, int i, boolean z, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z = true;
        }
        dc07ProFragment.updateHpFilter(i, z);
    }

    public static final void updateHpFilter$lambda$9(Dc07ProFragment this$0, CompoundButton buttonView, boolean z) {
        TextView textView;
        TextView textView2;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(buttonView, "buttonView");
        if (z) {
            UacManager.getUacManager().setHpFilter(1);
            FragmentDc07proBinding fragmentDc07proBinding = this$0._binding;
            if (fragmentDc07proBinding == null || (textView2 = fragmentDc07proBinding.hpFilterSwitchTv) == null) {
                return;
            }
            textView2.setText(R.string.on);
            return;
        }
        UacManager.getUacManager().setHpFilter(0);
        FragmentDc07proBinding fragmentDc07proBinding2 = this$0._binding;
        if (fragmentDc07proBinding2 == null || (textView = fragmentDc07proBinding2.hpFilterSwitchTv) == null) {
            return;
        }
        textView.setText(R.string.off);
    }

    private final void updateKeyControllerBar(int i, boolean z) {
        TextView textView;
        Switch r2;
        TextView textView2;
        FragmentDc07proBinding fragmentDc07proBinding;
        Switch r3;
        if (!z && (fragmentDc07proBinding = this._binding) != null && (r3 = fragmentDc07proBinding.pcmVolumeTrimKeySwitch) != null) {
            r3.setOnCheckedChangeListener(null);
        }
        FragmentDc07proBinding fragmentDc07proBinding2 = this._binding;
        Switch r0 = fragmentDc07proBinding2 != null ? fragmentDc07proBinding2.pcmVolumeTrimKeySwitch : null;
        if (r0 != null) {
            r0.setChecked(i != 1);
        }
        if (i == 0) {
            FragmentDc07proBinding fragmentDc07proBinding3 = this._binding;
            if (fragmentDc07proBinding3 != null && (textView2 = fragmentDc07proBinding3.pcmVolumeTrimKeySwitchTv) != null) {
                textView2.setText(R.string.enable);
            }
        } else {
            FragmentDc07proBinding fragmentDc07proBinding4 = this._binding;
            if (fragmentDc07proBinding4 != null && (textView = fragmentDc07proBinding4.pcmVolumeTrimKeySwitchTv) != null) {
                textView.setText(R.string.disable);
            }
        }
        FragmentDc07proBinding fragmentDc07proBinding5 = this._binding;
        if (fragmentDc07proBinding5 == null || (r2 = fragmentDc07proBinding5.pcmVolumeTrimKeySwitch) == null) {
            return;
        }
        r2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z2) {
                Dc07ProFragment.updateKeyControllerBar$lambda$11(Dc07ProFragment.this, compoundButton, z2);
            }
        });
    }

    public static void updateKeyControllerBar$default(Dc07ProFragment dc07ProFragment, int i, boolean z, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z = true;
        }
        dc07ProFragment.updateKeyControllerBar(i, z);
    }

    public static final void updateKeyControllerBar$lambda$11(Dc07ProFragment this$0, CompoundButton buttonView, boolean z) {
        TextView textView;
        TextView textView2;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(buttonView, "buttonView");
        if (z) {
            UacManager.getUacManager().setKeyControlStatus(0);
            FragmentDc07proBinding fragmentDc07proBinding = this$0._binding;
            if (fragmentDc07proBinding == null || (textView2 = fragmentDc07proBinding.pcmVolumeTrimKeySwitchTv) == null) {
                return;
            }
            textView2.setText(R.string.enable);
            return;
        }
        UacManager.getUacManager().setKeyControlStatus(1);
        FragmentDc07proBinding fragmentDc07proBinding2 = this$0._binding;
        if (fragmentDc07proBinding2 == null || (textView = fragmentDc07proBinding2.pcmVolumeTrimKeySwitchTv) == null) {
            return;
        }
        textView.setText(R.string.disable);
    }

    private final void updateScreenRation(int i, boolean z) {
        TextView textView;
        Switch r3;
        TextView textView2;
        FragmentDc07proBinding fragmentDc07proBinding;
        Switch r4;
        if (!z && (fragmentDc07proBinding = this._binding) != null && (r4 = fragmentDc07proBinding.screenRotateSwitch) != null) {
            r4.setOnCheckedChangeListener(null);
        }
        FragmentDc07proBinding fragmentDc07proBinding2 = this._binding;
        Switch r0 = fragmentDc07proBinding2 != null ? fragmentDc07proBinding2.screenRotateSwitch : null;
        if (r0 != null) {
            r0.setChecked(i == 1);
        }
        if (i == 1) {
            FragmentDc07proBinding fragmentDc07proBinding3 = this._binding;
            if (fragmentDc07proBinding3 != null && (textView2 = fragmentDc07proBinding3.screenRotateSwitchTv) != null) {
                textView2.setText(R.string.on);
            }
        } else {
            FragmentDc07proBinding fragmentDc07proBinding4 = this._binding;
            if (fragmentDc07proBinding4 != null && (textView = fragmentDc07proBinding4.screenRotateSwitchTv) != null) {
                textView.setText(R.string.off);
            }
        }
        FragmentDc07proBinding fragmentDc07proBinding5 = this._binding;
        if (fragmentDc07proBinding5 == null || (r3 = fragmentDc07proBinding5.screenRotateSwitch) == null) {
            return;
        }
        r3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z2) {
                Dc07ProFragment.updateScreenRation$lambda$5(Dc07ProFragment.this, compoundButton, z2);
            }
        });
    }

    public static void updateScreenRation$default(Dc07ProFragment dc07ProFragment, int i, boolean z, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z = true;
        }
        dc07ProFragment.updateScreenRation(i, z);
    }

    public static final void updateScreenRation$lambda$5(Dc07ProFragment this$0, CompoundButton buttonView, boolean z) {
        TextView textView;
        TextView textView2;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(buttonView, "buttonView");
        if (z) {
            UacManager.getUacManager().setScreenRotation(1);
            FragmentDc07proBinding fragmentDc07proBinding = this$0._binding;
            if (fragmentDc07proBinding == null || (textView2 = fragmentDc07proBinding.screenRotateSwitchTv) == null) {
                return;
            }
            textView2.setText(R.string.on);
            return;
        }
        UacManager.getUacManager().setScreenRotation(0);
        FragmentDc07proBinding fragmentDc07proBinding2 = this$0._binding;
        if (fragmentDc07proBinding2 == null || (textView = fragmentDc07proBinding2.screenRotateSwitchTv) == null) {
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
        FragmentDc07proBinding fragmentDc07proBinding;
        RadioGroup radioGroup6;
        if (!z && (fragmentDc07proBinding = this._binding) != null && (radioGroup6 = fragmentDc07proBinding.screenTimeRg) != null) {
            radioGroup6.setOnCheckedChangeListener(null);
        }
        if (i == 1) {
            FragmentDc07proBinding fragmentDc07proBinding2 = this._binding;
            if (fragmentDc07proBinding2 != null && (radioGroup = fragmentDc07proBinding2.screenTimeRg) != null) {
                radioGroup.check(R.id.screenTime1Rg);
            }
        } else if (i == 2) {
            FragmentDc07proBinding fragmentDc07proBinding3 = this._binding;
            if (fragmentDc07proBinding3 != null && (radioGroup3 = fragmentDc07proBinding3.screenTimeRg) != null) {
                radioGroup3.check(R.id.screenTime2Rg);
            }
        } else if (i != 3) {
            FragmentDc07proBinding fragmentDc07proBinding4 = this._binding;
            if (fragmentDc07proBinding4 != null && (radioGroup5 = fragmentDc07proBinding4.screenTimeRg) != null) {
                radioGroup5.check(R.id.screenTime0Rg);
            }
        } else {
            FragmentDc07proBinding fragmentDc07proBinding5 = this._binding;
            if (fragmentDc07proBinding5 != null && (radioGroup4 = fragmentDc07proBinding5.screenTimeRg) != null) {
                radioGroup4.check(R.id.screenTime3Rg);
            }
        }
        FragmentDc07proBinding fragmentDc07proBinding6 = this._binding;
        if (fragmentDc07proBinding6 == null || (radioGroup2 = fragmentDc07proBinding6.screenTimeRg) == null) {
            return;
        }
        radioGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public final void onCheckedChanged(RadioGroup radioGroup7, int i2) {
                Dc07ProFragment.updateScreenTime$lambda$12(radioGroup7, i2);
            }
        });
    }

    public static void updateScreenTime$default(Dc07ProFragment dc07ProFragment, int i, boolean z, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z = false;
        }
        dc07ProFragment.updateScreenTime(i, z);
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
        FragmentDc07proBinding fragmentDc07proBinding;
        Switch r4;
        if (!z && (fragmentDc07proBinding = this._binding) != null && (r4 = fragmentDc07proBinding.codecExternalSwitch) != null) {
            r4.setOnCheckedChangeListener(null);
        }
        FragmentDc07proBinding fragmentDc07proBinding2 = this._binding;
        Switch r0 = fragmentDc07proBinding2 != null ? fragmentDc07proBinding2.codecExternalSwitch : null;
        if (r0 != null) {
            r0.setChecked(i == 1);
        }
        if (i == 1) {
            FragmentDc07proBinding fragmentDc07proBinding3 = this._binding;
            if (fragmentDc07proBinding3 != null && (textView2 = fragmentDc07proBinding3.codecExternalSwitchTv) != null) {
                textView2.setText(R.string.on);
            }
        } else {
            FragmentDc07proBinding fragmentDc07proBinding4 = this._binding;
            if (fragmentDc07proBinding4 != null && (textView = fragmentDc07proBinding4.codecExternalSwitchTv) != null) {
                textView.setText(R.string.off);
            }
        }
        FragmentDc07proBinding fragmentDc07proBinding5 = this._binding;
        if (fragmentDc07proBinding5 == null || (r3 = fragmentDc07proBinding5.codecExternalSwitch) == null) {
            return;
        }
        r3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z2) {
                Dc07ProFragment.updateSpdif$lambda$7(Dc07ProFragment.this, compoundButton, z2);
            }
        });
    }

    public static void updateSpdif$default(Dc07ProFragment dc07ProFragment, int i, boolean z, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z = true;
        }
        dc07ProFragment.updateSpdif(i, z);
    }

    public static final void updateSpdif$lambda$7(Dc07ProFragment this$0, CompoundButton buttonView, boolean z) {
        TextView textView;
        TextView textView2;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(buttonView, "buttonView");
        if (z) {
            UacManager.getUacManager().setCodecExternal(1);
            FragmentDc07proBinding fragmentDc07proBinding = this$0._binding;
            if (fragmentDc07proBinding == null || (textView2 = fragmentDc07proBinding.codecExternalSwitchTv) == null) {
                return;
            }
            textView2.setText(R.string.on);
            return;
        }
        UacManager.getUacManager().setCodecExternal(0);
        FragmentDc07proBinding fragmentDc07proBinding2 = this$0._binding;
        if (fragmentDc07proBinding2 == null || (textView = fragmentDc07proBinding2.codecExternalSwitchTv) == null) {
            return;
        }
        textView.setText(R.string.off);
    }

    public final void updateThreeGainTipsViewStatus(int i, boolean z) {
        TextView textView;
        TextView textView2;
        TextView textView3;
        if (i == 0) {
            FragmentDc07proBinding fragmentDc07proBinding = this._binding;
            if (fragmentDc07proBinding != null && (textView = fragmentDc07proBinding.gainTipsTv) != null) {
                textView.setText(R.string.settings_gain_low);
            }
        } else if (i != 1) {
            FragmentDc07proBinding fragmentDc07proBinding2 = this._binding;
            if (fragmentDc07proBinding2 != null && (textView3 = fragmentDc07proBinding2.gainTipsTv) != null) {
                textView3.setText(R.string.settings_gain_high);
            }
        } else {
            FragmentDc07proBinding fragmentDc07proBinding3 = this._binding;
            if (fragmentDc07proBinding3 != null && (textView2 = fragmentDc07proBinding3.gainTipsTv) != null) {
                textView2.setText(R.string.settings_gain_middle);
            }
        }
        if (z) {
            UacManager.getUacManager().setGain(i);
        }
    }

    public static void updateThreeGainTipsViewStatus$default(Dc07ProFragment dc07ProFragment, int i, boolean z, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z = false;
        }
        dc07ProFragment.updateThreeGainTipsViewStatus(i, z);
    }

    @Override
    @Nullable
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        FragmentDc07proBinding inflate = FragmentDc07proBinding.inflate(inflater, viewGroup, false);
        this._binding = inflate;
        if (inflate != null) {
            return inflate.getRoot();
        }
        return null;
    }

    @Subscribe(sticky = EmbeddingCompat.DEBUG, threadMode = ThreadMode.MAIN)
    public final void onDc06VolumeEvent(@Nullable Dc06VolumeEvent dc06VolumeEvent) {
        FragmentDc07proBinding fragmentDc07proBinding = this._binding;
        SeekBar seekBar = fragmentDc07proBinding != null ? fragmentDc07proBinding.volumeSeekBar : null;
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
            FragmentDc07proBinding fragmentDc07proBinding = this._binding;
            TextView textView = fragmentDc07proBinding != null ? fragmentDc07proBinding.fpgaVersionTv : null;
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
            }
        }
    }

    @Subscribe(sticky = EmbeddingCompat.DEBUG, threadMode = ThreadMode.MAIN)
    public final void onNormalVolumeEvent(@NotNull NormalVolumeEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
    }

    @Override
    public void onProgressChanged(@Nullable SeekBar seekBar, int i, boolean z) {
        FragmentDc07proBinding fragmentDc07proBinding = this._binding;
        TextView textView = fragmentDc07proBinding != null ? fragmentDc07proBinding.volumeValueTv : null;
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
        FragmentDc07proBinding fragmentDc07proBinding = this._binding;
        if (fragmentDc07proBinding != null && (seekBar = fragmentDc07proBinding.volumeSeekBar) != null) {
            seekBar.setOnSeekBarChangeListener(this);
        }
        if (SpStorage.getInstance().getIsVolumeBtReverse()) {
            FragmentDc07proBinding fragmentDc07proBinding2 = this._binding;
            if (fragmentDc07proBinding2 != null && (imageView6 = fragmentDc07proBinding2.volumeDownBt) != null) {
                imageView6.setImageResource(R.drawable.ic_volume_up);
            }
            FragmentDc07proBinding fragmentDc07proBinding3 = this._binding;
            if (fragmentDc07proBinding3 != null && (imageView5 = fragmentDc07proBinding3.volumeUpBt) != null) {
                imageView5.setImageResource(R.drawable.ic_volume_down);
            }
        } else {
            FragmentDc07proBinding fragmentDc07proBinding4 = this._binding;
            if (fragmentDc07proBinding4 != null && (imageView2 = fragmentDc07proBinding4.volumeDownBt) != null) {
                imageView2.setImageResource(R.drawable.ic_volume_down);
            }
            FragmentDc07proBinding fragmentDc07proBinding5 = this._binding;
            if (fragmentDc07proBinding5 != null && (imageView = fragmentDc07proBinding5.volumeUpBt) != null) {
                imageView.setImageResource(R.drawable.ic_volume_up);
            }
        }
        FragmentDc07proBinding fragmentDc07proBinding6 = this._binding;
        if (fragmentDc07proBinding6 != null && (imageView4 = fragmentDc07proBinding6.volumeDownBt) != null) {
            imageView4.setOnClickListener(new View.OnClickListener() {
                @Override
                public final void onClick(View view2) {
                    Dc07ProFragment.onViewCreated$lambda$0(view2);
                }
            });
        }
        FragmentDc07proBinding fragmentDc07proBinding7 = this._binding;
        if (fragmentDc07proBinding7 != null && (imageView3 = fragmentDc07proBinding7.volumeUpBt) != null) {
            imageView3.setOnClickListener(new View.OnClickListener() {
                @Override
                public final void onClick(View view2) {
                    Dc07ProFragment.onViewCreated$lambda$1(view2);
                }
            });
        }
        updateDigitalFilter(UacManager.getUacManager().getDigitalFilter() & 255, false);
        updateHpFilter(UacManager.getUacManager().getHpFilter() & 255, false);
        updateGain(SpStorage.getInstance().getDc07ProGain(), false);
        updateScreenRation(SpStorage.getInstance().getDc07ProScreenRotation(), false);
        updateSpdif(UacManager.getUacManager().getCodecExternal(), false);
        updateKeyControllerBar(UacManager.getUacManager().getKeyControlStatus(), false);
        EventBus.getDefault().register(this);
    }
}
