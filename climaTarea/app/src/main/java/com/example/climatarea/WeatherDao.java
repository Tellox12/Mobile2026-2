package com.example.climatarea;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface WeatherDao {

    @Insert
    void insertWeather(WeatherEntity weather);

    @Query("SELECT * FROM weather_history ORDER BY id DESC")
    List<WeatherEntity> getAllHistory();

}
