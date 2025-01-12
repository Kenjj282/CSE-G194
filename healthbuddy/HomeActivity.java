package com.example.healthbuddy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    private Button buttonSymptomAnalyzer, buttonBMI, buttonMedicineReminder, buttonHealthTips, buttonNearbyHospitals, buttonCalorieTracker, buttonLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Initialize buttons
        buttonSymptomAnalyzer = findViewById(R.id.buttonSymptomAnalyzer);
        buttonBMI = findViewById(R.id.buttonBMI);
        buttonMedicineReminder = findViewById(R.id.buttonMedicineReminder);
        buttonHealthTips = findViewById(R.id.buttonHealthTips);
        buttonNearbyHospitals = findViewById(R.id.buttonNearbyHospitals);
        buttonCalorieTracker = findViewById(R.id.buttonCalorieTracker); // Initialize Calorie Tracker button
        buttonLogout = findViewById(R.id.buttonLogout);

        // Set onClick listeners for buttons
        buttonSymptomAnalyzer.setOnClickListener(v -> navigateToSymptomAnalyzer());
        buttonBMI.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, BMICalculatorActivity.class);
            startActivity(intent);
        });
        buttonMedicineReminder.setOnClickListener(v -> navigateToMedicineReminder());
        buttonHealthTips.setOnClickListener(v -> navigateToHealthTips());
        buttonNearbyHospitals.setOnClickListener(v -> navigateToNearbyHospitals());

        // Set onClick listener for Calorie Tracker
        buttonCalorieTracker.setOnClickListener(v -> navigateToCalorieTracker());

        buttonLogout.setOnClickListener(v -> logoutUser());
    }

    private void navigateToSymptomAnalyzer() {
        Intent intent = new Intent(HomeActivity.this, SymptomAnalyzerActivity.class);
        startActivity(intent);
    }

    private void navigateToBMICalculator() {
        Intent intent = new Intent(HomeActivity.this, BMICalculatorActivity.class);
        startActivity(intent);
    }

    private void navigateToMedicineReminder() {
        Intent intent = new Intent(HomeActivity.this, MedicineReminderActivity.class);
        startActivity(intent);
    }

    private void navigateToHealthTips() {
        Intent intent = new Intent(HomeActivity.this, HealthTipsActivity.class);
        startActivity(intent);
    }

    private void navigateToNearbyHospitals() {
        Intent intent = new Intent(HomeActivity.this, NearbyHospitalsActivity.class);
        startActivity(intent);
    }

    private void navigateToCalorieTracker() {
        Intent intent = new Intent(HomeActivity.this, CalorieTrackerActivity.class); // Navigate to Calorie Tracker
        startActivity(intent);
    }

    private void logoutUser() {
        // Here you can add Firebase sign-out logic if needed
        Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show();

        // Navigate back to login activity
        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK); // Clear the back stack
        startActivity(intent);
        finish(); // Close this activity
    }
}
