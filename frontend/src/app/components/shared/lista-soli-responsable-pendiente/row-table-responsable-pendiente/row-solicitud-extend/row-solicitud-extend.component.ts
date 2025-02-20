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
}
