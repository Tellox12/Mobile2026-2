package com.example.centralaviones;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.centralaviones.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private SimulacionManager simulacion;
    private int zActual = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        simulacion = new SimulacionManager();
        configurarBotones();
        actualizarUI();
    }

    private void configurarBotones() {
        binding.btnSiguiente.setOnClickListener(v -> {
            simulacion.avanzarPaso();
            actualizarUI();
        });

        binding.btnAnterior.setOnClickListener(v -> {
            simulacion.retrocederPaso();
            actualizarUI();
        });

        binding.btnReiniciar.setOnClickListener(v -> {
            simulacion.inicializar();
            zActual = 0;
            actualizarUI();
        });

        binding.btnSubirZ.setOnClickListener(v -> {
            if (zActual < simulacion.getTamanoTablero() - 1) {
                zActual++;
                actualizarUI();
            }
        });

        binding.btnBajarZ.setOnClickListener(v -> {
            if (zActual > 0) {
                zActual--;
                actualizarUI();
            }
        });
    }

    private void actualizarUI() {
        int n = simulacion.getTamanoTablero();
        char[][] tablero = simulacion.getTableroZ(zActual);

        // Pasar el tablero al view personalizado
        binding.tableroView.setTablero(tablero, n);

        // Contar aviones en la capa actual
        int avionesEnCapa = 0;
        for (char[] fila : tablero) {
            for (char c : fila) {
                if (c == '^' || c == 'v' || c == '<' || c == '>') avionesEnCapa++;
            }
        }

        // Actualizar estadísticas
        binding.tvPasos.setText(String.valueOf(simulacion.getNumeroPaso()));
        binding.tvAltitud.setText(String.valueOf(zActual));
        binding.tvAviones.setText(String.valueOf(avionesEnCapa));
        binding.tvColisiones.setText(String.valueOf(simulacion.getTotalColisiones()));

        // Estado de botones
        boolean puedeRetroceder = simulacion.puedeRetroceder();
        binding.btnAnterior.setEnabled(puedeRetroceder);
        binding.btnAnterior.setAlpha(puedeRetroceder ? 1.0f : 0.35f);

        binding.btnBajarZ.setEnabled(zActual > 0);
        binding.btnBajarZ.setAlpha(zActual > 0 ? 1.0f : 0.35f);

        binding.btnSubirZ.setEnabled(zActual < n - 1);
        binding.btnSubirZ.setAlpha(zActual < n - 1 ? 1.0f : 0.35f);
    }
}
