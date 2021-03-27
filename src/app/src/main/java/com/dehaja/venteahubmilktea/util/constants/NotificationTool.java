package com.dehaja.venteahubmilktea.util.constants;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotificationTool extends AppCompatActivity {
    private final short  CHANNEL_ID = 123;
    private AppCompatActivity activity;
    private String channelId;
    private String title;
    private String message;

    public NotificationTool(AppCompatActivity activity, String channelId, String title, String message) {
        this.activity = activity;
        this.channelId = channelId;
        this.title = title;
        this.message = message;

        showNotification();
    }

    private void showNotification() {
        createNotificationChannel();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this.activity, this.channelId);
        builder.setContentTitle(this.title);
        builder.setContentText(this.message);
        builder.setSmallIcon(android.R.drawable.star_on);
        NotificationManagerCompat manager = NotificationManagerCompat.from(this.activity);
        manager.notify(CHANNEL_ID, builder.build());
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(Short.toString(CHANNEL_ID), this.channelId,
                    NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }
}
