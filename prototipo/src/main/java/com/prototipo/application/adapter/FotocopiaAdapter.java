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
    private SolicitanteAbstract solicitanteAbstract;

    public FotocopiaAdapter(UsuarioAbastract usuarioAbastract,
                            RolAbstract rolAbstract,
                            MapperApplicationAbstract mapperApplicationAbstract,
                            CredencialAbstract credencialAbstract,
                            ResponsableAbstract responsableAbstract,
                            UnidadAbstract unidadAbstract,
                            OperadorUnidadAbstract operadorUnidadAbstract,
                            SolicitanteAbstract solicitanteAbstract) {

        this.usuarioAbastract = usuarioAbastract;
        this.rolAbstract = rolAbstract;
        this.mapperApplicationAbstract = mapperApplicationAbstract;
        this.credencialAbstract = credencialAbstract;
        this.responsableAbstract = responsableAbstract;
        this.unidadAbstract = unidadAbstract;
        this.operadorUnidadAbstract = operadorUnidadAbstract;
        this.solicitanteAbstract = solicitanteAbstract;
    }

    @Override
    public void creaUsuarioSolicitante(UsuarioDomain userSoli, Long rolId, Long idUniSoli){
        UsuarioDto usuarioDto = crearUsuarioConRol(userSoli, rolId);
        UnidadDto unidadDto = unidadAbstract.findUnidadPorIdAbstract(idUniSoli);
        SolicitanteDto solicitanteDto = SolicitanteDto.builder()
                .id(null)
                .isActive(true)
                .fkUsuario(usuarioDto)
                .fkUnidad(unidadDto)
                .build();

        solicitanteAbstract.guardarSolicitanteAbstract(solicitanteDto);

        crearCredencial(usuarioDto);
    }

    @Override
    public void creaUsuarioResponsable(UsuarioDomain userRespon, Long rolId, Long idUniRespon){
        UsuarioDto usuarioDto = crearUsuarioConRol(userRespon, rolId);

        UnidadDto unidadDto = unidadAbstract.findUnidadPorIdAbstract(idUniRespon);
        ResponsableDto responsableDto = ResponsableDto.builder()
                .id(null)
                //Se deberia controloar cuales ya no son responsable no?
                .isActive(true)
                .fkUsuario(usuarioDto)
                .fkUnidad(unidadDto)
                .build();

        responsableAbstract.guardarResponsable(responsableDto);

        crearCredencial(usuarioDto);
    }

    @Override
    public void creaUsuarioOperador(UsuarioDomain userOpe, Long rolId, String direccionPiso){
        UsuarioDto usuarioDto = crearUsuarioConRol(userOpe, rolId);

        List<UnidadDto> unidadDtos = unidadAbstract.listaDeUnidadesByDireccionAbstract(direccionPiso);
        unidadDtos.forEach(x -> {
            //TODO, guardarOperadorUnidad, esto meterlo en un Stream enviando x
            OperadorUnidadDto newOpeUniDto = OperadorUnidadDto.builder()
                    .id(null)
                    .isActive(true)
                    .fkUsuario(usuarioDto)
                    .fkUnidad(x)
                    .build();
            operadorUnidadAbstract.guardarOperadorUnidadAbstract(newOpeUniDto);
        });

        crearCredencial(usuarioDto);
    }

    private void crearCredencial(UsuarioDto usuarioDtoResp) {
        CredencialDto newCredencialDto = CredencialDto.builder()
                .id(null)
                .correo(usuarioDtoResp.getCorreo())
                .pass(usuarioDtoResp.getApellidos())
                .fkUsuario(usuarioDtoResp)
                .build();

        credencialAbstract.guardarCredencialAbstract(newCredencialDto);
    }

    private UsuarioDto crearUsuarioConRol(UsuarioDomain user, Long rolId) {
        RolDto rolDto = rolAbstract.encontrarRolPorId(rolId);
        UsuarioDto usuarioDto = mapperApplicationAbstract.mapearAbstract(user, UsuarioDto.class);
        usuarioDto.setId(null);
        usuarioDto.setFkRol(rolDto);
        return usuarioAbastract.guardarUsuarioAbastract(usuarioDto);
    }

    @Override
    public UsuarioDomain editarUsuario(UsuarioDomain usuarioEditado, Long rolId) {
        RolDto rolDto = rolAbstract.encontrarRolPorId(rolId);
        UsuarioDto usuarioDto = mapperApplicationAbstract.mapearAbstract(usuarioEditado, UsuarioDto.class);
        usuarioDto.setFkRol(rolDto);
        UsuarioDto usuarioDtoResp = usuarioAbastract.guardarUsuarioAbastract(usuarioDto);
        return mapperApplicationAbstract.mapearAbstract(usuarioDtoResp, UsuarioDomain.class);
    }

    @Override
    public void eliminarUsuario(Long idUsuario) {
        UsuarioDto usuarioDto = usuarioAbastract.findUsuarioPorIdAbastract(idUsuario);
        usuarioAbastract.guardarUsuarioAbastract(usuarioDto);
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
