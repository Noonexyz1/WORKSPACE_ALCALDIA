package com.prototipo.infrastructure.rest.controller;

import com.prototipo.application.useCase.AprobacionService;
import com.prototipo.application.useCase.ResponsableService;
import com.prototipo.domain.model.*;
import com.prototipo.infrastructure.rest.report.SolicitudReport;
import com.prototipo.infrastructure.rest.report.TablaSolicitudReport;
import com.prototipo.infrastructure.rest.request.AprobacionSoliRequest;
import com.prototipo.infrastructure.rest.request.PaginacionResponRequest;
import com.prototipo.infrastructure.rest.response.SolicitudResponResponse;
import com.prototipo.infrastructure.service.NotaPedidoServiceReport;
import com.prototipo.infrastructure.service.ReporteServiceReport;
import net.sf.jasperreports.engine.JRException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
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
    @Autowired
    private NotaPedidoServiceReport notaPedidoServiceReport;
    @Autowired
    private ReporteServiceReport reporteServiceReport;

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



    //localhost:8081/solicitante/exportSolicitudDPF
    @GetMapping("/exportNotaPedidoDPF/{idSolicitud}/{idSolicitante}/{idResponsable}")
    public ResponseEntity<byte[]> exportNotaPedidoDPF(
            @PathVariable Long idSolicitud,
            @PathVariable Long idSolicitante,
            @PathVariable Long idResponsable ) throws IOException, JRException {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData(
                "notaPedidoPDF",
                "notaPedidoPDF.pdf"
        );


        List<NotaDePedido> notaDePedidoList = responsableService.generarNotaDePedidoPDF(idSolicitud);

//        List<Reporte> listReport = responsableService.generarReportePDF(idSolicitud);
//        System.out.println(listReport);

        return ResponseEntity.ok()
                .headers(headers)
                .body(notaPedidoServiceReport.exportToPdf(notaDePedidoList));
    }

    //localhost:8081/solicitante/exportSolicitudDPF
    @GetMapping("/exportReporteDPF/{idSolicitud}/{idSolicitante}/{idResponsable}")
    public ResponseEntity<byte[]> exportReporteDPF(
            @PathVariable Long idSolicitud,
            @PathVariable Long idSolicitante,
            @PathVariable Long idResponsable ) throws IOException, JRException {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData(
                "reportePDF",
                "reportePDF.pdf"
        );

        List<Reporte> listReport = responsableService.generarReportePDF(idSolicitud);
        System.out.println(listReport);

        return ResponseEntity.ok()
                .headers(headers)
                .body(reporteServiceReport.exportToPdf(listReport));
    }
}
