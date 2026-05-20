package com.example.centralaviones;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class SimulacionManager {
    private int tamanoTablero;
    private int numAvionesInicial;
    private List<Avion> aviones;
    private boolean[][][] colisiones;
    private int numeroPaso;
    private int totalColisiones;
    private final Stack<EstadoSimulacion> historial;

    public SimulacionManager() {
        historial = new Stack<>();
        inicializar();
    }

    public int getTamanoTablero() { return tamanoTablero; }

    public void inicializar() {
        historial.clear();
        numeroPaso = 0;
        totalColisiones = 0;

        tamanoTablero = 20;

        // Aumentamos drásticamente los aviones para llenar el mapa de 20x20x20
        numAvionesInicial = 400;

        colisiones = new boolean[tamanoTablero][tamanoTablero][tamanoTablero];
        aviones = generarAvionesAleatorios();
    }

    public void avanzarPaso() {
        historial.push(new EstadoSimulacion(numeroPaso, totalColisiones, aviones, colisiones));
        colisiones = new boolean[tamanoTablero][tamanoTablero][tamanoTablero];

        for (Avion avion : aviones) moverAvion(avion);
        // Cambia esto:
        // aviones.removeIf(a -> !a.isActivo() || fueraDeRango(a));

        // Por esto:
        aviones.removeIf(a -> !a.isActivo());
        detectarColisiones();
        numeroPaso++;
    }

    public boolean retrocederPaso() {
        if (historial.isEmpty()) return false;
        EstadoSimulacion anterior = historial.pop();
        numeroPaso = anterior.getNumeroPaso();
        totalColisiones = anterior.getTotalColisiones();
        aviones = anterior.getAvionesCopiados();
        colisiones = anterior.getColisionesCopiadas();
        return true;
    }

    public boolean puedeRetroceder() { return !historial.isEmpty(); }
    public int getNumeroPaso() { return numeroPaso; }
    public int getTotalColisiones() { return totalColisiones; }

    public char[][] getTableroZ(int z) {
        char[][] tablero = new char[tamanoTablero][tamanoTablero];
        for (int r = 0; r < tamanoTablero; r++) {
            for (int c = 0; c < tamanoTablero; c++) tablero[r][c] = ' ';
        }
        for (int r = 0; r < tamanoTablero; r++) {
            for (int c = 0; c < tamanoTablero; c++) {
                if (colisiones[z][r][c]) tablero[r][c] = 'X';
            }
        }
        for (Avion a : aviones) {
            if (a.isActivo() && a.getZ() == z) tablero[a.getY()][a.getX()] = a.getSimbolo();
        }
        return tablero;
    }

    private void moverAvion(Avion avion) {
        switch (avion.getDireccion()) {
            case ARRIBA:    avion.setY(avion.getY() - 1); break;
            case ABAJO:     avion.setY(avion.getY() + 1); break;
            case IZQUIERDA: avion.setX(avion.getX() - 1); break;
            case DERECHA:   avion.setX(avion.getX() + 1); break;
        }

        // Efecto infinito: si sale por un borde, entra por el opuesto
        if (avion.getX() < 0) avion.setX(tamanoTablero - 1);
        if (avion.getX() >= tamanoTablero) avion.setX(0);
        if (avion.getY() < 0) avion.setY(tamanoTablero - 1);
        if (avion.getY() >= tamanoTablero) avion.setY(0);
    }

    private boolean fueraDeRango(Avion a) {
        return a.getX() < 0 || a.getX() >= tamanoTablero ||
                a.getY() < 0 || a.getY() >= tamanoTablero ||
                a.getZ() < 0 || a.getZ() >= tamanoTablero;
    }

    private void detectarColisiones() {
        Map<String, List<Avion>> mapa = new HashMap<>();
        for (Avion a : aviones) {
            String clave = a.getX() + "," + a.getY() + "," + a.getZ();
            mapa.computeIfAbsent(clave, k -> new ArrayList<>()).add(a);
        }
        for (Map.Entry<String, List<Avion>> entrada : mapa.entrySet()) {
            List<Avion> grupo = entrada.getValue();
            if (grupo.size() >= 2) {
                int x = grupo.get(0).getX();
                int y = grupo.get(0).getY();
                int z = grupo.get(0).getZ();
                colisiones[z][y][x] = true;
                totalColisiones++;
                for (Avion a : grupo) a.setActivo(false);
            }
        }
        aviones.removeIf(a -> !a.isActivo());
    }

    private List<Avion> generarAvionesAleatorios() {
        List<Avion> lista = new ArrayList<>();
        List<String> posicionesUsadas = new ArrayList<>();
        int intentos = 0;
        while (lista.size() < numAvionesInicial && intentos < 2000) {
            int x = (int) (Math.random() * tamanoTablero);
            int y = (int) (Math.random() * tamanoTablero);
            int z = (int) (Math.random() * tamanoTablero);
            String clave = x + "," + y + "," + z;
            if (!posicionesUsadas.contains(clave)) {
                posicionesUsadas.add(clave);
                lista.add(new Avion(x, y, z, Avion.Direccion.aleatoria()));
            }
            intentos++;
        }
        return lista;
    }
}