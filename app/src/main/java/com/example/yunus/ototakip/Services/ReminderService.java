package com.example.yunus.ototakip.Services;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.yunus.ototakip.Araba;
import com.example.yunus.ototakip.AyarlarActivity;
import com.example.yunus.ototakip.MainActivity;
import com.example.yunus.ototakip.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Eren Özhan on 28.4.2017.
 */

public class ReminderService extends IntentService{

    private String userId;
    private DatabaseReference arabalarRef;
    private long gunFarki;
    private int notificationId;
    private List<String> bildirimler;
    private long emisyonFark,sigortaFark,kaskoFark,muayeneFark;
    public  int indis;
    public boolean aktiflik;

    public ReminderService() {
        super("ReminderService");
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();

        if(user != null){
            userId = user.getUid();
            arabalarRef = dbRef.child("Arabalar").child(userId);
        }
        bildirimler = new ArrayList<>();
    }

    @Override
    protected void onHandleIntent(@Nullable Intent ıntent) {

        if(arabalarRef != null)
        {

            arabalarRef.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Araba araba = dataSnapshot.getValue(Araba.class);
                    araba.setEditTextPlaka(dataSnapshot.getKey());
                    emisyonFark = gunFarkiniGetir(araba.getEditTextEmisyonTarihi());
                    sigortaFark = gunFarkiniGetir(araba.getEditTextSigortaTarihi());
                    muayeneFark = gunFarkiniGetir(araba.getEditTextMuayeneTarihi());
                    kaskoFark = gunFarkiniGetir(araba.getEditTextKaskoTarihi());
                    SharedPreferences settings = getSharedPreferences("Tercih", Context.MODE_PRIVATE);
                    indis = settings.getInt("gelenIndis", 0);
                    aktiflik = settings.getBoolean("aktiflik", true);
                    Log.v("indis", String.valueOf(indis));
                    Log.v("tercih", String.valueOf(aktiflik));
                    if (aktiflik) //eğer hatırlatma açıksa açık=0, kapalı=1
                    {
                        if (indis == 0) //gününde seçilmişse
                        {
                            if (emisyonFark == 0)
                                bildirimler.add(araba.getEditTextPlaka() + " plakalı aracınızın " + araba.getEditTextEmisyonTarihi() + " tarihindeki emisyon değişim tarihi bugün!");
                            if (sigortaFark == 0)
                                bildirimler.add(araba.getEditTextPlaka() + " plakalı aracınızın " + araba.getEditTextSigortaTarihi() + " tarihindeki sigorta zamanı bugün sona eriyor!");
                            if (muayeneFark == 0)
                                bildirimler.add(araba.getEditTextPlaka() + " plakalı aracınızın " + araba.getEditTextMuayeneTarihi() + " tarihindeki muayene zamanı bugün!");
                            if (kaskoFark == 0)
                                bildirimler.add(araba.getEditTextPlaka() + " plakalı aracınızın " + araba.getEditTextKaskoTarihi() + " tarihindeki kaskonuz bugün sona erdi.");
                        }

                        if (indis == 1) //1 gün önce seçilmişse
                        {
                            if (emisyonFark == 1)
                                bildirimler.add(araba.getEditTextPlaka() + " plakalı aracınızın " + araba.getEditTextEmisyonTarihi() + " tarihindeki emisyon değişim tarihi yarın!");
                            if (sigortaFark == 1)
                                bildirimler.add(araba.getEditTextPlaka() + " plakalı aracınızın " + araba.getEditTextSigortaTarihi() + " tarihindeki sigorta zamanı yarın sona eriyor!");
                            if (muayeneFark == 1)
                                bildirimler.add(araba.getEditTextPlaka() + " plakalı aracınızın " + araba.getEditTextMuayeneTarihi() + " tarihindeki muayene zamanı yarın!");
                            if (kaskoFark == 1)
                                bildirimler.add(araba.getEditTextPlaka() + " plakalı aracınızın " + araba.getEditTextKaskoTarihi() + " tarihindeki kaskonuz yarın sona erecek.");
                        }

                        if (indis == 2) //1 hafta önce seçilmişse
                        {
                            if (emisyonFark == 7)
                                bildirimler.add(araba.getEditTextPlaka() + " plakalı aracınızın " + araba.getEditTextEmisyonTarihi() + " tarihindeki emisyon değişim tarihi 7 gün sonra!");
                            if (sigortaFark == 7)
                                bildirimler.add(araba.getEditTextPlaka() + " plakalı aracınızın " + araba.getEditTextSigortaTarihi() + " tarihindeki sigorta zamanı 7 gün sonra sona eriyor!");
                            if (muayeneFark == 7)
                                bildirimler.add(araba.getEditTextPlaka() + " plakalı aracınızın  " + araba.getEditTextMuayeneTarihi() + " tarihindeki muayene zamanı 7 gün sonra!");
                            if (kaskoFark == 7)
                                bildirimler.add(araba.getEditTextPlaka() + " plakalı aracınızın " + araba.getEditTextKaskoTarihi() + " tarihindeki kaskonuz 7 gün sonra sona erecek.");
                        }

                        bildirimGuncelle();
                    }
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

    }

    private void bildirimGuncelle(){
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setContentTitle("Oto Takip")
                .setDefaults(Notification.DEFAULT_ALL)
                .setContentText("Yaklaşan önemli tarihleriniz var.");

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            mBuilder.setSmallIcon(R.drawable.ototakip_launcher_transparent);
        }
        else
        {
            mBuilder.setSmallIcon(R.drawable.otosplash);
        }
        NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
        bigTextStyle.setBigContentTitle("Tarih detayları:");
        for (String bildirim : bildirimler)
        {
            bigTextStyle.bigText(bildirim);
        }
        mBuilder.setStyle(bigTextStyle);

        Intent resultIntent = new Intent(ReminderService.this, MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(ReminderService.this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(notificationId, mBuilder.build());
    }

    public long gunFarkiniGetir(String gelenTarih)
    {
        Calendar takvim = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        try
        {

            Date date1 = sdf.parse(gelenTarih);
            Date date2 = sdf.parse(sdf.format(takvim.getTime()));
            if(date1.after(date2))
                gunFarki = (date1.getTime() - date2.getTime())/86400000;
            else
                gunFarki=0;
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        Log.d("geldi", String.valueOf(gunFarki));
        return gunFarki;
    }

}
