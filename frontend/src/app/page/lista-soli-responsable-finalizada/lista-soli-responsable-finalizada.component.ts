import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { catchError, map, of } from 'rxjs';
import { UsuarioResponse } from '../../utils/models/UsuarioResponse';
import { LocalStorageService } from '../../utils/services/local-storage/local-storage.service';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {UrlsProperties} from "../../utils/enums/UrlsProperties";
import {SolicitudResponResponse} from "../../utils/models/SolicitudResponResponse";
import {PageRequest} from "../../utils/models/PageRequest";
import {PageResponse} from "../../utils/models/PageResponse";
import {
  ModalSolicitudData,
  ModalSolicitudInfoComponent
} from "../../share/modal-solicitud-info/modal-solicitud-info.component";
import {
  ItemPopover,
  PaginaData,
  TablaResponsableComponent
} from "../../share/tabla-responsable/tabla-responsable.component";
import {DomSanitizer, SafeHtml} from "@angular/platform-browser";
import {iconoDocumento, iconoOjo} from "../../utils/icons/IconsSVG";
import {DetalleSolicitudExtendidoResponse} from "../../utils/models/DetalleSolicitudExtendidoResponse";
import {ModelMapperService} from "../../utils/mapper/model-mapper.service";

@Component({
  selector: 'app-lista-soli-responsable-finalizada',
  standalone: true,
  imports: [FormsModule, ReactiveFormsModule, ModalSolicitudInfoComponent, TablaResponsableComponent],
  templateUrl: './lista-soli-responsable-finalizada.component.html'
})
export class ListaSoliFinalizadaResponsableComponent{

  //DATOS PARA EL COMPONENTE TABLA
  tituloDeTabla: string = "Lista de solicitudes finalizadas";
  listTituloTabla: string[] = ["Accion", "Id", "Cite", "Fecha", "Autor", "Cargo", "Unidad"];
  //Inicializamos por defecto este atributo para que se cambien a lo largo de la vida del componente
  pagina: PaginaData = {
    page: 0,
    size: 10,
    sortBy: 'id',
    direction: "ASC",
    content: [],
    totalPages: 0,
    totalElements: 0
  };
  listPopover: ItemPopover[] = [];


  //DATOS PARA EL COMPONENTE MODAL
  solicitudDetalle: ModalSolicitudData = {
    tituloModal: '',
    datosSolicitud: {
      idSolicitud: 0,
      cite: '',
      fecha: '',
      descripcion: '',
      nombreServicio: '',
      precioTotal: 0,
      detalleSolicitudResponses: [],
    },
  }


  isModalClose: boolean = false;
  usuario: UsuarioResponse = new UsuarioResponse();


  constructor(
    private readonly http: HttpClient,
    private readonly localStorage: LocalStorageService,
    private readonly sanitizer: DomSanitizer,
    private readonly modelMapper: ModelMapperService) {

    this.usuario = this.localStorage.getItem('userData');
    this.listarSolicitudes();

    //Inciamos los valores
    this.listPopover = [
      {
        icono: this.getSafeSvg(iconoDocumento),
        opcion: 'Generar nota de pedidos',
        //Las firmas son iguales jaja se puede enviar directamente el metodo pero lo voy a dejar asi
        accion: (idSolicitud: string) => {this.botonNotaDeSolicitud(+idSolicitud)}
      },
      {
        icono: this.getSafeSvg(iconoOjo),
        opcion: 'Ver detalles completos',
        //Las firmas son iguales jaja se puede enviar directamente el metodo pero lo voy a dejar asi
        accion: (idSolicitud: string) => {this.botonTraerDatosModal(+idSolicitud)}
      },
    ];

  }


  private getSafeSvg(svg: string): SafeHtml {
    return this.sanitizer.bypassSecurityTrustHtml(svg);
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
      UrlsProperties.PATH_LIST_SOLIFINALI,
      body
    ).pipe(
      map((response: PageResponse<SolicitudResponResponse>) => {
        this.pagina = this.modelMapper
          .pageResponseToPaginaDataResponsable(response, this.listPopover);
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
      UrlsProperties.PATH_SOLI_FINALIBYID,
      body
    ).pipe(
      map((response: PageResponse<SolicitudResponResponse>) => {
        this.pagina = this.modelMapper
          .pageResponseToPaginaDataResponsable(response, this.listPopover);
      }),
      catchError(error => {
        console.error('Error en la petición:', error);
        alert('Hubo un error al listar las solicitudes finalizadas para el responsable');
        return of(null); // Retornar un observable vacío en caso de error
      })
    ).subscribe();

  }


  // Estos son metodos que seran disparados en cuanto se recibe un evento
  botonNotaDeSolicitud(idSolicitud: number): void {
    this.http.get(
      UrlsProperties.PATH_NOTA_PDF + idSolicitud,
      { responseType: 'blob' }
    ).pipe(
      map((response: Blob) => {
        this.descargarPDF("notaPedido_" + idSolicitud + ".pdf", response);

        // Habilita el botón "Finalizar" solo para la fila correspondiente
        /*const solicitud = this.pagina.content.find(s => s.idSolicitud === idSolicitud);
        if (solicitud) {
          solicitud.isActiveBtnFinalizar = true;
        }*/

      }),
      catchError(error => {
        this.errorDescargaPDF("notaPedido_" + idSolicitud + ".pdf", error);
        return of(null);
      })
    ).subscribe();
  }


  botonTraerDatosModal(idSolicitud: number): void {
    this.isModalClose = true;

    this.http.get<DetalleSolicitudExtendidoResponse>(
      UrlsProperties.PATH_DETALLE_SOLI + idSolicitud
    ).pipe(
      map((response: DetalleSolicitudExtendidoResponse) => {
        //Aqui armamos el objeto ModalSolicitudData
        this.solicitudDetalle = {
          tituloModal:"Detalle de solicitud",
          datosSolicitud: {
            idSolicitud: response.idSolicitud,
            cite: response.cite,
            fecha: response.fecha,
            descripcion: response.descripcion,
            nombreServicio: response.nombreServicio,
            precioTotal: response.precioTotal,
            detalleSolicitudResponses: response.detalleSolicitudResponses,
          }
        }
      }),
      catchError(error => {
        console.error('Error en la petición:', error);
        alert('Hubo un error al listar los datos para el modal');
        return of(null); // Retornar un observable vacío en caso de error
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



  toggleModal(isActive: boolean) {
    this.isModalClose = isActive;
  }
}
