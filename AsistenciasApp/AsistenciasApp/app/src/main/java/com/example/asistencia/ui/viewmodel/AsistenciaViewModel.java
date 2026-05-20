package com.example.asistencia.ui.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.asistencia.data.entity.Asistencia;
import com.example.asistencia.data.relation.AsistenciaDetalle;
import com.example.asistencia.data.repository.AsistenciaRepository;
import java.time.LocalDate;
import java.util.List;

public class AsistenciaViewModel extends AndroidViewModel {
    private final AsistenciaRepository repo;

    private long alumnoId = -1;
    private long grupoId  = -1;
    private LiveData<List<AsistenciaDetalle>> historial;
    private LiveData<Integer>                 presentes;

    public AsistenciaViewModel(@NonNull Application app) {
        super(app);
        repo = new AsistenciaRepository(app);
    }

    public void init(long alumnoId, long grupoId) {
        if (this.alumnoId == alumnoId && this.grupoId == grupoId) return;
        this.alumnoId = alumnoId;
        this.grupoId  = grupoId;
        historial = repo.getHistorial(alumnoId, grupoId);
        presentes = repo.countPresentes(alumnoId, grupoId);
    }

    public LiveData<List<AsistenciaDetalle>> getHistorial() { return historial; }
    public LiveData<Integer>                 getPresentes() { return presentes; }

    public void registrar(boolean estaPresente) {
        String fecha = LocalDate.now().toString();
        repo.registrar(new Asistencia(alumnoId, grupoId, fecha, estaPresente));
    }
}
