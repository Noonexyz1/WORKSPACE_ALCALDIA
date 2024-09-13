import { Component } from '@angular/core';
import { RowTableUserComponent } from "./row-table-user/row-table-user.component";

@Component({
  selector: 'app-lista-de-usuarios',
  standalone: true,
  imports: [RowTableUserComponent],
  templateUrl: './lista-de-usuarios.component.html',
  styleUrl: './lista-de-usuarios.component.css'
})
export class ListaDeUsuariosComponent {

}
