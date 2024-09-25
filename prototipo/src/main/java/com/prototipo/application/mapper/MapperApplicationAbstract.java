package com.prototipo.application.mapper;

public interface MapperApplicationAbstract {
    <T> T mapearAbstract(Object objectFrom, Class<T> targetClass);
}
