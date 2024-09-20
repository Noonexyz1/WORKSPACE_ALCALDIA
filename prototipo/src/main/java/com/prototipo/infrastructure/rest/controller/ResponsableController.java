package com.prototipo.infrastructure.rest.controller;

import com.prototipo.application.useCase.SolicitudService;
import com.prototipo.infrastructure.persistence.db.entity.Solicitud;
import com.prototipo.infrastructure.rest.request.SolicitudRequest;
import com.prototipo.infrastructure.rest.request.SolicitudResponRequest;
import com.prototipo.infrastructure.rest.response.SolicitudResponResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/responsable")
public class ResponsableController {

    @Autowired
    private SolicitudService solicitudService;

    @PostMapping(path = {"/aprobarSolicitud"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void aprobarSolicitud(@RequestBody SolicitudResponRequest request) {

    }

    @GetMapping(path = {"/verSolicitudes"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<SolicitudResponResponse>> verListaSolicitudes() {
        List<SolicitudResponResponse> listSolicitud = solicitudService.getListaSolicitudesService()
                .stream()
                .map(x -> SolicitudResponResponse.builder()
                        .id(x.getId())
                        .nroDeCopias(x.getNroDeCopias())
                        .tipoDeDocumento(x.getTipoDeDocumento())
                        .nroDePaginas(x.getNroDePaginas())
                        .nombreUnidad(x.getUnidad().getNombre())
                        .estadoSolicitud(x.getEstadoSolicitud())
                        .build()
                )
                .toList();

        return new ResponseEntity<>(listSolicitud, HttpStatus.OK);
    }
}
