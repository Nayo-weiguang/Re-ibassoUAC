package com.ibasso.volume.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
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
public final class FragmentDc07proBinding implements ViewBinding {
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
    public final ImageView gainIv;
    @NonNull
    public final TextView gainTipsTv;
    @NonNull
    public final TextView gainTv;
    @NonNull
    public final ThirdSwitchBar gainhirdSwitchBar;
    @NonNull
    public final ImageView hpFilterIv;
    @NonNull
    public final Switch hpFilterSwitch;
    @NonNull
    public final TextView hpFilterSwitchTv;
    @NonNull
    public final TextView hpFilterTv;
    @NonNull
    public final ImageView icVolumeImage;
    @NonNull
    public final TextView manufacturerTv;
    @NonNull
    public final ImageView pcmVolumeTrimKeyIv;
    @NonNull
    public final Switch pcmVolumeTrimKeySwitch;
    @NonNull
    public final TextView pcmVolumeTrimKeySwitchTv;
    @NonNull
    public final TextView pcmVolumeTrimKeyTv;
    @NonNull
    public final ImageView productIdIv;
    @NonNull
    public final TextView productNameTv;
    @NonNull
    private final ScrollView rootView;
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
    public final View secondDiv;
    @NonNull
    public final View secondDiv0;
    @NonNull
    public final RelativeLayout settingsDigitalFilterLayout;
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
    public final ImageView volumeDownBt;
    @NonNull
    public final SeekBar volumeSeekBar;
    @NonNull
    public final ImageView volumeUpBt;
    @NonNull
    public final TextView volumeValueTv;

    private FragmentDc07proBinding(@NonNull ScrollView scrollView, @NonNull ImageView imageView, @NonNull BalanceView balanceView, @NonNull TextView textView, @NonNull ImageView imageView2, @NonNull Switch r6, @NonNull TextView textView2, @NonNull TextView textView3, @NonNull RadioButton radioButton, @NonNull RadioButton radioButton2, @NonNull RadioButton radioButton3, @NonNull RadioButton radioButton4, @NonNull RadioButton radioButton5, @NonNull ImageView imageView3, @NonNull RadioGroup radioGroup, @NonNull TextView textView4, @NonNull ConstraintLayout constraintLayout, @NonNull View view, @NonNull View view2, @NonNull View view3, @NonNull TextView textView5, @NonNull ImageView imageView4, @NonNull TextView textView6, @NonNull TextView textView7, @NonNull ThirdSwitchBar thirdSwitchBar, @NonNull ImageView imageView5, @NonNull Switch r27, @NonNull TextView textView8, @NonNull TextView textView9, @NonNull ImageView imageView6, @NonNull TextView textView10, @NonNull ImageView imageView7, @NonNull Switch r33, @NonNull TextView textView11, @NonNull TextView textView12, @NonNull ImageView imageView8, @NonNull TextView textView13, @NonNull ImageView imageView9, @NonNull ImageView imageView10, @NonNull Switch r40, @NonNull TextView textView14, @NonNull TextView textView15, @NonNull RadioButton radioButton6, @NonNull RadioButton radioButton7, @NonNull RadioButton radioButton8, @NonNull RadioButton radioButton9, @NonNull RadioGroup radioGroup2, @NonNull View view4, @NonNull View view5, @NonNull RelativeLayout relativeLayout, @NonNull RelativeLayout relativeLayout2, @NonNull View view6, @NonNull View view7, @NonNull TextView textView16, @NonNull View view8, @NonNull ImageView imageView11, @NonNull SeekBar seekBar, @NonNull ImageView imageView12, @NonNull TextView textView17) {
        this.rootView = scrollView;
        this.balanceIv = imageView;
        this.balanceSeekBar = balanceView;
        this.balanceValueTv = textView;
        this.codecExternalIv = imageView2;
        this.codecExternalSwitch = r6;
        this.codecExternalSwitchTv = textView2;
        this.codecExternalTv = textView3;
        this.digitalFilter1Rg = radioButton;
        this.digitalFilter2Rg = radioButton2;
        this.digitalFilter3Rg = radioButton3;
        this.digitalFilter4Rg = radioButton4;
        this.digitalFilter5Rg = radioButton5;
        this.digitalFilterIv = imageView3;
        this.digitalFilterRg = radioGroup;
        this.digitalFilterValueTv = textView4;
        this.eightDiv = constraintLayout;
        this.firstDiv = view;
        this.firstDiv0 = view2;
        this.fiveDiv = view3;
        this.fpgaVersionTv = textView5;
        this.gainIv = imageView4;
        this.gainTipsTv = textView6;
        this.gainTv = textView7;
        this.gainhirdSwitchBar = thirdSwitchBar;
        this.hpFilterIv = imageView5;
        this.hpFilterSwitch = r27;
        this.hpFilterSwitchTv = textView8;
        this.hpFilterTv = textView9;
        this.icVolumeImage = imageView6;
        this.manufacturerTv = textView10;
        this.pcmVolumeTrimKeyIv = imageView7;
        this.pcmVolumeTrimKeySwitch = r33;
        this.pcmVolumeTrimKeySwitchTv = textView11;
        this.pcmVolumeTrimKeyTv = textView12;
        this.productIdIv = imageView8;
        this.productNameTv = textView13;
        this.screemTimeIv = imageView9;
        this.screenRotateIv = imageView10;
        this.screenRotateSwitch = r40;
        this.screenRotateSwitchTv = textView14;
        this.screenRotateTv = textView15;
        this.screenTime0Rg = radioButton6;
        this.screenTime1Rg = radioButton7;
        this.screenTime2Rg = radioButton8;
        this.screenTime3Rg = radioButton9;
        this.screenTimeRg = radioGroup2;
        this.secondDiv = view4;
        this.secondDiv0 = view5;
        this.settingsDigitalFilterLayout = relativeLayout;
        this.settingsScreenTimeLayout = relativeLayout2;
        this.sevenDiv = view6;
        this.sixDiv = view7;
        this.textView = textView16;
        this.thirdDiv = view8;
        this.volumeDownBt = imageView11;
        this.volumeSeekBar = seekBar;
        this.volumeUpBt = imageView12;
        this.volumeValueTv = textView17;
    }

    @NonNull
    public static FragmentDc07proBinding bind(@NonNull View view) {
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
                                                        ImageView imageView3 = (ImageView) ViewBindings.findChildViewById(view, R.id.digital_filter_iv);
                                                        if (imageView3 != null) {
                                                            i = R.id.digitalFilterRg;
                                                            RadioGroup radioGroup = (RadioGroup) ViewBindings.findChildViewById(view, R.id.digitalFilterRg);
                                                            if (radioGroup != null) {
                                                                i = R.id.digitalFilterValueTv;
                                                                TextView textView4 = (TextView) ViewBindings.findChildViewById(view, R.id.digitalFilterValueTv);
                                                                if (textView4 != null) {
                                                                    i = R.id.eightDiv;
                                                                    ConstraintLayout constraintLayout = (ConstraintLayout) ViewBindings.findChildViewById(view, R.id.eightDiv);
                                                                    if (constraintLayout != null) {
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
                                                                                    TextView textView5 = (TextView) ViewBindings.findChildViewById(view, R.id.fpgaVersionTv);
                                                                                    if (textView5 != null) {
                                                                                        i = R.id.gainIv;
                                                                                        ImageView imageView4 = (ImageView) ViewBindings.findChildViewById(view, R.id.gainIv);
                                                                                        if (imageView4 != null) {
                                                                                            i = R.id.gainTipsTv;
                                                                                            TextView textView6 = (TextView) ViewBindings.findChildViewById(view, R.id.gainTipsTv);
                                                                                            if (textView6 != null) {
                                                                                                i = R.id.gainTv;
                                                                                                TextView textView7 = (TextView) ViewBindings.findChildViewById(view, R.id.gainTv);
                                                                                                if (textView7 != null) {
                                                                                                    i = R.id.gainhirdSwitchBar;
                                                                                                    ThirdSwitchBar thirdSwitchBar = (ThirdSwitchBar) ViewBindings.findChildViewById(view, R.id.gainhirdSwitchBar);
                                                                                                    if (thirdSwitchBar != null) {
                                                                                                        i = R.id.hpFilterIv;
                                                                                                        ImageView imageView5 = (ImageView) ViewBindings.findChildViewById(view, R.id.hpFilterIv);
                                                                                                        if (imageView5 != null) {
                                                                                                            i = R.id.hpFilterSwitch;
                                                                                                            Switch r30 = (Switch) ViewBindings.findChildViewById(view, R.id.hpFilterSwitch);
                                                                                                            if (r30 != null) {
                                                                                                                i = R.id.hpFilterSwitchTv;
                                                                                                                TextView textView8 = (TextView) ViewBindings.findChildViewById(view, R.id.hpFilterSwitchTv);
                                                                                                                if (textView8 != null) {
                                                                                                                    i = R.id.hpFilterTv;
                                                                                                                    TextView textView9 = (TextView) ViewBindings.findChildViewById(view, R.id.hpFilterTv);
                                                                                                                    if (textView9 != null) {
                                                                                                                        i = R.id.icVolumeImage;
                                                                                                                        ImageView imageView6 = (ImageView) ViewBindings.findChildViewById(view, R.id.icVolumeImage);
                                                                                                                        if (imageView6 != null) {
                                                                                                                            i = R.id.manufacturerTv;
                                                                                                                            TextView textView10 = (TextView) ViewBindings.findChildViewById(view, R.id.manufacturerTv);
                                                                                                                            if (textView10 != null) {
                                                                                                                                i = R.id.pcmVolumeTrimKeyIv;
                                                                                                                                ImageView imageView7 = (ImageView) ViewBindings.findChildViewById(view, R.id.pcmVolumeTrimKeyIv);
                                                                                                                                if (imageView7 != null) {
                                                                                                                                    i = R.id.pcmVolumeTrimKeySwitch;
                                                                                                                                    Switch r36 = (Switch) ViewBindings.findChildViewById(view, R.id.pcmVolumeTrimKeySwitch);
                                                                                                                                    if (r36 != null) {
                                                                                                                                        i = R.id.pcmVolumeTrimKeySwitchTv;
                                                                                                                                        TextView textView11 = (TextView) ViewBindings.findChildViewById(view, R.id.pcmVolumeTrimKeySwitchTv);
                                                                                                                                        if (textView11 != null) {
                                                                                                                                            i = R.id.pcmVolumeTrimKeyTv;
                                                                                                                                            TextView textView12 = (TextView) ViewBindings.findChildViewById(view, R.id.pcmVolumeTrimKeyTv);
                                                                                                                                            if (textView12 != null) {
                                                                                                                                                i = R.id.productIdIv;
                                                                                                                                                ImageView imageView8 = (ImageView) ViewBindings.findChildViewById(view, R.id.productIdIv);
                                                                                                                                                if (imageView8 != null) {
                                                                                                                                                    i = R.id.productNameTv;
                                                                                                                                                    TextView textView13 = (TextView) ViewBindings.findChildViewById(view, R.id.productNameTv);
                                                                                                                                                    if (textView13 != null) {
                                                                                                                                                        i = R.id.screemTimeIv;
                                                                                                                                                        ImageView imageView9 = (ImageView) ViewBindings.findChildViewById(view, R.id.screemTimeIv);
                                                                                                                                                        if (imageView9 != null) {
                                                                                                                                                            i = R.id.screenRotateIv;
                                                                                                                                                            ImageView imageView10 = (ImageView) ViewBindings.findChildViewById(view, R.id.screenRotateIv);
                                                                                                                                                            if (imageView10 != null) {
                                                                                                                                                                i = R.id.screenRotateSwitch;
                                                                                                                                                                Switch r43 = (Switch) ViewBindings.findChildViewById(view, R.id.screenRotateSwitch);
                                                                                                                                                                if (r43 != null) {
                                                                                                                                                                    i = R.id.screenRotateSwitchTv;
                                                                                                                                                                    TextView textView14 = (TextView) ViewBindings.findChildViewById(view, R.id.screenRotateSwitchTv);
                                                                                                                                                                    if (textView14 != null) {
                                                                                                                                                                        i = R.id.screenRotateTv;
                                                                                                                                                                        TextView textView15 = (TextView) ViewBindings.findChildViewById(view, R.id.screenRotateTv);
                                                                                                                                                                        if (textView15 != null) {
                                                                                                                                                                            i = R.id.screenTime0Rg;
                                                                                                                                                                            RadioButton radioButton6 = (RadioButton) ViewBindings.findChildViewById(view, R.id.screenTime0Rg);
                                                                                                                                                                            if (radioButton6 != null) {
                                                                                                                                                                                i = R.id.screenTime1Rg;
                                                                                                                                                                                RadioButton radioButton7 = (RadioButton) ViewBindings.findChildViewById(view, R.id.screenTime1Rg);
                                                                                                                                                                                if (radioButton7 != null) {
                                                                                                                                                                                    i = R.id.screenTime2Rg;
                                                                                                                                                                                    RadioButton radioButton8 = (RadioButton) ViewBindings.findChildViewById(view, R.id.screenTime2Rg);
                                                                                                                                                                                    if (radioButton8 != null) {
                                                                                                                                                                                        i = R.id.screenTime3Rg;
                                                                                                                                                                                        RadioButton radioButton9 = (RadioButton) ViewBindings.findChildViewById(view, R.id.screenTime3Rg);
                                                                                                                                                                                        if (radioButton9 != null) {
                                                                                                                                                                                            i = R.id.screenTimeRg;
                                                                                                                                                                                            RadioGroup radioGroup2 = (RadioGroup) ViewBindings.findChildViewById(view, R.id.screenTimeRg);
                                                                                                                                                                                            if (radioGroup2 != null) {
                                                                                                                                                                                                i = R.id.secondDiv;
                                                                                                                                                                                                View findChildViewById4 = ViewBindings.findChildViewById(view, R.id.secondDiv);
                                                                                                                                                                                                if (findChildViewById4 != null) {
                                                                                                                                                                                                    i = R.id.secondDiv0;
                                                                                                                                                                                                    View findChildViewById5 = ViewBindings.findChildViewById(view, R.id.secondDiv0);
                                                                                                                                                                                                    if (findChildViewById5 != null) {
                                                                                                                                                                                                        i = R.id.settings_digital_filter_layout;
                                                                                                                                                                                                        RelativeLayout relativeLayout = (RelativeLayout) ViewBindings.findChildViewById(view, R.id.settings_digital_filter_layout);
                                                                                                                                                                                                        if (relativeLayout != null) {
                                                                                                                                                                                                            i = R.id.settings_screen_time_layout;
                                                                                                                                                                                                            RelativeLayout relativeLayout2 = (RelativeLayout) ViewBindings.findChildViewById(view, R.id.settings_screen_time_layout);
                                                                                                                                                                                                            if (relativeLayout2 != null) {
                                                                                                                                                                                                                i = R.id.sevenDiv;
                                                                                                                                                                                                                View findChildViewById6 = ViewBindings.findChildViewById(view, R.id.sevenDiv);
                                                                                                                                                                                                                if (findChildViewById6 != null) {
                                                                                                                                                                                                                    i = R.id.sixDiv;
                                                                                                                                                                                                                    View findChildViewById7 = ViewBindings.findChildViewById(view, R.id.sixDiv);
                                                                                                                                                                                                                    if (findChildViewById7 != null) {
                                                                                                                                                                                                                        i = R.id.textView;
                                                                                                                                                                                                                        TextView textView16 = (TextView) ViewBindings.findChildViewById(view, R.id.textView);
                                                                                                                                                                                                                        if (textView16 != null) {
                                                                                                                                                                                                                            i = R.id.thirdDiv;
                                                                                                                                                                                                                            View findChildViewById8 = ViewBindings.findChildViewById(view, R.id.thirdDiv);
                                                                                                                                                                                                                            if (findChildViewById8 != null) {
                                                                                                                                                                                                                                i = R.id.volumeDownBt;
                                                                                                                                                                                                                                ImageView imageView11 = (ImageView) ViewBindings.findChildViewById(view, R.id.volumeDownBt);
                                                                                                                                                                                                                                if (imageView11 != null) {
                                                                                                                                                                                                                                    i = R.id.volumeSeekBar;
                                                                                                                                                                                                                                    SeekBar seekBar = (SeekBar) ViewBindings.findChildViewById(view, R.id.volumeSeekBar);
                                                                                                                                                                                                                                    if (seekBar != null) {
                                                                                                                                                                                                                                        i = R.id.volumeUpBt;
                                                                                                                                                                                                                                        ImageView imageView12 = (ImageView) ViewBindings.findChildViewById(view, R.id.volumeUpBt);
                                                                                                                                                                                                                                        if (imageView12 != null) {
                                                                                                                                                                                                                                            i = R.id.volumeValueTv;
                                                                                                                                                                                                                                            TextView textView17 = (TextView) ViewBindings.findChildViewById(view, R.id.volumeValueTv);
                                                                                                                                                                                                                                            if (textView17 != null) {
                                                                                                                                                                                                                                                return new FragmentDc07proBinding((ScrollView) view, imageView, balanceView, textView, imageView2, r9, textView2, textView3, radioButton, radioButton2, radioButton3, radioButton4, radioButton5, imageView3, radioGroup, textView4, constraintLayout, findChildViewById, findChildViewById2, findChildViewById3, textView5, imageView4, textView6, textView7, thirdSwitchBar, imageView5, r30, textView8, textView9, imageView6, textView10, imageView7, r36, textView11, textView12, imageView8, textView13, imageView9, imageView10, r43, textView14, textView15, radioButton6, radioButton7, radioButton8, radioButton9, radioGroup2, findChildViewById4, findChildViewById5, relativeLayout, relativeLayout2, findChildViewById6, findChildViewById7, textView16, findChildViewById8, imageView11, seekBar, imageView12, textView17);
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
    public static FragmentDc07proBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static FragmentDc07proBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z) {
        View inflate = layoutInflater.inflate(R.layout.fragment_dc07pro, viewGroup, false);
        if (z) {
            viewGroup.addView(inflate);
        }
        return bind(inflate);
    }

    @Override
    @NonNull
    public ScrollView getRoot() {
        return this.rootView;
    }
}
