import {RowSolicitud} from "./RowSolicitud";

export interface SolicitudRequest {
  cite: string;
  idSolicitante: number;
  idUnidad: number;
  listSolicitud: RowSolicitud[];
}
