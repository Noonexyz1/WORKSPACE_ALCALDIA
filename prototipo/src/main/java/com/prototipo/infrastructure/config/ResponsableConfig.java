package com.prototipo.infrastructure.config;

import com.prototipo.application.adapter.ResponsableAdapter;
import com.prototipo.application.mapper.MapperApplicationAbstract;
import com.prototipo.application.port.*;
import com.prototipo.application.useCase.ResponsableService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ResponsableConfig {

    @Bean
    public ResponsableService responsableServiceBean(@Qualifier("solicitudImpl")
                                                     SolicitudAbstract solicitudAbstract,
                                                     @Qualifier("aprobacionImpl")
                                                     AprobacionAbstract aprobacionAbstract,
                                                     @Qualifier("usuarioImpl")
                                                     UsuarioAbastract usuarioAbstract,
                                                     @Qualifier("operacionImpl")
                                                     OperacionAbstract operacionAbstract,
                                                     @Qualifier("mapperApplicationImpl")
                                                     MapperApplicationAbstract mapperApplicationAbstract,
                                                     @Qualifier("reportesPDFImpl")
                                                     ReportesPDFAbstract reportesPDFAbstract) {

        return new ResponsableAdapter(
                solicitudAbstract,
                aprobacionAbstract,
                usuarioAbstract,
                operacionAbstract,
                mapperApplicationAbstract,
                reportesPDFAbstract
        );

    }
}
