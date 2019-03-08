package com.tkmsoft.akarat.activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.tkmsoft.akarat.R;
import com.tkmsoft.akarat.util.ListSharePreference;

public class SplashActivity extends AppCompatActivity {
    Button button_ar, button_en;
    ListSharePreference.Set setSharedPreference;
    ListSharePreference.Get getSharedPreference;
    Boolean isFirstRun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        setSharedPreference = new ListSharePreference.Set(SplashActivity.this.getApplicationContext());
        getSharedPreference = new ListSharePreference.Get(SplashActivity.this.getApplicationContext());

        button_ar = findViewById(R.id.btn_ar);
        button_en = findViewById(R.id.btn_en);
        insertAnimation();
//        initButtonClickListener();

        isFirstRun = getSharedPreference.getIsFirstRun();
    }

//    private void initButtonClickListener() {
//
//
//        button_en.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                setSharedPreference.setLanguage("en");
//                setSharedPreference.setIsFirstRun(false);
//
//                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
//                startActivity(intent);
//
//                finish();
//            }
//        });
//
//        button_ar.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
//
//                setSharedPreference.setLanguage("ar");
//                setSharedPreference.setIsFirstRun(false);
//
//                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
//                startActivity(intent);
//
//                finish();
//            }
//        });
//
//
//    }

    private void insertAnimation() {
        Animation animation = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.anim);
        ImageView splash = findViewById(R.id.splach_screen);
        splash.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
//                if (isFirstRun) {
//                    button_ar.setVisibility(View.VISIBLE);
//                    button_en.setVisibility(View.VISIBLE);
//                    YoYo.with(Techniques.BounceIn).playOn(button_ar);
//                    YoYo.with(Techniques.BounceIn).playOn(button_en);
//                } else {
                setSharedPreference.setLanguage("ar");
                Intent intent;
                if (getSharedPreference.getIsLogged())
                    intent = new Intent(SplashActivity.this, MainActivity.class);
                else
                    intent = new Intent(SplashActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
//                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


    }
}
