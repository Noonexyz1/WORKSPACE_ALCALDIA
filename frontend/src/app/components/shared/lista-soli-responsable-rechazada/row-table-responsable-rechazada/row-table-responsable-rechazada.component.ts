import { Component, Input } from '@angular/core';
import { SolicitudResponResponse } from '../../../../models/SolicitudResponResponse';

@Component({
  selector: 'app-row-table-responsable-rechazada',
  standalone: true,
  imports: [],
  templateUrl: './row-table-responsable-rechazada.component.html',
  styleUrl: './row-table-responsable-rechazada.component.css'
})
export class RowTableRechazadaResponsableComponent {

  @Input()
  solicitud!: SolicitudResponResponse;
 
}
