import {Component} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {TablaResponsableComponent} from "../../share/tabla-responsable/tabla-responsable.component";
import {PageRequest} from "../../utils/models/PageRequest";
import {PageResponse} from "../../utils/models/PageResponse";
import {SolicitudResponResponse} from "../../utils/models/SolicitudResponResponse";
import {UrlsProperties} from "../../utils/enums/UrlsProperties";
import {catchError, map, of} from "rxjs";
import {UsuarioResponse} from "../../utils/models/UsuarioResponse";
import {LocalStorageService} from "../../utils/services/local-storage/local-storage.service";
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-lista-soli-responsable-autorizada',
  standalone: true,
  imports: [FormsModule, ReactiveFormsModule, TablaResponsableComponent],
  templateUrl: './lista-soli-responsable-autorizada.component.html'
})
export class ListaSoliAutorizadaResponsableComponent {

  tituloDeTabla: string = "Lista de solicitudes autorizadas";
  listTituloTabla: string[] = ["Accion", "Id", "Cite", "Fecha", "Autor", "Cargo", "Unidad"];

  usuario: UsuarioResponse = new UsuarioResponse();

  //Inicializamos por defecto este atributo para que se cambien a lo largo de la vida del componente
  pagina: PageResponse<SolicitudResponResponse> = {
    page: 0,
    size: 10,
    sortBy: 'id',
    direction: "ASC",
    content: [],
    totalPages: 0,
    totalElements: 0
  };


  constructor(
    private readonly http: HttpClient,
    private readonly localStorage: LocalStorageService) {

    this.usuario = this.localStorage.getItem('userData');
    this.listarSolicitudes();
  }


  private listarSolicitudes(): void {
    const body: PageRequest = {
      id: this.usuario.id,
      page: this.pagina.page,
      size: this.pagina.size,
      sortBy: this.pagina.sortBy,
      direction: this.pagina.direction
    };

    this.http.post<PageResponse<SolicitudResponResponse>>(
      UrlsProperties.PATH_LIST_SOLIAPRO,
      body
    ).pipe(
      map((response: PageResponse<SolicitudResponResponse>) => {
        this.pagina = response;
      }),
      catchError(error => {
        console.error('Error en la petición:', error);
        alert('Hubo un error al listar las solicitudes para el responsable');
        return of(null); // Retornar un observable vacío en caso de error
      })
    ).subscribe();
  }


  // Estos son metodos que seran disparados en cuanto se recibe un evento
  botonBuscarSolicitudById(id: number): void {
    //Con este id emitido hacer tal...
    const body: PageRequest = {
      id: id,
      page: this.pagina.page,
      size: this.pagina.size,
      sortBy: this.pagina.sortBy,
      direction: this.pagina.direction
    }

    this.http.post<PageResponse<SolicitudResponResponse>>(
      UrlsProperties.PATH_SOLI_AUTORIBYID,
      body
    ).pipe(
      map((response: PageResponse<SolicitudResponResponse>) => {
        this.pagina = response;
      }),
      catchError(error => {
        console.error('Error en la petición:', error);
        alert('Hubo un error al listar las solicitudes pendientes para el responsable');
        return of(null); // Retornar un observable vacío en caso de error
      })
    ).subscribe();

  }


  // Estos son metodos que seran disparados en cuanto se recibe un evento
  botonNotaDeSolicitud(idSolicitud: number | undefined): void {
    this.http.get(
      UrlsProperties.PATH_NOTA_PDF + idSolicitud,
      { responseType: 'blob' }
    ).pipe(
      map((response: Blob) => {
        this.descargarPDF("notaPedido_" + idSolicitud + ".pdf", response);

        // Habilita el botón "Finalizar" solo para la fila correspondiente
        const solicitud = this.pagina.content.find(s => s.idSolicitud === idSolicitud);
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

  private descargarPDF(nombrePdf: string, response: Blob): void {
    const blob = new Blob([response], { type: 'application/pdf' });
    const url = window.URL.createObjectURL(blob);
    const a = document.createElement('a');
    a.href = url;
    a.download = nombrePdf;
    a.click();
    window.URL.revokeObjectURL(url);
  }

  private errorDescargaPDF(nombrePdf: string, error: any): void {
    console.error('Error en la petición:', error);
    alert('Hubo un ERROR al generar ' + nombrePdf);
  }


  // Estos son metodos que seran disparados en cuanto se recibe un evento
  goToPage(page: number): void {
    this.pagina.page = page;
    this.listarSolicitudes();
  }

  // Estos son metodos que seran disparados en cuanto se recibe un evento
  goToPreviousPage(newPage: number): void {
    this.pagina.page = newPage;
    this.listarSolicitudes();
  }

  // Estos son metodos que seran disparados en cuanto se recibe un evento
  goToNextPage(newPage: number): void {
    this.pagina.page = newPage;
    this.listarSolicitudes();
  }

}
