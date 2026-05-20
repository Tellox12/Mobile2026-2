package com.example.asistencia.ui.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.asistencia.data.entity.Grupo;
import com.example.asistencia.data.relation.GrupoWithAlumnos;
import com.example.asistencia.data.repository.GrupoRepository;
import java.util.List;

public class GrupoViewModel extends AndroidViewModel {
    private final GrupoRepository     repo;
    private final LiveData<List<Grupo>> grupos;

    public GrupoViewModel(@NonNull Application app) {
        super(app);
        repo   = new GrupoRepository(app);
        grupos = repo.getAll();
    }

    public LiveData<List<Grupo>> getGrupos() { return grupos; }

    public LiveData<GrupoWithAlumnos> getGrupoWithAlumnos(long id) {
        return repo.getGrupoWithAlumnos(id);
    }

    public void insertar(Grupo g) { repo.insert(g); }
    public void eliminar(Grupo g) { repo.delete(g); }
}
