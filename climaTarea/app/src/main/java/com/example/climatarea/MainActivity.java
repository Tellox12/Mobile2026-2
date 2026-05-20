package com.example.climatarea;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private final String API_KEY = "187907041a8a0524e402d86012460e1d";
    private RecyclerView recyclerView;
    private WeatherAdapter adapter;
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rvWeather);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        verificarPermisosYObtenerClima();
    }

    private void verificarPermisosYObtenerClima() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);
        } else {
            obtenerUbicacionActual();
        }
    }

    private void obtenerUbicacionActual() {
        try {
            fusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
                if (location != null) {
                    obtenerClimaDeAPI(location.getLatitude(), location.getLongitude());
                } else {
                    Log.e("Clima", "No se pudo obtener la ubicación");
                    guardarEnHistórico(0.0, "Ubicación no encontrada");
                }
            });
        } catch (SecurityException e) {
            Log.e("Clima", "Error de seguridad: " + e.getMessage());
        }
    }

    private void obtenerClimaDeAPI(double lat, double lon) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WeatherApiService service = retrofit.create(WeatherApiService.class);

        service.getCurrentWeather(lat, lon, API_KEY, "metric").enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    double temp = response.body().main.temp;
                    String desc = response.body().weather.get(0).description;
                    guardarEnHistórico(temp, desc);
                } else {
                    Log.e("Clima", "Error API: " + response.code());
                    guardarEnHistórico(0.0, "Error de API (Llave inactiva)");
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                Log.e("Clima", "Fallo de red: " + t.getMessage());
                guardarEnHistórico(0.0, "Sin conexión");
            }
        });
    }

    private void mostrarHistorial() {
        AsyncTask.execute(() -> {
            AppDatabase db = AppDatabase.getInstance(getApplicationContext());
            java.util.List<WeatherEntity> lista = db.weatherDao().getAllHistory();
            runOnUiThread(() -> {
                adapter = new WeatherAdapter(lista);
                recyclerView.setAdapter(adapter);
            });
        });
    }

    private void guardarEnHistórico(double temp, String desc) {
        AsyncTask.execute(() -> {
            AppDatabase db = AppDatabase.getInstance(getApplicationContext());
            Date ahora = new Date();
            SimpleDateFormat fFecha = new SimpleDateFormat("yyyy-MM-dd", new Locale("es", "MX"));
            SimpleDateFormat fDia = new SimpleDateFormat("EEEE", new Locale("es", "MX"));

            String dia = fDia.format(ahora);
            dia = dia.substring(0, 1).toUpperCase() + dia.substring(1);

            db.weatherDao().insertWeather(new WeatherEntity(fFecha.format(ahora), dia, temp, desc));
            mostrarHistorial();
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            obtenerUbicacionActual();
        } else {
            Toast.makeText(this, "Se requiere el GPS para ver el clima de tu zona", Toast.LENGTH_LONG).show();
            mostrarHistorial();
        }
    }
}