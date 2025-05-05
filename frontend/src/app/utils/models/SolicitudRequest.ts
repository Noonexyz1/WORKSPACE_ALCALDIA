import {RowSolicitud} from "./RowSolicitud";

export interface SolicitudRequest {
  fkUsuarioSolicitante: number,
  cite: string,
  descripcion: string,
  listDetalleSolicitud: RowSolicitud[];
}
