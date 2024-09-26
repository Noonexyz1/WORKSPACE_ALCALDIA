package com.prototipo.infrastructure.config;

import com.prototipo.application.adapter.OperadorAdapter;
import com.prototipo.application.mapper.MapperApplicationAbstract;
import com.prototipo.application.port.OperacionAbstract;
import com.prototipo.application.port.SolicitudAbstract;
import com.prototipo.application.port.UsuarioAbastract;
import com.prototipo.application.useCase.OperadorService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OperadorConfig {

    @Bean
    public OperadorService operadorServiceBean(@Qualifier("mapperApplicationImpl")
                                               MapperApplicationAbstract mapperApplicationAbstract,
                                               @Qualifier("operacionImpl")
                                               OperacionAbstract operacionAbstract,
                                               @Qualifier("usuarioImpl")
                                               UsuarioAbastract usuarioAbastract){

        return new OperadorAdapter(mapperApplicationAbstract,
                                   operacionAbstract,
                                   usuarioAbastract);
    }
}
