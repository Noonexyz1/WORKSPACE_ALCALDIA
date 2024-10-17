package com.prototipo.infrastructure.rest.controller;

import com.prototipo.application.useCase.OperadorService;
import com.prototipo.domain.enums.EstadoByOperadorEnum;
import com.prototipo.domain.model.ArchivoPdf;
import com.prototipo.domain.model.Operacion;
import com.prototipo.infrastructure.rest.request.OperacionSoliRequest;
import com.prototipo.infrastructure.rest.request.PaginacionOpeRequest;
import com.prototipo.infrastructure.rest.response.ArchivoPdfResponse;
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


    //TODO, cuando inicie la operacion, el archivo o los archivos pdf se deben descargar
    @PostMapping(path = {"/iniciarOperacion"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<ArchivoPdfResponse>> iniciarSolicitudOperacion(@RequestBody OperacionSoliRequest opeSoliRequest) {
        Long idOperacion = opeSoliRequest.getIdOperacion();
        Long idOperador = opeSoliRequest.getIdOperador();
        List<ArchivoPdf> listArchivos = operadorService.iniciarSolicitudOperacion(idOperacion, idOperador);
        List<ArchivoPdfResponse> listArchivosResp = listArchivos.stream()
                .map(x -> modelMapper.map(x, ArchivoPdfResponse.class))
                .toList();
        return new ResponseEntity<>(listArchivosResp, HttpStatus.OK);
    }

    @PostMapping(path = {"/terminarOperacion"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void finalizarSolicitudOperacion(@RequestBody OperacionSoliRequest opeSoliRequest) {
        Long idOperacion = opeSoliRequest.getIdOperacion();
        operadorService.terminarSolicitudOperacion(idOperacion);
    }

    //Este operador tiene una forma de trabajar, y es por piso,
    //entonces se deberia mostrar las solicitudes correspondientes a su piso
    @GetMapping(path = {"/verSolicitudesPendientes"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<SolicitudOperaResponse>> verSolicitudes(@RequestBody PaginacionOpeRequest pageParam) {
        String estado = EstadoByOperadorEnum.PENDIENTE.getNombre();
        Long idOperador = pageParam.getIdUsuario();
        Long page = pageParam.getPage();
        Long size = pageParam.getSize();
        String byColumName = pageParam.getByColumName();

        List<Operacion> list = operadorService
                .verSolicitudesDeOperadorPendientes(idOperador, page, size);
        List<SolicitudOperaResponse> listSolicitud = list
                .stream()
                .map(this::funcion)
                .toList();
        return new ResponseEntity<>(listSolicitud, HttpStatus.OK);
    }

    @GetMapping(path = {"/verSolicitudesIniciadas"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<SolicitudOperaResponse>> verSolicitudesIniciadas(@RequestBody PaginacionOpeRequest pageParam) {
        String estado = EstadoByOperadorEnum.INICIADO.getNombre();
        Long idOperador = pageParam.getIdUsuario();
        Long page = pageParam.getPage();
        Long size = pageParam.getSize();
        String byColumName = pageParam.getByColumName();

        List<Operacion> list = operadorService.verSolicitudesDeOperadorIniciadas(idOperador, page, size);
        List<SolicitudOperaResponse> listSolicitud = list
                .stream()
                .map(this::funcion)
                .toList();
        return new ResponseEntity<>(listSolicitud, HttpStatus.OK);
    }

    @GetMapping(path = {"/verSolicitudesCompletas"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<SolicitudOperaResponse>> verSolicitudesCompletadas(@RequestBody PaginacionOpeRequest pageParam) {
        String estado = EstadoByOperadorEnum.COMPLETADO.getNombre();
        Long idOperador = pageParam.getIdUsuario();
        Long page = pageParam.getPage();
        Long size = pageParam.getSize();
        String byColumName = pageParam.getByColumName();

        List<Operacion> list = operadorService
                .verSolicitudesDeOperadorCompletadas(idOperador, page, size);
        List<SolicitudOperaResponse> listSolicitud = list
                .stream()
                .map(this::funcion)
                .toList();
        return new ResponseEntity<>(listSolicitud, HttpStatus.OK);
    }

    private SolicitudOperaResponse funcion(Operacion x){
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
