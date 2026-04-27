package com.example.appmapa;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationClient;

    // Variables para nuestra interfaz futurista
    private TextView tvCoordinates;
    private TextView tvLocationAddress;
    private TextView tvCurrentTime;
    private TextView tvCurrentDate;
    private FloatingActionButton fabLocate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Conectamos las variables con los NUEVOS IDs del XML
        tvCoordinates = findViewById(R.id.tvCoordinates);
        tvLocationAddress = findViewById(R.id.tvLocationAddress);
        tvCurrentTime = findViewById(R.id.tvCurrentTime);
        tvCurrentDate = findViewById(R.id.tvCurrentDate);
        fabLocate = findViewById(R.id.fabLocate);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        // Le damos vida al nuevo botón flotante
        fabLocate.setOnClickListener(v -> solicitarPermisosYLocalizar());

        actualizarReloj();
    }

    private void actualizarReloj() {
        Date ahora = new Date();
        SimpleDateFormat formatoHora = new SimpleDateFormat("hh:mm a z", new Locale("es", "MX"));
        SimpleDateFormat formatoFecha = new SimpleDateFormat("EEEE, d 'de' MMMM 'de' yyyy", new Locale("es", "MX"));

        tvCurrentTime.setText(formatoHora.format(ahora).toUpperCase());
        String fecha = formatoFecha.format(ahora);
        tvCurrentDate.setText(fecha.substring(0, 1).toUpperCase() + fecha.substring(1));
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        solicitarPermisosYLocalizar();
    }

    private void solicitarPermisosYLocalizar() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1000);
        } else {
            configurarMapa();
        }
    }

    private void configurarMapa() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);

            // Ocultamos el botón por defecto de Google porque hicimos el nuestro más bonito
            mMap.getUiSettings().setMyLocationButtonEnabled(false);

            fusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
                if (location != null) {
                    LatLng miUbicacion = new LatLng(location.getLatitude(), location.getLongitude());

                    mMap.clear(); // Limpiamos marcadores viejos
                    mMap.addMarker(new MarkerOptions().position(miUbicacion).title("Punto de sincronización"));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(miUbicacion, 16f));

                    // Actualizamos las coordenadas
                    tvCoordinates.setText(String.format(Locale.getDefault(), "Lat: %.4f | Lon: %.4f", location.getLatitude(), location.getLongitude()));

                    // Buscamos el nombre de la calle
                    obtenerDireccion(location.getLatitude(), location.getLongitude());

                    // Actualizamos la hora del panel
                    actualizarReloj();
                } else {
                    tvLocationAddress.setText("Buscando señal satelital...");
                }
            });
        }
    }

    // Esta función traduce las coordenadas a una dirección física (ej. Av. Reforma)
    private void obtenerDireccion(double lat, double lon) {
        Geocoder geocoder = new Geocoder(this, new Locale("es", "MX"));
        try {
            List<Address> direcciones = geocoder.getFromLocation(lat, lon, 1);
            if (direcciones != null && !direcciones.isEmpty()) {
                String direccionFisica = direcciones.get(0).getAddressLine(0);
                tvLocationAddress.setText(direccionFisica);
            } else {
                tvLocationAddress.setText("Sector desconocido");
            }
        } catch (IOException e) {
            tvLocationAddress.setText("Falla en decodificador de dirección");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1000 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            configurarMapa();
        } else {
            Toast.makeText(this, "Permiso de ubicación denegado", Toast.LENGTH_SHORT).show();
            tvLocationAddress.setText("Ubicación bloqueada por el usuario");
        }
    }
}