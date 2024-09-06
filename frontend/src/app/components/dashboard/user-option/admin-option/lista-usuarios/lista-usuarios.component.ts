import { Component } from '@angular/core';
import { RowTableUserComponent } from "./row-table-user/row-table-user.component";

@Component({
  selector: 'app-lista-usuarios',
  standalone: true,
  imports: [RowTableUserComponent],
  templateUrl: './lista-usuarios.component.html',
  styleUrl: './lista-usuarios.component.css'
})
export class ListaUsuariosComponent {

}
