package com.example.yunus.ototakip.Services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.yunus.ototakip.AyarlarActivity;

import java.util.Calendar;

/**
 * Created by Eren Özhan on 28.4.2017.
 */

public class GeneralReceiver extends BroadcastReceiver {


    public int saat,dakika;
    @Override
    public void onReceive(Context context, Intent ıntent) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent serviceIntent = new Intent(context, ReminderService.class);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, serviceIntent, 0);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        SharedPreferences settings = context.getSharedPreferences("SaatTercih", Context.MODE_PRIVATE);
        saat = settings.getInt("saat", 10); //default olarak 10:00'a
        dakika=settings.getInt("dakika",00);
        calendar.set(Calendar.HOUR_OF_DAY, saat);
        calendar.set(Calendar.MINUTE, dakika);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pendingIntent);

    }

}
