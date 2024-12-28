package com.prototipo.infrastructure.rest.controller;

import com.prototipo.application.pager.PaginableIn;
import com.prototipo.application.pager.PaginableOut;
import com.prototipo.application.useCase.FotocopiaService;
import com.prototipo.application.useCase.UsuarioService;
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
    private UsuarioService usuarioService;
    @Autowired
    private FotocopiaService fotocopiaService;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping(path = {"/listaDeUsuarios"},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<PageResponse<UsuarioUnidadResponse>> listaDeUsuarios(
            @RequestBody PageRequest pageReq) {

        PaginableOut<UsuarioUnidad> paginableOut = usuarioService
                .listaDeUsuariosServiceDef(
                        modelMapper.map(pageReq, PaginableIn.class)
                );

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

                .idUser(userUni.getFkUsuario().getId())
                .nombres(userUni.getFkUsuario().getNombres())
                .paterno(userUni.getFkUsuario().getPaterno())
                .materno(userUni.getFkUsuario().getMaterno())
                .correo(userUni.getFkUsuario().getCorreo())

                .nombreRol(userUni.getFkRol().getNombreRol())
                .nombreUnidad(
                        (userUni.getFkUnidad() != null)?
                                userUni.getFkUnidad().getNombre():
                                "Unidad Servicios Generales"
                )
                .idRol(userUni.getFkRol().getId())
                .idUni((userUni.getFkUnidad() != null)? userUni.getFkUnidad().getId(): null)
                .nombreCargo(
                        (userUni.getFkCargo() != null)?
                        userUni.getFkCargo().getNombreCargo():
                        "Unidad Servicios Generales"
                )
                .ci(userUni.getFkUsuario().getCi())
                .build();
    }

    @PostMapping(path = {"/crearUsuario"},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public void crearUsuario(@RequestBody UsuarioNuevoRequest newUser){
        Long idRol = newUser.getIdRol();
        Long idUni = newUser.getIdUni();
        Long idCargo = newUser.getIdCargo();

        Long idResponsable = newUser.getIdResponsable();
        Long idDirector = newUser.getIdDirector();

        Usuario usuario = modelMapper.map(newUser, Usuario.class);
        fotocopiaService.creaUsuario(
                usuario,
                idRol,
                idUni,
                idCargo,
                idResponsable,
                idDirector);

    }

    @GetMapping(path = {"/eliminarFuncionario/{idUsuarioUnidad}"},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public void eliminarUsuario(@PathVariable Long idUsuarioUnidad){
        fotocopiaService.eliminarUsuario(idUsuarioUnidad);
    }

    @PostMapping(path = {"/editarUsuario"},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public void editarUsuario(@RequestBody UsuarioRequest request){
        Usuario usuario = modelMapper.map(request, Usuario.class);
        fotocopiaService.editarUsuarioUnidad(usuario);
    }

    @GetMapping(path = {"/listarRoles"})
    public ResponseEntity<List<RolResponse>> listarRoles(){
        List<RolResponse> listaRoles = fotocopiaService
                .listarRolesService()
                .stream()
                .map(x -> modelMapper.map(x, RolResponse.class))
                .toList();
        return new ResponseEntity<>(listaRoles, HttpStatus.OK);
    }

    @GetMapping(path = {"/listarUnidades"})
    public ResponseEntity<List<UnidadResponse>> listarUnidades(){
        List<UnidadResponse> listaUnidades = fotocopiaService
                .listarUnidadesService()
                .stream()
                .map(x -> modelMapper.map(x, UnidadResponse.class))
                .toList();
        return new ResponseEntity<>(listaUnidades, HttpStatus.OK);
    }

    @GetMapping(path = {"/listarCargos"})
    public ResponseEntity<List<CargoResponse>> listarCargos() {
        List<Cargo> listaCargos = fotocopiaService
                .listarCargosService();
        List<CargoResponse> listaCargosResp = listaCargos.stream()
                .map(x -> modelMapper.map(x, CargoResponse.class))
                .toList();
        return new ResponseEntity<>(listaCargosResp, HttpStatus.OK);
    }

    @PostMapping(path = {"/cambiarPass"})
    public void cambiarPass(@RequestBody NuevoPassRequest request){
        Credencial credencial = Credencial.builder()
                .ci(request.getCi())
                .pass(request.getPass())
                .build();
        fotocopiaService.cambiarPass(credencial, request.getNuevoPass());
    }

    @GetMapping(path = {"/generarReporte"},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ReporteResponse> generarReporte(){
        //TODO, generar reporte
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
