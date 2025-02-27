package com.prototipo.infrastructure.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import java.util.concurrent.atomic.AtomicInteger;

@Service
public class Observable {

    private final Flux<Integer> flux;
    private FluxSink<Integer> fluxSink;
    private final AtomicInteger lastValue = new AtomicInteger(0); // Mantiene el último valor enviado

    public Observable() {
        this.flux = Flux.<Integer>create(sink -> this.fluxSink = sink).share(); // Comparte el mismo stream con todos los suscriptores
    }

    public Flux<Integer> obtenerFlux() {
        return flux;
    }

    public void publicarValor(int valor) {
        if (fluxSink != null) {
            lastValue.set(valor);
            fluxSink.next(valor); // Envía el valor a todos los suscriptores
        }
    }
}
