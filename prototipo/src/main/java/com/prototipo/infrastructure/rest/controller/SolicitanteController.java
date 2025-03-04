package com.prototipo.infrastructure.rest.controller;

import com.prototipo.application.model.PaginableIn;
import com.prototipo.application.model.PaginableOut;
import com.prototipo.application.port.in.SolicitanteService;
import com.prototipo.domain.model.*;
import com.prototipo.infrastructure.rest.request.PageRequest;
import com.prototipo.infrastructure.rest.request.SolicitudRequest;
import com.prototipo.infrastructure.rest.response.PageResponse;
import com.prototipo.infrastructure.rest.response.SolicitudSoliciResponse;
import com.prototipo.infrastructure.service.Observable;
import net.sf.jasperreports.engine.JRException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@CrossOrigin(origins = "*", maxAge = 86400)
@RestController
@RequestMapping(path = "/solicitante")
public class SolicitanteController {

    @Autowired
    private SolicitanteService solicitanteService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private Observable observable;


    @PostMapping(
            path = {"/solicitarFotocopiar"},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public void solicitarFotocopiarV2(@RequestBody SolicitudRequest solicitudRequest) {
        List<Fotocopia> listFotocopias = solicitudRequest
                .getListDetalleSolicitud()
                .stream()
                .map(x -> {
                    ServicioFotocopia serFoto = ServicioFotocopia.builder()
                            .color(x.getColorFotocopia())
                            .tamano(x.getTamanoPagina())
                            .anverRever(x.getAnversoReverso())
                            .build();
                    Fotocopia fot = modelMapper.map(x, Fotocopia.class);
                    fot.setFkServicioFotocopia(serFoto);
                    return fot;
                })
                .toList();

        //Truco de los Ids
        UsuarioUnidad usuarioUnidad = UsuarioUnidad.builder()
                .id(solicitudRequest.getFkUsuarioSolicitante())
                .build();

        LocalDate fechaActual = LocalDate.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");


        //Debo usar los mappeadores de mi Infraestrucutura
        Solicitud solicitud = Solicitud.builder()
                .cite(solicitudRequest.getCite())
                .fecha(fechaActual.format(formato))
                .descripcion(solicitudRequest.getDescripcion())
                .fkUsuarioSolicitante(usuarioUnidad)
                .autoriFlag(0L)
                .isActive(true)
                .build();

        solicitanteService.solicitarFotocopia(solicitud, listFotocopias);
        observable.publicarValor(1);
    }

    @GetMapping("/eliminarSolicitudById/{idSolicitud}")
    public void eliminarSolicitudById(@PathVariable Long idSolicitud){
        solicitanteService.eliminarSolicitud(idSolicitud);
    }



    @PostMapping(
            path = {"/verSolicitudesPendientes"},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<PageResponse<SolicitudSoliciResponse>> verSolicitudesPendientes(
            @RequestBody PageRequest pageReq) {

        PaginableOut<Solicitud> paginableOut = solicitanteService
                .listaDeSolicitudes(modelMapper.map(pageReq, PaginableIn.class));

        List<SolicitudSoliciResponse> list = paginableOut.getContent()
                .stream()
                .map(this::funcToReturn)
                .toList();

        PageResponse<SolicitudSoliciResponse> pageResponse = PageResponse
                .<SolicitudSoliciResponse>builder()
                .page(pageReq.getPage().intValue())
                .size(pageReq.getSize().intValue())
                .sortBy(pageReq.getSortBy())
                .direction(pageReq.getDirection())

                .content(list)
                .totalPages(paginableOut.getTotalPages())
                .totalElements(paginableOut.getTotalElements())
                .build();

        return new ResponseEntity<>(pageResponse, HttpStatus.OK);
    }

    @PostMapping(
            path = {"/verSolicitudesAutorizadas"},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<PageResponse<SolicitudSoliciResponse>> verSolicitudesAutorizadas(
            @RequestBody PageRequest pageReq) {

        PaginableOut<Solicitud> paginableOut = solicitanteService
                .listaDeAutorizaciones(modelMapper.map(pageReq, PaginableIn.class));

        List<SolicitudSoliciResponse> list = paginableOut.getContent()
                .stream()
                .map(this::funcToReturn)
                .toList();

        PageResponse<SolicitudSoliciResponse> pageResponse = PageResponse
                .<SolicitudSoliciResponse>builder()
                .page(pageReq.getPage().intValue())
                .size(pageReq.getSize().intValue())
                .sortBy(pageReq.getSortBy())
                .direction(pageReq.getDirection())

                .content(list)
                .totalPages(paginableOut.getTotalPages())
                .totalElements(paginableOut.getTotalElements())
                .build();

        return new ResponseEntity<>(pageResponse, HttpStatus.OK);
    }

    @PostMapping(
            path = {"/verSolicitudesFinalizadas"},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<PageResponse<SolicitudSoliciResponse>> verSolicitudesFinalizadas(
            @RequestBody PageRequest pageReq) {

        PaginableOut<Solicitud> paginableOut = solicitanteService
                .listaDeFinalizaciones(modelMapper.map(pageReq, PaginableIn.class));


        List<SolicitudSoliciResponse> list = paginableOut.getContent().stream()
                .map(this::funcToReturn)
                .toList();

        PageResponse<SolicitudSoliciResponse> pageResponse = PageResponse
                .<SolicitudSoliciResponse>builder()
                .page(pageReq.getPage().intValue())
                .size(pageReq.getSize().intValue())
                .sortBy(pageReq.getSortBy())
                .direction(pageReq.getDirection())

                .content(list)
                .totalPages(paginableOut.getTotalPages())
                .totalElements(paginableOut.getTotalElements())
                .build();

        return new ResponseEntity<>(pageResponse, HttpStatus.OK);
    }

    private SolicitudSoliciResponse funcToReturn(Solicitud x) {
        SolicitudSoliciResponse solicitudSoliciResponse = modelMapper
                .map(x, SolicitudSoliciResponse.class);
        solicitudSoliciResponse.setId(x.getId());
        solicitudSoliciResponse.setCi(x.getFkUsuarioSolicitante().getFkUsuario().getCi());
        solicitudSoliciResponse.setCargo(x.getFkUsuarioSolicitante().getFkCargo().getNombreCargo());
        return solicitudSoliciResponse;
    }



    @GetMapping(
            path = {"/listarTamano"},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<String>> listarTamano(){
        List<String> listTam = solicitanteService.listaDeTamanoPagina();
        return new ResponseEntity<>(listTam, HttpStatus.OK);
    }

    @GetMapping(
            path = {"/listarAnversoReverso"},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<String>> listarAnversoReverso(){
        List<String> listAnRev = solicitanteService.listaDeAnverReverPagina();
        return new ResponseEntity<>(listAnRev, HttpStatus.OK);
    }

    @GetMapping(
            path = {"/listarColor"},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<String>> listarColor(){
        List<String> listColor = solicitanteService.listaDeColorPagina();
        return new ResponseEntity<>(listColor, HttpStatus.OK);
    }



    @Async
    @GetMapping("/exportSolicitudDPF/{idSolicitud}")
    public CompletableFuture<ResponseEntity<byte[]>> exportSolicitudDPF(
            // Unicamente con el Id de Solicitud puedes traer toda la informacion debido a sus relaciones
            @PathVariable Long idSolicitud) {

        return CompletableFuture.supplyAsync(() -> {
            try {
                solicitanteService.generarSolicitudDeFotocopiaPDF(idSolicitud);

                // Los heades para el reponse
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_PDF);
                headers.setContentDispositionFormData("solicitudPDF", "solicitudPDF.pdf");

                return ResponseEntity.ok().headers(headers).body(null);

            } catch (Exception e) {
                throw new RuntimeException("Error al generar el PDF", e);
            }
        });
    }

    @Async
    @GetMapping("/exportOrdenDeSolicitudDPF/{idSolicitud}")
    public CompletableFuture<ResponseEntity<byte[]>> exportOrdenDeSolicitudDPF(
            @PathVariable Long idSolicitud) {

        return CompletableFuture.supplyAsync(() -> {
            try {
                solicitanteService.generarOrdenDeFotocopiaPDF(idSolicitud);

                // Configurar encabezados de la respuesta
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_PDF);
                headers.setContentDispositionFormData(
                        "ordenParaFotocopiaPDF",
                        "ordenParaFotocopia.pdf"
                );

                // Crear y devolver la respuesta
                return ResponseEntity.ok().headers(headers).body(null);

            } catch (JRException e) {
                throw new RuntimeException("Error al generar el PDF", e);
            }
        });
    }

    @Async
    @GetMapping("/exportComunicacionInternaDPF/{idSolicitud}")
    public CompletableFuture<ResponseEntity<byte[]>> exportComunicacionInternaDPF(
            @PathVariable Long idSolicitud) {

        return CompletableFuture.supplyAsync(() -> {
            try {
                solicitanteService.generarComunicacionInternaPDF(idSolicitud);

                // Configurar encabezados de la respuesta
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_PDF);
                headers.setContentDispositionFormData(
                        "comunicacionInternaPDF",
                        "comunicacionInterna.pdf"
                );

                // Crear y devolver la respuesta
                return ResponseEntity.ok().headers(headers).body(null);

            } catch (JRException e) {
                throw new RuntimeException("Error al generar el PDF", e);
            }
        });
    }

    private String formatListaDocumentos(List<Fotocopia> listFotocopiaSolicitudResp) {
        // Usamos collect() para obtener una lista en versiones de Java anteriores a 16
        List<String> nombres = listFotocopiaSolicitudResp.stream()
                .map(Fotocopia::getNombreDocumento).toList();

        // Verifica la cantidad de documentos
        if (nombres.size() == 1) {
            return nombres.get(0); // Solo hay un documento
        } else if (nombres.size() == 2) {
            return String.join(" y ", nombres); // Solo hay dos documentos
        } else {
            // Junta todos menos el último con comas, y añade ' y ' antes del último
            return String.join(", ", nombres.subList(0, nombres.size() - 1))
                    + " y " + nombres.get(nombres.size() - 1);
        }
    }
}
