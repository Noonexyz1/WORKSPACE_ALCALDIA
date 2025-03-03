package com.prototipo.application.adapter;

import com.prototipo.application.mapper.MapperApplicationAbstract;
import com.prototipo.application.modelDto.*;
import com.prototipo.application.pager.PaginableIn;
import com.prototipo.application.pager.PaginableOut;
import com.prototipo.application.port.*;
import com.prototipo.application.useCase.ResponsableService;
import com.prototipo.domain.model.*;
import com.prototipo.infrastructure.rest.report.NotaDePedidoReport;
import com.prototipo.infrastructure.rest.report.ReporteReport;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ResponsableAdapter implements ResponsableService {

    //Para que haria otro ResponsableAbstract para esta clase???
    //Si unicamente puedo ADAPTAR una implementacion existente para esta!! ;D
    private SolicitudAbstract solicitudAbstract;
    private ReportesPDFAbstract reportesPDFAbstract;

    private AprobacionAbstract aprobacionAbstract;
    private MapperApplicationAbstract mapperApplicationAbstract;
    private AutorizacionAbstract autorizacionAbstract;
    private FotocopiaAbstract fotocopiaAbstract;

    public ResponsableAdapter(
            SolicitudAbstract solicitudAbstract,
            ReportesPDFAbstract reportesPDFAbstract,
            AprobacionAbstract aprobacionAbstract,
            MapperApplicationAbstract mapperApplicationAbstract,
            AutorizacionAbstract autorizacionAbstract,
            FotocopiaAbstract fotocopiaAbstract) {

        this.solicitudAbstract = solicitudAbstract;
        this.reportesPDFAbstract = reportesPDFAbstract;
        this.aprobacionAbstract = aprobacionAbstract;
        this.mapperApplicationAbstract = mapperApplicationAbstract;
        this.autorizacionAbstract = autorizacionAbstract;
        this.fotocopiaAbstract = fotocopiaAbstract;
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
        PaginableOut<AutorizacionDto> soliAutorizadas = aprobacionAbstract
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
        PaginableOut<FinalizacionDto> finalizacionDtoList = aprobacionAbstract
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
        PaginableOut<AutorizacionDto> soliAutorizadas = aprobacionAbstract
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
        PaginableOut<FinalizacionDto> finalizacionDtoList = aprobacionAbstract
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
        solicitudAbstract.guardarFinalizacionAbs(finalizacionDto);

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
    public void generarNotaPedidoPDF(Long idSolicitud) throws JRException, IOException {
        // Llamar al servicio de manera sincrónica en este caso
        List<NotaDePedido> notaDePedidoList = listaDeNotasDePedido(idSolicitud);
        exportToPdf(idSolicitud, notaDePedidoList);
    }

    private byte[] exportToPdf(Long idSolicitud, List<NotaDePedido> notaDePedidoList) throws JRException, IOException {
        String pdfOutputDirectory = "/home/kali/Downloads/pdfs";
        JasperPrint jasperPrint = getReport(idSolicitud, notaDePedidoList);

        // Crear el directorio si no existe
        File outputDir = new File(pdfOutputDirectory);
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }

        // Ruta del archivo PDF
        String outputFilePath = pdfOutputDirectory + "/notaPedido_" + idSolicitud + ".pdf";

        // Exportar el PDF a un archivo
        JasperExportManager.exportReportToPdfFile(jasperPrint, outputFilePath);

        // Devolver el contenido del PDF como un arreglo de bytes
        return Files.readAllBytes(Paths.get(outputFilePath));
    }

    private JasperPrint getReport(Long idSolicitud, List<NotaDePedido> notaDePedidoList) throws JRException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("templates/report/notaPedido.jrxml");

        if (inputStream == null) {
            throw new RuntimeException("No se pudo encontrar el archivo notaPedido.jrxml en el classpath.");
        }

        LocalDate fechaActual = LocalDate.now();
        DateTimeFormatter formato = DateTimeFormatter
                .ofPattern("dd 'de' MMMM 'de' yyyy", new Locale("es", "ES"));


        SolicitudDto solicitudDto = solicitudAbstract.buscarSolicitudByIdAbstract(idSolicitud);
        Solicitud solicitud = mapperApplicationAbstract.mapearAbstract(solicitudDto, Solicitud.class);

        List<NotaDePedidoReport> listNotaPedidoPDF = notaDePedidoList.stream()
                .map(x -> mapperApplicationAbstract.mapearAbstract(x, NotaDePedidoReport.class))
                .toList();

        Map<String, Object> params = new HashMap<>();
        params.put("fecha", fechaActual.format(formato));
        params.put("imageDir", "classpath:/static/images/");
        params.put("nombreServicio", solicitud.getNombreServicio());
        params.put("precioTotal", BigDecimal.valueOf(solicitud.getPrecioTotal()).setScale(2, RoundingMode.HALF_UP).doubleValue());
        params.put("ds", new JRBeanCollectionDataSource(listNotaPedidoPDF));

        JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);

        return JasperFillManager.fillReport(jasperReport, params, new JREmptyDataSource());
    }


    @Override
    public void generarReportePDF(Long idSolicitud) throws JRException {
        List<Reporte> listReport = listaDeReportes(idSolicitud);
        exportToPdf2(idSolicitud, listReport);
    }

    private byte[] exportToPdf2(Long idSolicitud, List<Reporte> listReport)
            throws JRException {

        JasperPrint jasperPrint = getReport2(idSolicitud, listReport);
        return JasperExportManager.exportReportToPdf(jasperPrint);
    }

    private JasperPrint getReport2(Long idSolicitud, List<Reporte> listReport)
            throws JRException {
        //Ruta total
        String filePath = "src" + File.separator +
                "main" + File.separator +
                "resources" + File.separator +
                "templates" + File.separator +
                "report" + File.separator +
                "reporte.jrxml";

        // Formato con nombre del mes completo
        LocalDate fechaActual = LocalDate.now();
        DateTimeFormatter formato = DateTimeFormatter
                .ofPattern("dd 'de' MMMM 'de' yyyy", new Locale("es", "ES"));

        SolicitudDto solicitudDto = solicitudAbstract.buscarSolicitudByIdAbstract(idSolicitud);
        Solicitud solicitud = mapperApplicationAbstract.mapearAbstract(solicitudDto, Solicitud.class);

        List<ReporteReport> listReporte = listReport.stream()
                .map(x -> mapperApplicationAbstract.mapearAbstract(x, ReporteReport.class))
                .toList();

        Map<String, Object> params = new HashMap<>();
        // Asigna los campos de SolicitudReport a los parámetros del reporte
        params.put("fecha", fechaActual.format(formato));
        params.put("imageDir", "classpath:/static/images/");
        params.put("nombreServicio", solicitud.getNombreServicio());
        params.put("precioTotal", BigDecimal.valueOf(solicitud.getPrecioTotal()).setScale(2, RoundingMode.HALF_UP).doubleValue());

        params.put("paginaTotal", solicitud.getPaginaTotal());
        params.put("copiaTotal", solicitud.getCopiaTotal());

        params.put("ds", new JRBeanCollectionDataSource(listReporte));

        JasperReport jasperReport = JasperCompileManager.compileReport(filePath);

        return JasperFillManager.fillReport(jasperReport, params, new JREmptyDataSource());
    }
}
