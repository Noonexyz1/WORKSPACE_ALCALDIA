package com.prototipo.application.port;

import com.prototipo.application.modelDto.FotocopiaDto;

import java.util.List;

public interface FotocopiaAbstract {
    List<FotocopiaDto> getFotocopiasSolicitudAbstract(Long idSolicitud);
    void guardarRegistroFotocopia(FotocopiaDto fotocopiaDto);
}
