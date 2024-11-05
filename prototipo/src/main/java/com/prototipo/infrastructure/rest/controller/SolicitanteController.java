package com.prototipo.infrastructure.rest.controller;

import com.prototipo.application.useCase.*;
import com.prototipo.domain.model.*;
import com.prototipo.infrastructure.rest.report.SolicitudReport;
import com.prototipo.infrastructure.rest.request.PaginacionSoliRequest;
import com.prototipo.infrastructure.rest.request.SolicitudRequest;
import com.prototipo.infrastructure.rest.response.SolicitudSoliciResponse;
import com.prototipo.infrastructure.service.SolicitudServiceReport;
import net.sf.jasperreports.engine.JRException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.io.IOException;
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


    @PostMapping(path = {"/solicitarFotocopiar"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void solicitarFotocopiar(@RequestBody SolicitudRequest solicitudRequest) {
        //Truco de los Ids
        Unidad unidad = Unidad.builder()
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

        solicitudService.solicitarFotocopiarService(solicitud, archivoPdfs);
    }

    @PostMapping(path = {"/verHistorialSolicitudes"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<SolicitudSoliciResponse>> verHistorialSolicitudes(@RequestBody PaginacionSoliRequest pageArg) {
        Long idUsuario = pageArg.getIdUsuario();

        Long page = pageArg.getPage();
        Long size = pageArg.getSize();
        //String byColumName = pageArg.getByColumName();

        List<Solicitud> listDomain = solicitudService.getListaSolicitudesService(idUsuario, page, size);
        List<SolicitudSoliciResponse> list = listDomain.stream()
                .map(this::funcToReturn)
                .toList();

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    private SolicitudSoliciResponse funcToReturn(Solicitud x) {
        SolicitudSoliciResponse solicitudSoliciResponse = modelMapper.map(x, SolicitudSoliciResponse.class);

        Aprobacion aprobacion = aprobacionService
                .findAprovacionByIdSoliService(x.getId());
        Operacion operacion = operacionService
                .findOperacionByIdSoliService(x.getId());

        String estadoAprobacion = (aprobacion != null)? aprobacion.getEstadoByResponsable(): null;
        String estadoOperacion = (operacion != null)? operacion.getEstadoByOperador(): null;

        solicitudSoliciResponse.setEstadoResponsable(estadoAprobacion);
        solicitudSoliciResponse.setEstadoOperador(estadoOperacion);
        return solicitudSoliciResponse;
    }

    @GetMapping("/exportSolicitudDPF")
    public ResponseEntity<byte[]> exportPdf()
            throws IOException, JRException {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("solicitudPDF", "solicitudPDF.pdf");

        SolicitudReport solicitudReport = SolicitudReport.builder()
                .funcionarioTo("Juan Pérez")
                .funcionarioFrom("Maria García")
                .funcionarioToCargo("Director de Operaciones")
                .funcionarioFromCargo("Gerente de Recursos Humanos")
                .cite("CITE-2024-001")
                .fecha("2024-11-04")
                .nombreOrganizacion("Corporación XYZ")
                .build();

        return ResponseEntity.ok().headers(headers).body(solicitudServiceReport.exportToPdf(solicitudReport));

    }
}
