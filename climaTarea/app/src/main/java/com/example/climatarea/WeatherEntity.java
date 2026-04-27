package com.example.climatarea;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "weather_history")
public class WeatherEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String date;
    public String dayOfWeek;
    public double temperature;
    public String description;

    public WeatherEntity( String date, String dayOfWeek, double temperature, String description) {
        this.date = date;
        this.dayOfWeek = dayOfWeek;
        this.temperature = temperature;
        this.description = description;
    }
}
