package com.example.asistencia.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.asistencia.data.entity.Alumno;
import com.example.asistencia.data.entity.Grupo;
import com.example.asistencia.databinding.FragmentAlumnosBinding;
import com.example.asistencia.databinding.ItemAlumnoBinding;
import com.example.asistencia.ui.Navigator;
import com.example.asistencia.ui.adapter.DiffCallbacks;
import com.example.asistencia.ui.adapter.GenericAdapter;
import com.example.asistencia.ui.viewmodel.AlumnoViewModel;
import com.example.asistencia.ui.viewmodel.GrupoViewModel;

public class AlumnosFragment extends Fragment {

    private static final String ARG_GRUPO_ID     = "grupoId";
    private static final String ARG_GRUPO_NOMBRE = "grupoNombre";

    public static AlumnosFragment newInstance(long grupoId, String grupoNombre) {
        AlumnosFragment f = new AlumnosFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_GRUPO_ID, grupoId);
        args.putString(ARG_GRUPO_NOMBRE, grupoNombre);
        f.setArguments(args);
        return f;
    }

    private FragmentAlumnosBinding binding;
    private Navigator              navigator;
    private AlumnoViewModel        alumnoVM;
    private GrupoViewModel         grupoVM;
    private Grupo                  grupoActual;

    @Override
    public void onAttach(@NonNull Context ctx) {
        super.onAttach(ctx);
        navigator = (Navigator) ctx;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAlumnosBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        assert getArguments() != null;
        long   grupoId     = getArguments().getLong(ARG_GRUPO_ID);
        String grupoNombre = getArguments().getString(ARG_GRUPO_NOMBRE, "Grupo");

        binding.tvTituloGrupo.setText(grupoNombre);
        binding.btnBack.setOnClickListener(v -> navigator.navigateBack());

        alumnoVM = new ViewModelProvider(this).get(AlumnoViewModel.class);
        grupoVM  = new ViewModelProvider(requireActivity()).get(GrupoViewModel.class);
        alumnoVM.setGrupoId(grupoId);

        grupoVM.getGrupoWithAlumnos(grupoId).observe(getViewLifecycleOwner(), gwa -> {
            if (gwa != null) grupoActual = gwa.grupo;
        });

        GenericAdapter<Alumno, ItemAlumnoBinding> adapter = new GenericAdapter<>(
                DiffCallbacks.ALUMNO,
                (inf, parent) -> ItemAlumnoBinding.inflate(inf, parent, false),
                (b, alumno) -> {
                    b.tvNombreAlumno.setText(alumno.getNombreCompleto());
                    b.tvEmail.setText(alumno.email);
                    // Inicial del alumno como avatar
                    String inicial = alumno.nombre.substring(0, 1).toUpperCase();
                    b.tvAvatar.setText(inicial);
                },
                (b, alumno) -> b.getRoot().setOnClickListener(v -> {
                    if (grupoActual != null) navigator.onAlumnoSelected(alumno, grupoActual);
                })
        );

        binding.recyclerAlumnos.setAdapter(adapter);
        alumnoVM.alumnos.observe(getViewLifecycleOwner(), lista -> {
            binding.tvSinAlumnos.setVisibility(
                    (lista == null || lista.isEmpty()) ? View.VISIBLE : View.GONE);
            adapter.submitList(lista);
        });
    }

    @Override
    public void onDestroyView() { super.onDestroyView(); binding = null; }
}
