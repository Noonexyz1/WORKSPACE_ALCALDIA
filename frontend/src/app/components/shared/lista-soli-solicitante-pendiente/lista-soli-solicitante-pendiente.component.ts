import {Component, OnDestroy, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {SolicitudResponse} from '../../../models/SolicitudResponse';
import {BehaviorSubject, catchError, map, of} from 'rxjs';
import {UsuarioResponse} from '../../../models/UsuarioResponse';
import {LocalStorageService} from '../../../services/local-storage/local-storage.service';
import {PageProperties} from "../../../models/PageProperties";
import {UrlsProperties} from "../../../enums/UrlsProperties";
import {PageRequest} from "../../../models/PageRequest";
import {PageResponse} from "../../../models/PageResponse";

@Component({
  selector: 'app-lista-soli-solicitante-pendiente',
  standalone: true,
  imports: [],
  templateUrl: './lista-soli-solicitante-pendiente.component.html',
  styleUrl: './lista-soli-solicitante-pendiente.component.css'
})
export class ListaSoliSolicitantePendienteComponent {

  usuario: UsuarioResponse = new UsuarioResponse();

  constructor(
    private http: HttpClient,
    private localStorage: LocalStorageService) {

    this.usuario = this.localStorage.getItem('userData');
    this.listarSolicitudes();
  }

  listSolicitud: SolicitudResponse[] = [];

  pageProperties: PageProperties = new PageProperties();
  listaConsecutiva: number[] = Array.from(
    { length: this.pageProperties.totalPages },
    (_, index) => index
  );
  listarSolicitudes(): void {
    const body: PageRequest = {
      id: this.usuario.id,
      page: this.pageProperties.currentPage,
      size: this.pageProperties.pageSize,
      sortBy: this.pageProperties.sortBy,
      direction: this.pageProperties.direction
    }

    this.http.post<PageResponse<SolicitudResponse>>(
      UrlsProperties.PATH_LIST_SOLIC,
      body
    ).pipe(
      map((response: PageResponse<SolicitudResponse>) => {
        this.pageProperties.currentPage = response.page;
        this.pageProperties.pageSize = response.size;
        this.pageProperties.sortBy = response.sortBy;
        this.pageProperties.direction = response.direction;

        this.listSolicitud = response.content;
        this.pageProperties.totalPages = response.totalPages;
        this.pageProperties.totalElements = response.totalElements;

        this.listaConsecutiva = Array.from(
          {length: this.pageProperties.totalPages},
          (_, index) => index
        );
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
  hayInforme: boolean = true;
  toggleModal(idSolicitud: number): void {
    this.subject$.next(idSolicitud);
    this.isModalVisible = !this.isModalVisible;

    const url = UrlsProperties.PATH_IS_INFORME + idSolicitud;
    this.http.get<boolean>(url)
      .subscribe({
        next: (resp: boolean) => {
          this.hayInforme = resp;
        },
        error: error => {
          console.error('Error en la petición:', error);
          alert("Error en la peticion");
        }
      });

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
        this.descargarPDF("solicitud_" + idSolicitud + ".pdf", response);
      }),
      catchError(error => {
        this.errorDescargaPDF("solicitud_" + idSolicitud + ".pdf", error);
        return of(null);
      })
    ).subscribe();
  }

  isModaleInforme: boolean = false;
  botonInformePDF() {

    this.isModaleInforme = !this.isModaleInforme;
    this.isModalVisible = false;

    /*let idSolicitud: number = 0;
    this.subject$.asObservable().subscribe(x => {
      idSolicitud = x;
    });
    const url = UrlsProperties.PATH_INFORSOLI_PDF + idSolicitud;

    // Recibimos la peticion
    this.http.get(
      url,
      { responseType: 'blob' }
    ).pipe( // Cambiar el tipo de respuesta
      map((response: Blob) => {
        this.descargarPDF("informe_" + idSolicitud + ".pdf", response);
      }),
      catchError(error => {
        this.errorDescargaPDF("informe_" + idSolicitud + ".pdf", error);
        return of(null);
      })
    ).subscribe();*/




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
        this.descargarPDF("ordenDeFotocopia_" + idSolicitud + ".pdf", response);
      }),
      catchError(error => {
        this.errorDescargaPDF("ordenDeFotocopia_" + idSolicitud + ".pdf", error);
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
        this.descargarPDF("comunicacion_" + idSolicitud + ".pdf", response);
      }),
      catchError(error => {
        this.errorDescargaPDF("comunicacion_" + idSolicitud + ".pdf", error)
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
