package com.prototipo.infrastructure.config;

import com.prototipo.application.adapter.ResponsableAdapter;
import com.prototipo.application.port.in.ResponsableService;
import com.prototipo.application.port.out.pdf.GeneracionPDFArchivoAbstract;
import com.prototipo.application.port.out.persistence.GeneracionPDFDataAbstract;
import com.prototipo.application.port.out.persistence.AutorizacionAbstract;
import com.prototipo.application.port.out.persistence.FinalizacionAbstract;
import com.prototipo.application.port.out.persistence.FotocopiaAbstract;
import com.prototipo.application.port.out.persistence.SolicitudAbstract;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ResponsableConfig {

    @Bean
    public ResponsableService responsableServiceBean(
            @Qualifier("solicitudImpl")
            SolicitudAbstract solicitudAbstract,
            @Qualifier("generacionPDFDataImpl")
            GeneracionPDFDataAbstract generacionPDFDataAbstract,
            @Qualifier("autorizacionImpl")
            AutorizacionAbstract autorizacionAbstract,
            @Qualifier("fotocopiaImpl")
            FotocopiaAbstract fotocopiaAbstract,
            @Qualifier("finalizacionImpl")
            FinalizacionAbstract finalizacionAbstract,
            @Qualifier("generacionPDFArchivoImpl")
            GeneracionPDFArchivoAbstract generacionPDFArchivoAbstract) {

        return new ResponsableAdapter(
                solicitudAbstract,
                generacionPDFDataAbstract,
                autorizacionAbstract,
                fotocopiaAbstract,
                finalizacionAbstract,
                generacionPDFArchivoAbstract);
    }
}
