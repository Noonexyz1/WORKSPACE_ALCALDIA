import {Component} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule} from '@angular/forms';
import {RowSolicitud} from '../../../../models/RowSolicitud';
import {SubjectDocumentoService} from "../../../../services/subject-documento/subject-documento.service";

@Component({
  selector: 'app-row-document',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './row-document.component.html',
  styleUrls: ['./row-document.component.css']
})
export class RowDocumentComponent {

  rowSoliForm: FormGroup;

  valor: string = "";

  //Instancia por de fecto
  rowSolicitud: RowSolicitud = {
    nombreDocumento: 'ALERT',
    nroPaginas: 0,
    nroCopias: 0,
    tamanoPagina: 'ALERT',
    anversoReverso: 'ALERT',
    colorFotocopia: 'ALERT'
  }

  private observable: SubjectDocumentoService;

  constructor(private formBuilder: FormBuilder,
              observable: SubjectDocumentoService) {

    this.observable = observable;
    this.rowSoliForm = this.formBuilder.group({
      nombreRowDocumento: [],   // Agrega valor predeterminado
      nroRowPaginas: [],         // Agrega valor predeterminado
      nroRowCopias: [],          // Agrega valor predeterminado

      tamanoRowPagina: [],          // Agrega valor predeterminado
      anversoRowReverso: [],          // Agrega valor predeterminado
      colorRowFotocopia: [],          // Agrega valor predeterminado
    });
  }

  botonDocumentoInsertado(): void {
    this.rowSolicitud.nombreDocumento = this.rowSoliForm.get('nombreRowDocumento')?.value;
    this.rowSolicitud.nroPaginas = this.rowSoliForm.get('nroRowPaginas')?.value;
    this.rowSolicitud.nroCopias = this.rowSoliForm.get('nroRowCopias')?.value;
    this.rowSolicitud.tamanoPagina = this.rowSoliForm.get('tamanoRowPagina')?.value;
    this.rowSolicitud.anversoReverso = this.rowSoliForm.get('anversoRowReverso')?.value;
    this.rowSolicitud.colorFotocopia = this.rowSoliForm.get('colorRowFotocopia')?.value;
    //Publicando la instancia en el Observable

    this.observable.publicarDatos(this.rowSolicitud);
    console.log(this.rowSolicitud);
    alert("Documento registrado con exito")
  }
}
