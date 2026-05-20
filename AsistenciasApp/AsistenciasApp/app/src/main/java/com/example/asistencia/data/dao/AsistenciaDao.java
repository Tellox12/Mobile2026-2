package com.example.asistencia.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import com.example.asistencia.data.entity.Asistencia;
import com.example.asistencia.data.relation.AsistenciaDetalle;
import java.util.List;

@Dao
public interface AsistenciaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE) long insert(Asistencia a);
    @Update void update(Asistencia a);
    @Delete void delete(Asistencia a);

    @Query("SELECT as1.*, " +
           "a.nombre AS nombreAlumno, a.apellido AS apellidoAlumno, " +
           "g.nombre AS nombreGrupo " +
           "FROM asistencias as1 " +
           "INNER JOIN alumnos  a ON as1.alumnoId = a.id " +
           "INNER JOIN grupos   g ON as1.grupoId  = g.id " +
           "WHERE as1.alumnoId = :alumnoId AND as1.grupoId = :grupoId " +
           "ORDER BY as1.fecha DESC")
    LiveData<List<AsistenciaDetalle>> getHistorial(long alumnoId, long grupoId);

    @Query("SELECT COUNT(*) FROM asistencias " +
           "WHERE alumnoId = :alumnoId AND grupoId = :grupoId AND presente = 1")
    LiveData<Integer> countPresentes(long alumnoId, long grupoId);
}
