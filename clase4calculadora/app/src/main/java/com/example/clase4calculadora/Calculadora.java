package com.example.clase4calculadora;

public class Calculadora implements ICalculadora{
    @Override
    public Double suma(double x, double y) {
        return x + y;
    }

    @Override
    public Double resta(double x, double y) {
        return x - y;
    }

    @Override
    public Double multiplicacion(double x, double y) {
        return x * y;
    }

    @Override
    public Double division(double x, double y) {
        return x / y;
    }
}
