package com.example.andriod.todolistactivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    private static int Splash = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_splash);

        SharedPreferences prefs = getSharedPreferences("EXP", MODE_PRIVATE);
        int restoredText = prefs.getInt("EXP", 0);

        if (restoredText==0){
            SharedPreferences.Editor editor = getSharedPreferences("EXP", MODE_PRIVATE).edit();
            editor.putInt("EXP", 0);
            editor.apply();
        } else{
            int idName = prefs.getInt("EXP", 0);

        }


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainintent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(mainintent);
                finish();
            }
        },Splash);
    }
}
