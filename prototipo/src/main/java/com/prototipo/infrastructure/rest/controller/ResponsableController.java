package com.prototipo.infrastructure.rest.controller;

import com.prototipo.infrastructure.rest.request.SolicitudRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/responsable")
public class ResponsableController {
    
    @PostMapping(path = {"/aprobarSolicitud"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void aprobarSolicitud(@RequestBody SolicitudRequest request) {

    }
}
