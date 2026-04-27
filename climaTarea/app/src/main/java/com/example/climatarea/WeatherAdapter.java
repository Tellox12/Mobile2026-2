package com.example.climatarea;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder> {
    private List<WeatherEntity> weatherList;

    public WeatherAdapter(List<WeatherEntity> weatherList) {
        this.weatherList = weatherList;
    }

    @NonNull
    @Override
    public WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_weather, parent, false);
        return new WeatherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherViewHolder holder, int position) {
        WeatherEntity weather = weatherList.get(position);
        holder.tvDay.setText(weather.dayOfWeek);
        holder.tvDate.setText(weather.date); // Añade esta línea
        holder.tvDescription.setText(weather.description);
        holder.tvTemp.setText(weather.temperature + "°C");
    }

    @Override
    public int getItemCount() {
        return weatherList.size();
    }

    public static class WeatherViewHolder extends RecyclerView.ViewHolder {
        TextView tvDay, tvDescription, tvTemp, tvDate;
        public WeatherViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDay = itemView.findViewById(R.id.tvDay);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvTemp = itemView.findViewById(R.id.tvTemp);
            tvDate = itemView.findViewById(R.id.tvDate);
        }
    }
}