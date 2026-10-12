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
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.ibasso.music.widgets.BalanceView;
import com.ibasso.music.widgets.ThirdSwitchBar;
import com.ibasso.volume.R;
public final class FragmentDc03proBinding implements ViewBinding {
    @NonNull
    public final ImageView balanceIv;
    @NonNull
    public final BalanceView balanceSeekBar;
    @NonNull
    public final TextView balanceValueTv;
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
    public final View firstDiv;
    @NonNull
    public final View fiveDiv;
    @NonNull
    public final View fourDiv;
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

    private FragmentDc03proBinding(@NonNull ScrollView scrollView, @NonNull ImageView imageView, @NonNull BalanceView balanceView, @NonNull TextView textView, @NonNull RadioButton radioButton, @NonNull RadioButton radioButton2, @NonNull RadioButton radioButton3, @NonNull RadioButton radioButton4, @NonNull RadioButton radioButton5, @NonNull ImageView imageView2, @NonNull RadioGroup radioGroup, @NonNull TextView textView2, @NonNull View view, @NonNull View view2, @NonNull View view3, @NonNull ImageView imageView3, @NonNull ThirdSwitchBar thirdSwitchBar, @NonNull TextView textView3, @NonNull TextView textView4, @NonNull ImageView imageView4, @NonNull TextView textView5, @NonNull ImageView imageView5, @NonNull Switch r23, @NonNull TextView textView6, @NonNull TextView textView7, @NonNull ImageView imageView6, @NonNull TextView textView8, @NonNull View view4, @NonNull RelativeLayout relativeLayout, @NonNull View view5, @NonNull TextView textView9, @NonNull View view6, @NonNull ImageView imageView7, @NonNull SeekBar seekBar, @NonNull ImageView imageView8, @NonNull TextView textView10) {
        this.rootView = scrollView;
        this.balanceIv = imageView;
        this.balanceSeekBar = balanceView;
        this.balanceValueTv = textView;
        this.digitalFilter1Rg = radioButton;
        this.digitalFilter2Rg = radioButton2;
        this.digitalFilter3Rg = radioButton3;
        this.digitalFilter4Rg = radioButton4;
        this.digitalFilter5Rg = radioButton5;
        this.digitalFilterIv = imageView2;
        this.digitalFilterRg = radioGroup;
        this.digitalFilterValueTv = textView2;
        this.firstDiv = view;
        this.fiveDiv = view2;
        this.fourDiv = view3;
        this.gainIv = imageView3;
        this.gainThirdSwitchBar = thirdSwitchBar;
        this.gainTipsTv = textView3;
        this.gainTv = textView4;
        this.icVolumeImage = imageView4;
        this.manufacturerTv = textView5;
        this.outputIv = imageView5;
        this.outputSwitch = r23;
        this.outputSwitchTv = textView6;
        this.outputTv = textView7;
        this.productIdIv = imageView6;
        this.productNameTv = textView8;
        this.secondDiv = view4;
        this.settingsDigitalFilterLayout = relativeLayout;
        this.sixDiv = view5;
        this.textView = textView9;
        this.thirdDiv = view6;
        this.volumeDownBt = imageView7;
        this.volumeSeekBar = seekBar;
        this.volumeUpBt = imageView8;
        this.volumeValueTv = textView10;
    }

    @NonNull
    public static FragmentDc03proBinding bind(@NonNull View view) {
        int i = R.id.balanceIv;
        ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, R.id.balanceIv);
        if (imageView != null) {
            i = R.id.balanceSeekBar;
            BalanceView balanceView = (BalanceView) ViewBindings.findChildViewById(view, R.id.balanceSeekBar);
            if (balanceView != null) {
                i = R.id.balanceValueTv;
                TextView textView = (TextView) ViewBindings.findChildViewById(view, R.id.balanceValueTv);
                if (textView != null) {
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
                                        ImageView imageView2 = (ImageView) ViewBindings.findChildViewById(view, R.id.digital_filter_iv);
                                        if (imageView2 != null) {
                                            i = R.id.digitalFilterRg;
                                            RadioGroup radioGroup = (RadioGroup) ViewBindings.findChildViewById(view, R.id.digitalFilterRg);
                                            if (radioGroup != null) {
                                                i = R.id.digitalFilterValueTv;
                                                TextView textView2 = (TextView) ViewBindings.findChildViewById(view, R.id.digitalFilterValueTv);
                                                if (textView2 != null) {
                                                    i = R.id.firstDiv;
                                                    View findChildViewById = ViewBindings.findChildViewById(view, R.id.firstDiv);
                                                    if (findChildViewById != null) {
                                                        i = R.id.fiveDiv;
                                                        View findChildViewById2 = ViewBindings.findChildViewById(view, R.id.fiveDiv);
                                                        if (findChildViewById2 != null) {
                                                            i = R.id.fourDiv;
                                                            View findChildViewById3 = ViewBindings.findChildViewById(view, R.id.fourDiv);
                                                            if (findChildViewById3 != null) {
                                                                i = R.id.gainIv;
                                                                ImageView imageView3 = (ImageView) ViewBindings.findChildViewById(view, R.id.gainIv);
                                                                if (imageView3 != null) {
                                                                    i = R.id.gainThirdSwitchBar;
                                                                    ThirdSwitchBar thirdSwitchBar = (ThirdSwitchBar) ViewBindings.findChildViewById(view, R.id.gainThirdSwitchBar);
                                                                    if (thirdSwitchBar != null) {
                                                                        i = R.id.gainTipsTv;
                                                                        TextView textView3 = (TextView) ViewBindings.findChildViewById(view, R.id.gainTipsTv);
                                                                        if (textView3 != null) {
                                                                            i = R.id.gainTv;
                                                                            TextView textView4 = (TextView) ViewBindings.findChildViewById(view, R.id.gainTv);
                                                                            if (textView4 != null) {
                                                                                i = R.id.icVolumeImage;
                                                                                ImageView imageView4 = (ImageView) ViewBindings.findChildViewById(view, R.id.icVolumeImage);
                                                                                if (imageView4 != null) {
                                                                                    i = R.id.manufacturerTv;
                                                                                    TextView textView5 = (TextView) ViewBindings.findChildViewById(view, R.id.manufacturerTv);
                                                                                    if (textView5 != null) {
                                                                                        i = R.id.outputIv;
                                                                                        ImageView imageView5 = (ImageView) ViewBindings.findChildViewById(view, R.id.outputIv);
                                                                                        if (imageView5 != null) {
                                                                                            i = R.id.outputSwitch;
                                                                                            Switch r26 = (Switch) ViewBindings.findChildViewById(view, R.id.outputSwitch);
                                                                                            if (r26 != null) {
                                                                                                i = R.id.outputSwitchTv;
                                                                                                TextView textView6 = (TextView) ViewBindings.findChildViewById(view, R.id.outputSwitchTv);
                                                                                                if (textView6 != null) {
                                                                                                    i = R.id.outputTv;
                                                                                                    TextView textView7 = (TextView) ViewBindings.findChildViewById(view, R.id.outputTv);
                                                                                                    if (textView7 != null) {
                                                                                                        i = R.id.productIdIv;
                                                                                                        ImageView imageView6 = (ImageView) ViewBindings.findChildViewById(view, R.id.productIdIv);
                                                                                                        if (imageView6 != null) {
                                                                                                            i = R.id.productNameTv;
                                                                                                            TextView textView8 = (TextView) ViewBindings.findChildViewById(view, R.id.productNameTv);
                                                                                                            if (textView8 != null) {
                                                                                                                i = R.id.secondDiv;
                                                                                                                View findChildViewById4 = ViewBindings.findChildViewById(view, R.id.secondDiv);
                                                                                                                if (findChildViewById4 != null) {
                                                                                                                    i = R.id.settings_digital_filter_layout;
                                                                                                                    RelativeLayout relativeLayout = (RelativeLayout) ViewBindings.findChildViewById(view, R.id.settings_digital_filter_layout);
                                                                                                                    if (relativeLayout != null) {
                                                                                                                        i = R.id.sixDiv;
                                                                                                                        View findChildViewById5 = ViewBindings.findChildViewById(view, R.id.sixDiv);
                                                                                                                        if (findChildViewById5 != null) {
                                                                                                                            i = R.id.textView;
                                                                                                                            TextView textView9 = (TextView) ViewBindings.findChildViewById(view, R.id.textView);
                                                                                                                            if (textView9 != null) {
                                                                                                                                i = R.id.thirdDiv;
                                                                                                                                View findChildViewById6 = ViewBindings.findChildViewById(view, R.id.thirdDiv);
                                                                                                                                if (findChildViewById6 != null) {
                                                                                                                                    i = R.id.volumeDownBt;
                                                                                                                                    ImageView imageView7 = (ImageView) ViewBindings.findChildViewById(view, R.id.volumeDownBt);
                                                                                                                                    if (imageView7 != null) {
                                                                                                                                        i = R.id.volumeSeekBar;
                                                                                                                                        SeekBar seekBar = (SeekBar) ViewBindings.findChildViewById(view, R.id.volumeSeekBar);
                                                                                                                                        if (seekBar != null) {
                                                                                                                                            i = R.id.volumeUpBt;
                                                                                                                                            ImageView imageView8 = (ImageView) ViewBindings.findChildViewById(view, R.id.volumeUpBt);
                                                                                                                                            if (imageView8 != null) {
                                                                                                                                                i = R.id.volumeValueTv;
                                                                                                                                                TextView textView10 = (TextView) ViewBindings.findChildViewById(view, R.id.volumeValueTv);
                                                                                                                                                if (textView10 != null) {
                                                                                                                                                    return new FragmentDc03proBinding((ScrollView) view, imageView, balanceView, textView, radioButton, radioButton2, radioButton3, radioButton4, radioButton5, imageView2, radioGroup, textView2, findChildViewById, findChildViewById2, findChildViewById3, imageView3, thirdSwitchBar, textView3, textView4, imageView4, textView5, imageView5, r26, textView6, textView7, imageView6, textView8, findChildViewById4, relativeLayout, findChildViewById5, textView9, findChildViewById6, imageView7, seekBar, imageView8, textView10);
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
    public static FragmentDc03proBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static FragmentDc03proBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z) {
        View inflate = layoutInflater.inflate(R.layout.fragment_dc03pro, viewGroup, false);
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
