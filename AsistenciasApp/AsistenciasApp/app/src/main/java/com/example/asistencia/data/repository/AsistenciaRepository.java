package com.example.asistencia.data.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.example.asistencia.data.AppDatabase;
import com.example.asistencia.data.dao.AsistenciaDao;
import com.example.asistencia.data.entity.Asistencia;
import com.example.asistencia.data.relation.AsistenciaDetalle;
import java.util.List;

public class AsistenciaRepository {
    private final AsistenciaDao dao;

    public AsistenciaRepository(Application app) {
        dao = AppDatabase.getInstance(app).asistenciaDao();
    }

    public LiveData<List<AsistenciaDetalle>> getHistorial(long alumnoId, long grupoId) {
        return dao.getHistorial(alumnoId, grupoId);
    }

    public LiveData<Integer> countPresentes(long alumnoId, long grupoId) {
        return dao.countPresentes(alumnoId, grupoId);
    }

    public void registrar(Asistencia a) {
        AppDatabase.dbExecutor.execute(() -> dao.insert(a));
    }
}
