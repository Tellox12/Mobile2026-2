package com.example.clase7;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView imgDetail = findViewById(R.id.imgDetail);
        TextView tvDetailName = findViewById(R.id.tvDetailName);
        TextView tvDetailDesc = findViewById(R.id.tvDetailDesc);
        TextView tvDetailAttack = findViewById(R.id.tvDetailAttack);
        TextView tvDetailDefense = findViewById(R.id.tvDetailDefense);

        Personaje personaje = (Personaje) getIntent().getSerializableExtra("personaje");

        if (personaje != null) {
            tvDetailName.setText(personaje.getName());
            tvDetailDesc.setText(personaje.getDesc());
            tvDetailAttack.setText("Ataque: " + personaje.getAtaque());
            tvDetailDefense.setText("Defensa: " + personaje.getDefensa());

            Glide.with(this)
                    .load(personaje.getPhoto())
                    .centerCrop()
                    .into(imgDetail);
        }
    }
}