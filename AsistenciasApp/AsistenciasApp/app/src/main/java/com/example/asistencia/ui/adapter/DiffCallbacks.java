package com.example.asistencia.ui.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import com.example.asistencia.data.entity.Alumno;
import com.example.asistencia.data.entity.Grupo;
import com.example.asistencia.data.relation.AsistenciaDetalle;

public final class DiffCallbacks {

    public static final DiffUtil.ItemCallback<Grupo> GRUPO =
            new DiffUtil.ItemCallback<Grupo>() {
                @Override public boolean areItemsTheSame(@NonNull Grupo a, @NonNull Grupo b)    { return a.id == b.id; }
                @Override public boolean areContentsTheSame(@NonNull Grupo a, @NonNull Grupo b) { return a.nombre.equals(b.nombre) && a.horario.equals(b.horario); }
            };

    public static final DiffUtil.ItemCallback<Alumno> ALUMNO =
            new DiffUtil.ItemCallback<Alumno>() {
                @Override public boolean areItemsTheSame(@NonNull Alumno a, @NonNull Alumno b)    { return a.id == b.id; }
                @Override public boolean areContentsTheSame(@NonNull Alumno a, @NonNull Alumno b) { return a.getNombreCompleto().equals(b.getNombreCompleto()); }
            };

    public static final DiffUtil.ItemCallback<AsistenciaDetalle> ASISTENCIA =
            new DiffUtil.ItemCallback<AsistenciaDetalle>() {
                @Override public boolean areItemsTheSame(@NonNull AsistenciaDetalle a, @NonNull AsistenciaDetalle b)    { return a.asistencia.id == b.asistencia.id; }
                @Override public boolean areContentsTheSame(@NonNull AsistenciaDetalle a, @NonNull AsistenciaDetalle b) { return a.asistencia.fecha.equals(b.asistencia.fecha) && a.asistencia.presente == b.asistencia.presente; }
            };

    private DiffCallbacks() {}
}
