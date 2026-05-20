package com.example.asistencia.data.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "alumnos")
public class Alumno {
    @PrimaryKey(autoGenerate = true)
    public long id;
    public String nombre;
    public String apellido;
    public String email;

    public Alumno(String nombre, String apellido, String email) {
        this.nombre   = nombre;
        this.apellido = apellido;
        this.email    = email;
    }

    public String getNombreCompleto() { return nombre + " " + apellido; }
}
