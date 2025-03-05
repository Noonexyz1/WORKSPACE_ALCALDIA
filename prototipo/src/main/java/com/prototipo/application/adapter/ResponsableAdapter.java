package com.prototipo.application.adapter;

import com.prototipo.application.model.PaginableIn;
import com.prototipo.application.model.PaginableOut;
import com.prototipo.application.port.out.*;
import com.prototipo.application.port.in.ResponsableService;
import com.prototipo.domain.model.*;

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
    private GeneracionPDFDataAbstract generacionPDFDataAbstract;
    private AutorizacionAbstract autorizacionAbstract;
    private FotocopiaAbstract fotocopiaAbstract;
    private FinalizacionAbstract finalizacionAbstract;
    private GeneracionPDFArchivoAbstract generacionPDFArchivoAbstract;

    public ResponsableAdapter(
            SolicitudAbstract solicitudAbstract,
            GeneracionPDFDataAbstract generacionPDFDataAbstract,
            AutorizacionAbstract autorizacionAbstract,
            FotocopiaAbstract fotocopiaAbstract,
            FinalizacionAbstract finalizacionAbstract,
            GeneracionPDFArchivoAbstract generacionPDFArchivoAbstract) {

        this.solicitudAbstract = solicitudAbstract;
        this.generacionPDFDataAbstract = generacionPDFDataAbstract;
        this.autorizacionAbstract = autorizacionAbstract;
        this.fotocopiaAbstract = fotocopiaAbstract;
        this.finalizacionAbstract = finalizacionAbstract;
        this.generacionPDFArchivoAbstract = generacionPDFArchivoAbstract;
    }

    @Override
    public void rechazarSolicitud(Long idSolicitud, Long idResponsable) {
        Solicitud solicitud = solicitudAbstract
                .buscarSolicitudByIdAbstract(idSolicitud);
        solicitud.setIsActive(false);
        solicitudAbstract.guardarSolicitudAbstract(solicitud);
    }

    @Override
    public List<NotaDePedido> listaDeNotasDePedido(Long idSolicitud) {
        return generacionPDFDataAbstract.getNotaDePedidoAbstract(idSolicitud);
    }

    @Override
    public List<Reporte> listaDeReportes(Long idSolicitud) {
        return generacionPDFDataAbstract.generarReportePDFAbstract(idSolicitud);
    }

    @Override
    public Autorizacion obtenerAutorizacion(Long idSolicitud) {
        return autorizacionAbstract.findAutorizacionByIdSoli(idSolicitud);
    }

    @Override
    public PaginableOut<Solicitud> listaDeSolicitudesPendientesByIdResponsable(PaginableIn paginableIn) {
        PaginableOut<Solicitud> solicitudDtos = solicitudAbstract
                .listaDeSolicitudesPendientesByIdResponsable(paginableIn);

        PaginableOut<Solicitud> paginableResponse = PaginableOut
                .<Solicitud>builder()
                .content(solicitudDtos.getContent())
                .totalPages(solicitudDtos.getTotalPages())
                .totalElements(solicitudDtos.getTotalElements())
                .build();

        return paginableResponse;
    }

    @Override
    public PaginableOut<Solicitud> listaDeSolicitudesPendientesByIdSolicitud(PaginableIn paginableIn) {
        PaginableOut<Solicitud> solicitudDtos = solicitudAbstract
                .listaDeSolicitudesPendientesAbstractPageByIdSoli(paginableIn);

        PaginableOut<Solicitud> paginableResponse = PaginableOut
                .<Solicitud>builder()
                .content(solicitudDtos.getContent())
                .totalPages(solicitudDtos.getTotalPages())
                .totalElements(solicitudDtos.getTotalElements())
                .build();

        return paginableResponse;
    }

    @Override
    public PaginableOut<Autorizacion> listaDeSolicitudesAutorizadasByIdResponsable(PaginableIn paginableIn) {
        PaginableOut<Autorizacion> soliAutorizadas = autorizacionAbstract
                .listaDeSoliAutorizadasAbstractPageByIdResponsable(paginableIn);

        PaginableOut<Autorizacion> paginableResponse = PaginableOut
                .<Autorizacion>builder()
                .content(soliAutorizadas.getContent())
                .totalPages(soliAutorizadas.getTotalPages())
                .totalElements(soliAutorizadas.getTotalElements())
                .build();

        return paginableResponse;
    }

    @Override
    public PaginableOut<Finalizacion> listaDeSolicitudesFinalizadasByIdResponsable(PaginableIn paginableIn) {
        PaginableOut<Finalizacion> finalizacionDtoList = finalizacionAbstract
                .listaDeFinalizacionesAbstractPageByIdResponsable(paginableIn);

        PaginableOut<Finalizacion> paginableResponse = PaginableOut
                .<Finalizacion>builder()
                .content(finalizacionDtoList.getContent())
                .totalPages(finalizacionDtoList.getTotalPages())
                .totalElements(finalizacionDtoList.getTotalElements())
                .build();

        return paginableResponse;
    }

    @Override
    public PaginableOut<Autorizacion> listaDeSolicitudesAutorizadasByIdSolicitud(PaginableIn paginableIn) {
        PaginableOut<Autorizacion> soliAutorizadas = autorizacionAbstract
                .listaDeSoliAutorizadasAbstractPageByIdSoli(paginableIn);

        PaginableOut<Autorizacion> paginableResponse = PaginableOut
                .<Autorizacion>builder()
                .content(soliAutorizadas.getContent())
                .totalPages(soliAutorizadas.getTotalPages())
                .totalElements(soliAutorizadas.getTotalElements())
                .build();

        return paginableResponse;
    }

    @Override
    public PaginableOut<Finalizacion> listaDeSolicitudesFinalizadasByIdSolicitud(PaginableIn paginableIn) {
        PaginableOut<Finalizacion> finalizacionDtoList = finalizacionAbstract
                .listaDeFinalizacionesAbstractPageByIdSoli(paginableIn);

        PaginableOut<Finalizacion> paginableResponse = PaginableOut
                .<Finalizacion>builder()
                .content(finalizacionDtoList.getContent())
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

        autorizacionAbstract.guardarAutorizacionAbs(autorizacion);

        Solicitud solicitud = solicitudAbstract
                .buscarSolicitudByIdAbstract(autorizacion.getFkSolicitud().getId());
        solicitud.setAutoriFlag(1L);
        solicitudAbstract.guardarSolicitudAbstract(solicitud);
    }

    @Override
    public void guardarFinalizacion(Finalizacion finalizacion) {
        LocalDate fechaActual = LocalDate.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        finalizacion.setFecha(fechaActual.format(formato));
        finalizacionAbstract.guardarFinalizacionAbs(finalizacion);

        Autorizacion autorizacion = autorizacionAbstract
                .buscarAutorizacionByIdAbs(finalizacion.getFkAutorizacion().getId());

        autorizacion.setFinaliFlag(1L);
        autorizacionAbstract.guardarAutorizacionAbs(autorizacion);
    }

    @Override
    public List<Fotocopia> listaDeFotocopias(Long idSolicitud) {
        return fotocopiaAbstract.getFotocopiasSolicitudAbstract(idSolicitud);
    }



    @Override
    public void generarNotaPedidoPDF(Long idSolicitud) {

        LocalDate fechaActual = LocalDate.now();
        DateTimeFormatter formato = DateTimeFormatter
                .ofPattern("dd 'de' MMMM 'de' yyyy", new Locale("es", "ES"));
        String fechaFormateada = fechaActual.format(formato);


        Solicitud solicitud = solicitudAbstract.buscarSolicitudByIdAbstract(idSolicitud);
        String nombreServicio = solicitud.getNombreServicio();
        Double precioTotalRedondeado = BigDecimal.valueOf(solicitud.getPrecioTotal()).setScale(2, RoundingMode.HALF_UP).doubleValue();


        List<NotaDePedido> listNotaPedidoPDF = listaDeNotasDePedido(idSolicitud);


        NotaDePedidoReport notaDePedidoReport = NotaDePedidoReport.builder()
                .idSolicitud(idSolicitud)
                .fecha(fechaFormateada)
                .nombreServicio(nombreServicio)
                .precioTotal(precioTotalRedondeado)
                .listNotaPedido(listNotaPedidoPDF)
                .build();


        String salidaPdfPsth = "/home/kali/Downloads/notaPedidoPDF";

        // Crear el directorio si no existe
        File outputDir = new File(salidaPdfPsth);
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }

        InputStream recursoJrxmlPath = getClass().getClassLoader().getResourceAsStream("templates/report/notaPedido.jrxml");
        if (recursoJrxmlPath == null) {
            throw new RuntimeException("No se pudo encontrar el archivo notaPedido.jrxml en el classpath.");
        }

        String recursoImagenPath = "classpath:/static/images/";

        // Ruta del archivo PDF
        String generacionPdfPath = salidaPdfPsth + "/notaPedido_" + idSolicitud + ".pdf";


        generacionPDFArchivoAbstract.generarNotaPedidoPDFAbs(
                notaDePedidoReport,
                recursoJrxmlPath,
                recursoImagenPath,
                generacionPdfPath
        );
    }

    @Override
    public void generarReportePDF(Long idSolicitud) {

        // Formato con nombre del mes completo
        LocalDate fechaActual = LocalDate.now();
        DateTimeFormatter formato = DateTimeFormatter
                .ofPattern("dd 'de' MMMM 'de' yyyy", new Locale("es", "ES"));
        String fechaActualString = fechaActual.format(formato);

        Solicitud solicitud = solicitudAbstract.buscarSolicitudByIdAbstract(idSolicitud);

        String nombreServicio = solicitud.getNombreServicio();
        Double precioTotal = BigDecimal.valueOf(solicitud.getPrecioTotal()).setScale(2, RoundingMode.HALF_UP).doubleValue();
        Long paginaTotal = solicitud.getPaginaTotal();
        Long copiaTotal = solicitud.getCopiaTotal();
        List<Reporte> listReporte = listaDeReportes(idSolicitud);

        ReporteReport reporteReport = ReporteReport.builder()
                .idSolicitud(idSolicitud)
                .fecha(fechaActualString)
                .nombreServicio(nombreServicio)
                .precioTotal(precioTotal)
                .paginaTotal(paginaTotal)
                .copiaTotal(copiaTotal)
                .listReporte(listReporte)
                .build();



        String salidaPdfPsth = "/home/kali/Downloads/reportePDF";

        // Crear el directorio si no existe
        File outputDir = new File(salidaPdfPsth);
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }

        InputStream recursoJrxmlPath = getClass().getClassLoader().getResourceAsStream("templates/report/reporte.jrxml");
        if (recursoJrxmlPath == null) {
            throw new RuntimeException("No se pudo encontrar el archivo reporte.jrxml en el classpath.");
        }

        String recursoImagenPath = "classpath:/static/images/";

        // Ruta del archivo PDF
        String generacionPdfPath = salidaPdfPsth + "/reporte_" + idSolicitud + ".pdf";


        generacionPDFArchivoAbstract.generarReportePDFAbs(
                reporteReport,
                recursoJrxmlPath,
                recursoImagenPath,
                generacionPdfPath
        );
    }

}
