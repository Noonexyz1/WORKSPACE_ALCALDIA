package com.prototipo.application.adapter;

import com.prototipo.application.model.PaginableIn;
import com.prototipo.application.model.PaginableOut;
import com.prototipo.application.port.in.ResponsableService;
import com.prototipo.application.port.out.persistence.*;
import com.prototipo.application.port.out.pdf.GeneracionPDFArchivoAbstract;
import com.prototipo.application.port.in.SolicitanteService;

import com.prototipo.domain.enums.*;
import com.prototipo.domain.model.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.CompletableFuture;

// IMPORTANTE: En la Arquitectura Hexagonal, el núcleo de la aplicación (dominio)
// no conoce los detalles de implementación de las capas externas (infraestructura).
// En su lugar, define interfaces (puertos) que las capas externas deben implementar.
//
// Estas interfaces usan modelos del dominio en sus métodos, lo que permite
// que la lógica de negocio funcione sin depender de detalles técnicos externos, no es su preocupacion
// lo que le preocupa es cumplir con los casos de uso.
//
// Los modelos del dominio son simples estructuras de datos que representan
// la información necesaria para la lógica de negocio. Pueden ser usados en
// varias capas de la arquitectura, pero siempre deben ser manejados, modificados por la capa
// appilcation o dominio.
//
// La infraestructura solo los usa para cumplir con los contratos definidos por los puertos.
//
// Tiene que ser asi, es inevitable
//
// Un modelo es un simple modelo de datos, no tiene logica de prgramacion o implementacion de logica,
// mas bien siempre se piensa en el comportamiento de las dependencias por capas, no en el modelo de datos
// a eso nos referimos con conocer los detalles de una capa, o mejor dicho, los detalles de una implementacion
public class SolicitanteAdapter implements SolicitanteService {

    private SolicitudAbstract solicitudAbstract;
    private FotocopiaAbstract fotocopiaAbstract;
    private ServicioFotocopiaAbstract findServicioFotocopia;
    private GeneracionPDFArchivoAbstract generacionPDFArchivoAbstract;
    private DocumentoRetiroAbstract documentoRetiroAbstract;
    private ResponsableService responsableService;
    private InformeReportAbstract informeReportAbstract;

    public SolicitanteAdapter(
            SolicitudAbstract solicitudAbstract,
            FotocopiaAbstract fotocopiaAbstract,
            ServicioFotocopiaAbstract findServicioFotocopia,
            GeneracionPDFArchivoAbstract generacionPDFArchivoAbstract,
            DocumentoRetiroAbstract documentoRetiroAbstract,
            ResponsableService responsableService,
            InformeReportAbstract informeReportAbstract) {

        this.solicitudAbstract = solicitudAbstract;
        this.fotocopiaAbstract = fotocopiaAbstract;
        this.findServicioFotocopia = findServicioFotocopia;
        this.generacionPDFArchivoAbstract = generacionPDFArchivoAbstract;
        this.documentoRetiroAbstract = documentoRetiroAbstract;
        this.responsableService = responsableService;
        this.informeReportAbstract = informeReportAbstract;
    }


    @Override
    public void solicitarFotocopia(Solicitud solicitudDto, List<Fotocopia> listFotocopia) {
        solicitudDto.setNombreServicio(TipoServicioEnum.FOTOCOPIA.getNombre());

        //Primero guardamos en la tabla Solitcitud
        Solicitud solicitudDtoResp = solicitudAbstract
                .solicitarFotocopiarAbstract(solicitudDto);

        Double precioTotalSoli = listFotocopia.stream()
                .map(x -> {
                    x.setFkSolicitud(solicitudDtoResp);
                    return guardadFotocopiaAbstrac(x);
                })
                .reduce(0.0, Double::sum);

        List<Fotocopia> fotocopiaSoliReso = fotocopiaAbstract
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

        Solicitud solicitudSaved = solicitudAbstract.solicitarFotocopiarAbstract(solicitudDtoResp);

        // Estoy usando CompletableFuture para las tareas asincronas para estos tres procesos
        // estoy conciente que estoy usando Java21 y que hay VirtualThreas pero... naaaaa. solo son tres tareas ;D
        CompletableFuture.allOf(
                CompletableFuture.runAsync(() -> generarComunicacionInternaPDF(solicitudSaved.getId())),
                CompletableFuture.runAsync(() -> generarSolicitudDeFotocopiaPDF(solicitudSaved.getId()))
        ).join();
    }

    private double guardadFotocopiaAbstrac(Fotocopia fotocopia) {
        ServicioFotocopia solicitudFotocopiaDto = findServicioFotocopia
                .findServicioFotocopia(fotocopia.getFkServicioFotocopia());

        double precioDocu = fotocopia.getNroCopias() *
                fotocopia.getNroPaginas() *
                solicitudFotocopiaDto.getPrecioRef();

        fotocopia.setFkServicioFotocopia(solicitudFotocopiaDto);
        fotocopia.setPrecioDocu(precioDocu);

        fotocopiaAbstract.guardarRegistroFotocopia(fotocopia);

        return precioDocu;
    }

    @Override
    public PaginableOut<Solicitud> listaDeSolicitudes(PaginableIn paginableIn) {
        PaginableOut<Solicitud> paginableOut = solicitudAbstract
                .getListaSolicitudesAbstract(paginableIn);

        PaginableOut<Solicitud> paginableResponse = PaginableOut
                .<Solicitud>builder()
                .content(paginableOut.getContent())
                .totalPages(paginableOut.getTotalPages())
                .totalElements(paginableOut.getTotalElements())
                .build();

        //Quiero filtar las solicitudes segun el Usuarioo que lo esta pidiendo
        return paginableResponse;
    }

    @Override
    public Solicitud buscarSolicitud(Long idSolicitud) {
        return solicitudAbstract.buscarSolicitudByIdAbstract(idSolicitud);
    }

    @Override
    public void eliminarSolicitud(Long idSolicitud) {
        Solicitud solicitud = solicitudAbstract
                .buscarSolicitudByIdAbstract(idSolicitud);
        solicitud.setIsActive(false);
        solicitudAbstract.guardarSolicitudAbstract(solicitud);
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
        PaginableOut<Solicitud> paginableOut = solicitudAbstract
                .getListaSolicitudesAutoriAbstract(paginableIn);

        PaginableOut<Solicitud> paginableResponse = PaginableOut
                .<Solicitud>builder()
                .content(paginableOut.getContent())
                .totalPages(paginableOut.getTotalPages())
                .totalElements(paginableOut.getTotalElements())
                .build();
        //Quiero filtar las solicitudes segun el Usuario que lo esta pidiendo
        return paginableResponse;
    }

    @Override
    public PaginableOut<Solicitud> listaDeFinalizaciones(PaginableIn paginableIn) {
        PaginableOut<Solicitud> paginableOut = solicitudAbstract
                .getListaSolicitudesFinaliAbstract(paginableIn);

        PaginableOut<Solicitud> paginableResponse = PaginableOut
                .<Solicitud>builder()
                .content(paginableOut.getContent())
                .totalPages(paginableOut.getTotalPages())
                .totalElements(paginableOut.getTotalElements())
                .build();

        //Quiero filtar las solicitudes segun el Usuario que lo esta pidiendo
        return paginableResponse;
    }

    @Override
    public List<Fotocopia> listaDeFotocopias(Long idSolicitud) {
        return fotocopiaAbstract.getFotocopiasSolicitudAbstract(idSolicitud);
    }


    @Override
    public void generarSolicitudDeFotocopiaPDF(Long idSolicitud) {
        // Traemos los datos necesarios para el reporte
        Solicitud solicitudResp = buscarSolicitud(idSolicitud);

        //Esto me sale null
        UsuarioUnidad usuarioResponsable = solicitudResp.getFkUsuarioSolicitante().getFkResponsable();

        UsuarioUnidad usuarioSolicitante = solicitudResp.getFkUsuarioSolicitante();
        List<Fotocopia> listFotocopias = listaDeFotocopias(idSolicitud);

        // Mapeamos con los datos obtenidos para exportar el PDF
        List<SolicitudTablaReport> listReportFotocopias = listFotocopias.stream()
                .map(x -> SolicitudTablaReport.builder()
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


        String salidaPdfPsth = "/home/kali/Downloads/solicitudPDF";

        // Crear el directorio si no existe
        File outputDir = new File(salidaPdfPsth);
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }

        InputStream recursoJrxmlPath = getClass().getClassLoader().getResourceAsStream("templates/report/solicitud.jrxml");
        if (recursoJrxmlPath == null) {
            throw new RuntimeException("No se pudo encontrar el archivo reporte.jrxml en el classpath.");
        }


        String recursoImagenPath = "classpath:/static/images/";

        // Ruta del archivo PDF
        String generacionPdfPath = salidaPdfPsth + "/solicitud_" + idSolicitud + ".pdf";

        generacionPDFArchivoAbstract.generarSolicitudDeFotocopiaPDFAbs(
                solicitudReport,
                recursoJrxmlPath,
                recursoImagenPath,
                generacionPdfPath);
    }

    // IMPORTANTE: Tu como capa application debes de construir los datos para luego pasarlos
    // a la interfaz Abs (ya que esta se encarga de realizar logica de prog) y esta
    // preocuparse por cumplirla ya sea con otra libreria.
    // Osea construimos el modelo de dato para que la otra capa simplemente genere el PDF con este modelo.
    // Este modelo seria por ejemplo un OrdenFotocopia

    @Override
    public void generarComunicacionInternaPDF(Long idSolicitud) {
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


        String salidaPdfPsth = "/home/kali/Downloads/comunicacionPDF";

        // Crear el directorio si no existe
        File outputDir = new File(salidaPdfPsth);
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }

        InputStream recursoJrxmlPath = getClass().getClassLoader().getResourceAsStream("templates/report/comunicacion.jrxml");
        if (recursoJrxmlPath == null) {
            throw new RuntimeException("No se pudo encontrar el archivo comunicacion.jrxml en el classpath.");
        }


        String recursoImagenPath = "classpath:/static/images/";

        // Ruta del archivo PDF
        String generacionPdfPath = salidaPdfPsth + "/comunicacion_" + idSolicitud + ".pdf";


        generacionPDFArchivoAbstract.generarComunicacionInternaPDFAbs(
                comunicacionReport,
                recursoJrxmlPath,
                recursoImagenPath,
                generacionPdfPath
        );
    }

    @Override
    public void generarInformeSolicitudPDF(Long idSolicitud, String editorContent) {
        InformeReport informeReport = informeReportAbstract.getInformeReport(idSolicitud);

        String salidaPdfPsth = "/home/kali/Downloads/informePDF";

        // Crear el directorio si no existe
        File outputDir = new File(salidaPdfPsth);
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }

        InputStream recursoJrxmlPath = getClass().getClassLoader().getResourceAsStream("templates/report/informe.jrxml");
        if (recursoJrxmlPath == null) {
            throw new RuntimeException("No se pudo encontrar el archivo informe.jrxml en el classpath.");
        }


        String recursoImagenPath = "classpath:/static/images/";

        // Ruta del archivo PDF
        String generacionPdfPath = salidaPdfPsth + "/informe_" + idSolicitud + ".pdf";

        generacionPDFArchivoAbstract.generarInformeSolicitudPDFAbs(
                informeReport,
                recursoJrxmlPath,
                recursoImagenPath,
                generacionPdfPath,
                editorContent
        );

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

    @Override
    public List<DocumentoRetiro> listDocumentoRetirar(Long idSolicitud) {
        //Debo pensar entidad por entidad porque cada modelo de dominio es un mundo aparte
        //y como es un mundo aparte, debe persistir aparte
        List<Fotocopia> fotoSoliAbs = fotocopiaAbstract.getFotocopiasSolicitudAbstract(idSolicitud);
        List<Long> idFotoSoliAbs = fotoSoliAbs.stream().map(Fotocopia::getId).toList();

        //Debo pensar entidad por entidad porque cada modelo de dominio es un mundo aparte
        //y como es un mundo aparte, debe persistir aparte
        List<DocumentoRetiro> retiroDocumentoList = idFotoSoliAbs.stream()
                .map(x -> documentoRetiroAbstract.getRetiroDocuByFkFoto(x))
                .toList();

        return retiroDocumentoList;
    }

    @Override
    public byte[] guardarListaDocuRetiros(List<DocumentoRetiro> listDocumentoRetiro) throws IOException {
        List<DocumentoRetiro> listDocuRetiSave = listDocumentoRetiro
                .stream().map(x -> {

                    // "23/05/2015"
                    String fechaActual = LocalDate.now()
                            .format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

                    DocumentoRetiro docuRetRetry = documentoRetiroAbstract.getDocumentoRetiroById(x.getId());

                    DocumentoRetiro newDocuRetry = new DocumentoRetiro();
                    newDocuRetry.setId(null);
                    newDocuRetry.setTotalCopia(docuRetRetry.getTotalCopia());
                    newDocuRetry.setTotalUsado(docuRetRetry.getSumNroRetiro() + x.getNroRetiro());
                    newDocuRetry.setTotalDisponible(docuRetRetry.getTotalCopia() - (docuRetRetry.getSumNroRetiro() + x.getNroRetiro()));

                    newDocuRetry.setPrecioParcial(
                            docuRetRetry.getFkFotocopia().getFkServicioFotocopia().getPrecioRef() *
                                    docuRetRetry.getFkFotocopia().getNroPaginas() *
                                    x.getNroRetiro()
                    );

                    // Se tiene que tener una sumatoria de los preciosParciales porque en un mes el
                    // solicitante puede hacer mas de dos o tres retiros, pero para un informe de responsable
                    // unicamente se debe hacer por mes, y para ese mes de debe traer la sumatorio
                    newDocuRetry.setPrecioSumParcial(
                            docuRetRetry.getPrecioSumParcial() +
                                    (docuRetRetry.getFkFotocopia().getFkServicioFotocopia().getPrecioRef() *
                                            docuRetRetry.getFkFotocopia().getNroPaginas() *
                                            x.getNroRetiro())
                    );
                    newDocuRetry.setPrecioTotal(docuRetRetry.getPrecioTotal());

                    newDocuRetry.setNroRetiro(x.getNroRetiro());
                    newDocuRetry.setSumNroRetiro(docuRetRetry.getSumNroRetiro() + x.getNroRetiro());

                    newDocuRetry.setFecha(fechaActual);

                    newDocuRetry.setFkFotocopia(docuRetRetry.getFkFotocopia());

                    return newDocuRetry;
                }).toList();

        List<DocumentoRetiro> documentoRetiros = documentoRetiroAbstract
                .guardarListaDocumentoRetiro(listDocuRetiSave);

        List<Fotocopia> fotocopiaList = documentoRetiros.stream()
                .map(x -> {
                    Fotocopia fotocopia = x.getFkFotocopia();
                    fotocopia.setNroCopias(x.getNroRetiro());
                    fotocopia.setPrecioDocu(x.getPrecioParcial());
                    return fotocopia;
                })
                .toList();


        String salidaPdfPsth = "/home/kali/Downloads/ordenPDF";

        // Crear el directorio si no existe
        File outputDir = new File(salidaPdfPsth);
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }

        InputStream recursoJrxmlPath = getClass().getClassLoader().getResourceAsStream("templates/report/orden.jrxml");
        if (recursoJrxmlPath == null) {
            throw new RuntimeException("No se pudo encontrar el archivo reporte.jrxml en el classpath.");
        }

        String recursoImagenPath = "classpath:/static/images/";
        
        String idDocuRetiros = documentoRetiros.stream()
                .map(doc -> doc.getId().toString()) // Convertir a String
                .reduce((a, b) -> a + "-" + b)
                .orElse("");

        // Ruta del archivo PDF
        String generacionPdfPath = salidaPdfPsth + "/ordenDeFotocopia_" + idDocuRetiros + ".pdf";

        generacionPDFArchivoAbstract.generarOrdenDeFotocopiaPDFAbs(
                fotocopiaList,
                recursoJrxmlPath,
                recursoImagenPath,
                generacionPdfPath
        );


        // Esto tiene que ser bajo una condicion, pero esto debe pasar de un estado aprobado
        // a finalizado, unicamente el Responsable debe aprobar

        // traer la Autorizacion que corresponde a la fotocopia del cual estoy haciendo esto
        Long id = fotocopiaList.getFirst().getFkSolicitud().getId();

        Fotocopia[] listFotocopias = responsableService.listaDeFotocopias(id).toArray(new Fotocopia[0]);

        int tamListFoto = listFotocopias.length;
        int contandorCeros = 0;

        for (Fotocopia idFotocopia: listFotocopias) {
            DocumentoRetiro[] documentoRetiroList = documentoRetiroAbstract
                    .listaDocuRetiroByFkFotocopia(idFotocopia.getId()).toArray(new DocumentoRetiro[0]);

            for (DocumentoRetiro documentoRetiro : documentoRetiroList) {
                if (documentoRetiro.getTotalDisponible() == 0)
                    contandorCeros++;
            }
        }

        if (tamListFoto == contandorCeros) {
            Autorizacion autorizacion = responsableService
                    .obtenerAutorizacion(id);

            Finalizacion finalizacion = Finalizacion.builder()
                    .fkAutorizacion(Autorizacion.builder().id(autorizacion.getId()).build())
                    .build();

            //luego hay que cambiar el estado de esta Autorizacion a un estado TERMINADO, este metodo ya lo hace
            responsableService.guardarFinalizacion(finalizacion);
        }

        // Aqui tengo que enviar los PDF para descargar
        // Devolver el contenido del PDF como un arreglo de bytes
        return Files.readAllBytes(Paths.get(generacionPdfPath));

    }

    @Override
    public boolean isInforme(Long idSolicitud) {
        Solicitud solicitud = solicitudAbstract.buscarSolicitudByIdAbstract(idSolicitud);
        UsuarioUnidad fkUsuarioSolicitante = solicitud.getFkUsuarioSolicitante();
        Unidad fkUnidad = fkUsuarioSolicitante.getFkUnidad();
        var valor = fkUnidad.getLimiteMonetario() < solicitud.getPrecioTotal();
        return valor;
    }

    @Override
    public byte[] descargaSolicitudDeFotocopiaPDF(Long idSolicitud) throws IOException {
        String salidaPdfPsth = "/home/kali/Downloads/solicitudPDF";
        // Ruta del archivo PDF
        String generacionPdfPath = salidaPdfPsth + "/solicitud_" + idSolicitud + ".pdf";
        // Devolver el contenido del PDF como un arreglo de bytes
        return Files.readAllBytes(Paths.get(generacionPdfPath));
    }

    @Override
    public byte[] descargarOrdenDeFotocopiaPDF(Long idSolicitud) throws IOException {
        String salidaPdfPsth = "/home/kali/Downloads/ordenPDF";
        String generacionPdfPath = salidaPdfPsth + "/ordenDeFotocopia_" + idSolicitud + ".pdf";
        // Devolver el contenido del PDF como un arreglo de bytes
        return Files.readAllBytes(Paths.get(generacionPdfPath));
    }

    @Override
    public byte[] descargarComunicacionInternaPDF(Long idSolicitud) throws IOException {
        String salidaPdfPsth = "/home/kali/Downloads/comunicacionPDF";
        // Ruta del archivo PDF
        String generacionPdfPath = salidaPdfPsth + "/comunicacion_" + idSolicitud + ".pdf";
        return Files.readAllBytes(Paths.get(generacionPdfPath));
    }

    @Override
    public byte[] descargarInformeSolicitudPDF(Long idSolicitud, String editorContent) throws IOException {
        //TODO, revisar este metodo
        generarInformeSolicitudPDF(idSolicitud, editorContent);

        String salidaPdfPsth = "/home/kali/Downloads/informePDF";
        // Ruta del archivo PDF
        String generacionPdfPath = salidaPdfPsth + "/informe_" + idSolicitud + ".pdf";
        return Files.readAllBytes(Paths.get(generacionPdfPath));
    }
}
