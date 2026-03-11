package com.example.clasefca3;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //1.
    TextView miTexto;

    TextView miGrupo;
    Button miButton;

    //3.
    String [][] nombres = {
            { "Cristian", "Pedro", "Pablo", "Mauricio" }, // 2291
            { "Kenia", "Paulina", "Giselle", "Alicia", "Claudia" } //2292
    };

    String [] grupos = { "2291", "2292" };

    int indice = 0;
    int indiceGrupo = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //2.
        miButton = findViewById(R.id.btnMiButton);
        miTexto = findViewById(R.id.txvMiTexto);
        miGrupo = findViewById(R.id.txvGrupo);

        miButton.setOnClickListener(this);

        if (nombres.length > 0) {
            miGrupo.setText(grupos[indiceGrupo]);
            miTexto.setText(nombres[indiceGrupo][indice]);
        }
    }

    private void renderizaAlumnos() {
        // 1. Primero avanzamos al siguiente alumno
        indice += 1;


        if (indice >= nombres[indiceGrupo].length) {
            indice = 0;       // Reiniciamos al primer alumno
            indiceGrupo += 1; // Aprovechamos para cambiar al siguiente grupo aquí mismo


            if (indiceGrupo >= grupos.length) {
                indiceGrupo = 0;
            }
        }


        miTexto.setText(nombres[indiceGrupo][indice]);
    }

    private void renderizaGrupo() {
        miGrupo.setText(grupos[indiceGrupo]);
    }

    @Override
    public void onClick(View v) {
        // Tu estructura original se queda exactamente igual
        renderizaAlumnos();
        renderizaGrupo();
    }
}