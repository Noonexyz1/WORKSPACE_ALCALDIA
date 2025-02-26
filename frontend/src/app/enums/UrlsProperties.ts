export enum UrlsProperties {
  PATH_LOGIN = 'http://localhost:8081/login',
  PATH_DELETE_USER = 'http://localhost:8081/administrador/eliminarFuncionario/',
  PATH_LIST_USERS = 'http://localhost:8081/administrador/listaDeUsuarios',
  PATH_CHANCE_PASS = 'http://localhost:8081/cambioPassService/cambiarPass',
  PATH_LIST_CARGOS = 'http://localhost:8081/administrador/listarCargos',
  PATH_LIST_ROLES = 'http://localhost:8081/administrador/listarRoles',
  PATH_LIST_UNIDADES = 'http://localhost:8081/administrador/listarUnidades',
  PATH_CREATE_USER = 'http://localhost:8081/administrador/crearUsuario',
  PATH_EDIT_USER = 'http://localhost:8081/administrador/editarUsuario',
  PATH_CREATE_SOLICITUD = 'http://localhost:8081/solicitante/solicitarFotocopiar',

  PATH_SOLICITUD_PDF = 'http://localhost:8081/solicitante/exportSolicitudDPF/',
  PATH_ORDENFOTO_PDF = 'http://localhost:8081/solicitante/exportOrdenDeSolicitudDPF/',
  PATH_COMUINTERNA_PDF = 'http://localhost:8081/solicitante/exportComunicacionInternaDPF/',

  PATH_NOTA_PDF = 'http://localhost:8081/responsable/exportNotaPedidoDPF/',
  PATH_REPORTE_PDF = 'http://localhost:8081/responsable/exportReporteDPF/',

  PATH_LIST_SOLIC = 'http://localhost:8081/solicitante/verSolicitudesPendientes',
  PATH_AUTORIZ_SOLI = 'http://localhost:8081/solicitante/verSolicitudesAutorizadas',

  PATH_FINALIZADAS_SOLI = 'http://localhost:8081/solicitante/verSolicitudesFinalizadas',
  PATH_ELIMINAR_SOLIC = 'http://localhost:8081/solicitante/eliminarSolicitudById/',
  PATH_LIST_TAM = 'http://localhost:8081/solicitante/listarTamano',

  PATH_LIST_ANVER = 'http://localhost:8081/solicitante/listarAnversoReverso',
  PATH_LIST_COLOR = 'http://localhost:8081/solicitante/listarColor',

  PATH_LIST_SOLIFINALI = 'http://localhost:8081/responsable/verSolicitudesFinalizadas',

  PATH_LIST_SOLIPENDIENTE = 'http://localhost:8081/responsable/verSolicitudesPendientes',
  PATH_SOLI_BYID = 'http://localhost:8081/responsable/verSolicitudesPendientesByIdSolicitud',
  PATH_SOLI_AUTORIBYID = 'http://localhost:8081/responsable/verSolicitudesAutoriByIdSolicitud',
  PATH_SOLI_FINALIBYID = 'http://localhost:8081/responsable/verSolicitudesFinaliByIdSolicitud',

  PATH_DETALLE_SOLI = 'http://localhost:8081/responsable/verDetalleDeSolicitud/',
  PATH_AUTORIZAR_SOLI = 'http://localhost:8081/responsable/autorizarSolicitud',
  PATH_RECHAZAR_SOLI = 'http://localhost:8081/responsable/rechazarSolicitud',

  PATH_AUTORI_SOLI = 'http://localhost:8081/responsable/verAutorizacionSolicitud/',
  PATH_FINALIZAR_SOLI = 'http://localhost:8081/responsable/finalizarSolicitud',
  PATH_LIST_SOLIAPRO = 'http://localhost:8081/responsable/verSolicitudesAprobadas',
}
