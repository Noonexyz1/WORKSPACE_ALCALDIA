package com.prototipo.infrastructure.config;

import com.prototipo.application.adapter.ResponsableAdapter;
import com.prototipo.application.port.in.ResponsableService;
import com.prototipo.application.port.out.pdf.GeneracionPDFArchivoAbstract;
import com.prototipo.application.port.out.persistence.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ResponsableConfig {

    @Bean
    public ResponsableService responsableServiceBean(
            @Qualifier("solicitudImpl")
            SolicitudAbstract solicitudAbstract,
            @Qualifier("autorizacionImpl")
            AutorizacionAbstract autorizacionAbstract,
            @Qualifier("fotocopiaImpl")
            FotocopiaAbstract fotocopiaAbstract,
            @Qualifier("finalizacionImpl")
            FinalizacionAbstract finalizacionAbstract,
            @Qualifier("generacionPDFArchivoImpl")
            GeneracionPDFArchivoAbstract generacionPDFArchivoAbstract,
            @Qualifier("documentoRetiroImpl")
            DocumentoRetiroAbstract documentoRetiroAbstract,
            @Qualifier("notaDePedidoImpl")
            NotaDePedidoAbstract notaDePedidoAbstract,
            @Qualifier("reporteImpl")
            ReporteAbstract reporteAbstract) {

        return new ResponsableAdapter(
                solicitudAbstract,
                autorizacionAbstract,
                fotocopiaAbstract,
                finalizacionAbstract,
                generacionPDFArchivoAbstract,
                documentoRetiroAbstract,
                notaDePedidoAbstract,
                reporteAbstract
        );
    }
}
