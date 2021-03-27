package com.dehaja.venteahubmilktea.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.dehaja.venteahubmilktea.R;
import com.dehaja.venteahubmilktea.util.constants.NotificationTool;

import static androidx.core.content.ContextCompat.getSystemService;

public class RegisterActivity extends AppCompatActivity {
    private final String CHANNEL_NAME = "VenteaHub";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        createNotificationChannel(CHANNEL_NAME);
    }

    public void submitOnClick(View view) {
        // Intent declaration
        Intent submitIntent = new Intent("android.intent.action.LOGIN");
        submitIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        // Activity Fields
        EditText txtEmail = findViewById(R.id.txtUsername);
        EditText txtUsername = findViewById(R.id.txtUsername);
        EditText txtPassword = findViewById(R.id.txtPassword);
        Switch swDriver = findViewById(R.id.swDriver);

        String fields = String.format("{email: %s, username: %s, passwod: %s, driver: %s}",
                txtEmail.getText(),
                txtUsername.getText(),
                txtPassword.getText(),
                swDriver.isChecked());
        showNotification(CHANNEL_NAME, "Ventea Hub Miltea", fields);
    }

    private void showNotification(String channel_name, String title, String message) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(RegisterActivity.this, channel_name);
        builder.setContentTitle(title);
        builder.setContentText(message);
        builder.setSmallIcon(android.R.drawable.star_on);
        builder.setAutoCancel(true);
        NotificationManagerCompat manager = NotificationManagerCompat.from(RegisterActivity.this);
        manager.notify(1, builder.build());
    }

    private void createNotificationChannel(String channel_id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channel_id, "main_notif",
                    NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }
}