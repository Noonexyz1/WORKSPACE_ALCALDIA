import { Component, Input } from '@angular/core';
import { SolicitudResponResponse } from '../../../../models/SolicitudResponResponse';
import { SolicitudOperaResponse } from '../../../../models/SolicitudOperaResponse';
import { UsuarioResponse } from '../../../../models/UsuarioResponse';
import { catchError, map, of } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { SubjectUserLoginService } from '../../../../services/subject-user-login/subject-user-login.service';
import { Route, Router } from '@angular/router';
import { RootNavigateService } from '../../../../services/root-navigate/root-navigate.service';
import { OperacionSoliRequest } from '../../../../models/OperacionSoliRequest';
import { LocalStorageService } from '../../../../services/local-storage/local-storage.service';
import { ArchivoPdfResponse } from '../../../../models/ArchivoPdfResponse';
import { ArchivoPdfService } from '../../../../services/archivo-pdf/archivo-pdf.service';

@Component({
  selector: 'app-row-table-operador-pendiente',
  standalone: true,
  imports: [],
  templateUrl: 'row-table-operador-pendiente.component.html',
  styleUrl: './row-table-operador-pendiente.component.css'
})
export class RowTablePendienteOperadorComponent {

  @Input()
  solicitud!: SolicitudOperaResponse;

  usuario: UsuarioResponse = {
    id: 0,
    nombres: '',
    apellidos: '',
    correo: '',
    nombreRol: '',
    dashConfig: '',
    idUnidad: 0
  };

  private http: HttpClient;
  private observable: SubjectUserLoginService;
  private router: Router;
  private rootNavigateService: RootNavigateService;
  private localStorage: LocalStorageService;
  private archivoPdfService: ArchivoPdfService;

  archivoPdfResponse: ArchivoPdfResponse[] = [];

  constructor(http: HttpClient, 
              observable: SubjectUserLoginService,
              router: Router, 
              rootNavigateService: RootNavigateService,
              localStorage: LocalStorageService,
              archivoPdfService: ArchivoPdfService){

    this.http = http;
    this.observable = observable;
    this.router = router;
    this.rootNavigateService = rootNavigateService;
    this.localStorage = localStorage;
    this.archivoPdfService = archivoPdfService;
  }

  botonIniciarOperacion(): void {
    const url = 'http://localhost:8081/operador/iniciarOperacion'; // URL de tu API

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

    //post<ValorDeRespuesta>
    this.http.post<ArchivoPdfResponse[]>(url, aprobacion).pipe(
      map((response) => {
        this.archivoPdfResponse = response;
    
        // Intervalo entre cada descarga
        this.archivoPdfResponse.forEach((archivo, index) => {
          setTimeout(() => {
            this.archivoPdfService.descargarPdf(archivo);
          }, index * 500); // Intervalo de 500 ms entre cada descarga
        });
    
        // Navegación después de que todas las descargas deberían estar completas
        setTimeout(() => {
          const toNavegate = this.rootNavigateService.valorParaNavegar('Operador');
          this.router.navigate([toNavegate]);
          window.location.reload();
        }, this.archivoPdfResponse.length * 500 + 1000); // Tiempo suficiente para todas las descargas
      }),
      catchError(error => {
        console.error('Error en la petición:', error);
        alert('Hubo un error al iniciar la solicitud');
        return of(null); // Retornar un observable vacío en caso de error
      })
    ).subscribe();

    
  }

}
