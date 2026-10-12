package com.ibasso.volume.eq;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.ibasso.volume.R;
public class ParamEqActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_param_eq);
        if (bundle == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ParametricEqualizerFragment()).commitNow();
        }
    }
}
