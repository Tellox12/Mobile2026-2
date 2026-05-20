package com.example.asistencia.data.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.example.asistencia.data.AppDatabase;
import com.example.asistencia.data.dao.GrupoDao;
import com.example.asistencia.data.entity.Grupo;
import com.example.asistencia.data.relation.GrupoWithAlumnos;
import java.util.List;

public class GrupoRepository {
    private final GrupoDao dao;

    public GrupoRepository(Application app) {
        dao = AppDatabase.getInstance(app).grupoDao();
    }

    public LiveData<List<Grupo>> getAll() { return dao.getAll(); }

    public LiveData<GrupoWithAlumnos> getGrupoWithAlumnos(long id) {
        return dao.getGrupoWithAlumnos(id);
    }

    public void insert(Grupo g) {
        AppDatabase.dbExecutor.execute(() -> dao.insert(g));
    }

    public void delete(Grupo g) {
        AppDatabase.dbExecutor.execute(() -> dao.delete(g));
    }
}
