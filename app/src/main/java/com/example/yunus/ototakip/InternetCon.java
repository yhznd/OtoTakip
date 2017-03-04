package com.example.yunus.ototakip;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import mehdi.sakout.fancybuttons.FancyButton;

public class InternetCon extends AppCompatActivity {


    public TextView dene;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internet_con);
        dene= (TextView) findViewById(R.id.textDene);

        dene.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                CoordinatorLayout rootLayout = (CoordinatorLayout) findViewById(R.id.myCoordinatorLayout2);
                Snackbar.make(rootLayout, "İnternet bağlantısı kurulamadı.", Snackbar.LENGTH_LONG)
                        .setAction("YENİDEN DENE", new View.OnClickListener() {
                            @Override
                            public void onClick(View v)
                            {
                                if (internetErisimi())
                                {
                                    startActivity(new Intent(InternetCon.this, MainActivity.class));
                                }
                                else
                                {

                                }

                            }
                        } )
                        .show();

            }
        });

    }

    public boolean internetErisimi() {

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
