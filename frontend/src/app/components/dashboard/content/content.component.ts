import { Component } from '@angular/core';
import { DataTableComponent } from "./data-table/data-table.component";
import { FormNuevoUsuarioComponent } from "./form-nuevo-usuario/form-nuevo-usuario.component";
import { InfoSolicitudComponent } from "./info-solicitud/info-solicitud.component";

@Component({
  selector: 'app-content',
  standalone: true,
  imports: [DataTableComponent, FormNuevoUsuarioComponent, InfoSolicitudComponent],
  templateUrl: './content.component.html',
  styleUrl: './content.component.css'
})
export class ContentComponent {

}
