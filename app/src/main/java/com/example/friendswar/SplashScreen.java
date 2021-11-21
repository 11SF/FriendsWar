package com.example.friendswar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends AppCompatActivity {
    private static  int SPLASH_TIME = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed((Runnable) () -> {
            Intent mySuperIntent = new Intent(SplashScreen.this, AddPlayers.class);
            startActivity(mySuperIntent);
            finish();
        },SPLASH_TIME);
    }
}