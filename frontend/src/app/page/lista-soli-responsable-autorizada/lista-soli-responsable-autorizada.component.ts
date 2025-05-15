import {Component} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {TablaResponsableComponent} from "../../share/tabla-responsable/tabla-responsable.component";

@Component({
  selector: 'app-lista-soli-responsable-autorizada',
  standalone: true,
  imports: [FormsModule, ReactiveFormsModule, TablaResponsableComponent],
  templateUrl: './lista-soli-responsable-autorizada.component.html'
})
export class ListaSoliAutorizadaResponsableComponent {

  tituloDeTabla: string = "Lista de solicitudes autorizadas";
  listTituloTabla: string[] = ["Accion", "Id", "Cite", "Fecha", "Autor", "Cargo", "Unidad"];

}
