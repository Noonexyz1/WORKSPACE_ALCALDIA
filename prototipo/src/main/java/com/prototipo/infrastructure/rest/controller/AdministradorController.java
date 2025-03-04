package com.prototipo.infrastructure.rest.controller;

import com.prototipo.application.model.PaginableIn;
import com.prototipo.application.model.PaginableOut;
import com.prototipo.application.port.in.AdministradorService;
import com.prototipo.domain.model.*;
import com.prototipo.infrastructure.rest.request.*;
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
@RequestMapping(path = "/administrador")
public class AdministradorController {

    @Autowired
    private AdministradorService administradorService;
    @Autowired
    private ModelMapper modelMapper;


    @PostMapping(
            path = {"/listaDeUsuarios"},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<PageResponse<UsuarioUnidadResponse>> listaDeUsuarios(
            @RequestBody PageRequest pageReq) {

        PaginableOut<UsuarioUnidad> paginableOut = administradorService
                .listaDeUsuarios(modelMapper.map(pageReq, PaginableIn.class));

        List<UsuarioUnidadResponse> listResponse = paginableOut.getContent()
                .stream()
                .map(this::mapeoUsuarioUnidadToResponse)
                .toList();

        PageResponse<UsuarioUnidadResponse> pageResponse = PageResponse
                .<UsuarioUnidadResponse>builder()
                .page(pageReq.getPage().intValue())
                .size(pageReq.getSize().intValue())
                .sortBy(pageReq.getSortBy())
                .direction(pageReq.getDirection())

                .content(listResponse)
                .totalPages(paginableOut.getTotalPages())
                .totalElements(paginableOut.getTotalElements())
                .build();

        return new ResponseEntity<>(pageResponse, HttpStatus.OK);
    }

    private UsuarioUnidadResponse mapeoUsuarioUnidadToResponse(UsuarioUnidad userUni) {
        return UsuarioUnidadResponse.builder()
                .id(userUni.getId())
                .isActive(userUni.getIsActive())

                .idUser((userUni.getFkUsuario() != null)? userUni.getFkUsuario().getId(): null)
                .nombres((userUni.getFkUsuario() != null)? userUni.getFkUsuario().getNombres(): null)
                .materno((userUni.getFkUsuario() != null)? userUni.getFkUsuario().getMaterno(): null)
                .paterno((userUni.getFkUsuario() != null)? userUni.getFkUsuario().getPaterno(): null)
                .correo((userUni.getFkUsuario() != null)? userUni.getFkUsuario().getCorreo(): null)
                .ci((userUni.getFkUsuario() != null)? userUni.getFkUsuario().getCi(): null)

                .nombreRol(userUni.getFkRol().getNombreRol())
                .nombreUnidad((userUni.getFkUnidad() != null)? userUni.getFkUnidad().getNombre(): "Unidad Servicios Generales")
                .nombreCargo((userUni.getFkCargo() != null)? userUni.getFkCargo().getNombreCargo(): "Unidad Servicios Generales")

                .idRol(userUni.getFkRol().getId())
                .idUni((userUni.getFkUnidad() != null)? userUni.getFkUnidad().getId(): null)
                .idCargo(userUni.getFkCargo().getId())
                .idResponsable(userUni.getFkResponsable() != null ? userUni.getFkResponsable().getId(): null)
                .idDirector(userUni.getFkDirector() != null ? userUni.getFkDirector().getId(): null)
                .build();
    }

    @PostMapping(
            path = {"/crearUsuario"},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public void crearUsuario(@RequestBody UsuarioUnidadEditRequest newUser){
        Usuario usuario = modelMapper.map(newUser, Usuario.class);
        UsuarioUnidad usuarioUnidad = usuarioUnidadBuilder(newUser);
        administradorService.crearUsuarioUnidad(usuario, usuarioUnidad);
    }

    @PostMapping(
            path = {"/editarUsuario"},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public void editarUsuario(@RequestBody UsuarioUnidadEditRequest editUser) {
        Usuario usuario = modelMapper.map(editUser, Usuario.class);
        UsuarioUnidad usuarioUnidad = usuarioUnidadBuilder(editUser);
        administradorService.crearUsuarioUnidad(usuario, usuarioUnidad);
    }

    private UsuarioUnidad usuarioUnidadBuilder(UsuarioUnidadEditRequest user){
        return UsuarioUnidad.builder()
                .fkRol(Rol.builder().id(user.getIdRol()).build())
                .fkUnidad(Unidad.builder().id(user.getIdUni()).build())
                .fkCargo(Cargo.builder().id(user.getIdCargo()).build())
                .fkResponsable(UsuarioUnidad.builder().id(user.getIdResponsable()).build())
                .fkDirector(UsuarioUnidad.builder().id(user.getIdDirector()).build())
                .build();
    }

    @GetMapping(
            path = {"/eliminarFuncionario/{idUsuarioUnidad}"},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public void eliminarUsuario(@PathVariable Long idUsuarioUnidad){
        administradorService.eliminarUsuarioUnidad(idUsuarioUnidad);
    }

    @GetMapping(
            path = {"/listarRoles"},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<RolResponse>> listarRoles(){
        List<RolResponse> listaRoles = administradorService
                .listaDeRoles()
                .stream()
                .map(x -> modelMapper.map(x, RolResponse.class))
                .toList();
        return new ResponseEntity<>(listaRoles, HttpStatus.OK);
    }

    @GetMapping(
            path = {"/listarUnidades"},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<UnidadResponse>> listarUnidades(){
        List<UnidadResponse> listaUnidades = administradorService
                .listaDeUnidades()
                .stream()
                .map(x -> modelMapper.map(x, UnidadResponse.class))
                .toList();
        return new ResponseEntity<>(listaUnidades, HttpStatus.OK);
    }

    @GetMapping(
            path = {"/listarCargos"},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<CargoResponse>> listarCargos() {
        List<Cargo> listaCargos = administradorService
                .listaDeCargos();
        List<CargoResponse> listaCargosResp = listaCargos.stream()
                .map(x -> modelMapper.map(x, CargoResponse.class))
                .toList();
        return new ResponseEntity<>(listaCargosResp, HttpStatus.OK);
    }
}
