package com.example.clase7;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AgregarActivity extends AppCompatActivity {

    EditText etNombre, etDesc, etPhoto, etAttack, etDef;
    Button btnGuardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar);

        etNombre = findViewById(R.id.etNombre);
        etDesc = findViewById(R.id.etDesc);
        etPhoto = findViewById(R.id.etPhoto);
        etAttack = findViewById(R.id.etAttack);
        etDef = findViewById(R.id.etDef);
        btnGuardar = findViewById(R.id.btnGuardar);

        btnGuardar.setOnClickListener(v -> {
            String nom = etNombre.getText().toString();
            String des = etDesc.getText().toString();
            String url = etPhoto.getText().toString();

            if (!nom.isEmpty()) {

                btnGuardar.setEnabled(false);

                int atk = etAttack.getText().toString().isEmpty() ? 0 : Integer.parseInt(etAttack.getText().toString());
                int dfs = etDef.getText().toString().isEmpty() ? 0 : Integer.parseInt(etDef.getText().toString());

                Personaje personaje = new Personaje(nom, des, url, atk, dfs);

                new Thread(() -> {
                    try {
                        MiCliente cliente = new MiCliente();
                        cliente.addElement(personaje);

                        runOnUiThread(() -> {
                            Toast.makeText(this, "Personaje Guardado", Toast.LENGTH_SHORT).show();
                            finish();
                        });
                    } catch (Exception e) {
                        runOnUiThread(() -> {
                            btnGuardar.setEnabled(true);
                            Toast.makeText(this, "Error al guardar", Toast.LENGTH_SHORT).show();
                        });
                    }
                }).start();
            } else {
                etNombre.setError("El nombre es obligatorio");
            }
        });
}
}