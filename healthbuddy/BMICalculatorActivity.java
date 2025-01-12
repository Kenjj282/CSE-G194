package com.example.healthbuddy;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class BMICalculatorActivity extends AppCompatActivity {
    private EditText editTextHeight, editTextWeight;
    private Button buttonCalculate;
    private TextView textViewResult, textViewDiagnosis;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmicalculator);

        editTextHeight = findViewById(R.id.editTextHeight);
        editTextWeight = findViewById(R.id.editTextWeight);
        buttonCalculate = findViewById(R.id.buttonCalculate);
        textViewResult = findViewById(R.id.textViewResult);
        textViewDiagnosis = findViewById(R.id.textViewDiagnosis);

        buttonCalculate.setOnClickListener(v -> calculateBMI());
    }

    private void calculateBMI() {
        String heightStr = editTextHeight.getText().toString();
        String weightStr = editTextWeight.getText().toString();

        if (heightStr.isEmpty() || weightStr.isEmpty()) {
            Toast.makeText(this, "Please enter both height and weight", Toast.LENGTH_SHORT).show();
            return;
        }

        double height = Double.parseDouble(heightStr) / 100; // Convert height from cm to meters
        double weight = Double.parseDouble(weightStr);

        double bmi = weight / (height * height); // BMI formula
        textViewResult.setText(String.format("Your BMI is: %.2f", bmi));

        // Call method to get diagnosis and prevention
        provideDiagnosisAndPrevention(bmi);
    }

    private void provideDiagnosisAndPrevention(double bmi) {
        String diagnosis;
        String prevention;

        if (bmi < 18.5) {
            diagnosis = "Underweight";
            prevention = "Consider a balanced diet with more calories.";
        } else if (bmi >= 18.5 && bmi < 24.9) {
            diagnosis = "Normal weight";
            prevention = "Maintain a balanced diet and regular exercise.";
        } else if (bmi >= 25 && bmi < 29.9) {
            diagnosis = "Overweight";
            prevention = "Adopt a healthier diet and increase physical activity.";
        } else {
            diagnosis = "Obesity";
            prevention = "Consult a healthcare provider for personalized advice.";
        }

        textViewDiagnosis.setText("Diagnosis: " + diagnosis + "\nPrevention: " + prevention);
    }
}
