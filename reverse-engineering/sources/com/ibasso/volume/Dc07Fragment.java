package com.ibasso.volume;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.window.embedding.EmbeddingCompat;
import com.ibasso.music.widgets.ThirdSwitchBar;
import com.ibasso.volume.bean.FpgaVersionEvent;
import com.ibasso.volume.bean.NativeReportEvent;
import com.ibasso.volume.databinding.FragmentDc07Binding;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
public final class Dc07Fragment extends Fragment implements SeekBar.OnSeekBarChangeListener {
    @Nullable
    private FragmentDc07Binding _binding;
    private boolean isTouched;

    private final void updateCompensation(int i, boolean z) {
        TextView textView;
        Switch r3;
        TextView textView2;
        FragmentDc07Binding fragmentDc07Binding;
        Switch r4;
        if (!z && (fragmentDc07Binding = this._binding) != null && (r4 = fragmentDc07Binding.outputSwitch) != null) {
            r4.setOnCheckedChangeListener(null);
        }
        FragmentDc07Binding fragmentDc07Binding2 = this._binding;
        Switch r0 = fragmentDc07Binding2 != null ? fragmentDc07Binding2.outputSwitch : null;
        if (r0 != null) {
            r0.setChecked(i == 1);
        }
        if (i == 1) {
            FragmentDc07Binding fragmentDc07Binding3 = this._binding;
            if (fragmentDc07Binding3 != null && (textView2 = fragmentDc07Binding3.outputSwitchTv) != null) {
                textView2.setText(R.string.on);
            }
        } else {
            FragmentDc07Binding fragmentDc07Binding4 = this._binding;
            if (fragmentDc07Binding4 != null && (textView = fragmentDc07Binding4.outputSwitchTv) != null) {
                textView.setText(R.string.off);
            }
        }
        FragmentDc07Binding fragmentDc07Binding5 = this._binding;
        if (fragmentDc07Binding5 == null || (r3 = fragmentDc07Binding5.outputSwitch) == null) {
            return;
        }
        r3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z2) {
                Dc07Fragment.updateCompensation$lambda$2(Dc07Fragment.this, compoundButton, z2);
            }
        });
    }

    public static void updateCompensation$default(Dc07Fragment dc07Fragment, int i, boolean z, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z = true;
        }
        dc07Fragment.updateCompensation(i, z);
    }

    public static final void updateCompensation$lambda$2(Dc07Fragment this$0, CompoundButton buttonView, boolean z) {
        TextView textView;
        TextView textView2;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(buttonView, "buttonView");
        if (z) {
            UacManager.getUacManager().setOutput(1);
            FragmentDc07Binding fragmentDc07Binding = this$0._binding;
            if (fragmentDc07Binding == null || (textView2 = fragmentDc07Binding.outputSwitchTv) == null) {
                return;
            }
            textView2.setText(R.string.on);
            return;
        }
        UacManager.getUacManager().setOutput(0);
        FragmentDc07Binding fragmentDc07Binding2 = this$0._binding;
        if (fragmentDc07Binding2 == null || (textView = fragmentDc07Binding2.outputSwitchTv) == null) {
            return;
        }
        textView.setText(R.string.off);
    }

    private final void updateDigitalFilter(int i, boolean z) {
        TextView textView;
        RadioGroup radioGroup;
        RadioGroup radioGroup2;
        TextView textView2;
        RadioGroup radioGroup3;
        FragmentDc07Binding fragmentDc07Binding;
        RadioGroup radioGroup4;
        if (!z && (fragmentDc07Binding = this._binding) != null && (radioGroup4 = fragmentDc07Binding.digitalFilterRg) != null) {
            radioGroup4.setOnCheckedChangeListener(null);
        }
        if (i == 1) {
            FragmentDc07Binding fragmentDc07Binding2 = this._binding;
            if (fragmentDc07Binding2 != null && (radioGroup3 = fragmentDc07Binding2.digitalFilterRg) != null) {
                radioGroup3.check(R.id.digitalFilter2Rg);
            }
            FragmentDc07Binding fragmentDc07Binding3 = this._binding;
            if (fragmentDc07Binding3 != null && (textView2 = fragmentDc07Binding3.digitalFilterValueTv) != null) {
                textView2.setText(R.string.digital_filter_dx320_two);
            }
        } else {
            FragmentDc07Binding fragmentDc07Binding4 = this._binding;
            if (fragmentDc07Binding4 != null && (radioGroup = fragmentDc07Binding4.digitalFilterRg) != null) {
                radioGroup.check(R.id.digitalFilter1Rg);
            }
            FragmentDc07Binding fragmentDc07Binding5 = this._binding;
            if (fragmentDc07Binding5 != null && (textView = fragmentDc07Binding5.digitalFilterValueTv) != null) {
                textView.setText(R.string.digital_filter_dx320_one);
            }
        }
        FragmentDc07Binding fragmentDc07Binding6 = this._binding;
        if (fragmentDc07Binding6 == null || (radioGroup2 = fragmentDc07Binding6.digitalFilterRg) == null) {
            return;
        }
        radioGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public final void onCheckedChanged(RadioGroup radioGroup5, int i2) {
                Dc07Fragment.updateDigitalFilter$lambda$0(Dc07Fragment.this, radioGroup5, i2);
            }
        });
    }

    public static void updateDigitalFilter$default(Dc07Fragment dc07Fragment, int i, boolean z, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z = true;
        }
        dc07Fragment.updateDigitalFilter(i, z);
    }

    public static final void updateDigitalFilter$lambda$0(Dc07Fragment this$0, RadioGroup radioGroup, int i) {
        TextView textView;
        TextView textView2;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(radioGroup, "<anonymous parameter 0>");
        if (i == R.id.digitalFilter2Rg) {
            UacManager.getUacManager().setDigitalFilter(1);
            FragmentDc07Binding fragmentDc07Binding = this$0._binding;
            if (fragmentDc07Binding == null || (textView2 = fragmentDc07Binding.digitalFilterValueTv) == null) {
                return;
            }
            textView2.setText(R.string.digital_filter_dx320_two);
            return;
        }
        UacManager.getUacManager().setDigitalFilter(0);
        FragmentDc07Binding fragmentDc07Binding2 = this$0._binding;
        if (fragmentDc07Binding2 == null || (textView = fragmentDc07Binding2.digitalFilterValueTv) == null) {
            return;
        }
        textView.setText(R.string.digital_filter_dx320_one);
    }

    private final void updateDsdFilter(int i, boolean z) {
        ThirdSwitchBar thirdSwitchBar;
        ThirdSwitchBar thirdSwitchBar2;
        FragmentDc07Binding fragmentDc07Binding;
        ThirdSwitchBar thirdSwitchBar3;
        if (!z && (fragmentDc07Binding = this._binding) != null && (thirdSwitchBar3 = fragmentDc07Binding.dsdFilterThirdSwitchBar) != null) {
            thirdSwitchBar3.setSeekTouchListener(null);
        }
        FragmentDc07Binding fragmentDc07Binding2 = this._binding;
        if (fragmentDc07Binding2 != null && (thirdSwitchBar2 = fragmentDc07Binding2.dsdFilterThirdSwitchBar) != null) {
            thirdSwitchBar2.setStatus(i);
        }
        updateThreeDsdFilterTipsViewStatus$default(this, i, false, 2, null);
        FragmentDc07Binding fragmentDc07Binding3 = this._binding;
        if (fragmentDc07Binding3 == null || (thirdSwitchBar = fragmentDc07Binding3.dsdFilterThirdSwitchBar) == null) {
            return;
        }
        thirdSwitchBar.setSeekTouchListener(new ThirdSwitchBar.SeekTouchListener() {
            @Override
            public void touchEnd(@Nullable SeekBar seekBar) {
                Dc07Fragment.this.updateThreeDsdFilterTipsViewStatus(2, true);
            }

            @Override
            public void touchMiddle(@Nullable SeekBar seekBar) {
                Dc07Fragment.this.updateThreeDsdFilterTipsViewStatus(1, true);
            }

            @Override
            public void touchTop(@Nullable SeekBar seekBar) {
                Dc07Fragment.this.updateThreeDsdFilterTipsViewStatus(0, true);
            }
        });
    }

    public static void updateDsdFilter$default(Dc07Fragment dc07Fragment, int i, boolean z, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z = true;
        }
        dc07Fragment.updateDsdFilter(i, z);
    }

    private final void updateKeyControllerBar(int i, boolean z) {
        TextView textView;
        Switch r3;
        TextView textView2;
        FragmentDc07Binding fragmentDc07Binding;
        Switch r4;
        if (!z && (fragmentDc07Binding = this._binding) != null && (r4 = fragmentDc07Binding.pcmVolumeTrimKeySwitch) != null) {
            r4.setOnCheckedChangeListener(null);
        }
        FragmentDc07Binding fragmentDc07Binding2 = this._binding;
        Switch r0 = fragmentDc07Binding2 != null ? fragmentDc07Binding2.pcmVolumeTrimKeySwitch : null;
        if (r0 != null) {
            r0.setChecked(i == 1);
        }
        if (i == 1) {
            FragmentDc07Binding fragmentDc07Binding3 = this._binding;
            if (fragmentDc07Binding3 != null && (textView2 = fragmentDc07Binding3.pcmVolumeTrimKeySwitchTv) != null) {
                textView2.setText(R.string.enable);
            }
        } else {
            FragmentDc07Binding fragmentDc07Binding4 = this._binding;
            if (fragmentDc07Binding4 != null && (textView = fragmentDc07Binding4.pcmVolumeTrimKeySwitchTv) != null) {
                textView.setText(R.string.disable);
            }
        }
        FragmentDc07Binding fragmentDc07Binding5 = this._binding;
        if (fragmentDc07Binding5 == null || (r3 = fragmentDc07Binding5.pcmVolumeTrimKeySwitch) == null) {
            return;
        }
        r3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z2) {
                Dc07Fragment.updateKeyControllerBar$lambda$7(Dc07Fragment.this, compoundButton, z2);
            }
        });
    }

    public static void updateKeyControllerBar$default(Dc07Fragment dc07Fragment, int i, boolean z, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z = true;
        }
        dc07Fragment.updateKeyControllerBar(i, z);
    }

    public static final void updateKeyControllerBar$lambda$7(Dc07Fragment this$0, CompoundButton buttonView, boolean z) {
        TextView textView;
        TextView textView2;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(buttonView, "buttonView");
        if (z) {
            UacManager.getUacManager().setKeyControlStatus(1);
            FragmentDc07Binding fragmentDc07Binding = this$0._binding;
            if (fragmentDc07Binding == null || (textView2 = fragmentDc07Binding.pcmVolumeTrimKeySwitchTv) == null) {
                return;
            }
            textView2.setText(R.string.enable);
            return;
        }
        UacManager.getUacManager().setKeyControlStatus(0);
        FragmentDc07Binding fragmentDc07Binding2 = this$0._binding;
        if (fragmentDc07Binding2 == null || (textView = fragmentDc07Binding2.pcmVolumeTrimKeySwitchTv) == null) {
            return;
        }
        textView.setText(R.string.disable);
    }

    private final void updatePcmTrim(int i, boolean z) {
        RadioGroup radioGroup;
        RadioGroup radioGroup2;
        RadioGroup radioGroup3;
        RadioGroup radioGroup4;
        RadioGroup radioGroup5;
        FragmentDc07Binding fragmentDc07Binding;
        RadioGroup radioGroup6;
        if (!z && (fragmentDc07Binding = this._binding) != null && (radioGroup6 = fragmentDc07Binding.pcmTrimRg) != null) {
            radioGroup6.setOnCheckedChangeListener(null);
        }
        if (i == 1) {
            FragmentDc07Binding fragmentDc07Binding2 = this._binding;
            if (fragmentDc07Binding2 != null && (radioGroup = fragmentDc07Binding2.pcmTrimRg) != null) {
                radioGroup.check(R.id.pcmTrim1Rg);
            }
        } else if (i == 2) {
            FragmentDc07Binding fragmentDc07Binding3 = this._binding;
            if (fragmentDc07Binding3 != null && (radioGroup3 = fragmentDc07Binding3.pcmTrimRg) != null) {
                radioGroup3.check(R.id.pcmTrim2Rg);
            }
        } else if (i != 3) {
            FragmentDc07Binding fragmentDc07Binding4 = this._binding;
            if (fragmentDc07Binding4 != null && (radioGroup5 = fragmentDc07Binding4.pcmTrimRg) != null) {
                radioGroup5.check(R.id.pcmTrim0Rg);
            }
        } else {
            FragmentDc07Binding fragmentDc07Binding5 = this._binding;
            if (fragmentDc07Binding5 != null && (radioGroup4 = fragmentDc07Binding5.pcmTrimRg) != null) {
                radioGroup4.check(R.id.pcmTrim3Rg);
            }
        }
        FragmentDc07Binding fragmentDc07Binding6 = this._binding;
        if (fragmentDc07Binding6 == null || (radioGroup2 = fragmentDc07Binding6.pcmTrimRg) == null) {
            return;
        }
        radioGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public final void onCheckedChanged(RadioGroup radioGroup7, int i2) {
                Dc07Fragment.updatePcmTrim$lambda$5(radioGroup7, i2);
            }
        });
    }

    public static void updatePcmTrim$default(Dc07Fragment dc07Fragment, int i, boolean z, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z = true;
        }
        dc07Fragment.updatePcmTrim(i, z);
    }

    public static final void updatePcmTrim$lambda$5(RadioGroup radioGroup, int i) {
        Intrinsics.checkNotNullParameter(radioGroup, "<anonymous parameter 0>");
        switch (i) {
            case R.id.pcmTrim0Rg:
                UacManager.getUacManager().setPcmTrim(0);
                return;
            case R.id.pcmTrim1Rg:
                UacManager.getUacManager().setPcmTrim(1);
                return;
            case R.id.pcmTrim2Rg:
            default:
                UacManager.getUacManager().setPcmTrim(2);
                return;
            case R.id.pcmTrim3Rg:
                UacManager.getUacManager().setPcmTrim(3);
                return;
        }
    }

    private final void updateSpdif(int i, boolean z) {
        TextView textView;
        Switch r3;
        TextView textView2;
        FragmentDc07Binding fragmentDc07Binding;
        Switch r4;
        if (!z && (fragmentDc07Binding = this._binding) != null && (r4 = fragmentDc07Binding.codecExternalSwitch) != null) {
            r4.setOnCheckedChangeListener(null);
        }
        FragmentDc07Binding fragmentDc07Binding2 = this._binding;
        Switch r0 = fragmentDc07Binding2 != null ? fragmentDc07Binding2.codecExternalSwitch : null;
        if (r0 != null) {
            r0.setChecked(i == 1);
        }
        if (i == 1) {
            FragmentDc07Binding fragmentDc07Binding3 = this._binding;
            if (fragmentDc07Binding3 != null && (textView2 = fragmentDc07Binding3.codecExternalSwitchTv) != null) {
                textView2.setText(R.string.on);
            }
        } else {
            FragmentDc07Binding fragmentDc07Binding4 = this._binding;
            if (fragmentDc07Binding4 != null && (textView = fragmentDc07Binding4.codecExternalSwitchTv) != null) {
                textView.setText(R.string.off);
            }
        }
        FragmentDc07Binding fragmentDc07Binding5 = this._binding;
        if (fragmentDc07Binding5 == null || (r3 = fragmentDc07Binding5.codecExternalSwitch) == null) {
            return;
        }
        r3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z2) {
                Dc07Fragment.updateSpdif$lambda$4(Dc07Fragment.this, compoundButton, z2);
            }
        });
    }

    public static void updateSpdif$default(Dc07Fragment dc07Fragment, int i, boolean z, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z = true;
        }
        dc07Fragment.updateSpdif(i, z);
    }

    public static final void updateSpdif$lambda$4(Dc07Fragment this$0, CompoundButton buttonView, boolean z) {
        TextView textView;
        TextView textView2;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(buttonView, "buttonView");
        if (z) {
            UacManager.getUacManager().setCodecExternal(1);
            FragmentDc07Binding fragmentDc07Binding = this$0._binding;
            if (fragmentDc07Binding == null || (textView2 = fragmentDc07Binding.codecExternalSwitchTv) == null) {
                return;
            }
            textView2.setText(R.string.on);
            return;
        }
        UacManager.getUacManager().setCodecExternal(0);
        FragmentDc07Binding fragmentDc07Binding2 = this$0._binding;
        if (fragmentDc07Binding2 == null || (textView = fragmentDc07Binding2.codecExternalSwitchTv) == null) {
            return;
        }
        textView.setText(R.string.off);
    }

    public final void updateThreeDsdFilterTipsViewStatus(int i, boolean z) {
        TextView textView;
        TextView textView2;
        TextView textView3;
        if (i == 0) {
            FragmentDc07Binding fragmentDc07Binding = this._binding;
            if (fragmentDc07Binding != null && (textView = fragmentDc07Binding.gainTipsTv) != null) {
                textView.setText(R.string.settings_gain_low);
            }
        } else if (i != 1) {
            FragmentDc07Binding fragmentDc07Binding2 = this._binding;
            if (fragmentDc07Binding2 != null && (textView3 = fragmentDc07Binding2.gainTipsTv) != null) {
                textView3.setText(R.string.settings_gain_high);
            }
        } else {
            FragmentDc07Binding fragmentDc07Binding3 = this._binding;
            if (fragmentDc07Binding3 != null && (textView2 = fragmentDc07Binding3.gainTipsTv) != null) {
                textView2.setText(R.string.settings_gain_middle);
            }
        }
        if (z) {
            UacManager.getUacManager().setDsdFilter(i);
        }
    }

    public static void updateThreeDsdFilterTipsViewStatus$default(Dc07Fragment dc07Fragment, int i, boolean z, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z = false;
        }
        dc07Fragment.updateThreeDsdFilterTipsViewStatus(i, z);
    }

    @Override
    @Nullable
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        FragmentDc07Binding inflate = FragmentDc07Binding.inflate(inflater, viewGroup, false);
        this._binding = inflate;
        if (inflate != null) {
            return inflate.getRoot();
        }
        return null;
    }

    @Subscribe(sticky = EmbeddingCompat.DEBUG, threadMode = ThreadMode.MAIN)
    public final void onDc06VolumeEvent(@Nullable Dc06VolumeEvent dc06VolumeEvent) {
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
        Integer valueOf = digitalFilterEvent != null ? Integer.valueOf(digitalFilterEvent.value & 255) : null;
        if (valueOf != null && valueOf.intValue() == 1) {
            FragmentDc07Binding fragmentDc07Binding = this._binding;
            if (fragmentDc07Binding != null && (radioGroup2 = fragmentDc07Binding.digitalFilterRg) != null) {
                radioGroup2.check(R.id.digitalFilter2Rg);
            }
            FragmentDc07Binding fragmentDc07Binding2 = this._binding;
            if (fragmentDc07Binding2 == null || (textView2 = fragmentDc07Binding2.digitalFilterValueTv) == null) {
                return;
            }
            textView2.setText(R.string.digital_filter_dx320_two);
            return;
        }
        FragmentDc07Binding fragmentDc07Binding3 = this._binding;
        if (fragmentDc07Binding3 != null && (radioGroup = fragmentDc07Binding3.digitalFilterRg) != null) {
            radioGroup.check(R.id.digitalFilter1Rg);
        }
        FragmentDc07Binding fragmentDc07Binding4 = this._binding;
        if (fragmentDc07Binding4 == null || (textView = fragmentDc07Binding4.digitalFilterValueTv) == null) {
            return;
        }
        textView.setText(R.string.digital_filter_dx320_one);
    }

    @Subscribe(sticky = EmbeddingCompat.DEBUG, threadMode = ThreadMode.MAIN)
    public final void onFpgaVersionEvent(@Nullable FpgaVersionEvent fpgaVersionEvent) {
        ConstraintLayout constraintLayout;
        if (fpgaVersionEvent != null) {
            FragmentDc07Binding fragmentDc07Binding = this._binding;
            TextView textView = fragmentDc07Binding != null ? fragmentDc07Binding.fpgaVersionTv : null;
            if (textView != null) {
                textView.setText(getString(R.string.version) + fpgaVersionEvent.fpgaVersion);
            }
            String str = fpgaVersionEvent.fpgaVersion;
            Intrinsics.checkNotNullExpressionValue(str, "event.fpgaVersion");
            List split$default = StringsKt.split$default((CharSequence) str, new String[]{"."}, false, 0, 6, (Object) null);
            int parseInt = Integer.parseInt((String) split$default.get(0));
            int parseInt2 = Integer.parseInt((String) split$default.get(1));
            if (parseInt > 1 || (parseInt == 1 && parseInt2 > 89)) {
                FragmentDc07Binding fragmentDc07Binding2 = this._binding;
                RadioButton radioButton = fragmentDc07Binding2 != null ? fragmentDc07Binding2.pcmTrim3Rg : null;
                if (radioButton != null) {
                    radioButton.setVisibility(0);
                }
            } else {
                FragmentDc07Binding fragmentDc07Binding3 = this._binding;
                RadioButton radioButton2 = fragmentDc07Binding3 != null ? fragmentDc07Binding3.pcmTrim3Rg : null;
                if (radioButton2 != null) {
                    radioButton2.setVisibility(8);
                }
            }
            if (parseInt > 1 || (parseInt == 1 && parseInt2 > 98)) {
                FragmentDc07Binding fragmentDc07Binding4 = this._binding;
                constraintLayout = fragmentDc07Binding4 != null ? fragmentDc07Binding4.eightDiv : null;
                if (constraintLayout == null) {
                    return;
                }
                constraintLayout.setVisibility(0);
                return;
            }
            FragmentDc07Binding fragmentDc07Binding5 = this._binding;
            constraintLayout = fragmentDc07Binding5 != null ? fragmentDc07Binding5.eightDiv : null;
            if (constraintLayout == null) {
                return;
            }
            constraintLayout.setVisibility(8);
        }
    }

    @Subscribe(sticky = EmbeddingCompat.DEBUG, threadMode = ThreadMode.MAIN)
    public final void onNativeReportEvent(@Nullable NativeReportEvent nativeReportEvent) {
        if (nativeReportEvent != null) {
            int i = nativeReportEvent.type;
            if (((byte) i) == 96) {
                updateSpdif(nativeReportEvent.value, false);
                updatePcmTrim(nativeReportEvent.value1, false);
                updateCompensation(nativeReportEvent.value2, false);
                updateKeyControllerBar(nativeReportEvent.value3, false);
            } else if (89 == i) {
                updateDigitalFilter(nativeReportEvent.value, false);
                updateDsdFilter(nativeReportEvent.value1, false);
            } else if (i == 96) {
                updateSpdif(nativeReportEvent.value, false);
                updatePcmTrim(nativeReportEvent.value1, false);
                updateCompensation(nativeReportEvent.value2, false);
                updateKeyControllerBar(nativeReportEvent.value3, false);
            }
        }
    }

    @Subscribe(sticky = EmbeddingCompat.DEBUG, threadMode = ThreadMode.MAIN)
    public final void onNormalVolumeEvent(@NotNull NormalVolumeEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
    }

    @Override
    public void onProgressChanged(@Nullable SeekBar seekBar, int i, boolean z) {
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
        Intrinsics.checkNotNullParameter(view, "view");
        super.onViewCreated(view, bundle);
        updateDigitalFilter(UacManager.getUacManager().getDigitalFilter() & 255, false);
        updatePcmTrim$default(this, UacManager.getUacManager().getPcmTrim() & 255, false, 2, null);
        updateDsdFilter(SpStorage.getInstance().getDc07DsdFilter(), false);
        updateCompensation(SpStorage.getInstance().getDc07DsdVolumeCompensation(), false);
        updateSpdif(UacManager.getUacManager().getCodecExternal(), false);
        updateKeyControllerBar(UacManager.getUacManager().getKeyControlStatus(), false);
        EventBus.getDefault().register(this);
    }
}
