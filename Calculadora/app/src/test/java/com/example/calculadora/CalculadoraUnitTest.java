package com.example.calculadora;

import org.junit.Assert;
import org.junit.Test;

public class CalculadoraUnitTest {

    @Test
    public void testOperationSuma() {
        ICalculadora calculadora = new Calculadora();
        Double result = calculadora.sum(5, 5);
        Assert.assertEquals(10.0, result, 0);
    }

    @Test
    public void testOperationResta() {
        ICalculadora calculadora = new Calculadora();
        Double result = calculadora.minus(5, 5);
        Assert.assertEquals(0, result, 0);
    }

    @Test
    public void testOperationMultiplicacion() {
        ICalculadora calculadora = new Calculadora();
        Double result = calculadora.multiply(5, 5);
        Assert.assertEquals(25, result, 0);
    }

    @Test
    public void testOperationDivision() {
        ICalculadora calculadora = new Calculadora();
        Double result = calculadora.divide(5, 5);
        Assert.assertEquals(1, result, 0);
    }

    @Test
    public void testOperationDivisionEntrecero() {
        ICalculadora calculadora = new Calculadora();
        Double result = calculadora.divide(5, 0);
        Assert.assertEquals(Double.POSITIVE_INFINITY, result, 0);
    }
}
