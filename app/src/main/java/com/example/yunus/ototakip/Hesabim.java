package com.example.yunus.ototakip;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;
import mehdi.sakout.fancybuttons.FancyButton;

public class Hesabim extends AppCompatActivity {

    public CircleImageView kullaniciHesapResmi;
    public FancyButton sifreDegistir,hesapSil,hesapGuncelle;
    public TextView kullanici,kullaniciMail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hesabim);
        kullanici= (TextView) findViewById(R.id.kullaniciHesapAd);
        kullaniciMail= (TextView) findViewById(R.id.kullaniciHesapMail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_hesabim);

        Typeface type = Typeface.createFromAsset(getAssets(), "fonts/CaviarDreams.ttf");
        kullanici.setTypeface(type);
        kullaniciMail.setTypeface(type);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Hesabim.this, MainActivity.class));
            }
        });
    }
}
