import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { SolicitudRequest } from '../../../models/SolicitudRequest';
import { catchError, map, of } from 'rxjs';
import { UsuarioResponse } from '../../../models/UsuarioResponse';
import { LocalStorageService } from '../../../services/local-storage/local-storage.service';
import { RowDocumentComponent } from './row-document/row-document.component';
import { RowSolicitud } from '../../../models/RowSolicitud';

@Component({
  selector: 'app-nueva-solicitud',
  standalone: true,
  imports: [ReactiveFormsModule, RowDocumentComponent, RowDocumentComponent],
  templateUrl: './nueva-solicitud.component.html',
  styleUrl: './nueva-solicitud.component.css'
})
export class NuevaSolicitudComponent {

  private http: HttpClient;
  private formBuilder: FormBuilder;
  private router: Router;
  private localStorage: LocalStorageService;

  usuario: UsuarioResponse = {
    id: 0,
    nombres: '',
    apellidos: '',
    correo: '',
    nombreRol: '',
    dashConfig: '',
    idUnidad: 0
  };

  solicitudForm: FormGroup;

  nroDeDocumentos: number = 0;
  estadoNroDocumentos: boolean = false;

  listSolicitud: RowSolicitud[] = []
  listSolicitudToFor: RowSolicitud[] = []

  constructor(http: HttpClient,
              formBuilder: FormBuilder,
              router: Router,
              localStorage: LocalStorageService){

    this.http = http;
    this.formBuilder = formBuilder;
    this.router = router;
    this.localStorage = localStorage;
    this.solicitudForm = this.formBuilder.group({
      cite: [],

      nroDeDocumento: [],

      nombreRowDocumento: [],   // Agrega valor predeterminado
      nroRowPaginas: [],         // Agrega valor predeterminado
      nroRowCopias: [],          // Agrega valor predeterminado
    });

  }

  botonNuevaSolicitud(): void {
    const url = 'http://localhost:8081/solicitante/solicitarFotocopiarPDF'; // URL de tu API
    this.usuario = this.localStorage.getItem('userData');

    const solicitudRequest: SolicitudRequest = {
      cite: this.solicitudForm.get('cite')?.value,
      idSolicitante: this.usuario.id,
      idUnidad: this.usuario.idUnidad,
      listSolicitud: this.listSolicitud,
    };

    // Enviar la solicitud
    this.http.post<SolicitudRequest>(url, solicitudRequest).pipe(
      map(() => {
        this.router.navigate(['/solicitante/misSolicitudes']);
      }),
      catchError(error => {
        console.error('Error en la petición:', error);
        alert('Hubo un error al enviar la solicitud');
        return of(null); // Retornar un observable vacío en caso de error
      })
    ).subscribe();

  }

  botonSetNumeroDeCopias(): void {
    this.nroDeDocumentos = this.solicitudForm.get('nroDeDocumento')?.value;
    this.listSolicitudToFor = new Array(this.nroDeDocumentos);
    this.estadoNroDocumentos = true;
  }

  botonDocumentoInsertado(): void {
    const nuevoDocumento: RowSolicitud = {
      nombreDocumento: this.solicitudForm.get('nombreRowDocumento')?.value,
      nroPaginas: this.solicitudForm.get('nroRowPaginas')?.value,
      nroCopias: this.solicitudForm.get('nroRowCopias')?.value,
    };

    this.listSolicitud.push(nuevoDocumento);
    console.log(this.listSolicitud); // Verifica que el documento se agregue correctamente
  }
}
