import { Component } from '@angular/core';
import { RowTableSoliComponent } from "./row-table-soli/row-table-soli.component";

@Component({
  selector: 'app-lista-soli-admin',
  standalone: true,
  imports: [RowTableSoliComponent],
  templateUrl: './lista-soli-admin.component.html',
  styleUrl: './lista-soli-admin.component.css'
})
export class ListaSoliAdminComponent {

}
