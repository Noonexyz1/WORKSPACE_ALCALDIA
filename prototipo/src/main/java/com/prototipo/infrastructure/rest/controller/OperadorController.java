package com.prototipo.infrastructure.rest.controller;

import com.prototipo.application.useCase.OperadorService;
import com.prototipo.infrastructure.rest.response.SolicitudOperaResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/operador")
public class OperadorController {

    @Autowired
    private OperadorService operadorService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping(path = {"/setSolicitudCompletada/{idSolicitud}"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void setEstadoSolicitud(@PathVariable Long idSolicitud) {
        operadorService.cambiarEstadoDeSolicitud(idSolicitud);
    }

    @GetMapping(path = {"/verSolicitudes"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<SolicitudOperaResponse>> verSolicitudes() {
        List<SolicitudOperaResponse> list = operadorService.verSolicitudes()
                .stream()
                .map(x -> modelMapper.map(x, SolicitudOperaResponse.class))
                .toList();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
