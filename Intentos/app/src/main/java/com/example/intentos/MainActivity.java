package com.example.intentos;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Button btnIntento;
    EditText edtInput;
    String miTexto = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnIntento = findViewById(R.id.btnIntento);
        edtInput = findViewById(R.id.edtInput);

        btnIntento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Validación rápida para no enviar datos vacíos
                if (!miTexto.isEmpty()) {
                    Bundle bundle = new Bundle();
                    bundle.putString(Valores.miLlave, miTexto);
                    Intent i = new Intent(MainActivity.this, MainActivity2.class);
                    i.putExtras(bundle);
                    startActivity(i); // <-- Corregido aquí
                } else {
                    edtInput.setError("Ingresa un número primero");
                }
            }
        });

        edtInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                miTexto = String.valueOf(s);
                Log.d("Neri", miTexto);
            }
        });
    }
} // <-- Llave de cierre de la clase agregada}