import {Component} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {catchError, map, of} from 'rxjs';
import {PageRequestID} from '../../../models/PageRequestID';
import {UsuarioResponse} from '../../../models/UsuarioResponse';
import {SolicitudResponResponse} from '../../../models/SolicitudResponResponse';
import {LocalStorageService} from '../../../services/local-storage/local-storage.service';
import {FormsModule} from "@angular/forms";
import {RowSolicitudExtendComponent} from "./row-table-responsable-pendiente/row-solicitud-extend/row-solicitud-extend.component";
import {PageProperties} from "../../../models/PageProperties";
import {DetalleSolicitudExtendidoResponse} from "../../../models/DetalleSolicitudExtendidoResponse";
import {DetalleSolicitudCotizadoResponse} from "../../../models/DetalleSolicitudCotizadoResponse";
import {RootNavigateService} from "../../../services/root-navigate/root-navigate.service";
import {UrlsProperties} from "../../../enums/UrlsProperties";
import {AprobacionSoliRequest} from "../../../models/AutorizacionRequest";

@Component({
  selector: 'app-lista-soli-responsable-pendiente',
  standalone: true,
  imports: [FormsModule, RowSolicitudExtendComponent],
  templateUrl: './lista-soli-responsable-pendiente.component.html',
  styleUrl: './lista-soli-responsable-pendiente.component.css'
})
export class ListaSoliPendienteResponsableComponent{

  private http: HttpClient;
  private localStorage: LocalStorageService;
  private rootNavigateService: RootNavigateService;

  listSolicitud: SolicitudResponResponse[] = [];

  pageProperties: PageProperties = new PageProperties();
  listaConsecutiva: number[] = Array.from(
    { length: this.pageProperties.totalPages },
    (_, index) => index
  );

  usuario: UsuarioResponse = new UsuarioResponse();

  constructor(http: HttpClient,
              rootNavigateService: RootNavigateService,
              localStorage: LocalStorageService) {

    this.http = http;
    this.localStorage = localStorage;
    this.rootNavigateService = rootNavigateService;
    this.usuario = this.localStorage.getItem('userData');
    this.listarSolicitudes();
  }

  listarSolicitudes(): void {
    const body: PageRequestID = {
      idUsuarioUnidad: this.usuario.id,
      page: 0,
      size: 100,
      byColumName: ""
    }

    this.http.post<SolicitudResponResponse[]>(
      UrlsProperties.PATH_LIST_SOLIPENDIENTE,
      body
    ).pipe(
      map((response: SolicitudResponResponse[]) => {
        this.listSolicitud = response;
      }),
      catchError(error => {
        console.error('Error en la petición:', error);
        alert('Hubo un error al listar las solicitudes pendientes para el responsable');
        return of(null); // Retornar un observable vacío en caso de error
      })
    ).subscribe();
  }

  estadoModal: boolean = false;
  isModalVisible: boolean = false;
  detSoliExtendidoResponse: DetalleSolicitudExtendidoResponse = {
    idSolicitud: 0,
    cite: '',
    fecha: '',
    descripcion: '',
    nombreServicio: '',
    precioTotal: 0,
    detalleSolicitudResponses: [],
  };

  botonTraerDatosModal(idSolicitud: number): void {
    this.estadoModal = true;
    this.isModalVisible = !this.isModalVisible;

    this.http.get<DetalleSolicitudExtendidoResponse>(
      UrlsProperties.PATH_DETALLE_SOLI + idSolicitud
    ).pipe(
      map((response: DetalleSolicitudExtendidoResponse) => {
        this.detSoliExtendidoResponse = response;
      }),
      catchError(error => {
        console.error('Error en la petición:', error);
        alert('Hubo un error al listar los datos para el modal');
        return of(null); // Retornar un observable vacío en caso de error
      })
    ).subscribe();
  }

  toggleModal(): void {
    this.isModalVisible = !this.isModalVisible;
  }

  botonAutorizar(): void {
    this.http.post<AprobacionSoliRequest>(
      UrlsProperties.PATH_AUTORIZAR_SOLI,
      {
        idResponsable: this.usuario.id,
        idSolicitud: this.detSoliExtendidoResponse.idSolicitud
      }
    ).pipe(
      map(() => {
        this.rootNavigateService.valorParaNavegar('ResponsableAutorizadas');
      }),
      catchError(error => {
        console.error('Error en la petición:', error);
        alert('Hubo un error al enviar las solicitudes cotizadas');
        return of(null); // Retornar un observable vacío en caso de error
      })
    ).subscribe();
  }

  botonRechazar(): void {
    this.http.post<AprobacionSoliRequest>(
      UrlsProperties.PATH_RECHAZAR_SOLI,
      {
        idResponsable: this.usuario.id,
        idSolicitud: this.detSoliExtendidoResponse.idSolicitud
      }
    ).pipe(
      map(() => {
        this.toggleModal();
        this.listarSolicitudes();
      }),
      catchError(error => {
        console.error('Error en la petición:', error);
        alert('Hubo un error al rechazar las solicitudes cotizadas');
        return of(null); // Retornar un observable vacío en caso de error
      })
    ).subscribe();
  }

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
