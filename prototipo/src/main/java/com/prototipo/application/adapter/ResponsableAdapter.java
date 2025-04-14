package com.prototipo.application.adapter;

import com.prototipo.application.model.PaginableIn;
import com.prototipo.application.model.PaginableOut;
import com.prototipo.application.port.in.ResponsableService;
import com.prototipo.application.port.out.pdf.GeneracionPDFArchivoAbstract;
import com.prototipo.application.port.out.pdf.GeneracionPDFDataAbstract;
import com.prototipo.application.port.out.persistence.*;
import com.prototipo.domain.model.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Paths;
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
    private DocumentoRetiroAbstract documentoRetiroAbstract;

    public ResponsableAdapter(
            SolicitudAbstract solicitudAbstract,
            GeneracionPDFDataAbstract generacionPDFDataAbstract,
            AutorizacionAbstract autorizacionAbstract,
            FotocopiaAbstract fotocopiaAbstract,
            FinalizacionAbstract finalizacionAbstract,
            GeneracionPDFArchivoAbstract generacionPDFArchivoAbstract,
            DocumentoRetiroAbstract documentoRetiroAbstract) {

        this.solicitudAbstract = solicitudAbstract;
        this.generacionPDFDataAbstract = generacionPDFDataAbstract;
        this.autorizacionAbstract = autorizacionAbstract;
        this.fotocopiaAbstract = fotocopiaAbstract;
        this.finalizacionAbstract = finalizacionAbstract;
        this.generacionPDFArchivoAbstract = generacionPDFArchivoAbstract;
        this.documentoRetiroAbstract = documentoRetiroAbstract;
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
    public List<Reporte> listaDeReporteMensual(String mesAnio) {
        return generacionPDFDataAbstract.generarReporteMensualPDFAbstract(mesAnio);
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
        generarNotaPedidoPDF(solicitud.getId());


        //en cuanto se autoriza, debo habilitarle este "credito"
        List<Fotocopia> fotocopias = fotocopiaAbstract
                .getFotocopiasSolicitudAbstract(solicitud.getId());

        fotocopias.forEach(x -> {
            DocumentoRetiro documentoRetiro = DocumentoRetiro.builder()
                    .totalCopia(x.getNroCopias())
                    // Es total usado es por documento, no importa si ese documento tiene 20 paginas, se
                    // entiendes como 1 / que el usuario fotocopiara el documento entero
                    .totalUsado(0L)
                    .totalDisponible(x.getNroCopias())

                    .precioParcial(0D)
                    .precioSumParcial(0D)
                    .precioTotal(x.getPrecioDocu())

                    .nroRetiro(0L)
                    .sumNroRetiro(0L)

                    .fecha(fechaActual.format(formato))
                    .fkFotocopia(x)
                    .build();
            documentoRetiroAbstract.aprobarDocumentoRetiro(documentoRetiro);
        });

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
        //esto deberia quitarlo, y tambien para la consistencia, este metodo no deberia hacer dos cosas como
        //guardar finalizacion y generar PDFs como dice el nombre de este metodo, solo guardarFinalizacion
        //generarReportePDF(autorizacion.getFkSolicitud().getId());
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
    public void generarReportePDF() {

        // Formato con nombre del mes completo
        DateTimeFormatter formato = DateTimeFormatter
                .ofPattern("dd 'de' MMMM 'de' yyyy", new Locale("es", "ES"));
        String fechaActualString = LocalDate.now().format(formato);


        String mesAnio = LocalDate.now().format(DateTimeFormatter.ofPattern("MM/yyyy"));
        List<Reporte> listReporte = listaDeReporteMensual(mesAnio);

        Integer paginaTotal = listReporte.stream().map(Reporte::getNroPaginas).reduce(0, Integer::sum);
        Integer copiaTotal = listReporte.stream().map(Reporte::getNroCopiasExtrac).reduce( 0, Integer::sum);

        Double precioTotal = listReporte.stream()
                .map(Reporte::getPrecioParcial)
                .reduce(0.0, Double::sum);

        BigDecimal precioRedondeo = new BigDecimal(precioTotal).setScale(2, RoundingMode.HALF_UP);

        ReporteReport reporteReport = ReporteReport.builder()
                .fecha(fechaActualString)
                .nombreServicio("Fotocopia")
                .precioTotal(precioRedondeo.doubleValue())
                .paginaTotal(Long.valueOf(paginaTotal))
                .copiaTotal(Long.valueOf(copiaTotal))
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


        //Debo tener cuado con esto "MM/yyyy" esa barra puede interpretarse como separador de rutas
        //String mesAnio = LocalDate.now().format(DateTimeFormatter.ofPattern("MM/yyyy"));
        //Ruta del archivo PDF
        String mesAnioNombre = LocalDate.now().format(DateTimeFormatter.ofPattern("MM-yyyy"));
        String generacionPdfPath = salidaPdfPsth + "/reporte_" + mesAnioNombre + ".pdf";


        generacionPDFArchivoAbstract.generarReportePDFAbs(
                reporteReport,
                recursoJrxmlPath,
                recursoImagenPath,
                generacionPdfPath
        );
    }



    @Override
    public byte[] descargarNotaPedidoPDF(Long idSolicitud) throws IOException {
        String salidaPdfPsth = "/home/kali/Downloads/notaPedidoPDF";
        // Ruta del archivo PDF
        String generacionPdfPath = salidaPdfPsth + "/notaPedido_" + idSolicitud + ".pdf";
        // Devolver el contenido del PDF como un arreglo de bytes
        return Files.readAllBytes(Paths.get(generacionPdfPath));
    }

    @Override
    public byte[] descargarReportePDF() throws IOException {
        //aqui se debe generar le reporte pdf
        generarReportePDF();

        String mesAnio = LocalDate.now().format(DateTimeFormatter.ofPattern("MM-yyyy"));

        String salidaPdfPsth = "/home/kali/Downloads/reportePDF";
        // Ruta del archivo PDF
        String generacionPdfPath = salidaPdfPsth + "/reporte_" + mesAnio + ".pdf";
        return Files.readAllBytes(Paths.get(generacionPdfPath));
    }

}
