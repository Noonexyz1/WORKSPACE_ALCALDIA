import { ArchivoPdfRequest } from "./ArchivoPdfRequest";

export interface SolicitudRequest {
    idSolicitante: number;
    idUnidad: number;
    nroDeCopias: number;          // Usar 'number' en lugar de 'Long'
    tipoDeDocumento: string;
    nroDePaginas: number;         // Usar 'number' en lugar de 'Long'
    archivosPdf: ArchivoPdfRequest[]; 
}
