package com.prototipo.infrastructure.config;

import com.prototipo.application.adapter.SolicitudAdapter;
import com.prototipo.application.port.SolicitudAbstract;
import com.prototipo.application.useCase.SolicitudService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SolicitudConfig {

    @Bean
    public SolicitudService solicitudServiceBean(@Qualifier("solicitudImpl") SolicitudAbstract solicitudAbstract){
        //Se mecesota una dependencia
        return new SolicitudAdapter(solicitudAbstract);
    }
}
