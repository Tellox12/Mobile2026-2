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

import com.example.clase7.MiAdaptador;
import com.example.clase7.R;

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

        Cliente cliente = new Cliente();

        AsyncTask.execute(() ->{
            ArrayList<String> misDatos = cliente.getElements();
            runOnUiThread(() -> {
                adaptador = new MiAdaptador(misDatos);
                recyclerView.setAdapter(adaptador);
            });

        });


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adaptador);

        btnAgregar.setOnClickListener(v ->{
            String nuevoNombre = edtInput.getText().toString();

            if (!nuevoNombre.isEmpty()){
                adaptador.agregarNombre(nuevoNombre);
                edtInput.setText("");

                recyclerView.scrollToPosition(adaptador.getItemCount()-1);
            }
        });
    }
}