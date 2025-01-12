package com.example.healthbuddy;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

public class HealthTipsActivity extends AppCompatActivity {
    private ListView listViewHealthTips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_tips);

        listViewHealthTips = findViewById(R.id.listViewHealthTips);

        // Sample health tips data
        String[] healthTips = {
                "Drink plenty of water to stay hydrated.",
                "Eat a balanced diet rich in fruits and vegetables.",
                "Exercise regularly to maintain a healthy weight.",
                "Get enough sleep each night for better health.",
                "Practice good hygiene to prevent illness.",
                "Manage stress through relaxation techniques.",
                "Avoid smoking and excessive alcohol consumption."
        };

        // Set up the adapter for the ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, healthTips);
        listViewHealthTips.setAdapter(adapter);
    }
}
