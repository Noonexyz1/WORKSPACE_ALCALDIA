import { Component } from '@angular/core';
import { RowTableComponent } from "./row-table/row-table.component";

@Component({
  selector: 'app-data-table',
  standalone: true,
  imports: [RowTableComponent],
  templateUrl: './data-table.component.html',
  styleUrl: './data-table.component.css'
})
export class DataTableComponent {

}
