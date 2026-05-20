package com.example.asistencia.ui.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import com.example.asistencia.data.entity.Alumno;
import com.example.asistencia.data.repository.AlumnoRepository;
import java.util.List;

public class AlumnoViewModel extends AndroidViewModel {
    private final AlumnoRepository repo;
    private final MutableLiveData<Long> grupoIdLive = new MutableLiveData<>();
    public  final LiveData<List<Alumno>> alumnos;

    public AlumnoViewModel(@NonNull Application app) {
        super(app);
        repo    = new AlumnoRepository(app);
        alumnos = Transformations.switchMap(grupoIdLive, repo::getByGrupo);
    }

    public void setGrupoId(long grupoId) { grupoIdLive.setValue(grupoId); }

    public void insertar(Alumno a)               { repo.insert(a); }
    public void inscribir(long aId, long gId)    { repo.inscribir(aId, gId); }
    public void desinscribir(long aId, long gId) { repo.desinscribir(aId, gId); }
}
