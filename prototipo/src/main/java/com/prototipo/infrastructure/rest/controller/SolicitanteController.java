package com.prototipo.infrastructure.rest.controller;

import com.prototipo.application.useCase.*;
import com.prototipo.domain.model.*;
import com.prototipo.infrastructure.rest.report.ComunicacionReport;
import com.prototipo.infrastructure.rest.report.InformeReport;
import com.prototipo.infrastructure.rest.report.SolicitudReport;
import com.prototipo.infrastructure.rest.report.TablaSolicitudReport;
import com.prototipo.infrastructure.rest.request.PaginacionSoliRequest;
import com.prototipo.infrastructure.rest.request.SolicitudRequest;
import com.prototipo.infrastructure.rest.response.SolicitudSoliciResponse;
import com.prototipo.infrastructure.service.ComunicacionInternaServiceReport;
import com.prototipo.infrastructure.service.InformeServiceReport;
import com.prototipo.infrastructure.service.OrdenFotoServiceReport;
import com.prototipo.infrastructure.service.SolicitudServiceReport;
import net.sf.jasperreports.engine.JRException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@CrossOrigin(origins = "*", maxAge = 86400)
@RestController
@RequestMapping(path = "/solicitante")
public class SolicitanteController {

    @Autowired
    private SolicitudService solicitudService;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private SolicitudServiceReport solicitudServiceReport;
    @Autowired
    private ComunicacionInternaServiceReport comunicacionInternaServiceReport;
    @Autowired
    private InformeServiceReport informeServiceReport;
    @Autowired
    private OrdenFotoServiceReport ordenFotoServiceReport;


    @PostMapping(path = {"/solicitarFotocopiar"},
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

        //Debo usar los mappeadores de mi Infraestrucutura
        Solicitud solicitud = Solicitud.builder()
                .cite(solicitudRequest.getCite())
                .fecha(LocalDate.now().toString())
                .descripcion(solicitudRequest.getDescripcion())
                .fkUsuarioSolicitante(usuarioUnidad)
                .autoriFlag(0L)
                .isActive(true)
                .build();

        solicitudService.solicitarFotocopiarService(solicitud, listFotocopias);
    }

    @PostMapping(path = {"/verSolicitudesPendientes"},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<SolicitudSoliciResponse>> verSolicitudesPendientes(
            @RequestBody PaginacionSoliRequest pageArg ) {

        Long idUserUni = pageArg.getIdUsuarioUnidad();

        Long page = pageArg.getPage();
        Long size = pageArg.getSize();
        //String byColumName = pageArg.getByColumName();

        List<Solicitud> listSoli = solicitudService
                .getListaSolicitudesService(idUserUni, page, size);

        List<SolicitudSoliciResponse> list = listSoli.stream()
                .map(this::funcToReturn)
                .toList();

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping(path = {"/verSolicitudesAutorizadas"},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<SolicitudSoliciResponse>> verSolicitudesAutorizadas(
            @RequestBody PaginacionSoliRequest pageArg ) {

        Long idUserUni = pageArg.getIdUsuarioUnidad();

        Long page = pageArg.getPage();
        Long size = pageArg.getSize();
        //String byColumName = pageArg.getByColumName();

        List<Solicitud> listSoli = solicitudService
                .getListaSolicitudesAutoriService(idUserUni, page, size);

        List<SolicitudSoliciResponse> list = listSoli.stream()
                .map(this::funcToReturn)
                .toList();

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping(path = {"/verSolicitudesFinalizadas"},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<SolicitudSoliciResponse>> verSolicitudesFinalizadas(
            @RequestBody PaginacionSoliRequest pageArg ) {

        Long idUserUni = pageArg.getIdUsuarioUnidad();

        Long page = pageArg.getPage();
        Long size = pageArg.getSize();
        //String byColumName = pageArg.getByColumName();

        List<Solicitud> listSoli = solicitudService
                .getListaSolicitudesFinaliService(idUserUni, page, size);

        List<SolicitudSoliciResponse> list = listSoli.stream()
                .map(this::funcToReturn)
                .toList();

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    private SolicitudSoliciResponse funcToReturn(Solicitud x) {
        SolicitudSoliciResponse solicitudSoliciResponse = modelMapper
                .map(x, SolicitudSoliciResponse.class);
        solicitudSoliciResponse.setId(x.getId());
        solicitudSoliciResponse.setCi(x.getFkUsuarioSolicitante().getFkUsuario().getCi());
        solicitudSoliciResponse.setCargo(x.getFkUsuarioSolicitante().getFkCargo().getNombreCargo());
        return solicitudSoliciResponse;
    }

    @GetMapping("/eliminarSolicitudById/{idSolicitud}")
    public void eliminarSolicitudById(@PathVariable Long idSolicitud){
        solicitudService.eliminarSolicitudById(idSolicitud);
    }

    @GetMapping("/listarTamano")
    public ResponseEntity<List<String>> listarTamano(){
        List<String> listTam = solicitudService.listarTamano();
        return new ResponseEntity<>(listTam, HttpStatus.OK);
    }

    @GetMapping("/listarAnversoReverso")
    public ResponseEntity<List<String>> listarAnversoReverso(){
        List<String> listAnRev = solicitudService.listarAnversoReverso();
        return new ResponseEntity<>(listAnRev, HttpStatus.OK);
    }

    @GetMapping("/listarColor")
    public ResponseEntity<List<String>> listarColor(){
        List<String> listColor = solicitudService.listarColor();
        return new ResponseEntity<>(listColor, HttpStatus.OK);
    }


    //localhost:8081/solicitante/exportSolicitudDPF
    @Async
    @GetMapping("/exportSolicitudDPF/{idSolicitud}")
    public CompletableFuture<ResponseEntity<byte[]>> exportSolicitudDPF(
            // Unicamente con el Id de Solicitud puedes traer toda la informacion debido a sus relaciones
            @PathVariable Long idSolicitud) {

        return CompletableFuture.supplyAsync(() -> {
            try {
                SolicitudReport solicitudReport = solicitudServiceReport.getObtenerDatosForReport(idSolicitud);

                // PDF generado
                byte[] pdfData = solicitudServiceReport.exportToPdf(solicitudReport);

                // Los heades para el reponse
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_PDF);
                headers.setContentDispositionFormData("solicitudPDF", "solicitudPDF.pdf");

                return ResponseEntity.ok().headers(headers).body(pdfData);

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
                // Recuperar datos necesarios
                List<Fotocopia> listFotocopia = solicitudService
                        .listFotocopiaSolicitud(idSolicitud);

                // Generar el PDF
                byte[] pdfData = ordenFotoServiceReport.exportToPdf(listFotocopia);

                // Configurar encabezados de la respuesta
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_PDF);
                headers.setContentDispositionFormData(
                        "ordenParaFotocopiaPDF",
                        "ordenParaFotocopia.pdf"
                );

                // Crear y devolver la respuesta
                return ResponseEntity.ok()
                        .headers(headers)
                        .body(pdfData);

            } catch (IOException | JRException e) {
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
                // Recuperar datos necesarios
                Solicitud solicitudResp = solicitudService.buscarSolicitudService(idSolicitud);

                List<Fotocopia> listFotocopiaSolicitudResp = solicitudService
                        .listFotocopiaSolicitud(idSolicitud);

                String documentos = formatListaDocumentos(listFotocopiaSolicitudResp);
                long totalCopias = listFotocopiaSolicitudResp.stream()
                        .mapToLong(Fotocopia::getNroCopias)
                        .sum();

                UsuarioUnidad usuarioSolicitante = solicitudResp.getFkUsuarioSolicitante();
                UsuarioUnidad usuarioResponsable = solicitudResp.getFkUsuarioSolicitante().getFkResponsable();

                // Crear objeto de reporte
                ComunicacionReport comunicacionReport = ComunicacionReport.builder()
                        .funcionarioTo(
                                usuarioResponsable.getFkUsuario().getNombres() + " " +
                                        usuarioResponsable.getFkUsuario().getPaterno() + " " +
                                        usuarioResponsable.getFkUsuario().getMaterno())
                        .funcionarioToCargo(
                                usuarioResponsable.getFkCargo().getNombreCargo())
                        .funcionarioFrom(
                                usuarioSolicitante.getFkUsuario().getNombres() + " " +
                                        usuarioSolicitante.getFkUsuario().getPaterno() + " " +
                                        usuarioSolicitante.getFkUsuario().getMaterno())
                        .funcionarioFromCargo(
                                usuarioSolicitante.getFkCargo().getNombreCargo())
                        .cite(solicitudResp.getCite())
                        .nombreOrganizacion(usuarioSolicitante.getFkUnidad().getNombre())
                        .documentos(documentos)
                        .totalCopias((int) totalCopias)
                        .build();

                // Generar el PDF
                byte[] pdfBytes = comunicacionInternaServiceReport.exportToPdf(comunicacionReport);

                // Configurar encabezados de la respuesta
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_PDF);
                headers.setContentDispositionFormData(
                        "comunicacionInternaPDF",
                        "comunicacionInterna.pdf"
                );

                // Crear y devolver la respuesta
                return ResponseEntity.ok()
                        .headers(headers)
                        .body(pdfBytes);

            } catch (IOException | JRException e) {
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







    @GetMapping("/exportInformeDPF/{idSolicitud}/{idSolicitante}/{idResponsable}")
    public ResponseEntity<byte[]> exportInformeDPF(
            @PathVariable Long idSolicitud,
            @PathVariable Long idSolicitante,
            @PathVariable Long idResponsable ) throws IOException, JRException {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("informePDF", "informePDF.pdf");

        Solicitud solicitudResp = solicitudService
                .buscarSolicitudService(idSolicitud);
        List<Fotocopia> listDetalleSolicitudResp = solicitudService
                .listFotocopiaSolicitud(idSolicitud);

        List<TablaSolicitudReport> listReport = listDetalleSolicitudResp.stream()
                .map(x -> TablaSolicitudReport.builder()
                        .documento(x.getNombreDocumento())
                        .cantidad(x.getNroCopias().intValue())
                        .build())
                .toList();

        UsuarioUnidad usuarioResponsable = usuarioService
                .findUsuarioUnidadByIdUSer(idResponsable);
        UsuarioUnidad usuarioSolicitante = usuarioService
                .findUsuarioUnidadByIdUSer(idSolicitante);

        Long totalCopias = listDetalleSolicitudResp.stream()
                .mapToLong(Fotocopia::getNroCopias)  // Convierte a stream de long
                .sum();  // Suma todos los valores


        if (totalCopias >= 5000) {
            InformeReport report = InformeReport.builder()
                    .funcionarioTo(
                            usuarioResponsable.getFkUsuario().getNombres() + " " +
                            usuarioResponsable.getFkUsuario().getPaterno() + " " +
                            usuarioResponsable.getFkUsuario().getMaterno())
                    .funcionarioToCargo(
                            usuarioResponsable.getFkCargo().getNombreCargo())
                    .funcionarioFrom(
                            usuarioSolicitante.getFkUsuario().getNombres() + " " +
                            usuarioResponsable.getFkUsuario().getPaterno() + " " +
                            usuarioResponsable.getFkUsuario().getMaterno())
                    .funcionarioFromCargo(
                            usuarioSolicitante.getFkCargo().getNombreCargo())
                    .cite(solicitudResp.getCite())
                    .fecha(LocalDate.now().toString())
                    .nombreOrganizacion(usuarioSolicitante.getFkUnidad().getNombre())
                    .build();

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(informeServiceReport.exportToPdf(report, listReport));

        } else {
            InformeReport report = InformeReport.builder()
                    .funcionarioTo("-----NO SE SUPERA LOS 5000 UNIDADES PARA UN INFORME-----")
                    .funcionarioToCargo("-----NO SE SUPERA LOS 5000 UNIDADES PARA UN INFORME-----")
                    .funcionarioFrom("-----NO SE SUPERA LOS 5000 UNIDADES PARA UN INFORME-----")
                    .funcionarioFromCargo("-----NO SE SUPERA LOS 5000 UNIDADES PARA UN INFORME-----")
                    .cite("-----NO SE SUPERA LOS 5000 UNIDADES PARA UN INFORME-----")
                    .fecha("-----NO SE SUPERA LOS 5000 UNIDADES PARA UN INFORME-----")
                    .nombreOrganizacion("-----NO SE SUPERA LOS 5000 UNIDADES PARA UN INFORME-----")
                    .build();

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(informeServiceReport.exportToPdf(report, null));

        }

    }
}
