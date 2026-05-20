package com.example.centralaviones;

import java.util.Random;

public class Avion {
    public enum Direccion {
        ARRIBA('^'), ABAJO('v'), IZQUIERDA('<'), DERECHA('>');
        private final char simbolo;
        Direccion(char simbolo) { this.simbolo = simbolo; }
        public char getSimbolo() { return simbolo; }
        public static Direccion aleatoria() {
            return values()[new Random().nextInt(values().length)];
        }
    }

    private int x, y, z;
    private Direccion direccion;
    private boolean activo;

    public Avion(int x, int y, int z, Direccion direccion) {
        this.x = x; this.y = y; this.z = z;
        this.direccion = direccion;
        this.activo = true;
    }

    public Avion(Avion otro) {
        this.x = otro.x; this.y = otro.y; this.z = otro.z;
        this.direccion = otro.direccion;
        this.activo = otro.activo;
    }

    public int getX() { return x; }
    public void setX(int x) { this.x = x; }
    public int getY() { return y; }
    public void setY(int y) { this.y = y; }
    public int getZ() { return z; }
    public void setZ(int z) { this.z = z; }
    public Direccion getDireccion() { return direccion; }
    public char getSimbolo() { return direccion.getSimbolo(); }
    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
}