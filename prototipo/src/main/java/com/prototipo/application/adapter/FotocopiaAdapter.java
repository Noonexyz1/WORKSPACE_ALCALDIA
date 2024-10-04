package com.prototipo.application.adapter;

import com.prototipo.application.mapper.MapperApplicationAbstract;
import com.prototipo.application.modelDto.*;
import com.prototipo.application.port.*;
import com.prototipo.application.useCase.FotocopiaService;
import com.prototipo.domain.enums.EstadoByResponsableEnum;
import com.prototipo.domain.model.*;

import java.util.List;

public class FotocopiaAdapter implements FotocopiaService {

    private UsuarioAbastract usuarioAbastract;
    private RolAbstract rolAbstract;
    private MapperApplicationAbstract mapperApplicationAbstract;
    private CredencialAbstract credencialAbstract;
    private ResponsableAbstract responsableAbstract;
    private UnidadAbstract unidadAbstract;
    private OperadorUnidadAbstract operadorUnidadAbstract;

    public FotocopiaAdapter(UsuarioAbastract usuarioAbastract,
                            RolAbstract rolAbstract,
                            MapperApplicationAbstract mapperApplicationAbstract,
                            CredencialAbstract credencialAbstract,
                            ResponsableAbstract responsableAbstract,
                            UnidadAbstract unidadAbstract,
                            OperadorUnidadAbstract operadorUnidadAbstract) {

        this.usuarioAbastract = usuarioAbastract;
        this.rolAbstract = rolAbstract;
        this.mapperApplicationAbstract = mapperApplicationAbstract;
        this.credencialAbstract = credencialAbstract;
        this.responsableAbstract = responsableAbstract;
        this.unidadAbstract = unidadAbstract;
        this.operadorUnidadAbstract = operadorUnidadAbstract;
    }

    @Override
    public void crearUsuario(UsuarioDomain nuevoUsuario, Long rolId, Long idUnidadResp, String direccion) {
        //1.- Encontrar el Rol,
        RolDto rolDto = rolAbstract.encontrarRolPorId(rolId);

        //2.- Creamos el usuario nuevo con el rol que se ha encontraoo
        UsuarioDto usuarioDto = mapperApplicationAbstract.mapearAbstract(nuevoUsuario, UsuarioDto.class);
        usuarioDto.setId(null);
        usuarioDto.setIsActive(true);
        usuarioDto.setFkRol(rolDto);

        UsuarioDto usuarioDtoResp = usuarioAbastract.guardarUsuario(usuarioDto);

        //2.5.-
        //Registramos en la tabla Responsable si este rol es de Responsable
        if (idUnidadResp != null) {
            guardarResponsable(usuarioDtoResp, idUnidadResp);
        }
        if (direccion != null) {
            guardarOperadorUnidad(usuarioDtoResp, direccion);
        }

        //3.- Teniendo el Usuario con el Rol, le creamos su credencial
        CredencialDto newCredencialDto = CredencialDto.builder()
                .id(null)
                .nombreUser(usuarioDtoResp.getCorreo())
                .pass(usuarioDtoResp.getApellidos())
                .fkUsuario(usuarioDtoResp)
                .build();

        credencialAbstract.guardarCredencialAbstract(newCredencialDto);
    }

    private void guardarResponsable(UsuarioDto usuarioDtoResp, Long idUnidadResp){
        UnidadDto unidadDto = unidadAbstract.findUnidadPorIdAbstract(idUnidadResp);
        ResponsableDto responsableDto = ResponsableDto.builder()
                .id(null)
                //Se deberia controloar cuales ya no son responsable no?
                .isActive(true)
                .fkUsuario(usuarioDtoResp)
                .fkUnidad(unidadDto)
                .build();
        responsableAbstract.guardarResponsable(responsableDto);
    }

    //TODO, Probar este metodo
    private void guardarOperadorUnidad(UsuarioDto usuarioDtoResp, String direccion) {
        List<UnidadDto> unidadDtos = unidadAbstract.listaDeUnidadesByDireccionAbstract(direccion);
        unidadDtos.forEach(x -> {
            //TODO, guardarOperadorUnidad, esto meterlo en un Stream enviando x
            OperadorUnidadDto newOpeUniDto = OperadorUnidadDto.builder()
                    .id(null)
                    .isActive(true)
                    .fkUsuario(usuarioDtoResp)
                    .fkUnidad(x)
                    .build();
            operadorUnidadAbstract.guardarOperadorUnidadAbstract(newOpeUniDto);
        });
    }

    @Override
    public UsuarioDomain editarUsuario(UsuarioDomain usuarioEditado, Long rolId) {
        RolDto rolDto = rolAbstract.encontrarRolPorId(rolId);
        UsuarioDto usuarioDto = mapperApplicationAbstract.mapearAbstract(usuarioEditado, UsuarioDto.class);
        usuarioDto.setIsActive(true);
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
