import {AfterViewInit, ChangeDetectorRef, Component, Input} from '@angular/core';
import {SolicitudResponse} from '../../../../models/SolicitudResponse';

@Component({
  selector: 'app-row-table-solicitudes',
  standalone: true,
  imports: [],
  templateUrl: './row-table-solicitudes.component.html',
  styleUrl: './row-table-solicitudes.component.css'
})
export class RowTableSolicitudesComponent {

  @Input()
  solicitud!: SolicitudResponse;

  botonSolicitudFotocopiaPDF(): void {
    alert("botonSolicitudFotocopiaPDF()");
  }

  botonInformePDF(): void {
    alert("botonInformePDF()")
  }

  botonComunicacionInterna(): void {
    alert("botonComunicacionInterna()");
  }

}
