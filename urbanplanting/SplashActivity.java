package com.app.urbanplanting;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatActivity;

import com.app.urbanplanting.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        boolean isStarted = prefs.getBoolean(GetStarted.SHARED_BUTTON,false);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if(isStarted){
                    Intent loginActivity = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(loginActivity);
                    finishAffinity();
                }else{
                    Intent getStarted = new Intent(SplashActivity.this, GetStarted.class);
                    startActivity(getStarted);
                    finishAffinity();
                }

            }
        },3000);


    }
}