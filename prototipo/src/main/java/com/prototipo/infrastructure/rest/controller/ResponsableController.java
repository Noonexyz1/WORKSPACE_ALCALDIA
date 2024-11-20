package com.prototipo.infrastructure.rest.controller;

import com.prototipo.application.useCase.AprobacionService;
import com.prototipo.application.useCase.ResponsableService;
import com.prototipo.application.useCase.SolicitudService;
import com.prototipo.domain.model.*;
import com.prototipo.infrastructure.rest.request.*;
import com.prototipo.infrastructure.rest.response.DetalleSolicitudExtendidoResponse;
import com.prototipo.infrastructure.rest.response.DetalleSolicitudResponse;
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
import java.math.BigDecimal;
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
    @Autowired
    private SolicitudService solicitudService;

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
    public ResponseEntity<List<SolicitudResponResponse>> verListaSolicitudesPendientes(
            @RequestBody PaginacionResponRequest pageParam) {

        Long idResponsable = pageParam.getIdUsuarioUnidad();
        Long page = pageParam.getPage();
        Long size = pageParam.getSize();
        String byColumName = pageParam.getByColumName();

        List<Solicitud> solicituds = aprobacionService
                .listaDeSolicitudesPendientesService(idResponsable, page, size, byColumName);

        List<SolicitudResponResponse> listSolicitud = solicituds
                .stream()
                .map(this::funcion)
                .toList();

        return new ResponseEntity<>(listSolicitud, HttpStatus.OK);
    }

    private SolicitudResponResponse funcion(Solicitud x){
        return SolicitudResponResponse.builder()
                .id(x.getId())
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

    /*ver detalle de una solicitud especifica - HECHO

    Una vez viendo el detalle, el responsable lo puede cotizar y registrarlo en la tabla Cotizacion,
    dandole un precio unitario a cada detalle una vez cotizado, se registra la solicitud en la
    tabla autorizacion.

    TODO Una vez terminado la operacion por parte de un externo, entonces se
    registra como finalizado en la tabla Finalizacion*/

    @GetMapping(path = {"/finalizacion/{idSolicitud}"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void finalizacion(@PathVariable Long idSolicitud) {

        Autorizacion autorizacion = solicitudService.buscarAutorizacionByIdSoli(idSolicitud);

        Finalizacion finalizacion = Finalizacion.builder()
                .fecha(LocalDate.now().toString())
                .totalEjecutado(autorizacion.getTotalAutorizado())
                .totalEjecutadoBs(autorizacion.getTotalCotizadoBs())
                .fkAutorizacion(autorizacion)
                .build();

        solicitudService.guardarFinalizacion(finalizacion);

    }




    @PostMapping(path = {"/cotizarAutorizarSolicitud"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void cotizarAutorizarSolicitud(@RequestBody List<DetalleSolicitudCotizRequest> listSoliCoti) {
        listSoliCoti.forEach(x -> {

            DetalleSolicitud detalleSolicitud = DetalleSolicitud.builder()
                    .id(x.getIdDetalleSolicitud())
                    .build();

            BigDecimal total = x.getPrecioUnitario()
                    .multiply(BigDecimal.valueOf(x.getNroCopias()))
                    .multiply(BigDecimal.valueOf(x.getNroPaginas()));

            Cotizacion cotizacion = Cotizacion.builder()
                    .precioUnitario(x.getPrecioUnitario())
                    .precioTotal(total)
                    .fkDetalleSolicitud(detalleSolicitud)
                    .build();

            responsableService.guardarCotizacion(cotizacion);
        });

        Long idSolicitud = listSoliCoti.getFirst().getIdSolicitud();
        Long idUsuarioUnidad = listSoliCoti.getFirst().getIdUsuarioUnidad();

        UsuarioUnidad usuarioUnidad = UsuarioUnidad.builder()
                .id(idUsuarioUnidad)
                .build();
        Solicitud solicitud = Solicitud.builder()
                .id(idSolicitud)
                .build();

        List<DetalleSolicitud> list = solicitudService.findListDetalleSoliBySolicitudId(idSolicitud);
        Long totalAutorizado = list.stream()
                .mapToLong(x -> x.getNroCopias())
                .sum();
        BigDecimal totalAutorizadoBs = list.stream()
                .map(detalle -> {
                    BigDecimal paginas = detalle.getNroPaginas() != null ? BigDecimal.valueOf(detalle.getNroPaginas()) : BigDecimal.ZERO;
                    BigDecimal copias = detalle.getNroCopias() != null ? BigDecimal.valueOf(detalle.getNroCopias()) : BigDecimal.ZERO;
                    return paginas.multiply(copias);
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Autorizacion autorizacion = Autorizacion.builder()
                .fecha(LocalDate.now().toString())
                .totalAutorizado(totalAutorizado)
                .totalCotizadoBs(totalAutorizadoBs)
                .fkUsuarioResponsable(usuarioUnidad)
                .fkSolicitud(solicitud)
                .build();

        solicitudService.guardarAutorizacion(autorizacion);

    }


    @GetMapping(path = {"/verDetalleDeSolicitud/{idSolicitud}"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<DetalleSolicitudExtendidoResponse> verDetalleDeSolicitud(@PathVariable Long idSolicitud) {
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
                .idDetalleSolicitud(detalleSolicitud.getId())
                .nroCopias(detalleSolicitud.getNroCopias())
                .nombreDocumento(detalleSolicitud.getNombreDocumento())
                .nroPaginas(detalleSolicitud.getNroPaginas())
                .tamanoPagina(detalleSolicitud.getTamanoPagina())
                .anversoReverso(detalleSolicitud.getAnversoReverso())
                .colorFotocopia(detalleSolicitud.getColorFotocopia())
                .build();
    }




    @PostMapping(path = {"/verSolicitudesAprobadas"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<SolicitudResponResponse>> verListaSolicitudesAprobadas(@RequestBody PaginacionResponRequest pageParam) {
        /*Long idResponsable = pageParam.getIdUsuario();

        Long page = pageParam.getPage();
        Long size = pageParam.getSize();
        String byColumName = pageParam.getByColumName();

        List<Aprobacion> aprobacionDomains = aprobacionService
                .listaDeSolicitudesAprobadasService(idResponsable, page, size, byColumName);
        List<SolicitudResponResponse> listSolicitud = aprobacionDomains
                .stream()
                .map(this::funcion)
                .toList();*/
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PostMapping(path = {"/verSolicitudesRechazadas"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<SolicitudResponResponse>> verListaSolicitudesRechazadas(@RequestBody PaginacionResponRequest pageParam) {
        /*Long idResponsable = pageParam.getIdUsuario();

        Long page = pageParam.getPage();
        Long size = pageParam.getSize();
        String byColumName = pageParam.getByColumName();

        List<Aprobacion> aprobacionDomains = aprobacionService
                .listaDeSolicitudesRechazadasService(idResponsable, page, size, byColumName);
        List<SolicitudResponResponse> listSolicitud = aprobacionDomains
                .stream()
                .map(this::funcion)
                .toList();*/
        return new ResponseEntity<>(null, HttpStatus.OK);
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
