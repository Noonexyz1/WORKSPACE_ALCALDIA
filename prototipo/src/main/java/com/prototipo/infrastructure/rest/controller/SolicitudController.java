package com.prototipo.infrastructure.rest.controller;

import com.prototipo.application.useCase.SolicitudService;
import com.prototipo.application.useCase.UnidadService;
import com.prototipo.application.useCase.UsuarioService;
import com.prototipo.domain.model.ArchivoPdfDomain;
import com.prototipo.domain.model.SolicitudDomain;
import com.prototipo.domain.model.UnidadDomain;
import com.prototipo.domain.model.UsuarioDomain;
import com.prototipo.infrastructure.rest.request.SolicitudRequest;
import com.prototipo.infrastructure.rest.response.SolicitudSoliciResponse;
import com.prototipo.infrastructure.rest.response.UnidadResponse;
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
public class SolicitudController {

    @Autowired
    private SolicitudService solicitudService;
    @Autowired
    private UnidadService unidadService;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private ModelMapper modelMapper;


    @PostMapping(path = {"/solicitarFotocopiar"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void solicitarFotocopiar(@RequestBody SolicitudRequest solicitudRequest) {
        //Debo encontrar el Usuario
        UnidadDomain unidad = unidadService.findUnidadPorIdService(solicitudRequest.getIdUnidad());
        //Debo encontrar la unidad
        UsuarioDomain usuario = usuarioService.findUsuarioPorIdService(solicitudRequest.getIdSolicitante());

        List<ArchivoPdfDomain> archivoPdfs = solicitudRequest.getArchivosPdf().stream().map(x ->
                    ArchivoPdfDomain.builder()
                            .archivo(x)
                            .build()
                ).toList();

        //Debo usar los mappeadores de mi Infraestrucutura
        SolicitudDomain solicitud = SolicitudDomain.builder()
                .nroDeCopias(solicitudRequest.getNroDeCopias())
                .tipoDeDocumento(solicitudRequest.getTipoDeDocumento())
                .nroDePaginas(solicitudRequest.getNroDePaginas())
                .fkUnidad(unidad)
                .fkSolicitante(usuario)
                .build();

        solicitudService.solicitarFotocopiarService(solicitud, archivoPdfs);
    }


    @GetMapping(path = {"/verListaUnidades"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<UnidadResponse>> verListaDeUnidades() {
        List<UnidadResponse> unidadResponses = unidadService.listaDeUnidadesService()
                .stream()
                .map(x -> modelMapper.map(x, UnidadResponse.class))
                .toList();
        return new ResponseEntity<>(unidadResponses, HttpStatus.OK);
    }


    @GetMapping(path = {"/verHistorialSolicitudes"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<SolicitudSoliciResponse>> verHistorialSolicitudes() {
        List<SolicitudSoliciResponse> list = solicitudService.getListaSolicitudesService()
                .stream()
                .map(x -> modelMapper.map(x, SolicitudSoliciResponse.class))
                .toList();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
