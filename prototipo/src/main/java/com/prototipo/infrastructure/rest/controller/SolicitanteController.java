package com.prototipo.infrastructure.rest.controller;

import com.prototipo.application.useCase.*;
import com.prototipo.domain.model.*;
import com.prototipo.infrastructure.rest.report.ComunicacionReport;
import com.prototipo.infrastructure.rest.report.InformeReport;
import com.prototipo.infrastructure.rest.report.SolicitudReport;
import com.prototipo.infrastructure.rest.report.TablaSolicitudReport;
import com.prototipo.infrastructure.rest.request.PaginacionSoliRequest;
import com.prototipo.infrastructure.rest.request.RowSolicitud;
import com.prototipo.infrastructure.rest.request.SolicitudRequest;
import com.prototipo.infrastructure.rest.request.SolicitudRequestPDF;
import com.prototipo.infrastructure.rest.response.SolicitudSoliciResponse;
import com.prototipo.infrastructure.service.ComunicacionInternaServiceReport;
import com.prototipo.infrastructure.service.InformeServiceReport;
import com.prototipo.infrastructure.service.OrdenFotoServiceReport;
import com.prototipo.infrastructure.service.SolicitudServiceReport;
import net.sf.jasperreports.engine.JRException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 86400)
@RestController
@RequestMapping(path = "/solicitante")
public class SolicitanteController {

    @Autowired
    private SolicitudService solicitudService;
    @Autowired
    private UnidadService unidadService;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private AprobacionService aprobacionService;
    @Autowired
    private OperacionService operacionService;
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
    public void solicitarFotocopiar(@RequestBody SolicitudRequest solicitudRequest) {
        //Truco de los Ids
        /*Unidad unidad = Unidad.builder()
                .id(solicitudRequest.getIdUnidad())
                .build();
        Usuario usuario = Usuario.builder()
                .id(solicitudRequest.getIdSolicitante())
                .build();

        List<ArchivoPdf> archivoPdfs = solicitudRequest.getArchivosPdf()
                .stream()
                .map(x -> modelMapper.map(x, ArchivoPdf.class))
                .toList();

        //Debo usar los mappeadores de mi Infraestrucutura
        Solicitud solicitud = Solicitud.builder()
                .nroDeCopias(solicitudRequest.getNroDeCopias())
                .tipoDeDocumento(solicitudRequest.getTipoDeDocumento())
                .nroDePaginas(solicitudRequest.getNroDePaginas())
                .fkUnidad(unidad)
                .fkSolicitante(usuario)
                .build();

        solicitudService.solicitarFotocopiarService(solicitud, archivoPdfs);*/
    }

    @PostMapping(path = {"/v2/solicitarFotocopiar"},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public void solicitarFotocopiarV2(@RequestBody SolicitudRequest solicitudRequest) {
        //Truco de los Ids
        UsuarioUnidad usuarioUnidad = UsuarioUnidad.builder()
                .id(solicitudRequest.getFkUsuarioSolicitante())
                .build();

        List<DetalleSolicitud> listDetalleSolicitud = solicitudRequest
                .getListDetalleSolicitud()
                .stream()
                .map(x -> modelMapper.map(x, DetalleSolicitud.class))
                .toList();

        //Debo usar los mappeadores de mi Infraestrucutura
        Solicitud solicitud = Solicitud.builder()
                .cite(solicitudRequest.getCite())
                .fecha(LocalDate.now().toString())
                .descripcion(solicitudRequest.getDescripcion())
                .fkUsuarioSolicitante(usuarioUnidad)
                .autoriFlag(0L)
                .build();

        solicitudService.solicitarFotocopiarService(solicitud, listDetalleSolicitud);
    }

    @PostMapping(path = {"/solicitarFotocopiarPDF"},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public void solicitarFotocopiar(@RequestBody SolicitudRequestPDF solicitudRequest) {
        //Truco de los Ids
        /*Usuario usuario = Usuario.builder()
                .id(solicitudRequest.getIdSolicitante())
                .build();
        Unidad unidad = Unidad.builder()
                .id(solicitudRequest.getIdUnidad())
                .build();

        Solicitud solicitud = Solicitud.builder()
                .fkSolicitante(usuario)
                .fkUnidad(unidad)
                .cite(solicitudRequest.getCite())
                .build();

        List<DetalleSolicitud> list = solicitudRequest.getListSolicitud()
                .stream()
                .map(x -> modelMapper.map(x, DetalleSolicitud.class))
                .toList();

        solicitudService.registrarFotocopiarService(solicitud, list);*/
    }

    @PostMapping(path = {"/verHistorialSolicitudes"},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<SolicitudSoliciResponse>> verHistorialSolicitudes(
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

    private SolicitudSoliciResponse funcToReturn(Solicitud x) {
        SolicitudSoliciResponse solicitudSoliciResponse = modelMapper.map(x, SolicitudSoliciResponse.class);
        solicitudSoliciResponse.setCi(x.getFkUsuarioSolicitante().getFkUsuario().getCi());
        solicitudSoliciResponse.setCargo(x.getFkUsuarioSolicitante().getFkCargo().getNombreCargo());
        return solicitudSoliciResponse;
    }


    //localhost:8081/solicitante/exportSolicitudDPF
    @GetMapping("/exportSolicitudDPF/{idSolicitud}/{idSolicitante}/{idResponsable}")
    public ResponseEntity<byte[]> exportSolicitudDPF(
            @PathVariable Long idSolicitud,
            @PathVariable Long idSolicitante,
            @PathVariable Long idResponsable ) throws IOException, JRException {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData(
                "solicitudPDF",
                "solicitudPDF.pdf"
        );

        Solicitud solicitudResp = solicitudService
                .buscarSolicitudService(idSolicitud);
        List<DetalleSolicitud> listDetalleSolicitudResp = solicitudService
                .listDetalleSolicitud(idSolicitud);

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

        SolicitudReport solicitudReport = SolicitudReport.builder()
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
                .body(solicitudServiceReport.exportToPdf(solicitudReport, listReport));
    }

    @GetMapping("/exportComunicacionInternaDPF/{idSolicitud}/{idSolicitante}/{idResponsable}")
    public ResponseEntity<byte[]> exportComunicacionInternaDPF(
            @PathVariable Long idSolicitud,
            @PathVariable Long idSolicitante,
            @PathVariable Long idResponsable ) throws IOException, JRException {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData(
                "comunicacionInternaPDF",
                "comunicacionInterna.pdf"
        );

        Solicitud solicitudResp = solicitudService
                .buscarSolicitudService(idSolicitud);
        List<DetalleSolicitud> listDetalleSolicitudResp = solicitudService
                .listDetalleSolicitud(idSolicitud);

        String documentos = formatListaDocumentos(listDetalleSolicitudResp);
        Long totalCopias = listDetalleSolicitudResp.stream()
                .mapToLong(DetalleSolicitud::getNroCopias)  // Convierte a stream de long
                .sum();  // Suma todos los valores

        UsuarioUnidad usuarioResponsable = usuarioService
                .findUsuarioUnidadByIdUSer(idResponsable);
        UsuarioUnidad usuarioSolicitante = usuarioService
                .findUsuarioUnidadByIdUSer(idSolicitante);


        ComunicacionReport comunicacionReport = ComunicacionReport.builder()
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
                .nombreOrganizacion(usuarioSolicitante.getFkUnidad().getNombre())
                .documentos(documentos)
                .totalCopias(totalCopias.intValue())
                .build();

        return ResponseEntity.ok()
                .headers(headers)
                .body(comunicacionInternaServiceReport.exportToPdf(comunicacionReport));
    }

    private String formatListaDocumentos(List<DetalleSolicitud> listDetalleSolicitudResp) {
        // Usamos collect() para obtener una lista en versiones de Java anteriores a 16
        List<String> nombres = listDetalleSolicitudResp.stream()
                .map(DetalleSolicitud::getNombreDocumento).toList();

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
        headers.setContentDispositionFormData(
                "informePDF",
                "informePDF.pdf"
        );

        Solicitud solicitudResp = solicitudService
                .buscarSolicitudService(idSolicitud);
        List<DetalleSolicitud> listDetalleSolicitudResp = solicitudService
                .listDetalleSolicitud(idSolicitud);

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
                .mapToLong(DetalleSolicitud::getNroCopias)  // Convierte a stream de long
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

    @GetMapping("/exportOrdenParaFotocopiaDPF/{idSolicitud}/{idSolicitante}/{idResponsable}")
    public ResponseEntity<byte[]> exportOrdenParaFotocopiaDPF(
            @PathVariable Long idSolicitud,
            @PathVariable Long idSolicitante,
            @PathVariable Long idResponsable ) throws IOException, JRException {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData(
                "ordenParaFotocopiaPDF",
                "ordenParaFotocopia.pdf"
        );

        Solicitud solicitudResp = solicitudService
                .buscarSolicitudService(idSolicitud);
        List<DetalleSolicitud> listDetalleSolicitudResp = solicitudService
                .listDetalleSolicitud(idSolicitud);

        return ResponseEntity.ok()
                .headers(headers)
                .body(ordenFotoServiceReport.exportToPdf(listDetalleSolicitudResp));
    }
}
