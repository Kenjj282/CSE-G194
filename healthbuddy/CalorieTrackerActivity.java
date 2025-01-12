package com.example.healthbuddy;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

public class CalorieTrackerActivity extends AppCompatActivity {
    private EditText editTextFoodItem, editTextQuantity;  // EditText for food item and quantity
    private Button buttonAdd, buttonCalculate;
    private TextView textViewTotalCalories;

    private HashMap<String, Integer> foodCalories; // To store food items and their calorie values
    private int totalCalories = 0; // To keep track of total calories consumed

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calorie_tracker);

        editTextFoodItem = findViewById(R.id.editTextFoodItem);
        editTextQuantity = findViewById(R.id.editTextQuantity);  // Initialized quantity input field
        buttonAdd = findViewById(R.id.buttonAdd);
        buttonCalculate = findViewById(R.id.buttonCalculate);
        textViewTotalCalories = findViewById(R.id.textViewTotalCalories);

        // Initialize the food calories map
        initializeFoodCalories();

        buttonAdd.setOnClickListener(v -> addFoodItem());
        buttonCalculate.setOnClickListener(v -> calculateTotalCalories());
    }

    private void initializeFoodCalories() {
        foodCalories = new HashMap<>();
        foodCalories.put("apple", 95);
        foodCalories.put("banana", 105);
        foodCalories.put("orange", 62);
        foodCalories.put("bread", 80);
        foodCalories.put("chicken", 335);
        foodCalories.put("rice", 206);
        // Add more food items and their calorie values as needed
    }

    private void addFoodItem() {
        String foodItem = editTextFoodItem.getText().toString().toLowerCase();
        String quantityText = editTextQuantity.getText().toString();

        // Check if the food item and quantity are entered
        if (foodItem.isEmpty() || quantityText.isEmpty()) {
            Toast.makeText(this, "Please enter both food item and quantity", Toast.LENGTH_SHORT).show();
            return;
        }

        Integer calories = foodCalories.get(foodItem);
        if (calories != null) {
            try {
                int quantity = Integer.parseInt(quantityText); // Convert quantity to an integer
                if (quantity > 0) {
                    int totalFoodCalories = calories * quantity/100; // Multiply by quantity
                    totalCalories += totalFoodCalories;
                    Toast.makeText(this, "Added " + quantity + " " + foodItem + "(s) (" + totalFoodCalories + " calories)", Toast.LENGTH_SHORT).show();
                    editTextFoodItem.setText(""); // Clear food input field
                    editTextQuantity.setText(""); // Clear quantity input field
                } else {
                    Toast.makeText(this, "Quantity must be a positive number", Toast.LENGTH_SHORT).show();
                }
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Please enter a valid number for quantity", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Food item not recognized", Toast.LENGTH_SHORT).show();
        }
    }

    private void calculateTotalCalories() {
        textViewTotalCalories.setText("Total Calories: " + totalCalories);
    }
}
