package com.example.yunus.ototakip;

import android.app.AlertDialog;
import android.app.Dialog;
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

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
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
    private TextView textViewSignup, textViewSifreUnuttum, girismesaji;
    public boolean cancel = false;
    public boolean isFirstStart;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private Dialog progressDialog;
    private CallbackManager mCallbackManager;
    private static final String TAG = "FacebookLogin";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_giris);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                //  Initialize SharedPreferences
                SharedPreferences getPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                //  Create a new boolean and preference and set it to true
                isFirstStart = getPrefs.getBoolean("firstStart", true);
                //  If the activity has never started before...
                if (isFirstStart) {
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


        firebaseAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // Kullanıcı oturumu açtı
                    finish();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    Log.d("onCreate", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // Kullanıcı oturumu kapattı.
                    Log.d("onCreate", "onAuthStateChanged:signed_out");
                }

            }
        };
        mCallbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);

            }
        });
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        buttonSignIn = (FancyButton) findViewById(R.id.buttonSignin);
        textViewSignup = (TextView) findViewById(R.id.textViewSignUp);
        textViewSifreUnuttum = (TextView) findViewById(R.id.textViewSifreUnuttum);
        buttonSignIn.setOnClickListener(this);
        textViewSignup.setOnClickListener(this);
        textViewSifreUnuttum.setOnClickListener(this);
        progressDialog = new Dialog(this, R.style.progress_dialog);
        progressDialog.setContentView(R.layout.dialog);
        progressDialog.setCancelable(true);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        girismesaji = (TextView) progressDialog.findViewById(R.id.id_tv_loadingmsg);
        girismesaji.setText("Giriş Yapılıyor...");


    }

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

                progressDialog.show();

                firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {


                                if (task.isSuccessful()) {
                                    //start the profile activity
                                    finish();
                                    Intent normalGiris=new Intent(Giris.this, MainActivity.class);
                                    normalGiris.putExtra("giris","normal");
                                    startActivity(normalGiris);
                                } else {
                                    Toast.makeText(Giris.this, "Girdiğin e-posta ve şifre kayıtlarımızdakiyle eşleşmedi. Lütfen doğru girdiğinden emin ol ve tekrar dene.",
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


    @Override
    public void onClick(View view) {
        if (view == buttonSignIn)
        {
            userLogin();
        }

        if (view == textViewSignup) {
            finish();
            startActivity(new Intent(this, KayitOl.class));
        }
        if (view ==  textViewSifreUnuttum)
        {
            finish();
            startActivity(new Intent(this, SifreUnuttum.class));
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

        ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        //net bağlantısı varsa, erişilebilir ve bağlı ise true gönder
        if (conMgr.getActiveNetworkInfo() != null && conMgr.getActiveNetworkInfo().isAvailable()
                && conMgr.getActiveNetworkInfo().isConnected()) {
            return true;
        } else {
            return false;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);
        if (internetErisimi())
        {

            AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
            progressDialog.show();
            firebaseAuth.signInWithCredential(credential)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());
                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            //
                            if(task.isSuccessful())
                            {
                                finish();
                                Intent fGiris=new Intent(Giris.this, MainActivity.class);
                                fGiris.putExtra("giris","facebook");
                                startActivity(fGiris);

                            }
                            else
                            {
                                Log.w(TAG, "signInWithCredential", task.getException());
                                Toast.makeText(Giris.this, "Facebook ile bağlantı başarız oldu.",
                                        Toast.LENGTH_SHORT).show();

                            }
                            progressDialog.dismiss();
                        }

                    });
        }
        else
        {
            Intent hata = new Intent(Giris.this, InternetCon.class);
            startActivity(hata);
        }

    }




}

