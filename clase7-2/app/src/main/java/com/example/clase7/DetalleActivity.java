package com.example.clase7;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;

public class DetalleActivity extends AppCompatActivity {

    ImageView imgFoto;
    TextView tvNombre, tvDesc, tvAtaque, tvDefensa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);

        imgFoto = findViewById(R.id.imgFotoDetalle);
        tvNombre = findViewById(R.id.tvNombreDetalle);
        tvDesc = findViewById(R.id.tvDescDetalle);
        tvAtaque = findViewById(R.id.tvAtaqueDetalle);
        tvDefensa = findViewById(R.id.tvDefensaDetalle);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String nombre = extras.getString("name");
            String descripcion = extras.getString("desc");
            String urlFoto = extras.getString("photo");
            int ataque = extras.getInt("attack");
            int defensa = extras.getInt("def");

            tvNombre.setText(nombre);
            tvDesc.setText(descripcion);
            tvAtaque.setText(String.valueOf(ataque));
            tvDefensa.setText(String.valueOf(defensa));

            Glide.with(this)
                    .load(urlFoto)
                    .placeholder(android.R.drawable.ic_menu_gallery)
                    .into(imgFoto);
        }
    }
}