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

}
