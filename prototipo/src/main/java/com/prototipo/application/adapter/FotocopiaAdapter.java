package com.prototipo.application.adapter;

import com.prototipo.application.mapper.MapperApplicationAbstract;
import com.prototipo.application.modelDto.CredencialDto;
import com.prototipo.application.modelDto.RolDto;
import com.prototipo.application.modelDto.UsuarioDto;
import com.prototipo.application.port.CredencialAbstract;
import com.prototipo.application.port.RolAbstract;
import com.prototipo.application.port.UsuarioAbastract;
import com.prototipo.application.useCase.FotocopiaService;
import com.prototipo.domain.enums.EstadoByResponsableEnum;
import com.prototipo.domain.model.*;

public class FotocopiaAdapter implements FotocopiaService {

    private UsuarioAbastract usuarioAbastract;
    private RolAbstract rolAbstract;
    private MapperApplicationAbstract mapperApplicationAbstract;
    private CredencialAbstract credencialAbstract;

    public FotocopiaAdapter(UsuarioAbastract usuarioAbastract,
                            RolAbstract rolAbstract,
                            MapperApplicationAbstract mapperApplicationAbstract,
                            CredencialAbstract credencialAbstract) {

        this.usuarioAbastract = usuarioAbastract;
        this.rolAbstract = rolAbstract;
        this.mapperApplicationAbstract = mapperApplicationAbstract;
        this.credencialAbstract = credencialAbstract;
    }

    @Override
    public void crearUsuario(UsuarioDomain nuevoUsuario, Long rolId) {
        //1.- Encontrar el Rol,
        RolDto rolDto = rolAbstract.encontrarRolPorId(rolId);

        //2.- Creamos el usuario nuevo con el rol que se ha encontraoo
        UsuarioDto usuarioDto = mapperApplicationAbstract.mapearAbstract(nuevoUsuario, UsuarioDto.class);
        usuarioDto.setId(null);
        usuarioDto.setFkRol(rolDto);

        UsuarioDto usuarioDtoResp = usuarioAbastract.guardarUsuario(usuarioDto);

        //3.- Teniendo el Usuario con el Rol, le creamos su credencial
        CredencialDto newCredencialDto = CredencialDto.builder()
                .id(null)
                .nombreUser(usuarioDtoResp.getCorreo())
                .pass(usuarioDtoResp.getApellidos())
                .fkUsuario(usuarioDtoResp)
                .build();

        credencialAbstract.guardarCredencialAbstract(newCredencialDto);
    }

    @Override
    public UsuarioDomain editarUsuario(UsuarioDomain usuarioEditado, Long rolId) {
        RolDto rolDto = rolAbstract.encontrarRolPorId(rolId);
        UsuarioDto usuarioDto = mapperApplicationAbstract.mapearAbstract(usuarioEditado, UsuarioDto.class);
        usuarioDto.setFkRol(rolDto);
        UsuarioDto usuarioDtoResp = usuarioAbastract.guardarUsuario(usuarioDto);
        return mapperApplicationAbstract.mapearAbstract(usuarioDtoResp, UsuarioDomain.class);
    }

    @Override
    public void eliminarUsuario(Long idUsuario) {
        UsuarioDto usuarioDto = usuarioAbastract.findUsuarioPorIdAbastract(idUsuario);
        usuarioDto.setIsActive(false);
        usuarioAbastract.guardarUsuario(usuarioDto);
    }

    @Override
    public void subirArchivoPdf() {
        //TODO
    }

    @Override
    public EstadoByResponsableEnum enviarNotificacionEstado() {
        //TODO
        return null;
    }

    @Override
    public OperadorDomain asignarOperador() {
        //TODO
        return null;
    }

    @Override
    public void guardarRegistroInsumosUtilizado(InsumoDomain insumoUtilizado) {
        //TODO
    }

    @Override
    public void generarAlertaInsumosBajos() {
        //TODO
    }

    @Override
    public ReporteDomain generarReporte() {
        //TODO
        return null;
    }

    @Override
    public SolicitudDomain enviarNotificacionEstadoSoli() {
        //TODO
        return null;
    }
}
