package com.example.clase7;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    MiAdaptador adaptador;
    EditText edtInput;
    Button btnAgregar;

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

        edtInput = findViewById(R.id.edtInput);
        btnAgregar = findViewById(R.id.btnAgregar);
        recyclerView = findViewById(R.id.my_recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adaptador = new MiAdaptador(new ArrayList<>());
        recyclerView.setAdapter(adaptador);

        MiCliente miCliente = new MiCliente();

        AsyncTask.execute(() -> {
            try {
                ArrayList<Personaje> misDatos = miCliente.getElementos();
                runOnUiThread(() -> {
                    adaptador.actualizarDatos(misDatos);
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        /*

        btnAgregar.setOnClickListener(v -> {
            String nuevoNombre = edtInput.getText().toString();
            if (!nuevoNombre.isEmpty()){
                // Aquí creamos un personaje genérico para el ejemplo
                Personaje p = new Personaje(nuevoNombre, "Nuevo", "", 10, 10);
                adaptador.agregarPersonaje(p);
                edtInput.setText("");
                recyclerView.scrollToPosition(adaptador.getItemCount()-1);
            }
        });

         */
    }
}