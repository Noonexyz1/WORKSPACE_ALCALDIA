package com.prototipo.infrastructure.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class Observable {

    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private CompletableFuture<Boolean> completableFuture = CompletableFuture
            .supplyAsync(() -> false, executorService);

    public CompletableFuture<Boolean> getObservable() {
        return completableFuture;
    }

    public void publicar(Boolean senal) {
        completableFuture = CompletableFuture.supplyAsync(() -> senal, executorService);
    }
}
