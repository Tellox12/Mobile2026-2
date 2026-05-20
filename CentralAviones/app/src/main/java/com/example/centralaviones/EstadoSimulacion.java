package com.example.centralaviones;

import java.util.ArrayList;
import java.util.List;

public class EstadoSimulacion {
    private final int numeroPaso;
    private final int totalColisiones;
    private final List<Avion> aviones;
    private final boolean[][][] colisiones;
    private final int tamano;

    public EstadoSimulacion(int numeroPaso, int totalColisiones, List<Avion> aviones, boolean[][][] colisiones) {
        this.numeroPaso = numeroPaso;
        this.totalColisiones = totalColisiones;
        this.tamano = colisiones.length;

        this.aviones = new ArrayList<>(aviones.size());
        for (Avion a : aviones) this.aviones.add(new Avion(a));

        this.colisiones = new boolean[tamano][tamano][tamano];
        for (int z = 0; z < tamano; z++) {
            for (int y = 0; y < tamano; y++) {
                System.arraycopy(colisiones[z][y], 0, this.colisiones[z][y], 0, tamano);
            }
        }
    }

    public int getNumeroPaso() { return numeroPaso; }
    public int getTotalColisiones() { return totalColisiones; }

    public List<Avion> getAvionesCopiados() {
        List<Avion> copia = new ArrayList<>(aviones.size());
        for (Avion a : aviones) copia.add(new Avion(a));
        return copia;
    }

    public boolean[][][] getColisionesCopiadas() {
        boolean[][][] copia = new boolean[tamano][tamano][tamano];
        for (int z = 0; z < tamano; z++) {
            for (int y = 0; y < tamano; y++) {
                System.arraycopy(colisiones[z][y], 0, copia[z][y], 0, tamano);
            }
        }
        return copia;
    }
}