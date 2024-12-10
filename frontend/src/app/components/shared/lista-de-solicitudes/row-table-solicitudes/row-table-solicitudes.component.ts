import {AfterViewInit, ChangeDetectorRef, Component, Input} from '@angular/core';
import {SolicitudResponse} from '../../../../models/SolicitudResponse';
import {LocalStorageService} from "../../../../services/local-storage/local-storage.service";
import {UsuarioResponse} from "../../../../models/UsuarioResponse";
import {HttpClient} from "@angular/common/http";
import {catchError, map, of} from "rxjs";
import {Router} from "@angular/router";
import {SubjectUserLoginService} from "../../../../services/subject-user-login/subject-user-login.service";
import {RootNavigateService} from "../../../../services/root-navigate/root-navigate.service";

@Component({
  selector: 'app-row-table-solicitudes',
  standalone: true,
  imports: [],
  templateUrl: './row-table-solicitudes.component.html',
  styleUrl: './row-table-solicitudes.component.css'
})
export class RowTableSolicitudesComponent {

  @Input()
  solicitud!: SolicitudResponse;

  isModalVisible: boolean = false;

  private localStorage: LocalStorageService;
  private http: HttpClient;

  private router: Router;
  private rootNavigateService: RootNavigateService;

  toggleModal(): void {
    this.isModalVisible = !this.isModalVisible;
  }

  constructor(localStorage: LocalStorageService,
              http: HttpClient,
              router: Router,
              rootNavigateService: RootNavigateService) {

    this.http = http;
    this.router = router;
    this.rootNavigateService = rootNavigateService;
    this.localStorage = localStorage;
  }

  botonSolicitudFotocopiaPDF(): void {
    alert(JSON.stringify(this.solicitud, null, 2));
    const usuario: UsuarioResponse = this.localStorage.getItem('userData');
    alert(JSON.stringify(usuario, null, 2));

    //"/exportSolicitudDPF/{idSolicitud}/{idSolicitante}/{idResponsable}"
    const url = 'http://localhost:8081/solicitante/exportSolicitudDPF/' + this.solicitud.id + '/' + usuario.id + '/2';
    alert(JSON.stringify(url, null, 2));

    // Recibimos la peticion
    this.http.get(url, { responseType: 'blob' }).pipe( // Cambiar el tipo de respuesta
      map((response: Blob) => {
        const blob = new Blob([response], { type: 'application/pdf' });
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = 'solicitudPDF.pdf';
        a.click();
        window.URL.revokeObjectURL(url);
      }),
      catchError(error => {
        console.error('Error en la petición:', error);
        alert('Hubo un error al generar la solicitud PDF');
        return of(null);
      })
    ).subscribe();
  }

  botonInformePDF(): void {
    alert("botonInformePDF()")
  }

  botonComunicacionInterna(): void {
    alert("botonComunicacionInterna()");
  }

}
