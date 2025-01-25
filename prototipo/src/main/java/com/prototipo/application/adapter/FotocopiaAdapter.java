package com.prototipo.application.adapter;

import com.prototipo.application.mapper.MapperApplicationAbstract;
import com.prototipo.application.modelDto.*;
import com.prototipo.application.port.*;
import com.prototipo.application.useCase.FotocopiaService;
import com.prototipo.domain.model.*;

import java.util.List;

public class FotocopiaAdapter implements FotocopiaService {

    private UsuarioAbastract usuarioAbastract;
    private RolAbstract rolAbstract;
    private MapperApplicationAbstract mapperApplicationAbstract;
    private CredencialAbstract credencialAbstract;
    private UnidadAbstract unidadAbstract;
    private UsuarioUnidadAbstract usuarioUnidadAbstract;
    private CargoAbstract cargoAbstract;

    public FotocopiaAdapter(
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
    public void creaUsuario(
            Usuario user,
            Long idRol,
            Long idUni,
            Long idCargo,
            Long idResponsable,
            Long idDirector){

        //Verificar primero si ya existe el usuario registrado
        UsuarioDto usuarioDtoResp = crearUsuario(user);

        // insertamos las credenciales a la BD correspondientes para el nuevo usuario
        crearCredencial(usuarioDtoResp);

        // Creamos instancias de RolDto y UnidadDto usando únicamente sus IDs.
        // No es necesario realizar consultas adicionales a la base de datos para obtener el Rol o la Unidad
        // a partir de sus IDs, ya que JPA puede manejar estas referencias directamente con los IDs proporcionados.
        // Esto mejora el rendimiento al evitar llamadas innecesarias a la BD.
        // Que trucaso no????? jaja
        RolDto rolDto = RolDto.builder().id(idRol).build();
        UnidadDto unidadDto = UnidadDto.builder().id(idUni).build();
        CargoDto cargoDto = CargoDto.builder().id(idCargo).build();
        UsuarioUnidadDto usuarioUnidadDto = UsuarioUnidadDto.builder().id(idResponsable).build();
        UsuarioUnidadDto usuarioDirectorDto = UsuarioUnidadDto.builder().id(idDirector).build();

        UsuarioUnidadDto userUniNew = UsuarioUnidadDto.builder()
                .id(null)
                .isActive(true)
                .fkRol(rolDto)
                .fkUnidad(unidadDto)
                .fkCargo(cargoDto)
                .fkUsuario(usuarioDtoResp)
                .fkResponsable(usuarioUnidadDto)
                .fkDirector(usuarioDirectorDto)
                .build();

        UsuarioUnidadDto userRespon = existeUsuarioUnidad(usuarioDtoResp);
        if (userRespon == null) {
            usuarioUnidadAbstract.guardarUsuarioUnidad(userUniNew);
        } else {
            userRespon.setIsActive(false);
            usuarioUnidadAbstract.guardarUsuarioUnidad(userRespon);
            usuarioUnidadAbstract.guardarUsuarioUnidad(userUniNew);
        }

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
        //TODO. por que tendria que buscar por id, si tiene ID entonces lo actualiza,
        //TODO. si no tiene id, entonces lo crea, NO NECESITAS UN IF(){}
        UsuarioDto usuarioRes = usuarioAbastract
                .buscarUsuarioPorCi(user.getCi());
        if (usuarioRes == null) {
            UsuarioDto usuarioDto = mapperApplicationAbstract
                    .mapearAbstract(user, UsuarioDto.class);
            return usuarioAbastract.guardarUsuarioAbastract(usuarioDto);
        } else {
            UsuarioDto usuarioDto = mapperApplicationAbstract
                    .mapearAbstract(user, UsuarioDto.class);
            return usuarioAbastract.guardarUsuarioAbastract(usuarioDto);
        }
    }

    private UsuarioUnidadDto existeUsuarioUnidad(UsuarioDto usuarioDto) {
        return usuarioUnidadAbstract
                .encontrarUsuarioUnidadByUsuarioId(usuarioDto.getId());
    }

    @Override
    public void eliminarUsuario(Long idUsuarioUnidad) {
        UsuarioUnidadDto usuarioUnidadDto = usuarioUnidadAbstract
                .encontarUsuarioUnidadId(idUsuarioUnidad);
        usuarioUnidadDto.setIsActive(false);
        usuarioUnidadAbstract.guardarUsuarioUnidad(usuarioUnidadDto);
    }

    @Override
    public void editarUsuarioUnidad(Usuario userEdit) {
        UsuarioDto usuarioDto = mapperApplicationAbstract
                .mapearAbstract(userEdit, UsuarioDto.class);
        usuarioAbastract.guardarUsuarioAbastract(usuarioDto);
    }

    @Override
    public List<Rol> listarRolesService() {
        List<RolDto> listaRolesDto = rolAbstract.listarRoles();
        return listaRolesDto.stream()
                .map(x -> mapperApplicationAbstract
                        .mapearAbstract(x, Rol.class))
                .toList();
    }

    @Override
    public List<Unidad> listarUnidadesService() {
        List<UnidadDto> listaUnidades = unidadAbstract.listaDeUnidadesAbstract();
        return listaUnidades.stream()
                .map(x -> mapperApplicationAbstract
                        .mapearAbstract(x, Unidad.class))
                .toList();
    }

    @Override
    public List<Cargo> listarCargosService() {
        List<CargoDto> listCargos = cargoAbstract.findAllCargos();
        return listCargos.stream()
                .map(x -> mapperApplicationAbstract.mapearAbstract(x, Cargo.class))
                .toList();
    }

    @Override
    public void cambiarPass(Credencial credencial, String newPass) {
        String ci = credencial.getCi();
        String pass = credencial.getPass();
        CredencialDto credencialDto = credencialAbstract.encontrarCredencial(ci, pass);
        credencialDto.setPass(newPass);
        credencialAbstract.guardarCredencialAbstract(credencialDto);
    }
}
