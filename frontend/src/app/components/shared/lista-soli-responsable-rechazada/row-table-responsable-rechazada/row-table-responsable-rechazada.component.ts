import { Component, Input } from '@angular/core';
import { SolicitudResponResponse } from '../../../../models/SolicitudResponResponse';
import {FinalizacionResponse} from "../../../../models/FinalizacionResponse";
import {UsuarioResponse} from "../../../../models/UsuarioResponse";
import {catchError, map, of} from "rxjs";
import {LocalStorageService} from "../../../../services/local-storage/local-storage.service";
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-row-table-responsable-rechazada',
  standalone: true,
  imports: [],
  templateUrl: './row-table-responsable-rechazada.component.html',
  styleUrl: './row-table-responsable-rechazada.component.css'
})
export class RowTableRechazadaResponsableComponent {

  @Input()
  solicitudFinalizada!: FinalizacionResponse;


  private localStorage: LocalStorageService;
  private http: HttpClient;

  constructor(localStorage: LocalStorageService,
              http: HttpClient) {

    this.http = http;
    this.localStorage = localStorage;
  }


  botonDescargoSolicitud(): void {
    alert(JSON.stringify(this.solicitudFinalizada, null, 2));
    const usuario: UsuarioResponse = this.localStorage.getItem('userData');
    alert(JSON.stringify(usuario, null, 2));

    //TODO, revisar el tipo de id que se envia, el solicitud o autorizacion
    //"/exportOrdenParaFotocopiaDPF/{idSolicitud}/{idSolicitante}/{idResponsable}"
    const url = 'http://localhost:8081/responsable/exportReporteDPF/' + this.solicitudFinalizada.idSoliAutorizada + '/' + usuario.id + '/2';
    alert(JSON.stringify(url, null, 2));

    // Recibimos la peticion
    this.http.get(url, { responseType: 'blob' }).pipe( // Cambiar el tipo de respuesta
      map((response: Blob) => {
        const blob = new Blob([response], { type: 'application/pdf' });
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = 'reportePDF.pdf';
        a.click();
        window.URL.revokeObjectURL(url);
      }),
      catchError(error => {
        console.error('Error en la petición:', error);
        alert('Hubo un error al generar el descargo o reporte PDF');
        return of(null);
      })
    ).subscribe();
  }

}
