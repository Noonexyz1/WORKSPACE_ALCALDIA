package com.prototipo.infrastructure.config;

import com.prototipo.application.adapter.SolicitanteAdapter;
import com.prototipo.application.port.in.ResponsableService;
import com.prototipo.application.port.out.persistence.*;
import com.prototipo.application.port.out.pdf.GeneracionPDFArchivoAbstract;
import com.prototipo.application.port.in.SolicitanteService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SolicitudConfig {

    @Bean
    public SolicitanteService solicitudServiceBean(
            @Qualifier("solicitudImpl")
            SolicitudAbstract solicitudAbstract,
            @Qualifier("fotocopiaImpl")
            FotocopiaAbstract fotocopiaAbstract,
            @Qualifier("servicioFotocopiaImpl")
            ServicioFotocopiaAbstract servicioFotocopiaAbstract,
            @Qualifier("generacionPDFArchivoImpl")
            GeneracionPDFArchivoAbstract generacionPDFArchivoAbstract,
            @Qualifier("documentoRetiroImpl")
            DocumentoRetiroAbstract documentoRetiroAbstract,

            @Qualifier("responsableServiceBean")
            ResponsableService responsableService,
            @Qualifier("informeReportImpl")
            InformeReportAbstract informeReportAbstract){

        //Se necesita una dependencia
        return new SolicitanteAdapter(
                solicitudAbstract,
                fotocopiaAbstract,
                servicioFotocopiaAbstract,
                generacionPDFArchivoAbstract,
                documentoRetiroAbstract,
                responsableService,
                informeReportAbstract);
    }
}
