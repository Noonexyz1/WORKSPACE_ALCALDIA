package com.prototipo.application.useCase;

import com.prototipo.domain.model.NotaDePedido;

import java.util.List;

public interface ReportesPDFService {
    List<NotaDePedido> getNotaDePedidoService(Long idSolicitud);
}
