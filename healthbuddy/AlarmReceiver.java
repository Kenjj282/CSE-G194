package com.example.healthbuddy;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String medicineName = intent.getStringExtra("medicine_name");

        // Check and request notification permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context, "Notification permission not granted", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create the notification channel
        createNotificationChannel(context);

        // Create and show the notification
        createNotification(context, medicineName);
    }

    private void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "medicine_reminder_channel";
            CharSequence name = "Medicine Reminder";
            String description = "Channel for medicine reminders";
            int importance = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel channel = new NotificationChannel(channelId, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void createNotification(Context context, String medicineName) {
        String channelId = "medicine_reminder_channel";

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("Medicine Reminder")
                .setContentText("Time to take your medicine: " + medicineName)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        // Generate a unique ID for the notification
        int notificationId = (int) System.currentTimeMillis();

        // Ensure permission is checked before calling notify
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU ||
                ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            notificationManager.notify(notificationId, builder.build());
        } else {
            Toast.makeText(context, "Notification permission not granted", Toast.LENGTH_SHORT).show();
        }

        // Play notification sound
        playNotificationSound(context);
    }

    private void playNotificationSound(Context context) {
        try {
            Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone ringtone = RingtoneManager.getRingtone(context, alarmSound);
            ringtone.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
