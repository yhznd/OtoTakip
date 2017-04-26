package com.example.yunus.ototakip;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import mehdi.sakout.fancybuttons.FancyButton;

public class AracListele extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextPlaka;
    private EditText editTextModel;
    private EditText editTextKaskoTarihi;
    private EditText editTextMuayeneTarihi;
    private EditText editTextSigortaTarihi;
    private EditText editTextEmisyonTarihi;

    Calendar myCalendar = Calendar.getInstance();
    String myFormat = "dd/MM/yyyy"; //In which you need put here
    SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
    String userId;
    String userMail;
    String aracPlakasi;
    String aracModeli;
    String aracKaskoTarhi;
    String aracTrafikTrhi;
    String aracMuayeneTrhi;
    String aracSigortaTrhi;
    Araba araba;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase database;
    DatabaseReference reference;
    FloatingActionButton buttonAracGuncelle,buttonAracSil;
    private DatePickerDialog kaskoTarihiDialog;
    private DatePickerDialog sigortaTarihiDialog;
    private DatePickerDialog muayeneTarihiDialog;
    private DatePickerDialog emisyonTarihiDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arac_ekle);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_aracgoruntule);

        firebaseAuth=FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();


        userId=firebaseAuth.getCurrentUser().getUid();
        userMail=firebaseAuth.getCurrentUser().getEmail();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AracListele.this, MainActivity.class));
            }
        });

        editTextPlaka=(EditText)findViewById(R.id.editTextPlaka);
        editTextModel=(EditText)findViewById(R.id.editTextModel);
        editTextKaskoTarihi=(EditText)findViewById(R.id.editTextKaskoTarihi);
        editTextKaskoTarihi.setInputType(InputType.TYPE_NULL);
        editTextKaskoTarihi.setOnClickListener((View.OnClickListener) this);
        editTextMuayeneTarihi=(EditText)findViewById(R.id.editTextMuayeneTarihi);
        editTextMuayeneTarihi.setInputType(InputType.TYPE_NULL);
        editTextMuayeneTarihi.setOnClickListener((View.OnClickListener) this);
        editTextSigortaTarihi=(EditText)findViewById(R.id.editTextSigortaTarihi);
        editTextSigortaTarihi.setInputType(InputType.TYPE_NULL);
        editTextSigortaTarihi.setOnClickListener((View.OnClickListener) this);
        editTextEmisyonTarihi=(EditText)findViewById(R.id.editTextEmisyonTarihi);
        editTextEmisyonTarihi.setInputType(InputType.TYPE_NULL);
        editTextEmisyonTarihi.setOnClickListener((View.OnClickListener) this);
        buttonAracGuncelle=(FloatingActionButton) findViewById(R.id.editActionButton);
        buttonAracSil=(FloatingActionButton) findViewById(R.id.deleteActionButton);





        Calendar newCalendar = Calendar.getInstance();
        kaskoTarihiDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                editTextKaskoTarihi.setText(dateFormat.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        sigortaTarihiDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                editTextSigortaTarihi.setText(dateFormat.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        muayeneTarihiDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                editTextMuayeneTarihi.setText(dateFormat.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));


        emisyonTarihiDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                editTextEmisyonTarihi.setText(dateFormat.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));



    }

    @Override
    public void onClick(View view)
    {
        if(view == editTextKaskoTarihi)
        {
            kaskoTarihiDialog.show();
        }
        else if(view == editTextEmisyonTarihi)
        {
            emisyonTarihiDialog.show();
        }

        else if(view == editTextMuayeneTarihi)
        {
            muayeneTarihiDialog.show();
        }

        else if(view == editTextSigortaTarihi)
        {
            sigortaTarihiDialog.show();
        }
    }





    private void aracGuncelle(){

        String aracPlakasi=editTextPlaka.getText().toString();
        String aracModeli=editTextModel.getText().toString();
        String aracKaskoTrhi=editTextKaskoTarihi.getText().toString();
        String aracTrafikTrhi=editTextMuayeneTarihi.getText().toString();
        String aracMuayeneTrhi=editTextSigortaTarihi.getText().toString();
        String aracSigortaTrhi=editTextEmisyonTarihi.getText().toString();




    }
}
