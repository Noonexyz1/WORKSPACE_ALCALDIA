import { Component } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-admin-option',
  standalone: true,
  imports: [RouterOutlet],
  templateUrl: './admin-option.component.html',
  styleUrl: './admin-option.component.css'
})
export class AdminOptionComponent {

  private router: Router;

  constructor(route: Router) {
    this.router = route;
  }

  botonListaUsuarios(): void {
    this.router.navigate(['/dashboard/admin/listaUsuarios']);
  }
  
  botonNuevoUsuario(): void {
    this.router.navigate(['/dashboard/admin/nuevoUsuario']);
  }
  
  botonGenerarReporte(): void {
    this.router.navigate(['/dashboard/admin/****************']);
  }

  botonCambiarContrasena(): void {
    this.router.navigate(['/dashboard/admin/cambioPass']);
  }
}
