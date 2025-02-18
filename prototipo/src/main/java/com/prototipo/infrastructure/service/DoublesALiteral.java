package com.prototipo.infrastructure.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;

@Service
public class DoublesALiteral {

    @Autowired
    private NumeroALiteral numeroALiteral;

    private final String[] UNIDADES = {"", "uno", "dos", "tres", "cuatro", "cinco", "seis", "siete", "ocho", "nueve"};
    private final String[] DIEZ_A_DIECINUEVE = {"diez", "once", "doce", "trece", "catorce", "quince", "dieciséis", "diecisiete", "dieciocho", "diecinueve"};
    private final String[] DECENAS = {"", "diez", "veinte", "treinta", "cuarenta", "cincuenta", "sesenta", "setenta", "ochenta", "noventa"};
    private final String[] CENTENAS = {"", "ciento", "doscientos", "trescientos", "cuatrocientos", "quinientos", "seiscientos", "setecientos", "ochocientos", "novecientos"};

    public String convertir(BigDecimal numero) {
        BigInteger parteEntera = numero.toBigInteger();
        BigDecimal parteDecimal = numero.remainder(BigDecimal.ONE).multiply(BigDecimal.valueOf(100)).setScale(0, BigDecimal.ROUND_HALF_UP);
        //return convertirNumero(parteEntera) + " con " + (parteDecimal.equals(BigDecimal.ZERO) ? "00" : parteDecimal) + "/100";
        return convertirNumero(parteEntera) + " con " + numeroALiteral.convertirNumeroALiteral(parteDecimal.intValue());
    }

    private String convertirNumero(BigInteger numero) {
        if (numero.equals(BigInteger.ZERO)) return "cero";
        if (numero.compareTo(BigInteger.TEN) < 0) return UNIDADES[numero.intValue()];
        if (numero.compareTo(BigInteger.valueOf(20)) < 0) return DIEZ_A_DIECINUEVE[numero.intValue() - 10];
        if (numero.compareTo(BigInteger.valueOf(100)) < 0)
            return DECENAS[numero.intValue() / 10] + (numero.mod(BigInteger.TEN).intValue() != 0 ? " y " + convertirNumero(numero.mod(BigInteger.TEN)) : "");
        if (numero.compareTo(BigInteger.valueOf(200)) < 0)
            return "ciento" + (numero.mod(BigInteger.valueOf(100)).intValue() != 0 ? " " + convertirNumero(numero.mod(BigInteger.valueOf(100))) : "");
        if (numero.compareTo(BigInteger.valueOf(1000)) < 0)
            return CENTENAS[numero.intValue() / 100] + (numero.mod(BigInteger.valueOf(100)).intValue() != 0 ? " " + convertirNumero(numero.mod(BigInteger.valueOf(100))) : "");
        if (numero.compareTo(BigInteger.valueOf(1000000)) < 0)
            return convertirNumero(numero.divide(BigInteger.valueOf(1000))) + " mil" + (numero.mod(BigInteger.valueOf(1000)).intValue() != 0 ? " " + convertirNumero(numero.mod(BigInteger.valueOf(1000))) : "");
        if (numero.compareTo(BigInteger.valueOf(1000000000)) < 0)
            return convertirNumero(numero.divide(BigInteger.valueOf(1000000))) + " millones" + (numero.mod(BigInteger.valueOf(1000000)).intValue() != 0 ? " " + convertirNumero(numero.mod(BigInteger.valueOf(1000000))) : "");
        return "Número demasiado grande";
    }
}
