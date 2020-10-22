package ch.ost.group20.speedcamerareminder.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import ch.ost.group20.speedcamerareminder.MainActivity;
import ch.ost.group20.speedcamerareminder.R;

public class NotificationService extends Service {

    // service to create notification channel and show notification

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        NotificationChannel channel = new NotificationChannel(getResources().getString(R.string.notification_channel),
                getResources().getString(R.string.notification_channel_description),
                NotificationManager.IMPORTANCE_DEFAULT);

        ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(channel);

        Intent mainActivityIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, mainActivityIntent, 0);

        Notification notification = new NotificationCompat.Builder(this, getResources().getString(R.string.notification_channel))
                .setSmallIcon(R.drawable.ic_speed)
                .setContentTitle(getResources().getString(R.string.notification_title))
                .setContentText(getResources().getString(R.string.notification_text))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setOngoing(false)
                .setAutoCancel(true)
                .build();

        NotificationManagerCompat notificationManger = NotificationManagerCompat.from(this);
        notificationManger.notify(1, notification);

        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}