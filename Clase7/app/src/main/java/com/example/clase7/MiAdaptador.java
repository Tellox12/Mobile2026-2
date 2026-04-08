package com.example.clase7;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MiAdaptador extends RecyclerView.Adapter<MiViewHolder> {

    private ArrayList<String> localDataSet;

    // cambiamos String[] por ArrayList<String>
    public MiAdaptador(ArrayList<String> dataSet) {
        localDataSet = dataSet;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MiViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.text_row_item, viewGroup, false);

        return new MiViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MiViewHolder viewHolder, final int position) {

        viewHolder.getTextView().setText(localDataSet.get(position));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }

    //agregra datos nuevos
    public void agregarNombre(String nuevoNombre) {
        localDataSet.add(nuevoNombre); // Lo mete a la lista
        notifyItemInserted(localDataSet.size() - 1); // Le avisa a la pantalla que dibuje el nuevo
    }



}