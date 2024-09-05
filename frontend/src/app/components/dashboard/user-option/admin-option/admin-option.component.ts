import { Component } from '@angular/core';
import { FormNuevoUsuarioComponent } from "./form-nuevo-usuario/form-nuevo-usuario.component";
import { InfoSolicitudComponent } from "./info-solicitud/info-solicitud.component";
import { ListaUsuariosComponent } from "./lista-usuarios/lista-usuarios.component";

@Component({
  selector: 'app-admin-option',
  standalone: true,
  imports: [FormNuevoUsuarioComponent, InfoSolicitudComponent, ListaUsuariosComponent],
  templateUrl: './admin-option.component.html',
  styleUrl: './admin-option.component.css'
})
export class AdminOptionComponent {
  
  opcion: number = 1;

  botonListaUsuarios(): void {
    this.opcion = 1;
  }

  botonListaSolicitudes(): void {
    this.opcion = 2;
  }

  botonGenerarReporte(): void {
    this.opcion = 3;
  }
}
