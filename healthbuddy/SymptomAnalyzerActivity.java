package com.example.healthbuddy;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SymptomAnalyzerActivity extends AppCompatActivity {
    private EditText editTextSymptoms;
    private Button buttonAnalyze;
    private TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptom_analyzer);

        editTextSymptoms = findViewById(R.id.editTextSymptoms);
        buttonAnalyze = findViewById(R.id.buttonAnalyze);
        textViewResult = findViewById(R.id.textViewResult);

        buttonAnalyze.setOnClickListener(v -> analyzeSymptoms());
    }

    private void analyzeSymptoms() {
        String symptoms = editTextSymptoms.getText().toString();

        if (symptoms.isEmpty()) {
            Toast.makeText(this, "Please enter symptoms", Toast.LENGTH_SHORT).show();
            return;
        }

        // Call the API to analyze symptoms
        InfermedicaApi api = ApiClient.getClient().create(InfermedicaApi.class);
        Call<DiagnosisResponse> call = api.analyzeSymptoms(new SymptomsRequest(symptoms));

        call.enqueue(new Callback<DiagnosisResponse>() {

            @Override
            public void onResponse(@NonNull Call<DiagnosisResponse> call, @NonNull Response<DiagnosisResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    textViewResult.setText(response.body().getDiagnosis());
                } else {
                    try {
                        String errorDetails = response.errorBody() != null ? response.errorBody().string() : "Unknown error";
                        Toast.makeText(SymptomAnalyzerActivity.this, "Error analyzing symptoms: " + errorDetails, Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }


            @Override
            public void onFailure(Call<DiagnosisResponse> call, Throwable t) {
                // Log failure message
                Toast.makeText(SymptomAnalyzerActivity.this, "API call failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
