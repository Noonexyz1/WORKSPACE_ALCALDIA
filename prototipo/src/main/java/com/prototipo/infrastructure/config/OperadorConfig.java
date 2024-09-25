package com.prototipo.infrastructure.config;

import com.prototipo.application.adapter.OperadorAdapter;
import com.prototipo.application.mapper.MapperApplicationAbstract;
import com.prototipo.application.port.SolicitudAbstract;
import com.prototipo.application.useCase.OperadorService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OperadorConfig {

    @Bean
    public OperadorService operadorServiceBean(@Qualifier("solicitudImpl")
                                               SolicitudAbstract solicitudAbstract,
                                               @Qualifier("mapperApplicationImpl")
                                               MapperApplicationAbstract mapperApplicationAbstract){

        return new OperadorAdapter(solicitudAbstract, mapperApplicationAbstract);
    }
}
