import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { catchError, map, of } from 'rxjs';
import { PageRequestID } from '../../../models/PageRequestID';
import { UsuarioResponse } from '../../../models/UsuarioResponse';
import { SolicitudResponResponse } from '../../../models/SolicitudResponResponse';
import { LocalStorageService } from '../../../services/local-storage/local-storage.service';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule} from "@angular/forms";
import {AutorizacionResponse} from "../../../models/AutorizacionResponse";
import {RootNavigateService} from "../../../services/root-navigate/root-navigate.service";
import {PageProperties} from "../../../models/PageProperties";
import {UrlsProperties} from "../../../enums/UrlsProperties";

@Component({
  selector: 'app-lista-soli-responsable-autorizada',
  standalone: true,
  imports: [FormsModule, ReactiveFormsModule],
  templateUrl: './lista-soli-responsable-autorizada.component.html',
  styleUrl: './lista-soli-responsable-autorizada.component.css'
})
export class ListaSoliAutorizadaResponsableComponent {

  private http: HttpClient;
  private localStorage: LocalStorageService;

  listSolicitud: SolicitudResponResponse[] = [];

  usuario: UsuarioResponse = new UsuarioResponse();

  constructor(http: HttpClient,
              rootNavigateService: RootNavigateService,
              formBuilder: FormBuilder,
              localStorage: LocalStorageService){

    this.http = http;
    this.localStorage = localStorage;
    this.rootNavigateService = rootNavigateService;
    this.formBuilder = formBuilder;
    this.formGroup = this.formBuilder.group({
      totalEjecutado: [],
      totalEjecutadoBs: [],
    });
    this.usuario = this.localStorage.getItem('userData');
    this.listarSolicitudes();
  }

  estadoModal = false;
  isModalVisible: boolean = false;
  autorizacion: AutorizacionResponse | null = null;

  botonTraerDatosModal(idSolicitud: number): void {
    this.estadoModal = true;
    this.isModalVisible = !this.isModalVisible;

    // Recibimos la peticion
    this.http.get<AutorizacionResponse>(
      UrlsProperties.PATH_AUTORI_SOLI + idSolicitud
    ).pipe(
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

  toggleModal(): void {
    this.isModalVisible = !this.isModalVisible;
  }

  formGroup: FormGroup;
  private formBuilder: FormBuilder;
  private rootNavigateService: RootNavigateService;

  botonFinalizarSolicitud(solicitud: SolicitudResponResponse): void {
    this.http.post<number>(
      UrlsProperties.PATH_FINALIZAR_SOLI,
      solicitud.idAutorizacion
    ).pipe(
      map(() => {
        this.rootNavigateService.valorParaNavegar('ResponsableFinalizadas');
      }),
      catchError(error => {
        console.error('Error en la petición:', error);
        alert('Hubo un error al guardar la Finalizacion');
        return of(null); // Retornar un observable vacío en caso de error
      })
    ).subscribe();

  }

  botonNotaDeSolicitud(idSolicitud: number): void {
    const usuario: UsuarioResponse = this.localStorage.getItem('userData');
    //"/exportOrdenParaFotocopiaDPF/{idSolicitud}/{idSolicitante}/{idResponsable}"
    const url = 'http://localhost:8081/responsable/exportNotaPedidoDPF/' + idSolicitud + '/' + usuario.id + '/2';

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

  listarSolicitudes(): void {
    const url = 'http://localhost:8081/responsable/verSolicitudesAprobadas';

    this.usuario = this.localStorage.getItem('userData');

    const body: PageRequestID = {
      idUsuarioUnidad: this.usuario.id,
      page: 0,
      size: 100,
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

  pageProperties: PageProperties = new PageProperties();
  listaConsecutiva: number[] = Array.from(
    { length: this.pageProperties.totalPages},
    (_, index) => index
  );

  goToPage(page: number): void {
    if (page >= 0 && page < this.pageProperties.totalPages) {
      this.pageProperties.currentPage = page;
      this.listarSolicitudes();
    }
  }

  goToPreviousPage(): void {
    if (this.pageProperties.currentPage > 0) {
      this.pageProperties.currentPage--;
      this.listarSolicitudes();
    }
  }

  goToNextPage(): void {
    if (this.pageProperties.currentPage < this.pageProperties.totalPages - 1) {
      this.pageProperties.currentPage++;
      this.listarSolicitudes();
    }
  }

}
