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
    public ResponsableService responsableServiceBean(
            @Qualifier("solicitudImpl")
            SolicitudAbstract solicitudAbstract,
            @Qualifier("reportesPDFImpl")
            ReportesPDFAbstract reportesPDFAbstract,
            @Qualifier("aprobacionImpl")
            AprobacionAbstract aprobacionAbstract,
            @Qualifier("mapperApplicationImpl")
            MapperApplicationAbstract mapperApplicationAbstract,
            @Qualifier("autorizacionImpl")
            AutorizacionAbstract autorizacionAbstract,
            @Qualifier("fotocopiaImpl")
            FotocopiaAbstract fotocopiaAbstract,
            @Qualifier("finalizacionImpl")
            FinalizacionAbstract finalizacionAbstract) {

        return new ResponsableAdapter(
                solicitudAbstract,
                reportesPDFAbstract,
                aprobacionAbstract,
                mapperApplicationAbstract,
                autorizacionAbstract,
                fotocopiaAbstract,
                finalizacionAbstract);
    }
}
