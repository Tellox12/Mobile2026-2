package com.example.oauth.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

import com.example.oauth.MainActivity;
import com.example.oauth.R;
import com.example.oauth.utils.TokenManager;

public class SplashActivity extends AppCompatActivity {

    private static final long SPLASH_DELAY_MS = 2200L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Class<?> nextScreen = new TokenManager(this).isLoggedIn()
                    ? MainActivity.class
                    : LoginActivity.class;
            startActivity(new Intent(this, nextScreen));
            finish();
        }, SPLASH_DELAY_MS);
    }
}
