package com.prototipo.application.adapter;

import com.prototipo.application.mapper.MapperApplicationAbstract;
import com.prototipo.application.modelDto.*;
import com.prototipo.application.port.SolicitudAbstract;
import com.prototipo.application.useCase.SolicitudService;
import com.prototipo.domain.enums.*;
import com.prototipo.domain.model.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class SolicitudAdapter implements SolicitudService {

    private SolicitudAbstract solicitudAbstract;
    private MapperApplicationAbstract mapperApplicationAbstract;

    public SolicitudAdapter(
            SolicitudAbstract solicitudAbstract,
            MapperApplicationAbstract mapperApplicationAbstract) {

        this.solicitudAbstract = solicitudAbstract;
        this.mapperApplicationAbstract = mapperApplicationAbstract;
    }

    @Override
    public void solicitarFotocopiarService(Solicitud solicitudDomain, List<Fotocopia> fotocopiasList) {
        SolicitudDto solicitudDto = mapperApplicationAbstract
                .mapearAbstract(solicitudDomain, SolicitudDto.class);
        solicitudDto.setNombreServicio(TipoServicioEnum.FOTOCOPIA.getNombre());

        //Primero guardamos en la tabla Solitcitud
        SolicitudDto solicitudDtoResp = solicitudAbstract
                .solicitarFotocopiarAbstract(solicitudDto);

        Solicitud solicitudResp = mapperApplicationAbstract
                .mapearAbstract(solicitudDtoResp, Solicitud.class);

        Double precioTotalSoli = fotocopiasList.stream()
                .map(x -> {
                    x.setFkSolicitud(solicitudResp);
                    return guardadFotocopiaAbstrac(x);
                })
                .reduce(0.0, Double::sum);

        solicitudDtoResp.setPrecioTotal(precioTotalSoli);
        solicitudAbstract.solicitarFotocopiarAbstract(solicitudDtoResp);
    }

    private void guardadDetalleSolicitudAbstrac(DetalleSolicitud solicitud){
        DetalleSolicitudDto detalleSolicitudDto = mapperApplicationAbstract
                .mapearAbstract(solicitud, DetalleSolicitudDto.class);
        solicitudAbstract.guardarRegistroSolicitud(detalleSolicitudDto);
    }

    private double guardadFotocopiaAbstrac(Fotocopia fotocopia){
        FotocopiaDto fotocopiaDto = mapperApplicationAbstract
                .mapearAbstract(fotocopia, FotocopiaDto.class);

        ServicioFotocopiaDto solicitudFotocopiaDto = solicitudAbstract
                .findServicioFotocopia(fotocopiaDto.getFkServicioFotocopia());

        double precioDocu = fotocopia.getNroCopias() *
                fotocopia.getNroPaginas() *
                solicitudFotocopiaDto.getPrecioRef();

        fotocopiaDto.setFkServicioFotocopia(solicitudFotocopiaDto);
        fotocopiaDto.setPrecioDocu(precioDocu);

        solicitudAbstract.guardarRegistroFotocopia(fotocopiaDto);

        return precioDocu;
    }

    @Override
    public void registrarFotocopiarService(Solicitud solicitudDomain, List<DetalleSolicitud> list) {
        SolicitudDto solicitudDto = mapperApplicationAbstract
                .mapearAbstract(solicitudDomain, SolicitudDto.class);
        SolicitudDto solicitudResp = solicitudAbstract.solicitarFotocopiarAbstract(solicitudDto);
        Solicitud solicitudDomainResp = mapperApplicationAbstract.mapearAbstract(solicitudResp, Solicitud.class);

        list.forEach(x -> {
            x.setFkSolicitud(solicitudDomainResp);
            guardadDetalleSolicitudAbstrac(x);
        });

    }

    @Override
    public void guardarPdfDeLaSolicitudAbstract(ArchivoPdf archivoPdfDomain) {
        ArchivoPdfDto archivoPdfDto = mapperApplicationAbstract.mapearAbstract(archivoPdfDomain, ArchivoPdfDto.class);
        solicitudAbstract.guardarPdfDeLaSolicitudAbstract(archivoPdfDto);
    }

    @Override
    public List<Solicitud> getListaSolicitudesService(Long idUsuarioUnidad, Long page, Long size) {
        List<SolicitudDto> listSoli = solicitudAbstract
                .getListaSolicitudesAbstract(idUsuarioUnidad, page, size);
        //Quiero filtar las solicitudes segun el Usuario que lo esta pidiendo
        return listSoli.stream()
                .map(x -> mapperApplicationAbstract.mapearAbstract(x, Solicitud.class))
                .toList();
    }

    @Override
    public void guardarSolicitudService(Solicitud solicitudDomain) {
        //TODO
    }

    @Override
    public Solicitud buscarSolicitudService(Long id) {
        SolicitudDto solicitudDto = solicitudAbstract
                .buscarSolicitudByIdAbstract(id);
        return mapperApplicationAbstract
                .mapearAbstract(solicitudDto, Solicitud.class);
    }

    @Override
    public List<Finalizacion> listFinalizacionSolicitud(Long idFunUni, Long page, Long size) {
        List<FinalizacionDto> listFinDto = solicitudAbstract
                .listFinalizacionSolicitudAbs(idFunUni, page, size);
        return listFinDto.stream()
                .map(x -> mapperApplicationAbstract.mapearAbstract(x, Finalizacion.class))
                .toList();
    }

    @Override
    public List<Fotocopia> findListDetalleSoliBySolicitudId(Long idSolicitud) {
        List<FotocopiaDto> listDetalleDto = solicitudAbstract
                .findListDetalleSoliBySolicitudIdAbs(idSolicitud);
        return listDetalleDto.stream()
                .map(x ->
                        mapperApplicationAbstract.mapearAbstract(x, Fotocopia.class)
                )
                .toList();
    }

    @Override
    public void guardarAutorizacion(Autorizacion autorizacion) {
        //Unicamente aqui va la logica, utilizando los mismos recuros de su dominio
        LocalDate fechaActual = LocalDate.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        autorizacion.setFecha(fechaActual.format(formato));
        autorizacion.setFinaliFlag(0L);

        AutorizacionDto autorizacionDto = mapperApplicationAbstract
                .mapearAbstract(autorizacion, AutorizacionDto.class);
        solicitudAbstract.guardarAutorizacionAbs(autorizacionDto);

        SolicitudDto solicitudDto = solicitudAbstract
                .buscarSolicitudByIdAbstract(autorizacion.getFkSolicitud().getId());
        solicitudDto.setAutoriFlag(1L);
        solicitudAbstract.guardarSolicitudAbstract(solicitudDto);
    }

    @Override
    public Autorizacion buscarAutorizacionByIdSoli(Long idSolicitud) {
        AutorizacionDto autorizacionDto = solicitudAbstract
                .buscarAutorizacionByIdSoliAbs(idSolicitud);
        return mapperApplicationAbstract
                .mapearAbstract(autorizacionDto, Autorizacion.class);
    }

    @Override
    public Autorizacion buscarAutorizacionById(Long idAutorizacion) {
        AutorizacionDto autorizacionDto = solicitudAbstract
                .buscarAutorizacionByIdAbs(idAutorizacion);
        return mapperApplicationAbstract
                .mapearAbstract(autorizacionDto, Autorizacion.class);
    }

    @Override
    public void guardarFinalizacion(Finalizacion finalizacion) {
        //Aqui tiene que ir la logica
        FinalizacionDto finalizacionDto = mapperApplicationAbstract
                .mapearAbstract(finalizacion, FinalizacionDto.class);

        LocalDate fechaActual = LocalDate.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        finalizacionDto.setFecha(fechaActual.format(formato));
        solicitudAbstract.guardarFinalizacionAbs(finalizacionDto);

        AutorizacionDto autorizacionDto = solicitudAbstract
                .buscarAutorizacionByIdAbs(finalizacion.getFkAutorizacion().getId());

        autorizacionDto.setFinaliFlag(1L);
        solicitudAbstract.guardarAutorizacionAbs(autorizacionDto);
    }

    @Override
    public void eliminarSolicitudById(Long idSolicitud) {
        SolicitudDto solicitudDto = solicitudAbstract
                .buscarSolicitudByIdAbstract(idSolicitud);
        solicitudDto.setIsActive(false);
        solicitudAbstract.guardarSolicitudAbstract(solicitudDto);
    }

    @Override
    public List<String> listarTamano() {
        return List.of(
                TamanoPaginaEnum.CARTA.getNombre(),
                TamanoPaginaEnum.OFICIO.getNombre()
        );
    }

    @Override
    public List<String> listarAnversoReverso() {
        return List.of(
                AnversoReversoEnum.ANVERSO.getNombre(),
                AnversoReversoEnum.ANVERSO_REVERSO.getNombre()
        );
    }

    @Override
    public List<String> listarColor() {
        return List.of(
                ColorFotocopiaEnum.BLANCO_NEGRO.getNombre(),
                ColorFotocopiaEnum.COLOR.getNombre()
        );
    }

    @Override
    public List<Solicitud> getListaSolicitudesAutoriService(Long idUserUni, Long page, Long size) {
        List<SolicitudDto> listSoli = solicitudAbstract
                .getListaSolicitudesAutoriAbstract(idUserUni, page, size);
        //Quiero filtar las solicitudes segun el Usuario que lo esta pidiendo
        return listSoli.stream()
                .map(x -> mapperApplicationAbstract.mapearAbstract(x, Solicitud.class))
                .toList();
    }

    @Override
    public List<Solicitud> getListaSolicitudesFinaliService(Long idUserUni, Long page, Long size) {
        List<SolicitudDto> listSoli = solicitudAbstract
                .getListaSolicitudesFinaliAbstract(idUserUni, page, size);
        //Quiero filtar las solicitudes segun el Usuario que lo esta pidiendo
        return listSoli.stream()
                .map(x -> mapperApplicationAbstract.mapearAbstract(x, Solicitud.class))
                .toList();
    }

    @Override
    public List<DetalleSolicitud> listDetalleSolicitud(Long idSolicitud) {
        List<DetalleSolicitudDto> list = solicitudAbstract.
                getListaDetalleSolicitudAbstract(idSolicitud);
        return list.stream()
                .map(x ->
                        mapperApplicationAbstract.mapearAbstract(x, DetalleSolicitud.class))
                .toList();
    }

    @Override
    public List<Fotocopia> listFotocopiaSolicitud(Long idSolicitud) {
        List<FotocopiaDto> list = solicitudAbstract.
                getListaFotocopiaSolicitudAbstract(idSolicitud);
        return list.stream()
                .map(x ->
                        mapperApplicationAbstract.mapearAbstract(x, Fotocopia.class))
                .toList();
    }
}
