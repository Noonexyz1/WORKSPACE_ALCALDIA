package com.prototipo.infrastructure.rest.controller;

import com.prototipo.application.useCase.AprobacionService;
import com.prototipo.application.useCase.ResponsableService;
import com.prototipo.application.useCase.SolicitudService;
import com.prototipo.domain.model.*;
import com.prototipo.infrastructure.rest.request.*;
import com.prototipo.infrastructure.rest.response.*;
import com.prototipo.infrastructure.service.NotaPedidoServiceReport;
import com.prototipo.infrastructure.service.ReporteServiceReport;
import net.sf.jasperreports.engine.JRException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CompletableFuture;

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
    @Autowired
    private SolicitudService solicitudService;


    @PostMapping(
            path = {"/autorizarSolicitud"},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public void autorizarSolicitud(@RequestBody AprobacionSoliRequest aprobacionSoliRequest) {
        //Para persistir en la BD SECUENCIA BIEN HECHA
        Long idSolicitud = aprobacionSoliRequest.getIdSolicitud();
        Long idResponsable = aprobacionSoliRequest.getIdResponsable();

        Autorizacion autorizacion = Autorizacion.builder()
                .fkSolicitud(Solicitud.builder().id(idSolicitud).build())
                .fkUsuarioResponsable(UsuarioUnidad.builder().id(idResponsable).build())
                .build();
        solicitudService.guardarAutorizacion(autorizacion);
    }

    @PostMapping(
            path = {"/rechazarSolicitud"},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public void rechazarSolicitud(@RequestBody AprobacionSoliRequest aprobacionSoliRequest) {
        Long idSolicitud = aprobacionSoliRequest.getIdSolicitud();
        Long idResponsable = aprobacionSoliRequest.getIdResponsable();
        responsableService.rechazarSolicitudService(idSolicitud, idResponsable);
    }

    @PostMapping(
            path = {"/verSolicitudesPendientes"},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<SolicitudResponResponse>> verListaSolicitudesPendientes(
            @RequestBody PaginacionResponRequest pageParam) {

        Long idResponsable = pageParam.getIdUsuarioUnidad();
        Long page = pageParam.getPage();
        Long size = pageParam.getSize();
        String byColumName = pageParam.getByColumName();

        List<Solicitud> solicituds = aprobacionService
                .listaDeSolicitudesPendientesService(
                        idResponsable,
                        page,
                        size,
                        byColumName
                );

        List<SolicitudResponResponse> listSolicitud = solicituds
                .stream()
                .map(this::funcion)
                .toList();

        return new ResponseEntity<>(listSolicitud, HttpStatus.OK);
    }

    private SolicitudResponResponse funcion(Solicitud x){
        return SolicitudResponResponse.builder()
                .idSolicitud(x.getId())
                .cite(x.getCite())
                .fecha(x.getFecha())
                .nomCompleto(
                        x.getFkUsuarioSolicitante().getFkUsuario().getNombres() + " " +
                        x.getFkUsuarioSolicitante().getFkUsuario().getPaterno() + " " +
                        x.getFkUsuarioSolicitante().getFkUsuario().getMaterno()
                )
                .nomCargo(x.getFkUsuarioSolicitante().getFkCargo().getNombreCargo())
                .nombreUnidad(x.getFkUsuarioSolicitante().getFkUnidad().getNombre())
                .build();
    }

    @PostMapping(
            path = {"/verSolicitudesAprobadas"},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<SolicitudResponResponse>> verListaSolicitudesAprobadas(
            @RequestBody PaginacionResponRequest pageParam) {

        Long idResponsable = pageParam.getIdUsuarioUnidad();

        Long page = pageParam.getPage();
        Long size = pageParam.getSize();
        String byColumName = pageParam.getByColumName();

        List<Autorizacion> autorizacionList = aprobacionService
                .listaDeSolicitudesAutorizadasService(
                        idResponsable,
                        page,
                        size,
                        byColumName
                );

        List<SolicitudResponResponse> listSolicitud = autorizacionList
                .stream()
                .map(this::funcion)
                .toList();
        return new ResponseEntity<>(listSolicitud, HttpStatus.OK);
    }

    private SolicitudResponResponse funcion(Autorizacion x){
        return SolicitudResponResponse.builder()
                .idAutorizacion(x.getId())
                .idSolicitud(x.getFkSolicitud().getId())
                .cite(x.getFkSolicitud().getCite())
                .fecha(x.getFecha())
                .nomCompleto(
                        x.getFkSolicitud().getFkUsuarioSolicitante().getFkUsuario().getNombres() + " " +
                        x.getFkSolicitud().getFkUsuarioSolicitante().getFkUsuario().getPaterno() + " " +
                        x.getFkSolicitud().getFkUsuarioSolicitante().getFkUsuario().getMaterno()
                )
                .nomCargo(x.getFkSolicitud().getFkUsuarioSolicitante().getFkCargo().getNombreCargo())
                .nombreUnidad(x.getFkSolicitud().getFkUsuarioSolicitante().getFkUnidad().getNombre())
                .build();
    }

    @PostMapping(
            path = {"/verSolicitudesFinalizadas"},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<SolicitudResponResponse>> verSolicitudesFinalizadas(
            @RequestBody PaginacionResponRequest pageParam) {

        Long idResponsable = pageParam.getIdUsuarioUnidad();

        Long page = pageParam.getPage();
        Long size = pageParam.getSize();
        String byColumName = pageParam.getByColumName();

        List<Finalizacion> finalizacionList = aprobacionService
                .listaDeSolicitudesFinalizadasService(
                        idResponsable,
                        page,
                        size,
                        byColumName
                );

        List<SolicitudResponResponse> listSolicitud = finalizacionList
                .stream()
                .map(this::funcion)
                .toList();
        return new ResponseEntity<>(listSolicitud, HttpStatus.OK);
    }

    private SolicitudResponResponse funcion(Finalizacion x){
        return SolicitudResponResponse.builder()
                .idAutorizacion(x.getId())
                .idSolicitud(x.getFkAutorizacion().getId())
                .cite(x.getFkAutorizacion().getFkSolicitud().getCite())
                .fecha(x.getFecha())
                .nomCompleto(
                        x.getFkAutorizacion().getFkSolicitud().getFkUsuarioSolicitante().getFkUsuario().getNombres() + " " +
                                x.getFkAutorizacion().getFkSolicitud().getFkUsuarioSolicitante().getFkUsuario().getPaterno() + " " +
                                x.getFkAutorizacion().getFkSolicitud().getFkUsuarioSolicitante().getFkUsuario().getMaterno()
                )
                .nomCargo(x.getFkAutorizacion().getFkSolicitud().getFkUsuarioSolicitante().getFkCargo().getNombreCargo())
                .nombreUnidad(x.getFkAutorizacion().getFkSolicitud().getFkUsuarioSolicitante().getFkUnidad().getNombre())
                .build();
    }

    @PostMapping(
            path = {"/finalizarSolicitud"},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public void finalizarSolicitud(@RequestBody Long idAutorizacion) {
        Finalizacion finalizacion = Finalizacion.builder()
                .fkAutorizacion(
                        Autorizacion.builder().id(idAutorizacion).build()
                )
                .build();
        solicitudService.guardarFinalizacion(finalizacion);
    }

    @GetMapping(
            path = {"/verDetalleDeSolicitud/{idSolicitud}"},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<DetalleSolicitudExtendidoResponse> verDetalleDeSolicitud(
            @PathVariable Long idSolicitud) {

        List<Fotocopia> listDetSoli = solicitudService
                .findListDetalleSoliBySolicitudId(idSolicitud);

        List<DetalleSolicitudResponse> listDetSoliRespone = listDetSoli.stream()
                .map(this::funcion)
                .toList();

        DetalleSolicitudExtendidoResponse solicitudExtendido = DetalleSolicitudExtendidoResponse.builder()
                .idSolicitud(listDetSoli.getFirst().getFkSolicitud().getId())
                .cite(listDetSoli.getFirst().getFkSolicitud().getCite())
                .fecha(listDetSoli.getFirst().getFkSolicitud().getFecha())
                .descripcion(listDetSoli.getFirst().getFkSolicitud().getDescripcion())
                .nombreServicio(listDetSoli.getFirst().getFkSolicitud().getNombreServicio())
                .precioTotal(listDetSoli.getFirst().getFkSolicitud().getPrecioTotal())
                .detalleSolicitudResponses(listDetSoliRespone)
                .build();

        return new ResponseEntity<>(solicitudExtendido, HttpStatus.OK);
    }

    private DetalleSolicitudResponse funcion(Fotocopia detalleSolicitud){
        return DetalleSolicitudResponse.builder()
                .idSolicitud(detalleSolicitud.getFkSolicitud().getId())
                .idDetalleSolicitud(detalleSolicitud.getId())
                .nroCopias(detalleSolicitud.getNroCopias())
                .nombreDocumento(detalleSolicitud.getNombreDocumento())
                .nroPaginas(detalleSolicitud.getNroPaginas())
                .tamanoPagina(detalleSolicitud.getFkServicioFotocopia().getTamano())
                .anversoReverso(detalleSolicitud.getFkServicioFotocopia().getAnverRever())
                .colorFotocopia(detalleSolicitud.getFkServicioFotocopia().getColor())
                .precioRef(detalleSolicitud.getFkServicioFotocopia().getPrecioRef())
                .precioDocu(detalleSolicitud.getPrecioDocu())
                .build();
    }

    @GetMapping(
            path = {"/verAutorizacionSolicitud/{idSolicitud}"},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<AutorizacionResponse> verAutorizacionSolicitud(
            @PathVariable Long idSolicitud) {

        Autorizacion autorizacion = aprobacionService.findAutorizacionByIdSoliService(idSolicitud);
        AutorizacionResponse autorizacionResponse = null;

        if (autorizacion != null) {
            autorizacionResponse = AutorizacionResponse.builder()
                    .idAutorizacion(autorizacion.getId())
                    .idSolicitud(autorizacion.getFkSolicitud().getId())
                    .cite(autorizacion.getFkSolicitud().getCite())
                    .fecha(autorizacion.getFkSolicitud().getFecha())
                    .nomCompleto(
                            autorizacion.getFkSolicitud().getFkUsuarioSolicitante().getFkUsuario().getNombres() + " " +
                            autorizacion.getFkSolicitud().getFkUsuarioSolicitante().getFkUsuario().getPaterno() + " " +
                            autorizacion.getFkSolicitud().getFkUsuarioSolicitante().getFkUsuario().getMaterno()
                    )
                    .nomCargo(autorizacion.getFkSolicitud().getFkUsuarioSolicitante().getFkCargo().getNombreCargo())
                    .nombreUnidad(autorizacion.getFkSolicitud().getFkUsuarioSolicitante().getFkUnidad().getNombre())

                    .totalAutorizado(0L)
                    .totalCotizadoBs(BigDecimal.valueOf(0.0))
                    .fechaCotizado(autorizacion.getFecha())
                    .build();
        }

        return new ResponseEntity<>(autorizacionResponse, HttpStatus.OK);
    }



    //local:8081/responsable/exportNotaPedidoDPF/1/2/2
    @Async  // La anotación para indicar que este méttodo es asincrónico
    @GetMapping("/exportNotaPedidoDPF/{idSolicitud}/{idSolicitante}/{idResponsable}")
    public CompletableFuture<ResponseEntity<byte[]>> exportNotaPedidoDPF(
            @PathVariable Long idSolicitud,
            @PathVariable Long idSolicitante,
            @PathVariable Long idResponsable) throws IOException, JRException {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData(
                "notaPedidoPDF",
                "notaPedidoPDF.pdf"
        );

        // Llamar al servicio de manera sincrónica en este caso
        List<NotaDePedido> notaDePedidoList = responsableService
                .generarNotaDePedidoPDF(idSolicitud);

        // Procesar el archivo PDF y devolver el resultado asincrónicamente
        return CompletableFuture.supplyAsync(() -> {
            try {
                return ResponseEntity.ok()
                        .headers(headers)
                        .body(notaPedidoServiceReport.exportToPdf(notaDePedidoList));
            } catch (IOException | JRException e) {
                throw new RuntimeException("Error al generar el PDF", e);
            }
        });
    }

    //localhost:8081/solicitante/exportSolicitudDPF
    @Async
    @GetMapping("/exportReporteDPF/{idSolicitud}/{idSolicitante}/{idResponsable}")
    public CompletableFuture<ResponseEntity<byte[]>> exportReporteDPF(
            @PathVariable Long idSolicitud,
            @PathVariable Long idSolicitante,
            @PathVariable Long idResponsable ) throws IOException, JRException {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData(
                "reportePDF",
                "reportePDF.pdf"
        );

        List<Reporte> listReport = responsableService
                .generarReportePDF(idSolicitud);

        return CompletableFuture.supplyAsync(() -> {
            try {
                return ResponseEntity.ok()
                        .headers(headers)
                        .body(reporteServiceReport.exportToPdf(listReport));
            } catch (IOException | JRException e) {
                throw new RuntimeException("Error al generar el PDF", e);
            }
        });
    }
}
