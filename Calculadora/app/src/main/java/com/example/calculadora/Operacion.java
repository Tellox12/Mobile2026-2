package com.example.calculadora;

public class Operacion {
    Double x;
    Double y;
    OperationType type;

    public Operacion(Double x, Double y, OperationType type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public OperationType getType() {
        return type;
    }

    public void setType(OperationType type) {
        this.type = type;
    }
}
