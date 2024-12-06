import {Component, EventEmitter, Input, Output} from '@angular/core';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule} from "@angular/forms";
import {DetalleSolicitudResponse} from "../../../../../models/DetalleSolicitudResponse";
import {DetalleSolicitudCotizadoResponse} from "../../../../../models/DetalleSolicitudCotizadoResponse";
import {LocalStorageService} from "../../../../../services/local-storage/local-storage.service";

@Component({
  selector: 'app-row-solicitud-extend',
  standalone: true,
  imports: [
    FormsModule,
    ReactiveFormsModule
  ],
  templateUrl: './row-solicitud-extend.component.html',
  styleUrl: './row-solicitud-extend.component.css'
})
export class RowSolicitudExtendComponent {

  @Input()
  solicitudDetalle!: DetalleSolicitudResponse;
  @Output()
  cotizacionEmitida = new EventEmitter<DetalleSolicitudCotizadoResponse>(); // Evento de salida


  private formBuilder: FormBuilder;
  cotizacionForm: FormGroup;

  private localStorage: LocalStorageService;

  estadoBotonEstablecer: boolean = true;
  precioEstablecido: number = 0;

  constructor(formBuilder: FormBuilder,
              localStorage: LocalStorageService) {

    this.formBuilder = formBuilder;
    this.cotizacionForm = this.formBuilder.group({
      precUnitario: [],          // Agrega valor predeterminado
    });

    this.localStorage = localStorage;

  }


  botonSetPrecioUnitario(): void {
    const cotizacionDetalleSoli: DetalleSolicitudCotizadoResponse = {
      idSolicitud: this.solicitudDetalle.idSolicitud,
      idDetalleSolicitud: this.solicitudDetalle.idDetalleSolicitud,
      nroPaginas: this.solicitudDetalle.nroPaginas,
      nroCopias: this.solicitudDetalle.nroCopias,
      idUsuarioUnidad: this.localStorage.getItem('userData').idUsuarioUnidad,
      precioUnit: this.cotizacionForm.get('precUnitario')?.value
    };

    // Emitir el objeto al componente padre
    this.cotizacionEmitida.emit(cotizacionDetalleSoli);
    this.estadoBotonEstablecer = false;
    this.precioEstablecido = this.cotizacionForm.get('precUnitario')?.value;
  }

  botonSetDeshacerPrecioUnitario(): void {
    this.estadoBotonEstablecer = true;
  }

}
