package com.ibasso.volume;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.window.embedding.EmbeddingCompat;
import com.ibasso.music.widgets.BalanceView;
import com.ibasso.music.widgets.ThirdSwitchBar;
import com.ibasso.volume.bean.FpgaVersionEvent;
import com.ibasso.volume.bean.NativeReportEvent;
import com.ibasso.volume.databinding.FragmentD18Binding;
import com.ibasso.volume.eq.EqualizerManager;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import okhttp3.HttpUrl;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
@SourceDebugExtension({"SMAP\nD18Fragment.kt\nKotlin\n*S Kotlin\n*F\n+ 1 D18Fragment.kt\ncom/ibasso/volume/D18Fragment\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 Color.kt\nandroidx/core/graphics/ColorKt\n*L\n1#1,449:1\n1#2:450\n470#3:451\n470#3:452\n470#3:453\n470#3:454\n*S KotlinDebug\n*F\n+ 1 D18Fragment.kt\ncom/ibasso/volume/D18Fragment\n*L\n127#1:451\n128#1:452\n130#1:453\n131#1:454\n*E\n"})
public final class D18Fragment extends Fragment implements SeekBar.OnSeekBarChangeListener {
    @Nullable
    private FragmentD18Binding _binding;
    private boolean isTouched;

    private final void updateBalance(int i, boolean z) {
        TextView textView;
        BalanceView balanceView;
        BalanceView balanceView2;
        FragmentD18Binding fragmentD18Binding;
        BalanceView balanceView3;
        if (!z && (fragmentD18Binding = this._binding) != null && (balanceView3 = fragmentD18Binding.balanceSeekBar) != null) {
            balanceView3.setSeekBarProgressClickListener(null);
        }
        int i2 = i - 10;
        if (i2 > 0) {
            FragmentD18Binding fragmentD18Binding2 = this._binding;
            textView = fragmentD18Binding2 != null ? fragmentD18Binding2.balanceValueTv : null;
            if (textView != null) {
                StringBuilder sb = new StringBuilder();
                sb.append('R');
                sb.append(i2);
                textView.setText(sb.toString());
            }
        } else if (i2 < 0) {
            FragmentD18Binding fragmentD18Binding3 = this._binding;
            textView = fragmentD18Binding3 != null ? fragmentD18Binding3.balanceValueTv : null;
            if (textView != null) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append('L');
                sb2.append(-i2);
                textView.setText(sb2.toString());
            }
        } else {
            FragmentD18Binding fragmentD18Binding4 = this._binding;
            textView = fragmentD18Binding4 != null ? fragmentD18Binding4.balanceValueTv : null;
            if (textView != null) {
                textView.setText("L=R");
            }
        }
        FragmentD18Binding fragmentD18Binding5 = this._binding;
        if (fragmentD18Binding5 != null && (balanceView2 = fragmentD18Binding5.balanceSeekBar) != null) {
            balanceView2.setProgress(i2);
        }
        FragmentD18Binding fragmentD18Binding6 = this._binding;
        if (fragmentD18Binding6 == null || (balanceView = fragmentD18Binding6.balanceSeekBar) == null) {
            return;
        }
        balanceView.setSeekBarProgressClickListener(new BalanceView.ISeekBarProgressClickListener() {
            @Override
            public void onSeekBarChanged(@Nullable BalanceView balanceView4, int i3) {
                FragmentD18Binding fragmentD18Binding7;
                TextView textView2;
                FragmentD18Binding fragmentD18Binding8;
                FragmentD18Binding fragmentD18Binding9;
                UacManager.getUacManager().setBalanceVolume(i3 + 10);
                if (i3 > 0) {
                    fragmentD18Binding9 = D18Fragment.this._binding;
                    textView2 = fragmentD18Binding9 != null ? fragmentD18Binding9.balanceValueTv : null;
                    if (textView2 == null) {
                        return;
                    }
                    textView2.setText("R-" + i3);
                } else if (i3 >= 0) {
                    fragmentD18Binding7 = D18Fragment.this._binding;
                    textView2 = fragmentD18Binding7 != null ? fragmentD18Binding7.balanceValueTv : null;
                    if (textView2 == null) {
                        return;
                    }
                    textView2.setText("L=R");
                } else {
                    fragmentD18Binding8 = D18Fragment.this._binding;
                    textView2 = fragmentD18Binding8 != null ? fragmentD18Binding8.balanceValueTv : null;
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

    public static void updateBalance$default(D18Fragment d18Fragment, int i, boolean z, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z = false;
        }
        d18Fragment.updateBalance(i, z);
    }

    private final void updateDacGain(int i, boolean z) {
        TextView textView;
        Switch r3;
        TextView textView2;
        FragmentD18Binding fragmentD18Binding;
        Switch r4;
        if (!z && (fragmentD18Binding = this._binding) != null && (r4 = fragmentD18Binding.dacGainSwitch) != null) {
            r4.setOnCheckedChangeListener(null);
        }
        FragmentD18Binding fragmentD18Binding2 = this._binding;
        Switch r0 = fragmentD18Binding2 != null ? fragmentD18Binding2.dacGainSwitch : null;
        if (r0 != null) {
            r0.setChecked(i == 1);
        }
        if (i == 1) {
            FragmentD18Binding fragmentD18Binding3 = this._binding;
            if (fragmentD18Binding3 != null && (textView2 = fragmentD18Binding3.dacGainTipsTv) != null) {
                textView2.setText(R.string.settings_gain_high);
            }
        } else {
            FragmentD18Binding fragmentD18Binding4 = this._binding;
            if (fragmentD18Binding4 != null && (textView = fragmentD18Binding4.dacGainTipsTv) != null) {
                textView.setText(R.string.settings_gain_low);
            }
        }
        FragmentD18Binding fragmentD18Binding5 = this._binding;
        if (fragmentD18Binding5 == null || (r3 = fragmentD18Binding5.dacGainSwitch) == null) {
            return;
        }
        r3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z2) {
                D18Fragment.updateDacGain$lambda$6(D18Fragment.this, compoundButton, z2);
            }
        });
    }

    public static void updateDacGain$default(D18Fragment d18Fragment, int i, boolean z, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z = true;
        }
        d18Fragment.updateDacGain(i, z);
    }

    public static final void updateDacGain$lambda$6(D18Fragment this$0, CompoundButton buttonView, boolean z) {
        TextView textView;
        TextView textView2;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(buttonView, "buttonView");
        if (z) {
            UacManager.getUacManager().setDacGain(1);
            FragmentD18Binding fragmentD18Binding = this$0._binding;
            if (fragmentD18Binding == null || (textView2 = fragmentD18Binding.dacGainTipsTv) == null) {
                return;
            }
            textView2.setText(R.string.settings_gain_high);
            return;
        }
        UacManager.getUacManager().setDacGain(0);
        FragmentD18Binding fragmentD18Binding2 = this$0._binding;
        if (fragmentD18Binding2 == null || (textView = fragmentD18Binding2.dacGainTipsTv) == null) {
            return;
        }
        textView.setText(R.string.settings_gain_low);
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
        FragmentD18Binding fragmentD18Binding;
        RadioGroup radioGroup7;
        if (!z && (fragmentD18Binding = this._binding) != null && (radioGroup7 = fragmentD18Binding.digitalFilterRg) != null) {
            radioGroup7.setOnCheckedChangeListener(null);
        }
        if (i == 1) {
            FragmentD18Binding fragmentD18Binding2 = this._binding;
            if (fragmentD18Binding2 != null && (radioGroup = fragmentD18Binding2.digitalFilterRg) != null) {
                radioGroup.check(R.id.digitalFilter2Rg);
            }
            FragmentD18Binding fragmentD18Binding3 = this._binding;
            if (fragmentD18Binding3 != null && (textView = fragmentD18Binding3.digitalFilterValueTv) != null) {
                textView.setText(R.string.digital_filter_dx160_four);
            }
        } else if (i == 2) {
            FragmentD18Binding fragmentD18Binding4 = this._binding;
            if (fragmentD18Binding4 != null && (radioGroup3 = fragmentD18Binding4.digitalFilterRg) != null) {
                radioGroup3.check(R.id.digitalFilter3Rg);
            }
            FragmentD18Binding fragmentD18Binding5 = this._binding;
            if (fragmentD18Binding5 != null && (textView2 = fragmentD18Binding5.digitalFilterValueTv) != null) {
                textView2.setText(R.string.digital_filter_dx160_three);
            }
        } else if (i == 3) {
            FragmentD18Binding fragmentD18Binding6 = this._binding;
            if (fragmentD18Binding6 != null && (radioGroup4 = fragmentD18Binding6.digitalFilterRg) != null) {
                radioGroup4.check(R.id.digitalFilter4Rg);
            }
            FragmentD18Binding fragmentD18Binding7 = this._binding;
            if (fragmentD18Binding7 != null && (textView3 = fragmentD18Binding7.digitalFilterValueTv) != null) {
                textView3.setText(R.string.digital_filter_dx160_two);
            }
        } else if (i != 4) {
            FragmentD18Binding fragmentD18Binding8 = this._binding;
            if (fragmentD18Binding8 != null && (radioGroup6 = fragmentD18Binding8.digitalFilterRg) != null) {
                radioGroup6.check(R.id.digitalFilter1Rg);
            }
            FragmentD18Binding fragmentD18Binding9 = this._binding;
            if (fragmentD18Binding9 != null && (textView5 = fragmentD18Binding9.digitalFilterValueTv) != null) {
                textView5.setText(R.string.digital_filter_dx160_one);
            }
        } else {
            FragmentD18Binding fragmentD18Binding10 = this._binding;
            if (fragmentD18Binding10 != null && (radioGroup5 = fragmentD18Binding10.digitalFilterRg) != null) {
                radioGroup5.check(R.id.digitalFilter5Rg);
            }
            FragmentD18Binding fragmentD18Binding11 = this._binding;
            if (fragmentD18Binding11 != null && (textView4 = fragmentD18Binding11.digitalFilterValueTv) != null) {
                textView4.setText(R.string.digital_filter_dx160_five);
            }
        }
        FragmentD18Binding fragmentD18Binding12 = this._binding;
        if (fragmentD18Binding12 == null || (radioGroup2 = fragmentD18Binding12.digitalFilterRg) == null) {
            return;
        }
        radioGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public final void onCheckedChanged(RadioGroup radioGroup8, int i2) {
                D18Fragment.updateDigitalFilter$lambda$4(D18Fragment.this, radioGroup8, i2);
            }
        });
    }

    public static void updateDigitalFilter$default(D18Fragment d18Fragment, int i, boolean z, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z = true;
        }
        d18Fragment.updateDigitalFilter(i, z);
    }

    public static final void updateDigitalFilter$lambda$4(D18Fragment this$0, RadioGroup radioGroup, int i) {
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
                FragmentD18Binding fragmentD18Binding = this$0._binding;
                if (fragmentD18Binding == null || (textView = fragmentD18Binding.digitalFilterValueTv) == null) {
                    return;
                }
                textView.setText(R.string.digital_filter_dx160_four);
                return;
            case R.id.digitalFilter3Rg:
                UacManager.getUacManager().setDigitalFilter(2);
                FragmentD18Binding fragmentD18Binding2 = this$0._binding;
                if (fragmentD18Binding2 == null || (textView2 = fragmentD18Binding2.digitalFilterValueTv) == null) {
                    return;
                }
                textView2.setText(R.string.digital_filter_dx160_three);
                return;
            case R.id.digitalFilter4Rg:
                UacManager.getUacManager().setDigitalFilter(3);
                FragmentD18Binding fragmentD18Binding3 = this$0._binding;
                if (fragmentD18Binding3 == null || (textView3 = fragmentD18Binding3.digitalFilterValueTv) == null) {
                    return;
                }
                textView3.setText(R.string.digital_filter_dx160_two);
                return;
            case R.id.digitalFilter5Rg:
                UacManager.getUacManager().setDigitalFilter(4);
                FragmentD18Binding fragmentD18Binding4 = this$0._binding;
                if (fragmentD18Binding4 == null || (textView4 = fragmentD18Binding4.digitalFilterValueTv) == null) {
                    return;
                }
                textView4.setText(R.string.digital_filter_dx160_five);
                return;
            default:
                UacManager.getUacManager().setDigitalFilter(0);
                FragmentD18Binding fragmentD18Binding5 = this$0._binding;
                if (fragmentD18Binding5 == null || (textView5 = fragmentD18Binding5.digitalFilterValueTv) == null) {
                    return;
                }
                textView5.setText(R.string.digital_filter_dx160_one);
                return;
        }
    }

    private final void updateGain(int i, boolean z) {
        ThirdSwitchBar thirdSwitchBar;
        ThirdSwitchBar thirdSwitchBar2;
        FragmentD18Binding fragmentD18Binding;
        ThirdSwitchBar thirdSwitchBar3;
        if (!z && (fragmentD18Binding = this._binding) != null && (thirdSwitchBar3 = fragmentD18Binding.gainThirdSwitchBar) != null) {
            thirdSwitchBar3.setSeekTouchListener(null);
        }
        FragmentD18Binding fragmentD18Binding2 = this._binding;
        if (fragmentD18Binding2 != null && (thirdSwitchBar2 = fragmentD18Binding2.gainThirdSwitchBar) != null) {
            thirdSwitchBar2.setStatus(SpStorage.getInstance().getD18Gain());
        }
        updateThreeGainTipsViewStatus$default(this, SpStorage.getInstance().getD18Gain(), false, 2, null);
        FragmentD18Binding fragmentD18Binding3 = this._binding;
        if (fragmentD18Binding3 == null || (thirdSwitchBar = fragmentD18Binding3.gainThirdSwitchBar) == null) {
            return;
        }
        thirdSwitchBar.setSeekTouchListener(new ThirdSwitchBar.SeekTouchListener() {
            @Override
            public void touchEnd(@Nullable SeekBar seekBar) {
                D18Fragment.this.updateThreeGainTipsViewStatus(2, true);
            }

            @Override
            public void touchMiddle(@Nullable SeekBar seekBar) {
                D18Fragment.this.updateThreeGainTipsViewStatus(1, true);
            }

            @Override
            public void touchTop(@Nullable SeekBar seekBar) {
                D18Fragment.this.updateThreeGainTipsViewStatus(0, true);
            }
        });
    }

    public static void updateGain$default(D18Fragment d18Fragment, int i, boolean z, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z = true;
        }
        d18Fragment.updateGain(i, z);
    }

    private final void updateLanguage(int i, boolean z) {
        RadioGroup radioGroup;
        RadioGroup radioGroup2;
        RadioGroup radioGroup3;
        RadioGroup radioGroup4;
        FragmentD18Binding fragmentD18Binding;
        RadioGroup radioGroup5;
        if (!z && (fragmentD18Binding = this._binding) != null && (radioGroup5 = fragmentD18Binding.languageRg) != null) {
            radioGroup5.setOnCheckedChangeListener(null);
        }
        if (i == 1) {
            FragmentD18Binding fragmentD18Binding2 = this._binding;
            if (fragmentD18Binding2 != null && (radioGroup = fragmentD18Binding2.languageRg) != null) {
                radioGroup.check(R.id.language1Rg);
            }
        } else if (i != 2) {
            FragmentD18Binding fragmentD18Binding3 = this._binding;
            if (fragmentD18Binding3 != null && (radioGroup4 = fragmentD18Binding3.languageRg) != null) {
                radioGroup4.check(R.id.language0Rg);
            }
        } else {
            FragmentD18Binding fragmentD18Binding4 = this._binding;
            if (fragmentD18Binding4 != null && (radioGroup3 = fragmentD18Binding4.languageRg) != null) {
                radioGroup3.check(R.id.language2Rg);
            }
        }
        FragmentD18Binding fragmentD18Binding5 = this._binding;
        if (fragmentD18Binding5 == null || (radioGroup2 = fragmentD18Binding5.languageRg) == null) {
            return;
        }
        radioGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public final void onCheckedChanged(RadioGroup radioGroup6, int i2) {
                D18Fragment.updateLanguage$lambda$3(radioGroup6, i2);
            }
        });
    }

    public static void updateLanguage$default(D18Fragment d18Fragment, int i, boolean z, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z = false;
        }
        d18Fragment.updateLanguage(i, z);
    }

    public static final void updateLanguage$lambda$3(RadioGroup radioGroup, int i) {
        Intrinsics.checkNotNullParameter(radioGroup, "<anonymous parameter 0>");
        switch (i) {
            case R.id.language1Rg:
                UacManager.getUacManager().setLanguage(1);
                return;
            case R.id.language2Rg:
                UacManager.getUacManager().setLanguage(2);
                return;
            default:
                UacManager.getUacManager().setLanguage(0);
                return;
        }
    }

    private final void updateMicMode(int i, boolean z) {
        TextView textView;
        Switch r3;
        TextView textView2;
        FragmentD18Binding fragmentD18Binding;
        Switch r4;
        if (!z && (fragmentD18Binding = this._binding) != null && (r4 = fragmentD18Binding.micModeSwitch) != null) {
            r4.setOnCheckedChangeListener(null);
        }
        FragmentD18Binding fragmentD18Binding2 = this._binding;
        Switch r0 = fragmentD18Binding2 != null ? fragmentD18Binding2.micModeSwitch : null;
        if (r0 != null) {
            r0.setChecked(i == 1);
        }
        if (i == 1) {
            FragmentD18Binding fragmentD18Binding3 = this._binding;
            if (fragmentD18Binding3 != null && (textView2 = fragmentD18Binding3.micModeSwitchTv) != null) {
                textView2.setText(R.string.enable);
            }
        } else {
            FragmentD18Binding fragmentD18Binding4 = this._binding;
            if (fragmentD18Binding4 != null && (textView = fragmentD18Binding4.micModeSwitchTv) != null) {
                textView.setText(R.string.disable);
            }
        }
        FragmentD18Binding fragmentD18Binding5 = this._binding;
        if (fragmentD18Binding5 == null || (r3 = fragmentD18Binding5.micModeSwitch) == null) {
            return;
        }
        r3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z2) {
                D18Fragment.updateMicMode$lambda$2(D18Fragment.this, compoundButton, z2);
            }
        });
    }

    public static void updateMicMode$default(D18Fragment d18Fragment, int i, boolean z, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z = false;
        }
        d18Fragment.updateMicMode(i, z);
    }

    public static final void updateMicMode$lambda$2(D18Fragment this$0, CompoundButton buttonView, boolean z) {
        TextView textView;
        TextView textView2;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(buttonView, "buttonView");
        if (!z) {
            UacManager.getUacManager().setMicMode(0);
            FragmentD18Binding fragmentD18Binding = this$0._binding;
            if (fragmentD18Binding == null || (textView = fragmentD18Binding.micModeSwitchTv) == null) {
                return;
            }
            textView.setText(R.string.disable);
            return;
        }
        UacManager.getUacManager().setMicMode(1);
        FragmentD18Binding fragmentD18Binding2 = this$0._binding;
        if (fragmentD18Binding2 != null && (textView2 = fragmentD18Binding2.micModeSwitchTv) != null) {
            textView2.setText(R.string.enable);
        }
        if (UacManager.getUacManager().getCodecExternal() == 1) {
            this$0.updateSpdif(0, true);
        }
    }

    private final void updateScreenRation(int i, boolean z) {
        TextView textView;
        Switch r3;
        TextView textView2;
        FragmentD18Binding fragmentD18Binding;
        Switch r4;
        if (!z && (fragmentD18Binding = this._binding) != null && (r4 = fragmentD18Binding.screenRotateSwitch) != null) {
            r4.setOnCheckedChangeListener(null);
        }
        FragmentD18Binding fragmentD18Binding2 = this._binding;
        Switch r0 = fragmentD18Binding2 != null ? fragmentD18Binding2.screenRotateSwitch : null;
        if (r0 != null) {
            r0.setChecked(i == 1);
        }
        if (i == 1) {
            FragmentD18Binding fragmentD18Binding3 = this._binding;
            if (fragmentD18Binding3 != null && (textView2 = fragmentD18Binding3.screenRotateSwitchTv) != null) {
                textView2.setText(R.string.on);
            }
        } else {
            FragmentD18Binding fragmentD18Binding4 = this._binding;
            if (fragmentD18Binding4 != null && (textView = fragmentD18Binding4.screenRotateSwitchTv) != null) {
                textView.setText(R.string.off);
            }
        }
        FragmentD18Binding fragmentD18Binding5 = this._binding;
        if (fragmentD18Binding5 == null || (r3 = fragmentD18Binding5.screenRotateSwitch) == null) {
            return;
        }
        r3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z2) {
                D18Fragment.updateScreenRation$lambda$8(D18Fragment.this, compoundButton, z2);
            }
        });
    }

    public static void updateScreenRation$default(D18Fragment d18Fragment, int i, boolean z, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z = true;
        }
        d18Fragment.updateScreenRation(i, z);
    }

    public static final void updateScreenRation$lambda$8(D18Fragment this$0, CompoundButton buttonView, boolean z) {
        TextView textView;
        TextView textView2;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(buttonView, "buttonView");
        if (z) {
            UacManager.getUacManager().setScreenRotation(1);
            FragmentD18Binding fragmentD18Binding = this$0._binding;
            if (fragmentD18Binding == null || (textView2 = fragmentD18Binding.screenRotateSwitchTv) == null) {
                return;
            }
            textView2.setText(R.string.on);
            return;
        }
        UacManager.getUacManager().setScreenRotation(0);
        FragmentD18Binding fragmentD18Binding2 = this$0._binding;
        if (fragmentD18Binding2 == null || (textView = fragmentD18Binding2.screenRotateSwitchTv) == null) {
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
        FragmentD18Binding fragmentD18Binding;
        RadioGroup radioGroup6;
        if (!z && (fragmentD18Binding = this._binding) != null && (radioGroup6 = fragmentD18Binding.screenTimeRg) != null) {
            radioGroup6.setOnCheckedChangeListener(null);
        }
        if (i == 1) {
            FragmentD18Binding fragmentD18Binding2 = this._binding;
            if (fragmentD18Binding2 != null && (radioGroup = fragmentD18Binding2.screenTimeRg) != null) {
                radioGroup.check(R.id.screenTime1Rg);
            }
        } else if (i == 2) {
            FragmentD18Binding fragmentD18Binding3 = this._binding;
            if (fragmentD18Binding3 != null && (radioGroup3 = fragmentD18Binding3.screenTimeRg) != null) {
                radioGroup3.check(R.id.screenTime2Rg);
            }
        } else if (i != 3) {
            FragmentD18Binding fragmentD18Binding4 = this._binding;
            if (fragmentD18Binding4 != null && (radioGroup5 = fragmentD18Binding4.screenTimeRg) != null) {
                radioGroup5.check(R.id.screenTime0Rg);
            }
        } else {
            FragmentD18Binding fragmentD18Binding5 = this._binding;
            if (fragmentD18Binding5 != null && (radioGroup4 = fragmentD18Binding5.screenTimeRg) != null) {
                radioGroup4.check(R.id.screenTime3Rg);
            }
        }
        FragmentD18Binding fragmentD18Binding6 = this._binding;
        if (fragmentD18Binding6 == null || (radioGroup2 = fragmentD18Binding6.screenTimeRg) == null) {
            return;
        }
        radioGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public final void onCheckedChanged(RadioGroup radioGroup7, int i2) {
                D18Fragment.updateScreenTime$lambda$11(radioGroup7, i2);
            }
        });
    }

    public static void updateScreenTime$default(D18Fragment d18Fragment, int i, boolean z, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z = false;
        }
        d18Fragment.updateScreenTime(i, z);
    }

    public static final void updateScreenTime$lambda$11(RadioGroup radioGroup, int i) {
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
        FragmentD18Binding fragmentD18Binding;
        Switch r4;
        if (!z && (fragmentD18Binding = this._binding) != null && (r4 = fragmentD18Binding.codecExternalSwitch) != null) {
            r4.setOnCheckedChangeListener(null);
        }
        FragmentD18Binding fragmentD18Binding2 = this._binding;
        Switch r0 = fragmentD18Binding2 != null ? fragmentD18Binding2.codecExternalSwitch : null;
        if (r0 != null) {
            r0.setChecked(i == 1);
        }
        if (i == 1) {
            FragmentD18Binding fragmentD18Binding3 = this._binding;
            if (fragmentD18Binding3 != null && (textView2 = fragmentD18Binding3.codecExternalSwitchTv) != null) {
                textView2.setText(R.string.on);
            }
        } else {
            FragmentD18Binding fragmentD18Binding4 = this._binding;
            if (fragmentD18Binding4 != null && (textView = fragmentD18Binding4.codecExternalSwitchTv) != null) {
                textView.setText(R.string.off);
            }
        }
        FragmentD18Binding fragmentD18Binding5 = this._binding;
        if (fragmentD18Binding5 == null || (r3 = fragmentD18Binding5.codecExternalSwitch) == null) {
            return;
        }
        r3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z2) {
                D18Fragment.updateSpdif$lambda$10(D18Fragment.this, compoundButton, z2);
            }
        });
    }

    public static void updateSpdif$default(D18Fragment d18Fragment, int i, boolean z, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z = true;
        }
        d18Fragment.updateSpdif(i, z);
    }

    public static final void updateSpdif$lambda$10(D18Fragment this$0, CompoundButton buttonView, boolean z) {
        TextView textView;
        TextView textView2;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(buttonView, "buttonView");
        if (!z) {
            UacManager.getUacManager().setCodecExternal(0);
            FragmentD18Binding fragmentD18Binding = this$0._binding;
            if (fragmentD18Binding == null || (textView = fragmentD18Binding.codecExternalSwitchTv) == null) {
                return;
            }
            textView.setText(R.string.off);
            return;
        }
        UacManager.getUacManager().setCodecExternal(1);
        FragmentD18Binding fragmentD18Binding2 = this$0._binding;
        if (fragmentD18Binding2 != null && (textView2 = fragmentD18Binding2.codecExternalSwitchTv) != null) {
            textView2.setText(R.string.on);
        }
        if (UacManager.getUacManager().getMicMode() == 1) {
            this$0.updateMicMode(0, true);
        }
    }

    public final void updateThreeGainTipsViewStatus(int i, boolean z) {
        TextView textView;
        TextView textView2;
        TextView textView3;
        if (i == 1) {
            FragmentD18Binding fragmentD18Binding = this._binding;
            if (fragmentD18Binding != null && (textView = fragmentD18Binding.gainTipsTv) != null) {
                textView.setText(R.string.settings_gain_high);
            }
        } else if (i != 2) {
            FragmentD18Binding fragmentD18Binding2 = this._binding;
            if (fragmentD18Binding2 != null && (textView3 = fragmentD18Binding2.gainTipsTv) != null) {
                textView3.setText(R.string.settings_gain_low);
            }
        } else {
            FragmentD18Binding fragmentD18Binding3 = this._binding;
            if (fragmentD18Binding3 != null && (textView2 = fragmentD18Binding3.gainTipsTv) != null) {
                textView2.setText(R.string.settings_gain_super);
            }
        }
        if (z) {
            UacManager.getUacManager().setGain(i);
        }
    }

    public static void updateThreeGainTipsViewStatus$default(D18Fragment d18Fragment, int i, boolean z, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z = false;
        }
        d18Fragment.updateThreeGainTipsViewStatus(i, z);
    }

    private final void updateUacVersion(int i, boolean z) {
    }

    public static void updateUacVersion$default(D18Fragment d18Fragment, int i, boolean z, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z = false;
        }
        d18Fragment.updateUacVersion(i, z);
    }

    @Override
    @Nullable
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        FragmentD18Binding inflate = FragmentD18Binding.inflate(inflater, viewGroup, false);
        this._binding = inflate;
        if (inflate != null) {
            return inflate.getRoot();
        }
        return null;
    }

    @Subscribe(sticky = EmbeddingCompat.DEBUG, threadMode = ThreadMode.MAIN)
    public final void onDc06VolumeEvent(@Nullable Dc06VolumeEvent dc06VolumeEvent) {
        FragmentD18Binding fragmentD18Binding = this._binding;
        SeekBar seekBar = fragmentD18Binding != null ? fragmentD18Binding.volumeSeekBar : null;
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
            FragmentD18Binding fragmentD18Binding = this._binding;
            TextView textView = fragmentD18Binding != null ? fragmentD18Binding.fpgaVersionTv : null;
            if (textView == null) {
                return;
            }
            textView.setText(getString(R.string.version) + fpgaVersionEvent.fpgaVersion);
        }
    }

    @Subscribe(sticky = EmbeddingCompat.DEBUG, threadMode = ThreadMode.MAIN)
    public final void onNativeReportEvent(@Nullable NativeReportEvent nativeReportEvent) {
        TextView textView;
        TextView textView2;
        TextView textView3;
        TextView textView4;
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
                updateDacGain(nativeReportEvent.value2, false);
                updateGain(nativeReportEvent.value3, false);
            } else if (i == 97) {
                updateScreenRation(nativeReportEvent.value, false);
                updateScreenTime(nativeReportEvent.value2, false);
                updateLanguage(nativeReportEvent.value3, false);
            } else if (i == 99) {
                updateUacVersion(nativeReportEvent.value, false);
                updateMicMode(nativeReportEvent.value2, false);
            } else if (i == -1) {
                int i2 = nativeReportEvent.value;
                if (i2 == -1 || i2 == 0) {
                    if (i2 == -1) {
                        FragmentD18Binding fragmentD18Binding = this._binding;
                        if (fragmentD18Binding != null && (textView4 = fragmentD18Binding.manufacturerTv) != null) {
                            textView4.setTextColor(Color.parseColor("#FF0000"));
                        }
                        FragmentD18Binding fragmentD18Binding2 = this._binding;
                        if (fragmentD18Binding2 == null || (textView3 = fragmentD18Binding2.productNameTv) == null) {
                            return;
                        }
                        textView3.setTextColor(Color.parseColor("#FF0000"));
                        return;
                    }
                    FragmentD18Binding fragmentD18Binding3 = this._binding;
                    if (fragmentD18Binding3 != null && (textView2 = fragmentD18Binding3.manufacturerTv) != null) {
                        textView2.setTextColor(Color.parseColor("#ebebeb"));
                    }
                    FragmentD18Binding fragmentD18Binding4 = this._binding;
                    if (fragmentD18Binding4 == null || (textView = fragmentD18Binding4.productNameTv) == null) {
                        return;
                    }
                    textView.setTextColor(Color.parseColor("#ebebeb"));
                }
            }
        }
    }

    @Subscribe(sticky = EmbeddingCompat.DEBUG, threadMode = ThreadMode.MAIN)
    public final void onNormalVolumeEvent(@NotNull NormalVolumeEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
    }

    @Override
    public void onProgressChanged(@Nullable SeekBar seekBar, int i, boolean z) {
        FragmentD18Binding fragmentD18Binding = this._binding;
        TextView textView = fragmentD18Binding != null ? fragmentD18Binding.volumeValueTv : null;
        if (textView != null) {
            textView.setText(HttpUrl.FRAGMENT_ENCODE_SET + i);
        }
        if (!this.isTouched || seekBar == null) {
            return;
        }
        Log.i("UacManager", "volume changed progress: " + seekBar.getProgress() + "  max " + seekBar.getMax());
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
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onStopTrackingTouch(@Nullable SeekBar seekBar) {
        this.isTouched = false;
    }

    @Override
    @SuppressLint({"SetTextI18n"})
    public void onViewCreated(@NotNull View view, @Nullable Bundle bundle) {
        TextView textView;
        SeekBar seekBar;
        Intrinsics.checkNotNullParameter(view, "view");
        super.onViewCreated(view, bundle);
        FragmentD18Binding fragmentD18Binding = this._binding;
        if (fragmentD18Binding != null && (seekBar = fragmentD18Binding.volumeSeekBar) != null) {
            seekBar.setOnSeekBarChangeListener(this);
        }
        FragmentD18Binding fragmentD18Binding2 = this._binding;
        if (fragmentD18Binding2 != null && (textView = fragmentD18Binding2.manufacturerTv) != null) {
            textView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(@Nullable View view2) {
                    EqualizerManager.getInstance().dumpFlash();
                    Toast.makeText(D18Fragment.this.requireContext(), "dumpFlash", 0).show();
                    return true;
                }
            });
        }
        updateDigitalFilter(UacManager.getUacManager().getDigitalFilter() & 255, false);
        updateGain(SpStorage.getInstance().getD18Gain(), false);
        updateDacGain(SpStorage.getInstance().getD18DacGain(), false);
        updateScreenRation(SpStorage.getInstance().getD18ScreenRotation(), false);
        updateSpdif(UacManager.getUacManager().getCodecExternal(), false);
        updateMicMode(UacManager.getUacManager().getMicMode(), false);
        EventBus.getDefault().register(this);
    }
}
