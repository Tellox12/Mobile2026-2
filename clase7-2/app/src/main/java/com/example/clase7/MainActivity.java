package com.example.clase7;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    MiAdaptador adapter;
    ArrayList<Personaje> listaNombres = new ArrayList<>();;

    //EditText edtNombre;
    //Button btnAgregar;

    FloatingActionButton fabAgregar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Esto es para que arranque la aplicacion de java
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        //creamos el arreglo y le metemos de golpe los nombres
        //listaNombres = new ArrayList<>(Arrays.asList("Mario", "Luigi", "Peach", "Browser"));

        MiCliente miCliente = new MiCliente();

        //Conectamos los elementos del xml con java mapeo de cogigo java a xml
        recyclerView = findViewById(R.id.my_recycler_view);
        //edtNombre = findViewById(R.id.edtNombre);
        //btnAgregar = findViewById(R.id.btnAgregar);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        AsyncTask.execute(() ->{

            try {
                ArrayList<Personaje> datosDesdeServer = miCliente.getElements();
                runOnUiThread(() -> {

                    listaNombres.clear();
                    listaNombres.addAll(datosDesdeServer);

                    adapter = new MiAdaptador(listaNombres);
                    recyclerView.setAdapter(adapter);


                });

            }catch (Exception e){

                e.printStackTrace();
            }
        });


        //Creo adapter

        //le digo que es vertical
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //inicializo


        /*btnAgregar.setOnClickListener(v -> {
            String nombre = edtNombre.getText().toString().trim();
            //Si hay nombre, inyecta nombre
            if (!nombre.isEmpty() && adapter != null) {
                listaNombres.add(nombre);

                //Nombre al final de la fila
                adapter.notifyItemInserted(listaNombres.size() - 1);

                //Hace la lista hacia arriba
                recyclerView.scrollToPosition(listaNombres.size() - 1);
                edtNombre.setText("");

                AsyncTask.execute(() ->{

                    miCliente.addElement(nombre);

                });
                }
            //Borra la viewText :p
            //edtNombre.setText("");
        });*/

        /*btnAgregar.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, AgregarActivity.class);
            startActivity(i);
        });*/

        fabAgregar = findViewById(R.id.fabAgregar);
        fabAgregar.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, AgregarActivity.class);
            startActivity(i);
        });


    }

    @Override
    protected void onResume() {
        super.onResume();

        new Thread(() -> {
            try {
                Thread.sleep(500);

                MiCliente miCliente = new MiCliente();
                ArrayList<Personaje> listaActualizada = miCliente.getElements();

                runOnUiThread(() -> {
                    if (listaActualizada != null) {
                        listaNombres.clear();
                        listaNombres.addAll(listaActualizada);

                        if (adapter != null) {
                            adapter.notifyDataSetChanged();
                            Log.d("EXITO", "Se cargaron " + listaNombres.size() + " elementos");
                        } else {
                            adapter = new MiAdaptador(listaNombres);
                            recyclerView.setAdapter(adapter);
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
