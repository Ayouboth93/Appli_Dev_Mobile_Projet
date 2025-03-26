package com.example.appli_dev_mobile_v2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    private static final int SPLASH_DELAY = 2000; // 2 secondes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Délai avant de lancer MainActivity
        new Handler().postDelayed(() -> {
            startActivity(new Intent(SplashActivity.this, RegisterActivity.class));
            finish(); // Fermer SplashActivity pour ne pas revenir dessus en appuyant sur "Back"
        }, SPLASH_DELAY);
    }
}
