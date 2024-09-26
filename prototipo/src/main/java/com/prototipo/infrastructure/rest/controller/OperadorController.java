package com.prototipo.infrastructure.rest.controller;

import com.prototipo.application.useCase.OperadorService;
import com.prototipo.domain.model.OperacionDomain;
import com.prototipo.infrastructure.rest.request.OperacionSoliRequest;
import com.prototipo.infrastructure.rest.response.SolicitudOperaResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/operador")
public class OperadorController {

    @Autowired
    private OperadorService operadorService;
    @Autowired
    private ModelMapper modelMapper;


    @PostMapping(path = {"/iniciarOperacion"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void iniciarSolicitudOperacion(@RequestBody OperacionSoliRequest opeSoliRequest) {
        operadorService.iniciarSolicitarOperacion(opeSoliRequest.getIdSolicitud(), opeSoliRequest.getIdOperador());
    }

    @PostMapping(path = {"/terminarOperacion"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void finalizarSolicitudOperacion(@RequestBody OperacionSoliRequest opeSoliRequest) {
        operadorService.terminarSolicitarOperacion(opeSoliRequest.getIdSolicitud(), opeSoliRequest.getIdOperador());
    }

    //TODO verificar los datos que se trae de la base de datos
    @GetMapping(path = {"/verSolicitudes"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<SolicitudOperaResponse>> verSolicitudes() {
        List<OperacionDomain> list = operadorService.verSolicitudesDeOperador();

        List<SolicitudOperaResponse> listSolicitud = list
                .stream()
                .filter(x -> x.getFkOperador() == null)
                .map(this::funcion)
                .toList();

        return new ResponseEntity<>(listSolicitud, HttpStatus.OK);
    }

    private SolicitudOperaResponse funcion(OperacionDomain x){
        return SolicitudOperaResponse.builder()
                //No el ID de la solicitud, sino el id del registro Aprobacion
                .id(x.getId())
                .idSolicitud(x.getFkAprobacion().getFkSolicitud().getId())
                .nroDeCopias(x.getFkAprobacion().getFkSolicitud().getNroDeCopias())
                .tipoDeDocumento(x.getFkAprobacion().getFkSolicitud().getTipoDeDocumento())
                .nroDePaginas(x.getFkAprobacion().getFkSolicitud().getNroDePaginas())
                .nombreUnidad(x.getFkAprobacion().getFkSolicitud().getFkUnidad().getNombre())
                .estadoByResponsable(x.getFkAprobacion().getEstadoByResponsable())
                .estadoByOperador(x.getEstadoByOperador())
                .build();
    }
}
