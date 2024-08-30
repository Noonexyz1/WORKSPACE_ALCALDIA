package com.prototipo.infrastructure.rest.controller;

import com.prototipo.infrastructure.rest.request.SolicitudRequest;
import com.prototipo.infrastructure.rest.response.SolicitudResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/operador")
public class OperadorController {


    @PostMapping(path = {"/setEstadoSolicitud"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void setEstadoSolicitud(@RequestBody SolicitudRequest request) {

    }

    @GetMapping(path = {"/verSolicitudes"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<SolicitudResponse>> verSolicitudes() {
        List<SolicitudResponse> listRespon = new ArrayList<>();
        listRespon.add(SolicitudResponse.builder()
                        .id(24L)
                        .nroDeCopias(234L)
                        .build());
        listRespon.add(SolicitudResponse.builder()
                .id(24L)
                .nroDeCopias(234L)
                .build());
        listRespon.add(SolicitudResponse.builder()
                .id(24L)
                .nroDeCopias(234L)
                .build());

        return new ResponseEntity<>(listRespon, HttpStatus.OK);
    }

}
