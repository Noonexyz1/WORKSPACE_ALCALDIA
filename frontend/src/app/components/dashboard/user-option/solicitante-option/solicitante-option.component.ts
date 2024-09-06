import { Component } from '@angular/core';
import { ListTableSoliComponent } from "./list-table-soli/list-table-soli.component";
import { FormNuevaSolicitudComponent } from "./form-nueva-solicitud/form-nueva-solicitud.component";
import { FormNuevoPassComponent } from './form-nuevo-pass/form-nuevo-pass.component';

@Component({
  selector: 'app-solicitante-option',
  standalone: true,
  imports: [ListTableSoliComponent, FormNuevaSolicitudComponent, FormNuevoPassComponent],
  templateUrl: './solicitante-option.component.html',
  styleUrl: './solicitante-option.component.css'
})
export class SolicitanteOptionComponent {

  opcion = 1;

  botonListaSolicitudes(): void {
    this.opcion = 1;
  }

  botonNuevaSolicitud(): void {
    this.opcion = 2;
  }
  botonNuevoPass(): void {
    this.opcion = 3;
  }
}
