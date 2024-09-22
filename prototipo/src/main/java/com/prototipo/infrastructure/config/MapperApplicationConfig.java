package com.prototipo.infrastructure.config;

import com.prototipo.application.mapper.MapperApplicationAbstract;
import com.prototipo.infrastructure.impl.MapperApplicationImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperApplicationConfig {

    @Bean
    public MapperApplicationAbstract mapperApplicationAbstractBean(@Qualifier("modelMapperBean") ModelMapper modelMapper){
        return new MapperApplicationImpl(modelMapper);
    }
}
