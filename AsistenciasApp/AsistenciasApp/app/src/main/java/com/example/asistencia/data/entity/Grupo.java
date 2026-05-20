package com.example.asistencia.data.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "grupos")
public class Grupo {
    @PrimaryKey(autoGenerate = true)
    public long id;
    public String nombre;
    public String descripcion;
    public String horario;

    public Grupo(String nombre, String descripcion, String horario) {
        this.nombre      = nombre;
        this.descripcion = descripcion;
        this.horario     = horario;
    }
}
