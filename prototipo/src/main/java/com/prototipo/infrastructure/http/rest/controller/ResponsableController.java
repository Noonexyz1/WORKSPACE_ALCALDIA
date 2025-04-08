package com.prototipo.infrastructure.http.rest.controller;

import com.prototipo.application.model.PaginableIn;
import com.prototipo.application.model.PaginableOut;
import com.prototipo.application.port.in.ResponsableService;
import com.prototipo.domain.model.*;
import com.prototipo.infrastructure.http.rest.model.request.AprobacionSoliRequest;
import com.prototipo.infrastructure.http.rest.model.request.PageRequest;
import com.prototipo.infrastructure.http.rest.model.response.*;
import com.prototipo.infrastructure.service.Observable;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@CrossOrigin(origins = "*", maxAge = 86400)
@RestController
@RequestMapping(path = "/responsable")
public class ResponsableController {

    @Autowired
    private ResponsableService responsableService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private Observable observable;



    @GetMapping(
            value = "/notificacion",
            produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Integer> obtenerActualizacion() {
        return observable.obtenerFlux();
    }



    @PostMapping(
            path = {"/verSolicitudesPendientes"},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<PageResponse<SolicitudResponResponse>> verSolicitudesPendientes(
            @RequestBody PageRequest pageReq) {

        PaginableOut<Solicitud> paginableOut = responsableService
                .listaDeSolicitudesPendientesByIdResponsable(modelMapper.map(pageReq, PaginableIn.class));

        List<SolicitudResponResponse> listSolicitud = paginableOut.getContent()
                .stream()
                .map(this::funcion)
                .toList();

        PageResponse<SolicitudResponResponse> pageResponse = PageResponse
                .<SolicitudResponResponse>builder()
                .page(pageReq.getPage().intValue())
                .size(pageReq.getSize().intValue())
                .sortBy(pageReq.getSortBy())
                .direction(pageReq.getDirection())

                .content(listSolicitud)
                .totalPages(paginableOut.getTotalPages())
                .totalElements(paginableOut.getTotalElements())
                .build();

        return new ResponseEntity<>(pageResponse, HttpStatus.OK);
    }

    @PostMapping(
            path = {"/verSolicitudesPendientesByIdSolicitud"},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<PageResponse<SolicitudResponResponse>> verSolicitudesPendientesByIdSolicitud(
            @RequestBody PageRequest pageReq) {

        PaginableOut<Solicitud> paginableOut = responsableService
                .listaDeSolicitudesPendientesByIdSolicitud(modelMapper.map(pageReq, PaginableIn.class));

        List<SolicitudResponResponse> listSolicitud = paginableOut.getContent()
                .stream()
                .map(this::funcion)
                .toList();

        PageResponse<SolicitudResponResponse> pageResponse = PageResponse
                .<SolicitudResponResponse>builder()
                .page(pageReq.getPage().intValue())
                .size(pageReq.getSize().intValue())
                .sortBy(pageReq.getSortBy())
                .direction(pageReq.getDirection())

                .content(listSolicitud)
                .totalPages(paginableOut.getTotalPages())
                .totalElements(paginableOut.getTotalElements())
                .build();

        return new ResponseEntity<>(pageResponse, HttpStatus.OK);
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
    public ResponseEntity<PageResponse<SolicitudResponResponse>> verSolicitudesAprobadas(
            @RequestBody PageRequest pageReq) {

        PaginableOut<Autorizacion> paginableOut = responsableService
                .listaDeSolicitudesAutorizadasByIdResponsable(modelMapper.map(pageReq, PaginableIn.class));

        List<SolicitudResponResponse> listSolicitud = paginableOut.getContent()
                .stream()
                .map(this::funcion)
                .toList();

        PageResponse<SolicitudResponResponse> pageResponse = PageResponse
                .<SolicitudResponResponse>builder()
                .page(pageReq.getPage().intValue())
                .size(pageReq.getSize().intValue())
                .sortBy(pageReq.getSortBy())
                .direction(pageReq.getDirection())

                .content(listSolicitud)
                .totalPages(paginableOut.getTotalPages())
                .totalElements(paginableOut.getTotalElements())
                .build();

        return new ResponseEntity<>(pageResponse, HttpStatus.OK);
    }

    @PostMapping(
            path = {"/verSolicitudesAutoriByIdSolicitud"},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<PageResponse<SolicitudResponResponse>> verSolicitudesAutoriByIdSolicitud(
            @RequestBody PageRequest pageReq) {

        PaginableOut<Autorizacion> paginableOut = responsableService
                .listaDeSolicitudesAutorizadasByIdSolicitud(modelMapper.map(pageReq, PaginableIn.class));

        List<SolicitudResponResponse> listSolicitud = paginableOut.getContent()
                .stream()
                .map(this::funcion)
                .toList();

        PageResponse<SolicitudResponResponse> pageResponse = PageResponse
                .<SolicitudResponResponse>builder()
                .page(pageReq.getPage().intValue())
                .size(pageReq.getSize().intValue())
                .sortBy(pageReq.getSortBy())
                .direction(pageReq.getDirection())

                .content(listSolicitud)
                .totalPages(paginableOut.getTotalPages())
                .totalElements(paginableOut.getTotalElements())
                .build();

        return new ResponseEntity<>(pageResponse, HttpStatus.OK);
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
    public ResponseEntity<PageResponse<SolicitudResponResponse>> verSolicitudesFinalizadas(
            @RequestBody PageRequest pageReq) {

        PaginableOut<Finalizacion> paginableOut = responsableService
                .listaDeSolicitudesFinalizadasByIdResponsable(modelMapper.map(pageReq, PaginableIn.class));

        List<SolicitudResponResponse> listSolicitud = paginableOut.getContent()
                .stream()
                .map(this::funcion)
                .toList();

        PageResponse<SolicitudResponResponse> pageResponse = PageResponse
                .<SolicitudResponResponse>builder()
                .page(pageReq.getPage().intValue())
                .size(pageReq.getSize().intValue())
                .sortBy(pageReq.getSortBy())
                .direction(pageReq.getDirection())

                .content(listSolicitud)
                .totalPages(paginableOut.getTotalPages())
                .totalElements(paginableOut.getTotalElements())
                .build();

        return new ResponseEntity<>(pageResponse, HttpStatus.OK);
    }

    @PostMapping(
            path = {"/verSolicitudesFinaliByIdSolicitud"},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<PageResponse<SolicitudResponResponse>> verSolicitudesFinaliByIdSolicitud(
            @RequestBody PageRequest pageReq) {

        PaginableOut<Finalizacion> paginableOut = responsableService
                .listaDeSolicitudesFinalizadasByIdSolicitud(modelMapper.map(pageReq, PaginableIn.class));

        List<SolicitudResponResponse> listSolicitud = paginableOut.getContent()
                .stream()
                .map(this::funcion)
                .toList();

        PageResponse<SolicitudResponResponse> pageResponse = PageResponse
                .<SolicitudResponResponse>builder()
                .page(pageReq.getPage().intValue())
                .size(pageReq.getSize().intValue())
                .sortBy(pageReq.getSortBy())
                .direction(pageReq.getDirection())

                .content(listSolicitud)
                .totalPages(paginableOut.getTotalPages())
                .totalElements(paginableOut.getTotalElements())
                .build();

        return new ResponseEntity<>(pageResponse, HttpStatus.OK);
    }

    private SolicitudResponResponse funcion(Finalizacion x){
        return SolicitudResponResponse.builder()
                .idAutorizacion(x.getFkAutorizacion().getId())
                .idSolicitud(x.getFkAutorizacion().getFkSolicitud().getId())
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
        responsableService.guardarAutorizacion(autorizacion);
    }

    @PostMapping(
            path = {"/rechazarSolicitud"},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public void rechazarSolicitud(@RequestBody AprobacionSoliRequest aprobacionSoliRequest) {
        Long idSolicitud = aprobacionSoliRequest.getIdSolicitud();
        Long idResponsable = aprobacionSoliRequest.getIdResponsable();
        responsableService.rechazarSolicitud(idSolicitud, idResponsable);
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
        responsableService.guardarFinalizacion(finalizacion);
    }





    @GetMapping(
            path = {"/verDetalleDeSolicitud/{idSolicitud}"},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<DetalleSolicitudExtendidoResponse> verDetalleDeSolicitud(
            @PathVariable Long idSolicitud) {

        List<Fotocopia> listDetSoli = responsableService
                .listaDeFotocopias(idSolicitud);

        List<DetalleSolicitudResponse> listDetSoliRespone = listDetSoli.stream()
                .map(this::funcion)
                .toList();

        DetalleSolicitudExtendidoResponse solicitudExtendido = DetalleSolicitudExtendidoResponse.builder()
                .idSolicitud(listDetSoli.getFirst().getFkSolicitud().getId())
                .cite(listDetSoli.getFirst().getFkSolicitud().getCite())
                .fecha(listDetSoli.getFirst().getFkSolicitud().getFecha())
                .descripcion(listDetSoli.getFirst().getFkSolicitud().getDescripcion())
                .nombreServicio(listDetSoli.getFirst().getFkSolicitud().getNombreServicio())
                .precioTotal(BigDecimal.valueOf(listDetSoli.getFirst().getFkSolicitud().getPrecioTotal()).setScale(2, RoundingMode.HALF_UP).doubleValue())
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
                .precioDocu(BigDecimal.valueOf(detalleSolicitud.getPrecioDocu()).setScale(2, RoundingMode.HALF_UP).doubleValue())
                .build();
    }

    @GetMapping(
            path = {"/verAutorizacionSolicitud/{idSolicitud}"},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<AutorizacionResponse> verAutorizacionSolicitud(
            @PathVariable Long idSolicitud) {

        Autorizacion autorizacion = responsableService.obtenerAutorizacion(idSolicitud);
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



    @Async  // La anotación para indicar que este mettodo es asincronico
    @GetMapping("/exportNotaPedidoDPF/{idSolicitud}")
    public CompletableFuture<ResponseEntity<byte[]>> exportNotaPedidoDPF(
            @PathVariable Long idSolicitud) throws IOException {

        byte[] notaDePedido = responsableService.descargarNotaPedidoPDF(idSolicitud);

        return CompletableFuture.supplyAsync(() -> {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_PDF);
                headers.setContentDispositionFormData(
                        "notaPedidoPDF",
                        "notaPedido_" + idSolicitud + ".pdf"
                );
                return ResponseEntity.ok().headers(headers).body(notaDePedido);
        });
    }

    @Async
    @GetMapping("/exportReporteDPF")
    public CompletableFuture<ResponseEntity<byte[]>> exportReporteDPF() throws IOException {

        byte[] reporte = responsableService.descargarReportePDF();

        return CompletableFuture.supplyAsync(() -> {
            String mesAnio = LocalDate.now().format(DateTimeFormatter.ofPattern("MM-yyyy"));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData(
                    "reportePDF",
                    "reporte_" + mesAnio + ".pdf"
            );
            return ResponseEntity.ok().headers(headers).body(reporte);
        });
    }
}
