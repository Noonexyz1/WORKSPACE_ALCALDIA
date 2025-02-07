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
import java.time.LocalDate;
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



    @PostMapping(path = {"/aprobarSolicitud"},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public void aprobarSolicitud(@RequestBody AprobacionSoliRequest aprobacionSoliRequest) {
        Long idAprobacion = aprobacionSoliRequest.getIdAprobacion();
        Long idResponsable = aprobacionSoliRequest.getIdResponsable();
        responsableService.aprobarSolicitudService(idAprobacion, idResponsable);
    }

    @PostMapping(path = {"/rechazarSolicitud"},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public void rechazarSolicitud(@RequestBody AprobacionSoliRequest aprobacionSoliRequest) {
        Long idAprobacion = aprobacionSoliRequest.getIdAprobacion();
        Long idResponsable = aprobacionSoliRequest.getIdResponsable();
        responsableService.rechazarSolicitudService(idAprobacion, idResponsable);
    }



    @PostMapping(path = {"/verSolicitudesPendientes"},
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

    @PostMapping(path = {"/verSolicitudesAprobadas"},
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

    @PostMapping(path = {"/verSolicitudesFinalizadas"},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<FinalizacionResponse>> verSolicitudesFinalizadas(
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

        List<FinalizacionResponse> listSolicitud = finalizacionList
                .stream()
                .map(this::funcion)
                .toList();
        return new ResponseEntity<>(listSolicitud, HttpStatus.OK);
    }

    private FinalizacionResponse funcion(Finalizacion finalizacion){
        return FinalizacionResponse.builder()
                .idSoliAutorizada(finalizacion.getFkAutorizacion().getFkSolicitud().getId())
                .nombreCompleto(finalizacion.getFkAutorizacion().getFkSolicitud().getDescripcion())
                .nombreUnidad(finalizacion.getFkAutorizacion().getFkSolicitud().getFkUsuarioSolicitante().getFkUnidad().getNombre())
                .nombreCargo(finalizacion.getFkAutorizacion().getFkSolicitud().getFkUsuarioSolicitante().getFkCargo().getNombreCargo())
                .descripcion(finalizacion.getFkAutorizacion().getFkSolicitud().getDescripcion())
                .totalAutorizado(finalizacion.getFkAutorizacion().getTotalAutorizado())
                .totalCotizadoBs(finalizacion.getFkAutorizacion().getTotalCotizadoBs())
                .totalEjecutado(finalizacion.getTotalEjecutado())
                .totalEjecutadoBs(finalizacion.getTotalEjecutadoBs())
                .fecha(finalizacion.getFecha())
                .build();
    }



    /*ver detalle de una solicitud especifica - HECHO
    Una vez viendo el detalle, el responsable lo puede cotizar y registrarlo en la tabla Cotizacion,
    dandole un precio unitario a cada detalle una vez cotizado, se registra la solicitud en la
    tabla autorizacion.
    Una vez terminado la operacion por parte de un externo, entonces se
    registra como finalizado en la tabla Finalizacion*/

    @PostMapping(path = {"/finalizarSolicitud"},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public void finalizarSolicitud(@RequestBody FinalizacionRequest request) {
        Autorizacion autorizacion = solicitudService
                .buscarAutorizacionById(request.getIdAutorizacion());
        autorizacion.setFinaliFlag(1L);

        aprobacionService.guardarAutorizacionService(autorizacion);

        Finalizacion finalizacion = Finalizacion.builder()
                .fecha(LocalDate.now().toString())
                .totalEjecutado(request.getTotalEjecutado())
                .totalEjecutadoBs(request.getTotalEjecutadoBs())
                .fkAutorizacion(autorizacion)
                .build();

        solicitudService.guardarFinalizacion(finalizacion);
    }

    @PostMapping(path = {"/cotizarAutorizarSolicitud"},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public void cotizarAutorizarSolicitud(
            @RequestBody List<DetalleSolicitudCotizRequest> listSoliCoti) {

        //Esto sirve para cotizar el detalle de una solicitud, por cada uno de ellos
        listSoliCoti.forEach(x -> {

            DetalleSolicitud detalleSolicitud = DetalleSolicitud.builder()
                    .id(x.getIdDetalleSolicitud())
                    .build();

            BigDecimal total = x.getPrecioUnit()
                    .multiply(BigDecimal.valueOf(x.getNroCopias()))
                    .multiply(BigDecimal.valueOf(x.getNroPaginas()));

            Cotizacion cotizacion = Cotizacion.builder()
                    .precioUnitario(x.getPrecioUnit())
                    .precioTotal(total)
                    .fkDetalleSolicitud(detalleSolicitud)
                    .build();

            responsableService.guardarCotizacion(cotizacion);
        });


        Long idUsuarioUnidadResponsable = listSoliCoti.getFirst().getIdUsuarioUnidad();
        UsuarioUnidad usuarioUnidadResponsable = UsuarioUnidad.builder()
                .id(idUsuarioUnidadResponsable)
                .build();


        Long idSolicitud = listSoliCoti.getFirst().getIdSolicitud();
        Solicitud solicitud = Solicitud.builder()
                .id(idSolicitud)
                .autoriFlag(1L)
                .build();

        //EStas son OPERACIONES para la sumatoria para guardar el Autorizacion
        List<DetalleSolicitud> list = solicitudService
                .findListDetalleSoliBySolicitudId(idSolicitud);

        Long totalAutorizado = 0L;
        for (DetalleSolicitud y: list) {
            totalAutorizado = totalAutorizado + y.getNroCopias();
        }

        //EStas son OPERACIONES para el producto para guardar el Autorizacion
        BigDecimal totalAutorizadoBs = list.stream()
                .map(detalle -> {
                    BigDecimal paginas = detalle.getNroPaginas() != null ?
                            BigDecimal.valueOf(detalle.getNroPaginas()) : BigDecimal.ZERO;
                    BigDecimal copias = detalle.getNroCopias() != null ?
                            BigDecimal.valueOf(detalle.getNroCopias()) : BigDecimal.ZERO;

                    return paginas.multiply(copias);
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        //Para persistir en la BD
        Autorizacion autorizacion = Autorizacion.builder()
                .fecha(LocalDate.now().toString())
                .totalAutorizado(totalAutorizado)
                .totalCotizadoBs(totalAutorizadoBs)
                .fkUsuarioResponsable(usuarioUnidadResponsable)
                .fkSolicitud(solicitud)
                .finaliFlag(0L)
                .build();

        solicitudService.guardarAutorizacion(autorizacion);
    }

    @GetMapping(path = {"/verDetalleDeSolicitud/{idSolicitud}"},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<DetalleSolicitudExtendidoResponse> verDetalleDeSolicitud(
            @PathVariable Long idSolicitud) {

        List<DetalleSolicitud> listDetSoli = solicitudService
                .findListDetalleSoliBySolicitudId(idSolicitud);

        List<DetalleSolicitudResponse> listDetSoliRespone = listDetSoli.stream()
                .map(this::funcion)
                .toList();

        DetalleSolicitudExtendidoResponse solicitudExtendido = DetalleSolicitudExtendidoResponse.builder()
                .idSolicitud(listDetSoli.getFirst().getFkSolicitud().getId())
                .cite(listDetSoli.getFirst().getFkSolicitud().getCite())
                .fecha(listDetSoli.getFirst().getFkSolicitud().getFecha())
                .descripcion(listDetSoli.getFirst().getFkSolicitud().getDescripcion())
                .detalleSolicitudResponses(listDetSoliRespone)
                .build();

        return new ResponseEntity<>(solicitudExtendido, HttpStatus.OK);
    }

    private DetalleSolicitudResponse funcion(DetalleSolicitud detalleSolicitud){
        return DetalleSolicitudResponse.builder()
                .idSolicitud(detalleSolicitud.getFkSolicitud().getId())
                .idDetalleSolicitud(detalleSolicitud.getId())
                .nroCopias(detalleSolicitud.getNroCopias())
                .nombreDocumento(detalleSolicitud.getNombreDocumento())
                .nroPaginas(detalleSolicitud.getNroPaginas())
                .tamanoPagina(detalleSolicitud.getTamanoPagina())
                .anversoReverso(detalleSolicitud.getAnversoReverso())
                .colorFotocopia(detalleSolicitud.getColorFotocopia())
                .build();
    }

    @GetMapping(path = {"/verAutorizacionSolicitud/{idAutorizacion}"},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<AutorizacionResponse> verAutorizacionSolicitud(
            @PathVariable Long idAutorizacion) {

        Autorizacion autorizacion = aprobacionService.findAutorizacionById(idAutorizacion);
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

                    .totalAutorizado(autorizacion.getTotalAutorizado())
                    .totalCotizadoBs(autorizacion.getTotalCotizadoBs())
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
