export interface FinalizacionResponse {
  idAutorizacion: number;

  nombreCompleto: string;
  nombreUnidad: string;
  nombreCargo: string;

  descripcion: string;
  totalAutorizado: number;
  totalCotizadoBs: number;

  totalEjecutado: number;
  totalEjecutadoBs: number;
  fecha: string;
}
