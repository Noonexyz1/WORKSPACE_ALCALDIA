import { Component, Input } from '@angular/core';
import { SolicitudResponResponse } from '../../../../models/SolicitudResponResponse';
import {CredencialRequest} from "../../../../models/CredencialRequest";
import {UsuarioResponse} from "../../../../models/UsuarioResponse";
import {catchError, map, of} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {AutorizacionResponse} from "../../../../models/AutorizacionResponse";
import {FormBuilder, FormGroup, ReactiveFormsModule} from "@angular/forms";
import {FinalizacionRequest} from "../../../../models/FinalizacionRequest";
import {RootNavigateService} from "../../../../services/root-navigate/root-navigate.service";
import {Router} from "@angular/router";
import {LocalStorageService} from "../../../../services/local-storage/local-storage.service";

@Component({
  selector: 'app-row-table-responsable-aprobada',
  standalone: true,
  imports: [
    ReactiveFormsModule
  ],
  templateUrl: './row-table-responsable-aprobada.component.html',
  styleUrl: './row-table-responsable-aprobada.component.css'
})
export class RowTableAprobadaResponsableComponent {

  @Input()
  solicitud!: SolicitudResponResponse;

  estadoModal: boolean = true;

  private localStorage: LocalStorageService;
  private http: HttpClient;
  private formBuilder: FormBuilder;
  private router: Router;
  formGroup: FormGroup;

  private rootNavigateService: RootNavigateService;

  autorizacion: AutorizacionResponse | null = null;

  constructor(http: HttpClient,
              formBuilder: FormBuilder,
              router: Router,
              rootNavigateService: RootNavigateService,
              localStorage: LocalStorageService) {

    this.http = http;
    this.localStorage = localStorage;
    this.router = router;
    this.formBuilder = formBuilder;
    this.rootNavigateService = rootNavigateService;
    this.formGroup = this.formBuilder.group({
      totalEjecutado: [],
      totalEjecutadoBs: [],
    });
  }

  isModalVisible: boolean = false;
  toggleModal(): void {
    this.isModalVisible = !this.isModalVisible;
  }

  botonNotaDeSolicitud(): void {
    const usuario: UsuarioResponse = this.localStorage.getItem('userData');
    //"/exportOrdenParaFotocopiaDPF/{idSolicitud}/{idSolicitante}/{idResponsable}"
    const url = 'http://localhost:8081/responsable/exportNotaPedidoDPF/' + this.solicitud.idSolicitud + '/' + usuario.id + '/2';

    // Recibimos la peticion
    this.http.get(url, { responseType: 'blob' }).pipe( // Cambiar el tipo de respuesta
      map((response: Blob) => {
        const blob = new Blob([response], { type: 'application/pdf' });
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = 'notaPedidoPDF.pdf';
        a.click();
        window.URL.revokeObjectURL(url);
      }),
      catchError(error => {
        console.error('Error en la petición:', error);
        alert('Hubo un error al generar la orden de fotocopia PDF');
        return of(null);
      })
    ).subscribe();
  }

  botonTraerDatosModal(): void {
    this.isModalVisible = !this.isModalVisible;
    const url = 'http://localhost:8081/responsable/verAutorizacionSolicitud/' + this.solicitud.idAutorizacion;

    // Recibimos la peticion
    this.http.get<AutorizacionResponse>(url).pipe(
      map((autorizacion: AutorizacionResponse) => {
        this.autorizacion = autorizacion;
      }),
      catchError(error => {
        console.error('Error en la petición:', error);
        alert('Hubo un error al traer la solicitud autorizada');
        return of(null); // Retornar un observable vacío en caso de error
      })
    ).subscribe();
  }

  botonFinalizarSolicitud(): void {
    const url = 'http://localhost:8081/responsable/finalizarSolicitud'; // URL de tu API

    // Extraer los valores del formulario
    const finalizacionRequest: FinalizacionRequest = {
      idAutorizacion: this.solicitud.idAutorizacion,
      totalEjecutado: this.formGroup.get('totalEjecutado')?.value, // Obtener el valor de correo
      totalEjecutadoBs: this.formGroup.get('totalEjecutadoBs')?.value // Obtener el valor de correo
    };

    // Recibimos la peticion
    this.http.post<FinalizacionRequest>(url, finalizacionRequest).pipe(
      map(() => {
        let toNavegate = this.rootNavigateService.valorParaNavegar('Responsable');
        this.router.navigate([toNavegate]);
      }),
      catchError(error => {
        console.error('Error en la petición:', error);
        alert('Hubo un error al guardar la Finalizacion');
        return of(null); // Retornar un observable vacío en caso de error
      })
    ).subscribe();

  }

}
