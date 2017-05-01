package com.example.yunus.ototakip;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.jaredrummler.materialspinner.MaterialSpinner;

import mehdi.sakout.fancybuttons.FancyButton;

public class AyarlarActivity extends AppCompatActivity {

    public MaterialSpinner tarihAraliklari;
    public EditText saatSecimi;
    public TimePickerDialog timePickerDialog;
    public FancyButton tercihKaydet;
    public int saat;
    public int dakika;
    public ToggleButton ayarOnOff;
    public static boolean hatitlatmaDurumu=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayarlar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_ayarlar);
        saatSecimi = (EditText) findViewById(R.id.saatSecimi);
        ayarOnOff= (ToggleButton) findViewById(R.id.ayarOnOffButton);
        tercihKaydet= (FancyButton) findViewById(R.id.buttonAyarKaydet);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AyarlarActivity.this, MainActivity.class));
            }
        });

        tarihAraliklari = (MaterialSpinner) findViewById(R.id.spinnerTarihAraliklari);
        tarihAraliklari.setItems("Gününde","1 gün önce");
        saatSecimi.setInputType(0);
        if(!ayarOnOff.isChecked())
        {
            hatitlatmaDurumu=false;
        }
        else
            hatitlatmaDurumu=true;
        SharedPreferences preferences = getSharedPreferences("SaatTercih", Context.MODE_PRIVATE);
        saat = preferences.getInt("saat", 10);
        dakika = preferences.getInt("dakika", 00);
        saatSecimi.setText(String.valueOf(saat)+":"+String.valueOf(dakika));

        tercihKaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                SharedPreferences settings = getSharedPreferences("SaatTercih", Context.MODE_PRIVATE);
                //değerleri yerleştir
                SharedPreferences.Editor editor = settings.edit();
                editor.putInt("saat", saat);
                editor.putInt("dakika", dakika);
                editor.commit();
                Log.d("Giden",String.valueOf(saat));
                Log.d("Giden",String.valueOf(dakika));
                Toast.makeText(AyarlarActivity.this,"Hatırla saatiniz "+saat+":"+dakika+" olarak değiştirildi.",Toast.LENGTH_LONG).show();
            }
        });




    }

    public void selectTimeToDisplay(View view)
    {
        Calendar calendar = Calendar.getInstance();

        timePickerDialog = new TimePickerDialog(
                AyarlarActivity.this,
                onTimeSetListener,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                DateFormat.is24HourFormat(this));
        timePickerDialog.show();

    }

    TimePickerDialog.OnTimeSetListener onTimeSetListener
            = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute)
        {
            final Calendar calNow = Calendar.getInstance();
            final Calendar calSet = (Calendar) calNow.clone();

            calSet.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calSet.set(Calendar.MINUTE, minute);
            calSet.set(Calendar.SECOND, 0);
            calSet.set(Calendar.MILLISECOND, 0);
            if (calSet.compareTo(calNow) <= 0)
            {

                calSet.add(Calendar.DATE, 1);
            }


            saatSecimi.setText(hourOfDay+":"+minute);
            saat=hourOfDay;
            dakika=minute;


        }
    };




}
