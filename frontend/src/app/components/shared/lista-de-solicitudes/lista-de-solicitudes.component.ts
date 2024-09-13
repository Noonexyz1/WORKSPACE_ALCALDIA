import { Component } from '@angular/core';
import { RowTableSoliOpeComponent } from "./row-table-soli-ope/row-table-soli-ope.component";

@Component({
  selector: 'app-lista-de-solicitudes',
  standalone: true,
  imports: [RowTableSoliOpeComponent],
  templateUrl: './lista-de-solicitudes.component.html',
  styleUrl: './lista-de-solicitudes.component.css'
})
export class ListaDeSolicitudesComponent {
  
}
