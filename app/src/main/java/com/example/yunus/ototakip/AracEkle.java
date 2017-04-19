package com.example.yunus.ototakip;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import mehdi.sakout.fancybuttons.FancyButton;

public class AracEkle extends AppCompatActivity {


    private EditText editTextPlaka;
    private EditText editTextModel;
    private EditText editTextKaskoTarihi;
    private EditText editTextMuayeneTarihi;
    private EditText editTextSigortaTarihi;
    private EditText editTextEmisyonTarihi;

    Calendar myCalendar = Calendar.getInstance();
    String myFormat = "dd/MM/yyyy"; //In which you need put here
    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);

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

    FancyButton guncelle,sil;
    Button buttonAracKaydet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arac_ekle);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_aracekle);

        firebaseAuth=FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        userId=firebaseAuth.getCurrentUser().getUid();
        userMail=firebaseAuth.getCurrentUser().getEmail();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AracEkle.this, MainActivity.class));
            }
        });

        editTextPlaka=(EditText)findViewById(R.id.editTextPlaka);
        editTextModel=(EditText)findViewById(R.id.editTextModel);
        editTextKaskoTarihi=(EditText)findViewById(R.id.editTextKaskoTarihi);
        editTextMuayeneTarihi=(EditText)findViewById(R.id.editTextMuayeneTarihi);
        editTextSigortaTarihi=(EditText)findViewById(R.id.editTextSigortaTarihi);
        editTextEmisyonTarihi=(EditText)findViewById(R.id.editTextEmisyonTarihi);
        buttonAracKaydet=(Button) findViewById(R.id.buttonAracKaydet);



        buttonAracKaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Kaydet();
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


        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                editTextKaskoTarihi.setText(sdf.format(myCalendar.getTime()));
            }

        };

        editTextKaskoTarihi.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                new DatePickerDialog(AracEkle.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        editTextKaskoTarihi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(AracEkle.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        final DatePickerDialog.OnDateSetListener date1 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                editTextMuayeneTarihi.setText(sdf.format(myCalendar.getTime()));
            }

        };

        editTextMuayeneTarihi.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                new DatePickerDialog(AracEkle.this, date1, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        editTextMuayeneTarihi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(AracEkle.this, date1, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        final DatePickerDialog.OnDateSetListener date2 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                editTextSigortaTarihi.setText(sdf.format(myCalendar.getTime()));
            }

        };

        editTextSigortaTarihi.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                new DatePickerDialog(AracEkle.this, date2, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        editTextSigortaTarihi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(AracEkle.this, date2, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        final DatePickerDialog.OnDateSetListener date3 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                editTextEmisyonTarihi.setText(sdf.format(myCalendar.getTime()));
            }

        };

        editTextEmisyonTarihi.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                new DatePickerDialog(AracEkle.this, date3, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        editTextEmisyonTarihi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(AracEkle.this, date3, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });



    }
    private void Kaydet(){

        String aracPlakasi=editTextPlaka.getText().toString();
        String aracModeli=editTextModel.getText().toString();
        String aracKaskoTrhi=editTextKaskoTarihi.getText().toString();
        String aracTrafikTrhi=editTextMuayeneTarihi.getText().toString();
        String aracMuayeneTrhi=editTextSigortaTarihi.getText().toString();
        String aracSigortaTrhi=editTextEmisyonTarihi.getText().toString();


        araba =new Araba(userMail,userId,aracModeli,aracKaskoTrhi,aracTrafikTrhi,aracMuayeneTrhi,aracSigortaTrhi);

        //araba =new Araba(userMail,userId,"astra","kasko","trafik","muayene","sigorta");

        //String aracPlakasi = "34U8269";

        reference=database.getReference("Arabalar").child(aracPlakasi);

        reference.setValue(araba);

    }

}
