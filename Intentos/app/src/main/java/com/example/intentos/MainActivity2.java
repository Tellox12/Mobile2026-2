package com.example.intentos;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity2 extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
        textView = findViewById(R.id.textView);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        String textoRecibido = getIntent().getStringExtra(Valores.miLlave);
        if (textoRecibido != null && !textoRecibido.isEmpty()){
            try {
                int n = Integer.parseInt(textoRecibido);
                long resultado = Fibonacci.obtenerFibonacci(n);

                textView.setText("Posición: " + n + "\nFibonacci: " + resultado);

            } catch (NumberFormatException e) {
                textView.setText("Error: El texto ingresado no es un número válido.");
            }
        } else {
            textView.setText("Error: El texto ingresado está vacío o es nulo.");
        }
    }
} // <-- Llave de cierre de la clase agregada