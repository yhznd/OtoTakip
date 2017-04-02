package com.example.yunus.ototakip;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


public class IpuclariSayfasi extends AppCompatActivity  {

    public ImageButton ipucu1, ipucu2, ipucu3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ipuclari_sayfasi);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_ipuclari);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(IpuclariSayfasi.this, MainActivity.class));
            }
        });

        ipucu1 = (ImageView) findViewById(R.id.go_Traffic);
        ipucu2 = (ImageView) findViewById(R.id.go_Aid);
        ipucu3 = (ImageView) findViewById(R.id.go_Engine);

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
