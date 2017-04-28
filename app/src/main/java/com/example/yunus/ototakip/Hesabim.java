package com.example.yunus.ototakip;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
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


    public FancyButton hesapSil;
    public TextView kullanici,kullaniciMail;
    public FirebaseAuth firebaseAuth;
    public CircleImageView userImage;
    public String facebookUserId = "";
    public String photoURL;
    private String sifre = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hesabim);
        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()==null)
        {
            finish();
            startActivity(new Intent(this,Giris.class));
        }
        kullanici= (TextView) findViewById(R.id.kullaniciHesapAd);
        hesapSil= (FancyButton) findViewById(R.id.buttonHesapSil);
        kullaniciMail= (TextView) findViewById(R.id.kullaniciHesapMail);
        kullaniciMail.setText(firebaseAuth.getCurrentUser().getEmail()); //mail alıyoruz
        userImage= (CircleImageView) findViewById(R.id.kullaniciHesapResmi); //fotoğrafını alıyoruz
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        for(UserInfo profile : user.getProviderData())
        {
            // check if the provider id matches "facebook.com"
            if(profile.getProviderId().equals(getString(R.string.facebook_provider_id)))
            {
                facebookUserId = profile.getUid();
            }
        }

        photoURL = "https://graph.facebook.com/" + facebookUserId + "/picture?height=500";
        Picasso.with(this).load(photoURL).error(R.drawable.kullanici_pp).into(userImage);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_hesabim);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Hesabim.this, MainActivity.class));
            }
        });



        hesapSil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                    final MaterialDialog sifreIste =new  MaterialDialog.Builder(Hesabim.this)
                    .title("Sizi özleyeceğiz!")
                    .content("Hesabını silmek için şifreni tekrar doğrulamalısın:")
                    .inputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)
                    .input("Şifreni gir",sifre, new MaterialDialog.InputCallback() {

                        @Override
                        public void onInput(MaterialDialog dialog, CharSequence input) {
                            final StringBuilder sb = new StringBuilder(input.length());
                            sb.append(input); //stringi sb.toString ile alıyorum
                            if (sb.toString()!= null && !sb.toString().isEmpty())
                            {
                                final FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
                                AuthCredential credential = EmailAuthProvider.getCredential(current_user.getEmail(), sb.toString());
                                current_user.reauthenticate(credential)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Log.d("FirebaseUser", "Kullanici yeniden girdi");
                                            current_user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Log.d("Hesabim", "Kullanıcı hesabı silindi.");
                                                        Toast.makeText(Hesabim.this, "Hesabınız silindi!", Toast.LENGTH_SHORT).show();
                                                        startActivity(new Intent(Hesabim.this, Giris.class));
                                                        finish();
                                                    } else {
                                                        Log.d("Hesabim", "Kullanıcı hesabı silinemedi.");
                                                        Toast.makeText(Hesabim.this, "Yanlış şifre girdiniz." +
                                                                "Hesap silme işlemi gerçekleştirilemedi.", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                        }
                                    });
                            }
                            else
                            {
                                Toast.makeText(Hesabim.this, "Şifre alanı boş olamaz.", Toast.LENGTH_LONG).show();


                            }
                    }
                    }).show();

            }
        });

        }






}
