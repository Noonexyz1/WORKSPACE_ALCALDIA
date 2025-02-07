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
    private CotizacionRepository cotizacionRepository;
    @Autowired
    private AutorizacionRepository autorizacionRepository;
    @Autowired
    private UsuarioUnidadRepository usuarioUnidadRepository;

    //Tu unicamente deberias traerla Solicitud
    @Override
    public SolicitudDto solicitarFotocopiarAbstract(SolicitudDto solicitudDto) {
        SolicitudEntity solicitudEntity = modelMapper.map(solicitudDto, SolicitudEntity.class);
        SolicitudEntity solicitudEntityResp = solicitudRepository.save(solicitudEntity);
        return modelMapper.map(solicitudEntityResp, SolicitudDto.class);
    }

    @Override
    public void guardarPdfDeLaSolicitudAbstract(ArchivoPdfDto archivoPdfDto) {

    }

    @Override
    public void guardarRegistroSolicitud(DetalleSolicitudDto detalleSolicitudDto) {
        DetalleSolicitudEntity solicitud = modelMapper
                .map(detalleSolicitudDto, DetalleSolicitudEntity.class);
        detalleSolicitudRepository.save(solicitud);
    }

    @Override
    public List<SolicitudDto> getListaSolicitudesAbstract(
            Long idUsuarioUnidad,
            Long page,
            Long size) {

        List<SolicitudEntity> listSoli = solicitudRepository
                .findAllByIdUserUnidad(idUsuarioUnidad);
        return listSoli.stream()
                .map(x -> modelMapper.map(x, SolicitudDto.class))
                .toList();
    }

    @Override
    public List<SolicitudDto> getListaSolicitudesByUnidad(Long idUnidad) {

        return null;
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
    public SolicitudDto buscarSolicitudByFkUnidad(Long idUnidad) {

        return null;
    }

    @Override
    public List<DetalleSolicitudDto> getListaDetalleSolicitudAbstract(Long idSolicitud) {
        List<DetalleSolicitudEntity> list = detalleSolicitudRepository
                .findAllBySolicitudId(idSolicitud);
        return list.stream()
                .map(x -> modelMapper.map(x, DetalleSolicitudDto.class))
                .toList();
    }

    @Override
    public List<FinalizacionDto> listFinalizacionSolicitudAbs(Long idFunUni, Long page, Long size) {

        return null;
    }

    @Override
    public List<DetalleSolicitudDto> findListDetalleSoliBySolicitudIdAbs(Long idSolicitud) {
        List<DetalleSolicitudEntity> allBySolicitudId = detalleSolicitudRepository.findAllBySolicitudId(idSolicitud);
        return allBySolicitudId.stream()
                .map(x -> modelMapper.map(x, DetalleSolicitudDto.class))
                .toList();
    }

    @Override
    public void guardarCotizacionAbs(CotizacionDto cotizacionDto) {
        // Intenta cargar el DetalleSolicitudEntity desde la base de datos
        DetalleSolicitudEntity detalleSolicitud = null;

        if (cotizacionDto.getFkDetalleSolicitud() != null && cotizacionDto.getFkDetalleSolicitud().getId() != null) {
            detalleSolicitud = detalleSolicitudRepository.findById(cotizacionDto.getFkDetalleSolicitud().getId())
                    .orElseThrow(() -> new IllegalArgumentException("DetalleSolicitud no encontrado"));
        } else {
            // O persiste uno nuevo si no tiene ID (asegúrate de que tenga todos los campos requeridos)
            detalleSolicitud = modelMapper.map(cotizacionDto.getFkDetalleSolicitud(), DetalleSolicitudEntity.class);
            detalleSolicitud = detalleSolicitudRepository.save(detalleSolicitud);
        }

        // Mapear y asignar
        CotizacionEntity cotizacion = new CotizacionEntity();
        cotizacion.setPrecioUnitario(cotizacionDto.getPrecioUnitario());
        cotizacion.setPrecioTotal(cotizacionDto.getPrecioTotal());
        cotizacion.setFkDetalleSolicitud(detalleSolicitud);

        // Guardar cotización
        cotizacionRepository.save(cotizacion);
    }

    @Override
    public void guardarAutorizacionAbs(AutorizacionDto autorizacionDto) {
        AutorizacionEntity autorizacion = new AutorizacionEntity();

        // Mapear campos simples
        autorizacion.setFecha(autorizacionDto.getFecha());
        autorizacion.setTotalAutorizado(autorizacionDto.getTotalAutorizado());
        autorizacion.setTotalCotizadoBs(autorizacionDto.getTotalCotizadoBs());
        autorizacion.setFinaliFlag(autorizacionDto.getFinaliFlag());

        //aqui tengo que traer la instancia de la solicitud con ID, porque si no, no esta dentro del contexto
        SolicitudEntity solicitudEntity = solicitudRepository
                .findById(autorizacionDto.getFkSolicitud().getId()).orElseThrow();

        autorizacion.setFkSolicitud(solicitudEntity);
        //aqui tengo que traer la instancia de UsuarioUnidad con ID, porque si no, no esta dentro del contexto
        UsuarioUnidadEntity usuarioEntity = usuarioUnidadRepository
                .findById(autorizacionDto.getFkUsuarioResponsable().getId()).orElseThrow();

        autorizacion.setFkUsuarioResponsable(usuarioEntity);
        autorizacion.setFinaliFlag(autorizacion.getFinaliFlag());

        autorizacionRepository.save(autorizacion);

        //Cambio de estado de la solicitud
        //Se necesita del contexto de JPA donde vive esta entidad a ser editada
        SolicitudEntity solicitudRepositoryById = solicitudRepository
                .findById(autorizacionDto.getFkSolicitud().getId())
                .orElseThrow();

        solicitudRepositoryById.setAutoriFlag(autorizacionDto.getFkSolicitud().getAutoriFlag());
        solicitudRepository.save(solicitudRepositoryById);

    }

    @Override
    public AutorizacionDto buscarAutorizacionByIdSoliAbs(Long idSolicitud) {
        AutorizacionEntity autorizacion = autorizacionRepository
                .buscarAutorizacionByIdSoli(idSolicitud);
        return modelMapper.map(autorizacion, AutorizacionDto.class);
    }

    @Override
    public AutorizacionDto buscarAutorizacionByIdAbs(Long idAutorizacion) {
        AutorizacionEntity autorizacion = autorizacionRepository
                .findById(idAutorizacion).get();
        return modelMapper.map(autorizacion, AutorizacionDto.class);
    }

    @Override
    public void guardarFinalizacionAbs(FinalizacionDto finalizacionDto) {
        FinalizacionEntity finalizacion = new FinalizacionEntity();

        // Mapear los valores simples
        finalizacion.setFecha(finalizacionDto.getFecha());
        finalizacion.setTotalEjecutado(finalizacionDto.getTotalEjecutado());
        finalizacion.setTotalEjecutadoBs(finalizacionDto.getTotalEjecutadoBs());

        // Mapear fkAutorizacion manualmente
        AutorizacionEntity autorizacionEntity = new AutorizacionEntity();
        autorizacionEntity.setId(finalizacionDto.getFkAutorizacion().getId());
        // Mapear propiedades adicionales de fkAutorizacion si es necesario
        finalizacion.setFkAutorizacion(autorizacionEntity);

        finalizacionRepository.save(finalizacion);
    }

}
