package com.example.yunus.ototakip;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class IpuclariSayfasi extends AppCompatActivity {

    public ImageView ipucu1,ipucu2,ipucu3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ipuclari_sayfasi);

        ipucu1= (ImageView) findViewById(R.id.imageView13);
        ipucu2= (ImageView) findViewById(R.id.imageView14);
        ipucu3= (ImageView) findViewById(R.id.imageView15);

        ipucu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Toast.makeText(IpuclariSayfasi.this,"SELAM SUAT. TRAFİĞE BASTIN.",Toast.LENGTH_LONG).show();
            }
        });
    }
}
