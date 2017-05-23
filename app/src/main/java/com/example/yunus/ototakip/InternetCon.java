package com.example.yunus.ototakip;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class InternetCon extends AppCompatActivity {


    public TextView dene;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internet_con);
        dene = (TextView) findViewById(R.id.textDene);

        dene.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (internetErisimi())
                {
                    Intent gelen=getIntent();
                    String gelenActivity=gelen.getStringExtra("hataKaynak");
                    switch (gelenActivity)
                    {
                        case "Giris":
                            startActivity(new Intent(InternetCon.this,Giris.class));
                            break;
                        case "MainActivity":
                            startActivity(new Intent(InternetCon.this,MainActivity.class));
                        break;
                        case "AracEkle":
                            startActivity(new Intent(InternetCon.this,AracEkle.class));
                            break;
                    }
                }
                else
                {
                    CoordinatorLayout rootLayout = (CoordinatorLayout) findViewById(R.id.myCoordinatorLayout2);
                    Snackbar.make(rootLayout, "İnternet bağlantısı kurulamadı.", Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }


    public boolean internetErisimi()
    {

        ConnectivityManager conMgr = (ConnectivityManager) getSystemService (Context.CONNECTIVITY_SERVICE);
        //net bağlantısı varsa, erişilebilir ve bağlı ise true gönder
        if (conMgr.getActiveNetworkInfo() != null && conMgr.getActiveNetworkInfo().isAvailable()
                && conMgr.getActiveNetworkInfo().isConnected()) {
            return true;
        } else {
            return false;
        }

    }

}
