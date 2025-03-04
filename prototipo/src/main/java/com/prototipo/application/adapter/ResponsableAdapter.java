package com.prototipo.application.adapter;

import com.prototipo.application.mapper.MapperApplicationAbstract;
import com.prototipo.application.modelDto.*;
import com.prototipo.application.pager.PaginableIn;
import com.prototipo.application.pager.PaginableOut;
import com.prototipo.application.port.out.*;
import com.prototipo.application.port.in.ResponsableService;
import com.prototipo.domain.model.*;
import com.prototipo.infrastructure.rest.report.NotaDePedidoReport;
import com.prototipo.infrastructure.rest.report.ReporteReport;

import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class ResponsableAdapter implements ResponsableService {

    //Para que haria otro ResponsableAbstract para esta clase???
    //Si unicamente puedo ADAPTAR una implementacion existente para esta!! ;D
    private SolicitudAbstract solicitudAbstract;

    //Nose que hace esto
    private ReportesPDFAbstract reportesPDFAbstract;

    private MapperApplicationAbstract mapperApplicationAbstract;
    private AutorizacionAbstract autorizacionAbstract;
    private FotocopiaAbstract fotocopiaAbstract;
    private FinalizacionAbstract finalizacionAbstract;
    private ResponsablePDFAbstract responsablePDFAbstract;

    public ResponsableAdapter(
            SolicitudAbstract solicitudAbstract,
            ReportesPDFAbstract reportesPDFAbstract,
            MapperApplicationAbstract mapperApplicationAbstract,
            AutorizacionAbstract autorizacionAbstract,
            FotocopiaAbstract fotocopiaAbstract,
            FinalizacionAbstract finalizacionAbstract,
            ResponsablePDFAbstract responsablePDFAbstract) {

        this.solicitudAbstract = solicitudAbstract;
        this.reportesPDFAbstract = reportesPDFAbstract;
        this.mapperApplicationAbstract = mapperApplicationAbstract;
        this.autorizacionAbstract = autorizacionAbstract;
        this.fotocopiaAbstract = fotocopiaAbstract;
        this.finalizacionAbstract = finalizacionAbstract;
        this.responsablePDFAbstract = responsablePDFAbstract;
    }

    @Override
    public void rechazarSolicitud(Long idSolicitud, Long idResponsable) {
        SolicitudDto solicitudDto = solicitudAbstract
                .buscarSolicitudByIdAbstract(idSolicitud);
        solicitudDto.setIsActive(false);
        solicitudAbstract.guardarSolicitudAbstract(solicitudDto);
    }

    @Override
    public List<NotaDePedido> listaDeNotasDePedido(Long idSolicitud) {
        List<NotaDePedidoDto> notaDePedidoDtoList = reportesPDFAbstract
                .getNotaDePedidoAbstract(idSolicitud);

        return notaDePedidoDtoList.stream()
                .map(x ->
                        mapperApplicationAbstract.mapearAbstract(x, NotaDePedido.class))
                .toList();
    }

    @Override
    public List<Reporte> listaDeReportes(Long idSolicitud) {
        return reportesPDFAbstract.generarReportePDFAbstract(idSolicitud)
                .stream()
                .map(x -> mapperApplicationAbstract.mapearAbstract(x, Reporte.class))
                .toList();
    }

    @Override
    public Autorizacion obtenerAutorizacion(Long idSolicitud) {
        AutorizacionDto autorizacionDto = autorizacionAbstract
                .findAutorizacionByIdSoli(idSolicitud);
        return mapperApplicationAbstract
                .mapearAbstract(autorizacionDto, Autorizacion.class);
    }

    @Override
    public PaginableOut<Solicitud> listaDeSolicitudesPendientesByIdResponsable(PaginableIn paginableIn) {
        PaginableOut<SolicitudDto> solicitudDtos = solicitudAbstract
                .listaDeSolicitudesPendientesByIdResponsable(paginableIn);

        PaginableOut<Solicitud> paginableResponse = PaginableOut
                .<Solicitud>builder()
                .content(
                        solicitudDtos.getContent().stream()
                                .map(x -> mapperApplicationAbstract.mapearAbstract(x, Solicitud.class))
                                .toList()
                )
                .totalPages(solicitudDtos.getTotalPages())
                .totalElements(solicitudDtos.getTotalElements())
                .build();

        return paginableResponse;
    }

    @Override
    public PaginableOut<Solicitud> listaDeSolicitudesPendientesByIdSolicitud(PaginableIn paginableIn) {
        PaginableOut<SolicitudDto> solicitudDtos = solicitudAbstract
                .listaDeSolicitudesPendientesAbstractPageByIdSoli(paginableIn);

        PaginableOut<Solicitud> paginableResponse = PaginableOut
                .<Solicitud>builder()
                .content(
                        solicitudDtos.getContent().stream()
                                .map(x -> mapperApplicationAbstract.mapearAbstract(x, Solicitud.class))
                                .toList()
                )
                .totalPages(solicitudDtos.getTotalPages())
                .totalElements(solicitudDtos.getTotalElements())
                .build();

        return paginableResponse;
    }

    @Override
    public PaginableOut<Autorizacion> listaDeSolicitudesAutorizadasByIdResponsable(PaginableIn paginableIn) {
        PaginableOut<AutorizacionDto> soliAutorizadas = autorizacionAbstract
                .listaDeSoliAutorizadasAbstractPageByIdResponsable(paginableIn);

        PaginableOut<Autorizacion> paginableResponse = PaginableOut
                .<Autorizacion>builder()
                .content(
                        soliAutorizadas.getContent().stream()
                                .map(x -> mapperApplicationAbstract
                                        .mapearAbstract(x, Autorizacion.class))
                                .toList()
                )
                .totalPages(soliAutorizadas.getTotalPages())
                .totalElements(soliAutorizadas.getTotalElements())
                .build();

        return paginableResponse;
    }

    @Override
    public PaginableOut<Finalizacion> listaDeSolicitudesFinalizadasByIdResponsable(PaginableIn paginableIn) {
        PaginableOut<FinalizacionDto> finalizacionDtoList = finalizacionAbstract
                .listaDeFinalizacionesAbstractPageByIdResponsable(paginableIn);

        PaginableOut<Finalizacion> paginableResponse = PaginableOut
                .<Finalizacion>builder()
                .content(
                        finalizacionDtoList.getContent().stream()
                                .map(x -> mapperApplicationAbstract
                                        .mapearAbstract(x, Finalizacion.class))
                                .toList()
                )
                .totalPages(finalizacionDtoList.getTotalPages())
                .totalElements(finalizacionDtoList.getTotalElements())
                .build();

        return paginableResponse;
    }

    @Override
    public PaginableOut<Autorizacion> listaDeSolicitudesAutorizadasByIdSolicitud(PaginableIn paginableIn) {
        PaginableOut<AutorizacionDto> soliAutorizadas = autorizacionAbstract
                .listaDeSoliAutorizadasAbstractPageByIdSoli(paginableIn);

        PaginableOut<Autorizacion> paginableResponse = PaginableOut
                .<Autorizacion>builder()
                .content(
                        soliAutorizadas.getContent().stream()
                                .map(x -> mapperApplicationAbstract
                                        .mapearAbstract(x, Autorizacion.class))
                                .toList()
                )
                .totalPages(soliAutorizadas.getTotalPages())
                .totalElements(soliAutorizadas.getTotalElements())
                .build();

        return paginableResponse;
    }

    @Override
    public PaginableOut<Finalizacion> listaDeSolicitudesFinalizadasByIdSolicitud(PaginableIn paginableIn) {
        PaginableOut<FinalizacionDto> finalizacionDtoList = finalizacionAbstract
                .listaDeFinalizacionesAbstractPageByIdSoli(paginableIn);

        PaginableOut<Finalizacion> paginableResponse = PaginableOut
                .<Finalizacion>builder()
                .content(
                        finalizacionDtoList.getContent().stream()
                                .map(x -> mapperApplicationAbstract
                                        .mapearAbstract(x, Finalizacion.class))
                                .toList()
                )
                .totalPages(finalizacionDtoList.getTotalPages())
                .totalElements(finalizacionDtoList.getTotalElements())
                .build();

        return paginableResponse;
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
        autorizacionAbstract.guardarAutorizacionAbs(autorizacionDto);

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
        finalizacionAbstract.guardarFinalizacionAbs(finalizacionDto);

        AutorizacionDto autorizacionDto = autorizacionAbstract
                .buscarAutorizacionByIdAbs(finalizacion.getFkAutorizacion().getId());

        autorizacionDto.setFinaliFlag(1L);
        autorizacionAbstract.guardarAutorizacionAbs(autorizacionDto);
    }

    @Override
    public List<Fotocopia> listaDeFotocopias(Long idSolicitud) {
        List<FotocopiaDto> list = fotocopiaAbstract.
                getFotocopiasSolicitudAbstract(idSolicitud);
        return list.stream()
                .map(x ->
                        mapperApplicationAbstract.mapearAbstract(x, Fotocopia.class))
                .toList();
    }





    @Override
    public void generarNotaPedidoPDF(Long idSolicitud) {

        String pdfOutputDirectory = "/home/kali/Downloads/notaPedidoPDF";

        // Crear el directorio si no existe
        File outputDir = new File(pdfOutputDirectory);
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("templates/report/notaPedido.jrxml");
        if (inputStream == null) {
            throw new RuntimeException("No se pudo encontrar el archivo notaPedido.jrxml en el classpath.");
        }

        String recursoImagen = "classpath:/static/images/";




        LocalDate fechaActual = LocalDate.now();
        DateTimeFormatter formato = DateTimeFormatter
                .ofPattern("dd 'de' MMMM 'de' yyyy", new Locale("es", "ES"));
        String fechaFormateada = fechaActual.format(formato);



        SolicitudDto solicitudDto = solicitudAbstract.buscarSolicitudByIdAbstract(idSolicitud);
        Solicitud solicitud = mapperApplicationAbstract.mapearAbstract(solicitudDto, Solicitud.class);
        String nombreServicio = solicitud.getNombreServicio();
        Double precioTotalRedondeado = BigDecimal.valueOf(solicitud.getPrecioTotal()).setScale(2, RoundingMode.HALF_UP).doubleValue();


        List<NotaDePedidoReport> listNotaPedidoPDF = listaDeNotasDePedido(idSolicitud)
                .stream()
                .map(x -> mapperApplicationAbstract.mapearAbstract(x, NotaDePedidoReport.class))
                .toList();


        responsablePDFAbstract.generarNotaPedidoPDFAbs(
                idSolicitud,
                fechaFormateada,
                recursoImagen,
                nombreServicio,
                precioTotalRedondeado,
                listNotaPedidoPDF,
                inputStream,
                pdfOutputDirectory
        );

    }

    @Override
    public void generarReportePDF(Long idSolicitud) {

        String pdfOutputDirectory = "/home/kali/Downloads/reportePDF";

        // Crear el directorio si no existe
        File outputDir = new File(pdfOutputDirectory);
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("templates/report/reporte.jrxml");
        if (inputStream == null) {
            throw new RuntimeException("No se pudo encontrar el archivo reporte.jrxml en el classpath.");
        }

        String recursoImagen = "classpath:/static/images/";




        // Formato con nombre del mes completo
        LocalDate fechaActual = LocalDate.now();
        DateTimeFormatter formato = DateTimeFormatter
                .ofPattern("dd 'de' MMMM 'de' yyyy", new Locale("es", "ES"));
        String fechaActualString = fechaActual.format(formato);




        SolicitudDto solicitudDto = solicitudAbstract.buscarSolicitudByIdAbstract(idSolicitud);
        Solicitud solicitud = mapperApplicationAbstract.mapearAbstract(solicitudDto, Solicitud.class);


        String nombreServicio = solicitud.getNombreServicio();
        Double precioTotal = BigDecimal.valueOf(solicitud.getPrecioTotal()).setScale(2, RoundingMode.HALF_UP).doubleValue();
        Long paginaTotal = solicitud.getPaginaTotal();
        Long copiaTotal = solicitud.getCopiaTotal();


        List<ReporteReport> listReporte = listaDeReportes(idSolicitud)
                .stream()
                .map(x -> mapperApplicationAbstract.mapearAbstract(x, ReporteReport.class))
                .toList();

        responsablePDFAbstract.generarReportePDFAbs(
                idSolicitud,
                fechaActualString,
                recursoImagen,
                nombreServicio,
                precioTotal,
                paginaTotal,
                copiaTotal,
                listReporte,
                inputStream,
                pdfOutputDirectory
        );
    }

}
