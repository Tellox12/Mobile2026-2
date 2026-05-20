package com.example.asistencia.data.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.example.asistencia.data.AppDatabase;
import com.example.asistencia.data.dao.AlumnoDao;
import com.example.asistencia.data.entity.Alumno;
import com.example.asistencia.data.entity.AlumnoGrupoCrossRef;
import java.util.List;

public class AlumnoRepository {
    private final AlumnoDao dao;

    public AlumnoRepository(Application app) {
        dao = AppDatabase.getInstance(app).alumnoDao();
    }

    public LiveData<List<Alumno>> getByGrupo(long grupoId) {
        return dao.getAlumnosByGrupo(grupoId);
    }

    public void insert(Alumno a) {
        AppDatabase.dbExecutor.execute(() -> dao.insert(a));
    }

    public void inscribir(long alumnoId, long grupoId) {
        AppDatabase.dbExecutor.execute(
            () -> dao.insertCrossRef(new AlumnoGrupoCrossRef(alumnoId, grupoId)));
    }

    public void desinscribir(long alumnoId, long grupoId) {
        AppDatabase.dbExecutor.execute(
            () -> dao.deleteCrossRef(new AlumnoGrupoCrossRef(alumnoId, grupoId)));
    }
}
