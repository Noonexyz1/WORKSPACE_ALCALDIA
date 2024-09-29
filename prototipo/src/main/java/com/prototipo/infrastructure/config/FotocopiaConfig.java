package com.prototipo.infrastructure.config;

import com.prototipo.application.adapter.FotocopiaAdapter;
import com.prototipo.application.mapper.MapperApplicationAbstract;
import com.prototipo.application.port.CredencialAbstract;
import com.prototipo.application.port.RolAbstract;
import com.prototipo.application.port.UsuarioAbastract;
import com.prototipo.application.useCase.FotocopiaService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FotocopiaConfig {

    @Bean
    public FotocopiaService fotocopiaServiceBean(@Qualifier("usuarioImpl")
                                                 UsuarioAbastract usuarioAbastract,
                                                 @Qualifier("mapperApplicationImpl")
                                                 MapperApplicationAbstract mapperApplicationAbstract,
                                                 @Qualifier("rolImpl")
                                                 RolAbstract rolAbstract,
                                                 @Qualifier("credencialImpl")
                                                 CredencialAbstract credencialAbstract){

        //En los construntores, EL ORDEN IMPORTA
        return new FotocopiaAdapter(usuarioAbastract,
                                    rolAbstract,
                                    mapperApplicationAbstract,
                                    credencialAbstract);
    }
}
