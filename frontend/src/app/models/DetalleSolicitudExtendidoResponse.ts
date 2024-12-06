import {DetalleSolicitudResponse} from "./DetalleSolicitudResponse";

export interface DetalleSolicitudExtendidoResponse {

  idSolicitud: number;
  cite: string;
  fecha: string;
  descripcion: string;
  detalleSolicitudResponses: DetalleSolicitudResponse[];
}
