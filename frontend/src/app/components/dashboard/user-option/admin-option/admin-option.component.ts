import { Component } from '@angular/core';
import { DataTableComponent } from "./data-table/data-table.component";
import { FormNuevoUsuarioComponent } from "./form-nuevo-usuario/form-nuevo-usuario.component";
import { InfoSolicitudComponent } from "./info-solicitud/info-solicitud.component";

@Component({
  selector: 'app-admin-option',
  standalone: true,
  imports: [DataTableComponent, FormNuevoUsuarioComponent, InfoSolicitudComponent],
  templateUrl: './admin-option.component.html',
  styleUrl: './admin-option.component.css'
})
export class AdminOptionComponent {
  
  botonListaUsuarios(): void {
    alert('Lista de usuarios');
  }

  botonListaSolicitudes(): void {
    alert('Lista de solicitudes');
  }

  botonGenerarReporte(): void {
    alert('Generar Reporte');
  }
}
