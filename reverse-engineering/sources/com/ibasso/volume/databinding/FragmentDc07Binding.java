package com.ibasso.volume.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.ibasso.music.widgets.ThirdSwitchBar;
import com.ibasso.volume.R;
public final class FragmentDc07Binding implements ViewBinding {
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
    public final ImageView digitalFilterIv;
    @NonNull
    public final RadioGroup digitalFilterRg;
    @NonNull
    public final TextView digitalFilterValueTv;
    @NonNull
    public final ThirdSwitchBar dsdFilterThirdSwitchBar;
    @NonNull
    public final ConstraintLayout eightDiv;
    @NonNull
    public final View firstDiv;
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
    public final TextView manufacturerTv;
    @NonNull
    public final ImageView outputIv;
    @NonNull
    public final Switch outputSwitch;
    @NonNull
    public final TextView outputSwitchTv;
    @NonNull
    public final TextView outputTv;
    @NonNull
    public final RadioButton pcmTrim0Rg;
    @NonNull
    public final RadioButton pcmTrim1Rg;
    @NonNull
    public final RadioButton pcmTrim2Rg;
    @NonNull
    public final RadioButton pcmTrim3Rg;
    @NonNull
    public final ImageView pcmTrimIv;
    @NonNull
    public final RadioGroup pcmTrimRg;
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
    public final View secondDiv;
    @NonNull
    public final RelativeLayout settingsDigitalFilterLayout;
    @NonNull
    public final RelativeLayout settingsPcmTrimLayout;
    @NonNull
    public final View sevenDiv;
    @NonNull
    public final View sixDiv;
    @NonNull
    public final View thirdDiv;

    private FragmentDc07Binding(@NonNull ScrollView scrollView, @NonNull ImageView imageView, @NonNull Switch r3, @NonNull TextView textView, @NonNull TextView textView2, @NonNull RadioButton radioButton, @NonNull RadioButton radioButton2, @NonNull ImageView imageView2, @NonNull RadioGroup radioGroup, @NonNull TextView textView3, @NonNull ThirdSwitchBar thirdSwitchBar, @NonNull ConstraintLayout constraintLayout, @NonNull View view, @NonNull View view2, @NonNull TextView textView4, @NonNull ImageView imageView3, @NonNull TextView textView5, @NonNull TextView textView6, @NonNull TextView textView7, @NonNull ImageView imageView4, @NonNull Switch r21, @NonNull TextView textView8, @NonNull TextView textView9, @NonNull RadioButton radioButton3, @NonNull RadioButton radioButton4, @NonNull RadioButton radioButton5, @NonNull RadioButton radioButton6, @NonNull ImageView imageView5, @NonNull RadioGroup radioGroup2, @NonNull ImageView imageView6, @NonNull Switch r31, @NonNull TextView textView10, @NonNull TextView textView11, @NonNull ImageView imageView7, @NonNull TextView textView12, @NonNull View view3, @NonNull RelativeLayout relativeLayout, @NonNull RelativeLayout relativeLayout2, @NonNull View view4, @NonNull View view5, @NonNull View view6) {
        this.rootView = scrollView;
        this.codecExternalIv = imageView;
        this.codecExternalSwitch = r3;
        this.codecExternalSwitchTv = textView;
        this.codecExternalTv = textView2;
        this.digitalFilter1Rg = radioButton;
        this.digitalFilter2Rg = radioButton2;
        this.digitalFilterIv = imageView2;
        this.digitalFilterRg = radioGroup;
        this.digitalFilterValueTv = textView3;
        this.dsdFilterThirdSwitchBar = thirdSwitchBar;
        this.eightDiv = constraintLayout;
        this.firstDiv = view;
        this.fiveDiv = view2;
        this.fpgaVersionTv = textView4;
        this.gainIv = imageView3;
        this.gainTipsTv = textView5;
        this.gainTv = textView6;
        this.manufacturerTv = textView7;
        this.outputIv = imageView4;
        this.outputSwitch = r21;
        this.outputSwitchTv = textView8;
        this.outputTv = textView9;
        this.pcmTrim0Rg = radioButton3;
        this.pcmTrim1Rg = radioButton4;
        this.pcmTrim2Rg = radioButton5;
        this.pcmTrim3Rg = radioButton6;
        this.pcmTrimIv = imageView5;
        this.pcmTrimRg = radioGroup2;
        this.pcmVolumeTrimKeyIv = imageView6;
        this.pcmVolumeTrimKeySwitch = r31;
        this.pcmVolumeTrimKeySwitchTv = textView10;
        this.pcmVolumeTrimKeyTv = textView11;
        this.productIdIv = imageView7;
        this.productNameTv = textView12;
        this.secondDiv = view3;
        this.settingsDigitalFilterLayout = relativeLayout;
        this.settingsPcmTrimLayout = relativeLayout2;
        this.sevenDiv = view4;
        this.sixDiv = view5;
        this.thirdDiv = view6;
    }

    @NonNull
    public static FragmentDc07Binding bind(@NonNull View view) {
        int i = R.id.codecExternalIv;
        ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, R.id.codecExternalIv);
        if (imageView != null) {
            i = R.id.codecExternalSwitch;
            Switch r6 = (Switch) ViewBindings.findChildViewById(view, R.id.codecExternalSwitch);
            if (r6 != null) {
                i = R.id.codecExternalSwitchTv;
                TextView textView = (TextView) ViewBindings.findChildViewById(view, R.id.codecExternalSwitchTv);
                if (textView != null) {
                    i = R.id.codecExternalTv;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(view, R.id.codecExternalTv);
                    if (textView2 != null) {
                        i = R.id.digitalFilter1Rg;
                        RadioButton radioButton = (RadioButton) ViewBindings.findChildViewById(view, R.id.digitalFilter1Rg);
                        if (radioButton != null) {
                            i = R.id.digitalFilter2Rg;
                            RadioButton radioButton2 = (RadioButton) ViewBindings.findChildViewById(view, R.id.digitalFilter2Rg);
                            if (radioButton2 != null) {
                                i = R.id.digital_filter_iv;
                                ImageView imageView2 = (ImageView) ViewBindings.findChildViewById(view, R.id.digital_filter_iv);
                                if (imageView2 != null) {
                                    i = R.id.digitalFilterRg;
                                    RadioGroup radioGroup = (RadioGroup) ViewBindings.findChildViewById(view, R.id.digitalFilterRg);
                                    if (radioGroup != null) {
                                        i = R.id.digitalFilterValueTv;
                                        TextView textView3 = (TextView) ViewBindings.findChildViewById(view, R.id.digitalFilterValueTv);
                                        if (textView3 != null) {
                                            i = R.id.dsdFilterThirdSwitchBar;
                                            ThirdSwitchBar thirdSwitchBar = (ThirdSwitchBar) ViewBindings.findChildViewById(view, R.id.dsdFilterThirdSwitchBar);
                                            if (thirdSwitchBar != null) {
                                                i = R.id.eightDiv;
                                                ConstraintLayout constraintLayout = (ConstraintLayout) ViewBindings.findChildViewById(view, R.id.eightDiv);
                                                if (constraintLayout != null) {
                                                    i = R.id.firstDiv;
                                                    View findChildViewById = ViewBindings.findChildViewById(view, R.id.firstDiv);
                                                    if (findChildViewById != null) {
                                                        i = R.id.fiveDiv;
                                                        View findChildViewById2 = ViewBindings.findChildViewById(view, R.id.fiveDiv);
                                                        if (findChildViewById2 != null) {
                                                            i = R.id.fpgaVersionTv;
                                                            TextView textView4 = (TextView) ViewBindings.findChildViewById(view, R.id.fpgaVersionTv);
                                                            if (textView4 != null) {
                                                                i = R.id.gainIv;
                                                                ImageView imageView3 = (ImageView) ViewBindings.findChildViewById(view, R.id.gainIv);
                                                                if (imageView3 != null) {
                                                                    i = R.id.gainTipsTv;
                                                                    TextView textView5 = (TextView) ViewBindings.findChildViewById(view, R.id.gainTipsTv);
                                                                    if (textView5 != null) {
                                                                        i = R.id.gainTv;
                                                                        TextView textView6 = (TextView) ViewBindings.findChildViewById(view, R.id.gainTv);
                                                                        if (textView6 != null) {
                                                                            i = R.id.manufacturerTv;
                                                                            TextView textView7 = (TextView) ViewBindings.findChildViewById(view, R.id.manufacturerTv);
                                                                            if (textView7 != null) {
                                                                                i = R.id.outputIv;
                                                                                ImageView imageView4 = (ImageView) ViewBindings.findChildViewById(view, R.id.outputIv);
                                                                                if (imageView4 != null) {
                                                                                    i = R.id.outputSwitch;
                                                                                    Switch r24 = (Switch) ViewBindings.findChildViewById(view, R.id.outputSwitch);
                                                                                    if (r24 != null) {
                                                                                        i = R.id.outputSwitchTv;
                                                                                        TextView textView8 = (TextView) ViewBindings.findChildViewById(view, R.id.outputSwitchTv);
                                                                                        if (textView8 != null) {
                                                                                            i = R.id.outputTv;
                                                                                            TextView textView9 = (TextView) ViewBindings.findChildViewById(view, R.id.outputTv);
                                                                                            if (textView9 != null) {
                                                                                                i = R.id.pcmTrim0Rg;
                                                                                                RadioButton radioButton3 = (RadioButton) ViewBindings.findChildViewById(view, R.id.pcmTrim0Rg);
                                                                                                if (radioButton3 != null) {
                                                                                                    i = R.id.pcmTrim1Rg;
                                                                                                    RadioButton radioButton4 = (RadioButton) ViewBindings.findChildViewById(view, R.id.pcmTrim1Rg);
                                                                                                    if (radioButton4 != null) {
                                                                                                        i = R.id.pcmTrim2Rg;
                                                                                                        RadioButton radioButton5 = (RadioButton) ViewBindings.findChildViewById(view, R.id.pcmTrim2Rg);
                                                                                                        if (radioButton5 != null) {
                                                                                                            i = R.id.pcmTrim3Rg;
                                                                                                            RadioButton radioButton6 = (RadioButton) ViewBindings.findChildViewById(view, R.id.pcmTrim3Rg);
                                                                                                            if (radioButton6 != null) {
                                                                                                                i = R.id.pcmTrimIv;
                                                                                                                ImageView imageView5 = (ImageView) ViewBindings.findChildViewById(view, R.id.pcmTrimIv);
                                                                                                                if (imageView5 != null) {
                                                                                                                    i = R.id.pcmTrimRg;
                                                                                                                    RadioGroup radioGroup2 = (RadioGroup) ViewBindings.findChildViewById(view, R.id.pcmTrimRg);
                                                                                                                    if (radioGroup2 != null) {
                                                                                                                        i = R.id.pcmVolumeTrimKeyIv;
                                                                                                                        ImageView imageView6 = (ImageView) ViewBindings.findChildViewById(view, R.id.pcmVolumeTrimKeyIv);
                                                                                                                        if (imageView6 != null) {
                                                                                                                            i = R.id.pcmVolumeTrimKeySwitch;
                                                                                                                            Switch r34 = (Switch) ViewBindings.findChildViewById(view, R.id.pcmVolumeTrimKeySwitch);
                                                                                                                            if (r34 != null) {
                                                                                                                                i = R.id.pcmVolumeTrimKeySwitchTv;
                                                                                                                                TextView textView10 = (TextView) ViewBindings.findChildViewById(view, R.id.pcmVolumeTrimKeySwitchTv);
                                                                                                                                if (textView10 != null) {
                                                                                                                                    i = R.id.pcmVolumeTrimKeyTv;
                                                                                                                                    TextView textView11 = (TextView) ViewBindings.findChildViewById(view, R.id.pcmVolumeTrimKeyTv);
                                                                                                                                    if (textView11 != null) {
                                                                                                                                        i = R.id.productIdIv;
                                                                                                                                        ImageView imageView7 = (ImageView) ViewBindings.findChildViewById(view, R.id.productIdIv);
                                                                                                                                        if (imageView7 != null) {
                                                                                                                                            i = R.id.productNameTv;
                                                                                                                                            TextView textView12 = (TextView) ViewBindings.findChildViewById(view, R.id.productNameTv);
                                                                                                                                            if (textView12 != null) {
                                                                                                                                                i = R.id.secondDiv;
                                                                                                                                                View findChildViewById3 = ViewBindings.findChildViewById(view, R.id.secondDiv);
                                                                                                                                                if (findChildViewById3 != null) {
                                                                                                                                                    i = R.id.settings_digital_filter_layout;
                                                                                                                                                    RelativeLayout relativeLayout = (RelativeLayout) ViewBindings.findChildViewById(view, R.id.settings_digital_filter_layout);
                                                                                                                                                    if (relativeLayout != null) {
                                                                                                                                                        i = R.id.settings_pcm_trim_layout;
                                                                                                                                                        RelativeLayout relativeLayout2 = (RelativeLayout) ViewBindings.findChildViewById(view, R.id.settings_pcm_trim_layout);
                                                                                                                                                        if (relativeLayout2 != null) {
                                                                                                                                                            i = R.id.sevenDiv;
                                                                                                                                                            View findChildViewById4 = ViewBindings.findChildViewById(view, R.id.sevenDiv);
                                                                                                                                                            if (findChildViewById4 != null) {
                                                                                                                                                                i = R.id.sixDiv;
                                                                                                                                                                View findChildViewById5 = ViewBindings.findChildViewById(view, R.id.sixDiv);
                                                                                                                                                                if (findChildViewById5 != null) {
                                                                                                                                                                    i = R.id.thirdDiv;
                                                                                                                                                                    View findChildViewById6 = ViewBindings.findChildViewById(view, R.id.thirdDiv);
                                                                                                                                                                    if (findChildViewById6 != null) {
                                                                                                                                                                        return new FragmentDc07Binding((ScrollView) view, imageView, r6, textView, textView2, radioButton, radioButton2, imageView2, radioGroup, textView3, thirdSwitchBar, constraintLayout, findChildViewById, findChildViewById2, textView4, imageView3, textView5, textView6, textView7, imageView4, r24, textView8, textView9, radioButton3, radioButton4, radioButton5, radioButton6, imageView5, radioGroup2, imageView6, r34, textView10, textView11, imageView7, textView12, findChildViewById3, relativeLayout, relativeLayout2, findChildViewById4, findChildViewById5, findChildViewById6);
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
    public static FragmentDc07Binding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static FragmentDc07Binding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z) {
        View inflate = layoutInflater.inflate(R.layout.fragment_dc07, viewGroup, false);
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
