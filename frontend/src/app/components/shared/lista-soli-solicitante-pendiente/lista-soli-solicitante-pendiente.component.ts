import {Component} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {SolicitudResponse} from '../../../models/SolicitudResponse';
import {BehaviorSubject, catchError, map, of} from 'rxjs';
import {PageRequestID} from '../../../models/PageRequestID';
import {UsuarioResponse} from '../../../models/UsuarioResponse';
import {LocalStorageService} from '../../../services/local-storage/local-storage.service';
import {PageProperties} from "../../../models/PageProperties";
import {UrlsProperties} from "../../../enums/UrlsProperties";
import {UsuarioUnidadEditRequest} from "../../../models/UsuarioUnidadEditRequest";

@Component({
  selector: 'app-lista-soli-solicitante-pendiente',
  standalone: true,
  imports: [],
  templateUrl: './lista-soli-solicitante-pendiente.component.html',
  styleUrl: './lista-soli-solicitante-pendiente.component.css'
})
export class ListaSoliSolicitantePendienteComponent {

  private http: HttpClient;
  private localStorage: LocalStorageService;

  usuario: UsuarioResponse = new UsuarioResponse();

  constructor(
    http: HttpClient,
    localStorage: LocalStorageService) {

    this.http = http;
    this.localStorage = localStorage;
    this.usuario = this.localStorage.getItem('userData');
    this.listarSolicitudes();
  }

  listSolicitud: SolicitudResponse[] = [];
  listarSolicitudes(): void {
    const body: PageRequestID = {
      idUsuarioUnidad: this.usuario.id,
      page: 0,
      size: 10,
      byColumName: ''
    }

    this.http.post<SolicitudResponse[]>(
      UrlsProperties.PATH_LIST_SOLIC,
      body
    ).pipe(
      map((response: SolicitudResponse[]) => {
        this.listSolicitud = response;
      }),
      catchError(error => {
        console.error('Error en la petición:', error);
        alert('Hubo un error al listar las solicitudes de usuario');
        return of(null); // Retornar un observable vacío en caso de error
      })
    ).subscribe();

  }

  eliminarSolicitudById(idSoliciud: number): void {
    let url: string = UrlsProperties.PATH_ELIMINAR_SOLIC + idSoliciud;
    this.http.get(
      url
    ).pipe(
      map(() => {
        window.location.reload();
      }),
      catchError(error => {
        console.error('Error en la petición:', error);
        alert('Hubo un error al listar las solicitudes de usuario');
        return of(null); // Retornar un observable vacío en caso de error
      })
    ).subscribe();
  }

  private subject$ = new BehaviorSubject<number>(0);
  isModalVisible: boolean = false;
  toggleModal(idSolicitud: number): void {
    this.subject$.next(idSolicitud);
    this.isModalVisible = !this.isModalVisible;
  }

  botonSolicitudFotocopiaPDF(): void {
    let idSolicitud: number = 0;
    this.subject$.asObservable().subscribe(x => {
      idSolicitud = x;
    });

    const url = UrlsProperties.PATH_SOLICITUD_PDF + idSolicitud;

    // Recibimos la peticion
    this.http.get(
      url,
      { responseType: 'blob' }
    ).pipe( // Cambiar el tipo de respuesta
      map((response: Blob) => {
        this.descargarPDF('solicitudPDF.pdf', response);
      }),
      catchError(error => {
        this.errorDescargaPDF('solicitudPDF.pdf', error);
        return of(null);
      })
    ).subscribe();
  }

  botonOrdenFotocopiaPDF(): void {
    let idSolicitud: number = 0;
    this.subject$.asObservable().subscribe(x => {
      idSolicitud = x;
    });
    const url = UrlsProperties.PATH_ORDENFOTO_PDF + idSolicitud;

    // Recibimos la peticion
    this.http.get(
      url,
      { responseType: 'blob' }
    ).pipe( // Cambiar el tipo de respuesta
      map((response: Blob) => {
        this.descargarPDF('ordenParaFotocopia.pdf', response);
      }),
      catchError(error => {
        this.errorDescargaPDF('ordenParaFotocopia.pdf', error);
        return of(null);
      })
    ).subscribe();
  }

  botonComunicacionInternaPDF(): void {
    let idSolicitud: number = 0;
    this.subject$.asObservable().subscribe(x => {
      idSolicitud = x;
    });
    const url = UrlsProperties.PATH_COMUINTERNA_PDF + idSolicitud;

    // Recibimos la peticion
    this.http.get(
      url,
      { responseType: 'blob' }
    ).pipe( // Cambiar el tipo de respuesta
      map((response: Blob) => {
        this.descargarPDF('comunicacionInterna.pdf', response);
      }),
      catchError(error => {
        this.errorDescargaPDF('comunicacionInterna.pdf', error)
        return of(null);
      })
    ).subscribe();
  }

  descargarPDF(nombrePdf: string, response: Blob): void {
    const blob = new Blob([response], { type: 'application/pdf' });
    const url = window.URL.createObjectURL(blob);
    const a = document.createElement('a');
    a.href = url;
    a.download = nombrePdf;
    a.click();
    window.URL.revokeObjectURL(url);
  }

  errorDescargaPDF(nombrePdf: string, error: any): void {
    console.error('Error en la petición:', error);
    alert('Hubo un ERROR al generar ' + nombrePdf);
  }




  pageProperties: PageProperties = new PageProperties();
  listaConsecutiva: number[] = Array.from(
    { length: this.pageProperties.totalPages },
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
