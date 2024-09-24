package com.prototipo.infrastructure.rest.controller;

import com.prototipo.application.useCase.AprobacionService;
import com.prototipo.application.useCase.ResponsableService;
import com.prototipo.domain.model.AprobacionDomain;
import com.prototipo.infrastructure.rest.request.AprobacionSoliRequest;
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
    private AprobacionService aprobacionService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ResponsableService responsableService;

    @PostMapping(path = {"/aprobarSolicitud"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void aprobarSolicitud(@RequestBody AprobacionSoliRequest aprobacionSoliRequest) {
        Long idSolicitud = aprobacionSoliRequest.getIdSolicitud();
        Long idResponsable = aprobacionSoliRequest.getIdResponsable();
        responsableService.aprobarSolicitudService(idSolicitud, idResponsable);
    }

    @PostMapping(path = {"/rechazarSolicitud"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void rechazarSolicitud(@RequestBody AprobacionSoliRequest aprobacionSoliRequest) {
        Long idSolicitud = aprobacionSoliRequest.getIdSolicitud();
        Long idResponsable = aprobacionSoliRequest.getIdResponsable();
        responsableService.rechazarSolicitudService(idSolicitud, idResponsable);
    }

    @GetMapping(path = {"/verSolicitudes"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<SolicitudResponResponse>> verListaSolicitudes() {
        List<AprobacionDomain> aprobacionDomains = aprobacionService.listaDeSolicitudesService();
        List<SolicitudResponResponse> listSolicitud = aprobacionDomains
                .stream()
                .map(x -> SolicitudResponResponse.builder()
                        .id(x.getFkSolicitud().getId())
                        .nroDeCopias(x.getFkSolicitud().getNroDeCopias())
                        .tipoDeDocumento(x.getFkSolicitud().getTipoDeDocumento())
                        .nroDePaginas(x.getFkSolicitud().getNroDePaginas())
                        .estadoByResponsable(x.getEstadoByResponsable())
                        .nombreUnidad(x.getFkSolicitud().getFkUnidad().getNombre())
                        .build()
                )
                .toList();
        return new ResponseEntity<>(listSolicitud, HttpStatus.OK);
    }
}
