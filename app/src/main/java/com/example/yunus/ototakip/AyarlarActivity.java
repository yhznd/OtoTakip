package com.example.yunus.ototakip;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.jaredrummler.materialspinner.MaterialSpinner;

public class AyarlarActivity extends AppCompatActivity {

    public MaterialSpinner tarihAraliklari;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayarlar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_ayarlar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AyarlarActivity.this, MainActivity.class));
            }
        });

        tarihAraliklari = (MaterialSpinner) findViewById(R.id.spinnerTarihAraliklari);
        tarihAraliklari.setItems("1 gün önce","1 hafta önce","2 hafta önce","1 ay önce");
    }
}
