package com.prototipo.infrastructure.rest.controller;

import com.prototipo.infrastructure.rest.request.UsuarioRequest;
import com.prototipo.infrastructure.rest.response.ReporteResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/administrador")
public class AdministradorController {

    @PostMapping(path = {"/creaUsuario"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void crearUsuario(@RequestBody UsuarioRequest request){

    }

    @PostMapping(path = {"/editarUsuario"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void editarUsuario(@RequestBody UsuarioRequest request){

    }

    @GetMapping(path = {"/eliminarUsuario/{idUsuario}"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void eliminarUsuario(@PathVariable Long idUsuario){

    }

    @GetMapping(path = {"/asignarOperador/{idOperador}"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void asignarOperador(@PathVariable Long idOperador){

    }

    @GetMapping(path = {"/generarReporte"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ReporteResponse> generarReporte(){
        ReporteResponse reporte = ReporteResponse.builder()
                .id(234L)
                .nombreUnidad("Servicios Generales Ejemplo")
                .nroFotoRealizadas(345L)
                .build();

        return new ResponseEntity<>(reporte, HttpStatus.OK);
    }
}
