package com.example.yunus.ototakip;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.Slide;
import android.view.View;
import android.widget.ImageView;

public class IpuclariSayfasi extends AppCompatActivity {

    public ImageView ipucu1,ipucu2,ipucu3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ipuclari_sayfasi);

        ipucu1= (ImageView) findViewById(R.id.imageTraffic);
        ipucu2= (ImageView) findViewById(R.id.imageAid);
       ipucu3= (ImageView) findViewById(R.id.imageEngine);


        ipucu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(IpuclariSayfasi.this,TrafficActivity.class));
            }
        });

      ipucu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(IpuclariSayfasi.this,AidActivity.class));
            }
        });

        ipucu3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(IpuclariSayfasi.this,EngineActivity.class));
            }
        });

    }






}
