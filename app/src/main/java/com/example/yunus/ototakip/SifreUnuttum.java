package com.example.yunus.ototakip;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import mehdi.sakout.fancybuttons.FancyButton;

public class SifreUnuttum extends AppCompatActivity {

    public EditText sifirlamaMail;
    public TextView geriDonus;
    public FancyButton sifirlaButton;
    public CoordinatorLayout bilgiLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sifre_unuttum);
        bilgiLayout = (CoordinatorLayout) findViewById(R.id.sifreUnuttumCoordinatorLayout);
        geriDonus= (TextView) findViewById(R.id.textGeriDon);
        sifirlamaMail= (EditText) findViewById(R.id.editTextSifirlamaEmail);
        sifirlaButton= (FancyButton) findViewById(R.id.buttonSifirla);


        sifirlaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                FirebaseAuth auth = FirebaseAuth.getInstance();
                String emailAddress = sifirlamaMail.getText().toString();
                auth.sendPasswordResetEmail(emailAddress)
                        .addOnCompleteListener(new OnCompleteListener<Void>()
                        {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful())
                                {
                                    Log.d("SifreUnuttum", "Sıfırlama e-postası yollandı.");
                                    Snackbar.make(bilgiLayout, "Sıfırlama linki e-posta hesabınıza gönderildi!",
                                            Snackbar.LENGTH_LONG).show();
                                    startActivity(new Intent(SifreUnuttum.this,Giris.class));
                                }
                            }
                        });
            }
        });



        geriDonus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(SifreUnuttum.this,Giris.class));
            }
        });
    }
}
