package com.ibasso.volume.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.ibasso.music.widgets.BalanceView;
import com.ibasso.music.widgets.ThirdSwitchBar;
import com.ibasso.volume.R;
public final class FragmentMacchiatoBinding implements ViewBinding {
    @NonNull
    public final TextView ampModeTv;
    @NonNull
    public final ImageView apmModeIv;
    @NonNull
    public final ImageView apmModeTipsIv;
    @NonNull
    public final TextView apmModeTipsTv;
    @NonNull
    public final ImageView balanceIv;
    @NonNull
    public final BalanceView balanceSeekBar;
    @NonNull
    public final TextView balanceValueTv;
    @NonNull
    public final ImageView codecExternalIv;
    @NonNull
    public final Switch codecExternalSwitch;
    @NonNull
    public final TextView codecExternalSwitchTv;
    @NonNull
    public final TextView codecExternalTv;
    @NonNull
    public final RadioButton digitalFilter1Rg;
    @NonNull
    public final RadioButton digitalFilter2Rg;
    @NonNull
    public final RadioButton digitalFilter3Rg;
    @NonNull
    public final RadioButton digitalFilter4Rg;
    @NonNull
    public final RadioButton digitalFilter5Rg;
    @NonNull
    public final ImageView digitalFilterIv;
    @NonNull
    public final RadioGroup digitalFilterRg;
    @NonNull
    public final TextView digitalFilterValueTv;
    @NonNull
    public final ImageView dreIv;
    @NonNull
    public final ImageView dreTipsIv;
    @NonNull
    public final TextView dreTipsTv;
    @NonNull
    public final TextView dreTv;
    @NonNull
    public final ConstraintLayout eightDiv;
    @NonNull
    public final ImageView eqIv;
    @NonNull
    public final ImageView eqTipsIv;
    @NonNull
    public final TextView eqTipsTv;
    @NonNull
    public final TextView eqTv;
    @NonNull
    public final View firstDiv;
    @NonNull
    public final View firstDiv0;
    @NonNull
    public final View fiveDiv;
    @NonNull
    public final TextView fpgaVersionTv;
    @NonNull
    public final FrameLayout fragmentContainer;
    @NonNull
    public final ImageView gainIv;
    @NonNull
    public final ThirdSwitchBar gainThirdSwitchBar;
    @NonNull
    public final TextView gainTipsTv;
    @NonNull
    public final TextView gainTv;
    @NonNull
    public final ImageView icVolumeImage;
    @NonNull
    public final RadioButton language0Rg;
    @NonNull
    public final RadioButton language1Rg;
    @NonNull
    public final RadioButton language2Rg;
    @NonNull
    public final View languageDiv;
    @NonNull
    public final ImageView languageIv;
    @NonNull
    public final RadioGroup languageRg;
    @NonNull
    public final TextView manufacturerTv;
    @NonNull
    public final ImageView micModeIv;
    @NonNull
    public final Switch micModeSwitch;
    @NonNull
    public final TextView micModeSwitchTv;
    @NonNull
    public final TextView micModeTv;
    @NonNull
    public final ImageView opVoltageIv;
    @NonNull
    public final ImageView opVoltageTipsIv;
    @NonNull
    public final TextView opVoltageTipsTv;
    @NonNull
    public final TextView opVoltageTv;
    @NonNull
    public final ImageView productIdIv;
    @NonNull
    public final TextView productNameTv;
    @NonNull
    private final FrameLayout rootView;
    @NonNull
    public final ImageView screemTimeIv;
    @NonNull
    public final ImageView screenRotateIv;
    @NonNull
    public final Switch screenRotateSwitch;
    @NonNull
    public final TextView screenRotateSwitchTv;
    @NonNull
    public final TextView screenRotateTv;
    @NonNull
    public final RadioButton screenTime0Rg;
    @NonNull
    public final RadioButton screenTime1Rg;
    @NonNull
    public final RadioButton screenTime2Rg;
    @NonNull
    public final RadioButton screenTime3Rg;
    @NonNull
    public final RadioGroup screenTimeRg;
    @NonNull
    public final View secondDiv0;
    @NonNull
    public final ConstraintLayout settingsAmpModeLayout;
    @NonNull
    public final RelativeLayout settingsDigitalFilterLayout;
    @NonNull
    public final ConstraintLayout settingsDreLayout;
    @NonNull
    public final ConstraintLayout settingsEqLayout;
    @NonNull
    public final RelativeLayout settingsLanguageLayout;
    @NonNull
    public final ConstraintLayout settingsOpVoltageLayout;
    @NonNull
    public final RelativeLayout settingsScreenTimeLayout;
    @NonNull
    public final View sevenDiv;
    @NonNull
    public final View sixDiv;
    @NonNull
    public final TextView textView;
    @NonNull
    public final View thirdDiv;
    @NonNull
    public final SeekBar volumeSeekBar;
    @NonNull
    public final TextView volumeValueTv;

    private FragmentMacchiatoBinding(@NonNull FrameLayout frameLayout, @NonNull TextView textView, @NonNull ImageView imageView, @NonNull ImageView imageView2, @NonNull TextView textView2, @NonNull ImageView imageView3, @NonNull BalanceView balanceView, @NonNull TextView textView3, @NonNull ImageView imageView4, @NonNull Switch r10, @NonNull TextView textView4, @NonNull TextView textView5, @NonNull RadioButton radioButton, @NonNull RadioButton radioButton2, @NonNull RadioButton radioButton3, @NonNull RadioButton radioButton4, @NonNull RadioButton radioButton5, @NonNull ImageView imageView5, @NonNull RadioGroup radioGroup, @NonNull TextView textView6, @NonNull ImageView imageView6, @NonNull ImageView imageView7, @NonNull TextView textView7, @NonNull TextView textView8, @NonNull ConstraintLayout constraintLayout, @NonNull ImageView imageView8, @NonNull ImageView imageView9, @NonNull TextView textView9, @NonNull TextView textView10, @NonNull View view, @NonNull View view2, @NonNull View view3, @NonNull TextView textView11, @NonNull FrameLayout frameLayout2, @NonNull ImageView imageView10, @NonNull ThirdSwitchBar thirdSwitchBar, @NonNull TextView textView12, @NonNull TextView textView13, @NonNull ImageView imageView11, @NonNull RadioButton radioButton6, @NonNull RadioButton radioButton7, @NonNull RadioButton radioButton8, @NonNull View view4, @NonNull ImageView imageView12, @NonNull RadioGroup radioGroup2, @NonNull TextView textView14, @NonNull ImageView imageView13, @NonNull Switch r48, @NonNull TextView textView15, @NonNull TextView textView16, @NonNull ImageView imageView14, @NonNull ImageView imageView15, @NonNull TextView textView17, @NonNull TextView textView18, @NonNull ImageView imageView16, @NonNull TextView textView19, @NonNull ImageView imageView17, @NonNull ImageView imageView18, @NonNull Switch r59, @NonNull TextView textView20, @NonNull TextView textView21, @NonNull RadioButton radioButton9, @NonNull RadioButton radioButton10, @NonNull RadioButton radioButton11, @NonNull RadioButton radioButton12, @NonNull RadioGroup radioGroup3, @NonNull View view5, @NonNull ConstraintLayout constraintLayout2, @NonNull RelativeLayout relativeLayout, @NonNull ConstraintLayout constraintLayout3, @NonNull ConstraintLayout constraintLayout4, @NonNull RelativeLayout relativeLayout2, @NonNull ConstraintLayout constraintLayout5, @NonNull RelativeLayout relativeLayout3, @NonNull View view6, @NonNull View view7, @NonNull TextView textView22, @NonNull View view8, @NonNull SeekBar seekBar, @NonNull TextView textView23) {
        this.rootView = frameLayout;
        this.ampModeTv = textView;
        this.apmModeIv = imageView;
        this.apmModeTipsIv = imageView2;
        this.apmModeTipsTv = textView2;
        this.balanceIv = imageView3;
        this.balanceSeekBar = balanceView;
        this.balanceValueTv = textView3;
        this.codecExternalIv = imageView4;
        this.codecExternalSwitch = r10;
        this.codecExternalSwitchTv = textView4;
        this.codecExternalTv = textView5;
        this.digitalFilter1Rg = radioButton;
        this.digitalFilter2Rg = radioButton2;
        this.digitalFilter3Rg = radioButton3;
        this.digitalFilter4Rg = radioButton4;
        this.digitalFilter5Rg = radioButton5;
        this.digitalFilterIv = imageView5;
        this.digitalFilterRg = radioGroup;
        this.digitalFilterValueTv = textView6;
        this.dreIv = imageView6;
        this.dreTipsIv = imageView7;
        this.dreTipsTv = textView7;
        this.dreTv = textView8;
        this.eightDiv = constraintLayout;
        this.eqIv = imageView8;
        this.eqTipsIv = imageView9;
        this.eqTipsTv = textView9;
        this.eqTv = textView10;
        this.firstDiv = view;
        this.firstDiv0 = view2;
        this.fiveDiv = view3;
        this.fpgaVersionTv = textView11;
        this.fragmentContainer = frameLayout2;
        this.gainIv = imageView10;
        this.gainThirdSwitchBar = thirdSwitchBar;
        this.gainTipsTv = textView12;
        this.gainTv = textView13;
        this.icVolumeImage = imageView11;
        this.language0Rg = radioButton6;
        this.language1Rg = radioButton7;
        this.language2Rg = radioButton8;
        this.languageDiv = view4;
        this.languageIv = imageView12;
        this.languageRg = radioGroup2;
        this.manufacturerTv = textView14;
        this.micModeIv = imageView13;
        this.micModeSwitch = r48;
        this.micModeSwitchTv = textView15;
        this.micModeTv = textView16;
        this.opVoltageIv = imageView14;
        this.opVoltageTipsIv = imageView15;
        this.opVoltageTipsTv = textView17;
        this.opVoltageTv = textView18;
        this.productIdIv = imageView16;
        this.productNameTv = textView19;
        this.screemTimeIv = imageView17;
        this.screenRotateIv = imageView18;
        this.screenRotateSwitch = r59;
        this.screenRotateSwitchTv = textView20;
        this.screenRotateTv = textView21;
        this.screenTime0Rg = radioButton9;
        this.screenTime1Rg = radioButton10;
        this.screenTime2Rg = radioButton11;
        this.screenTime3Rg = radioButton12;
        this.screenTimeRg = radioGroup3;
        this.secondDiv0 = view5;
        this.settingsAmpModeLayout = constraintLayout2;
        this.settingsDigitalFilterLayout = relativeLayout;
        this.settingsDreLayout = constraintLayout3;
        this.settingsEqLayout = constraintLayout4;
        this.settingsLanguageLayout = relativeLayout2;
        this.settingsOpVoltageLayout = constraintLayout5;
        this.settingsScreenTimeLayout = relativeLayout3;
        this.sevenDiv = view6;
        this.sixDiv = view7;
        this.textView = textView22;
        this.thirdDiv = view8;
        this.volumeSeekBar = seekBar;
        this.volumeValueTv = textView23;
    }

    @NonNull
    public static FragmentMacchiatoBinding bind(@NonNull View view) {
        int i = R.id.ampModeTv;
        TextView textView = (TextView) ViewBindings.findChildViewById(view, R.id.ampModeTv);
        if (textView != null) {
            i = R.id.apmModeIv;
            ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, R.id.apmModeIv);
            if (imageView != null) {
                i = R.id.apmModeTipsIv;
                ImageView imageView2 = (ImageView) ViewBindings.findChildViewById(view, R.id.apmModeTipsIv);
                if (imageView2 != null) {
                    i = R.id.apmModeTipsTv;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(view, R.id.apmModeTipsTv);
                    if (textView2 != null) {
                        i = R.id.balanceIv;
                        ImageView imageView3 = (ImageView) ViewBindings.findChildViewById(view, R.id.balanceIv);
                        if (imageView3 != null) {
                            i = R.id.balanceSeekBar;
                            BalanceView balanceView = (BalanceView) ViewBindings.findChildViewById(view, R.id.balanceSeekBar);
                            if (balanceView != null) {
                                i = R.id.balanceValueTv;
                                TextView textView3 = (TextView) ViewBindings.findChildViewById(view, R.id.balanceValueTv);
                                if (textView3 != null) {
                                    i = R.id.codecExternalIv;
                                    ImageView imageView4 = (ImageView) ViewBindings.findChildViewById(view, R.id.codecExternalIv);
                                    if (imageView4 != null) {
                                        i = R.id.codecExternalSwitch;
                                        Switch r13 = (Switch) ViewBindings.findChildViewById(view, R.id.codecExternalSwitch);
                                        if (r13 != null) {
                                            i = R.id.codecExternalSwitchTv;
                                            TextView textView4 = (TextView) ViewBindings.findChildViewById(view, R.id.codecExternalSwitchTv);
                                            if (textView4 != null) {
                                                i = R.id.codecExternalTv;
                                                TextView textView5 = (TextView) ViewBindings.findChildViewById(view, R.id.codecExternalTv);
                                                if (textView5 != null) {
                                                    i = R.id.digitalFilter1Rg;
                                                    RadioButton radioButton = (RadioButton) ViewBindings.findChildViewById(view, R.id.digitalFilter1Rg);
                                                    if (radioButton != null) {
                                                        i = R.id.digitalFilter2Rg;
                                                        RadioButton radioButton2 = (RadioButton) ViewBindings.findChildViewById(view, R.id.digitalFilter2Rg);
                                                        if (radioButton2 != null) {
                                                            i = R.id.digitalFilter3Rg;
                                                            RadioButton radioButton3 = (RadioButton) ViewBindings.findChildViewById(view, R.id.digitalFilter3Rg);
                                                            if (radioButton3 != null) {
                                                                i = R.id.digitalFilter4Rg;
                                                                RadioButton radioButton4 = (RadioButton) ViewBindings.findChildViewById(view, R.id.digitalFilter4Rg);
                                                                if (radioButton4 != null) {
                                                                    i = R.id.digitalFilter5Rg;
                                                                    RadioButton radioButton5 = (RadioButton) ViewBindings.findChildViewById(view, R.id.digitalFilter5Rg);
                                                                    if (radioButton5 != null) {
                                                                        i = R.id.digital_filter_iv;
                                                                        ImageView imageView5 = (ImageView) ViewBindings.findChildViewById(view, R.id.digital_filter_iv);
                                                                        if (imageView5 != null) {
                                                                            i = R.id.digitalFilterRg;
                                                                            RadioGroup radioGroup = (RadioGroup) ViewBindings.findChildViewById(view, R.id.digitalFilterRg);
                                                                            if (radioGroup != null) {
                                                                                i = R.id.digitalFilterValueTv;
                                                                                TextView textView6 = (TextView) ViewBindings.findChildViewById(view, R.id.digitalFilterValueTv);
                                                                                if (textView6 != null) {
                                                                                    i = R.id.dreIv;
                                                                                    ImageView imageView6 = (ImageView) ViewBindings.findChildViewById(view, R.id.dreIv);
                                                                                    if (imageView6 != null) {
                                                                                        i = R.id.dreTipsIv;
                                                                                        ImageView imageView7 = (ImageView) ViewBindings.findChildViewById(view, R.id.dreTipsIv);
                                                                                        if (imageView7 != null) {
                                                                                            i = R.id.dreTipsTv;
                                                                                            TextView textView7 = (TextView) ViewBindings.findChildViewById(view, R.id.dreTipsTv);
                                                                                            if (textView7 != null) {
                                                                                                i = R.id.dreTv;
                                                                                                TextView textView8 = (TextView) ViewBindings.findChildViewById(view, R.id.dreTv);
                                                                                                if (textView8 != null) {
                                                                                                    i = R.id.eightDiv;
                                                                                                    ConstraintLayout constraintLayout = (ConstraintLayout) ViewBindings.findChildViewById(view, R.id.eightDiv);
                                                                                                    if (constraintLayout != null) {
                                                                                                        i = R.id.eqIv;
                                                                                                        ImageView imageView8 = (ImageView) ViewBindings.findChildViewById(view, R.id.eqIv);
                                                                                                        if (imageView8 != null) {
                                                                                                            i = R.id.eqTipsIv;
                                                                                                            ImageView imageView9 = (ImageView) ViewBindings.findChildViewById(view, R.id.eqTipsIv);
                                                                                                            if (imageView9 != null) {
                                                                                                                i = R.id.eqTipsTv;
                                                                                                                TextView textView9 = (TextView) ViewBindings.findChildViewById(view, R.id.eqTipsTv);
                                                                                                                if (textView9 != null) {
                                                                                                                    i = R.id.eqTv;
                                                                                                                    TextView textView10 = (TextView) ViewBindings.findChildViewById(view, R.id.eqTv);
                                                                                                                    if (textView10 != null) {
                                                                                                                        i = R.id.firstDiv;
                                                                                                                        View findChildViewById = ViewBindings.findChildViewById(view, R.id.firstDiv);
                                                                                                                        if (findChildViewById != null) {
                                                                                                                            i = R.id.firstDiv0;
                                                                                                                            View findChildViewById2 = ViewBindings.findChildViewById(view, R.id.firstDiv0);
                                                                                                                            if (findChildViewById2 != null) {
                                                                                                                                i = R.id.fiveDiv;
                                                                                                                                View findChildViewById3 = ViewBindings.findChildViewById(view, R.id.fiveDiv);
                                                                                                                                if (findChildViewById3 != null) {
                                                                                                                                    i = R.id.fpgaVersionTv;
                                                                                                                                    TextView textView11 = (TextView) ViewBindings.findChildViewById(view, R.id.fpgaVersionTv);
                                                                                                                                    if (textView11 != null) {
                                                                                                                                        i = R.id.fragment_container;
                                                                                                                                        FrameLayout frameLayout = (FrameLayout) ViewBindings.findChildViewById(view, R.id.fragment_container);
                                                                                                                                        if (frameLayout != null) {
                                                                                                                                            i = R.id.gainIv;
                                                                                                                                            ImageView imageView10 = (ImageView) ViewBindings.findChildViewById(view, R.id.gainIv);
                                                                                                                                            if (imageView10 != null) {
                                                                                                                                                i = R.id.gainThirdSwitchBar;
                                                                                                                                                ThirdSwitchBar thirdSwitchBar = (ThirdSwitchBar) ViewBindings.findChildViewById(view, R.id.gainThirdSwitchBar);
                                                                                                                                                if (thirdSwitchBar != null) {
                                                                                                                                                    i = R.id.gainTipsTv;
                                                                                                                                                    TextView textView12 = (TextView) ViewBindings.findChildViewById(view, R.id.gainTipsTv);
                                                                                                                                                    if (textView12 != null) {
                                                                                                                                                        i = R.id.gainTv;
                                                                                                                                                        TextView textView13 = (TextView) ViewBindings.findChildViewById(view, R.id.gainTv);
                                                                                                                                                        if (textView13 != null) {
                                                                                                                                                            i = R.id.icVolumeImage;
                                                                                                                                                            ImageView imageView11 = (ImageView) ViewBindings.findChildViewById(view, R.id.icVolumeImage);
                                                                                                                                                            if (imageView11 != null) {
                                                                                                                                                                i = R.id.language0Rg;
                                                                                                                                                                RadioButton radioButton6 = (RadioButton) ViewBindings.findChildViewById(view, R.id.language0Rg);
                                                                                                                                                                if (radioButton6 != null) {
                                                                                                                                                                    i = R.id.language1Rg;
                                                                                                                                                                    RadioButton radioButton7 = (RadioButton) ViewBindings.findChildViewById(view, R.id.language1Rg);
                                                                                                                                                                    if (radioButton7 != null) {
                                                                                                                                                                        i = R.id.language2Rg;
                                                                                                                                                                        RadioButton radioButton8 = (RadioButton) ViewBindings.findChildViewById(view, R.id.language2Rg);
                                                                                                                                                                        if (radioButton8 != null) {
                                                                                                                                                                            i = R.id.languageDiv;
                                                                                                                                                                            View findChildViewById4 = ViewBindings.findChildViewById(view, R.id.languageDiv);
                                                                                                                                                                            if (findChildViewById4 != null) {
                                                                                                                                                                                i = R.id.languageIv;
                                                                                                                                                                                ImageView imageView12 = (ImageView) ViewBindings.findChildViewById(view, R.id.languageIv);
                                                                                                                                                                                if (imageView12 != null) {
                                                                                                                                                                                    i = R.id.languageRg;
                                                                                                                                                                                    RadioGroup radioGroup2 = (RadioGroup) ViewBindings.findChildViewById(view, R.id.languageRg);
                                                                                                                                                                                    if (radioGroup2 != null) {
                                                                                                                                                                                        i = R.id.manufacturerTv;
                                                                                                                                                                                        TextView textView14 = (TextView) ViewBindings.findChildViewById(view, R.id.manufacturerTv);
                                                                                                                                                                                        if (textView14 != null) {
                                                                                                                                                                                            i = R.id.micModeIv;
                                                                                                                                                                                            ImageView imageView13 = (ImageView) ViewBindings.findChildViewById(view, R.id.micModeIv);
                                                                                                                                                                                            if (imageView13 != null) {
                                                                                                                                                                                                i = R.id.micModeSwitch;
                                                                                                                                                                                                Switch r51 = (Switch) ViewBindings.findChildViewById(view, R.id.micModeSwitch);
                                                                                                                                                                                                if (r51 != null) {
                                                                                                                                                                                                    i = R.id.micModeSwitchTv;
                                                                                                                                                                                                    TextView textView15 = (TextView) ViewBindings.findChildViewById(view, R.id.micModeSwitchTv);
                                                                                                                                                                                                    if (textView15 != null) {
                                                                                                                                                                                                        i = R.id.micModeTv;
                                                                                                                                                                                                        TextView textView16 = (TextView) ViewBindings.findChildViewById(view, R.id.micModeTv);
                                                                                                                                                                                                        if (textView16 != null) {
                                                                                                                                                                                                            i = R.id.opVoltageIv;
                                                                                                                                                                                                            ImageView imageView14 = (ImageView) ViewBindings.findChildViewById(view, R.id.opVoltageIv);
                                                                                                                                                                                                            if (imageView14 != null) {
                                                                                                                                                                                                                i = R.id.opVoltageTipsIv;
                                                                                                                                                                                                                ImageView imageView15 = (ImageView) ViewBindings.findChildViewById(view, R.id.opVoltageTipsIv);
                                                                                                                                                                                                                if (imageView15 != null) {
                                                                                                                                                                                                                    i = R.id.opVoltageTipsTv;
                                                                                                                                                                                                                    TextView textView17 = (TextView) ViewBindings.findChildViewById(view, R.id.opVoltageTipsTv);
                                                                                                                                                                                                                    if (textView17 != null) {
                                                                                                                                                                                                                        i = R.id.opVoltageTv;
                                                                                                                                                                                                                        TextView textView18 = (TextView) ViewBindings.findChildViewById(view, R.id.opVoltageTv);
                                                                                                                                                                                                                        if (textView18 != null) {
                                                                                                                                                                                                                            i = R.id.productIdIv;
                                                                                                                                                                                                                            ImageView imageView16 = (ImageView) ViewBindings.findChildViewById(view, R.id.productIdIv);
                                                                                                                                                                                                                            if (imageView16 != null) {
                                                                                                                                                                                                                                i = R.id.productNameTv;
                                                                                                                                                                                                                                TextView textView19 = (TextView) ViewBindings.findChildViewById(view, R.id.productNameTv);
                                                                                                                                                                                                                                if (textView19 != null) {
                                                                                                                                                                                                                                    i = R.id.screemTimeIv;
                                                                                                                                                                                                                                    ImageView imageView17 = (ImageView) ViewBindings.findChildViewById(view, R.id.screemTimeIv);
                                                                                                                                                                                                                                    if (imageView17 != null) {
                                                                                                                                                                                                                                        i = R.id.screenRotateIv;
                                                                                                                                                                                                                                        ImageView imageView18 = (ImageView) ViewBindings.findChildViewById(view, R.id.screenRotateIv);
                                                                                                                                                                                                                                        if (imageView18 != null) {
                                                                                                                                                                                                                                            i = R.id.screenRotateSwitch;
                                                                                                                                                                                                                                            Switch r62 = (Switch) ViewBindings.findChildViewById(view, R.id.screenRotateSwitch);
                                                                                                                                                                                                                                            if (r62 != null) {
                                                                                                                                                                                                                                                i = R.id.screenRotateSwitchTv;
                                                                                                                                                                                                                                                TextView textView20 = (TextView) ViewBindings.findChildViewById(view, R.id.screenRotateSwitchTv);
                                                                                                                                                                                                                                                if (textView20 != null) {
                                                                                                                                                                                                                                                    i = R.id.screenRotateTv;
                                                                                                                                                                                                                                                    TextView textView21 = (TextView) ViewBindings.findChildViewById(view, R.id.screenRotateTv);
                                                                                                                                                                                                                                                    if (textView21 != null) {
                                                                                                                                                                                                                                                        i = R.id.screenTime0Rg;
                                                                                                                                                                                                                                                        RadioButton radioButton9 = (RadioButton) ViewBindings.findChildViewById(view, R.id.screenTime0Rg);
                                                                                                                                                                                                                                                        if (radioButton9 != null) {
                                                                                                                                                                                                                                                            i = R.id.screenTime1Rg;
                                                                                                                                                                                                                                                            RadioButton radioButton10 = (RadioButton) ViewBindings.findChildViewById(view, R.id.screenTime1Rg);
                                                                                                                                                                                                                                                            if (radioButton10 != null) {
                                                                                                                                                                                                                                                                i = R.id.screenTime2Rg;
                                                                                                                                                                                                                                                                RadioButton radioButton11 = (RadioButton) ViewBindings.findChildViewById(view, R.id.screenTime2Rg);
                                                                                                                                                                                                                                                                if (radioButton11 != null) {
                                                                                                                                                                                                                                                                    i = R.id.screenTime3Rg;
                                                                                                                                                                                                                                                                    RadioButton radioButton12 = (RadioButton) ViewBindings.findChildViewById(view, R.id.screenTime3Rg);
                                                                                                                                                                                                                                                                    if (radioButton12 != null) {
                                                                                                                                                                                                                                                                        i = R.id.screenTimeRg;
                                                                                                                                                                                                                                                                        RadioGroup radioGroup3 = (RadioGroup) ViewBindings.findChildViewById(view, R.id.screenTimeRg);
                                                                                                                                                                                                                                                                        if (radioGroup3 != null) {
                                                                                                                                                                                                                                                                            i = R.id.secondDiv0;
                                                                                                                                                                                                                                                                            View findChildViewById5 = ViewBindings.findChildViewById(view, R.id.secondDiv0);
                                                                                                                                                                                                                                                                            if (findChildViewById5 != null) {
                                                                                                                                                                                                                                                                                i = R.id.settings_amp_mode_layout;
                                                                                                                                                                                                                                                                                ConstraintLayout constraintLayout2 = (ConstraintLayout) ViewBindings.findChildViewById(view, R.id.settings_amp_mode_layout);
                                                                                                                                                                                                                                                                                if (constraintLayout2 != null) {
                                                                                                                                                                                                                                                                                    i = R.id.settings_digital_filter_layout;
                                                                                                                                                                                                                                                                                    RelativeLayout relativeLayout = (RelativeLayout) ViewBindings.findChildViewById(view, R.id.settings_digital_filter_layout);
                                                                                                                                                                                                                                                                                    if (relativeLayout != null) {
                                                                                                                                                                                                                                                                                        i = R.id.settings_dre_layout;
                                                                                                                                                                                                                                                                                        ConstraintLayout constraintLayout3 = (ConstraintLayout) ViewBindings.findChildViewById(view, R.id.settings_dre_layout);
                                                                                                                                                                                                                                                                                        if (constraintLayout3 != null) {
                                                                                                                                                                                                                                                                                            i = R.id.settings_eq_layout;
                                                                                                                                                                                                                                                                                            ConstraintLayout constraintLayout4 = (ConstraintLayout) ViewBindings.findChildViewById(view, R.id.settings_eq_layout);
                                                                                                                                                                                                                                                                                            if (constraintLayout4 != null) {
                                                                                                                                                                                                                                                                                                i = R.id.settings_language_layout;
                                                                                                                                                                                                                                                                                                RelativeLayout relativeLayout2 = (RelativeLayout) ViewBindings.findChildViewById(view, R.id.settings_language_layout);
                                                                                                                                                                                                                                                                                                if (relativeLayout2 != null) {
                                                                                                                                                                                                                                                                                                    i = R.id.settings_op_voltage_layout;
                                                                                                                                                                                                                                                                                                    ConstraintLayout constraintLayout5 = (ConstraintLayout) ViewBindings.findChildViewById(view, R.id.settings_op_voltage_layout);
                                                                                                                                                                                                                                                                                                    if (constraintLayout5 != null) {
                                                                                                                                                                                                                                                                                                        i = R.id.settings_screen_time_layout;
                                                                                                                                                                                                                                                                                                        RelativeLayout relativeLayout3 = (RelativeLayout) ViewBindings.findChildViewById(view, R.id.settings_screen_time_layout);
                                                                                                                                                                                                                                                                                                        if (relativeLayout3 != null) {
                                                                                                                                                                                                                                                                                                            i = R.id.sevenDiv;
                                                                                                                                                                                                                                                                                                            View findChildViewById6 = ViewBindings.findChildViewById(view, R.id.sevenDiv);
                                                                                                                                                                                                                                                                                                            if (findChildViewById6 != null) {
                                                                                                                                                                                                                                                                                                                i = R.id.sixDiv;
                                                                                                                                                                                                                                                                                                                View findChildViewById7 = ViewBindings.findChildViewById(view, R.id.sixDiv);
                                                                                                                                                                                                                                                                                                                if (findChildViewById7 != null) {
                                                                                                                                                                                                                                                                                                                    i = R.id.textView;
                                                                                                                                                                                                                                                                                                                    TextView textView22 = (TextView) ViewBindings.findChildViewById(view, R.id.textView);
                                                                                                                                                                                                                                                                                                                    if (textView22 != null) {
                                                                                                                                                                                                                                                                                                                        i = R.id.thirdDiv;
                                                                                                                                                                                                                                                                                                                        View findChildViewById8 = ViewBindings.findChildViewById(view, R.id.thirdDiv);
                                                                                                                                                                                                                                                                                                                        if (findChildViewById8 != null) {
                                                                                                                                                                                                                                                                                                                            i = R.id.volumeSeekBar;
                                                                                                                                                                                                                                                                                                                            SeekBar seekBar = (SeekBar) ViewBindings.findChildViewById(view, R.id.volumeSeekBar);
                                                                                                                                                                                                                                                                                                                            if (seekBar != null) {
                                                                                                                                                                                                                                                                                                                                i = R.id.volumeValueTv;
                                                                                                                                                                                                                                                                                                                                TextView textView23 = (TextView) ViewBindings.findChildViewById(view, R.id.volumeValueTv);
                                                                                                                                                                                                                                                                                                                                if (textView23 != null) {
                                                                                                                                                                                                                                                                                                                                    return new FragmentMacchiatoBinding((FrameLayout) view, textView, imageView, imageView2, textView2, imageView3, balanceView, textView3, imageView4, r13, textView4, textView5, radioButton, radioButton2, radioButton3, radioButton4, radioButton5, imageView5, radioGroup, textView6, imageView6, imageView7, textView7, textView8, constraintLayout, imageView8, imageView9, textView9, textView10, findChildViewById, findChildViewById2, findChildViewById3, textView11, frameLayout, imageView10, thirdSwitchBar, textView12, textView13, imageView11, radioButton6, radioButton7, radioButton8, findChildViewById4, imageView12, radioGroup2, textView14, imageView13, r51, textView15, textView16, imageView14, imageView15, textView17, textView18, imageView16, textView19, imageView17, imageView18, r62, textView20, textView21, radioButton9, radioButton10, radioButton11, radioButton12, radioGroup3, findChildViewById5, constraintLayout2, relativeLayout, constraintLayout3, constraintLayout4, relativeLayout2, constraintLayout5, relativeLayout3, findChildViewById6, findChildViewById7, textView22, findChildViewById8, seekBar, textView23);
                                                                                                                                                                                                                                                                                                                                }
                                                                                                                                                                                                                                                                                                                            }
                                                                                                                                                                                                                                                                                                                        }
                                                                                                                                                                                                                                                                                                                    }
                                                                                                                                                                                                                                                                                                                }
                                                                                                                                                                                                                                                                                                            }
                                                                                                                                                                                                                                                                                                        }
                                                                                                                                                                                                                                                                                                    }
                                                                                                                                                                                                                                                                                                }
                                                                                                                                                                                                                                                                                            }
                                                                                                                                                                                                                                                                                        }
                                                                                                                                                                                                                                                                                    }
                                                                                                                                                                                                                                                                                }
                                                                                                                                                                                                                                                                            }
                                                                                                                                                                                                                                                                        }
                                                                                                                                                                                                                                                                    }
                                                                                                                                                                                                                                                                }
                                                                                                                                                                                                                                                            }
                                                                                                                                                                                                                                                        }
                                                                                                                                                                                                                                                    }
                                                                                                                                                                                                                                                }
                                                                                                                                                                                                                                            }
                                                                                                                                                                                                                                        }
                                                                                                                                                                                                                                    }
                                                                                                                                                                                                                                }
                                                                                                                                                                                                                            }
                                                                                                                                                                                                                        }
                                                                                                                                                                                                                    }
                                                                                                                                                                                                                }
                                                                                                                                                                                                            }
                                                                                                                                                                                                        }
                                                                                                                                                                                                    }
                                                                                                                                                                                                }
                                                                                                                                                                                            }
                                                                                                                                                                                        }
                                                                                                                                                                                    }
                                                                                                                                                                                }
                                                                                                                                                                            }
                                                                                                                                                                        }
                                                                                                                                                                    }
                                                                                                                                                                }
                                                                                                                                                            }
                                                                                                                                                        }
                                                                                                                                                    }
                                                                                                                                                }
                                                                                                                                            }
                                                                                                                                        }
                                                                                                                                    }
                                                                                                                                }
                                                                                                                            }
                                                                                                                        }
                                                                                                                    }
                                                                                                                }
                                                                                                            }
                                                                                                        }
                                                                                                    }
                                                                                                }
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i)));
    }

    @NonNull
    public static FragmentMacchiatoBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static FragmentMacchiatoBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z) {
        View inflate = layoutInflater.inflate(R.layout.fragment_macchiato, viewGroup, false);
        if (z) {
            viewGroup.addView(inflate);
        }
        return bind(inflate);
    }

    @Override
    @NonNull
    public FrameLayout getRoot() {
        return this.rootView;
    }
}
