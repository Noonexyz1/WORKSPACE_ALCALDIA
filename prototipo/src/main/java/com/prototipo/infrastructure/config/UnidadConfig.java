package com.prototipo.infrastructure.config;

import com.prototipo.application.adapter.UnidadAdapter;
import com.prototipo.application.port.UnidadAbstract;
import com.prototipo.application.useCase.UnidadService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UnidadConfig {

    @Bean
    public UnidadService unidadServiceBean(@Qualifier("unidadImpl") UnidadAbstract unidadAbstract){
        //Se mecesota una dependencia
        return new UnidadAdapter(unidadAbstract);
    }
}
