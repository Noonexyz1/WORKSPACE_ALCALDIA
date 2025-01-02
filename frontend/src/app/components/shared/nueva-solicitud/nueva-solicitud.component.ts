import { HttpClient } from '@angular/common/http';
import {Component, OnInit} from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { SolicitudRequest } from '../../../models/SolicitudRequest';
import {catchError, map, of} from 'rxjs';
import { UsuarioResponse } from '../../../models/UsuarioResponse';
import { LocalStorageService } from '../../../services/local-storage/local-storage.service';
import { RowDocumentComponent } from './row-document/row-document.component';
import { RowSolicitud } from '../../../models/RowSolicitud';
import {SubjectDocumentoService} from "../../../services/subject-documento/subject-documento.service";
import {UrlsProperties} from "../../../enums/UrlsProperties";

@Component({
  selector: 'app-nueva-solicitud',
  standalone: true,
  imports: [ReactiveFormsModule, RowDocumentComponent],
  templateUrl: './nueva-solicitud.component.html',
  styleUrl: './nueva-solicitud.component.css'
})
export class NuevaSolicitudComponent implements OnInit{

  private http: HttpClient;
  private formBuilder: FormBuilder;
  private router: Router;
  private localStorage: LocalStorageService;

  usuario: UsuarioResponse = {
    id: 0,
    fkUsuario: 0,
    fkUnidad: 0,
    fkRol: 0,
    nombreRol: '',
    dashConfig: '',
    fkCargo: 0,
    fkResponsable: 0,

    nombreUsuario: '',
    apellidoUsuario: ''
  };

  solicitudForm: FormGroup;

  nroDeDocumentos: number = 0;
  estadoNroDocumentos: boolean = false;

  listSolicitud: RowSolicitud[] = []
  listSolicitudToFor: RowSolicitud[] = []

  private observable: SubjectDocumentoService;

  constructor(http: HttpClient,
              formBuilder: FormBuilder,
              router: Router,
              localStorage: LocalStorageService,
              observable: SubjectDocumentoService){

    this.observable = observable;
    this.http = http;
    this.formBuilder = formBuilder;
    this.router = router;
    this.localStorage = localStorage;
    this.solicitudForm = this.formBuilder.group({
      fkUsuarioSolicitante: [],
      cite: [],
      descripcion: [],

      nroDeDocumento: [],
    });

  }

  ngOnInit(): void {
    this.observable.obtenerObservable().subscribe((nuevoDocumento: RowSolicitud) => {
      if (nuevoDocumento.tamanoPagina) {
        this.listSolicitud.push(nuevoDocumento);
      }
    });
  }

  botonNuevaSolicitud(): void {
    this.usuario = this.localStorage.getItem('userData');

    const solicitudRequest: SolicitudRequest = {
      fkUsuarioSolicitante: this.usuario.id,
      cite: this.solicitudForm.get('cite')?.value,
      descripcion: this.solicitudForm.get('descripcion')?.value,

      listDetalleSolicitud: this.listSolicitud,
    };

    // Enviar la solicitud
    this.http.post<SolicitudRequest>(
      UrlsProperties.PATH_CREATE_SOLICITUD,
      solicitudRequest
    ).pipe(
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
}
