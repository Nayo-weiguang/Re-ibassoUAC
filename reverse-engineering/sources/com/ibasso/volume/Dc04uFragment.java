package com.ibasso.volume;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.window.embedding.EmbeddingCompat;
import com.ibasso.music.widgets.BalanceView;
import com.ibasso.music.widgets.ThirdSwitchBar;
import com.ibasso.volume.bean.FpgaVersionEvent;
import com.ibasso.volume.bean.NativeReportEvent;
import com.ibasso.volume.databinding.FragmentDc04uBinding;
import com.ibasso.volume.eq.EqualizerManager;
import com.ibasso.volume.eq.ParametricEqTypeSettingFragment;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.text.Regex;
import okhttp3.HttpUrl;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
@SourceDebugExtension({"SMAP\nDc04uFragment.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Dc04uFragment.kt\ncom/ibasso/volume/Dc04uFragment\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 Color.kt\nandroidx/core/graphics/ColorKt\n*L\n1#1,806:1\n1#2:807\n470#3:808\n470#3:809\n470#3:810\n470#3:811\n*S KotlinDebug\n*F\n+ 1 Dc04uFragment.kt\ncom/ibasso/volume/Dc04uFragment\n*L\n408#1:808\n409#1:809\n411#1:810\n412#1:811\n*E\n"})
public final class Dc04uFragment extends Fragment implements SeekBar.OnSeekBarChangeListener {
    @Nullable
    private FragmentDc04uBinding _binding;
    @Nullable
    private AlertDialog ampModeDialog;
    @Nullable
    private AlertDialog dreDialog;
    @Nullable
    private AlertDialog eqResetDialog;
    private boolean isTouched;
    @Nullable
    private AlertDialog opVoltageDialog;

    public static final void onViewCreated$lambda$0(Dc04uFragment this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.showOpVoltageDialog();
    }

    public static final void onViewCreated$lambda$1(Dc04uFragment this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.showEqDialog();
    }

    public static final void onViewCreated$lambda$2(Dc04uFragment this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.showAmpModeDialog();
    }

    public static final void onViewCreated$lambda$3(Dc04uFragment this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.showDreDialog();
    }

    private final void showAmpModeDialog() {
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.amp_mode_dialog_layout, (ViewGroup) null);
        Intrinsics.checkNotNullExpressionValue(inflate, "from(context).inflate(R.…mode_dialog_layout, null)");
        RadioGroup radioGroup = (RadioGroup) inflate.findViewById(R.id.rg);
        if (SpStorage.getInstance().getDc04uAmpMode() == 1) {
            radioGroup.check(R.id.rb_1);
        } else {
            radioGroup.check(R.id.rb_0);
        }
        if (radioGroup != null) {
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public final void onCheckedChanged(RadioGroup radioGroup2, int i) {
                    Dc04uFragment.showAmpModeDialog$lambda$4(Dc04uFragment.this, radioGroup2, i);
                }
            });
        }
        AlertDialog create = new AlertDialog.Builder(requireContext()).setView(inflate).create();
        this.ampModeDialog = create;
        Intrinsics.checkNotNull(create);
        create.show();
    }

    public static final void showAmpModeDialog$lambda$4(Dc04uFragment this$0, RadioGroup radioGroup, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(radioGroup, "<anonymous parameter 0>");
        switch (i) {
            case R.id.rb_0:
                UacManager.getUacManager().setAmpMode(0);
                break;
            case R.id.rb_1:
                UacManager.getUacManager().setAmpMode(1);
                break;
            default:
                AlertDialog alertDialog = this$0.ampModeDialog;
                if (alertDialog != null) {
                    alertDialog.dismiss();
                    break;
                }
                break;
        }
        AlertDialog alertDialog2 = this$0.ampModeDialog;
        if (alertDialog2 != null) {
            alertDialog2.dismiss();
        }
    }

    private final void showDreDialog() {
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.dre_dialog_layout, (ViewGroup) null);
        Intrinsics.checkNotNullExpressionValue(inflate, "from(context).inflate(R.….dre_dialog_layout, null)");
        RadioGroup radioGroup = (RadioGroup) inflate.findViewById(R.id.rg);
        int dc04uDRE = SpStorage.getInstance().getDc04uDRE();
        if (dc04uDRE == 1) {
            radioGroup.check(R.id.rb_1);
        } else if (dc04uDRE != 2) {
            radioGroup.check(R.id.rb_0);
        } else {
            radioGroup.check(R.id.rb_2);
        }
        if (radioGroup != null) {
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public final void onCheckedChanged(RadioGroup radioGroup2, int i) {
                    Dc04uFragment.showDreDialog$lambda$5(Dc04uFragment.this, radioGroup2, i);
                }
            });
        }
        AlertDialog create = new AlertDialog.Builder(requireContext()).setView(inflate).create();
        this.dreDialog = create;
        Intrinsics.checkNotNull(create);
        create.show();
    }

    public static final void showDreDialog$lambda$5(Dc04uFragment this$0, RadioGroup radioGroup, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(radioGroup, "<anonymous parameter 0>");
        switch (i) {
            case R.id.rb_0:
                UacManager.getUacManager().setDRE(0);
                break;
            case R.id.rb_1:
                UacManager.getUacManager().setDRE(1);
                break;
            case R.id.rb_2:
                UacManager.getUacManager().setDRE(2);
                break;
            default:
                AlertDialog alertDialog = this$0.dreDialog;
                if (alertDialog != null) {
                    alertDialog.dismiss();
                    break;
                }
                break;
        }
        AlertDialog alertDialog2 = this$0.dreDialog;
        if (alertDialog2 != null) {
            alertDialog2.dismiss();
        }
    }

    private final void showEqDialog() {
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.eq_dialog_layout, (ViewGroup) null);
        Intrinsics.checkNotNullExpressionValue(inflate, "from(context).inflate(R.…t.eq_dialog_layout, null)");
        RadioGroup radioGroup = (RadioGroup) inflate.findViewById(R.id.eq_rg);
        TextView textView = (TextView) inflate.findViewById(R.id.eq_reset_tv);
        switch (SpStorage.getInstance().getDc04uEQ()) {
            case 1:
                radioGroup.check(R.id.eq_1_rb);
                break;
            case 2:
                radioGroup.check(R.id.eq_2_rb);
                break;
            case 3:
                radioGroup.check(R.id.eq_3_rb);
                break;
            case 4:
                radioGroup.check(R.id.eq_4_rb);
                break;
            case 5:
                radioGroup.check(R.id.eq_5_rb);
                break;
            case 6:
                radioGroup.check(R.id.eq_6_rb);
                break;
            case 7:
                radioGroup.check(R.id.eq_7_rb);
                break;
            case 8:
                radioGroup.check(R.id.eq_8_rb);
                break;
            case 9:
                radioGroup.check(R.id.eq_9_rb);
                break;
            case 10:
                radioGroup.check(R.id.eq_10_rb);
                break;
            default:
                radioGroup.check(R.id.eq_0_rb);
                break;
        }
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                Dc04uFragment.showEqDialog$lambda$8(Dc04uFragment.this, view);
            }
        });
        if (radioGroup != null) {
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public final void onCheckedChanged(RadioGroup radioGroup2, int i) {
                    Dc04uFragment.showEqDialog$lambda$9(Dc04uFragment.this, radioGroup2, i);
                }
            });
        }
        AlertDialog create = new AlertDialog.Builder(requireContext()).setView(inflate).create();
        this.opVoltageDialog = create;
        Intrinsics.checkNotNull(create);
        create.show();
        ((RadioButton) inflate.findViewById(R.id.eq_8_rb)).setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                Dc04uFragment.showEqDialog$lambda$10(Dc04uFragment.this, view);
            }
        });
        ((RadioButton) inflate.findViewById(R.id.eq_9_rb)).setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                Dc04uFragment.showEqDialog$lambda$11(Dc04uFragment.this, view);
            }
        });
        ((RadioButton) inflate.findViewById(R.id.eq_10_rb)).setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                Dc04uFragment.showEqDialog$lambda$12(Dc04uFragment.this, view);
            }
        });
    }

    public static final void showEqDialog$lambda$10(Dc04uFragment this$0, View view) {
        AlertDialog alertDialog;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        UacManager.getUacManager().setEqualizer(8);
        this$0.startEqFragment(0);
        AlertDialog alertDialog2 = this$0.opVoltageDialog;
        if (alertDialog2 == null || !alertDialog2.isShowing() || (alertDialog = this$0.opVoltageDialog) == null) {
            return;
        }
        alertDialog.dismiss();
    }

    public static final void showEqDialog$lambda$11(Dc04uFragment this$0, View view) {
        AlertDialog alertDialog;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        UacManager.getUacManager().setEqualizer(9);
        this$0.startEqFragment(1);
        AlertDialog alertDialog2 = this$0.opVoltageDialog;
        if (alertDialog2 == null || !alertDialog2.isShowing() || (alertDialog = this$0.opVoltageDialog) == null) {
            return;
        }
        alertDialog.dismiss();
    }

    public static final void showEqDialog$lambda$12(Dc04uFragment this$0, View view) {
        AlertDialog alertDialog;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        UacManager.getUacManager().setEqualizer(10);
        this$0.startEqFragment(2);
        AlertDialog alertDialog2 = this$0.opVoltageDialog;
        if (alertDialog2 == null || !alertDialog2.isShowing() || (alertDialog = this$0.opVoltageDialog) == null) {
            return;
        }
        alertDialog.dismiss();
    }

    public static final void showEqDialog$lambda$8(Dc04uFragment this$0, View view) {
        AlertDialog alertDialog;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        AlertDialog alertDialog2 = this$0.opVoltageDialog;
        if (alertDialog2 != null && alertDialog2.isShowing() && (alertDialog = this$0.opVoltageDialog) != null) {
            alertDialog.dismiss();
        }
        this$0.showResetEqDialog();
    }

    public static final void showEqDialog$lambda$9(Dc04uFragment this$0, RadioGroup radioGroup, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(radioGroup, "<anonymous parameter 0>");
        switch (i) {
            case R.id.eq_0_rb:
                UacManager.getUacManager().setEqualizer(0);
                break;
            case R.id.eq_10_rb:
            default:
                AlertDialog alertDialog = this$0.opVoltageDialog;
                if (alertDialog != null) {
                    alertDialog.dismiss();
                    break;
                }
                break;
            case R.id.eq_1_rb:
                UacManager.getUacManager().setEqualizer(1);
                break;
            case R.id.eq_2_rb:
                UacManager.getUacManager().setEqualizer(2);
                break;
            case R.id.eq_3_rb:
                UacManager.getUacManager().setEqualizer(3);
                break;
            case R.id.eq_4_rb:
                UacManager.getUacManager().setEqualizer(4);
                break;
            case R.id.eq_5_rb:
                UacManager.getUacManager().setEqualizer(5);
                break;
            case R.id.eq_6_rb:
                UacManager.getUacManager().setEqualizer(6);
                break;
            case R.id.eq_7_rb:
                UacManager.getUacManager().setEqualizer(7);
                break;
        }
        AlertDialog alertDialog2 = this$0.opVoltageDialog;
        if (alertDialog2 != null) {
            alertDialog2.dismiss();
        }
    }

    private final void showOpVoltageDialog() {
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.op_voltage_dialog_layout, (ViewGroup) null);
        Intrinsics.checkNotNullExpressionValue(inflate, "from(context).inflate(R.…tage_dialog_layout, null)");
        final EditText editText = (EditText) inflate.findViewById(R.id.op_voltage_value_et);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(@NotNull Editable s) {
                List emptyList;
                Intrinsics.checkNotNullParameter(s, "s");
                String obj = s.toString();
                if (obj.length() <= 0 || Intrinsics.areEqual(obj, "-")) {
                    return;
                }
                try {
                    double parseDouble = Double.parseDouble(obj);
                    if (parseDouble < 4.0d) {
                        editText.setText("4.0");
                        editText.setSelection(3);
                    } else if (parseDouble > 6.0d) {
                        editText.setText("6.0");
                        editText.setSelection(3);
                    } else {
                        List<String> split = new Regex("\\.").split(obj, 0);
                        if (!split.isEmpty()) {
                            ListIterator<String> listIterator = split.listIterator(split.size());
                            while (listIterator.hasPrevious()) {
                                if (listIterator.previous().length() != 0) {
                                    emptyList = CollectionsKt.take(split, listIterator.nextIndex() + 1);
                                    break;
                                }
                            }
                        }
                        emptyList = CollectionsKt.emptyList();
                        String[] strArr = (String[]) emptyList.toArray(new String[0]);
                        if (strArr.length <= 1 || strArr[1].length() <= 1) {
                            return;
                        }
                        String str = strArr[0] + '.' + strArr[1].charAt(0);
                        editText.setText(str);
                        editText.setSelection(str.length());
                    }
                } catch (NumberFormatException unused) {
                    editText.setText("4.0");
                    editText.setSelection(3);
                }
            }

            @Override
            public void beforeTextChanged(@NotNull CharSequence s, int i, int i2, int i3) {
                Intrinsics.checkNotNullParameter(s, "s");
            }

            @Override
            public void onTextChanged(@NotNull CharSequence s, int i, int i2, int i3) {
                Intrinsics.checkNotNullParameter(s, "s");
            }
        });
        ((Button) inflate.findViewById(R.id.confirm_action_bt)).setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                Dc04uFragment.showOpVoltageDialog$lambda$13(editText, this, view);
            }
        });
        ((Button) inflate.findViewById(R.id.cancel_action_bt)).setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                Dc04uFragment.showOpVoltageDialog$lambda$14(Dc04uFragment.this, view);
            }
        });
        AlertDialog create = new AlertDialog.Builder(requireContext()).setView(inflate).create();
        this.opVoltageDialog = create;
        Intrinsics.checkNotNull(create);
        create.show();
    }

    public static final void showOpVoltageDialog$lambda$13(EditText editText, Dc04uFragment this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        try {
            double parseDouble = Double.parseDouble(editText.getText().toString());
            if (parseDouble <= 6.0d && parseDouble >= 4.0d) {
                UacManager.getUacManager().setOpVoltage(((int) (parseDouble * 10)) - 40);
                AlertDialog alertDialog = this$0.opVoltageDialog;
                if (alertDialog != null) {
                    alertDialog.dismiss();
                    return;
                }
                return;
            }
        } catch (NumberFormatException e2) {
            e2.printStackTrace();
        }
        Toast.makeText(this$0.requireContext(), this$0.requireContext().getString(R.string.op_voltage_value_hit), 0).show();
    }

    public static final void showOpVoltageDialog$lambda$14(Dc04uFragment this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        AlertDialog alertDialog = this$0.opVoltageDialog;
        Intrinsics.checkNotNull(alertDialog);
        alertDialog.dismiss();
    }

    private final void showResetEqDialog() {
        AlertDialog create = new AlertDialog.Builder(requireContext()).setTitle("Reset Equalizer").setMessage("Are you sure to reset all equalizer parameters?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public final void onClick(DialogInterface dialogInterface, int i) {
                Dc04uFragment.showResetEqDialog$lambda$6(Dc04uFragment.this, dialogInterface, i);
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public final void onClick(DialogInterface dialogInterface, int i) {
                Dc04uFragment.showResetEqDialog$lambda$7(Dc04uFragment.this, dialogInterface, i);
            }
        }).create();
        this.eqResetDialog = create;
        if (create != null) {
            create.show();
        }
    }

    public static final void showResetEqDialog$lambda$6(Dc04uFragment this$0, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        EqualizerManager.getInstance().eraseFlash();
        Toast.makeText(this$0.requireContext(), "Reset all equalizer parameters!!!", 0).show();
    }

    public static final void showResetEqDialog$lambda$7(Dc04uFragment this$0, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        AlertDialog alertDialog = this$0.eqResetDialog;
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
    }

    private final void startEqFragment(int i) {
        if (getChildFragmentManager().findFragmentByTag("ParametricEqTypeSettingFragment") == null) {
            getChildFragmentManager().beginTransaction().replace(R.id.fragment_container, new ParametricEqTypeSettingFragment(HttpUrl.FRAGMENT_ENCODE_SET, i)).addToBackStack("ParametricEqTypeSettingFragment").commit();
            FragmentActivity activity = getActivity();
            Intrinsics.checkNotNull(activity, "null cannot be cast to non-null type com.ibasso.volume.MainActivity2");
            ((MainActivity2) activity).setSettingIv();
        }
    }

    private final void updateAmpMode(int i) {
        TextView textView;
        if (i == 1) {
            FragmentDc04uBinding fragmentDc04uBinding = this._binding;
            textView = fragmentDc04uBinding != null ? fragmentDc04uBinding.apmModeTipsTv : null;
            if (textView == null) {
                return;
            }
            textView.setText("Class H");
            return;
        }
        FragmentDc04uBinding fragmentDc04uBinding2 = this._binding;
        textView = fragmentDc04uBinding2 != null ? fragmentDc04uBinding2.apmModeTipsTv : null;
        if (textView == null) {
            return;
        }
        textView.setText("Class AB");
    }

    private final void updateBalance(int i, boolean z) {
        TextView textView;
        BalanceView balanceView;
        BalanceView balanceView2;
        FragmentDc04uBinding fragmentDc04uBinding;
        BalanceView balanceView3;
        if (!z && (fragmentDc04uBinding = this._binding) != null && (balanceView3 = fragmentDc04uBinding.balanceSeekBar) != null) {
            balanceView3.setSeekBarProgressClickListener(null);
        }
        int i2 = i - 10;
        if (i2 > 0) {
            FragmentDc04uBinding fragmentDc04uBinding2 = this._binding;
            textView = fragmentDc04uBinding2 != null ? fragmentDc04uBinding2.balanceValueTv : null;
            if (textView != null) {
                StringBuilder sb = new StringBuilder();
                sb.append('R');
                sb.append(i2);
                textView.setText(sb.toString());
            }
        } else if (i2 < 0) {
            FragmentDc04uBinding fragmentDc04uBinding3 = this._binding;
            textView = fragmentDc04uBinding3 != null ? fragmentDc04uBinding3.balanceValueTv : null;
            if (textView != null) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append('L');
                sb2.append(-i2);
                textView.setText(sb2.toString());
            }
        } else {
            FragmentDc04uBinding fragmentDc04uBinding4 = this._binding;
            textView = fragmentDc04uBinding4 != null ? fragmentDc04uBinding4.balanceValueTv : null;
            if (textView != null) {
                textView.setText("L=R");
            }
        }
        FragmentDc04uBinding fragmentDc04uBinding5 = this._binding;
        if (fragmentDc04uBinding5 != null && (balanceView2 = fragmentDc04uBinding5.balanceSeekBar) != null) {
            balanceView2.setProgress(i2);
        }
        FragmentDc04uBinding fragmentDc04uBinding6 = this._binding;
        if (fragmentDc04uBinding6 == null || (balanceView = fragmentDc04uBinding6.balanceSeekBar) == null) {
            return;
        }
        balanceView.setSeekBarProgressClickListener(new BalanceView.ISeekBarProgressClickListener() {
            @Override
            public void onSeekBarChanged(@Nullable BalanceView balanceView4, int i3) {
                FragmentDc04uBinding fragmentDc04uBinding7;
                TextView textView2;
                FragmentDc04uBinding fragmentDc04uBinding8;
                FragmentDc04uBinding fragmentDc04uBinding9;
                UacManager.getUacManager().setBalanceVolume(i3 + 10);
                if (i3 > 0) {
                    fragmentDc04uBinding9 = Dc04uFragment.this._binding;
                    textView2 = fragmentDc04uBinding9 != null ? fragmentDc04uBinding9.balanceValueTv : null;
                    if (textView2 == null) {
                        return;
                    }
                    textView2.setText("R-" + i3);
                } else if (i3 >= 0) {
                    fragmentDc04uBinding7 = Dc04uFragment.this._binding;
                    textView2 = fragmentDc04uBinding7 != null ? fragmentDc04uBinding7.balanceValueTv : null;
                    if (textView2 == null) {
                        return;
                    }
                    textView2.setText("L=R");
                } else {
                    fragmentDc04uBinding8 = Dc04uFragment.this._binding;
                    textView2 = fragmentDc04uBinding8 != null ? fragmentDc04uBinding8.balanceValueTv : null;
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

    public static void updateBalance$default(Dc04uFragment dc04uFragment, int i, boolean z, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z = false;
        }
        dc04uFragment.updateBalance(i, z);
    }

    private final void updateDRE(int i, boolean z) {
        String string = i != 1 ? i != 2 ? getString(R.string.dre_off) : getString(R.string.dre_opt) : getString(R.string.dre_on);
        Intrinsics.checkNotNullExpressionValue(string, "when (value) {\n         …)\n            }\n        }");
        FragmentDc04uBinding fragmentDc04uBinding = this._binding;
        TextView textView = fragmentDc04uBinding != null ? fragmentDc04uBinding.dreTipsTv : null;
        if (textView == null) {
            return;
        }
        textView.setText(string);
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
        FragmentDc04uBinding fragmentDc04uBinding;
        RadioGroup radioGroup7;
        if (!z && (fragmentDc04uBinding = this._binding) != null && (radioGroup7 = fragmentDc04uBinding.digitalFilterRg) != null) {
            radioGroup7.setOnCheckedChangeListener(null);
        }
        if (i == 1) {
            FragmentDc04uBinding fragmentDc04uBinding2 = this._binding;
            if (fragmentDc04uBinding2 != null && (radioGroup = fragmentDc04uBinding2.digitalFilterRg) != null) {
                radioGroup.check(R.id.digitalFilter2Rg);
            }
            FragmentDc04uBinding fragmentDc04uBinding3 = this._binding;
            if (fragmentDc04uBinding3 != null && (textView = fragmentDc04uBinding3.digitalFilterValueTv) != null) {
                textView.setText(R.string.digital_filter_dc07pro_two);
            }
        } else if (i == 2) {
            FragmentDc04uBinding fragmentDc04uBinding4 = this._binding;
            if (fragmentDc04uBinding4 != null && (radioGroup3 = fragmentDc04uBinding4.digitalFilterRg) != null) {
                radioGroup3.check(R.id.digitalFilter3Rg);
            }
            FragmentDc04uBinding fragmentDc04uBinding5 = this._binding;
            if (fragmentDc04uBinding5 != null && (textView2 = fragmentDc04uBinding5.digitalFilterValueTv) != null) {
                textView2.setText(R.string.digital_filter_dc07pro_three);
            }
        } else if (i == 3) {
            FragmentDc04uBinding fragmentDc04uBinding6 = this._binding;
            if (fragmentDc04uBinding6 != null && (radioGroup4 = fragmentDc04uBinding6.digitalFilterRg) != null) {
                radioGroup4.check(R.id.digitalFilter4Rg);
            }
            FragmentDc04uBinding fragmentDc04uBinding7 = this._binding;
            if (fragmentDc04uBinding7 != null && (textView3 = fragmentDc04uBinding7.digitalFilterValueTv) != null) {
                textView3.setText(R.string.digital_filter_dc07pro_four);
            }
        } else if (i != 4) {
            FragmentDc04uBinding fragmentDc04uBinding8 = this._binding;
            if (fragmentDc04uBinding8 != null && (radioGroup6 = fragmentDc04uBinding8.digitalFilterRg) != null) {
                radioGroup6.check(R.id.digitalFilter1Rg);
            }
            FragmentDc04uBinding fragmentDc04uBinding9 = this._binding;
            if (fragmentDc04uBinding9 != null && (textView5 = fragmentDc04uBinding9.digitalFilterValueTv) != null) {
                textView5.setText(R.string.digital_filter_dc07pro_one);
            }
        } else {
            FragmentDc04uBinding fragmentDc04uBinding10 = this._binding;
            if (fragmentDc04uBinding10 != null && (radioGroup5 = fragmentDc04uBinding10.digitalFilterRg) != null) {
                radioGroup5.check(R.id.digitalFilter5Rg);
            }
            FragmentDc04uBinding fragmentDc04uBinding11 = this._binding;
            if (fragmentDc04uBinding11 != null && (textView4 = fragmentDc04uBinding11.digitalFilterValueTv) != null) {
                textView4.setText(R.string.digital_filter_dc07pro_five);
            }
        }
        FragmentDc04uBinding fragmentDc04uBinding12 = this._binding;
        if (fragmentDc04uBinding12 == null || (radioGroup2 = fragmentDc04uBinding12.digitalFilterRg) == null) {
            return;
        }
        radioGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public final void onCheckedChanged(RadioGroup radioGroup8, int i2) {
                Dc04uFragment.updateDigitalFilter$lambda$19(Dc04uFragment.this, radioGroup8, i2);
            }
        });
    }

    public static void updateDigitalFilter$default(Dc04uFragment dc04uFragment, int i, boolean z, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z = true;
        }
        dc04uFragment.updateDigitalFilter(i, z);
    }

    public static final void updateDigitalFilter$lambda$19(Dc04uFragment this$0, RadioGroup radioGroup, int i) {
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
                FragmentDc04uBinding fragmentDc04uBinding = this$0._binding;
                if (fragmentDc04uBinding == null || (textView = fragmentDc04uBinding.digitalFilterValueTv) == null) {
                    return;
                }
                textView.setText(R.string.digital_filter_dc07pro_two);
                return;
            case R.id.digitalFilter3Rg:
                UacManager.getUacManager().setDigitalFilter(2);
                FragmentDc04uBinding fragmentDc04uBinding2 = this$0._binding;
                if (fragmentDc04uBinding2 == null || (textView2 = fragmentDc04uBinding2.digitalFilterValueTv) == null) {
                    return;
                }
                textView2.setText(R.string.digital_filter_dc07pro_three);
                return;
            case R.id.digitalFilter4Rg:
                UacManager.getUacManager().setDigitalFilter(3);
                FragmentDc04uBinding fragmentDc04uBinding3 = this$0._binding;
                if (fragmentDc04uBinding3 == null || (textView3 = fragmentDc04uBinding3.digitalFilterValueTv) == null) {
                    return;
                }
                textView3.setText(R.string.digital_filter_dc07pro_four);
                return;
            case R.id.digitalFilter5Rg:
                UacManager.getUacManager().setDigitalFilter(4);
                FragmentDc04uBinding fragmentDc04uBinding4 = this$0._binding;
                if (fragmentDc04uBinding4 == null || (textView4 = fragmentDc04uBinding4.digitalFilterValueTv) == null) {
                    return;
                }
                textView4.setText(R.string.digital_filter_dc07pro_five);
                return;
            default:
                UacManager.getUacManager().setDigitalFilter(0);
                FragmentDc04uBinding fragmentDc04uBinding5 = this$0._binding;
                if (fragmentDc04uBinding5 == null || (textView5 = fragmentDc04uBinding5.digitalFilterValueTv) == null) {
                    return;
                }
                textView5.setText(R.string.digital_filter_dc07pro_one);
                return;
        }
    }

    private final void updateEQ(int i, boolean z) {
        String string;
        switch (i) {
            case 1:
                string = getString(R.string.eq_one);
                break;
            case 2:
                string = getString(R.string.eq_two);
                break;
            case 3:
                string = getString(R.string.eq_three);
                break;
            case 4:
                string = getString(R.string.eq_four);
                break;
            case 5:
                string = getString(R.string.eq_five);
                break;
            case 6:
                string = getString(R.string.eq_six);
                break;
            case 7:
                string = getString(R.string.eq_seven);
                break;
            case 8:
                string = getString(R.string.eq8);
                break;
            case 9:
                string = getString(R.string.eq9);
                break;
            case 10:
                string = getString(R.string.eq10);
                break;
            default:
                string = getString(R.string.eq_off);
                break;
        }
        Intrinsics.checkNotNullExpressionValue(string, "when (value) {\n         …)\n            }\n        }");
        FragmentDc04uBinding fragmentDc04uBinding = this._binding;
        TextView textView = fragmentDc04uBinding != null ? fragmentDc04uBinding.eqTipsTv : null;
        if (textView == null) {
            return;
        }
        textView.setText(string);
    }

    private final void updateGain(int i, boolean z) {
        ThirdSwitchBar thirdSwitchBar;
        ThirdSwitchBar thirdSwitchBar2;
        FragmentDc04uBinding fragmentDc04uBinding;
        ThirdSwitchBar thirdSwitchBar3;
        if (!z && (fragmentDc04uBinding = this._binding) != null && (thirdSwitchBar3 = fragmentDc04uBinding.gainThirdSwitchBar) != null) {
            thirdSwitchBar3.setSeekTouchListener(null);
        }
        FragmentDc04uBinding fragmentDc04uBinding2 = this._binding;
        if (fragmentDc04uBinding2 != null && (thirdSwitchBar2 = fragmentDc04uBinding2.gainThirdSwitchBar) != null) {
            thirdSwitchBar2.setStatus(SpStorage.getInstance().getDc04uGain());
        }
        updateThreeGainTipsViewStatus$default(this, SpStorage.getInstance().getDc04uGain(), false, 2, null);
        FragmentDc04uBinding fragmentDc04uBinding3 = this._binding;
        if (fragmentDc04uBinding3 == null || (thirdSwitchBar = fragmentDc04uBinding3.gainThirdSwitchBar) == null) {
            return;
        }
        thirdSwitchBar.setSeekTouchListener(new ThirdSwitchBar.SeekTouchListener() {
            @Override
            public void touchEnd(@Nullable SeekBar seekBar) {
                Dc04uFragment.this.updateThreeGainTipsViewStatus(2, true);
            }

            @Override
            public void touchMiddle(@Nullable SeekBar seekBar) {
                Dc04uFragment.this.updateThreeGainTipsViewStatus(1, true);
            }

            @Override
            public void touchTop(@Nullable SeekBar seekBar) {
                Dc04uFragment.this.updateThreeGainTipsViewStatus(0, true);
            }
        });
    }

    public static void updateGain$default(Dc04uFragment dc04uFragment, int i, boolean z, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z = true;
        }
        dc04uFragment.updateGain(i, z);
    }

    private final void updateLanguage(int i, boolean z) {
        RadioGroup radioGroup;
        RadioGroup radioGroup2;
        RadioGroup radioGroup3;
        RadioGroup radioGroup4;
        FragmentDc04uBinding fragmentDc04uBinding;
        RadioGroup radioGroup5;
        if (!z && (fragmentDc04uBinding = this._binding) != null && (radioGroup5 = fragmentDc04uBinding.languageRg) != null) {
            radioGroup5.setOnCheckedChangeListener(null);
        }
        if (i == 1) {
            FragmentDc04uBinding fragmentDc04uBinding2 = this._binding;
            if (fragmentDc04uBinding2 != null && (radioGroup = fragmentDc04uBinding2.languageRg) != null) {
                radioGroup.check(R.id.language1Rg);
            }
        } else if (i != 2) {
            FragmentDc04uBinding fragmentDc04uBinding3 = this._binding;
            if (fragmentDc04uBinding3 != null && (radioGroup4 = fragmentDc04uBinding3.languageRg) != null) {
                radioGroup4.check(R.id.language0Rg);
            }
        } else {
            FragmentDc04uBinding fragmentDc04uBinding4 = this._binding;
            if (fragmentDc04uBinding4 != null && (radioGroup3 = fragmentDc04uBinding4.languageRg) != null) {
                radioGroup3.check(R.id.language2Rg);
            }
        }
        FragmentDc04uBinding fragmentDc04uBinding5 = this._binding;
        if (fragmentDc04uBinding5 == null || (radioGroup2 = fragmentDc04uBinding5.languageRg) == null) {
            return;
        }
        radioGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public final void onCheckedChanged(RadioGroup radioGroup6, int i2) {
                Dc04uFragment.updateLanguage$lambda$18(radioGroup6, i2);
            }
        });
    }

    public static void updateLanguage$default(Dc04uFragment dc04uFragment, int i, boolean z, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z = false;
        }
        dc04uFragment.updateLanguage(i, z);
    }

    public static final void updateLanguage$lambda$18(RadioGroup radioGroup, int i) {
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
        FragmentDc04uBinding fragmentDc04uBinding;
        Switch r4;
        if (!z && (fragmentDc04uBinding = this._binding) != null && (r4 = fragmentDc04uBinding.micModeSwitch) != null) {
            r4.setOnCheckedChangeListener(null);
        }
        FragmentDc04uBinding fragmentDc04uBinding2 = this._binding;
        Switch r0 = fragmentDc04uBinding2 != null ? fragmentDc04uBinding2.micModeSwitch : null;
        if (r0 != null) {
            r0.setChecked(i == 1);
        }
        if (i == 1) {
            FragmentDc04uBinding fragmentDc04uBinding3 = this._binding;
            if (fragmentDc04uBinding3 != null && (textView2 = fragmentDc04uBinding3.micModeSwitchTv) != null) {
                textView2.setText(R.string.enable);
            }
        } else {
            FragmentDc04uBinding fragmentDc04uBinding4 = this._binding;
            if (fragmentDc04uBinding4 != null && (textView = fragmentDc04uBinding4.micModeSwitchTv) != null) {
                textView.setText(R.string.disable);
            }
        }
        FragmentDc04uBinding fragmentDc04uBinding5 = this._binding;
        if (fragmentDc04uBinding5 == null || (r3 = fragmentDc04uBinding5.micModeSwitch) == null) {
            return;
        }
        r3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z2) {
                Dc04uFragment.updateMicMode$lambda$17(Dc04uFragment.this, compoundButton, z2);
            }
        });
    }

    public static void updateMicMode$default(Dc04uFragment dc04uFragment, int i, boolean z, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z = false;
        }
        dc04uFragment.updateMicMode(i, z);
    }

    public static final void updateMicMode$lambda$17(Dc04uFragment this$0, CompoundButton buttonView, boolean z) {
        TextView textView;
        TextView textView2;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(buttonView, "buttonView");
        if (!z) {
            UacManager.getUacManager().setMicMode(0);
            FragmentDc04uBinding fragmentDc04uBinding = this$0._binding;
            if (fragmentDc04uBinding == null || (textView = fragmentDc04uBinding.micModeSwitchTv) == null) {
                return;
            }
            textView.setText(R.string.disable);
            return;
        }
        UacManager.getUacManager().setMicMode(1);
        FragmentDc04uBinding fragmentDc04uBinding2 = this$0._binding;
        if (fragmentDc04uBinding2 != null && (textView2 = fragmentDc04uBinding2.micModeSwitchTv) != null) {
            textView2.setText(R.string.enable);
        }
        if (UacManager.getUacManager().getCodecExternal() == 1) {
            this$0.updateSpdif(0, true);
        }
    }

    private final void updateOpVoltage(int i) {
        StringBuilder sb = new StringBuilder();
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        String format = String.format("%.1f", Arrays.copyOf(new Object[]{Float.valueOf((i * 0.1f) + 4.0f)}, 1));
        Intrinsics.checkNotNullExpressionValue(format, "format(format, *args)");
        sb.append(format);
        sb.append('V');
        String sb2 = sb.toString();
        FragmentDc04uBinding fragmentDc04uBinding = this._binding;
        TextView textView = fragmentDc04uBinding != null ? fragmentDc04uBinding.opVoltageTipsTv : null;
        if (textView == null) {
            return;
        }
        textView.setText(sb2);
    }

    private final void updateScreenRation(int i, boolean z) {
        TextView textView;
        Switch r3;
        TextView textView2;
        FragmentDc04uBinding fragmentDc04uBinding;
        Switch r4;
        if (!z && (fragmentDc04uBinding = this._binding) != null && (r4 = fragmentDc04uBinding.screenRotateSwitch) != null) {
            r4.setOnCheckedChangeListener(null);
        }
        FragmentDc04uBinding fragmentDc04uBinding2 = this._binding;
        Switch r0 = fragmentDc04uBinding2 != null ? fragmentDc04uBinding2.screenRotateSwitch : null;
        if (r0 != null) {
            r0.setChecked(i == 1);
        }
        if (i == 1) {
            FragmentDc04uBinding fragmentDc04uBinding3 = this._binding;
            if (fragmentDc04uBinding3 != null && (textView2 = fragmentDc04uBinding3.screenRotateSwitchTv) != null) {
                textView2.setText(R.string.on);
            }
        } else {
            FragmentDc04uBinding fragmentDc04uBinding4 = this._binding;
            if (fragmentDc04uBinding4 != null && (textView = fragmentDc04uBinding4.screenRotateSwitchTv) != null) {
                textView.setText(R.string.off);
            }
        }
        FragmentDc04uBinding fragmentDc04uBinding5 = this._binding;
        if (fragmentDc04uBinding5 == null || (r3 = fragmentDc04uBinding5.screenRotateSwitch) == null) {
            return;
        }
        r3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z2) {
                Dc04uFragment.updateScreenRation$lambda$21(Dc04uFragment.this, compoundButton, z2);
            }
        });
    }

    public static void updateScreenRation$default(Dc04uFragment dc04uFragment, int i, boolean z, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z = true;
        }
        dc04uFragment.updateScreenRation(i, z);
    }

    public static final void updateScreenRation$lambda$21(Dc04uFragment this$0, CompoundButton buttonView, boolean z) {
        TextView textView;
        TextView textView2;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(buttonView, "buttonView");
        if (z) {
            UacManager.getUacManager().setScreenRotation(1);
            FragmentDc04uBinding fragmentDc04uBinding = this$0._binding;
            if (fragmentDc04uBinding == null || (textView2 = fragmentDc04uBinding.screenRotateSwitchTv) == null) {
                return;
            }
            textView2.setText(R.string.on);
            return;
        }
        UacManager.getUacManager().setScreenRotation(0);
        FragmentDc04uBinding fragmentDc04uBinding2 = this$0._binding;
        if (fragmentDc04uBinding2 == null || (textView = fragmentDc04uBinding2.screenRotateSwitchTv) == null) {
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
        FragmentDc04uBinding fragmentDc04uBinding;
        RadioGroup radioGroup6;
        if (!z && (fragmentDc04uBinding = this._binding) != null && (radioGroup6 = fragmentDc04uBinding.screenTimeRg) != null) {
            radioGroup6.setOnCheckedChangeListener(null);
        }
        if (i == 1) {
            FragmentDc04uBinding fragmentDc04uBinding2 = this._binding;
            if (fragmentDc04uBinding2 != null && (radioGroup = fragmentDc04uBinding2.screenTimeRg) != null) {
                radioGroup.check(R.id.screenTime1Rg);
            }
        } else if (i == 2) {
            FragmentDc04uBinding fragmentDc04uBinding3 = this._binding;
            if (fragmentDc04uBinding3 != null && (radioGroup3 = fragmentDc04uBinding3.screenTimeRg) != null) {
                radioGroup3.check(R.id.screenTime2Rg);
            }
        } else if (i != 3) {
            FragmentDc04uBinding fragmentDc04uBinding4 = this._binding;
            if (fragmentDc04uBinding4 != null && (radioGroup5 = fragmentDc04uBinding4.screenTimeRg) != null) {
                radioGroup5.check(R.id.screenTime0Rg);
            }
        } else {
            FragmentDc04uBinding fragmentDc04uBinding5 = this._binding;
            if (fragmentDc04uBinding5 != null && (radioGroup4 = fragmentDc04uBinding5.screenTimeRg) != null) {
                radioGroup4.check(R.id.screenTime3Rg);
            }
        }
        FragmentDc04uBinding fragmentDc04uBinding6 = this._binding;
        if (fragmentDc04uBinding6 == null || (radioGroup2 = fragmentDc04uBinding6.screenTimeRg) == null) {
            return;
        }
        radioGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public final void onCheckedChanged(RadioGroup radioGroup7, int i2) {
                Dc04uFragment.updateScreenTime$lambda$24(radioGroup7, i2);
            }
        });
    }

    public static void updateScreenTime$default(Dc04uFragment dc04uFragment, int i, boolean z, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z = false;
        }
        dc04uFragment.updateScreenTime(i, z);
    }

    public static final void updateScreenTime$lambda$24(RadioGroup radioGroup, int i) {
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
        FragmentDc04uBinding fragmentDc04uBinding;
        Switch r4;
        if (!z && (fragmentDc04uBinding = this._binding) != null && (r4 = fragmentDc04uBinding.codecExternalSwitch) != null) {
            r4.setOnCheckedChangeListener(null);
        }
        FragmentDc04uBinding fragmentDc04uBinding2 = this._binding;
        Switch r0 = fragmentDc04uBinding2 != null ? fragmentDc04uBinding2.codecExternalSwitch : null;
        if (r0 != null) {
            r0.setChecked(i == 1);
        }
        if (i == 1) {
            FragmentDc04uBinding fragmentDc04uBinding3 = this._binding;
            if (fragmentDc04uBinding3 != null && (textView2 = fragmentDc04uBinding3.codecExternalSwitchTv) != null) {
                textView2.setText(R.string.on);
            }
        } else {
            FragmentDc04uBinding fragmentDc04uBinding4 = this._binding;
            if (fragmentDc04uBinding4 != null && (textView = fragmentDc04uBinding4.codecExternalSwitchTv) != null) {
                textView.setText(R.string.off);
            }
        }
        FragmentDc04uBinding fragmentDc04uBinding5 = this._binding;
        if (fragmentDc04uBinding5 == null || (r3 = fragmentDc04uBinding5.codecExternalSwitch) == null) {
            return;
        }
        r3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z2) {
                Dc04uFragment.updateSpdif$lambda$23(Dc04uFragment.this, compoundButton, z2);
            }
        });
    }

    public static void updateSpdif$default(Dc04uFragment dc04uFragment, int i, boolean z, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z = true;
        }
        dc04uFragment.updateSpdif(i, z);
    }

    public static final void updateSpdif$lambda$23(Dc04uFragment this$0, CompoundButton buttonView, boolean z) {
        TextView textView;
        TextView textView2;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(buttonView, "buttonView");
        if (!z) {
            UacManager.getUacManager().setCodecExternal(0);
            FragmentDc04uBinding fragmentDc04uBinding = this$0._binding;
            if (fragmentDc04uBinding == null || (textView = fragmentDc04uBinding.codecExternalSwitchTv) == null) {
                return;
            }
            textView.setText(R.string.off);
            return;
        }
        UacManager.getUacManager().setCodecExternal(1);
        FragmentDc04uBinding fragmentDc04uBinding2 = this$0._binding;
        if (fragmentDc04uBinding2 != null && (textView2 = fragmentDc04uBinding2.codecExternalSwitchTv) != null) {
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
            FragmentDc04uBinding fragmentDc04uBinding = this._binding;
            if (fragmentDc04uBinding != null && (textView = fragmentDc04uBinding.gainTipsTv) != null) {
                textView.setText(R.string.settings_gain_middle);
            }
        } else if (i != 2) {
            FragmentDc04uBinding fragmentDc04uBinding2 = this._binding;
            if (fragmentDc04uBinding2 != null && (textView3 = fragmentDc04uBinding2.gainTipsTv) != null) {
                textView3.setText(R.string.settings_gain_low);
            }
        } else {
            FragmentDc04uBinding fragmentDc04uBinding3 = this._binding;
            if (fragmentDc04uBinding3 != null && (textView2 = fragmentDc04uBinding3.gainTipsTv) != null) {
                textView2.setText(R.string.settings_gain_high);
            }
        }
        if (z) {
            UacManager.getUacManager().setGain(i);
        }
    }

    public static void updateThreeGainTipsViewStatus$default(Dc04uFragment dc04uFragment, int i, boolean z, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z = false;
        }
        dc04uFragment.updateThreeGainTipsViewStatus(i, z);
    }

    private final void updateUacVersion(int i, boolean z) {
    }

    public static void updateUacVersion$default(Dc04uFragment dc04uFragment, int i, boolean z, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z = false;
        }
        dc04uFragment.updateUacVersion(i, z);
    }

    @Override
    @Nullable
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        FragmentDc04uBinding inflate = FragmentDc04uBinding.inflate(inflater, viewGroup, false);
        this._binding = inflate;
        if (inflate != null) {
            return inflate.getRoot();
        }
        return null;
    }

    @Subscribe(sticky = EmbeddingCompat.DEBUG, threadMode = ThreadMode.MAIN)
    public final void onDc06VolumeEvent(@Nullable Dc06VolumeEvent dc06VolumeEvent) {
        FragmentDc04uBinding fragmentDc04uBinding = this._binding;
        SeekBar seekBar = fragmentDc04uBinding != null ? fragmentDc04uBinding.volumeSeekBar : null;
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
            FragmentDc04uBinding fragmentDc04uBinding = this._binding;
            TextView textView = fragmentDc04uBinding != null ? fragmentDc04uBinding.fpgaVersionTv : null;
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
                updateEQ(nativeReportEvent.value1, false);
                updateDRE(nativeReportEvent.value2, false);
            } else if (i == 96) {
                updateSpdif(nativeReportEvent.value, false);
                updateBalance(nativeReportEvent.value1, false);
                updateGain(nativeReportEvent.value2, false);
            } else if (i == 97) {
                updateScreenRation(nativeReportEvent.value, false);
                updateOpVoltage(nativeReportEvent.value1);
                updateScreenTime(nativeReportEvent.value2, false);
                updateLanguage(nativeReportEvent.value3, false);
            } else if (i == 99) {
                updateUacVersion(nativeReportEvent.value, false);
                updateAmpMode(nativeReportEvent.value1);
                updateMicMode(nativeReportEvent.value2, false);
            } else if (i == -1) {
                int i2 = nativeReportEvent.value;
                if (i2 == -1 || i2 == 0) {
                    if (i2 == -1) {
                        FragmentDc04uBinding fragmentDc04uBinding = this._binding;
                        if (fragmentDc04uBinding != null && (textView4 = fragmentDc04uBinding.manufacturerTv) != null) {
                            textView4.setTextColor(Color.parseColor("#FF0000"));
                        }
                        FragmentDc04uBinding fragmentDc04uBinding2 = this._binding;
                        if (fragmentDc04uBinding2 == null || (textView3 = fragmentDc04uBinding2.productNameTv) == null) {
                            return;
                        }
                        textView3.setTextColor(Color.parseColor("#FF0000"));
                        return;
                    }
                    FragmentDc04uBinding fragmentDc04uBinding3 = this._binding;
                    if (fragmentDc04uBinding3 != null && (textView2 = fragmentDc04uBinding3.manufacturerTv) != null) {
                        textView2.setTextColor(Color.parseColor("#ebebeb"));
                    }
                    FragmentDc04uBinding fragmentDc04uBinding4 = this._binding;
                    if (fragmentDc04uBinding4 == null || (textView = fragmentDc04uBinding4.productNameTv) == null) {
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
        FragmentDc04uBinding fragmentDc04uBinding = this._binding;
        TextView textView = fragmentDc04uBinding != null ? fragmentDc04uBinding.volumeValueTv : null;
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
        AlertDialog alertDialog;
        AlertDialog alertDialog2;
        AlertDialog alertDialog3;
        AlertDialog alertDialog4;
        super.onStop();
        AlertDialog alertDialog5 = this.opVoltageDialog;
        if (alertDialog5 != null && alertDialog5.isShowing() && (alertDialog4 = this.opVoltageDialog) != null) {
            alertDialog4.dismiss();
        }
        AlertDialog alertDialog6 = this.ampModeDialog;
        if (alertDialog6 != null && alertDialog6.isShowing() && (alertDialog3 = this.ampModeDialog) != null) {
            alertDialog3.dismiss();
        }
        AlertDialog alertDialog7 = this.eqResetDialog;
        if (alertDialog7 != null && alertDialog7.isShowing() && (alertDialog2 = this.eqResetDialog) != null) {
            alertDialog2.dismiss();
        }
        AlertDialog alertDialog8 = this.dreDialog;
        if (alertDialog8 == null || !alertDialog8.isShowing() || (alertDialog = this.dreDialog) == null) {
            return;
        }
        alertDialog.dismiss();
    }

    @Override
    public void onStopTrackingTouch(@Nullable SeekBar seekBar) {
        this.isTouched = false;
    }

    @Override
    @SuppressLint({"SetTextI18n"})
    public void onViewCreated(@NotNull View view, @Nullable Bundle bundle) {
        ConstraintLayout constraintLayout;
        ConstraintLayout constraintLayout2;
        ConstraintLayout constraintLayout3;
        ConstraintLayout constraintLayout4;
        TextView textView;
        SeekBar seekBar;
        Intrinsics.checkNotNullParameter(view, "view");
        super.onViewCreated(view, bundle);
        FragmentDc04uBinding fragmentDc04uBinding = this._binding;
        if (fragmentDc04uBinding != null && (seekBar = fragmentDc04uBinding.volumeSeekBar) != null) {
            seekBar.setOnSeekBarChangeListener(this);
        }
        FragmentDc04uBinding fragmentDc04uBinding2 = this._binding;
        if (fragmentDc04uBinding2 != null && (textView = fragmentDc04uBinding2.manufacturerTv) != null) {
            textView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(@Nullable View view2) {
                    EqualizerManager.getInstance().dumpFlash();
                    Toast.makeText(Dc04uFragment.this.requireContext(), "dumpFlash", 0).show();
                    return true;
                }
            });
        }
        updateDigitalFilter(UacManager.getUacManager().getDigitalFilter() & 255, false);
        updateGain(SpStorage.getInstance().getDc04uGain(), false);
        updateScreenRation(SpStorage.getInstance().getDc04uScreenRotation(), false);
        updateSpdif(UacManager.getUacManager().getCodecExternal(), false);
        updateMicMode(UacManager.getUacManager().getMicMode(), false);
        FragmentDc04uBinding fragmentDc04uBinding3 = this._binding;
        if (fragmentDc04uBinding3 != null && (constraintLayout4 = fragmentDc04uBinding3.settingsOpVoltageLayout) != null) {
            constraintLayout4.setOnClickListener(new View.OnClickListener() {
                @Override
                public final void onClick(View view2) {
                    Dc04uFragment.onViewCreated$lambda$0(Dc04uFragment.this, view2);
                }
            });
        }
        FragmentDc04uBinding fragmentDc04uBinding4 = this._binding;
        if (fragmentDc04uBinding4 != null && (constraintLayout3 = fragmentDc04uBinding4.settingsEqLayout) != null) {
            constraintLayout3.setOnClickListener(new View.OnClickListener() {
                @Override
                public final void onClick(View view2) {
                    Dc04uFragment.onViewCreated$lambda$1(Dc04uFragment.this, view2);
                }
            });
        }
        FragmentDc04uBinding fragmentDc04uBinding5 = this._binding;
        if (fragmentDc04uBinding5 != null && (constraintLayout2 = fragmentDc04uBinding5.settingsAmpModeLayout) != null) {
            constraintLayout2.setOnClickListener(new View.OnClickListener() {
                @Override
                public final void onClick(View view2) {
                    Dc04uFragment.onViewCreated$lambda$2(Dc04uFragment.this, view2);
                }
            });
        }
        FragmentDc04uBinding fragmentDc04uBinding6 = this._binding;
        if (fragmentDc04uBinding6 != null && (constraintLayout = fragmentDc04uBinding6.settingsDreLayout) != null) {
            constraintLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public final void onClick(View view2) {
                    Dc04uFragment.onViewCreated$lambda$3(Dc04uFragment.this, view2);
                }
            });
        }
        EventBus.getDefault().register(this);
    }
}
