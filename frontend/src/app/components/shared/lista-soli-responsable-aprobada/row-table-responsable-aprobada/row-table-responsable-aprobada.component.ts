import { Component, Input } from '@angular/core';
import { SolicitudResponResponse } from '../../../../models/SolicitudResponResponse';

@Component({
  selector: 'app-row-table-responsable-aprobada',
  standalone: true,
  imports: [],
  templateUrl: './row-table-responsable-aprobada.component.html',
  styleUrl: './row-table-responsable-aprobada.component.css'
})
export class RowTableAprobadaResponsableComponent {

  @Input()
  solicitud!: SolicitudResponResponse;
  
}
