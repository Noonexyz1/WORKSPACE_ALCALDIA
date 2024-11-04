import { Component, OnInit } from '@angular/core';
import { RowTablePendienteOperadorComponent } from './row-table-operador-pendiente/row-table-operador-pendiente.component';
import { SolicitudResponResponse } from '../../../models/SolicitudResponResponse';
import { HttpClient } from '@angular/common/http';
import { SubjectUserLoginService } from '../../../services/subject-user-login/subject-user-login.service';
import { catchError, map, of } from 'rxjs';
import { PageRequestID } from '../../../models/PageRequestID';
import { UsuarioResponse } from '../../../models/UsuarioResponse';
import { LocalStorageService } from '../../../services/local-storage/local-storage.service';
import { SolicitudOperaResponse } from '../../../models/SolicitudOperaResponse';

@Component({
  selector: 'app-lista-soli-operador-pendiente',
  standalone: true,
  imports: [RowTablePendienteOperadorComponent],
  templateUrl: './lista-soli-operador-pendiente.component.html',
  styleUrl: './lista-soli-operador-pendiente.component.css'
})
export class ListaSoliPendienteOperadorComponent implements OnInit{

  private http: HttpClient;
  private observable: SubjectUserLoginService;
  private localStorage: LocalStorageService;
  listSolicitud: SolicitudOperaResponse[] = [];
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
    this.usuario = this.localStorage.getItem('userData');
    this.listarSolicitudes();
  }

  listarSolicitudes(): void {
    const url = 'http://localhost:8081/operador/verSolicitudesPendientes';
    
    this.observable.obtenerObservable().subscribe((datos) => {
      this.usuario = datos;
    });

    this.usuario = this.localStorage.getItem('userData');
    console.log('valor del local storage; ', this.usuario)
    
    const body: PageRequestID = {
      //TODO, este valor tiene que se de un observable general
      idUsuario: this.usuario.id,
      page: 0,
      size: 100,
      byColumName: ""
    }

    console.log('Datos a enviar: ', body)

    this.http.post<SolicitudOperaResponse[]>(url, body).pipe(
      map((response: SolicitudOperaResponse[]) => {
        console.log('valor de respuesta: ', response);
        this.listSolicitud = response;
      }),
      catchError(error => {
        console.error('Error en la petición:', error);
        alert('Hubo un error al listar las solicitudes pendientes para el operador');
        return of(null); // Retornar un observable vacío en caso de error
      })
    )
    .subscribe();
  }

}
