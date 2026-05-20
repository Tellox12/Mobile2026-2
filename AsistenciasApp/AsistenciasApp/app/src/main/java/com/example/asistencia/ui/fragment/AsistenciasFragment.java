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
import com.example.asistencia.databinding.FragmentAsistenciasBinding;
import com.example.asistencia.databinding.ItemAsistenciaBinding;
import com.example.asistencia.ui.Navigator;
import com.example.asistencia.ui.adapter.DiffCallbacks;
import com.example.asistencia.ui.adapter.GenericAdapter;
import com.example.asistencia.ui.viewmodel.AsistenciaViewModel;

public class AsistenciasFragment extends Fragment {

    private static final String ARG_ALUMNO_ID = "alumnoId";
    private static final String ARG_GRUPO_ID  = "grupoId";

    public static AsistenciasFragment newInstance(long alumnoId, long grupoId) {
        AsistenciasFragment f = new AsistenciasFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_ALUMNO_ID, alumnoId);
        args.putLong(ARG_GRUPO_ID,  grupoId);
        f.setArguments(args);
        return f;
    }

    private FragmentAsistenciasBinding binding;
    private Navigator                  navigator;
    private AsistenciaViewModel        viewModel;

    @Override
    public void onAttach(@NonNull Context ctx) {
        super.onAttach(ctx);
        navigator = (Navigator) ctx;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAsistenciasBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        assert getArguments() != null;
        long alumnoId = getArguments().getLong(ARG_ALUMNO_ID);
        long grupoId  = getArguments().getLong(ARG_GRUPO_ID);

        binding.btnBack.setOnClickListener(v -> navigator.navigateBack());

        viewModel = new ViewModelProvider(this).get(AsistenciaViewModel.class);
        viewModel.init(alumnoId, grupoId);

        GenericAdapter<com.example.asistencia.data.relation.AsistenciaDetalle,
                       ItemAsistenciaBinding> adapter = new GenericAdapter<>(
                DiffCallbacks.ASISTENCIA,
                (inf, parent) -> ItemAsistenciaBinding.inflate(inf, parent, false),
                (b, detalle) -> {
                    b.tvNombreAsistencia.setText(
                            detalle.nombreAlumno + " " + detalle.apellidoAlumno);
                    b.tvGrupoAsistencia.setText(detalle.nombreGrupo);
                    b.tvFecha.setText(detalle.asistencia.fecha);
                    b.tvEstado.setText(detalle.asistencia.presente ? "✓" : "✗");
                    b.tvEstado.setTextColor(detalle.asistencia.presente
                            ? 0xFF388E3C : 0xFFD32F2F);
                },
                (b, d) -> {} // sin click en historial
        );

        binding.recyclerAsistencias.setAdapter(adapter);

        viewModel.getHistorial().observe(getViewLifecycleOwner(), lista -> {
            binding.tvSinAsistencias.setVisibility(
                    (lista == null || lista.isEmpty()) ? View.VISIBLE : View.GONE);
            adapter.submitList(lista);
        });

        viewModel.getPresentes().observe(getViewLifecycleOwner(), count ->
                binding.tvContador.setText("Asistencias: " + (count != null ? count : 0)));

        binding.btnPresente.setOnClickListener(v -> viewModel.registrar(true));
        binding.btnAusente.setOnClickListener(v  -> viewModel.registrar(false));
    }

    @Override
    public void onDestroyView() { super.onDestroyView(); binding = null; }
}
