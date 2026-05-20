package com.example.asistencia;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.example.asistencia.data.entity.Alumno;
import com.example.asistencia.data.entity.Grupo;
import com.example.asistencia.databinding.ActivityMainBinding;
import com.example.asistencia.ui.Navigator;
import com.example.asistencia.ui.fragment.AlumnosFragment;
import com.example.asistencia.ui.fragment.AsistenciasFragment;
import com.example.asistencia.ui.fragment.GruposFragment;

public class MainActivity extends AppCompatActivity implements Navigator {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (savedInstanceState == null) {
            navigate(GruposFragment.newInstance(), false);
        }
    }

    /* ---- Navigator ---- */

    @Override
    public void onGrupoSelected(Grupo grupo) {
        navigate(AlumnosFragment.newInstance(grupo.id, grupo.nombre), true);
    }

    @Override
    public void onAlumnoSelected(Alumno alumno, Grupo grupo) {
        navigate(AsistenciasFragment.newInstance(alumno.id, grupo.id), true);
    }

    @Override
    public void navigateBack() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        }
    }

    /* ---- Helper ---- */

    private void navigate(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction tx = getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(
                        android.R.anim.fade_in,  android.R.anim.fade_out,
                        android.R.anim.fade_in,  android.R.anim.fade_out)
                .replace(R.id.fragment_container, fragment);
        if (addToBackStack) tx.addToBackStack(null);
        tx.commit();
    }
}
