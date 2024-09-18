package com.prototipo.infrastructure.rest.controller;

import com.prototipo.application.useCase.SolicitudService;
import com.prototipo.application.useCase.UnidadService;
import com.prototipo.domain.model.Solicitud;
import com.prototipo.domain.model.Unidad;
import com.prototipo.infrastructure.rest.request.SolicitudRequest;
import com.prototipo.infrastructure.rest.response.SolicitudResponse;
import com.prototipo.infrastructure.rest.response.UnidadResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/solicitante")
public class SolicitudController {

    @Autowired
    private SolicitudService solicitudService;
    @Autowired
    private UnidadService unidadService;

    //TODO ----hacer este modulo para registrar la solicitud
    @PostMapping(path = {"/solicitarFotocopiar"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void solicitarFotocopiar(@RequestBody SolicitudRequest solicitudRequest) {
        //Se esta recibiendo el modelo de request

        //Debo usar los mappeadores de mi Infraestrucutura
        Solicitud solicitud = Solicitud.builder()
                .nroDeCopias(solicitudRequest.getNroDeCopias())
                .tipoDeDocumento(solicitudRequest.getTipoDeDocumento())
                .nroDePaginas(solicitudRequest.getNroDePaginas())
                .listArvhicosPDF(solicitudRequest.getListArvhicosPDF())
                .build();

        solicitudService.solicitarFotocopiarService(solicitud);
    }


    @GetMapping(path = {"/verListaUnidades"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<UnidadResponse>> verListaDeUnidades() {
        List<Unidad> unidadList = unidadService.listaDeUnidadesService();

        List<UnidadResponse> unidadResponses = unidadList.stream().map(x ->
                UnidadResponse.builder()
                        .id(x.getId())
                        .nombre(x.getNombre())
                        .direccion(x.getDireccion())
                        .build()
        ).toList();

        return new ResponseEntity<>(unidadResponses, HttpStatus.OK);
    }


    @GetMapping(path = {"/verHistorialSolicitudes"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<SolicitudResponse>> verHistorialSolicitudes() {
        List<SolicitudResponse> list = new ArrayList<>();
        list.add(SolicitudResponse.builder()
                        .id(345345L)
                        .archivoPdf("asdfasdf2342asdfasdfasdf")
                .build());
        list.add(SolicitudResponse.builder()
                .id(345345L)
                .archivoPdf("asdfasdf2342asdfasdfasdf")
                .build());
        list.add(SolicitudResponse.builder()
                .id(345345L)
                .archivoPdf("asdfasdf2342asdfasdfasdf")
                .build());

        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
