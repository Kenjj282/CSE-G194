package com.example.healthbuddy;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class NearbyHospitalsActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private FusedLocationProviderClient fusedLocationClient;
    private GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_hospitals);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap map) {
        this.googleMap = map;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
            return;
        }

        googleMap.setMyLocationEnabled(true);
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, location -> {
                    if (location != null) {
                        LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 15));
                        fetchNearbyHospitals(location);
                    }
                });
    }

    private void fetchNearbyHospitals(Location location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();

        // Replace YOUR_API_KEY with your actual Google Places API key
        String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" +
                latitude + "," + longitude + "&radius=5000&type=hospital&key=AIzaSyBdfDx7ZrBo3P0Jnaie3hcipFejikh6hQE";

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        JSONArray results = response.getJSONArray("results");
                        for (int i = 0; i < results.length(); i++) {
                            JSONObject hospital = results.getJSONObject(i);
                            double lat = hospital.getJSONObject("geometry").getJSONObject("location").getDouble("lat");
                            double lon = hospital.getJSONObject("geometry").getJSONObject("location").getDouble("lng");
                            String name = hospital.getString("name");

                            LatLng hospitalLocation = new LatLng(lat, lon);
                            googleMap.addMarker(new MarkerOptions().position(hospitalLocation).title(name));
                        }
                    } catch (JSONException e) {
                        Log.e("NearbyHospitals", "JSON parsing error: " + e.getMessage());
                    }
                },
                error -> Log.e("NearbyHospitals", "Error fetching hospitals: " + error.getMessage())
        );

        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                onMapReady(googleMap); // Retry after permission is granted
            } else {
                Toast.makeText(this, "Location permission is required to show nearby hospitals", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
