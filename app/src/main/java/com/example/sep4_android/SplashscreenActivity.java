package com.example.sep4_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashscreenActivity extends AppCompatActivity {

    private ImageView logoImage;
    private TextView logoText;
    Animation topAnim, bottomAnim;
    private static int SPLASH_SCREEN = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

    logoImage = findViewById(R.id.splashScreenLogo);
    logoText = findViewById(R.id.splashScreenText);

    //Animations
    topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
    bottomAnim= AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

    logoImage.setAnimation(bottomAnim);
    logoText.setAnimation(bottomAnim);

    new Handler().postDelayed((Runnable) () -> {
        Intent intent = new Intent(SplashscreenActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();
    },SPLASH_SCREEN);

    }
}