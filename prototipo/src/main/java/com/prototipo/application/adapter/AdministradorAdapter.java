package com.prototipo.application.adapter;

import com.prototipo.application.model.PaginableIn;
import com.prototipo.application.model.PaginableOut;
import com.prototipo.application.port.in.AdministradorService;
import com.prototipo.application.port.out.persistence.*;
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

        //Buscamos el id del userUnidad la parte del usuario que quire ser responsable
        Rol role = this.rolAbstract.buscarRolPorId(userUnidad.getFkRol().getId());

        //Si es la opcion de responsable, entonces este no tiene responsable la primera vez
        if (role.getNombreRol().equals("Responsable")) {
            //Buscamos el responsable existente activo y el ultimo
            UsuarioUnidad usuarioUnidad = this.usuarioUnidadAbstract
                    .encontrarUsuarioUnidadResponsableActivo();
            if (usuarioUnidad == null) {
                userUnidad.setFkResponsable(null);
                userUnidad.setId(null);
                userUnidad.setIsActive(true);
            } else {
                throw new RuntimeException("Ya existe un responsable, solo puede haber uno, elimine el usuario que es responsable si desea un responsable nuevo");
            }
        }

        //Si es opcion Solicitante, entonces deberia ponerle el director, y el responsable
        if (role.getNombreRol().equals("Solicitante")) {
            //Buscamos el responsable existente activo y el ultimo
            UsuarioUnidad usuarioUnidad = this.usuarioUnidadAbstract
                    .encontrarUsuarioUnidadResponsableActivo();

            if (usuarioUnidad == null) {
                throw new RuntimeException("No hay un Responsable activo. Por favor cree un reponsable antes de crear un funcionario comun");
            }

            userUnidad.setFkResponsable(usuarioUnidad);

            userUnidad.setId(null);
            userUnidad.setIsActive(true);
        }

        userUnidad.setFkUsuario(usuarioResp);
        this.usuarioUnidadAbstract.guardarUsuarioUnidad(userUnidad);
    }


    //TODO, PAra la parte de Editar
    /*Si el administrador prdente crear otro responsable, pues este no debe poderse crear ya que
    * pues se debe verificar si ya hay un responasble activo, si hay un responsable activo, entonces no puede crear un
    * registro, y ademas, ademas, eso tendria que ser en la parte de crear un responsable,
    * indicar que ya existe, y si si quiere crear un responsable, pues que deba eliminar ese responsable, y luego crear el
    * nuevo responsable*/

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
                    .pass("f" + usuarioResp.getCi())
                    .fkUsuario(usuarioResp)
                    .build();
            credencialAbstract.guardarCredencialAbstract(newCredencial);
        } else {
            credencialResp.setPass("f" + usuarioResp.getCi());
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
