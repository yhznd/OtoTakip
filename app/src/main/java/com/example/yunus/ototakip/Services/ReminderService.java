package com.example.yunus.ototakip.Services;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

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

    private int notificationId;
    private List<String> bildirimler;

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
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    Calendar calendar = Calendar.getInstance();
                    String nowDate = simpleDateFormat.format(calendar.getTime());

                    if(nowDate.compareTo(araba.getEditTextKaskoTarihi()) == 0)
                    {
                        bildirimler.add(araba.getEditTextPlaka() + " plakalı aracınızın " + araba.getEditTextKaskoTarihi() + " tarihindeki kaskonuz bugün sona erdi.");
                    }
                    if(nowDate.compareTo(araba.getEditTextEmisyonTarihi()) == 0)
                    {
                        bildirimler.add(araba.getEditTextPlaka() + " plakalı aracınızın " + araba.getEditTextEmisyonTarihi() + " tarihindeki emisyon değişim tarihi bugün!");
                    }
                    if(nowDate.compareTo(araba.getEditTextMuayeneTarihi()) == 0)
                    {
                        bildirimler.add(araba.getEditTextPlaka() + " plakalı aracınızın " + araba.getEditTextMuayeneTarihi() + " tarihindeki muayene zamanı bugün!");
                    }
                    if(nowDate.compareTo(araba.getEditTextSigortaTarihi()) == 0)
                    {
                        bildirimler.add(araba.getEditTextPlaka() + " plakalı aracınızın " + araba.getEditTextSigortaTarihi() + " tarihindeki sigorta zamanı bugün sona eriyor!");
                    }

                    bildirimGuncelle();
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
                .setContentText("Yaklaşan önemli tarihleriniz var.");

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            mBuilder.setSmallIcon(R.drawable.ototakip_launcher_transparent);
        }
        else
        {
            mBuilder.setSmallIcon(R.drawable.otosplash);
        }
        NotificationCompat.InboxStyle inboxStyle =
                new NotificationCompat.InboxStyle();

        inboxStyle.setBigContentTitle("Tarih detayları:");
        for (String bildirim : bildirimler)
        {
            inboxStyle.addLine(bildirim);
        }
        mBuilder.setStyle(inboxStyle);

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

}
