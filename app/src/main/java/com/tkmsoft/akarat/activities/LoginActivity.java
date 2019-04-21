package com.tkmsoft.akarat.activities;

import android.content.Context;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import com.tkmsoft.akarat.fragment.login.LoginFragment;
import com.tkmsoft.akarat.R;
import com.tkmsoft.akarat.interfaces.IOnBackPressed;
import com.tkmsoft.akarat.util.ListSharePreference;
import com.tkmsoft.akarat.util.MoveToFragment;
import com.tkmsoft.akarat.util.MyContextWrapper;

import java.util.Locale;

import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {
    private ListSharePreference.Set setSharedPreference;
    private ListSharePreference.Get getSharedPreference;
    Locale locale;
    private IOnBackPressed onBackPressedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(LoginActivity.this);
        setLayoutLanguage(getLang());
        MoveToFragment moveFragment = new MoveToFragment(this);
        moveFragment.moveInLogin(new LoginFragment());
    }

    private void setLayoutLanguage(String language) {
        if (language.equals("ar"))
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        else
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        setSharedPreference = new ListSharePreference.Set(newBase);
        getSharedPreference = new ListSharePreference.Get(newBase);
        super.attachBaseContext(MyContextWrapper.wrap(newBase, getLang()));
    }

    private String getLang() {
        return getSharedPreference.getLanguage();
    }

    @Override
    public void onBackPressed() {
        if (onBackPressedListener != null)
            onBackPressedListener.onBackPressed();
        else
            super.onBackPressed();
    }

    public void setOnBackPressedListener(IOnBackPressed onBackPressedListener) {
        this.onBackPressedListener = onBackPressedListener;
    }
}