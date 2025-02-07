import {Component} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {SolicitudResponse} from '../../../models/SolicitudResponse';
import {catchError, map, of} from 'rxjs';
import {PageRequestID} from '../../../models/PageRequestID';
import {UsuarioResponse} from '../../../models/UsuarioResponse';
import {LocalStorageService} from '../../../services/local-storage/local-storage.service';
import {PageProperties} from "../../../models/PageProperties";
import {UrlsProperties} from "../../../enums/UrlsProperties";

@Component({
  selector: 'app-lista-soli-solicitante-finalizada',
  standalone: true,
  imports: [],
  templateUrl: './lista-soli-solicitante-finalizada.component.html',
  styleUrl: './lista-soli-solicitante-finalizada.component.css'
})
export class ListaSoliSolicitanteFinalizadaComponent {

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
