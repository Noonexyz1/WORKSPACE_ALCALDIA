import {Component} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule} from '@angular/forms';
import {RowSolicitud} from '../../../../models/RowSolicitud';

@Component({
  selector: 'app-row-document',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './row-document.component.html',
  styleUrls: ['./row-document.component.css']
})
export class RowDocumentComponent {

  rowSoliForm: FormGroup;

  valor: string = ""

  rowSolicitud: RowSolicitud = {
    nombreDocumento: '',
    nroPaginas: 0,
    nroCopias: 0
  }

  constructor(private formBuilder: FormBuilder) {
    this.rowSoliForm = this.formBuilder.group({
      nombreDocumento: [''],   // Agrega valor predeterminado
      nroPaginas: [0],         // Agrega valor predeterminado
      nroCopias: [0],          // Agrega valor predeterminado
    });
  }

  botonDocumentoInsertado(): void {
    this.rowSolicitud.nombreDocumento = this.rowSoliForm.get('nombreDocumento')?.value;
    this.rowSolicitud.nroPaginas = this.rowSoliForm.get('nroPaginas')?.value;
    this.rowSolicitud.nroCopias = this.rowSoliForm.get('nroCopias')?.value;

    console.log(this.rowSolicitud);
  }
}
