package com.prototipo.infrastructure.config;

import com.prototipo.application.adapter.InicioSesionAdapter;
import com.prototipo.application.mapper.MapperApplicationAbstract;
import com.prototipo.application.port.InicioSesionAbstract;
import com.prototipo.application.useCase.InicioSesionService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InicioSesionConfig {

    @Bean
    public InicioSesionService inicioSesionServiceBean(@Qualifier("inicioSesionImpl")
                                                       InicioSesionAbstract inicioSesionAbstract,
                                                       @Qualifier("mapperApplicationImpl")
                                                       MapperApplicationAbstract mapperApplicationAbstract){

        return new InicioSesionAdapter(inicioSesionAbstract, mapperApplicationAbstract);
    }

}
