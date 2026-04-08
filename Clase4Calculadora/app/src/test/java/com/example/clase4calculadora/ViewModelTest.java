package com.example.clase4calculadora;

import org.junit.Assert;
import org.junit.Test;
import java.util.Optional;

public class ViewModelTest {

    @Test
    public void testMakeOperationSuma() {
        ViewModel viewModel = new ViewModel();
        Operacion operacion = new Operacion(5.0, 5.0, OperationType.SUMA);
        viewModel.makeOperation(operacion);
        Double resultado = viewModel.makeOperation(operacion);
        Assert.assertEquals(10.0, resultado, 0.0);
    }

    @Test
    public void testMakeOperationResta() {
        ViewModel viewModel = new ViewModel();
        Operacion operacion = new Operacion(5.0, 5.0, OperationType.RESTA);
        viewModel.makeOperation(operacion);
        Double resultado = viewModel.makeOperation(operacion);
        Assert.assertEquals(.0, resultado, 0.0);
    }

    @Test
    public void testMakeOperationMultiplicacion() {
        ViewModel viewModel = new ViewModel();
        Operacion operacion = new Operacion(5.0, 5.0, OperationType.MULTIPLICACION);
        viewModel.makeOperation(operacion);
        Double resultado = viewModel.makeOperation(operacion);
        Assert.assertEquals(25.0, resultado, 0.0);
    }

    @Test
    public void testMakeOperationDivision() {
        ViewModel viewModel = new ViewModel();
        Operacion operacion = new Operacion(5.0, 5.0, OperationType.DIVISON);
        viewModel.makeOperation(operacion);
        Double resultado = viewModel.makeOperation(operacion);
        Assert.assertEquals(1.0, resultado, 0.0);
    }

    @Test
    public void testMakeOperationDivisonEntreCero(){
        ViewModel viewModel = new ViewModel();
        Operacion operacion = new Operacion(5.0, 0.0, OperationType.DIVISON);
        Double resultado = viewModel.makeOperation(operacion);
        Assert.assertNotNull(resultado);
        Assert.assertEquals(Double.POSITIVE_INFINITY, resultado, 0.0);
    }

    @Test
    public void testMakeOperations(){
        ViewModel viewModel = new ViewModel();
        Operacion operacion1 = new Operacion(5.0, 5.0, OperationType.SUMA);
        Operacion operacion2 = new Operacion(5.0, 5.0, OperationType.SUMA);
        Double resultado = viewModel.makeOperation(new Operacion[]{operacion1, operacion2});
        Assert.assertEquals(20.0, resultado, 0.0);
    }

    @Test
    public void testMakeOperationsMix(){
        ViewModel viewModel = new ViewModel();
        Operacion operacion1 = new Operacion(-5.0, -5.0, OperationType.SUMA);
        Operacion operacion2 = new Operacion(5.0, 5.0, OperationType.SUMA);
        Double resultado = viewModel.makeOperation(new Operacion[]{operacion1, operacion2});
        Assert.assertEquals(0.0, resultado, 0.0);
    }

    @Test
    public void testMakeOperationsNegativo(){
        ViewModel viewModel = new ViewModel();
        Operacion operacion1 = new Operacion(-5.0, -5.0, OperationType.SUMA);
        Operacion operacion2 = new Operacion(-5.0, -5.0, OperationType.SUMA);
        Double resultado = viewModel.makeOperation(new Operacion[]{operacion1, operacion2});
        Assert.assertEquals(-20.0, resultado, 0.0);
    }

}
