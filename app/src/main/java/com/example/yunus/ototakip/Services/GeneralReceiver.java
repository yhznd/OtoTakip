package com.example.yunus.ototakip.Services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.yunus.ototakip.AyarlarActivity;

import java.util.Calendar;

/**
 * Created by Eren Özhan on 28.4.2017.
 */

public class GeneralReceiver extends BroadcastReceiver {


    public int gelenSaat,gelenDakika;

    @Override
    public void onReceive(Context context, Intent ıntent) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent serviceIntent = new Intent(context, ReminderService.class);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, serviceIntent, 0);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        SharedPreferences settings = context.getSharedPreferences("SaatTercih", Context.MODE_PRIVATE);
        gelenSaat = settings.getInt("saat", 15); //default olarak 10:00'a
        gelenDakika=settings.getInt("dakika",0);
        calendar.set(Calendar.HOUR_OF_DAY, gelenSaat);
        calendar.set(Calendar.MINUTE, gelenDakika);
        calendar.set(Calendar.SECOND,0);
        Log.d("Gelen",String.valueOf(gelenSaat));
        Log.d("Gelen",String.valueOf(gelenDakika));
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pendingIntent);

    }

}
