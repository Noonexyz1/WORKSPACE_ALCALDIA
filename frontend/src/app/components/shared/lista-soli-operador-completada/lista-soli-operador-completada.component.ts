import { Component } from '@angular/core';
import { RowTableCompletadaOperadorComponent } from './row-table-operador-completada/row-table-operador-completada.component';
import { HttpClient } from '@angular/common/http';
import { SubjectUserLoginService } from '../../../services/subject-user-login/subject-user-login.service';
import { LocalStorageService } from '../../../services/local-storage/local-storage.service';
import { SolicitudOperaResponse } from '../../../models/SolicitudOperaResponse';
import { UsuarioResponse } from '../../../models/UsuarioResponse';
import { PageRequestID } from '../../../models/PageRequestID';
import { catchError, map, of } from 'rxjs';

@Component({
  selector: 'app-lista-soli-operador-pendiente',
  standalone: true,
  imports: [RowTableCompletadaOperadorComponent],
  templateUrl: './lista-soli-operador-completada.component.html',
  styleUrl: './lista-soli-operador-completada.component.css'
})
export class ListaSoliCompletaOperadorComponent {

  private http: HttpClient;
  private observable: SubjectUserLoginService;
  private localStorage: LocalStorageService;
  listSolicitud: SolicitudOperaResponse[] = [];
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
    const url = 'http://localhost:8081/operador/verSolicitudesCompletas';

    this.observable.obtenerObservable().subscribe((datos) => {
      this.usuario = datos;
    });

    this.usuario = this.localStorage.getItem('userData');
    console.log('valor del local storage; ', this.usuario)

    const body: PageRequestID = {
      idUsuarioUnidad: this.usuario.id,
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
