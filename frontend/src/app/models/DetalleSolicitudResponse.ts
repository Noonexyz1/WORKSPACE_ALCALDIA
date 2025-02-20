export interface DetalleSolicitudResponse {

  idSolicitud: number;

  idDetalleSolicitud: number;
  nombreDocumento: string;
  nroCopias: number;
  nroPaginas: number;
  tamanoPagina: string;
  anversoReverso: string;
  colorFotocopia: string;
  precioRef: number;
  precioDocu: number;
}
