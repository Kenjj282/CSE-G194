package com.example.healthbuddy;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class MedicineReminderActivity extends AppCompatActivity {
    private TimePicker timePicker;
    private EditText editTextMedicineName;
    private Button buttonSetReminder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_reminder);

        timePicker = findViewById(R.id.timePicker);
        editTextMedicineName = findViewById(R.id.editTextMedicineName);
        buttonSetReminder = findViewById(R.id.buttonSetReminder);

        buttonSetReminder.setOnClickListener(v -> setMedicineReminder());
    }

    @SuppressLint("ScheduleExactAlarm")
    private void setMedicineReminder() {
        String medicineName = editTextMedicineName.getText().toString();
        if (medicineName.isEmpty()) {
            Toast.makeText(this, "Please enter the medicine name", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get the time from the TimePicker
        int hour = timePicker.getHour();
        int minute = timePicker.getMinute();

        // Set the alarm for the specified time
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        // If the alarm time is in the past, add a day
        if (calendar.getTimeInMillis() < System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        // Create an Intent to trigger the AlarmReceiver
        Intent intent = new Intent(this, AlarmReceiver.class);
        intent.putExtra("medicine_name", medicineName);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        // Set the alarm
        if (alarmManager != null) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            Toast.makeText(this, "Reminder set for " + medicineName + " at " + hour + ":" + String.format("%02d", minute), Toast.LENGTH_SHORT).show();
            finish(); // Close the activity after setting the reminder
        }
    }
}
