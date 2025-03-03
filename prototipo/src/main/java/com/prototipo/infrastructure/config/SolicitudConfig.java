package com.prototipo.infrastructure.config;

import com.prototipo.application.adapter.SolicitanteAdapter;
import com.prototipo.application.mapper.MapperApplicationAbstract;
import com.prototipo.application.port.SolicitudAbstract;
import com.prototipo.application.useCase.SolicitanteService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SolicitudConfig {

    @Bean
    public SolicitanteService solicitudServiceBean(
            @Qualifier("solicitudImpl")
            SolicitudAbstract solicitudAbstract,
            @Qualifier("mapperApplicationAbstractBean")
            MapperApplicationAbstract mapperApplicationAbstract){

        //Se necesita una dependencia
        return new SolicitanteAdapter(
                solicitudAbstract,
                mapperApplicationAbstract);
    }
}
