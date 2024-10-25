export interface SolicitudOperaResponse {
    id: number;
    idSolicitud: number;
    nroDeCopias: number;
    tipoDeDocumento: string;
    nroDePaginas: number;
    estadoByOperador: string;
    nombreUnidad: string;
}