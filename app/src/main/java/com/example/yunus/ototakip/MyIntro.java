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
        //startActivity(new Intent(this,SignInActivity.class));
    }

    @Override
    public void onNextPressed() {

    }

    @Override
    public void onDonePressed()
    {
        finish();
        //startActivity(new Intent(this,BluetoothBaglanti.class));
    }

    @Override
    public void onSlideChanged()
    {

    }

    //SignIn Activity onCreate metoduna eklenecek kod.
    /*Thread t = new Thread(new Runnable() {
        @Override
        public void run() {
            //  Initialize SharedPreferences
            SharedPreferences getPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
            //  Create a new boolean and preference and set it to true
            isFirstStart = getPrefs.getBoolean("firstStart", true);
            //  If the activity has never started before...
            if (isFirstStart) {
                //  Launch app intro
                Intent i = new Intent(BluetoothBaglanti.this, MyIntro.class);
                startActivity(i);
                //  Make a new preferences editor
                SharedPreferences.Editor e = getPrefs.edit();
                //  Edit preference to make it false because we don't want this to run again
                e.putBoolean("firstStart", false);
                //  Apply changes
                e.apply();
            }
        }
    });

// Start the thread
    t.start();*/
}
