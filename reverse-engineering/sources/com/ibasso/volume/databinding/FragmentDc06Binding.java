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
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.ibasso.music.widgets.BalanceView;
import com.ibasso.volume.R;
public final class FragmentDc06Binding implements ViewBinding {
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
    public final ImageView digitalFilterIv;
    @NonNull
    public final RadioGroup digitalFilterRg;
    @NonNull
    public final TextView digitalFilterValueTv;
    @NonNull
    public final View firstDiv;
    @NonNull
    public final View fourDiv;
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

    private FragmentDc06Binding(@NonNull ScrollView scrollView, @NonNull ImageView imageView, @NonNull BalanceView balanceView, @NonNull TextView textView, @NonNull RadioButton radioButton, @NonNull RadioButton radioButton2, @NonNull ImageView imageView2, @NonNull RadioGroup radioGroup, @NonNull TextView textView2, @NonNull View view, @NonNull View view2, @NonNull ImageView imageView3, @NonNull TextView textView3, @NonNull ImageView imageView4, @NonNull TextView textView4, @NonNull View view3, @NonNull RelativeLayout relativeLayout, @NonNull TextView textView5, @NonNull View view4, @NonNull ImageView imageView5, @NonNull SeekBar seekBar, @NonNull ImageView imageView6, @NonNull TextView textView6) {
        this.rootView = scrollView;
        this.balanceIv = imageView;
        this.balanceSeekBar = balanceView;
        this.balanceValueTv = textView;
        this.digitalFilter1Rg = radioButton;
        this.digitalFilter2Rg = radioButton2;
        this.digitalFilterIv = imageView2;
        this.digitalFilterRg = radioGroup;
        this.digitalFilterValueTv = textView2;
        this.firstDiv = view;
        this.fourDiv = view2;
        this.icVolumeImage = imageView3;
        this.manufacturerTv = textView3;
        this.productIdIv = imageView4;
        this.productNameTv = textView4;
        this.secondDiv = view3;
        this.settingsDigitalFilterLayout = relativeLayout;
        this.textView = textView5;
        this.thirdDiv = view4;
        this.volumeDownBt = imageView5;
        this.volumeSeekBar = seekBar;
        this.volumeUpBt = imageView6;
        this.volumeValueTv = textView6;
    }

    @NonNull
    public static FragmentDc06Binding bind(@NonNull View view) {
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
                                            i = R.id.fourDiv;
                                            View findChildViewById2 = ViewBindings.findChildViewById(view, R.id.fourDiv);
                                            if (findChildViewById2 != null) {
                                                i = R.id.icVolumeImage;
                                                ImageView imageView3 = (ImageView) ViewBindings.findChildViewById(view, R.id.icVolumeImage);
                                                if (imageView3 != null) {
                                                    i = R.id.manufacturerTv;
                                                    TextView textView3 = (TextView) ViewBindings.findChildViewById(view, R.id.manufacturerTv);
                                                    if (textView3 != null) {
                                                        i = R.id.productIdIv;
                                                        ImageView imageView4 = (ImageView) ViewBindings.findChildViewById(view, R.id.productIdIv);
                                                        if (imageView4 != null) {
                                                            i = R.id.productNameTv;
                                                            TextView textView4 = (TextView) ViewBindings.findChildViewById(view, R.id.productNameTv);
                                                            if (textView4 != null) {
                                                                i = R.id.secondDiv;
                                                                View findChildViewById3 = ViewBindings.findChildViewById(view, R.id.secondDiv);
                                                                if (findChildViewById3 != null) {
                                                                    i = R.id.settings_digital_filter_layout;
                                                                    RelativeLayout relativeLayout = (RelativeLayout) ViewBindings.findChildViewById(view, R.id.settings_digital_filter_layout);
                                                                    if (relativeLayout != null) {
                                                                        i = R.id.textView;
                                                                        TextView textView5 = (TextView) ViewBindings.findChildViewById(view, R.id.textView);
                                                                        if (textView5 != null) {
                                                                            i = R.id.thirdDiv;
                                                                            View findChildViewById4 = ViewBindings.findChildViewById(view, R.id.thirdDiv);
                                                                            if (findChildViewById4 != null) {
                                                                                i = R.id.volumeDownBt;
                                                                                ImageView imageView5 = (ImageView) ViewBindings.findChildViewById(view, R.id.volumeDownBt);
                                                                                if (imageView5 != null) {
                                                                                    i = R.id.volumeSeekBar;
                                                                                    SeekBar seekBar = (SeekBar) ViewBindings.findChildViewById(view, R.id.volumeSeekBar);
                                                                                    if (seekBar != null) {
                                                                                        i = R.id.volumeUpBt;
                                                                                        ImageView imageView6 = (ImageView) ViewBindings.findChildViewById(view, R.id.volumeUpBt);
                                                                                        if (imageView6 != null) {
                                                                                            i = R.id.volumeValueTv;
                                                                                            TextView textView6 = (TextView) ViewBindings.findChildViewById(view, R.id.volumeValueTv);
                                                                                            if (textView6 != null) {
                                                                                                return new FragmentDc06Binding((ScrollView) view, imageView, balanceView, textView, radioButton, radioButton2, imageView2, radioGroup, textView2, findChildViewById, findChildViewById2, imageView3, textView3, imageView4, textView4, findChildViewById3, relativeLayout, textView5, findChildViewById4, imageView5, seekBar, imageView6, textView6);
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
    public static FragmentDc06Binding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static FragmentDc06Binding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z) {
        View inflate = layoutInflater.inflate(R.layout.fragment_dc06, viewGroup, false);
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
