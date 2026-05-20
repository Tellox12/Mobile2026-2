package com.example.asistencia.data.relation;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;
import com.example.asistencia.data.entity.Alumno;
import com.example.asistencia.data.entity.AlumnoGrupoCrossRef;
import com.example.asistencia.data.entity.Grupo;
import java.util.List;

public class GrupoWithAlumnos {
    @Embedded
    public Grupo grupo;

    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy  = @Junction(
            value        = AlumnoGrupoCrossRef.class,
            parentColumn = "grupoId",
            entityColumn = "alumnoId"
        )
    )
    public List<Alumno> alumnos;
}
