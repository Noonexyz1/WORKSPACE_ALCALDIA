import { Component, Input } from '@angular/core';
import { SolicitudResponResponse } from '../../../../models/SolicitudResponResponse';
import { AprobacionSoliRequest } from '../../../../models/AprobacionSoliRequest';
import { HttpClient } from '@angular/common/http';
import { SubjectUserLoginService } from '../../../../services/subject-user-login/subject-user-login.service';
import { UsuarioResponse } from '../../../../models/UsuarioResponse';
import { catchError, map, of } from 'rxjs';
import { Route, Router } from '@angular/router';
import { RootNavigateService } from '../../../../services/root-navigate/root-navigate.service';

@Component({
  selector: 'app-row-table-responsable-pendiente',
  standalone: true,
  imports: [],
  templateUrl: './row-table-responsable-pendiente.component.html',
  styleUrl: './row-table-responsable-pendiente.component.css'
})
export class RowTablePendienteResponsableComponent {

  @Input()
  solicitud!: SolicitudResponResponse;

  private http: HttpClient;
  private observable: SubjectUserLoginService;
  private router: Router;
  private rootNavigateService: RootNavigateService

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
              router: Router, 
              rootNavigateService: RootNavigateService){

    this.http = http;
    this.observable = observable;
    this.router = router;
    this.rootNavigateService = rootNavigateService;
  }


  botonAprobarSolicitud(): void {
    const url = 'http://localhost:8081/responsable/aprobarSolicitud'; // URL de tu API

    this.observable.obtenerObservable().subscribe((datos) => {
      this.usuario = datos;
      console.log("Datos obtenidos del publicador", this.usuario);
    });

    const aprobacion: AprobacionSoliRequest = {
      idResponsable: this.usuario.id,
      idAprobacion: this.solicitud.idSolicitud
    };
    
    console.log('Aprobacion objeto: ', aprobacion)

    this.http.post<AprobacionSoliRequest>(url, aprobacion).pipe(
      map(() => {
        let toNavegate = this.rootNavigateService.valorParaNavegar('Responsable');
        this.router.navigate([toNavegate]);
      }),
      catchError(error => {
        console.error('Error en la petición:', error);
        alert('Hubo un error al aprobar la solicitud');
        return of(null); // Retornar un observable vacío en caso de error
      })
    ).subscribe();
  }

  botonRechazarSolicitud(): void {

    const url = 'http://localhost:8081/responsable/rechazarSolicitud'; // URL de tu API

    this.observable.obtenerObservable().subscribe((datos) => {
      this.usuario = datos;
      console.log("Datos obtenidos del publicador", this.usuario);
    });

    const aprobacion: AprobacionSoliRequest = {
      idResponsable: this.usuario.id,
      idAprobacion: this.solicitud.idSolicitud
    };
    
    this.http.post<AprobacionSoliRequest>(url, aprobacion).pipe(
      map(() => {
        let toNavegate = this.rootNavigateService.valorParaNavegar('Responsable');
        this.router.navigate([toNavegate]);
      }),
      catchError(error => {
        console.error('Error en la petición:', error);
        alert('Hubo un error al recharzar la solicitud');
        return of(null); // Retornar un observable vacío en caso de error
      })
    ).subscribe();
  }

}
