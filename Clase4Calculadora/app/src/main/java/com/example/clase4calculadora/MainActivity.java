package com.example.clase4calculadora;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class MainActivity extends AppCompatActivity {

    ViewModel viewModel = new ViewModel();
    CalculadoraBuilder builder = new CalculadoraBuilder();

    // Estas son las pantallas (TextViews) que definiste en tu XML
    TextView txvOperation, txvResultado;
    // Esta variable guardará los números conforme los vayas presionando

    String concatenar = "";
    OperationType operacionPendiente = null;
    boolean esPrimerNumero = true;
    String expresionVisual = ""; // Esta guardará el texto completo: "5 + 2 + 3"

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        //se enlanzan pantallas
        txvOperation = findViewById(R.id.txvOperation);
        txvResultado = findViewById(R.id.txvResultado);
        //el resultado en 0 al iniciar
        txvResultado.setText("0");

        //Fila 1 botones
        //boton 7
        findViewById(R.id.btnSeven).setOnClickListener(v -> numeroPresionando("7"));
        //boton 8
        findViewById(R.id.btnEight).setOnClickListener(v -> numeroPresionando("8"));
        //boton 9
        findViewById(R.id.btnNine).setOnClickListener(v -> numeroPresionando("9"));

        //Fila 2 botones
        //boton 4
        findViewById(R.id.btnFour).setOnClickListener(v -> numeroPresionando("4"));
        //boton 5
        findViewById(R.id.btnFive).setOnClickListener(v -> numeroPresionando("5"));
        //boton 6
        findViewById(R.id.btnSix).setOnClickListener(v -> numeroPresionando("6"));

        //Fila 3 botones
        //boton 1
        findViewById(R.id.btnOne).setOnClickListener(v -> numeroPresionando("1"));
        //boton 2
        findViewById(R.id.btnSecond).setOnClickListener(v -> numeroPresionando("2"));
        //boton 3
        findViewById(R.id.btnThird).setOnClickListener(v -> numeroPresionando("3"));

        //Fila 4 botones
        //boton 0
        findViewById(R.id.btnCero).setOnClickListener(v -> numeroPresionando("0"));

        //Validacion de entrada
        findViewById(R.id.btnPoint).setOnClickListener(v -> {
            // 1. Verificamos si ya existe un punto
            if (!concatenar.contains(".")) { //Si la cadena de texto NO contiene un punto, entonces déjalo pasar
                // 2. Si está vacío, ponemos "0." para que no empiece con un punto solo
                if (concatenar.isEmpty()) {
                    concatenar = "0.";
                } else {
                    // 3. Si ya tiene números, simplemente le pegamos el punto al final
                    concatenar += ".";
                }
                // 4. Actualizamos el texto en la pantalla del celular
                txvResultado.setText(concatenar);
            }
        });

        // Botón de Punto
        findViewById(R.id.btnPoint).setOnClickListener(v -> {
            if (!concatenar.contains(".")) {
                if (concatenar.isEmpty()) {
                    concatenar = "0.";
                } else {
                    concatenar += ".";
                }
                // Actualizamos la pantalla con el historial + el punto
                txvResultado.setText(expresionVisual + concatenar);
            }
        });


        // Botón AC
        findViewById(R.id.btnAc).setOnClickListener(v -> {
            concatenar = "";
            operacionPendiente = null;
            esPrimerNumero = true;
            expresionVisual = "";
            builder = new CalculadoraBuilder();
            txvOperation.setText("");
            txvResultado.setText("0");
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Botones de operacion
        findViewById(R.id.btnPlus).setOnClickListener(v -> operacionPresionada(OperationType.SUMA));
        findViewById(R.id.btnMinus).setOnClickListener(v -> operacionPresionada(OperationType.RESTA));
        findViewById(R.id.btnMultiple).setOnClickListener(v -> operacionPresionada(OperationType.MULTIPLICACION));
        findViewById(R.id.btnDivide).setOnClickListener(v -> operacionPresionada(OperationType.DIVISON));

        //Boton igual
        findViewById(R.id.btnEqual).setOnClickListener(v -> calcularResultado());

    }

    private void numeroPresionando(String numero) {
        if (concatenar.equals("0")) {
            concatenar = numero;
        } else {
            concatenar += numero;
        }
        // La pantalla siempre muestra el historial + el número que estás tecleando
        txvResultado.setText(expresionVisual + concatenar);
    }

    private void operacionPresionada(OperationType type) {
        if (!concatenar.isEmpty()) {
            double numeroActual = Double.parseDouble(concatenar);

            if (esPrimerNumero) {
                // El primer número siempre entra limpio con una suma de 0 (ej: 5 + 0 = 5)
                builder.addOperationInitial(new Operacion(numeroActual, 0.0, OperationType.SUMA));
                esPrimerNumero = false;
            } else {
                // Los siguientes números entran con el operador que estaba pendiente
                builder.addOperation(new OperacionMin(operacionPendiente, numeroActual));
            }

            operacionPendiente = type; // Guardamos el nuevo operador para el futuro
            // Actualizamos el historial visual (ej: pasa de "5" a "5 + ")
            expresionVisual = expresionVisual + concatenar + obtenerSimbolo(type);
            txvResultado.setText(expresionVisual);

            concatenar = ""; // Limpiamos para recibir el siguiente número
        }
    }

    //Metodo auxiliar para que el texto de arriba se vea bonito
    private String obtenerSimbolo(OperationType type){
        switch (type){
            case SUMA: return " + ";
            case RESTA: return " - ";
            case MULTIPLICACION: return " x ";
            case DIVISON: return " / ";
            default: return "";
        }

    }

    private void calcularResultado() {
        if (!concatenar.isEmpty() && operacionPendiente != null) {
            double ultimoNum = Double.parseDouble(concatenar);

            builder.addOperation(new OperacionMin(operacionPendiente, ultimoNum));

            expresionVisual = expresionVisual + concatenar;
            txvOperation.setText(expresionVisual);

            CalculadoraInput input = builder.build();
            Double resultadoFinal = viewModel.resolverCadena(input);

            txvResultado.setText(formatearNumero(resultadoFinal));

            builder = new CalculadoraBuilder();
            concatenar = formatearNumero(resultadoFinal);
            expresionVisual = "";

            esPrimerNumero = true;
            operacionPendiente = null;
        }
    }

    private String formatearNumero(Double numero) {
        if (numero == null) return "";
        if (numero % 1 == 0) {
            return String.valueOf(numero.intValue());
        } else {
            return String.valueOf(numero);
        }
    }
}

