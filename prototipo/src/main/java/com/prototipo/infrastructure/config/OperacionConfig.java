package com.prototipo.infrastructure.config;

import com.prototipo.application.adapter.OperacionAdapter;
import com.prototipo.application.mapper.MapperApplicationAbstract;
import com.prototipo.application.port.OperacionAbstract;
import com.prototipo.application.useCase.OperacionService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OperacionConfig {

    @Bean
    public OperacionService operacionServiceBean(@Qualifier("operacionImpl")
                                                 OperacionAbstract operacionAbstract,
                                                 @Qualifier("mapperApplicationImpl")
                                                 MapperApplicationAbstract mapperApplicationAbstract){

        return new OperacionAdapter(operacionAbstract, mapperApplicationAbstract);
    }
}
