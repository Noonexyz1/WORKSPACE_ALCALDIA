package com.prototipo.infrastructure.impl;

import com.prototipo.application.modelDto.FinalizacionDto;
import com.prototipo.application.port.FinalizacionAbstract;
import com.prototipo.infrastructure.persistence.db.entity.AutorizacionEntity;
import com.prototipo.infrastructure.persistence.db.entity.FinalizacionEntity;
import com.prototipo.infrastructure.persistence.db.repository.FinalizacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FinalizacionImpl implements FinalizacionAbstract {

    @Autowired
    private FinalizacionRepository finalizacionRepository;

    @Override
    public void guardarFinalizacionAbs(FinalizacionDto finalizacionDto) {
        AutorizacionEntity autorizacion = AutorizacionEntity.builder()
                .id(finalizacionDto.getFkAutorizacion().getId())
                .build();

        FinalizacionEntity finalizacionEntity = FinalizacionEntity.builder()
                .fecha(finalizacionDto.getFecha())
                .fkAutorizacion(autorizacion)
                .build();
        finalizacionRepository.save(finalizacionEntity);
    }
}
