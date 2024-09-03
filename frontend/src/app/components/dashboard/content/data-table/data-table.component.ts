import { Component } from '@angular/core';
import { RowTableUserComponent } from "./row-table-user/row-table-user.component";
import { RowTableSoliComponent } from './row-table-soli/row-table-soli.component';
import { RowTableOpeComponent } from './row-table-ope/row-table-ope.component';

@Component({
  selector: 'app-data-table',
  standalone: true,
  imports: [RowTableUserComponent, RowTableSoliComponent, RowTableOpeComponent],
  templateUrl: './data-table.component.html',
  styleUrl: './data-table.component.css'
})
export class DataTableComponent {

}
