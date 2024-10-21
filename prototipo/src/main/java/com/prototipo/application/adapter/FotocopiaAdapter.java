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
    private UnidadAbstract unidadAbstract;
    private UsuarioUnidadAbstract usuarioUnidadAbstract;

    public FotocopiaAdapter(UsuarioAbastract usuarioAbastract,
                            RolAbstract rolAbstract,
                            MapperApplicationAbstract mapperApplicationAbstract,
                            CredencialAbstract credencialAbstract,
                            UnidadAbstract unidadAbstract,
                            UsuarioUnidadAbstract usuarioUnidadAbstract) {

        this.usuarioAbastract = usuarioAbastract;
        this.rolAbstract = rolAbstract;
        this.mapperApplicationAbstract = mapperApplicationAbstract;
        this.credencialAbstract = credencialAbstract;
        this.unidadAbstract = unidadAbstract;
        this.usuarioUnidadAbstract = usuarioUnidadAbstract;
    }

    @Override
    public void creaUsuario(Usuario user, Long rolId, Long idUni){
        // Insertamos al usuario en la tabla 'usuario'
        UsuarioDto usuarioDtoResp = crearUsuario(user);

        // Creamos las credenciales correspondientes para el nuevo usuario
        crearCredencial(usuarioDtoResp);

        // Creamos instancias de RolDto y UnidadDto usando únicamente sus IDs.
        // No es necesario realizar consultas adicionales a la base de datos para obtener el Rol o la Unidad
        // a partir de sus IDs, ya que JPA puede manejar estas referencias directamente con los IDs proporcionados.
        // Esto mejora el rendimiento al evitar llamadas innecesarias a la BD.
        // Que trucaso no????? jaja
        RolDto rolDto = RolDto.builder().id(rolId).build();
        UnidadDto unidadDto = UnidadDto.builder().id(idUni).build();

        UsuarioUnidadDto userUni = UsuarioUnidadDto.builder()
                .id(null)
                .isActive(true)
                .fkRol(rolDto)
                .fkUnidad(unidadDto)
                .fkUsuario(usuarioDtoResp)
                .build();

        usuarioUnidadAbstract.guardarUsuarioUnidad(userUni);
    }

    private void crearCredencial(UsuarioDto usuarioDtoResp) {
        CredencialDto credencialDtoResp = credencialAbstract
                .encontrarCredencialPorUsuarioId(usuarioDtoResp.getId());
        if (credencialDtoResp == null) {
            CredencialDto newCredencialDto = CredencialDto.builder()
                    .correo(usuarioDtoResp.getCorreo())
                    .pass(usuarioDtoResp.getApellidos())
                    .fkUsuario(usuarioDtoResp)
                    .build();
            credencialAbstract.guardarCredencialAbstract(newCredencialDto);
        }
    }

    private UsuarioDto crearUsuario(Usuario user) {
        //Primero encontramos si existe este registro o no
        UsuarioDto usuarioRes = usuarioAbastract
                .buscarUsuarioPorEmail(user.getCorreo());
        if (usuarioRes == null) {
            UsuarioDto usuarioDto = mapperApplicationAbstract
                    .mapearAbstract(user, UsuarioDto.class);
            return usuarioAbastract.guardarUsuarioAbastract(usuarioDto);
        }
        return usuarioRes;
    }

    @Override
    public void eliminarUsuario(Long idUsuarioUnidad) {
        UsuarioUnidadDto usuarioUnidadDto = usuarioUnidadAbstract
                .encontarUsuarioUnidadId(idUsuarioUnidad);
        usuarioUnidadDto.setIsActive(false);
        usuarioUnidadAbstract.guardarUsuarioUnidad(usuarioUnidadDto);
    }

    @Override
    public UsuarioUnidad editarUsuarioUnidad(UsuarioUnidad usuarioEditado) {
        UsuarioUnidadDto usuarioUnidadDto = mapperApplicationAbstract
                .mapearAbstract(usuarioEditado, UsuarioUnidadDto.class);

        UsuarioDto usuarioDto = usuarioAbastract
                .guardarUsuarioAbastract(usuarioUnidadDto.getFkUsuario());

        usuarioUnidadDto.setFkUsuario(usuarioDto);

        UsuarioUnidadDto usuarioDtoResp = usuarioAbastract
                .guardarUsuarioUnidadAbastract(usuarioUnidadDto);
        return mapperApplicationAbstract
                .mapearAbstract(usuarioDtoResp, UsuarioUnidad.class);
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
    public void cambiarPass(Credencial credencial, String newPass) {
        String correo = credencial.getCorreo();
        String pass = credencial.getPass();
        CredencialDto credencialDto = credencialAbstract.encontrarCredencial(correo, pass);
        credencialDto.setPass(newPass);
        credencialAbstract.guardarCredencialAbstract(credencialDto);
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
    public void guardarRegistroInsumosUtilizado(Insumo insumoUtilizado) {
        //TODO
    }

    @Override
    public void generarAlertaInsumosBajos() {
        //TODO
    }

    @Override
    public Solicitud enviarNotificacionEstadoSoli() {
        //TODO
        return null;
    }


}
