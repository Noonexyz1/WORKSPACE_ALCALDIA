export interface SolicitudRequest {
    nroDeCopias: number;          // Usar 'number' en lugar de 'Long'
    tipoDeDocumento: string;
    nroDePaginas: number;         // Usar 'number' en lugar de 'Long'
    nombreUnidad: string;
    archivosPdf: string[];        // Array de strings para la lista de archivos
}
