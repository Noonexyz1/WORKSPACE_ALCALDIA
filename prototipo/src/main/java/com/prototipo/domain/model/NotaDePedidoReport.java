package com.prototipo.domain.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotaDePedidoReport {
    private Long idSolicitud;
    private String fecha;
    private String nombreServicio;
    private Double precioTotal;
    private List<NotaDePedido> listNotaPedido;
}
