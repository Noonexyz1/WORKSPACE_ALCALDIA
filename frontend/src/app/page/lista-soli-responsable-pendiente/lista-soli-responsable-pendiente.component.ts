import {ChangeDetectorRef, Component, OnDestroy, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {catchError, map, of, Subscription} from 'rxjs';
import {UsuarioResponse} from '../../utils/models/UsuarioResponse';
import {SolicitudResponResponse} from '../../utils/models/SolicitudResponResponse';
import {LocalStorageService} from '../../utils/services/local-storage/local-storage.service';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {RowSolicitudExtendComponent} from "./row-solicitud-extend/row-solicitud-extend.component";
import {PageProperties} from "../../utils/models/PageProperties";
import {DetalleSolicitudExtendidoResponse} from "../../utils/models/DetalleSolicitudExtendidoResponse";
import {RootNavigateService} from "../../utils/services/root-navigate/root-navigate.service";
import {UrlsProperties} from "../../utils/enums/UrlsProperties";
import {AprobacionSoliRequest} from "../../utils/models/AutorizacionRequest";
import {PageRequest} from "../../utils/models/PageRequest";
import {PageResponse} from "../../utils/models/PageResponse";
import {numeroMayorACeroValidator} from "../../utils/extra/Validation";
import {ObservableNotifyService} from "../../utils/services/subject-notify/observable-notify.service";

@Component({
  selector: 'app-lista-soli-responsable-pendiente',
  standalone: true,
  imports: [FormsModule, RowSolicitudExtendComponent, ReactiveFormsModule],
  templateUrl: './lista-soli-responsable-pendiente.component.html'
})
export class ListaSoliPendienteResponsableComponent implements OnInit, OnDestroy{

  listSolicitud: SolicitudResponResponse[] = [];

  idSolicitudForm: FormGroup;

  pageProperties: PageProperties = new PageProperties();
  listaConsecutiva: number[] = Array.from(
    { length: this.pageProperties.totalPages },
    (_, index) => index
  );

  usuario: UsuarioResponse = new UsuarioResponse();

  constructor(
    private http: HttpClient,
    private rootNavigateService: RootNavigateService,
    private localStorage: LocalStorageService,
    private formBuilder: FormBuilder,
    private observableNotify: ObservableNotifyService,
    private cdr: ChangeDetectorRef) {

    this.http = http;
    this.localStorage = localStorage;
    this.rootNavigateService = rootNavigateService;
    this.usuario = this.localStorage.getItem('userData');
    this.idSolicitudForm = this.formBuilder.group({
      id: ['', [Validators.required, numeroMayorACeroValidator]]
    });
    this.listarSolicitudes();
  }

  private subscription: Subscription | undefined;

  ngOnInit(): void {
    this.observableNotify.pathToSuscribe = UrlsProperties.SUSCRIBE_RESPONSABLE;
    this.subscription = this.observableNotify.obtenerActualizacion()
      .subscribe({
        next: (valor: number) => {
          if (valor === 1) {
            this.listarSolicitudes();
          }
        },
        error: (error) => {
          console.error('Error en SSE:', error);
        }
      });
  }

  ngOnDestroy(): void {
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
    this.observableNotify.cerrarConexion();
  }




  listarSolicitudes(): void {
    const body: PageRequest = {
      id: this.usuario.id,
      page: this.pageProperties.currentPage,
      size: this.pageProperties.pageSize,
      sortBy: this.pageProperties.sortBy,
      direction: this.pageProperties.direction
    }

    this.http.post<PageResponse<SolicitudResponResponse>>(
      UrlsProperties.PATH_LIST_SOLIPENDIENTE,
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
        this.cdr.detectChanges(); // FORZAR detección de cambios
      }),
      catchError(error => {
        console.error('Error en la petición:', error);
        alert('Hubo un error al listar las solicitudes pendientes para el responsable');
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
      UrlsProperties.PATH_SOLI_BYID,
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

  botonTraerDatosModal(idSolicitud: number | undefined): void {
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
