package com.example.clase4calculadora;

import org.junit.Assert;
import org.junit.Test;

public class CalculadoraUnitTest {

    @Test
    public void testOperationSuma() {
        ICalculadora calculadora = new Calculadora();
        Double result = calculadora.suma(5, 5);
        Assert.assertEquals(10.0, result, 0);
    }

    @Test
    public void testOperationResta() {
        ICalculadora calculadora = new Calculadora();
        Double result = calculadora.resta(5, 5);
        Assert.assertEquals(0, result, 0);
    }

    @Test
    public void testOperationMultiplicacion() {
        ICalculadora calculadora = new Calculadora();
        Double result = calculadora.multiplicacion(5, 5);
        Assert.assertEquals(25, result, 0);

    }

    @Test
    public void testOperationDivision() {
        ICalculadora calculadora = new Calculadora();
        Double result = calculadora.division(5, 5);
        Assert.assertEquals(1, result, 0);
    }

    @Test
    public void testOperationDivisionEntreCero() {
        ICalculadora calculadora = new Calculadora();
        Double result = calculadora.division(5, 0);
        Assert.assertEquals(Double.POSITIVE_INFINITY, result, 0);
    }

}
