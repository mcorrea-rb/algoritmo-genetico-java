package com.example.demo.genetic.tools;

public class LogTime {

    public static void logged(long endTime, long startTime , int iterador) {
        long durationNano = (endTime - startTime);
        long durationMillis = durationNano / 1000000;      // Milisegundos
        long durationMicros = durationNano / 1000;         // Microsegundos
        long durationSeconds = durationNano / 1000000000;  // Segundos
        System.out.println("\nIteracion: "+ iterador +" CCon una duracion de:" );
        System.out.println(durationSeconds+" segundos");
        System.out.println(durationMillis + " milliseconds");
        System.out.println(durationMicros + " micros");
    }
}
