package com.example.healthbuddy;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface InfermedicaApi {
    @POST("diagnosis")
    default // Specify the endpoint for diagnosis
    Call<DiagnosisResponse> analyzeSymptoms(@Body SymptomsRequest request) // Method to analyze symptoms
    {
        return null;
    }
}
