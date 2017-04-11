package com.example.yunus.ototakip;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import mehdi.sakout.fancybuttons.FancyButton;

public class Hesabim extends AppCompatActivity {

    public CircleImageView kullaniciHesapResmi;
    public FancyButton sifreDegistir,hesapSil,hesapGuncelle;
    public TextView kullanici,kullaniciMail;
    public FirebaseAuth firebaseAuth;
    public CircleImageView userImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hesabim);
        kullanici= (TextView) findViewById(R.id.kullaniciHesapAd);
        hesapSil= (FancyButton) findViewById(R.id.buttonHesapSil);
        kullaniciMail= (TextView) findViewById(R.id.kullaniciHesapMail);
        userImage= (CircleImageView) findViewById(R.id.kullaniciHesapResmi);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_hesabim);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Hesabim.this, MainActivity.class));
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()==null)
        {
            finish();
            startActivity(new Intent(this,Giris.class));
        }

        kullaniciMail.setText(firebaseAuth.getCurrentUser().getEmail());
        hesapSil.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                new MaterialStyledDialog.Builder(Hesabim.this)
                        .setTitle("Sizi özleyeceğiz!")
                        .setDescription("Hesabınız kalıcı olarak silinecek. Devam etmek istiyor musunuz?")
                        .setPositiveText("EVET, SİL")
                        .setNegativeText("HAYIR")
                        .setCancelable(false)
                        .setHeaderDrawable(R.drawable.banner)
                        .onPositive(new MaterialDialog.SingleButtonCallback()
                        {

                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which)
                            {
                                final FirebaseUser current_user=firebaseAuth.getCurrentUser();
                                //
                                current_user.delete().addOnCompleteListener(new OnCompleteListener<Void>()
                                        {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful())
                                                {
                                                    Log.d("Hesabim", "Kullanıcı hesabı silindi.");
                                                    Toast.makeText(Hesabim.this,"Hesabınız silindi!",Toast.LENGTH_SHORT).show();
                                                    startActivity(new Intent(Hesabim.this, Giris.class));
                                                    finish();
                                                }

                                                else
                                                {
                                                    Log.d("Hesabim", "Kullanıcı hesabı silinemedi.");
                                                    Toast.makeText(Hesabim.this,"Hesap silme işlemi başarısız oldu." +
                                                            "Lütfen daha sonra tekrar deneyin!",Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            }
                        })

                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which)
                            {
                                // TODO
                                dialog.dismiss();

                            }
                        })
                        .show();


            }
        });
        }



}
