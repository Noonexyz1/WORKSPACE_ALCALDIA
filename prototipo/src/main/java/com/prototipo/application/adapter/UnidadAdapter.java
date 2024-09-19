package com.prototipo.application.adapter;

import com.prototipo.application.modelDto.UnidadDto;
import com.prototipo.application.port.UnidadAbstract;
import com.prototipo.application.useCase.UnidadService;
import com.prototipo.domain.model.Unidad;

import java.util.List;

public class UnidadAdapter implements UnidadService {

    private UnidadAbstract unidadAbstract;

    public UnidadAdapter(UnidadAbstract unidadAbstract){
        this.unidadAbstract = unidadAbstract;
    }

    @Override
    public List<Unidad> listaDeUnidadesService() {
        List<UnidadDto> unidadListDto = unidadAbstract.listaDeUnidadesAbstract();
        return unidadListDto.stream().map(x ->
                Unidad.builder()
                        .id(x.getId())
                        .nombre(x.getNombre())
                        .direccion(x.getDireccion())
                        .build()
        ).toList();
    }

    @Override
    public Unidad findUnidadPorIdService(Long idUnidad) {
        UnidadDto unidadDto = unidadAbstract.findUnidadPorIdAbstract(idUnidad);
        Unidad unidad = Unidad.builder()
                .id(unidadDto.getId())
                .nombre(unidadDto.getNombre())
                .direccion(unidadDto.getDireccion())
                .build();

        return unidad;
    }


}
