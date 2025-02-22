package com.prototipo.application.adapter;

import com.prototipo.application.mapper.MapperApplicationAbstract;
import com.prototipo.application.modelDto.*;
import com.prototipo.application.pager.PaginableIn;
import com.prototipo.application.pager.PaginableOut;
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

        List<FotocopiaDto> fotocopiaSoliReso = solicitudAbstract
                .getFotocopiasSolicitudAbstract(solicitudDtoResp.getId());

        Long paginaTotal = fotocopiaSoliReso.stream()
                .map(x -> x.getNroPaginas())
                .reduce(0L, Long::sum);

        Long copiaTotal = fotocopiaSoliReso.stream()
                .map(x -> x.getNroCopias())
                .reduce(0L, Long::sum);

        solicitudDtoResp.setPrecioTotal(precioTotalSoli);

        solicitudDtoResp.setPaginaTotal(paginaTotal);
        solicitudDtoResp.setCopiaTotal(copiaTotal);

        solicitudAbstract.solicitarFotocopiarAbstract(solicitudDtoResp);
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
    public PaginableOut<Solicitud> getListaSolicitudesService(PaginableIn paginableIn) {
        PaginableOut<SolicitudDto> paginableOut = solicitudAbstract
                .getListaSolicitudesAbstract(paginableIn);

        PaginableOut<Solicitud> paginableResponse = PaginableOut
                .<Solicitud>builder()
                .content(
                        paginableOut.getContent().stream()
                                .map(x -> mapperApplicationAbstract
                                        .mapearAbstract(x, Solicitud.class)
                                )
                                .toList()
                )
                .totalPages(paginableOut.getTotalPages())
                .totalElements(paginableOut.getTotalElements())
                .build();

        //Quiero filtar las solicitudes segun el Usuario que lo esta pidiendo
        return paginableResponse;
    }

    @Override
    public Solicitud buscarSolicitudService(Long id) {
        SolicitudDto solicitudDto = solicitudAbstract
                .buscarSolicitudByIdAbstract(id);
        return mapperApplicationAbstract
                .mapearAbstract(solicitudDto, Solicitud.class);
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
    public PaginableOut<Solicitud> getListaSolicitudesAutoriService(PaginableIn paginableIn) {
        PaginableOut<SolicitudDto> paginableOut = solicitudAbstract
                .getListaSolicitudesAutoriAbstract(paginableIn);

        PaginableOut<Solicitud> paginableResponse = PaginableOut
                .<Solicitud>builder()
                .content(
                        paginableOut.getContent()
                                .stream()
                                .map(x ->
                                        mapperApplicationAbstract.mapearAbstract(x, Solicitud.class))
                                .toList()
                )
                .totalPages(paginableOut.getTotalPages())
                .totalElements(paginableOut.getTotalElements())
                .build();
        //Quiero filtar las solicitudes segun el Usuario que lo esta pidiendo
        return paginableResponse;
    }

    @Override
    public PaginableOut<Solicitud> getListaSolicitudesFinaliService(PaginableIn paginableIn) {
        PaginableOut<SolicitudDto> paginableOut = solicitudAbstract
                .getListaSolicitudesFinaliAbstract(paginableIn);

        PaginableOut<Solicitud> paginableResponse = PaginableOut
                .<Solicitud>builder()
                .content(
                        paginableOut.getContent()
                                .stream()
                                .map(x ->
                                        mapperApplicationAbstract.mapearAbstract(x, Solicitud.class))
                                .toList()
                )
                .totalPages(paginableOut.getTotalPages())
                .totalElements(paginableOut.getTotalElements())
                .build();

        //Quiero filtar las solicitudes segun el Usuario que lo esta pidiendo
        return paginableResponse;
    }

    @Override
    public List<Fotocopia> listFotocopiaSolicitud(Long idSolicitud) {
        List<FotocopiaDto> list = solicitudAbstract.
                getFotocopiasSolicitudAbstract(idSolicitud);
        return list.stream()
                .map(x ->
                        mapperApplicationAbstract.mapearAbstract(x, Fotocopia.class))
                .toList();
    }
}
