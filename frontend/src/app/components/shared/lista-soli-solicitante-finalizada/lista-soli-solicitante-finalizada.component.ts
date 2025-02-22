import {Component} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {SolicitudResponse} from '../../../models/SolicitudResponse';
import {catchError, map, of} from 'rxjs';
import {PageRequestID} from '../../../models/PageRequestID';
import {UsuarioResponse} from '../../../models/UsuarioResponse';
import {LocalStorageService} from '../../../services/local-storage/local-storage.service';
import {PageProperties} from "../../../models/PageProperties";
import {UrlsProperties} from "../../../enums/UrlsProperties";
import {PageRequest} from "../../../models/PageRequest";
import {PageResponse} from "../../../models/PageResponse";

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
      UrlsProperties.PATH_FINALIZADAS_SOLI,
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
