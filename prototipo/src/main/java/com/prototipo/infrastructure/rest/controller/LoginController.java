package com.prototipo.infrastructure.rest.controller;

import com.prototipo.infrastructure.persistence.db.entity.Credencial;
import com.prototipo.infrastructure.persistence.db.entity.DashboardConfig;
import com.prototipo.infrastructure.persistence.db.entity.Rol;
import com.prototipo.infrastructure.persistence.db.entity.Usuario;
import com.prototipo.infrastructure.persistence.db.repository.CredencialRepository;
import com.prototipo.infrastructure.rest.request.CredencialRequest;
import com.prototipo.infrastructure.rest.response.UsuarioResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 86400)
//@Validated
@RestController
@RequestMapping(path = "/dologin")
public class LoginController {

    @Autowired
    private CredencialRepository credencialRepository;

    @PostMapping(path = {""}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<UsuarioResponse> iniciarSesion(@RequestBody CredencialRequest request){
        Credencial credencial = credencialRepository.findById(1L).get();
        Usuario usuario = credencial.getFk_usuario();
        Rol rol = usuario.getFk_rol();
        List<DashboardConfig> dashboardConfig = rol.getListDashConfig();

        List<String> listaConfig = dashboardConfig.stream().map(DashboardConfig::getNombreComponente).toList();

        UsuarioResponse usuarioResponse = UsuarioResponse.builder()
                .nombres(usuario.getNombres())
                .apellidos(usuario.getApellidos())
                .nombreRol(rol.getNombreRol())
                .listDashConfig(listaConfig)
                .build();

        return new ResponseEntity<>(usuarioResponse, HttpStatus.OK);
    }
}
