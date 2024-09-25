package com.prototipo.application.mapper;

public class MapperApplication implements MapperApplicationService {

    private final MapperApplicationAbstract mapperApplicationAbstract;

    public MapperApplication(MapperApplicationAbstract mapperApplicationAbstract) {
        this.mapperApplicationAbstract = mapperApplicationAbstract;
    }

    @Override
    public <T> T mapear(Object objectFrom, Class<T> targetClass) {
        return mapperApplicationAbstract.mapearAbstract(objectFrom, targetClass);
    }
}
