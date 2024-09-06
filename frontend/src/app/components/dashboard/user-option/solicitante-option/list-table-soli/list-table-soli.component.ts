import { Component } from '@angular/core';
import { RowTableSoliSoliComponent } from "./row-table-soli-soli/row-table-soli-soli.component";

@Component({
  selector: 'app-list-table-soli',
  standalone: true,
  imports: [RowTableSoliSoliComponent],
  templateUrl: './list-table-soli.component.html',
  styleUrl: './list-table-soli.component.css'
})
export class ListTableSoliComponent {

}
