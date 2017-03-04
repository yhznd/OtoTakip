package com.example.yunus.ototakip;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mehdi.sakout.fancybuttons.FancyButton;

public class AracEkle extends AppCompatActivity {

    FancyButton guncelle,sil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arac_ekle);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_aracekle);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AracEkle.this, MainActivity.class));
            }
        });
        guncelle= (FancyButton) findViewById(R.id.buttonGuncelle);
        sil= (FancyButton) findViewById(R.id.buttonSil);



        guncelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CoordinatorLayout rootLayout = (CoordinatorLayout) findViewById(R.id.myCoordinatorLayout);
                Snackbar.make(rootLayout, "Araç güncellendi!", Snackbar.LENGTH_LONG)
                        .setAction("TAMAM", new View.OnClickListener() {
                            @Override
                            public void onClick(View v)
                            {
                               //guncelleye basıldığında olacak olan olay
                            }
                        } )
                        .show();
            }
        });

        sil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CoordinatorLayout rootLayout = (CoordinatorLayout) findViewById(R.id.myCoordinatorLayout);
                Snackbar.make(rootLayout, "Araç silindi!", Snackbar.LENGTH_LONG)
                        .setAction("TAMAM", new View.OnClickListener() {
                            @Override
                            public void onClick(View v)
                            {
                                //boş bırakıldığında dismiss oluyor
                                //sil basıldığında olacak olan olay
                            }
                        } )
                        .show();
            }
        });

    }
}
