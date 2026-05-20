package com.example.asistencia.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import com.example.asistencia.data.entity.Alumno;
import com.example.asistencia.data.entity.AlumnoGrupoCrossRef;
import java.util.List;

@Dao
public interface AlumnoDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE) long insert(Alumno a);
    @Insert(onConflict = OnConflictStrategy.IGNORE) void insertCrossRef(AlumnoGrupoCrossRef ref);
    @Delete void deleteCrossRef(AlumnoGrupoCrossRef ref);
    @Update void update(Alumno a);
    @Delete void delete(Alumno a);

    @Query("SELECT a.* FROM alumnos a " +
           "INNER JOIN alumno_grupo ag ON a.id = ag.alumnoId " +
           "WHERE ag.grupoId = :grupoId ORDER BY a.apellido ASC")
    LiveData<List<Alumno>> getAlumnosByGrupo(long grupoId);

    @Query("SELECT * FROM alumnos WHERE id = :id")
    LiveData<Alumno> getById(long id);
}
