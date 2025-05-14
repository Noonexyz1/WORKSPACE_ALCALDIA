import {Component} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {SolicitudResponse} from '../../utils/models/SolicitudResponse';
import {catchError, map, of} from 'rxjs';
import {UsuarioResponse} from '../../utils/models/UsuarioResponse';
import {LocalStorageService} from '../../utils/services/local-storage/local-storage.service';
import {PageProperties} from "../../utils/models/PageProperties";
import {UrlsProperties} from "../../utils/enums/UrlsProperties";
import {PageRequest} from "../../utils/models/PageRequest";
import {PageResponse} from "../../utils/models/PageResponse";
import {RegistrarRetiroComponent} from "../../share/registrar-retiro/registrar-retiro.component";
import {SubjectIdSolicitudService} from "../../utils/services/subject-id-solicitud/subject-id-solicitud.service";
import {SubjectDocumentoRetiroResponseService} from "../../utils/services/subject-retiro-documento/subject-documento-retiro-response.service";
import {ObservableService} from "../../utils/services/observable/observable.service";

@Component({
  selector: 'app-lista-soli-solicitante-autorizada',
  standalone: true,
  imports: [
    RegistrarRetiroComponent
  ],
  templateUrl: './lista-soli-solicitante-autorizada.component.html'
})
export class ListaSoliSolicitanteAutorizadaComponent {

  usuario: UsuarioResponse = new UsuarioResponse();

  constructor(
    private http: HttpClient,
    private localStorage: LocalStorageService,
    private subject$: SubjectIdSolicitudService,
    private subject2$: SubjectDocumentoRetiroResponseService,
    private observableBoolean: ObservableService<boolean>) {

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
      UrlsProperties.PATH_AUTORIZ_SOLI,
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

  isModalVisible: boolean = false;
  toggleModal(idSolicitud: number): void {
    this.subject$.publicarDatos(idSolicitud);
    this.isModalVisible = !this.isModalVisible;
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
