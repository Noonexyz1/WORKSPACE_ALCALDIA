package com.prototipo.infrastructure.impl;

import com.prototipo.application.modelDto.AutorizacionDto;
import com.prototipo.application.port.AutorizacionAbstract;
import com.prototipo.infrastructure.persistence.db.entity.AutorizacionEntity;
import com.prototipo.infrastructure.persistence.db.entity.SolicitudEntity;
import com.prototipo.infrastructure.persistence.db.entity.UsuarioUnidadEntity;
import com.prototipo.infrastructure.persistence.db.repository.AutorizacionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AutorizacionImpl implements AutorizacionAbstract {

    @Autowired
    private AutorizacionRepository autorizacionRepository;
    @Autowired
    private ModelMapper modelMapper;


    @Override
    public void guardarAutorizacionAbs(AutorizacionDto autorizacionDto) {
        UsuarioUnidadEntity usuarioResponsable = UsuarioUnidadEntity.builder()
                .id(autorizacionDto.getFkUsuarioResponsable().getId())
                .build();

        SolicitudEntity solicitudEntity = SolicitudEntity.builder()
                .id(autorizacionDto.getFkSolicitud().getId())
                .build();

        AutorizacionEntity autorizacion = AutorizacionEntity.builder()
                .id(autorizacionDto.getId())
                .fecha(autorizacionDto.getFecha())
                .finaliFlag(autorizacionDto.getFinaliFlag())
                .fkUsuarioResponsable(usuarioResponsable)
                .fkSolicitud(solicitudEntity)
                .build();

        autorizacionRepository.save(autorizacion);
    }

    @Override
    public AutorizacionDto buscarAutorizacionByIdAbs(Long idAutorizacion) {
        AutorizacionEntity autorizacion = autorizacionRepository
                .findById(idAutorizacion).get();
        return modelMapper.map(autorizacion, AutorizacionDto.class);
    }

    @Override
    public AutorizacionDto findAutorizacionByIdSoli(Long idSolicitud) {
        AutorizacionEntity autorizacionEntity = autorizacionRepository
                .buscarAutorizacionByIdSoli(idSolicitud);
        return modelMapper.map(autorizacionEntity, AutorizacionDto.class);
    }
}
