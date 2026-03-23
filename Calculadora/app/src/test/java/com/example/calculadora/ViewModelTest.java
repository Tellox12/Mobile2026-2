package com.example.calculadora;

import org.junit.Assert;
import org.junit.Test;

public class ViewModelTest {

    @Test
    public void testMakeOperationSuma(){
        ViewModel viewModel = new ViewModel();
        Operacion operacion = new Operacion(5.0,5.0, OperationType.ADD);
        Double resultado = viewModel.makeOperation(operacion);
        Assert.assertNotNull(resultado);
        Assert.assertEquals(10.0, resultado, 0.0);
    }

    @Test
    public void testMakeOperationResta(){
        ViewModel viewModel = new ViewModel();
        Operacion operacion = new Operacion(5.0,5.0, OperationType.MINUS);
        Double resultado = viewModel.makeOperation(operacion);
        Assert.assertNotNull(resultado);
        Assert.assertEquals(0.0, resultado, 0.0);
    }

    @Test
    public void testMakeOperationMult(){
        ViewModel viewModel = new ViewModel();
        Operacion operacion = new Operacion(5.0,5.0, OperationType.MULTIPLY);
        Double resultado = viewModel.makeOperation(operacion);
        Assert.assertNotNull(resultado);
        Assert.assertEquals(25.0, resultado, 0.0);
    }

    @Test
    public void testMakeOperationDiv(){
        ViewModel viewModel = new ViewModel();
        Operacion operacion = new Operacion(5.0,5.0, OperationType.DIV);
        Double resultado = viewModel.makeOperation(operacion);
        Assert.assertNotNull(resultado);
        Assert.assertEquals(1.0, resultado, 0.0);
    }

    @Test
    public void testMakeOperationDivEntreCero(){
        ViewModel viewModel = new ViewModel();
        Operacion operacion = new Operacion(5.0,0.0, OperationType.DIV);
        Double resultado = viewModel.makeOperation(operacion);
        Assert.assertNotNull(resultado);
        Assert.assertEquals(Double.POSITIVE_INFINITY, resultado, 0.0);
    }

    @Test
    public void testMakeOperations(){
        ViewModel viewModel = new ViewModel();
        Operacion operacion1 = new Operacion(5.0,5.0, OperationType.ADD);
        Operacion operacion2 = new Operacion(5.0,5.0, OperationType.ADD);
        Double resultado = viewModel.makeOperation(new Operacion[]{ operacion1, operacion2 });
        Assert.assertEquals(20.0, resultado, 0.0);
    }

    @Test
    public void testMakeOperationsMix(){
        ViewModel viewModel = new ViewModel();
        Operacion operacion1 = new Operacion(-5.0,-5.0, OperationType.ADD);
        Operacion operacion2 = new Operacion(5.0,5.0, OperationType.ADD);
        Double resultado = viewModel.makeOperation(new Operacion[]{ operacion1, operacion2 });
        Assert.assertEquals(0.0, resultado, 0.0);
    }

    @Test
    public void testMakeOperationsNegative(){
        ViewModel viewModel = new ViewModel();
        Operacion operacion1 = new Operacion(-5.0,-5.0, OperationType.ADD);
        Operacion operacion2 = new Operacion(-5.0,-5.0, OperationType.ADD);
        Double resultado = viewModel.makeOperation(new Operacion[]{ operacion1, operacion2 });
        Assert.assertEquals(-20.0, resultado, 0.0);
    }
}
