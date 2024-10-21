package com.prototipo.infrastructure.rest.controller;

import com.prototipo.application.useCase.FotocopiaService;
import com.prototipo.application.useCase.UsuarioService;
import com.prototipo.domain.model.*;
import com.prototipo.infrastructure.rest.request.*;
import com.prototipo.infrastructure.rest.response.ReporteResponse;
import com.prototipo.infrastructure.rest.response.RolResponse;
import com.prototipo.infrastructure.rest.response.UnidadResponse;
import com.prototipo.infrastructure.rest.response.UsuarioUnidadResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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

    @PostMapping(path = {"/listaDeUsuarios"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<UsuarioUnidadResponse>> listaDeUsuarios(@RequestBody PaginacionAdminRequest pageReq){
        List<UsuarioUnidad> listUserDomain = usuarioService
                .listaDeUsuariosServiceDef(pageReq.getPage(), pageReq.getSize());

        List<UsuarioUnidadResponse> listResponse = listUserDomain.stream()
                .map(this::mapeoUsuarioUnidadToResponse)
                .collect(Collectors.toMap(
                        UsuarioUnidadResponse::getIdUser,  // Clave para agrupar, aquí usamos idUser
                        usuario -> usuario,               // Valor, el propio usuario
                        (existing, replacement) -> existing)) // Si hay duplicados, mantén el primero encontrado
                .values()                             // Obtenemos los valores del Map
                .stream()                             // Los convertimos de nuevo a stream
                .toList();
        return new ResponseEntity<>(listResponse, HttpStatus.OK);
    }

    private UsuarioUnidadResponse mapeoUsuarioUnidadToResponse(UsuarioUnidad userUni) {
        return UsuarioUnidadResponse.builder()
                .id(userUni.getId())
                .isActive(userUni.getIsActive())

                .idUser(userUni.getFkUsuario().getId())
                .nombres(userUni.getFkUsuario().getNombres())
                .apellidos(userUni.getFkUsuario().getApellidos())
                .correo(userUni.getFkUsuario().getCorreo())

                .nombreRol(userUni.getFkRol().getNombreRol())
                .nombreUnidad(
                        (userUni.getFkUnidad() != null)?
                                userUni.getFkUnidad().getNombre():
                                "Administrador"
                )

                .idRol(userUni.getFkRol().getId())
                .idUni((userUni.getFkUnidad() != null)? userUni.getFkUnidad().getId(): null)
                .build();
    }

    @PostMapping(path = {"/crearUsuario"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void crearUsuario(@RequestBody UsuarioNuevoRequest newUser){
        /* TODO, para el caso del operador, pues un piso esta lleno de unidades/area
        * asi que tendria que guardarse todas las unidades/area que sea han asigando a un operador,
        * en definitiva, este metodo deberia repetirse desde el frontend N unidades/area asignadas */

        Long rolId = newUser.getIdRol();
        Long idUni = newUser.getIdUni();
        Usuario usuario = modelMapper.map(newUser, Usuario.class);
        fotocopiaService.creaUsuario(usuario, rolId, idUni);
    }

    @GetMapping(path = {"/eliminarUsuario/{idUsuario}"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void eliminarUsuario(@PathVariable Long idUsuario){
        fotocopiaService.eliminarUsuario(idUsuario);
    }

    @PostMapping(path = {"/editarUsuario"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void editarUsuario(@RequestBody UsuarioUnidadRequest request){
        //TODO, hacer para Auditoria
        Usuario usuario = Usuario.builder()
                .id(request.getIdUser())
                .nombres(request.getNombres())
                .apellidos(request.getApellidos())
                .correo(request.getCorreo())
                .build();

        //Haciendo el truco de los Ids ;D
        Unidad unidad = Unidad.builder().id(request.getIdUni()).build();
        Rol rol = Rol.builder().id(request.getIdRol()).build();

        UsuarioUnidad editUserUni = UsuarioUnidad.builder()
                .id(request.getId())
                .isActive(request.getIsActive())
                .fkUsuario(usuario)
                .fkUnidad(unidad)
                .fkRol(rol)
                .build();

        fotocopiaService.editarUsuarioUnidad(editUserUni);
    }

    @GetMapping(path = {"/listarRoles"})
    public ResponseEntity<List<RolResponse>> listarRoles(){
        List<RolResponse> listaRoles = fotocopiaService.listarRolesService()
                .stream()
                .map(x -> modelMapper.map(x, RolResponse.class))
                .toList();
        return new ResponseEntity<>(listaRoles, HttpStatus.OK);
    }

    @GetMapping(path = {"/listarUnidades"})
    public ResponseEntity<List<UnidadResponse>> listarUnidades(){
        List<UnidadResponse> listaUnidades = fotocopiaService.listarUnidadesService()
                .stream()
                .map(x -> modelMapper.map(x, UnidadResponse.class))
                .toList();
        return new ResponseEntity<>(listaUnidades, HttpStatus.OK);
    }

    @PostMapping(path = {"/cambiarPass"})
    public void cambiarPass(@RequestBody NuevoPassRequest request){
        Credencial credencial = Credencial.builder()
                .correo(request.getCorreo())
                .pass(request.getPass())
                .build();
        fotocopiaService.cambiarPass(credencial, request.getNuevoPass());
    }

    @GetMapping(path = {"/generarReporte"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ReporteResponse> generarReporte(){
        //TODO
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
