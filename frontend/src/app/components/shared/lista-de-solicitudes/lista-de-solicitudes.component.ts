import { Component, OnInit } from '@angular/core';
import { RowTableSolicitudesComponent } from "./row-table-solicitudes/row-table-solicitudes.component";
import { HttpClient } from '@angular/common/http';
import { SolicitudResponse } from '../../../models/SolicitudResponse';
import { catchError, map, of } from 'rxjs';
import { PageRequestID } from '../../../models/PageRequestID';
import { SubjectUserLoginService } from '../../../services/subject-user-login/subject-user-login.service';
import { UsuarioResponse } from '../../../models/UsuarioResponse';
import { LocalStorageService } from '../../../services/local-storage/local-storage.service';

@Component({
  selector: 'app-lista-de-solicitudes',
  standalone: true,
  imports: [RowTableSolicitudesComponent],
  templateUrl: './lista-de-solicitudes.component.html',
  styleUrl: './lista-de-solicitudes.component.css'
})
export class ListaDeSolicitudesComponent implements OnInit {

  //TODO, tengo que publicar en un estado global el usuario 
  //con el que se ha iniciado sesion
  private http: HttpClient;
  private observable: SubjectUserLoginService;
  private localStorage: LocalStorageService;

  listSolicitud: SolicitudResponse[] = [];

  usuario: UsuarioResponse = {
    id: 0,
    nombres: '',
    apellidos: '',
    correo: '',
    nombreRol: '',
    dashConfig: '',
    idUnidad: 0
  };

  constructor(http: HttpClient, 
              observable: SubjectUserLoginService,
              localStorage: LocalStorageService){

    this.http = http;
    this.observable = observable;
    this.localStorage = localStorage;
  }

  ngOnInit(): void {
    this.observable.obtenerObservable().subscribe((datos) => {
      this.usuario = datos;
      console.log(this.usuario);
    });
    this.usuario = this.localStorage.getItem('userData');
    this.listarSolicitudes();
  }

  listarSolicitudes(): void {
    const url = 'http://localhost:8081/solicitante/verHistorialSolicitudes';
    
    this.usuario = this.localStorage.getItem('userData');

    const body: PageRequestID = {
      //TODO, este valor tiene que se de un observable general
      idUsuario: this.usuario.id,
      page: 0,
      size: 100,
      byColumName: ""
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
    )
    .subscribe();
  }

}
