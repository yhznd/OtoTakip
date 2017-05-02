package com.example.yunus.ototakip;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_GSERVICES;


import mehdi.sakout.fancybuttons.FancyButton;

public class Yakinimdakiler extends Fragment {


    public Yakinimdakiler() {}
    public FancyButton haritayaAktar;
    public static CheckBox muayene,bakim,sigorta;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view= inflater.inflate(R.layout.activity_yakinimdakiler, container, false);
        haritayaAktar= (FancyButton) view.findViewById(R.id.buttonHaritayaGotur);
        muayene= (CheckBox) view.findViewById(R.id.checkMuayene);
        bakim= (CheckBox) view.findViewById(R.id.checkBakim);
        sigorta= (CheckBox) view.findViewById(R.id.checkSigorta);

        haritayaAktar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(getActivity(), MapsActivity.class));
            }
        });
        return view;
    }


}
