package com.example.asistencia.data.entity;

import androidx.room.Entity;
import androidx.room.Index;

@Entity(
    tableName    = "alumno_grupo",
    primaryKeys  = {"alumnoId", "grupoId"},
    indices      = { @Index("grupoId") }
)
public class AlumnoGrupoCrossRef {
    public long alumnoId;
    public long grupoId;

    public AlumnoGrupoCrossRef(long alumnoId, long grupoId) {
        this.alumnoId = alumnoId;
        this.grupoId  = grupoId;
    }
}
