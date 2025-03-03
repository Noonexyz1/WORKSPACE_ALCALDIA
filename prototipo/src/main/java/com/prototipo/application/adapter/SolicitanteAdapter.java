package com.prototipo.application.adapter;

import com.prototipo.application.mapper.MapperApplicationAbstract;
import com.prototipo.application.modelDto.*;
import com.prototipo.application.pager.PaginableIn;
import com.prototipo.application.pager.PaginableOut;
import com.prototipo.application.port.FotocopiaAbstract;
import com.prototipo.application.port.ServicioFotocopiaAbstract;
import com.prototipo.application.port.SolicitudAbstract;
import com.prototipo.application.useCase.SolicitanteService;

import com.prototipo.domain.enums.*;
import com.prototipo.domain.model.*;

import com.prototipo.infrastructure.rest.report.ComunicacionReport;
import com.prototipo.infrastructure.rest.report.SolicitudReport;
import com.prototipo.infrastructure.rest.report.TablaSolicitudReport;
import com.prototipo.application.util.DoublesALiteral;
import com.prototipo.application.util.NumeroALiteral;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.pdf.JRPdfExporter;
import net.sf.jasperreports.pdf.SimplePdfExporterConfiguration;
import net.sf.jasperreports.pdf.SimplePdfReportConfiguration;
import net.sf.jasperreports.pdf.type.PdfVersionEnum;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SolicitanteAdapter implements SolicitanteService {

    private SolicitudAbstract solicitudAbstract;
    private MapperApplicationAbstract mapperApplicationAbstract;
    private FotocopiaAbstract fotocopiaAbstract;
    private ServicioFotocopiaAbstract findServicioFotocopia;

    public SolicitanteAdapter(
            SolicitudAbstract solicitudAbstract,
            MapperApplicationAbstract mapperApplicationAbstract,
            FotocopiaAbstract fotocopiaAbstract,
            ServicioFotocopiaAbstract findServicioFotocopia) {

        this.solicitudAbstract = solicitudAbstract;
        this.mapperApplicationAbstract = mapperApplicationAbstract;
        this.fotocopiaAbstract = fotocopiaAbstract;
        this.findServicioFotocopia = findServicioFotocopia;
    }

    @Override
    public void solicitarFotocopia(Solicitud solicitud, List<Fotocopia> listFotocopia) {
        SolicitudDto solicitudDto = mapperApplicationAbstract
                .mapearAbstract(solicitud, SolicitudDto.class);
        solicitudDto.setNombreServicio(TipoServicioEnum.FOTOCOPIA.getNombre());

        //Primero guardamos en la tabla Solitcitud
        SolicitudDto solicitudDtoResp = solicitudAbstract
                .solicitarFotocopiarAbstract(solicitudDto);

        Solicitud solicitudResp = mapperApplicationAbstract
                .mapearAbstract(solicitudDtoResp, Solicitud.class);

        Double precioTotalSoli = listFotocopia.stream()
                .map(x -> {
                    x.setFkSolicitud(solicitudResp);
                    return guardadFotocopiaAbstrac(x);
                })
                .reduce(0.0, Double::sum);

        List<FotocopiaDto> fotocopiaSoliReso = fotocopiaAbstract
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

        ServicioFotocopiaDto solicitudFotocopiaDto = findServicioFotocopia
                .findServicioFotocopia(fotocopiaDto.getFkServicioFotocopia());

        double precioDocu = fotocopia.getNroCopias() *
                fotocopia.getNroPaginas() *
                solicitudFotocopiaDto.getPrecioRef();

        fotocopiaDto.setFkServicioFotocopia(solicitudFotocopiaDto);
        fotocopiaDto.setPrecioDocu(precioDocu);

        fotocopiaAbstract.guardarRegistroFotocopia(fotocopiaDto);

        return precioDocu;
    }

    @Override
    public PaginableOut<Solicitud> listaDeSolicitudes(PaginableIn paginableIn) {
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
    public Solicitud buscarSolicitud(Long idSolicitud) {
        SolicitudDto solicitudDto = solicitudAbstract
                .buscarSolicitudByIdAbstract(idSolicitud);
        return mapperApplicationAbstract
                .mapearAbstract(solicitudDto, Solicitud.class);
    }

    @Override
    public void eliminarSolicitud(Long idSolicitud) {
        SolicitudDto solicitudDto = solicitudAbstract
                .buscarSolicitudByIdAbstract(idSolicitud);
        solicitudDto.setIsActive(false);
        solicitudAbstract.guardarSolicitudAbstract(solicitudDto);
    }

    @Override
    public List<String> listaDeTamanoPagina() {
        return List.of(
                TamanoPaginaEnum.CARTA.getNombre(),
                TamanoPaginaEnum.OFICIO.getNombre()
        );
    }

    @Override
    public List<String> listaDeAnverReverPagina() {
        return List.of(
                AnversoReversoEnum.ANVERSO.getNombre(),
                AnversoReversoEnum.ANVERSO_REVERSO.getNombre()
        );
    }

    @Override
    public List<String> listaDeColorPagina() {
        return List.of(
                ColorFotocopiaEnum.BLANCO_NEGRO.getNombre(),
                ColorFotocopiaEnum.COLOR.getNombre()
        );
    }

    @Override
    public PaginableOut<Solicitud> listaDeAutorizaciones(PaginableIn paginableIn) {
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
    public PaginableOut<Solicitud> listaDeFinalizaciones(PaginableIn paginableIn) {
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
    public List<Fotocopia> listaDeFotocopias(Long idSolicitud) {
        List<FotocopiaDto> list = fotocopiaAbstract.
                getFotocopiasSolicitudAbstract(idSolicitud);
        return list.stream()
                .map(x ->
                        mapperApplicationAbstract.mapearAbstract(x, Fotocopia.class))
                .toList();
    }

    @Override
    public void generarOrdenDeFotocopiaPDF(Long idSolicitud) throws JRException {
        List<Fotocopia> listFotocopia = listaDeFotocopias(idSolicitud);
        List<JasperPrint> jasperPrintList = getReportByList(listFotocopia);
        exportToPdfByListByJRPdfExporter(jasperPrintList);
    }

    private List<JasperPrint> getReportByList(List<Fotocopia> listFotocopiaSolicitud)
            throws JRException {

        List<JasperPrint> paginasJasperPrints = new ArrayList<>();

        Fotocopia[] detalleFotoVect = listFotocopiaSolicitud
                .toArray(new Fotocopia[0]);

        int marcador = 0;

        //1. Determinar cuantas paginas son necesarias para poder imprimir los datos
        //digamos que son 7 y necesito 2 paginas
        int nroPaginas = (int) Math.ceil(
                (double) listFotocopiaSolicitud.size() / 4
        );

        //para dos paginas en total, solo debo recorrer 2 paginas
        for (int i = 1; i <= nroPaginas; i++) {
            //para la pagina 1
            //para la primera pagina se va ha mandar estos datos
            String filePath = "src" + File.separator +
                    "main" + File.separator +
                    "resources" + File.separator +
                    "templates" + File.separator +
                    "report" + File.separator +
                    "orden.jrxml";

            //sabes que, con esta primera pagina, quiero que lo pobles con datos
            Map<String, Object> params = new HashMap<>();
            int j = marcador;
            for (int k = 1; k <= 4 && j < detalleFotoVect.length; k++) {
                // Verificar que j no esté fuera del rango
                if (j < detalleFotoVect.length) {
                    // Asigna los parámetros relacionados con la fotocopia
                    params.put("nroCantidadFotocopia" + k, detalleFotoVect[j].getNroCopias().intValue());
                    params.put("litCantidadFotocopia" + k, NumeroALiteral
                            .convertirNumeroALiteral(detalleFotoVect[j].getNroCopias().intValue()));

                    params.put("precio" + k, BigDecimal.valueOf(detalleFotoVect[j].getPrecioDocu()).setScale(2, RoundingMode.HALF_UP).doubleValue());

                    params.put("literalPrecio" + k, DoublesALiteral
                            .convertir(BigDecimal.valueOf(detalleFotoVect[j].getPrecioDocu())));

                    params.put("detalle" + k, detalleFotoVect[j].getNombreDocumento());
                    j++; // Avanzar al siguiente elemento
                } else {
                    // Si no hay más elementos, puedes asignar valores predeterminados
                    params.put("nroCantidadFotocopia" + k, 0);
                    params.put("precio" + k, new BigDecimal("0.00"));
                    params.put("litCantidadFotocopia" + k, "N/A");
                    params.put("literalPrecio" + k, "N/A");
                    params.put("detalle" + k, "Sin detalle");
                }
            }

            params.put("imageDir", "classpath:/static/images/");

            //ESTE FILE PATH esta guardando con el PATH CORRESPONDIENTE??s?
            JasperReport jasperReport = JasperCompileManager.compileReport(filePath);
            //TRAS HABER CRFEADO EL jaspertReport, pues no inserta de forma correcta el orden2.jrxml

            JasperPrint report = JasperFillManager.fillReport(//DEBO VISUALIZAR ESTA VARIABLE EN LA SIGUIENTE PRUEBA
                    jasperReport,
                    params,
                    new JREmptyDataSource()
            );

            paginasJasperPrints.add(report);//AL ANADIR A LA LISTA, NO PONE EL ORDEN2 COMO JRXML EN LA LISTA
            marcador = j;
        }

        return paginasJasperPrints;
    }

    private byte[] exportToPdfByListByJRPdfExporter(List<JasperPrint> jasperPrintList)
            throws JRException {

        // 1. Crea un flujo de salida en memoria
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        // 2. Configura el exportador PDF
        JRPdfExporter exporter = new JRPdfExporter();
        exporter.setExporterInput(SimpleExporterInput.getInstance(jasperPrintList)); // Agrega todos los JasperPrint
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(baos)); // Define el flujo de salida

        // Opcional: Configuración adicional para el PDF
        SimplePdfReportConfiguration reportConfig = new SimplePdfReportConfiguration();
        reportConfig.setSizePageToContent(true);
        reportConfig.setForceLineBreakPolicy(false);

        SimplePdfExporterConfiguration exportConfig = new SimplePdfExporterConfiguration();
        exportConfig.setMetadataAuthor("TuNombre");
        exportConfig.setPdfVersion(PdfVersionEnum.VERSION_1_7);

        exporter.setConfiguration(reportConfig);
        exporter.setConfiguration(exportConfig);

        // 3. Exporta todos los JasperPrint en un único PDF
        exporter.exportReport();

        // 4. Convierte el contenido del flujo de salida a un arreglo de bytes
        return baos.toByteArray();
    }

    @Override
    public void generarComunicacionInternaPDF(Long idSolicitud) throws JRException {
        // Recuperar datos necesarios
        Solicitud solicitudResp = buscarSolicitud(idSolicitud);

        List<Fotocopia> listFotocopiaSolicitudResp = listaDeFotocopias(idSolicitud);

        String documentos = formatListaDocumentos(listFotocopiaSolicitudResp);
        long totalCopias = listFotocopiaSolicitudResp.stream()
                .mapToLong(Fotocopia::getNroCopias)
                .sum();

        UsuarioUnidad usuarioSolicitante = solicitudResp.getFkUsuarioSolicitante();
        UsuarioUnidad usuarioResponsable = solicitudResp.getFkUsuarioSolicitante().getFkResponsable();

        // Crear objeto de reporte
        ComunicacionReport comunicacionReport = ComunicacionReport.builder()
                .funcionarioTo(
                        usuarioResponsable.getFkUsuario().getNombres() + " " +
                                usuarioResponsable.getFkUsuario().getPaterno() + " " +
                                usuarioResponsable.getFkUsuario().getMaterno())
                .funcionarioToCargo(
                        usuarioResponsable.getFkCargo().getNombreCargo())
                .funcionarioFrom(
                        usuarioSolicitante.getFkUsuario().getNombres() + " " +
                                usuarioSolicitante.getFkUsuario().getPaterno() + " " +
                                usuarioSolicitante.getFkUsuario().getMaterno())
                .funcionarioFromCargo(
                        usuarioSolicitante.getFkCargo().getNombreCargo())
                .cite(solicitudResp.getCite())
                .nombreOrganizacion(usuarioSolicitante.getFkUnidad().getNombre())
                .documentos(documentos)
                .totalCopias((int) totalCopias)
                .build();

        JasperPrint jasperPrint = getReport(comunicacionReport);
        JasperExportManager.exportReportToPdf(jasperPrint);
    }

    private String formatListaDocumentos(List<Fotocopia> listFotocopiaSolicitudResp) {
        // Usamos collect() para obtener una lista en versiones de Java anteriores a 16
        List<String> nombres = listFotocopiaSolicitudResp.stream()
                .map(Fotocopia::getNombreDocumento).toList();

        // Verifica la cantidad de documentos
        if (nombres.size() == 1) {
            return nombres.get(0); // Solo hay un documento
        } else if (nombres.size() == 2) {
            return String.join(" y ", nombres); // Solo hay dos documentos
        } else {
            // Junta todos menos el último con comas, y añade ' y ' antes del último
            return String.join(", ", nombres.subList(0, nombres.size() - 1))
                    + " y " + nombres.get(nombres.size() - 1);
        }
    }

    private JasperPrint getReport(ComunicacionReport parametros)
            throws JRException {

        //Ruta total
        String filePath = "src" + File.separator +
                "main" + File.separator +
                "resources" + File.separator +
                "templates" + File.separator +
                "report" + File.separator +
                "comunicacion.jrxml";

        Map<String, Object> params = new HashMap<>();
        // Asigna los campos de ComunicacionReport a los parámetros del reporte
        params.put("funcionarioTo", parametros.getFuncionarioTo());
        params.put("funcionarioFrom", parametros.getFuncionarioFrom());
        params.put("funcionarioToCargo", parametros.getFuncionarioToCargo());
        params.put("funcionarioFromCargo", parametros.getFuncionarioFromCargo());
        params.put("cite", parametros.getCite());
        params.put("nombreOrganizacion", parametros.getNombreOrganizacion());
        params.put("documentos", parametros.getDocumentos());
        params.put("totalCopias", parametros.getTotalCopias());
        params.put("imageDir", "classpath:/static/images/");

        JasperReport jasperReport = JasperCompileManager.compileReport(filePath);

        JasperPrint report = JasperFillManager.fillReport(
                jasperReport,
                params,
                new JREmptyDataSource()
        );

        return report;
    }

    @Override
    public void generarSolicitudDeFotocopiaPDF(Long idSolicitud) throws JRException {
        // Traemos los datos necesarios para el reporte
        Solicitud solicitudResp = buscarSolicitud(idSolicitud);
        UsuarioUnidad usuarioResponsable = solicitudResp.getFkUsuarioSolicitante().getFkResponsable();
        UsuarioUnidad usuarioSolicitante = solicitudResp.getFkUsuarioSolicitante();
        List<Fotocopia> listFotocopias = listaDeFotocopias(idSolicitud);

        // Mapeamos con los datos obtenidos para exportar el PDF
        List<TablaSolicitudReport> listReportFotocopias = listFotocopias.stream()
                .map(x -> TablaSolicitudReport.builder()
                        .documento(x.getNombreDocumento())
                        .cantidad(x.getNroCopias().intValue())
                        .build())
                .toList();

        SolicitudReport solicitudReport = SolicitudReport.builder()
                .funcionarioTo(usuarioResponsable.getFkUsuario().getNombres() + " " +
                        usuarioResponsable.getFkUsuario().getPaterno() + " " +
                        usuarioResponsable.getFkUsuario().getMaterno())
                .funcionarioToCargo(usuarioResponsable.getFkCargo().getNombreCargo())
                .funcionarioFrom(usuarioSolicitante.getFkUsuario().getNombres() + " " +
                        usuarioSolicitante.getFkUsuario().getPaterno() + " " +
                        usuarioSolicitante.getFkUsuario().getMaterno())
                .funcionarioFromCargo(usuarioSolicitante.getFkCargo().getNombreCargo())
                .cite(solicitudResp.getCite())
                .fecha(solicitudResp.getFecha())
                .nombreOrganizacion(usuarioSolicitante.getFkUnidad().getNombre())
                .cantidadSumado(solicitudResp.getCopiaTotal() + "")
                .listReportFotocopias(listReportFotocopias)
                .build();

        JasperPrint jasperPrint = getReport(solicitudReport);
        JasperExportManager.exportReportToPdf(jasperPrint);
    }
    //Deberias enviar el Mapa por parametro para reutilizar codigo
    private JasperPrint getReport(SolicitudReport solicitudReport)
            throws JRException {

        // Asigna los campos de SolicitudReport a los parámetros del reporte plantilla
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("funcionarioTo", solicitudReport.getFuncionarioTo());
        parameter.put("funcionarioFrom", solicitudReport.getFuncionarioFrom());
        parameter.put("funcionarioToCargo", solicitudReport.getFuncionarioToCargo());
        parameter.put("funcionarioFromCargo", solicitudReport.getFuncionarioFromCargo());
        parameter.put("cite", solicitudReport.getCite());
        parameter.put("fecha", solicitudReport.getFecha());
        parameter.put("nombreOrganizacion", solicitudReport.getNombreOrganizacion());
        parameter.put("cantidadSumado", solicitudReport.getCantidadSumado());
        parameter.put("ds", new JRBeanCollectionDataSource(solicitudReport.getListReportFotocopias()));
        parameter.put("imageDir", "classpath:/static/images/");

        //Ruta total para traer la plantilla PDF de Solicitud
        String rutaPlantillaSoliPDF = getRutaPlantillaSolicitudDPF();
        JasperReport jasperReport = JasperCompileManager.compileReport(rutaPlantillaSoliPDF);
        return JasperFillManager.fillReport(jasperReport, parameter, new JREmptyDataSource());
    }

    private String getRutaPlantillaSolicitudDPF(){
        return "src" + File.separator +
                "main" + File.separator +
                "resources" + File.separator +
                "templates" + File.separator +
                "report" + File.separator +
                "solicitud.jrxml";
    }
}
