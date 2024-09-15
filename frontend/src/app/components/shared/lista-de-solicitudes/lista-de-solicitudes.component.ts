import { Component } from '@angular/core';
import { RowTableSolicitudesComponent } from "./row-table-solicitudes/row-table-solicitudes.component";

@Component({
  selector: 'app-lista-de-solicitudes',
  standalone: true,
  imports: [RowTableSolicitudesComponent],
  templateUrl: './lista-de-solicitudes.component.html',
  styleUrl: './lista-de-solicitudes.component.css'
})
export class ListaDeSolicitudesComponent {
  
}
