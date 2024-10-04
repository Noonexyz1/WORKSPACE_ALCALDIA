package com.prototipo.infrastructure.rest.controller;

import com.prototipo.application.useCase.OperadorService;
import com.prototipo.domain.enums.EstadoByOperadorEnum;
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


    //TODO, verificar este metodo
    @PostMapping(path = {"/iniciarOperacion"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void iniciarSolicitudOperacion(@RequestBody OperacionSoliRequest opeSoliRequest) {
        operadorService.iniciarSolicitarOperacion(opeSoliRequest.getIdSolicitud(), opeSoliRequest.getIdOperador());
    }

    @PostMapping(path = {"/terminarOperacion"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void finalizarSolicitudOperacion(@RequestBody OperacionSoliRequest opeSoliRequest) {
        operadorService.terminarSolicitarOperacion(opeSoliRequest.getIdSolicitud(), opeSoliRequest.getIdOperador());
    }

    //Este operador tiene una forma de trabajar, y es por piso,
    //entonces se deberia mostrar las solicitudes correspondientes a su piso
    @GetMapping(path = {"/verSolicitudes/{idOperador}"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<SolicitudOperaResponse>> verSolicitudes(@PathVariable Long idOperador) {
        String nombre = EstadoByOperadorEnum.PENDIENTE.getNombre();

        List<OperacionDomain> list = operadorService.verSolicitudesDeOperador(idOperador, nombre);
        List<SolicitudOperaResponse> listSolicitud = list
                .stream()
                .map(this::funcion)
                .toList();
        return new ResponseEntity<>(listSolicitud, HttpStatus.OK);
    }

    @GetMapping(path = {"/verSolicitudesIniciadas/{idOperador}"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<SolicitudOperaResponse>> verSolicitudesIniciadas(@PathVariable Long idOperador) {
        String nombre = EstadoByOperadorEnum.INICIADO.getNombre();

        List<OperacionDomain> list = operadorService.verSolicitudesDeOperador(idOperador, nombre);
        List<SolicitudOperaResponse> listSolicitud = list
                .stream()
                .map(this::funcion)
                .toList();
        return new ResponseEntity<>(listSolicitud, HttpStatus.OK);
    }

    @GetMapping(path = {"/verSolicitudesCompletas/{idOperador}"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<SolicitudOperaResponse>> verSolicitudesCompletadas(@PathVariable Long idOperador) {
        String nombre = EstadoByOperadorEnum.COMPLETADO.getNombre();

        List<OperacionDomain> list = operadorService.verSolicitudesDeOperador(idOperador, nombre);
        List<SolicitudOperaResponse> listSolicitud = list
                .stream()
                .map(this::funcion)
                .toList();
        return new ResponseEntity<>(listSolicitud, HttpStatus.OK);
    }

    private SolicitudOperaResponse funcion(OperacionDomain x){
        return SolicitudOperaResponse.builder()
                //No el ID de la solicitud, sino el id del registro Aprobacion
                .id(x.getId())
                .idSolicitud(x.getFkSolicitud().getId())
                .nroDeCopias(x.getFkSolicitud().getNroDeCopias())
                .tipoDeDocumento(x.getFkSolicitud().getTipoDeDocumento())
                .nroDePaginas(x.getFkSolicitud().getNroDePaginas())
                .nombreUnidad(x.getFkSolicitud().getFkUnidad().getNombre())
                .estadoByOperador(x.getEstadoByOperador())
                .build();
    }
}
