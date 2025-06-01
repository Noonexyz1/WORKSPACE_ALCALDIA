import {Component} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {catchError, map, of} from 'rxjs';
import {UsuarioResponse} from '../../utils/models/UsuarioResponse';
import {SolicitudResponResponse} from '../../utils/models/SolicitudResponResponse';
import {LocalStorageService} from '../../utils/services/local-storage/local-storage.service';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {DetalleSolicitudExtendidoResponse} from "../../utils/models/DetalleSolicitudExtendidoResponse";
import {UrlsProperties} from "../../utils/enums/UrlsProperties";
import {PageRequest} from "../../utils/models/PageRequest";
import {PageResponse} from "../../utils/models/PageResponse";
import {
  ItemPopover,
  PaginaData, RowIntemData,
  TablaResponsableComponent
} from "../../share/tabla-responsable/tabla-responsable.component";
import {
  ModalSolicitudData,
  ModalSolicitudInfoComponent
} from "../../share/modal-solicitud-info/modal-solicitud-info.component";
import {DomSanitizer, SafeHtml} from "@angular/platform-browser";
import {iconoOjo} from "../../utils/icons/IconsSVG";
import {AprobacionSoliRequest} from "../../utils/models/AutorizacionRequest";
import {ModelMapperService} from "../../utils/mapper/model-mapper.service";

@Component({
  selector: 'app-lista-soli-responsable-pendiente',
  standalone: true,
  imports: [FormsModule, ReactiveFormsModule, TablaResponsableComponent, ModalSolicitudInfoComponent],
  templateUrl: './lista-soli-responsable-pendiente.component.html'
})
export class ListaSoliPendienteResponsableComponent {

  //DATOS PARA EL COMPONENTE TABLA
  tituloDeTabla: string = "Lista de solicitudes pendientes";
  listTituloTabla: string[] = ["Accion", "Id", "Cite", "Fecha", "Autor", "Cargo", "Unidad"];
  //Inicializamos por defecto este atributo para que se cambien a lo largo de la vida del componente
  pagina: PaginaData = {
    page: 0,
    size: 10, // Valor por defecto más lógico
    sortBy: "id", // Campo por defecto común
    direction: "ASC", // Dirección por defecto
    content: [],
    totalPages: 1,
    totalElements: 1
  };
  itemPopoverList: ItemPopover[] = [];


  //DATOS PARA EL COMPONENTE MODAL
  isBotonesActivos: boolean = true;
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
    //debo crear un arrego de ItemPopover para luego insertarlo a la pagina e enviarlo al hijo
    this.itemPopoverList = [
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
      UrlsProperties.PATH_LIST_SOLIPENDIENTE,
      body
    ).pipe(
      map((response: PageResponse<SolicitudResponResponse>) => {

        this.pagina = this.modelMapper
          .pageResponseToPaginaDataResponsable(response, this.itemPopoverList);

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
      UrlsProperties.PATH_SOLI_BYID,
      body
    ).pipe(
      map((response: PageResponse<SolicitudResponResponse>) => {

        this.pagina = this.modelMapper
          .pageResponseToPaginaDataResponsable(response, this.itemPopoverList);

      }),
      catchError(error => {
        console.error('Error en la petición:', error);
        alert('Hubo un error al listar las solicitudes autorizadas para el responsable');
        return of(null); // Retornar un observable vacío en caso de error
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


  botonAutorizarSolicitud(idSolicitud: number) {
    console.log("Id de Solicitud a autorizar/rechazar: " + idSolicitud)

    this.http.post<AprobacionSoliRequest>(
      UrlsProperties.PATH_AUTORIZAR_SOLI,
      {
        idResponsable: this.usuario.id,
        idSolicitud: idSolicitud
      }
    ).pipe(
      map(() => {
        this.isModalClose = !this.isModalClose;
        this.listarSolicitudes();
      }),
      catchError(error => {
        console.error('Error en la petición:', error);
        alert('Hubo un error al enviar las solicitudes cotizadas');
        return of(null); // Retornar un observable vacío en caso de error
      })
    ).subscribe();
  }

  botonRechazarSolicitud(idSolicitud: number) {
    console.log("Id de Solicitud a autorizar/rechazar: " + idSolicitud)

    this.http.post<AprobacionSoliRequest>(
      UrlsProperties.PATH_RECHAZAR_SOLI,
      {
        idResponsable: this.usuario.id,
        idSolicitud: idSolicitud
      }
    ).pipe(
      map(() => {
        this.isModalClose = !this.isModalClose;
        this.listarSolicitudes();
      }),
      catchError(error => {
        console.error('Error en la petición:', error);
        alert('Hubo un error al rechazar las solicitudes cotizadas');
        return of(null); // Retornar un observable vacío en caso de error
      })
    ).subscribe();
  }

  botonTrashPublisher() {
    this.listarSolicitudes();
  }
}
