package com.example.yunus.ototakip;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.taishi.flipprogressdialog.FlipProgressDialog;

import java.util.ArrayList;
import java.util.List;

import mehdi.sakout.fancybuttons.FancyButton;

public class KayitOl extends AppCompatActivity implements View.OnClickListener {

    private FancyButton buttonRegister;
    private EditText editTextEmail;
    public boolean cancel=false;
    private EditText editTextPassword,editTextPasswordRepeat;
    private TextView textViewSignin;
    private FirebaseAuth firebaseAuth;
    private FlipProgressDialog progressDialog;
    List<Integer> imageList2 = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayit_ol);
        firebaseAuth = FirebaseAuth.getInstance();
        buttonRegister = (FancyButton) findViewById(R.id.buttonRegister);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextPasswordRepeat= (EditText) findViewById(R.id.editTextPasswordRepeat);
        textViewSignin = (TextView) findViewById(R.id.textViewSignIn);
        buttonRegister.setOnClickListener(this);
        textViewSignin.setOnClickListener(this);
        progressDialog = new FlipProgressDialog();
        imageList2.add(R.drawable.hourglass);
        progressDialog.setImageList(imageList2);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setBackgroundAlpha(1.0f);
        progressDialog.setImageSize(80);
        progressDialog.setBackgroundColor(getResources().getColor(R.color.icon_arkasi));
        progressDialog.setCornerRadius(32);
        progressDialog.setDuration(600);
        progressDialog.setStartAngle(0.0f);                                  // Set an angle when flipping ratation start
        progressDialog.setDimAmount(0.6f);
        progressDialog.setOrientation("rotationX");

    }

    private void registerUser() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String passwordRepeat = editTextPasswordRepeat.getText().toString().trim();
        editTextEmail.setError(null);
        editTextPassword.setError(null);
        editTextPasswordRepeat.setError(null);
        View focusView = null;
        if (TextUtils.isEmpty(email)) {
            editTextEmail.setError(getString(R.string.mail_alan_gerekli));
            focusView = editTextEmail;
            cancel = true;
            return;
        } else if (!isEmailValid(email)) {
            editTextEmail.setError(getString(R.string.eposta_gecersiz));
            focusView = editTextEmail;
            cancel = true;
        }

        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError(getString(R.string.sifre_alan_gerekli));
            focusView = editTextPassword;
            cancel = true;
            return;
        } else if (!isPasswordValid(password)) {
            editTextPassword.setError(getString(R.string.sifre_gecersiz));
            focusView = editTextPassword;
            cancel = true;
        }

        if (TextUtils.isEmpty(passwordRepeat)) {
            editTextPasswordRepeat.setError(getString(R.string.sifre_alan_gerekli));
            focusView = editTextPasswordRepeat;
            cancel = true;
            return;
        } else if (!isPasswordValid(passwordRepeat)) {
            editTextPasswordRepeat.setError(getString(R.string.sifre_gecersiz));
            focusView = editTextPasswordRepeat;
            cancel = true;
        }
        if (!password.equals(passwordRepeat))  //şifre uyuşmazlık kontrolü
        {
            editTextPasswordRepeat.setError(getString(R.string.parola_uyusmuyor));
            focusView = editTextPasswordRepeat;
            cancel = true;
        }

        if (internetErisimi()) {
            if (cancel == false) {

                progressDialog.show(getFragmentManager(),"");
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                finish();
                                Intent i = new Intent(KayitOl.this, MainActivity.class);
                                i.putExtra("email",firebaseAuth.getCurrentUser().getEmail());
                                startActivity(i);

                            } else {
                                Toast.makeText(KayitOl.this, "Kayıt başarısız. Tekrar Deneyiniz.", Toast.LENGTH_LONG).show();

                            }
                            progressDialog.dismiss();

                        }
                    });
        }
    }

        else

        {
            startActivity(new Intent(KayitOl.this, InternetCon.class));

        }

        cancel = false;
    }


    @Override
    public void onClick(View view) {

        if (view == buttonRegister)
        {
            registerUser();

        }
        if (view == textViewSignin) {
            //will open login activity here
            startActivity(new Intent(KayitOl.this,Giris.class));

        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@") && email.contains(".com");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 5;
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