package com.prototipo.application.adapter;

import com.prototipo.application.model.PaginableIn;
import com.prototipo.application.model.PaginableOut;
import com.prototipo.application.port.out.*;
import com.prototipo.application.port.in.AdministradorService;
import com.prototipo.domain.model.*;

import java.util.List;

public class AdministradorAdapter implements AdministradorService {

    // IMPORTANTE: Si es arquitectura hexagonal, no deberias tener mapeadores ya que para este
    // packete application y packete dominio pues se entiende que unicamente se necesita el model del dominio
    // ya que asi le pedimos a las otras capas que nos envien datos mediante la interfaz port out o abstractas
    private UsuarioAbastract usuarioAbastract;
    private RolAbstract rolAbstract;
    private CredencialAbstract credencialAbstract;
    private UnidadAbstract unidadAbstract;
    private UsuarioUnidadAbstract usuarioUnidadAbstract;
    private CargoAbstract cargoAbstract;

    public AdministradorAdapter(
            UsuarioAbastract usuarioAbastract,
            RolAbstract rolAbstract,
            CredencialAbstract credencialAbstract,
            UnidadAbstract unidadAbstract,
            UsuarioUnidadAbstract usuarioUnidadAbstract,
            CargoAbstract cargoAbstract) {

        this.usuarioAbastract = usuarioAbastract;
        this.rolAbstract = rolAbstract;
        this.credencialAbstract = credencialAbstract;
        this.unidadAbstract = unidadAbstract;
        this.usuarioUnidadAbstract = usuarioUnidadAbstract;
        this.cargoAbstract = cargoAbstract;
    }


    @Override
    public PaginableOut<UsuarioUnidad> listaDeUsuarios(PaginableIn paginableIn) {
        PaginableOut<UsuarioUnidad> paginableOut = usuarioUnidadAbstract
                .listaDeUsuariosAbsDef(paginableIn);

        PaginableOut<UsuarioUnidad> paginableResponse = PaginableOut
                .<UsuarioUnidad>builder()
                .content(paginableOut.getContent())
                .totalPages(paginableOut.getTotalPages())
                .totalElements(paginableOut.getTotalElements())
                .build();

        return paginableResponse;
    }

    @Override
    public void crearUsuarioUnidad(Usuario user, UsuarioUnidad userUnidad){
        //Verificar primero si ya existe el usuario registrado
        Usuario usuarioResp = crearUsuario(user);

        // insertamos las credenciales a la BD correspondientes para el nuevo usuario
        crearCredencial(usuarioResp);

        // buscamos un responsable activo
        UsuarioUnidad responsable = usuarioUnidadAbstract.encontrarUsuarioUnidadUltimoActivo();

        userUnidad.setFkResponsable(UsuarioUnidad.builder().id(responsable.getId()).build());
        userUnidad.setId(null);
        userUnidad.setIsActive(true);
        userUnidad.setFkUsuario(usuarioResp);

        //Este metodo unicamente evalua si existe el usuario nuevo en unidadUsuario o no
        UsuarioUnidad userRespon = existeUsuarioUnidad(usuarioResp);

        if (userRespon == null) {
            usuarioUnidadAbstract.guardarUsuarioUnidad(userUnidad);
        } else {
            if (!hayCambioUserUni(userUnidad, userRespon)) {
                userRespon.setIsActive(false);
                usuarioUnidadAbstract.guardarUsuarioUnidad(userRespon);
                usuarioUnidadAbstract.guardarUsuarioUnidad(userUnidad);
            }
        }

    }

    private boolean hayCambioUserUni(UsuarioUnidad userUniNew, UsuarioUnidad userRespon){
        if (userRespon.getFkResponsable() == null &&
                userRespon.getFkDirector() == null) {
            return userRespon.getFkUsuario().getId() == userUniNew.getFkUsuario().getId() &&
                    userRespon.getFkUnidad().getId() == userUniNew.getFkUnidad().getId() &&
                    userRespon.getFkRol().getId() == userUniNew.getFkRol().getId() &&
                    userRespon.getFkCargo().getId() == userUniNew.getFkCargo().getId();
        }
        if (userRespon.getFkResponsable() == null) {
            return userRespon.getFkUsuario().getId() == userUniNew.getFkUsuario().getId() &&
                    userRespon.getFkUnidad().getId() == userUniNew.getFkUnidad().getId() &&
                    userRespon.getFkRol().getId() == userUniNew.getFkRol().getId() &&
                    userRespon.getFkCargo().getId() == userUniNew.getFkCargo().getId() &&
                    userRespon.getFkDirector().getId() == userUniNew.getFkDirector().getId();
        }

        return userRespon.getFkUsuario().getId() == userUniNew.getFkUsuario().getId() &&
                userRespon.getFkUnidad().getId() == userUniNew.getFkUnidad().getId() &&
                userRespon.getFkRol().getId() == userUniNew.getFkRol().getId() &&
                userRespon.getFkCargo().getId() == userUniNew.getFkCargo().getId() &&
                userRespon.getFkDirector().getId() == userUniNew.getFkDirector().getId() &&
                userRespon.getFkResponsable().getId() == userUniNew.getFkResponsable().getId();

    }

    private void crearCredencial(Usuario usuarioResp) {
        Credencial credencialResp = credencialAbstract
                .encontrarCredencialPorUsuarioId(usuarioResp.getId());
        if (credencialResp == null) {
            Credencial newCredencial = Credencial.builder()
                    .ci(usuarioResp.getCi())
                    .pass("funcionario" + usuarioResp.getCi())
                    .fkUsuario(usuarioResp)
                    .build();
            credencialAbstract.guardarCredencialAbstract(newCredencial);
        } else {
            credencialResp.setPass("funcionario" + usuarioResp.getCi());
            credencialAbstract.guardarCredencialAbstract(credencialResp);
        }
    }

    private Usuario crearUsuario(Usuario user) {
        // Por que tendria que buscar por id o ci?, si tiene ID entonces lo actualiza,
        // si no tiene id, entonces lo crea, NO NECESITAS UN IF(){}

        UsuarioUnidad siExisteUsuario = existeUsuarioUnidadByCi(user.getCi());
        if (siExisteUsuario != null) {
            return siExisteUsuario.getFkUsuario();
        }
        return usuarioAbastract.guardarUsuarioAbastract(user);
    }

    private UsuarioUnidad existeUsuarioUnidadByCi(String ciUser) {
        return usuarioUnidadAbstract
                .encontrarUsuarioUnidadByCi(ciUser);
    }

    private UsuarioUnidad existeUsuarioUnidad(Usuario usuario) {
        return usuarioUnidadAbstract
                .encontrarUsuarioUnidadByUsuarioId(usuario.getId());
    }

    @Override
    public void eliminarUsuarioUnidad(Long idUsuarioUnidad) {
        UsuarioUnidad usuarioUnidad = usuarioUnidadAbstract
                .encontarUsuarioUnidadId(idUsuarioUnidad);
        usuarioUnidad.setIsActive(false);
        usuarioUnidadAbstract.guardarUsuarioUnidad(usuarioUnidad);
    }

    @Override
    public List<Rol> listaDeRoles() {
        List<Rol> listaRolesDto = rolAbstract.listarRoles();
        return listaRolesDto;
    }

    @Override
    public List<Unidad> listaDeUnidades() {
        List<Unidad> listaUnidades = unidadAbstract.listaDeUnidadesAbstract();
        return listaUnidades;
    }

    @Override
    public List<Cargo> listaDeCargos() {
        List<Cargo> listCargos = cargoAbstract.findAllCargos();
        return listCargos;
    }
}
