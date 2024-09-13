import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-row-table-user',
  standalone: true,
  imports: [],
  templateUrl: './row-table-user.component.html',
  styleUrl: './row-table-user.component.css'
})
export class RowTableUserComponent {

  private router: Router;

  constructor(router: Router) {
    this.router = router;
  }

  botonVerUsuario(): void { 
    this.router.navigate(['/dashboard/admin/listaUsuarios/ver']);
  }
  botonEditarUsuario(): void { 
    this.router.navigate(['/dashboard/admin/listaUsuarios/editar']);
  }
}
