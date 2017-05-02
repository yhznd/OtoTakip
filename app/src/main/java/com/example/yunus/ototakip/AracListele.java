package com.example.yunus.ototakip;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jaredrummler.materialspinner.MaterialSpinner;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import mehdi.sakout.fancybuttons.FancyButton;

public class AracListele extends AppCompatActivity implements View.OnClickListener {

    private EditText textPlaka;
    private MaterialSpinner textModel;
    private EditText textKaskoTarihi;
    private EditText textMuayeneTarihi;
    private EditText textSigortaTarihi;
    private EditText textEmisyonTarihi;
    private FirebaseAuth firebaseAuth;
    Calendar newCalendar = Calendar.getInstance();
    String myFormat = "dd/MM/yyyy"; //In which you need put here
    SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
    String userId,userMail;
    String aracPlakasi;
    Araba araba;
    FloatingActionButton buttonAracGuncelle,buttonAracSil,buttonAracTamam;
    private DatePickerDialog kaskoTarihiDialog;
    private DatePickerDialog sigortaTarihiDialog;
    private DatePickerDialog muayeneTarihiDialog;
    private DatePickerDialog emisyonTarihiDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arac_listele);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_aracgoruntule);
        toolbar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AracListele.this, MainActivity.class));
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        userMail=firebaseAuth.getCurrentUser().getEmail();
        userId=firebaseAuth.getCurrentUser().getUid();
        textPlaka = (EditText) findViewById(R.id.editTextPlaka);
        textModel = (MaterialSpinner) findViewById(R.id.editTextModel);
        textKaskoTarihi = (EditText) findViewById(R.id.editTextKaskoTarihi);
        textMuayeneTarihi = (EditText) findViewById(R.id.editTextMuayeneTarihi);
        textSigortaTarihi = (EditText) findViewById(R.id.editTextSigortaTarihi);
        textEmisyonTarihi = (EditText) findViewById(R.id.editTextEmisyonTarihi);
        buttonAracGuncelle = (FloatingActionButton) findViewById(R.id.editActionButton);
        buttonAracSil = (FloatingActionButton) findViewById(R.id.deleteActionButton);
        buttonAracTamam = (FloatingActionButton) findViewById(R.id.doneActionButton);
        disableEditText(textKaskoTarihi);
        disableEditText(textPlaka);
        disableEditText(textMuayeneTarihi);
        disableEditText(textSigortaTarihi);
        disableEditText(textEmisyonTarihi);
        buttonAracTamam.setVisibility(Button.INVISIBLE);


        Intent alPlaka=getIntent();
        aracPlakasi=alPlaka.getStringExtra("aracPlaka");
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Arabalar").child(userId).child(aracPlakasi);
        reference.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot snapshot)
            {

                        String plaka = snapshot.getKey();
                        String muayeneTarihi = (String) snapshot.child("editTextMuayeneTarihi").getValue();
                        String sigortaTarihi = (String) snapshot.child("editTextSigortaTarihi").getValue();
                        String emisyonTarihi = (String) snapshot.child("editTextEmisyonTarihi").getValue();
                        String kaskoTarihi = (String) snapshot.child("editTextKaskoTarihi").getValue();
                        String model = (String) snapshot.child("editTextModel").getValue();
                        textPlaka.setText(aracPlakasi);
                        textMuayeneTarihi.setText(muayeneTarihi);
                        textSigortaTarihi.setText(sigortaTarihi);
                        textEmisyonTarihi.setText(emisyonTarihi);
                        textKaskoTarihi.setText(kaskoTarihi);
                        textModel.setItems(model);
                }


            @Override
            public void onCancelled(DatabaseError databaseError)
            {
                Log.d("Listeleme", "Başarısız" + databaseError);
            }
        });


        buttonAracGuncelle.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                    enableEditText(textPlaka);
                    enableTarihYerleri(textKaskoTarihi);
                    enableTarihYerleri(textMuayeneTarihi);
                    enableTarihYerleri(textSigortaTarihi);
                    enableTarihYerleri(textEmisyonTarihi);
                    if(buttonAracTamam.getVisibility()==Button.INVISIBLE)
                    {
                        buttonAracTamam.setVisibility(Button.VISIBLE);
                    }
                    buttonAracGuncelle.setVisibility(Button.INVISIBLE);
                    buttonAracSil.setVisibility(Button.INVISIBLE);
                    textModel.setItems("Araç modelinizi seçiniz:","Mercedes","BMW","Audi","Toyota","Opel","Renault","Volkswagen","Range Rover");
            }
        });

        buttonAracTamam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                //asıl güncelleme işlemi burada
                String eskiPlaka=reference.getKey();
                DatabaseReference referenceSil = FirebaseDatabase.getInstance().getReference().child("Arabalar").child(userId).child(eskiPlaka);
                referenceSil.removeValue();
                //eskiyi sil
                String aracPlakasi=textPlaka.getText().toString().trim().toUpperCase();
                int indis=textModel.getSelectedIndex();
                String aracModeli=textModel.getItems().get(indis).toString();
                String aracKaskoTrhi=textKaskoTarihi.getText().toString();
                String aracTrafikTrhi=textMuayeneTarihi.getText().toString();
                String aracMuayeneTrhi=textSigortaTarihi.getText().toString();
                String aracSigortaTrhi=textEmisyonTarihi.getText().toString();
                DatabaseReference referenceYeni = FirebaseDatabase.getInstance().getReference().child("Arabalar").child(userId).child(aracPlakasi);
                Araba newAraba =new Araba(userMail,aracModeli,aracKaskoTrhi,aracTrafikTrhi,aracMuayeneTrhi,aracSigortaTrhi);
                referenceYeni.setValue(newAraba);
                //yeniyi ekle
                Toast.makeText(AracListele.this,"Aracınız başarıyla güncellendi!",Toast.LENGTH_LONG).show();
                startActivity(new Intent(AracListele.this,MainActivity.class));

            }
        });

        buttonAracSil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Arabalar").child(userId).child(aracPlakasi);
                reference.removeValue();
                Toast.makeText(AracListele.this,"Aracınız başarıyla silindi!",Toast.LENGTH_LONG).show();
                startActivity(new Intent(AracListele.this,MainActivity.class));
            }
        });


        kaskoTarihiDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                textKaskoTarihi.setText(dateFormat.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        sigortaTarihiDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                textSigortaTarihi.setText(dateFormat.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        muayeneTarihiDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                textMuayeneTarihi.setText(dateFormat.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));


        emisyonTarihiDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                textEmisyonTarihi.setText(dateFormat.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));


    }

    private void disableEditText(EditText editText) {
        editText.setFocusable(false);
        editText.setEnabled(false);
        editText.setCursorVisible(false);
        editText.setKeyListener(null);
        editText.setTextColor(Color.BLACK);
        editText.setBackgroundColor(Color.TRANSPARENT);
        editText.setClickable(false);
    }

    private void enableTarihYerleri(EditText editText)
    {
        editText.setFocusable(true);
        editText.setEnabled(true);
        editText.setCursorVisible(true);
        editText.setKeyListener(new EditText(getApplicationContext()).getKeyListener());
        editText.setClickable(true);
        editText.setFocusableInTouchMode(true);
        editText.setInputType(0);
        editText.setBackgroundColor(Color.WHITE);
        editText.setOnClickListener(this);

    }

    private void enableEditText(EditText editText)
    {
        editText.setFocusable(true);
        editText.setEnabled(true);
        editText.setCursorVisible(true);
        editText.setKeyListener(new EditText(getApplicationContext()).getKeyListener());
        editText.setClickable(true);
        editText.setBackgroundColor(Color.WHITE);
        editText.setFocusableInTouchMode(true);
    }

    @Override
    public void onClick(View view)
    {
        if(view == textKaskoTarihi)
        {
            kaskoTarihiDialog.show();
        }
        else if(view == textEmisyonTarihi)
        {
            emisyonTarihiDialog.show();
        }

        else if(view == textMuayeneTarihi)
        {
            muayeneTarihiDialog.show();
        }

        else if(view == textSigortaTarihi)
        {
            sigortaTarihiDialog.show();
        }
    }



}