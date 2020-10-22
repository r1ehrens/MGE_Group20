package ch.ost.group20.speedcamerareminder.notification;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Set;

import ch.ost.group20.speedcamerareminder.R;

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getResources().getString(R.string.preferences_default_file), Context.MODE_PRIVATE);

        if (sharedPreferences.getBoolean(context.getResources().getString(R.string.notification_enabled_key), false)) {

            String timePreference = sharedPreferences.getString(context.getResources().getString(R.string.notification_frequency_time_key), "07:00");

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timePreference.substring(0, 2)));
            calendar.set(Calendar.MINUTE, Integer.parseInt(timePreference.substring(3, 5)));
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);

            if (System.currentTimeMillis() + 1000 * 5 > calendar.getTimeInMillis()) {
                calendar.add(Calendar.DATE, 1);
            }

            Calendar date = Calendar.getInstance();
            String currentWeekday = new SimpleDateFormat("EEEE", Locale.GERMAN).format(date.getTime());

            if (!intent.getBooleanExtra(context.getResources().getString(R.string.notification_intent_is_config), false)) {
                Set<String> daySet = sharedPreferences.getStringSet(context.getResources().getString(R.string.notification_frequency_day_key), null);
                if (daySet.stream().anyMatch(e -> e.equals(currentWeekday))) {
                    startAlarmService(context, intent);
                }
            }

            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            intent.putExtra(context.getResources().getString(R.string.notification_intent_is_config), false);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }
    }

    private void startAlarmService(Context context, Intent intent) {

        Log.e("RECEIVER", "START SERVICE JETZT");
        Intent intentService = new Intent(context, NotificationService.class);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intentService);

        } else {
            context.startService(intentService);
        }
    }
}
