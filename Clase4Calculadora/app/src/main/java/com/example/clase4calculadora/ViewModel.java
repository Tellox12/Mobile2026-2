package com.example.clase4calculadora;

public class ViewModel {
    private ICalculadora calculadora = new Calculadora();
    private  CalculadoraBuilder builder = new CalculadoraBuilder();

    ViewModel(){

    }

    Double makeOperation(Operacion [] operations){
        Double cache = 0.0;
        for (Operacion operation: operations){
            cache += makeOperation(operation);
        }
        return cache;
    }

    Double makeOperation(Operacion operacion) {
        switch (operacion.getType()) {
            case SUMA:
                return calculadora.suma(operacion.x, operacion.y);
            case RESTA:
                return calculadora.resta(operacion.x, operacion.y);
            case MULTIPLICACION:
                return calculadora.multiplicacion(operacion.x, operacion.y);
            case DIVISON:
                return calculadora.division(operacion.x, operacion.y);
            default:
                return 0.0;
        }
    }

    public Double resolverCadena(CalculadoraInput input) {
        if (input.operacionInicial == null) return 0.0;

        // Saca el primer número (ej. el 5)
        Double resultadoFinal = makeOperation(input.operacionInicial);

        // Recorre y calcula el resto (+ 2, - 2)
        for (OperacionMin op : input.operaciones) {
            Operacion siguiente = new Operacion(resultadoFinal, op.getX(), op.getOperationType());
            resultadoFinal = makeOperation(siguiente);
        }

        return resultadoFinal;
    }

}

