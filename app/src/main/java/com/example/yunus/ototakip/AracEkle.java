package com.example.yunus.ototakip;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import mehdi.sakout.fancybuttons.FancyButton;

public class AracEkle extends AppCompatActivity implements View.OnClickListener {


    private EditText editTextPlaka;
    private MaterialSpinner editTextModel;
    private EditText editTextKaskoTarihi;
    private EditText editTextMuayeneTarihi;
    private EditText editTextSigortaTarihi;
    private EditText editTextEmisyonTarihi;
    Calendar myCalendar = Calendar.getInstance();
    String myFormat = "dd/MM/yyyy";
    public boolean cancel = false;
    SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
    String userId;
    String userMail;
    Araba araba;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase database;

    DatabaseReference reference;
    FloatingActionButton buttonAracKaydet;
    private DatePickerDialog kaskoTarihiDialog;
    private DatePickerDialog sigortaTarihiDialog;
    private DatePickerDialog muayeneTarihiDialog;
    private DatePickerDialog emisyonTarihiDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arac_ekle);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_aracekle);


        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();


        userId = firebaseAuth.getCurrentUser().getUid();
        userMail = firebaseAuth.getCurrentUser().getEmail();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AracEkle.this, MainActivity.class));
            }
        });

        editTextPlaka = (EditText) findViewById(R.id.editTextPlaka);
        editTextModel = (MaterialSpinner) findViewById(R.id.editTextModel);
        editTextModel.setItems("Araç modelinizi seçiniz:", "Mercedes", "BMW", "Audi", "Toyota", "Opel", "Renault", "Volkswagen", "Range Rover");
        editTextKaskoTarihi = (EditText) findViewById(R.id.editTextKaskoTarihi);
        editTextKaskoTarihi.setInputType(0);
        editTextKaskoTarihi.setOnClickListener((View.OnClickListener) this);
        editTextMuayeneTarihi = (EditText) findViewById(R.id.editTextMuayeneTarihi);
        editTextMuayeneTarihi.setInputType(0);
        editTextMuayeneTarihi.setOnClickListener((View.OnClickListener) this);
        editTextSigortaTarihi = (EditText) findViewById(R.id.editTextSigortaTarihi);
        editTextSigortaTarihi.setInputType(0);
        editTextSigortaTarihi.setOnClickListener((View.OnClickListener) this);
        editTextEmisyonTarihi = (EditText) findViewById(R.id.editTextEmisyonTarihi);
        editTextEmisyonTarihi.setInputType(0);
        editTextEmisyonTarihi.setOnClickListener((View.OnClickListener) this);
        buttonAracKaydet = (FloatingActionButton) findViewById(R.id.buttonAracKaydet);


        buttonAracKaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                aracKaydet();

            }
        });

        Calendar newCalendar = Calendar.getInstance();
        kaskoTarihiDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                editTextKaskoTarihi.setText(dateFormat.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        sigortaTarihiDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                editTextSigortaTarihi.setText(dateFormat.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        muayeneTarihiDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                editTextMuayeneTarihi.setText(dateFormat.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));


        emisyonTarihiDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                editTextEmisyonTarihi.setText(dateFormat.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));


    }

    @Override
    public void onClick(View view) {
        if (view == editTextKaskoTarihi) {
            kaskoTarihiDialog.show();
        } else if (view == editTextEmisyonTarihi) {
            emisyonTarihiDialog.show();
        } else if (view == editTextMuayeneTarihi) {
            muayeneTarihiDialog.show();
        } else if (view == editTextSigortaTarihi) {
            sigortaTarihiDialog.show();
        }
    }

    public boolean internetErisimi() {

        ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        //net bağlantısı varsa, erişilebilir ve bağlı ise true gönder
        if (conMgr.getActiveNetworkInfo() != null && conMgr.getActiveNetworkInfo().isAvailable()
                && conMgr.getActiveNetworkInfo().isConnected()) {
            return true;
        } else {
            return false;
        }

    }


    private void aracKaydet()
    {

        String aracPlakasi = editTextPlaka.getText().toString().trim().toUpperCase();
        editTextPlaka.setError(null);
        int indis = editTextModel.getSelectedIndex();
        String aracModeli = editTextModel.getItems().get(indis).toString();
        String aracKaskoTrhi = editTextKaskoTarihi.getText().toString();
        String aracTrafikTrhi = editTextMuayeneTarihi.getText().toString();
        String aracMuayeneTrhi = editTextSigortaTarihi.getText().toString();
        String aracSigortaTrhi = editTextEmisyonTarihi.getText().toString();
        View focusView = null;
        if (TextUtils.isEmpty(aracPlakasi))
        {
            editTextPlaka.setError(getString(R.string.plaka_alan_gerekli));
            focusView = editTextPlaka;
            cancel = true;
        }

        if (internetErisimi())
        {
            if (cancel == false)
            {
                araba = new Araba(userMail, aracModeli, aracKaskoTrhi, aracTrafikTrhi, aracMuayeneTrhi, aracSigortaTrhi);
                reference = database.getReference("Arabalar").child(userId).child(aracPlakasi);
                reference.setValue(araba);
                Toast.makeText(AracEkle.this, "Araç başarıyla eklendi!", Toast.LENGTH_LONG).show();
                CoordinatorLayout rootLayout = (CoordinatorLayout) findViewById(R.id.myCoordinatorLayout);
                Snackbar.make(rootLayout, "Araç eklendi!", Snackbar.LENGTH_LONG).show();
                startActivity(new Intent(AracEkle.this, MainActivity.class));
            }

        }

        else
        {
            Intent hata = new Intent(AracEkle.this, InternetCon.class);
            startActivity(hata);
        }
        cancel = false;


    }
}

