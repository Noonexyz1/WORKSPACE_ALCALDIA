package com.prototipo.application.mapper;

public interface MapperApplicationService {
    <T> T mapear(Object objectFrom, Class<T> targetClass);
}
