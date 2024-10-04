package com.prototipo.application.port;

import com.prototipo.application.modelDto.UnidadDto;

import java.util.List;

public interface UnidadAbstract {
    List<UnidadDto> listaDeUnidadesAbstract();
    UnidadDto findUnidadPorIdAbstract(Long idUnidad);
    List<UnidadDto> listaDeUnidadesByDireccionAbstract(String direccion);
}
