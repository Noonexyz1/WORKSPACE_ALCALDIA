import {Component, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {SolicitudResponse} from '../../../models/SolicitudResponse';
import {catchError, map, of} from 'rxjs';
import {PageRequestID} from '../../../models/PageRequestID';
import {UsuarioResponse} from '../../../models/UsuarioResponse';
import {SubjectUserLoginService} from '../../../services/subject-user-login/subject-user-login.service';
import {SolicitudResponResponse} from '../../../models/SolicitudResponResponse';
import {
  RowTablePendienteResponsableComponent
} from './row-table-responsable-pendiente/row-table-responsable-pendiente.component';
import {LocalStorageService} from '../../../services/local-storage/local-storage.service';

@Component({
  selector: 'app-lista-soli-responsable',
  standalone: true,
  imports: [RowTablePendienteResponsableComponent],
  templateUrl: './lista-soli-responsable-pendiente.component.html',
  styleUrl: './lista-soli-responsable-pendiente.component.css'
})
export class ListaSoliPendienteResponsableComponent implements OnInit {

  private http: HttpClient;
  private observable: SubjectUserLoginService;
  private localStorage: LocalStorageService;

  listSolicitud: SolicitudResponResponse[] = [];

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

  constructor(http: HttpClient,
              observable: SubjectUserLoginService,
              localStorage: LocalStorageService) {

    this.http = http;
    this.observable = observable;
    this.localStorage = localStorage;
  }

  ngOnInit(): void {
    this.listarSolicitudes();
  }

  listarSolicitudes(): void {
    const url = 'http://localhost:8081/responsable/verSolicitudesPendientes';

    this.observable.obtenerObservable().subscribe((datos) => {
      this.usuario = datos;
    });

    this.usuario = this.localStorage.getItem('userData');

    const body: PageRequestID = {
      idUsuarioUnidad: this.usuario.id,
      page: 0,
      size: 100,
      byColumName: ""
    }

    this.http.post<SolicitudResponResponse[]>(url, body).pipe(
      map((response: SolicitudResponResponse[]) => {
        this.listSolicitud = response;
      }),
      catchError(error => {
        console.error('Error en la petición:', error);
        alert('Hubo un error al listar las solicitudes pendientes para el responsable');
        return of(null); // Retornar un observable vacío en caso de error
      })
    ).subscribe();
  }

}
