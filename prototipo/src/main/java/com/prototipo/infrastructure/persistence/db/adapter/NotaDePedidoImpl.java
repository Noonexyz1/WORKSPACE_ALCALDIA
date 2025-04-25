package com.prototipo.infrastructure.persistence.db.adapter;

import com.prototipo.application.port.out.persistence.NotaDePedidoAbstract;
import com.prototipo.domain.model.NotaDePedido;
import com.prototipo.infrastructure.persistence.db.repository.NotaDePedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Component
public class NotaDePedidoImpl implements NotaDePedidoAbstract {

    @Autowired
    private NotaDePedidoRepository notaDePedidoRepository;

    @Override
    public List<NotaDePedido> getNotaDePedidoAbstract(Long idSolicitud) {
        List<Object[]> notaDePedido = notaDePedidoRepository.getNotaDePedido(idSolicitud);
        List<NotaDePedido> dtosNota = notaDePedido.stream().map(x -> {
            // Convertir Long a Integer explícitamente
            String nombreDocumento = (String) x[0];
            Integer nroPaginas = ((Long) x[1]).intValue();
            Integer nroCopias = ((Long) x[2]).intValue();
            String tamano = (String) x[3];
            String color = (String) x[4];
            String anverRever = (String) x[5];
            Double precioRef = BigDecimal.valueOf((Double) x[6]).setScale(2, RoundingMode.HALF_UP).doubleValue();
            Double precioDocu = BigDecimal.valueOf((Double) x[7]).setScale(2, RoundingMode.HALF_UP).doubleValue();
            return new NotaDePedido(nombreDocumento, nroPaginas, nroCopias, tamano, color, anverRever, precioRef, precioDocu);
        }).toList();

        return dtosNota;
    }
}
