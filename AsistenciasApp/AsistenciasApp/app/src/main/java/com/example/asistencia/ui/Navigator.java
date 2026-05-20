package com.example.asistencia.ui;

import com.example.asistencia.data.entity.Alumno;
import com.example.asistencia.data.entity.Grupo;

/** Contrato de navegación entre fragmentos — implementado por MainActivity */
public interface Navigator {
    void onGrupoSelected(Grupo grupo);
    void onAlumnoSelected(Alumno alumno, Grupo grupo);
    void navigateBack();
}
