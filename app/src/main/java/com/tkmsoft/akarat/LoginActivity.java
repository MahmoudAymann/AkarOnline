package com.tkmsoft.akarat;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.tkmsoft.akarat.LoginRegister.LoginFragment;
import com.tkmsoft.akarat.util.ListSharePreference;

import java.util.Locale;

public class LoginActivity extends AppCompatActivity {
    private ListSharePreference.Set setSharedPreference;
    private ListSharePreference.Get getSharedPreference;
    Locale locale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setSharedPreference = new ListSharePreference.Set(LoginActivity.this.getApplicationContext());
        getSharedPreference = new ListSharePreference.Get(LoginActivity.this.getApplicationContext());

        setLayoutLanguage();

        getFragmentManager().beginTransaction()
                .replace(R.id.frame_home, new LoginFragment())
                .commit();
    }

    private void setLayoutLanguage() {
        String langStr = getSharedPreference.getLanguage();
        if (langStr.equals("en")) {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
            locale = new Locale("en");
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            this.getApplicationContext().getResources().updateConfiguration(config, null);
        } else {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            locale = new Locale("ar");
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            this.getApplicationContext().getResources().updateConfiguration(config, null);
        }
    }
}