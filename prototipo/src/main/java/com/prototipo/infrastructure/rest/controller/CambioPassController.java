package com.prototipo.infrastructure.rest.controller;

import com.prototipo.application.useCase.FotocopiaService;
import com.prototipo.domain.model.Credencial;
import com.prototipo.infrastructure.rest.request.NuevoPassRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

        fotocopiaService.cambiarPass(
                credencial,
                request.getNuevoPass()
        );
    }
}
