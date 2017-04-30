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
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.jaredrummler.materialspinner.MaterialSpinner;

public class AyarlarActivity extends AppCompatActivity {

    public MaterialSpinner tarihAraliklari;
    public EditText saatSecimi;
    public TimePickerDialog timePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayarlar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_ayarlar);
        saatSecimi = (EditText) findViewById(R.id.saatSecimi);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AyarlarActivity.this, MainActivity.class));
            }
        });

        tarihAraliklari = (MaterialSpinner) findViewById(R.id.spinnerTarihAraliklari);
        tarihAraliklari.setItems("1 gün önce");
        saatSecimi.setInputType(0);
        saatSecimi.setText("13"+":"+"00");




    }

    public void selectTimeToDisplay(View view) {
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
            SharedPreferences settings = getSharedPreferences("SaatTercih", Context.MODE_PRIVATE);
            //değerleri yerleştir
            SharedPreferences.Editor editor = settings.edit();
            editor.putInt("saat", hourOfDay);
            editor.putInt("dakika", minute);
            editor.commit();

        }
    };


}
