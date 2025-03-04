package com.prototipo.infrastructure.config;

import com.prototipo.application.adapter.AutenticacionAdapter;
import com.prototipo.application.mapper.MapperApplicationAbstract;
import com.prototipo.application.port.out.CredencialAbstract;
import com.prototipo.application.port.out.UsuarioAbastract;
import com.prototipo.application.port.in.AutenticacionService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AutenticacionConfig {

    @Bean
    public AutenticacionService autenticacionServiceBean(
            @Qualifier("credencialImpl")
            CredencialAbstract credencialAbstract,
            @Qualifier("mapperApplicationImpl")
            MapperApplicationAbstract mapperApplicationAbstract,
            @Qualifier("usuarioImpl")
            UsuarioAbastract usuarioAbastract){

        //En los constructores, EL ORDEN IMPORTA
        return new AutenticacionAdapter(
                credencialAbstract,
                mapperApplicationAbstract,
                usuarioAbastract);
    }
}
