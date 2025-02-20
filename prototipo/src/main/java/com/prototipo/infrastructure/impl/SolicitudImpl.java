package com.prototipo.infrastructure.impl;

import com.prototipo.application.modelDto.*;
import com.prototipo.application.port.SolicitudAbstract;
import com.prototipo.infrastructure.persistence.db.entity.*;
import com.prototipo.infrastructure.persistence.db.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SolicitudImpl implements SolicitudAbstract {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private DetalleSolicitudRepository detalleSolicitudRepository;
    @Autowired
    private SolicitudRepository solicitudRepository;
    @Autowired
    private FinalizacionRepository finalizacionRepository;
    @Autowired
    private AutorizacionRepository autorizacionRepository;
    @Autowired
    private UsuarioUnidadRepository usuarioUnidadRepository;
    @Autowired
    private FotocopiaRepository fotocopiaRepository;
    @Autowired
    private ServicioFotocopiaRepository servicioFotocopiaRepository;

    //Tu unicamente deberias traerla Solicitud
    @Override
    public SolicitudDto solicitarFotocopiarAbstract(SolicitudDto solicitudDto) {
        SolicitudEntity solicitudEntity = modelMapper.map(solicitudDto, SolicitudEntity.class);
        SolicitudEntity solicitudEntityResp = solicitudRepository.save(solicitudEntity);
        return modelMapper.map(solicitudEntityResp, SolicitudDto.class);
    }

    @Override
    public void guardarRegistroFotocopia(FotocopiaDto fotocopiaDto) {
        FotocopiaEntity fotocopiaEntity = modelMapper
                .map(fotocopiaDto, FotocopiaEntity.class);
        fotocopiaRepository.save(fotocopiaEntity);
    }

    @Override
    public ServicioFotocopiaDto findServicioFotocopia(ServicioFotocopiaDto fkServicioFotocopia) {
        ServicioFotocopiaEntity serFotoEntity = servicioFotocopiaRepository
                .findByAnverColorTam(
                    fkServicioFotocopia.getAnverRever(),
                    fkServicioFotocopia.getColor(),
                    fkServicioFotocopia.getTamano()
                );

        return modelMapper.map(serFotoEntity, ServicioFotocopiaDto.class);
    }

    @Override
    public List<SolicitudDto> getListaSolicitudesAutoriAbstract(Long idUserUni, Long page, Long size) {
        List<SolicitudEntity> listSoli = solicitudRepository
                .findAllAutoriByIdUserUnidad(idUserUni);
        return listSoli.stream()
                .map(x -> modelMapper.map(x, SolicitudDto.class))
                .toList();
    }

    @Override
    public List<SolicitudDto> getListaSolicitudesFinaliAbstract(Long idUserUni, Long page, Long size) {
        List<SolicitudEntity> listSoli = solicitudRepository
                .findAllFinaliByIdUserUnidad(idUserUni);
        return listSoli.stream()
                .map(x -> modelMapper.map(x, SolicitudDto.class))
                .toList();
    }

    @Override
    public List<SolicitudDto> getListaSolicitudesAbstract(Long idUsuarioUnidad, Long page, Long size) {
        List<SolicitudEntity> listSoli = solicitudRepository
                .findAllByIdUserUnidad(idUsuarioUnidad);
        return listSoli.stream()
                .map(x -> modelMapper.map(x, SolicitudDto.class))
                .toList();
    }

    @Override
    public void guardarSolicitudAbstract(SolicitudDto solicitudDto) {
        SolicitudEntity solicitudEntity = modelMapper.map(solicitudDto, SolicitudEntity.class);
        solicitudRepository.save(solicitudEntity);
    }

    @Override
    public SolicitudDto buscarSolicitudByIdAbstract(Long id) {
        SolicitudEntity solicitudEntity = solicitudRepository
                .findById(id).orElseThrow();
        return modelMapper.map(solicitudEntity, SolicitudDto.class);
    }

    @Override
    public List<FotocopiaDto> getFotocopiasSolicitudAbstract(Long idSolicitud) {
        List<FotocopiaEntity> list = detalleSolicitudRepository
                .findAllFotocopiaByIdSoli(idSolicitud);
        return list.stream()
                .map(x -> modelMapper.map(x, FotocopiaDto.class))
                .toList();
    }

    @Override
    public List<FotocopiaDto> findListDetalleSoliBySolicitudIdAbs(Long idSolicitud) {
        List<FotocopiaEntity> allBySolicitudId = detalleSolicitudRepository
                .findAllFotocopiaByIdSoli(idSolicitud);
        return allBySolicitudId.stream()
                .map(x -> modelMapper.map(x, FotocopiaDto.class))
                .toList();
    }

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
