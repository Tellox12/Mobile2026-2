package com.example.asistencia.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import com.example.asistencia.data.entity.Grupo;
import com.example.asistencia.data.relation.GrupoWithAlumnos;
import java.util.List;

@Dao
public interface GrupoDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE) long insert(Grupo g);
    @Update void update(Grupo g);
    @Delete void delete(Grupo g);

    @Query("SELECT * FROM grupos ORDER BY nombre ASC")
    LiveData<List<Grupo>> getAll();

    @Query("SELECT * FROM grupos WHERE id = :id")
    LiveData<Grupo> getById(long id);

    @Transaction
    @Query("SELECT * FROM grupos WHERE id = :grupoId")
    LiveData<GrupoWithAlumnos> getGrupoWithAlumnos(long grupoId);

    @Transaction
    @Query("SELECT * FROM grupos ORDER BY nombre ASC")
    LiveData<List<GrupoWithAlumnos>> getAllGruposWithAlumnos();
}
