package com.example.yunus.ototakip;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class AyarlarActivity extends AppCompatActivity {

    public Spinner tarihAraliklari;
    public String[] bildirimTarihleri={"1 gün önce","1 hafta önce","2 hafta önce","1 ay önce"};
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
        tarihAraliklari = (Spinner) findViewById(R.id.spinnerTarihAraliklari);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, bildirimTarihleri);
        tarihAraliklari.setAdapter(adapter);
    }
}
