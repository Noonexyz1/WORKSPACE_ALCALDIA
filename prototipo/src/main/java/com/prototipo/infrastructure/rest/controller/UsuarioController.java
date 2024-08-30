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
@RequestMapping(path = "/usuario")
public class UsuarioController {

    @PostMapping(path = {"/solicitarFotocopiar"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void solicitarFotocopiar(@RequestBody SolicitudRequest request) {

    }

    @GetMapping(path = {"/verHistorialSolicitudes"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<SolicitudResponse>> verHistorialSolicitudes() {
        List<SolicitudResponse> list = new ArrayList<>();
        list.add(SolicitudResponse.builder()
                        .id(345345L)
                        .archivoPdf("asdfasdf2342asdfasdfasdf")
                .build());
        list.add(SolicitudResponse.builder()
                .id(345345L)
                .archivoPdf("asdfasdf2342asdfasdfasdf")
                .build());
        list.add(SolicitudResponse.builder()
                .id(345345L)
                .archivoPdf("asdfasdf2342asdfasdfasdf")
                .build());

        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
