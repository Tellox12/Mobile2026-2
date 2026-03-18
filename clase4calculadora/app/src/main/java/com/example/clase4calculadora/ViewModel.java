package com.example.clase4calculadora;

public class ViewModel {

    ICalculadora calculadora = new Calculadora();

    ViewModel(){


    }

    Double suma(double x, double y){
        return x + y;
    }

    Double resta(double x, double y){
        return x - y;
    }

    Double multiplicacion(double x, double y){
        return x * y;
    }

    Double division(double x, double y){
        return x / y;
    }

}
