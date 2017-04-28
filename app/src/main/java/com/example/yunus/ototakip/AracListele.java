package com.example.yunus.ototakip;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import mehdi.sakout.fancybuttons.FancyButton;

public class AracListele extends AppCompatActivity  {

    private EditText textPlaka;
    private EditText textModel;
    private EditText textKaskoTarihi;
    private EditText textMuayeneTarihi;
    private EditText textSigortaTarihi;
    private EditText textEmisyonTarihi;
    Calendar myCalendar = Calendar.getInstance();
    String myFormat = "dd/MM/yyyy"; //In which you need put here
    SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
    String userId;
    String aracPlakasi;
    Araba araba;
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
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AracListele.this, MainActivity.class));
            }
        });

        textPlaka = (EditText) findViewById(R.id.editTextPlaka);
        textModel = (EditText) findViewById(R.id.editTextModel);
        textKaskoTarihi = (EditText) findViewById(R.id.editTextKaskoTarihi);
        textMuayeneTarihi = (EditText) findViewById(R.id.editTextMuayeneTarihi);
        textSigortaTarihi = (EditText) findViewById(R.id.editTextSigortaTarihi);
        textEmisyonTarihi = (EditText) findViewById(R.id.editTextEmisyonTarihi);
        buttonAracGuncelle = (FloatingActionButton) findViewById(R.id.editActionButton);
        buttonAracSil = (FloatingActionButton) findViewById(R.id.deleteActionButton);
        disableEditText(textPlaka);
        disableEditText(textKaskoTarihi);
        disableEditText(textMuayeneTarihi);
        disableEditText(textSigortaTarihi);
        disableEditText(textEmisyonTarihi);

        buttonAracGuncelle.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                enableEditText(textPlaka);
                enableEditText(textKaskoTarihi);
                enableEditText(textMuayeneTarihi);
                enableEditText(textSigortaTarihi);
                enableEditText(textEmisyonTarihi);
            }
        });




        Intent alPlaka=getIntent();
        aracPlakasi=alPlaka.getStringExtra("aracPlaka");
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("Arabalar").child(userId).orderByKey().equalTo(aracPlakasi);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                for(DataSnapshot singleSnapshot : dataSnapshot.getChildren())
                {
                    String plaka = singleSnapshot.getKey();
                    String muayeneTarihi= (String) singleSnapshot.child("editTextMuyeneTarihi").getValue();
                    String sigortaTarihi= (String) singleSnapshot.child("editTextSigortaTarihi").getValue();
                    String emisyonTarihi= (String) singleSnapshot.child("editTextEmisyonTarihi").getValue();
                    String kaskoTarihi= (String) singleSnapshot.child("editTextKaskoTarihi").getValue();
                    String model= (String) singleSnapshot.child("editTextModel").getValue();

                    textPlaka.setText(plaka);
                    textMuayeneTarihi.setText(muayeneTarihi);
                    textSigortaTarihi.setText(sigortaTarihi);
                    textEmisyonTarihi.setText(emisyonTarihi);
                    textKaskoTarihi.setText(kaskoTarihi);
                    textModel.setText(model);
                    Log.d("Listele","Başarılı");
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError)
            {
                Log.d("Listele","Başarısız:"+databaseError);
            }
        });

    }

    private void disableEditText(EditText editText) {
        editText.setFocusable(false);
        editText.setEnabled(false);
        editText.setCursorVisible(false);
        editText.setKeyListener(null);
        editText.setBackgroundColor(Color.TRANSPARENT);
    }

    private void enableEditText(EditText editText) {
        editText.setFocusable(true);
        editText.setEnabled(true);
        editText.setCursorVisible(true);
        editText.setKeyListener(new AppCompatEditText(getApplicationContext()).getKeyListener());
        editText.setBackgroundColor(getResources().getColor(R.color.colorAccent));
    }


}