import { Component } from '@angular/core';
import { ListTableOpeComponent } from "./list-table-ope/list-table-ope.component";
import { InfoSolicitudOpeComponent } from './info-solicitud-ope/info-solicitud-ope.component';

@Component({
  selector: 'app-operador-option',
  standalone: true,
  imports: [ListTableOpeComponent, InfoSolicitudOpeComponent],
  templateUrl: './operador-option.component.html',
  styleUrl: './operador-option.component.css'
})
export class OperadorOptionComponent {

  opcion = 1;

  botonListaSolicitudes(): void {
    this.opcion = 1;
  }

  botonVerSolicitud(): void {
    this.opcion = 2;
  }
}
