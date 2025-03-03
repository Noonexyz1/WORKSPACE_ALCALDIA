package com.prototipo.infrastructure.config;

import com.prototipo.application.adapter.AdministradorAdapter;
import com.prototipo.application.mapper.MapperApplicationAbstract;
import com.prototipo.application.port.*;
import com.prototipo.application.useCase.AdministradorService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AdministradorConfig {

    @Bean
    public AdministradorService administradorServiceBean(
            @Qualifier("usuarioImpl")
            UsuarioAbastract usuarioAbastract,
            @Qualifier("mapperApplicationImpl")
            MapperApplicationAbstract mapperApplicationAbstract,
            @Qualifier("rolImpl")
            RolAbstract rolAbstract,
            @Qualifier("credencialImpl")
            CredencialAbstract credencialAbstract,
            @Qualifier("unidadImpl")
            UnidadAbstract unidadAbstract,
            @Qualifier("usuarioUnidadImpl")
            UsuarioUnidadAbstract usuarioUnidadAbstract,
            @Qualifier("cargoImpl")
            CargoAbstract cargoAbstract){


        //En los constructores, EL ORDEN IMPORTA
        return new AdministradorAdapter(
                usuarioAbastract,
                rolAbstract,
                mapperApplicationAbstract,
                credencialAbstract,
                unidadAbstract,
                usuarioUnidadAbstract,
                cargoAbstract);
    }
}
