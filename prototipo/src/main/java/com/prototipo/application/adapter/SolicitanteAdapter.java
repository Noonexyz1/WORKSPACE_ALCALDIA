package com.prototipo.application.adapter;

import com.prototipo.application.model.PaginableIn;
import com.prototipo.application.model.PaginableOut;
import com.prototipo.application.port.out.FotocopiaAbstract;
import com.prototipo.application.port.out.GeneracionPDFArchivoAbstract;
import com.prototipo.application.port.out.ServicioFotocopiaAbstract;
import com.prototipo.application.port.out.SolicitudAbstract;
import com.prototipo.application.port.in.SolicitanteService;

import com.prototipo.domain.enums.*;
import com.prototipo.domain.model.*;

import java.io.File;
import java.util.List;

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

    public SolicitanteAdapter(
            SolicitudAbstract solicitudAbstract,
            FotocopiaAbstract fotocopiaAbstract,
            ServicioFotocopiaAbstract findServicioFotocopia,
            GeneracionPDFArchivoAbstract generacionPDFArchivoAbstract) {

        this.solicitudAbstract = solicitudAbstract;
        this.fotocopiaAbstract = fotocopiaAbstract;
        this.findServicioFotocopia = findServicioFotocopia;
        this.generacionPDFArchivoAbstract = generacionPDFArchivoAbstract;
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

        solicitudAbstract.solicitarFotocopiarAbstract(solicitudDtoResp);
    }

    private double guardadFotocopiaAbstrac(Fotocopia fotocopia){
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
    public void generarOrdenDeFotocopiaPDF(Long idSolicitud) {
        // IMPORTANTE: Tu como capa application debes de construir los datos para luego pasarlos
        // a la interfaz Abs (ya que esta se encarga de realizar logica de prog) y esta
        // preocuparse por cumplirla ya sea con otra libreria.
        // Osea construimos el modelo de dato para que la otra capa simplemente genere el PDF con este modelo.
        // Este modelo seria por ejemplo un OrdenFotocopia
        List<Fotocopia> listFotocopia = listaDeFotocopias(idSolicitud);

        String filePath = "src" + File.separator +
                "main" + File.separator +
                "resources" + File.separator +
                "templates" + File.separator +
                "report" + File.separator +
                "orden.jrxml";

        String recursoImagen = "classpath:/static/images/";

        generacionPDFArchivoAbstract.generarOrdenDeFotocopiaPDFAbs(listFotocopia, recursoImagen, filePath);
    }

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


        //Ruta total
        String filePath = "src" + File.separator +
                "main" + File.separator +
                "resources" + File.separator +
                "templates" + File.separator +
                "report" + File.separator +
                "comunicacion.jrxml";

        String recursoString = "classpath:/static/images/";

        generacionPDFArchivoAbstract.generarComunicacionInternaPDFAbs(comunicacionReport, filePath, recursoString);
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
    public void generarSolicitudDeFotocopiaPDF(Long idSolicitud) {
        // Traemos los datos necesarios para el reporte
        Solicitud solicitudResp = buscarSolicitud(idSolicitud);
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


        //Ruta total para traer la plantilla PDF de Solicitud
        String rutaPlantillaSoliPDF = "src" + File.separator +
                "main" + File.separator +
                "resources" + File.separator +
                "templates" + File.separator +
                "report" + File.separator +
                "solicitud.jrxml";


        String recursoImagen = "classpath:/static/images/";


        generacionPDFArchivoAbstract.generarSolicitudDeFotocopiaPDFAbs(solicitudReport, recursoImagen, rutaPlantillaSoliPDF);
    }
}
