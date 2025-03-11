package com.prototipo.application.util;

public class NumeroALiteral {

    private static final String[] UNIDADES = {"", "uno", "dos", "tres", "cuatro", "cinco", "seis", "siete", "ocho", "nueve"};
    private static final String[] DECENAS = {"", "diez", "veinte", "treinta", "cuarenta", "cincuenta", "sesenta", "setenta", "ochenta", "noventa"};
    private static final String[] ESPECIALES = {"diez", "once", "doce", "trece", "catorce", "quince", "dieciséis", "diecisiete", "dieciocho", "diecinueve"};
    private static final String[] CENTENAS = {"", "ciento", "doscientos", "trescientos", "cuatrocientos", "quinientos", "seiscientos", "setecientos", "ochocientos", "novecientos"};

    public static String convertirNumeroALiteral(int numero) {
        if (numero == 0) {
            return "cero";
        }

        String literal = "";

        // Millones
        int millones = numero / 1000000;
        if (millones > 0) {
            literal += convertirCentenas(millones) + " millón ";
        }

        // Miles
        int miles = (numero % 1000000) / 1000;
        if (miles > 0) {
            literal += convertirCentenas(miles) + " mil ";
        }

        // Centenas, decenas y unidades
        int resto = numero % 1000;
        if (resto > 0) {
            literal += convertirCentenas(resto);
        }

        return literal.trim();
    }

    private static String convertirCentenas(int numero) {
        String resultado = "";

        // Centenas
        int centenas = numero / 100;
        if (centenas > 0) {
            resultado += CENTENAS[centenas] + " ";
        }

        // Decenas y unidades
        int resto = numero % 100;
        if (resto > 0) {
            resultado += convertirDecenas(resto);
        }

        return resultado.trim();
    }

    private static String convertirDecenas(int numero) {
        String resultado = "";

        if (numero < 10) {
            resultado += UNIDADES[numero];
        } else if (numero >= 10 && numero < 20) {
            resultado += ESPECIALES[numero - 10];
        } else {
            int decenas = numero / 10;
            int unidades = numero % 10;

            resultado += DECENAS[decenas];
            if (unidades > 0) {
                resultado += " y " + UNIDADES[unidades];
            }
        }

        return resultado;
    }
}
