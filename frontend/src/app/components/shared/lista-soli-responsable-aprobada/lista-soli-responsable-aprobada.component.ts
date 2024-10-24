import { Component, OnInit } from '@angular/core';
import { RowTableAprobadaResponsableComponent } from "./row-table-responsable-aprobada/row-table-responsable-aprobada.component";
import { HttpClient } from '@angular/common/http';
import { SolicitudResponse } from '../../../models/SolicitudResponse';
import { catchError, map, of } from 'rxjs';
import { PageRequestID } from '../../../models/PageRequestID';
import { UsuarioResponse } from '../../../models/UsuarioResponse';
import { SubjectUserLoginService } from '../../../services/subject-user-login/subject-user-login.service';
import { SolicitudResponResponse } from '../../../models/SolicitudResponResponse';

@Component({
  selector: 'app-lista-soli-responsable',
  standalone: true,
  imports: [RowTableAprobadaResponsableComponent],
  templateUrl: './lista-soli-responsable-aprobada.component.html',
  styleUrl: './lista-soli-responsable-aprobada.component.css'
})
export class ListaSoliAprobadaResponsableComponent implements OnInit{

  private http: HttpClient;
  private observable: SubjectUserLoginService;

  listSolicitud: SolicitudResponResponse[] = [];

  usuario: UsuarioResponse = {
    id: 0,
    nombres: '',
    apellidos: '',
    correo: '',
    nombreRol: '',
    dashConfig: '',
    idUnidad: 0
  };

  constructor(http: HttpClient, observable: SubjectUserLoginService){
    this.http = http;
    this.observable = observable;
  }

  ngOnInit(): void {
    this.observable.obtenerObservable().subscribe((datos) => {
      this.usuario = datos;
      console.log(this.usuario);
    });
    this.listarSolicitudes();
  }

  listarSolicitudes(): void {
    const url = 'http://localhost:8081/responsable/verSolicitudesAprobadas';
    
    const body: PageRequestID = {
      //TODO, este valor tiene que se de un observable general
      idUsuario: this.usuario.id,
      page: 0,
      size: 10,
      byColumName: ""
    }

    this.http.post<SolicitudResponResponse[]>(url, body).pipe(
      map((response: SolicitudResponResponse[]) => {
        console.log(response);
        this.listSolicitud = response;
      }),
      catchError(error => {
        console.error('Error en la petición:', error);
        alert('Hubo un error al listar las solicitudes para el responsable');
        return of(null); // Retornar un observable vacío en caso de error
      })
    )
    .subscribe();
  }

}
