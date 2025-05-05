import {Component, Input} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {DetalleSolicitudResponse} from "../../../../utils/models/DetalleSolicitudResponse";

@Component({
  selector: 'app-row-solicitud-extend',
  standalone: true,
  imports: [
    FormsModule,
    ReactiveFormsModule
  ],
  templateUrl: './row-solicitud-extend.component.html'
})
export class RowSolicitudExtendComponent {
  @Input()
  solicitudDetalle!: DetalleSolicitudResponse;
}
