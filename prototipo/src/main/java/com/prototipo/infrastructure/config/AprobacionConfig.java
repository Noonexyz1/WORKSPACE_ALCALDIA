package com.prototipo.infrastructure.config;

import com.prototipo.application.adapter.AprobacionAdapter;
import com.prototipo.application.mapper.MapperApplicationAbstract;
import com.prototipo.application.port.AprobacionAbstract;
import com.prototipo.application.useCase.AprobacionService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AprobacionConfig {

    @Bean
    public AprobacionService aprobacionServiceBean(@Qualifier("aprobacionImpl")
                                                   AprobacionAbstract aprobacionAbstract,
                                                   @Qualifier("mapperApplicationImpl")
                                                   MapperApplicationAbstract mapperApplicationAbstract) {

        return new AprobacionAdapter(aprobacionAbstract, mapperApplicationAbstract);
    }
}
