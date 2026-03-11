package com.example.clasefca3;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SegundaActividad extends AppCompatActivity {

    TextView txvTitulo;
    Button btnIncrementa;
    int contador = 0;

    String MI_LLAVE = "actividad.segunda.miLlave";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segunda);

        txvTitulo = findViewById(R.id.txvMiTitulo);
        btnIncrementa = findViewById(R.id.btnIncrementa);

        // La recuperación de datos va aquí, directamente en onCreate
        if(savedInstanceState != null && savedInstanceState.containsKey(MI_LLAVE)){
            contador = savedInstanceState.getInt(MI_LLAVE);
            contadorTitulo();
        }

        btnIncrementa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // El clic solo suma y muestra
                contador += 1;
                contadorTitulo();
            }
        });

        Log.i("FCA", "onCreate() executed");
    }

    private void contadorTitulo(){
        txvTitulo.setText("Contador: " + contador);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("FCA", "onStart() executed");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("FCA", "onResume() executed");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("FCA", "onRestart() executed");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("FCA", "onPause() executed");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("FCA", "onStop() executed");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("FCA", "onDestroy() executed");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putInt(MI_LLAVE, contador);
    }
}