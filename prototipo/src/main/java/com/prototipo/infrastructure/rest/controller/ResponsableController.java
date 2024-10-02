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

    @GetMapping(path = {"/verSolicitudes/{idResponsable}"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<SolicitudResponResponse>> verListaSolicitudes(@PathVariable Long idResponsable) {
        //Buscar por id de responsable todos aquellas solicitudes que le pertencen a su unidades
        List<AprobacionDomain> aprobacionDomains = aprobacionService.listaDeSolicitudesService(idResponsable);

        //Las solicitudes solamente pueden tener dos estados, Pendiente - Aprobado o
        //Pendiente - Rechazado, del cual solamente hay que mostrar el ultimo estado.
        //Filtramos para mostrar todos los que el el fk_responsable_id es null
        //Que muestre unicamente las solicitudes que tiene por aprobar el Responsable de unidad o area
        List<SolicitudResponResponse> listSolicitud = aprobacionDomains
                .stream()
                .filter(x -> x.getFkResponsable() == null)
                .map(this::funcion)
                .toList();
        return new ResponseEntity<>(listSolicitud, HttpStatus.OK);
    }

    private SolicitudResponResponse funcion(AprobacionDomain x){
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
