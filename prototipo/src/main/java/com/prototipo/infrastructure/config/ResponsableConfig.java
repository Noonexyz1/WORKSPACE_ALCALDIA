package com.prototipo.infrastructure.config;

import com.prototipo.application.adapter.ResponsableAdapter;
import com.prototipo.application.port.SolicitudAbstract;
import com.prototipo.application.useCase.ResponsableService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ResponsableConfig {

    @Bean
    public ResponsableService responsableServiceBean(@Qualifier("solicitudImpl")
                                                     SolicitudAbstract solicitudAbstract) {

        return new ResponsableAdapter(solicitudAbstract);
    }
}
