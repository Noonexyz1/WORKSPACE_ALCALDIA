import { Component, Input } from '@angular/core';
import { SolicitudOperaResponse } from '../../../../models/SolicitudOperaResponse';
import { UsuarioResponse } from '../../../../models/UsuarioResponse';
import { HttpClient } from '@angular/common/http';
import { SubjectUserLoginService } from '../../../../services/subject-user-login/subject-user-login.service';
import { Router } from '@angular/router';
import { RootNavigateService } from '../../../../services/root-navigate/root-navigate.service';
import { LocalStorageService } from '../../../../services/local-storage/local-storage.service';
import { OperacionSoliRequest } from '../../../../models/OperacionSoliRequest';
import { catchError, map, of } from 'rxjs';

@Component({
  selector: 'app-row-table-operador-iniciada',
  standalone: true,
  imports: [],
  templateUrl: 'row-table-operador-iniciada.component.html',
  styleUrl: './row-table-operador-iniciada.component.css'
})
export class RowTablePendienteOperadorComponent {

  @Input()
  solicitud!: SolicitudOperaResponse;

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

  private http: HttpClient;
  private observable: SubjectUserLoginService;
  private router: Router;
  private rootNavigateService: RootNavigateService
  private localStorage: LocalStorageService

  constructor(http: HttpClient,
              observable: SubjectUserLoginService,
              router: Router,
              rootNavigateService: RootNavigateService,
              localStorage: LocalStorageService){

    this.http = http;
    this.observable = observable;
    this.router = router;
    this.rootNavigateService = rootNavigateService;
    this.localStorage = localStorage;
  }

  botonTerminarOperacion(): void {
    const url = 'http://localhost:8081/operador/terminarOperacion';

    this.observable.obtenerObservable().subscribe((datos) => {
      this.usuario = datos;
      console.log("Datos obtenidos del publicador", this.usuario);
    });

    this.usuario = this.localStorage.getItem('userData');
    console.log('valor del local storage; ', this.usuario)

    const aprobacion: OperacionSoliRequest = {
      idOperador: this.usuario.id,
      idOperacion: this.solicitud.idSolicitud
    };

    console.log('Aprobacion objeto: ', aprobacion)

    this.http.post<OperacionSoliRequest>(url, aprobacion).pipe(
      map((response) => {
        console.log('Datos para imprimir pdf', response)
        window.location.reload();

        let toNavegate = this.rootNavigateService.valorParaNavegar('Operador');
        this.router.navigate([toNavegate]);
      }),
      catchError(error => {
        console.error('Error en la petición:', error);
        alert('Hubo un error al iniciar la solicitud');
        return of(null); // Retornar un observable vacío en caso de error
      })
    ).subscribe();
  }

}
