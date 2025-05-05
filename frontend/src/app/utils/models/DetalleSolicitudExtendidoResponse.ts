import {DetalleSolicitudResponse} from "./DetalleSolicitudResponse";

export interface DetalleSolicitudExtendidoResponse {
  idSolicitud: number;
  cite: string;
  fecha: string;
  descripcion: string;
  nombreServicio: string;
  precioTotal: number;

  detalleSolicitudResponses: DetalleSolicitudResponse[];
}
