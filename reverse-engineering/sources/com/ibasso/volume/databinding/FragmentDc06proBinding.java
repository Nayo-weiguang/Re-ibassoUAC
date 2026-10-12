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
import com.ibasso.volume.R;
public final class FragmentDc06proBinding implements ViewBinding {
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
    public final TextView fpgaVersionTv;
    @NonNull
    public final ImageView icVolumeImage;
    @NonNull
    public final TextView manufacturerTv;
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

    private FragmentDc06proBinding(@NonNull ScrollView scrollView, @NonNull ImageView imageView, @NonNull BalanceView balanceView, @NonNull TextView textView, @NonNull ImageView imageView2, @NonNull Switch r6, @NonNull TextView textView2, @NonNull TextView textView3, @NonNull RadioButton radioButton, @NonNull RadioButton radioButton2, @NonNull ImageView imageView3, @NonNull RadioGroup radioGroup, @NonNull TextView textView4, @NonNull View view, @NonNull View view2, @NonNull View view3, @NonNull TextView textView5, @NonNull ImageView imageView4, @NonNull TextView textView6, @NonNull ImageView imageView5, @NonNull TextView textView7, @NonNull View view4, @NonNull RelativeLayout relativeLayout, @NonNull TextView textView8, @NonNull View view5, @NonNull ImageView imageView6, @NonNull SeekBar seekBar, @NonNull ImageView imageView7, @NonNull TextView textView9) {
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
        this.digitalFilterIv = imageView3;
        this.digitalFilterRg = radioGroup;
        this.digitalFilterValueTv = textView4;
        this.firstDiv = view;
        this.fiveDiv = view2;
        this.fourDiv = view3;
        this.fpgaVersionTv = textView5;
        this.icVolumeImage = imageView4;
        this.manufacturerTv = textView6;
        this.productIdIv = imageView5;
        this.productNameTv = textView7;
        this.secondDiv = view4;
        this.settingsDigitalFilterLayout = relativeLayout;
        this.textView = textView8;
        this.thirdDiv = view5;
        this.volumeDownBt = imageView6;
        this.volumeSeekBar = seekBar;
        this.volumeUpBt = imageView7;
        this.volumeValueTv = textView9;
    }

    @NonNull
    public static FragmentDc06proBinding bind(@NonNull View view) {
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
                                            i = R.id.digital_filter_iv;
                                            ImageView imageView3 = (ImageView) ViewBindings.findChildViewById(view, R.id.digital_filter_iv);
                                            if (imageView3 != null) {
                                                i = R.id.digitalFilterRg;
                                                RadioGroup radioGroup = (RadioGroup) ViewBindings.findChildViewById(view, R.id.digitalFilterRg);
                                                if (radioGroup != null) {
                                                    i = R.id.digitalFilterValueTv;
                                                    TextView textView4 = (TextView) ViewBindings.findChildViewById(view, R.id.digitalFilterValueTv);
                                                    if (textView4 != null) {
                                                        i = R.id.firstDiv;
                                                        View findChildViewById = ViewBindings.findChildViewById(view, R.id.firstDiv);
                                                        if (findChildViewById != null) {
                                                            i = R.id.fiveDiv;
                                                            View findChildViewById2 = ViewBindings.findChildViewById(view, R.id.fiveDiv);
                                                            if (findChildViewById2 != null) {
                                                                i = R.id.fourDiv;
                                                                View findChildViewById3 = ViewBindings.findChildViewById(view, R.id.fourDiv);
                                                                if (findChildViewById3 != null) {
                                                                    i = R.id.fpgaVersionTv;
                                                                    TextView textView5 = (TextView) ViewBindings.findChildViewById(view, R.id.fpgaVersionTv);
                                                                    if (textView5 != null) {
                                                                        i = R.id.icVolumeImage;
                                                                        ImageView imageView4 = (ImageView) ViewBindings.findChildViewById(view, R.id.icVolumeImage);
                                                                        if (imageView4 != null) {
                                                                            i = R.id.manufacturerTv;
                                                                            TextView textView6 = (TextView) ViewBindings.findChildViewById(view, R.id.manufacturerTv);
                                                                            if (textView6 != null) {
                                                                                i = R.id.productIdIv;
                                                                                ImageView imageView5 = (ImageView) ViewBindings.findChildViewById(view, R.id.productIdIv);
                                                                                if (imageView5 != null) {
                                                                                    i = R.id.productNameTv;
                                                                                    TextView textView7 = (TextView) ViewBindings.findChildViewById(view, R.id.productNameTv);
                                                                                    if (textView7 != null) {
                                                                                        i = R.id.secondDiv;
                                                                                        View findChildViewById4 = ViewBindings.findChildViewById(view, R.id.secondDiv);
                                                                                        if (findChildViewById4 != null) {
                                                                                            i = R.id.settings_digital_filter_layout;
                                                                                            RelativeLayout relativeLayout = (RelativeLayout) ViewBindings.findChildViewById(view, R.id.settings_digital_filter_layout);
                                                                                            if (relativeLayout != null) {
                                                                                                i = R.id.textView;
                                                                                                TextView textView8 = (TextView) ViewBindings.findChildViewById(view, R.id.textView);
                                                                                                if (textView8 != null) {
                                                                                                    i = R.id.thirdDiv;
                                                                                                    View findChildViewById5 = ViewBindings.findChildViewById(view, R.id.thirdDiv);
                                                                                                    if (findChildViewById5 != null) {
                                                                                                        i = R.id.volumeDownBt;
                                                                                                        ImageView imageView6 = (ImageView) ViewBindings.findChildViewById(view, R.id.volumeDownBt);
                                                                                                        if (imageView6 != null) {
                                                                                                            i = R.id.volumeSeekBar;
                                                                                                            SeekBar seekBar = (SeekBar) ViewBindings.findChildViewById(view, R.id.volumeSeekBar);
                                                                                                            if (seekBar != null) {
                                                                                                                i = R.id.volumeUpBt;
                                                                                                                ImageView imageView7 = (ImageView) ViewBindings.findChildViewById(view, R.id.volumeUpBt);
                                                                                                                if (imageView7 != null) {
                                                                                                                    i = R.id.volumeValueTv;
                                                                                                                    TextView textView9 = (TextView) ViewBindings.findChildViewById(view, R.id.volumeValueTv);
                                                                                                                    if (textView9 != null) {
                                                                                                                        return new FragmentDc06proBinding((ScrollView) view, imageView, balanceView, textView, imageView2, r9, textView2, textView3, radioButton, radioButton2, imageView3, radioGroup, textView4, findChildViewById, findChildViewById2, findChildViewById3, textView5, imageView4, textView6, imageView5, textView7, findChildViewById4, relativeLayout, textView8, findChildViewById5, imageView6, seekBar, imageView7, textView9);
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
    public static FragmentDc06proBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static FragmentDc06proBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z) {
        View inflate = layoutInflater.inflate(R.layout.fragment_dc06pro, viewGroup, false);
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
