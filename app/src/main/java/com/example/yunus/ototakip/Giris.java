package com.example.yunus.ototakip;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.taishi.flipprogressdialog.FlipProgressDialog;

import java.util.ArrayList;
import java.util.List;

import mehdi.sakout.fancybuttons.FancyButton;

public class Giris extends AppCompatActivity implements View.OnClickListener {

    private FancyButton buttonSignIn;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignup,textViewSifreUnuttum;
    public boolean cancel=false;
    public boolean isFirstStart;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FlipProgressDialog progressDialog;
    List<Integer> imageList = new ArrayList<Integer>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giris);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                //  Initialize SharedPreferences
                SharedPreferences getPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                //  Create a new boolean and preference and set it to true
                isFirstStart = getPrefs.getBoolean("firstStart", true);
                //  If the activity has never started before...
                if (isFirstStart)
                {
                    //  Launch app intro
                    startActivity(new Intent(Giris.this, MyIntro.class));
                    //  Make a new preferences editor
                    SharedPreferences.Editor e = getPrefs.edit();
                    //  Edit preference to make it false because we don't want this to run again
                    e.putBoolean("firstStart", false);
                    //  Apply changes
                    e.apply();
                }
            }
        });
        // Start the thread
        t.start();


        firebaseAuth=FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // Kullanıcı oturumu açtı
                    finish();
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    Log.d("onCreate","onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // Kullanıcı oturumu kapattı.
                    Log.d("onCreate", "onAuthStateChanged:signed_out");
                }

            }
        };

        editTextEmail=(EditText)findViewById(R.id.editTextEmail);
        editTextPassword=(EditText)findViewById(R.id.editTextPassword);
        buttonSignIn=(FancyButton)findViewById(R.id.buttonSignin);
        textViewSignup=(TextView)findViewById(R.id.textViewSignUp);
        textViewSifreUnuttum= (TextView) findViewById(R.id.textViewSifreUnuttum);
        buttonSignIn.setOnClickListener(this);
        textViewSignup.setOnClickListener(this);
        progressDialog = new FlipProgressDialog();
        imageList.add(R.drawable.hourglass);
        progressDialog.setImageList(imageList);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setBackgroundAlpha(1.0f);
        progressDialog.setImageSize(100);
        progressDialog.setBackgroundColor(getResources().getColor(R.color.icon_arkasi));
        progressDialog.setCornerRadius(32);
        progressDialog.setDuration(500);
        progressDialog.setStartAngle(0.0f);                                  // Set an angle when flipping ratation start
        progressDialog.setDimAmount(0.6f);
        progressDialog.setOrientation("rotationX");

    }

    private void userLogin() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        editTextEmail.setError(null);
        editTextPassword.setError(null);
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

        if (internetErisimi()) {
            if (cancel == false) {

                progressDialog.show(getFragmentManager(),"");

                firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {


                                if (task.isSuccessful()) {
                                    //start the profile activity
                                    finish();
                                    startActivity(new Intent(Giris.this, MainActivity.class));
                                }

                                else
                                {
                                    Toast.makeText(Giris.this,"Girdiğin e-posta ve şifre kayıtlarımızdakiyle eşleşmedi. Lütfen doğru girdiğinden emin ol ve tekrar dene.",
                                    Toast.LENGTH_LONG).show();
                                }

                                progressDialog.dismiss();
                            }
                        });


            }
        }

        else
        {
            Intent hata = new Intent(Giris.this, InternetCon.class);
            startActivity(hata);
        }
        cancel = false;

    }
/*
    //buraya bakmamız gerek
    @Override
    public void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            firebaseAuth.removeAuthStateListener(mAuthListener);
        }
    }
*/

    @Override
    public void onClick(View view) {
            if (view == buttonSignIn)
            {
                userLogin();
            }

            if (view == textViewSignup)
            {
                finish();
                startActivity(new Intent(this, KayitOl.class));
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
