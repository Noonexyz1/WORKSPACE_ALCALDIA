package com.prototipo.infrastructure.config;

import com.prototipo.application.adapter.AprobacionAdapter;
import com.prototipo.application.mapper.MapperApplicationAbstract;
import com.prototipo.application.port.AprobacionAbstract;
import com.prototipo.application.port.ResponsableAbstract;
import com.prototipo.application.port.SolicitudAbstract;
import com.prototipo.application.port.UsuarioAbastract;
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
                                                   MapperApplicationAbstract mapperApplicationAbstract,
                                                   @Qualifier("responsableImpl")
                                                   ResponsableAbstract responsableAbstract,
                                                   @Qualifier("usuarioImpl")
                                                   UsuarioAbastract usuarioAbastract,
                                                   @Qualifier("solicitudImpl")
                                                   SolicitudAbstract solicitudAbstract) {

        return new AprobacionAdapter(aprobacionAbstract,
                                     mapperApplicationAbstract,
                                     responsableAbstract,
                                     usuarioAbastract,
                                     solicitudAbstract);
    }
}
