package com.prototipo.infrastructure.rest.controller;

import com.prototipo.application.useCase.*;
import com.prototipo.domain.model.*;
import com.prototipo.infrastructure.rest.request.PaginacionSoliRequest;
import com.prototipo.infrastructure.rest.request.SolicitudRequest;
import com.prototipo.infrastructure.rest.response.SolicitudSoliciResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(path = {"/verHistorialSolicitudes"}, produces = {MediaType.APPLICATION_JSON_VALUE})
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
        return solicitudSoliciResponse;
    }
}
