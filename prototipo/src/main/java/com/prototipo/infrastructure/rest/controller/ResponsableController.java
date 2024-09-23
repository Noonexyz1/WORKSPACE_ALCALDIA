package com.prototipo.infrastructure.rest.controller;

import com.prototipo.application.useCase.ResponsableService;
import com.prototipo.application.useCase.SolicitudService;
import com.prototipo.infrastructure.rest.response.SolicitudResponResponse;
import org.modelmapper.ModelMapper;
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
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ResponsableService responsableService;

    @GetMapping(path = {"/aprobarSolicitud/{idSolicitud}"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void aprobarSolicitud(@PathVariable Long idSolicitud) {
        responsableService.aprobarSolicitudService(idSolicitud);
    }

    @GetMapping(path = {"/rechazarSolicitud/{idSolicitud}"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void rechazarSolicitud(@PathVariable Long idSolicitud) {
        responsableService.rechazarSolicitudService(idSolicitud);
    }

    @GetMapping(path = {"/verSolicitudes"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<SolicitudResponResponse>> verListaSolicitudes() {
        List<SolicitudResponResponse> listSolicitud = solicitudService.getListaSolicitudesService()
                .stream()
                .map(x -> modelMapper.map(x, SolicitudResponResponse.class))
                .toList();
        return new ResponseEntity<>(listSolicitud, HttpStatus.OK);
    }
}
