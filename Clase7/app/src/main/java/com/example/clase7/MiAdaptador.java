package com.example.clase7;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;

public class MiAdaptador extends RecyclerView.Adapter<MiViewHolder> {

    private ArrayList<Personaje> localDataSet;

    public MiAdaptador(ArrayList<Personaje> dataSet) {
        localDataSet = dataSet;
    }

    @Override
    public MiViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.text_row_item, viewGroup, false);

        return new MiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MiViewHolder viewHolder, final int position) {
        Personaje p = localDataSet.get(position);

        viewHolder.getTextView().setText(p.getName());

        Glide.with(viewHolder.itemView.getContext())
                .load(p.getPhoto())
                .centerCrop()
                .into(viewHolder.getImageView());

        // Acción de clic para ir a la segunda pantalla
        viewHolder.itemView.setOnClickListener(v -> {
            Context context = v.getContext();
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("personaje", p);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }

    public void agregarPersonaje(Personaje nuevo) {
        localDataSet.add(nuevo);
        notifyItemInserted(localDataSet.size() - 1);
    }

    public void actualizarDatos(ArrayList<Personaje> nuevosDatos) {
        localDataSet.clear();
        localDataSet.addAll(nuevosDatos);
        notifyDataSetChanged();
    }
}