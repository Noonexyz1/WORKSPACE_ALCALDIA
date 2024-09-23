package com.prototipo.infrastructure.config;

import com.prototipo.application.adapter.SolicitudAdapter;
import com.prototipo.application.adapter.UsuarioAdapter;
import com.prototipo.application.mapper.MapperApplicationAbstract;
import com.prototipo.application.port.SolicitudAbstract;
import com.prototipo.application.port.UsuarioAbastract;
import com.prototipo.application.useCase.SolicitudService;
import com.prototipo.application.useCase.UsuarioService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SolicitudConfig {

    @Bean
    public SolicitudService solicitudServiceBean(@Qualifier("solicitudImpl")
                                                 SolicitudAbstract solicitudAbstract,
                                                 @Qualifier("mapperApplicationAbstractBean")
                                                 MapperApplicationAbstract mapperApplicationAbstract){

        //Se necesita una dependencia
        return new SolicitudAdapter(solicitudAbstract, mapperApplicationAbstract);
    }

    @Bean
    public UsuarioService usuarioServiceBean(@Qualifier("usuarioImpl") UsuarioAbastract usuarioAbastract) {
        return new UsuarioAdapter(usuarioAbastract);
    }
}
