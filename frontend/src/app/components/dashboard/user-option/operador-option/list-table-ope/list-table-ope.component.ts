import { Component } from '@angular/core';
import { RowTableSoliOpeComponent } from "./row-table-soli-ope/row-table-soli-ope.component";

@Component({
  selector: 'app-list-table-ope',
  standalone: true,
  imports: [RowTableSoliOpeComponent],
  templateUrl: './list-table-ope.component.html',
  styleUrl: './list-table-ope.component.css'
})
export class ListTableOpeComponent {

}
