package com.prototipo.application.port.out.persistence;

import com.prototipo.domain.model.NotaDePedido;

import java.util.List;

public interface NotaDePedidoAbstract {
    List<NotaDePedido> getNotaDePedidoAbstract(Long idSolicitud);
}
