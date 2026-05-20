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
import com.example.asistencia.data.entity.Grupo;
import com.example.asistencia.databinding.FragmentGruposBinding;
import com.example.asistencia.databinding.ItemGrupoBinding;
import com.example.asistencia.ui.Navigator;
import com.example.asistencia.ui.adapter.DiffCallbacks;
import com.example.asistencia.ui.adapter.GenericAdapter;
import com.example.asistencia.ui.viewmodel.GrupoViewModel;

public class GruposFragment extends Fragment {

    public static GruposFragment newInstance() { return new GruposFragment(); }

    private FragmentGruposBinding binding;
    private Navigator             navigator;
    private GrupoViewModel        viewModel;

    @Override
    public void onAttach(@NonNull Context ctx) {
        super.onAttach(ctx);
        if (ctx instanceof Navigator) navigator = (Navigator) ctx;
        else throw new ClassCastException("Activity debe implementar Navigator");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentGruposBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(GrupoViewModel.class);

        GenericAdapter<Grupo, ItemGrupoBinding> adapter = new GenericAdapter<>(
                DiffCallbacks.GRUPO,
                (inf, parent) -> ItemGrupoBinding.inflate(inf, parent, false),
                (b, grupo) -> {
                    b.tvNombreGrupo.setText(grupo.nombre);
                    b.tvHorario.setText(grupo.horario);
                    b.tvDescripcion.setText(grupo.descripcion);
                },
                (b, grupo) -> b.getRoot().setOnClickListener(v -> navigator.onGrupoSelected(grupo))
        );

        binding.recyclerGrupos.setAdapter(adapter);
        viewModel.getGrupos().observe(getViewLifecycleOwner(), lista -> {
            binding.tvVacio.setVisibility(
                    (lista == null || lista.isEmpty()) ? View.VISIBLE : View.GONE);
            adapter.submitList(lista);
        });

        binding.fabAddGrupo.setOnClickListener(v ->
                viewModel.insertar(new Grupo("Nuevo Grupo", "Descripción", "Lun 09:00")));
    }

    @Override
    public void onDestroyView() { super.onDestroyView(); binding = null; }
}
