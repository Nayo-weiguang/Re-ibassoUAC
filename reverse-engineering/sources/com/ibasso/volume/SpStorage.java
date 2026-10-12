package com.ibasso.volume;

import android.content.Context;
import android.content.SharedPreferences;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import okhttp3.HttpUrl;
import org.jetbrains.annotations.NotNull;
public class SpStorage {
    private static final String SP_KEY_D18_AMP_MODE = "key_d18_amp_mode";
    private static final String SP_KEY_D18_BALANCE = "key_d18_balance";
    private static final String SP_KEY_D18_DAC_GAIN = "key_d18_dac_gain";
    private static final String SP_KEY_D18_DIGITAL_FILTER = "key_d18_digital_filter";
    private static final String SP_KEY_D18_GAIN = "key_d18_gain";
    private static final String SP_KEY_D18_INPUT_MODE = "key_d18_input_mode";
    private static final String SP_KEY_D18_LANGUAGE = "key_d18_language";
    private static final String SP_KEY_D18_MIC_MODE = "key_d18_mic_mode";
    private static final String SP_KEY_D18_SCREEN_ROTATION = "key_d18_screen_rotation";
    private static final String SP_KEY_D18_SCREEN_TIME = "key_d18_screen_time";
    private static final String SP_KEY_D18_SPDIF_MODE = "key_d18_spdif_mode";
    private static final String SP_KEY_D18_THEME = "key_d18_theme";
    private static final String SP_KEY_D18_UAC_VERSION = "key_d18_uac_version";
    private static final String SP_KEY_D18_VOLUME = "key_d18_volume";
    private static final String SP_KEY_DC03PRO_BALANCE = "key_dc03pro_balance";
    private static final String SP_KEY_DC03PRO_DIGITAL_FILTER = "key_dc03pro_digital_filter";
    private static final String SP_KEY_DC03PRO_VOLUME = "key_dc03pro_volume";
    private static final String SP_KEY_DC03_RPO_GAIN = "key_dc03pro_gain";
    private static final String SP_KEY_DC03_RPO_OUTPUT = "key_dc03pro_output";
    private static final String SP_KEY_DC04PRO_BALANCE = "key_dc04pro_balance";
    private static final String SP_KEY_DC04PRO_DIGITAL_FILTER = "key_dc04pro_digital_filter";
    private static final String SP_KEY_DC04PRO_VOLUME = "key_dc04pro_volume";
    private static final String SP_KEY_DC04U_AMP_MODE = "key_dc04u_amp_mode";
    private static final String SP_KEY_DC04U_BALANCE = "key_dc04u_balance";
    private static final String SP_KEY_DC04U_DIGITAL_FILTER = "key_dc04u_digital_filter";
    private static final String SP_KEY_DC04U_EQ = "key_dc04u_eq";
    private static final String SP_KEY_DC04U_GAIN = "key_dc04u_gain";
    private static final String SP_KEY_DC04U_KEY_CONTROL_STATUS = "key_dc04u_key_control_status";
    private static final String SP_KEY_DC04U_LANGUAGE = "key_dc04u_language";
    private static final String SP_KEY_DC04U_MIC_MODE = "key_dc04u_mic_mode";
    private static final String SP_KEY_DC04U_OP_VOLTAGE = "key_dc04u_op_voltage";
    private static final String SP_KEY_DC04U_OUTPUT = "key_dc04u_output";
    private static final String SP_KEY_DC04U_SCREEN_ROTATION = "key_dc04u_screen_rotation";
    private static final String SP_KEY_DC04U_SCREEN_TIME = "key_dc04u_screen_time";
    private static final String SP_KEY_DC04U_SPDIF_MODE = "key_dc04u_spdif_mode";
    private static final String SP_KEY_DC04U_THEME = "key_dc04u_theme";
    private static final String SP_KEY_DC04U_THEME_COLOR = "key_dc04u_theme_color";
    private static final String SP_KEY_DC04U_UAC_VERSION = "key_dc04u_uac_version";
    private static final String SP_KEY_DC04U_VOLUME = "key_dc04u_volume";
    private static final String SP_KEY_DC04_RPO_CODEC_EXTERNAL = "key_dc04pro_codec_external";
    private static final String SP_KEY_DC04_RPO_GAIN = "key_dc04pro_gain";
    private static final String SP_KEY_DC04_RPO_OUTPUT = "key_dc04pro_output";
    private static final String SP_KEY_DC06_BALANCE = "key_dc06_balance";
    private static final String SP_KEY_DC06_DIGITAL_FILTER = "key_dc06_digital_filter";
    private static final String SP_KEY_DC06_PRO_BALANCE = "key_dc06pro_balance";
    private static final String SP_KEY_DC06_PRO_DIGITAL_FILTER = "key_dc06pro_digital_filter";
    private static final String SP_KEY_DC06_PRO_VOLUME = "key_dc06pro_volume";
    private static final String SP_KEY_DC06_RPO_CODEC_EXTERNAL = "key_dc06pro_codec_external";
    private static final String SP_KEY_DC06_VOLUME = "key_dc06_volume";
    private static final String SP_KEY_DC07_DIGITAL_FILTER = "key_dc07_digital_filter";
    private static final String SP_KEY_DC07_DSD_Filter = "key_dc07_dsd_filter";
    private static final String SP_KEY_DC07_DSD_VOLUME_COMPENSATION = "key_dc07_dsd_volume_compensation";
    private static final String SP_KEY_DC07_HP_Filter = "key_dc07_hp_filter";
    private static final String SP_KEY_DC07_KEY_CONTROL_STATUS = "key_dc07_key_control_status";
    private static final String SP_KEY_DC07_PCM_TRIM = "key_dc07_pcm_trim";
    private static final String SP_KEY_DC07_PRO_DIGITAL_FILTER = "key_dc07_pro_digital_filter";
    private static final String SP_KEY_DC07_PRO_KEY_CONTROL_STATUS = "key_dc07pro_key_control_status";
    private static final String SP_KEY_DC07_PRO_SPDIF_MODE = "key_dc07_pro_spdif_mode";
    private static final String SP_KEY_DC07_PRO_VOLUME = "key_dc07_pro_volume";
    private static final String SP_KEY_DC07_RPO_BALANCE = "key_dc07_rpo_balance";
    private static final String SP_KEY_DC07_RPO_GAIN = "key_dc07pro_gain";
    private static final String SP_KEY_DC07_RPO_SCREEN_ROTATION = "key_dc07_rpo_screen_rotation";
    private static final String SP_KEY_DC07_RPO_SCREEN_TIME = "key_dc07_pro_screen_time";
    private static final String SP_KEY_DC07_SPDIF_MODE = "key_dc07_spdif_mode";
    private static final String SP_KEY_DC08_BALANCE = "key_dc08_balance";
    private static final String SP_KEY_DC08_CODEC_EXTERNAL = "key_dc08_codec_external";
    private static final String SP_KEY_DC08_DIGITAL_FILTER = "key_dc08_digital_filter";
    private static final String SP_KEY_DC08_GAIN = "key_dc08_gain";
    private static final String SP_KEY_DC08_OUTPUT = "key_dc08_output";
    private static final String SP_KEY_DC08_VOLUME = "key_dc08_volume";
    private static final String SP_KEY_DC09_BALANCE = "key_dc09_balance";
    private static final String SP_KEY_DC09_DIGITAL_FILTER = "key_dc09_digital_filter";
    private static final String SP_KEY_DC09_GAIN = "key_dc09_gain";
    private static final String SP_KEY_DC09_HP_Filter = "key_dc09_hp_filter";
    private static final String SP_KEY_DC09_KEY_CONTROL_STATUS = "key_dc09_key_control_status";
    private static final String SP_KEY_DC09_OUTPUT = "key_dc09_output";
    private static final String SP_KEY_DC09_SCREEN_ROTATION = "key_dc09_screen_rotation";
    private static final String SP_KEY_DC09_SCREEN_TIME = "key_dc09_screen_time";
    private static final String SP_KEY_DC09_SPDIF_MODE = "key_dc09_spdif_mode";
    private static final String SP_KEY_DC09_VOLUME = "key_dc09_volume";
    private static final String SP_KEY_DCO4U_BRIGHTNESS = "key_dc04u_brightness";
    private static final String SP_KEY_DCO4U_DRE = "key_dc04u_dre";
    private static final String SP_KEY_LANGUAGE = "key_language";
    private static final String SP_KEY_LAST_OPEN_TIME = "key_last_open_time";
    private static final String SP_KEY_MACCHIATO_AMP_MODE = "key_macchiato_amp_mode";
    private static final String SP_KEY_MACCHIATO_BALANCE = "key_macchiato_balance";
    private static final String SP_KEY_MACCHIATO_DIGITAL_FILTER = "key_macchiato_digital_filter";
    private static final String SP_KEY_MACCHIATO_DRE = "key_macchiato_dre";
    private static final String SP_KEY_MACCHIATO_EQ = "key_macchiato_eq";
    private static final String SP_KEY_MACCHIATO_GAIN = "key_macchiato_gain";
    private static final String SP_KEY_MACCHIATO_KEY_CONTROL_STATUS = "key_macchiato_key_control_status";
    private static final String SP_KEY_MACCHIATO_LANGUAGE = "key_macchiato_language";
    private static final String SP_KEY_MACCHIATO_MIC_MODE = "key_macchiato_mic_mode";
    private static final String SP_KEY_MACCHIATO_OP_VOLTAGE = "key_macchiato_op_voltage";
    private static final String SP_KEY_MACCHIATO_OUTPUT = "key_macchiato_output";
    private static final String SP_KEY_MACCHIATO_SCREEN_ROTATION = "key_macchiato_screen_rotation";
    private static final String SP_KEY_MACCHIATO_SCREEN_TIME = "key_macchiato_screen_time";
    private static final String SP_KEY_MACCHIATO_SPDIF_MODE = "key_macchiato_spdif_mode";
    private static final String SP_KEY_MACCHIATO_THEME = "key_macchiato_theme";
    private static final String SP_KEY_MACCHIATO_THEME_COLOR = "key_macchiato_theme_color";
    private static final String SP_KEY_MACCHIATO_UAC_VERSION = "key_macchiato_uac_version";
    private static final String SP_KEY_MACCHIATO_VOLUME = "key_macchiato_volume";
    private static final String SP_KEY_MAX_VOICE_VOLUME = "key_max_voice_volume";
    private static final String SP_KEY_NOTIFICATION = "key_notification";
    private static final String SP_KEY_RESET_LAST_VALUE = "key_reset_last_value";
    private static final String SP_KEY_REVERSE_VOLUME = "key_reverse_volume";
    private static final String SP_KEY_SCREEN_SIZE = "key_screen_size";
    private static final String SP_KEY_TONFA_BALANCE = "key_tonfa_balance";
    private static final String SP_KEY_TONFA_DIGITAL_FILTER = "key_tonfa_digital_filter";
    private static final String SP_KEY_TONFA_GAIN = "key_tonfa_gain";
    private static final String SP_KEY_TONFA_HP_Filter = "key_tonfa_hp_filter";
    private static final String SP_KEY_TONFA_KEY_CONTROL_STATUS = "key_tonfa_key_control_status";
    private static final String SP_KEY_TONFA_SCREEN_ROTATION = "key_tonfa_screen_rotation";
    private static final String SP_KEY_TONFA_SCREEN_TIME = "key_tonfa_screen_time";
    private static final String SP_KEY_TONFA_SPDIF_MODE = "key_tonfa_spdif_mode";
    private static final String SP_KEY_TONFA_VOLUME = "key_tonfa_volume";
    private static final String SP_KEY_VOLUME = "key_volume";
    public static final String SP_NAME = "com.ibasso.volume_preferences";
    private static final String TAG = "SpStorage";
    private static SpStorage mSpStorage;
    private Context mContext;
    private SharedPreferences.Editor mEditor;
    private SharedPreferences mSp;

    public static class SharedPreferencesCompat {
        private static final Method sApplyMethod = findApplyMethod();

        private SharedPreferencesCompat() {
        }

        public static void apply(SharedPreferences.Editor editor) {
            try {
                Method method = sApplyMethod;
                if (method != null) {
                    method.invoke(editor, null);
                    return;
                }
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException unused) {
            }
            editor.commit();
        }

        private static Method findApplyMethod() {
            try {
                return SharedPreferences.Editor.class.getMethod("apply", null);
            } catch (NoSuchMethodException unused) {
                return null;
            }
        }
    }

    public SpStorage(Context context) {
        this.mContext = context;
        SharedPreferences sharedPreferences = context.getSharedPreferences(SP_NAME, 0);
        this.mSp = sharedPreferences;
        this.mEditor = sharedPreferences.edit();
    }

    private int get(String str, int i) {
        return this.mSp.getInt(str, i);
    }

    private String get(String str, String str2) {
        return this.mSp.getString(str, str2);
    }

    private boolean get(String str, boolean z) {
        return this.mSp.getBoolean(str, z);
    }

    public static SpStorage getInstance() {
        return mSpStorage;
    }

    public static synchronized void init(Context context) {
        synchronized (SpStorage.class) {
            if (mSpStorage == null) {
                mSpStorage = new SpStorage(context);
            }
        }
    }

    private void set(String str, long j) {
        this.mEditor.putLong(str, j);
        this.mEditor.commit();
    }

    private void set(String str, String str2) {
        this.mEditor.putString(str, str2);
        this.mEditor.commit();
    }

    public void clear() {
        this.mEditor.clear();
        SharedPreferencesCompat.apply(this.mEditor);
    }

    public boolean contains(String str) {
        return this.mSp.contains(str);
    }

    public Object get(String str, Object obj) {
        if (obj instanceof String) {
            return this.mSp.getString(str, (String) obj);
        }
        if (obj instanceof Integer) {
            return Integer.valueOf(this.mSp.getInt(str, ((Integer) obj).intValue()));
        }
        if (obj instanceof Boolean) {
            return Boolean.valueOf(this.mSp.getBoolean(str, ((Boolean) obj).booleanValue()));
        }
        if (obj instanceof Float) {
            return Float.valueOf(this.mSp.getFloat(str, ((Float) obj).floatValue()));
        }
        if (obj instanceof Long) {
            return Long.valueOf(this.mSp.getLong(str, ((Long) obj).longValue()));
        }
        return null;
    }

    public Map<String, ?> getAll(Context context) {
        return this.mSp.getAll();
    }

    public String getCustomBg() {
        return get("key_custom_bg", HttpUrl.FRAGMENT_ENCODE_SET);
    }

    public int getD18AmpMode() {
        return this.mSp.getInt(SP_KEY_D18_AMP_MODE, 0);
    }

    public int getD18Balance() {
        return this.mSp.getInt(SP_KEY_D18_BALANCE, 0);
    }

    public int getD18DacGain() {
        return this.mSp.getInt(SP_KEY_D18_DAC_GAIN, 0);
    }

    public int getD18DigitalFilter() {
        return this.mSp.getInt(SP_KEY_D18_DIGITAL_FILTER, 0);
    }

    public int getD18Gain() {
        return this.mSp.getInt(SP_KEY_D18_GAIN, 0);
    }

    public int getD18Language() {
        return this.mSp.getInt(SP_KEY_D18_LANGUAGE, 0);
    }

    public int getD18MicMode() {
        return this.mSp.getInt(SP_KEY_D18_MIC_MODE, 1);
    }

    public int getD18OInputMode() {
        return this.mSp.getInt(SP_KEY_D18_INPUT_MODE, 1);
    }

    public int getD18ScreenRotation() {
        return this.mSp.getInt(SP_KEY_D18_SCREEN_ROTATION, 2);
    }

    public int getD18ScreenTime() {
        return this.mSp.getInt(SP_KEY_D18_SCREEN_TIME, 2);
    }

    public int getD18SpdifMode() {
        return this.mSp.getInt(SP_KEY_D18_SPDIF_MODE, 0);
    }

    public int getD18Theme() {
        return this.mSp.getInt(SP_KEY_D18_THEME, 1);
    }

    public int getD18UacVersion() {
        return this.mSp.getInt(SP_KEY_D18_UAC_VERSION, 0);
    }

    public int getD18Volume() {
        return this.mSp.getInt(SP_KEY_D18_VOLUME, 30);
    }

    public int getDc03ProBalance() {
        return this.mSp.getInt(SP_KEY_DC03PRO_BALANCE, 0);
    }

    public int getDc03ProDigitalFilter() {
        return this.mSp.getInt(SP_KEY_DC03PRO_DIGITAL_FILTER, 66);
    }

    public int getDc03ProGain() {
        return this.mSp.getInt(SP_KEY_DC03_RPO_GAIN, 2);
    }

    public int getDc03ProOutput() {
        return this.mSp.getInt(SP_KEY_DC03_RPO_OUTPUT, 1);
    }

    public int getDc03ProVolume() {
        return this.mSp.getInt(SP_KEY_DC03PRO_VOLUME, 30);
    }

    public int getDc04ProBalance() {
        return this.mSp.getInt(SP_KEY_DC04PRO_BALANCE, 0);
    }

    public int getDc04ProCodecExternal() {
        return this.mSp.getInt(SP_KEY_DC04_RPO_CODEC_EXTERNAL, 0);
    }

    public int getDc04ProDigitalFilter() {
        return this.mSp.getInt(SP_KEY_DC04PRO_DIGITAL_FILTER, 66);
    }

    public int getDc04ProGain() {
        return this.mSp.getInt(SP_KEY_DC04_RPO_GAIN, 2);
    }

    public int getDc04ProOutput() {
        return this.mSp.getInt(SP_KEY_DC04_RPO_OUTPUT, 1);
    }

    public int getDc04ProVolume() {
        return this.mSp.getInt(SP_KEY_DC04PRO_VOLUME, 30);
    }

    public int getDc04uAmpMode() {
        return this.mSp.getInt(SP_KEY_DC04U_AMP_MODE, 0);
    }

    public int getDc04uBalance() {
        return this.mSp.getInt(SP_KEY_DC04U_BALANCE, 0);
    }

    public int getDc04uBrightness() {
        return this.mSp.getInt(SP_KEY_DCO4U_BRIGHTNESS, 0);
    }

    public int getDc04uDRE() {
        return this.mSp.getInt(SP_KEY_DCO4U_DRE, 0);
    }

    public int getDc04uDigitalFilter() {
        return this.mSp.getInt(SP_KEY_DC04U_DIGITAL_FILTER, 0);
    }

    public int getDc04uEQ() {
        return this.mSp.getInt(SP_KEY_DC04U_EQ, 0);
    }

    public int getDc04uGain() {
        return this.mSp.getInt(SP_KEY_DC04U_GAIN, 0);
    }

    public int getDc04uKeyControlStatus() {
        return this.mSp.getInt(SP_KEY_DC04U_KEY_CONTROL_STATUS, 1);
    }

    public int getDc04uLanguage() {
        return this.mSp.getInt(SP_KEY_DC04U_LANGUAGE, 0);
    }

    public int getDc04uMicMode() {
        return this.mSp.getInt(SP_KEY_DC04U_MIC_MODE, 1);
    }

    public int getDc04uOpVoltage() {
        return this.mSp.getInt(SP_KEY_DC04U_OP_VOLTAGE, 1);
    }

    public int getDc04uOutput() {
        return this.mSp.getInt(SP_KEY_DC04U_OUTPUT, 1);
    }

    public int getDc04uScreenRotation() {
        return this.mSp.getInt(SP_KEY_DC04U_SCREEN_ROTATION, 2);
    }

    public int getDc04uScreenTime() {
        return this.mSp.getInt(SP_KEY_DC04U_SCREEN_TIME, 2);
    }

    public int getDc04uSpdifMode() {
        return this.mSp.getInt(SP_KEY_DC04U_SPDIF_MODE, 0);
    }

    public int getDc04uTheme() {
        return this.mSp.getInt(SP_KEY_DC04U_THEME, 1);
    }

    public int getDc04uThemeColor() {
        return this.mSp.getInt(SP_KEY_DC04U_THEME_COLOR, 0);
    }

    public int getDc04uUacVersion() {
        return this.mSp.getInt(SP_KEY_DC04U_UAC_VERSION, 0);
    }

    public int getDc04uVolume() {
        return this.mSp.getInt(SP_KEY_DC04U_VOLUME, 30);
    }

    public int getDc06Balance() {
        return this.mSp.getInt(SP_KEY_DC06_BALANCE, 0);
    }

    public int getDc06DigitalFilter() {
        return this.mSp.getInt(SP_KEY_DC06_DIGITAL_FILTER, 0);
    }

    public int getDc06ProBalance() {
        return this.mSp.getInt(SP_KEY_DC06_PRO_BALANCE, 0);
    }

    public int getDc06ProCodecExternal() {
        return this.mSp.getInt(SP_KEY_DC06_RPO_CODEC_EXTERNAL, 0);
    }

    public int getDc06ProDigitalFilter() {
        return this.mSp.getInt(SP_KEY_DC06_PRO_DIGITAL_FILTER, 0);
    }

    public int getDc06ProVolume() {
        return this.mSp.getInt(SP_KEY_DC06_PRO_VOLUME, 30);
    }

    public int getDc06Volume() {
        return this.mSp.getInt(SP_KEY_DC06_VOLUME, 30);
    }

    public int getDc07DigitalFilter() {
        return this.mSp.getInt(SP_KEY_DC07_DIGITAL_FILTER, 0);
    }

    public int getDc07DsdFilter() {
        return this.mSp.getInt(SP_KEY_DC07_DSD_Filter, 0);
    }

    public int getDc07DsdVolumeCompensation() {
        return this.mSp.getInt(SP_KEY_DC07_DSD_VOLUME_COMPENSATION, 1);
    }

    public int getDc07KeyControlStatus() {
        return this.mSp.getInt(SP_KEY_DC07_KEY_CONTROL_STATUS, 1);
    }

    public int getDc07PcmTrim() {
        return this.mSp.getInt(SP_KEY_DC07_PCM_TRIM, 0);
    }

    public int getDc07ProBalance() {
        return this.mSp.getInt(SP_KEY_DC07_RPO_BALANCE, 0);
    }

    public int getDc07ProDigitalFilter() {
        return this.mSp.getInt(SP_KEY_DC07_PRO_DIGITAL_FILTER, 0);
    }

    public int getDc07ProGain() {
        return this.mSp.getInt(SP_KEY_DC07_RPO_GAIN, 0);
    }

    public int getDc07ProHpFilter() {
        return this.mSp.getInt(SP_KEY_DC07_HP_Filter, 0);
    }

    public int getDc07ProKeyControlStatus() {
        return this.mSp.getInt(SP_KEY_DC07_PRO_KEY_CONTROL_STATUS, 1);
    }

    public int getDc07ProScreenRotation() {
        return this.mSp.getInt(SP_KEY_DC07_RPO_SCREEN_ROTATION, 2);
    }

    public int getDc07ProScreenTime() {
        return this.mSp.getInt(SP_KEY_DC07_RPO_SCREEN_TIME, 2);
    }

    public int getDc07ProSpdifMode() {
        return this.mSp.getInt(SP_KEY_DC07_PRO_SPDIF_MODE, 0);
    }

    public int getDc07ProVolume() {
        return this.mSp.getInt(SP_KEY_DC07_PRO_VOLUME, 30);
    }

    public int getDc07SpdifMode() {
        return this.mSp.getInt(SP_KEY_DC07_SPDIF_MODE, 0);
    }

    public int getDc08Balance() {
        return this.mSp.getInt(SP_KEY_DC08_BALANCE, 0);
    }

    public int getDc08CodecExternal() {
        return this.mSp.getInt(SP_KEY_DC08_CODEC_EXTERNAL, 0);
    }

    public int getDc08DigitalFilter() {
        return this.mSp.getInt(SP_KEY_DC08_DIGITAL_FILTER, 66);
    }

    public int getDc08Gain() {
        return this.mSp.getInt(SP_KEY_DC08_GAIN, 2);
    }

    public int getDc08Output() {
        return this.mSp.getInt(SP_KEY_DC08_OUTPUT, 1);
    }

    public int getDc08Volume() {
        return this.mSp.getInt(SP_KEY_DC08_VOLUME, 30);
    }

    public int getDc09Balance() {
        return this.mSp.getInt(SP_KEY_DC09_BALANCE, 0);
    }

    public int getDc09DigitalFilter() {
        return this.mSp.getInt(SP_KEY_DC09_DIGITAL_FILTER, 0);
    }

    public int getDc09Gain() {
        return this.mSp.getInt(SP_KEY_DC09_GAIN, 0);
    }

    public int getDc09HpFilter() {
        return this.mSp.getInt(SP_KEY_DC09_HP_Filter, 0);
    }

    public int getDc09KeyControlStatus() {
        return this.mSp.getInt(SP_KEY_DC09_KEY_CONTROL_STATUS, 1);
    }

    public int getDc09Output() {
        return this.mSp.getInt(SP_KEY_DC09_OUTPUT, 1);
    }

    public int getDc09ScreenRotation() {
        return this.mSp.getInt(SP_KEY_DC09_SCREEN_ROTATION, 2);
    }

    public int getDc09ScreenTime() {
        return this.mSp.getInt(SP_KEY_DC09_SCREEN_TIME, 2);
    }

    public int getDc09SpdifMode() {
        return this.mSp.getInt(SP_KEY_DC09_SPDIF_MODE, 0);
    }

    public int getDc09Volume() {
        return this.mSp.getInt(SP_KEY_DC09_VOLUME, 30);
    }

    public String getDefaultBg() {
        return get("key_default_bg", HttpUrl.FRAGMENT_ENCODE_SET);
    }

    public boolean getDisplayNotification() {
        return get(SP_KEY_NOTIFICATION, true);
    }

    public boolean getIsVolumeBtReverse() {
        return get(SP_KEY_REVERSE_VOLUME, false);
    }

    public String getLanguage() {
        return this.mSp.getString(SP_KEY_LANGUAGE, "zh");
    }

    public long getLastOpenTime() {
        return this.mSp.getLong(SP_KEY_LAST_OPEN_TIME, 0L);
    }

    public int getMacchiatoAmpMode() {
        return this.mSp.getInt(SP_KEY_MACCHIATO_AMP_MODE, 0);
    }

    public int getMacchiatoBalance() {
        return this.mSp.getInt(SP_KEY_MACCHIATO_BALANCE, 0);
    }

    public int getMacchiatoDRE() {
        return this.mSp.getInt(SP_KEY_MACCHIATO_DRE, 0);
    }

    public int getMacchiatoDigitalFilter() {
        return this.mSp.getInt(SP_KEY_MACCHIATO_DIGITAL_FILTER, 0);
    }

    public int getMacchiatoEQ() {
        return this.mSp.getInt(SP_KEY_MACCHIATO_EQ, 0);
    }

    public int getMacchiatoGain() {
        return this.mSp.getInt(SP_KEY_MACCHIATO_GAIN, 0);
    }

    public int getMacchiatoKeyControlStatus() {
        return this.mSp.getInt(SP_KEY_MACCHIATO_KEY_CONTROL_STATUS, 1);
    }

    public int getMacchiatoLanguage() {
        return this.mSp.getInt(SP_KEY_MACCHIATO_LANGUAGE, 0);
    }

    public int getMacchiatoMicMode() {
        return this.mSp.getInt(SP_KEY_MACCHIATO_MIC_MODE, 1);
    }

    public int getMacchiatoOpVoltage() {
        return this.mSp.getInt(SP_KEY_MACCHIATO_OP_VOLTAGE, 1);
    }

    public int getMacchiatoOutput() {
        return this.mSp.getInt(SP_KEY_MACCHIATO_OUTPUT, 1);
    }

    public int getMacchiatoScreenRotation() {
        return this.mSp.getInt(SP_KEY_MACCHIATO_SCREEN_ROTATION, 2);
    }

    public int getMacchiatoScreenTime() {
        return this.mSp.getInt(SP_KEY_MACCHIATO_SCREEN_TIME, 2);
    }

    public int getMacchiatoSpdifMode() {
        return this.mSp.getInt(SP_KEY_MACCHIATO_SPDIF_MODE, 0);
    }

    public int getMacchiatoTheme() {
        return this.mSp.getInt(SP_KEY_MACCHIATO_THEME, 1);
    }

    public int getMacchiatoThemeColor() {
        return this.mSp.getInt(SP_KEY_MACCHIATO_THEME_COLOR, 0);
    }

    public int getMacchiatoUacVersion() {
        return this.mSp.getInt(SP_KEY_MACCHIATO_UAC_VERSION, 0);
    }

    public int getMacchiatoVolume() {
        return this.mSp.getInt(SP_KEY_MACCHIATO_VOLUME, 30);
    }

    public int getMaxVoiceVolume() {
        try {
            return Integer.parseInt(get(SP_KEY_MAX_VOICE_VOLUME, "30"));
        } catch (NumberFormatException unused) {
            return 30;
        }
    }

    public boolean getResetLastValue() {
        return get(SP_KEY_RESET_LAST_VALUE, true);
    }

    public String getScreenSize() {
        return this.mSp.getString(SP_KEY_SCREEN_SIZE, "1080x1920");
    }

    public int getTonfaBalance() {
        return this.mSp.getInt(SP_KEY_TONFA_BALANCE, 0);
    }

    public int getTonfaDigitalFilter() {
        return this.mSp.getInt(SP_KEY_TONFA_DIGITAL_FILTER, 0);
    }

    public int getTonfaGain() {
        return this.mSp.getInt(SP_KEY_TONFA_GAIN, 0);
    }

    public int getTonfaKeyControlStatus() {
        return this.mSp.getInt(SP_KEY_TONFA_KEY_CONTROL_STATUS, 1);
    }

    public int getTonfaScreenRotation() {
        return this.mSp.getInt(SP_KEY_TONFA_SCREEN_ROTATION, 2);
    }

    public int getTonfaScreenTime() {
        return this.mSp.getInt(SP_KEY_TONFA_SCREEN_TIME, 2);
    }

    public int getTonfaSpdifMode() {
        return this.mSp.getInt(SP_KEY_TONFA_SPDIF_MODE, 0);
    }

    public int getTonfaVolume() {
        return this.mSp.getInt(SP_KEY_TONFA_VOLUME, 30);
    }

    public int getVolume() {
        return get(SP_KEY_VOLUME, 40);
    }

    public void put(String str, Object obj) {
        if (obj instanceof String) {
            this.mEditor.putString(str, (String) obj);
        } else if (obj instanceof Integer) {
            this.mEditor.putInt(str, ((Integer) obj).intValue());
        } else if (obj instanceof Boolean) {
            this.mEditor.putBoolean(str, ((Boolean) obj).booleanValue());
        } else if (obj instanceof Float) {
            this.mEditor.putFloat(str, ((Float) obj).floatValue());
        } else if (obj instanceof Long) {
            this.mEditor.putLong(str, ((Long) obj).longValue());
        } else {
            this.mEditor.putString(str, obj.toString());
        }
        SharedPreferencesCompat.apply(this.mEditor);
    }

    public void remove(Context context, String str) {
        this.mEditor.remove(str);
        SharedPreferencesCompat.apply(this.mEditor);
    }

    public void set(String str, int i) {
        this.mEditor.putInt(str, i);
        this.mEditor.commit();
    }

    public void set(String str, boolean z) {
        this.mEditor.putBoolean(str, z);
        this.mEditor.commit();
    }

    public void setCustomBg(String str) {
        set("key_custom_bg", str);
    }

    public void setD18AmpMode(int i) {
        set(SP_KEY_D18_AMP_MODE, i);
    }

    public void setD18Balance(int i) {
        set(SP_KEY_D18_BALANCE, i);
    }

    public void setD18DacGain(int i) {
        set(SP_KEY_D18_DAC_GAIN, i);
    }

    public void setD18DigitalFilter(int i) {
        set(SP_KEY_D18_DIGITAL_FILTER, i);
    }

    public void setD18Gain(int i) {
        set(SP_KEY_D18_GAIN, i);
    }

    public void setD18Language(int i) {
        set(SP_KEY_D18_LANGUAGE, i);
    }

    public void setD18MicMode(int i) {
        set(SP_KEY_D18_MIC_MODE, i);
    }

    public void setD18OInputMode(int i) {
        set(SP_KEY_D18_INPUT_MODE, i);
    }

    public void setD18ScreenRotation(int i) {
        set(SP_KEY_D18_SCREEN_ROTATION, i);
    }

    public void setD18ScreenTime(int i) {
        set(SP_KEY_D18_SCREEN_TIME, i);
    }

    public void setD18SpdifMode(int i) {
        set(SP_KEY_D18_SPDIF_MODE, i);
    }

    public void setD18Theme(int i) {
        set(SP_KEY_D18_THEME, i);
    }

    public void setD18UacVersion(int i) {
        set(SP_KEY_D18_UAC_VERSION, i);
    }

    public void setD18Volume(int i) {
        set(SP_KEY_D18_VOLUME, i);
    }

    public void setDc03ProBalance(int i) {
        set(SP_KEY_DC03PRO_BALANCE, i);
    }

    public void setDc03ProDigitalFilter(int i) {
        set(SP_KEY_DC03PRO_DIGITAL_FILTER, i);
    }

    public void setDc03ProGain(int i) {
        set(SP_KEY_DC03_RPO_GAIN, i);
    }

    public void setDc03ProOutput(int i) {
        set(SP_KEY_DC03_RPO_OUTPUT, i);
    }

    public void setDc03ProVolume(int i) {
        set(SP_KEY_DC03PRO_VOLUME, i);
    }

    public void setDc04ProBalance(int i) {
        set(SP_KEY_DC04PRO_BALANCE, i);
    }

    public void setDc04ProCodecExternal(int i) {
        set(SP_KEY_DC04_RPO_CODEC_EXTERNAL, i);
    }

    public void setDc04ProDigitalFilter(int i) {
        set(SP_KEY_DC04PRO_DIGITAL_FILTER, i);
    }

    public void setDc04ProGain(int i) {
        set(SP_KEY_DC04_RPO_GAIN, i);
    }

    public void setDc04ProOutput(int i) {
        set(SP_KEY_DC04_RPO_OUTPUT, i);
    }

    public void setDc04ProVolume(int i) {
        set(SP_KEY_DC04PRO_VOLUME, i);
    }

    public void setDc04uAmpMode(int i) {
        set(SP_KEY_DC04U_AMP_MODE, i);
    }

    public void setDc04uBalance(int i) {
        set(SP_KEY_DC04U_BALANCE, i);
    }

    public void setDc04uBrightness(int i) {
        set(SP_KEY_DCO4U_BRIGHTNESS, i);
    }

    public void setDc04uDRE(int i) {
        set(SP_KEY_DCO4U_DRE, i);
    }

    public void setDc04uDigitalFilter(int i) {
        set(SP_KEY_DC04U_DIGITAL_FILTER, i);
    }

    public void setDc04uEQ(int i) {
        set(SP_KEY_DC04U_EQ, i);
    }

    public void setDc04uGain(int i) {
        set(SP_KEY_DC04U_GAIN, i);
    }

    public void setDc04uKeyControlStatus(int i) {
        set(SP_KEY_DC04U_KEY_CONTROL_STATUS, i);
    }

    public void setDc04uLanguage(int i) {
        set(SP_KEY_DC04U_LANGUAGE, i);
    }

    public void setDc04uMicMode(int i) {
        set(SP_KEY_DC04U_MIC_MODE, i);
    }

    public void setDc04uOpVoltage(int i) {
        set(SP_KEY_DC04U_OP_VOLTAGE, i);
    }

    public void setDc04uOutput(int i) {
        set(SP_KEY_DC04U_OUTPUT, i);
    }

    public void setDc04uScreenRotation(int i) {
        set(SP_KEY_DC04U_SCREEN_ROTATION, i);
    }

    public void setDc04uScreenTime(int i) {
        set(SP_KEY_DC04U_SCREEN_TIME, i);
    }

    public void setDc04uSpdifMode(int i) {
        set(SP_KEY_DC04U_SPDIF_MODE, i);
    }

    public void setDc04uTheme(int i) {
        set(SP_KEY_DC04U_THEME, i);
    }

    public void setDc04uThemeColor(int i) {
        set(SP_KEY_DC04U_THEME_COLOR, i);
    }

    public void setDc04uUacVersion(int i) {
        set(SP_KEY_DC04U_UAC_VERSION, i);
    }

    public void setDc04uVolume(int i) {
        set(SP_KEY_DC04U_VOLUME, i);
    }

    public void setDc06Balance(int i) {
        set(SP_KEY_DC06_BALANCE, i);
    }

    public void setDc06DigitalFilter(int i) {
        set(SP_KEY_DC06_DIGITAL_FILTER, i);
    }

    public void setDc06ProBalance(int i) {
        set(SP_KEY_DC06_PRO_BALANCE, i);
    }

    public void setDc06ProCodecExternal(int i) {
        set(SP_KEY_DC06_RPO_CODEC_EXTERNAL, i);
    }

    public void setDc06ProDigitalFilter(int i) {
        set(SP_KEY_DC06_PRO_DIGITAL_FILTER, i);
    }

    public void setDc06ProVolume(int i) {
        set(SP_KEY_DC06_PRO_VOLUME, i);
    }

    public void setDc06Volume(int i) {
        set(SP_KEY_DC06_VOLUME, i);
    }

    public void setDc07DigitalFilter(int i) {
        set(SP_KEY_DC07_DIGITAL_FILTER, i);
    }

    public void setDc07DsdFilter(int i) {
        set(SP_KEY_DC07_DSD_Filter, i);
    }

    public void setDc07DsdVolumeCompensation(int i) {
        set(SP_KEY_DC07_DSD_VOLUME_COMPENSATION, i);
    }

    public void setDc07KeyControlStatus(int i) {
        set(SP_KEY_DC07_KEY_CONTROL_STATUS, i);
    }

    public void setDc07PcmTrim(int i) {
        set(SP_KEY_DC07_PCM_TRIM, i);
    }

    public void setDc07ProBalance(int i) {
        set(SP_KEY_DC07_RPO_BALANCE, i);
    }

    public void setDc07ProDigitalFilter(int i) {
        set(SP_KEY_DC07_PRO_DIGITAL_FILTER, i);
    }

    public void setDc07ProGain(int i) {
        set(SP_KEY_DC07_RPO_GAIN, i);
    }

    public void setDc07ProHpFilter(int i) {
        set(SP_KEY_DC07_HP_Filter, i);
    }

    public void setDc07ProKeyControlStatus(int i) {
        set(SP_KEY_DC07_PRO_KEY_CONTROL_STATUS, i);
    }

    public void setDc07ProScreenRotation(int i) {
        set(SP_KEY_DC07_RPO_SCREEN_ROTATION, i);
    }

    public void setDc07ProScreenTime(int i) {
        set(SP_KEY_DC07_RPO_SCREEN_TIME, i);
    }

    public void setDc07ProSpdifMode(int i) {
        set(SP_KEY_DC07_PRO_SPDIF_MODE, i);
    }

    public void setDc07ProVolume(int i) {
        set(SP_KEY_DC07_PRO_VOLUME, i);
    }

    public void setDc07SpdifMode(int i) {
        set(SP_KEY_DC07_SPDIF_MODE, i);
    }

    public void setDc08Balance(int i) {
        set(SP_KEY_DC08_BALANCE, i);
    }

    public void setDc08CodecExternal(int i) {
        set(SP_KEY_DC08_CODEC_EXTERNAL, i);
    }

    public void setDc08DigitalFilter(int i) {
        set(SP_KEY_DC08_DIGITAL_FILTER, i);
    }

    public void setDc08Gain(int i) {
        set(SP_KEY_DC08_GAIN, i);
    }

    public void setDc08Output(int i) {
        set(SP_KEY_DC08_OUTPUT, i);
    }

    public void setDc08Volume(int i) {
        set(SP_KEY_DC08_VOLUME, i);
    }

    public void setDc09Balance(int i) {
        set(SP_KEY_DC09_BALANCE, i);
    }

    public void setDc09DigitalFilter(int i) {
        set(SP_KEY_DC09_DIGITAL_FILTER, i);
    }

    public void setDc09Gain(int i) {
        set(SP_KEY_DC09_GAIN, i);
    }

    public void setDc09HpFilter(int i) {
        set(SP_KEY_DC09_HP_Filter, i);
    }

    public void setDc09KeyControlStatus(int i) {
        set(SP_KEY_DC09_KEY_CONTROL_STATUS, i);
    }

    public void setDc09Output(int i) {
        set(SP_KEY_DC09_OUTPUT, i);
    }

    public void setDc09ScreenRotation(int i) {
        set(SP_KEY_DC09_SCREEN_ROTATION, i);
    }

    public void setDc09ScreenTime(int i) {
        set(SP_KEY_DC09_SCREEN_TIME, i);
    }

    public void setDc09SpdifMode(int i) {
        set(SP_KEY_DC09_SPDIF_MODE, i);
    }

    public void setDc09Volume(int i) {
        set(SP_KEY_DC09_VOLUME, i);
    }

    public void setDefaultBg(String str) {
        set("key_default_bg", str);
    }

    public void setDisplayNotification(boolean z) {
        set(SP_KEY_NOTIFICATION, z);
    }

    public void setIsVolumeBtReverse(boolean z) {
        set(SP_KEY_REVERSE_VOLUME, z);
    }

    public void setLanguage(@NotNull String str) {
        set(SP_KEY_LANGUAGE, str);
    }

    public void setLastOpenTime(long j) {
        set(SP_KEY_LAST_OPEN_TIME, j);
    }

    public void setMacchiatoAmpMode(int i) {
        set(SP_KEY_MACCHIATO_AMP_MODE, i);
    }

    public void setMacchiatoBalance(int i) {
        set(SP_KEY_MACCHIATO_BALANCE, i);
    }

    public void setMacchiatoDRE(int i) {
        set(SP_KEY_MACCHIATO_DRE, i);
    }

    public void setMacchiatoDigitalFilter(int i) {
        set(SP_KEY_MACCHIATO_DIGITAL_FILTER, i);
    }

    public void setMacchiatoEQ(int i) {
        set(SP_KEY_MACCHIATO_EQ, i);
    }

    public void setMacchiatoGain(int i) {
        set(SP_KEY_MACCHIATO_GAIN, i);
    }

    public void setMacchiatoKeyControlStatus(int i) {
        set(SP_KEY_MACCHIATO_KEY_CONTROL_STATUS, i);
    }

    public void setMacchiatoLanguage(int i) {
        set(SP_KEY_MACCHIATO_LANGUAGE, i);
    }

    public void setMacchiatoMicMode(int i) {
        set(SP_KEY_MACCHIATO_MIC_MODE, i);
    }

    public void setMacchiatoOpVoltage(int i) {
        set(SP_KEY_MACCHIATO_OP_VOLTAGE, i);
    }

    public void setMacchiatoOutput(int i) {
        set(SP_KEY_MACCHIATO_OUTPUT, i);
    }

    public void setMacchiatoScreenRotation(int i) {
        set(SP_KEY_MACCHIATO_SCREEN_ROTATION, i);
    }

    public void setMacchiatoScreenTime(int i) {
        set(SP_KEY_MACCHIATO_SCREEN_TIME, i);
    }

    public void setMacchiatoSpdifMode(int i) {
        set(SP_KEY_MACCHIATO_SPDIF_MODE, i);
    }

    public void setMacchiatoTheme(int i) {
        set(SP_KEY_MACCHIATO_THEME, i);
    }

    public void setMacchiatoThemeColor(int i) {
        set(SP_KEY_MACCHIATO_THEME_COLOR, i);
    }

    public void setMacchiatoUacVersion(int i) {
        set(SP_KEY_MACCHIATO_UAC_VERSION, i);
    }

    public void setMacchiatoVolume(int i) {
        set(SP_KEY_MACCHIATO_VOLUME, i);
    }

    public void setResetLastValue(boolean z) {
        set(SP_KEY_RESET_LAST_VALUE, z);
    }

    public void setScreenSize(@NotNull String str) {
        set(SP_KEY_SCREEN_SIZE, str);
    }

    public void setTonfaBalance(int i) {
        set(SP_KEY_TONFA_BALANCE, i);
    }

    public void setTonfaDigitalFilter(int i) {
        set(SP_KEY_TONFA_DIGITAL_FILTER, i);
    }

    public void setTonfaGain(int i) {
        set(SP_KEY_TONFA_GAIN, i);
    }

    public void setTonfaKeyControlStatus(int i) {
        set(SP_KEY_TONFA_KEY_CONTROL_STATUS, i);
    }

    public void setTonfaScreenRotation(int i) {
        set(SP_KEY_TONFA_SCREEN_ROTATION, i);
    }

    public void setTonfaScreenTime(int i) {
        set(SP_KEY_TONFA_SCREEN_TIME, i);
    }

    public void setTonfaSpdifMode(int i) {
        set(SP_KEY_TONFA_SPDIF_MODE, i);
    }

    public void setTonfaVolume(int i) {
        set(SP_KEY_TONFA_VOLUME, i);
    }

    public void setVolume(int i) {
        set(SP_KEY_VOLUME, i);
    }
}
