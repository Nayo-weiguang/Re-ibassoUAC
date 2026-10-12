package com.ibasso.volume;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.window.embedding.EmbeddingCompat;
import com.ibasso.music.widgets.BalanceView;
import com.ibasso.volume.databinding.FragmentDc06Binding;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.HttpUrl;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
public final class Dc06Fragment extends Fragment implements SeekBar.OnSeekBarChangeListener {
    @Nullable
    private FragmentDc06Binding _binding;
    private boolean isTouched;

    public static final void onViewCreated$lambda$0(RadioGroup radioGroup, int i) {
        Intrinsics.checkNotNullParameter(radioGroup, "<anonymous parameter 0>");
        if (i == R.id.digitalFilter1Rg) {
            UacManager.getUacManager().setDigitalFilter(0);
        } else {
            UacManager.getUacManager().setDigitalFilter(192);
        }
    }

    public static final void onViewCreated$lambda$1(View view) {
        if (SpStorage.getInstance().getIsVolumeBtReverse()) {
            UacManager.getUacManager().setVolume(true);
        } else {
            UacManager.getUacManager().setVolume(false);
        }
    }

    public static final void onViewCreated$lambda$2(View view) {
        if (SpStorage.getInstance().getIsVolumeBtReverse()) {
            UacManager.getUacManager().setVolume(false);
        } else {
            UacManager.getUacManager().setVolume(true);
        }
    }

    @Override
    @Nullable
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        FragmentDc06Binding inflate = FragmentDc06Binding.inflate(inflater, viewGroup, false);
        this._binding = inflate;
        if (inflate != null) {
            return inflate.getRoot();
        }
        return null;
    }

    @Subscribe(sticky = EmbeddingCompat.DEBUG, threadMode = ThreadMode.MAIN)
    public final void onDc06VolumeEvent(@Nullable Dc06VolumeEvent dc06VolumeEvent) {
        if (this.isTouched) {
            return;
        }
        FragmentDc06Binding fragmentDc06Binding = this._binding;
        SeekBar seekBar = fragmentDc06Binding != null ? fragmentDc06Binding.volumeSeekBar : null;
        if (seekBar != null) {
            Integer valueOf = dc06VolumeEvent != null ? Integer.valueOf(dc06VolumeEvent.volume) : null;
            Intrinsics.checkNotNull(valueOf);
            seekBar.setProgress(valueOf.intValue());
        }
        FragmentDc06Binding fragmentDc06Binding2 = this._binding;
        TextView textView = fragmentDc06Binding2 != null ? fragmentDc06Binding2.volumeValueTv : null;
        if (textView == null) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(HttpUrl.FRAGMENT_ENCODE_SET);
        sb.append(dc06VolumeEvent != null ? Integer.valueOf(dc06VolumeEvent.volume) : null);
        textView.setText(sb.toString());
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
        if (digitalFilterEvent == null || digitalFilterEvent.value != 0) {
            FragmentDc06Binding fragmentDc06Binding = this._binding;
            if (fragmentDc06Binding != null && (radioGroup = fragmentDc06Binding.digitalFilterRg) != null) {
                radioGroup.check(R.id.digitalFilter2Rg);
            }
            FragmentDc06Binding fragmentDc06Binding2 = this._binding;
            if (fragmentDc06Binding2 == null || (textView = fragmentDc06Binding2.digitalFilterValueTv) == null) {
                return;
            }
            textView.setText(R.string.dc06_digital_filter_2);
            return;
        }
        FragmentDc06Binding fragmentDc06Binding3 = this._binding;
        if (fragmentDc06Binding3 != null && (radioGroup2 = fragmentDc06Binding3.digitalFilterRg) != null) {
            radioGroup2.check(R.id.digitalFilter1Rg);
        }
        FragmentDc06Binding fragmentDc06Binding4 = this._binding;
        if (fragmentDc06Binding4 == null || (textView2 = fragmentDc06Binding4.digitalFilterValueTv) == null) {
            return;
        }
        textView2.setText(R.string.dc06_digital_filter_1);
    }

    @Subscribe(sticky = EmbeddingCompat.DEBUG, threadMode = ThreadMode.MAIN)
    public final void onNormalVolumeEvent(@NotNull NormalVolumeEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
    }

    @Override
    public void onProgressChanged(@Nullable SeekBar seekBar, int i, boolean z) {
        FragmentDc06Binding fragmentDc06Binding = this._binding;
        TextView textView = fragmentDc06Binding != null ? fragmentDc06Binding.volumeValueTv : null;
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
        ImageView imageView;
        ImageView imageView2;
        ImageView imageView3;
        ImageView imageView4;
        super.onResume();
        if (SpStorage.getInstance().getIsVolumeBtReverse()) {
            FragmentDc06Binding fragmentDc06Binding = this._binding;
            if (fragmentDc06Binding != null && (imageView4 = fragmentDc06Binding.volumeDownBt) != null) {
                imageView4.setImageResource(R.drawable.ic_volume_up);
            }
            FragmentDc06Binding fragmentDc06Binding2 = this._binding;
            if (fragmentDc06Binding2 == null || (imageView3 = fragmentDc06Binding2.volumeUpBt) == null) {
                return;
            }
            imageView3.setImageResource(R.drawable.ic_volume_down);
            return;
        }
        FragmentDc06Binding fragmentDc06Binding3 = this._binding;
        if (fragmentDc06Binding3 != null && (imageView2 = fragmentDc06Binding3.volumeDownBt) != null) {
            imageView2.setImageResource(R.drawable.ic_volume_down);
        }
        FragmentDc06Binding fragmentDc06Binding4 = this._binding;
        if (fragmentDc06Binding4 == null || (imageView = fragmentDc06Binding4.volumeUpBt) == null) {
            return;
        }
        imageView.setImageResource(R.drawable.ic_volume_up);
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
        TextView textView;
        RadioGroup radioGroup;
        TextView textView2;
        BalanceView balanceView;
        TextView textView3;
        RadioGroup radioGroup2;
        ImageView imageView3;
        ImageView imageView4;
        ImageView imageView5;
        ImageView imageView6;
        BalanceView balanceView2;
        RadioGroup radioGroup3;
        SeekBar seekBar;
        Intrinsics.checkNotNullParameter(view, "view");
        super.onViewCreated(view, bundle);
        FragmentDc06Binding fragmentDc06Binding = this._binding;
        if (fragmentDc06Binding != null && (seekBar = fragmentDc06Binding.volumeSeekBar) != null) {
            seekBar.setOnSeekBarChangeListener(this);
        }
        FragmentDc06Binding fragmentDc06Binding2 = this._binding;
        if (fragmentDc06Binding2 != null && (radioGroup3 = fragmentDc06Binding2.digitalFilterRg) != null) {
            radioGroup3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public final void onCheckedChanged(RadioGroup radioGroup4, int i) {
                    Dc06Fragment.onViewCreated$lambda$0(radioGroup4, i);
                }
            });
        }
        FragmentDc06Binding fragmentDc06Binding3 = this._binding;
        if (fragmentDc06Binding3 != null && (balanceView2 = fragmentDc06Binding3.balanceSeekBar) != null) {
            balanceView2.setSeekBarProgressClickListener(new BalanceView.ISeekBarProgressClickListener() {
                @Override
                public void onSeekBarChanged(@Nullable BalanceView balanceView3, int i) {
                    FragmentDc06Binding fragmentDc06Binding4;
                    TextView textView4;
                    FragmentDc06Binding fragmentDc06Binding5;
                    FragmentDc06Binding fragmentDc06Binding6;
                    UacManager.getUacManager().setBalanceVolume(i);
                    if (i > 0) {
                        fragmentDc06Binding6 = Dc06Fragment.this._binding;
                        textView4 = fragmentDc06Binding6 != null ? fragmentDc06Binding6.balanceValueTv : null;
                        if (textView4 == null) {
                            return;
                        }
                        textView4.setText("R-" + i);
                    } else if (i >= 0) {
                        fragmentDc06Binding4 = Dc06Fragment.this._binding;
                        textView4 = fragmentDc06Binding4 != null ? fragmentDc06Binding4.balanceValueTv : null;
                        if (textView4 == null) {
                            return;
                        }
                        textView4.setText("L=R");
                    } else {
                        fragmentDc06Binding5 = Dc06Fragment.this._binding;
                        textView4 = fragmentDc06Binding5 != null ? fragmentDc06Binding5.balanceValueTv : null;
                        if (textView4 == null) {
                            return;
                        }
                        StringBuilder sb = new StringBuilder();
                        sb.append('L');
                        sb.append(i);
                        textView4.setText(sb.toString());
                    }
                }

                @Override
                public void onSeekBarStart(@Nullable BalanceView balanceView3, int i) {
                }

                @Override
                public void onSeekBarStop(@Nullable BalanceView balanceView3, int i) {
                }
            });
        }
        if (SpStorage.getInstance().getIsVolumeBtReverse()) {
            FragmentDc06Binding fragmentDc06Binding4 = this._binding;
            if (fragmentDc06Binding4 != null && (imageView6 = fragmentDc06Binding4.volumeDownBt) != null) {
                imageView6.setImageResource(R.drawable.ic_volume_up);
            }
            FragmentDc06Binding fragmentDc06Binding5 = this._binding;
            if (fragmentDc06Binding5 != null && (imageView5 = fragmentDc06Binding5.volumeUpBt) != null) {
                imageView5.setImageResource(R.drawable.ic_volume_down);
            }
        } else {
            FragmentDc06Binding fragmentDc06Binding6 = this._binding;
            if (fragmentDc06Binding6 != null && (imageView2 = fragmentDc06Binding6.volumeDownBt) != null) {
                imageView2.setImageResource(R.drawable.ic_volume_down);
            }
            FragmentDc06Binding fragmentDc06Binding7 = this._binding;
            if (fragmentDc06Binding7 != null && (imageView = fragmentDc06Binding7.volumeUpBt) != null) {
                imageView.setImageResource(R.drawable.ic_volume_up);
            }
        }
        FragmentDc06Binding fragmentDc06Binding8 = this._binding;
        if (fragmentDc06Binding8 != null && (imageView4 = fragmentDc06Binding8.volumeDownBt) != null) {
            imageView4.setOnClickListener(new View.OnClickListener() {
                @Override
                public final void onClick(View view2) {
                    Dc06Fragment.onViewCreated$lambda$1(view2);
                }
            });
        }
        FragmentDc06Binding fragmentDc06Binding9 = this._binding;
        if (fragmentDc06Binding9 != null && (imageView3 = fragmentDc06Binding9.volumeUpBt) != null) {
            imageView3.setOnClickListener(new View.OnClickListener() {
                @Override
                public final void onClick(View view2) {
                    Dc06Fragment.onViewCreated$lambda$2(view2);
                }
            });
        }
        FragmentDc06Binding fragmentDc06Binding10 = this._binding;
        SeekBar seekBar2 = fragmentDc06Binding10 != null ? fragmentDc06Binding10.volumeSeekBar : null;
        if (seekBar2 != null) {
            seekBar2.setMax(100);
        }
        FragmentDc06Binding fragmentDc06Binding11 = this._binding;
        SeekBar seekBar3 = fragmentDc06Binding11 != null ? fragmentDc06Binding11.volumeSeekBar : null;
        if (seekBar3 != null) {
            seekBar3.setProgress(UacManager.getUacManager().getVolume());
        }
        FragmentDc06Binding fragmentDc06Binding12 = this._binding;
        TextView textView4 = fragmentDc06Binding12 != null ? fragmentDc06Binding12.volumeValueTv : null;
        if (textView4 != null) {
            textView4.setText(HttpUrl.FRAGMENT_ENCODE_SET + UacManager.getUacManager().getVolume());
        }
        if (UacManager.getUacManager().getDigitalFilter() == 0) {
            FragmentDc06Binding fragmentDc06Binding13 = this._binding;
            if (fragmentDc06Binding13 != null && (radioGroup2 = fragmentDc06Binding13.digitalFilterRg) != null) {
                radioGroup2.check(R.id.digitalFilter1Rg);
            }
            FragmentDc06Binding fragmentDc06Binding14 = this._binding;
            if (fragmentDc06Binding14 != null && (textView3 = fragmentDc06Binding14.digitalFilterValueTv) != null) {
                textView3.setText(R.string.dc06_digital_filter_1);
            }
        } else {
            FragmentDc06Binding fragmentDc06Binding15 = this._binding;
            if (fragmentDc06Binding15 != null && (radioGroup = fragmentDc06Binding15.digitalFilterRg) != null) {
                radioGroup.check(R.id.digitalFilter2Rg);
            }
            FragmentDc06Binding fragmentDc06Binding16 = this._binding;
            if (fragmentDc06Binding16 != null && (textView = fragmentDc06Binding16.digitalFilterValueTv) != null) {
                textView.setText(R.string.dc06_digital_filter_2);
            }
        }
        int balanceVolume = UacManager.getUacManager().getBalanceVolume();
        if (balanceVolume > 0) {
            FragmentDc06Binding fragmentDc06Binding17 = this._binding;
            textView2 = fragmentDc06Binding17 != null ? fragmentDc06Binding17.balanceValueTv : null;
            if (textView2 != null) {
                textView2.setText("R-" + balanceVolume);
            }
        } else if (balanceVolume < 0) {
            FragmentDc06Binding fragmentDc06Binding18 = this._binding;
            textView2 = fragmentDc06Binding18 != null ? fragmentDc06Binding18.balanceValueTv : null;
            if (textView2 != null) {
                StringBuilder sb = new StringBuilder();
                sb.append('L');
                sb.append(balanceVolume);
                textView2.setText(sb.toString());
            }
        } else {
            FragmentDc06Binding fragmentDc06Binding19 = this._binding;
            textView2 = fragmentDc06Binding19 != null ? fragmentDc06Binding19.balanceValueTv : null;
            if (textView2 != null) {
                textView2.setText("L=R");
            }
        }
        FragmentDc06Binding fragmentDc06Binding20 = this._binding;
        if (fragmentDc06Binding20 != null && (balanceView = fragmentDc06Binding20.balanceSeekBar) != null) {
            balanceView.setProgress(balanceVolume);
        }
        EventBus.getDefault().register(this);
    }
}
