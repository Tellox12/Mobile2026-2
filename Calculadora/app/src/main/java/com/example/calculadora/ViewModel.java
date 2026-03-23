package com.example.calculadora;

public class ViewModel {
    ICalculadora calculadora = new Calculadora();

    ViewModel() {

    }

    double makeOperation(Operacion [] operacions) {

        double cache = 0;

        for(Operacion operation : operacions){
            cache += makeOperation(operacions);
        }

        return cache;

    }

    Double sum(double x, double y) {
        return calculadora.sum(x, y);
    }

    Double minus(double x, double y) {
        return calculadora.minus(x, y);
    }

    Double multiply(double x, double y) {
        return calculadora.multiply(x, y);
    }

    Double divide(double x, double y) {
        return calculadora.divide(x, y);
    }
}
