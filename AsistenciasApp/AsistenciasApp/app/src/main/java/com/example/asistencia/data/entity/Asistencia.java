package com.example.asistencia.data.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
    tableName    = "asistencias",
    foreignKeys  = {
        @ForeignKey(entity = Alumno.class,
                    parentColumns = "id", childColumns = "alumnoId",
                    onDelete = ForeignKey.CASCADE),
        @ForeignKey(entity = Grupo.class,
                    parentColumns = "id", childColumns = "grupoId",
                    onDelete = ForeignKey.CASCADE)
    },
    indices = { @Index("alumnoId"), @Index("grupoId") }
)
public class Asistencia {
    @PrimaryKey(autoGenerate = true)
    public long    id;
    public long    alumnoId;
    public long    grupoId;
    public String  fecha;
    public boolean presente;

    public Asistencia(long alumnoId, long grupoId, String fecha, boolean presente) {
        this.alumnoId = alumnoId;
        this.grupoId  = grupoId;
        this.fecha    = fecha;
        this.presente = presente;
    }
}
