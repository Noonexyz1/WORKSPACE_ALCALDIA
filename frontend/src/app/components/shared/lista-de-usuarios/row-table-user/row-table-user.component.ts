import { Component } from '@angular/core';
import { Router, RouterLink } from '@angular/router';

@Component({
  selector: 'app-row-table-user',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './row-table-user.component.html',
  styleUrl: './row-table-user.component.css'
})
export class RowTableUserComponent {

  private router: Router;

  constructor(router: Router) {
    this.router = router;
  }
  
  botonEditarUsuario(): void { 
    this.router.navigate(['/administrador/editarUsuario']);
  }
}
