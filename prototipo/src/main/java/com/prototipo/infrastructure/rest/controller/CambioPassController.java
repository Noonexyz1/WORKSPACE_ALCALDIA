package com.prototipo.infrastructure.rest.controller;

import com.prototipo.application.useCase.FotocopiaService;
import com.prototipo.application.useCase.UsuarioService;
import com.prototipo.domain.model.Cargo;
import com.prototipo.domain.model.Credencial;
import com.prototipo.domain.model.Usuario;
import com.prototipo.domain.model.UsuarioUnidad;
import com.prototipo.infrastructure.rest.request.NuevoPassRequest;
import com.prototipo.infrastructure.rest.request.PaginacionAdminRequest;
import com.prototipo.infrastructure.rest.request.UsuarioNuevoRequest;
import com.prototipo.infrastructure.rest.request.UsuarioRequest;
import com.prototipo.infrastructure.rest.response.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 86400)
@RestController
@RequestMapping(path = "/cambioPassService")
public class CambioPassController {

    @Autowired
    private FotocopiaService fotocopiaService;

    @PostMapping(path = {"/cambiarPass"})
    public void cambiarPass(@RequestBody NuevoPassRequest request){
        Credencial credencial = Credencial.builder()
                .ci(request.getCi())
                .pass(request.getPass())
                .build();
        fotocopiaService.cambiarPass(credencial, request.getNuevoPass());
    }
}
