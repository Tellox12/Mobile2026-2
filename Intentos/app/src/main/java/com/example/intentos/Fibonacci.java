package com.example.intentos;

public class Fibonacci {

    public static long obtenerFibonacci(int n) {
        if (n <= 1) return 0;
        if (n == 2) return 1;

        long a = 0;
        long b = 1;

        for (int i = 2; i <= n; i++) {
            long suma = a + b;
            a = b;
            b = suma;
        }

        return b;

    }
}
