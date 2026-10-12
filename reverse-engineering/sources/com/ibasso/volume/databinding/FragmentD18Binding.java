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
public final class FragmentD18Binding implements ViewBinding {
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
    public final View dacGainDivider;
    @NonNull
    public final ImageView dacGainIv;
    @NonNull
    public final Switch dacGainSwitch;
    @NonNull
    public final TextView dacGainTipsTv;
    @NonNull
    public final TextView dacGainTv;
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
    public final ConstraintLayout eightDiv;
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
    public final RelativeLayout settingsDigitalFilterLayout;
    @NonNull
    public final RelativeLayout settingsLanguageLayout;
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

    private FragmentD18Binding(@NonNull FrameLayout frameLayout, @NonNull ImageView imageView, @NonNull BalanceView balanceView, @NonNull TextView textView, @NonNull ImageView imageView2, @NonNull Switch r6, @NonNull TextView textView2, @NonNull TextView textView3, @NonNull View view, @NonNull ImageView imageView3, @NonNull Switch r11, @NonNull TextView textView4, @NonNull TextView textView5, @NonNull RadioButton radioButton, @NonNull RadioButton radioButton2, @NonNull RadioButton radioButton3, @NonNull RadioButton radioButton4, @NonNull RadioButton radioButton5, @NonNull ImageView imageView4, @NonNull RadioGroup radioGroup, @NonNull TextView textView6, @NonNull ConstraintLayout constraintLayout, @NonNull View view2, @NonNull View view3, @NonNull View view4, @NonNull TextView textView7, @NonNull FrameLayout frameLayout2, @NonNull ImageView imageView5, @NonNull ThirdSwitchBar thirdSwitchBar, @NonNull TextView textView8, @NonNull TextView textView9, @NonNull ImageView imageView6, @NonNull RadioButton radioButton6, @NonNull RadioButton radioButton7, @NonNull RadioButton radioButton8, @NonNull View view5, @NonNull ImageView imageView7, @NonNull RadioGroup radioGroup2, @NonNull TextView textView10, @NonNull ImageView imageView8, @NonNull Switch r41, @NonNull TextView textView11, @NonNull TextView textView12, @NonNull ImageView imageView9, @NonNull TextView textView13, @NonNull ImageView imageView10, @NonNull ImageView imageView11, @NonNull Switch r48, @NonNull TextView textView14, @NonNull TextView textView15, @NonNull RadioButton radioButton9, @NonNull RadioButton radioButton10, @NonNull RadioButton radioButton11, @NonNull RadioButton radioButton12, @NonNull RadioGroup radioGroup3, @NonNull View view6, @NonNull RelativeLayout relativeLayout, @NonNull RelativeLayout relativeLayout2, @NonNull RelativeLayout relativeLayout3, @NonNull View view7, @NonNull View view8, @NonNull TextView textView16, @NonNull View view9, @NonNull SeekBar seekBar, @NonNull TextView textView17) {
        this.rootView = frameLayout;
        this.balanceIv = imageView;
        this.balanceSeekBar = balanceView;
        this.balanceValueTv = textView;
        this.codecExternalIv = imageView2;
        this.codecExternalSwitch = r6;
        this.codecExternalSwitchTv = textView2;
        this.codecExternalTv = textView3;
        this.dacGainDivider = view;
        this.dacGainIv = imageView3;
        this.dacGainSwitch = r11;
        this.dacGainTipsTv = textView4;
        this.dacGainTv = textView5;
        this.digitalFilter1Rg = radioButton;
        this.digitalFilter2Rg = radioButton2;
        this.digitalFilter3Rg = radioButton3;
        this.digitalFilter4Rg = radioButton4;
        this.digitalFilter5Rg = radioButton5;
        this.digitalFilterIv = imageView4;
        this.digitalFilterRg = radioGroup;
        this.digitalFilterValueTv = textView6;
        this.eightDiv = constraintLayout;
        this.firstDiv = view2;
        this.firstDiv0 = view3;
        this.fiveDiv = view4;
        this.fpgaVersionTv = textView7;
        this.fragmentContainer = frameLayout2;
        this.gainIv = imageView5;
        this.gainThirdSwitchBar = thirdSwitchBar;
        this.gainTipsTv = textView8;
        this.gainTv = textView9;
        this.icVolumeImage = imageView6;
        this.language0Rg = radioButton6;
        this.language1Rg = radioButton7;
        this.language2Rg = radioButton8;
        this.languageDiv = view5;
        this.languageIv = imageView7;
        this.languageRg = radioGroup2;
        this.manufacturerTv = textView10;
        this.micModeIv = imageView8;
        this.micModeSwitch = r41;
        this.micModeSwitchTv = textView11;
        this.micModeTv = textView12;
        this.productIdIv = imageView9;
        this.productNameTv = textView13;
        this.screemTimeIv = imageView10;
        this.screenRotateIv = imageView11;
        this.screenRotateSwitch = r48;
        this.screenRotateSwitchTv = textView14;
        this.screenRotateTv = textView15;
        this.screenTime0Rg = radioButton9;
        this.screenTime1Rg = radioButton10;
        this.screenTime2Rg = radioButton11;
        this.screenTime3Rg = radioButton12;
        this.screenTimeRg = radioGroup3;
        this.secondDiv0 = view6;
        this.settingsDigitalFilterLayout = relativeLayout;
        this.settingsLanguageLayout = relativeLayout2;
        this.settingsScreenTimeLayout = relativeLayout3;
        this.sevenDiv = view7;
        this.sixDiv = view8;
        this.textView = textView16;
        this.thirdDiv = view9;
        this.volumeSeekBar = seekBar;
        this.volumeValueTv = textView17;
    }

    @NonNull
    public static FragmentD18Binding bind(@NonNull View view) {
        int i = R.id.balanceIv;
        ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, R.id.balanceIv);
        if (imageView != null) {
            i = R.id.balanceSeekBar;
            BalanceView balanceView = (BalanceView) ViewBindings.findChildViewById(view, R.id.balanceSeekBar);
            if (balanceView != null) {
                i = R.id.balanceValueTv;
                TextView textView = (TextView) ViewBindings.findChildViewById(view, R.id.balanceValueTv);
                if (textView != null) {
                    i = R.id.codecExternalIv;
                    ImageView imageView2 = (ImageView) ViewBindings.findChildViewById(view, R.id.codecExternalIv);
                    if (imageView2 != null) {
                        i = R.id.codecExternalSwitch;
                        Switch r9 = (Switch) ViewBindings.findChildViewById(view, R.id.codecExternalSwitch);
                        if (r9 != null) {
                            i = R.id.codecExternalSwitchTv;
                            TextView textView2 = (TextView) ViewBindings.findChildViewById(view, R.id.codecExternalSwitchTv);
                            if (textView2 != null) {
                                i = R.id.codecExternalTv;
                                TextView textView3 = (TextView) ViewBindings.findChildViewById(view, R.id.codecExternalTv);
                                if (textView3 != null) {
                                    i = R.id.dacGainDivider;
                                    View findChildViewById = ViewBindings.findChildViewById(view, R.id.dacGainDivider);
                                    if (findChildViewById != null) {
                                        i = R.id.dacGainIv;
                                        ImageView imageView3 = (ImageView) ViewBindings.findChildViewById(view, R.id.dacGainIv);
                                        if (imageView3 != null) {
                                            i = R.id.dacGainSwitch;
                                            Switch r14 = (Switch) ViewBindings.findChildViewById(view, R.id.dacGainSwitch);
                                            if (r14 != null) {
                                                i = R.id.dacGainTipsTv;
                                                TextView textView4 = (TextView) ViewBindings.findChildViewById(view, R.id.dacGainTipsTv);
                                                if (textView4 != null) {
                                                    i = R.id.dacGainTv;
                                                    TextView textView5 = (TextView) ViewBindings.findChildViewById(view, R.id.dacGainTv);
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
                                                                            ImageView imageView4 = (ImageView) ViewBindings.findChildViewById(view, R.id.digital_filter_iv);
                                                                            if (imageView4 != null) {
                                                                                i = R.id.digitalFilterRg;
                                                                                RadioGroup radioGroup = (RadioGroup) ViewBindings.findChildViewById(view, R.id.digitalFilterRg);
                                                                                if (radioGroup != null) {
                                                                                    i = R.id.digitalFilterValueTv;
                                                                                    TextView textView6 = (TextView) ViewBindings.findChildViewById(view, R.id.digitalFilterValueTv);
                                                                                    if (textView6 != null) {
                                                                                        i = R.id.eightDiv;
                                                                                        ConstraintLayout constraintLayout = (ConstraintLayout) ViewBindings.findChildViewById(view, R.id.eightDiv);
                                                                                        if (constraintLayout != null) {
                                                                                            i = R.id.firstDiv;
                                                                                            View findChildViewById2 = ViewBindings.findChildViewById(view, R.id.firstDiv);
                                                                                            if (findChildViewById2 != null) {
                                                                                                i = R.id.firstDiv0;
                                                                                                View findChildViewById3 = ViewBindings.findChildViewById(view, R.id.firstDiv0);
                                                                                                if (findChildViewById3 != null) {
                                                                                                    i = R.id.fiveDiv;
                                                                                                    View findChildViewById4 = ViewBindings.findChildViewById(view, R.id.fiveDiv);
                                                                                                    if (findChildViewById4 != null) {
                                                                                                        i = R.id.fpgaVersionTv;
                                                                                                        TextView textView7 = (TextView) ViewBindings.findChildViewById(view, R.id.fpgaVersionTv);
                                                                                                        if (textView7 != null) {
                                                                                                            i = R.id.fragment_container;
                                                                                                            FrameLayout frameLayout = (FrameLayout) ViewBindings.findChildViewById(view, R.id.fragment_container);
                                                                                                            if (frameLayout != null) {
                                                                                                                i = R.id.gainIv;
                                                                                                                ImageView imageView5 = (ImageView) ViewBindings.findChildViewById(view, R.id.gainIv);
                                                                                                                if (imageView5 != null) {
                                                                                                                    i = R.id.gainThirdSwitchBar;
                                                                                                                    ThirdSwitchBar thirdSwitchBar = (ThirdSwitchBar) ViewBindings.findChildViewById(view, R.id.gainThirdSwitchBar);
                                                                                                                    if (thirdSwitchBar != null) {
                                                                                                                        i = R.id.gainTipsTv;
                                                                                                                        TextView textView8 = (TextView) ViewBindings.findChildViewById(view, R.id.gainTipsTv);
                                                                                                                        if (textView8 != null) {
                                                                                                                            i = R.id.gainTv;
                                                                                                                            TextView textView9 = (TextView) ViewBindings.findChildViewById(view, R.id.gainTv);
                                                                                                                            if (textView9 != null) {
                                                                                                                                i = R.id.icVolumeImage;
                                                                                                                                ImageView imageView6 = (ImageView) ViewBindings.findChildViewById(view, R.id.icVolumeImage);
                                                                                                                                if (imageView6 != null) {
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
                                                                                                                                                View findChildViewById5 = ViewBindings.findChildViewById(view, R.id.languageDiv);
                                                                                                                                                if (findChildViewById5 != null) {
                                                                                                                                                    i = R.id.languageIv;
                                                                                                                                                    ImageView imageView7 = (ImageView) ViewBindings.findChildViewById(view, R.id.languageIv);
                                                                                                                                                    if (imageView7 != null) {
                                                                                                                                                        i = R.id.languageRg;
                                                                                                                                                        RadioGroup radioGroup2 = (RadioGroup) ViewBindings.findChildViewById(view, R.id.languageRg);
                                                                                                                                                        if (radioGroup2 != null) {
                                                                                                                                                            i = R.id.manufacturerTv;
                                                                                                                                                            TextView textView10 = (TextView) ViewBindings.findChildViewById(view, R.id.manufacturerTv);
                                                                                                                                                            if (textView10 != null) {
                                                                                                                                                                i = R.id.micModeIv;
                                                                                                                                                                ImageView imageView8 = (ImageView) ViewBindings.findChildViewById(view, R.id.micModeIv);
                                                                                                                                                                if (imageView8 != null) {
                                                                                                                                                                    i = R.id.micModeSwitch;
                                                                                                                                                                    Switch r44 = (Switch) ViewBindings.findChildViewById(view, R.id.micModeSwitch);
                                                                                                                                                                    if (r44 != null) {
                                                                                                                                                                        i = R.id.micModeSwitchTv;
                                                                                                                                                                        TextView textView11 = (TextView) ViewBindings.findChildViewById(view, R.id.micModeSwitchTv);
                                                                                                                                                                        if (textView11 != null) {
                                                                                                                                                                            i = R.id.micModeTv;
                                                                                                                                                                            TextView textView12 = (TextView) ViewBindings.findChildViewById(view, R.id.micModeTv);
                                                                                                                                                                            if (textView12 != null) {
                                                                                                                                                                                i = R.id.productIdIv;
                                                                                                                                                                                ImageView imageView9 = (ImageView) ViewBindings.findChildViewById(view, R.id.productIdIv);
                                                                                                                                                                                if (imageView9 != null) {
                                                                                                                                                                                    i = R.id.productNameTv;
                                                                                                                                                                                    TextView textView13 = (TextView) ViewBindings.findChildViewById(view, R.id.productNameTv);
                                                                                                                                                                                    if (textView13 != null) {
                                                                                                                                                                                        i = R.id.screemTimeIv;
                                                                                                                                                                                        ImageView imageView10 = (ImageView) ViewBindings.findChildViewById(view, R.id.screemTimeIv);
                                                                                                                                                                                        if (imageView10 != null) {
                                                                                                                                                                                            i = R.id.screenRotateIv;
                                                                                                                                                                                            ImageView imageView11 = (ImageView) ViewBindings.findChildViewById(view, R.id.screenRotateIv);
                                                                                                                                                                                            if (imageView11 != null) {
                                                                                                                                                                                                i = R.id.screenRotateSwitch;
                                                                                                                                                                                                Switch r51 = (Switch) ViewBindings.findChildViewById(view, R.id.screenRotateSwitch);
                                                                                                                                                                                                if (r51 != null) {
                                                                                                                                                                                                    i = R.id.screenRotateSwitchTv;
                                                                                                                                                                                                    TextView textView14 = (TextView) ViewBindings.findChildViewById(view, R.id.screenRotateSwitchTv);
                                                                                                                                                                                                    if (textView14 != null) {
                                                                                                                                                                                                        i = R.id.screenRotateTv;
                                                                                                                                                                                                        TextView textView15 = (TextView) ViewBindings.findChildViewById(view, R.id.screenRotateTv);
                                                                                                                                                                                                        if (textView15 != null) {
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
                                                                                                                                                                                                                                View findChildViewById6 = ViewBindings.findChildViewById(view, R.id.secondDiv0);
                                                                                                                                                                                                                                if (findChildViewById6 != null) {
                                                                                                                                                                                                                                    i = R.id.settings_digital_filter_layout;
                                                                                                                                                                                                                                    RelativeLayout relativeLayout = (RelativeLayout) ViewBindings.findChildViewById(view, R.id.settings_digital_filter_layout);
                                                                                                                                                                                                                                    if (relativeLayout != null) {
                                                                                                                                                                                                                                        i = R.id.settings_language_layout;
                                                                                                                                                                                                                                        RelativeLayout relativeLayout2 = (RelativeLayout) ViewBindings.findChildViewById(view, R.id.settings_language_layout);
                                                                                                                                                                                                                                        if (relativeLayout2 != null) {
                                                                                                                                                                                                                                            i = R.id.settings_screen_time_layout;
                                                                                                                                                                                                                                            RelativeLayout relativeLayout3 = (RelativeLayout) ViewBindings.findChildViewById(view, R.id.settings_screen_time_layout);
                                                                                                                                                                                                                                            if (relativeLayout3 != null) {
                                                                                                                                                                                                                                                i = R.id.sevenDiv;
                                                                                                                                                                                                                                                View findChildViewById7 = ViewBindings.findChildViewById(view, R.id.sevenDiv);
                                                                                                                                                                                                                                                if (findChildViewById7 != null) {
                                                                                                                                                                                                                                                    i = R.id.sixDiv;
                                                                                                                                                                                                                                                    View findChildViewById8 = ViewBindings.findChildViewById(view, R.id.sixDiv);
                                                                                                                                                                                                                                                    if (findChildViewById8 != null) {
                                                                                                                                                                                                                                                        i = R.id.textView;
                                                                                                                                                                                                                                                        TextView textView16 = (TextView) ViewBindings.findChildViewById(view, R.id.textView);
                                                                                                                                                                                                                                                        if (textView16 != null) {
                                                                                                                                                                                                                                                            i = R.id.thirdDiv;
                                                                                                                                                                                                                                                            View findChildViewById9 = ViewBindings.findChildViewById(view, R.id.thirdDiv);
                                                                                                                                                                                                                                                            if (findChildViewById9 != null) {
                                                                                                                                                                                                                                                                i = R.id.volumeSeekBar;
                                                                                                                                                                                                                                                                SeekBar seekBar = (SeekBar) ViewBindings.findChildViewById(view, R.id.volumeSeekBar);
                                                                                                                                                                                                                                                                if (seekBar != null) {
                                                                                                                                                                                                                                                                    i = R.id.volumeValueTv;
                                                                                                                                                                                                                                                                    TextView textView17 = (TextView) ViewBindings.findChildViewById(view, R.id.volumeValueTv);
                                                                                                                                                                                                                                                                    if (textView17 != null) {
                                                                                                                                                                                                                                                                        return new FragmentD18Binding((FrameLayout) view, imageView, balanceView, textView, imageView2, r9, textView2, textView3, findChildViewById, imageView3, r14, textView4, textView5, radioButton, radioButton2, radioButton3, radioButton4, radioButton5, imageView4, radioGroup, textView6, constraintLayout, findChildViewById2, findChildViewById3, findChildViewById4, textView7, frameLayout, imageView5, thirdSwitchBar, textView8, textView9, imageView6, radioButton6, radioButton7, radioButton8, findChildViewById5, imageView7, radioGroup2, textView10, imageView8, r44, textView11, textView12, imageView9, textView13, imageView10, imageView11, r51, textView14, textView15, radioButton9, radioButton10, radioButton11, radioButton12, radioGroup3, findChildViewById6, relativeLayout, relativeLayout2, relativeLayout3, findChildViewById7, findChildViewById8, textView16, findChildViewById9, seekBar, textView17);
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
    public static FragmentD18Binding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static FragmentD18Binding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z) {
        View inflate = layoutInflater.inflate(R.layout.fragment_d18, viewGroup, false);
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
