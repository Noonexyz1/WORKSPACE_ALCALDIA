import {Component, EventEmitter, Input, Output} from '@angular/core';
import {DetalleSolicitudResponse} from "../../utils/models/DetalleSolicitudResponse";

export interface SolicitudData {
  idSolicitud: number;
  cite: string;
  fecha: string;
  descripcion: string;
  nombreServicio: string;
  precioTotal: number;
  detalleSolicitudResponses: DetalleSolicitudResponse[];
}

export interface ModalSolicitudData {
  tituloModal: string;
  datosSolicitud: SolicitudData;
}

@Component({
  selector: 'app-modal-solicitud-info',
  standalone: true,
  imports: [],
  templateUrl: './modal-solicitud-info.component.html',
})
export class ModalSolicitudInfoComponent {

  @Input() solicitudDetalle: ModalSolicitudData = {
    tituloModal: '',
    datosSolicitud: {
      idSolicitud: 0,
      cite: '',
      fecha: '',
      descripcion: '',
      nombreServicio: '',
      precioTotal: 0,
      detalleSolicitudResponses: [],
    },
  }
  @Input() isBotonesActivos: boolean = false;


  @Output() isModalClose = new EventEmitter<boolean>();
  @Output() autorizarClicked = new EventEmitter<number>();
  @Output() rechazarClicked = new EventEmitter<number>();


  toggleModal() {
    this.isModalClose.emit(false)
  }

  botonAutorizar() {
    this.autorizarClicked.emit(this.solicitudDetalle.datosSolicitud.idSolicitud)
  }

  botonRechazar() {
    this.rechazarClicked.emit(this.solicitudDetalle.datosSolicitud.idSolicitud)
  }

}
