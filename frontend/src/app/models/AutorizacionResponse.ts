export interface AutorizacionResponse {
  idAutorizacion: number;

  idSolicitud: number;
  cite: string;
  fecha: string;
  nomCompleto: string;
  nomCargo: string;
  nombreUnidad: string;

  totalAutorizado: number;
  totalCotizadoBs: number;
  fechaCotizado: string;
}
