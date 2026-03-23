package com.example.calculadora;

public class ViewModel {
    private ICalculadora calculadora = new Calculadora();
    private CalculadoraBuilder builder = new CalculadoraBuilder();
    ViewModel() {

    }
    Double makeOperation(Operacion [] operations) {
        Double cache = 0.0;
        for (Operacion operation: operations) {
            cache += makeOperation(operation);
        }
        return cache;
    }
    Double makeOperation(Operacion operacion) {
        switch (operacion.getType()) {
            case ADD:
                return calculadora.sum(operacion.x, operacion.y);
            case MINUS:
                return calculadora.minus(operacion.x, operacion.y);
            case MULTIPLY:
                return calculadora.multiply(operacion.x, operacion.y);
            case DIV:
                return calculadora.divide(operacion.x, operacion.y);
            default:
                return 0.0;
        }
    }
}
