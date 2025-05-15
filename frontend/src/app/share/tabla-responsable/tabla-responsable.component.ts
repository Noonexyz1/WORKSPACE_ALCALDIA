import {Component, HostListener} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {SolicitudResponResponse} from "../../utils/models/SolicitudResponResponse";
import {PageProperties} from "../../utils/models/PageProperties";
import {PageRequest} from "../../utils/models/PageRequest";
import {PageResponse} from "../../utils/models/PageResponse";
import {UrlsProperties} from "../../utils/enums/UrlsProperties";
import {catchError, map, of} from "rxjs";
import {UsuarioResponse} from "../../utils/models/UsuarioResponse";
import {HttpClient} from "@angular/common/http";
import {numeroMayorACeroValidator} from "../../utils/extra/Validation";
import {LocalStorageService} from "../../utils/services/local-storage/local-storage.service";

@Component({
  selector: 'app-tabla-responsable',
  standalone: true,
  imports: [
    ReactiveFormsModule
  ],
  templateUrl: './tabla-responsable.component.html'
})
export class TablaResponsableComponent {

  idSolicitudForm: FormGroup;
  usuario: UsuarioResponse = new UsuarioResponse();
  listSolicitud: SolicitudResponResponse[] = [];
  pageProperties: PageProperties = new PageProperties();
  listaConsecutiva: number[] = Array.from(
    { length: this.pageProperties.totalPages},
    (_, index) => index
  );

  constructor(
    private readonly http: HttpClient,
    private readonly localStorage: LocalStorageService,
    private readonly formBuilder: FormBuilder) {

    this.formBuilder = formBuilder;
    this.idSolicitudForm = this.formBuilder.group({
      id: ['', [Validators.required, numeroMayorACeroValidator]]
    });
    this.usuario = this.localStorage.getItem('userData');
    this.listarSolicitudes();
  }

  @HostListener('document:click', ['$event'])
  onDocumentClick(event: MouseEvent) {
    // Cierra el popover si se hace clic en cualquier parte fuera de él
    if (this.showPopoverId !== null) {
      this.showPopoverId = null;
    }
  }

  listarSolicitudes(): void {
    const body: PageRequest = {
      id: this.usuario.id,
      page: this.pageProperties.currentPage,
      size: this.pageProperties.pageSize,
      sortBy: this.pageProperties.sortBy,
      direction: this.pageProperties.direction
    };

    this.http.post<PageResponse<SolicitudResponResponse>>(
      UrlsProperties.PATH_LIST_SOLIAPRO,
      body
    ).pipe(
      map((response: PageResponse<SolicitudResponResponse>) => {
        this.pageProperties.currentPage = response.page;
        this.pageProperties.pageSize = response.size;
        this.pageProperties.sortBy = response.sortBy;
        this.pageProperties.direction = response.direction;

        // Inicializa la propiedad isActiveBtnFinalizar para cada solicitud
        this.listSolicitud = response.content.map(solicitud => ({
          ...solicitud,
          isActiveBtnFinalizar: false // Inicializa en false
        }));

        this.pageProperties.totalPages = response.totalPages;
        this.pageProperties.totalElements = response.totalElements;

        this.listaConsecutiva = Array.from(
          { length: this.pageProperties.totalPages },
          (_, index) => index
        );
      }),
      catchError(error => {
        console.error('Error en la petición:', error);
        alert('Hubo un error al listar las solicitudes para el responsable');
        return of(null); // Retornar un observable vacío en caso de error
      })
    ).subscribe();
  }

  botonBuscarSolicitudById(): void {
    const body: PageRequest = {
      id: this.idSolicitudForm.get('id')?.value,
      page: this.pageProperties.currentPage,
      size: this.pageProperties.pageSize,
      sortBy: this.pageProperties.sortBy,
      direction: this.pageProperties.direction
    }

    this.http.post<PageResponse<SolicitudResponResponse>>(
      UrlsProperties.PATH_SOLI_AUTORIBYID,
      body
    ).pipe(
      map((response: PageResponse<SolicitudResponResponse>) => {
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
        alert('Hubo un error al listar las solicitudes pendientes para el responsable');
        return of(null); // Retornar un observable vacío en caso de error
      })
    ).subscribe();

  }

  showPopoverId: null | number | undefined = null;
  togglePopover(solicitudId: number | undefined, event?: MouseEvent): void {
    if (event) {
      event.stopPropagation();
    }
    this.showPopoverId = this.showPopoverId === solicitudId ? null : solicitudId;
    console.log(solicitudId);
  }

  // Métoodo para manejar el clic en "Nota de Pedido"
  botonNotaDeSolicitud(idSolicitud: number | undefined): void {
    this.http.get(
      UrlsProperties.PATH_NOTA_PDF + idSolicitud,
      { responseType: 'blob' }
    ).pipe(
      map((response: Blob) => {
        this.descargarPDF("notaPedido_" + idSolicitud + ".pdf", response);

        // Habilita el botón "Finalizar" solo para la fila correspondiente
        const solicitud = this.listSolicitud.find(s => s.idSolicitud === idSolicitud);
        if (solicitud) {
          solicitud.isActiveBtnFinalizar = true;
        }
      }),
      catchError(error => {
        this.errorDescargaPDF("notaPedido_" + idSolicitud + ".pdf", error);
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
