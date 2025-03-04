package com.prototipo.application.adapter;

import com.prototipo.application.mapper.MapperApplicationAbstract;
import com.prototipo.application.modelDto.*;
import com.prototipo.application.pager.PaginableIn;
import com.prototipo.application.pager.PaginableOut;
import com.prototipo.application.port.out.*;
import com.prototipo.application.port.in.AdministradorService;
import com.prototipo.domain.model.*;

import java.util.List;

public class AdministradorAdapter implements AdministradorService {

    private UsuarioAbastract usuarioAbastract;
    private RolAbstract rolAbstract;
    private MapperApplicationAbstract mapperApplicationAbstract;
    private CredencialAbstract credencialAbstract;
    private UnidadAbstract unidadAbstract;
    private UsuarioUnidadAbstract usuarioUnidadAbstract;
    private CargoAbstract cargoAbstract;

    public AdministradorAdapter(
            UsuarioAbastract usuarioAbastract,
            RolAbstract rolAbstract,
            MapperApplicationAbstract mapperApplicationAbstract,
            CredencialAbstract credencialAbstract,
            UnidadAbstract unidadAbstract,
            UsuarioUnidadAbstract usuarioUnidadAbstract,
            CargoAbstract cargoAbstract) {

        this.usuarioAbastract = usuarioAbastract;
        this.rolAbstract = rolAbstract;
        this.mapperApplicationAbstract = mapperApplicationAbstract;
        this.credencialAbstract = credencialAbstract;
        this.unidadAbstract = unidadAbstract;
        this.usuarioUnidadAbstract = usuarioUnidadAbstract;
        this.cargoAbstract = cargoAbstract;
    }


    @Override
    public PaginableOut<UsuarioUnidad> listaDeUsuarios(PaginableIn paginableIn) {
        PaginableOut<UsuarioUnidadDto> paginableOut = usuarioUnidadAbstract
                .listaDeUsuariosAbsDef(paginableIn);

        PaginableOut<UsuarioUnidad> paginableResponse = PaginableOut
                .<UsuarioUnidad>builder()
                .content(
                        paginableOut.getContent()
                                .stream()
                                .map(x -> mapperApplicationAbstract
                                        .mapearAbstract(x, UsuarioUnidad.class)
                                )
                                .toList()
                )
                .totalPages(paginableOut.getTotalPages())
                .totalElements(paginableOut.getTotalElements())
                .build();

        return paginableResponse;
    }

    @Override
    public void crearUsuarioUnidad(Usuario user, UsuarioUnidad userUnidad){
        //Verificar primero si ya existe el usuario registrado
        UsuarioDto usuarioDtoResp = crearUsuario(user);

        // insertamos las credenciales a la BD correspondientes para el nuevo usuario
        crearCredencial(usuarioDtoResp);

        //El trucaso de los IDs
        UsuarioUnidadDto userUniNew = mapperApplicationAbstract
                .mapearAbstract(userUnidad, UsuarioUnidadDto.class);

        userUniNew.setId(null);
        userUniNew.setIsActive(true);
        userUniNew.setFkUsuario(usuarioDtoResp);

        //Este metodo unicamente evalua si existe el usuario nuevo en unidadUsuario o no
        UsuarioUnidadDto userRespon = existeUsuarioUnidad(usuarioDtoResp);

        if (userRespon == null) {
            usuarioUnidadAbstract.guardarUsuarioUnidad(userUniNew);
        } else {
            if (!hayCambioUserUni(userUniNew, userRespon)) {
                userRespon.setIsActive(false);
                usuarioUnidadAbstract.guardarUsuarioUnidad(userRespon);
                usuarioUnidadAbstract.guardarUsuarioUnidad(userUniNew);
            }
        }

    }

    private boolean hayCambioUserUni(UsuarioUnidadDto userUniNew, UsuarioUnidadDto userRespon){
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

    private void crearCredencial(UsuarioDto usuarioDtoResp) {
        CredencialDto credencialDtoResp = credencialAbstract
                .encontrarCredencialPorUsuarioId(usuarioDtoResp.getId());
        if (credencialDtoResp == null) {
            CredencialDto newCredencialDto = CredencialDto.builder()
                    .ci(usuarioDtoResp.getCi())
                    .pass("funcionario" + usuarioDtoResp.getCi())
                    .fkUsuario(usuarioDtoResp)
                    .build();
            credencialAbstract.guardarCredencialAbstract(newCredencialDto);
        } else {
            credencialDtoResp.setPass("funcionario" + usuarioDtoResp.getCi());
            credencialAbstract.guardarCredencialAbstract(credencialDtoResp);
        }
    }

    private UsuarioDto crearUsuario(Usuario user) {
        // Por que tendria que buscar por id o ci?, si tiene ID entonces lo actualiza,
        // si no tiene id, entonces lo crea, NO NECESITAS UN IF(){}
        UsuarioDto usuarioDto = mapperApplicationAbstract
                .mapearAbstract(user, UsuarioDto.class);

        UsuarioUnidadDto siExisteUsuario = existeUsuarioUnidadByCi(usuarioDto.getCi());
        if (siExisteUsuario != null) {
            return siExisteUsuario.getFkUsuario();
        }
        return usuarioAbastract.guardarUsuarioAbastract(usuarioDto);
    }

    private UsuarioUnidadDto existeUsuarioUnidadByCi(String ciUser) {
        return usuarioUnidadAbstract
                .encontrarUsuarioUnidadByCi(ciUser);
    }

    private UsuarioUnidadDto existeUsuarioUnidad(UsuarioDto usuarioDto) {
        return usuarioUnidadAbstract
                .encontrarUsuarioUnidadByUsuarioId(usuarioDto.getId());
    }

    @Override
    public void eliminarUsuarioUnidad(Long idUsuarioUnidad) {
        UsuarioUnidadDto usuarioUnidadDto = usuarioUnidadAbstract
                .encontarUsuarioUnidadId(idUsuarioUnidad);
        usuarioUnidadDto.setIsActive(false);
        usuarioUnidadAbstract.guardarUsuarioUnidad(usuarioUnidadDto);
    }

    @Override
    public List<Rol> listaDeRoles() {
        List<RolDto> listaRolesDto = rolAbstract.listarRoles();
        return listaRolesDto.stream()
                .map(x -> mapperApplicationAbstract
                        .mapearAbstract(x, Rol.class))
                .toList();
    }

    @Override
    public List<Unidad> listaDeUnidades() {
        List<UnidadDto> listaUnidades = unidadAbstract.listaDeUnidadesAbstract();
        return listaUnidades.stream()
                .map(x -> mapperApplicationAbstract
                        .mapearAbstract(x, Unidad.class))
                .toList();
    }

    @Override
    public List<Cargo> listaDeCargos() {
        List<CargoDto> listCargos = cargoAbstract.findAllCargos();
        return listCargos.stream()
                .map(x -> mapperApplicationAbstract.mapearAbstract(x, Cargo.class))
                .toList();
    }
}
