package com.prototipo.infrastructure.rest.controller;

import com.prototipo.application.useCase.AprobacionService;
import com.prototipo.application.useCase.ResponsableService;
import com.prototipo.domain.model.Aprobacion;
import com.prototipo.infrastructure.rest.request.AprobacionSoliRequest;
import com.prototipo.infrastructure.rest.request.PaginacionResponRequest;
import com.prototipo.infrastructure.rest.response.SolicitudResponResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 86400)
@RestController
@RequestMapping(path = "/responsable")
public class ResponsableController {

    @Autowired
    private AprobacionService aprobacionService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ResponsableService responsableService;

    //TODO, verificar este metodo
    @PostMapping(path = {"/aprobarSolicitud"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void aprobarSolicitud(@RequestBody AprobacionSoliRequest aprobacionSoliRequest) {
        Long idAprobacion = aprobacionSoliRequest.getIdAprobacion();
        Long idResponsable = aprobacionSoliRequest.getIdResponsable();
        responsableService.aprobarSolicitudService(idAprobacion, idResponsable);
    }

    @PostMapping(path = {"/rechazarSolicitud"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void rechazarSolicitud(@RequestBody AprobacionSoliRequest aprobacionSoliRequest) {
        Long idAprobacion = aprobacionSoliRequest.getIdAprobacion();
        Long idResponsable = aprobacionSoliRequest.getIdResponsable();
        responsableService.rechazarSolicitudService(idAprobacion, idResponsable);
    }

    @PostMapping(path = {"/verSolicitudesPendientes"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<SolicitudResponResponse>> verListaSolicitudesPendientes(@RequestBody PaginacionResponRequest pageParam) {
        Long idResponsable = pageParam.getIdUsuario();

        Long page = pageParam.getPage();
        Long size = pageParam.getSize();
        String byColumName = pageParam.getByColumName();

        List<Aprobacion> aprobacionDomains = aprobacionService
                .listaDeSolicitudesPendientesService(idResponsable, page, size, byColumName);
        List<SolicitudResponResponse> listSolicitud = aprobacionDomains
                .stream()
                .filter(x -> x.getFkResponsable() == null)
                .map(this::funcion)
                .toList();
        return new ResponseEntity<>(listSolicitud, HttpStatus.OK);
    }

    @PostMapping(path = {"/verSolicitudesAprobadas"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<SolicitudResponResponse>> verListaSolicitudesAprobadas(@RequestBody PaginacionResponRequest pageParam) {
        Long idResponsable = pageParam.getIdUsuario();

        Long page = pageParam.getPage();
        Long size = pageParam.getSize();
        String byColumName = pageParam.getByColumName();

        List<Aprobacion> aprobacionDomains = aprobacionService
                .listaDeSolicitudesAprobadasService(idResponsable, page, size, byColumName);
        List<SolicitudResponResponse> listSolicitud = aprobacionDomains
                .stream()
                .map(this::funcion)
                .toList();
        return new ResponseEntity<>(listSolicitud, HttpStatus.OK);
    }

    @PostMapping(path = {"/verSolicitudesRechazadas"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<SolicitudResponResponse>> verListaSolicitudesRechazadas(@RequestBody PaginacionResponRequest pageParam) {
        Long idResponsable = pageParam.getIdUsuario();

        Long page = pageParam.getPage();
        Long size = pageParam.getSize();
        String byColumName = pageParam.getByColumName();

        List<Aprobacion> aprobacionDomains = aprobacionService
                .listaDeSolicitudesRechazadasService(idResponsable, page, size, byColumName);
        List<SolicitudResponResponse> listSolicitud = aprobacionDomains
                .stream()
                .map(this::funcion)
                .toList();
        return new ResponseEntity<>(listSolicitud, HttpStatus.OK);
    }

    private SolicitudResponResponse funcion(Aprobacion x){
        return SolicitudResponResponse.builder()
                //No el ID de la solicitud, sino el id del registro Aprobacion
                .id(x.getId())
                .idSolicitud(x.getFkSolicitud().getId())
                .nroDeCopias(x.getFkSolicitud().getNroDeCopias())
                .tipoDeDocumento(x.getFkSolicitud().getTipoDeDocumento())
                .nroDePaginas(x.getFkSolicitud().getNroDePaginas())
                .estadoByResponsable(x.getEstadoByResponsable())
                .nombreUnidad(x.getFkSolicitud().getFkUnidad().getNombre())
                .build();
    }
}
