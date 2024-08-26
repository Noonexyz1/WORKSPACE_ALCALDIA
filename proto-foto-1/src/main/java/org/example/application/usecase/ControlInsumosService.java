package org.example.application.usecase;

import org.example.domain.model.Insumo;

public interface ControlInsumosService {
    void registrarInsumosPersist(Insumo insumo);
    void reportarInsumosBajos();
}
