package com.example.asistencia.data.relation;

import androidx.room.Embedded;
import com.example.asistencia.data.entity.Asistencia;

public class AsistenciaDetalle {
    @Embedded
    public Asistencia asistencia;
    public String     nombreAlumno;
    public String     apellidoAlumno;
    public String     nombreGrupo;
}
