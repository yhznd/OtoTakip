package com.example.yunus.ototakip;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.github.ybq.android.spinkit.style.DoubleBounce;

public class SplashScreen extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 3500;//3.5sn
    DoubleBounce doubleBounce = new DoubleBounce();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                // Ana activity başlat
                startActivity(new Intent(SplashScreen.this, MainActivity.class));
                // close this activity
                finish();//Activity örneği yok edildi
            }
        }, SPLASH_TIME_OUT);
    }
}
