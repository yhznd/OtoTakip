package com.example.yunus.ototakip;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.paolorotolo.appintro.AppIntro;

public class MyIntro extends AppIntro {


    @Override
    public void init(@Nullable Bundle savedInstanceState)
    {
        addSlide(SampleSlide.newInstance(R.layout.intro1));
        addSlide(SampleSlide.newInstance(R.layout.intro2));
        addSlide(SampleSlide.newInstance(R.layout.intro3));


        // Hide Skip/Done button
        showSkipButton(true);
        showStatusBar(false);
        setSkipText("ATLA");
        setDoneText("TAMAMLA");
        // Turn vibration on and set intensity
        // NOTE: you will probably need to ask VIBRATE permisssion in Manifest
        setVibrate(true);
        setVibrateIntensity(30);
        setDepthAnimation();

    }

    @Override
    public void onSkipPressed()
    {
        finish();
        startActivity(new Intent(this,MainActivity.class));
    }

    @Override
    public void onNextPressed() {

    }

    @Override
    public void onDonePressed()
    {
        finish();
        startActivity(new Intent(this,MainActivity.class));
    }

    @Override
    public void onSlideChanged()
    {

    }

}
