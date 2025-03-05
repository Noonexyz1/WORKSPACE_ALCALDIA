package com.prototipo.infrastructure.persistence.db.adapter;

import com.prototipo.application.port.out.UnidadAbstract;
import com.prototipo.domain.model.Unidad;
import com.prototipo.infrastructure.persistence.db.repository.UnidadRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UnidadImpl implements UnidadAbstract {

    @Autowired
    private UnidadRepository unidadRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<Unidad> listaDeUnidadesAbstract() {
        return unidadRepository.findAll()
                .stream()
                .map(x -> modelMapper.map(x, Unidad.class))
                .toList();
    }
}
