package com.app.urbanplanting;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.app.urbanplanting.R;

public class GetStarted extends AppCompatActivity {
    public static  final String SHARED_BUTTON = "started";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);
        getSupportActionBar().hide();


    }

    public void GetStarted(View view) {
        Intent intent = new Intent(GetStarted.this, LoginActivity.class);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(SHARED_BUTTON, true);
        editor.apply();
        startActivity(intent);
        finishAffinity();
    }
}