package com.prototipo.application.adapter;

import com.prototipo.application.mapper.MapperApplicationAbstract;
import com.prototipo.application.modelDto.UnidadDto;
import com.prototipo.application.port.UnidadAbstract;
import com.prototipo.application.useCase.UnidadService;
import com.prototipo.domain.model.Unidad;

import java.util.List;

public class UnidadAdapter implements UnidadService {

    private UnidadAbstract unidadAbstract;
    private MapperApplicationAbstract mapperApplicationAbstract;

    public UnidadAdapter(UnidadAbstract unidadAbstract,
                         MapperApplicationAbstract mapperApplicationAbstract){

        this.unidadAbstract = unidadAbstract;
        this.mapperApplicationAbstract = mapperApplicationAbstract;
    }

    @Override
    public List<Unidad> listaDeUnidadesService() {
        return unidadAbstract.listaDeUnidadesAbstract()
                .stream()
                .map(x -> mapperApplicationAbstract.mapearAbstract(x, Unidad.class))
                .toList();
    }

    @Override
    public Unidad findUnidadPorIdService(Long idUnidad) {
        //TODO
        UnidadDto unidadDto = unidadAbstract.findUnidadPorIdAbstract(idUnidad);
        Unidad unidad = Unidad.builder()
                .id(unidadDto.getId())
                .nombre(unidadDto.getNombre())
                .direccion(unidadDto.getDireccion())
                .build();

        return unidad;
    }
}
