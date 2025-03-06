export class SolicitudResponResponse {
  idAutorizacion: number | undefined;

  idSolicitud: number| undefined;
  cite: string| undefined;
  fecha: string| undefined;
  nomCompleto: string| undefined;
  nomCargo: string| undefined;
  nombreUnidad: string| undefined;

  isActiveBtnFinalizar: boolean = false; // Nueva propiedad
}
