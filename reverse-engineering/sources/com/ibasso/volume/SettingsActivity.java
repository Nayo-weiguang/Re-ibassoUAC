package com.ibasso.volume;

import android.content.ContentResolver;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.InputDeviceCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.ibasso.volume.SettingsActivity;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import okhttp3.HttpUrl;
public class SettingsActivity extends AppCompatActivity {
    ImageView customImageView;
    RadioButton customRb;
    ImageView defaultImageView;
    RadioButton defaultRb;
    ActivityResultLauncher<PickVisualMediaRequest> pickMedia = registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), new ActivityResultCallback() {
        @Override
        public final void onActivityResult(Object obj) {
            SettingsActivity.k(SettingsActivity.this, (Uri) obj);
        }
    });

    public static class SettingsFragment extends PreferenceFragmentCompat {
        EditTextPreference editTextPreference;
        SwitchPreference switchPreference;
        SwitchPreference switchVolume;

        public static class InputFilterMinMax implements InputFilter {
            private int max;
            private int min;

            public InputFilterMinMax(int i, int i2) {
                this.min = i;
                this.max = i2;
            }

            public InputFilterMinMax(String str, String str2) {
                this.min = Integer.parseInt(str);
                this.max = Integer.parseInt(str2);
            }

            private boolean isInRange(int i, int i2, int i3) {
                return i2 > i ? i3 >= i && i3 <= i2 : i3 >= i2 && i3 <= i;
            }

            @Override
            public CharSequence filter(CharSequence charSequence, int i, int i2, Spanned spanned, int i3, int i4) {
                try {
                    if (isInRange(this.min, this.max, Integer.parseInt(spanned.toString() + charSequence.toString()))) {
                        return null;
                    }
                    return HttpUrl.FRAGMENT_ENCODE_SET;
                } catch (NumberFormatException unused) {
                    return HttpUrl.FRAGMENT_ENCODE_SET;
                }
            }
        }

        public static boolean a(TextView textView, int i, KeyEvent keyEvent) {
            if (keyEvent == null || 66 != keyEvent.getKeyCode()) {
                return false;
            }
            if (keyEvent.getAction() == 1 && TextUtils.isEmpty(textView.getText())) {
                textView.setText("30");
            }
            return true;
        }

        public static boolean b(Preference preference, Object obj) {
            EditTextPreference editTextPreference = (EditTextPreference) preference;
            String obj2 = obj.toString();
            if (TextUtils.isEmpty(obj2)) {
                obj2 = "30";
            }
            editTextPreference.setSummary(obj2);
            return true;
        }

        public static void c(EditText editText) {
            editText.setInputType(InputDeviceCompat.SOURCE_TOUCHSCREEN);
            editText.setSingleLine();
            editText.setHint(R.string.hit_volume_value);
            editText.setFilters(new InputFilter[]{new InputFilterMinMax(0, UacManager.getUacManager().getMaxVolume())});
            editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public final boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                    return SettingsActivity.SettingsFragment.a(textView, i, keyEvent);
                }
            });
        }

        @Override
        public void onCreatePreferences(Bundle bundle, String str) {
            setPreferencesFromResource(R.xml.root_preferences, str);
            EditTextPreference editTextPreference = (EditTextPreference) findPreference("key_max_voice_volume");
            this.editTextPreference = editTextPreference;
            editTextPreference.setSummary(HttpUrl.FRAGMENT_ENCODE_SET + SpStorage.getInstance().getMaxVoiceVolume());
            SwitchPreference switchPreference = (SwitchPreference) findPreference("key_reset_last_value");
            this.switchPreference = switchPreference;
            switchPreference.setChecked(SpStorage.getInstance().getResetLastValue());
            SwitchPreference switchPreference2 = (SwitchPreference) findPreference("key_reverse_volume_button");
            this.switchVolume = switchPreference2;
            switchPreference2.setChecked(SpStorage.getInstance().getIsVolumeBtReverse());
            this.switchVolume.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object obj) {
                    Boolean bool = (Boolean) obj;
                    if (SettingsFragment.this.switchVolume.isChecked() != bool.booleanValue()) {
                        boolean booleanValue = bool.booleanValue();
                        SettingsFragment.this.switchVolume.setChecked(booleanValue);
                        SpStorage.getInstance().setIsVolumeBtReverse(booleanValue);
                        return false;
                    }
                    return false;
                }
            });
            this.editTextPreference.setOnBindEditTextListener(new EditTextPreference.OnBindEditTextListener() {
                @Override
                public final void onBindEditText(EditText editText) {
                    SettingsActivity.SettingsFragment.c(editText);
                }
            });
            this.editTextPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public final boolean onPreferenceChange(Preference preference, Object obj) {
                    return SettingsActivity.SettingsFragment.b(preference, obj);
                }
            });
        }
    }

    public static void h(SettingsActivity settingsActivity, View view) {
        settingsActivity.getClass();
        SpStorage.getInstance().setDefaultBg(HttpUrl.FRAGMENT_ENCODE_SET);
        settingsActivity.updateBgStatus();
    }

    public static void i(SettingsActivity settingsActivity, View view) {
        settingsActivity.getClass();
        String customBg = SpStorage.getInstance().getCustomBg();
        if (customBg.isEmpty()) {
            settingsActivity.openPhotoPick();
            return;
        }
        SpStorage.getInstance().setDefaultBg(customBg);
        settingsActivity.updateBgStatus();
    }

    public static void j(SettingsActivity settingsActivity, View view) {
        settingsActivity.getClass();
        SpStorage.getInstance().setDefaultBg(HttpUrl.FRAGMENT_ENCODE_SET);
        settingsActivity.updateBgStatus();
    }

    public static void k(SettingsActivity settingsActivity, Uri uri) {
        settingsActivity.getClass();
        if (uri == null) {
            Log.d("PhotoPicker", "No media selected");
            return;
        }
        Log.d("PhotoPicker", "Selected URI: " + uri);
        try {
            File saveUriToFile = settingsActivity.saveUriToFile(uri, "bg_" + System.currentTimeMillis());
            String customBg = SpStorage.getInstance().getCustomBg();
            if (saveUriToFile != null) {
                SpStorage.getInstance().setDefaultBg(saveUriToFile.getPath());
                SpStorage.getInstance().setCustomBg(saveUriToFile.getPath());
                settingsActivity.updateBgStatus();
                File file = new File(customBg);
                if (file.exists()) {
                    file.delete();
                }
            }
        } catch (IOException e2) {
            throw new RuntimeException(e2);
        }
    }

    public static void m(SettingsActivity settingsActivity, View view) {
        settingsActivity.getClass();
        String customBg = SpStorage.getInstance().getCustomBg();
        if (customBg.isEmpty()) {
            settingsActivity.openPhotoPick();
            return;
        }
        SpStorage.getInstance().setDefaultBg(customBg);
        settingsActivity.updateBgStatus();
    }

    private File saveUriToFile(Uri uri, String str) throws IOException {
        ContentResolver contentResolver = App.getContext().getContentResolver();
        File file = new File(App.getContext().getCacheDir(), str);
        try {
            InputStream openInputStream = contentResolver.openInputStream(uri);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            try {
                byte[] bArr = new byte[1024];
                while (true) {
                    int read = openInputStream.read(bArr);
                    if (read <= 0) {
                        fileOutputStream.close();
                        openInputStream.close();
                        return file;
                    }
                    fileOutputStream.write(bArr, 0, read);
                }
            } catch (Throwable th) {
                try {
                    fileOutputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (IOException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    private void updateBgStatus() {
        if (UacManager.DC07.equals(UacManager.getUacManager().getProductName())) {
            findViewById(R.id.settings).setVisibility(8);
        } else if (UacManager.DC07PRO.equals(UacManager.getUacManager().getProductName())) {
            findViewById(R.id.settings).setVisibility(8);
        } else if (UacManager.DC09.equals(UacManager.getUacManager().getProductName())) {
            findViewById(R.id.settings).setVisibility(8);
        } else {
            findViewById(R.id.settings).setVisibility(0);
        }
        if (UacManager.DC08.equals(UacManager.getUacManager().getProductName())) {
            this.defaultImageView.setImageResource(R.drawable.dc08_bg);
        } else if (UacManager.Macchiato.equals(UacManager.getUacManager().getProductName())) {
            this.defaultImageView.setImageResource(R.drawable.ic_macchiato_bg);
        } else {
            this.defaultImageView.setImageResource(R.drawable.ic_main_bg);
        }
        if (SpStorage.getInstance().getDefaultBg().isEmpty()) {
            this.defaultRb.setChecked(true);
            this.customRb.setChecked(false);
        } else {
            this.defaultRb.setChecked(false);
            this.customRb.setChecked(true);
        }
        String customBg = SpStorage.getInstance().getCustomBg();
        if (customBg.isEmpty()) {
            return;
        }
        Glide.with((FragmentActivity) this).asBitmap().load(customBg).into((RequestBuilder<Bitmap>) new SimpleTarget<Bitmap>() {
            public void onResourceReady(@NonNull Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {
                SettingsActivity.this.customImageView.setImageBitmap(bitmap);
            }

            @Override
            public void onResourceReady(@NonNull Object obj, @Nullable Transition transition) {
                onResourceReady((Bitmap) obj, (Transition<? super Bitmap>) transition);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onCreate(Bundle bundle) {
        PackageInfo packageInfo;
        super.onCreate(bundle);
        setContentView(R.layout.settings_activity);
        getSupportFragmentManager().beginTransaction().replace(R.id.settings, new SettingsFragment()).commit();
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }
        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e2) {
            e2.printStackTrace();
            packageInfo = null;
        }
        ((TextView) findViewById(R.id.version_tv)).setText(String.format("v%s", packageInfo.versionName));
        findViewById(R.id.default_cover_custom_add_iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                SettingsActivity.this.openPhotoPick();
            }
        });
        this.defaultImageView = (ImageView) findViewById(R.id.default_cover_first_iv);
        findViewById(R.id.default_cover_first).setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                SettingsActivity.h(SettingsActivity.this, view);
            }
        });
        this.defaultRb = (RadioButton) findViewById(R.id.default_cover_first_rb);
        this.customRb = (RadioButton) findViewById(R.id.default_cover_custom_rb);
        this.customImageView = (ImageView) findViewById(R.id.default_cover_custom_iv);
        this.defaultRb.setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                SettingsActivity.j(SettingsActivity.this, view);
            }
        });
        this.customRb.setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                SettingsActivity.m(SettingsActivity.this, view);
            }
        });
        this.customImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                SettingsActivity.i(SettingsActivity.this, view);
            }
        });
        updateBgStatus();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateBgStatus();
    }

    public void openPhotoPick() {
        this.pickMedia.launch(new PickVisualMediaRequest.Builder().setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE).build());
    }
}
