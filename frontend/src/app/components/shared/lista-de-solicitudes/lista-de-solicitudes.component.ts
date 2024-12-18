import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {RowTableSolicitudesComponent} from "./row-table-solicitudes/row-table-solicitudes.component";
import {HttpClient} from '@angular/common/http';
import {SolicitudResponse} from '../../../models/SolicitudResponse';
import {catchError, map, of} from 'rxjs';
import {PageRequestID} from '../../../models/PageRequestID';
import {SubjectUserLoginService} from '../../../services/subject-user-login/subject-user-login.service';
import {UsuarioResponse} from '../../../models/UsuarioResponse';
import {LocalStorageService} from '../../../services/local-storage/local-storage.service';

@Component({
  selector: 'app-lista-de-solicitudes',
  standalone: true,
  imports: [RowTableSolicitudesComponent],
  templateUrl: './lista-de-solicitudes.component.html',
  styleUrl: './lista-de-solicitudes.component.css'
})
export class ListaDeSolicitudesComponent implements OnInit {

  private http: HttpClient;
  private localStorage: LocalStorageService;

  listSolicitud: SolicitudResponse[] = [];

  usuario: UsuarioResponse = {
    id: 0,
    fkUsuario: 0,
    fkUnidad: 0,
    fkRol: 0,
    nombreRol: '',
    dashConfig: '',
    fkCargo: 0,
    fkResponsable: 0
  };

  constructor(http: HttpClient,
              localStorage: LocalStorageService) {

    this.http = http;
    this.localStorage = localStorage;
  }

  ngOnInit(): void {
    this.listarSolicitudes();
  }

  listarSolicitudes(): void {
    const url = 'http://localhost:8081/solicitante/verHistorialSolicitudes';

    this.usuario = this.localStorage.getItem('userData');

    const body: PageRequestID = {
      idUsuarioUnidad: this.usuario.id,
      page: 0,
      size: 10,
      byColumName: ''
    }

    this.http.post<SolicitudResponse[]>(url, body).pipe(
      map((response: SolicitudResponse[]) => {
        console.log(response);
        this.listSolicitud = response;
      }),
      catchError(error => {
        console.error('Error en la petición:', error);
        alert('Hubo un error al listar las solicitudes de usuario');
        return of(null); // Retornar un observable vacío en caso de error
      })
    ).subscribe();

  }

}
