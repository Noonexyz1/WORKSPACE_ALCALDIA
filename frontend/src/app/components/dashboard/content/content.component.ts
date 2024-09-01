import { Component } from '@angular/core';
import { DataTableComponent } from "./data-table/data-table.component";

@Component({
  selector: 'app-content',
  standalone: true,
  imports: [DataTableComponent],
  templateUrl: './content.component.html',
  styleUrl: './content.component.css'
})
export class ContentComponent {

}
