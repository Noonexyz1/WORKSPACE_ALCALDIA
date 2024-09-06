import { Component } from '@angular/core';
import { ListTableRespoComponent } from './list-table-respo/list-table-respo.component';
import { InfoSolicitudRespoComponent } from './info-solicitud-respo/info-solicitud-respo.component';

@Component({
  selector: 'app-responsable-option',
  standalone: true,
  imports: [ListTableRespoComponent, InfoSolicitudRespoComponent],
  templateUrl: './responsable-option.component.html',
  styleUrl: './responsable-option.component.css'
})
export class ResponsableOptionComponent {
  
  opcion = 1;

  botonListaSolicitudes(): void {
    this.opcion = 1;
  }

  botonVerSolicitud(): void {
    this.opcion = 2;
  }
}
